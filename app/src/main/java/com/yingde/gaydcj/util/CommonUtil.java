package com.yingde.gaydcj.util;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Environment;
import android.provider.Settings;
import android.telephony.TelephonyManager;
import android.util.Base64;
import android.widget.Toast;

import com.yingde.gaydcj.entity.User;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


/**
 * 公用工具
 */
public class CommonUtil {


    private static final String FILE_NAME = "PEOPLE_PD", TOKEN_ID = "TOKEN_ID", USER = "USER", REGCODE = "REGCODE";

    //token
    public static String getTokenId(Context mContext) {
        String tokenId = SharedPreferencesUtil.getSetting(FILE_NAME, mContext, TOKEN_ID);
        return tokenId;
    }

    public static void setTokenId(Context mContext, String tokenId) {
        SharedPreferencesUtil.setSetting(FILE_NAME, mContext, TOKEN_ID, tokenId);
    }

    //RegCode
    public static String getRegCode(Context mContext) {
        String tokenId = SharedPreferencesUtil.getSetting(FILE_NAME, mContext, REGCODE);
        return tokenId;
    }

    public static void setRegCode(Context mContext, String tokenId) {
        SharedPreferencesUtil.setSetting(FILE_NAME, mContext, REGCODE, tokenId);
    }

    //登陆基础信息
    public static User getUserEntity(Context mContext) {

        return SharedPreferencesUtil.getObject(FILE_NAME, mContext, USER, User.class);
    }

    public static void setUserEntity(Context mContext, User userEntity) {
        SharedPreferencesUtil.setObject(FILE_NAME, mContext, USER, userEntity);
    }


    /**
     * 验证邮箱
     *
     * @param email
     * @return
     */
    public static boolean checkEmail(String email) {
        boolean flag = false;
        try {
            String check = "^([a-z0-9A-Z]+[-|\\.]?)+[a-z0-9A-Z]@([a-z0-9A-Z]+(-[a-z0-9A-Z]+)?\\.)+[a-zA-Z]{2,}$";
            Pattern regex = Pattern.compile(check);
            Matcher matcher = regex.matcher(email);
            flag = matcher.matches();
        } catch (Exception e) {
            flag = false;
        }
        return flag;
    }


    public static String dataToString(DateFormat dateFormat, Date date, String userId, String type) {
        if (userId != null && type != null)
            return dateFormat.format(date) + "__" + userId + "__" + type + "__" + ".jpg";
        else
            return dateFormat.format(date) + ".jpg";
    }

    public static String dataToString(DateFormat dateFormat, Date date, int index) {
        return "imageFile" + index + "_" + dateFormat.format(date);
    }


    /**
     * 验证是否是手机号码
     *
     * @param mobiles
     * @return
     */
    public static boolean isMobileNumber(String mobiles) {
        return mobiles
                .matches("^((13[0-9])|(14[0-9])|(15[0-9])|(17[0-9])|(18[0-9]))\\d{8}$");
    }

    /**
     * 获取手机IMEi号
     *
     * @return
     */
    public static String getImei(Context mContext) {
        String imei = ((TelephonyManager) mContext.getSystemService(Context.TELEPHONY_SERVICE)).getDeviceId();
        String android_id = Settings.Secure.getString(mContext.getContentResolver(), Settings.Secure.ANDROID_ID);
        if (!CommonUtil.isEmpty(imei)) {
            return imei;
        } else {
            return android_id;
        }
    }


    /**
     * 获取当前时间 格式 2012-12-21 12:12:12
     *
     * @return
     */
    public static String getCurrentDate() {
        SimpleDateFormat sDateFormat = new SimpleDateFormat(
                "yyyy-MM-dd HH:mm:ss");
        String date = sDateFormat.format(new Date());
        return date;
    }

    /**
     * toast提示
     *
     * @param mContext
     * @param text
     */
    public static void sendToast(Context mContext, String text) {
        Toast.makeText(mContext, text, Toast.LENGTH_SHORT).show();
    }

    /**
     * 检查是否有网络
     *
     * @param mContext
     * @return
     */
    public static boolean checkNetwork(Context mContext) {
        ConnectivityManager cwjManager = (ConnectivityManager) mContext
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo info = cwjManager.getActiveNetworkInfo();
        boolean flag = info != null && info.isAvailable() ? true : false;
        if (!flag) {
            CommonUtil.sendToast(mContext, "您的手机貌似还未连接网络,请检查网络连接是否正常");
        }
        return flag;
    }

