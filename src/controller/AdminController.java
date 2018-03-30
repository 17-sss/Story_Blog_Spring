package controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin")
public class AdminController {
	
	/*
	// 관리자 유저관리
	// /story/admin/accountList
	public String accountList(HttpServletRequest req, HttpServletResponse res) throws Throwable {
		int pageSize = 10;
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		String pageNum = req.getParameter("pageNum");
		if (pageNum == null || pageNum == "") {
			pageNum = "1";
		}
		int currentPage = Integer.parseInt(pageNum);
		int startRow = (currentPage - 1) * pageSize + 1;
		// 사칙연산은 곱셈먼저.. +1은 맨나중에! 왼쪽에서부터 오른쪽으로 차례대로 계산
		int endRow = currentPage * pageSize;
		int count = 0;
		int number = 0;
		List usList = null;
		UserDBMyBatis dbPro = UserDBMyBatis.getInstance();
		count = dbPro.getUserCount();
		// 게시판에 있는 글 수 count
		if (count > 0) {
			usList = dbPro.getUsers(startRow, endRow);
		}
		number = count - (currentPage - 1) * pageSize;

		int bottomLine = 3;
		int pageCount = count / pageSize + (count % pageSize == 0 ? 0 : 1);
		int startPage = 1 + (currentPage - 1) / bottomLine * bottomLine; // 곱셈, 나눗셈먼저.
		int endPage = startPage + bottomLine - 1;

		if (endPage > pageCount)
			endPage = pageCount;

		req.setAttribute("count", count);
		req.setAttribute("usList", usList);
		req.setAttribute("currentPage", currentPage);
		req.setAttribute("startPage", startPage);
		req.setAttribute("bottomLine", bottomLine);
		req.setAttribute("pageCount", pageCount);
		req.setAttribute("number", number);
		req.setAttribute("endPage", endPage);

		return "/Project/admin/accountList.jsp";
	}

	
	
	// /story/admin/updateUserForm
	public String updateUserForm(HttpServletRequest req, HttpServletResponse res) throws Throwable {
		String email = req.getParameter("email");
		String pwd = req.getParameter("pwd");
		String pageNum = req.getParameter("pageNum");
		if (pageNum == null || pageNum == "") {
			pageNum = "1";
		}
		try {
			UserDBMyBatis userPro = UserDBMyBatis.getInstance();
			UserDataBean user = userPro.getUser(email, pwd);

			req.setAttribute("user", user);
		} catch (Exception e) {
		}
		return "/Project/admin/updateUserForm.jsp";
	}

	
	
	// /story/admin/updateUserPro
	public String updateUserPro(HttpServletRequest req, HttpServletResponse res) throws Throwable {
		UserDataBean user = new UserDataBean();
		UserDBMyBatis userPro = UserDBMyBatis.getInstance();

		// 사진 업로드용 ============================================
		String realFolder = ""; // 웹 어플리케이션상의 절대경로
		String encType = "euc-kr"; // 인코딩 타입
		int maxSize = 3 * 1024 * 1024; // 최대 업로드 될 파일 크기 .. 3MB (회원사진)
		ServletContext context = req.getServletContext();
		realFolder = context.getRealPath("userSave");
		MultipartRequest multi = null;

		// DefaultFileRenamePolicy는 중복된 파일 업로드할때 자동으로 Rename / aaa있으면 aaa(1)로
		multi = new MultipartRequest(req, realFolder, maxSize, encType, new DefaultFileRenamePolicy());

		Enumeration files = multi.getFileNames();
		String filename = "";
		File file = null;

		if (files.hasMoreElements()) { // 만약 파일이 다수면 if를 while로..
			String name = (String) files.nextElement();
			filename = multi.getFilesystemName(name); // DefaultFileRenamePolicy 적용
			String original = multi.getOriginalFileName(name); // 파일 원래 이름 (추가해도되고, 안해도..?)
			String type = multi.getContentType(name); // 파일 타입 (추가해도되고, 안해도..?)
			file = multi.getFile(name);
		}

		// end. 사진 업로드용 ============================================

		String email = multi.getParameter("email");
		String pwd = multi.getParameter("pwd");
		String pageNum = multi.getParameter("pageNum");
		if (pageNum == null || pageNum == "") {
			pageNum = "1";
		}

		try {
			user.setEmail(multi.getParameter("email"));
			user.setPwd(multi.getParameter("pwd"));
			user.setName(multi.getParameter("name"));
			user.setTel(multi.getParameter("tel"));
			user.setBirth(multi.getParameter("birth"));
			user.setFilename(multi.getParameter("filename"));
			user.setIp(req.getRemoteAddr());

			// + (사진 관련)
			if (file != null) {
				user.setFilename(filename);
				user.setFilesize((int) file.length());
			} else {
				 user.setFilename(" "); 
				 user.setFilesize(0); 
			}
			// ============

			int chk = userPro.updateUser(user);

			req.setAttribute("chk", chk);
			req.setAttribute("pageNum", pageNum);
			req.setAttribute("email", email);

			System.out.println("수정여부: " + chk);
			System.out.println(user);

		} catch (Exception e) {
			e.printStackTrace();
		}

		return "/Project/admin/updateUserPro.jsp";
	}

	
	
	// /story/admin/deleteUserPro
	public String deleteUserPro(HttpServletRequest req, HttpServletResponse res) throws Throwable {
		UserDataBean user = new UserDataBean();

		String pageNum = req.getParameter("pageNum");
		if (pageNum == null || pageNum == "") {
			pageNum = "1";
		}
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

		return "/Project/admin/deleteUserPro.jsp";
	}

*/}
