package kr.or.ddit.user.repository;

import java.util.List;

import javax.annotation.Resource;

import org.apache.ibatis.session.SqlSession;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.stereotype.Repository;

import kr.or.ddit.common.model.PageVo;
import kr.or.ddit.user.model.UserVo;



@Repository("userDao")
public class UserDaoImpl implements UserDao{

	@Resource(name = "sqlSessionTemplate")
	private SqlSessionTemplate template;
	

	@Override
	public List<UserVo> selectAllUser() {
		
		
		// select : 리턴되는 값의 복수 유무를 판단
		//			1. 단건  : 일반객체를 반환(UserVo) selectOne()
		//			2. 여러건 : List<UserVo> selectList()
		// insert, update, delete : insert, update, delete 
		
		// 메소드 이름과 실행할 sql id를 동일하게 맞추자(유지보수-다른 개발자의 가독성)
		return template.selectList("users.selectAllUser");
		
		
	}
	
	@Override
	public UserVo selectUser(String userid) {
		
		return template.selectOne("users.selectUser",userid);
	}
	
	
	
	@Override
	public List<UserVo> selectPagingUser(PageVo vo) {
		return template.selectList("users.selectPagingUser",vo);
	
	}

	@Override
	public int selectAllUserCount() {
		return template.selectOne("users.selectAllUserCount");
	
	}

	@Override
	public int modifyUser(UserVo userVo) {
		return template.update("users.modifyUser", userVo);
		
	
	}

	@Override
	public int registUser(UserVo userVo) {
		
		return template.insert("users.registUser", userVo);
		
	
	}

	@Override
	public int deleteUser(String userid) {
		
		return template.delete("users.deleteUser", userid);
		
		
	
	}
	
	
}
