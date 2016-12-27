package com.cz.txl.model;

public class User {
	private int id;
	private String username;
	private String password;
	private int age;
	private String gender;
	private String phone;
	private String email;
	private int isDelete;
	private long createTime;
	private long updateTime;
	private String userInfo;//用户简介
	private int userStatus;//用户状态
	//通过在User中引用Role对象的引用来表示用户与角色之间的一对一关系,一个用户只能有一个角色
	private Role role;

	public User() {
		super();
	}



	public User(String username, String password, int age, String gender, String phone, String email, int isDelete,
				long createTime, long updateTime) {
		super();
		this.username = username;
		this.password = password;
		this.age = age;
		this.gender = gender;
		this.phone = phone;
		this.email = email;
		this.isDelete = isDelete;
		this.createTime = createTime;
		this.updateTime = updateTime;
	}




	public User(int id, String username, String password, int age, String gender, String phone, String email,
				int isDelete, long createTime, long updateTime) {
		super();
		this.id = id;
		this.username = username;
		this.password = password;
		this.age = age;
		this.gender = gender;
		this.phone = phone;
		this.email = email;
		this.isDelete = isDelete;
		this.createTime = createTime;
		this.updateTime = updateTime;
	}

	public User(int id, String username, String password, int age, String gender, String phone, String email,
				int isDelete, long createTime, long updateTime, String userInfo, int userStatus, Role role) {
		super();
		this.id = id;
		this.username = username;
		this.password = password;
		this.age = age;
		this.gender = gender;
		this.phone = phone;
		this.email = email;
		this.isDelete = isDelete;
		this.createTime = createTime;
		this.updateTime = updateTime;
		this.userInfo = userInfo;
		this.userStatus = userStatus;
		this.role = role;
	}



	public int getId() {
		return id;
	}



	public void setId(int id) {
		this.id = id;
	}



	public String getUsername() {
		return username;
	}



	public void setUsername(String username) {
		this.username = username;
	}



	public String getPassword() {
		return password;
	}



	public void setPassword(String password) {
		this.password = password;
	}



	public int getAge() {
		return age;
	}



	public void setAge(int age) {
		this.age = age;
	}



	public String getGender() {
		return gender;
	}



	public void setGender(String gender) {
		this.gender = gender;
	}



	public String getPhone() {
		return phone;
	}



	public void setPhone(String phone) {
		this.phone = phone;
	}



	public String getEmail() {
		return email;
	}



	public void setEmail(String email) {
		this.email = email;
	}



	public int getIsDelete() {
		return isDelete;
	}



	public void setIsDelete(int isDelete) {
		this.isDelete = isDelete;
	}



	public long getCreateTime() {
		return createTime;
	}



	public void setCreateTime(long createTime) {
		this.createTime = createTime;
	}



	public long getUpdateTime() {
		return updateTime;
	}



	public void setUpdateTime(long updateTime) {
		this.updateTime = updateTime;
	}

	public Role getRole() {
		return role;
	}

	public void setRole(Role role) {
		this.role = role;
	}

	public String getUserInfo() {
		return userInfo;
	}



	public void setUserInfo(String userInfo) {
		this.userInfo = userInfo;
	}



	public int getUserStatus() {
		return userStatus;
	}



	public void setUserStatus(int userStatus) {
		this.userStatus = userStatus;
	}



	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + age;
		result = prime * result + (int) (createTime ^ (createTime >>> 32));
		result = prime * result + ((email == null) ? 0 : email.hashCode());
		result = prime * result + ((gender == null) ? 0 : gender.hashCode());
		result = prime * result + id;
		result = prime * result + isDelete;
		result = prime * result + ((password == null) ? 0 : password.hashCode());
		result = prime * result + ((phone == null) ? 0 : phone.hashCode());
		result = prime * result + ((role == null) ? 0 : role.hashCode());
		result = prime * result + (int) (updateTime ^ (updateTime >>> 32));
		result = prime * result + ((userInfo == null) ? 0 : userInfo.hashCode());
		result = prime * result + userStatus;
		result = prime * result + ((username == null) ? 0 : username.hashCode());
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
		User other = (User) obj;
		if (age != other.age)
			return false;
		if (createTime != other.createTime)
			return false;
		if (email == null) {
			if (other.email != null)
				return false;
		} else if (!email.equals(other.email))
			return false;
		if (gender == null) {
			if (other.gender != null)
				return false;
		} else if (!gender.equals(other.gender))
			return false;
		if (id != other.id)
			return false;
		if (isDelete != other.isDelete)
			return false;
		if (password == null) {
			if (other.password != null)
				return false;
		} else if (!password.equals(other.password))
			return false;
		if (phone == null) {
			if (other.phone != null)
				return false;
		} else if (!phone.equals(other.phone))
			return false;
		if (role == null) {
			if (other.role != null)
				return false;
		} else if (!role.equals(other.role))
			return false;
		if (updateTime != other.updateTime)
			return false;
		if (userInfo == null) {
			if (other.userInfo != null)
				return false;
		} else if (!userInfo.equals(other.userInfo))
			return false;
		if (userStatus != other.userStatus)
			return false;
		if (username == null) {
			if (other.username != null)
				return false;
		} else if (!username.equals(other.username))
			return false;
		return true;
	}



	@Override
	public String toString() {
		return "User [id=" + id + ", username=" + username + ", password=" + password + ", age=" + age + ", gender="
				+ gender + ", phone=" + phone + ", email=" + email + ", isDelete=" + isDelete + ", createTime="
				+ createTime + ", updateTime=" + updateTime + ", userInfo=" + userInfo + ", userStatus=" + userStatus
				+ ", role=" + role + "]";
	}

}
