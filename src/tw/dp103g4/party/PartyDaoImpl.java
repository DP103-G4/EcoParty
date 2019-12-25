package tw.dp103g4.party;


import static tw.dp103g4.main.Common.*;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
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
	public List<Party> getAll() {
		String sql = "select party_id, owner_id, party_name, party_start_time, party_end_time, "
				+ "party_post_time, party_post_end_time, party_location, party_address, party_content, "
				+ "party_count_upper_limit, party_count_lower_limit, party_count_current, party_state, party_distance from Party "
				+ "where party_state = 1 order by party_post_time desc;";

				List<Party> partyList = new ArrayList<Party>();
		try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
				PreparedStatement ps = connection.prepareStatement(sql);) {
			try (ResultSet rs = ps.executeQuery();) {
				while (rs.next()) {
					int id = rs.getInt(1);
					int ownerId = rs.getInt(2);
					String name = rs.getString(3);
					Date startTime = rs.getDate(4);
					Date endTime = rs.getDate(5);
					Date postTime = rs.getDate(6);
					Date postEndTime = rs.getDate(7);
					String location = rs.getString(8);
					String address = rs.getString(9);
					String content = rs.getString(10);
					int countUpperLimit = rs.getInt(11);
					int countLowerLimit = rs.getInt(12);
					int countCurrent = rs.getInt(13);
					int state = rs.getInt(14);
					double distance = rs.getDouble(15);
					Party party = new Party(id, ownerId, name, startTime, endTime, postEndTime, location, 
							address, content, countUpperLimit, countLowerLimit, countCurrent, state, distance);
							
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
	public Party findById(int id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int insert(Party party, byte[] coverImg) {
		int count = 0;
		String sql = "INSERT INTO Party" + "(owner_id, party_name,"
				+ " party_start_time, party_end_time, party_post_end_time,"
				+ " party_location, party_address, longitude, latitude, party_content,"
				+ " party_count_upper_limit, party_count_lower_limit, party_count_current,"
				+ " party_state, party_distance)"
				+ " VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?);";
		Connection connection = null;
		PreparedStatement ps = null;
		try {
			connection = DriverManager.getConnection(URL, USER, PASSWORD);
			ps = connection.prepareStatement(sql);
			ps.setInt(1, party.getOwnerId());
			ps.setString(2, party.getName());
			ps.setDate(3, party.getStartTime());
			ps.setDate(4, party.getEndTime());
			ps.setDate(5, party.getPostEndTime());
			ps.setString(6, party.getLocation());
			ps.setString(7, party.getAddress());
			ps.setDouble(8, party.getLongitude());
			ps.setDouble(9, party.getLatitude());
			ps.setString(10, party.getContent());
			ps.setInt(11, party.getCountUpperLimit());
			ps.setInt(12, party.getCountLowerLimit());
			ps.setInt(13, party.getCountCurrent());
			ps.setInt(14, party.getState());
			ps.setDouble(15, party.getDistance());
			
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
		String sql = "UPDATE Party SET"
				+ "	owner_id = ?, party_name = ?,"
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
			ps.setInt(1, party.getOwnerId());
			ps.setString(2, party.getName());
			ps.setDate(3, party.getStartTime());
			ps.setDate(4, party.getEndTime());
			ps.setDate(5, party.getPostEndTime());
			ps.setString(6, party.getLocation());
			ps.setString(7, party.getAddress());
			ps.setDouble(8, party.getLongitude());
			ps.setDouble(9, party.getLatitude());
			ps.setString(10, party.getContent());
			ps.setInt(11, party.getCountUpperLimit());
			ps.setInt(12, party.getCountLowerLimit());
			ps.setInt(13, party.getCountCurrent());
			ps.setInt(14, party.getState());
			ps.setDouble(15, party.getDistance());
						
			if (coverImg != null) {
				ps.setBytes(16, coverImg);
				ps.setInt(17, party.getId());
			} else {
				ps.setInt(16, party.getId());
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
	public int delete(int id) {
		int count = 0;
		String sql = "DELETE FROM Party WHERE party_id = ?;";
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
	public byte[] getCoverImg(int id) {
		String sql = "select party_cover_img from Party where party_id = ?;";
		byte[] image = null;
		try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
				PreparedStatement ps = connection.prepareStatement(sql);) {
			ps.setInt(1, id);
			try (ResultSet rs = ps.executeQuery();) {
				if (rs.next()) {
					image = rs.getBytes(1);
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return image;
	}

	@Override
	public byte[] getBeforeImg(int id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public byte[] getAfterImg(int id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int setImg(int id, byte[] beforeImg, byte[] afterImg) {
		// TODO Auto-generated method stub
		return 0;
	}



}
