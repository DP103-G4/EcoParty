package tw.dp103g4.admin;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;

@WebServlet("/AdminServlet")
public class AdminServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private final static String CONTENT_TYPE = "text/html; charset=UTF-8";
	AdminDao adminDao = null;

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
//		response.getWriter().append("Served at: ").append(request.getContextPath());

		if (adminDao == null) {
			adminDao = new AdminDaoImpl();
		}
		List<Admin> admins = adminDao.getAllAdmins();
		Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd").create();
		writeText(response, gson.toJson(admins));
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
		//給gson一個時間格式避免錯誤
		Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd").create();
		//讀取輸入
		BufferedReader br = request.getReader();
		//建立自動增加長度的String物件
		StringBuilder jsonIn = new StringBuilder();
		String line = null;
		while ((line = br.readLine()) != null) {
			jsonIn.append(line);
		}
		System.out.println("input: " + jsonIn);
		JsonObject jsonObject = gson.fromJson(jsonIn.toString(), JsonObject.class);
		if (adminDao == null) {
			adminDao = new AdminDaoImpl();
		}
		String action = jsonObject.get("action").getAsString();

		if (action.equals("getAllAdmins")) {
			List<Admin> admins = adminDao.getAllAdmins();
			writeText(response, gson.toJson(admins));
			
		} else if (action.equals("isAdminLogin")) { // 判斷登入
			boolean isAdminValid = false; 
			String admin_Account = jsonObject.get("admin_account").getAsString(); //get("client端的key") (key=要傳遞的資料的名字)
			String admin_Password = jsonObject.get("admin_password").getAsString();
			isAdminValid = adminDao.isAdminLogin(admin_Account, admin_Password);
			writeText(response, String.valueOf(isAdminValid));

		} else if (action.equals("getAdminByAccount")) {
			Admin admin = null;
			String adminAccount = jsonObject.get("admin_Account").getAsString();
			admin = adminDao.getAdminByAccount(adminAccount);
			writeText(response, gson.toJson(admin));
			
		} else if (action.equals("findByAdminID")) {
			int adminId = jsonObject.get("admin_id").getAsInt();
			Admin admin = adminDao.findByAdminID(adminId);
			writeText(response, gson.toJson(admin));

		}
	}

	private void writeText(HttpServletResponse response, String outText) throws IOException {
		response.setContentType(CONTENT_TYPE);

		PrintWriter out = response.getWriter();
		out.print(outText);
		System.out.println("output: " + outText);
	}
}
