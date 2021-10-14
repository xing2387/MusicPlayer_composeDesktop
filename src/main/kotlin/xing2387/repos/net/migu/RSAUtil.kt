package xing2387.repos.net.migu

import xing2387.repos.net.migu.KeyHandlerNative.getSecret
import xing2387.repos.net.migu.Base64Utils.decode
import java.security.PrivateKey
import java.security.PublicKey
import javax.crypto.Cipher
import java.io.UnsupportedEncodingException
import java.lang.Exception
import java.lang.StringBuilder
import java.nio.charset.Charset
import java.security.KeyFactory
import java.security.Signature
import java.security.spec.PKCS8EncodedKeySpec
import java.security.spec.X509EncodedKeySpec

/*
 * Exception performing whole class analysis ignored.
 */
class RSAUtil {
    //    public String signQr(byte[] object) {
    //        try {
    //            Signature signature = Signature.getInstance("SHA256withRSA");
    //            signature.initSign(this.mQrPrivateKey);
    //            signature.update((byte[]) object);
    //            return StringUtils.bytesToString((byte[]) signature.sign());
    //        } catch (Exception exception) {
    //            return null;
    //        }
    //    }
    fun verify(arrby: ByteArray?, string: String?): Boolean {
        return try {
            val signature = Signature.getInstance("SHA256withRSA")
            signature.initVerify(mClientPublicKey)
            signature.update(arrby)
            signature.verify(StringUtils.hex2Bytes(string))
        } catch (exception: Exception) {
            false
        }
    }

