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

object ConstantUtil {
    val aESKey: String
        get() = KeyHandlerNative.getSecret(21)!!
}