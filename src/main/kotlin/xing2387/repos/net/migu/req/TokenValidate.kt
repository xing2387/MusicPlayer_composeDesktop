package xing2387.repos.net.migu.req

import okhttp3.OkHttpClient
import okhttp3.Request

object TokenValidate {
    var baseUrl =
        "https://app.u.nf.migu.cn/MIGUM3.0/user/token-validate/v1.0?loginType=2&tokenId=%s"

    fun doReq(token: String?, aversionid: String?): String {
        val url = String.format(baseUrl, token)
        val client = OkHttpClient()
        val request: Request = Request.Builder()
            .header("token", "")
            .header("timestamp", System.currentTimeMillis().toString() + "")
            .header("aversionid", aversionid ?: "")
            .url(url)
            .build()
        try {
            client.newCall(request).execute().use { response -> return response.body!!.string() }
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return "TokenValidate error"
    }
}