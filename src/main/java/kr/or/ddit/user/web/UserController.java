package kr.or.ddit.user.web;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.annotation.Resource;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import kr.or.ddit.common.model.PageVo;
import kr.or.ddit.user.model.UserVo;
import kr.or.ddit.user.service.UserService;
import kr.or.ddit.util.FileUtil;

@RequestMapping("user")
@Controller
public class UserController {
	
	
	private static final Logger logger = LoggerFactory.getLogger(UserController.class);

	
	@Resource(name = "userService")
	private UserService userService;
	
	@RequestMapping(path = "allUser",method = RequestMethod.GET)
	public String allUser(Model model) {
		
		List<UserVo> userList = userService.selectAllUser();
		
		
		model.addAttribute("userList", userList);
		return "/user/allUser";
	}
	
	/////////////////////////////////////////////////////
	
	@RequestMapping(path = "allUserTiles",method = RequestMethod.GET)
	public String allUserTiles(Model model) {
		
		List<UserVo> userList = userService.selectAllUser();
		
		
		model.addAttribute("userList", userList);
		return "tiles.user.allUser";
	}
	
	/////////////////////////////////////////////////////
	
	//@RequestMapping("paingUser")
	public String paingUser(@RequestParam(defaultValue = "1") int page, 
							@RequestParam(defaultValue = "5") int pageSize,
							@RequestParam(name = "p") int price) {
		logger.debug("page : {}, pageSize : {}, price : {} ", page, pageSize, price);
		
		
		
		return "";
	}
	

	@RequestMapping(path="deleteUser",method = RequestMethod.POST)
	public String deleteUser(String userid, RedirectAttributes ra) {
		
		int deleteCnt = 0;
		
		try {
			deleteCnt = userService.deleteUser(userid);
		} catch (Exception e) {
			e.printStackTrace();
			deleteCnt= 0;
		}
		
		if(deleteCnt==1) {
			return "redirect:/user/pagingUser";
		}else {
			ra.addAttribute("userid", userid);
			return "redirect:user/user";
		}
		
		
	}
	
	@RequestMapping(path="modifyUser",method = RequestMethod.GET)
	public String modifyUserView(String userid, Model model) {
		
		model.addAttribute("user", userService.selectUser(userid));
		
		return "user/userModify";
	}
	
