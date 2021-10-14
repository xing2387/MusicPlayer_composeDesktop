package xing2387.repos.net.migu.req

import okhttp3.OkHttpClient
import okhttp3.Request

object QueryUserInfo {
    var baseUrl =
        "https://app.u.nf.migu.cn/MIGUM3.0/v1.0/user/queryUserInfo.do?userId=%s" //91042961852

    fun doReq(userId: String?, aversionid: String?): String {
        val url = String.format(baseUrl, userId)
        val client = OkHttpClient()
        val request: Request = Request.Builder()
            .header("aversionid", aversionid ?: "")
            .url(url)
            .build()
        try {
            client.newCall(request).execute().use { response -> return response.body!!.string() }
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return "QueryUserInfo error"
    }
}