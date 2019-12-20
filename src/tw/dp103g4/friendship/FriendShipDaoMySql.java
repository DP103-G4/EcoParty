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
	public List<FriendShip> getAll() {
		String sql = "SELECT userone_id, usertwo_id " + "FROM Friendship ORDER BY userone DESC;";
		Connection connection = null;
		PreparedStatement ps = null;
		List<FriendShip> friendShipList = new ArrayList<FriendShip>();
		try {
			connection = DriverManager.getConnection(URL, USER, PASSWORD);
			ps = connection.prepareStatement(sql);
			ResultSet resultSet = ps.executeQuery();
			while (resultSet.next()) {
				int userOne = resultSet.getInt(1);
				int userTwo = resultSet.getInt(2);
				
				FriendShip friendShip = new FriendShip(userOne, userTwo);
				friendShipList.add(friendShip);
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

}
