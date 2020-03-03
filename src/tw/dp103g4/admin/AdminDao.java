package tw.dp103g4.admin;

import java.util.List;

import tw.dp103g4.user.User;

public interface AdminDao {
	
	//登入後，用帳號取ID
	Admin getAdminByAccount(String adminAccount);
	
	//判斷登入
	boolean isAdminLogin(String adminAccount, String adminPassword);
	
	//登入：有相符就回傳 & 取ID和Account顯示在AdminSelf(iOS)
	Admin findByAdminID(int adminId);

	
	List<Admin> getAllAdmins();
	
	

}
