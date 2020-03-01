package tw.dp103g4.reviewImg;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Base64;
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
import tw.dp103g4.party.PartyDaoImpl;




@SuppressWarnings("serial")
@WebServlet("/ReviewImgServlet")
public class ReviewImgServlet extends HttpServlet {
	private final static String CONTENT_TYPE = "text/html; charset=utf-8";
	ReviewImgDao reviewImgDao = null;
      	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		if (reviewImgDao == null) {
			reviewImgDao = new ReviewImgDaoImpl();
		}
		List<ReviewImg> reviewImgs = new ArrayList<ReviewImg>();
		reviewImgs = reviewImgDao.getAllByParty(2);
		Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").create();
		writeText(response, gson.toJson(reviewImgs));
		}
	

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
		OutputStream os;
		int id, partyId, imageSize;
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
		if (reviewImgDao == null) {
			reviewImgDao = new ReviewImgDaoImpl();
		}
		
		String action = jsonObject.get("action").getAsString();
		if(action.equals("getAllByParty")) {
			partyId = jsonObject.get("partyId").getAsInt();
			List<ReviewImg> reviewImgsId = reviewImgDao.getAllByParty(partyId);
			for(int i = 0; i <= reviewImgsId.size(); i++) {
				os = response.getOutputStream();
				id = reviewImgsId.get(i).getId();
				imageSize = jsonObject.get("imageSize").getAsInt();
				byte[] reviewImg = reviewImgDao.getImage(id);
				if (reviewImg != null) {
					reviewImg = ImageUtil.shrink(reviewImg, imageSize);
					response.setContentType("image/jpeg");
					response.setContentLength(reviewImg.length);
					os.write(reviewImg);
				}
			}
		}else if (action.equals("insertReviewImg")) {
			partyId = jsonObject.get("partyId").getAsInt();
			byte[] image = null;
			int count = 0;
			if (jsonObject.get("imageBase64") != null) {
				String imageBase64 = jsonObject.get("imageBase64").getAsString();
				if (imageBase64 != null && !imageBase64.isEmpty()) {
					image = Base64.getMimeDecoder().decode(imageBase64);
					count = reviewImgDao.insert(partyId, image);
				}
			}
			writeText(response, String.valueOf(count));
			
		}else if (action.equals("deleteReviewImg")) {
			id = jsonObject.get("id").getAsInt();
			int count = reviewImgDao.delete(id);
			writeText(response, String.valueOf(count));
		}else{
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

}
