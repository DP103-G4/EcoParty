package tw.dp103g4.user;

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

import tw.dp103g4.main.ImageUtil;

@WebServlet("/UserServlet")
public class UserServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private final static String CONTENT_TYPE = "text/html; charset=utf-8";
	UserDao userDao = null;

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
//		response.getWriter().append("Served at: ").append(request.getContextPath());
		if (userDao == null) {
			userDao = new UserDaoImpl();
		}
		List<User> users = userDao.getAll();

		Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd").create();
		writeText(response, gson.toJson(users));

	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
		Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd").create();
		// BufferedReader需求讀出
		BufferedReader br = request.getReader();
		// 寫入：會變動的字串，用Builder
		StringBuilder jsonIn = new StringBuilder();
		String line = null;
		// readLine:讀BufferedReader
		while ((line = br.readLine()) != null) {
			// 若(需求)line不為空，就寫入到StringBuilder
			jsonIn.append(line);
		}
		// 將輸入資料列印出來除錯用
		System.out.println("input: " + jsonIn);
		JsonObject jsonObject = gson.fromJson(jsonIn.toString(), JsonObject.class);
		if (userDao == null) {
			userDao = new UserDaoImpl();
		}
		// "action"= getImage 或其他實作項目
		String action = jsonObject.get("action").getAsString();

//		if (action.equals("getAll")) {
//			// 建立bookDao去取getAll
//			List<User> users = userDao.getAll();
//			writeText(response, gson.toJson(users));
//	}else
		if (action.equals("getUserImage")) {
			OutputStream os = response.getOutputStream();
			String account = jsonObject.get("account").getAsString();
			//
			int imageSize = jsonObject.get("imageSize").getAsInt();
			byte[] image = userDao.getUserImg(account);
			if (image != null) {
				// 圖不為空
				// 直接輸出＆告知圖檔型態
				image = ImageUtil.shrink(image, imageSize);
				response.setContentType("image/jpeg");
				// 輸出圖檔的大小
				response.setContentLength(image.length);
				os.write(image);
			}
		}
//		 else 
			if (action.equals("insert") || action.equals("update")) {
			String userJson = jsonObject.get("user").getAsString();
			System.out.println("userJson = " + userJson);
			// 字串解析成物件bookJson->Book.class
			User user = gson.fromJson(userJson, User.class);
			byte[] image = null;
			// 檢查是否有上傳圖片
			if (jsonObject.get("imageBase64") != null) {
				// 純文字改成byte
				String imageBase64 = jsonObject.get("imageBase64").getAsString();
				if (imageBase64 != null && !imageBase64.isEmpty()) {
					// 用編碼轉回來byte[]
					image = Base64.getMimeDecoder().decode(imageBase64);
				}
			}
			int count = 0; // 新增筆數
			// 兩種情況：新增或修改
			if (action.equals("insert")) {
				// 新增時不用管id(自動加入）
				// 得到異動成功的筆數 executeUpdate()
				count = userDao.insert(user, image);
			} else if (action.equals("update")) {
				count = userDao.update(user, image);
			}
			writeText(response, String.valueOf(count));
		}
		//判斷登入
		else if (action.equals("isLogin")) {
			boolean isValid = false;
			String account = jsonObject.get("account").getAsString();
			String password = jsonObject.get("password").getAsString();
			isValid = userDao.isLogin(account, password);
			writeText(response, String.valueOf(isValid));
		}
//		else if
//			(action.equals("delete")) {
//			String account = jsonObject.get("account").getAsString();
//			int count = userDao.delete(account);
//			writeText(response, String.valueOf(count));
//		} 
		//登入：回傳資料
		else if (action.equals("findById")) {
			String account = jsonObject.get("account").getAsString();
			User user = userDao.findById(account);
			writeText(response, gson.toJson(user));
			
		}else if (action.equals("searchUser")) {
			String account = jsonObject.get("account").getAsString();
			User user = userDao.searchUser(account);
			writeText(response, gson.toJson(user));
			
		}else {
		writeText(response, "");
		}

	}

	private void writeText(HttpServletResponse response, String outText) throws IOException {
		response.setContentType(CONTENT_TYPE);
		// 輸出
		PrintWriter out = response.getWriter();
		out.print(outText);
		// 將輸出資料列印出來除錯用
		System.out.println("output: " + outText);
	}

}