	@RequestMapping(path="modifyUser",method = RequestMethod.POST)
	public String modifyUser(UserVo userVo, Model model,RedirectAttributes ra, MultipartFile profile) {
		userVo.setFilename("");
		UserVo vo = userService.selectUser(userVo.getUserid());
		
		
		if(userVo.getFilename()==null) {
			userVo.setFilename(vo.getFilename());
			
			if(userVo.getFilename()==null) {
				userVo.setFilename("");
			}
		}
		
		
		if(userVo.getRealfilename()==null) {
			userVo.setRealfilename(vo.getRealfilename());
			
			if(userVo.getRealfilename()==null) {
				userVo.setRealfilename("");
			}
		}
		
		
		try {
			String fileExtension = FileUtil.getFileExtension(profile.getOriginalFilename());
			String realFileName = UUID.randomUUID().toString()+fileExtension;
			
			profile.transferTo(new File("d:/upload/" + realFileName));
			userVo.setFilename(profile.getOriginalFilename());
			
			userVo.setRealfilename(realFileName);
			
		} catch (IllegalStateException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		int updateCnt = 0;
		
		try {
			updateCnt = userService.modifyUser(userVo);
			
			
		} catch (Exception e) {
			e.printStackTrace();
			updateCnt= 0;
		}
		
		 
		
		if(updateCnt==1) {
			ra.addAttribute("userid", userVo.getUserid());
			return "redirect:/user/userInfo";
		}else {
			model.addAttribute("user", userVo);
			return "user/userModify";
		}
		
	}
	
	
	
	@RequestMapping("fileupload/upload")
	public String fileupload(String userid, MultipartFile picture) {
		
		logger.debug("userid : {}", userid);
		logger.debug("filesize : {}, name : {} , originalFilename : {}",picture.getSize(),picture.getName(),picture.getOriginalFilename());
		
		
		try {
			picture.transferTo(new File("d:/upload/" + picture.getOriginalFilename()));
			
		} catch (IllegalStateException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		return "file/view";
	}
	
	
	
	@RequestMapping(path="registUser",method = RequestMethod.GET)
	public String registUser() {
		
		
		
		return "tiles.user.registUser";
	}
	
	
	//�߿�! �ش� Ŀ��� ��ü �ڿ� �� BindingResult ��ü �ֱ�
	//bindingResult ��ü�� command ��ü �ٷ� �ڿ� ���ڷ� ����ؾ� �Ѵ�
	@RequestMapping(path="registUser",method = RequestMethod.POST)
	public String registUser(@Valid UserVo uservo, BindingResult result, Model model, MultipartFile profile) {
		// ������ ��ü��������
		//new UserVoValidator().validate(uservo, result);
		// ���⼭ �ٲ� ����� ����(������ü�ϱ�)
		if(result.hasErrors()) {
			logger.debug("result has error");
			model.addAttribute("user", uservo);
			return "user/registUser";
		}
		
		try {
			String fileExtension = FileUtil.getFileExtension(profile.getOriginalFilename());
			String realFileName = UUID.randomUUID().toString()+fileExtension;
			
			profile.transferTo(new File("d:/upload/" + realFileName));
			uservo.setFilename(profile.getOriginalFilename());
			
			uservo.setRealfilename(realFileName);
			
		} catch (IllegalStateException | IOException e) {
			e.printStackTrace();
		}
		
		
		int insertCnt = 0;
		
		try {
			insertCnt = userService.registUser(uservo);
		} catch (Exception e) {
			insertCnt= 0;
		}
		
		 
		
		if(insertCnt==1) {
			return "redirect:/user/pagingUser";
		}else {
			model.addAttribute("user", uservo);
			return "user/registUser";
		}
		
		
	}
	
	
	@RequestMapping("userInfo")
	public String userInfo(String userid, Model model) {
		
		model.addAttribute("user",userService.selectUser(userid));
		
		
		return "tiles.user.user";
		//return "user/user";
	}
	
	///////////////////////////////////////////////////////
	@RequestMapping("pagingUserTiles")
	public String paingUserTiles(PageVo pageVo,Model model) {
		
		Map<String, Object> map = userService.selectPagingUser(pageVo);
		
		List<UserVo> userList = (List<UserVo>)map.get("userList");
		int userCnt = (int)map.get("userCnt");
		logger.debug("userCnt : {}" ,userCnt);
		logger.debug("pageVo.getPageSize() : {}" ,pageVo.getPageSize());
		int pagination = (int)Math.ceil((double)userCnt/pageVo.getPageSize());
		
		model.addAttribute("userList", userList);
		model.addAttribute("pagination", pagination);
		model.addAttribute("pageVo", pageVo);
		
		

		int startPage = 1;
		int endPage = pagination;
		if((pageVo.getPage()-2)>2) {
			if(pageVo.getPage()==pagination||pageVo.getPage()==pagination-1||pageVo.getPage()==pagination-3) {
				startPage = pagination-4;
				
			}else{
				startPage = pageVo.getPage()-2;
			}
			if(startPage+4<pagination) {
				endPage = startPage+4;
			}
		}
		logger.debug("start : {}",startPage);
		if((pageVo.getPage()+2)<pagination-1) {
			if(pageVo.getPage()==1) {
				endPage = pageVo.getPage()+4;
			}else if(pageVo.getPage() == 2) {
				endPage = pageVo.getPage()+3;
			}else if(pageVo.getPage() == 4){
				endPage = pageVo.getPage()+1;
			}else {
				endPage = pageVo.getPage()+2;
			}
			if(endPage-4>pageVo.getPage()) {
				startPage = endPage-4;
			}
		}
		model.addAttribute("startPage", startPage);
		model.addAttribute("endPage", endPage);
		

		
		//tiles-definition�� ������ name
		return "tiles.user.pagingUser";
	}
	//////////////////////////////////////////////////////////////
	//����� ����Ʈ�� ���� ������ ȭ�鸸 �������� ����
	@RequestMapping("pagingUserAjaxView")
	public String pagingUserAjaxView() {
		return "tiles.user.pagingUserAjax";
	}
	
	
	///////////////////////////////////////////////////////////////
	
	@RequestMapping("pagingUserAjax")
	public String pagingUserAjax(PageVo pageVo,Model model) {
		
		Map<String, Object> map = userService.selectPagingUser(pageVo);
		
		List<UserVo> userList = (List<UserVo>)map.get("userList");
		int userCnt = (int)map.get("userCnt");
		logger.debug("userCnt : {}" ,userCnt);
		logger.debug("pageVo.getPageSize() : {}" ,pageVo.getPageSize());
		int pagination = (int)Math.ceil((double)userCnt/pageVo.getPageSize());
		
		model.addAttribute("userList", userList);
		model.addAttribute("pagination", pagination);
		model.addAttribute("pageVo", pageVo);
		
		

		int startPage = 1;
		int endPage = pagination;
		if((pageVo.getPage()-2)>2) {
			if(pageVo.getPage()==pagination||pageVo.getPage()==pagination-1||pageVo.getPage()==pagination-3) {
				startPage = pagination-4;
				
			}else{
				startPage = pageVo.getPage()-2;
			}
			if(startPage+4<pagination) {
				endPage = startPage+4;
			}
		}
		logger.debug("start : {}",startPage);
		if((pageVo.getPage()+2)<pagination-1) {
			if(pageVo.getPage()==1) {
				endPage = pageVo.getPage()+4;
			}else if(pageVo.getPage() == 2) {
				endPage = pageVo.getPage()+3;
			}else if(pageVo.getPage() == 4){
				endPage = pageVo.getPage()+1;
			}else {
				endPage = pageVo.getPage()+2;
			}
			if(endPage-4>pageVo.getPage()) {
				startPage = endPage-4;
			}
		}
		model.addAttribute("startPage", startPage);
		model.addAttribute("endPage", endPage);
		
		
		
		//tiles-definition�� ������ name
		return "jsonView";
	}
	
	////////////////////////////////////////////////////////////////////
	//HTML �� ����
	
	@RequestMapping("pagingUserAjaxHtml")
	public String pagingUserAjaxHtml(PageVo pageVo,Model model) {
		
		Map<String, Object> map = userService.selectPagingUser(pageVo);
		
		List<UserVo> userList = (List<UserVo>)map.get("userList");
		int userCnt = (int)map.get("userCnt");
		logger.debug("userCnt : {}" ,userCnt);
		logger.debug("pageVo.getPageSize() : {}" ,pageVo.getPageSize());
		int pagination = (int)Math.ceil((double)userCnt/pageVo.getPageSize());
		
		model.addAttribute("userList", userList);
		model.addAttribute("pagination", pagination);
		model.addAttribute("pageVo", pageVo);
		
		

		int startPage = 1;
		int endPage = pagination;
		if((pageVo.getPage()-2)>2) {
			if(pageVo.getPage()==pagination||pageVo.getPage()==pagination-1||pageVo.getPage()==pagination-3) {
				startPage = pagination-4;
				
			}else{
				startPage = pageVo.getPage()-2;
			}
			if(startPage+4<pagination) {
				endPage = startPage+4;
			}
		}
		logger.debug("start : {}",startPage);
		if((pageVo.getPage()+2)<pagination-1) {
			if(pageVo.getPage()==1) {
				endPage = pageVo.getPage()+4;
			}else if(pageVo.getPage() == 2) {
				endPage = pageVo.getPage()+3;
			}else if(pageVo.getPage() == 4){
				endPage = pageVo.getPage()+1;
			}else {
				endPage = pageVo.getPage()+2;
			}
			if(endPage-4>pageVo.getPage()) {
				startPage = endPage-4;
			}
		}
		model.addAttribute("startPage", startPage);
		model.addAttribute("endPage", endPage);
		
		
		
		//tiles-definition�� ������ name
		return "user/pagingUserAjaxHtml";
		
		/*0,1�Ѵ� Ż��
		  pagingUserAjaxHtml ==> /WEB-INF/view/user/pagingUserAjaxHtml.jsp 
		 */
	}
	
	
	
	///////////////////////////////////////////////////////////////
	@RequestMapping("pagingUser")
	public String paingUser(PageVo pageVo,Model model) {
		
		Map<String, Object> map = userService.selectPagingUser(pageVo);
		
		List<UserVo> userList = (List<UserVo>)map.get("userList");
		int userCnt = (int)map.get("userCnt");
		logger.debug("userCnt : {}" ,userCnt);
		logger.debug("pageVo.getPageSize() : {}" ,pageVo.getPageSize());
		int pagination = (int)Math.ceil((double)userCnt/pageVo.getPageSize());
		
		model.addAttribute("userList", userList);
		model.addAttribute("pagination", pagination);
		model.addAttribute("pageVo", pageVo);
		
		

		int startPage = 1;
		int endPage = pagination;
		if((pageVo.getPage()-2)>2) {
			if(pageVo.getPage()==pagination||pageVo.getPage()==pagination-1||pageVo.getPage()==pagination-3) {
				startPage = pagination-4;
				
			}else{
				startPage = pageVo.getPage()-2;
			}
			if(startPage+4<pagination) {
				endPage = startPage+4;
			}
		}
		logger.debug("start : {}",startPage);
		if((pageVo.getPage()+2)<pagination-1) {
			if(pageVo.getPage()==1) {
				endPage = pageVo.getPage()+4;
			}else if(pageVo.getPage() == 2) {
				endPage = pageVo.getPage()+3;
			}else if(pageVo.getPage() == 4){
				endPage = pageVo.getPage()+1;
			}else {
				endPage = pageVo.getPage()+2;
			}
			if(endPage-4>pageVo.getPage()) {
				startPage = endPage-4;
			}
		}
		model.addAttribute("startPage", startPage);
		model.addAttribute("endPage", endPage);
		

		
		
		return "user/pagingUser";
	}
	
	
	
	
	public String paingUser2(PageVo pageVo,Model model) {
		
		Map<String, Object> map = userService.selectPagingUser(pageVo);
		
		List<UserVo> userList = (List<UserVo>)map.get("userList");
		int userCnt = (int)map.get("userCnt");
		logger.debug("userCnt : {}" ,userCnt);
		logger.debug("pageVo.getPageSize() : {}" ,pageVo.getPageSize());
		int pagination = (int)Math.ceil((double)userCnt/pageVo.getPageSize());
		
		model.addAllAttributes(map);
		
		model.addAttribute("userList", userList);
		model.addAttribute("pagination", pagination);
		model.addAttribute("pageVo", pageVo);
		
		

		int startPage = 1;
		int endPage = pagination;
		if((pageVo.getPage()-2)>2) {
			if(pageVo.getPage()==pagination||pageVo.getPage()==pagination-1||pageVo.getPage()==pagination-3) {
				startPage = pagination-4;
				
			}else{
				startPage = pageVo.getPage()-2;
			}
			if(startPage+4<pagination) {
				endPage = startPage+4;
			}
		}
		logger.debug("start : {}",startPage);
		if((pageVo.getPage()+2)<pagination-1) {
			if(pageVo.getPage()==1) {
				endPage = pageVo.getPage()+4;
			}else if(pageVo.getPage() == 2) {
				endPage = pageVo.getPage()+3;
			}else if(pageVo.getPage() == 4){
				endPage = pageVo.getPage()+1;
			}else {
				endPage = pageVo.getPage()+2;
			}
			if(endPage-4>pageVo.getPage()) {
				startPage = endPage-4;
			}
		}
		model.addAttribute("startPage", startPage);
		model.addAttribute("endPage", endPage);
		

		
		
		return "user/pagingUser";
	}
	
	@RequestMapping("excelDownload")
	public String excelDownload(Model model) {
		List<String> header = new ArrayList<>();
		header.add("����ھ��̵�");
		header.add("������̸�");
		header.add("����ں���");
		header.add("�������");
		header.add("�����ּ�");
		header.add("���ּ�");
		header.add("�����ȣ");
		
		model.addAttribute("header", header);
		model.addAttribute("data", userService.selectAllUser());
		
		return "userExcelDownloadView";
	}
	
	//localhost/user/profile
	@RequestMapping("profile")
	public void profile(HttpServletResponse resp, String userid, HttpServletRequest req) {

		resp.setContentType("image");
		
		// userid �Ķ���͸� �̿��Ͽ�
		// userService ��ü�� ���� ������� ���� ���� �̸��� ȹ��
		// ���� ������� ���� ������ �о�鿩 resp��ü�� outputStream���� ���� ����
		
		UserVo userVo = userService.selectUser(userid);
		
		String path = "";
		if(userVo.getRealfilename() == null) {
			path = req.getServletContext().getRealPath("/image/unknown.png");
		}else {
		
			path = userVo.getRealfilename();
		}
		logger.debug("path : {} ", path);
		try {
			
			FileInputStream fis = new FileInputStream(path);
			ServletOutputStream sos = resp.getOutputStream();
			
			byte[] buff = new byte[512];
			while(fis.read(buff)!=-1) {
				
				sos.write(buff);
				
			}
			
			
			fis.close();
			sos.flush();
			sos.close();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		
		
	}
	
	
	@RequestMapping("/profileDownload")
	public String fileDownload(String userid, Model model) {
		//1. �ٿ�ε� ������ ��� => realFilename
		//2. �ٿ�ε�� ������ ���ϸ� => filename
		//1, 2�� model�� �־��ش�
		//userid �Ķ���͸� �����ٰ� ����
		//�Ķ���͸� �̿��ؼ� �ش� ������� ��������(realFilename, filename)�� ��ȸ
		
		UserVo userVo = userService.selectUser(userid);
		model.addAttribute("realFilename", userVo.getRealfilename());
		model.addAttribute("filename", userVo.getFilename());
		
		
		return "fd";
	}
	
	
}
