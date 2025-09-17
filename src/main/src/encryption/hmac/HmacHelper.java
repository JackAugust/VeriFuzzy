package encryption.hmac;


import encryption.utils.LogUtils;
import encryption.utils.StringUtil;
import encryption.utils.TextUtils;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.math.BigInteger;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;


/**
 * hmac算法主类
 *
 * @author gunaonian
 */
public class HmacHelper {

    private static final String TAG = HmacHelper.class.getSimpleName();

    /**
     * Hmac加密返回值
     *
     * @param hashType hash类型
     * @param data     The data.
     * @param key      加密密码
     * @return the hex string of Hmac encryption
     */
    public static String encryptHmacToString(HmacType hashType, String data, String key) {
        if (TextUtils.isEmpty(data) || TextUtils.isEmpty(key)) {
            return null;
        }
        return encryptHmacToString(hashType, data.getBytes(), key.getBytes());
    }

    public static BigInteger encryptHmacToBigInteger(HmacType hashType, String data, String key) {
        if (TextUtils.isEmpty(data) || TextUtils.isEmpty(key)) {
            return null;
        }
        return encryptHmac2(hashType, data.getBytes(), key.getBytes());
    }

    /**
     * Hmac加密返回值
     *
     * @param hashType hash类型
     * @param data     The data.
     * @param key      byte加密密码
     * @return the hex string of Hmac encryption
     */
    public static String encryptHmacToString(HmacType hashType, byte[] data, byte[] key) {
        return StringUtil.bytes2HexString(encryptHmac(hashType, data, key));
    }

    /**
     * Hmac加密返回值
     *
     * @param hashType hash类型
     * @param data     The data.
     * @param key      byte加密密码
     * @return the hex string of Hmac encryption
     */
    public static byte[] encryptHmac(HmacType hashType, byte[] data, byte[] key) {
        return hmacTemplate(hashType, data, key);
    }

    public static BigInteger encryptHmac2(HmacType hashType, byte[] data, byte[] key) {
        return hmacTemplate2(hashType, data, key);
    }

    /**
     * 具体的方法计算
     *
     * @param hashType hash加密类型
     * @param data     byte加密数据
     * @param key      byte加密密码
     * @return byte加密后的数据
     */
    private static byte[] hmacTemplate(HmacType hashType, byte[] data, byte[] key) {
        if (data == null || data.length == 0 || key == null || key.length == 0) {
            return null;
        }
        try {
            SecretKeySpec secretKey = new SecretKeySpec(key, hashType.getTypeName());
            Mac mac = Mac.getInstance(hashType.getTypeName());
            mac.init(secretKey);
            return mac.doFinal(data);
        } catch (InvalidKeyException | NoSuchAlgorithmException e) {
            LogUtils.e(TAG, e.toString());
            return null;
        }
    }

    // 修改后的函数，返回BigInteger类型
    public static BigInteger hmacTemplate2(HmacType hashType, byte[] data, byte[] key) {
        if (data == null || data.length == 0 || key == null || key.length == 0) {
            return null; // 或者抛出一个更具体的异常
        }
        try {
            SecretKeySpec secretKey = new SecretKeySpec(key, hashType.getTypeName());
            Mac mac = Mac.getInstance(hashType.getTypeName());
            mac.init(secretKey);
            byte[] hmacBytes = mac.doFinal(data);
            // 直接将HMAC的字节输出转换为BigInteger，假设HMAC输出总是非负的
            return new BigInteger(1, hmacBytes); // 1 表示正数
        } catch (InvalidKeyException | NoSuchAlgorithmException e) {
            // 使用适当的日志记录机制记录错误，这里只是简单地打印到控制台
            System.err.println(TAG + ": " + e.toString());
            return null; // 或者抛出一个运行时异常
        }
    }


}
