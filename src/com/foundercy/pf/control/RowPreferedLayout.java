/*
 * 创建日期 2004-6-11
 * 
 * TODO 要更改此生成的文件的模板，请转至
 * 窗口 － 首选项 － Java － 代码样式 － 代码模板
 */
package com.foundercy.pf.control;

import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.LayoutManager2;
import java.awt.Rectangle;
import java.util.Vector;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;

/**
 * @author fangyi
 * 行优先排列表格
 * TODO 要更改此生成的类型注释的模板，请转至
 * 窗口 － 首选项 － Java － 代码样式 － 代码模板
 */
public class RowPreferedLayout  implements LayoutManager2  {
	
	
	/**
	 * 缺省的行高
	 */
	public static int DEFAULT_ROW_HEIGHT = 20;
	/**
	 * 缺省的列宽
	 */
	public static int DEFAULT_COLUMN_WIDTH= 8*DEFAULT_ROW_HEIGHT;

	/** 
	 * 横向间隔的缺省值 
	 */
	private static final int DEFAULT_COL_GAP = 18;
	/**  
	 * 纵向间隔的缺省值
	 */	
	private static final int DEFAULT_ROW_GAP = 3;

	
	/**
	 * 表格布局中，控件的纵向间隔
	 */
	private int columnGap = DEFAULT_COL_GAP;

	/**
	 * 表格布局中，控件的横向间隔
	 */
	private int rowGap = DEFAULT_ROW_GAP;
	
	
	private int rowHeight = DEFAULT_ROW_HEIGHT;
	
	private int columnWidth = DEFAULT_COLUMN_WIDTH;
	/**
	 * 表格的行
	 */
	//private Vector table = new Vector();
	
	
	private StaticColumnTable table = null;
	/**
	 * 控件位置列表
	 */
	private Vector positions = new Vector();
	

	/**
	 * 构造函数，行优先排列的布局
	 * @param columns 指定固定列数
	 */
	public RowPreferedLayout(int columns){
		if(columns > 0) {
			this.table = new StaticColumnTable(columns,this);
		}else {
			this.table = new StaticColumnTable(1,this);
		}
	}
	
	
	/**
	 * 获得表格行数
	 * @return
	 */
	public int getColumns() {
		return this.table.getColumns();
	}
	
	/**
	 * 获得表格行数
	 * @return
	 */
	public int getRows() {
		return table.getRows();
	}
	
	/**
	 * 重新根据容器的高度调整各行的高度
	 * @param container
	 */
	protected void reAdjustRowHeight(Container container){
		
		int insetTop = container.getInsets().top;
		int insertBottom = container.getInsets().bottom;
		int rowgaps = (this.getRows() -1) * this.rowGap;
		int rowheights = this.getRows()* rowHeight;
		
		int height = insetTop+insertBottom+rowgaps+rowheights;
		
		
		int containerHeight = container.getHeight();
		
		int difs = containerHeight-height;

		Vector trs = this.getHeightReadjustTableRows();
		
		
		//adjust row height:
		if(trs.size()>0){
			
			if(trs.size()==1){
				
				Integer tr = (Integer)trs.get(0);
				this.table.setRowHeight(tr.intValue(),rowHeight+difs);
				//tr.setHeight(DEFAULT_ROW_HEIGHT+difs);
				
			}else {
				int dif = (int)difs/trs.size();
				int lastdif = difs - (trs.size()-1)*dif;
				
				for(int i=0; i<trs.size(); i++){
					Integer tr = (Integer)trs.get(i);
					if(i==trs.size()-1){
						this.table.setRowHeight(tr.intValue(),rowHeight+lastdif);
					}else {
						this.table.setRowHeight(tr.intValue(),rowHeight+dif);
						//tr.setHeight(DEFAULT_ROW_HEIGHT+dif);
					}
				}
			}
		    
		
		}
		
	}
	
