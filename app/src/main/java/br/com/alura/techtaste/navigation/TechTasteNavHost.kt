package br.com.alura.techtaste.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import br.com.alura.techtaste.samples.sampleCategories
import br.com.alura.techtaste.samples.sampleMappedOrders
import br.com.alura.techtaste.ui.screens.AssistantScreen
import br.com.alura.techtaste.ui.screens.HomeScreen
import br.com.alura.techtaste.ui.viewmodels.AssistantViewModel
import kotlinx.coroutines.launch

object Routes {
    const val HOME = "home"
    const val ASSISTANT = "assistant"
}

@Composable
fun TechTasteNavHost(navController: NavHostController) {
    NavHost(
        navController = navController,
        startDestination = Routes.HOME
    ) {
        composable(Routes.HOME) {
            HomeScreen(
                ordersSection = sampleMappedOrders,
                categories = sampleCategories
            )
        }
        composable(Routes.ASSISTANT) {
            val viewModel = viewModel<AssistantViewModel>()
            val uiState by viewModel.uiState.collectAsState()
            val scope = rememberCoroutineScope()
            AssistantScreen(
                uiState = uiState,
                onCloseClick = {
                    navController.popBackStack()
                },
                onSendClick = { text ->
                    scope.launch {
                        viewModel.send(text)
                    }
                },
                onDeleteMessageClick = { viewModel.deleteErrorMessage() },
                onRetryMessageClick = {
                    scope.launch {
                        viewModel.retry()
                    }
                }
            )
        }
    }
}
