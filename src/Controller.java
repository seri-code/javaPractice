package controller.mall.icia;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import auth.services.mall.icia.Authentication;


@WebServlet({"/LogIn", "/LogOut", "/Join"}) //클라이언트가 요청한 값(action=''과 일치)
public class Controller extends HttpServlet {
	private static final long serialVersionUID = 1L;
       

    public Controller() {
        super();
        
    }

	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
	}

	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");//post방식에서만 제어됌
		Authentication auth;
		//System.out.println(request.getRequestURI());
		//System.out.println(request.getContextPath());
		
		String reqValue = request.getRequestURI().substring(request.getContextPath().length()+1);
		
		if(reqValue.equals("LogIn")) {
			auth = new Authentication();
			auth.entrance(request,1);
		}
		
		else if(reqValue.equals("LogOut")) {
			auth = new Authentication();
			auth.entrance(request,2);
		}
		
		else if(reqValue.equals("Join")) {
			auth = new Authentication();
			auth.entrance(request,3);
		}
		
		if(false) {
			response.sendRedirect("response.jsp");
		}else {
			RequestDispatcher dispatcher = request.getRequestDispatcher("response.jsp");
			dispatcher.forward(request, response);
		}
		

}
}
