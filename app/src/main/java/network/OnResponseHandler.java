package network;

/**
 * 响应处理器
 * @author 韬睿科技：李赞红
 *
 */
public interface OnResponseHandler {

	/**
	 * 获得响应结果
	 * @param result 响应内容
	 * @param status 状态
	 */
	void onResponse(String result, RequestStatus status);
}
