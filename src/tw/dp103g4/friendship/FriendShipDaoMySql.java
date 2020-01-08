package tw.dp103g4.friendship;

import static tw.dp103g4.main.Common.*;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;



public class FriendShipDaoMySql implements FriendShipDao {
	public FriendShipDaoMySql() {
		super();
		try {
			Class.forName(CLASS_NAME);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}

	@Override
	public int insert(int idOne, int idTwo) {
		int count = 0;
		String sql = "INSERT INTO Friendship " + "(idone,idtwo)" + "VALUES (?,?);";
		Connection connection = null;
		PreparedStatement ps = null;
		try {
			connection = DriverManager.getConnection(URL, USER, PASSWORD);
			ps = connection.prepareStatement(sql);
			ps.setInt(1, idOne);
			ps.setInt(2, idTwo);

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

	@Override
	public int delete(int idOne, int idTwo) {
		int count = 0;
		String sql = "DELETE FROM Friendship WHERE (userone_id = ?) and (usertwo_id = ?);";
		Connection connection = null;
		PreparedStatement ps = null;
		try {
			connection = DriverManager.getConnection(URL, USER, PASSWORD);
			ps = connection.prepareStatement(sql);
			ps.setInt(1, idOne);
			ps.setInt(2, idTwo);
			count = ps.executeUpdate();
			if (count == 0) {
				ps.setInt(1, idTwo);
				ps.setInt(2, idOne);
				count = ps.executeUpdate();
			}
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
	public List<FriendShip> getAllFriend(int userId) {
		String sql = "SELECT userone_id, usertwo_id, isInvite FROM Friendship "
				+ "WHERE userone_id = ? or usertwo_id = ?;";
		Connection connection = null;
		PreparedStatement ps = null;
		List<FriendShip> friendShipList = new ArrayList<FriendShip>();
		try {
			connection = DriverManager.getConnection(URL, USER, PASSWORD);
			ps = connection.prepareStatement(sql);
			ps.setInt(1, userId);
			ps.setInt(2, userId);
			ResultSet resultSet = ps.executeQuery();
			int friendId;		
			
			while (resultSet.next()) {
				int userOne = resultSet.getInt(1);
				int userTwo = resultSet.getInt(2);
				Boolean isInvite = resultSet.getBoolean(3);
				if (isInvite) {
					if(userId == userOne) {
						friendId = userTwo;
						sql = "SELECT user_account FROM Friendship a LEFT join User b "
								+ "on a.usertwo_id = b.user_id WHERE userone_id = ? and usertwo_id = ? and isInvite = true;";
						ps = connection.prepareStatement(sql);
						ps.setInt(1, userOne);
						ps.setInt(2, friendId);
						
					}else {
						friendId = userOne;
						sql = "SELECT user_account FROM Friendship a "
								+ "LEFT join User b on a.userone_id = b.user_id "
								+ "WHERE usertwo_id = ? and userone_id = ? and isInvite = true;";
						ps = connection.prepareStatement(sql);
						ps.setInt(1, userTwo);	
						ps.setInt(2, friendId);
					}
					ResultSet accountResultSet = ps.executeQuery();
					while (accountResultSet.next()) {				
						String account = accountResultSet.getString(1);
						System.out.println(account);
						FriendShip friendShip = new FriendShip(userOne, userTwo, friendId, isInvite, account);
						friendShipList.add(friendShip);}
				}
			}
			
			return friendShipList;
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
		return friendShipList;
	}
	
	@Override
	public int updateIsInvite(int idOne, int idTwo) {
		int count = 0;
		String sql = "UPDATE Friendship SET isInvite = 1 WHERE userone_id = ? and usertwo_id = ?;";
		Connection connection = null;
		PreparedStatement ps = null;
		try {
			connection = DriverManager.getConnection(URL, USER, PASSWORD);
			ps = connection.prepareStatement(sql);
			ps.setInt(1, idOne);
			ps.setInt(2, idTwo);
			count = ps.executeUpdate();
			if (count == 0) {
				ps.setInt(1, idTwo);
				ps.setInt(2, idOne);
				count = ps.executeUpdate();
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

		return count;
	}
	
	@Override
	public List<FriendShip> getAllInvite(int userId){
		String sql = "SELECT userone_id, usertwo_id, isInvite FROM Friendship " 
						+ "WHERE userone_id = ? or usertwo_id = ?;";
		Connection connection = null;
		PreparedStatement ps = null;
		List<FriendShip> friendShipInvite = new ArrayList<FriendShip>();
		try {
			connection = DriverManager.getConnection(URL, USER, PASSWORD);
			ps = connection.prepareStatement(sql);
			ps.setInt(1, userId);
			ps.setInt(2, userId);
			ResultSet resultSet = ps.executeQuery();
			int friendId;		
			
			while (resultSet.next()) {
				int userOne = resultSet.getInt(1);
				int userTwo = resultSet.getInt(2);
				Boolean isInvite = resultSet.getBoolean(3);
				if (!isInvite) {
					if(userId == userOne) {
						friendId = userTwo;
						sql = "SELECT user_account FROM Friendship a LEFT join User b "
								+ "on a.usertwo_id = b.user_id WHERE userone_id = ? and usertwo_id = ? and isInvite = false;";
						ps = connection.prepareStatement(sql);
						ps.setInt(1, userId);
						ps.setInt(2, friendId);
						
					}else {
						friendId = userOne;
						sql = "SELECT user_account FROM Friendship a LEFT join User b "
								+ "on a.userone_id = b.user_id WHERE usertwo_id = ? and userone_id = ? and isInvite = false;";
						ps = connection.prepareStatement(sql);
						ps.setInt(1, userId);	
						ps.setInt(2, friendId);
					}
					ResultSet accountResultSet = ps.executeQuery();
					while (accountResultSet.next()) {				
						String account = accountResultSet.getString(1);
						System.out.println(account);
						FriendShip friendShip = new FriendShip(userOne, userTwo, friendId, isInvite, account);
						friendShipInvite.add(friendShip);}
				}
			}
			
			return friendShipInvite;
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
		return friendShipInvite;
	}

}
