package tw.dp103g4.user;

import java.util.List;

public interface UserDao {
	// 增刪改查
	int insert(User user, byte[] userImg);

	int update(User user, byte[] userImg);

	// 登入後，用帳號取Id
	User getUserByAccount(String account);
	
	//判斷登入：布林
	boolean isLogin(String account, String password);
	
	//登入：有相符就回傳
	User findById(int id);

	// 呈現資料列表
	List<User> getAll();

	//停權列表
	List<User> getUserOver();
	
	
	// 用String account 去找圖片id(以byte[]回傳)
	byte[] getUserImg(int id);
	
	
	
	//選取停權後，會員進到停權名單
	int userOver(int id);
	//選取復權後，會員進到一般名單
	int userBack(int id);
	
	
	
	
	
	public User searchUser(String account);
	
}
