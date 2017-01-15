package org.jhrcore.entity.annotation;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
public @interface ObjectListHint {
    // ��ȡֵ�б��hql

    String hqlForObjectList() default "";

    // �༭��ʽ,��ʾ������ѡ���ǶԻ���ѡ��
    String editType() default "����ѡ��";

    boolean nullable() default false;
}
