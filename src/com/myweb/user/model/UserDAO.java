package com.myweb.user.model;

import java.sql.*;

import javax.naming.InitialContext;
import javax.sql.DataSource;

import com.myweb.util.JdbcUtil;

public class UserDAO {
/*
 * 1. DAO는 클래스를 단순히 DB연동기능을 수행하면 되기 때문에
 * 불필요하게 여러개 생성되도록 구성할 필요가 없기 때문에 싱글톤 패턴으로 생성합니다
 */
	
	//1. 스스로 객체를 생성하고 1개로 제한 
	private static UserDAO instance = new UserDAO();
	
	//2. 직접 생성할 수 없도록 생성자에 private을 붙임
	private UserDAO() {
		//객체가 생성될때 JDBC드라이버 로딩
		try {
			 ct = new InitialContext(); //초기 설정 값들이 저장 됨
			ds = (DataSource)ct.lookup("java:comp/env/jdbc/oracle");
			
			
		}catch (Exception e) {
			System.out.println("클래스 로딩중 에러");
		}
	}
	//3. 외부에서 객체생성을 요구할 때 getter 메서드를 통해 반환함
	public static UserDAO getInstance() {
		return instance;
	}

	// DAO에 회원관리에 필요한 기능을 메서드로 생성, DB관련 변수를 멤버 변수로 선언
//	private String url = "jdbc:oracle:thin:@localhost:1521/XEPDB1";
//	private String uid = "JSP";
//	private String upw = "JSP";
	
	private DataSource ds;
	private InitialContext ct;
	
	private Connection conn =null;
	private PreparedStatement pstmt = null;
	private ResultSet rs = null;
	
	public int checkId(String id) {
		int result =0;
		
		String sql = "select * from users where id = ?";
		
		try {
			conn = ds.getConnection();
			
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, id);
			rs=pstmt.executeQuery();
			
			if(rs.next()) {//중복의 의미
				result=1;
			}else {
				result=0;
			}
			
		}catch (Exception e) {
			e.printStackTrace();
		}finally {
			JdbcUtil.close(conn, pstmt, rs);
		}
		return result;
	}
	
	public int insert(UserVO vo) {
		int result = 0;
		
		String sql = "insert into users(id,pw,name,email,address) values(?,?,?,?,?)";
				
		try {
			
			conn = ds.getConnection();
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1,vo.getId());
			pstmt.setString(2,vo.getPw());
			pstmt.setString(3,vo.getName());
			pstmt.setString(4,vo.getEmail());
			pstmt.setString(5,vo.getAddress());
			
			result = pstmt.executeUpdate(); //성공시 1을반환 실패시 0을 반환
			
		}catch (Exception e) {
			e.printStackTrace();
		}finally {
			JdbcUtil.close(conn, pstmt, null);
			
		}
		
		return result;
		
	}
	public int login(String id,String pw) {
		int result =0;
		
		String sql = "select * from users where id = ? and pw =?";
		
		try {
			
			conn = ds.getConnection();
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, id);
			pstmt.setString(2, pw);
			
			rs = pstmt.executeQuery();
			
			if(rs.next()){
				result = 1;
			}else{
				result = 0;
			}
			
		}catch (Exception e) {
			e.printStackTrace();
			
		}finally{
			JdbcUtil.close(conn, pstmt, rs);
		}
		return result;
	}
	
	public UserVO getInfo(String id) {
		UserVO vo = new UserVO();
		
		String sql = "select * from users where id = ?";
		
		try {
			conn=ds.getConnection();
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, id);
			
			rs = pstmt.executeQuery();
			if(rs.next()) {
		
				 vo.setId(rs.getString("id"));
				 vo.setName(rs.getString("name"));
				 vo.setEmail(rs.getString("email"));
				 vo.setAddress(rs.getString("address"));
				 vo.setRegdate(rs.getTimestamp("regdate"));
					
			}
			
		}catch (Exception e) {
			 e.printStackTrace();
		}finally {
			JdbcUtil.close(conn, pstmt, rs);
		}
		
		
		return vo;
		
		
	}
	public int update(UserVO vo) {
		int result = 0;
		String sql = "update users set pw=?,name=?,email=?,address=? where id =?";
		
		try {
			conn=ds.getConnection();
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1,vo.getPw());
			pstmt.setString(2,vo.getName());
			pstmt.setString(3,vo.getEmail());
			pstmt.setString(4,vo.getAddress());
			pstmt.setString(5,vo.getId());
			
			
			
					
			result =pstmt.executeUpdate();
					
		}catch (Exception e) {
			e.printStackTrace();
		}finally {
			JdbcUtil.close(conn, pstmt, rs);
		}
		
		
		
		return result;
	}
	
	public int delete(String id) {
		int result = 0;
		String sql = "delete from users where id=?";
		try {
			conn=ds.getConnection();
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1,id);
			
			result = pstmt.executeUpdate();
			

		}catch (Exception e) {
			e.printStackTrace();
		}finally {
			JdbcUtil.close(conn, pstmt, null);
		}
		return result;
	}
}
	
