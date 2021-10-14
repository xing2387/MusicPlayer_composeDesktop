package xing2387.repos.net.migu.req

import okhttp3.OkHttpClient
import okhttp3.Request

object ListenUrl {
    var baseUrl =
        "https://app.u.nf.migu.cn/MIGUM2.0/v1.0/user/queryMusicListSongs.do?musicListId=%s&pageNo=1&pageSize=50" //179934290

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
}