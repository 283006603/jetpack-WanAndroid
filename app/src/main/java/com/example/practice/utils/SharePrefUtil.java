package com.example.practice.utils;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.List;

/**
 * SharePreferences操作工具类
 */
public class SharePrefUtil{
    private static String tag = SharePrefUtil.class.getSimpleName();
    private final static String SP_NAME = "config";
    private static SharedPreferences sp;
    private static Gson gson = new Gson();
/*
    public interface KEY {
        String ClickCell = "clickCell";
        String IsAutoLogin = "isAutoLogin";
        String Identity = "identity";        //用户true，技术员false;
        String TokenKey = "token";
        String USERREGTIME = "userRegTime";
        String COMPANYREGTIME = "companyRegTime";
        String PERSONREGTIME = "personRegTime";
        String FINDPASSWORDTIME = "findpasswordTime";
        String SEARCHLIST = "searchList";
        String SECONDHANDSEARCH = "secondhandSearch";
        String LASTVERSION = "lastVersion";
        String ISFIRSTUSE = "isFirstUse";
        String CATEGORYUPDATETIME = "categoryUpdateTime";
        String CATEGORYLIST = "categoryList";
        String USERICONURL = "userIconUrl";
        String USERNAME = "userName";
        String MOBILE = "mobile";
        String PUSHID = "pushId";
        String USERISSETPUSH = "userIsSetPush";
        String TECHISSETPUSH = "techIsSetPush";
        String ISPUSH = "isPush";
        String LATLONG = "nearLatLong";
        String ONOROFFLINE = "onOrOffLine";
        String PHOTOSDATA = "photosData";
        String CLOCKINDEX = "clockIndex";
    }
*/

    public static SharedPreferences sharedPreferences = null;
    public static String fileName = "mySharedPreferences";

    /**
     * 保存布尔值
     *
     * @param context
     * @param key
     * @param value
     */
    public static void saveBoolean(Context context, String key, boolean value) {
        if (sp == null) {
            sp = context.getSharedPreferences(SP_NAME, 0);
        }
        sp.edit().putBoolean(key, value).commit();
    }

    /**
     * 保存字符串
     *
     * @param context
     * @param key
     * @param value
     */
    public static void saveString(Context context, String key, String value) {
        if (sp == null) {
            sp = context.getSharedPreferences(SP_NAME, 0);
        }
        sp.edit().putString(key, value).commit();

    }

    /**
     * 清除所有的缓存
     *
     * @param context
     */
    public static void clear(Context context) {
        if (sp == null) {
            sp = context.getSharedPreferences(SP_NAME, 0);
        }
        sp.edit().clear().commit();
    }

    /**
     * 保存long型
     *
     * @param context
     * @param key
     * @param value
     */
    public static void saveLong(Context context, String key, long value) {
        if (sp == null) {
            sp = context.getSharedPreferences(SP_NAME, 0);
        }
        sp.edit().putLong(key, value).commit();
    }

    /**
     * 保存int型
     *
     * @param context
     * @param key
     * @param value
     */
    public static void saveInt(Context context, String key, int value) {
        if (sp == null) {
            sp = context.getSharedPreferences(SP_NAME, 0);
        }
        sp.edit().putInt(key, value).commit();
    }

    /**
     * 保存float型
     *
     * @param context
     * @param key
     * @param value
     */
    public static void saveFloat(Context context, String key, float value) {
        if (sp == null) {
            sp = context.getSharedPreferences(SP_NAME, 0);
        }
        sp.edit().putFloat(key, value).commit();
    }

    /**
     * 获取字符值
     *
     * @param context
     * @param key
     * @param defValue
     * @return
     */
    public static String getString(Context context, String key, String defValue) {
        if (sp == null) {
            sp = context.getSharedPreferences(SP_NAME, 0);
        }
        return sp.getString(key, defValue);
    }

    /**
     * 获取int值
     *
     * @param context
     * @param key
     * @param defValue
     * @return
     */
    public static int getInt(Context context, String key, int defValue) {
        if (sp == null) {
            sp = context.getSharedPreferences(SP_NAME, 0);
        }
        return sp.getInt(key, defValue);
    }

    /**
     * 获取long值
     *
     * @param context
     * @param key
     * @param defValue
     * @return
     */
    public static long getLong(Context context, String key, long defValue) {
        if (sp == null) {
            sp = context.getSharedPreferences(SP_NAME, 0);
        }
        return sp.getLong(key, defValue);
    }

    /**
     * 获取float值
     *
     * @param context
     * @param key
     * @param defValue
     * @return
     */
    public static float getFloat(Context context, String key, float defValue) {
        if (sp == null) {
            sp = context.getSharedPreferences(SP_NAME, 0);
        }
        return sp.getFloat(key, defValue);
    }

    /**
     * 获取布尔值
     *
     * @param context
     * @param key
     * @param defValue
     * @return
     */
    public static boolean getBoolean(Context context, String key,
                                     boolean defValue) {
        if (sp == null) {
            sp = context.getSharedPreferences(SP_NAME, 0);
        }
        return sp.getBoolean(key, defValue);
    }

    /**
     * 取出String
     */
    public static String getValueByKey(Context context, String key) {
        return getSharedPreferences(context).getString(key, "");
    }

    public static void saveObj(Context context, String key, Object object) {
        saveString(context, key, new Gson().toJson(object));
    }

    public static <T> T getObject(Context context, String key, Class<T> type) {
        return new Gson().fromJson(getString(context, key, ""), type);
    }


    /**
     * 设置String
     */
    public static void setValueByKey(Context context, String key, String value) {
        getSharedPreferences(context).edit().putString(key, value).commit();
    }

    public static SharedPreferences getSharedPreferences(Context context) {
        if (sharedPreferences == null) {
            sharedPreferences = context.getSharedPreferences(fileName,
                    Context.MODE_PRIVATE);
        }
        return sharedPreferences;
    }

    public static <T> void saveListValue(Context context, String key, List<T> dataList) {
        if (null == dataList || dataList.size() <= 0) {
            return;
        }
        sp = context.getSharedPreferences(SP_NAME, 0);
        sp.edit().clear();
        //转换成json数据，再保存
        String strJson = gson.toJson(dataList);
        sp.edit().putString(key, strJson).commit();
    }

    public static <T> List<T> getListValue(Context context, String key) {
        sp = context.getSharedPreferences(SP_NAME, 0);
        List<T> datalist = new ArrayList<T>();
        String strJson = sp.getString(key, null);
        if (null == strJson) {
            return datalist;
        }
        datalist = gson.fromJson(strJson, new TypeToken<List<T>>() {
        }.getType());
        return datalist;
    }

}
