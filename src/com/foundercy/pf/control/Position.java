/*
 * �������� 2004-6-11
 *
 * TODO Ҫ���Ĵ����ɵ��ļ���ģ�壬��ת��
 * ���� �� ��ѡ�� �� Java �� ������ʽ �� ����ģ��
 */
package com.foundercy.pf.control;

import java.awt.Component;

/**
 * @author fangyi
 *
 * TODO Ҫ���Ĵ����ɵ�����ע�͵�ģ�壬��ת��
 * ���� �� ��ѡ�� �� Java �� ������ʽ �� ����ģ��
 */
class Position {
	private int top = 0;
	private int bottom = 0;
	private int left = 0;
	private int right = 0;
	Component comp = null;
	public Position() {
		
	}
	public Position(Component comp, int top, int bottom ,int left, int right) {
		this.comp = comp;
		this.top = top;
		this.bottom = bottom;
		this.left = left;
		this.right = right;
	}
	
	
	/**
	 * @return ���� bottom��
	 */
	public int getBottom() {
		return bottom;
	}
	/**
	 * @param bottom Ҫ���õ� bottom��
	 */
	public void setBottom(int bottom) {
		this.bottom = bottom;
	}
	/**
	 * @return ���� left��
	 */
	public int getLeft() {
		return left;
	}
	/**
	 * @param left Ҫ���õ� left��
	 */
	public void setLeft(int left) {
		this.left = left;
	}
	/**
	 * @return ���� right��
	 */
	public int getRight() {
		return right;
	}
	/**
	 * @param right Ҫ���õ� right��
	 */
	public void setRight(int right) {
		this.right = right;
	}
	/**
	 * @return ���� top��
	 */
	public int getTop() {
		return top;
	}
	/**
	 * @param top Ҫ���õ� top��
	 */
	public void setTop(int top) {
		this.top = top;
	}
	/**
	 * @return ���� comp��
	 */
	public Component getComponent() {
		return comp;
	}
	/**
	 * @param comp Ҫ���õ� comp��
	 */
	public void setComponent(Component comp) {
		this.comp = comp;
	}
}
