package com.niko.slib.annotation;

import android.support.annotation.LayoutRes;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Created by scanor on 2016/7/7.
 * at 9:40
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface InjectLayout {
    @LayoutRes int layout() default 0; //布局
    int mode() default  0;//显示模式，0是默认的普通模式，1为列表模式
}
