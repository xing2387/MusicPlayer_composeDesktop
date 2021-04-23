import androidx.compose.desktop.Window
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextDecoration
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
        TextField(
            value = searchText,
            onValueChange = onSearchTextChange,
            singleLine = true,
            colors = TextFieldDefaults.textFieldColors(
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent,
                cursorColor = Color.Black,
                textColor = Color.Black
            ),
            shape = MaterialTheme.shapes.small.copy(CornerSize(11.dp)),
            modifier = Modifier.width(218.dp + 124.dp)
                .height(44.dp)
                .
                .padding(124.dp, 0.dp, 0.dp, 0.dp),
        )
    }
}