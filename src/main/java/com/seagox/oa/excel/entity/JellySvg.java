package com.seagox.oa.excel.entity;

import com.baomidou.mybatisplus.annotation.KeySequence;

/**
 * svg数据
 */
@KeySequence(value = "jelly_svg_seq")
public class JellySvg {

    /**
     * id
     */
    private Long id;

    /**
     * 名称
     */
    private String name;

    /**
     * svg xml数据
     */
    private String content;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
