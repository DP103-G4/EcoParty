package tw.dp103g4.news;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.Base64;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;
import com.google.gson.JsonObject;

import tw.dp103g4.main.ImageUtil;

@SuppressWarnings("serial")
@WebServlet("/NewsServlet")
public class NewsServlet extends HttpServlet {
	private final static String CONTENT_TYPE = "text/html; charset=utf-8";
	private NewsDao newsDao = null;

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
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

		String newsJson = jsonObject.get("news").getAsString();
		System.out.println("newsJson = " + newsJson);
		News news = gson.fromJson(newsJson, News.class);
		byte[] image = null;
		// 檢查是否有上傳圖片
		if (jsonObject.get("imageBase64") != null) {
			String imageBase64 = jsonObject.get("imageBase64").getAsString();
			if (imageBase64 != null && !imageBase64.isEmpty()) {
				image = Base64.getMimeDecoder().decode(imageBase64);
			}
		}
		int count = 0;
		switch (action) {
		case "getAll":
			List<News> newsList = newsDao.getAll();
			writeText(response, gson.toJson(newsList));
			break;
		case "insert":			
			count = newsDao.insert(news, image);
			writeText(response, String.valueOf(count));
			break;
		case "update":	
			count = newsDao.update(news, image);
			writeText(response, String.valueOf(count));
			break;
		case "delete":
			int newsId = jsonObject.get("newsId").getAsInt();
			count = newsDao.delete(newsId);
			writeText(response, String.valueOf(count));
			break;
		case "getImage":
			break;
		default:

		if (action.equals("getAll")) {
			List<News> news = newsDao.getAll();
			writeText(response, gson.toJson(news));
		} else if (action.equals("getImage")) {
			OutputStream os = response.getOutputStream();
			int id = jsonObject.get("id").getAsInt();
			int imageSize = jsonObject.get("imageSize").getAsInt();
			byte[] image = newsDao.getImageById(id);
			if (image != null) {
				image = ImageUtil.shrink(image, imageSize);
				response.setContentType("image/jpeg");
				response.setContentLength(image.length);
				os.write(image);
			}
		} else if (action.equals("newsInsert") || action.equals("newsUpdate")) {
			String newsJson = jsonObject.get("news").getAsString();
			System.out.println("newsJson = " + newsJson);
			News news = gson.fromJson(newsJson, News.class);
			byte[] image = null;
			if (jsonObject.get("imageBase64") != null) {
				String imageBase64 = jsonObject.get("imageBase64").getAsString();
				if (imageBase64 != null && !imageBase64.isEmpty()) {
					image = Base64.getMimeDecoder().decode(imageBase64);

				}
			}
			int count = 0;
			if (action.equals("newsInsert")) {
				count = newsDao.insert(news, image);
			} else if (action.equals("newsUpdate")) {
				count = newsDao.update(news, image);
			}
			writeText(response, String.valueOf(count));
		} else if (action.equals("newsDelete")) {
			int newsId = jsonObject.get("newsId").getAsInt();
			int count = newsDao.deleteById(newsId);
			writeText(response, String.valueOf(count));
		} else {

			writeText(response, "");
		}
	}

	private void writeText(HttpServletResponse response, String outText) throws IOException {
		response.setContentType(CONTENT_TYPE);
		PrintWriter out = response.getWriter();
		out.print(outText);
		System.out.println("output: " + outText);

	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		if (newsDao == null) {
			newsDao = new NewsDaoMySql();
		}
		List<News> news = newsDao.getAll();
		writeText(response, new Gson().toJson(news));
	}

}
