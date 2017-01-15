package com.foundercy.pf.control.table;

/**
 * �ϼ���״̬�����࣬�б��Ƿ���ںϼ����Լ�С�����ܼƵĹ�ϵ
 * @author jerry
 */
public class GetSumRowStute {
	
	//�Ƿ����С����
	private boolean isPartSumRow = false;
	//�Ƿ�����ܼ���
	private boolean isAllSumRow = false;
	//С���е�λ���Ƿ��ڱ����ϲ�
	private boolean isPartSumRowTop = true;
	//�ܼ��е�λ���Ƿ��ڱ����ϲ�
	private boolean isAllSumRowTop = true;
	/**
	 * С�������ܼ���λ�õĹ�ϵ
	 * true���ܼ�����С���е��ϲ�
	 * false���ܼ�����С���е��²�
	 */
	private boolean isAllTopPart = true;
	
	/**
	 * �ϼ���״̬
	 */
	/****************begin**************/
	//û�кϼ���
	public static int noSumRow = 0;
	
	//ֻ��С���У���С�������ϲ�
	public static int onlyPartSumRowTop = 10;
	
	//ֻ��С���У���С�������ϲ�
	public static int onlyPartSumRowBottom = 11;
	//ֻ���ܼ��У���С�������ϲ�
	public static int onlyAllSumRowTop = 20;
	//ֻ���ܼ��У����ܼ����ڵײ�
	public static int onlyAllSumRowBottom = 21;
	
	//�����ܼ�������С����
	public static int sumRow = 3;
	
	//�����ܼ�������С���У����ܼ��к�С�������ϲ����ܼ�����С����֮��
	public static int sumRowTopAllPart = 110;
	//�����ܼ�������С���У����ܼ��к�С�������ϲ����ܼ�����С����֮��
	public static int sumRowTopPartAll = 111;
	
	//�����ܼ�������С���У����ܼ������ϲ���С�����ڵײ�
	public static int sumRowAllPart = 120;
	//�����ܼ�������С���У����ܼ����ڵײ���С�������ϲ�
	public static int sumRowPartAll = 211;
	
	//�����ܼ�������С���У����ܼ��к�С�����ڵײ����ܼ�����С����֮��
	public static int sumRowBottomAllPart = 220;
	//�����ܼ�������С���У����ܼ��к�С�����ڵײ����ܼ�����С����֮��
	public static int sumRowBottomPartAll = 221;
	
	/********************end*****************/
	
	/**
	 * �������췽��
	 */
	public GetSumRowStute() {};
	
	/**
	 * ���úϼ���״̬���췽��
	 * @param isPartSumRow �Ƿ���С����
	 * @param isAllSumRow �Ƿ��кϼ���
	 */
	public GetSumRowStute(boolean isPartSumRow,boolean isAllSumRow, boolean isPartSumRowTop, boolean isAllSumRowTop, boolean isAllTopPart) {
		
		this.setPartSumRow(isPartSumRow);
		this.setAllSumRow(isAllSumRow);
		this.setPartSumRowTop(isPartSumRowTop);
		this.setAllSumRowTop(isAllSumRowTop);
		this.setAllTopPart(isAllTopPart);
		
	}
	
	/**
	 * �б��Ƿ���ںϼ���
	 * @return int���� �ϼ���״̬
	 */
	private int checkIsSumRow() {
		
		if(!this.isAllSumRow() && !this.isPartSumRow()) {
			return GetSumRowStute.noSumRow;
		}else {
			if(this.isAllSumRow() && this.isPartSumRow()) {
				return GetSumRowStute.sumRow;
			}else {
				if(this.isAllSumRow()) {
					return this.isAllSumRowTop()?GetSumRowStute.onlyAllSumRowTop:GetSumRowStute.onlyAllSumRowBottom;
				}
				if(this.isPartSumRow()) {
					return this.isPartSumRowTop()?GetSumRowStute.onlyPartSumRowTop:GetSumRowStute.onlyPartSumRowBottom;
				}
			}
		}
		return GetSumRowStute.noSumRow;
		
	}
	
	/**
	 * �������ܼ�������С����ʱ���б��ܼ�����С���еĹ�ϵ״̬
	 * @param isAllSumRowTop �ܼ����Ƿ��ڱ���ϲ�
	 * @param isPartSumRowTop С�����Ƿ��ڱ���ϲ�
	 * @param isAllTopPart �ܼ����Ƿ���С���к�֮��
	 * @return int���� �ϼ���״̬
	 */
	private int checkSumRowAllAndPartStute(boolean isAllSumRowTop, boolean isPartSumRowTop, boolean isAllTopPart) {
		
		if(isAllSumRowTop && isPartSumRowTop) {
			return isAllTopPart?GetSumRowStute.sumRowTopAllPart:GetSumRowStute.sumRowTopPartAll;
		}
		if(!isAllSumRowTop && !isPartSumRowTop) {
			return isAllTopPart?GetSumRowStute.sumRowBottomAllPart:GetSumRowStute.sumRowBottomPartAll;
		}
		if(isAllSumRowTop && !isPartSumRowTop) {
			return GetSumRowStute.sumRowAllPart;
		}
		if(!isAllSumRowTop && isPartSumRowTop) {
			return GetSumRowStute.sumRowPartAll;
		}
		return GetSumRowStute.sumRowTopAllPart;
		
	}
	
	/**
	 * ��ȡ�ϼ���״̬
	 * @return int���� �ϼ���״̬
	 */
	public int getSumRowStute() {
		
		int result = this.checkIsSumRow();
		if(result == GetSumRowStute.sumRow) {
			result = this.checkSumRowAllAndPartStute(this.isAllSumRowTop(), this.isPartSumRowTop(), this.isAllTopPart());
		}
		return result;
		
	}

	public boolean isAllSumRow() {
		return isAllSumRow;
	}

	public void setAllSumRow(boolean isAllSumRow) {
		this.isAllSumRow = isAllSumRow;
	}

	public boolean isAllSumRowTop() {
		return isAllSumRowTop;
	}

	public void setAllSumRowTop(boolean isAllSumRowTop) {
		this.isAllSumRowTop = isAllSumRowTop;
	}

	public boolean isAllTopPart() {
		return isAllTopPart;
	}

	public void setAllTopPart(boolean isAllTopPart) {
		this.isAllTopPart = isAllTopPart;
	}

	public boolean isPartSumRow() {
		return isPartSumRow;
	}

	public void setPartSumRow(boolean isPartSumRow) {
		this.isPartSumRow = isPartSumRow;
	}

	public boolean isPartSumRowTop() {
		return isPartSumRowTop;
	}

	public void setPartSumRowTop(boolean isPartSumRowTop) {
		this.isPartSumRowTop = isPartSumRowTop;
	}

}
