package xing2387.repos.bean

data class UserInfoEntity(
    val userId: String,
    val userName: String = "用户名",
    val avatarUrl: String? = null,
    val token: String? = null,
    val ps: String? = null
)
