package xing2387.repos.net.migu

import okhttp3.internal.and
import java.lang.StringBuilder
import java.util.HashMap
import xing2387.repos.net.migu.TextUtils
import kotlin.experimental.or

object StringUtils {
    fun bytesToString(var0: ByteArray): String {
        val var1 = StringBuilder()
        val var2 = var0.size
        for (var3 in 0 until var2) {
            var1.append(String.format("%02x", var0[var3] and 255))
        }
        return var1.toString().toUpperCase()
    }


    //    public static byte[] hex2Bytes(String var0) {
//        byte[] var1 = var0.getBytes();
//        byte[] var3 = new byte[var1.length / 2];
//
//        for (int var2 = 0; var2 < var3.length; ++var2) {
//            var3[var2] = (byte) ((byte) ((byte) (Byte.decode("0x" + new String(new byte[]{var1[var2 * 2]})) << 4) | Byte.decode("0x" + new String(new byte[]{var1[var2 * 2 + 1]}))));
//        }
//
//        return var3;
//    }
    fun hex2Bytes(var0: String?): ByteArray {
        val var1 = var0!!.toByteArray()
        val var3 = ByteArray(var1.size / 2)
        for (var2 in var3.indices) {
            var3[var2] =
//            class java.lang.Integer cannot be cast to class java.lang.Byte (java.lang.Integer and java.lang.Byte are in module java.base of loader 'bootstrap')
                ((java.lang.Byte.decode("0x" + String(byteArrayOf(var1[var2 * 2])))
                    .toInt() shl 4).toByte() or java.lang.Byte.decode(
                    "0x" + String(
                        byteArrayOf(
                            var1[var2 * 2 + 1]
                        )
                    )
                )) as Byte
        }
        return var3
    }

    fun isEmpty(var0: String?): Boolean {
        val var1: Boolean
        var1 = if (var0 != null && var0.length > 0) {
            false
        } else {
            true
        }
        return var1
    }

    fun parseKeyValue(var0: String, var1: String): Map<*, *>? {
        var var0 = var0
        var var1 = var1
        var var8: HashMap<*, *>?
        if (!TextUtils.isEmpty(var0) && !TextUtils.isEmpty(var1)) {
            val var2: HashMap<String, String> = HashMap<String, String>()
            val var3 = var0.split(var1).toTypedArray()
            val var4 = var3.size
            var var5 = 0
            while (true) {
                var8 = var2
                if (var5 >= var4) {
                    break
                }
                var0 = var3[var5]
                if (var0.contains("=")) {
                    val var6 = var0.indexOf("=\"")
                    if (var6 > 0) {
                        val var7 = var0.substring(0, var6)
                        var1 = var0.substring(var6 + 2)
                        var0 = var1
                        if (!TextUtils.isEmpty(var1)) {
                            var0 = var1.replace("\"", "")
                        }
                        var2[var7] = var0
                    }
                }
                ++var5
            }
        } else {
            var8 = null
        }
        return var8
    }
}