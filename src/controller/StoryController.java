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
		System.out.println("기본 경로 테스트: "+req.getContextPath());
		/*String msg= req.getParameter("msg");
        
        if(msg!=null && msg.equals("0")) {
           System.out.println("<script>alert('비밀번호를 확인해 주세요.');</script>");
        }
        else if(msg!=null && msg.equals("-1")) {    
        	System.out.println("<script>alert('이메일을 확인해 주세요.');</script>");
        }*/
		return "index"; 
	}
	
	// 유저 - 회원가입
	@RequestMapping("/accountForm") //url 매핑
	public String accountForm() { 
		return "accountForm";  // jsp파일
	} 
	
	// 유저 - 회원가입 전송
	@RequestMapping("/accountPro")
	public String accountPro(Model model, MultipartHttpServletRequest req)  throws Exception { 
		//ModelAndView mv = new ModelAndView();
		
		MultipartFile multi = req.getFile("filename");
		String filename = multi.getOriginalFilename();
		System.out.println("유저 파일 업로드: "+filename);
		
		UserDataBean user = new UserDataBean();
		
		user.setEmail(req.getParameter("email"));
		user.setPwd(req.getParameter("pwd"));
		user.setName(req.getParameter("name"));
		user.setBirth(req.getParameter("birth"));
		user.setTel(req.getParameter("tel"));
		user.setIp(req.getRemoteAddr());
		
		if (filename != null && !filename.equals("")) {
			String uploadPath = req.getRealPath("/")+"userSave";
			System.out.println("업로드 경로: " +uploadPath);
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
	
	// 유저 - 이메일 확인  <<이메일 같을 시 에러..>>
	@RequestMapping("/confirmEmail")
	public String confirmEmail (HttpServletRequest req, Model model)  throws Throwable { 
		String email = req.getParameter("email"); 
		boolean result = usPro.confirmEmail(email);
		model.addAttribute("result", result);
		model.addAttribute("email", email);
		
		return  "confirmEmail"; 
	}
	
	// 유저 - 로그인 
	@RequestMapping("/LoginPro")
	public String LoginPro(HttpServletRequest req, ModelAndView mv)  throws Throwable { 
		 // 로그인 화면에 입력된 아이디와 비밀번호를 가져온다
		HttpSession session = req.getSession();
		
		String email= req.getParameter("email");
        String pwd = req.getParameter("pwd");
        System.out.println("LoginPro=============");
     	
        // DB에서 아이디, 비밀번호 확인
        System.out.println("입력 email: " + email +"\n입력 pwd: "+ pwd);
        int check = usPro.loginCheck(email, pwd);
        
        UserDataBean user = new UserDataBean();
       
        // URL 및 로그인관련 전달 메시지
        String msg = "";
 
        if(check == 1)    // 로그인 성공
        {
            // 세션에 현재 아이디 세팅
        	session.setAttribute("sessionID", email);
        	// 유저 정보 가져와서 헤더에 뿌려주기.
        	user=usPro.getUser(email);
            session.setAttribute("name", user.getName());
            session.setAttribute("filename", user.getFilename());
			msg = "/story/user_main";
			System.out.println("loginPro 진입완료");
        }
        else if(check == 0) // 비밀번호가 틀릴경우
        {
            msg = "/story/index?msg=0";
        }
        else    // 아이디가 틀릴경우
        {
            msg = "/story/index?msg=-1";
        }
       
        mv.setViewName(msg);
        
        return "redirect:"+msg;
	}
	
	// 유저 - 로그아웃
	@RequestMapping("/LogoutPro")
	public ModelAndView LogoutPro(HttpServletRequest req, ModelAndView mv)  throws Throwable {
		
	    HttpSession  session = req.getSession();
		
	    session.invalidate(); // 모든세션정보 삭제
	    mv.setViewName("index"); // 로그인 화면으로 다시 돌아간다.
	    
		return mv;
	}
	
	// 유저 - 메인
	@RequestMapping("/user_main")
	public String user_main (HttpServletRequest req, Model model)  throws Throwable {
		HttpSession session = req.getSession();
		
		String diaryid = req.getParameter("diaryid");
		String subject = req.getParameter("subject");
		
		if (diaryid==null) diaryid = "Main"; 
		if (subject==null) subject = "하루의 끝";
		
		
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
		//게시판에 있는 글 수 count
		if (count > 0) {
			diaryList = dbPro.getDiaries(startRow, endRow, (String)session.getAttribute("sessionID"), diaryid);
		}
		number = count - (currentPage - 1) * pageSize;
		
		System.out.println("일기장 수: "+count);
		
		int bottomLine = 3; 
		int pageCount = count / pageSize + (count % pageSize == 0 ? 0 : 1);
		int startPage = 1 + (currentPage - 1) / bottomLine * bottomLine; //곱셈, 나눗셈먼저.
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
	
	// 유저 - 갤러리
	@RequestMapping("/user_gallery")
	public String user_gallery (HttpServletRequest req, Model model)  throws Throwable {
		HttpSession session = req.getSession();
		
		String diaryid = req.getParameter("diaryid");
		String subject = req.getParameter("subject");
		
		if (diaryid==null) diaryid = "Main"; 
		if (subject==null) subject = "하루의 끝";
		
		
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
		//게시판에 있는 글 수 count
		if (count > 0) {
			diaryList = dbPro.getDiaries(startRow, endRow, (String)session.getAttribute("sessionID"), diaryid);
		}
		number = count - (currentPage - 1) * pageSize;
		
		System.out.println(count+":"+diaryList);
		
		int bottomLine = 3; 
		int pageCount = count / pageSize + (count % pageSize == 0 ? 0 : 1);
		int startPage = 1 + (currentPage - 1) / bottomLine * bottomLine; //곱셈, 나눗셈먼저.
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
	
	// 유저 - 타임라인
	@RequestMapping("/user_timeline")
	public String user_timeline (HttpServletRequest req, Model model)  throws Throwable {
		HttpSession session = req.getSession();
		
		String diaryid = req.getParameter("diaryid");
		String subject = req.getParameter("subject");
		
		if (diaryid==null) diaryid = "Main"; 
		if (subject==null) subject = "하루의 끝";
		
		
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
		//게시판에 있는 글 수 count
		if (count > 0) {
			diaryList = dbPro.getDiaries(startRow, endRow, (String)session.getAttribute("sessionID"), diaryid);
		}
		number = count - (currentPage - 1) * pageSize;
		
		System.out.println(count+":"+diaryList);
		
		int bottomLine = 3; 
		int pageCount = count / pageSize + (count % pageSize == 0 ? 0 : 1);
		int startPage = 1 + (currentPage - 1) / bottomLine * bottomLine; //곱셈, 나눗셈먼저.
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
	
	// 유저 - 일기 수정 폼
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
	
	// 일기 수정 전송 - 파일 업로드 (수정요망)
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
		System.out.println("일기 수정 이미지1: "+filename0+"\n" + "일기 수정 이미지2: "+filename1+"\n" + "일기 수정 이미지3: "+filename2+"\n"
				+"일기 수정 이미지4: "+filename3+"\n" + "일기 수정 이미지5: "+filename4+"\n");
		
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
			String uploadPath = req.getRealPath("/")+"fileSave"; // 작대기 그어진 거 신경쓰지말기. 이클립스에서 쓰지않았음 좋겠다는 표시를 해주는 것 뿐.
			System.out.println("업로드 경로: " + uploadPath);
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
		
		System.out.println("수정여부: " + chk);
		
		System.out.println(diary);
		
		return "view/user_updateDPro";
	}
	
	// 유저 - 일기 삭제 전송
	@RequestMapping(value = "user_deleteDPro")
	public ModelAndView user_deleteDPro(HttpServletRequest req)  throws Throwable { 
		ModelAndView mv = new ModelAndView();
		
		HttpSession session = req.getSession();
		
		String diaryid = req.getParameter("diaryid");
		String pageNum = req.getParameter("pageNum");
		if (pageNum == null || pageNum == "") {pageNum = "1";}
		
		int num = Integer.parseInt(req.getParameter("num"));
		
		
		int check = dbPro.deleteDiary(num, (String)session.getAttribute("sessionID"), diaryid);
		
		System.out.println("삭제여부: " + check);
		
		mv.addObject("check", check);
		mv.addObject("pageNum", pageNum);
		mv.setViewName("view/user_deleteDPro");

		return mv; 
	}
	
	// 유저 - 일기 쓰기 폼 
	@RequestMapping("/user_write")
	public String user_write(Model model, HttpServletRequest req)  throws Throwable { 
		String subject = req.getParameter("subject");
		String diaryid = req.getParameter("diaryid");
		int num=0;
		 
		System.out.println("제목:"+subject);
		
		if (diaryid==null) diaryid = "Main";
		if (subject==null) subject = "제목없음";

		if (req.getParameter("num")!=null) {num = Integer.parseInt(req.getParameter("num"));}
		
		model.addAttribute("diaryid", diaryid);
		model.addAttribute("subject", subject);
		model.addAttribute("num", num);
		
		return  "view/user_write"; 
	}
	
	// 유저 - 일기 쓰기 폼 전송 (사진 다수)
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
		System.out.println("일기 이미지1: "+filename0+"\n" + "일기 이미지2: "+filename1+"\n" + "일기 이미지3: "+filename2+"\n"
				+"일기 이미지4: "+filename3+"\n" + "일기 이미지5: "+filename4+"\n");
		
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
			String uploadPath = req.getRealPath("/")+"fileSave"; // 작대기 그어진 거 신경쓰지말기. 이클립스에서 쓰지않았음 좋겠다는 표시를 해주는 것 뿐.
			System.out.println("업로드 경로: " + uploadPath);
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
	// 유저 - 콘텐츠 (갤러리에서 이동)
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
			System.out.println("유저 콘텐츠: "+diary);
			
		} catch (Exception e) {e.printStackTrace();}
		
		return "/Project/view/user_content.jsp";
	}
	
	// 유저 - 마이페이지 
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
		
		// 6) fileSave 폴더 webcontent폴더 안에 만들기
		String realFolder = ""; //웹 어플리케이션상의 절대경로
		String encType = "euc-kr"; // 인코딩 타입
		int maxSize = 5 *1024 * 1024; // 최대 업로드 될 파일 크기 .. 5MB
		ServletContext context = req.getServletContext();
		realFolder =context.getRealPath("userSave");
		MultipartRequest multi = null;
		
		// DefaultFileRenamePolicy는 중복된 파일 업로드할때 자동으로 Rename / aaa있으면 aaa(1)로
		multi = new MultipartRequest(req, realFolder, maxSize, encType,  new DefaultFileRenamePolicy());
		
		Enumeration files = multi.getFileNames();
		String filename="";
		File file = null;
		// =================================================
		// 7) 
		if (files.hasMoreElements()) { // 만약 파일이 다수면 if를 while로..
			String name = (String) files.nextElement();
			filename = multi.getFilesystemName(name); // DefaultFileRenamePolicy 적용
			String original = multi.getOriginalFileName(name); // 파일 원래 이름 (추가해도되고, 안해도..?)
			String type = multi.getContentType(name); // 파일 타입 (추가해도되고, 안해도..?)
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
			
			System.out.println("수정여부: " + chk);
			System.out.println("수정아 좀되라..==========: "+user);
			
			
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
		
		System.out.println("삭제여부: " + check);
		
		req.setAttribute("pwd", pwd);
		req.setAttribute("email", email);
		req.setAttribute("check", check);
		
		return "/Project/view/user_deleteUPro.jsp"; 
	}*/
	// end. 유저 - 마이페이지 ============================= 

	// 헤더 테스트 ==========================================
	// header.jspf - /story/head
	/*@RequestMapping("/head")
	public ModelAndView head(HttpServletRequest req, ModelAndView mv)  throws Throwable {
		HttpSession session = req.getSession(); 
		
     
		// 로그인이 안되었을 때
		if(session.getAttribute("sessionID") == null)  {
			mv.setViewName(" index");
		}
		// 로그인 되었을 때
		else {
	    	mv.setViewName("view/user_main");
		} 
		  	
		return mv;
	}*/

// {} class
}
