package tw.dp103g4.party;


import static tw.dp103g4.main.Common.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

public class PartyDaoImpl implements PartyDao{

	@Override
	public List<Party> getAll() {
		// TODO Auto-generated method stub
		return null;
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
		// TODO Auto-generated method stub
		return null;
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
