package br.com.alura.techtaste.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.widthIn
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.ColorPainter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import br.com.alura.techtaste.R
import br.com.alura.techtaste.ui.theme.TechTasteTheme
import coil.compose.AsyncImage

@Composable
fun MainBanner(
    modifier: Modifier = Modifier
) {
    Column(
        modifier,
        verticalArrangement = Arrangement.spacedBy(24.dp)
    ) {
        AsyncImage(
            R.drawable.app_banner,
            contentDescription = "banner principal",
            Modifier
                .fillMaxWidth()
                .heightIn(max = 250.dp),
            contentScale = ContentScale.Crop,
            placeholder = ColorPainter(Color.Gray)
        )
        Box(
            modifier = Modifier
                .fillMaxWidth()
        ) {
            AsyncImage(
                R.drawable.app_logo,
                contentDescription = "logo do início",
                Modifier
                    .align(
                        Alignment.Center
                    )
                    .widthIn(max = 200.dp)
                    .heightIn(max = 40.dp),
                contentScale = ContentScale.Crop,
                placeholder = ColorPainter(Color.Gray)
            )
        }
    }
}

@Preview
@Composable
fun MainBannerPreview() {
    TechTasteTheme {
        Surface(color = MaterialTheme.colorScheme.background) {
            MainBanner()
        }
    }
}