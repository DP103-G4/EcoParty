package tw.dp103g4.user;

import java.util.List;

public interface UserDao {
	// 增刪改查
	int insert(User user, byte[] userImg);

	int update(User user, byte[] userImg);

	// 登入後，用帳號取Id
	int getUserIdByAccount(String account);
	
	//判斷登入：布林
	boolean isLogin(String account, String password);
	
	//登入：有相符就回傳
	User findById(int id);

	// 呈現資料列表
	List<User> getAll();

	// 用String account 去找圖片id(以byte[]回傳)
	byte[] getUserImg(int id);
	
}
