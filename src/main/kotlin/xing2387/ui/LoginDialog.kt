package xing2387.ui

import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp


//fun Dialog(
//    onCloseRequest: () -> Unit,
//    state: DialogState = rememberDialogState(),
//    visible: Boolean = true,
//    title: String = "Untitled",
//    icon: Painter? = null,
//    undecorated: Boolean = false,
//    resizable: Boolean = true,
//    enabled: Boolean = true,
//    focusable: Boolean = true,
//    onPreviewKeyEvent: ((KeyEvent) -> Boolean) = { false },
//    onKeyEvent: ((KeyEvent) -> Boolean) = { false },
//    content: @Composable DialogWindowScope.() -> Unit
//) {
@Composable
fun LoginDialog(onCloseClicked: () -> Unit, onLoginClicked: (String, String) -> Unit) {
    Surface(
        modifier = Modifier.size(300.dp, 400.dp).offset(250.dp, 100.dp),
        shape = RoundedCornerShape(10.dp),
        elevation = 10.dp
    ) {
        Column(modifier = Modifier.fillMaxSize().padding(36.dp)) {
            Image(
                painter = painterResource("images/ic_close_24.xml"),
                contentDescription = "close",
                modifier = Modifier.size(20.dp).clickable { onCloseClicked() }
                    .offset((-10).dp, (-10).dp),
                colorFilter = ColorFilter.tint(Color(0xFF666666), BlendMode.SrcIn)
            )

            var hintUserName = "用户名/手机号"
            var hintPasswd = "密码"

            Text(
                "登录",
                style = TextStyle(Color(0xFF333333), 26.sp),
                modifier = Modifier.offset(0.dp, 24.dp)
            )

            var userName by remember { mutableStateOf(hintUserName) }
            var password by remember { mutableStateOf(hintPasswd) }

            Box(
                modifier = Modifier.fillMaxWidth().height(48.dp).offset(0.dp, 56.dp)
                    .border(1.dp, Color(0xFFAAAAAA), RoundedCornerShape(6.dp))
            ) {
                BasicTextField(
                    value = userName,
                    textStyle = TextStyle.Default.copy(
                        color = Color.Black,
                        fontSize = 16.sp,
                        textAlign = TextAlign.Start
                    ),
                    modifier = Modifier.padding(16.dp, 0.dp).height(26.dp).fillMaxWidth()
                        .align(Alignment.CenterStart)
                        .onFocusChanged { state ->
                            if (state.hasFocus && userName == hintUserName) {
                                userName = ""
                            } else if (userName == "") {
                                userName = hintUserName;
                            }
                        },
                    singleLine = true,
                    onValueChange = { userName = it },
//                label = { Text("用户名/手机号") },
                )
            }
            Box(
                modifier = Modifier.fillMaxWidth().height(48.dp).offset(0.dp, 66.dp)
                    .border(1.dp, Color(0xFFAAAAAA), RoundedCornerShape(6.dp))
            ) {
                BasicTextField(
                    keyboardActions = KeyboardActions { },
                    value = password,
                    textStyle = TextStyle.Default.copy(
                        color = Color.Black,
                        fontSize = 16.sp,
                        textAlign = TextAlign.Start
                    ),
                    modifier = Modifier.padding(16.dp, 0.dp).height(26.dp).fillMaxWidth()
                        .align(Alignment.CenterStart)
                        .onFocusChanged { state ->
                            if (state.hasFocus && password == hintPasswd) {
                                password = ""
                            } else if (password == "") {
                                password = hintPasswd;
                            }
                        },
                    singleLine = true,
                    onValueChange = { password = it },
//                label = { Text("密码") },
                )
            }

            Surface(
                shape = RoundedCornerShape(10.dp), color = Color(0xFFD33A31),
                modifier = Modifier.fillMaxWidth().offset(0.dp, 110.dp)
            ) {
                Text(
                    "登录",
                    style = TextStyle(Color.White, 18.sp),
                    textAlign = TextAlign.Center,
                    modifier = Modifier.align(Alignment.CenterHorizontally).padding(8.dp)
                        .clickable {
                            onLoginClicked(userName, password)
                        }
                )
            }
        }
    }
}
