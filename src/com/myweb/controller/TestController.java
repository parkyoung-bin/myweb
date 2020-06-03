package com.myweb.controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


//1. 어노테이션을 확장자 패턴으로 변경(.test로 끝나는 요청은 다 받습니다)

@WebServlet("*.test")
public class TestController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       

    public TestController() {
        super();
     
    }

	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doAction(request,response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doAction(request,response);
	}

	//get post 요청 하나로 묶기
	protected void doAction(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//3.요청 분기(uri기반)
		
		String uri = request.getRequestURI();
		String path = request.getContextPath();//컨패트
		
		String command = uri.substring(path.length());
		
		System.out.println(command);
		if(command.equals("/controller/login.test")) {
			
		}
		
			
	}
	
}
