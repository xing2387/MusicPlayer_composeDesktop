package xing2387.repos.net.migu.req

import xing2387.repos.net.migu.HTTPSClient.ReqInfo
import kotlin.Throws
import java.io.IOException
import java.lang.StringBuilder
import xing2387.repos.net.migu.SignatureDataHelper
import xing2387.repos.net.migu.EncRcDataHelper
import java.util.UUID
import xing2387.repos.net.migu.EncUtil
import xing2387.repos.net.migu.RSAUtil
import java.io.Writer

class CallValidCodeManage(var phoneNumber: String) : ReqInfo() {
    @Throws(IOException::class)
    override fun writeParam(out: Writer) {
        val sb = StringBuilder()
        val userManage = userManage()
        out.write("POST /client/validCodeManage HTTP/1.1\r\n")
        var str = """
              signature: ${SignatureDataHelper.getSignature(userManage)}
              
              """.trimIndent()
        sb.append(str)
        out.write(str)
        str = "UserManage: $userManage\r\n"
        sb.append(str)
        out.write(str)
        str = """
              encRcData: ${EncRcDataHelper.encRcData()}
              
              """.trimIndent()
        sb.append(str)
        out.write(str)
        out.write("Content-Type: application/x-www-form-urlencoded\r\n")
        out.write("User-Agent: Dalvik/2.1.0 (Linux; U; Android 6.0.1; MuMu Build/V417IR)\r\n")
        out.write("Host: passport.migu.cn:8443\r\n")
        out.write("Connection: Keep-Alive\r\n")
        out.write("Accept-Encoding: gzip\r\n")
        out.write("Content-Length: 0\r\n")
        println("=================== request start ============")
        println(sb)
        println("=================== request end ============")
    }

    fun userManage(): String {
//        String format = "VC Phone_ID=\"a6c64893f7e3fdfb\",msgtype=\"3\",apptype=\"3\",rcflag=\"1\",sdkversion=\"UnionSDK-20AndroidV5.1.0" +
//                "\",clientversion=\"1.0\",appid=\"22002401\",validcodeflag=\"1\",vcSecVal=\"1\",msgid=\"409bc7a6610d44c2bc7cef58b9ab0ba2\",msisdn=\"手机号\",enccnonce=\"\""
        val msgId = UUID.randomUUID().toString().replace("-", "")
        val phoneId = "a6c64893f7e3fdfb"
        val msgtype = "3"
        val apptype = "3"
        val rcflag = "1"
        val sdkversion = "UnionSDK-20AndroidV5.1.0"
        val clientversion = "1.0"
        val appid = "22002401"
        val validcodeflag = "1"
        val vcSecVal = "1"

//        String phone = "手机号";
        val cnonceKek =
            EncUtil.getUUIDAndTimeToMd5(java.lang.Long.toString(System.currentTimeMillis()))
        val enccnonce = RSAUtil.byPublicKeyEncrypt(cnonceKek)
        return StringBuilder()
            .append("VC Phone_ID=\"").append(phoneId)
            .append("\",msgtype=\"").append(msgtype)
            .append("\",apptype=\"").append(apptype)
            .append(",rcflag=").append(rcflag)
            .append("\",sdkversion=\"").append(sdkversion)
            .append("\",clientversion=\"").append(clientversion)
            .append("\",appid=\"").append(appid)
            .append("\",validcodeflag=\"").append(validcodeflag)
            .append("\",vcSecVal=\"").append(vcSecVal)
            .append("\",msgid=\"").append(msgId)
            .append("\",msisdn=\"").append(phoneNumber)
            .append("\",enccnonce=\"").append(enccnonce)
            .append("\"")
            .toString()
    }
}