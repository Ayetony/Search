package com.yzc.action;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.opensymphony.xwork2.ActionSupport;
import com.yzc.bean.DBPool;
import com.yzc.bean.Manager;

public class UpdateUserAction extends ActionSupport{
	private String[] check;
	private String[] delete;
	private String[] groupId;
	//这是刷新的
	private List list = new ArrayList();
	private HashMap select = new HashMap();
	private String back;
	public String execute(){
		String tmp = "";
		DBPool pool = DBPool.getInstance();
		Connection conn = null;
		Statement stmt = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try{
			conn = pool.getConnection();
			stmt = conn.createStatement();
			for(int i = 0; i < check.length; i++){
				tmp = check[i].substring(check[i].indexOf("@@") + 2).replace("\r\n", "");
				check[i] = check[i].replace("@@" + tmp, "");
				check[i] = check[i].replace("\r\n", "");
				stmt.executeUpdate("update login set login.groupid='" + groupId[Integer.parseInt(tmp)] + "' where login.userid='" + check[i] + "';");
			}
			if(stmt != null)
				stmt.close();
			if(conn != null)
				conn.close();
		} catch(Exception e){
			e.printStackTrace();
		}
		if(null != delete && delete.length != 0){
			try{
				conn = pool.getConnection();
				stmt = conn.createStatement();
				for(int i = 0; i < delete.length; i++){
					delete[i] = delete[i].replace("\r\n", "");
					for(int j = 0; j < check.length; j++)
						if(delete[i].equals(check[j]))
							stmt.executeUpdate("delete from login where login.userid='" + delete[i] + "';");
				}
				if(stmt != null)
					stmt.close();
				if(conn != null)
					conn.close();
			} catch(Exception e){
				e.printStackTrace();
			}
		}
		//以下为刷新部分
		String sql1 = "SELECT A.UserId,A.UserName,B.GroupId,B.GroupName " +
				"from login as A," +
				"groupinfor as B " +
				"where A.groupid=B.groupid;";
		String sql2 = "SELECT groupinfor.GroupId,groupinfor.GroupName from groupinfor;";
		String sql3 = "SELECT level.LevelId,level.LevelName from level;";
		String sql4 = "SELECT relation.GroupId,relation.LevelId from relation;";
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
		return "success";
	 }
	public void validate(){
		if(null == check)
			;
	}
	public String[] getCheck() {
		return check;
	}
	public void setCheck(String[] check) {
		this.check = check;
	}
	public String[] getDelete() {
		return delete;
	}
	public void setDelete(String[] delete) {
		this.delete = delete;
	}
	public String[] getGroupId() {
		return groupId;
	}
	public void setGroupId(String[] groupId) {
		this.groupId = groupId;
	}
	public List getList() {
		return list;
	}
	public void setList(List list) {
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
