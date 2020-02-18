package tw.dp103g4.partyImg;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;

import tw.dp103g4.main.ImageUtil;
import tw.dp103g4.news.News;

/**
 * Servlet implementation class PartyImgServlet
 */
@WebServlet("/PartyImgServlet")
public class PartyImgServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private final static String CONTENT_TYPE = "text/html; charset=utf-8";
	PartyImgDao partyImgDao = null;
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		int id, imageSize;
		byte[] image = null;
		
		request.setCharacterEncoding("utf-8");
		Gson gson = new GsonBuilder()  
				  .setDateFormat("yyyy-MM-dd HH:mm:ss")  
				  .create(); 
		BufferedReader br = request.getReader();
		StringBuilder jsonIn = new StringBuilder();
		String line = null;
		while ((line = br.readLine()) != null) {
			jsonIn.append(line);
		}
		JsonObject jsonObject = gson.fromJson(jsonIn.toString(), JsonObject.class);
		if (partyImgDao == null) {
			partyImgDao = new PartyImgDaoImpl();
		}
		
		String action = jsonObject.get("action").getAsString();
		if (action.equals("getPartyImgs")) {
			id = jsonObject.get("partyId").getAsInt();
			List<PartyImg> PartyImgs = partyImgDao.getAllByParty(id);
			writeText(response, gson.toJson(PartyImgs));
		} else if (action.equals("getImage")) {
			OutputStream os = response.getOutputStream();
			id = jsonObject.get("id").getAsInt();
			imageSize = jsonObject.get("imageSize").getAsInt();
			image = partyImgDao.getImage(id);
			if (image != null) {
				// 圖不為空
				// 直接輸出＆告知圖檔型態
				image = ImageUtil.shrink(image, imageSize);
				response.setContentType("image/jpeg");
				// 輸出圖檔的大小
				response.setContentLength(image.length);
				os.write(image);
			}
		} else {
			writeText(response, "");
		}
	}

	private void writeText(HttpServletResponse response, String outText) throws IOException {
		response.setContentType(CONTENT_TYPE);
		PrintWriter out = response.getWriter();
		out.print(outText);
		// 將輸出資料列印出來除錯用
		System.out.println("output: " + outText);
	}
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	
	}
}
