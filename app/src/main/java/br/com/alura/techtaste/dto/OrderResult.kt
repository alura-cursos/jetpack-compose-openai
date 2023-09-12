package br.com.alura.techtaste.dto

import br.com.alura.techtaste.models.Order
import kotlinx.serialization.Serializable
import java.math.BigDecimal

@Serializable
class OrderResult(
    val orders: List<OrderDto>
)

@Serializable
class OrderDto(
    val name: String,
    val description: String,
    val price: String
) {
    fun toOrder(image: String? = null) = Order(
        name = name,
        description = description,
        price = BigDecimal(price),
        image = image
    )
}