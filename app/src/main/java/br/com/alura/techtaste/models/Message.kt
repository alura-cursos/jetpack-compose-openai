package br.com.alura.techtaste.models

data class Message(
    val text: String,
    val orders: List<Order> = emptyList(),
    val isAuthor: Boolean = true,
    val isLoading: Boolean = false
)