package com.seagox.oa.util;

public class VerifyHandlerResult {
	
	/**
     * 是否正确
     */
    private boolean success;
    /**
     * 错误信息
     */
    private String  msg;

    public VerifyHandlerResult() {

    }

    public VerifyHandlerResult(boolean success) {
        this.success = success;
    }

    public VerifyHandlerResult(boolean success, String msg) {
        this.success = success;
        this.msg = msg;
    }

	public boolean isSuccess() {
		return success;
	}

	public void setSuccess(boolean success) {
		this.success = success;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}
    
}
