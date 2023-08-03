package br.com.alura.techtaste.ui.viewmodels

import androidx.compose.ui.tooling.preview.datasource.LoremIpsum
import androidx.lifecycle.ViewModel
import br.com.alura.techtaste.models.Message
import br.com.alura.techtaste.samples.sampleOrders
import br.com.alura.techtaste.ui.states.AssistantUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlin.random.Random

class AssistantViewModel : ViewModel() {

    private val _uiState = MutableStateFlow(AssistantUiState())
    val uiState = _uiState.asStateFlow()

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

    fun send(text: String) {
        _uiState.update {
            it.copy(
                messages = it.messages +
                        Message(text, isAuthor = true)
            )
        }
        val message = LoremIpsum(Random.nextInt(10, 50)).values.first()
        val orders = sampleOrders.shuffled().subList(0, 2)
        _uiState.update { currentState ->
            currentState.copy(
                messages = _uiState.value.messages + Message(
                    text = message,
                    isAuthor = false,
                    orders = orders,
                )
            )
        }
    }

}


