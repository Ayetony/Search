package com.yzc.action;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Date;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.util.Version;
import org.apache.poi.POIXMLDocument;
import org.apache.poi.POIXMLTextExtractor;
import org.apache.poi.hslf.HSLFSlideShow;
import org.apache.poi.hslf.model.Slide;
import org.apache.poi.hslf.model.TextRun;
import org.apache.poi.hslf.usermodel.SlideShow;
import org.apache.poi.hssf.extractor.ExcelExtractor;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hwpf.extractor.WordExtractor;
import org.apache.poi.openxml4j.exceptions.OpenXML4JException;
import org.apache.poi.openxml4j.opc.OPCPackage;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.xslf.extractor.XSLFPowerPointExtractor;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.poi.xwpf.extractor.XWPFWordExtractor;
import org.apache.xmlbeans.XmlException;
import org.pdfbox.cos.COSDocument;
import org.pdfbox.pdfparser.PDFParser;
import org.pdfbox.pdmodel.PDDocument;
import org.pdfbox.util.PDFTextStripper;
import org.wltea.analyzer.lucene.IKAnalyzer;
import com.yzc.all.*;

public class DoIndexAction {
	private String Time;
	public String getTime() {
		return Time;
	}

	public void setTime(String time) {
		this.Time = time;
	}
	
