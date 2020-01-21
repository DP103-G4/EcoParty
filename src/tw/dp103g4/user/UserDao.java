package tw.dp103g4.user;

import java.util.List;

public interface UserDao {
	// 增刪改查
	public int insert(User user, byte[] userImg);

	public int update(User user, byte[] userImg);

//	public int delete(String account);

	
	//判斷登入：布林
	public boolean isLogin(String account, String password);
	
	//判斷登入：有相符就回傳
	public User findById(String account);

	// 呈現資料列表
	public List<User> getAll();

	// 用String account 去找圖片id(以byte[]回傳)
	public byte[] getUserImg(String account);
	
	public User searchUser(String account);
	
}
