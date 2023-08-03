package br.com.alura.techtaste.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import br.com.alura.techtaste.extensions.toBrazilianCurrency
import br.com.alura.techtaste.models.Message
import br.com.alura.techtaste.samples.sampleMessages
import br.com.alura.techtaste.samples.sampleRandomImage
import br.com.alura.techtaste.ui.theme.Gray1
import br.com.alura.techtaste.ui.theme.LightOrange
import br.com.alura.techtaste.ui.theme.MediumOrange
import br.com.alura.techtaste.ui.theme.TechTasteTheme
import coil.compose.AsyncImage

@Composable
fun AssistantMessage(
    message: Message,
    modifier: Modifier = Modifier,
    onOrderClick: () -> Unit = {}
) {
    Column(modifier) {
        Text(
            text = message.text,
            Modifier.padding(8.dp),
            color = LightOrange
        )
        if (message.orders.isNotEmpty()) {
            message.orders.forEach { order ->
                Column(Modifier.padding(horizontal = 8.dp)) {
                    Row(
                        Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Row(
                            Modifier
                                .weight(1f)
                                .padding(end = 8.dp),
                            horizontalArrangement = Arrangement.spacedBy(
                                16.dp
                            ),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            AsyncImage(
                                order.image,
                                contentDescription = null,
                                Modifier
                                    .size(80.dp, 70.dp)
                                    .background(
                                        Color.Gray,
                                        shape = RoundedCornerShape(
                                            20.dp
                                        )
                                    )
                                    .clip(
                                        shape = RoundedCornerShape(
                                            20.dp
                                        )
                                    ),
                                contentScale = ContentScale.Crop
                            )
                            Text(
                                text = order.name,
                                color = MediumOrange,
                                fontSize = 18.sp,
                                fontWeight = FontWeight.Bold,
                                overflow = TextOverflow.Ellipsis
                            )
                        }
                        Text(text = order.price.toBrazilianCurrency())
                    }
                    Spacer(
                        modifier = Modifier
                            .padding(vertical = 8.dp)
                            .height(1.dp)
                            .fillMaxWidth()
                            .background(Color.Gray.copy(alpha = 0.5f))
                    )
                }

            }
            Row(
                Modifier
                    .padding(8.dp)
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(text = "Total:")
                val total = message.orders.sumOf { it.price }
                Text(
                    text = total.toBrazilianCurrency(),
                    fontSize = 22.sp
                )
            }
            Button(
                onClick = { onOrderClick() },
                Modifier
                    .padding(8.dp)
                    .fillMaxWidth()

            ) {
                Text(text = "Pedir", color = Gray1)
            }
        }
    }
}

@Preview
@Composable
fun AssistantMessagePreview() {
    TechTasteTheme {
        Surface(color = MaterialTheme.colorScheme.background) {
            AssistantMessage(message = sampleMessages.first { it.orders.isEmpty() })
        }
    }
}

@Preview
@Composable
fun AssistantMessageWithOrdersPreview() {
    TechTasteTheme {
        Surface(color = MaterialTheme.colorScheme.background) {
            AssistantMessage(message = sampleMessages.first { it.orders.isNotEmpty() })
        }
    }
}