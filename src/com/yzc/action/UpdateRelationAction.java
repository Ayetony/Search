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

public class UpdateRelationAction extends ActionSupport {
	private String[] updateRelation;
	private String[] relation;
	// 刷新
	private String createId;
	private String createName;
	private HashMap select = new HashMap();
	private HashMap allLevel = new HashMap();
	private HashMap<String, List<String>> groupToLevel = new HashMap<String, List<String>>();
	private String back;
	public String execute() {
		createId = "";
		createName = "";
		String tmp = "";
		DBPool pool = DBPool.getInstance();
		Connection conn = null;
		Statement stmt = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			conn = pool.getConnection();
			stmt = conn.createStatement();
			for (int i = 0; i < relation.length; i++) {
				tmp = relation[i].substring(relation[i].indexOf("@@") + 2)
						.replace("\r\n", "");
				int go = 0;
				int tmpInt = Integer.valueOf(tmp);
				if(tmpInt == 0)
					go = 0;
				else{
					while(tmpInt > 0){
						if(updateRelation[go].equals("split"))
							tmpInt--;
						go++;
					}
				}
				relation[i] = relation[i].replace("@@" + tmp, "");
				relation[i] = relation[i].replace("\r\n", "");
				stmt.executeUpdate("delete from relation where relation.GroupId='"
						+ relation[i] + "';");
				for (int j = go; j < updateRelation.length; j++){
					if(updateRelation[j].equals("split"))
						break;	
					stmt.executeUpdate("insert into relation values('"
							+ relation[i] + "','" + updateRelation[j]
							+ "');");
				}
			}
			if (stmt != null)
				stmt.close();
			if (conn != null)
				conn.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		// 以下为刷新部分
		String sql2 = "SELECT groupinfor.GroupId,groupinfor.GroupName from groupinfor;";
		String sql3 = "SELECT level.LevelId,level.LevelName from level;";
		String sql4 = "SELECT relation.GroupId,relation.LevelId from relation;";
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
		try{
			conn = pool.getConnection();
			pstmt = conn.prepareStatement(sql3);
			rs = pstmt.executeQuery();
			while(rs.next()){
				allLevel.put(String.valueOf(rs.getInt(1)), rs.getString(2));
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
			pstmt = conn.prepareStatement(sql4);
			rs = pstmt.executeQuery();
			while(rs.next()){
				List<String> tmp1 = new ArrayList<String>();
				if(groupToLevel.containsKey(String.valueOf(rs.getInt(1)))){
					groupToLevel.get(String.valueOf(rs.getInt(1))).add(String.valueOf(rs.getInt(2)));
				}
				else{
					tmp1.add(String.valueOf(rs.getInt(2)));
					groupToLevel.put(String.valueOf(rs.getInt(1)), tmp1);
				}
			}
			if(pstmt != null)
				pstmt.close();
			if(conn != null)
				conn.close();
		} catch(Exception e){
			e.printStackTrace();
		}
		back = "修改成功！";
		return "success";
	}

	public String[] getUpdateRelation() {
		return updateRelation;
	}

	public void setUpdateRelation(String[] updateRelation) {
		this.updateRelation = updateRelation;
	}

	public String[] getRelation() {
		return relation;
	}

	public void setRelation(String[] relation) {
		this.relation = relation;
	}

	public String getBack() {
		return back;
	}

	public void setBack(String back) {
		this.back = back;
	}

	public HashMap getAllLevel() {
		return allLevel;
	}

	public void setAllLevel(HashMap allLevel) {
		this.allLevel = allLevel;
	}

	public HashMap<String, List<String>> getGroupToLevel() {
		return groupToLevel;
	}

	public void setGroupToLevel(HashMap<String, List<String>> groupToLevel) {
		this.groupToLevel = groupToLevel;
	}
	public HashMap getSelect() {
		return select;
	}
	public void setSelect(HashMap select) {
		this.select = select;
	}

	public String getCreateId() {
		return createId;
	}

	public void setCreateId(String createId) {
		this.createId = createId;
	}

	public String getCreateName() {
		return createName;
	}

	public void setCreateName(String createName) {
		this.createName = createName;
	}
}
