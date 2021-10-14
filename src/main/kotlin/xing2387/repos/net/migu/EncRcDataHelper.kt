package xing2387.repos.net.migu

import xing2387.repos.net.migu.AESUtil.encryptSso
import xing2387.repos.net.migu.EncRcDataHelper
import com.google.gson.JsonObject

object EncRcDataHelper {
    fun encRcData(): String? {
        return encryptSso(deviceInfo, AESUtil.AES_KEY)
    }

    /*
     * {
     *     "os": "AndroidV-25 7.1.2 NJH47F",
     *     "dev_model": "OnePlus A0001",
     *     "imei": "865800028532932",
     *     "sim_num": "nopsim",
     *     "network_type": "WIFI",
     *     "appName": "cmccwm.mobilemusic",
     *     "network_operator": {
     *         "net_country_iso": "",
     *         "net_oper": ""
     *     }
     * }
     */
    private val deviceInfo: String
        private get() {
            val jsonObject = JsonObject()
            jsonObject.addProperty("os", "AndroidV-25 7.1.2 NJH47F")
            jsonObject.addProperty("dev_model", "OnePlus A0001")
            jsonObject.addProperty("imei", "865800028532932")
            jsonObject.addProperty("sim_num", "nopsim")
            jsonObject.addProperty("network_type", "WIFI")
            jsonObject.addProperty("appName", "cmccwm.mobilemusic")
            val network_operator = JsonObject()
            network_operator.addProperty("net_country_iso", "")
            network_operator.addProperty("net_oper", "")
            jsonObject.add("network_operator", network_operator)
            return jsonObject.toString()
        }
}