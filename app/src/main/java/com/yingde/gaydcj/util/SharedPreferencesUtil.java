package com.yingde.gaydcj.util;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.util.Base64;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.StreamCorruptedException;

/**
 * 配置文件工具
 */
public class SharedPreferencesUtil {

    /**
     * 根据name获取保存到配置文件的字符串
     * @param fileName 文件名
     * @param context  上下文
     * @param key
     * @return
     */
    public static String getSetting(String fileName, Context context, String key) {
        SharedPreferences setting = context.getSharedPreferences(fileName,
                Activity.MODE_PRIVATE);
        return setting.getString(key, "");
    }
    /**
     * 根据name获取保存到配置文件的int值
     * @param fileName 文件名
     * @param context  上下文
     * @param key
     * @return
     */
    public static int getSettingInt(String fileName, Context context, String key) {
        SharedPreferences setting = context.getSharedPreferences(fileName,
                Activity.MODE_PRIVATE);
        return setting.getInt(key, 0);
    }

    /**
     * 根据name获取保存到配置文件的Float
     * @param fileName 文件名
     * @param context  上下文
     * @param key
     * @return
     */
    public static float getSettingFloat(String fileName, Context context, String key) {
        SharedPreferences setting = context.getSharedPreferences(fileName,
                Activity.MODE_PRIVATE);
        return setting.getFloat(key, 0.0f);
    }
    /**
     * 根据name获取保存到配置文件的Long
     * @param fileName 文件名
     * @param context  上下文
     * @param key
     * @return
     */
    public static long getSettingLong(String fileName, Context context, String key) {
        SharedPreferences setting = context.getSharedPreferences(fileName,
                Activity.MODE_PRIVATE);
        return setting.getLong(key, 0);
    }
    /**
     * 根据name获取保存到配置文件的Boolean
     * @param fileName 文件名
     * @param context  上下文
     * @param key
     * @return
     */
    public static boolean getSettingBoolean(String fileName, Context context, String key,
                                            boolean defaultVal) {
        SharedPreferences setting = context.getSharedPreferences(fileName,
                Activity.MODE_PRIVATE);
        return setting.getBoolean(key, defaultVal);
    }
    /**
     * 清除所有
     * @param fileName 文件名
     * @param context  上下文
     */
    public static void clearAll(String fileName, Context context) {
        SharedPreferences setting = context.getSharedPreferences(fileName,
                Activity.MODE_PRIVATE);
        SharedPreferences.Editor editor = setting.edit();
        editor.clear();
        editor.commit();
    }

    public static void removeSetting(String fileName, Context context, String key) {
        SharedPreferences setting = context.getSharedPreferences(fileName,
                Activity.MODE_PRIVATE);
        SharedPreferences.Editor editor = setting.edit();
        editor.remove(key);
        editor.commit();
    }

    public static String setSetting(String fileName, Context context, String key, String value) {
        SharedPreferences setting = context.getSharedPreferences(fileName,
                Activity.MODE_PRIVATE);
        SharedPreferences.Editor editor = setting.edit();
        editor.putString(key, value);
        editor.commit();
        return fileName;
    }

    public static void setSetting(String fileName, Context context, String key, int value) {
        SharedPreferences setting = context.getSharedPreferences(fileName,
                Activity.MODE_PRIVATE);
        SharedPreferences.Editor editor = setting.edit();
        editor.putInt(key, value);
        editor.commit();
    }

    public static void setSetting(String fileName, Context context, String key, float value) {
        SharedPreferences setting = context.getSharedPreferences(fileName,
                Activity.MODE_PRIVATE);
        SharedPreferences.Editor editor = setting.edit();
        editor.putFloat(key, value);
        editor.commit();
    }


    public static void setSetting(String fileName, Context context, String key, long value) {
        SharedPreferences setting = context.getSharedPreferences(fileName,
                Activity.MODE_PRIVATE);
        SharedPreferences.Editor editor = setting.edit();
        editor.putLong(key, value);
        editor.commit();
    }

    public static void setSetting(String fileName, Context context, String key, boolean value) {
        SharedPreferences setting = context.getSharedPreferences(fileName,
                Activity.MODE_PRIVATE);
        SharedPreferences.Editor editor = setting.edit();
        editor.putBoolean(key, value);
        editor.commit();
    }


    public static void setObject(String fileName, Context context, String key, Object object) {
        SharedPreferences sp = context.getSharedPreferences(fileName, Context.MODE_PRIVATE);

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ObjectOutputStream out = null;
        try {

            out = new ObjectOutputStream(baos);
            out.writeObject(object);
            String objectVal = new String(Base64.encode(baos.toByteArray(), Base64.DEFAULT));
            SharedPreferences.Editor editor = sp.edit();
            editor.putString(key, objectVal);
            editor.commit();

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (baos != null) {
                    baos.close();
                }
                if (out != null) {
                    out.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    //存对象
    public static  <T> T getObject(String fileName, Context context, String key, Class<T> clazz) {
        SharedPreferences sp = context.getSharedPreferences(fileName, Context.MODE_PRIVATE);
        if (sp.contains(key)) {
            String objectVal = sp.getString(key, null);
            byte[] buffer = Base64.decode(objectVal, Base64.DEFAULT);
            ByteArrayInputStream bais = new ByteArrayInputStream(buffer);
            ObjectInputStream ois = null;
            try {
                ois = new ObjectInputStream(bais);
                T t = (T) ois.readObject();
                return t;
            } catch (StreamCorruptedException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            } finally {
                try {
                    if (bais != null) {
                        bais.close();
                    }
                    if (ois != null) {
                        ois.close();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return null;
    }


}
