package tw.dp103g4.user;

import static tw.dp103g4.main.Common.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.print.attribute.standard.MediaSize.ISO;

public class UserDaoImpl implements UserDao {

	public UserDaoImpl() {

		super();
		try {
			Class.forName(CLASS_NAME);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}

	@Override
	public int insert(User user, byte[] userImg) {
		// 判斷帳號是否重複註冊
		User checkAccount = findById(user.getId());
		if (checkAccount == null) {
			return -1;
		}

		int count = 0;
		String sql = "INSERT INTO User" + "(user_account, user_password, user_email, user_name, user_img, user_over) "
				+ "VALUES(?, ?, ?, ?, ?, ?);";
		Connection connection = null;
		PreparedStatement ps = null;
		try {
			connection = DriverManager.getConnection(URL, USER, PASSWORD);
			ps = connection.prepareStatement(sql);
			ps.setString(1, user.getAccount());
			ps.setString(2, user.getPassword());
			ps.setString(3, user.getEmail());
			ps.setString(4, user.getName());
			ps.setBytes(5, userImg);
			ps.setBoolean(6, user.isOver());
			count = ps.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if (ps != null) {
					// When a Statement object is closed,
					// its current ResultSet object is also closed
					ps.close();
				}
				if (connection != null) {
					connection.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return count;

	}

	@Override
	public int update(User user, byte[] userImg) {
		int count = 0;
		String sql = "";
		// image為null就不更新image欄位內容
		if (userImg != null) {
			sql = "UPDATE User SET user_password = ?, user_email = ?, user_name = ?, user_img = ? WHERE user_id = ?;";
		} else {
			sql = "UPDATE User SET user_password = ?, user_email = ?, user_name = ? WHERE user_id = ?;";
		}
		Connection connection = null;
		PreparedStatement ps = null;
		try {
			connection = DriverManager.getConnection(URL, USER, PASSWORD);
			ps = connection.prepareStatement(sql);
			ps.setString(1, user.getPassword());
			ps.setString(2, user.getEmail());
			ps.setString(3, user.getName());
			if (userImg != null) {
				ps.setBytes(4, userImg); // 第一行的userImg在第4位
				ps.setInt(5, user.getId());// 第一行的userId第5位
			} else {
				ps.setInt(4, user.getId());// 第二行的userId在第4位
			}
			// 以上是 完整MySQL的語法，再一起丟給MySQL執行
			count = ps.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if (ps != null) {
					ps.close();
				}
				if (connection != null) {
					connection.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return count;
	}

	// 登入後回傳相符的資料在detailFragment
	@Override
	public User findById(int id) {
		// get id 帳號密碼信箱暱稱 （可以不顯示但要抓到資料）
		String sql = "SELECT user_email, user_name , user_password FROM User WHERE user_id = ?;";
		Connection conn = null;
		PreparedStatement ps = null;
		User user = null;
		try {
			conn = DriverManager.getConnection(URL, USER, PASSWORD);
			ps = conn.prepareStatement(sql);
			ps.setInt(1, id);
			ResultSet rs = ps.executeQuery();
			if (rs.next()) {
				String email = rs.getString(1);
				String name = rs.getString(2);
				String password = rs.getString(3);
				user = new User(id, null, password, email, name);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if (ps != null) {
					ps.close();
				}
				if (conn != null) {
					conn.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return user;
	}

	@Override
	public List<User> getAll() {
		String sql = "SELECT user_id, user_account, user_password, user_email, user_name, user_over FROM User where user_over = 0 ORDER BY user_id ;";
		Connection connection = null;
		PreparedStatement ps = null;
		List<User> userList = new ArrayList<User>();
		boolean isOver = false;
		try {
			connection = DriverManager.getConnection(URL, USER, PASSWORD);
			ps = connection.prepareStatement(sql);
			ResultSet rs = ps.executeQuery();
			while (rs.next()) { // 取得該筆資料（第一筆）
				int id = rs.getInt(1);
				String account = rs.getString(2);
				String password = rs.getString(3);
				String email = rs.getString(4);
				String name = rs.getString(5);
				isOver = rs.getBoolean(6);
//				Date time = rs.getDate(6);
				User user = new User(id, account, password, email, name, isOver, null);
				userList.add(user);
			}
			return userList;

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if (ps != null) {
					ps.close();
				}
				if (connection != null) {
					connection.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return userList;
	}

	@Override
	public List<User> getUserOver() {
		String sql = "SELECT user_id, user_account, user_password, user_email, user_name, user_over FROM User WHERE user_over = 1 ORDER BY user_id ;";
		Connection connection = null;
		PreparedStatement ps = null;
		List<User> userList = new ArrayList<User>();
		boolean isOver = false;
		try {
			connection = DriverManager.getConnection(URL, USER, PASSWORD);
			ps = connection.prepareStatement(sql);
			ResultSet rs = ps.executeQuery();
			while (rs.next()) { // 取得該筆資料（第一筆）
				int id = rs.getInt(1);
				String account = rs.getString(2);
				String password = rs.getString(3);
				String email = rs.getString(4);
				String name = rs.getString(5);
				isOver = rs.getBoolean(6);
//				Date time = rs.getDate(6);
				User user = new User(id, account, password, email, name, isOver, null);
				userList.add(user);
			}
			return userList;

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if (ps != null) {
					ps.close();
				}
				if (connection != null) {
					connection.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return userList;
	}

	@Override
	public byte[] getUserImg(int id) {
		String sql = "SELECT user_img FROM User WHERE user_id = ?;";
		Connection connection = null;
		PreparedStatement ps = null;
		byte[] image = null;
		try {
			connection = DriverManager.getConnection(URL, USER, PASSWORD);
			ps = connection.prepareStatement(sql);
			ps.setInt(1, id);
			ResultSet rs = ps.executeQuery();
			if (rs.next()) {
				// image = []
				image = rs.getBytes(1);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if (ps != null) {
					ps.close();
				}
				if (connection != null) {
					connection.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return image;
	}

//判斷登入
	@Override
	public boolean isLogin(String account, String password) {
		boolean isValid = false;
		boolean isOver = false;
		String sql = "SELECT user_password, user_over FROM `user` WHERE user_account = ?;";
		Connection connection = null;
		PreparedStatement ps = null;
		try {
			connection = DriverManager.getConnection(URL, USER, PASSWORD);
			ps = connection.prepareStatement(sql);
			ps.setString(1, account);
			ResultSet rs = ps.executeQuery();
			if (rs.next()) {
				isValid = rs.getString(1).equals(password);
				isOver = rs.getBoolean(2);
			}
			rs.close();
		} catch (Exception e) {
			e.printStackTrace();

		} finally {
			try {
				if (ps != null) {
					ps.close();
				}
				if (connection != null) {
					connection.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return isValid && !isOver;
	}

	@Override
	public User searchUser(String account) {
		String sql = "SELECT user_id, user_account FROM User WHERE user_account = ?;";
		Connection connection = null;
		PreparedStatement ps = null;
		User user = null;
		try {
			connection = DriverManager.getConnection(URL, USER, PASSWORD);
			ps = connection.prepareStatement(sql);
			ps.setString(1, account);
			ResultSet rs = ps.executeQuery();
			if (rs.next()) {
				int userId = rs.getInt(1);
				String reAccount = rs.getString(2);
				user = new User(userId, reAccount);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if (ps != null) {
					ps.close();
				}
				if (connection != null) {
					connection.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return user;
	}

	// 用Account取Id
	@Override
	public User getUserByAccount(String account) {
		User user = null;
		String sql = "SELECT user_id, user_name FROM User WHERE user_account = ?;";
		try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
				PreparedStatement ps = conn.prepareStatement(sql);) {
			ps.setString(1, account);
			ResultSet rs = ps.executeQuery();
			if (rs.next()) { // 如果next有資料就取得id
				int id = rs.getInt(1);
				String name = rs.getString(2);
				user = new User(id, null, null, null, name);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return user;
	}

	// 選取停權後，會員進到停權名單
	@Override
	public int userOver(int id) {
		int count = 0;
		String sql = "";
		sql = "UPDATE User SET user_over = 1  WHERE user_id = ?;";
		Connection connection = null;
		PreparedStatement ps = null;
		try {
			connection = DriverManager.getConnection(URL, USER, PASSWORD);
			ps = connection.prepareStatement(sql);
			ps.setInt(1, id);
			count = ps.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if (ps != null) {
					ps.close();
				}
				if (connection != null) {
					connection.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return count;
	}

	// 選取復權後，會員進到一般名單
	@Override
	public int userBack(int id) {
		int count = 0;
		String sql = "";
		sql = "UPDATE User SET user_over = 0 WHERE user_id = ?;";
		Connection connection = null;
		PreparedStatement ps = null;
		try {
			connection = DriverManager.getConnection(URL, USER, PASSWORD);
			ps = connection.prepareStatement(sql);
			ps.setInt(1, id);
			count = ps.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if (ps != null) {
					ps.close();
				}
				if (connection != null) {
					connection.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return count;
	}

}
