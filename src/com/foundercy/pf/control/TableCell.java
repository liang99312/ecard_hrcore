/*
 * �������� 2004-6-11
 *
 * TODO Ҫ���Ĵ����ɵ��ļ���ģ�壬��ת��
 * ���� �� ��ѡ�� �� Java �� ������ʽ �� ����ģ��
 */
package com.foundercy.pf.control;


/**
 * @author fangyi
 *
 * TODO Ҫ���Ĵ����ɵ�����ע�͵�ģ�壬��ת��
 * ���� �� ��ѡ�� �� Java �� ������ʽ �� ����ģ��
 */
class TableCell {
	

	//������߶��Ƿ���Ը��ݸ��ؼ��ĸ߶�������
	private boolean heightReadjustable = true;
	
	private boolean widthReadjustable = false;
	
    private boolean arranged = false;
	
	public TableCell() {
		
	}

	/**
	 * @return ���� heightAdjustable��
	 */
	public boolean isHeightReadjustable() {
		return heightReadjustable;
	}
	/**
	 * @param heightAdjustable Ҫ���õ� heightAdjustable��
	 */
	public void setHeightReadjustable(boolean heightAdjustable) {
		this.heightReadjustable = heightAdjustable;
	}
	
	
	/**
	 * @return ���� arranged��
	 */
	public boolean isArranged() {
		return arranged;
	}
	/**
	 * @param arranged Ҫ���õ� arranged��
	 */
	public void setArranged(boolean arranged) {
		this.arranged = arranged;
	}

	public boolean isWidthReadjustable() {
		return widthReadjustable;
	}

	public void setWidthReadjustable(boolean widthReadjustable) {
		this.widthReadjustable = widthReadjustable;
	}
}
