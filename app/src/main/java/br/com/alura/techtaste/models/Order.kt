package br.com.alura.techtaste.models

import java.math.BigDecimal

class Order(
    val name: String,
    val price: BigDecimal,
    val description: String,
    val image: String? = null
)