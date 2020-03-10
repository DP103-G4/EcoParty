package tw.dp103g4.talk;

import java.io.BufferedReader;
import java.io.IOException;
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
import com.google.gson.reflect.TypeToken;
import com.mysql.cj.conf.ConnectionUrl.Type;

@WebServlet("/TalkServlet")
public class TalkServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final String CONTENT_TYPE = "text/html; charset=utf-8";
	TalkDao talkDao = null;

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		if (talkDao == null) {
			talkDao = new TalkDaoMySql();
		}
		List<NewestTalk> newestTalks = talkDao.getNewestTalk(3);
		writeText(response, new Gson().toJson(newestTalks));
//		List<Talk> talks = talkDao.getAll(3, 4);
//		writeText(response, new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").create().toJson(talks));

	}

	private void writeText(HttpServletResponse response, String outText) throws IOException{
		response.setContentType(CONTENT_TYPE);
		PrintWriter out = response.getWriter();
		out.print(outText);
		// 將輸出資料列印出來除錯用
		System.out.println("output: " + outText);

	}
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
		Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").create();
		BufferedReader br = request.getReader();
		StringBuilder jsonIn = new StringBuilder();
		String line = null;
		while ((line = br.readLine()) != null) {
			jsonIn.append(line);
		}
		JsonObject jsonObject = gson.fromJson(jsonIn.toString(), JsonObject.class);
		if (talkDao == null) {
			talkDao = new TalkDaoMySql();
		}
		String action = jsonObject.get("action").getAsString();
		
		if (action.equals("getAll")) {
			int userId = jsonObject.get("userId").getAsInt();
			int friendId = jsonObject.get("friendId").getAsInt();
			List<Talk> talks = talkDao.getAll(userId,friendId);
			writeText(response, gson.toJson(talks));	
			
		} else if (action.equals("talkInsert")) {
			String talkJson = jsonObject.get("talk").getAsString();
			System.out.println("TalkJson = " + talkJson);
			Talk talk = gson.fromJson(talkJson, Talk.class);
			int count = talkDao.insert(talk);
			writeText(response, String.valueOf(count));
			
		} else if (action.equals("updateIsRead")) {
			int senderId = jsonObject.get("senderId").getAsInt();
			int receiverId = jsonObject.get("receiverId").getAsInt();
			int count = talkDao.updateIsRead(senderId,receiverId);
			writeText(response, String.valueOf(count));

		}else if(action.equals("getNewestTalk")) {
			int userId = jsonObject.get("userId").getAsInt();
			List<NewestTalk> newestTalks = talkDao.getNewestTalk(userId);
			writeText(response, gson.toJson(newestTalks));	
		} else if (action.equals("talksInsert")) {
			String talkJson = jsonObject.get("talks").getAsString();
			System.out.println("TalkJson = " + talkJson);
			List<Talk> talks = gson.fromJson(talkJson, new TypeToken<List<Talk>>() {}.getType());
			int count = 0;
			for (Talk talk:talks) {
				count = talkDao.insert(talk);
			}
			writeText(response, String.valueOf(count));
		}
	}
}
