package cn.ibona.t1.common.control.utils;

import java.text.DecimalFormat;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by qun on 15/10/16.
 */
public class StringFormatUtil {


    private static final String EMAIL_REGEX = "^([a-z0-9A-Z]+[-|\\.]?)+[a-z0-9A-Z]@([a-z0-9A-Z]+(-[a-z0-9A-Z]+)?\\.)+[a-zA-Z]{2,}$";
    private static final String NUMBER_REGEX = "^[0-9]*$";
    private static final String PHONE_NUMBER_REGEX = "^((13[0-9])|(15[^4,\\D])|(17[0,6,7,8])|(18[0-9])|(14[5,7]))\\d{8}$";
    private static final String PASSWORD_REGEX = "[`~!@#$%^&*()+=|{}':;',//[//].<>/?~！@#￥%……&*（）——+|{}【】‘；：”“’。，、？]";
    private static final String ABC_REGEX = "^[A-Za-z]+$";
    /**
     * 判断是否是Email格式
     * @param email 用于判断的email文本
     * @return 正确Email格式，返回true
     */
    public static boolean isEmailFormat(String email) {
        boolean tag = true;
        final Pattern pattern = Pattern.compile(EMAIL_REGEX);
        final Matcher mat = pattern.matcher(email);
        if (!mat.find()) {
            tag = false;
        }
        return tag;
    }

    /**
     * 格式化为两位小数的文本
     * @param value float数值
     * @return 两位小数的文本
     */
    public static String formatTwoDecimalPlaces (float value) {
        DecimalFormat decimalFormat = new DecimalFormat("0.00");//构造方法的字符格式这里如果小数不足2位,会以0补足.
        String p = decimalFormat.format(value);//format 返回的是字符串
        return p;
    }


    /**匹配数字*/
    public static boolean isNumber(String number) {
        Pattern regex = Pattern.compile(NUMBER_REGEX);
        Matcher matcher = regex.matcher(number);
        return matcher.matches();
    }
    /**匹配手机号码*/
    public static boolean isMobilePhoneNumber(String number) {
        Pattern regex = Pattern.compile(PHONE_NUMBER_REGEX);
        Matcher matcher = regex.matcher(number);
        return matcher.matches();
    }
    /**正在匹配注册密码过滤非法字符*/
    public static boolean patterPasswrod(String password) {
        if (password.length() < 6 || password.length() > 16) {
            return true;
        }
        Pattern p = Pattern.compile(PASSWORD_REGEX);
        Matcher m = p.matcher(password);
        return m.find();
    }

    /**
     * 判断是否为字母
     * @return true 如果是字母的话
     */
    public static boolean isAbc(String str){
        Pattern regex = Pattern.compile(ABC_REGEX);
        Matcher matcher = regex.matcher(str);
        return matcher.matches();
    }
}