	/**
	 * 重新根据容器的宽度调整各列的宽度
	 * @param container
	 */
	protected void reAdjustCloumnWidth(Container container){
		
		int insetLeft = container.getInsets().left;
		int insertRight = container.getInsets().right;
		int columngaps = (this.getColumns() -1) * this.columnGap;
		int columnWidths = this.getColumns()* this.columnWidth;
		
		int width = insetLeft+insertRight+columngaps+columnWidths;
		
		
		int containerWidth = container.getWidth();
		
		int difs = containerWidth-width;

		Vector trs = this.getWidthReadjustTableColumns();
		
		
		//adjust row height:
		if(trs.size()>0){
			
			if(trs.size()==1){
				
				Integer tr = (Integer)trs.get(0);
				this.table.setColumnWidth(tr.intValue(),columnWidth+difs);
				//tr.setHeight(DEFAULT_ROW_HEIGHT+difs);
				
			}else {
				int dif = (int)difs/trs.size();
				int lastdif = difs - (trs.size()-1)*dif;
				
				for(int i=0; i<trs.size(); i++){
					Integer tr = (Integer)trs.get(i);
					if(i==trs.size()-1){
						this.table.setColumnWidth(tr.intValue(),columnWidth+lastdif);
					}else {
						this.table.setColumnWidth(tr.intValue(),columnWidth+dif);
						//tr.setHeight(DEFAULT_ROW_HEIGHT+dif);
					}
				}
			}
		    
		
		}
			
//		int insetLeft = container.getInsets().left;
//		int insertRight = container.getInsets().right;
//		int columngaps = (this.getColumns() -1) * this.columnGap;
//		//int columnW = this.getColumns()* columnWidth;
//		
//		//除去边距和列间距剩下可以分配的宽度
//		int w = insetLeft+insertRight+columngaps;
//
//		int containerWidth = container.getWidth();
//		
//		int allocableWidth = containerWidth-w;
//
//		
//		Vector trs = this.getWidthReadjustTableColumns();
//
//		
//		
//		//adjust row height:
//		if(trs.size()>0){
//		
//			int colWidth = (int)allocableWidth/trs.size();
//			
//			if(trs.size()==1){
//				
//				Integer tr = (Integer)trs.get(0);
//				this.table.setColumnWidth(tr.intValue(),colWidth);
//				//tr.setHeight(DEFAULT_ROW_HEIGHT+difs);
//				
//			}else {
//				//int dif = (int)allocableWidth/trs.size();
//				int lastdif = allocableWidth - ((trs.size()-1)*colWidth);
//				
//				for(int i=0; i<trs.size(); i++){
//					Integer tr = (Integer)trs.get(i);
//					if(i==trs.size()-1){
//						this.table.setColumnWidth(tr.intValue(),lastdif);
//					}else {
//						this.table.setColumnWidth(tr.intValue(),colWidth);
//						//tr.setHeight(DEFAULT_ROW_HEIGHT+dif);
//					}
//				}
//			}
//		    
//		
//		}
		
	}	
	/**
	 * getBound for position
	 * @param p
	 * @return
	 */
	protected Rectangle getBound(Position p,Container container){
		
		//calculate column width
//		int width = container.getWidth();
//		int insertLeft = container.getInsets().left;
//		int insertRight = container.getInsets().right;

//		int columnWidth = (width-(this.getColumns()-1)*this.columnGap -insertLeft-insertRight)/this.getColumns();
//		int lastColumnWidth = width-(this.getColumns()-1)*(this.columnGap+columnWidth) -insertLeft-insertRight;
		
		
		
		int x = container.getInsets().left;
		int y = container.getInsets().top;	
		//System.out.println(y);
		int w = 0;
		int h = 0;

		//To get Control left position
//		for(int i=0; i<p.getLeft(); i++){
//			x += columnWidth + this.columnGap;
//		}
		for(int i=0; i<p.getLeft(); i++){
			
			x += this.table.getColumnWidth(i) + this.columnGap; 
		}
				
		//To get control width.
//		for(int i=p.getLeft(); i<p.getRight(); i++){
//			if(i < this.getColumns()-1) {
//				w += columnWidth + this.columnGap;
//			}else {
//				w += lastColumnWidth + this.columnGap;
//			}
//		}
		for(int i=p.getLeft(); i<p.getRight(); i++){
			
			w += this.table.getColumnWidth(i) + this.columnGap; 
		}
		w = w - this.columnGap;
				
		
		//To get control top position
		for(int i=0; i<p.getTop(); i++){
			
			y += this.table.getRowHeight(i) + this.rowGap; 
		}
		
		//To get Control height.
		for(int i=p.getTop(); i<p.getBottom(); i++){
			
			h += this.table.getRowHeight(i) + this.rowGap; 
		}
		h = h - this.rowGap;
		
		Rectangle rect = new Rectangle(x,y,w,h);
		
		return rect;
	}
	
