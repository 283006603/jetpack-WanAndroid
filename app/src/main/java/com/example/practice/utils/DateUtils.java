package com.example.practice.utils;

import android.util.Log;

import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class DateUtils{


    private final static ThreadLocal<SimpleDateFormat> dateFormaterYyMmDdd = new ThreadLocal<SimpleDateFormat>() {
        @Override
        protected SimpleDateFormat initialValue() {
            return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        }
    };

    /**
     * date类型进行格式化输出
     *
     * @param date
     * @return
     */
    public static String dateFormat(Date date) {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String dateString = formatter.format(date);
        return dateString;
    }

    /**
     * date类型进行格式化输出
     *
     * @param date
     * @return
     */
    public static String dateFormat2(Date date) {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy年MM月dd日");
        String dateString = formatter.format(date);
        return dateString;
    }

    /**
     * 将"2015-08-31 21:08:06"型字符串转化为Date
     *
     * @param str
     * @return
     * @throws ParseException
     */
    public static Date StringToDate(String str) throws ParseException {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = (Date) formatter.parse(str);
        return date;
    }

    /**
     * 将CST时间类型字符串进行格式化输出
     *
     * @param str
     * @return
     * @throws ParseException
     */
    public static String CSTFormat(String str) throws ParseException {
        SimpleDateFormat formatter = new SimpleDateFormat("EEE MMM dd HH:mm:ss zzz yyyy", Locale.US);
        Date date = (Date) formatter.parse(str);
        return dateFormat(date);
    }


    /**
     * 将long类型转化为Date
     *
     * @param str
     * @return
     * @throws ParseException
     */
    public static Date LongToDare(long str) throws ParseException {
        return new Date(str * 1000);
    }

    /**
     * 将格林威治时间字符串转换为yyyy-MM-dd HH:mm:ss字符串，JDK1.7以上版本支持该方法
     *
     * @param s
     * @return
     */
    public static String DateString2formatString(String s) {
        String str = "";
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy年MM月dd日");
            SimpleDateFormat sd = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
            Date date = sd.parse(s);
            str = sdf.format(date);
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println(e.getMessage());
            return str;
        }
        return str;
    }


    /**
     * 将格林威治时间字符串转换为yyyy-MM-dd HH:mm:ss字符串，JDK1.7以上版本支持该方法
     *
     * @param s
     * @return
     */
    public static String DateStringTformatString(String s) {
        String str = "";
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy年MM月dd日");
            SimpleDateFormat sd = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Date date = sd.parse(s);
            str = sdf.format(date);
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println(e.getMessage());
            return str;
        }
        return str;
    }

    /**
     * 将格林威治时间字符串转换为yyyy-MM-dd HH:mm:ss字符串，JDK1.7以上版本支持该方法
     *
     * @param s
     * @return
     */
    public static String DateStringT3formatString(String s) {
        String str = "";
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
            SimpleDateFormat sd = new SimpleDateFormat("yyyy-MM-dd");
            Date date = sd.parse(s);
            str = sdf.format(date);
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println(e.getMessage());
            return str;
        }
        return str;
    }


    /**
     * 将格林威治时间字符串转换为yyyy-MM-dd HH:mm:ss字符串，JDK1.7以上版本支持该方法
     *
     * @param s
     * @return
     */
    public static String DateString12formatString(String s) {
        String str = "";
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy年MM月dd日 HH:mm");
            SimpleDateFormat sd = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
            Date date = sd.parse(s);
            str = sdf.format(date);
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println(e.getMessage());
            return str;
        }
        return str;
    }

    /**
     * 将格林威治时间字符串转换为yyyy-MM-dd HH:mm:ss字符串，JDK1.7以上版本支持该方法
     *
     * @param s
     * @return
     */
    public static String DateString15formatString(String s) {
        String str = "";
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
            SimpleDateFormat sd = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
            Date date = sd.parse(s);
            str = sdf.format(date);
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println(e.getMessage());
            return str;
        }
        return str;
    }

    /**
     * 将格林威治时间字符串转换为yyyy-MM-dd HH:mm:ss字符串，JDK1.7以上版本支持该方法
     *
     * @param s
     * @return
     */
    public static String DateString4formatString(String s) {
        String str = "";
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy年MM月dd日");
            SimpleDateFormat sd = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
            Date date = sd.parse(s);
            str = sdf.format(date);
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println(e.getMessage());
            return str;
        }
        return str;
    }

    /**
     * 将yyyy年MM月dd日 转换为yyyy/MM/dd
     *
     * @param s
     * @return
     */
    public static String DateString7formatString(String s) {
        String str = "";
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
            SimpleDateFormat sd = new SimpleDateFormat("yyyy年MM月dd日");
            Date date = sd.parse(s);
            str = sdf.format(date);
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println(e.getMessage());
            return str;
        }
        return str;
    }

    /**
     * yyyy/MM/dd将转换为yyyy年MM月dd日
     *
     * @param s
     * @return
     */
    public static String DateString8formatString(String s) {
        String str = "";
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy年MM月dd日");
            SimpleDateFormat sd = new SimpleDateFormat("yyyy-MM-dd");
            Date date = sd.parse(s);
            str = sdf.format(date);
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println(e.getMessage());
            return str;
        }
        return str;
    }


    /**
     * 将格林威治时间字符串转换为yyyy-MM-dd HH:mm:ss字符串，JDK1.7以上版本支持该方法
     *
     * @return
     */
    public static String DateString9formatString(String createTime) {

        String formatStr2 = null;
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");//注意格式化的表达式
        try {
            Date time = format.parse(createTime);
            String date = time.toString();
            //将西方形式的日期字符串转换成java.util.Date对象
            SimpleDateFormat sdf = new SimpleDateFormat("EEE MMM dd HH:mm:ss zzz yyyy", Locale.US);
            Date datetime = (Date) sdf.parse(date);
            //再转换成自己想要显示的格式
            formatStr2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(datetime);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return formatStr2;
    }

    /**
     * GMT(格林威治标准时间)转换当前北京时间
     * 比如：1526217409 -->2018/5/13 21:16:49 与北京时间相差8个小时，调用下面的方法，是在1526217409加上8*3600秒
     *
     * @return
     */
    public static String stampToDate(String createTime) {

        /**
         * GMT(格林威治标准时间)转换当前北京时间
         * 比如：1526217409 -->2018/5/13 21:16:49 与北京时间相差8个小时，调用下面的方法，是在1526217409加上8*3600秒
         * @param GMT 秒单部位
         * @return
         */

        long lt = Long.parseLong(createTime) + 8 * 3600;
        String res = null;
        try {
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy年MM月dd日 HH:mm");

            res = simpleDateFormat.format(lt * 1000);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return res;
    }

    /**
     * GMT(格林威治标准时间)转换当前北京时间
     * 比如：1526217409 -->2018/5/13 21:16:49 与北京时间相差8个小时，调用下面的方法，是在1526217409加上8*3600秒
     *
     * @return
     */
    public static String stampToDate1(String createTime) {

        /**
         * GMT(格林威治标准时间)转换当前北京时间
         * 比如：1526217409 -->2018/5/13 21:16:49 与北京时间相差8个小时，调用下面的方法，是在1526217409加上8*3600秒
         * @param GMT 秒单部位
         * @return
         */

        long lt = Long.parseLong(createTime) / 1000;
        String res = null;
        try {
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy年MM月dd日 HH:mm");

            res = simpleDateFormat.format(lt * 1000);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return res;
    }


    /**
     * 将格林威治时间字符串转换为yyyy-MM-dd HH:mm:ss字符串，JDK1.7以上版本支持该方法
     *
     * @param s
     * @return
     */
    public static String DateString10formatString(String s) {
        String str = "";
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy年MM月dd日 HH:mm");
            SimpleDateFormat sd = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");

            Date date = sd.parse(s);
            str = sdf.format(date);
            Date time = sdf.parse(str);
            long Timelog = time.getTime() / 1000;
            str = stampToDate(String.valueOf(Timelog));
        } catch (Exception e) {
            e.printStackTrace();

            return str;
        }
        return str;
    }

    /**
     * 将格林威治时间字符串转换为yyyy-MM-dd HH:mm:ss字符串，JDK1.7以上版本支持该方法
     *
     * @param s
     * @return
     */
    public static String DateString5formatString(String s) {
        String str = "";
        try {
            str = s.replaceAll("\\p{InCJK Unified Ideographs}+", "-");
            str = str.substring(0, str.lastIndexOf("-"));
            Log.e("DateUtils","查询处理结果：" + str);
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println(e.getMessage());
            return str;
        }
        return str;
    }

    /**
     * 将格林威治时间字符串转换为yyyy-MM-dd HH:mm:ss字符串，JDK1.7以上版本支持该方法
     *
     * @param s
     * @return
     */
    public static String DateString6formatString(String s) {
        String str = "";
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            SimpleDateFormat sd = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Date date = sd.parse(s);
            str = sdf.format(date);
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println(e.getMessage());
            return str;
        }
        return str;
    }


    /**
     * 将格林威治时间字符串转换为yyyy-MM-dd HH:mm:ss字符串，JDK1.7以上版本支持该方法
     *
     * @param s
     * @return
     */
    public static String DateString14formatString(String s) {
        String str = "";
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            SimpleDateFormat sd = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Date date = sd.parse(s);
            str = sdf.format(date);
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println(e.getMessage());
            return str;
        }
        return str;
    }

    /**
     * 将格林威治时间字符串转换为yyyy-MM-dd HH:mm:ss字符串，JDK1.7以上版本支持该方法
     *
     * @param s
     * @return
     */
    public static String DateString13formatString(String s) {
        String str = "";
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
            SimpleDateFormat sd = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Date date = sd.parse(s);
            str = sdf.format(date);
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println(e.getMessage());
            return str;
        }
        return str;
    }

    /**
     * 将格林威治时间字符串转换为yyyy-MM-dd HH:mm:ss字符串，JDK1.7以上版本支持该方法
     *
     * @param s
     * @return
     */
    public static String DateStringT2formatString(String s) {
        String str = "";
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy.MM");
            SimpleDateFormat sd = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Date date = sd.parse(s);
            str = sdf.format(date);
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println(e.getMessage());
            return str;
        }
        return str;
    }

    /**
     * 将格林威治时间字符串转换为yyyy.MM.dd 字符串，JDK1.7以上版本支持该方法
     *
     * @param s
     * @return
     */
    public static String DateString3formatString(String s) {
        String str = "";
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy.MM.dd");
            SimpleDateFormat sd = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
            Date date = sd.parse(s);
            str = sdf.format(date);
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println(e.getMessage());
            return str;
        }
        return str;
    }

    /**
     * 将格林威治时间字符串转换为yyyy.MM.dd 字符串，JDK1.7以上版本支持该方法
     * 去T
     *
     * @param s
     * @return
     */
    public static String DateString11formatString(String s) {
        String str = "";
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            SimpleDateFormat sd = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
            Date date = sd.parse(s);
            str = sdf.format(date);
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println(e.getMessage());
            return str;
        }
        return str;
    }

    /**
     * 比较两个日期的大小，日期格式为yyyy-MM-dd
     *
     * @param str1 the first date
     * @param str2 the second date
     * @return true <br/>false
     */
    public static boolean isDateOneBigger(String str1, String str2) {
        boolean isBigger = false;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date dt1 = null;
        Date dt2 = null;
        try {
            dt1 = sdf.parse(str1);
            dt2 = sdf.parse(str2);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        if (dt1.getTime() > dt2.getTime()) {
            isBigger = true;
        } else if (dt1.getTime() < dt2.getTime()) {
            isBigger = false;
        }
        return isBigger;
    }


    /**
     * 判断2个时间大小
     * yyyy-MM-dd HH:mm 格式（自己可以修改成想要的时间格式）
     *
     * @param startTime
     * @param endTime
     * @return
     */
    public static int getTimeCompareSize(String startTime, String endTime) {
        int i = 0;
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");//年-月-日 时-分
        try {
            Date date1 = dateFormat.parse(startTime);//开始时间
            Date date2 = dateFormat.parse(endTime);//结束时间
            // 1 结束时间小于开始时间 2 开始时间与结束时间相同 3 结束时间大于开始时间
            if (date2.getTime() < date1.getTime()) {
                i = 1;
            } else if (date2.getTime() == date1.getTime()) {
                i = 2;
            } else if (date2.getTime() > date1.getTime()) {
                //正常情况下的逻辑操作.
                i = 3;
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return i;
    }


    public static String getPresentTime() throws ParseException {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        Date curDate = new Date(System.currentTimeMillis());
        String str = formatter.format(curDate);
        return str;

    }

    //返回当前时间往后几个月时间
    //yyyy/MM/dd HH:mm:ss
    public static String getNextPresentTime(int nexmonth) throws ParseException {
        SimpleDateFormat formatter = new SimpleDateFormat("HH:mm:ss");

        Date curDate = new Date(System.currentTimeMillis());
        String str = formatter.format(curDate);

        SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy", Locale.getDefault());
        SimpleDateFormat sdf = new SimpleDateFormat("MM", Locale.getDefault());
        SimpleDateFormat sdf2 = new SimpleDateFormat("dd", Locale.getDefault());
        String year = sdf1.format(new Date());
        int month = Integer.parseInt(sdf.format(new Date()));
        String day = sdf2.format(new Date());
        StringBuffer buffer = new StringBuffer();
        buffer.append(year);
        buffer.append("-");
        int add = month + nexmonth;
        if (add < 10) {
            buffer.append("0");
        }
        buffer.append(add);
        buffer.append("-");
        buffer.append(day);
        buffer.append(" ");
        buffer.append(str);
        return buffer.toString();

    }

    /*
     * 将时间转换为时间戳
     */
    public static String dateToStamp(String time) throws ParseException {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = simpleDateFormat.parse(time);
        long ts = date.getTime();
        return String.valueOf(ts);
    }

    /*
     * 将时间转换为时间戳
     */
    public static String dateToStamp2(String time) throws ParseException {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        Date date = simpleDateFormat.parse(time);
        long ts = date.getTime();
        return String.valueOf(ts);
    }

    /**
     * 毫秒转 mm：ss
     * @return
     */
    public static String secondToms(long second)
    {
        String ss= new DecimalFormat("00").format(second/1000 %  60);
        String mm=new DecimalFormat("00").format(second/1000 / 60);

        return mm + ":" + ss;
    }

}
