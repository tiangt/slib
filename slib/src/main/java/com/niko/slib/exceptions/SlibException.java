package com.niko.slib.exceptions;

/**
 * Created by scanor on 2016/6/25.
 */

public class SlibException extends Exception {
    /**
     * 错误类型，volley错误和服务器错误为0， 其他为自定义
     */
    private int mErrorType;

    /**
     * 错误提示信息，供自定义错误使用
     */
    private String mErrorMsg;

    public SlibException(Exception e, int errorType, String errorMsg) {
        super(e);
        mErrorType = errorType;
        mErrorMsg = errorMsg;
    }

    public String getErrorMsg() {
        return mErrorMsg;
    }

    public void setErrorMsg(String errorMsg) {
        mErrorMsg = errorMsg;
    }

    public int getErrorType() {
        return mErrorType;
    }

    public void setErrorType(int errorType) {
        mErrorType = errorType;
    }
}