    companion object {
        private const val CIPHER_ALGORITHM = "RSA/ECB/PKCS1Padding"
        private const val CLIENT_PUBLIC_KEY =
            "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQDN2CQ+ZcsbHVrLSJ+/v0BA6kgoa+riowfZErlGdlMjUhf4UTos1ZzKfavQjctIQjlEStxBWAZ+gtmtYakUmjWFBupks6RWcR/ho+VEePpzxhSis8M6zHDhf9Dl8u6uvSAdk+CM3NgGle0bC8WnMxy2k9k3MSrX93AKZV36DCMtUQIDAQAB"
        private const val DEFAULT_CHARSET = "UTF-8"
        private const val KEY_ALGORITHM = "RSA"
        private const val PEM_TAG = '-'
        private const val SERVER_PUBLIC_KEY =
            "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQDMQK5vjD6+FRf8/RQDgeyg8T/GvLo3CD7ngcZ0l2fXuHh83gxgM1C7BNN1RgQFRXWFzMDLxbynm/ZA3GsntY+OiN8SBza2SEnir+p0jrsZ+WNhaRWch4FpAxnfcnfxrJL26ZkfYaxS85pKby/IQuSwIswVom0F06RIZreK3rQDrwIDAQAB"
        private const val SIGNATURE_ALGORITHM = "SHA256withRSA"
        const val SIGN_ALGORITHMS = "SHA1WithRSA"
        private val TAG = RSAUtil::class.java.simpleName
        private val mClientPrivateKey = generateClientPrivateKey()
        private val mClientPublicKey = generateClientPublicKey()

        //    private static PrivateKey mQrPrivateKey = generateQRPrivateKey();
        private val mServerPublicKey = generateServerPublicKey()

        //    public static boolean doCheck(String charSequence, String string, String object) {
        //        try {
        //            Object object2 = KeyFactory.getInstance("RSA");
        //            object = Base64Utils.decode((String) object);
        //            X509EncodedKeySpec x509EncodedKeySpec = new X509EncodedKeySpec((byte[]) object);
        //            object2 = ((KeyFactory) object2).generatePublic(x509EncodedKeySpec);
        //            object = Signature.getInstance("SHA1WithRSA");
        //            ((Signature) object).initVerify((PublicKey) object2);
        //            ((Signature) object).update(((String) charSequence).getBytes("utf-8"));
        //            object2 = new StringBuilder();
        //            charSequence = new StringBuilder();
        //            boolean bl = ((Signature) object).verify(Base64Utils.decode((String) string));
        //            charSequence = new StringBuilder();
        //            return bl;
        //        } catch (Exception exception) {
        //            return false;
        //        }
        //    }
        private fun getAlgorithms(bl: Boolean): String {
            return if (!bl) "SHA1WithRSA" else "SHA256withRSA"
        }

        //    public static String sign(String string, String object, boolean bl) {
        //        try {
        //            Object object2 = new PKCS8EncodedKeySpec(Base64Utils.decode((String) object));
        //            object = KeyFactory.getInstance("RSA").generatePrivate((KeySpec) object2);
        //            object2 = Signature.getInstance(RSAUtil.getAlgorithms((boolean) bl));
        //            ((Signature) object2).initSign((PrivateKey) object);
        //            ((Signature) object2).update(string.getBytes("UTF-8"));
        //            return Base64Utils.encode((byte[]) ((Signature) object2).sign());
        //        } catch (Exception exception) {
        //            return null;
        //        }
        //    }
        fun byPrivateKeyDecrypt(var1: String): String {
            var var1 = var1
            val var2 = ""
            if (mClientPrivateKey == null) {
                var1 = var2
            } else if (TextUtils.isEmpty(var1)) {
                var1 = var2
            } else {
                val var4: String
                try {
                    val var3 = Cipher.getInstance("RSA/ECB/PKCS1Padding")
                    var3.init(2, mClientPrivateKey)
                    var4 =
                        String(var3.doFinal(StringUtils.hex2Bytes(var1)), Charset.forName("UTF-8"))
                } catch (var6: Exception) {
                    var6.printStackTrace()
                    var1 = var2
                    return var1
                }
                var1 = var4
            }
            return var1
        }

        fun byPublicKeyEncrypt(var1: String?): String? {
            var var1 = var1
            val var2: Any? = null
            var1 = if (mServerPublicKey == null) {
                var2 as String?
            } else if (TextUtils.isEmpty(var1)) {
                var2 as String?
            } else {
                try {
                    val var3 = Cipher.getInstance("RSA/ECB/PKCS1Padding")
                    var3.init(1, mServerPublicKey)
                    StringUtils.bytesToString(var3.doFinal(var1!!.toByteArray(charset("UTF-8"))))
                } catch (var4: Exception) {
                    var2 as String?
                }
            }
            return var1
        }

        fun generateClientPrivateKey(): PrivateKey? {
            return try {
                var `object`: Any = StringBuilder()
                for (i in 1..13) {
                    (`object` as StringBuilder).append(getSecret(i))
                    `object`.append('\r')
                }
                `object` = decode((`object` as StringBuilder).toString())
                val pKCS8EncodedKeySpec = PKCS8EncodedKeySpec(`object`)
                //            object = Build.VERSION.SDK_INT >= 24 ? KeyFactory.getInstance("RSA") : KeyFactory.getInstance("RSA", "BC");
                `object` = KeyFactory.getInstance("RSA")
                `object`.generatePrivate(pKCS8EncodedKeySpec)
            } catch (exception: Exception) {
                exception.printStackTrace()
                null
            }
        }

        fun generateClientPublicKey(): PublicKey? {
            return try {
                val arrby =
                    decode("MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQDN2CQ+ZcsbHVrLSJ+/v0BA6kgoa+riowfZErlGdlMjUhf4UTos1ZzKfavQjctIQjlEStxBWAZ+gtmtYakUmjWFBupks6RWcR/ho+VEePpzxhSis8M6zHDhf9Dl8u6uvSAdk+CM3NgGle0bC8WnMxy2k9k3MSrX93AKZV36DCMtUQIDAQAB")
                val keyFactory = KeyFactory.getInstance("RSA")
                val x509EncodedKeySpec = X509EncodedKeySpec(arrby)
                keyFactory.generatePublic(x509EncodedKeySpec)
            } catch (exception: Exception) {
                exception.printStackTrace()
                null
            }
        }

        //    public static PrivateKey generateQRPrivateKey() {
        //        try {
        //            Object object = new StringBuilder();
        //            for (int i = 1; i < 14; ++i) {
        //                ((StringBuilder) object).append(KeyHandlerNative.getQRSecret((int) i));
        //                ((StringBuilder) object).append('\r');
        //            }
        //            object = Base64Utils.decode((String) ((StringBuilder) object).toString());
        //            PKCS8EncodedKeySpec pKCS8EncodedKeySpec = new PKCS8EncodedKeySpec((byte[]) object);
        ////            object = Build.VERSION.SDK_INT >= 24 ? KeyFactory.getInstance("RSA") : KeyFactory.getInstance("RSA", "BC");
        //            object = KeyFactory.getInstance("RSA");
        //            return ((KeyFactory) object).generatePrivate(pKCS8EncodedKeySpec);
        //        } catch (Exception exception) {
        //            exception.printStackTrace();
        //            return null;
        //        }
        //    }
        fun generateServerPublicKey(): PublicKey? {
            return try {
                val arrby =
                    decode("MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQDMQK5vjD6+FRf8/RQDgeyg8T/GvLo3CD7ngcZ0l2fXuHh83gxgM1C7BNN1RgQFRXWFzMDLxbynm/ZA3GsntY+OiN8SBza2SEnir+p0jrsZ+WNhaRWch4FpAxnfcnfxrJL26ZkfYaxS85pKby/IQuSwIswVom0F06RIZreK3rQDrwIDAQAB")
                val keyFactory = KeyFactory.getInstance("RSA")
                val x509EncodedKeySpec = X509EncodedKeySpec(arrby)
                keyFactory.generatePublic(x509EncodedKeySpec)
            } catch (exception: Exception) {
                exception.printStackTrace()
                null
            }
        }

        @JvmStatic
        fun sign(`object`: ByteArray?): String? {
            return try {
                val signature = Signature.getInstance("SHA256withRSA")
                signature.initSign(mClientPrivateKey)
                signature.update(`object`)
                StringUtils.bytesToString(signature.sign() as ByteArray)
            } catch (exception: Exception) {
                null
            }
        }
    }
}