package kr.or.ddit.hello;


import static org.junit.Assert.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;


import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.web.servlet.ModelAndView;

import kr.or.ddit.test.config.WebTestConfig;
import kr.or.ddit.user.model.UserVo;

/*
 * java - gui swing, awt, java fx, swt
 */

@ContextConfiguration(locations = {"classpath:/kr/or/ddit/config/spring/application-context.xml",
								   "classpath:/kr/or/ddit/config/spring/root-context.xml"})
@WebAppConfiguration		//������ ȯ���� Web����� appliction Context�� ����
@RunWith(SpringJUnit4ClassRunner.class)
public class HelloControllerTest extends WebTestConfig {
	
	//���������� �� ���Դ��� üũ
	//@Resource(name="helloController")
	// ���������߿� ���� ������ Ÿ���� ������ ���� �����Ѵ�.
	// ���� ������ Ÿ���� ������ ���� ������ ���� ��� @Qulifier ������̼��� ����
	// Ư�� ������ ���� �̸��� ��Ī�� �� �ִ�.
	//	==> @Resource ������̼� �ϳ��� ��� ���� ��
	/*
	 * @Autowired private HelloController helloController;
	 */
	
	
	
	
	//localhost/hello/view
	@Test
	public void wiewTest() throws Exception {
		//perform() : ���𰡸� ������ �Ѵ�
		//status().isOk() : 200
		//view().name("hello") : viewname ��ȯ
		//model().attributeExists("userVo") : ����ִ� �Ӽ� ��ȯ
		MvcResult mvcResult = mockMvc.perform(get("/hello/view")).andExpect(status().isOk())
										   .andExpect(view().name("hello"))
										   .andExpect(model().attributeExists("userVo"))
										   .andDo(print())
										   .andReturn();
		
		ModelAndView mav = mvcResult.getModelAndView();
		
		assertEquals("hello", mav.getViewName());
		UserVo userVo = (UserVo)mav.getModel().get("userVo");
		
		assertEquals("����", userVo.getUsernm());
		
		
	}
	
	@Test
	public void pathVariableTest() throws Exception {
		MvcResult mvcResult = mockMvc.perform(get("/hello/path/cony"))
									   .andExpect(status().isOk())
									   .andExpect(model().attributeExists("subpath"))
									   .andDo(print())
									   .andReturn();
	}
	
	
	
	
}














