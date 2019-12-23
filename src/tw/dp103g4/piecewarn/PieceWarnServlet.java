package tw.dp103g4.piecewarn;

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

@WebServlet("/PieceWarnServlet")
public class PieceWarnServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final String CONTENT_TYPE = "text/html; charset=utf-8";
	PieceWarnDao pieceWarnDao = null;

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		if (pieceWarnDao == null) {
			pieceWarnDao = new PieceWarnDaoMySql();
		}
		List<PieceWarn> pieceWarns = pieceWarnDao.getAll();
		writeText(response, new Gson().toJson(pieceWarns));
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
		if (pieceWarnDao == null) {
			pieceWarnDao = new PieceWarnDaoMySql();
		}
		String action = jsonObject.get("action").getAsString();
		
		if (action.equals("getAll")) {
			List<PieceWarn> pieceWarns = pieceWarnDao.getAll();
			writeText(response, gson.toJson(pieceWarns));			
		} else if (action.equals("inster")) {
			String pieceWarnJson = jsonObject.get("pieceWarn").getAsString();
			System.out.println("pieceWarnJson = " + pieceWarnJson);
			PieceWarn pieceWarn = gson.fromJson(pieceWarnJson, PieceWarn.class);
			int count=0;
			count = pieceWarnDao.inster(pieceWarn);
			
		}else if (action.equals("delete")) {
			Integer id = jsonObject.get("id").getAsInt();
			int count = pieceWarnDao.delete(id);
			writeText(response, String.valueOf(count));
		}
	}
}
