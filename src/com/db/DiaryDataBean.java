package com.db;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DiaryDataBean {
	private int num; // 일기 고유번호
	private String email; // 회원 아이디 (회원마다 일기쓸수있게 받아 옴.)
	private String diaryid; // 일기장 고유 명 (일기장 리스트) 
	private String subject; // 제목
//	private String cdate;
	private Date cdate; // 날짜
	private String content; // 내용
	private String ip; // 아이피
	// 파일 업로드
	private String filename0; // 파일이름
	private int filesize0; // 파일크기
	private String filename1;
	private int filesize1; 
	private String filename2; 
	private int filesize2; 
	private String filename3; 
	private int filesize3;
	private String filename4; 
	private int filesize4;
	
	public int getNum() {
		return num;
	}
	public void setNum(int num) {
		this.num = num;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getDiaryid() {
		return diaryid;
	}
	public void setDiaryid(String diaryid) {
		this.diaryid = diaryid;
	}
	public String getSubject() {
		return subject;
	}
	public void setSubject(String subject) {
		this.subject = subject;
	}
	/*public String getCdate() {
		return cdate;
	}
	public void setCdate(String cdate) {
		this.cdate = cdate;
	}*/
	public Date getCdate() {
		return cdate;
	}
	public void setCdate(Date cdate) {
		this.cdate = cdate;
	}
	public void setCdate(String cdate) throws ParseException {
	    DateFormat df = new SimpleDateFormat("dd-MM-yyyy"); 
	    this.cdate = df.parse(cdate);  
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getIp() {
		return ip;
	}
	public void setIp(String ip) {
		this.ip = ip;
	}
	
	
	public String getFilename0() {
		return filename0;
	}
	public void setFilename0(String filename0) {
		this.filename0 = filename0;
	}
	public int getFilesize0() {
		return filesize0;
	}
	public void setFilesize0(int filesize0) {
		this.filesize0 = filesize0;
	}
	public String getFilename1() {
		return filename1;
	}
	public void setFilename1(String filename1) {
		this.filename1 = filename1;
	}
	public int getFilesize1() {
		return filesize1;
	}
	public void setFilesize1(int filesize1) {
		this.filesize1 = filesize1;
	}
	public String getFilename2() {
		return filename2;
	}
	public void setFilename2(String filename2) {
		this.filename2 = filename2;
	}
	public int getFilesize2() {
		return filesize2;
	}
	public void setFilesize2(int filesize2) {
		this.filesize2 = filesize2;
	}
	public String getFilename3() {
		return filename3;
	}
	public void setFilename3(String filename3) {
		this.filename3 = filename3;
	}
	public int getFilesize3() {
		return filesize3;
	}
	public void setFilesize3(int filesize3) {
		this.filesize3 = filesize3;
	}
	public String getFilename4() {
		return filename4;
	}
	public void setFilename4(String filename4) {
		this.filename4 = filename4;
	}
	public int getFilesize4() {
		return filesize4;
	}
	public void setFilesize4(int filesize4) {
		this.filesize4 = filesize4;
	}
	
	@Override
	public String toString() {
		return "DiaryDataBean [num=" + num + ", email=" + email + ", diaryid=" + diaryid + ", subject=" + subject
				+ ", cdate=" + cdate + ", content=" + content + ", ip=" + ip + ", filename0=" + filename0
				+ ", filesize0=" + filesize0 + ", filename1=" + filename1 + ", filesize1=" + filesize1 + ", filename2="
				+ filename2 + ", filesize2=" + filesize2 + ", filename3=" + filename3 + ", filesize3=" + filesize3
				+ ", filename4=" + filename4 + ", filesize4=" + filesize4 + "]";
	}
	
	
	
	
	
	
	
	
	
	
	
}
