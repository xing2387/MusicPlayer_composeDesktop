package xing2387.repos.net.migu

import org.bouncycastle.jce.provider.BouncyCastleProvider
import xing2387.repos.net.migu.req.CallAuthRequest
import java.io.FileInputStream
import java.io.InputStream
import java.security.KeyStore
import java.security.SecureRandom
import java.security.Security
import javax.net.ssl.KeyManagerFactory
import javax.net.ssl.SSLContext
import javax.net.ssl.SSLSocketFactory
import javax.net.ssl.TrustManagerFactory

///MIGUM2.0/v1.0/user/getHomePage.do 不需要用户id来请求，歌单列表里有用户id~，可以用来获取用户Id
object Main {
    @JvmField
    var baseDir = "./src"

//    @JvmStatic
//    fun main(args: Array<String>) {
//        loginByPassword()
//        //        test();
//    }

    fun loginByPassword() {
        try {
            val socketFactory = sslSocketFactory
            HTTPSClient.call(socketFactory, CallAuthRequest("your phone num", null, "password"))
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    fun test() {
        val encpasswd = RSAUtil.byPublicKeyEncrypt("password")
        println("===================password==============")
        println(encpasswd)
        println("===================password==============")

//        String aa = EncUtil.toMd5("username");
//        System.out.println("===================toMd5==============");
//        System.out.println(aa);
//        System.out.println("===================toMd5==============");
//
//        String pssid = "7C1545A2C3DBFC2CFD864EC795D9287334555AC49BF362847CA971F942C225F12C666427CE8DF1C4334CDA8A9DA429CDE4FC78091E5C4256F9275FCC543F35944C76867CFEDE0B4B9C7669BAB03F4240694F1005DCC5CEB9DEE180F78D6500AAB2FC22FA921E73AE80228DB49A87FA4C4BF114DDDA6950AF7A50A9409D2D19AD";
//        String pssidText = RSAUtil.byPrivateKeyDecrypt(pssid);
//        System.out.println("===================pssid==============");
//        System.out.println(pssidText);
//        System.out.println("===================pssid==============");
//
//        File file = new File("./app/bin/migutoken");
//        System.out.println(new Gson().toJson(file.listFiles()));
//        CallAuthRequest.testCalcToken();
    }

    fun loginBySms() {
        try {
            val socketFactory = sslSocketFactory
            //            HTTPSClient.call(socketFactory, new CallValidCodeManage("your phone num"));
            HTTPSClient.call(socketFactory, CallAuthRequest("your phone num", "861221", null))
        } catch (e: Throwable) {
            e.printStackTrace()
        }
    }

    //        SSLContext var2 = SSLContext.getInstance("SSL");
    val sslSocketFactory: SSLSocketFactory
        get() {
//        SSLContext var2 = SSLContext.getInstance("SSL");
            Security.addProvider(BouncyCastleProvider())
            val p12: InputStream = FileInputStream(baseDir + "/cert/client.p12")
            val bks: InputStream = FileInputStream(baseDir + "/cert/migu.bks")
            val var6 = KeyStore.getInstance("BKS")
            var6.load(bks, "android".toCharArray())
            val trustManager =
                TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm())
            trustManager.init(var6)
            val keyStore = KeyStore.getInstance("PKCS12")
            keyStore.load(p12, "Cx186AukGY".toCharArray())
            val keyManager = KeyManagerFactory.getInstance(KeyManagerFactory.getDefaultAlgorithm())
            keyManager.init(keyStore, "Cx186AukGY".toCharArray())
            val var27 = SSLContext.getInstance("TLS")
            var27!!.init(keyManager.keyManagers, trustManager.trustManagers, null as SecureRandom?)
            return var27.socketFactory
        }
}