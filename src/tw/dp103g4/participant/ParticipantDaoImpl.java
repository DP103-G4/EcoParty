package tw.dp103g4.participant;

import static tw.dp103g4.main.Common.PASSWORD;
import static tw.dp103g4.main.Common.URL;
import static tw.dp103g4.main.Common.USER;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import tw.dp103g4.party.Party;

public class ParticipantDaoImpl implements ParticipantDao {

	@Override
	public List<ParticipantInfo> getAllByParty(int partyId) {
		List<ParticipantInfo> participantInfos = new ArrayList<ParticipantInfo>();
		
		String sql = "select u.user_name, participant_id, participant_count, participant_isArrival, participant_isStaff "
				+ "from Participant p join User u on p.participant_id = u.user_id "
				+ "where party_id = ?;";
		Connection connection = null;
		PreparedStatement ps = null;
		try {
			connection = DriverManager.getConnection(URL, USER, PASSWORD);
			ps = connection.prepareStatement(sql);
			ps.setInt(1, partyId);
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				String name = rs.getString(1);
				int id = rs.getInt(2);
				int count = rs.getInt(3);
				boolean isArrival = rs.getBoolean(4);
				boolean isStaff = rs.getBoolean(5);
				Participant participant = new Participant(id, partyId, count, isArrival, isStaff);
				ParticipantInfo participantInfo = new ParticipantInfo(participant, name);
				participantInfos.add(participantInfo);
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
		
		return participantInfos;
	}

	@Override
	public Participant findById(int id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int insert(Participant participant) {
		int count = 0, success = 0;
		String sql;
		sql = "INSERT INTO Participant" + "(participant_id, party_id, participant_count)" + " VALUES(?, ?, ?);";
		Connection connection = null;
		PreparedStatement ps = null;
		
		try {
			connection = DriverManager.getConnection(URL, USER, PASSWORD);
			ps = connection.prepareStatement(sql);
			ps.setInt(1, participant.getId());
			ps.setInt(2, participant.getPartyId());
			ps.setInt(3, participant.getCount());
			success = ps.executeUpdate();
			count = participant.getCount();
			if (success == 0)
				count = 0;
			
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
	public int delete(Participant participant) {
		int count = 0, success = 0;
		String sql = "select participant_count FROM Participant WHERE participant_id = ? and party_id = ?;";
		Connection connection = null;
		PreparedStatement ps = null;
		try {
			connection = DriverManager.getConnection(URL, USER, PASSWORD);
			ps = connection.prepareStatement(sql);
			ps.setInt(1, participant.getId());
			ps.setInt(2, participant.getPartyId());
			
			ResultSet rs = ps.executeQuery();
			if (rs.next()) {
				count = rs.getInt(1);
			}
			
			sql = "DELETE FROM Participant WHERE participant_id = ? and party_id = ?;";
			ps = connection.prepareStatement(sql);
			ps.setInt(1, participant.getId());
			ps.setInt(2, participant.getPartyId());
			success = ps.executeUpdate();
			if (success == 0)
				count = 0;
							
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
	public int setArrival(int userId, int partyId, boolean isArrival) {
		int count = 0;
		String sql = "update Participant set participant_isArrival = ? where participant_id = ? and party_id = ?;";
		Connection connection = null;
		PreparedStatement ps = null;
		try {
			connection = DriverManager.getConnection(URL, USER, PASSWORD);
			ps = connection.prepareStatement(sql);
			ps.setBoolean(1, isArrival);
			ps.setInt(2, userId);
			ps.setInt(3, partyId);
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
	public int setStaff(int userId, int partyId, boolean isStaff) {
		int count = 0;
		String sql = "update Participant set participant_isStaff = ? where participant_id = ? and party_id = ?;";
		Connection connection = null;
		PreparedStatement ps = null;
		try {
			connection = DriverManager.getConnection(URL, USER, PASSWORD);
			ps = connection.prepareStatement(sql);
			ps.setBoolean(1, isStaff);
			ps.setInt(2, userId);
			ps.setInt(3, partyId);
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
	public int isIn(int userId, int partyId) {
		int count = 0;
		String sql = "select * from participant where participant_id = ? and party_id = ?;";
		Connection connection = null;
		PreparedStatement ps = null;
		try {
			connection = DriverManager.getConnection(URL, USER, PASSWORD);
			ps = connection.prepareStatement(sql);
			ps.setInt(1, userId);
			ps.setInt(2, partyId);
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
