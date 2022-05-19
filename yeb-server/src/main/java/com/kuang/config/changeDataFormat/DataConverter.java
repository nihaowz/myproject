package com.kuang.config.changeDataFormat;


import org.springframework.core.convert.converter.Converter;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/**
 * 日期转换 跟全局错误其实差不多
 */

public class DataConverter implements Converter<String, LocalDate> {

    @Override
    public LocalDate convert(String source) {
        try {
           return  LocalDate.parse(source, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
