package com.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
/*import jdk.nashorn.internal.ir.RuntimeNode.Request;*/


public class UserDBBean {
	// 싱글턴
	private static UserDBBean instance = new UserDBBean();
	private UserDBBean() {
		
	}
	
	public static UserDBBean getInstance() {
		return instance;
	}
	// 싱클턴 end.
	
	// 데이터베이스 연결 가져오기
	public static Connection getConnection() {
		Connection conn = null;
		try {
			String jdbcUrl = "jdbc:oracle:thin:@localhost:1521:orcl";
			String dbId = "scott";
			String dbpwd = "tiger";
			Class.forName("oracle.jdbc.driver.OracleDriver");
			conn = DriverManager.getConnection(jdbcUrl, dbId, dbpwd);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return conn;
	}
	
	// Close
	public void close (Connection conn, ResultSet rs, PreparedStatement pstmt) {
		if(conn!=null) try {conn.close();} catch(SQLException ex) {}
		if(rs!=null) try {rs.close();} catch(SQLException ex) {}
		if(pstmt!=null) try {pstmt.close();} catch (SQLException e) {}
	}
	
	// 회원가입 (추가) 메소드
	public void insertUser(UserDataBean user) {
		String sql="";
		Connection conn = getConnection();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		int number=0;
		try {
			sql = "insert into userlist(email, name, pwd, tel, birth, cdate, ip, filename, filesize)";
			sql += "values(?,?,?,?,?, sysdate, ?,?,?)";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, user.getEmail());
			pstmt.setString(2, user.getName());
			pstmt.setString(3, user.getPwd());
			pstmt.setString(4, user.getTel());
			pstmt.setString(5, user.getBirth());
			pstmt.setString(6, user.getIp());
			pstmt.setString(7, user.getFilename());
			pstmt.setInt(8, user.getFilesize());
		    pstmt.executeUpdate();
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close(conn, rs, pstmt);
		}
		
	}
	
	// 회원 수 메소드
	public int getUserCount() throws SQLException {
		int x = 0;
		String sql = "SELECT nvl(count(*),0) FROM userlist";
		Connection conn = getConnection();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		int number = 0;
		
		try {
		pstmt = conn.prepareStatement(sql);
		
		rs = pstmt.executeQuery();
		if (rs.next()) { x = rs.getInt(1); }
		} catch(Exception e) {
			e.printStackTrace();
		} finally {
			close(conn, rs, pstmt);
		}
		
		return x;
	}
	
	// 회원리스트 목록출력 메소드?
	public List getUsers(int startRow, int endRow) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		List usList = null;
		String sql = "";
		try {
			conn = getConnection();
			sql = "select * from (select rownum rnum, a.* from (select email, name, pwd, tel, birth, cdate, ip, filename from userlist)"
					+ " a) where rnum between ? and ? order by cdate desc";
			
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, startRow);
			pstmt.setInt(2, endRow);
			rs = pstmt.executeQuery();
			
