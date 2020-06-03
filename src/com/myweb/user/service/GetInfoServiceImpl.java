package com.myweb.user.service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.websocket.Session;

import com.myweb.user.model.UserDAO;
import com.myweb.user.model.UserVO;

public class GetInfoServiceImpl implements UserService{

	@Override
	public int execute(HttpServletRequest request, HttpServletResponse response) {
		
		HttpSession session = request.getSession();
		String id =(String)session.getAttribute("user_id");
		
		
		UserDAO dao = UserDAO.getInstance();
		
		UserVO vo = dao.getInfo(id);
		
		request.setAttribute("vo",vo);
		
		return 0; //신경쓰지않음
		
		
	}
	

}
