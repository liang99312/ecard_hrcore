package org.jhrcore.entity.annotation;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME) 
public @interface EnumHint {
	// ö�����͵�ȡֵ,ÿ��ֵ֮����';'�ָ�
	String enumList() default "";
        boolean nullable() default true;
	
}
