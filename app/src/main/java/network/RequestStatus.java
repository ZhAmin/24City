package network;

/**
 * 请求状态
 * @author 韬睿科技：李赞红
 *
 */
public enum RequestStatus {
	/**
	 * 请求成功
	 */
	SUCCESS,

	/**
	 * 请求失败
	 */
	FAILURE,

	/**
	 * 返回了不合法的JSON数据
	 */
	BAD_JSON
}
