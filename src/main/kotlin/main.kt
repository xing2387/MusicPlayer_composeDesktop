import androidx.compose.desktop.Window
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.layout.AlignmentLine
import androidx.compose.ui.layout.HorizontalAlignmentLine
import androidx.compose.ui.res.vectorXmlResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

fun main() = Window {
    var text by remember { mutableStateOf("Hello, World!") }

    MaterialTheme {
        Column {
            var searchText by rememberSaveable { mutableStateOf("") }
            TitleBar(searchText, onSearchTextChange = { searchText = it })
            Row(modifier = Modifier.background(Color.White).height(50.dp)) {

            }
        }
    }
}


@Composable
fun TitleBar(searchText: String, onSearchTextChange: (String) -> Unit) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.background(Color(0XFFC72E2E))
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