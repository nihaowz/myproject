package com.kuang.utils;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PageUtils {

    /**
     * 总条数
     */
    private Integer total;

    /**
     * 总的数据list
     * 使用泛型
     */
    List<?> data;


}
