/**
 * 
 */
package com.gnsmind.springBoot.exception;

/**
 * @author NET
 *
 */
public class ErrorObject {

	private String errMsg;
	private String errCode;
	private String errType;
	private static ErrorObject errorObj;

	public ErrorObject() {
	}

	/**
	 * @return the errMsg
	 */
	public String getErrMsg() {
		return errMsg;
	}

	/**
	 * @param errMsg
	 *            the errMsg to set
	 */
	public void setErrMsg(String errMsg) {
		this.errMsg = errMsg;
	}

	/**
	 * @return the errCode
	 */
	public String getErrCode() {
		return errCode;
	}

	/**
	 * @param errCode
	 *            the errCode to set
	 */
	public void setErrCode(String errCode) {
		this.errCode = errCode;
	}

	/**
	 * @return the errType
	 */
	public String getErrType() {
		return errType;
	}

	/**
	 * @param errType
	 *            the errType to set
	 */
	public void setErrType(String errType) {
		this.errType = errType;
	}

	public static ErrorObject getErrorObj() {

		synchronized (ErrorObject.class) {
			if (errorObj == null) {
				errorObj = new ErrorObject();// instance will be created at request time
			}
		}
		return errorObj;
	}

}
