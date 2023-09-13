package br.com.alura.techtaste.ui.viewmodels

import androidx.lifecycle.ViewModel
import br.com.alura.techtaste.models.Message
import br.com.alura.techtaste.openai.OrdersOpenAi
import br.com.alura.techtaste.ui.states.AssistantUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class AssistantViewModel : ViewModel() {

    private val _uiState = MutableStateFlow(AssistantUiState())
    val uiState = _uiState.asStateFlow()
    private val ordersOpenAi: OrdersOpenAi = OrdersOpenAi()

    init {
        _uiState.update { currentState ->
            currentState.copy(
                onTextChange = {
                    _uiState.value = _uiState.value.copy(text = it)
                },
                onCleanText = {
                    _uiState.value = _uiState.value.copy(text = "")
                }
            )
        }
    }

    suspend fun send(text: String) {
        _uiState.update {
            it.copy(
                messages = it.messages +
                        Message(text, isAuthor = true) +
                Message("", isAuthor = false, isLoading = true)
            )
        }
        val (message, orders) = ordersOpenAi.messageAndOrders(text)
        val currentMessages = _uiState.value.messages
        val lastMessage = currentMessages.last()
        val messages = if(!lastMessage.isAuthor && lastMessage.isLoading) {
            currentMessages.dropLast(1)
        } else {
            currentMessages
        }
        _uiState.update { currentState ->
            currentState.copy(
                messages = messages + Message(
                    text = message,
                    isAuthor = false,
                    orders = orders,
                )
            )
        }
    }

}

