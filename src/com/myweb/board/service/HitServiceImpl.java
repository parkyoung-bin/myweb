package com.myweb.board.service;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.myweb.board.model.BoardDAO;

public class HitServiceImpl implements BoardService{

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) {
		// 화면에 넘어온 쿠키값
		Cookie[] arr = request.getCookies();
		boolean bool = true;
		String bno = request.getParameter("bno");
		
		for(Cookie c : arr) {
			
			if(c.getName().equals(bno)) { //쿠키이름이 게시글 번호와 동일한지 검사
				bool = false;
				break;
			}
		}
		
		
		if(bool) {
			BoardDAO dao = BoardDAO.getInstance();
			dao.upHit(bno);
			
		}
		
		
		
		//중복 증가방지 쿠키
		Cookie cookie = new Cookie(bno,bno);
		cookie.setMaxAge(30);
		response.addCookie(cookie);
		
		
	}

	
	
}
