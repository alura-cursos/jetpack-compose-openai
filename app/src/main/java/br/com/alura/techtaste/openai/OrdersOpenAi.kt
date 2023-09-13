package br.com.alura.techtaste.openai

import br.com.alura.techtaste.BuildConfig
import br.com.alura.techtaste.dto.OrderResult
import br.com.alura.techtaste.models.Order
import br.com.alura.techtaste.samples.sampleRandomImage
import com.aallam.openai.api.chat.ChatCompletionRequest
import com.aallam.openai.api.chat.ChatMessage
import com.aallam.openai.api.chat.ChatRole
import com.aallam.openai.api.model.ModelId
import com.aallam.openai.client.OpenAI
import kotlinx.serialization.json.Json

private const val JSON = """
{
  "orders": [
    {
      "name": "Bife Grelhado",
      "description": "Um suculento bife grelhado acompanhado de legumes frescos.",
      "price": "35.90"
    },
    {
      "name": "Massa Carbonara",
      "description": "Massa cozida al dente com molho cremoso de queijo, bacon crocante e pimenta preta.",
      "price": "28.50"
    },
    {
      "name": "Salmão ao Molho de Maracujá",
      "description": "Filé de salmão grelhado coberto com molho agridoce de maracujá, servido com arroz selvagem.",
      "price": "42.75"
    },
    {
      "name": "Pizza Quatro Queijos",
      "description": "Pizza com uma deliciosa combinação de queijos: mussarela, gorgonzola, parmesão e provolone.",
      "price": "31.20"
    },
    {
      "name": "Salada Mediterrânea",
      "description": "Mix de folhas verdes, tomate, pepino, azeitonas, queijo feta e molho de azeite e ervas.",
      "price": "18.90"
    },
    {
      "name": "Frango Teriyaki",
      "description": "Peito de frango grelhado com molho teriyaki, acompanhado de arroz branco e legumes salteados.",
      "price": "29.75"
    },
    {
      "name": "Hambúrguer Clássico",
      "description": "Hambúrguer suculento com alface, tomate, cebola, queijo cheddar e molho especial, no pão brioche.",
      "price": "21.50"
    },
    {
      "name": "Tempura de Vegetais",
      "description": "Seleção crocante de legumes variados em massa de tempura, servido com molho de imersão.",
      "price": "16.25"
    },
    {
      "name": "Risoto de Cogumelos",
      "description": "Risoto cremoso preparado com arroz arbóreo e uma variedade de cogumelos frescos.",
      "price": "27.80"
    },
    {
      "name": "Sundae de Chocolate",
      "description": "Sorvete de baunilha coberto com calda quente de chocolate, chantilly e amêndoas torradas.",
      "price": "12.00"
    },
    {
      "name": "Salada Caesar",
      "description": "Alface romana, croutons, parmesão e molho Caesar.",
      "price": "15.00"
    },
    {
      "name": "Macarrão Primavera",
      "description": "Macarrão com vegetais frescos e molho de tomate leve.",
      "price": "19.75"
    },
    {
      "name": "Camarão Grelhado",
      "description": "Camarões grelhados servidos com molho de alho e ervas.",
      "price": "37.50"
    },
    {
      "name": "Sanduíche de Frango",
      "description": "Sanduíche de frango grelhado com alface, tomate e maionese.",
      "price": "14.50"
    },
    {
      "name": "Lasanha de Legumes",
      "description": "Lasanha com camadas de massa, legumes e molho bechamel.",
      "price": "23.90"
    },
    {
      "name": "Mousse de Morango",
      "description": "Mousse leve de morango com cobertura de frutas frescas.",
      "price": "9.50"
    },
    {
      "name": "Creme Brûlée",
      "description": "Creme de baunilha com uma crosta de açúcar queimado.",
      "price": "8.75"
    },
    {
      "name": "Smoothie de Frutas",
      "description": "Smoothie refrescante com uma mistura de frutas.",
      "price": "6.25"
    },
    {
      "name": "Água Mineral",
      "description": "Garrafa de água mineral.",
      "price": "2.00"
    },
    {
      "name": "Refrigerante",
      "description": "Lata de refrigerante.",
      "price": "3.00"
    },
    {
      "name": "Café Expresso",
      "description": "Uma dose de café expresso.",
      "price": "2.50"
    },
    {
      "name": "Chá Gelado",
      "description": "Chá gelado de frutas.",
      "price": "3.50"
    },
    {
      "name": "Frutas Frescas",
      "description": "Seleção de frutas frescas da estação.",
      "price": "7.00"
    },
    {
      "name": "Tiramisu",
      "description": "Sobremesa italiana com camadas de biscoitos, café e mascarpone.",
      "price": "11.50"
    },
    {
      "name": "Hambúrguer Vegetariano",
      "description": "Hambúrguer de legumes acompanhado de alface, tomate e molho especial, no pão integral.",
      "price": "18.50"
    },
    {
      "name": "Sopa de Tomate",
      "description": "Sopa de tomate caseira com croutons.",
      "price": "9.25"
    },
    {
      "name": "Batata Frita",
      "description": "Porção de batata frita crocante.",
      "price": "6.00"
    },
    {
      "name": "Pudim de Leite",
      "description": "Pudim de leite cremoso com calda de caramelo.",
      "price": "10.00"
    },
    {
      "name": "Chá Quente",
      "description": "Uma xícara de chá quente.",
      "price": "2.75"
    },
    {
      "name": "Mojito",
      "description": "Coquetel refrescante de rum, hortelã, limão e água com gás.",
      "price": "8.50"
    }
  ]
}
"""

