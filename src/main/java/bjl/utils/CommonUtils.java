package bjl.utils;

import java.util.regex.Pattern;

/**
 * Created by zhouxh on 2018/8/25.
 */
public class CommonUtils {

    //判断是否为数字
    public static boolean isInteger(String str) {
        Pattern pattern = Pattern.compile("^[-\\+]?[\\d]*$");
        return pattern.matcher(str).matches();
    }
}
