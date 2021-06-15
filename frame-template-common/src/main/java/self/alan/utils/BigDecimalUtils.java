package self.alan.utils;

import java.math.BigDecimal;

/**
 * @Classname BigDecimalUtils
 * @Description TODO
 * @Date 2020/12/21 上午10:16
 * @Created by liuhao
 */
public class BigDecimalUtils {

    public static Integer DEFAULT_SCALE = 2;

    public static Integer DEFAULT_SCALE_FOUR = 4;

    public static Integer DEFAULT_SELF_DIVIDE= 1;

    public static Integer ZERO = 0;

    /**
     * 用返回String 是因为数字序列化到 位数过多的情况下前端会精度丢失
     * @param targetCount
     * @param totalCount
     * @param scale
     * @param roundMode
     * @return
     */
    public static String calculatePercentStr(Integer targetCount , Integer totalCount,Integer scale,Integer roundMode){



        BigDecimal target = new BigDecimal(targetCount);
        BigDecimal total = new BigDecimal(totalCount);

        if(scale==null){
            scale =  DEFAULT_SCALE;
        }

        if(roundMode==null){
            roundMode =  BigDecimal.ROUND_HALF_UP;
        }

        if(totalCount==null || ZERO.equals(totalCount)){
            String zeroPercent = BigDecimal.ZERO.setScale(DEFAULT_SCALE).toString();

            StringBuffer sb = new StringBuffer(zeroPercent);

            zeroPercent= sb.append("%").toString();

            return zeroPercent;
        }

        BigDecimal divideNumber = target.divide(total, scale, roundMode);

        BigDecimal multiply = divideNumber.multiply(new BigDecimal(100));

        String percent = multiply.setScale(DEFAULT_SCALE).toString();

        StringBuffer sb = new StringBuffer(percent);

        percent= sb.append("%").toString();

        return percent;
    }


    /**
     * 用返回String 是因为数字序列化到 位数过多的情况下前端会精度丢失
     * @param target
     * @param total
     * @param scale
     * @param roundMode
     * @return
     */
    public static String calculateBigDecimalPercentStr(BigDecimal target,BigDecimal total,Integer scale,Integer roundMode){


        if(scale==null){
            scale =  DEFAULT_SCALE;
        }

        if(roundMode==null){
            roundMode =  BigDecimal.ROUND_HALF_UP;
        }

        if(total==null || ZERO.equals(total)){
            String zeroPercent = BigDecimal.ZERO.setScale(DEFAULT_SCALE).toString();

            StringBuffer sb = new StringBuffer(zeroPercent);

            zeroPercent= sb.append("%").toString();

            return zeroPercent;
        }

        BigDecimal divideNumber = target.divide(total, scale, roundMode);

        BigDecimal multiply = divideNumber.multiply(new BigDecimal(100));

        String percent = multiply.setScale(DEFAULT_SCALE).toString();

        StringBuffer sb = new StringBuffer(percent);

        percent= sb.append("%").toString();

        return percent;
    }


    /**
     * 用返回String 是因为数字序列化到 位数过多的情况下前端会精度丢失
     * @param target
     * @param total
     * @param scale
     * @param roundMode
     * @return
     */
    public static BigDecimal calculateBigDecimalPercent(BigDecimal target , BigDecimal total,Integer scale,Integer roundMode){

        if(target.compareTo(total)==0){
            return new BigDecimal(100).setScale(scale,roundMode);
        }


        if(scale==null){
            scale =  DEFAULT_SCALE;
        }

        if(roundMode==null){
            roundMode =  BigDecimal.ROUND_HALF_UP;
        }

        if(total==null || ZERO.equals(total)){

            BigDecimal result = total.setScale(scale, roundMode);

            return result;
        }

        return target.divide(total, scale,roundMode);
    }


    /**
     * 用返回String 是因为数字序列化到 位数过多的情况下前端会精度丢失
     * @param targetCount
     * @param totalCount
     * @param scale
     * @param roundMode
     * @return
     */
    public static BigDecimal calculatePercent(Integer targetCount , Integer totalCount,Integer scale,Integer roundMode){

        if(targetCount.equals(totalCount)){
            return new BigDecimal(100).setScale(scale,roundMode);
        }

        BigDecimal target = new BigDecimal(targetCount);
        BigDecimal total = new BigDecimal(totalCount);

        if(scale==null){
            scale =  DEFAULT_SCALE;
        }

        if(roundMode==null){
            roundMode =  BigDecimal.ROUND_HALF_UP;
        }

        if(totalCount==null || ZERO.equals(totalCount)){

            BigDecimal result = total.setScale(scale, roundMode);

            return result;
        }

        return target.divide(total, scale,roundMode).multiply(new BigDecimal(100));
    }


    /**
     * 用返回String 是因为数字序列化到 位数过多的情况下前端会精度丢失
     * @param targetCount
     * @param totalCount
     * @param scale
     * @param roundMode
     * @return
     */
    public static BigDecimal calculatePercentMuti100(Integer targetCount , Integer totalCount,Integer scale,Integer roundMode){

        BigDecimal target = new BigDecimal(targetCount);
        BigDecimal total = new BigDecimal(totalCount);

        if(scale==null){
            scale =  DEFAULT_SCALE;
        }

        if(roundMode==null){
            roundMode =  BigDecimal.ROUND_HALF_UP;
        }

        if(totalCount==null || ZERO.equals(totalCount)){

            BigDecimal result = total.setScale(scale, roundMode);

            return result;
        }

        return target.divide(total, scale,roundMode).multiply(new BigDecimal(100));
    }


}
