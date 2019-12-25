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
				+ "party_count_upper_limit, party_count_lower_limit, party_count_current, party_distance from Party "
				+ "order by party_post_time desc;";
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
					Date endPostTime = rs.getDate(7);
					String location = rs.getString(8);
					String address = rs.getString(9);
					String content = rs.getString(10);
					int countUpperLimit = rs.getInt(11);
					int countLowerLimit = rs.getInt(12);
					int countCurrent = rs.getInt(13);
					double distance = rs.getDouble(14);
					Party party = new Party(id, ownerId, name, startTime, endTime, endPostTime, location, address,
							content, countUpperLimit, countLowerLimit, distance);
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
	public int insert(Party party, byte[] image) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int update(Party party, byte[] coverImg, byte[] beforeImg, byte[] AfterImg) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int delete(int id) {
		// TODO Auto-generated method stub
		return 0;
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
	public int setPartyImg(int id, byte[] beforeImg, byte[] AfterImg) {
		// TODO Auto-generated method stub
		return 0;
	}

}
