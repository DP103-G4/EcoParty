package tw.dp103g4.news;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;
import com.google.gson.JsonObject;

@SuppressWarnings("serial")
@WebServlet("/NewsServlet")
public class NewsServlet extends HttpServlet {
	private final static String CONTENT_TYPE = "text/html; charset=UTF-8";
	private NewsDao newsDao = null;


	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
		Gson gson = new Gson();
		StringBuilder jsonIn = new StringBuilder();
		BufferedReader br = request.getReader();
		String line = "";
		while ((line = br.readLine()) != null) {
			jsonIn.append(line);
		}
		System.out.println("input: " + jsonIn);
		
		JsonObject jsonObject = gson.fromJson(jsonIn.toString(), JsonObject.class);
		if (newsDao == null) {
			newsDao = new NewsDaoMySql();
		}
		String action = jsonObject.get("action").getAsString();
		switch (action) {
		case "getAll":
			List<News> newsList = newsDao.getAll();
			writeText(response, gson.toJson(newsList));
			break ;
		default:
			writeText(response, "");
			break;
		}
		
	}
	
	private void writeText(HttpServletResponse response, String outText) throws IOException {
		response.setContentType(CONTENT_TYPE);
		PrintWriter out = response.getWriter();
		out.print(outText);
		System.out.println("output: " + outText);
		
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		if (newsDao == null) {
			newsDao = new NewsDaoMySql();
		}
		List<News> newsList = newsDao.getAll();
		writeText(response, new Gson().toJson(newsList));
	}

}
