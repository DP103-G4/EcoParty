package tw.dp103g4.msgwarn;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static tw.dp103g4.main.Common.*;

public class MsgWarnDaoMySql implements MsgWarnDao {

	public MsgWarnDaoMySql() {
		super();
		try {
			Class.forName(CLASS_NAME);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}

	@Override
	public List<MsgWarn> getAll() {
		String sql = "SELECT msg_warn_id, message_id, msg_warn_user_id, msg_warn_time, msg_warn_content " + "FROM Msg_warn ORDER BY msg_warn_time ASC;";
		Connection connection = null;
		PreparedStatement ps = null;
		List<MsgWarn> msgWarnList = new ArrayList<MsgWarn>();
		try {
			connection = DriverManager.getConnection(URL, USER, PASSWORD);
			ps = connection.prepareStatement(sql);
			ResultSet resultSet = ps.executeQuery();
			while (resultSet.next()) {
				int id = resultSet.getInt(1);
				int messageId = resultSet.getInt(2);
				int userId = resultSet.getInt(3);
				Date time = resultSet.getDate(4);
				String content = resultSet.getString(5);
				MsgWarn msgWarn = new MsgWarn(id, messageId, userId, time, content);
				msgWarnList.add(msgWarn);
			}
			return msgWarnList;
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
		return msgWarnList;
	}


	

	@Override
	public int inster(MsgWarn msgWarn) {
		int count = 0;
		String sql = "INSERT INTO Msg_warn " + "(message_id,msg_warn_user_id,msg_warn_content) " + "VALUES (?,?,?);";
		Connection connection = null;
		PreparedStatement ps = null;
		try {
			connection = DriverManager.getConnection(URL, USER, PASSWORD);
			ps = connection.prepareStatement(sql);
			ps.setInt(1, msgWarn.getMessageId());
			ps.setInt(2, msgWarn.getUserId());
			ps.setString(3, msgWarn.getContent());
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
	public int delete(int id) {
		int count = 0;
		String sql = "DELETE FROM Msg_warn WHERE msg_warn_id = ?;";
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
	
}
