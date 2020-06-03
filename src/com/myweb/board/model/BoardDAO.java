package com.myweb.board.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.ArrayList;

import javax.naming.InitialContext;
import javax.sql.DataSource;

import com.myweb.util.JdbcUtil;


public class BoardDAO {
	
	private static BoardDAO instance = new BoardDAO();
	
	//2. 직접 생성할 수 없도록 생성자에 private을 붙임
		private BoardDAO() {
			//객체가 생성될때 JDBC드라이버 로딩
			try {
				 ct = new InitialContext(); //초기 설정 값들이 저장 됨
				ds = (DataSource)ct.lookup("java:comp/env/jdbc/oracle");
				
				
			}catch (Exception e) {
				System.out.println("클래스 로딩중 에러");
			}
		}
		//3. 외부에서 객체생성을 요구할 때 getter 메서드를 통해 반환함
		public static BoardDAO getInstance() {
			return instance;
		}

		// DAO에 회원관리에 필요한 기능을 메서드로 생성, DB관련 변수를 멤버 변수로 선언
//		private String url = "jdbc:oracle:thin:@localhost:1521/XEPDB1";
//		private String uid = "JSP";
//		private String upw = "JSP";
		
		private DataSource ds;
		private InitialContext ct;
		
		
		private Connection conn =null;
		private PreparedStatement pstmt = null;
		private ResultSet rs = null;
		
		
		
		// 게시글 등록 메서드
		public void regist(String writer,String title,String content) {
			
			String sql = "insert into board (bno,writer,title,content) values(board_seq.nextval, ?, ?, ?)";
			
			try {
				
				conn = ds.getConnection();
				pstmt = conn.prepareStatement(sql);
				pstmt.setString(1, writer);
				pstmt.setString(2, title);
				pstmt.setString(3, content);
				
				pstmt.executeUpdate();
			}catch (Exception e) {
				e.printStackTrace();
			}finally {
				JdbcUtil.close(conn, pstmt, null);
			}
			
			
			
			
		}
		
		public ArrayList<BoardVO> getList(int pageNum,int amount) {
			ArrayList<BoardVO> list = new ArrayList<>();
			BoardVO vo = new BoardVO();
			//데이터베이스에서 모든 글목록을 조회후 vo에 담고 list에 추가
			String sql = "select *\r\n" + 
					"from(\r\n" + 
					"        select rownum rn,\r\n" + 
					"                bno,\r\n" + 
					"                writer,\r\n" + 
					"                title,\r\n" + 
					"                content,\r\n" + 
					"                regdate,\r\n" + 
					"                hit    \r\n" + 
					"                \r\n" + 
					"        from(\r\n" + 
					"                select *\r\n" + 
					"                from board order by bno desc\r\n" + 
					"            )\r\n" + 
					"            \r\n" + 
					")\r\n" + 
					"where rn > ? and rn <= ?";			
			try {
				conn=ds.getConnection();
				pstmt=conn.prepareStatement(sql);

				//전달되는 페이지 번호를 받아서 페이징 처리
				pstmt.setInt(1, (pageNum-1)*amount);
				pstmt.setInt(2, pageNum*amount);
				
				
				rs = pstmt.executeQuery();
				
				while(rs.next()) {
					int bno = rs.getInt("bno");
					String writer = rs.getString("writer");
					String title = rs.getString("title");
					String content = rs.getString("content");
					Timestamp regdate = rs.getTimestamp("regdate");
					int hit = rs.getInt("hit");
					
					vo= new BoardVO(bno, writer, title, content, regdate, hit);
					list.add(vo);
				}
				
			}catch (Exception e) {
				e.printStackTrace();
			}finally {
				JdbcUtil.close(conn, pstmt, rs);
			}
			
			return list;
		}
		
		//전체 게시글 수를 구하는 메서드
		public int getTotal() {
			int total = 0;
			
			String sql = "select count(*) as total from board";
			
			try {
				conn=ds.getConnection();
				pstmt=conn.prepareStatement(sql);
				rs = pstmt.executeQuery();
				
				if(rs.next()) {
					total = rs.getInt("total");
				}
				
				
			}catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
				
			}finally {
				JdbcUtil.close(conn, pstmt, rs);
			}
			
			return total;
		}
	//게시글 상세보기 메서드
		public BoardVO getContent(String bno){
			
			BoardVO vo = new BoardVO();
			
			String sql = "select * from board where bno=?";
		
			try {
				conn=ds.getConnection();
				pstmt=conn.prepareStatement(sql);
				pstmt.setString(1, bno);
				rs = pstmt.executeQuery();
				
				
				
				if(rs.next()) {
				vo.setBno(rs.getInt("bno"));
				vo.setWriter(rs.getString("writer"));
				vo.setTitle(rs.getString("title"));
				vo.setContent(rs.getString("content"));
				vo.setRegdate(rs.getTimestamp("regdate"));
				vo.setHit(rs.getInt("hit"));
				}
			
				
				
		}catch (Exception e) {
			
			e.printStackTrace();
			
		}finally {
			JdbcUtil.close(conn, pstmt, rs);
		}
		
		return vo;
	}
		
		public void update(String bno,String title,String content) {
			
			
			String sql = "update board set title=? ,content=? where bno=? ";
			
			try {
				conn=ds.getConnection();
				pstmt=conn.prepareStatement(sql);
				pstmt.setString(1, title);
				pstmt.setString(2, content);
				pstmt.setString(3, bno);
				
				pstmt.executeUpdate();
	

			}catch (Exception e) {
				e.printStackTrace();
				
			}finally {
				JdbcUtil.close(conn, pstmt, null);
			}
			
			
			
		}
		public void delete(String bno) {
			String sql = "delete from board where bno=?";
			try {
				conn=ds.getConnection();
				pstmt=conn.prepareStatement(sql);
				pstmt.setString(1, bno);
				
				pstmt.executeUpdate();
				
			}catch (Exception e) {
				e.printStackTrace();
				
			}finally {
				JdbcUtil.close(conn, pstmt, null);
			}
		}
		
		//조회수 관련 메서드
		public void upHit(String bno) {
			String sql = "update board set hit = hit + 1 where bno = ?";
			
			try {
				conn= ds.getConnection();
				pstmt = conn.prepareStatement(sql);
				pstmt.setString(1,bno);
				
				pstmt.executeUpdate();
				
					
			}catch (Exception e) {
				e.printStackTrace();
				
			}finally {
				JdbcUtil.close(conn, pstmt, null);
			}
		}
		
		
		
}