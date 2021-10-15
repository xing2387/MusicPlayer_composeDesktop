import androidx.compose.desktop.DesktopTheme
import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.window.WindowDraggableArea
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import xing2387.repos.bean.MusicListInfo
import xing2387.repos.db.CommonDB
import xing2387.ui.LoginDialog
import xing2387.ui.RemoteImage

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

    var isShowLogin by remember { mutableStateOf(true) }

    var loginedId: String? by remember { mutableStateOf(null) }


    Window(
        onCloseRequest = ::exitApplication,
        undecorated = true,
        title = "音乐播放器",
        resizable = true,
        state = windowState
    ) {

        val windowScope = rememberCoroutineScope()

        MaterialTheme {
            DesktopTheme {
                Column(modifier = Modifier.background(Color.White)) {
                    var searchText by rememberSaveable { mutableStateOf("") }
                    WindowDraggableArea {
                        TitleBar(
                            searchText,
                            onSearchTextChange = { searchText = it },
                            windowState = windowState
                        )
                    }

                    Row(modifier = Modifier.background(Color.White).fillMaxHeight()) {
                        Column(
                            modifier = Modifier.background(color = Color(0xFFEDEDED))
                                .fillMaxHeight()
                                .width(200.dp)
                        ) {
                            var userName: String? by remember { mutableStateOf(null) }
                            var avatar: String? by remember { mutableStateOf(null) }
                            UserInfo(avatar, userName) { isShowLogin = true }

                            LaunchedEffect(loginedId) {
                                withContext(Dispatchers.IO) {
                                    Thread.sleep(5000)
                                    System.out.println("query ${Thread.currentThread().name}")
                                    if (loginedId == null) {
                                        userName = null
                                        avatar = null
                                        System.out.println("restore user null")
                                    } else {
                                        val user = CommonDB.getUserById("111").executeAsOneOrNull()
                                        userName = user?.userName
                                        avatar = user?.avatarUrl
                                        System.out.println("restore user ${user}")
                                    }
                                }
                            }
                        }
                        Column(modifier = Modifier.fillMaxSize()) { }
                    }
                }


                System.out.println("main ${Thread.currentThread().name}")
                if (isShowLogin) {
                    LoginDialog({ isShowLogin = false }, { userName, passwd, isRememberPasswd ->
                        if (isRememberPasswd) {
                            windowScope.launch(Dispatchers.IO) {
                                Thread.sleep(5000)
                                System.out.println("insert ${Thread.currentThread().name}")
                                CommonDB.insert(
                                    "111",
                                    userName,
                                    avatar = "https://www.baidu.com/img/flexible/logo/pc/result.png",
                                    ps = passwd
                                )
                                loginedId = "2222"
                            }
                        }
//                        HTTPSClient.call(
//                            Main.sslSocketFactory,
//                            CallAuthRequest(userName, null, passwd)
//                        )
//                    System.out.println(Gson().toJson(File(".").listFiles()))
                    })
                }
            }
        }
    }
}

@Composable
fun MusicLists(musicLists: List<MusicListInfo>) {
    LazyColumn {
        for (info in musicLists) {

        }
    }
}

@Composable
fun UserInfo(url: String?, nick: String?, onClick: () -> Unit) {
    Row(
        modifier = Modifier.clickable { onClick() }
    ) {
        Surface(
            modifier = Modifier.size(60.dp).padding(10.dp),
            color = Color(0xFFE0E0E0),
            shape = CircleShape,
        ) {
            if (url.isNullOrEmpty()) {
                Image(
                    painter = painterResource("images/avatar_default.png"),
                    contentDescription = "default avatar",
                )
            } else {
                RemoteImage(url = url, contentDescription = "avatar")
            }
        }
        Text(
            text = nick ?: "未登录",
            color = Color(0xFF333333),
            fontSize = 15.sp,
            modifier = Modifier.align(Alignment.CenterVertically).fillMaxWidth()
        )
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
            contentDescription = "close application",
            Modifier.size(20.dp, 20.dp)
                .clickable { exitApplication() }
        )
        Image(
            painter = painterResource("images/ic_maximum_24.xml"),
            contentDescription = "maximum window",
            Modifier.size(20.dp, 20.dp).offset(4.dp, 0.dp)
                .clickable {
                    windowState.placement =
                        if (windowState.placement == WindowPlacement.Maximized) WindowPlacement.Floating
                        else WindowPlacement.Maximized
                }
        )
        Image(
            painter = painterResource("images/ic_minimize_24.xml"),
            contentDescription = "minimize window",
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
}