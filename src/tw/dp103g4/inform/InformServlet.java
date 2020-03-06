package tw.dp103g4.inform;

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
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;

@SuppressWarnings("serial")
@WebServlet("/InformServlet")
public class InformServlet extends HttpServlet {
	private final static String CONTENT_TYPE = "text/html; charset=utf-8";       
	private InformDao informDao = null;
	int receiverId;
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
		Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").create();
		StringBuilder jsonIn = new StringBuilder();
		BufferedReader br = request.getReader();
		String line = "";
		while ((line = br.readLine()) != null) {
			jsonIn.append(line);
		}
		System.out.println("input: " + jsonIn);

		JsonObject jsonObject = gson.fromJson(jsonIn.toString(), JsonObject.class);
		if (informDao == null) {
			informDao = new InformDaoImpl();
		}
		String action = jsonObject.get("action").getAsString();
		if (action.equals("getAllInform")) {
			receiverId = jsonObject.get("receiverId").getAsInt();
			List<Inform> informs = informDao.getAllbyReceiver(receiverId);
			writeText(response, gson.toJson(informs));
		}  else if (action.equals("informInsert")) {
			String informJson = jsonObject.get("news").getAsString();
			System.out.println("newsJson = " + informJson);
			Inform inform = gson.fromJson(informJson, Inform.class);
			int count = 0;
			count = informDao.insert(inform);
			writeText(response, String.valueOf(count));
		} else if (action.equals("informDelete")) {
			int informId = jsonObject.get("informId").getAsInt();
			int count = informDao.delete(informId);
			writeText(response, String.valueOf(count));
		} else if (action.equals("setAllRead")) {
			int receiverId = jsonObject.get("receiverId").getAsInt();
			int count = informDao.setAllRead(receiverId);
			writeText(response, String.valueOf(count));
		} else if (action.equals("setRead")) {
			int id = jsonObject.get("id").getAsInt();
			int count = informDao.setRead(id);
			writeText(response, String.valueOf(count));
		}else {
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
		if (informDao == null) {
			informDao = new InformDaoImpl();
			List<Inform> informs = new ArrayList<Inform>();
			informs = informDao.getAllbyReceiver(2);
			Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").create();
			writeText(response, gson.toJson(informs));
		}
	}

}
