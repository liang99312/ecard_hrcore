/*
 * 创建日期 2004-6-11
 *
 * TODO 要更改此生成的文件的模板，请转至
 * 窗口 － 首选项 － Java － 代码样式 － 代码模板
 */
package com.foundercy.pf.control;


/**
 * @author fangyi
 *
 * TODO 要更改此生成的类型注释的模板，请转至
 * 窗口 － 首选项 － Java － 代码样式 － 代码模板
 */
class TableCell {
	

	//该区域高度是否可以根据父控件的高度来调整
	private boolean heightReadjustable = true;
	
	private boolean widthReadjustable = false;
	
    private boolean arranged = false;
	
	public TableCell() {
		
	}

	/**
	 * @return 返回 heightAdjustable。
	 */
	public boolean isHeightReadjustable() {
		return heightReadjustable;
	}
	/**
	 * @param heightAdjustable 要设置的 heightAdjustable。
	 */
	public void setHeightReadjustable(boolean heightAdjustable) {
		this.heightReadjustable = heightAdjustable;
	}
	
	
	/**
	 * @return 返回 arranged。
	 */
	public boolean isArranged() {
		return arranged;
	}
	/**
	 * @param arranged 要设置的 arranged。
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
