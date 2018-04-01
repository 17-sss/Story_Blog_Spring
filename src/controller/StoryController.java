package controller;

import java.io.FileOutputStream;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;

import com.db.UserDBMyBatis;
import com.db.UserDataBean;

@Controller
@RequestMapping("/story")
public class StoryController {
	UserDBMyBatis usPro = UserDBMyBatis.getInstance();

	
	@RequestMapping("/index")
	public String index(HttpServletRequest req) { 
		/*String msg= req.getParameter("msg");
        
        if(msg!=null && msg.equals("0")) {
           System.out.println("<script>alert('��й�ȣ�� Ȯ���� �ּ���.');</script>");
        }
        else if(msg!=null && msg.equals("-1")) {    
        	System.out.println("<script>alert('�̸����� Ȯ���� �ּ���.');</script>");
        }*/
		return "index"; 
	}
	
	// ���� - ȸ������
	@RequestMapping("/accountForm")
	public String accountForm() { 
		return "accountForm"; 
	} 
	
	// ���� - ȸ������ ����
	@RequestMapping("/accountPro")
	public String accountPro(Model model, MultipartHttpServletRequest req)  throws Exception { 
		//ModelAndView mv = new ModelAndView();
		
		MultipartFile multi = req.getFile("filename");
		String filename = multi.getOriginalFilename();
		System.out.println("���� ���� ���ε�: "+filename);
		
		UserDataBean user = new UserDataBean();
		
		user.setEmail(req.getParameter("email"));
		user.setPwd(req.getParameter("pwd"));
		user.setName(req.getParameter("name"));
		user.setBirth(req.getParameter("birth"));
		user.setTel(req.getParameter("tel"));
		user.setIp(req.getRemoteAddr());
		
		if (filename != null && !filename.equals("")) {
			String uploadPath = req.getRealPath("/")+"userSave";
			System.out.println("���ε� ���: " +uploadPath);
			FileCopyUtils.copy(multi.getInputStream(), new FileOutputStream(uploadPath+"/"+multi.getOriginalFilename()));
			user.setFilename(filename);
			user.setFilesize((int)multi.getSize());
		} else {
			user.setFilename("");
			user.setFilesize(0);
		}
		
		System.out.println(user);
		
		usPro.insertUser(user);
		
		return "redirect:index";
	} 
	
