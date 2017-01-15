package com.foundercy.pf.control.table;

/**
 * 合计值的特殊数据类型，已区分合计值与一般值
 * @author jerry
 */
public class SumRowValueTypes extends Number{

	private static final long serialVersionUID = 1L;
	
	private Number object = null;
	
	public SumRowValueTypes(){
		super();
		
	}
	public SumRowValueTypes(Number object) {
		super();
		this.setObject(object);
	}

	public double doubleValue() {
		if(this.object == null)
			return 0;
		return object.doubleValue();
	}

	public float floatValue() {
		if(this.object == null)
			return 0;
		return object.floatValue();
	}

	public int intValue() {
		if(this.object == null)
			return 0;
		return object.intValue();
	}

	public long longValue() {
		if(this.object == null)
			return 0;
		return object.longValue();
	}

	public Object getObject() {
		
		return object;
	}

	public void setObject(Number object) {
		if(object ==null){
			object = new Double(0);
		}else {
		    this.object = object;
		}
	}
	
	public String toString() {
		if(this.object == null)
			return null;
		return this.object.toString();
	}
}
