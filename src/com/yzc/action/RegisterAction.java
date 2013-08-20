package com.yzc.action;

import java.io.UnsupportedEncodingException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.opensymphony.xwork2.ActionSupport;
import com.yzc.bean.DBPool;
import com.yzc.bean.Manager;

public class RegisterAction extends ActionSupport{
	private String id;
	private String name;
	private String password;
	private String password_val;
	private String group;
	//刷新
	private List list = new ArrayList();
	private HashMap select = new HashMap();
	private String back;
	public String execute(){
		String result = "success";
		if(null == group)
			group = String.valueOf(6);
		else
			result += "_admin";
		DBPool pool = DBPool.getInstance();
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = "insert into login values(?, ?, ?, ?);";
		try {
			sql = new String(sql.getBytes("UTF-8"));
		} catch (UnsupportedEncodingException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		try{
			conn = pool.getConnection();
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, id);
			pstmt.setString(2, password);
			pstmt.setInt(3, Integer.parseInt(group));
			pstmt.setString(4, name);
			pstmt.executeUpdate();
		} catch(Exception e){
			e.printStackTrace();
		}
		try {
			if(pstmt != null)
				pstmt.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			if(conn != null)
				conn.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//刷新
		if(result.equals("success_admin")){
			String sql1 = "SELECT A.UserId,A.UserName,B.GroupId,B.GroupName " +
					"from login as A," +
					"groupinfor as B " +
					"where A.groupid=B.groupid;";
			String sql2 = "SELECT groupinfor.GroupId,groupinfor.GroupName from groupinfor;";
			try{
				conn = pool.getConnection();
				pstmt = conn.prepareStatement(sql1);
				rs = pstmt.executeQuery();
				while(rs.next()){
					Manager manager = new Manager();
					manager.setId(rs.getString(1));
					manager.setName(rs.getString(2));
					manager.setGroupId(rs.getString(3));
					manager.setGroupName(rs.getString(4));
					list.add(manager);
				}
				if(pstmt != null)
					pstmt.close();
				if(conn != null)
					conn.close();
			} catch(Exception e){
				e.printStackTrace();
			}
			try{
				conn = pool.getConnection();
				pstmt = conn.prepareStatement(sql2);
				rs = pstmt.executeQuery();
				while(rs.next()){
					select.put(String.valueOf(rs.getInt(1)), rs.getString(2));
				}
				if(pstmt != null)
					pstmt.close();
				if(conn != null)
					conn.close();
			} catch(Exception e){
				e.printStackTrace();
			}
			back = "添加成功！";
		}
		return result;
	}
	public void validate(){
		if(null == group){
			if(null == id || id.length() == 0)
				this.addFieldError("id", "用户名不能为空");
			if(null == name || name.length() == 0)
				this.addFieldError("id", "用户姓名不能为空");
			if(null == password || password.length() == 0)
				this.addFieldError("id", "用户密码不能为空");
			if(null == password_val || password_val.length() == 0)
				this.addFieldError("id", "用户重复密码不能为空");
			if(null != password && null != password_val && !password.equals(password_val))
				this.addFieldError("id", "请确认两次输入的密码是否一致");
		}
	}
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
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getPassword_val() {
		return password_val;
	}
	public void setPassword_val(String password_val) {
		this.password_val = password_val;
	}
	public String getGroup() {
		return group;
	}
	public void setGroup(String group) {
		this.group = group;
	}
	public List getList(){
		return this.list;
	}
	public void setList(List list){
		this.list = list;
	}
	public HashMap getSelect() {
		return select;
	}
	public void setSelect(HashMap select) {
		this.select = select;
	}
	public String getBack() {
		return back;
	}
	public void setBack(String back) {
		this.back = back;
	}
}
