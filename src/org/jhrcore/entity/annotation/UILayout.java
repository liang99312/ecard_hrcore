package org.jhrcore.entity.annotation;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

// ����ע��ʵ����,��ʾ��ı༭���沼�ֲ���
@Retention(RetentionPolicy.RUNTIME) 
public @interface UILayout {
	// ÿ�е�������
	int colNum() default 2;
	// ÿ���༭��Ŀ��
	String colWidth() default "80dlu";	
}
