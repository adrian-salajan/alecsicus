package com.yell.webservice.servlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.yell.webservice.Service;
import com.yell.webservice.YellMessage;

public class JsonServlet extends HttpServlet {

	private Service service = Service.getInstance();

	@Override
	protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		doGet(req, resp);
	}

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

		PrintWriter writer = resp.getWriter();
		try {
			if (!service.getMessages().isEmpty()) {
				YellMessage poll = service.getMessages().poll();
				Gson gson = new GsonBuilder().setPrettyPrinting().create();
				gson.toJson(poll, writer);

			}
		} catch (Exception e) {
			System.err.println(e);
			writer.append(e.getMessage());
		} finally {
			writer.close();
		}

	}

}
