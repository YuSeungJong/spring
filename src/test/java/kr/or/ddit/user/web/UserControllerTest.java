package kr.or.ddit.user.web;

import static org.junit.Assert.*;
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

import kr.or.ddit.common.model.PageVo;
import kr.or.ddit.test.config.WebTestConfig;
import kr.or.ddit.user.model.UserVo;


@ContextConfiguration(locations = {"classpath:/kr/or/ddit/config/spring/application-context.xml",
"classpath:/kr/or/ddit/config/spring/root-context.xml"})
@WebAppConfiguration		//������ ȯ���� Web����� appliction Context�� ����
@RunWith(SpringJUnit4ClassRunner.class)
public class UserControllerTest extends WebTestConfig{

	@Test
	public void wiewTest() throws Exception {
		//perform() : ���𰡸� ������ �Ѵ�
		//status().isOk() : 200
		//view().name("hello") : viewname ��ȯ
		//model().attributeExists("userVo") : ����ִ� �Ӽ� ��ȯ
		MvcResult mvcResult = mockMvc.perform(get("/user/allUser")).andExpect(status().isOk())
										   .andExpect(view().name("/user/allUser"))
										   .andDo(print())
										   .andReturn();
		
		ModelAndView mav = mvcResult.getModelAndView();
		
		assertEquals("/user/allUser", mav.getViewName());
	
	}
	
	@Test
	public void pagingUserTest() throws Exception {

		MvcResult mvcResult = mockMvc.perform(get("/user/pagingUser").param("p", "1000"))
										   .andExpect(status().isOk())
										   .andExpect(view().name("user/pagingUser"))
										   .andDo(print())
										   .andReturn();

	}
	
	@Test
	public void pagingUserTest2() throws Exception {

		MvcResult mvcResult = mockMvc.perform(get("/user/pagingUser").param("p", "1000"))
										   .andExpect(status().isOk())
										   .andExpect(view().name("user/pagingUser"))
										   .andDo(print())
										   .andReturn();

	}
	
	@Test
	public void userInfoTest() throws Exception {
		MvcResult mvcResult = mockMvc.perform(get("/user/userInfo").param("userid", "brown"))
				   .andExpect(status().isOk())
				   .andExpect(view().name("user/user"))
				   .andDo(print())
				   .andReturn();
		
		
	}
	
	@Test
	public void registUserTest() throws Exception {
		MvcResult mvcResult = mockMvc.perform(get("/user/registUser"))
				   .andExpect(status().isOk())
				   .andExpect(view().name("user/registUser"))
				   .andDo(print())
				   .andReturn();
		
	}
	
	@Test
	public void modifyUserTest() throws Exception {
		MvcResult mvcResult = mockMvc.perform(get("/user/modifyUser"))
				   .andExpect(status().isOk())
				   .andExpect(view().name("user/userModify"))
				   .andDo(print())
				   .andReturn();
		
	
	}
	
	

}
