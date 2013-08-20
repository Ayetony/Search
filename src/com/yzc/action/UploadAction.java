package com.yzc.action;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Properties;

import org.apache.commons.io.FileUtils;

import com.opensymphony.xwork2.ActionSupport;
import com.yzc.all.Source;


public class UploadAction extends ActionSupport{
	private File[] upload;
    private String[] uploadFileName; // 上传文件名
    private String errorMsg;
    private String levelSelection;
    private String userName;
    //private List<UploadFiles> uploadFiles = new ArrayList<UploadFiles>();
    public String execute(){
    	/*
    	flag = false;
    	Validate();
    	if(flag)
    		return "input";*/
    	String root = this.getClass().getResource("/").getPath().replaceAll("/WEB-INF/classes/"
				, "/DATA");
    	int i = 0;
    	errorMsg = "";
    	try{
    		for(i = 0; i < upload.length; i++){
    			String fileName = uploadFileName[i];//UUID.randomUUID().toString()+ fileName.substring(fileName.lastIndexOf(".")
    			String realName = fileName;
    			SimpleDateFormat df = new SimpleDateFormat("yyyy年MM月dd日HH-mm-ss");
    			String tmpTime = df.format(new Date());
    			File target = new File(root, tmpTime + "_" + realName);
    			FileUtils.copyFile(upload[i], target);
    			String txtString = root + "/" + tmpTime 
    					+ "_" + realName 
    					+ "<@|||>_<|||@>" + realName 
    					+ "<@|||>_<|||@>" + levelSelection 
    					+ "<@|||>_<|||@>" + userName
    					+ "<@|||>_<|||@>" + tmpTime + "\r\n";
    			File file = new File(root, "FileList.txt");
    			FileWriter fw = null;
    			try{
    				if(file.exists()){
    					fw = new FileWriter(file, true);
        				fw.write(txtString);
    				}
    				else{
    					file.createNewFile();
    					fw = new FileWriter(file, false);
        				fw.write(txtString);
    				}
    			} catch(IOException e){
    				e.printStackTrace();
    			} finally{
    				if(fw != null)
    					try{
    						fw.close();
    					} catch(IOException e2){
    						e2.printStackTrace();
    					}
    			}
    			errorMsg += "文件" + realName + "上传成功；<br>";
    		}
    	} catch(Exception e){
    		e.printStackTrace();
    		errorMsg = "本次成功上传" + i + "个文件，失败" 
    				+ (upload.length - i) + "个";
        	return "success";
    	}
		errorMsg = "本次成功上传" + i + "个文件，失败" 
				+ (upload.length - i) + "个";
		Source.ADD_INDEX += i;
    	return "success";
    }
    public void validate(){
    	if(upload == null || upload.length == 0){
    		this.addFieldError("fieldupload", "我们上传所支持的文件最大为100M哦");
    	}
    	if(levelSelection == null || levelSelection.length() == 0 
    			|| levelSelection.equals("-1")){
    		this.addFieldError("fieldlevelSelection", "请先选择文件的权限！");
    	}
    }
	public File[] getUpload() {
		return upload;
	}
	public void setUpload(File[] upload) {
		this.upload = upload;
	}
	public String[] getUploadFileName() {
		return uploadFileName;
	}
	public void setUploadFileName(String[] uploadFileName) {
		this.uploadFileName = uploadFileName;
	}
	public String getErrorMsg() {
		return errorMsg;
	}
	public void setErrorMsg(String errorMsg) {
		this.errorMsg = errorMsg;
	}
	public String getLevelSelection() {
		return levelSelection;
	}
	public void setLevelSelection(String levelSelection) {
		this.levelSelection = levelSelection;
	}
	public String getUserName(){
		return userName;
	}
	public void setUserName(String userName){
		this.userName = userName;
	}
}
