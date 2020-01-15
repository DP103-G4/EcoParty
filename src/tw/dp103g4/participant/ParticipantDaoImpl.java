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
	public List<Participant> getAllByParty(int partyId) {
		List<Participant> participants = new ArrayList<Participant>();
		
		String sql = "select participant_id, participant_count, participant_isArrival, participant_isStaff "
				+ "from Participant "
				+ "where party_id = ?;";
		Connection connection = null;
		PreparedStatement ps = null;
		try {
			connection = DriverManager.getConnection(URL, USER, PASSWORD);
			ps = connection.prepareStatement(sql);
			ps.setInt(1, partyId);
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				int id = rs.getInt(1);
				int count = rs.getInt(2);
				boolean isArrival = rs.getBoolean(3);
				boolean isStaff = rs.getBoolean(4);
				Participant participant = new Participant(id, partyId, count, isArrival, isStaff);
				participants.add(participant);
			}
			return participants;
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
		
		return participants;
	}

	@Override
	public Participant findById(int id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int insert(Participant participant) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int delete(int id) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int setArrival(int id) {
		// TODO Auto-generated method stub
		return 0;
	}


}
