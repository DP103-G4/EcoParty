package tw.dp103g4.talk;

import static tw.dp103g4.main.Common.CLASS_NAME;
import static tw.dp103g4.main.Common.PASSWORD;
import static tw.dp103g4.main.Common.URL;
import static tw.dp103g4.main.Common.USER;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class TalkDaoMySql implements TalkDao {

	public TalkDaoMySql() {
		super();
		try {
			Class.forName(CLASS_NAME);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}

	@Override
	public List<Talk> getAll() {
		String sql = "SELECT talk_id, tk_receiver_id, tk_sender_id, party_id, talk_content, talk_time, talk_isRead "
				+ "FROM Talk ORDER BY talk_time ASC;";
		Connection connection = null;
		PreparedStatement ps = null;
		List<Talk> talkList = new ArrayList<Talk>();
		try {
			connection = DriverManager.getConnection(URL, USER, PASSWORD);
			ps = connection.prepareStatement(sql);
			ResultSet resultSet = ps.executeQuery();
			while (resultSet.next()) {
				int id = resultSet.getInt(1);
				int receiverId = resultSet.getInt(2);
				int senderId = resultSet.getInt(3);
				int partyId = resultSet.getInt(4);
				String content = resultSet.getString(5);
				Date time = resultSet.getDate(6);
				Boolean isRead = resultSet.getBoolean(7);
				Talk talk = new Talk(id, receiverId, senderId, partyId, content, time, isRead);
				talkList.add(talk);
			}
			return talkList;
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
		return talkList;

	}

	@Override
	public int insert(Talk talk) {
		int count = 0;
		String sql ="";
		int partyId = talk.getPartyId();
		
		if (String.valueOf(partyId).equals("")) {
			sql = "INSERT INTO Talk " + "(tk_receiver_id,tk_sender_id,party_id,talk_content) "
					+ "VALUES (?,?,?,?);";
		} else {
			sql = "INSERT INTO Talk " + "(tk_receiver_id,tk_sender_id,talk_content) " + "VALUES (?,?,?);";
		}
		Connection connection = null;
		PreparedStatement ps = null;
		try {
			connection = DriverManager.getConnection(URL, USER, PASSWORD);
			ps = connection.prepareStatement(sql);
			ps.setInt(1, talk.getReceiverId());
			ps.setInt(2, talk.getSenderId());
			if (partyId != -1) {
				ps.setInt(3, partyId);
				ps.setString(4, talk.getContent());
			} else {
				ps.setString(3, talk.getContent());
			}
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
	public int updateIsRead() {
		int count = 0;
		String sql = "UPDATE Talk SET talk_isRead = 1 WHERE talk_isRead = 0;";
		Connection connection = null;
		PreparedStatement ps = null;
		try {
			connection = DriverManager.getConnection(URL, USER, PASSWORD);
			ps = connection.prepareStatement(sql);
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
