package xing2387.repos.net.migu

import okhttp3.internal.and
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
import java.io.OutputStream

/*
 * Exception performing whole class analysis ignored.
 */
object Base64Utils {
    private const val BASE64_PADDING = '='
    private const val BASE64_WRAP = 14
    private val LEGAL_CHARS =
        "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789+/".toCharArray()

    //    public static Bitmap base64ToBitmap(String arrby) {
    //        arrby = Base64.decode((String)arrby, (int)0);
    //        return BitmapFactory.decodeByteArray((byte[])arrby, (int)0, (int)arrby.length);
    //    }
    private fun decode(c: Char): Int {
        if (c >= 'A' && c <= 'Z') {
            return c.toInt() - 65
        }
        if (c >= 'a' && c <= 'z') {
            return c.toInt() - 97 + 26
        }
        if (c >= '0' && c <= '9') {
            return c.toInt() - 48 + 26 + 26
        }
        when (c) {
            '+' -> {
                return 62
            }
            '/' -> {
                return 63
            }
            '=' -> {
            }
            else -> {
                throw RuntimeException("unexpected code: $c")
            }
        }
        return 0
    }

    @Throws(IOException::class)
    private fun decode(string: String?, outputStream: OutputStream) {
        var n = 0
        val n2 = string!!.length
        do {
            if (n < n2 && string[n] <= ' ') {
                ++n
                continue
            }
            if (n == n2) {
                return
            }
            val n3 = (decode(string[n]) shl 18) + (decode(
                string[n + 1]
            ) shl 12) + (decode(string[n + 2]) shl 6) + decode(
                string[n + 3]
            )
            outputStream.write(n3 shr 16 and 255)
            if (string[n + 2] == '=') return
            outputStream.write(n3 shr 8 and 255)
            if (string[n + 3] == '=') return
            outputStream.write(n3 and 255)
            n += 4
        } while (true)
    }

    @JvmStatic
    fun decode(var0: String?): ByteArray {
        val var1 = ByteArrayOutputStream()
        try {
            decode(var0, var1)
        } catch (var3: IOException) {
            throw RuntimeException()
        }
        val var4 = var1.toByteArray()
        try {
            var1.close()
        } catch (var2: IOException) {
            System.err.println("Error while decoding BASE64: $var2")
        }
        return var4
    }

    @JvmStatic
    fun decodeToString(var0: String?): String? {
        var var0 = var0
        var0 = if (var0 != null) {
            String(Base64.decode(var0.toByteArray(), 2))
        } else {
            null
        }
        return var0
    }

    fun encode(arrby: ByteArray): String {
        val n = arrby.size
        val stringBuffer = StringBuffer(arrby.size * 3 / 2)
        var n2 = 0
        var n3 = 0
        while (n2 <= n - 3) {
            var n4: Int =
                arrby[n2] and 255 shl 16 or (arrby[n2 + 1] and 255 shl 8) or (arrby[n2 + 2] and 255)
            stringBuffer.append(LEGAL_CHARS[n4 shr 18 and 63])
            stringBuffer.append(LEGAL_CHARS[n4 shr 12 and 63])
            stringBuffer.append(LEGAL_CHARS[n4 shr 6 and 63])
            stringBuffer.append(LEGAL_CHARS[n4 and 63])
            n4 = n2 + 3
            n2 = n3 + 1
            if (n3 >= 14) {
                n2 = 0
                stringBuffer.append(" ")
            }
            n3 = n2
            n2 = n4
        }
        if (n2 == 0 + n - 2) {
            n2 = arrby[n2] and 255 shl 16 or (arrby[n2 + 1] and 255) shl 8
            stringBuffer.append(LEGAL_CHARS[n2 shr 18 and 63])
            stringBuffer.append(LEGAL_CHARS[n2 shr 12 and 63])
            stringBuffer.append(LEGAL_CHARS[n2 shr 6 and 63])
            stringBuffer.append("=")
            return stringBuffer.toString()
        }
        if (n2 != 0 + n - 1) return stringBuffer.toString()
        n2 = arrby[n2] and 255 shl 16
        stringBuffer.append(LEGAL_CHARS[n2 shr 18 and 63])
        stringBuffer.append(LEGAL_CHARS[n2 shr 12 and 63])
        stringBuffer.append("==")
        return stringBuffer.toString()
    }

    fun encodeToString(string: String?): String? {
        return if (string == null) null else Base64.encodeToString(
            string.toByteArray(),
            2
        )
    }
}