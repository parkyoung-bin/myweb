package com.myweb.util.upload;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

@MultipartConfig(
		
		location="D:\\course\\jsp\\upload", // 임시 업로드 할 경로
		maxFileSize = -1, 					// 파일 허용 크기
		maxRequestSize= -1,					// 요청에대한 최대파일허용크기
		fileSizeThreshold = 1024			//임시저장하는 크기
		)

@WebServlet("/MultiUploadServlet")
public class MultiUploadServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    
    public MultiUploadServlet() {
        super();
      
    }
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
	}
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		
		ArrayList<String> list = new ArrayList<>(); // 파일이름을 추가할 리스트
		String realFileName = null;
		try {
		Collection<Part> parts = request.getParts();
		
		for(Part part : parts) {
			System.out.println(part.getName());
			System.out.println(part.getContentType());
			if(part.getHeader("Content-Disposition").contains("filename=")){
				
				if(part.getSize()>0) {
				realFileName = part.getSubmittedFileName();
				part.write("D:\\course\\jsp\\upload\\"+realFileName);
				}
				
				list.add(realFileName);//리스트 추가
				
			}
				
			
		}
		
	}catch (Exception e) {
		e.printStackTrace();
	}
		System.out.println(list.toString());
		
//		Connection conn = null;
//		PreparedStatement pstmt = null;
//		try {
//			conn = DriverManager.getConnection(url,user,password);
//			
//			for(String fileName: list) {
//				pstmt = conn.prepareStatement("insert into upload(id,filename) values(?,?)");
//				pstmt.setString(1,"kkk123");
//				pstmt.setString(2,fileName);
//				pstmt.executeUpdate();
//			}
//			
//			
//			
//			
//		}catch (Exception e) {
//			// TODO: handle exception
//		}
		
		
		
	}
}
