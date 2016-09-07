package com.example.utils;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.NetworkInfo.State;
import android.telephony.TelephonyManager;

/**
 * 网络判断�?
* @Description: TODO(这里用一句话描述这个类的作用) 
* @author LiLong
* @date 2014-10-28 上午10:41:43 
* @UpdateData 2014-10-28 上午10:41:43 by_
 */

public class NetworkHelper
{
	public static Intent intentNetWork = null;

	
	/**
	 * 手机是否处在漫游
	 * 
	 * @param mCm
	 * @return boolean
	 */
	public boolean isNetworkRoaming(Context mCm) {
		ConnectivityManager connectivity = (ConnectivityManager) mCm.getSystemService(Context.CONNECTIVITY_SERVICE);
		if (connectivity == null) {
			return false;
		}
		NetworkInfo info = connectivity.getActiveNetworkInfo();
		boolean isMobile = (info != null && info.getType() == ConnectivityManager.TYPE_MOBILE);
		TelephonyManager mTm = (TelephonyManager) mCm.getSystemService(Context.TELEPHONY_SERVICE);
		boolean isRoaming = isMobile && mTm.isNetworkRoaming();
		return isRoaming;
	}
	
	/**
	 * 判断是否联网
	* @Description: TODO(这里用一句话描述这个方法的作�? 
	* @author LiLong
	* @param @param mContext
	* @param @return    
	* @return boolean
	* @UpdateData 2014-10-28 上午10:41:50 by_
	 */
	public static boolean isNetworkAvailable(Context mContext)
	{
		Context context = mContext.getApplicationContext();
		ConnectivityManager connectivity = (ConnectivityManager) context
				.getSystemService(Context.CONNECTIVITY_SERVICE);

		if (connectivity == null)
		{
			return false;
		} else
		{
			NetworkInfo[] info = connectivity.getAllNetworkInfo();
			if (info != null)
			{
				for (int i = 0; i < info.length; i++)
				{
					if (info[i].getState() == State.CONNECTED)
					{
						return true;
					}
				}
			}
		}

		return false;
	}

	/**
	 * 判断是否为WiFi
	* @Description: TODO(这里用一句话描述这个方法的作�? 
	* @author LiLong
	* @param @param mContext
	* @param @return    
	* @return boolean
	* @UpdateData 2014-10-28 上午10:41:58 by_
	 */
	public static boolean isWifiAvailable(Context mContext)
	{

		Context context = mContext.getApplicationContext();

		ConnectivityManager connectivity = (ConnectivityManager) context
				.getSystemService(Context.CONNECTIVITY_SERVICE);


		State activeNetInfo = connectivity.getNetworkInfo(
				ConnectivityManager.TYPE_WIFI).getState(); 
		if (activeNetInfo == State.CONNECTED
				|| activeNetInfo == State.CONNECTING)
		{
			return true;
		} else
		{
			return false;
		}
	}

	/**
	 * 判断是否�?g
	* @Description: TODO(这里用一句话描述这个方法的作�? 
	* @author LiLong
	* @param @param mContext
	* @param @return    
	* @return boolean
	* @UpdateData 2014-10-28 上午10:42:11 by_
	 */
	public static boolean is3GAvailable(Context mContext)
	{

		Context context = mContext.getApplicationContext();

		ConnectivityManager connectivity = (ConnectivityManager) context
				.getSystemService(Context.CONNECTIVITY_SERVICE);

		State mobNetInfo = connectivity.getNetworkInfo(
				ConnectivityManager.TYPE_MOBILE).getState(); // 3G

		if (mobNetInfo == State.CONNECTED || mobNetInfo == State.CONNECTING)
		{
			return true;
		} else
		{
			return false;
		}

	}

	/**
	* @Description: 打开网络设置
	* @author LiLong
	* @param @param context    
	* @return void
	* @UpdateData 2014-10-28 上午10:42:24 by_
	 */
	public static void isNetworKSeting(final Context context)
	{

		Builder builder = new Builder(context);
		builder.setTitle("温馨提示");
		builder.setMessage("网络出现异常");

		builder.setPositiveButton("前往设置", new DialogInterface.OnClickListener()
		{

			public void onClick(DialogInterface dialog, int which)
			{

				if (android.os.Build.VERSION.SDK_INT > 10)
				{
					intentNetWork = new Intent(
							android.provider.Settings.ACTION_WIRELESS_SETTINGS);
				} else
				{
					intentNetWork = new Intent();
					ComponentName component = new ComponentName(
							"com.android.settings",
							"com.android.settings.WirelessSettings");
					intentNetWork.setComponent(component);
					intentNetWork.setAction("android.intent.action.VIEW");
				}
				((Activity) context).startActivity(intentNetWork);

				dialog.dismiss();
			}
		});
		builder.setNegativeButton("取消", new DialogInterface.OnClickListener()
		{
			public void onClick(DialogInterface dialoginterface, int i)
			{

			}
		});

		builder.show();
	}

	public static void settingApn(Context mContext)
	{
		if (android.os.Build.VERSION.SDK_INT > 10)
		{
			intentNetWork = new Intent(
					android.provider.Settings.ACTION_WIRELESS_SETTINGS);
		} else
		{
			intentNetWork = new Intent();
			ComponentName component = new ComponentName("com.android.settings",
					"com.android.settings.WirelessSettings");
			intentNetWork.setComponent(component);
			intentNetWork.setAction("android.intent.action.VIEW");
		}
		mContext.startActivity(intentNetWork);
	}
	
	 public static boolean isEmpty(CharSequence str) {
	        if (str == null || str.length() == 0)
	            return true;
	        else
	            return false;
	    }

	public static boolean isNumberString(String num)
	{
		// TODO Auto-generated method stub
		String telRegex = "[0-9]+";
        if (isEmpty(num)) {
            return false;
        } else {
            return num.matches(telRegex);
        }
	}
}
