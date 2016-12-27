package com.cz.txl.model;

//该modle对象对应的是数据表t_schedule
public class Schedule {
	private int id;
	private String schName;//行程标题
	private String schTime;//行程时间,从前台传过来的时间是string.最后存入到数据库中的是bigint
	private String schDesc;
	private int isFinish;
	private int isDelete;
	private int isSendMail;
	private long createTime;
	private long updateTime;

	public Schedule() {
		super();
	}

	public Schedule(int id, String schName, String schTime, String schDesc, int isFinish, int isDelete, int isSendMail,
					long createTime, long updateTime) {
		super();
		this.id = id;
		this.schName = schName;
		this.schTime = schTime;
		this.schDesc = schDesc;
		this.isFinish = isFinish;
		this.isDelete = isDelete;
		this.isSendMail = isSendMail;
		this.createTime = createTime;
		this.updateTime = updateTime;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getSchName() {
		return schName;
	}

	public void setSchName(String schName) {
		this.schName = schName;
	}

	public String getSchTime() {
		return schTime;
	}

	public void setSchTime(String schTime) {
		this.schTime = schTime;
	}

	public String getSchDesc() {
		return schDesc;
	}

	public void setSchDesc(String schDesc) {
		this.schDesc = schDesc;
	}

	public int getIsFinish() {
		return isFinish;
	}

	public void setIsFinish(int isFinish) {
		this.isFinish = isFinish;
	}

	public int getIsDelete() {
		return isDelete;
	}

	public void setIsDelete(int isDelete) {
		this.isDelete = isDelete;
	}

	public int getIsSendMail() {
		return isSendMail;
	}

	public void setIsSendMail(int isSendMail) {
		this.isSendMail = isSendMail;
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

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (int) (createTime ^ (createTime >>> 32));
		result = prime * result + id;
		result = prime * result + isDelete;
		result = prime * result + isFinish;
		result = prime * result + isSendMail;
		result = prime * result + ((schDesc == null) ? 0 : schDesc.hashCode());
		result = prime * result + ((schName == null) ? 0 : schName.hashCode());
		result = prime * result + ((schTime == null) ? 0 : schTime.hashCode());
		result = prime * result + (int) (updateTime ^ (updateTime >>> 32));
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
		Schedule other = (Schedule) obj;
		if (createTime != other.createTime)
			return false;
		if (id != other.id)
			return false;
		if (isDelete != other.isDelete)
			return false;
		if (isFinish != other.isFinish)
			return false;
		if (isSendMail != other.isSendMail)
			return false;
		if (schDesc == null) {
			if (other.schDesc != null)
				return false;
		} else if (!schDesc.equals(other.schDesc))
			return false;
		if (schName == null) {
			if (other.schName != null)
				return false;
		} else if (!schName.equals(other.schName))
			return false;
		if (schTime == null) {
			if (other.schTime != null)
				return false;
		} else if (!schTime.equals(other.schTime))
			return false;
		if (updateTime != other.updateTime)
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Schedule [id=" + id + ", schName=" + schName + ", schTime=" + schTime + ", schDesc=" + schDesc
				+ ", isFinish=" + isFinish + ", isDelete=" + isDelete + ", isSendMail=" + isSendMail + ", createTime="
				+ createTime + ", updateTime=" + updateTime + "]";
	}


}
