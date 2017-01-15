package org.jhrcore.entity.annotation;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

// 用来注解实体类,表示类的编辑界面布局参数
@Retention(RetentionPolicy.RUNTIME) 
public @interface UILayout {
	// 每行的属性数
	int colNum() default 2;
	// 每个编辑筐的宽度
	String colWidth() default "80dlu";	
}
