package com.yzc.action;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;

import org.apache.struts2.interceptor.SessionAware;

import com.opensymphony.xwork2.ActionSupport;
import com.yzc.all.Source;
import com.yzc.bean.DBPool;
import com.yzc.bean.User;

public class LoginAction extends ActionSupport implements SessionAware{
	private String id;
	private String password;
	private User user;
	private String msg;
	private Map session;
	public HashMap levelSelection;
	public static ResourceBundle apps = ResourceBundle
			.getBundle("public");
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	
	public String execute() throws Exception{
		levelSelection = new HashMap();
		user = new User();
		DBPool pool = DBPool.getInstance();
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = "SELECT A.UserId,A.UserName,B.GroupId,B.GroupName,D.LevelId,D.LevelName " +
				"from login as A," +
				"groupinfor as B," +
				"relation as C," +
				"level as D " +
				"where A.groupid=B.groupid " +
				"and A.UserId='" + id + "' " + 
				"and A.password='" + password + "' " +
				"and B.groupid = C.GroupId " + 
				"and D.LevelId = C.LevelId;";
		try{
			conn = pool.getConnection();
			pstmt = conn.prepareStatement(sql);
			rs = pstmt.executeQuery();
			if(rs == null)
				return "fail";
			rs.last();
			int i = rs.getRow();
			String[] tmp = new String[i];
			String[] intmp = new String[i];
			rs.first();
			if(rs != null){
				int j = 0;
				user.setId(rs.getString(1));
				user.setName(rs.getString(2));
				user.setGroupid(rs.getInt(3));
				user.setGroupname(rs.getString(4));
				tmp[j] = rs.getString(6);
				intmp[j] = rs.getString(5);
				levelSelection.put(intmp[j], tmp[j]);
				while(rs.next()){
					j++;
					tmp[j] = rs.getString(6);
					intmp[j] = rs.getString(5);
					levelSelection.put(intmp[j], tmp[j]);
				}
				user.setLevelid(intmp);
				user.setLevelinfor(tmp);
				session.put("levelSelection", levelSelection);
		        session.put("user", user);
		        session.put("name", user.getName());
		        session.put("allIndex", Source.ALL_INDEX);
		        session.put("addIndex", Source.ADD_INDEX);
				return "success";
			}
		} catch (SQLException e){
			e.printStackTrace();
		} finally{
			try{
				rs.close();
				pstmt.close();
				conn.close();
			} catch(SQLException e1){
				e1.printStackTrace();
			}
		}
		msg = "您的用户名或者密码输入有误，请返回重新登陆。";
		return "fail";
	}
	public String getMsg() {
		return msg;
	}
	@Override
	public void setSession(Map session) {
		// TODO Auto-generated method stub
		this.session = session;
	}
	public void validate(){
		if(id.length() == 0)
			this.addFieldError("id", "用户名不能为空");
		if(password.length() == 0)
			this.addFieldError("password", "密码不能为空");
	}
}
