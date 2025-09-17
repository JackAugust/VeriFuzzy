package encryption.utils;



import encryption.EncryptionHelper;

import java.util.logging.Level;
import java.util.logging.Logger;


public class LogUtils {

    private static final String TAG = EncryptionHelper.class.getSimpleName() + "-" + EncryptionHelper.getVersion();
    private static final Logger LOGGER = Logger.getLogger(EncryptionHelper.class.getName());
    public static void i(String tag, String value) {
        LOGGER.log(Level.INFO, TAG + "-" + tag + ": " + value);
    }

    public static void e(String tag, String value) {
        LOGGER.log(Level.SEVERE, TAG + "-" + tag + ": " + value);
    }
}
