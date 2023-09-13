package br.com.alura.techtaste.samples

import androidx.compose.ui.tooling.preview.datasource.LoremIpsum
import br.com.alura.techtaste.R
import br.com.alura.techtaste.models.Order
import br.com.alura.techtaste.models.Message
import java.math.BigDecimal
import kotlin.random.Random

val sampleMessages = List(10) {
    Message(
        text = LoremIpsum(Random.nextInt(5, 20)).values.first(),
        orders = if (it % 2 != 0) listOf(
            Order(
                name = "arroz",
                description = "integral",
                price = BigDecimal("20.0"),
            ),
            Order(
                name = "feijão",
                description = "carioca",
                price = BigDecimal("8.0"),
            ),
        ) else emptyList(),
        isAuthor = it % 2 == 0,
        isError = true
    )
}

val sampleOrders = List(10) {
    Order(
        name = LoremIpsum(Random.nextInt(2, 10)).values.first(),
        description = LoremIpsum(Random.nextInt(10, 20)).values.first(),
        price = BigDecimal(Random.nextDouble(300.0)),
        image = sampleRandomImage
    )
}

val sampleMappedOrders = mapOf(
    "Mais populares" to sampleOrders.shuffled().subList(0, 3),
    "Delícias vegetarianas" to sampleOrders.shuffled().subList(0, 3)
)

val sampleCategories = mapOf(
    R.drawable.petiscos to "Petiscos",
    R.drawable.principais to "Principais",
    R.drawable.massas to "Massas",
    R.drawable.sobremesas to "Sobremesas",
    R.drawable.bebidas to "Bebidas",
)

val sampleRandomImage get() = "https://picsum.photos/${
    Random.nextInt(
        1280,
        1920
    )
}/${
    Random.nextInt(
        720,
        1920
    )
}"