	/**
	 * 
	 * @return
	 */
	private Vector getHeightReadjustTableRows() {
		
		Vector v = new Vector();
		for(int i=0; i<this.table.getRows(); i++){
			//TableRow tr = (TableRow)this.table.get(i);
			if(this.table.isHeightReAdjustable(i)) {
				
				v.add(new Integer(i));
				
			}
		}
		
		return v;
	}
	
	/**
	 * 
	 * @return
	 */
	private Vector getWidthReadjustTableColumns() {
		
		Vector v = new Vector();
		for(int i=0; i<this.table.getColumns(); i++){
			//TableRow tr = (TableRow)this.table.get(i);
			if(this.table.isWidthReAdjustable(i)) {
				
				v.add(new Integer(i));
				
			}
		}
		
		return v;
	}
	/**
	 * 获得最近摆放控件的行索引。
	 * @return
	 */
	private int getLastArrangedRowIndex() {
		if(positions.size()==0) {
			return 0;
		}else {
			Position p = (Position)positions.lastElement();
			return p.getTop();
		}
			
	}
	/**
	 * 获得最近摆放控件的列索引。
	 * @return
	 */
	private int getLastArrangedColumnIndex() {
		if(positions.size()==0) {
			return -1;
		}else {
			Position p = (Position)positions.lastElement();
			return p.getRight()-1;
		}	
	}
	
	/**
	 * Adds the specified component with the specified name to the layout.
	 * @param name the name of the component
	 * @param comp the component to be added
	 */
	public void addLayoutComponent(String name, Component comp) {
		synchronized (comp.getTreeLock()) {
			addLayoutComponent(comp, null);
		}
	}

	/**
	 * Removes the specified component from the layout.
	 * @param comp the component to be removed
	 */
	public void removeLayoutComponent(Component comp) {
		//this.ccl.removeComponent(comp);
	}

