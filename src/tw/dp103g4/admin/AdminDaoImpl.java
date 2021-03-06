package tw.dp103g4.admin;

import static tw.dp103g4.main.Common.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import tw.dp103g4.user.User;

public class AdminDaoImpl implements AdminDao {

	public AdminDaoImpl() {
		super();
		try {
			Class.forName(CLASS_NAME);
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

//	// 新增管理員帳號
//	@Override
//	public int insert(Admin admin) {
//		// 判斷帳號是否重複註冊
//		Admin checkAdminAccount = findByAdminID(admin.getAdmin_id());
//		if (checkAdminAccount == null) {
//			return -1;
//		}
//
//		int count = 0;
//		String sql = "INSERT INTO Admin" + " (admin_id, admin_account, admin_password) " + "VALUES(?, ?, ?);";
//		Connection connection = null;
//		PreparedStatement ps = null;
//
//		try {
//			connection = DriverManager.getConnection(URL, USER, PASSWORD);
//			ps = connection.prepareStatement(sql);
//			ps.setInt(1, admin.getAdmin_id());
//			ps.setString(2, admin.getAdmin_account());
//			ps.setString(3, admin.getAdmin_password());
//			count = ps.executeUpdate();
//		} catch (SQLException e) {
//			e.printStackTrace();
//		} finally {
//			try {
//				if (ps != null) {
//					ps.close();
//				}
//
//				if (connection != null) {
//					connection.close();
//				}
//			} catch (SQLException e) {
//				e.printStackTrace();
//			}
//		}
//		return count;
//	}
//
//	// 更改管理員資料
//	@Override
//	public int update(Admin admin) {
//		int count = 0;
//		String sql = "UPDATE Admin SET admin_password = ?  WHERE admin_id = ?;";
//		Connection connection = null;
//		PreparedStatement ps = null;
//
//		try {
//			connection = DriverManager.getConnection(URL, USER, PASSWORD);
//			ps = connection.prepareStatement(sql);
//			ps.setString(1, admin.getAdmin_password());
//			ps.setInt(2, admin.getAdmin_id());
//			count = ps.executeUpdate();
//
//		} catch (SQLException e) {
//			e.printStackTrace();
//		} finally {
//
//			try {
//
//				if (ps != null) {
//					ps.close();
//				}
//				if (connection != null) {
//					connection.close();
//				}
//
//			} catch (SQLException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
//		}
//		return count;
//	}
//
//	// 刪除管理員帳號
//	@Override
//	public int delete(int admin_id) {
//		int count = 0;
//		String sql = "DELETE FROM Admin WHERE admin_id = ? ;";
//		Connection connection = null;
//		PreparedStatement ps = null;
//
//		try {
//			connection = DriverManager.getConnection(URL, USER, PASSWORD);
//			ps = connection.prepareStatement(sql);
//			ps.setInt(1, admin_id);
//			count = ps.executeUpdate();
//
//		} catch (SQLException e) {
//			e.printStackTrace();
//		} finally {
//
//			try {
//				if (ps != null) {
//					ps.close();
//				}
//				if (connection != null) {
//					connection.close();
//				}
//			} catch (SQLException e) {
//				e.printStackTrace();
//			}
//		}
//		return count;
//	}
//
	// 登入時查DB帳號
	@Override
	public Admin getAdminByAccount(String adminAccount) {
		Admin admin = null;
		String sql = "SELECT admin_id, admin_account FROM Admin WHERE admin_account = ?;";
		Connection connection = null;
		PreparedStatement ps = null;
		try {
			connection = DriverManager.getConnection(URL, USER, PASSWORD);
			ps = connection.prepareStatement(sql);
			ps.setString(1, adminAccount);
			ResultSet rs = ps.executeQuery();

			if (rs.next()) {
				int admin_Id = rs.getInt(1);
				String admin_Account = rs.getString(2);
				admin = new Admin(admin_Id, admin_Account, null);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return admin;
	}

	// 判斷管理員登入否
	@Override
	public boolean isAdminLogin(String adminAccount, String adminPassword) {
		boolean isAdminValid = false;
		String sql = "SELECT admin_password FROM Admin WHERE admin_account = ?;";
		Connection connection = null;
		PreparedStatement ps = null;
		try {
			connection = DriverManager.getConnection(URL, USER, PASSWORD);
			ps = connection.prepareStatement(sql);
			ps.setString(1, adminAccount);
			ResultSet rs = ps.executeQuery();
			if (rs.next()) {
				isAdminValid = rs.getString(1).equals(adminPassword);
			}
			rs.close();

		} catch (Exception e) {
			e.printStackTrace();
		} finally {

			try {
				if (ps != null) {
					ps.close();
					if (connection != null) {
						connection.close();
					}
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return isAdminValid;
	}

	// 登入，有相符就回傳 & 取ID和Account顯示在AdminSelf(iOS)
	@Override
	public Admin findByAdminID(int adminId) {
		String sql = "SELECT admin_id, admin_account FROM Admin WHERE admin_id = ?;";
		Connection connection = null;
		PreparedStatement ps = null;
		Admin admin = null;

		try {
			connection = DriverManager.getConnection(URL, USER, PASSWORD);
			ps = connection.prepareStatement(sql);
			ps.setInt(1, adminId);
			ResultSet rs = ps.executeQuery();

			if (rs.next()) {
//				int admin_Id = rs.getInt(1);
//				String admin_Account = rs.getString(2);
				adminId = rs.getInt(1);
				String adminAccount = rs.getString(2);
				admin = new Admin(adminId, adminAccount, null);
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
		return admin;
	}

	@Override
	public List<Admin> getAllAdmins() {
		String sql = "SELECT admin_id, admin_account, admin_password " + " FROM Admin ORDER BY admin_id ;";
		Connection connection = null;
		PreparedStatement ps = null;
		List<Admin> adminList = new ArrayList<Admin>();
		try {
			connection = DriverManager.getConnection(URL, USER, PASSWORD);
			ps = connection.prepareStatement(sql);
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				int admin_Id = rs.getInt(1);
				String admin_Account = rs.getString(2);
				String admin_Password = rs.getString(3);
				Admin admin = new Admin(admin_Id, admin_Account, admin_Password);
				adminList.add(admin);
			}
			return adminList;
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
		return adminList;
	}

}
