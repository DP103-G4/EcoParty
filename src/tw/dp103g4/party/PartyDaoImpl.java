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
	public List<Party> getAll(int state) {
		String sql = "select party_id, owner_id, party_name, party_start_time, party_end_time, "
				+ "party_post_end_time, party_location, party_address, party_content, "
				+ "party_count_upper_limit, party_count_lower_limit, party_count_current, party_distance from Party "
				+ "where party_state = ? order by party_post_time desc;";

		List<Party> partyList = new ArrayList<Party>();
		try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
				PreparedStatement ps = connection.prepareStatement(sql);) {
			ps.setInt(1, state);
			try (ResultSet rs = ps.executeQuery();) {
				while (rs.next()) {
					int id = rs.getInt(1);
					int ownerId = rs.getInt(2);
					String name = rs.getString(3);
					Date startTime = rs.getDate(4);
					Date endTime = rs.getDate(5);
					Date postEndTime = rs.getDate(6);
					String location = rs.getString(7);
					String address = rs.getString(8);
					String content = rs.getString(9);
					int countUpperLimit = rs.getInt(10);
					int countLowerLimit = rs.getInt(11);
					int countCurrent = rs.getInt(12);
					double distance = rs.getDouble(13);
					Party party = new Party(id, ownerId, name, startTime, endTime, postEndTime, location, address,
							content, countUpperLimit, countLowerLimit, countCurrent, state, distance);
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
		String sql = "select party_after_img from Party where party_id = ? order by party_end_time desc;";
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
	public int setImg(int id, byte[] beforeImg, byte[] afterImg) {
		// TODO Auto-generated method stub
		return 0;
	}

}
