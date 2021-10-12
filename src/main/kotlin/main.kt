import androidx.compose.desktop.AppWindow
import androidx.compose.desktop.LocalAppWindow
import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.LocalContentAlpha
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.platform.LocalWindowInfo
import androidx.compose.ui.res.vectorXmlResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.WindowPosition
import androidx.compose.ui.window.application
import androidx.compose.ui.window.rememberWindowState
import org.jetbrains.skiko.currentSystemTheme


fun main() = application {
    var text by remember { mutableStateOf("Hello, World!") }

    /*
    * 圆角：
    * https://github.com/JetBrains/compose-jb/issues/1179
    * We don't have compose api for this. But it could be done using native API's passing them window.windowHandle
    * 透明背景：
    * https://github.com/JetBrains/compose-jb/issues/418
    * Currently, not all supported operating systems support window transparency, so for now we decided to disable transparency in order to achieve the same behavior on all operating systems.
    * */
    Window(
        onCloseRequest = ::exitApplication,
        undecorated = true,
        title = "音乐播放器",
        resizable = true,
        state = rememberWindowState(
            width = 800.dp, height = 600.dp,
            position = WindowPosition(Alignment.Center)
        )
    ) {

        MaterialTheme(
            shapes = MaterialTheme.shapes.copy(
                RoundedCornerShape(16.dp),
                RoundedCornerShape(16.dp),
                RoundedCornerShape(16.dp)
            )
        ) {
            Column(modifier = Modifier.background(Color.Red, RoundedCornerShape(16.dp))) {
                var searchText by rememberSaveable { mutableStateOf("") }
                TitleBar(searchText, onSearchTextChange = { searchText = it })
                Row(modifier = Modifier.background(Color.White).height(50.dp)) {
                }
            }
        }
    }
}


@Composable
fun TitleBar(searchText: String, onSearchTextChange: (String) -> Unit) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.background(Color(0xFFD8534B), RoundedCornerShape(16.dp))
            .fillMaxWidth()
            .height(50.dp)
            .padding(15.dp, 0.dp)
    ) {
        Text("音乐", fontSize = 18.sp, color = Color.White)
        Surface(
            shape = RoundedCornerShape(11.dp),
            color = Color(0x1F000000),
            modifier = Modifier.height(22.dp)
                .padding(124.dp, 0.dp, 0.dp, 0.dp)
        ) {
            BasicTextField(
                value = searchText,
                onValueChange = onSearchTextChange,
                textStyle = TextStyle.Default.copy(color = Color.White, fontSize = 10.sp),
                singleLine = true,
                cursorBrush = SolidColor(Color.White),
                modifier = Modifier.width(218.dp)
                    .padding(11.dp, 5.dp, 28.dp, 0.dp)
                    .align(Alignment.CenterVertically),
            )
        }
        Image(
            imageVector = vectorXmlResource("images/ic_search_24.xml"),
            contentDescription = "Compose logo",
            Modifier.size(20.dp, 20.dp).offset((-20).dp - 5.dp, 0.dp)
        )
    }
}

@Preview
@Composable
fun preview() {
    Text(text = "Hello Compose!")
}