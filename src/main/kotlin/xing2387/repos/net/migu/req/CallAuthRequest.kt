package xing2387.repos.net.migu.req

import com.google.gson.JsonObject
import com.google.gson.JsonParser
import xing2387.repos.net.migu.*
import xing2387.repos.net.migu.HTTPSClient.ReqInfo
import xing2387.repos.net.migu.Main.baseDir
import java.io.*
import java.util.*

class CallAuthRequest(
    var phoneNumber: String,
    var verificationcode: String?,
    var password: String?
) : ReqInfo() {
    var isSms: Boolean
    var isPasswd: Boolean
    @Throws(IOException::class)
    override fun writeParam(out: Writer) {
        val sb = StringBuilder()
        val userManage = authorization(isSms, isPasswd)
        out.write("POST /client/authRequest HTTP/1.1\r\n")
        var str = """
            signature: ${SignatureDataHelper.getSignature(userManage)}
            
            """.trimIndent()
        sb.append(str)
        out.write(str)
        str = "Authorization: $userManage\r\n"
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

    fun authorization(bySms: Boolean, byPasswd: Boolean): String {
//        String format = "DUP Phone_ID=\"a6c64893f7e3fdfb\",rcflag=\"1\",verificationcode=\"685146\",appid=\"22002401\",msgid=\"d340ce51e77149d4b812c3619cd88a08" +
//                "\",enccnonce=\"\",apptype=\"3\",username=\"手机号\",sdkversion=\"UnionSDK-20AndroidV5.1.0\",clientversion=\"1.0" +
//                "\",secflag=\"1\",isSipApp=\"1\",tvflag=\"false\",btid=\"Njg1QUU1MEE4QTQ1QjYxRTdE@http://passport.migu.cn/n0001/@409bc7a6610d44c2bc7cef58b9ab0ba2\"";
        val msgId = UUID.randomUUID().toString().replace("-", "")
        val phoneId = "a6c64893f7e3fdfb"
        val apptype = "3"
        val rcflag = "1"
        val sdkversion = "UnionSDK-20AndroidV5.1.0"
        val clientversion = "1.0"
        val appid = "22002401"
        val secflag = "1"
        val isSipApp = "1"
        val tvflag = "false"
        val btid = ""
        val phone = phoneNumber
        val cnonceKek =
            EncUtil.getUUIDAndTimeToMd5(java.lang.Long.toString(System.currentTimeMillis()))
        val enccnonce = RSAUtil.byPublicKeyEncrypt(cnonceKek)
        val method = if (bySms) "DUP " else if (byPasswd) "UP " else ""
        val sb = StringBuilder()
        sb.append(method)
            .append("Phone_ID=\"").append(phoneId)
            .append("\",rcflag=\"").append(rcflag)
            .append("\",appid=\"").append(appid)
            .append("\",msgid=\"").append(msgId)
            .append("\",enccnonce=\"").append(enccnonce)
            .append("\",apptype=\"").append(apptype)
            .append("\",username=\"").append(phone)
            .append("\",sdkversion=\"").append(sdkversion)
            .append("\",clientversion=\"").append(clientversion)
            .append("\",secflag=\"").append(secflag)
            .append("\",isSipApp=\"").append(isSipApp)
            .append("\",tvflag=\"").append(tvflag)
            .append("\"")
        if (bySms) {
            sb.append("\",verificationcode=\"").append(verificationcode)
                .append("\",btid=\"").append(btid)
        }
        if (byPasswd) {
            val encpasswd = RSAUtil.byPublicKeyEncrypt(
                password
            )
            sb.append("\",validcode=\"").append("null")
                .append("\",encpasswd=\"").append(encpasswd)
                .append("\",sessionid=\"").append("null")
                .append("\",isusernamecn=\"").append("0")
        }
        return sb.toString()
    }

    override fun processResp(resp: JsonObject) {
        parseResultAndKs(resp)
    }

    companion object {
        var UNICORNPATH = "UNICORNPATH=/home/xing/.local/lib/python3.8/site-packages/unicorn"
        var KEYSTONEPATH = "KEYSTONEPATH=/home/xing/.local/lib/python3.8/site-packages/keystone"
        fun getJsonStrValue(key: String?, jsonObject: JsonObject): String {
            try {
                if (jsonObject.has(key)) {
                    return jsonObject[key].asString
                }
            } catch (e: Throwable) {
                e.printStackTrace()
            }
            return ""
        }

        fun parseResultAndKs(`object`: JsonObject?) {
            if (`object` == null || !`object`.has("resultCode")) {
                //返回格式不对，没有resultCode
                return
            }
            var resp_resultCode = `object`["resultCode"].asString.toInt()
            val string = `object`["WWW-Authenticate"].asString
            if (103000 != resp_resultCode) { //登录失败
                if (103204 == resp_resultCode) {
//                LogUtil.info((String) "make ks return null, skipping this request");
                }
                if (103203 == resp_resultCode) {
//                this.callback(103203, string);
                    return
                }
                if (103602 == resp_resultCode) {
//                this.procServiceAccountBlockError(object, n);
                    return
                }
                if (103507 == resp_resultCode) {
//                this.procUserPwd5Error(object, n);
                    return
                }
                if (103607 == resp_resultCode) {
//                this.procAndPassportLocked(object, n);
                    return
                }
                if (103516 == resp_resultCode || 103515 == resp_resultCode || 103901 == resp_resultCode) {
//                this.procSecVerifyParam(object, n);
                    return
                }
                if (103167 == resp_resultCode) {
//                this.procEmpoweReminds(object, n);
                    return
                }
                //            this.callback(n, string);
                return
            }
            resp_resultCode = -1
            if (`object`.has("flowCode")) {
                resp_resultCode = `object`["flowCode"].asString.toInt()
            }
            val object3: Any? = null
            var authMsgStr: String? = null
            if (`object`.has("Auth-Msg")) {
                authMsgStr = `object`["Auth-Msg"].asString
                try {
                    val authMsgJson = JsonParser.parseString(authMsgStr).asJsonObject
                    if (authMsgJson.has("emailbindmsg")) {
                        authMsgStr =
                            Base64Utils.decodeToString(authMsgJson["emailbindmsg"].asString)
                    }
                } catch (throwable: Throwable) {
                    throwable.printStackTrace()
                }
            }
            if (103530 == resp_resultCode) {      //不知道什么状态
//            this.callback(n, string, n, authMsgStr);
                return
            }
            val resp_mac = `object`["mac"].asString
            if (TextUtils.isEmpty(resp_mac)) {
//            LogUtil.info((String) "ks http response no mac info.");
//            this.callback(103117, string);
                return
            }
            val resp_www_authenticate = `object`["WWW-Authenticate"].asString
            //        Map map = this.getElements(resp_www_authenticate);
            var jsonStr = resp_www_authenticate.replace("DUP", "").trim { it <= ' ' }
            jsonStr = "{\"" + jsonStr.replace("=\"", "\":\"").replace(",", ",\"") + "}"
            val jsonObject = JsonParser.parseString(jsonStr).asJsonObject
            val auth_Nonce = getJsonStrValue("Nonce", jsonObject)
            var auth_BTID: String? = getJsonStrValue("BTID", jsonObject)
            auth_BTID = RSAUtil.byPrivateKeyDecrypt(auth_BTID!!)
            val auth_lifetime = getJsonStrValue("lifetime", jsonObject).toLong()
            val auth_expiretime = getJsonStrValue("expiretime", jsonObject)
            val auth_sqn = RSAUtil.byPrivateKeyDecrypt(getJsonStrValue("sqn", jsonObject))!!.toLong()
            val auth_passid = RSAUtil.byPrivateKeyDecrypt(getJsonStrValue("passid", jsonObject))
            val auth_username = RSAUtil.byPrivateKeyDecrypt(getJsonStrValue("username", jsonObject))
            val auth_spassword = getJsonStrValue("spassword", jsonObject)
            val auth_authtype = getJsonStrValue("authtype", jsonObject)
            val auth_implicit = getJsonStrValue("implicit", jsonObject)
            val auth_email = RSAUtil.byPrivateKeyDecrypt(getJsonStrValue("email", jsonObject))
            val auth_msisdn = RSAUtil.byPrivateKeyDecrypt(getJsonStrValue("msisdn", jsonObject))
            val auth_isusernamecn = getJsonStrValue("isusernamecn", jsonObject)
            //        String auth_1 = Base64Utils.decodeToString((String) this.auth_username);  //用户名可能要base64解码
            val var41 = makeKs(
                auth_passid,
                auth_Nonce,
                auth_lifetime,
                auth_BTID,
                auth_expiretime,
                auth_sqn,
                resp_www_authenticate,
                resp_mac
            )
            if (var41 == null) {
//            LogUtil.info("make ks return null, skipping this request");
//            this.callback(103117, resp_www_authenticate);
            } else if (!var41) {
//            this.callback(103117, resp_www_authenticate);
            } else {
//            LoginInfo.updateBtidSqn(this.mContext, var24, var28);
//            LogUtil.info("make ks successfully, sqn = " + var30);
                var resultString: String? = null
                if (`object`.has("resultString")) {
                    resultString = Base64Utils.decodeToString(`object`["resultString"].asString)
                }
                //            LogUtil.info("In BaseSso, accountRisk = " + var39);
//            TrackPointUtil.getInstance(this.mContext).updateLoginIdAndType(this.mUname, var24);
                val emailbindmsg: String? = null
                callback(
                    103000,
                    auth_username,
                    auth_passid,
                    auth_sqn,
                    auth_spassword,
                    auth_authtype,
                    auth_msisdn,
                    auth_email,
                    auth_implicit,
                    resultString,
                    resp_www_authenticate,
                    resp_resultCode,
                    emailbindmsg
                )
                //            var39 = DataStore.getInternalData(this.mContext, "SHELL_TEL");
//            if (!TextUtils.isEmpty(var39)) {
//                if (!var39.equals(this.mUname)) {
//                    DataStore.setInternalData(this.mContext, "SHELL_TEL", auth_username);
//                }
//            } else {
//                DataStore.setInternalData(this.mContext, "SHELL_TEL", auth_username);
//            }
            }
        }

        fun callback(
            n: Int,
            auth_username: String?,
            auth_passid: String?,
            auth_sqn: Long,
            auth_spassword: String?,
            auth_authtype: String?,
            auth_msisdn: String?,
            auth_email: String?,
            auth_implicit: String?,
            resultString: String?,
            resp_www_authenticate: String?,
            resp_resultCode: Int,
            emailbindmsg: String?
        ) {
//        if (this.mCallback == null)
//            return;
            val bundle = JsonObject()
            bundle.addProperty("resultCode", n)
            bundle.addProperty("username", auth_username)
            bundle.addProperty("sqn", auth_sqn)
            bundle.addProperty("passid", auth_passid)
            bundle.addProperty("password", auth_spassword)
            bundle.addProperty("authType", auth_authtype)
            bundle.addProperty("msisdn", auth_msisdn)
            bundle.addProperty("email", auth_email)
            bundle.addProperty("implicit", auth_implicit)
            bundle.addProperty("resultString", resultString)
            bundle.addProperty("resultInfo", resp_www_authenticate)
            bundle.addProperty("flowCode", resp_resultCode)
            bundle.addProperty("bindmsg", emailbindmsg)
            println("=========================== authRequest result start  ======================")
            println(bundle.toString())
            println("=========================== authRequest result end  ======================")
            val token = calcToken(auth_passid, "220024", auth_sqn, "7e22b0a4be260ed2")
            println("=========================== token start  ======================")
            println(token)
            println("=========================== token end  ======================")
            val aversionid = calcAversionid("9ea8bb973d2f0f16", System.currentTimeMillis())
            println("=========================== aversionid start  ======================")
            println(aversionid)
            println("=========================== aversionid end  ======================")
            val validate = TokenValidate.doReq(token, aversionid)
            println("=========================== validate start  ======================")
            println(validate)
            println("=========================== validate end  ======================")
        }

        /*
    {
    "resultCode": 103000,
    "username": "手机号",
    "sqn": 33264932,
    "passid": "378770921961759400",
    "password": "",
    "authType": "",
    "msisdn": "手机号",
    "email": null,
    "implicit": "0",
    "resultString": null,
    "resultInfo": "DUP Nonce=\"MHKBYypl9KG2wIeBUTrjz8Ck7pr4bINm\",BTID=\"9795DE260E189FCC1961D3067F90E070449E65FC45F77C5BDEAA2F2C4A178EF6EC03121A8496C8250C47EA250B1FCF759A347D0BFBF89A61F0944EE414D91078F0D1B716498423E334B724767567FB22A47FB4FEF85DB4043D8D422A37A06DA7A645AD1EE3B0BE4DA0E1C314623C03C1791E117AA30F63BECEC143DD831D25BD\",lifetime=\"7776000\",sqn=\"B034663EE81A8037698B67FD9B97A6A1B58720D61331005BAEF958DC62F72FB859B00E1C5FF1446FDF62A02F3725EF37D8689B210D50F06954B17D94D06530A5024CF2D74973D693D4B9E1468D1C438623BDC627E3DE6FEAE537C4BE074C0E229FC84F9107AA4D12542A561C67F90CC8B59082EE744B1DC0FDA9D7DD5C289F47\",expiretime=\"2021-07-21 10:04:16\",authtype=\"DUP\",username=\"B5D70C8A90592AC901AD9B988DEB8C9FF7D7374A6060EC2ABD28EE20FF7BB7056D063FA75021760882C731DCCC728DD5616E1FF1FBD97E7792A98B4761081D1A35B7125C90C0E3F1392A0DF6A7E7CC890C79B500274D4C90495BA63FFE5CCF7E70AC0704C5E0CA42DDFB636C2804D6A31AC3C377D7CBFD2CD3E3E1403BC508E4\",email=\"\",msisdn=\"4B8489E75BD8F3DB87F25911EC1729E7E2BA580AAB56808F98D4760627685F8CC47286FE148DD4B8C2DFE06E08249FB80FF7BF7353E2117C5747402BF5D95B5B69016EDDB647DDB92920806BBE6A7F69037229A4766960778150E539E464CB0DE67DD0C535E68FB3020BA9C9BE9BC7DE8721EC7CDF07B30E709C45D468DC6508\",implicit=\"0\",passid=\"C895811EBCE2BCEDC188F0181192C14FD258773DA37929031BB96D22D2C87FEC5BD7C7FFBC4CA86998A197AA8A6D1F5E4E1B3E050512564E6B79865042E456252D059D6AE9850D0A01D49ED15972BD82FB4127BF812AA9BEE0DE16414D474A1F25024FA1C1CE55E81B156FB18418585819520C3717AF3088FCB50C55347EBD57\"",
    "flowCode": -1,
    "bindmsg": null
}
    */
        fun makeKs(
            `object`: String?,
            string: String?,
            l: Long,
            string2: String?,
            string3: String?,
            l2: Long,
            string4: String?,
            string5: String?
        ): Boolean {
            return true
            //        if (TextUtils.isEmpty((CharSequence) string)) {
//            LogUtil.info((String) "nonce is null");
//            return false;
//        }
//        string3 = EncUtil.toMd5((String) ("migupassport:" + this.mUname + ":" + this.mSmsCode));
//        LogUtil.info((String) ("result : nonce=" + string + ",cnonce=" + this.mCnonceKek + ",btid=" + string2 + ",mac=" + string5 + ",kslifetime=" + l));
//        return KeyHandlerNative.getKeyByUSmsCode((String) object, (String) string3, (String) string, (String) this.mCnonceKek, (long) l, (String) string2, (long) l2, (String) string4, (String) string5);
        }

        @JvmStatic
        fun main(args: Array<String>) {
//        String str = "{\"Nonce\":\"lg5ZW9kDmpG4IhQD9Jib8wcERSsGrYyu\",\"BTID\":\"05DA66C150692085CF466705D58DA95DB3834A16869AA40D072A9A17D8E01A2853BF7F84CEB10BA7323ADB46F3EFBB7583B9E6F61E265E02D335911075C2767537337AAF5CCBBE5E21B9B5BF1090E4D26DB2C50EEF1123EDC6382E2CB2CEE43789146D2AD0E4493D77E8608900778CF43FFA5663A2C89904D5875ED630909AF4\",\"lifetime\":\"7776000\",\"sqn\":\"7047A90C05CB2521D456DADC03E43F970F5C90208316AD898E14E8209062D7DE3D979087A35FA9B2CCDCF8888D93F9806EB75A3B9EE76E5CCCE5E07B202E954FFE9D91C5C1CA84D61701F3DC382C77E24B3E82E0578EB2F1184178F97B41AA497527AF4BC3D43BF03562E4404F9D4B02F25A6A45CA503478F3E07E3E9FCFA478\",\"expiretime\":\"2021-07-21 09:33:58\",\"authtype\":\"\",\"username\":\"BAFD31CE385C58F8EE469B74E2FDDE1A59B4D867389C529C1BADD040DBE93BC7A3A25F070ABAC0BC99982C21DFC460822BB7993E4E55BD55FE8F41C0B0E90D1F090FB4A0B5D450451196D32F7EC50AAE85983C02C433CD194C9797037D209A956B4B42D47844D54DB122BDEC8A01AD288410A703E8101545762E760BBA924B0A\",\"email\":\"\",\"msisdn\":\"98E5FE3EEB3C5189014CE93CB54A3238CAB9EB77B53DD2504311979687A8F05AED50A329176C8CC6F0EC34D0FA2EFD2D1D0690C3D71FC5385B629A8D302D63D30D8ED4180E1C1293F156D4D4F169123D98C3ECBCF5262E97D825EE5F8B23B8A2A1FD7E519833EA63EB6F87AD9E8018A8B6A0E8FC3FA7EE15F3223D36CF6D8DB1\",\"implicit\":\"0\",\"passid\":\"A53BACFECB55F1889706FB8EEE656A8A9B960DA7D638C8BB6E9068A7F8085398954302811EB6116B8E982580765B3EF8B6A3C0589DBBBEA27AF8784AB1B726E75EBE42B027E0237B716810B7B251677D7343C4AE451E0543BD419356A999412D65F229FDE07C2E78A2DDCBBCEFEB4C7851FC4C095B2C0054E4A443EE2C199916\"}";
//        String objStr = "{\n" +
//                "    \"Server\": \"miguweb\",\n" +
//                "    \"Date\": \"Thu, 22 Apr 2021 01:33:58 GMT\",\n" +
//                "    \"Content-Length\": \"0\",\n" +
//                "    \"Connection\": \"keep-alive\",\n" +
//                "    \"X-Application-Context\": \"migu-sdk-auth:prod:7001\",\n" +
//                "    \"WWW-Authenticate\": \"DUP Nonce=\\\"lg5ZW9kDmpG4IhQD9Jib8wcERSsGrYyu\\\",BTID=\\\"05DA66C150692085CF466705D58DA95DB3834A16869AA40D072A9A17D8E01A2853BF7F84CEB10BA7323ADB46F3EFBB7583B9E6F61E265E02D335911075C2767537337AAF5CCBBE5E21B9B5BF1090E4D26DB2C50EEF1123EDC6382E2CB2CEE43789146D2AD0E4493D77E8608900778CF43FFA5663A2C89904D5875ED630909AF4\\\",lifetime=\\\"7776000\\\",sqn=\\\"7047A90C05CB2521D456DADC03E43F970F5C90208316AD898E14E8209062D7DE3D979087A35FA9B2CCDCF8888D93F9806EB75A3B9EE76E5CCCE5E07B202E954FFE9D91C5C1CA84D61701F3DC382C77E24B3E82E0578EB2F1184178F97B41AA497527AF4BC3D43BF03562E4404F9D4B02F25A6A45CA503478F3E07E3E9FCFA478\\\",expiretime=\\\"2021-07-21 09:33:58\\\",authtype=\\\"DUP\\\",username=\\\"BAFD31CE385C58F8EE469B74E2FDDE1A59B4D867389C529C1BADD040DBE93BC7A3A25F070ABAC0BC99982C21DFC460822BB7993E4E55BD55FE8F41C0B0E90D1F090FB4A0B5D450451196D32F7EC50AAE85983C02C433CD194C9797037D209A956B4B42D47844D54DB122BDEC8A01AD288410A703E8101545762E760BBA924B0A\\\",email=\\\"\\\",msisdn=\\\"98E5FE3EEB3C5189014CE93CB54A3238CAB9EB77B53DD2504311979687A8F05AED50A329176C8CC6F0EC34D0FA2EFD2D1D0690C3D71FC5385B629A8D302D63D30D8ED4180E1C1293F156D4D4F169123D98C3ECBCF5262E97D825EE5F8B23B8A2A1FD7E519833EA63EB6F87AD9E8018A8B6A0E8FC3FA7EE15F3223D36CF6D8DB1\\\",implicit=\\\"0\\\",passid=\\\"A53BACFECB55F1889706FB8EEE656A8A9B960DA7D638C8BB6E9068A7F8085398954302811EB6116B8E982580765B3EF8B6A3C0589DBBBEA27AF8784AB1B726E75EBE42B027E0237B716810B7B251677D7343C4AE451E0543BD419356A999412D65F229FDE07C2E78A2DDCBBCEFEB4C7851FC4C095B2C0054E4A443EE2C199916\\\"\",\n" +
//                "    \"mac\": \"b70c0e7735b39e8b1db8888fb1fe28a598842f1ebfce92229a71949182a444be\",\n" +
//                "    \"resultCode\": \"103000\"\n" +
//                "}";
//        JsonObject jsonObject = JsonParser.parseString(objStr).getAsJsonObject();
//        parseResultAndKs(jsonObject);

//        long auth_sqn = Long.parseLong(RSAUtil.byPrivateKeyDecrypt(getJsonStrValue("sqn", jsonObject)));
//        String auth_passid = RSAUtil.byPrivateKeyDecrypt(getJsonStrValue("passid", jsonObject));
            val token = calcToken("378770921961759400", "220024", 31406264, "7e22b0a4be260ed2")
            println("=========================== token start  ======================")
            println(token)
            println("=========================== token end  ======================")
            val aversionid = calcAversionid("7e22b0a4be260ed2", System.currentTimeMillis())
            println("=========================== aversionid start  ======================")
            println(aversionid)
            println("=========================== aversionid end  ======================")

//        String validate = TokenValidate.doReq(token, aversionid);
//        System.out.println("=========================== validate start  ======================");
//        System.out.println(validate);
//        System.out.println("=========================== validate end  ======================");
            val userInfo = QueryUserInfo.doReq("91042961852", aversionid)
            println("=========================== userInfo start  ======================")
            println(userInfo)
            println("=========================== userInfo end  ======================")
            val musicList = QueryMusicListSongs.doReq("179934290", aversionid, token)
            println("=========================== userInfo start  ======================")
            println(musicList)
            println("=========================== userInfo end  ======================")
        }

        fun calcAversionid(android: String?, timestamp: Long): String {
            val command = String.format(
                baseDir + "/app/bin/migunative encode %s %s",
                android,
                timestamp
            )
            val result = ExecCommand(
                command,
                UNICORNPATH,
                KEYSTONEPATH
            ).split("\n").toTypedArray()
            return result[result.size - 1]
        }

        //# passidMd5 = "d32e62f77c7138dacb61ca6b264b4ec8"
        //            # passid = "378770921961759400"
        //            # sourceId = "220024"
        //            # sqn = 59280976
        //            # androidId = "7e22b0a4be260ed2"
        fun calcToken(passid: String?, sourceId: String?, sqn: Long, androidId: String?): String {
            var token = "errorToken"
            try {
                val command: String = kotlin.String.format(
                    baseDir + "/app/bin/migunative token %s %s %s %d %s",
                    EncUtil.toMd5(passid!!),
                    passid,
                    sourceId,
                    sqn,
                    androidId
                )
                println(command)
                val result =
                    ExecCommand(command, UNICORNPATH, KEYSTONEPATH).split("\n").toTypedArray()
                token = result[result.size - 1]
            } catch (e: Exception) {
                e.printStackTrace()
            }
            return token
        }

        fun testCalcToken(): String {
            val command: String =
                baseDir + baseDir + "/app/bin/migunative token d32e62f77c7138dacb61ca6b264b4ec8 378770921961759400 220024 59280976 7e22b0a4be260ed2"
            return ExecCommand(command, UNICORNPATH, KEYSTONEPATH)
        }

        fun ExecCommand(command: String?, vararg envp: String?): String {
            try {
                val process = Runtime.getRuntime().exec(command, envp)
                val bis = BufferedInputStream(process.inputStream)
                val br = BufferedReader(InputStreamReader(bis))
                var line: String?
                val sb = StringBuilder()
                while (br.readLine().also { line = it } != null) {
                    sb.append(line).append("\n")
                    println(line)
                }
                process.waitFor()
                if (process.exitValue() != 0) {
                    println("error!")
                }
                bis.close()
                br.close()
                return sb.toString()
            } catch (e: Exception) {
                e.printStackTrace()
            }
            return "-1"
        }
    }

    init {
        isSms = verificationcode != null
        isPasswd = password != null
    }
}