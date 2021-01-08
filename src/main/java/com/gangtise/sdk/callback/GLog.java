package com.gangtise.sdk.callback;

import lombok.Data;

@Data
public class GLog {

    public String level;//string  等级 log 记录流水操作 / info 记录关键操作 / error 记录错误操作
    public String msg;//string    消息
    public String time;//date     记录时间
}
