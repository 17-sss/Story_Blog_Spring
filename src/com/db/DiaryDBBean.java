package com.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

 
public class DiaryDBBean {
//============================================== 싱글턴!	
	private static DiaryDBBean instance = new DiaryDBBean();
	private DiaryDBBean() {

	}
	
	
	public static DiaryDBBean getInstance() {
		return instance;
	}
	
//============================================== 싱글턴!
	
	
	public static Connection getConnection() {
		Connection con = null;
		try {
			String jdbcUrl = "jdbc:oracle:thin:@localhost:1521:orcl";
			String dbId = "scott";
			String dbPass = "tiger";
			Class.forName("oracle.jdbc.driver.OracleDriver");
			con = DriverManager.getConnection(jdbcUrl, dbId, dbPass);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return con;
	}
	
	
	
	
	public void close (Connection con, ResultSet rs, PreparedStatement pstmt) {
		if (pstmt != null) try {pstmt.close();} catch (SQLException ex) {}
		if (con != null) try {con.close();} catch (SQLException ex) {}
		if (rs != null) try {rs.close();} catch (SQLException ex) {}
	}
	
	// 일기 추가
	public void insertDiary(DiaryDataBean diary) {
		String sql="";
		Connection conn = getConnection();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		int number=0;
		try {
			pstmt = conn.prepareStatement("select diarySer.nextval from dual");
			rs = pstmt.executeQuery();
			if (rs.next())
				number = rs.getInt(1) + 1;
			else number = 1;
			
			System.out.println(diary.getEmail()); // Test
			
			sql = "insert into diary(num, email, diaryid, subject, cdate, content, ip, filename0, filesize0, filename1, filesize1,";
			sql += "filename2, filesize2,filename3, filesize3,filename4, filesize4)";
			sql += "values(?,?,?,?,sysdate,?,?,?,?,?,?,?,?,?,?,?,?)"; //+ filename, filesize 추가 / ? 추가 ( 다중 파일 업로드 )
			
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, number);
			pstmt.setString(2, diary.getEmail()); // !!
			pstmt.setString(3, diary.getDiaryid());
			pstmt.setString(4, diary.getSubject());
			pstmt.setString(5, diary.getContent());
			pstmt.setString(6, diary.getIp());
			pstmt.setString(7, diary.getFilename0()); //+
			pstmt.setInt(8, diary.getFilesize0()); //+
			pstmt.setString(9, diary.getFilename1()); //+
			pstmt.setInt(10, diary.getFilesize1()); //+
			pstmt.setString(11, diary.getFilename2()); //+
			pstmt.setInt(12, diary.getFilesize2()); //+
			pstmt.setString(13, diary.getFilename3()); //+
			pstmt.setInt(14, diary.getFilesize3()); //+
			pstmt.setString(15, diary.getFilename4()); //+
			pstmt.setInt(16, diary.getFilesize4()); //+
			
			
			pstmt.executeUpdate();
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close(conn, rs, pstmt);
		}
		
	}
	