private const val SYSTEM_TEXT = """
Você será um assistente de App de restaurante. 
Você receberá solicitações para sugerir pedidos e deverá devolver um pedido adequado com base na solicitação.
As solicitações podem ser pelo nome, descrição e preço.
A solicitação a partir de preço deve considerar o valor total de cada refeição, por exemplo, se pedir até 50 reais, deve somar o valor de cada refeição e deve ser igual ou menor que 50.
Se você não tiver uma resposta para o que foi pedido, responda apenas com o texto: 'Infelizmente não encontramos o que pediu'.

Todas respostas devem conter o seguinte padrão:

[texto explicando a decisão de maneira resumida]

[JSON com a lista de pedidos]

A lista de pedidos deve seguir o seguinte formato:

{
  "orders": [
    {
      "name": "",
      "description": "",
      "price": ""
    },
    {
      "name": "",
      "description": "",
      "price": ""
    },
  ]
}

Você deve se atentar aos pedidos e apresentar o te pedirem apenas! Abaixo está a base de dados para você decidir o que deve ser sugerido:

$JSON
"""

class OrdersOpenAi(
    private val openAI: OpenAI = OpenAI("BuildConfig.API_KEY"),
    private val modelId: ModelId = ModelId("gpt-3.5-turbo")
) {

    private suspend fun makeRequest(text: String): String {
        val chatCompletionRequest = ChatCompletionRequest(
            model = modelId,
            messages = listOf(
                ChatMessage(
                    role = ChatRole.System,
                    content = SYSTEM_TEXT
                ),
                ChatMessage(
                    role = ChatRole.User,
                    content = text
                )
            )
        )
        val openAiResult = openAI.chatCompletion(chatCompletionRequest)
            .choices
            .mapNotNull { chatChoice ->
                chatChoice.message.content
            }.joinToString(separator = "")
        return openAiResult
    }

    suspend fun messageAndOrders(text: String) : Pair<String, List<Order>> {
        val openAiResult = makeRequest(text)
        val jsonPattern = "\\{.*\\}".toRegex(RegexOption.DOT_MATCHES_ALL)
        val matchResult = jsonPattern.find(openAiResult)
        return matchResult?.value?.let { rawJson ->
            val json = Json { ignoreUnknownKeys = true }
            val orders = json.decodeFromString<OrderResult>(rawJson)
                .orders
                .map {
                    it.toOrder(image = sampleRandomImage)
                }
            val message = openAiResult.substringBefore(rawJson)
            Pair(message, orders)
        } ?: Pair(openAiResult, emptyList())
    }


}