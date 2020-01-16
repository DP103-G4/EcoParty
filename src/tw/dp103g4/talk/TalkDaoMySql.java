package tw.dp103g4.talk;

import static tw.dp103g4.main.Common.CLASS_NAME;
import static tw.dp103g4.main.Common.PASSWORD;
import static tw.dp103g4.main.Common.URL;
import static tw.dp103g4.main.Common.USER;

import java.sql.Timestamp;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashSet;
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
	public List<Talk> getAll(int userId, int friendId) {
		String sql = "SELECT talk_id, tk_receiver_id, tk_sender_id, party_id, talk_content, talk_time, talk_isRead "
				+ "FROM Talk WHERE tk_receiver_id = ? and tk_sender_id = ?  or (tk_receiver_id = ? and tk_sender_id = ?) ORDER BY talk_time ASC;";
		Connection connection = null;
		PreparedStatement ps = null;
		List<Talk> talkList = new ArrayList<Talk>();
		try {
			connection = DriverManager.getConnection(URL, USER, PASSWORD);
			ps = connection.prepareStatement(sql);
			ps.setInt(1, userId);
			ps.setInt(2, friendId);
			ps.setInt(3, friendId);
			ps.setInt(4, userId);
			ResultSet resultSet = ps.executeQuery();
			while (resultSet.next()) {
				int id = resultSet.getInt(1);
				int receiverId = resultSet.getInt(2);
				int senderId = resultSet.getInt(3);
				int partyId = resultSet.getInt(4);
				String content = resultSet.getString(5);
				Timestamp time = resultSet.getTimestamp(6);
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
		String sql = "";
		int partyId = talk.getPartyId();
		if (partyId != -1) {
			sql = "INSERT INTO Talk " + "(tk_receiver_id,tk_sender_id,party_id,talk_content) " + "VALUES (?,?,?,?);";
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
	public int updateIsRead(int senderId, int receiverId) {
		int count = 0;
		String sql = "UPDATE Talk SET talk_isRead = 1 WHERE tk_receiver_id = ? and tk_sender_id = ? and talk_isRead = 0;";
		Connection connection = null;
		PreparedStatement ps = null;
		try {
			connection = DriverManager.getConnection(URL, USER, PASSWORD);
			ps = connection.prepareStatement(sql);
			ps.setInt(1, receiverId);
			ps.setInt(2, senderId);
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
	public List<NewestTalk> getNewestTalk(int userId) {
		List<NewestTalk> newestTalksList = new ArrayList<NewestTalk>();
		String sql = "SELECT a.tk_receiver_id, a.tk_sender_id, a.talk_content, b.MaxTime, c.user_account, (d.user_account) as NoMy "
				+ "FROM ( select tk_receiver_id, tk_sender_id, Max(talk_time) as MaxTime From Talk where tk_receiver_id = ? or tk_sender_id = ? group by tk_sender_id, tk_receiver_id ) b "
				+ "inner join Talk a on a.tk_sender_id = b.tk_sender_id and a.talk_time = b.MaxTime "
				+ "left join User c on a.tk_sender_id = c.user_id "
				+ "left join User d on a.tk_receiver_id = d.user_id;";
		HashSet<String> friendCheck = new HashSet<String>();
		int friendId;
		Connection connection = null;
		PreparedStatement ps = null;
		try {
			connection = DriverManager.getConnection(URL, USER, PASSWORD);
			ps = connection.prepareStatement(sql);
			ps.setInt(1, userId);
			ps.setInt(2, userId);
			ResultSet resultSet = ps.executeQuery();
			while (resultSet.next()) {
				int receiverId = resultSet.getInt(1);
				int senderId = resultSet.getInt(2);
				String content = resultSet.getString(3);
				Timestamp newMsgTime = resultSet.getTimestamp(4);
				String account = resultSet.getString(5);
				String noMy = resultSet.getString(6);
				if(senderId==userId) {
					friendId = receiverId;
					account = noMy;
				}else {
					friendId = senderId;
				}
				if (friendCheck.add(String.valueOf(senderId) + String.valueOf(receiverId))
						& friendCheck.add(String.valueOf(receiverId) + String.valueOf(senderId))) {
					NewestTalk newestTalk = new NewestTalk(friendId, content, newMsgTime, account);
					newestTalksList.add(newestTalk);
				}
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
		return newestTalksList;
	}
}
