package controller;

import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;

import com.db.DiaryDBMyBatis;
import com.db.DiaryDataBean;
import com.db.UserDBMyBatis;
import com.db.UserDataBean;

@Controller
@RequestMapping("/story")
public class StoryController {
	UserDBMyBatis usPro = UserDBMyBatis.getInstance();
	DiaryDBMyBatis dbPro = DiaryDBMyBatis.getInstance();

	
	@RequestMapping("/index")
	public String index(HttpServletRequest req) {
		System.out.println("�⺻ ��� �׽�Ʈ: "+req.getContextPath());
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
	@RequestMapping("/accountForm") //url ����
	public String accountForm() { 
		return "accountForm";  // jsp����
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
	
	// ���� - �̸��� Ȯ��  <<�̸��� ���� �� ����..>>
	@RequestMapping("/confirmEmail")
	public String confirmEmail (HttpServletRequest req, Model model)  throws Throwable { 
		String email = req.getParameter("email"); 
		boolean result = usPro.confirmEmail(email);
		model.addAttribute("result", result);
		model.addAttribute("email", email);
		
		return  "confirmEmail"; 
	}
	
	// ���� - �α��� 
	@RequestMapping("/LoginPro")
	public String LoginPro(HttpServletRequest req, ModelAndView mv)  throws Throwable { 
		 // �α��� ȭ�鿡 �Էµ� ���̵�� ��й�ȣ�� �����´�
		HttpSession session = req.getSession();
		
		String email= req.getParameter("email");
        String pwd = req.getParameter("pwd");
        System.out.println("LoginPro=============");
     	
        // DB���� ���̵�, ��й�ȣ Ȯ��
        System.out.println("�Է� email: " + email +"\n�Է� pwd: "+ pwd);
        int check = usPro.loginCheck(email, pwd);
        
        UserDataBean user = new UserDataBean();
       
        // URL �� �α��ΰ��� ���� �޽���
        String msg = "";
 
        if(check == 1)    // �α��� ����
        {
            // ���ǿ� ���� ���̵� ����
        	session.setAttribute("sessionID", email);
        	// ���� ���� �����ͼ� ����� �ѷ��ֱ�.
        	user=usPro.getUser(email);
            session.setAttribute("name", user.getName());
            session.setAttribute("filename", user.getFilename());
			msg = "/story/user_main";
			System.out.println("loginPro ���ԿϷ�");
        }
        else if(check == 0) // ��й�ȣ�� Ʋ�����
        {
            msg = "/story/index?msg=0";
        }
        else    // ���̵� Ʋ�����
        {
            msg = "/story/index?msg=-1";
        }
       
        mv.setViewName(msg);
        
        return "redirect:"+msg;
	}
	
	// ���� - �α׾ƿ�
	@RequestMapping("/LogoutPro")
	public ModelAndView LogoutPro(HttpServletRequest req, ModelAndView mv)  throws Throwable {
		
	    HttpSession  session = req.getSession();
		
	    session.invalidate(); // ��缼������ ����
	    mv.setViewName("index"); // �α��� ȭ������ �ٽ� ���ư���.
	    
		return mv;
	}
	
	// ���� - ����
	@RequestMapping("/user_main")
	public String user_main (HttpServletRequest req, Model model)  throws Throwable {
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
		
		model.addAttribute("subject", subject);
		model.addAttribute("diaryid", diaryid);
		model.addAttribute("count", count);
		model.addAttribute("diaryList", diaryList);
		model.addAttribute("currentPage", currentPage);
		model.addAttribute("startPage", startPage);
		model.addAttribute("bottomLine", bottomLine);
		model.addAttribute("pageCount", pageCount);
		model.addAttribute("number", number);
		model.addAttribute("endPage", endPage);
		
		return "view/user_main";
	}
	
	// ���� - ������
	@RequestMapping("/user_gallery")
	public String user_gallery (HttpServletRequest req, Model model)  throws Throwable {
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
		
		model.addAttribute("subject", subject);
		model.addAttribute("diaryid", diaryid);
		model.addAttribute("count", count);
		model.addAttribute("countT", countT);
		model.addAttribute("diaryList", diaryList);
		model.addAttribute("currentPage", currentPage);
		model.addAttribute("startPage", startPage);
		model.addAttribute("bottomLine", bottomLine);
		model.addAttribute("pageCount", pageCount);
		model.addAttribute("number", number);
		model.addAttribute("endPage", endPage);
	
		return "view/user_gallery";
	}
	
	// ���� - Ÿ�Ӷ���
	@RequestMapping("/user_timeline")
	public String user_timeline (HttpServletRequest req, Model model)  throws Throwable {
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
		
		model.addAttribute("subject", subject);
		model.addAttribute("diaryid", diaryid);
		model.addAttribute("count", count);
		model.addAttribute("diaryList", diaryList);
		model.addAttribute("currentPage", currentPage);
		model.addAttribute("startPage", startPage);
		model.addAttribute("bottomLine", bottomLine);
		model.addAttribute("pageCount", pageCount);
		model.addAttribute("number", number);
		model.addAttribute("endPage", endPage);
		
		return "view/user_timeline";
	}
	
	// ���� - �ϱ� ���� ��
	@RequestMapping("/user_updateDForm")
	public String user_updateDForm(HttpServletRequest req, Model model)  throws Throwable { 
		HttpSession session = req.getSession();
		
		String diaryid = req.getParameter("diaryid");
		if (diaryid==null) diaryid="Main";
		String pageNum = req.getParameter("pageNum");
		if (pageNum == null || pageNum == "") { pageNum = "1"; }
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		
		int num = Integer.parseInt(req.getParameter("num"));
		
		try {
			DiaryDataBean diary = dbPro.getDiary(num, (String)session.getAttribute("sessionID"), diaryid);
			
			model.addAttribute("pageNum", pageNum); 
			model.addAttribute("diary", diary); 
		} catch (Exception e) {}
		
		return "view/user_updateDForm"; 
	}
	
	// �ϱ� ���� ���� - ���� ���ε� (�������)
	@RequestMapping("/user_updateDPro")
	public String user_updateDPro(Model model, MultipartHttpServletRequest req)  throws Throwable {
		DiaryDataBean diary = new DiaryDataBean();
		
		int num = Integer.parseInt(req.getParameter("num"));
		
		String pageNum = req.getParameter("pageNum");
		if (pageNum == null || pageNum == "") {pageNum = "1";}
		
		String diaryid = req.getParameter("diaryid");
		if (diaryid==null) diaryid = "Main";
	
		//ModelAndView mv = new ModelAndView();
		MultipartFile multi0 = req.getFile("filename0");
		String filename0 = multi0.getOriginalFilename();
		MultipartFile multi1 = req.getFile("filename1");
		String filename1 = multi1.getOriginalFilename();
		MultipartFile multi2 = req.getFile("filename2");
		String filename2 = multi2.getOriginalFilename();
		MultipartFile multi3 = req.getFile("filename3");
		String filename3 = multi3.getOriginalFilename();
		MultipartFile multi4 = req.getFile("filename4");
		String filename4 = multi4.getOriginalFilename();
		System.out.println("�ϱ� ���� �̹���1: "+filename0+"\n" + "�ϱ� ���� �̹���2: "+filename1+"\n" + "�ϱ� ���� �̹���3: "+filename2+"\n"
				+"�ϱ� ���� �̹���4: "+filename3+"\n" + "�ϱ� ���� �̹���5: "+filename4+"\n");
		
		diary.setNum(num);
		diary.setEmail(req.getParameter("email"));
		diary.setSubject(req.getParameter("subject"));
		diary.setContent(req.getParameter("content"));
		diary.setDiaryid(req.getParameter("diaryid"));
		diary.setFilename0(req.getParameter("filename0"));
		diary.setFilename1(req.getParameter("filename1"));
		diary.setFilename2(req.getParameter("filename2"));
		diary.setFilename3(req.getParameter("filename3"));
		diary.setFilename4(req.getParameter("filename4"));
		
		if (filename0 != null && !filename0.equals("")) {
			String uploadPath = req.getRealPath("/")+"fileSave"; // �۴�� �׾��� �� �Ű澲������. ��Ŭ�������� �����ʾ��� ���ڴٴ� ǥ�ø� ���ִ� �� ��.
			System.out.println("���ε� ���: " + uploadPath);
			FileCopyUtils.copy(multi0.getInputStream(), new FileOutputStream(uploadPath+"/"+multi0.getOriginalFilename()));
			diary.setFilename0(filename0);
			diary.setFilesize0((int)multi0.getSize());
		}
		if (filename1 != null && !filename1.equals("")) {
			String uploadPath = req.getRealPath("/")+"fileSave";
			FileCopyUtils.copy(multi1.getInputStream(), new FileOutputStream(uploadPath+"/"+multi1.getOriginalFilename()));
			diary.setFilename1(filename1);
			diary.setFilesize1((int)multi1.getSize());
		}
		if (filename2 != null && !filename2.equals("")) {
			String uploadPath = req.getRealPath("/")+"fileSave";
			FileCopyUtils.copy(multi2.getInputStream(), new FileOutputStream(uploadPath+"/"+multi2.getOriginalFilename()));
			diary.setFilename2(filename2);
			diary.setFilesize2((int)multi2.getSize());
		}
		if (filename3 != null && !filename3.equals("")) {
			String uploadPath = req.getRealPath("/")+"fileSave";
			FileCopyUtils.copy(multi3.getInputStream(), new FileOutputStream(uploadPath+"/"+multi3.getOriginalFilename()));
			diary.setFilename3(filename3);
			diary.setFilesize3((int)multi3.getSize());
		}
		if (filename4 != null && !filename4.equals("")) {
			String uploadPath = req.getRealPath("/")+"fileSave";
			FileCopyUtils.copy(multi4.getInputStream(), new FileOutputStream(uploadPath+"/"+multi4.getOriginalFilename()));
			diary.setFilename4(filename4);
			diary.setFilesize4((int)multi4.getSize());
		} else {}
		/* else {
			user.setFilename("");
			user.setFilesize(0);
		}*/
		
		int chk = dbPro.updateDiary(diary);
		
		model.addAttribute("chk", chk);
		model.addAttribute("pageNum", pageNum);
		model.addAttribute("diaryid", diaryid);
		
		System.out.println("��������: " + chk);
		
		System.out.println(diary);
		
		return "view/user_updateDPro";
	}
	
	// ���� - �ϱ� ���� ����
	@RequestMapping(value = "user_deleteDPro")
	public ModelAndView user_deleteDPro(HttpServletRequest req)  throws Throwable { 
		ModelAndView mv = new ModelAndView();
		
		HttpSession session = req.getSession();
		
		String diaryid = req.getParameter("diaryid");
		String pageNum = req.getParameter("pageNum");
		if (pageNum == null || pageNum == "") {pageNum = "1";}
		
		int num = Integer.parseInt(req.getParameter("num"));
		
		
		int check = dbPro.deleteDiary(num, (String)session.getAttribute("sessionID"), diaryid);
		
		System.out.println("��������: " + check);
		
		mv.addObject("check", check);
		mv.addObject("pageNum", pageNum);
		mv.setViewName("view/user_deleteDPro");

		return mv; 
	}
	
	// ���� - �ϱ� ���� �� 
	@RequestMapping("/user_write")
	public String user_write(Model model, HttpServletRequest req)  throws Throwable { 
		String subject = req.getParameter("subject");
		String diaryid = req.getParameter("diaryid");
		int num=0;
		 
		System.out.println("����:"+subject);
		
		if (diaryid==null) diaryid = "Main";
		if (subject==null) subject = "�������";

		if (req.getParameter("num")!=null) {num = Integer.parseInt(req.getParameter("num"));}
		
		model.addAttribute("diaryid", diaryid);
		model.addAttribute("subject", subject);
		model.addAttribute("num", num);
		
		return  "view/user_write"; 
	}
	
	// ���� - �ϱ� ���� �� ���� (���� �ټ�)
	@RequestMapping("/user_writePro")
	public ModelAndView user_writePro(MultipartHttpServletRequest req)  throws Throwable {
		HttpSession session = req.getSession();
		ModelAndView mv = new ModelAndView();
		
		DiaryDataBean diary = new DiaryDataBean();
		
		//int num = Integer.parseInt(req.getParameter("num"));
		
		String pageNum = req.getParameter("pageNum");
		if (pageNum == null || pageNum == "") {pageNum = "1";}
		
		String diaryid = req.getParameter("diaryid");
		if (diaryid==null) diaryid = "Main";
	
		//ModelAndView mv = new ModelAndView();
		MultipartFile multi0 = req.getFile("filename0");
		String filename0 = multi0.getOriginalFilename();
		MultipartFile multi1 = req.getFile("filename1");
		String filename1 = multi1.getOriginalFilename();
		MultipartFile multi2 = req.getFile("filename2");
		String filename2 = multi2.getOriginalFilename();
		MultipartFile multi3 = req.getFile("filename3");
		String filename3 = multi3.getOriginalFilename();
		MultipartFile multi4 = req.getFile("filename4");
		String filename4 = multi4.getOriginalFilename();
		System.out.println("�ϱ� �̹���1: "+filename0+"\n" + "�ϱ� �̹���2: "+filename1+"\n" + "�ϱ� �̹���3: "+filename2+"\n"
				+"�ϱ� �̹���4: "+filename3+"\n" + "�ϱ� �̹���5: "+filename4+"\n");
		
		diary.setNum(Integer.parseInt(req.getParameter("num")));
		diary.setEmail((String)session.getAttribute("sessionID"));
		diary.setSubject(req.getParameter("subject"));
		diary.setContent(req.getParameter("content"));
		diary.setDiaryid(req.getParameter("diaryid"));
		diary.setIp(req.getRemoteAddr());
		/*diary.setFilename0(req.getParameter("filename0"));
		diary.setFilename1(req.getParameter("filename1"));
		diary.setFilename2(req.getParameter("filename2"));
		diary.setFilename3(req.getParameter("filename3"));
		diary.setFilename4(req.getParameter("filename4"));*/
		
		if (filename0 != null && !filename0.equals("")) {
			String uploadPath = req.getRealPath("/")+"fileSave"; // �۴�� �׾��� �� �Ű澲������. ��Ŭ�������� �����ʾ��� ���ڴٴ� ǥ�ø� ���ִ� �� ��.
			System.out.println("���ε� ���: " + uploadPath);
			FileCopyUtils.copy(multi0.getInputStream(), new FileOutputStream(uploadPath+"/"+multi0.getOriginalFilename()));
			diary.setFilename0(filename0);
			diary.setFilesize0((int)multi0.getSize());
		}
		if (filename1 != null && !filename1.equals("")) {
			String uploadPath = req.getRealPath("/")+"fileSave";
			FileCopyUtils.copy(multi1.getInputStream(), new FileOutputStream(uploadPath+"/"+multi1.getOriginalFilename()));
			diary.setFilename1(filename1);
			diary.setFilesize1((int)multi1.getSize());
		}
		if (filename2 != null && !filename2.equals("")) {
			String uploadPath = req.getRealPath("/")+"fileSave";
			FileCopyUtils.copy(multi2.getInputStream(), new FileOutputStream(uploadPath+"/"+multi2.getOriginalFilename()));
			diary.setFilename2(filename2);
			diary.setFilesize2((int)multi2.getSize());
		}
		if (filename3 != null && !filename3.equals("")) {
			String uploadPath = req.getRealPath("/")+"fileSave";
			FileCopyUtils.copy(multi3.getInputStream(), new FileOutputStream(uploadPath+"/"+multi3.getOriginalFilename()));
			diary.setFilename3(filename3);
			diary.setFilesize3((int)multi3.getSize());
		}
		if (filename4 != null && !filename4.equals("")) {
			String uploadPath = req.getRealPath("/")+"fileSave";
			FileCopyUtils.copy(multi4.getInputStream(), new FileOutputStream(uploadPath+"/"+multi4.getOriginalFilename()));
			diary.setFilename4(filename4);
			diary.setFilesize4((int)multi4.getSize());
		} else {}
		/* else {
			user.setFilename("");
			user.setFilesize(0);
		}*/
		dbPro.insertDiary(diary);

		mv.addObject("pageNum", pageNum);
		//mv.setViewName("view/user_main?pageNum="+pageNum+"&diaryid="+diaryid);
		mv.setViewName("view/user_main?pageNum="+pageNum);
		
		System.out.println("====writePro====\n"+diary+"\n==============");
		
		return mv;
	}
	/*
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
	}*/
	// end. ���� - ���������� ============================= 

	// ��� �׽�Ʈ ==========================================
	// header.jspf - /story/head
	/*@RequestMapping("/head")
	public ModelAndView head(HttpServletRequest req, ModelAndView mv)  throws Throwable {
		HttpSession session = req.getSession(); 
		
     
		// �α����� �ȵǾ��� ��
		if(session.getAttribute("sessionID") == null)  {
			mv.setViewName(" index");
		}
		// �α��� �Ǿ��� ��
		else {
	    	mv.setViewName("view/user_main");
		} 
		  	
		return mv;
	}*/

// {} class
}
