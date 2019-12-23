package tw.dp103g4.msgwarn;

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
import com.google.gson.JsonObject;

@WebServlet("/MsgWarnServlet")
public class MsgWarnServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final String CONTENT_TYPE = "text/html; charset=utf-8";
	MsgWarnDao msgWarnDao = null;

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		if (msgWarnDao == null) {
			msgWarnDao = new MsgWarnDaoMySql();
		}
		List<MsgWarn> msgWarns = msgWarnDao.getAll();
		writeText(response, new Gson().toJson(msgWarns));
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
		Gson gson = new Gson();
		BufferedReader br = request.getReader();
		StringBuilder jsonIn = new StringBuilder();
		String line = null;
		while ((line = br.readLine()) != null) {
			jsonIn.append(line);
		}
		JsonObject jsonObject = gson.fromJson(jsonIn.toString(), JsonObject.class);
		if (msgWarnDao == null) {
			msgWarnDao = new MsgWarnDaoMySql();
		}
		String action = jsonObject.get("action").getAsString();
		
		if (action.equals("getAll")) {
			List<MsgWarn> msgWarns = msgWarnDao.getAll();
			writeText(response, gson.toJson(msgWarns));			
		} else if (action.equals("inster")) {
			String msgWarnJson = jsonObject.get("msgWarn").getAsString();
			System.out.println("msgWarnJson = " + msgWarnJson);
			MsgWarn msgWarn = gson.fromJson(msgWarnJson, MsgWarn.class);
			int count=0;
			count = msgWarnDao.inster(msgWarn);
			
		}else if (action.equals("delete")) {
			Integer id = jsonObject.get("id").getAsInt();
			int count = msgWarnDao.delete(id);
			writeText(response, String.valueOf(count));
		}
	}
}
