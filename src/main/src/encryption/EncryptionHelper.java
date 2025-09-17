package encryption;


import encryption.hash.HashHelper;
import encryption.hash.HashType;
import encryption.hmac.HmacHelper;
import encryption.hmac.HmacType;

/**
 * 罗列几种加密方法
 *
 * @author gunaonian
 */
public class EncryptionHelper {

    private static final String VERSION = "0.1.1";

    public static String getVersion() {
        return VERSION;
    }

    /**
     * MD5 加密
     *
     * @param data 加密数据
     * @return 加密结果
     */
    public static String getMd5Param(String data) {
        return HashHelper.encryptHashToString(HashType.MD5, data);
    }

    /**
     * SHA256 加密
     *
     * @param data 加密数据
     * @return 加密结果
     */
    public static String getSha256Param(String data) {
        return HashHelper.encryptHashToString(HashType.SHA_256, data);
    }

    /**
     * HmacMd5 加密
     *
     * @param data 加密数据
     * @param key  密码
     * @return 加密结果
     */
    public static String getHmacMd5Param(String data, String key) {
        return HmacHelper.encryptHmacToString(HmacType.HMAC_MD5, data, key);
    }

    /**
     * HmacSHA56 加密
     *
     * @param data 加密数据
     * @param key  密码
     * @return 加密结果
     */
    public static String getHmacSha256Param(String data, String key) {
        return HmacHelper.encryptHmacToString(HmacType.HMAC_SHA256, data, key);
    }


}