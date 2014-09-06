package com.yell.webservice.servlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class OnOffServlet2
 */
public class OnOffServlet2 extends HttpServlet {

	private Service service = Service.getInstance();

	@Override
	protected void service(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		doGet(req, resp);
	}

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {

		String yell = req.getParameter("yell");
		if (yell != null) {
			Service.getInstance().setRun(Boolean.valueOf(yell));
		}
		resp.setContentType("text/html");

		PrintWriter writer = resp.getWriter();
		try {
			writer.append("<!DOCTYPE html>");
			writer.append("<html><body>");

			writer.append("Yell service is now: "
					+ (Service.getInstance().isRun() ? "ON." : "OFF."));

			writer.append("<br/> You can turn it ");

			String base = req.getScheme() + "://" + req.getServerName() + ":"
					+ req.getServerPort() + req.getContextPath()
					+ "/YellStatus?";
			String onLink = "<a href=\"" + base + "yell=true\">ON</a>";
			String offLink = "<a href=\"" + base + "yell=false\">OFF</a>";

			writer.append(onLink + " or " + offLink + ".");

			writer.append("</body></html>");

		} catch (Exception e) {
			System.err.println(e);
			writer.append(e.getMessage());
		} finally {
			writer.close();
		}

	}

}