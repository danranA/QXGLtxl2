package com.cz.txl.model;

import java.util.List;

public class Role {
	private int id;
	private String roleName;//角色名称
	private int roleNo;//角色编号

	//通过在Role对象中引入List<Menu>来表示角色与菜单之间一对多的关系
	private List<Menu> menuList;
	public Role() {
		super();
	}
	public Role(String roleName, int roleNo) {
		super();
		this.roleName = roleName;
		this.roleNo = roleNo;
	}
	public Role(int id, String roleName, int roleNo) {
		super();
		this.id = id;
		this.roleName = roleName;
		this.roleNo = roleNo;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getRoleName() {
		return roleName;
	}
	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}
	public int getRoleNo() {
		return roleNo;
	}
	public void setRoleNo(int roleNo) {
		this.roleNo = roleNo;
	}

	public List<Menu> getMenuList() {
		return menuList;
	}
	public void setMenuList(List<Menu> menuList) {
		this.menuList = menuList;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + id;
		result = prime * result + ((menuList == null) ? 0 : menuList.hashCode());
		result = prime * result + ((roleName == null) ? 0 : roleName.hashCode());
		result = prime * result + roleNo;
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
		Role other = (Role) obj;
		if (id != other.id)
			return false;
		if (menuList == null) {
			if (other.menuList != null)
				return false;
		} else if (!menuList.equals(other.menuList))
			return false;
		if (roleName == null) {
			if (other.roleName != null)
				return false;
		} else if (!roleName.equals(other.roleName))
			return false;
		if (roleNo != other.roleNo)
			return false;
		return true;
	}
	@Override
	public String toString() {
		return "Role [id=" + id + ", roleName=" + roleName + ", roleNo=" + roleNo + ", menuList=" + menuList + "]";
	}
}
