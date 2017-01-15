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
public class StaticRowTable extends Table {
	
	/**
	 * �̶��б���캯��
	 * @param columns
	 */
	public StaticRowTable(int rows){
		if(rows >= 1) {
			this.rows = rows;
		}
		this.columns = 0;
	}
	
	/**
	 * ����һ��
	 */
	public void addColumn() {
		this.columns++;
	}
	
	/**
	 * to get an suitable postion for columns in this row;
	 * @param index
	 * @param cols
	 * @return
	 */
	public int getEnabledRowInColumn(int column, int startRow, int rs) {
		int enabledIndex = -1;
		for(int i=startRow; i< this.rows; i++){
			if(this.getCellAt(i,column).isArranged())
				continue;
			//���������Ѿ�����
			if( (i+rs) > this.rows)
				
				break;
			boolean ok = true;
			for(int j = i; j<rs ;j++){
				if(this.getCellAt(j,column).isArranged()) {
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
	public void setRows(int rows) {
		this.rows = rows;
	}

	public int getColumnWidth() {
		// TODO Auto-generated method stub
		return 0;
	}

	public int getRowHeight() {
		// TODO Auto-generated method stub
		return 0;
	}
	
}