	/*// ���� - �̸��� Ȯ��
	public String confirmEmail (HttpServletRequest req,HttpServletResponse res)  throws Throwable { 
		String email = req.getParameter("email"); 
		UserDBMyBatis dbPro = UserDBMyBatis.getInstance();
		boolean result = dbPro.confirmEmail(email);
		req.setAttribute("result", result);
		req.setAttribute("email", email);
		
		return  "/Project/confirmEmail.jsp"; 
	}
	
	// ���� - �α��� <<MyBatis ����>>
	public String LoginPro(HttpServletRequest req, HttpServletResponse res)  throws Throwable { 
		 // �α��� ȭ�鿡 �Էµ� ���̵�� ��й�ȣ�� �����´�
		HttpSession  session = req.getSession();
		
		String email= req.getParameter("email");
        String pwd = req.getParameter("pwd");
        System.out.println("LoginPro=============");
     	
        // DB���� ���̵�, ��й�ȣ Ȯ��
        UserDBMyBatis dbPro = UserDBMyBatis.getInstance();
        System.out.println(email +" "+ pwd);
        int check = dbPro.loginCheck(email, pwd);
        
        UserDataBean user = new UserDataBean();
       
        // URL �� �α��ΰ��� ���� �޽���
        String msg = "";
 
        if(check == 1)    // �α��� ����
        {
            // ���ǿ� ���� ���̵� ����
        	session.setAttribute("sessionID", email);
        	// ���� ���� �����ͼ� ����� �ѷ��ֱ�.
        	user=dbPro.getUser(email);
            session.setAttribute("name", user.getName());
            session.setAttribute("filename", user.getFilename());
			msg = req.getContextPath()+"/story/head";
        }
        else if(check == 0) // ��й�ȣ�� Ʋ�����
        {
            msg = req.getContextPath()+"/story/index?msg=0";
        }
        else    // ���̵� Ʋ�����
        {
            msg = req.getContextPath()+"/story/index?msg=-1";
        }
        res.sendRedirect(msg);
        
        return null;
	}
	
	// ���� - �α׾ƿ�
	public String LogoutPro(HttpServletRequest req, HttpServletResponse res)  throws Throwable {
		
	    HttpSession  session = req.getSession();
		
	    session.invalidate(); // ��缼������ ����
	    res.sendRedirect("index"); // �α��� ȭ������ �ٽ� ���ư���.
		return null;
	}
	
	// ���� - ����
	public String user_main (HttpServletRequest req, HttpServletResponse res)  throws Throwable {
		HttpSession session = req.getSession();
		
		String diaryid = req.getParameter("diaryid");
		String subject = req.getParameter("subject");
		
		if (diaryid==null) diaryid = "Main"; 
		if (subject==null) subject = "�Ϸ��� ��";
		
		
		int pageSize= 5;
		//SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		String pageNum = req.getParameter("pageNum");
		if (pageNum == null || pageNum =="") {
			pageNum = "1";
		}
		int currentPage = Integer.parseInt(pageNum);
		
		int startRow = (currentPage - 1) * pageSize + 1;
		int endRow = currentPage * pageSize;
		
		int count = 0;
		int number = 0;
		List diaryList = null;
		DiaryDBMyBatis dbPro = DiaryDBMyBatis.getInstance();
		count = dbPro.getDiaryCount(diaryid, (String)session.getAttribute("sessionID"));
		//�Խ��ǿ� �ִ� �� �� count
		if (count > 0) {
			diaryList = dbPro.getDiaries(startRow, endRow, (String)session.getAttribute("sessionID"), diaryid);
		}
		number = count - (currentPage - 1) * pageSize;
		
		System.out.println("�ϱ��� ��: "+count);
		
		int bottomLine = 3; 
		int pageCount = count / pageSize + (count % pageSize == 0 ? 0 : 1);
		int startPage = 1 + (currentPage - 1) / bottomLine * bottomLine; //����, ����������.
		int endPage = startPage + bottomLine -1;
		
		if (endPage > pageCount) endPage = pageCount;
		
		req.setAttribute("subject", subject);
		req.setAttribute("diaryid", diaryid);
		req.setAttribute("count", count);
		req.setAttribute("diaryList", diaryList);
		req.setAttribute("currentPage", currentPage);
		req.setAttribute("startPage", startPage);
		req.setAttribute("bottomLine", bottomLine);
		req.setAttribute("pageCount", pageCount);
		req.setAttribute("number", number);
		req.setAttribute("endPage", endPage);
		
		return "/Project/view/user_main.jsp";
	}
	
	// ���� - ������
	public String user_gallery (HttpServletRequest req, HttpServletResponse res)  throws Throwable {
		HttpSession session = req.getSession();
		
		String diaryid = req.getParameter("diaryid");
		String subject = req.getParameter("subject");
		
		if (diaryid==null) diaryid = "Main"; 
		if (subject==null) subject = "�Ϸ��� ��";
		
		
		int pageSize= 9;
		//SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		String pageNum = req.getParameter("pageNum");
		if (pageNum == null || pageNum =="") {
			pageNum = "1";
		}
		int currentPage = Integer.parseInt(pageNum);
		
		int startRow = (currentPage - 1) * pageSize + 1;
		int endRow = currentPage * pageSize;
		
		int count = 0;
		int countT = 0;
		int number = 0;
		List diaryList = null;
		DiaryDBMyBatis dbPro = DiaryDBMyBatis.getInstance();
		count = dbPro.getDiaryCount(diaryid, (String)session.getAttribute("sessionID"));
		countT = dbPro.getImgDiaryCountTotal(diaryid, (String)session.getAttribute("sessionID"));
		//�Խ��ǿ� �ִ� �� �� count
		if (count > 0) {
			diaryList = dbPro.getDiaries(startRow, endRow, (String)session.getAttribute("sessionID"), diaryid);
		}
		number = count - (currentPage - 1) * pageSize;
		
		System.out.println(count+":"+diaryList);
		
		int bottomLine = 3; 
		int pageCount = count / pageSize + (count % pageSize == 0 ? 0 : 1);
		int startPage = 1 + (currentPage - 1) / bottomLine * bottomLine; //����, ����������.
		int endPage = startPage + bottomLine -1;
		
		if (endPage > pageCount) endPage = pageCount;
		
		req.setAttribute("subject", subject);
		req.setAttribute("diaryid", diaryid);
		req.setAttribute("count", count);
		req.setAttribute("countT", countT);
		req.setAttribute("diaryList", diaryList);
		req.setAttribute("currentPage", currentPage);
		req.setAttribute("startPage", startPage);
		req.setAttribute("bottomLine", bottomLine);
		req.setAttribute("pageCount", pageCount);
		req.setAttribute("number", number);
		req.setAttribute("endPage", endPage);
	
		return "/Project/view/user_gallery.jsp";
	}
	
	// ���� - Ÿ�Ӷ���
	public String user_timeline (HttpServletRequest req, HttpServletResponse res)  throws Throwable {
		HttpSession session = req.getSession();
		
		String diaryid = req.getParameter("diaryid");
		String subject = req.getParameter("subject");
		
		if (diaryid==null) diaryid = "Main"; 
		if (subject==null) subject = "�Ϸ��� ��";
		
		
		int pageSize= 5;
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		String pageNum = req.getParameter("pageNum");
		if (pageNum == null || pageNum =="") {
			pageNum = "1";
		}
		int currentPage = Integer.parseInt(pageNum);
		
		int startRow = (currentPage - 1) * pageSize + 1;
		int endRow = currentPage * pageSize;
		
		int count = 0;
		int number = 0;
		List diaryList = null;
		DiaryDBMyBatis dbPro = DiaryDBMyBatis.getInstance();
		count = dbPro.getDiaryCount(diaryid, (String)session.getAttribute("sessionID"));
		//�Խ��ǿ� �ִ� �� �� count
		if (count > 0) {
			diaryList = dbPro.getDiaries(startRow, endRow, (String)session.getAttribute("sessionID"), diaryid);
		}
		number = count - (currentPage - 1) * pageSize;
		
		System.out.println(count+":"+diaryList);
		
		int bottomLine = 3; 
		int pageCount = count / pageSize + (count % pageSize == 0 ? 0 : 1);
		int startPage = 1 + (currentPage - 1) / bottomLine * bottomLine; //����, ����������.
		int endPage = startPage + bottomLine -1;
		
		if (endPage > pageCount) endPage = pageCount;
		
		req.setAttribute("subject", subject);
		req.setAttribute("diaryid", diaryid);
		req.setAttribute("count", count);
		req.setAttribute("diaryList", diaryList);
		req.setAttribute("currentPage", currentPage);
		req.setAttribute("startPage", startPage);
		req.setAttribute("bottomLine", bottomLine);
		req.setAttribute("pageCount", pageCount);
		req.setAttribute("number", number);
		req.setAttribute("endPage", endPage);
		
		return "/Project/view/user_timeline.jsp";
	}
	
	// ���� - �ϱ� ���� ��
	public String user_updateDForm(HttpServletRequest req, HttpServletResponse res)  throws Throwable { 
		HttpSession session = req.getSession();
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		String diaryid = req.getParameter("diaryid");
		if (diaryid==null) diaryid="Main";
		String pageNum = req.getParameter("pageNum");
			if (pageNum == null || pageNum == "") { 
				pageNum = "1"; 
			}
		int num = Integer.parseInt(req.getParameter("num"));
		
		try {
			DiaryDBMyBatis diaryPro = DiaryDBMyBatis.getInstance();
			DiaryDataBean diary = diaryPro.getDiary(num, (String)session.getAttribute("sessionID"), diaryid);
			
			req.setAttribute("pageNum", pageNum); 
			req.setAttribute("diary", diary); 
		} catch (Exception e) {}
		return "/Project/view/user_updateDForm.jsp"; 
	}
	
	// �ϱ� ���� ���� - ���� ���ε�
	public String user_updateDPro(HttpServletRequest req, HttpServletResponse res)  throws Throwable {
		DiaryDataBean diary = new DiaryDataBean();
		DiaryDBMyBatis diaPro = DiaryDBMyBatis.getInstance();
		
		// 6) fileSave ���� webcontent���� �ȿ� �����
		String realFolder = ""; //�� ���ø����̼ǻ��� ������
		String encType = "euc-kr"; // ���ڵ� Ÿ��
		int maxSize = 5 *1024 * 1024; // �ִ� ���ε� �� ���� ũ�� .. 5MB
		ServletContext context = req.getServletContext();
		realFolder =context.getRealPath("fileSave");
		MultipartRequest multi = null;
		
		// DefaultFileRenamePolicy�� �ߺ��� ���� ���ε��Ҷ� �ڵ����� Rename / aaa������ aaa(1)��
		multi = new MultipartRequest(req, realFolder, maxSize, encType,  new DefaultFileRenamePolicy());
		
		Enumeration files = multi.getFileNames();
		String[] filename = new String[5];
		File[] file = new File[5];
		int index = 0;
		
		String[] original = new String[5];
		String[] type = new String[5];
		
		// 7) 
		while (files.hasMoreElements()) { // ���� ������ �ټ��� if�� while��..
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
			
			System.out.println("��������: " + chk);
			System.out.println(diary);
			
			
		} catch (Exception e) {e.printStackTrace();}
			
		return "/Project/view/user_updateDPro.jsp";
	}
		
	// ���� - �ϱ� ���� ����	
	public String user_deleteDPro(HttpServletRequest req, HttpServletResponse res)  throws Throwable { 
		HttpSession session = req.getSession();
		
		String diaryid = req.getParameter("diaryid");
		String pageNum = req.getParameter("pageNum");
		if (pageNum == null || pageNum == "") {pageNum = "1";}
		int num = Integer.parseInt(req.getParameter("num"));
		
		DiaryDBMyBatis dbPro = DiaryDBMyBatis.getInstance();
		
		int check = dbPro.deleteDiary(num, (String)session.getAttribute("sessionID"), diaryid);
		
		req.setAttribute("check", check);
		
		return "/Project/view/user_deleteDPro.jsp"; 
	} 
	
	// ���� - �ϱ� ���� �� 
	public String user_write(HttpServletRequest req, HttpServletResponse res)  throws Throwable { 
		String subject = req.getParameter("subject");
	    System.out.println("����:"+subject);
	    
	    int num=0;
		String diaryid = req.getParameter("diaryid");
		
		if (diaryid==null) diaryid = "Main";
		if (subject==null) subject = "�������";

		if (req.getParameter("num")!=null) {num = Integer.parseInt(req.getParameter("num"));}
		
		req.setAttribute("diaryid", diaryid);
		req.setAttribute("subject", subject);
		
		return  "/Project/view/user_write.jsp"; 
	}
	
	// ���� - �ϱ� ���� �� ���� (���� �ټ�)
	public String user_writePro(HttpServletRequest req, HttpServletResponse res)  throws Throwable {
		HttpSession session = req.getSession();
		DiaryDataBean diary = new DiaryDataBean();
		DiaryDBMyBatis dbPro = DiaryDBMyBatis.getInstance();
		
		// 6) fileSave ���� webcontent���� �ȿ� �����
		String realFolder = ""; // �� ���ø����̼ǻ��� ������
		String encType = "euc-kr"; // ���ڵ� Ÿ��
		int maxSize = 5 * 1024 * 1024; // �ִ� ���ε� �� ���� ũ�� .. 5MB
		ServletContext context = req.getServletContext();
		realFolder = context.getRealPath("fileSave");
		MultipartRequest multi = null;

		// DefaultFileRenamePolicy�� �ߺ��� ���� ���ε��Ҷ� �ڵ����� Rename / aaa������ aaa(1)��
		multi = new MultipartRequest(req, realFolder, maxSize, encType, new DefaultFileRenamePolicy());

		Enumeration files = multi.getFileNames();
		String[] filename = new String[5];
		File[] file = new File[5];
		int index = 0;
		
		String[] original = new String[5];
		String[] type = new String[5];
		
		// 7) 
		while (files.hasMoreElements()) { // ���� ������ �ټ��� if�� while��..
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
		//9) insertDiary �޼ҵ� ���� (���� ���� �ҽ� �ʼ� ����)
		dbPro.insertDiary(diary);
		
		req.setAttribute("pageNum", pageNum);
		res.sendRedirect("user_main?pageNum="+pageNum+"&diaryid="+diaryid);
		
		return null;
	}
	
	// ���� - ������ (���������� �̵�)
	public String user_content(HttpServletRequest req, HttpServletResponse res)  throws Throwable {
		HttpSession session = req.getSession();
		int num = Integer.parseInt(req.getParameter("num"));
		String diaryid = req.getParameter("diaryid");
		if (diaryid==null) diaryid = "Main"; 

		
		String pageNum = req.getParameter("pageNum");
		if (pageNum == null || pageNum =="") {
			pageNum = "1";
		}
		try {
			DiaryDBMyBatis dbPro = DiaryDBMyBatis.getInstance();
			DiaryDataBean diary = dbPro.getDiary(num, (String)session.getAttribute("sessionID"), diaryid);
			
			req.setAttribute("diary", diary);
			req.setAttribute("pageNum", pageNum);
			System.out.println("���� ������: "+diary);
			
		} catch (Exception e) {e.printStackTrace();}
		
		return "/Project/view/user_content.jsp";
	}
	
	// ���� - ���������� 
	public String user_set(HttpServletRequest req, HttpServletResponse res)  throws Throwable { 
		HttpSession session = req.getSession();
		
		try {
			UserDBMyBatis userPro = UserDBMyBatis.getInstance();
			UserDataBean user = userPro.getUser((String)session.getAttribute("sessionID"));
			
			req.setAttribute("user", user); 
		} catch (Exception e) {}
		return "/Project/view/user_set.jsp"; 
	}
	
	
	public String user_updateUPro(HttpServletRequest req, HttpServletResponse res)  throws Throwable {
		UserDataBean user = new UserDataBean();
		UserDBMyBatis dbPro = UserDBMyBatis.getInstance();
		
		// 6) fileSave ���� webcontent���� �ȿ� �����
		String realFolder = ""; //�� ���ø����̼ǻ��� ������
		String encType = "euc-kr"; // ���ڵ� Ÿ��
		int maxSize = 5 *1024 * 1024; // �ִ� ���ε� �� ���� ũ�� .. 5MB
		ServletContext context = req.getServletContext();
		realFolder =context.getRealPath("userSave");
		MultipartRequest multi = null;
		
		// DefaultFileRenamePolicy�� �ߺ��� ���� ���ε��Ҷ� �ڵ����� Rename / aaa������ aaa(1)��
		multi = new MultipartRequest(req, realFolder, maxSize, encType,  new DefaultFileRenamePolicy());
		
		Enumeration files = multi.getFileNames();
		String filename="";
		File file = null;
		// =================================================
		// 7) 
		if (files.hasMoreElements()) { // ���� ������ �ټ��� if�� while��..
			String name = (String) files.nextElement();
			filename = multi.getFilesystemName(name); // DefaultFileRenamePolicy ����
			String original = multi.getOriginalFileName(name); // ���� ���� �̸� (�߰��ص��ǰ�, ���ص�..?)
			String type = multi.getContentType(name); // ���� Ÿ�� (�߰��ص��ǰ�, ���ص�..?)
			file = multi.getFile(name);
		}
		
		try {
			user.setEmail(multi.getParameter("email"));
			user.setName(multi.getParameter("name"));
			user.setTel(multi.getParameter("tel"));
			user.setPwd(multi.getParameter("pwd"));
			user.setFilename(multi.getParameter("filename"));
			user.setBirth(multi.getParameter("birth"));
			user.setIp(req.getRemoteAddr());
			
			if (file != null) {
				user.setFilename(filename);
				user.setFilesize((int)file.length());
			} else {
				user.setFilename(" ");
				user.setFilesize(0);
			}
			
			int chk = dbPro.updateUser(user);
			
			req.setAttribute("chk", chk);
			
			System.out.println("��������: " + chk);
			System.out.println("������ ���Ƕ�..==========: "+user);
			
			
		} catch (Exception e) {e.printStackTrace();}
			
		return "/Project/view/user_updateUPro.jsp";
	}
	
	public String user_deleteUPro(HttpServletRequest req, HttpServletResponse res)  throws Throwable {
		UserDataBean user = new UserDataBean();
		
		String pageNum = req.getParameter("pageNum");
		if (pageNum == null || pageNum == "") {pageNum = "1";}
		String email = req.getParameter("email");
		String pwd = req.getParameter("pwd");
		
		user.setEmail(req.getParameter("email"));
		user.setPwd(req.getParameter("pwd"));
		user.setName(req.getParameter("name"));
		user.setTel(req.getParameter("tel"));
		user.setBirth(req.getParameter("birth"));
		
		UserDBMyBatis dbPro = UserDBMyBatis.getInstance();
		
		int check = dbPro.deleteUser(email, pwd);
		
		System.out.println("��������: " + check);
		
		req.setAttribute("pwd", pwd);
		req.setAttribute("email", email);
		req.setAttribute("check", check);
		
		return "/Project/view/user_deleteUPro.jsp"; 
	}
	// end. ���� - ���������� ============================= 

	// ��� �׽�Ʈ ==========================================
	// header.jspf - /story/head
	public String head(HttpServletRequest req, HttpServletResponse res)  throws Throwable {
		HttpSession session = req.getSession(); 
		UserDBMyBatis dbPro = UserDBMyBatis.getInstance();
        UserDataBean user = new UserDataBean();
     
		// �α����� �ȵǾ��� ��
		if(session.getAttribute("sessionID") == null)  {
			res.sendRedirect(req.getContextPath()+"/story/index");
		}
		// �α��� �Ǿ��� ��
		else {
	    	res.sendRedirect(req.getContextPath()+"/story/user_main"); 
		} 
		  	
		return null;
	}

// {} class
*/}
