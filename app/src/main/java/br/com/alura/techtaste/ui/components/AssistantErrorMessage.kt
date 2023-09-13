package br.com.alura.techtaste.ui.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import br.com.alura.techtaste.ui.theme.Gray1
import br.com.alura.techtaste.ui.theme.LightOrange
import br.com.alura.techtaste.ui.theme.TechTasteTheme

@Composable
fun AssistantErrorMessage(
    error: String,
    modifier: Modifier = Modifier,
    onRetryMessageClick: () -> Unit = {},
    onDeleteMessageClick: () -> Unit = {}
) {
    Column(modifier) {
        Text(
            text = error,
            Modifier.padding(8.dp),
            color = LightOrange
        )
        Button(
            onClick = { onRetryMessageClick() },
            Modifier
                .padding(horizontal = 8.dp)
                .fillMaxWidth()
        ) {
            Text(text = "Tentar novamente", color = Gray1)
        }
        TextButton(
            onClick = { onDeleteMessageClick() },
            Modifier
                .padding(horizontal = 8.dp)
                .fillMaxWidth()
        ) {
            Text(text = "Apagar")
        }
    }
}

@Preview
@Composable
fun AssistantErrorMessagePreview() {
    TechTasteTheme {
        Surface(color = MaterialTheme.colorScheme.background) {
            AssistantErrorMessage(
                error = "falha ao carregar mensagem"
            )
        }
    }
}