			if (rs.next()) {
				usList = new ArrayList();
			
				do {
					UserDataBean user = new UserDataBean();
					user.setEmail(rs.getString("email"));
					user.setName(rs.getString("name"));
					user.setPwd(rs.getString("pwd"));
					user.setTel(rs.getString("tel"));
					user.setBirth(rs.getString("birth"));
					user.setCdate(rs.getTimestamp("cdate"));
					user.setIp(rs.getString("ip"));
					user.setFilename(rs.getString("filename"));
					usList.add(user);
				} while (rs.next()); 
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			close(conn, rs, pstmt);
		}
		return usList;
		
	}
	
	// 회원 수정할때 정보 불러오기
	public UserDataBean getUser(String email, String pwd) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		UserDataBean user = null;
		String sql = "";
		try {
			conn = getConnection();
			sql="select * from userlist where email = ? and pwd = ?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, email);
			pstmt.setString(2, pwd);
			rs=pstmt.executeQuery();
			
			user = new UserDataBean();
			if(rs.next()) {
				user.setEmail(rs.getString("email"));
				user.setName(rs.getString("name"));
				user.setPwd(rs.getString("pwd"));
				user.setTel(rs.getString("tel"));
				user.setBirth(rs.getString("birth"));
				user.setCdate(rs.getTimestamp("cdate"));
				user.setIp(rs.getString("ip"));
				user.setFilename(rs.getString("filename"));
				user.setFilesize(rs.getInt("filesize"));
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			close(conn, rs, pstmt);
		}
	
		return user;
		
	}
	
	// 회원 수정할때 정보 불러오기_2
	public UserDataBean getUser (String email) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		UserDataBean user = null;
		String sql = "";
		try {
			conn = getConnection();
			sql="select * from userlist where email = ?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, email);
			rs=pstmt.executeQuery();
			
			user = new UserDataBean();
			if(rs.next()) {
				user.setEmail(rs.getString("email"));
				user.setName(rs.getString("name"));
				user.setPwd(rs.getString("pwd"));
				user.setTel(rs.getString("tel"));
				user.setBirth(rs.getString("birth"));
				user.setCdate(rs.getTimestamp("cdate"));
				user.setIp(rs.getString("ip"));
				user.setFilename(rs.getString("filename"));
				user.setFilesize(rs.getInt("filesize"));
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			close(conn, rs, pstmt);
		}
	
		return user;
		
	}
	
	 // 로그인시 아이디, 비밀번호 체크 메서드
    // 아이디, 비밀번호를 인자로 받는다.
    public int loginCheck(String email, String pwd) 
    {
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
 
        String dbPW = ""; // db에서 꺼낸 비밀번호를 담을 변수
        int x = -1;
 
        try {
            // 쿼리 - 먼저 입력된 아이디로 DB에서 비밀번호를 조회한다.
            StringBuffer query = new StringBuffer();
            query.append("select pwd from userlist where email=?");
 
            conn = getConnection();
            pstmt = conn.prepareStatement(query.toString());
            pstmt.setString(1, email);
            rs = pstmt.executeQuery();
 
            if (rs.next()) // 입력된 아이디에 해당하는 비번 있을경우
            {
                dbPW = rs.getString("pwd"); // 비번을 변수에 넣는다.
 
                if (dbPW.equals(pwd)) 
                    x = 1; // 넘겨받은 비번과 꺼내온 배번 비교. 같으면 인증성공
                else                  
                    x = 0; // DB의 비밀번호와 입력받은 비밀번호 다름, 인증실패
                
            } else {
                x = -1; // 해당 아이디가 없을 경우
            }
 
            return x;
 
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        } finally {
        	 close(conn, null, pstmt);
        }
    } // end loginCheck()
    
    
   /* // 이메일 중복체크
    public boolean duplicateIdCheck(String email) {
    	Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		boolean x = false;
    	
		try {
            StringBuffer query = new StringBuffer();
            query.append("select email from userlist where email=?");
            
            conn = getConnection();
            pstmt = conn.prepareStatement(query.toString());
            pstmt.setString(1, email);
            rs = pstmt.executeQuery();
            
            if(rs.next()) x= true; //해당 이메일 존재
            
            return x;
		} catch (Exception e) {
			throw new RuntimeException(e.getMessage());
		} finally {
			try {
				close(conn, null, pstmt);
			} catch (Exception e2) {
				throw new RuntimeException(e2.getMessage());
			}
		}	
    }*/
    // 이메일 중복확인 ver.2
    public boolean confirmEmail(String email) {
    	Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
    	boolean result = false;
    	try {
    		conn = getConnection();
    		String sql = "select email from userlist where email =?";
    		pstmt = conn.prepareStatement(sql);
    		pstmt.setString(1, email);
    		rs = pstmt.executeQuery();
    		if (rs.next()) {
    			result = true;
    		}
    	} catch (Exception e) {
			e.printStackTrace();
		} finally {
			close(conn, rs, pstmt);
		}
    	return result;
    }
	
    
    public int deleteUser (String email, String pwd) throws Exception {
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = "delete from userlist where email=? and pwd=?";
		int x = -1;
		try {
			conn = getConnection();
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, email);
			pstmt.setString(2, pwd);
			x=pstmt.executeUpdate();
		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			close(conn, rs, pstmt);
		}
		return x;
	}
    
    public int updateUser (UserDataBean user) {
		String sql ="";
		Connection conn = getConnection();
		PreparedStatement pstmt = null;
		int chk= 0; // int 변수 하나 생성.
		/*ResultSet rs = null; <- 썼던거.(오답)*/
		
		try {
			conn = getConnection();
			sql = "update userlist set name=?, pwd=?, tel=?, birth=?, filename=? where email=?";
			pstmt = conn.prepareStatement(sql);
		
			pstmt.setString(1, user.getName());
			pstmt.setString(2, user.getPwd());
			pstmt.setString(3, user.getTel());
			pstmt.setString(4, user.getBirth());
			pstmt.setString(5, user.getFilename());
			pstmt.setString(6, user.getEmail());
			
			chk = pstmt.executeUpdate(); //컬럼이 업데이트가 되었을때 숫자를 반환
			pstmt.executeUpdate();
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			close(conn, null, pstmt);
		}
		return chk;
		
	}
    
}
