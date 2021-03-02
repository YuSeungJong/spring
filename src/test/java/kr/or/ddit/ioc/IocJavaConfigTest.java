package kr.or.ddit.ioc;

import static org.junit.Assert.*;

import javax.annotation.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import kr.or.ddit.ioc.config.IocJavaConfig;
import kr.or.ddit.test.config.WebTestConfig;
import kr.or.ddit.user.service.UserService;



/*@ContextConfiguration(locations = {"classpath:/kr/or/ddit/config/spring/application-context.xml",
		"classpath:/kr/or/ddit/config/spring/root-context.xml",
		"classpath:/kr/or/ddit/config/spring/datasource-context.xml",
		"classpath:/kr/or/ddit/ioc/component-scan.xml"})*/
@ContextConfiguration(classes = {IocJavaConfig.class})
@RunWith(SpringJUnit4ClassRunner.class)
@ComponentScan(basePackages = {"kr.or.ddit"})
public class IocJavaConfigTest {
	
	@Resource(name="userServiceCons")
	private UserService userServiceCons;
	
	@Resource(name="userService")
	private UserService userService;
	
	@Resource(name="userService")
	private UserService userService2;
	
	@Resource(name="userServicePrototype")
	private UserService userServicePrototype;
	

	@Resource(name="userServicePrototype")
	private UserService userServicePrototype2;
	
	@Resource(name="dbConfig")
	private DbConfig dbCongig;
	
	
	// userServiceCons ������ ���� ���������� ���� �Ǿ����� �׽�Ʈ
	@Test
	public void userServiceConsTest() {
		/***Given***/
		
		/***When***/

		/***Then***/
		assertNotNull(userServiceCons);
	}
	
	@Test
	public void beanScopeTest() {
		//������ ������ singleton �������� ���� �ΰ��� ��ü�� �� Ŭ������ ���� �������Ƿ� �����ؾ���
		//������ ������ singlton ������ bean ������Ʈ�� �������� �ϳ��� ��ü�� �����ȴ�
		assertNotEquals(userService, userServiceCons);
	}
	
	
	@Test
	public void beanScopeTest2() {
		//������ ������ ���� ���� �޾����Ƿ� userService, userService2�� ���� ��ü��.
		assertEquals(userService, userService2);
	}

	@Test
	public void beanScopePrototypeTest() {
		//������ userServicePrototype ���� ����(scope : prototype)
		assertNotEquals(userServicePrototype, userServicePrototype2);
	}
	
	@Test
	public void propertyPlaceholderTest() {
		assertNotNull(dbCongig);
		assertEquals("SJK", dbCongig.getUsername());
		assertEquals("java", dbCongig.getPassword());
		assertEquals("oracle.jdbc.driver.OracleDriver", dbCongig.getDriverClassName());
		assertEquals("jdbc:oracle:thin:@localhost:1521:xe", dbCongig.getUrl());
	}
	
}

