	/**
	 * Adds the specified component to the layout, using the specified
	 * constraint object.
	 *
	 * @param      comp         the component to be added.
	 * @param      constraints  an object that determines how
	 *                              the component is added to the layout.
	 */
	public void addLayoutComponent(Component comp, Object constraints) {

		if(comp == null || !comp.isVisible()) return;
		
		if (null == constraints) {
			constraints = new TableConstraints(1, 1);
		}
		
		if (constraints instanceof TableConstraints) {
			
			//如果所加入的控件的列数大于布局的列数，则自动设置为布局列数
			if(((TableConstraints)constraints).getColumns() > this.getColumns()) {
				((TableConstraints)constraints).setColumns(this.getColumns());
			}
			
			int myColumns = ((TableConstraints)constraints).getColumns();
			int myRows = ((TableConstraints)constraints).getRows();
			boolean myHeightReadjustable = ((TableConstraints)constraints).isHeightReadjustable();
			
			boolean myWidthReadjustable = ((TableConstraints)constraints).isWitdthReadjustable();
			boolean arranged = false;

			int startRow = this.getLastArrangedRowIndex();
			int startColumn = this.getLastArrangedColumnIndex()+1;
			
			//在没有摆放好控件的情况下，反复执行该操作。
			while(!arranged){
				//当行不够时，补齐行
				while(startRow > this.getRows()-1){
					this.table.addRow();
				}
				//如果当前行的列不够时，换下一行
				//TableRow tr = (TableRow)table.get(startRow);
				
				int enableIndex = this.table.getEnabledColumnInRow(startRow,startColumn,myColumns);
				
				//if no enableIndex found, start from next row.
				if(enableIndex < 0){
					startRow++;
					startColumn =0;
					continue;
				}
				
				startColumn = enableIndex;
				//Start to arrange
				//当行不够时，补齐行
				while(startRow + myRows > this.getRows()){
					this.table.addRow();
				}
				
				for(int i=startRow; i<startRow+myRows; i++){
					//TableRow tr1 = (TableRow)table.get(i);
					for(int j= startColumn ; j<startColumn+myColumns; j++){
						TableCell tc = this.table.getCellAt(i,j);
						tc.setArranged(true);
						tc.setHeightReadjustable(myHeightReadjustable);
						tc.setWidthReadjustable(myWidthReadjustable);
					}
				}
				Position p = new Position(comp,startRow, startRow+myRows,startColumn, startColumn+myColumns);
				
				this.positions.add(p);
				arranged = true;
			}
			
		} else {
			throw new IllegalArgumentException("can not addLayoutComponent:constraints is unsurpported!");
		}
	}

	/**
		 * Determines the preferred size of the <code>parent</code>
		 * container using this templet layout.
		 * <p>
		 * Most applications do not call this method directly.
		 * @param     parent   the container in which to do the layout.
		 * @see       java.awt.Container#getPreferredSize
		*/
	public Dimension preferredLayoutSize(Container parent) {
		synchronized (parent.getTreeLock()) {
			
			int columns = this.getColumns();
			int rows = this.getRows();
			int w = parent.getInsets().left + columns*DEFAULT_COLUMN_WIDTH + (columns-1)*columnGap + parent.getInsets().right;
			
			int h = parent.getInsets().top + rows*rowHeight + (rows-1)*rowGap + parent.getInsets().bottom;
			return new Dimension(w,h);
		}
	}

	/**
	 * Determines the minimum size of the <code>parent</code> container
	 * using this templet layout.
	 * <p>
	 * Most applications do not call this method directly.
	 * @param     parent   the container in which to do the layout.
	 * @see       java.awt.Container#doLayout
	 */
	public Dimension minimumLayoutSize(Container parent) {
		synchronized (parent.getTreeLock()) {
			return new Dimension(22,22);
		}
	}

	/**
		* Lays out the specified container using this templet layout.
		* This method reshapes components in the specified container in
		* order to satisfy the contraints of this <code>ObjectEditTempletLayout</code>
		* object.
		* <p>
		* Most applications do not call this method directly.
		* @param parent the container in which to do the layout.
		* @see java.awt.Container
		* @see java.awt.Container#doLayout
		*/
	public void layoutContainer(Container parent) {
		synchronized (parent.getTreeLock()) {
			this.reAdjustRowHeight(parent);
			this.reAdjustCloumnWidth(parent);
			for(int i=0;i<positions.size(); i++){
				Position p = (Position)positions.get(i);
				Rectangle rect = this.getBound(p,parent);
				p.getComponent().setBounds(rect);
				
			}
		}
	}

	/**
	 * Returns the maximum dimensions for this layout given the components
	 * in the specified target container.
	 * @param target the component which needs to be laid out
	 * @see Container
	 * @see #minimumLayoutSize
	 * @see #preferredLayoutSize
	 */
	public Dimension maximumLayoutSize(Container target) {
		synchronized (target.getTreeLock()) {
			return new Dimension(22,22);
		}
	}

	/**
	 * Returns the alignment along the x axis.  This specifies how
	 * the component would like to be aligned relative to other
	 * components.  The value should be a number between 0 and 1
	 * where 0 represents alignment along the origin, 1 is aligned
	 * the furthest away from the origin, 0.5 is centered, etc.
	 */
	public float getLayoutAlignmentX(Container parent) {
		return 0.5f;
	}

