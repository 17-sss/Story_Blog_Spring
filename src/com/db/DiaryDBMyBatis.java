package com.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;

public class DiaryDBMyBatis extends MybatisConnector {
	private final String namespace = "diary.mybatis";
	private static DiaryDBMyBatis instance = new DiaryDBMyBatis();
	private DiaryDBMyBatis() {}
	public static DiaryDBMyBatis getInstance() {
		return instance;
	}
	SqlSession sqlSession;
	
	// 각 일기장의 일기 수
	public int getDiaryCount(String diaryid, String email) {
		int x = 0;
		sqlSession=sqlSession();
		Map<String, String> map = new HashMap<String, String>();
		map.put("diaryid", diaryid);
		map.put("email", email);
		x = sqlSession.selectOne(namespace+".getDiaryCount", map);
		sqlSession.close();
		return x;
	}
	
	// 일기(목록) 가져오기
	public List getDiaries(int startRow, int endRow, String email, String diaryid) {
		sqlSession= sqlSession();
		Map map = new HashMap();
		map.put("startRow", startRow);
		map.put("endRow", endRow);
		map.put("email", email);
		map.put("diaryid", diaryid);
		List li = sqlSession.selectList(namespace + ".getDiaries" ,map);
		sqlSession.close();
		return li;
	}
	
	// 각 일기장의 사진 전체 갯수 카운트
	public int getImgDiaryCountTotal(String diaryid, String email) {
		int x = 0;
		sqlSession=sqlSession();
		Map<String, String> map = new HashMap<String, String>();
		map.put("diaryid", diaryid);
		map.put("email", email);
		x = sqlSession.selectOne(namespace+".getImgDiaryCountTotal", map);
		sqlSession.close();
		return x;
	}
	
	// 일기 수정 폼 (정보 가져오기)
	public DiaryDataBean getDiary(int num, String email, String diaryid) {
		sqlSession= sqlSession();
		Map map = new HashMap();
		map.put("num", num);
		map.put("email", email);
		map.put("diaryid", diaryid);
		
		DiaryDataBean diary=sqlSession.selectOne(namespace + ".getDiary" ,map);
		sqlSession.commit();
		sqlSession.close();
		
		return diary;
	}
	
	// 일기 수정Pro 메소드 - 파일 업로드
	public int updateDiary (DiaryDataBean diary) {
		sqlSession= sqlSession();
		int chk = sqlSession.update(namespace+".updateDiary", diary);
		sqlSession.commit();
		sqlSession.close();
		
		return chk;
	}
	
	// 일기 삭제
	public int deleteDiary (int num, String email, String diaryid) throws Exception {
		sqlSession= sqlSession();
		Map map = new HashMap();
		map.put("num", num);
		map.put("email", email);
		map.put("diaryid", diaryid);
		int chk = sqlSession.delete(namespace+".deleteDiary", map);
		sqlSession.commit();
		sqlSession.close();
		
		return chk;	
	}
	
	// 일기 쓰기
	public void insertDiary(DiaryDataBean diary) {
		sqlSession= sqlSession();
		int number = sqlSession.selectOne(namespace + ".getNextNumber",diary);
		number=number+1;
		
		diary.setNum(number);
	
		sqlSession.insert(namespace + ".insertDiary", diary);
		sqlSession.commit();
		sqlSession.close();
	}
	
}
