package com.gangtise.sdk.callback;

import lombok.Data;

@Data
public class GDataSet {
    //集合名、表名  string
    public String name;
    //行数        int
    public int rowCount;
    //列数        int
    public int colCount;
    //开始位置
    public int begin;
    // 条数
    // this.count;
    //总数
    public int totalRows;
    //表头
    public GColumnInfo columnInfos;
}
