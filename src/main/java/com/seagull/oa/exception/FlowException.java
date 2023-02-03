package com.seagull.oa.exception;

/**
 * 流程异常
 */
public class FlowException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    /**
     * 构造函数
     */
    public FlowException(String message) {
        super(message);
    }
}
