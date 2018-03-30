package com.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;

public class UserDBMyBatis extends MybatisConnector {
	private final String namespace = "user.mybatis";
	private static UserDBMyBatis instance = new UserDBMyBatis();
	private UserDBMyBatis() {}
	public static UserDBMyBatis getInstance() {
		return instance;
	}
	
	SqlSession sqlSession;
	
	// 회원 수 메소드
	public int getUserCount() {
		int x = 0;
		sqlSession=sqlSession();
		x = sqlSession.selectOne(namespace+".getUserCount");
		sqlSession.close();
		return x;
	}
	
	
	// 회원리스트 목록출력 메소드?
	public List getUsers(int startRow, int endRow) {
		sqlSession= sqlSession();
		Map map = new HashMap();
		map.put("startRow", startRow);
		map.put("endRow", endRow);
		List li = sqlSession.selectList(namespace + ".getUsers",map);
		sqlSession.close();
		return li;
	}
	
	// 회원 수정할때 정보 불러오기
	public UserDataBean getUser(String email, String pwd) {
		sqlSession= sqlSession();
		Map map = new HashMap();
		map.put("email", email);
		map.put("pwd", pwd);
		
		UserDataBean user = sqlSession.selectOne(namespace+".getUser", map);
		
		sqlSession.commit();
		sqlSession.close();
		
		return user;
	}
	
	// 회원 수정할때 정보 불러오기_2
	public UserDataBean getUser (String email) {
		sqlSession= sqlSession();
		Map map = new HashMap();
		map.put("email", email);
		
		UserDataBean user = sqlSession.selectOne(namespace+".getUser2", map);
		
		sqlSession.commit();
		sqlSession.close();
		
		return user;
	}
	
	// 회원 Update
	public int updateUser (UserDataBean user) {
		sqlSession= sqlSession();
		int chk = sqlSession.update(namespace+".updateUser", user);
		sqlSession.commit();
		sqlSession.close();
		
		return chk;
	}
	
	// 회원 강제 탈퇴
	public int deleteUser (String email, String pwd) throws Exception {
		sqlSession= sqlSession();
		Map map = new HashMap();
		map.put("email", email);
		map.put("pwd", pwd);
		int chk = sqlSession.delete(namespace+".deleteUser", map);
		sqlSession.commit();
		sqlSession.close();
		
		return chk;
	} 
	
	
	// 회원가입 (추가) 메소드
	public void insertUser(UserDataBean user) {
		sqlSession= sqlSession();
		sqlSession.insert(namespace + ".insertUser" ,user);
		sqlSession.commit();
		sqlSession.close();
	}
	
	// 이메일 중복확인 
	public boolean confirmEmail(String email) {
		sqlSession = sqlSession();
		Map map = new HashMap();
		Map map2 = new HashMap();
		map.put("email", email);
		boolean chk=true;
		
		map2=sqlSession.selectOne(namespace+".confirmEmail",map);
		
		if (map2!=null) {
			chk=true;
		}else {
			chk=false;
		}
		sqlSession.close();
		
		return chk;
	}
	// 로그인 체크
	public int loginCheck(String email, String pwd) {
		sqlSession= sqlSession();
		String dbPW = ""; // db에서 꺼낸 비밀번호를 담을 변수
        int x = -1;
        Map map = new HashMap();
		
        map.put("email", email);
		map.put("pwd", pwd);
		
		UserDataBean user = sqlSession.selectOne(namespace+".loginCheck", map);
		
		// 쿼리 - 먼저 입력된 아이디로 DB에서 비밀번호를 조회한다.
            if (user!=null) // 입력된 아이디에 해당하는 비번 있을경우
            {
                dbPW = user.getPwd();
 
                if (dbPW.equals(pwd)) 
                    x = 1; // 넘겨받은 비번과 꺼내온 배번 비교. 같으면 인증성공
                else                  
                    x = 0; // DB의 비밀번호와 입력받은 비밀번호 다름, 인증실패
                
            } else {
                x = -1; // 해당 아이디가 없을 경우
            }
 
            return x;
 
       
    } // end loginCheck()
	// 로그인 체크  <<보류>>
	/* public int loginCheck(String email, String pwd) {
		sqlSession= sqlSession();
		String dbPW="";
		Map map = new HashMap();
		Map map2 = new HashMap();
		map.put("email", email);
		map.put("pwd", pwd);
		
		int x = -1;
		
//		map2 = sqlSession.selectOne(namespace+".loginCheck", map);
		
		if (map.get(email) != null) { 
            dbPW = (String) map.get("pwd");
            
            if (dbPW.equals(pwd)) 
                x = 1; // 넘겨받은 비번과 꺼내온 배번 비교. 같으면 인증성공
            else                  
                x = 0; // DB의 비밀번호와 입력받은 비밀번호 다름, 인증실패
            
        } else {
            x = -1; // 해당 아이디가 없을 경우
        }
		sqlSession.close();
		
		return x;
	}*/

	
}
