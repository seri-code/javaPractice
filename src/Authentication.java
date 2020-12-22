package auth.services.mall.icia;

import javax.servlet.http.HttpServletRequest;

public class Authentication {

	 public Authentication() { //생성자 호출
}
	

	public void entrance(HttpServletRequest req, int serviceCode) {
		
	     if(serviceCode == 1) {
	         this.logIn(req);
	      }
	      if(serviceCode == 2) {
	          this.logOut(req);
	       }
	       if(serviceCode == 3) {
	          this.join(req);
	       }
	    }


	private void logIn(HttpServletRequest req) {
		//System.out.println(req.getParameterValues("accessInfo")[0]);
		//System.out.println(req.getParameterValues("accessInfo")[1]);
		req.setAttribute("message", "로그인성공");
	}
	
	private void logOut(HttpServletRequest req) {
	}
	
	private void join(HttpServletRequest req) {	
	}
}
			 

