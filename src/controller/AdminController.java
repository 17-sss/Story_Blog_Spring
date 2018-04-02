package controller;

import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;

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
	public String accountList(String pageNum, Model model, HttpServletRequest req) throws Exception {
		int pageSize = 10;
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		//String pageNum = req.getParameter("pageNum");
		if (pageNum == null || pageNum == "") {
			pageNum = "1";
		}
		/*// 임시로 무조건 1페이지로 가게해둠..
		int currentPage = 1;
		if(!"".equals(pageNum)){
		   currentPage = Integer.parseInt(pageNum);
		}*/
		// end. 임시 1페이지
		int currentPage = Integer.parseInt(pageNum);
		int startRow = (currentPage - 1) * pageSize + 1;
		int endRow = currentPage * pageSize;
		//System.out.println("+++++++++++\n"+"start: "+ startRow + "\nend: "+endRow + "\n+++++++++++");
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
	public String updateUserForm(String email, String pwd, String pageNum, Model model) throws Exception {
		/*String email = req.getParameter("email");
		String pwd = req.getParameter("pwd");*/
		
		UserDataBean user = usPro.getUser(email, pwd);
		
		model.addAttribute("user", user);
		model.addAttribute("pageNum", pageNum);
		
		return "/admin/updateUserForm";
	}

	// 관리자 유저수정(Pro)
	// /admin/updateUserPro
	@RequestMapping("/updateUserPro")
	public String updateUserPro(Model model, MultipartHttpServletRequest req, String pageNum) throws Exception {
		//String pageNum = req.getParameter("pageNum");
		
		if (pageNum == null || pageNum == "") {pageNum = "1";}
	
		//ModelAndView mv = new ModelAndView();
		MultipartFile multi = req.getFile("filename");
		String filename = multi.getOriginalFilename();
		System.out.println("유저 수정 이미지: "+filename);
		
		UserDataBean user = new UserDataBean();
		
		user.setEmail(req.getParameter("email"));
		user.setPwd(req.getParameter("pwd"));
		user.setName(req.getParameter("name"));
		user.setTel(req.getParameter("tel"));
		user.setBirth(req.getParameter("birth"));
		user.setFilename(req.getParameter("filename"));
		user.setIp(req.getRemoteAddr());
		
		if (filename != null && !filename.equals("")) {
			String uploadPath = req.getRealPath("/")+"userSave"; // 작대기 그어진 거 신경쓰지말기. 이클립스에서 쓰지않았음 좋겠다는 표시를 해주는 것 뿐.
			System.out.println("업로드 경로: " + uploadPath);
			FileCopyUtils.copy(multi.getInputStream(), new FileOutputStream(uploadPath+"/"+multi.getOriginalFilename()));
			user.setFilename(filename);
			user.setFilesize((int)multi.getSize());
		}/* else {
			user.setFilename("");
			user.setFilesize(0);
		}*/
			 
		
		System.out.println(user);
		int chk = usPro.updateUser(user);
		
		System.out.println("수정여부: " + chk);
		
		model.addAttribute("chk", chk);
		model.addAttribute("pageNum", pageNum);
		model.addAttribute("user", user);
		
		return "/admin/updateUserPro";
	}

	// 관리자 유저삭제(Pro)
    // /admin/deleteUserPro
	@RequestMapping(value = "deleteUserPro")
	public ModelAndView deleteUserPro(String email, String pwd,  HttpServletRequest req) throws Throwable {
		ModelAndView mv = new ModelAndView();
		
		String pageNum = req.getParameter("pageNum");
		if (pageNum == null || pageNum == "") {
			pageNum = "1";
		}
		
		int check = usPro.deleteUser(email, pwd);
		
		System.out.println("삭제여부: " + check);
		
		mv.addObject("check", check);
		mv.addObject("pageNum",pageNum);
		mv.setViewName("/admin/deleteUserPro");
		
		/*UserDataBean user = new UserDataBean();

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
		req.setAttribute("check", check);*/

		return mv;
	}
}
