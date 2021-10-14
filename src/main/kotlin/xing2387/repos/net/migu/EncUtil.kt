package xing2387.repos.net.migu

import okhttp3.internal.and
import xing2387.repos.net.migu.Base64Utils.decodeToString
import java.util.UUID
import java.lang.StringBuilder
import java.io.UnsupportedEncodingException
import java.lang.Exception
import java.security.MessageDigest
import java.security.NoSuchAlgorithmException

//import android.text.TextUtils;
//import com.cmcc.util.Base64Utils;
//import com.cmcc.util.LogUtil;
object EncUtil {
    private val HEXADECIMAL =
        charArrayOf('0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f')
    const val PWD_LEVEL_HIGH = 2
    const val PWD_LEVEL_MIDDLE = 1
    const val PWD_LEVEL_WEAK = 0
    fun checkPwdStrengh(var0: String): Int {
        val var1: Byte
        var1 = if (!TextUtils.isEmpty(var0) && var0.length >= 6) {
            if (!var0.matches(Regex("^\\d+$")) && !var0.matches(Regex("^[a-zA-Z]+$")) &&
                !var0.matches(Regex("^[_\\W]+$"))
            ) {
                if (!var0.matches(Regex("^[0-9a-zA-Z]+$")) && !var0.matches(Regex("^[_0-9\\W]+$")) && !var0.matches(
                        Regex("^[_A-Za-z\\W]+$")
                    )
                ) {
                    2
                } else {
                    1
                }
            } else {
                0
            }
        } else {
            -1
        }
        return var1.toInt()
    }

    fun convertCientKey(var0: String?): String? {
        val var1 = Integer.toString(222)
        val var2 = Integer.toString(186)
        var var4: String?
        if (var0 != null) {
            val var3 = decodeToString(var0)
            var4 = var0
            if (var3 != null) {
                var4 = var0
                if (var3.contains(var1)) {
                    var4 = var3.replace(var1, var2)
                }
            }
        } else {
            var4 = null
        }
        return var4
    }

    private fun encode(var0: ByteArray): String {
        val var1 = var0.size
        val var2 = CharArray(var1 * 2)
        for (var3 in 0 until var1) {
            val var4 = var0[var3]
            val var5 = var0[var3]
            var2[var3 * 2] = HEXADECIMAL[var5 and 240 shr 4]
            var2[var3 * 2 + 1] = HEXADECIMAL[var4 and 15]
        }
        return String(var2)
    }

    fun getUUIDAndTimeToMd5(var0: String?): String? {
        val var1 = ""
        val var10000: Exception
        var var3: String
        while (true) {
            val var10001: Boolean
            val var2: UUID?
            try {
                var2 = UUID.randomUUID()
            } catch (var5: Exception) {
                var10000 = var5
                var10001 = false
                break
            }
            var3 = var1
            if (var2 == null) {
                return toMd5(var3)
            }
            try {
                val var7 = StringBuilder()
                var3 = var7.append(var2.toString().replace("-", "")).append(var0).toString()
                return toMd5(var3)
            } catch (var4: Exception) {
                var10000 = var4
                var10001 = false
            }
            break
        }
        var10000.printStackTrace()
        var3 = var1
        return toMd5(var3)
    }


    fun isCN(var0: String): Boolean {
        var var1 = false
        if (!TextUtils.isEmpty(var0)) {
            val var2: Int
            val var3: Int
            try {
                var2 = var0.toByteArray(charset("UTF-8")).size
                var3 = var0.length
            } catch (var4: UnsupportedEncodingException) {
                return var1
            }
            if (var2 != var3) {
                var1 = true
            }
        }
        return var1
    }

    fun isEmpty(var0: String?): Boolean {
        val var1: Boolean
        var1 = if (var0 != null && var0.trim { it <= ' ' }.length != 0) {
            false
        } else {
            true
        }
        return var1
    }

    fun isRightEmail(var0: String): Boolean {
        val var1: Boolean
        var1 = if (isEmpty(var0)) {
            false
        } else {
            var0.matches(Regex("^([a-zA-Z0-9\\_\\-\\.])+@([a-zA-Z0-9\\_\\-\\.])+(\\.([a-zA-Z0-9])+)+$"))
        }
        return var1
    }

    fun isRightPassId(var0: String): Boolean {
        val var1: Boolean
        var1 = if (isEmpty(var0)) {
            false
        } else {
            var0.matches(Regex("^[1-9]\\d{8,14}$"))
        }
        return var1
    }

    fun isRightPhone(var0: String): Boolean {
        val var1: Boolean
        var1 = if (TextUtils.isEmpty(var0)) {
            false
        } else {
            var0.matches(Regex("1\\d{10}"))
        }
        return var1
    }

    fun isRightPhoneNum(var0: String): Boolean {
        val var1: Boolean
        var1 = if (TextUtils.isEmpty(var0)) {
            false
        } else {
            var0.matches(Regex("^[0-9]*$"))
        }
        return var1
    }

    fun isRightPwd(var0: String): Boolean {
        val var1 = false
        var var2: Boolean
        if (isEmpty(var0)) {
            var2 = var1
        } else {
            val var3 = var0.length
            var2 = var1
            if (var3 >= 6) {
                var2 = var1
                if (var3 <= 16) {
                    var2 = true
                }
            }
        }
        return var2
    }

    fun isRightTempSmsPwd(var0: String): Boolean {
        var var1 = false
        if (!isEmpty(var0) && var0.matches(Regex("\\d{6}"))) {
            var1 = true
        }
        return var1
    }

    fun toMd5(string: String): String {
        return try {
            val messageDigest = MessageDigest.getInstance("MD5")
            val string2 = ""
            if (TextUtils.isEmpty(string)) string2 else encode(
                messageDigest.digest(
                    string.toByteArray(
                        charset("UTF-8")
                    )
                ) as ByteArray
            )
        } catch (throwable: Throwable) {
            throwable.printStackTrace()
            ""
        }
    }

    fun toMd5(var0: ByteArray?): String {
        val var2: String
        var2 = try {
            encode(MessageDigest.getInstance("MD5").digest(var0))
        } catch (var1: NoSuchAlgorithmException) {
            ""
        }
        return var2
    }
}