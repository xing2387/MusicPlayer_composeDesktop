package xing2387.repos.net.migu.req

import okhttp3.OkHttpClient
import okhttp3.Request

object QueryMusicListSongs {
    var baseUrl =
        "https://app.c.nf.migu.cn/MIGUM2.0/v2.0/content/listen-url?albumId=63205&lowerQualityContentId=600902000003281405&netType=01&resourceType=2&songId=1000056007&toneFlag=HQ"

    fun doReq(listId: String?, aversionid: String?, token: String?): String {
        val url = String.format(baseUrl, listId)
        val client = OkHttpClient()
        val request: Request = Request.Builder()
            .header("aversionid", aversionid ?: "")
            .header("token", token ?: "")
            .url(url)
            .build()
        try {
            client.newCall(request).execute().use { response -> return response.body!!.string() }
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return "QueryUserInfo error"
    }

    annotation class Quality { //    String Low = ""
    }
}