package com.foundercy.pf.control.table;

/**
 * 合计行状态处理类，判别是否存在合计行以及小计与总计的关系
 * @author jerry
 */
public class GetSumRowStute {
	
	//是否存在小计行
	private boolean isPartSumRow = false;
	//是否存在总计行
	private boolean isAllSumRow = false;
	//小计行的位置是否在表格的上部
	private boolean isPartSumRowTop = true;
	//总计行的位置是否在表格的上部
	private boolean isAllSumRowTop = true;
	/**
	 * 小计行与总计行位置的关系
	 * true：总计行在小计行的上部
	 * false：总计行在小计行的下部
	 */
	private boolean isAllTopPart = true;
	
	/**
	 * 合计行状态
	 */
	/****************begin**************/
	//没有合计行
	public static int noSumRow = 0;
	
	//只有小计行，且小计行在上部
	public static int onlyPartSumRowTop = 10;
	
	//只有小计行，且小计行在上部
	public static int onlyPartSumRowBottom = 11;
	//只有总计行，且小计行在上部
	public static int onlyAllSumRowTop = 20;
	//只有总计行，且总计行在底部
	public static int onlyAllSumRowBottom = 21;
	
	//既有总计行又有小计行
	public static int sumRow = 3;
	
	//既有总计行又有小计行，且总计行和小计行在上部，总计行在小计行之上
	public static int sumRowTopAllPart = 110;
	//既有总计行又有小计行，且总计行和小计行在上部，总计行在小计行之下
	public static int sumRowTopPartAll = 111;
	
	//既有总计行又有小计行，且总计行在上部，小计行在底部
	public static int sumRowAllPart = 120;
	//既有总计行又有小计行，且总计行在底部，小计行在上部
	public static int sumRowPartAll = 211;
	
	//既有总计行又有小计行，且总计行和小计行在底部，总计行在小计行之上
	public static int sumRowBottomAllPart = 220;
	//既有总计行又有小计行，且总计行和小计行在底部，总计行在小计行之下
	public static int sumRowBottomPartAll = 221;
	
	/********************end*****************/
	
	/**
	 * 基本构造方法
	 */
	public GetSumRowStute() {};
	
	/**
	 * 设置合计行状态构造方法
	 * @param isPartSumRow 是否有小计行
	 * @param isAllSumRow 是否有合计行
	 */
	public GetSumRowStute(boolean isPartSumRow,boolean isAllSumRow, boolean isPartSumRowTop, boolean isAllSumRowTop, boolean isAllTopPart) {
		
		this.setPartSumRow(isPartSumRow);
		this.setAllSumRow(isAllSumRow);
		this.setPartSumRowTop(isPartSumRowTop);
		this.setAllSumRowTop(isAllSumRowTop);
		this.setAllTopPart(isAllTopPart);
		
	}
	
	/**
	 * 判别是否存在合计行
	 * @return int类型 合计行状态
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
	 * 当既有总计行又有小计行时，判别总计行于小计行的关系状态
	 * @param isAllSumRowTop 总计行是否在表格上部
	 * @param isPartSumRowTop 小计行是否在表格上部
	 * @param isAllTopPart 总计行是否在小计行很之上
	 * @return int类型 合计行状态
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
	 * 获取合计行状态
	 * @return int类型 合计行状态
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
