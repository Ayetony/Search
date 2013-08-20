package com.yzc.action;

import java.io.File;
import java.io.StringReader;
import java.util.Date;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.document.Document;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.queryParser.MultiFieldQueryParser;
import org.apache.lucene.queryParser.QueryParser;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.search.highlight.Highlighter;
import org.apache.lucene.search.highlight.QueryScorer;
import org.apache.lucene.search.highlight.SimpleHTMLFormatter;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.util.Version;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.interceptor.SessionAware;
import org.wltea.analyzer.lucene.IKAnalyzer;

import com.opensymphony.xwork2.ActionSupport;
import com.yzc.all.Source;
import com.yzc.bean.Result;
import com.yzc.bean.User;

public class DoSearchAction extends ActionSupport implements SessionAware{
	private String input;
	private String splitstring;
	private Result result;
	private Map session;
	public String execute() throws Exception{
		//session = (Map)ActionContext.getContext().get(ActionContext.SESSION);
		User user = (User) session.get("user");
		String[] level = user.getLevelid();
		int split = Integer.parseInt(splitstring);
		HttpServletRequest request = ServletActionContext.getRequest();
		String root = this.getClass().getResource("/").getPath().replaceAll("/WEB-INF/classes/"
				, "");
		result = new Result();
		Analyzer analyzer = new IKAnalyzer();
		IndexReader ireader = null;
		IndexSearcher isearcher = null;
		//索引文件夹
		Directory indexDir;
		if(Source.MUTEX == 1)
			indexDir = FSDirectory.open(new File(root + "/INDEX1"));
		else
			indexDir = FSDirectory.open(new File(root + "/INDEX2"));
		ireader = IndexReader.open(indexDir);
		isearcher = new IndexSearcher(ireader);
		//使用QueryParser查询分析器构造Query对象
		String[] fields = {"filename", "contents", "user"};
		QueryParser qp = new MultiFieldQueryParser(Version.LUCENE_36
				, fields
				, analyzer);
		qp.setDefaultOperator(QueryParser.AND_OPERATOR);
		Query query = qp.parse(input);
		long startTime = new Date().getTime();
		//搜索相似度最高的100条记录
		TopDocs topDocs = isearcher.search(query, 100);
		ScoreDoc[] scoreDocs = topDocs.scoreDocs;
		ScoreDoc[] levelDocs = new ScoreDoc[topDocs.totalHits];
		int intLevelDocs = 0;
		for(int i = 0; i < topDocs.totalHits; i++){
			Document targetDoc = isearcher.doc(scoreDocs[i].doc);
			for(int m = 0; m < level.length; m++)
				if(level[m].equals(targetDoc.get("level"))){
					levelDocs[intLevelDocs] = scoreDocs[i];
					intLevelDocs++;
					break;
				}
		}
		int doRes = split * 10 + 10 > intLevelDocs ? 
				intLevelDocs : split * 10 + 10;
		String[] tmpUser = new String[doRes - split * 10];
		String[] tmpTime = new String[doRes - split * 10];
		String[] tmpFilename = new String[doRes - split * 10];
		String[] tmpDetail = new String[doRes - split * 10];
		String[] tmpFilenameGo = new String[doRes - split * 10];
		for(int i = split * 10; i < doRes; i++){
			Document targetDoc = isearcher.doc(scoreDocs[i].doc);
			tmpFilenameGo[i - split * 10] = targetDoc.get("path");
			tmpTime[i - split * 10] = targetDoc.get("time");
			SimpleHTMLFormatter simpleHtmlFormatter = 
				new SimpleHTMLFormatter("<font color=\"red\">", "</font>");
			Highlighter highlighter = new Highlighter(
					simpleHtmlFormatter
					, new QueryScorer(query));
			TokenStream UsertokenStream = analyzer.tokenStream("text", new
					StringReader(targetDoc.get("user")));
			String UserhighlighterStr = highlighter.getBestFragment(UsertokenStream
					, targetDoc.get("user"));
			TokenStream ContentstokenStream = analyzer.tokenStream("text", new
					StringReader(targetDoc.get("contents")));
			String ContentshighlighterStr = highlighter.getBestFragment(ContentstokenStream
					, targetDoc.get("contents"));
			TokenStream FilenametokenStream = analyzer.tokenStream("text", new
					StringReader(targetDoc.get("filename")));
			String FilenamehighlighterStr = highlighter.getBestFragment(FilenametokenStream
					, targetDoc.get("filename"));
			tmpUser[i - split * 10] = UserhighlighterStr == null ? targetDoc.get("user"):UserhighlighterStr;
			tmpDetail[i - split * 10] = ContentshighlighterStr == null ? targetDoc.get("contents"):ContentshighlighterStr 
					+ '"';
			tmpFilename[i - split * 10] = FilenamehighlighterStr == null ? targetDoc.get("filename"):FilenamehighlighterStr 
					+ '"';
		}
		result.setMessage(input);
		result.setTotalHit(intLevelDocs);
		result.setUser(tmpUser);
		result.setUploadTime(tmpTime);
		result.setFilename(tmpFilename);
		result.setDetail(tmpDetail);
		result.setFilenameGo(tmpFilenameGo);
		long endTime = new Date().getTime();
		result.setTime(endTime-startTime);
		if(isearcher != null)
			isearcher.close();
		if(ireader != null)
			ireader.close();
		if(indexDir != null)
			indexDir.close();
		request.setAttribute("Result", result);
		return "success";
	}
	public void validate(){
		if(null == input || input.length() == 0)
			this.addFieldError("null", "");
	}
	public void setInput(String input){
		this.input = input;
	}
	public void setSplitstring(String splitstring){
		this.splitstring = splitstring;
	}
	@Override
	public void setSession(Map session) {
		// TODO Auto-generated method stub
		this.session = session;
	}
}