	public String execute() throws Exception{
		String root = this.getClass().getResource("/").getPath().replaceAll("/WEB-INF/classes/"
				, "");
		//索引文件夹
		Directory indexDir;
		if(Source.MUTEX == 1)
			indexDir = FSDirectory.open(new File(root + "/INDEX2"));
		else
			indexDir = FSDirectory.open(new File(root + "/INDEX1"));
		//数据文件夹
		Analyzer luceneAnalyzer = new IKAnalyzer();
		IndexWriterConfig config = new IndexWriterConfig(Version.LUCENE_36,
				luceneAnalyzer);
		IndexWriter indexWriter = 
				new IndexWriter(indexDir, config);
		long startTime = new Date().getTime();
		File file = new File(root + "/DATA", "FileList.txt");
		BufferedReader br = new BufferedReader(new FileReader(file));
		this.doIndex(br, indexWriter);
		indexWriter.close();
		long endTime = new Date().getTime();
		Time = "为" + root + "创建索引"
				+ "共花费时间" + (endTime-startTime) + "毫秒";
		Source.MUTEX = Source.MUTEX > 1 ? 1 : 2;
		return "success";
	}
	protected void doIndex(BufferedReader br, IndexWriter indexWriter) throws Exception{
		String tempString = "";
		while((tempString = br.readLine()) != null){
			String tmp1 = tempString.substring(0, tempString.indexOf("<@|||>_<|||@>"));
			tempString = tempString.substring(tempString.indexOf("<@|||>_<|||@>") + 13);
			String tmp2 = tempString.substring(0, tempString.indexOf("<@|||>_<|||@>"));
			tempString = tempString.substring(tempString.indexOf("<@|||>_<|||@>") + 13);
			String tmp3 = tempString.substring(0, tempString.indexOf("<@|||>_<|||@>"));
			tempString = tempString.substring(tempString.indexOf("<@|||>_<|||@>") + 13);
			String tmp4 = tempString.substring(0, tempString.indexOf("<@|||>_<|||@>"));
			String tmp5 = tempString.substring(tempString.indexOf("<@|||>_<|||@>") + 13);
			System.out.println("正在对文件"
					+ tmp2
					+ "建立索引");
			String format = "unknown";
			String contentString = null;
			format = tmp2.substring(tmp2.lastIndexOf('.') + 1);
			if(format == "html")
				contentString = readHTML(tmp1);
			else if(format == "doc")
				contentString = readDoc2003(tmp1);
			else if(format == "docx")
				contentString = readDoc2007(tmp1);
			else if(format == "pdf")
				contentString = readPDF(tmp1);
			else if(format == "ppt")
				contentString = readPowerPoint2003(tmp1);
			else if(format == "pptx")
				contentString = readPowerPoint2007(tmp1);
			else if(format == "xls")
				contentString = readExcel2003(tmp1);
			else if(format == "xlsx")
				contentString = readExcel2007(tmp1);
			else if(format == "txt")
				contentString = readTXT(tmp1);
			else
				contentString = "";
			Document document = new Document();
			document.add(new Field("path"
					, tmp1.substring(tmp1.lastIndexOf('/') + 1)
					, Field.Store.YES
					, Field.Index.NOT_ANALYZED));
			document.add(new Field("filename"
					, tmp2
					, Field.Store.YES
					, Field.Index.ANALYZED));
			document.add(new Field("contents"
					, contentString
					, Field.Store.YES
					, Field.Index.ANALYZED));
			document.add(new Field("level"
					, tmp3
					, Field.Store.YES
					, Field.Index.ANALYZED));
			document.add(new Field("user"
					, tmp4
					, Field.Store.YES
					, Field.Index.ANALYZED));
			document.add(new Field("time"
					, tmp5
					, Field.Store.YES
					, Field.Index.NOT_ANALYZED));
			indexWriter.addDocument(document);
		}
	}
	protected String readHTML(String path){
		StringBuffer content = new StringBuffer("");
		File file = new File(path);
		FileInputStream fis = null;
		try{
			fis = new FileInputStream(file);
			BufferedReader reader = new BufferedReader(new InputStreamReader(
					fis, "GB2312"));
			String line = null;
			while((line = reader.readLine()) != null)
				content.append(line + "/n");
			reader.close();
			fis.close();
		} catch(Exception e){
			e.printStackTrace();
		}
		return content.toString();
	}
	protected String readDoc2003(String path){
		File file = new File(path);
		FileInputStream fis = null;
		WordExtractor wordExtractor = null;
		try{
			fis = new FileInputStream(file);
			wordExtractor = new WordExtractor(fis);
			fis.close();
			return wordExtractor.getText();
		} catch(Exception e){
			e.printStackTrace();
		}
		return "";
	}
	protected String readDoc2007(String path){
		try {
			OPCPackage opcPackage = POIXMLDocument.openPackage(path);
			POIXMLTextExtractor poi = new XWPFWordExtractor(opcPackage);
			return poi.getText();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (XmlException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (OpenXML4JException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "";
	}
	protected String readExcel2003(String path){
		InputStream inputStream = null;
		try {
			inputStream = new FileInputStream(path);
			HSSFWorkbook hssf = new HSSFWorkbook(inputStream);
			ExcelExtractor excel = new ExcelExtractor(hssf);
			excel.setFormulasNotResults(true);
			excel.setIncludeSheetNames(false);
			return excel.getText();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		return "";
	}
	protected String readExcel2007(String path){
		StringBuffer stringBuffer = new StringBuffer();
		InputStream inputStream = null;
		try {
			inputStream = new FileInputStream(path);
			XSSFWorkbook xssf = new XSSFWorkbook(inputStream);
			for(int i = 0; i < xssf.getNumberOfSheets(); i++){
				XSSFSheet xSheet = xssf.getSheetAt(i);
				if(xSheet == null)
					continue;
				for(int j = 0; j < xSheet.getLastRowNum() + 1; j++){
					XSSFRow xRow = xSheet.getRow(j);
					if(xRow == null)
						continue;
					for(int k = 0; k < xRow.getLastCellNum() + 1; k++){
						XSSFCell xCell = xRow.getCell(k);
						if(xCell == null)
							continue;
						xCell.setCellType(Cell.CELL_TYPE_STRING);
						stringBuffer.append(xCell.getStringCellValue());
					}
				}
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "";
	}
	protected String readPowerPoint2003(String path){
		StringBuffer stringBuffer = new StringBuffer();
		InputStream inputStream = null;
		try {
			inputStream = new FileInputStream(path);
			SlideShow ss = new SlideShow(
					new HSLFSlideShow(inputStream));
			Slide[] slide = ss.getSlides();
			for(int i = 0; i < slide.length; i++){
				TextRun[] textRun = slide[i].getTextRuns();
				for(int j = 0; j < textRun.length; j++)
					stringBuffer.append(textRun[j].getText());
			}
			return stringBuffer.toString();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		return "";
	}
	protected String readPowerPoint2007(String path){
		try {
			OPCPackage opcPackage = POIXMLDocument.openPackage(path);
			XSLFPowerPointExtractor pptx = null;
			try {
				pptx = new XSLFPowerPointExtractor(
						opcPackage);
			} catch (XmlException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (OpenXML4JException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return pptx.getText();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "";
	}
	protected String readPDF(String path) throws IOException{
		StringBuffer stringBuffer = new StringBuffer();
		PDDocument pdfDocument = null;
		try {
			FileInputStream fis = new FileInputStream(path);
			PDFParser pdfParser = new PDFParser(fis);
			pdfParser.parse();
			pdfDocument = pdfParser.getPDDocument();
			PDFTextStripper pdfStripper = new PDFTextStripper();
			stringBuffer.append(pdfStripper.getText(pdfDocument));
			fis.close();
		} catch(IOException e){
			e.printStackTrace();
		} finally{
			if(pdfDocument != null){
				COSDocument cos = pdfDocument.getDocument();
				cos.close();
				pdfDocument.close();
			}
		}
		return stringBuffer.toString();
	}
	protected String readTXT(String path) throws IOException{
		StringBuffer stringBuffer = new StringBuffer();
		InputStream inputStream = new FileInputStream(path);
		BufferedReader bufferedReader = new BufferedReader(
				new InputStreamReader(inputStream, "GBK"));
		try{
			String line = "";
			while((line = bufferedReader.readLine()) != null)
				stringBuffer.append(line);
		} catch(FileNotFoundException e){
			e.printStackTrace();
		}
		return stringBuffer.toString();
	}
}
