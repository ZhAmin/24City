package network;

import android.app.ProgressDialog;
import android.content.Context;
import android.util.Log;

import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;

/**
 * 处理请求结果，结果类型为String
 * <p>
 * 请求时，添加进度条
 *
 * @author 韬睿科技：李赞红
 *
 */
public class RequestHandler extends RequestCallBack<String> {
	private Context context;
	private ProgressDialog progressDialog;
	private OnResponseHandler responseHandler;
	private JSONValidator jsonValidator;

	public RequestHandler(Context context,
						  OnResponseHandler responseHandler) {
		super();
		this.context = context;
		this.responseHandler = responseHandler;
		jsonValidator = new JSONValidator();
	}

	@Override
	public void onStart() {
		super.onStart();
		openDialog();
	}

	/**
	 * 打开进度条对话框
	 */
	private void openDialog() {
		Log.d("===========", context+"");
		progressDialog = new ProgressDialog(context);
		progressDialog.setTitle("操作进行中");
		progressDialog.setMessage("操作正在进行，请稍候");
		progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);//环形
		progressDialog.show();
	}

	/**
	 * 关闭进度条对话框
	 */
	private void closeDialog(){
		if(progressDialog != null){
			progressDialog.dismiss();
		}
	}

	@Override
	public void onLoading(long total, long current, boolean isUploading) {
		super.onLoading(total, current, isUploading);
	}

	@Override
	public void onFailure(HttpException arg0, String arg1) {
		if(responseHandler != null){
			responseHandler.onResponse(null, RequestStatus.FAILURE);
		}
		closeDialog();
	}

	@Override
	public void onSuccess(ResponseInfo<String> responseInfo) {
		if(responseHandler != null){
			String result = responseInfo.result;
			//结果是否为有效的JSON
			if(jsonValidator.validate(result)){
				responseHandler.onResponse(result, RequestStatus.SUCCESS);
			}else{
				responseHandler.onResponse(null, RequestStatus.BAD_JSON);
			}
		}

		closeDialog();
	}

}
