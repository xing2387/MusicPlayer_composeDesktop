package xing2387.repos.net.migu

import okhttp3.Request

class SignManager {
    private fun getSignUrl(url: String): String {
        val var1: Request = Request.Builder().url(url).build()
        val var2 = StringBuilder()
        if (TextUtils.isEmpty(var1.url.encodedQuery)) {
            var2.append(var1.url.encodedPath)
        } else {
            var2.append(var1.url.encodedPath).append("?").append(var1.url.encodedQuery)
        }
        return var2.toString()
    }
}