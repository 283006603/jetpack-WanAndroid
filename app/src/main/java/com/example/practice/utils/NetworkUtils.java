package com.example.practice.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Enumeration;

public class NetworkUtils{
	public static boolean isConnectivityActive(Context context) {


		NetworkInfo networkinfo = ((ConnectivityManager) context
				.getSystemService(Context.CONNECTIVITY_SERVICE))
				.getActiveNetworkInfo();
		return networkinfo != null && networkinfo.isConnected();
	}

	public static boolean isMobileConnected(Context context) {
		NetworkInfo networkinfo = ((ConnectivityManager) context
				.getSystemService(Context.CONNECTIVITY_SERVICE))
				.getActiveNetworkInfo();
		return networkinfo != null
				&& networkinfo.getType() == ConnectivityManager.TYPE_MOBILE
				&& networkinfo.isConnected();
	}

	public static boolean isWifiConnected(Context context) {
		NetworkInfo networkinfo = ((ConnectivityManager) context
				.getSystemService(Context.CONNECTIVITY_SERVICE))
				.getActiveNetworkInfo();
		return networkinfo != null
				&& networkinfo.getType() == ConnectivityManager.TYPE_WIFI
				&& networkinfo.isConnected();
	}

	/**
	 * 获取本机ip
	 */
	public static String getLocalIpAddress() {
		try {
			for (Enumeration<NetworkInterface> en = NetworkInterface
					.getNetworkInterfaces(); en.hasMoreElements();) {
				NetworkInterface intf = en.nextElement();
				for (Enumeration<InetAddress> enumIpAddr = intf
						.getInetAddresses(); enumIpAddr.hasMoreElements();) {
					InetAddress inetAddress = enumIpAddr.nextElement();
					if (!inetAddress.isLoopbackAddress()) {
						return inetAddress.getHostAddress().toString();
					}
				}
			}
		} catch (SocketException ex) {
//			Log.e("WifiPreference IpAddress", ex.toString());
		}
		return null;
	}

//	// MD5加密
//	public static String sign(String... args) {
//
//		String sign = "^$*computeroutputmicrofilm!)(*#1231";
//
//		for (String s : args) {
//			sign += s;
//		}
//
//		return MD5.getMessageDigest(sign.toString().getBytes());
//	}

	public static boolean isNetworkOnline() {

		Runtime runtime = Runtime.getRuntime();
		Process ipProcess = null;
		try {
			ipProcess = runtime.exec("ping -c 5 -w 4 223.5.5.5");
			InputStream input = ipProcess.getInputStream();

			BufferedReader in = new BufferedReader(new InputStreamReader(input));
			StringBuffer stringBuffer = new StringBuffer();
			String content = "";
			while ((content = in.readLine()) != null) {
				stringBuffer.append(content);
			}

			int exitValue = ipProcess.waitFor();
			if (exitValue == 0) {
				//WiFi连接，网络正常
				return true;
			} else {
				if (stringBuffer.indexOf("100% packet loss") != -1) {
					//网络丢包严重，判断为网络未连接
					return false;
				} else {
					//网络未丢包，判断为网络连接
					return true;
				}
			}
		} catch (IOException | InterruptedException e) {
			e.printStackTrace();
		} finally {
			if (ipProcess != null) {
				ipProcess.destroy();
			}
			runtime.gc();
		}
		return false;
	}

}
