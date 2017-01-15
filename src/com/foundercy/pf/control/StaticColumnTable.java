/*
 * �������� 2004-6-12
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
public class StaticColumnTable extends Table {
	
	private RowPreferedLayout layout;

	/**
	 * �̶��б���캯��
	 * @param columns
	 */
	public StaticColumnTable(int columns,RowPreferedLayout layout){
		
		if(columns >= 1) {
			this.columns = columns;
		}
		this.rows = 0;
		
		this.layout = layout;
	}
	
	/**
	 * ����һ��
	 */
	public void addRow() {
		this.rows++;
	}
	
	/**
	 * to get an suitable postion for columns in this row;
	 * @param index
	 * @param cols
	 * @return
	 */
	public int getEnabledColumnInRow(int row, int startColumn, int cols) {
		int enabledIndex = -1;
		for(int i=startColumn; i< this.columns; i++){
			if(this.getCellAt(row,i).isArranged())
				continue;
			//���������Ѿ�����
			if( (i+cols) > this.columns)
				
				break;
			boolean ok = true;
			for(int j = i; j<cols ;j++){
				if(this.getCellAt(row,j).isArranged()) {
					ok=false;
					break;
				}
			}
			if(ok) {
				enabledIndex = i;
				break;
			}
		}
		//System.out.println(enabledIndex);
		return enabledIndex;
	}	
	/**
	 * @param columns Ҫ���õ� columns��
	 */
	public void setColumns(int columns) {
		this.columns = columns;
	}

	public int getColumnWidth() {
		return this.layout.getColumnWidth();
	}



	public int getRowHeight() {
		return this.layout.getRowHeight();
	}

}
