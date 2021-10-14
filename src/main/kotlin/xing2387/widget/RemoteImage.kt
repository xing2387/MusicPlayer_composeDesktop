package xing2387.ui

import androidx.compose.foundation.Image
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import org.jetbrains.skija.Image.makeFromEncoded
import xing2387.repos.net.ImageLoader

/**
 * copy from [GerardPaligot/discovering-movies](https://github.com/GerardPaligot/discovering-movies/blob/main/components/src/desktopMain/kotlin/com/paligot/movies/components/RemoteImage.kt)
 */
@Composable
fun RemoteImage(
    url: String,
    contentDescription: String?,
    modifier: Modifier = Modifier,
    contentScale: ContentScale = ContentScale.Fit
) {
    val image = remember(url) { mutableStateOf<ImageBitmap?>(null) }
    LaunchedEffect(url) {
        ImageLoader.load(url)?.let {
            image.value = makeFromEncoded(it).asImageBitmap()
        }
    }
    if (image.value != null) {
        Image(
            bitmap = image.value!!,
            contentDescription = contentDescription,
            modifier = modifier,
            contentScale = contentScale
        )
    }
}