package ru.normno.myktortutorialkmp

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import org.jetbrains.compose.ui.tooling.preview.Preview
import ru.normno.myktortutorialkmp.data.KtorApiClient
import ru.normno.myktortutorialkmp.data.buildHttpClient
import ru.normno.myktortutorialkmp.data.game.Game
import ru.normno.myktortutorialkmp.data.game.Games
import ru.normno.myktortutorialkmp.data.getHttpEngine
import ru.normno.myktortutorialkmp.result_and_error.Error
import ru.normno.myktortutorialkmp.result_and_error.Result

val ktorApiClient = KtorApiClient(buildHttpClient(getHttpEngine()))

@Composable
@Preview
fun App() {
    MaterialTheme {

        var games by remember { mutableStateOf<List<Game>>(emptyList()) }
        var result: Result<Games, Error>? = null
        LaunchedEffect(true) {
            result = ktorApiClient.get<Games>("v1/games")
            games = result.data?.games ?: emptyList()
        }

        LazyColumn(
            modifier = Modifier
                .fillMaxWidth(),
            contentPadding = PaddingValues(4.dp),
        ) {
            items(games) { game ->
                Text(
                    text = game.name,
                    style = MaterialTheme.typography.body1,
                    modifier = Modifier
                        .padding(horizontal = 16.dp)
                )
            }
        }
    }
}