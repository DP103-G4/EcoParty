package tw.dp103g4.party;

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
@WebServlet("/PartyServlet")
public class PartyServlet extends HttpServlet {
	private final static String CONTENT_TYPE = "text/html; charset=utf-8";
	PartyDaoImpl partyDao = null;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		int id, imageSize;
		byte[] coverImg, beforeImg, afterImg;
		OutputStream os;
		String partyJson;
		Party party;
		List<Party> partys;
		
		request.setCharacterEncoding("utf-8");
		Gson gson = new Gson();
		BufferedReader br = request.getReader();
		StringBuilder jsonIn = new StringBuilder();
		String line = null;
		while ((line = br.readLine()) != null) {
			jsonIn.append(line);
		}
		// 將輸入資料列印出來除錯用
		// System.out.println("input: " + jsonIn);

		JsonObject jsonObject = gson.fromJson(jsonIn.toString(), JsonObject.class);
		if (partyDao == null) {
			partyDao = new PartyDaoImpl();
		}

		String action = jsonObject.get("action").getAsString();

		if (action.equals("getAll")) {
			
			
		} else if (action.equals("getCoverImg")) {
			os = response.getOutputStream();
			id = jsonObject.get("id").getAsInt();
			imageSize = jsonObject.get("imageSize").getAsInt();
			coverImg = partyDao.getCoverImg(id);
			if (coverImg != null) {
				coverImg = ImageUtil.shrink(coverImg, imageSize);
				response.setContentType("image/jpeg");
				response.setContentLength(coverImg.length);
				os.write(coverImg);
			}
		} else if (action.equals("getBeforeImg")) {
			os = response.getOutputStream();
			id = jsonObject.get("id").getAsInt();
			imageSize = jsonObject.get("imageSize").getAsInt();
			beforeImg = partyDao.getBeforeImg(id);
			if (beforeImg != null) {
				beforeImg = ImageUtil.shrink(beforeImg, imageSize);
				response.setContentType("image/jpeg");
				response.setContentLength(beforeImg.length);
				os.write(beforeImg);
			}
		} else if (action.equals("gerAfterImg")) {
			os = response.getOutputStream();
			id = jsonObject.get("id").getAsInt();
			imageSize = jsonObject.get("imageSize").getAsInt();
			afterImg = partyDao.getAfterImg(id);
			if (afterImg != null) {
				afterImg = ImageUtil.shrink(afterImg, imageSize);
				response.setContentType("image/jpeg");
				response.setContentLength(afterImg.length);
				os.write(afterImg);
			}
		} else if (action.equals("partyInsert") || action.equals("partyUpdate")) {
			partyJson = jsonObject.get("party").getAsString();
			System.out.println("partyJson = " + partyJson);
			party = gson.fromJson(partyJson, Party.class);
			coverImg = null;
			beforeImg = null;
			afterImg = null;
			// 檢查是否有上傳圖片
			if (jsonObject.get("coverImgBase64") != null) {
				String coverImgBase64 = jsonObject.get("coverImgBase64").getAsString();
				if (coverImgBase64 != null && !coverImgBase64.isEmpty()) {
					coverImg = Base64.getMimeDecoder().decode(coverImgBase64);
				}
			}
			
			int count = 0;
			if (action.equals("partyInsert")) {
				count = partyDao.insert(party, coverImg);
			} else if (action.equals("partyUpdate")) {
				count = partyDao.update(party, coverImg);
			}
			writeText(response, String.valueOf(count));
		} else if (action.equals("partyDelete")) {
			id = jsonObject.get("id").getAsInt();
			int count = partyDao.delete(id);
			writeText(response, String.valueOf(count));
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

}