    /**
     * 判断SD卡是否可用
     *
     * @return
     */
    public static boolean isSDCardExist() {
        return Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED);
    }

    /**
     * 获取外置SD卡路径
     *
     * @return
     */
    public static String getSDCardPath() {
        String cmd = "cat /proc/mounts";
        Runtime run = Runtime.getRuntime();// 返回与当�?Java 应用程序相关的运行时对象
        try {
            Process p = run.exec(cmd);// 启动另一个进程来执行命令
            BufferedInputStream in = new BufferedInputStream(p.getInputStream());
            BufferedReader inBr = new BufferedReader(new InputStreamReader(in));

            String lineStr;
            while ((lineStr = inBr.readLine()) != null) {
                // 获得命令执行后在控制台的输出信
                if (lineStr.contains("sdcard")
                        && lineStr.contains(".android_secure")) {
                    String[] strArray = lineStr.split(" ");
                    if (strArray != null && strArray.length >= 5) {
                        String result = strArray[1].replace("/.android_secure",
                                "");
                        return result;
                    }
                }
            }
            inBr.close();
            in.close();

        } catch (Exception e) {
            return Environment.getExternalStorageDirectory().getPath();
        }
        return Environment.getExternalStorageDirectory().getPath();
    }

    /**
     * 获取版本号
     *
     * @param context
     * @return
     */
    public static String getVersionName(Context context) {
        String versionName = "";
        try {
            PackageManager pm = context.getPackageManager();
            PackageInfo pi = pm.getPackageInfo(context.getPackageName(), 0);
            versionName = pi.versionName;
            if (versionName == null) {
                return "";
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return versionName;
    }

    /**
     * 判断给定字符串是否空白串。 空白串是指由空格、制表符、回车符、换行符组成的字符串 若输入字符串为null或空字符串，返回true
     *
     * @param input
     * @return boolean
     */
    public static boolean isEmpty(String input) {
        if (input == null || "".equals(input))
            return true;

        for (int i = 0; i < input.length(); i++) {
            char c = input.charAt(i);
            if (c != ' ' && c != '\t' && c != '\r' && c != '\n') {
                return false;
            }
        }
        return true;
    }

    /**
     * 判断给定的字符串是否符合微信号规则(6到20个字母，数字，下划线，和减号，必须以字母开头（不区分大小写）)
     *
     * @param wechat
     * @return
     */
    public static boolean isWechat(String wechat) {
        return wechat.matches("^[a-zA-Z][a-zA-Z0-9_-]{5,19}$");
    }

    //获得指定文件的byte数组
    public static byte[] getBytes(String filePath) {
        byte[] buffer = null;
        try {
            File file = new File(filePath);
            FileInputStream fis = new FileInputStream(file);
            ByteArrayOutputStream bos = new ByteArrayOutputStream(1000);
            byte[] b = new byte[1000];
            int n;
            while ((n = fis.read(b)) != -1) {
                bos.write(b, 0, n);
            }
            fis.close();
            bos.close();
            buffer = bos.toByteArray();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return buffer;
    }


    /**
     * encodeBase64File:(将文件转成base64 字符串). <br/>
     *
     * @param path 文件路径
     * @return
     * @throws Exception
     * @author guhaizhou@126.com
     * @since JDK 1.6
     */
    public static String encodeBase64File(String path) throws Exception {
        File file = new File(path);
        FileInputStream inputFile = new FileInputStream(file);
        byte[] buffer = new byte[(int) file.length()];
        inputFile.read(buffer);
        inputFile.close();
        return Base64.encodeToString(buffer, Base64.DEFAULT);
    }

    /**
     * decoderBase64File:(将base64字符解码保存文件). <br/>
     *
     * @param base64Code 编码后的字串
     * @param savePath   文件保存路径
     * @throws Exception
     * @author guhaizhou@126.com
     * @since JDK 1.6
     */
    public static void decoderBase64File(String base64Code, String savePath) throws Exception {
//byte[] buffer = new BASE64Decoder().decodeBuffer(base64Code);
        byte[] buffer = Base64.decode(base64Code, Base64.DEFAULT);
        FileOutputStream out = new FileOutputStream(savePath);
        out.write(buffer);
        out.close();
    }

}
