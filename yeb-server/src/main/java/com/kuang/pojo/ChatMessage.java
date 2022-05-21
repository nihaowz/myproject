package com.kuang.pojo;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDateTime;

@Data
@EqualsAndHashCode(callSuper = false)
public class ChatMessage {
    //用户名
    private String from;
    private  String to;
    private  String message;
    private LocalDateTime dateTime;
    //名称
    private  String name;

}
