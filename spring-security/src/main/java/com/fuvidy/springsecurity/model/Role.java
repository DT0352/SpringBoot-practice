package com.fuvidy.springsecurity.model;

import lombok.Data;

@Data
public class Role extends BaseEntity {

    private static final long serialVersionUID = -3572463217368803762L;
    private String roleName;// 角色名称
}
