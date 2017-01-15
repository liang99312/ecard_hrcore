package org.jhrcore.entity.annotation;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME) 
public @interface EnumHint {
	// 枚举类型的取值,每个值之间用';'分割
	String enumList() default "";
        boolean nullable() default true;
	
}
