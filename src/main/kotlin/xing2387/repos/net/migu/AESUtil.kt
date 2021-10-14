package xing2387.repos.net.migu

import kotlin.Throws
import kotlin.jvm.JvmOverloads
import javax.crypto.Cipher
import xing2387.repos.net.migu.Base64Utils
import javax.crypto.spec.SecretKeySpec
import javax.crypto.spec.IvParameterSpec
import java.security.NoSuchAlgorithmException
import java.lang.StringBuffer
import java.lang.IllegalArgumentException
import java.io.UnsupportedEncodingException
import java.lang.AssertionError
import xing2387.repos.net.migu.Base64.Coder
import java.lang.RuntimeException
import java.io.IOException
import java.io.ByteArrayOutputStream
import java.lang.Exception
import java.nio.charset.Charset
import java.security.Key
import javax.crypto.KeyGenerator

/*
 * Exception performing whole class analysis ignored.
 */
object AESUtil {
    private const val DEFAULT_CIPHER_ALGORITHM = "AES/ECB/PKCS5Padding"
    const val ENCODING = "UTF-8"
    private const val KEY_ALGORITHM = "AES"
    const val MIN_LENGTH = 1
    const val AES_KEY = "15acb4a88285ed2f"
    private fun charToByte(var0: Char): Byte {
        return "0123456789ABCDEF".indexOf(var0).toByte()
    }

//    @Throws(Exception::class)
//    fun decrypt(var0: String?, var1: String): String {
//        val var2 = var1.toByteArray()
//        return String(decrypt(hexStringToBytes(var0), var2, "AES/ECB/PKCS5Padding"))
//    }

    @JvmOverloads
    @Throws(Exception::class)
    fun decrypt(var0: ByteArray?, var1: Key?, var2: String? = "AES/ECB/PKCS5Padding"): ByteArray {
        val var3 = Cipher.getInstance(var2)
        var3.init(2, var1)
        return var3.doFinal(var0)
    }

    @JvmOverloads
    @Throws(Exception::class)
    fun decrypt(
        var0: ByteArray?,
        var1: ByteArray,
        var2: String? = "AES/ECB/PKCS5Padding"
    ): ByteArray {
        return decrypt(var0, toKey(var1), var2)
    }

    fun decryptSso(var0: String?, var1: String): String? {
        var var0 = var0
        try {
            val var2 = Base64Utils.decode(var0)
            val var5 = SecretKeySpec(var1.toByteArray(), "AES")
            val var6 = Cipher.getInstance("AES/CBC/PKCS5Padding")
            val var3 = IvParameterSpec(ByteArray(var6.blockSize))
            var6.init(2, var5, var3)
            val var7 = var6.doFinal(var2)
            var0 = String(var7, Charset.forName("UTF-8"))
        } catch (var4: Exception) {
            var0 = null
            var4.printStackTrace()
        }
        return var0
    }

//    @Throws(Exception::class)
//    fun encrypt(var0: String?, var1: String): String {
//        return printHexString(
//            encrypt(
//                var1.toByteArray(),
//                var1.toByteArray(),
//                "AES/ECB/PKCS5Padding"
//            )
//        )
//    }

//    @Throws(Exception::class)
//    fun encrypt(var0: ByteArray?, var1: String): String {
//        return printHexString(encrypt(var0, var1.toByteArray(), "AES/ECB/PKCS5Padding"))
//    }

    @JvmOverloads
    @Throws(Exception::class)
    fun encrypt(var0: ByteArray?, var1: Key?, var2: String? = "AES/ECB/PKCS5Padding"): ByteArray {
        val var3 = Cipher.getInstance(var2)
        var3.init(1, var1)
        return var3.doFinal(var0)
    }

    @JvmOverloads
    @Throws(Exception::class)
    fun encrypt(
        var0: ByteArray?,
        var1: ByteArray,
        var2: String? = "AES/ECB/PKCS5Padding"
    ): ByteArray {
        return encrypt(var0, toKey(var1), var2)
    }

    @JvmStatic
    fun encryptSso(string: String, aesKey: String): String? {
        return try {
            val var2 = SecretKeySpec(aesKey.toByteArray(), "AES")
            val cipher = Cipher.getInstance("AES/CBC/PKCS5Padding")
            val var3 = IvParameterSpec(ByteArray(cipher.blockSize))
            cipher.init(1, var2, var3)
            Base64Utils.encode(cipher.doFinal(string.toByteArray(charset("UTF-8"))))
        } catch (exception: Exception) {
            exception.printStackTrace()
            null
        }
    }

//    fun hexStringToBytes(var0: String?): ByteArray? {
//        var var0 = var0
//        var var6: ByteArray?
//        if (var0 == null || "" == var0.trim { it <= ' ' }) {
//            var6 = null
//        } else {
//            var0 = var0.toUpperCase()
//            val var1 = var0.length / 2
//            val var2 = var0.toCharArray()
//            val var3 = ByteArray(var1)
//            var var4 = 0
//            while (true) {
//                var6 = var3
//                if (var4 >= var1) {
//                    break
//                }
//                val var5 = var4 * 2
//                var3[var4] = (charToByte(var2[var5]) shl 4 or charToByte(
//                    var2[var5 + 1]
//                ))
//                ++var4
//            }
//        }
//        return var6
//    }

    fun initSecretKey(): ByteArray {
        val var0: ByteArray
        val var2: KeyGenerator
        try {
            var2 = KeyGenerator.getInstance("AES")
        } catch (var1: NoSuchAlgorithmException) {
            var0 = ByteArray(0)
            return var0
        }
        var2.init(128)
        var0 = var2.generateKey().encoded
        return var0
    }

//    fun printHexString(arrby: ByteArray): String {
//        val stringBuffer = StringBuffer()
//        var n = 0
//        while (n < arrby.size) {
//            var string: String
//            string = Integer.toHexString(arrby[n] and 255)
//            var string2 = string
//            if (string.length == 1) {
//                string2 = "0$string"
//            }
//            stringBuffer.append(string2)
//            ++n
//        }
//        return stringBuffer.toString()
//    }

    private fun toKey(arrby: ByteArray): Key {
        return SecretKeySpec(arrby, "AES")
    }
}