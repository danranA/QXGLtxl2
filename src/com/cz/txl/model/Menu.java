package com.cz.txl.model;

public class Menu {
	private int id;
	private int menuNo;//菜单编号
	private String menuName;//菜单名称
	private int parentId;//父节点id
	private String action;//点击菜单触发的动作
	private int sortNo;//该菜单在列表中的显示顺序
	private String menuDesc;//菜单的描述
	public Menu() {
		super();
	}
	public Menu(int menuNo, String menuName, int parentId, String action, int sortNo, String desc) {
		super();
		this.menuNo = menuNo;
		this.menuName = menuName;
		this.parentId = parentId;
		this.action = action;
		this.sortNo = sortNo;
		this.menuDesc = desc;
	}
	public Menu(int id, int menuNo, String menuName, int parentId, String action, int sortNo, String desc) {
		super();
		this.id = id;
		this.menuNo = menuNo;
		this.menuName = menuName;
		this.parentId = parentId;
		this.action = action;
		this.sortNo = sortNo;
		this.menuDesc = desc;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getMenuNo() {
		return menuNo;
	}
	public void setMenuNo(int menuNo) {
		this.menuNo = menuNo;
	}
	public String getMenuName() {
		return menuName;
	}
	public void setMenuName(String menuName) {
		this.menuName = menuName;
	}
	public int getParentId() {
		return parentId;
	}
	public void setParentId(int parentId) {
		this.parentId = parentId;
	}
	public String getAction() {
		return action;
	}
	public void setAction(String action) {
		this.action = action;
	}
	public int getSortNo() {
		return sortNo;
	}
	public void setSortNo(int sortNo) {
		this.sortNo = sortNo;
	}

	public String getMenuDesc() {
		return menuDesc;
	}
	public void setMenuDesc(String menuDesc) {
		this.menuDesc = menuDesc;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((action == null) ? 0 : action.hashCode());
		result = prime * result + ((menuDesc == null) ? 0 : menuDesc.hashCode());
		result = prime * result + id;
		result = prime * result + ((menuName == null) ? 0 : menuName.hashCode());
		result = prime * result + menuNo;
		result = prime * result + parentId;
		result = prime * result + sortNo;
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
		Menu other = (Menu) obj;
		if (action == null) {
			if (other.action != null)
				return false;
		} else if (!action.equals(other.action))
			return false;
		if (menuDesc == null) {
			if (other.menuDesc != null)
				return false;
		} else if (!menuDesc.equals(other.menuDesc))
			return false;
		if (id != other.id)
			return false;
		if (menuName == null) {
			if (other.menuName != null)
				return false;
		} else if (!menuName.equals(other.menuName))
			return false;
		if (menuNo != other.menuNo)
			return false;
		if (parentId != other.parentId)
			return false;
		if (sortNo != other.sortNo)
			return false;
		return true;
	}
	@Override
	public String toString() {
		return "Menu [id=" + id + ", menuNo=" + menuNo + ", menuName=" + menuName + ", parentId=" + parentId
				+ ", action=" + action + ", sortNo=" + sortNo + ", desc=" + menuDesc + "]";
	}


}
