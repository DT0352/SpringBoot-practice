package com.fuvidy.springsecurity.model;

import lombok.Data;

import java.io.Serializable;

/**
 * 说明 :实体基础类,包含共有属性
 */
@Data
public abstract class BaseEntity implements Serializable {


    private static final long serialVersionUID = 4034437877924885763L;
    protected Long id;//ID
    protected Integer state;//状态
    protected String creator;//创建人
    protected String createTime;//创建时间
    protected String updator;//修改人
    protected String updateTime;//修改时间
    protected Boolean deleted = false;//删除标志
}