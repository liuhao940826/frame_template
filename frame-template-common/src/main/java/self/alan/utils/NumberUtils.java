package self.alan.utils;

/**
 * @author jeecg-boot
 * @version 1.0
 * @description 描述
 * @create 2019-11-25 14:34
 */
public class NumberUtils {

    public static boolean checkLargeZero(Long num) {
        return null != num && num > 0L;
    }

    public static boolean checkLargeZero(Integer num) {
        return null != num && num > 0;
    }

    public static boolean checkLessEqZero(Integer num) {
        return !checkLargeZero(num);
    }

    public static boolean checkLessEqZero(Long num) {
        return !checkLargeZero(num);
    }

    public static boolean checkBetween(Integer num, int minValue, int maxValue) {
        return null != num && num <= maxValue && num >= minValue;
    }

}
