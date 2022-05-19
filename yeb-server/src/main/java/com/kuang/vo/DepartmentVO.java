package com.kuang.vo;

import lombok.Data;

@Data
public class DepartmentVO {

    //部门名字
    private String name;

    //父id
    private Integer parentId;

    //返回插入影响的条数
    private  Integer result;

    //返回插入的id
    private Integer result2;

    //是否启用
    private Boolean enabled;

}
