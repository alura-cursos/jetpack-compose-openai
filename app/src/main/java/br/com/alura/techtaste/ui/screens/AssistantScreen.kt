package br.com.alura.techtaste.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Send
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.datasource.LoremIpsum
import androidx.compose.ui.unit.dp
import br.com.alura.techtaste.R
import br.com.alura.techtaste.samples.sampleMessages
import br.com.alura.techtaste.ui.components.AssistantMessage
import br.com.alura.techtaste.ui.states.AssistantUiState
import br.com.alura.techtaste.ui.theme.Gray1
import br.com.alura.techtaste.ui.theme.Gray2
import br.com.alura.techtaste.ui.theme.MediumOrange
import br.com.alura.techtaste.ui.theme.TechTasteTheme
import coil.compose.AsyncImage
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun AssistantScreen(
    uiState: AssistantUiState,
    onCloseClick: () -> Unit,
    onSendClick: (String) -> Unit,
    modifier: Modifier = Modifier,
) {
    val messages = uiState.messages
    var text = uiState.text
    Column(modifier.fillMaxSize()) {
        Row(
            Modifier
                .height(68.dp)
                .fillMaxWidth()
                .background(
                    MediumOrange,
                    shape = RoundedCornerShape(bottomStart = 24.dp),
                ),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            AsyncImage(
                R.drawable.app_icon,
                contentDescription = "ícone do floating action button",
                Modifier
                    .padding(start = 24.dp)
                    .fillMaxHeight()
                    .width(36.dp),
                placeholder = painterResource(id = R.drawable.app_icon)
            )
            Icon(
                Icons.Filled.Close,
                contentDescription = null,
                Modifier
                    .padding(end = 16.dp)
                    .clip(CircleShape)
                    .clickable {
                        onCloseClick()
                    },
                tint = Gray1,
            )
        }
        LazyColumn(
            Modifier
                .weight(1f)
        ) {
            items(messages) { message ->
                val alignment = if (message.isAuthor) {
                    Alignment.CenterEnd
                } else {
                    Alignment.CenterStart
                }
                BoxWithConstraints(Modifier.fillMaxWidth()) {
                    Box(
                        modifier = Modifier
                            .padding(8.dp)
                            .widthIn(
                                max = maxWidth / 1.3f
                            )
                            .align(alignment)
                            .background(
                                Gray1,
                                shape = RoundedCornerShape(10.dp)
                            )
                    ) {
                        AssistantMessage(message)
                    }
                }
            }
        }
        Row(
            Modifier
                .heightIn(min = 80.dp)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            val keyboardController = LocalSoftwareKeyboardController.current
            val scope = rememberCoroutineScope()
            OutlinedTextField(
                value = text,
                onValueChange = {
                    uiState.onTextChange(it)
                },
                Modifier
                    .padding(
                        horizontal = 8.dp,
                        vertical = 16.dp
                    )
                    .weight(1f),
                placeholder = {
                    Text("Digite sua mensagem...")
                },
                colors = OutlinedTextFieldDefaults.colors(
                    unfocusedBorderColor = MediumOrange,
                    focusedBorderColor = MediumOrange,
                    cursorColor = MediumOrange,
                    focusedPlaceholderColor = Gray2,
                    unfocusedPlaceholderColor = Gray2,
                ),
                shape = RoundedCornerShape(8.dp)
            )
            Box(
                modifier = Modifier
                    .padding(8.dp)
                    .background(
                        MediumOrange,
                        shape = CircleShape
                    )
                    .size(48.dp)
            ) {
                Icon(
                    imageVector = Icons.Filled.Send,
                    contentDescription = null,
                    Modifier
                        .align(Alignment.Center)
                        .clickable {
                            scope.launch {
                                keyboardController?.hide()
                                delay(100)
                                onSendClick(text)
                                uiState.onCleanText()
                            }
                        },
                    tint = Gray1
                )
            }
        }
    }
}


@Preview
@Composable
fun AssistantScreenPreview() {
    TechTasteTheme {
        Surface(
            Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.background
        ) {
            Box {
                AssistantScreen(
                    AssistantUiState(messages = sampleMessages),
                    onCloseClick = {
                    },
                    onSendClick = {},
                )
            }
        }
    }
}

@Preview
@Composable
fun AssistantScreenWithTextInTextFieldPreview() {
    TechTasteTheme {
        Surface(
            Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.background
        ) {
            Box {
                AssistantScreen(
                    AssistantUiState(
                        messages = sampleMessages,
                        text = LoremIpsum(30).values.first()
                    ),
                    onCloseClick = {
                    },
                    onSendClick = {},
                )
            }
        }
    }

}