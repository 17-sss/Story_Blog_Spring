package controller;

import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.db.UserDBMyBatis;
import com.db.UserDataBean;

@Controller
@RequestMapping("/admin")
public class AdminController {
	UserDBMyBatis usPro = UserDBMyBatis.getInstance();
	String pageNum = "1";
	@ModelAttribute
	public void addAttributes(String pageNum) {
		if(pageNum !=null && pageNum != "") {
			this.pageNum = pageNum;
		}
	}
	
	// 관리자 유저관리
	// /admin/accountList
	@RequestMapping("/accountList")
	public String accountList(Model model) throws Exception {
		int pageSize = 10;
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		/*String pageNum = request.getParameter("pageNum");
		if (pageNum == null || pageNum == "") {
			pageNum = "1";
		}*/
		int currentPage = Integer.parseInt(pageNum);
		int startRow = (currentPage - 1) * pageSize + 1;
		int endRow = currentPage * pageSize;
		int count = 0;
		int number = 0;
		List usList = null;
		
		count = usPro.getUserCount();
		if (count > 0) {
			usList = usPro.getUsers(startRow, endRow);
		}
		number = count - (currentPage - 1) * pageSize;

		int bottomLine = 3;
		int pageCount = count / pageSize + (count % pageSize == 0 ? 0 : 1);
		int startPage = 1 + (currentPage - 1) / bottomLine * bottomLine;
		int endPage = startPage + bottomLine - 1;

		if (endPage > pageCount)
			endPage = pageCount;
		
		
		model.addAttribute("count", count);
		model.addAttribute("usList", usList);
		model.addAttribute("currentPage", currentPage);
		model.addAttribute("startPage", startPage);
		model.addAttribute("bottomLine", bottomLine);
		model.addAttribute("pageCount", pageCount);
		model.addAttribute("number", number);
		model.addAttribute("endPage", endPage);

		return "/admin/accountList";
	}
	
	// 관리자 유저수정
	// /admin/updateUserForm
	@RequestMapping("/updateUserForm")
	public String updateUserForm(String email, String pwd, Model model) throws Exception {
		/*String email = req.getParameter("email");
		String pwd = req.getParameter("pwd");*/

		UserDataBean user = usPro.getUser(email, pwd);
		
		model.addAttribute("user", user);
		
		return "/admin/updateUserForm";
	}

	// 관리자 유저수정(Pro)
	// /admin/updateUserPro
	@RequestMapping("/updateUserPro")
	public String updateUserPro(Model model, MultipartHttpServletRequest request) throws Exception {
		String pageNum = request.getParameter("pageNum");
		if (pageNum == null || pageNum == "") {
			pageNum = "1";
		}
		UserDataBean user = new UserDataBean();
		//ModelAndView mv = new ModelAndView();
		MultipartFile multi = request.getFile("filename");
		String filename = multi.getOriginalFilename();
		System.out.println("유저 수정 이미지: "+filename);
		
		user.setEmail(request.getParameter("email"));
		user.setPwd(request.getParameter("pwd"));
		user.setName(request.getParameter("name"));
		user.setTel(request.getParameter("tel"));
		user.setBirth(request.getParameter("birth"));
		user.setFilename(request.getParameter("filename"));
		user.setIp(request.getRemoteAddr());
		
		if (filename != null && !filename.equals("")) {
			String uploadPath = request.getRealPath("/")+"userSave"; // 작대기 그어진 거 신경쓰지말기. 이클립스에서 쓰지않았음 좋겠다는 표시를 해주는 것 뿐.
			System.out.println(uploadPath);
			FileCopyUtils.copy(multi.getInputStream(), new FileOutputStream(uploadPath+"/"+multi.getOriginalFilename()));
			user.setFilename(filename);
			user.setFilesize((int)multi.getSize());
		} else {
			user.setFilename("");
			user.setFilesize(0);
		}
			 
		
		System.out.println(user);
		int chk = usPro.updateUser(user);
		
		System.out.println("수정여부: " + chk);
		
		model.addAttribute("chk", chk);
		model.addAttribute("pageNum", pageNum);


		
		return "/admin/updateUserPro";
	}

	
	/*// /admin/deleteUserPro
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
*/
}
