package com.myweb.controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.myweb.board.service.BoardService;
import com.myweb.board.service.ContentServiceImpl;
import com.myweb.board.service.DeleteServiceImpl;
import com.myweb.board.service.GetListServiceImpl;
import com.myweb.board.service.HitServiceImpl;
import com.myweb.board.service.RegistServiceImpl;
import com.myweb.board.service.UpdateServiceImpl;

//1.확장자 패턴으로 매핑을 변경
@WebServlet("*.board")
public class BoardController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       

    public BoardController() {
        super();
        
    }


	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doAction(request, response);
	}

	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doAction(request, response);
	}
	
	//2.get , post 방식을 하나로 묶는다.
	protected void doAction(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		request.setCharacterEncoding("utf-8");
		
		String uri = request.getRequestURI(); // URI 정보
		String path = request.getContextPath(); // 컨택스트 패스 정보

		String command = uri.substring(path.length());
		System.out.println(command);
		
		BoardService service = null;
		
		
		
		if(command.equals("/board/list.board")) { //게시글 화면 요청 (목록 요청이 들어올때 게시글 정보를 갖고 화면에 가도록 처리)
		service = new GetListServiceImpl();
		service.execute(request, response);
		

//			response.sendRedirect("board_list.jsp"); 리다이렉트 권장 x
			request.getRequestDispatcher("board_list.jsp").forward(request, response);
			
		}else if(command.equals("/board/write.board")) { //글쓰기 화면 요청
			request.getRequestDispatcher("board_write.jsp").forward(request, response);
			
		}else if (command.equals("/board/regist.board")) { // 글 등록
			service = new RegistServiceImpl();
			service.execute(request, response);
			
			response.sendRedirect("list.board");//다시 컨트롤러를 태워서 목록으로 보냄
				
		}else if(command.equals("/board/content.board")) {//상세보기 화면 요청
			
			
			service = new ContentServiceImpl();
			service.execute(request, response);
			
			//조회수 관련된 service 실행 
			service = new HitServiceImpl();
			service.execute(request, response);
			
			
			request.getRequestDispatcher("board_content.jsp").forward(request, response);
		}else if (command.equals("/board/modify.board")) { //수정 화면 요청
			/*
			 * 0. 화면에서 경로에 bno값을 전달
			 * 1.service = new ContentsServiceImpl() 서비스 재활용
			 * 2. 포워드 board_mpodify.jsp
			 * 3. 화면에서는 태그안에 데이터 값을 출력
			 */
			service = new ContentServiceImpl();
			service.execute(request, response);
			
			request.getRequestDispatcher("board_modify.jsp").forward(request, response);
			
		}else if(command.equals("/board/update.board")) {
			/*
			 * 1.UpdateServiceImpl() 생성후 execute()메서드 실행
			 * 2.서비스에서는 bno,title,content 받아서 DAO의 update() 메서드 실행
			 * 3.update()메서드에서는 sql 문으로 업데이트 진행
			 * 4.컨트롤러에서는 페이지 이동을 content화면으로 이동
			 */
			service = new UpdateServiceImpl();
			service.execute(request, response);
			
			
			//다시 컨트롤러 태울 때는 redirect 보내고 , content.board 가 필요한 값을 강제로 담아서 보내면 됩니다.
			response.sendRedirect("content.board?bno="+request.getParameter("bno"));
		
		
	}else if(command.equals("/board/delete.board")) { //게시글 삭제
		/*
		 * 1.board_content 화면에서 삭제 버튼 클릭시 bno를 넘겨줍니다
		 * 2.DeleteServiceImpl()을 생성후 dao의 delete()메서드 실행
		 * 3.삭제 후 목록으로 이동
		 * 
		 */
		service = new DeleteServiceImpl();
		service.execute(request, response);
		response.sendRedirect("list.board");
	

	}

}
}
