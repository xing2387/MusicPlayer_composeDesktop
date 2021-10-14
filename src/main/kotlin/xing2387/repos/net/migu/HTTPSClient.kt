package xing2387.repos.net.migu

import kotlin.Throws
import javax.net.ssl.SSLSocketFactory
import xing2387.repos.net.migu.HTTPSClient.ReqInfo
import javax.net.ssl.SSLSocket
import com.google.gson.JsonObject
import xing2387.repos.net.migu.HTTPSClient
import java.io.*
import java.lang.Exception

object HTTPSClient {
    @Throws(IOException::class)
    fun call_funcManager(out: Writer) {
        out.write("POST https://passport.migu.cn:8443/client/funcManager HTTP/1.1\r\n")
        out.write("signature: 5860464E1A8375F7D52AA889739798EF06A9F75F93956FE3879EC285F7AF263809C8AA06580E7D99F83122821156089A28BF22A4394F34795626646F9660B0BCAF639FD3218FC4222B8DD00C2AE2D60834B8A0E8668F44073CBE633329717234B4E10232BBB74190206CB202D039F6ABEB9C442720C9D01157BEF3FC53ED81E9\r\n")
        out.write("funcManager: QR_CC Phone_ID=\"a6c64893f7e3fdfb\",appid=\"22002401\",apptype=\"3\",msgid=\"5f095bff81d04eebbdd2971aadc97923\",sdkversion=\"UnionSDK-20AndroidV5.1.0\",clientversion=\"1.0\",enccnonce=\"38500CDA7B996069C123DFCED39C4B766495D45301DBCE5AC7EABDAED39DFD318E4C4724D933AEEAAA4E53E40F60A9F58E857C398ED7C9F2BDFA92B77B1D62A4CBA3755801EE2A2C9A08E0BFAE7063B726C06852BA9E4861F5A0D254C65631DFE47F75E1C35EB58A3FE77ACDA943EC1CBEBA717510FD0FCDEBDFEAC4486B5049\"\r\n")
        out.write("encRcData: rNnva0+cf0e/EOYVEBi5X5vgDRlYdLZQJs3X1+OPOxeZuL+H6v349rMm/s2j /nNAhJX815I1Px0jmTUSzniHYjFiou5g0fKtIhfQwgAexy9bu07Iba3OUsGS Zl+mDsRI8iokdimNxlRnfS+FHO3BGY4VDQGnIeh6L8u5K+4oWfvM82insdBi 1zoGWaug1HhMA/t1EIRjYC164v9mRYIky25FSNql9SAUat7iEedidbSNq5yJ iVfBMbML8WH6aWlv1y+th1sG7DfcmDapbfG759m4kz/8b2HLzsEeoNlobMM=\r\n")
        out.write("Host: passport.migu.cn:8443\r\n")
        out.write("User-Agent: Dalvik/2.1.0 (Linux; U; Android 6.0.1; MuMu Build/V417IR)\r\n")
    }

    @Throws(IOException::class)
    fun call_ckRequest(out: Writer) {
        out.write("POST /client/ckRequest HTTP/1.1\r\n")
        out.write("signature: BA8D6767B7E09F81B15F59DDC70F1D75E28CF56154199DF14BECAAC79FC23FDE1CC35A9BB7B711E5C50D6CCD965479DA6E59825940A5B0DDAFC847AA268026093DF1A56DBAE79D56CD8A5AD182DE9262D3C9714CEFDB964AFBAA1421DC0C87051D144F3089BDB8E2484BCE7996E61CA0E6CF0543E65EDFC8C9EB0474EB4435BD\r\n")
        out.write("query: CK Phone_ID=\"a6c64893f7e3fdfb\",appid=\"22002401\",apptype=\"3\",msgid=\"b943ee15a2514c648d26ccafa3ea44c0\",sdkversion=\"UnionSDK-20AndroidV5.1.0\",clientversion=\"1.0\",package=\"cmccwm.mobilemusic\"\r\n")
        out.write("encRcData: rNnva0+cf0e/EOYVEBi5X5vgDRlYdLZQJs3X1+OPOxeZuL+H6v349rMm/s2j /nNAhJX815I1Px0jmTUSzniHYjFiou5g0fKtIhfQwgAexy9bu07Iba3OUsGS Zl+mDsRI8iokdimNxlRnfS+FHO3BGY4VDQGnIeh6L8u5K+4oWfvM82insdBi 1zoGWaug1HhMA/t1EIRjYC164v9mRYIky25FSNql9SAUat7iEedidbSNq5yJ iVfBMbML8WH6aWlv1y+th1sG7DfcmDapbfG759m4kz/8b2HLzsEeoNlobMM=\r\n")
        out.write("User-Agent: Dalvik/2.1.0 (Linux; U; Android 6.0.1; MuMu Build/V417IR)\r\n")
        out.write("Host: passport.migu.cn:8443\r\n")
    }

    fun call(factory: SSLSocketFactory, reqInfo: ReqInfo) {
        var socket: SSLSocket? = null
        try {
            socket = factory.createSocket(reqInfo.host(), reqInfo.port()) as SSLSocket

            //启用密码组
            val supportedCipherSuites = socket.supportedCipherSuites
            socket!!.enabledCipherSuites = supportedCipherSuites
            val out: Writer = OutputStreamWriter(socket.outputStream, "UTF-8")

            //https在get中需要完全的URL
            reqInfo.writeParam(out)
            out.write("\r\n")
            out.flush()

            //读取相应
            val reader = BufferedReader(InputStreamReader(socket.inputStream))
            var contentLength = Int.MAX_VALUE
            val respJson = JsonObject()
            println("=================== response start ============")
            //读取首部
            var s: String
            while (reader.readLine().also { s = it } != "") {
                if (!TextUtils.isEmpty(s)) {
                    val kv = s.split(": ").toTypedArray()
                    if (kv != null && kv.size >= 2) {
                        respJson.addProperty(kv[0], kv[1])
                    }
                }
                println(s)
                contentLength = readContentLength(s, contentLength)
            }
            println()
            reqInfo.processResp(respJson)
            if (contentLength > 0) {
                var c: Int
                var i = 0
                while (reader.read().also { c = it } != -1 && i++ < contentLength) {
                    System.out.write(c)
                }
            }
            println("=================== response end ============")
        } catch (e: Exception) {
            e.printStackTrace()
        } finally {
            try {
                socket?.close()
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    fun readContentLength(s: String?, defaultValue: Int): Int {
        return if (s != null && s.trim { it <= ' ' }.startsWith("Content-Length: ")) {
            s.trim { it <= ' ' }.replace("Content-Length: ", "").toInt()
        } else defaultValue
    }

    abstract class ReqInfo {
        fun port(): Int {
            return 8443
        }

        fun host(): String {
            return "passport.migu.cn"
        }

        @Throws(IOException::class)
        abstract fun writeParam(out: Writer)
        open fun processResp(resp: JsonObject) {}
    }
}