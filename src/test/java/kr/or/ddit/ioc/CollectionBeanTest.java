package kr.or.ddit.ioc;

import static org.junit.Assert.*;

import javax.annotation.Resource;

import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import kr.or.ddit.test.config.WebTestConfig;

@ContextConfiguration("classpath:/kr/or/ddit/ioc/ioc.xml")
@RunWith(SpringJUnit4ClassRunner.class)
public class CollectionBeanTest extends WebTestConfig{

	//collectionBean ������ ���� ���������� ���� �Ǿ�����
	@Resource(name="collectionBean")
	private CollectionBean collectionBean;
	
	@Test
	public void collectionBeanTest() {
		assertNotNull(collectionBean);
		assertNotNull(collectionBean.getList());
		assertEquals(3,collectionBean.getList().size());
		assertEquals("sally", collectionBean.getList().get(1));
		assertEquals("����", collectionBean.getMap().get("usernm"));
	}

}
