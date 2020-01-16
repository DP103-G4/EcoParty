package tw.dp103g4.party;

import static tw.dp103g4.main.Common.*;
import java.sql.Connection;
import java.util.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;

import java.util.List;


public class PartyDaoImpl implements PartyDao {

	public PartyDaoImpl() {
		super();
		try {
			Class.forName(CLASS_NAME);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}

	@Override
	public Party findById(int id) {
		Party party = null;	
		
		String sql = "select owner_id, party_name, party_start_time, party_end_time, "
				+ "party_post_time, party_post_end_time, party_location, party_address, longitude, latitude, party_content, "
				+ "party_count_upper_limit, party_count_lower_limit, party_count_current, party_state, party_distance "
				+ "from Party "
				+ "where party_id = ?;";
		Connection connection = null;
		PreparedStatement ps = null;
		try {
			connection = DriverManager.getConnection(URL, USER, PASSWORD);
			ps = connection.prepareStatement(sql);
			ps.setInt(1, id);
			ResultSet rs = ps.executeQuery();
			if (rs.next()) {
				int ownerId = rs.getInt(1);
				String name = rs.getString(2);
				Date startTime = rs.getTimestamp(3);
				Date endTime = rs.getTimestamp(4);
				Date postTime = rs.getTimestamp(5);
				Date postEndTime = rs.getTimestamp(6);
				String location = rs.getString(7);
				String address = rs.getString(8);
				Double longitude = rs.getDouble(9);
				Double latitude = rs.getDouble(10);
				String content = rs.getString(11);
				int countUpperLimit = rs.getInt(12);
				int countLowerLimit = rs.getInt(13);
				int countCurrent = rs.getInt(14);
				int state = rs.getInt(15);
				int distance = rs.getInt(16);
				
				party = new Party(id, ownerId, name, startTime, endTime, postTime, postEndTime, 
						location, address, longitude, latitude, content, 
						countUpperLimit, countLowerLimit, countCurrent, state, distance);
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
		
		return party;
	}

	@Override
	public int insert(Party party, byte[] coverImg) {
		int count = 0;
		String sql;
		if (coverImg != null) {
			sql = "INSERT INTO Party" + "(owner_id, party_name,"
					+ " party_start_time, party_end_time, party_post_time, party_post_end_time,"
					+ " party_location, party_address, longitude, latitude, party_content,"
					+ " party_count_upper_limit, party_count_lower_limit, party_count_current,"
					+ " party_state, party_distance, party_cover_img)" + " VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?);";
			
		} else {
			sql = "INSERT INTO Party" + "(owner_id, party_name,"
					+ " party_start_time, party_end_time, party_post_time, party_post_end_time,"
					+ " party_location, party_address, longitude, latitude, party_content,"
					+ " party_count_upper_limit, party_count_lower_limit, party_count_current,"
					+ " party_state, party_distance)" + " VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?);";
		}
		Connection connection = null;
		PreparedStatement ps = null;
		try {
			connection = DriverManager.getConnection(URL, USER, PASSWORD);
			ps = connection.prepareStatement(sql);
			ps.setInt(1, party.getOwnerId());
			ps.setString(2, party.getName());
			ps.setTimestamp(3, new Timestamp(party.getStartTime().getTime()));
			ps.setTimestamp(4, new Timestamp(party.getEndTime().getTime()));
			ps.setTimestamp(5, new Timestamp(party.getPostTime().getTime()));
			ps.setTimestamp(6, new Timestamp(party.getPostEndTime().getTime()));
			ps.setString(7, party.getLocation());
			ps.setString(8, party.getAddress());
			ps.setDouble(9, party.getLongitude());
			ps.setDouble(10, party.getLatitude());
			ps.setString(11, party.getContent());
			ps.setInt(12, party.getCountUpperLimit());
			ps.setInt(13, party.getCountLowerLimit());
			ps.setInt(14, party.getCountCurrent());
			ps.setInt(15, party.getState());
			ps.setInt(16, party.getDistance());
			
			if (coverImg != null) 
				ps.setBytes(17, coverImg);

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
	public int update(Party party, byte[] coverImg) {
		int count = 0;
		String sql = "UPDATE Party SET" + "	owner_id = ?, party_name = ?,"
				+ " party_start_time = ?, party_end_time = ?, party_post_end_time = ?,"
				+ " party_location = ?, party_address = ?, longitude = ?, latitude = ?, party_content = ?,"
				+ " party_count_upper_limit = ?, party_count_lower_limit = ?, party_count_current = ?,"
				+ " party_state = ?, party_distance = ?";
		if (coverImg != null) {
			sql += ", coverImg = ? WHERE party_id = ?";
		} else {
			sql += " WHERE party_id = ?";
		}

		Connection connection = null;
		PreparedStatement ps = null;
		try {
			connection = DriverManager.getConnection(URL, USER, PASSWORD);
			ps = connection.prepareStatement(sql);
			ps.setInt(1, party.getOwnerId());
			ps.setString(2, party.getName());
			ps.setTimestamp(3, new Timestamp(party.getStartTime().getTime()));
			ps.setTimestamp(4, new Timestamp(party.getEndTime().getTime()));
			ps.setTimestamp(5, new Timestamp(party.getPostTime().getTime()));
			ps.setTimestamp(6, new Timestamp(party.getPostEndTime().getTime()));
			ps.setString(7, party.getLocation());
			ps.setString(8, party.getAddress());
			ps.setDouble(9, party.getLongitude());
			ps.setDouble(10, party.getLatitude());
			ps.setString(11, party.getContent());
			ps.setInt(12, party.getCountUpperLimit());
			ps.setInt(13, party.getCountLowerLimit());
			ps.setInt(14, party.getCountCurrent());
			ps.setInt(15, party.getState());
			ps.setInt(16, party.getDistance());

			count = ps.executeUpdate();
			if (coverImg != null) {
				ps.setBytes(17, coverImg);
				ps.setInt(18, party.getId());
			} else {
				ps.setInt(17, party.getId());
			}
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
		
		return count;
	}

	@Override
	public List<Party> getPartyList(int state) {
		String sql = "select party_id, owner_id, party_address, party_start_time, party_name from Party "
				+ "where party_state = ? order by party_post_time desc;";
		
		List<Party> partyList = new ArrayList<Party>();
		try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
				PreparedStatement ps = connection.prepareStatement(sql);) {
			ps.setInt(1, state);
			try (ResultSet rs = ps.executeQuery();) {
				while (rs.next()) {
					int id = rs.getInt(1);
					int ownerId = rs.getInt(2);
					String address = rs.getString(3);
					Date startTime = rs.getDate(4);
					String name = rs.getString(5);
					Party party = new Party(id, ownerId, name, startTime, address, state);
					partyList.add(party);
				}
			}
			return partyList;
		
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return partyList;
	}

	@Override
	public List<Party> getPieceList (int state) {
		String sql = "select party_id from Party where party_state = ? order by party_end_time desc;";
		
		List<Party> pieceList = new ArrayList<Party>();
		try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
				PreparedStatement ps = connection.prepareStatement(sql);) {
			ps.setInt(1, state);
			try (ResultSet rs = ps.executeQuery();) {
				while (rs.next()) {
					int id = rs.getInt(1);
					Party party = new Party(id, state);
					pieceList.add(party);
				}
			}
			return pieceList;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return pieceList;
	}
	
	@Override
	public byte[] getCoverImg(int id) {
		byte[] image = null;
		String sql = "select party_cover_img from Party where party_id = ?;";
		
		try {
			Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
			PreparedStatement ps = connection.prepareStatement(sql);
			ps.setInt(1, id);
			ResultSet rs = ps.executeQuery();
			if (rs.next()) {
				image = rs.getBytes(1);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return image;
	}

	
	@Override
	public byte[] getBeforeImg(int id) {
		byte[] image = null;
		String sql = "select party_before_img from Party where party_id = ?;";
		
		try {
			Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
			PreparedStatement ps = connection.prepareStatement(sql);
			ps.setInt(1, id);
			ResultSet rs = ps.executeQuery();
			if (rs.next()) {
				image = rs.getBytes(1);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return image;
	}

	@Override
	public byte[] getAfterImg(int id) {
		byte[] image = null;
		String sql = "select party_after_img from Party where party_id = ?;";
		
		try {
			Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
			PreparedStatement ps = connection.prepareStatement(sql);
			ps.setInt(1, id);
			ResultSet rs = ps.executeQuery();
			if (rs.next()) {
				image = rs.getBytes(1);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return image;
	}

	@Override
	public int setImg(int id, byte[] beforeImg, byte[] afterImg) {
		int count = 0;
		String sql = "UPDATE Party SET" + "	party_before_img = ?, party_after_img = ?"
				+ "where party_id = ?";
		Connection connection = null;
		PreparedStatement ps = null;
		try {
			connection = DriverManager.getConnection(URL, USER, PASSWORD);
			ps = connection.prepareStatement(sql);
			ps.setBytes(1, beforeImg);
			ps.setBytes(2, afterImg);
			ps.setInt(3, id);
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
	public List<Party> getCurrentParty(int participantId, int state) {
		String sql = "select pt.party_id from Participant pt left join "
				+ "Party p on pt.party_id = p.party_id "
				+ "where participant_id = ? and party_state = ?;";
		List<Party> currentParty = new ArrayList<Party>();
		try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
				PreparedStatement ps = connection.prepareStatement(sql);) {
			ps.setInt(1, participantId);
			ps.setInt(2, state);
			try (ResultSet rs = ps.executeQuery();) {
				while (rs.next()) {
					int id = rs.getInt(1);
					Party party = new Party(id);
					currentParty.add(party);
				}
			}
			return currentParty;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return currentParty;
	}
}
