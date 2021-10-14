package xing2387.repos.net.migu

import xing2387.repos.net.migu.RSAUtil.Companion.sign

object SignatureDataHelper {
    fun getSignature(reqValue: String): String? {
        return sign(reqValue.toByteArray())
    }
}