package com.yzc.action;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.opensymphony.xwork2.ActionSupport;
import com.yzc.bean.DBPool;
import com.yzc.bean.Manager;

public class ManagerAction extends ActionSupport{
	private List list = new ArrayList();
	private HashMap select = new HashMap();
	public String execute(){
		DBPool pool = DBPool.getInstance();
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
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
}
