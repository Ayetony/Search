package com.yzc.bean;

public class Result {
	private String Message;
	private String[] Filename;
	private String[] FilenameGo;
	private String[] Detail;
	private String[] User;
	private String[] UploadTime;
	private int TotalHit;
	private long Time;
	public void setMessage(String msg){
		this.Message = msg;
	}
	public void setFilename(String[] msg){
		this.Filename = msg;
	}
	public void setFilenameGo(String[] msg){
		this.FilenameGo = msg;
	}
	public void setDetail(String[] msg){
		this.Detail = msg;
	}
	public void setTotalHit(int msg){
		this.TotalHit = msg;
	}
	public void setTime(long msg){
		this.Time = msg;
	}
	public String getMessage(){
		return this.Message;
	}
	public String[] getFilename(){
		return this.Filename;
	}
	public String[] getFilenameGo(){
		return this.FilenameGo;
	}
	public String[] getDetail(){
		return this.Detail;
	}
	public int getTotalHit(){
		return this.TotalHit;
	}
	public long getTime(){
		return this.Time;
	}
	public String[] getUser() {
		return User;
	}
	public void setUser(String[] User) {
		this.User = User;
	}
	public String[] getUploadTime() {
		return UploadTime;
	}
	public void setUploadTime(String[] uploadTime) {
		UploadTime = uploadTime;
	}
}