	// 일기장 추가 (미완성)
	public void insertDiaryid (DiaryDataBean diary) {
		String sql="";
		Connection conn = getConnection();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		int number=0;
		try {
			pstmt = conn.prepareStatement("select diarySer.nextval from dual");
			rs = pstmt.executeQuery();
			if (rs.next())
				number = rs.getInt(1) + 1;
			else number = 1;
			
			System.out.println(diary.getEmail()); // Test
			
			sql = "insert into diary(num, email, diaryid) ";
			sql += "values(?,?,?)";
			
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, number);
			pstmt.setString(2, diary.getEmail());
			pstmt.setString(3, diary.getDiaryid());
			
			pstmt.executeUpdate();
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close(conn, rs, pstmt);
		}
		
	}
	
	// 각 일기장의 일기 수
	public int getDiaryCount(String diaryid, String email) throws SQLException {
		int x = 0;
		String sql = "SELECT nvl(count(*),0) FROM diary WHERE diaryid = ? and email=?";
		Connection conn = getConnection();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		int number = 0;
		
		try {
		pstmt = conn.prepareStatement(sql);
		pstmt.setString(1, diaryid);
		pstmt.setString(2, email);
		
		rs = pstmt.executeQuery();
		if (rs.next()) { x = rs.getInt(1); }
		} catch(Exception e) {
			e.printStackTrace();
		} finally {
			close(conn, rs, pstmt);
		}
		
		return x;
	}
	// 각 일기장의 사진 전체 갯수 카운트
	public int getImgDiaryCountTotal(String diaryid, String email) throws SQLException {
		int x = 0;
		String sql = "SELECT nvl(count(filename0),0) + nvl(count(filename1),0) + nvl(count(filename2),0) + nvl(count(filename3),0) + nvl(count(filename4),0) "
				+ "FROM diary WHERE diaryid = ? and email=?";
		Connection conn = getConnection();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		int number = 0;
		
		try {
		pstmt = conn.prepareStatement(sql);
		pstmt.setString(1, diaryid);
		pstmt.setString(2, email);
		
		rs = pstmt.executeQuery();
		if (rs.next()) { x = rs.getInt(1); }
		} catch(Exception e) {
			e.printStackTrace();
		} finally {
			close(conn, rs, pstmt);
		}
		
		return x;
	}
	// 각 일기장의 사진 페이징 카운트
	public int getImgDiaryCount(String diaryid, String email) throws SQLException {
		int x = 0;
		String sql = "SELECT nvl(count(*),0) FROM diary WHERE diaryid = ? and email=?";
		Connection conn = getConnection();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		int number = 0;
		
		try {
		pstmt = conn.prepareStatement(sql);
		pstmt.setString(1, diaryid);
		pstmt.setString(2, email);
		
		rs = pstmt.executeQuery();
		if (rs.next()) { x = rs.getInt(1); }
		} catch(Exception e) {
			e.printStackTrace();
		} finally {
			close(conn, rs, pstmt);
		}
		
		return x;
	}		
		
	
	// 일기(목록) 가져오기
	public List getDiaries(int startRow, int endRow, String email, String diaryid) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		List diaryList = null;
		
		System.out.println("getDiaries: "+startRow+":"+endRow+"/"+email+"/"+diaryid); // Test
		
		String sql = "";
		try {
			conn = getConnection();
			sql = "select * from (select rownum rnum, b.* from (select num, email, diaryid, subject, cdate, content, ip, filename0, filename1, filename2, "
					+ "filename3, filename4 from diary where diaryid = ? and email = ? order by cdate desc) b) where rnum between ? and ?"; //filename 추가
			System.out.println(sql);
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, diaryid);
			pstmt.setString(2, email);
			pstmt.setInt(3, startRow);
			pstmt.setInt(4, endRow);
			rs = pstmt.executeQuery();
			
			if (rs.next()) {
				diaryList = new ArrayList();
				do {
					DiaryDataBean diary = new DiaryDataBean();
					diary.setNum(rs.getInt("num"));
					diary.setEmail(rs.getString("email"));
					diary.setDiaryid(rs.getString("diaryid"));
					diary.setSubject(rs.getString("subject"));
					diary.setCdate(rs.getTimestamp("cdate"));
					diary.setContent(rs.getString("content"));
					diary.setIp(rs.getString("ip"));
					diary.setFilename0(rs.getString("filename0")); //이미지(파일)
					diary.setFilename1(rs.getString("filename1"));
					diary.setFilename2(rs.getString("filename2"));
					diary.setFilename3(rs.getString("filename3"));
					diary.setFilename4(rs.getString("filename4"));
					diaryList.add(diary);
				} while (rs.next()); 
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			close(conn, rs, pstmt);
		}
		return diaryList;
		
	}
	// 이미지만 불러오기. // 파일 복수에선 쓰지 않음. (현재 쓰지않음) 
	public List getImgDiaries (int startRow, int endRow, String email, String diaryid) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		List diaryList = null;
		
		System.out.println("getImgDiaries: "+startRow+":"+endRow+"/"+email+"/"+diaryid); // Test
		
		String sql = "";
		try {
			conn = getConnection();
			sql = "select * from (select rownum rnum, b.* from (select num, email, diaryid, subject, cdate, content, ip, filename0, filename1, filename2, filename3, filename4 "
					+ "from diary where diaryid = ? and email = ? and filename0 is not null and filename1 is not null and filename2 is not null and filename3 is not null and filename4 is not null"
					+ " order by cdate desc) b) where rnum between ? and ?"; //filename 추가
			System.out.println(sql);
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, diaryid);
			pstmt.setString(2, email);
			pstmt.setInt(3, startRow);
			pstmt.setInt(4, endRow);
			rs = pstmt.executeQuery();
			
			if (rs.next()) {
				diaryList = new ArrayList();
				do {
					DiaryDataBean diary = new DiaryDataBean();
					diary.setNum(rs.getInt("num"));
					diary.setEmail(rs.getString("email"));
					diary.setDiaryid(rs.getString("diaryid"));
					diary.setSubject(rs.getString("subject"));
					diary.setCdate(rs.getTimestamp("cdate"));
					diary.setContent(rs.getString("content"));
					diary.setIp(rs.getString("ip"));
					diary.setFilename0(rs.getString("filename0")); //이미지(파일)
					diary.setFilename1(rs.getString("filename1"));
					diary.setFilename2(rs.getString("filename2"));
					diary.setFilename3(rs.getString("filename3"));
					diary.setFilename4(rs.getString("filename4"));
					diaryList.add(diary);
				} while (rs.next()); 
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			close(conn, rs, pstmt);
		}
		return diaryList;
		
	}
	
	// 일기 수정할때 정보 불러옴.
	public DiaryDataBean getDiary(int num, String email, String diaryid) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		DiaryDataBean diary = null;
		String sql = "";
		try {
			conn = getConnection();
			sql="select * from diary where num = ? and email = ? and diaryid = ?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, num);
			pstmt.setString(2, email);
			pstmt.setString(3, diaryid);
			rs=pstmt.executeQuery();
			
			diary = new DiaryDataBean();
			if(rs.next()) {
				diary.setNum(rs.getInt("num"));
				diary.setEmail(rs.getString("email"));
				diary.setDiaryid(rs.getString("diaryid"));
				diary.setSubject(rs.getString("subject"));
				diary.setCdate(rs.getTimestamp("cdate"));
				diary.setContent(rs.getString("content"));
				diary.setIp(rs.getString("ip"));
				diary.setFilename0(rs.getString("filename0"));
				diary.setFilesize0(rs.getInt("filesize0"));
				diary.setFilename1(rs.getString("filename1"));
				diary.setFilesize1(rs.getInt("filesize1"));
				diary.setFilename2(rs.getString("filename2"));
				diary.setFilesize2(rs.getInt("filesize2"));
				diary.setFilename3(rs.getString("filename3"));
				diary.setFilesize3(rs.getInt("filesize3"));
				diary.setFilename4(rs.getString("filename4"));
				diary.setFilesize4(rs.getInt("filesize4"));
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			close(conn, rs, pstmt);
		}
	
		return diary;
		
	}
	
	// 일기 수정Pro 메소드 - 파일 업로드
	public int updateDiary (DiaryDataBean diary) {
		String sql ="";
		Connection conn = getConnection();
		PreparedStatement pstmt = null;
		int chk= 0; // int 변수 하나 생성.
		ResultSet rs = null; 
		
		try {
			conn = getConnection();
			sql = "update diary set diaryid=?, subject=?, content=?, filename0 =?, filename1 =?, filename2 =?,";
			sql +=" filename3 =?, filename4 =? where num=? and email = ?";
			pstmt = conn.prepareStatement(sql);
		
			pstmt.setString(1, diary.getDiaryid());
			pstmt.setString(2, diary.getSubject());
			pstmt.setString(3, diary.getContent());
			pstmt.setString(4, diary.getFilename0());
			pstmt.setString(5, diary.getFilename1());
			pstmt.setString(6, diary.getFilename2());
			pstmt.setString(7, diary.getFilename3());
			pstmt.setString(8, diary.getFilename4());
			pstmt.setInt(9, diary.getNum());
			pstmt.setString(10, diary.getEmail());
			
			chk = pstmt.executeUpdate(); //컬럼이 업데이트가 되었을때 숫자를 반환
			pstmt.executeUpdate();
			
			System.out.println(diary);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			close(conn, null, pstmt);
		}
		return chk;
		
	}
	
	public int deleteDiary (int num, String email, String diaryid) throws Exception {
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = "delete from diary where num=? and email = ? and diaryid =?";
		int x = -1;
		try {
			conn = getConnection();
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, num);
			pstmt.setString(2, email);
			pstmt.setString(3, diaryid);
			x=pstmt.executeUpdate();
		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			close(conn, rs, pstmt);
		}
		return x;
		
	}
}

