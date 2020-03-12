package tw.dp103g4.location;

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

@SuppressWarnings("serial")
@WebServlet("/InfoLocationServlet")
public class InfoLocationServlet extends HttpServlet {
	private static final String CONTENT_TYPE = "text/html; charset=utf-8";
	private InfoLocationDao infoLocationDao = null;
	private int partyId;

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
		Gson gson = new Gson();
		StringBuilder jsonIn = new StringBuilder();
		BufferedReader br = request.getReader();
		String line = "";
		while ((line = br.readLine()) != null) {
			jsonIn.append(line);
		}
//		System.out.println("input: " + jsonIn);

		JsonObject jsonObject = gson.fromJson(jsonIn.toString(), JsonObject.class);
		if (infoLocationDao == null) {
			infoLocationDao = new InfoLocationDaoMySql();
		}
		String action = jsonObject.get("action").getAsString();
		if (action.equals("getAll")) {
			int partyId = jsonObject.get("partyId").getAsInt();
			List<InfoLocation> infoLocations = infoLocationDao.getAll(partyId);
			writeText(response, gson.toJson(infoLocations));
		} else if (action.equals("locationInsert") || action.equals("locationUpdate")) {
			String locationJson = jsonObject.get("location").getAsString();
//			System.out.println("locationJson" + locationJson);
			
			InfoLocation infoLoaction = gson.fromJson(locationJson, InfoLocation.class);
			int count = 0;
			if (action.equals("locationInsert")) {
				count = infoLocationDao.insert(infoLoaction);
			} else if (action.equals("locationUpdate")) {
				count = infoLocationDao.update(infoLoaction);
			}
			writeText(response, String.valueOf(count));
		} else if (action.equals("locationDelete")) {
			int id = jsonObject.get("id").getAsInt();
			int count = infoLocationDao.deleteById(id);
			writeText(response, String.valueOf(count));
		} else {
			writeText(response, "");
		}
	}

	private void writeText(HttpServletResponse response, String outText) throws IOException {
		response.setContentType(CONTENT_TYPE);
		PrintWriter out = response.getWriter();
		out.print(outText);
//		System.out.println("output: " + outText);
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		if (infoLocationDao == null) {
			infoLocationDao = new InfoLocationDaoMySql();
		}
		List<InfoLocation> infoLocationList = infoLocationDao.getAll(4);
		writeText(response, new Gson().toJson(infoLocationList));
	}

}
