package com.yzc.bean;

import java.util.List;

public class User {
	private String id;
	private String name;
	private String groupname;
	private String[] levelinfor;
	private String[] levelid;
	private int groupid;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getGroupname() {
		return groupname;
	}
	public void setGroupname(String groupname) {
		this.groupname = groupname;
	}
	public String[] getLevelinfor() {
		return levelinfor;
	}
	public void setLevelinfor(String[] levelinfor) {
		this.levelinfor = levelinfor;
	}
	public boolean admin(){
		if(this.groupid == 1)
			return true;
		return false;
	}
	public int getGroupid() {
		return groupid;
	}
	public void setGroupid(int groupid) {
		this.groupid = groupid;
	}
	public String[] getLevelid() {
		return levelid;
	}
	public void setLevelid(String[] levelid) {
		this.levelid = levelid;
	}
}
