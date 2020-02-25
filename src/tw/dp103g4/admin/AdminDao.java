package tw.dp103g4.admin;

import java.util.List;

public interface AdminDao {
	
	//增刪改
	int insert(Admin admin);
	
	int update(Admin admin);
	
	int delete(int admin_id);
	
	//登入後，用帳號取ID
	Admin getAdminByAccount(String admin_account);
	
	//判斷登入
	boolean isAdminLogin(String admin_account, String admin_password);
	
	//登入：有相符就回傳
	Admin findByAdminID(int admin_id);
	
	List<Admin> getAllAdmins();

}
