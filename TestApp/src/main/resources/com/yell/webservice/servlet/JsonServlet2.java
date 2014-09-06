package com.yell.webservice.servlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class JsonServlet2
 */
public class JsonServlet2 extends HttpServlet {

	private Service service = Service.getInstance();
	@Override
	protected void service(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		doGet(req, resp);
	}
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		
		PrintWriter writer = resp.getWriter();
		try {
			if (!service.getMessages().isEmpty()) {
				YellMessage poll = service.getMessages().poll();
				writer.append(poll.getMessage());
			}
		} catch (Exception e) {
			System.err.println(e);
			writer.append(e.getMessage());
		} finally {
			writer.close();
		}
			
		
	}


}
