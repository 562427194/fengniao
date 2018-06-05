package mybase.library.util;

import android.text.TextUtils;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by admin on 2017/7/6.
 */

public class StrUtils {
    public static boolean isNull(String str) {
        if (str == null)
            return true;
        if (str.trim().equals(""))
            return true;
        if (str.equalsIgnoreCase("null"))
            return true;
        return false;
    }

    public static boolean canNotBeEmpty(String error, TextView... tvs) {
        for (TextView tv : tvs) {
            if (TextUtils.isEmpty(tv.getText())) {
                tv.setError(error);
                return false;
            }
        }
        return true;
    }

    public static boolean isNull(TextView tv) {
        String str = tv.getText().toString();
        if (str == null)
            return true;
        if (str.trim().equals(""))
            return true;
        return false;
    }

    public static String getNotNullStr(JSONObject json, String key) {
        try {
            String str = json.getString(key);
            if (notNull(str)) {
                return str;
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return "";
    }

    public static boolean notNull(TextView tv) {
        return !isNull(tv);
    }

    public static boolean notNull(String str) {
        return !isNull(str);
    }

    public static boolean isMobile(String str) {
        Pattern p = null;
        Matcher m = null;
        boolean b = false;
        p = Pattern.compile("^[1][3,4,5,7,8][0-9]{9}$");
        m = p.matcher(str);
        b = m.matches();
        return b;
    }

    /**
     *
     * @param str
     * @return
     */
    public static boolean isPhone(String str) {
        Pattern p1 = null, p2 = null;
        Matcher m = null;
        boolean b = false;
        p1 = Pattern.compile("^[0][1-9]{2,3}-[0-9]{5,10}$");
        p2 = Pattern.compile("^[1-9]{1}[0-9]{5,8}$");
        if (str.length() > 9) {
            m = p1.matcher(str);
            b = m.matches();
        } else {
            m = p2.matcher(str);
            b = m.matches();
        }
        return b;
    }

    public static boolean isPhoneOrMobile(String str) {
        return isPhone(str) || isMobile(str);
    }

    /**
     *
     * @param passWord
     * @return
     */
    public static boolean verifyPassWord(String passWord) {
        Pattern p = null;
        Matcher m = null;
        boolean b = false;
        p = Pattern.compile("^(?![0-9]+$)(?![a-zA-Z]+$)[0-9A-Za-z]{6,16}$");
        m = p.matcher(passWord);
        b = m.matches();
        return b;
    }
    //邮箱验证
    public static boolean isEmail(String strEmail) {
        String strPattern = "^[a-zA-Z0-9][\\w\\.-]*[a-zA-Z0-9]@[a-zA-Z0-9][\\w\\.-]*[a-zA-Z0-9]\\.[a-zA-Z][a-zA-Z\\.]*[a-zA-Z]$";
        if (TextUtils.isEmpty(strPattern)) {
            return false;
        } else {
            return strEmail.matches(strPattern);
        }
    }

}
