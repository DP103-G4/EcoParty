package tw.dp103g4.friendship;

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

@WebServlet("/FriendShipServlet")
public class FriendShipServlet extends HttpServlet {
	private static final String CONTENT_TYPE = "text/html; charset=utf-8";
	FriendShipDao friendShipDao = null;

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		if (friendShipDao == null) {
			friendShipDao = new FriendShipDaoMySql();
		}
//		getAllInvite, getAllFriend, isInviteById
//		List<FriendShip> friendShips = friendShipDao.getAllInvite(2);
		FriendShip friendShips = friendShipDao.isInviteById(2, 4);
		writeText(response, new Gson().toJson(friendShips));
	}

	private void writeText(HttpServletResponse response, String outText) throws IOException {
		response.setContentType(CONTENT_TYPE);
		PrintWriter out = response.getWriter();
		out.print(outText);
		// 將輸出資料列印出來除錯用
//		System.out.println("output: " + outText);

	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
		Gson gson = new Gson();
		BufferedReader br = request.getReader();
		StringBuilder jsonIn = new StringBuilder();
		String line = null;
		while ((line = br.readLine()) != null) {
			jsonIn.append(line);
		}
//		System.out.println(jsonIn.toString());
		JsonObject jsonObject = gson.fromJson(jsonIn.toString(), JsonObject.class);
		if (friendShipDao == null) {
			friendShipDao = new FriendShipDaoMySql();
		}
		String action = jsonObject.get("action").getAsString();

		if (action.equals("getAllFriend")) {
			int userId = jsonObject.get("userId").getAsInt();
			List<FriendShip> friendShips = friendShipDao.getAllFriend(userId);
			writeText(response, gson.toJson(friendShips));

		} else if (action.equals("friendShipInsert")) {
			int count = 0;
			int idOne = jsonObject.get("idOne").getAsInt();
			int idTwo = jsonObject.get("idTwo").getAsInt();
			if (friendShipDao.searchFriendShipAdd(idTwo, idOne)) {
//				System.out.println("isInviteById");
				count = friendShipDao.updateIsInvite(idTwo, idOne);
			}else {
//				System.out.println("insert");
				count = friendShipDao.insert(idOne, idTwo);}
			writeText(response, gson.toJson(count));

		} else if (action.equals("friendShipDelete")) {
			int idOne = jsonObject.get("idOne").getAsInt();
			int idTwo = jsonObject.get("idTwo").getAsInt();
			int count = friendShipDao.delete(idOne, idTwo);
			writeText(response, String.valueOf(count));

		} else if (action.equals("updateIsInvite")) {
			int idOne = jsonObject.get("idOne").getAsInt();
			int idTwo = jsonObject.get("idTwo").getAsInt();
			int count = friendShipDao.updateIsInvite(idOne, idTwo);
			writeText(response, String.valueOf(count));

		}else if (action.equals("getAllInvite")) {
			int userId = jsonObject.get("userId").getAsInt();
			List<FriendShip> friendShips = friendShipDao.getAllInvite(userId);
			writeText(response, gson.toJson(friendShips));
			
		}else if(action.equals("isInviteById")){
			int idOne = jsonObject.get("idOne").getAsInt();
			int idTwo = jsonObject.get("idTwo").getAsInt();
			FriendShip isInvite = friendShipDao.isInviteById(idOne, idTwo);
			writeText(response, gson.toJson(isInvite));
		}
	}

}
