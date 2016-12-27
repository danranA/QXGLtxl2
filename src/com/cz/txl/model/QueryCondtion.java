package com.cz.txl.model;

import com.cz.txl.type.QueryConditionType;
//查询条件封装类
public class QueryCondtion {
	private String fieldName;//列名
	private Object fieldValue;//条件列值
	private QueryConditionType conditionType;//条件的比较类型
	//如果调用这个构造方法，默认我们设置比较类型为相等
	public QueryCondtion(String fieldName, Object fieldValue) {
		this(fieldName,fieldValue,QueryConditionType.Equals);
	}
	public QueryCondtion(String fieldName, Object fieldValue, QueryConditionType conditionType) {
		super();
		this.fieldName = fieldName;
		this.fieldValue = fieldValue;
		this.conditionType = conditionType;
	}

	public String getFieldName() {
		return fieldName;
	}

	public void setFieldName(String fieldName) {
		this.fieldName = fieldName;
	}




	public Object getFieldValue() {
		return fieldValue;
	}




	public void setFieldValue(Object fieldValue) {
		this.fieldValue = fieldValue;
	}




	public QueryConditionType getConditionType() {
		return conditionType;
	}




	public void setConditionType(QueryConditionType conditionType) {
		this.conditionType = conditionType;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((conditionType == null) ? 0 : conditionType.hashCode());
		result = prime * result + ((fieldName == null) ? 0 : fieldName.hashCode());
		result = prime * result + ((fieldValue == null) ? 0 : fieldValue.hashCode());
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		QueryCondtion other = (QueryCondtion) obj;
		if (conditionType != other.conditionType)
			return false;
		if (fieldName == null) {
			if (other.fieldName != null)
				return false;
		} else if (!fieldName.equals(other.fieldName))
			return false;
		if (fieldValue == null) {
			if (other.fieldValue != null)
				return false;
		} else if (!fieldValue.equals(other.fieldValue))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "QueryCondtion [fieldName=" + fieldName + ", fieldValue=" + fieldValue + ", conditionType="
				+ conditionType + "]";
	}


}

	