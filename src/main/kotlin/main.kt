import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.ClickableText
import androidx.compose.foundation.window.WindowDraggableArea
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.vectorXmlResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.*


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
    val windowState = rememberWindowState(
        width = 800.dp, height = 600.dp,
        position = WindowPosition(Alignment.Center)
    )
    Window(
        onCloseRequest = ::exitApplication,
        undecorated = true,
        title = "音乐播放器",
        resizable = true,
        state = windowState
    ) {

        MaterialTheme {
            Column(modifier = Modifier.background(Color.White)) {
                var searchText by rememberSaveable { mutableStateOf("") }
                WindowDraggableArea {
                    TitleBar(
                        searchText,
                        onSearchTextChange = { searchText = it },
                        windowState = windowState
                    )
                }

                Row(modifier = Modifier.background(Color.White).height(50.dp)) {
                }
            }
        }
    }
}


@Composable
fun ApplicationScope.TitleBar(
    searchText: String,
    onSearchTextChange: (String) -> Unit,
    windowState: WindowState
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.background(Color(0xFFD8534B))
            .fillMaxWidth()
            .height(50.dp)
            .padding(15.dp, 0.dp)
    ) {

        Image(
            painter = painterResource("images/ic_close_24.xml"),
            contentDescription = "search icon",
            Modifier.size(20.dp, 20.dp)
                .clickable { exitApplication() }
        )
        Image(
            painter = painterResource("images/ic_maximum_24.xml"),
            contentDescription = "search icon",
            Modifier.size(20.dp, 20.dp).offset(4.dp, 0.dp)
                .clickable {
                    windowState.placement =
                        if (windowState.placement == WindowPlacement.Maximized) WindowPlacement.Floating
                        else WindowPlacement.Maximized
                }
        )
        Image(
            painter = painterResource("images/ic_minimize_24.xml"),
            contentDescription = "search icon",
            Modifier.size(20.dp, 20.dp).offset(6.dp, (-6).dp)
                .clickable { windowState.isMinimized = true }
        )
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
            painter = painterResource("images/ic_search_24.xml"),
            contentDescription = "search icon",
            Modifier.size(20.dp, 20.dp).offset((-20).dp - 5.dp, 0.dp)
        )
    }
}

@Preview
@Composable
fun preview() {
    Text(text = "Hello Compose!")
}