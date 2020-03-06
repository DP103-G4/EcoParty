package tw.dp103g4.partyMessage;

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
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;

@WebServlet("/PartyMessageServlet")
public class PartyMessageServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private final static String CONTENT_TYPE = "text/html; charset=utf-8";
	PartyMessageDao partyMessageDao = null;

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		int id, userId, partyId;
		OutputStream os;
		String partyMessageJson;
		PartyMessage partyMessage;
		
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
		if (partyMessageDao == null) {
			partyMessageDao = new PartyMessageDaoImpl();
		}

		String action = jsonObject.get("action").getAsString();

		if (action.equals("getMsgList")) {
			partyId = jsonObject.get("partyId").getAsInt();
			List<PartyMsgInfo> msgList = partyMessageDao.getAllbyParty(partyId);
			writeText(response, gson.toJson(msgList)); 
			
		} else if (action.equals("msgInsert")) {
			String msgJson = jsonObject.get("message").getAsString();
			System.out.println("msgJson = " + msgJson);
			PartyMessage message = gson.fromJson(msgJson, PartyMessage.class);
			int count = 0;
			if (action.equals("msgInsert")) {
				count = partyMessageDao.insert(message);
				System.out.println(count);
			}
			
		} else if (action.equals("deleteById")) {
			int pieceId = jsonObject.get("id").getAsInt();
			int count = partyMessageDao.delete(pieceId);
			writeText(response, String.valueOf(count));
			
		} else if (action.equals("getOneById")) {
			id = jsonObject.get("id").getAsInt();
			partyMessage = partyMessageDao.getOneById(id);
			writeText(response, gson.toJson(partyMessage));
		}else {
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