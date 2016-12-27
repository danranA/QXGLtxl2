package com.cz.txl.type;
/*
 * 所谓枚举类，是一个特殊的类型
 * 首先它里面的属性和get,set方法及构造方法的理解与普通类是一致的
 * 但是，枚举类将该类可能生成的的实例对象都在该类内部一一列举出来了
 * 也就是说枚举是可以查清的，该类可能产生的对象是实例时确定
 * 而普通类理论上是可以产生无数的实例对象
 */
public enum QueryConditionType {

	Equals("equals","="),Like("like","like"),Bigger("bigger",">"),Less("less","<");

	private String type;
	private String name;


	private QueryConditionType(String type, String name) {
		this.type = type;
		this.name = name;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

}
