package org.jhrcore.entity.annotation;

import java.lang.annotation.*;

@Retention(RetentionPolicy.RUNTIME)
public @interface FieldAnnotation {

    boolean visible() default true;

    String displayName() default "display name";

    String groupName() default "";//默认组名为空

    String displayWidth() default "单列";
    String pym() default "";
    boolean isEditable() default true;

    int order_no() default 0;

    int fieldWidth() default 20;

    int view_width() default 20;
    // 是否非空
    int field_scale() default 0;
    boolean not_null() default false;

    boolean editableWhenNew() default true;

    boolean editableWhenEdit() default true;

    boolean visibleWhenNew() default true;

    boolean visibleWhenEdit() default true;
    String default_value() default "";
    String format() default "";
    String field_align() default "左对齐";
    String field_mark() default "";
    // 是否唯一
    boolean isUnique() default false;
}
