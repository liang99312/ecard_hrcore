package org.jhrcore.entity.annotation;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME) 
public @interface ClassAnnotation {
	String displayName() default "display name";
    String moduleName() default "module name";
}
