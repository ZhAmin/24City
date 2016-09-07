package network;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.util.Map;
import java.util.Set;

import org.apache.http.*;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.BasicCookieStore;
import org.apache.http.impl.cookie.BasicClientCookie;


import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.http.client.HttpRequest.HttpMethod;

/**
 * 包装Http请求操作
 *
 * @author 韬睿科技：李赞红
 *
 */
public class HttpRequestWrap {
	public static final String GET = "get";
	public static final String POST = "post";

	private HttpUtils httpUtils;
	private Context context;
	private RequestHandler requestHandler;

	/**
	 * 请求类型
	 */
	private HttpMethod method;

	/**
	 * 没有Context，默认是GET请求
	 */
	public HttpRequestWrap() {
		this(null, GET);
	}

	/**
	 * 默认是Get请求的构造方法
	 *
	 * @param context
	 */
	public HttpRequestWrap(Context context) {
		this(context, GET);
//		setCookie();
	}

	/**
	 * 构造方法
	 */
	public HttpRequestWrap(Context context, String method) {
		this.context = context;
		resetMethod(method);
		httpUtils = new HttpUtils();
	}

	public void setContext(Context context) {
		this.context = context;
	}

	/**
	 * 更改请求类型
	 *
	 * @param method
	 */
	public void setMethod(String method) {
		resetMethod(method);
	}

	/**
	 * 定义请求处理器
	 */
	public void setCallBack(RequestHandler requestHandler) {
		this.requestHandler = requestHandler;
	}

	/**
	 * 获取HttpUtils对象
	 *
	 * @return
	 */
	public HttpUtils getHttpUtils() {
		return httpUtils;
	}

	/**
	 * 将SessionID传送到服务器
	 */
	private void setCookie() {
		if (context != null) {
			// 获得SessionID
			SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(context);

			String session = sp.getString("session", "");
			if (!TextUtils.isEmpty(session)) {
				Log.d("HttpRequest", session);
				// 创建BasicClientCookie
				BasicClientCookie cc = new BasicClientCookie("JSESSIONID",
						session);
				cc.setVersion(0);
				cc.setDomain("tr.zzapi.gson.cn");
				cc.setPath("/");

				// 创建CookieStore
				BasicCookieStore cs = new BasicCookieStore();
				cs.addCookie(cc);
				Log.d("---------", "3");
				// 配置httpUtils
				httpUtils.configCookieStore(cs);
			}
		}
	}

	/**
	 * 转换请求类型
	 *
	 * @param method
	 */
	private void resetMethod(String method) {
		if (GET.equals(method)) {
			this.method = HttpMethod.GET;
		} else if (POST.equals(method)) {
			this.method = HttpMethod.POST;
		} else {
			throw new IllegalArgumentException("只支持get和post请求");
		}
	}

	/**
	 * 发送请求
	 *
	 * @param url
	 *            处理请求的URL
	 * @param params
	 *            参数列表
	 */
	public void send(String url, Map<String, Object> params) {
		Log.d("URL", url);
//		setCookie();JSON.toJSON(params)
//		httpUtils.send(method, url, JSON.toJSON(params), requestHandler);
		httpUtils.send(method, url, tranlateParams(params), requestHandler);
	}
	public void sendk(String url) {
		Log.d("URL", url);
		httpUtils.send(method, url,null, requestHandler);
	}
	public void login(String url, Map<String, Object> params) {
		Log.d("URL", url);
		httpUtils.send(method, url, tranlateParams(params), requestHandler);
	}

	/**
	 * 发送请求
	 *
	 * @param url
	 *            处理请求的URL
	 */
	public void send(String url) {
		send(url, null);
	}

	/**
	 * 将Map参数转换成为RequestParams参数
	 *
	 * @param map
	 * @return
	 */
	private RequestParams tranlateParams(Map<String, Object> map) {
		RequestParams params = new RequestParams();
		params.addHeader("Content-Type", "application/json");
		if (map == null){

//				Toast.makeText(context, "没有参数", Toast.LENGTH_LONG).show();
			return params;
		}else{
			try
			{

				params.setBodyEntity(new StringEntity(JSON.toJSONString(map), "UTF-8"));
			} catch (UnsupportedEncodingException e)
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return params;
	}
}
