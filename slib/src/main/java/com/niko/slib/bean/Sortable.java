package com.niko.slib.bean;

public interface Sortable {
    enum Type {
        CATEGORY,
        ITEM
    }
    String getKey();//用于分类的字符串字段,例如姓名,城市

    Type getType();//是类别比如A-Z,还是某一个内容项
}