package tw.dp103g4.partyLike;

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
import com.google.gson.JsonObject;

@SuppressWarnings("serial")
@WebServlet("/PartyLikeServlet")
public class PartyLikeServlet extends HttpServlet {
	private final static String CONTENT_TYPE = "text/html; charset=utf-8";
	PartyLikeDao partyLikeDao = null;

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
		System.out.println("input: " + jsonIn);

		JsonObject jsonObject = gson.fromJson(jsonIn.toString(), JsonObject.class);
		if (partyLikeDao == null) {
			partyLikeDao = new PartyLikeDaoImpl();
		}
		String action = jsonObject.get("action").getAsString();

		if (action.equals("getAll")) {
			int id = jsonObject.get("id").getAsInt();
			List<PartyLike> partyLikes = partyLikeDao.getAllByUser(id);
			writeText(response, gson.toJson(partyLikes));
		}  else if (action.equals("partyLikeInsert")) {
			String partyLikeJson = jsonObject.get("partyLike").getAsString();
			System.out.println("partyLikeJson = " + partyLikeJson);
			PartyLike partyLike = gson.fromJson(partyLikeJson, PartyLike.class);
			int count = 0;
			count = partyLikeDao.insert(partyLike);
			writeText(response, String.valueOf(count));
		} else if (action.equals("partyLikeDelete")) {
			int id = jsonObject.get("id").getAsInt();
			int partyId = jsonObject.get("partyId").getAsInt();
			int count = partyLikeDao.delete(id, partyId);
			writeText(response, String.valueOf(count));
		} else {
			writeText(response, "");
		}
	}

	private void writeText(HttpServletResponse response, String outText) throws IOException {
		response.setContentType(CONTENT_TYPE);
		PrintWriter out = response.getWriter();
		out.print(outText);
		System.out.println("output" + outText);
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		if (partyLikeDao == null) {
			partyLikeDao = new PartyLikeDaoImpl();
		}
		List<PartyLike> partyLikes = new ArrayList<PartyLike>();
		partyLikes = partyLikeDao.getAllByUser(4);
		writeText(response, new Gson().toJson(partyLikes));
	}

}
