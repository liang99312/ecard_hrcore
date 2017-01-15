/*
 * 创建日期 2004-6-11
 *
 * TODO 要更改此生成的文件的模板，请转至
 * 窗口 － 首选项 － Java － 代码样式 － 代码模板
 */
package com.foundercy.pf.control;

import java.awt.Component;

/**
 * @author fangyi
 *
 * TODO 要更改此生成的类型注释的模板，请转至
 * 窗口 － 首选项 － Java － 代码样式 － 代码模板
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
	 * @return 返回 bottom。
	 */
	public int getBottom() {
		return bottom;
	}
	/**
	 * @param bottom 要设置的 bottom。
	 */
	public void setBottom(int bottom) {
		this.bottom = bottom;
	}
	/**
	 * @return 返回 left。
	 */
	public int getLeft() {
		return left;
	}
	/**
	 * @param left 要设置的 left。
	 */
	public void setLeft(int left) {
		this.left = left;
	}
	/**
	 * @return 返回 right。
	 */
	public int getRight() {
		return right;
	}
	/**
	 * @param right 要设置的 right。
	 */
	public void setRight(int right) {
		this.right = right;
	}
	/**
	 * @return 返回 top。
	 */
	public int getTop() {
		return top;
	}
	/**
	 * @param top 要设置的 top。
	 */
	public void setTop(int top) {
		this.top = top;
	}
	/**
	 * @return 返回 comp。
	 */
	public Component getComponent() {
		return comp;
	}
	/**
	 * @param comp 要设置的 comp。
	 */
	public void setComponent(Component comp) {
		this.comp = comp;
	}
}
