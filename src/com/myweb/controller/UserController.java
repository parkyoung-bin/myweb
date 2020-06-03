package com.myweb.controller;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.myweb.user.service.DeleteServiceImpl;
import com.myweb.user.service.GetInfoServiceImpl;
import com.myweb.user.service.JoinServiceImpl;
import com.myweb.user.service.LoginServiceImpl;
import com.myweb.user.service.UpdateServiceImpl;
import com.myweb.user.service.UserService;

@WebServlet("*.user")
public class UserController extends HttpServlet {


	private static final long serialVersionUID = 1L;

	public UserController() {
		super();

	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doAction(request, response);

	}


	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doAction(request, response);
	}
	protected void doAction(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");

		String uri = request.getRequestURI();
		String path = request.getContextPath();
		String command = uri.substring(path.length());

		System.out.println(command);

		UserService service = null;

		if(command.equals("/user/join.user")) { //조인화면 이동
			// join기능 처리
			request.getRequestDispatcher("user_join.jsp").forward(request, response);

		}else if(command.equals("/user/joinForm.user")) {//회원가입처리 메서드 입니다.
			service = new JoinServiceImpl();
			int result = service.execute(request, response);

			if(result ==1) { //이미 존재하는 회원인 경우

				response.setContentType("text/html; charset=utf-8");
				PrintWriter out = response.getWriter();

				out.println("<script>");
				out.println("alert('이미 존재하는 회원입니다')");
				out.println(" location.href='user_join.jsp'  ");
				out.println("</script>");


			}else {//회원가입 성공
				
				response.setContentType("text/html; charset=utf-8");
				
				PrintWriter out = response.getWriter();
				out.println("<script>");
				out.println("alert('환영합니다 ')");
				out.println(" location.href='user_login.jsp'  ");
				out.println("</script>");


			}
		}else if(command.equals("/user/login.user") ) {	//로그인 화면 이동
			request.getRequestDispatcher("user_login.jsp").forward(request, response);
			
		}else if(command.equals("/user/loginForm.user") ) {//러그인 요청
			/*
			 * 1.loginserviceimpl 생성후
			 * 2. 서비스에서 폼값 받고 dao로그인 메서드 사용후 로그인
			 * 3. 성공시 userid이름으로 세션 id 저장 후 user_mypage 리다이렉트
			 * 4. 실패시 out.println이용해서 비번 확인 하라고 전송
			 */
			service = new LoginServiceImpl();
			int result = service.execute(request, response);
			if(result==0) {
				response.setContentType("text/html; charset=utf-8");
				PrintWriter out = response.getWriter();

				out.println("<script>");
				out.println("alert('아이디 또는 비밀번호를 확인해주세요')");
				out.println(" location.href='user_login.jsp'  ");
				out.println("</script>");
				
				
				
			}else {
				
				response.sendRedirect("user_mypage.jsp");
			}
		
		}else if(command.equals("/user/mypage.user")) {
			request.getRequestDispatcher("user_mypage.jsp").forward(request,response);
		}else if(command.equals("/user/update.user")) {
			/*
			 * 게시판에 진입할 때 회원의 대한 모든 정보를 가져옵니다.
			 * 1.GetInfoServiceImpl 서비스 생성하고 , DAO의 getInfo()메서드로 회원 아이디에 따른 회원정보를 가지고 온다.
			 * 2.회원정보를 request에 저장하고 화면에서는 얻은 정보를 태그에 출력
			 * 
			 */
			service = new GetInfoServiceImpl();
			service.execute(request, response);
			
			
			request.getRequestDispatcher("user_update.jsp").forward(request,response);
			
		}else if(command.equals("/user/updateForm.user")) {
			service = new UpdateServiceImpl();
			int result = service.execute(request, response);
			if(result ==1) { //업데이트 성공
			
			response.sendRedirect("mypage.user"); //마이페이지로
				
			}else { //실패
			response.setContentType("text/html; charset=utf-8");
		
			PrintWriter out = response.getWriter();
			out.println("<script>");
			out.println("alert('정보 수정 실패')");
			out.println(" location.href='user_mypage.jsp'  ");
			out.println("</script>");
			
			}
		}else if (command.equals("/user/delete.user")) {
			
			request.getRequestDispatcher("user_delete.jsp").forward(request, response);;
		}else if(command.equals("/user/deleteForm.user")) {
			service = new DeleteServiceImpl();
			int result = service.execute(request, response);
			
			if (result ==1) {
				HttpSession session = request.getSession();
				session.invalidate();
				response.setContentType("text/html; charset=utf-8");
				
				PrintWriter out = response.getWriter();
				out.println("<script>");
				out.println("alert('탈퇴 성공')");
				out.println(" location.href='/MyWeb/main.do'  ");
				out.println("</script>");
			}else {
				response.setContentType("text/html; charset=utf-8");
				
				PrintWriter out = response.getWriter();
				out.println("<script>");
				out.println("alert('삭제 실패')");
				out.println(" location.href='user_mypage.jsp'  ");
				out.println("</script>");
			}
		}else if(command.equals("/user/logout.user")) {//로그아웃
			HttpSession session = request.getSession();
			session.invalidate();
			response.sendRedirect("../main.do");
		}
	}
}

