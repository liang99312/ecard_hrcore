/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.jhrcore.query3;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author wangzhenhua
 */
public class TempEntity {
    // ���ֶ�������ʵ��������ȫ��
    private String entityClass;

    private String entityCaption;

    private List<TempField> tempFields;
    public List<TempField> getTempFields() {
        if (tempFields == null)
            tempFields = new ArrayList<TempField>();
        return tempFields;
    }

    public String toString(){
        return entityCaption;
    }

    public String getEntityCaption() {
        return entityCaption;
    }

    public void setEntityCaption(String entityCaption) {
        this.entityCaption = entityCaption;
    }

    public String getEntityClass() {
        return entityClass;
    }

    public void setEntityClass(String entityClass) {
        this.entityClass = entityClass;
    }
    
}
