package br.com.alura.techtaste.ui.states

import br.com.alura.techtaste.models.Message

data class AssistantUiState(
    val text: String = "",
    val onTextChange: (String) -> Unit = {},
    val onCleanText: () -> Unit = {},
    val messages: List<Message> = emptyList(),
)

