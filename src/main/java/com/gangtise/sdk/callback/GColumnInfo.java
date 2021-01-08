package com.gangtise.sdk.callback;

import lombok.Data;

@Data
public class GColumnInfo {
    public int index ;   //列序号    int
    public String name;    //列名称    string
    public GDataType dataType;//列类型   GDataType  ??
    public Object data;    //列附加数据  ??
}
