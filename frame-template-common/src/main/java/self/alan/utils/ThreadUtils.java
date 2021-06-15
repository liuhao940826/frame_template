package self.alan.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author jeecg-boot
 * @version 1.0
 * @description 描述
 * @create 2019-11-25 14:34
 */
public class ThreadUtils {

    public static final Logger logger = LoggerFactory.getLogger(ThreadUtils.class);

    public static void sleep(long millis) {
        try {
            Thread.sleep(millis);
        } catch (InterruptedException e) {
            logger.error("thread sleep fail." + e.getMessage(), e);
        }
    }
}
