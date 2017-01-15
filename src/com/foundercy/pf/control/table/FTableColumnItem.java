package com.foundercy.pf.control.table;

import java.util.ArrayList;
import java.util.List;

public class FTableColumnItem {

    private String id;  // ��ID
    private String title; // ��ʾ����
    private boolean isEditable;  // �༭ʱ�Ƿ�ɱ༭
    private boolean isLargeText;//�Ƿ���ı�
    // ���й������ֶ���ϸ�б����� person person_code

    private boolean editable_when_new; //������¼�¼�Ƿ�ɱ༭
    public boolean isEditable_when_new() {
        return editable_when_new;
    }

    public void setEditable_when_new(boolean editable_when_new) {
        this.editable_when_new = editable_when_new;
    }
    private List<String> field_list = null;

    public List<String> getField_list() {
        String fieldName = this.getId();
        if (field_list == null) {
            field_list = new ArrayList<String>();
            int start_ind = 0;
            for (int i = 0; i < fieldName.length(); i++) {
                if (fieldName.charAt(i) == '.') {
                    field_list.add(fieldName.substring(start_ind, i));
                    start_ind = i + 1;
                } else if (i == fieldName.length() - 1) {
                    field_list.add(fieldName.substring(start_ind));
                }
            }
        }
        return field_list;
    }

    public FTableColumnItem() {
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getId() {
        return this.id;
    }

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public boolean isEditable() {
        return isEditable;
    }

    public void setEditable(boolean isEditable) {
        this.isEditable = isEditable;
    }

    public boolean isLargeText() {
        return isLargeText;
    }

    public void setLargeText(boolean isLargeText) {
        this.isLargeText = isLargeText;
    }
}
