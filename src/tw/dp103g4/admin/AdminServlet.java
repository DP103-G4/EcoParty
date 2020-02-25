package tw.dp103g4.admin;

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

@WebServlet("/AdminServlet")
public class AdminServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private final static String CONTENT_TYPE = "text/html; charset=utf-8";

	AdminDao adminDao = null;

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.getWriter().append("Served at: ").append(request.getContextPath());

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
		Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd").create();
		BufferedReader br = request.getReader();
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

		if (action.equals("insert") || action.equals("update")) {
			String adminJson = jsonObject.get("admin").getAsString();
			System.out.println("adminJson = " + adminJson);
			Admin admin = gson.fromJson(adminJson, Admin.class);
			int count = 0;
			//新增帳號
			if (action.equals("insert")) {
				count = adminDao.insert(admin);
			} else if (action.equals("update")) {
				count = adminDao.update(admin);
			}
			writeText(response, String.valueOf(count));
		}
		//判斷登入
		else if(action.equals("isAdminLogin")) {
			boolean isAdminValid = false;
			String admin_account = jsonObject.get("admin_account").getAsString();
			String admin_password = jsonObject.get("admin_password").getAsString();
			isAdminValid = adminDao.isAdminLogin(admin_account, admin_password);
			writeText(response, String.valueOf(isAdminValid));
			
		}else if(action.equals("getAdminByAccount")) {
			Admin admin = null;
			String admin_account = jsonObject.get("admin_account").getAsString();
			admin = adminDao.getAdminByAccount(admin_account);
			writeText(response, gson.toJson(admin));
		}else if (action.equals("findByAdminID")) {
			int admin_id = jsonObject.get("admin_id").getAsInt();
			Admin admin = adminDao.findByAdminID(admin_id);
			writeText(response, gson.toJson(admin));
			
			//更改密碼
		}else if (action.equals("changeAdminPassword")) {
			int countPassword = 0 ;
			int admin_id = jsonObject.get("admin_id").getAsInt();
			String oldPass = jsonObject.get("oldPass").getAsString();
			String newPass = jsonObject.get("newPass").getAsString();
			Admin admin = adminDao.findByAdminID(admin_id);
			admin.setAdmin_id(admin_id);
			if(admin.getAdmin_password().equals(oldPass)) {
				admin.setAdmin_password(newPass);
				countPassword = adminDao.update(admin);
			}
			writeText(response, String.valueOf(countPassword));
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
}
