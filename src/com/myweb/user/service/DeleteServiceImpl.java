package com.myweb.user.service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.myweb.user.model.UserDAO;

public class DeleteServiceImpl implements UserService{

	@Override
	public int execute(HttpServletRequest request, HttpServletResponse response) {
		HttpSession session = request.getSession();
		
		String id = (String)session.getAttribute("user_id");
		String pw = request.getParameter("pw");
		UserDAO dao = UserDAO.getInstance();
		int result = dao.login(id, pw);
	
		if(result == 1) { //검증된 유저
			 dao.delete(id);
			return 1;
		}else {//비번 틀림
			return 0;
		}
	
	}
}
