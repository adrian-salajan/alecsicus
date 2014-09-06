package com.yell;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.webapp1.Lion;
import com.yell.webservice.Service;

public class HelloWorld extends HttpServlet {
	private String message;

	  public void init() throws ServletException
	  {
	      // Do required initialization
	      message = "Hello World";
	  }

	  public void doGet (HttpServletRequest request,
	                    HttpServletResponse response)
	            throws ServletException, IOException
	  {
	      // Set response content type
	      response.setContentType("text/html");
	      try {
				
	    	  Lion l = new Lion();
	      // Actual logic goes here.
	      PrintWriter out = response.getWriter();
	      out.println("<html>");
			out.println("<body>");
			out.println("<h1>Hello Servlet "+l.runLion()+"</h1>");
			out.println("</body>");
			out.println("</html>");	
			for( String s : (System.getenv()).keySet() ){
				System.out.println(s +" -> "+ System.getenv(s));
			}
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	      System.out.println(Service.getInstance().getMessages().peek());
	  }
	  
	  public void destroy()
	  {
	      // do nothing.
	  }

}