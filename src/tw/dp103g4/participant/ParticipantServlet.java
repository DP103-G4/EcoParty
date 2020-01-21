package tw.dp103g4.participant;

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


@WebServlet("/ParticipantServlet")
public class ParticipantServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private final static String CONTENT_TYPE = "text/html; charset=utf-8";
	ParticipantDao participantDao = null;

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		int partyId;
		
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
		if (participantDao == null) {
			participantDao = new ParticipantDaoImpl();
		}

		String action = jsonObject.get("action").getAsString();

		if (action.equals("getParticipantList")) {
			partyId = jsonObject.get("partyId").getAsInt();
			List<Participant> participants = participantDao.getAllByParty(partyId);
			writeText(response, gson.toJson(participants));
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
