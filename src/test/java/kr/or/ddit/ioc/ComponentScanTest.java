package kr.or.ddit.ioc;

import static org.junit.Assert.*;

import javax.annotation.Resource;

import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import kr.or.ddit.test.config.WebTestConfig;
import kr.or.ddit.user.model.UserVo;
import kr.or.ddit.user.repository.UserDao;
import kr.or.ddit.user.service.UserService;

@ContextConfiguration("classpath:/kr/or/ddit/ioc/component-scan.xml")
@RunWith(SpringJUnit4ClassRunner.class)
public class ComponentScanTest extends WebTestConfig{
	
	@Resource(name="userDao")
	private UserDao userDao;
	
	@Resource(name="userService")
	private UserService userService;
	
	//@Repository ������̼��� ������ userDaoImpl ������ ���� ���������� ���Ϳ��ʿ� ��� �Ǿ����� Ȯ��
	@Test
	public void userDaoImplSpringBeanTest() {
		assertNotNull(userDao);
		
		UserVo userVo = userDao.selectUser("brown");
		assertEquals("����", userVo.getUsernm());
	}
	
	//userServiceImpl ������ ���� ���������� �����̳ʿ� ��� �Ǿ����� Ȯ��
	@Test
	public void userServiceImplSpringBeanTest() {
		assertNotNull(userService);
		
		UserVo userVo = userService.selectUser("brown");
		assertEquals("����", userVo.getUsernm());
	}

}







