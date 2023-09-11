package br.com.alura.techtaste

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.LargeFloatingActionButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import br.com.alura.techtaste.navigation.Routes
import br.com.alura.techtaste.navigation.TechTasteNavHost
import br.com.alura.techtaste.ui.theme.MediumOrange
import br.com.alura.techtaste.ui.theme.TechTasteTheme
import coil.compose.AsyncImage
import com.aallam.openai.api.chat.ChatCompletionRequest
import com.aallam.openai.api.chat.ChatMessage
import com.aallam.openai.api.chat.ChatRole
import com.aallam.openai.api.model.ModelId
import com.aallam.openai.client.OpenAI
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val openAI = OpenAI("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")
        val chatCompletionRequest = ChatCompletionRequest(
            model = ModelId("gpt-3.5-turbo"),
            messages = listOf(
                ChatMessage(
                    role = ChatRole.System,
                    content = "Você vai ser um assistente de restaurante"
                ),
                ChatMessage(
                    role = ChatRole.User,
                    content = "me sugira uma refeição light"
                )
            )
        )
        lifecycleScope.launch {
            openAI.chatCompletion(chatCompletionRequest)
                .choices
                .forEach {chatChoice ->
                    Log.i("MainActivity", "onCreate: ${chatChoice.message}")
                }
        }

        setContent {
            TechTasteTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    val navController = rememberNavController()
                    val currentBackStack by navController.currentBackStackEntryAsState()
                    val currentRoute = currentBackStack?.destination?.route
                    TechTasteApp(currentRoute, navController) {
                        TechTasteNavHost(navController)
                    }
                }
            }
        }
    }
}

@Composable
private fun TechTasteApp(
    currentRoute: String?,
    navController: NavHostController = rememberNavController(),
    content: @Composable () -> Unit
) {
    Scaffold(
        floatingActionButton = {
            when (currentRoute) {
                Routes.HOME -> {
                    LargeFloatingActionButton(
                        onClick = { navController.navigate(Routes.ASSISTANT) },
                        containerColor = MediumOrange
                    ) {
                        AsyncImage(
                            R.drawable.app_icon,
                            contentDescription = "ícone do floating action button",
                            Modifier.size(36.dp),
                            placeholder = painterResource(id = R.drawable.app_icon)
                        )
                    }
                }
            }
        }) {
        Box(Modifier.padding(it)) {
            content()
        }
    }
}

@Preview
@Composable
fun TechTasteAppInHomeRoutePreview() {
    TechTasteTheme {
        Surface(
            Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.background
        ) {
            TechTasteApp(currentRoute = Routes.HOME) {
            }
        }
    }
}
