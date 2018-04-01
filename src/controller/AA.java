package controller;

import javax.servlet.http.HttpSession;

import com.db.DiaryDBMyBatis;
import com.db.DiaryDataBean;

public class AA {
	public String asd {
		/*DiaryDataBean diary = new DiaryDataBean();
		DiaryDBMyBatis diaPro = DiaryDBMyBatis.getInstance();
		
		// 6) fileSave 폴더 webcontent폴더 안에 만들기
		String realFolder = ""; //웹 어플리케이션상의 절대경로
		String encType = "euc-kr"; // 인코딩 타입
		int maxSize = 5 *1024 * 1024; // 최대 업로드 될 파일 크기 .. 5MB
		ServletContext context = req.getServletContext();
		realFolder =context.getRealPath("fileSave");
		MultipartRequest multi = null;
		
		// DefaultFileRenamePolicy는 중복된 파일 업로드할때 자동으로 Rename / aaa있으면 aaa(1)로
		multi = new MultipartRequest(req, realFolder, maxSize, encType,  new DefaultFileRenamePolicy());
		
		Enumeration files = multi.getFileNames();
		String[] filename = new String[5];
		File[] file = new File[5];
		int index = 0;
		
		String[] original = new String[5];
		String[] type = new String[5];
		
		// 7) 
		while (files.hasMoreElements()) { // 만약 파일이 다수면 if를 while로..
			String name = (String) files.nextElement();
			filename[index] = multi.getFilesystemName(name);
			original[index] = multi.getOriginalFileName(name);
			type[index] = multi.getContentType(name);
			file[index] = multi.getFile(name);
			index++;
		}
		
		int num = Integer.parseInt(multi.getParameter("num"));
		
		String pageNum = multi.getParameter("pageNum");
		if (pageNum == null || pageNum == "") {pageNum = "1";}
		
		String diaryid = multi.getParameter("diaryid");
		if (diaryid==null) diaryid = "Main";
		
		try {
			diary.setNum(num);
			diary.setEmail(multi.getParameter("email"));
			diary.setSubject(multi.getParameter("subject"));
			diary.setContent(multi.getParameter("content"));
			diary.setDiaryid(multi.getParameter("diaryid"));
			diary.setFilename0(multi.getParameter("filename0"));
			diary.setFilename1(multi.getParameter("filename1"));
			diary.setFilename2(multi.getParameter("filename2"));
			diary.setFilename3(multi.getParameter("filename3"));
			diary.setFilename4(multi.getParameter("filename4"));
			diary.setIp(req.getRemoteAddr());
			
			if (file[4] != null) {
				diary.setFilename0(filename[4]);
				diary.setFilesize0((int) file[4].length()); 
				
			} 
			
			if (file[3] != null) {
				diary.setFilename1(filename[3]);
				diary.setFilesize1((int) file[3].length()); 
				
			} 
			
			if (file[2] != null) {
				diary.setFilename2(filename[2]);
				diary.setFilesize2((int) file[2].length()); 
				
			}
			
			if (file[1] != null) {
				diary.setFilename3(filename[1]);
				diary.setFilesize3((int) file[1].length()); 
				
			} 
			
			if (file[0] != null) {
				diary.setFilename4(filename[0]);
				diary.setFilesize4((int) file[0].length()); 
				
			} else {}
			
			int chk = diaPro.updateDiary(diary);
			
			req.setAttribute("chk", chk);
			req.setAttribute("pageNum", pageNum);
			req.setAttribute("diaryid", diaryid);
			
			System.out.println("수정여부: " + chk);
			System.out.println(diary);
			
			
		} catch (Exception e) {e.printStackTrace();}*/
	}
	
	public String user_writePro() {
		HttpSession session = req.getSession();
		DiaryDataBean diary = new DiaryDataBean();
		DiaryDBMyBatis dbPro = DiaryDBMyBatis.getInstance();
		
		// 6) fileSave 폴더 webcontent폴더 안에 만들기
		String realFolder = ""; // 웹 어플리케이션상의 절대경로
		String encType = "euc-kr"; // 인코딩 타입
		int maxSize = 5 * 1024 * 1024; // 최대 업로드 될 파일 크기 .. 5MB
		ServletContext context = req.getServletContext();
		realFolder = context.getRealPath("fileSave");
		MultipartRequest multi = null;

		// DefaultFileRenamePolicy는 중복된 파일 업로드할때 자동으로 Rename / aaa있으면 aaa(1)로
		multi = new MultipartRequest(req, realFolder, maxSize, encType, new DefaultFileRenamePolicy());

		Enumeration files = multi.getFileNames();
		String[] filename = new String[5];
		File[] file = new File[5];
		int index = 0;
		
		String[] original = new String[5];
		String[] type = new String[5];
		
		// 7) 
		while (files.hasMoreElements()) { // 만약 파일이 다수면 if를 while로..
			String name = (String) files.nextElement();
			filename[index] = multi.getFilesystemName(name);
			original[index] = multi.getOriginalFileName(name);
			type[index] = multi.getContentType(name);
			file[index] = multi.getFile(name);
			index++;
		}
		// =================================================
		
		String pageNum = multi.getParameter("pageNum");
		if (pageNum == null || pageNum == "") {pageNum = "1";}
		
		String diaryid = multi.getParameter("diaryid");
		if (diaryid==null) diaryid = "Main";
		
		//diary.setNum(num);
		diary.setEmail((String)session.getAttribute("sessionID")); 
		diary.setSubject(multi.getParameter("subject"));
		diary.setContent(multi.getParameter("content"));
		diary.setDiaryid(multi.getParameter("diaryid"));
		diary.setIp(req.getRemoteAddr());
		
		// 8)
		if (file[4] != null) {
			diary.setFilename0(filename[4]);
			diary.setFilesize0((int) file[4].length()); 
			
		} 
		
		if (file[3] != null) {
			diary.setFilename1(filename[3]);
			diary.setFilesize1((int) file[3].length()); 
			
		} 
		
		if (file[2] != null) {
			diary.setFilename2(filename[2]);
			diary.setFilesize2((int) file[2].length()); 
			
		}
		
		if (file[1] != null) {
			diary.setFilename3(filename[1]);
			diary.setFilesize3((int) file[1].length()); 
			
		} 
		
		if (file[0] != null) {
			diary.setFilename4(filename[0]);
			diary.setFilesize4((int) file[0].length()); 
			
		} 
			
		// =================================================
		
		System.out.println(diary);
		//9) insertDiary 메소드 수정 (복수 개로 할시 필수 수정)
		dbPro.insertDiary(diary);
		
		req.setAttribute("pageNum", pageNum);
		res.sendRedirect("user_main?pageNum="+pageNum+"&diaryid="+diaryid);
	}
}
