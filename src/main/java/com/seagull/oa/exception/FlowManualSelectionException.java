package com.seagull.oa.exception;

/**
 * 流程手动选择异常
 */
public class FlowManualSelectionException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    /**
     * 构造函数
     */
    public FlowManualSelectionException(String message) {
        super(message);
    }
}
