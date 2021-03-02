package kr.or.ddit.mvc.web;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.View;
import org.springframework.web.servlet.view.json.MappingJackson2JsonView;

import kr.or.ddit.user.model.UserVo;

@RequestMapping("ajax")
@Controller
public class AjaxController {
	
	private static final Logger logger = LoggerFactory.getLogger(AjaxController.class);

	
	//리퀘스트매핑메서드가 붙은 메서드가 실행하기 전에 얘를 먼저 실행하고 얘가 반환하는 값을 모델객체에 자동으로 넣어줌(Model)
	@ModelAttribute(name = "")
	public List<String> rangers(){
		List<String> rangers = new ArrayList<>();
		rangers.add("brown");
		rangers.add("cony");
		rangers.add("james");
		rangers.add("sally");
		rangers.add("moon");
		
		return rangers;
	}
	
	@RequestMapping("view")
	public String view() {
		return "ajax/ajaxView";
	}
	
	
	@RequestMapping("form")
	public String form(UserVo userVo) {
		//메소드 인자에 모델 안써도 자동을 추가됨
		logger.debug("userVo : {}", userVo);
		
		return "jsonView";
	}
	
	
	//json 응답 테스트
	//요즘 많이 씀
	//localhost/ajax/jsonView
	@RequestMapping("jsonView")
	public String jsonView() {
		List<String> rangers = new ArrayList<>();
	
		//어플리케이션 컨텍스트에서 등록한 <bean id="jsonView" class="org.springframework.web.servlet.view.json.MappingJackson2JsonView"> 여기의 id랑 같은애를 반환
		return "jsonView";
		
	}
	
	
	@RequestMapping("jsonViewViewObj")
	public View jsonViewViewObj() {
		return new MappingJackson2JsonView();
	}
	
	//요즘은 잘 안씀
	@RequestMapping("jsonViewMav")
	public ModelAndView jsonViewMav() {
		ModelAndView mav = new ModelAndView();
		
		mav.setViewName("jsonView");
		
		return mav;
	}
	
	
	
	
}
