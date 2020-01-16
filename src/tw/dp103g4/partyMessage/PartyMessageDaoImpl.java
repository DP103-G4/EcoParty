package tw.dp103g4.partyMessage;

import static tw.dp103g4.main.Common.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class PartyMessageDaoImpl implements PartyMessageDao {

	@Override
	public List<PartyMessage> getAllbyParty(int partyId) {
		String sql = "select user_id, party_message_content, party_message_time from Party_message where party_id = ? order by party_message_time";
		Connection connection = null;
		PreparedStatement ps = null;
		List<PartyMessage> msgList = new ArrayList<PartyMessage>();
		try {
			connection = DriverManager.getConnection(URL, USER, PASSWORD);
			ps = connection.prepareStatement(sql);
			ps.setInt(1, partyId);
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				int userId = rs.getInt(1);
				String content= rs.getString(2);
				Date time = rs.getTimestamp(3);
				PartyMessage partyMessage = new PartyMessage(userId, content, time);
				msgList.add(partyMessage);
			}
			return msgList;
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
		return msgList;
	}

	@Override
	public int insert(PartyMessage partyMessage) {
		int count = 0;
		String sql = "INSERT INTO Party_message" + "(user_id, party_id, party_message_content, party_message_time) "
				+ "VALUES(?, ?, ?, ?);";
		Connection connection = null;
		PreparedStatement ps = null;
		try {
			connection = DriverManager.getConnection(URL, USER, PASSWORD);
			ps = connection.prepareStatement(sql);
			ps.setInt(1, partyMessage.getUserId());
			ps.setInt(2, partyMessage.getPartyId());
			ps.setString(3, partyMessage.getContent());
			ps.setTimestamp(4, new Timestamp(partyMessage.getTime().getTime()));
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
	public int delete(int id) {
		// TODO Auto-generated method stub
		return 0;
	}



}
