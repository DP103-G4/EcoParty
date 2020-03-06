package tw.dp103g4.party;

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
import com.mysql.cj.x.protobuf.MysqlxCrud.Insert;

import tw.dp103g4.main.ImageUtil;

@SuppressWarnings("serial")
@WebServlet("/PartyServlet")
public class PartyServlet extends HttpServlet {
	private final static String CONTENT_TYPE = "text/html; charset=utf-8";
	PartyDao partyDao = null;

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		int id, imageSize, state, participantId, userId;
		byte[] coverImg, beforeImg, afterImg;
		OutputStream os;
		String partyJson;
		Party party;
		PartyInfo PartyInfo;
		
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
//		將輸入資料列印出來除錯用
//		System.out.println("input: " + jsonIn);

		JsonObject jsonObject = gson.fromJson(jsonIn.toString(), JsonObject.class);
		if (partyDao == null) {
			partyDao = new PartyDaoImpl();
		}

		String action = jsonObject.get("action").getAsString();
		if (action.equals("getPartyList")) {
			state = jsonObject.get("state").getAsInt();
			List<Party> parties = partyDao.getPartyList(state);
			writeText(response, gson.toJson(parties));
			
		} else if (action.equals("getPieceList")) {
			state = jsonObject.get("state").getAsInt();
			List<Party> parties = partyDao.getPieceList(state);
			writeText(response, gson.toJson(parties));
			
		} else if (action.equals("getParty")) {
			id = jsonObject.get("id").getAsInt();
			userId = jsonObject.get("userId").getAsInt();
			PartyInfo = partyDao.findById(id, userId);
			writeText(response, gson.toJson(PartyInfo));
			
		} else if (action.equals("getMyParty")) {
			userId = jsonObject.get("userId").getAsInt();
			List<Party> parties = partyDao.getMyParty(userId);
			writeText(response, gson.toJson(parties));
			
		} else if (action.equals("getCurrentParty")) {
			state = jsonObject.get("state").getAsInt();
			participantId = jsonObject.get("participantId").getAsInt();
			List<Party> parties = partyDao.getCurrentParty(participantId, state);
			writeText(response, gson.toJson(parties));
			
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
			
		} else if (action.equals("getAfterImg")) {
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
			// 檢查是否有上傳圖片
			if (jsonObject.get("imageBase64") != null) {
				String coverImgBase64 = jsonObject.get("imageBase64").getAsString();
				if (coverImgBase64 != null && !coverImgBase64.isEmpty()) {
					coverImg = Base64.getMimeDecoder().decode(coverImgBase64);
				}
			} 
			int count = 0;
			if (action.equals("partyInsert"))
				count = partyDao.insert(party, coverImg);
			writeText(response, String.valueOf(count));
			
		} else if (action.equals("changePartyState")) {
			id = jsonObject.get("id").getAsInt();
			state = jsonObject.get("state").getAsInt();
			int count = 0;
			count = partyDao.setState(id, state);
			writeText(response, String.valueOf(count));
			
		}else if (action.equals("getPartyCheck")) {
			List<Party> partyCheck = partyDao.getPartyCheck();
			writeText(response, gson.toJson(partyCheck));
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
		if (partyDao == null) {
			partyDao = new PartyDaoImpl();
		}
		List<Party> parties = new ArrayList<Party>();
		parties = partyDao.getPartyCheck();
		Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").create();
		writeText(response, gson.toJson(parties));
		}
}

