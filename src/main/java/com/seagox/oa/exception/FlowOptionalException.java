package com.seagox.oa.exception;

/**
 * 流程审批自选异常
 */
public class FlowOptionalException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    /**
     * 构造函数
     */
    public FlowOptionalException(String message) {
        super(message);
    }
}
