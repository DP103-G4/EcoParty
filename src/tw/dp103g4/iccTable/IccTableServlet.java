package tw.dp103g4.iccTable;

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

import tw.dp103g4.party.Party;


/**
 * Servlet implementation class IccTableServlet
 */
@WebServlet("/IccTableServlet")
public class IccTableServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private final static String CONTENT_TYPE = "text/html; charset=utf-8";
	IccTableDao iccTableDao = null;
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		int partyId, userId;
		IccTable iccTable;
		IccTableInfo iccTableInfo;
		String iccTableJson;
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
		if (iccTableDao == null) {
			iccTableDao = new IccTableDaoImpl();
		}

		String action = jsonObject.get("action").getAsString();

		if (action.equals("getIccTableInfos")) {
			partyId = jsonObject.get("partyId").getAsInt();
			List<IccTableInfo> iccTableInfos = iccTableDao.getAllByParty(partyId);
			writeText(response, gson.toJson(iccTableInfos));
		} else if (action.equals("getIccTableInfo")) {
			userId = jsonObject.get("userId").getAsInt();
			partyId = jsonObject.get("partyId").getAsInt();
			iccTableInfo = iccTableDao.findById(userId, partyId);
			writeText(response, gson.toJson(iccTableInfo));
		} else if (action.equals("iccUpdate")) {
			iccTableJson = jsonObject.get("iccTable").getAsString();
//			System.out.println("iccTableJson = " + iccTableJson);
			iccTable = gson.fromJson(iccTableJson, IccTable.class);
			
			int count = 0;
			count = iccTableDao.update(iccTable);
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
//		System.out.println("output: " + outText);
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
	}

}