	/**
	 * Returns the alignment along the y axis.  This specifies how
	 * the component would like to be aligned relative to other
	 * components.  The value should be a number between 0 and 1
	 * where 0 represents alignment along the origin, 1 is aligned
	 * the furthest away from the origin, 0.5 is centered, etc.
	 */
	public float getLayoutAlignmentY(Container parent) {
		return 0.5f;
	}

	/**
	 * Invalidates the layout, indicating that if the layout manager
	 * has cached information it should be discarded.
	 */
	public void invalidateLayout(Container target) {
		//		this.components.removeAllElements();
	}

	
	public static void main(String[] args){
		RowPreferedLayout layout = new RowPreferedLayout(4);
		JTextField comp1 = new JTextField();
		TableConstraints tcs1 = new TableConstraints(1,1,false);
//		layout.addLayoutComponent(comp1,tcs1);
		
		JTextField comp2 = new JTextField();
		TableConstraints tcs2 = new TableConstraints(2,3,false);
//		layout.addLayoutComponent(comp2,tcs2);
		
		
		JTextField comp3 = new JTextField();
		TableConstraints tcs3 = new TableConstraints(1,1,false);
//		layout.addLayoutComponent(comp3,tcs3);
	
		JTextField comp4 = new JTextField();
		TableConstraints tcs4 = new TableConstraints(1,4,false);
//		layout.addLayoutComponent(comp4,tcs4);

		JFrame f = new JFrame();
		JPanel p =(JPanel)f.getContentPane();
		p.setLayout(layout);
		
		p.add(comp1,tcs1);
		p.add(comp2,tcs2);
		p.add(comp3,tcs3);
		p.add(comp4,tcs4);
		
		f.setSize(800,600);
		f.setVisible(true);
		
		
//		JPanel comp5 = new JPanel();
//		TableConstraints tcs5 = new TableConstraints(1,1,false);
//		layout.addLayoutComponent(comp5,tcs5);		
//
//		
//		JPanel comp6 = new JPanel();
//		TableConstraints tcs6 = new TableConstraints(1,1,false);
//		layout.addLayoutComponent(comp6,tcs6);	
//		
//		
//		JPanel comp7 = new JPanel();
//		TableConstraints tcs7 = new TableConstraints(1,2,false);
//		layout.addLayoutComponent(comp7,tcs7);			
//
//		JPanel comp8 = new JPanel();
//		TableConstraints tcs8 = new TableConstraints(1,3,false);
//		layout.addLayoutComponent(comp8,tcs8);
		
		layout.test();
		
	}
	public void test() {
		for(int i=0; i<positions.size(); i++){
			Position p = (Position)positions.get(i);
			System.out.println("contorl "+(i+1)+": [("+p.getTop()+","+p.getLeft()+")("+p.getBottom()+","+p.getRight()+")]");
		}
	}
	/**
	 * @param columns 要设置的 columns。
	 */
	public void setColumns(int columns) {
		
		this.table.setColumns(columns);
	}
	/**
	 * @return 返回 columnGap。
	 */
	public int getColumnGap() {
		return columnGap;
	}
	/**
	 * @param columnGap 要设置的 columnGap。
	 */
	public void setColumnGap(int columnGap) {
		this.columnGap = columnGap;
	}
	/**
	 * @return 返回 rowGap。
	 */
	public int getRowGap() {
		return rowGap;
	}
	/**
	 * @param rowGap 要设置的 rowGap。
	 */
	public void setRowGap(int rowGap) {
		this.rowGap = rowGap;
	}


	public int getRowHeight() {
		return rowHeight;
	}


	public void setRowHeight(int rowHeight) {
		this.rowHeight = rowHeight;
	}


	public int getColumnWidth() {
		return columnWidth;
	}


	public void setColumnWidth(int columnWidth) {
		this.columnWidth = columnWidth;
	}
}
