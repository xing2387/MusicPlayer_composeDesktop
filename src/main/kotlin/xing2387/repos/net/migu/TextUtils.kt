package xing2387.repos.net.migu

object TextUtils {
    fun isEmpty(str: String?): Boolean {
        return str == null || "" == str.trim { it <= ' ' }
    }
}