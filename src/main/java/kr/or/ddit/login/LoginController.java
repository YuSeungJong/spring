package kr.or.ddit.login;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.ImportResource;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import kr.or.ddit.user.model.UserVo;
import kr.or.ddit.user.service.UserService;


@ImportResource("classpath:/kr/or/ddit/config/spring/datasource-context.xml")
@RequestMapping("login")
@Controller
public class LoginController {

	private static final Logger logger = LoggerFactory.getLogger(LoginController.class);
	
	@Resource(name = "userService")
	private UserService userService;
	
	// /�ٿ��� �������
	// Ư�� �Ķ������ ���� ������ ���� ��ġ�Ҷ�
	@RequestMapping(path = "view",method = RequestMethod.GET)
	public String view() {
		
		return "login";
	}
	
	
	public String process(String userid, String pass, int price) {
		
		logger.debug("userid : {}", userid);
		logger.debug("usernm : {}", pass);
		logger.debug("price : {}", price);
		
		return "";
	}
	
	@RequestMapping(path = "process",method = RequestMethod.POST)
	public String pocess(UserVo userVo, HttpSession session, RedirectAttributes ra) {
		logger.debug("userVo : {}", userVo);
		
		UserVo dbUser = userService.selectUser(userVo.getUserid());
		
		if(dbUser!=null && userVo.getPass().equals(dbUser.getPass())) {
			session.setAttribute("S_USER", dbUser);
			return "main";
		}else {
			
			//���������� session�� ����Ͽ� �Ӽ��� ����
			//�����̷�Ʈ ó���� �Ϸ� �Ǹ� ������ �����ӿ�ũ���� �ڵ����� session���� ����
			ra.addFlashAttribute("msg", "�߸��� ����� �����Դϴ�.");
			
			//�Ϲ� �Ӽ��� �߰��� ��� : addAttribute
			//�����̷�Ʈ �������� �Ķ���ͷ� ���޵ȴ�.
			ra.addAttribute("userid", "brown");
			ra.addAttribute("usernm", "브라운");
			
			//session.setAttribute("msg", "�߸��� ����� �����Դϴ�.");
			
			
			return "redirect:/login/view";
		}
	}
	
}
