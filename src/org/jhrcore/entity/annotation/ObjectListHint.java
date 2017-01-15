package org.jhrcore.entity.annotation;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
public @interface ObjectListHint {
    // 获取值列表的hql

    String hqlForObjectList() default "";

    // 编辑方式,表示是下拉选择还是对话框选择
    String editType() default "下拉选择";

    boolean nullable() default false;
}
