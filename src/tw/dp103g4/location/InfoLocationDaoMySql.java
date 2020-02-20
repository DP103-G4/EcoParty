package tw.dp103g4.location;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static tw.dp103g4.main.Common.*;

public class InfoLocationDaoMySql implements InfoLocationDao{

	public InfoLocationDaoMySql() {
		super();
		try {
			Class.forName(CLASS_NAME);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}

	@Override
	public List<InfoLocation> getAll(int partyId) {
		String sql = "select location_id, user_id, longitude, latitude, location_name, "
				+ "location_content from Location where party_id = ?;";
		List<InfoLocation> infoLocationList = new ArrayList<InfoLocation>();
		try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
				PreparedStatement ps = connection.prepareStatement(sql);){
			ps.setInt(1,partyId);
			try (ResultSet rs = ps.executeQuery()) {
				while (rs.next()) {
					int id = rs.getInt(1);
					int userId = rs.getInt(2);
					Double longitude = rs.getDouble(3);
					Double latitude = rs.getDouble(4);
					String name = rs.getString(5);
					String content = rs.getString(6);
					InfoLocation infoLocation = new InfoLocation(id, partyId, userId, latitude, longitude, name, content);
					infoLocationList.add(infoLocation);
				}
			}
			return infoLocationList;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return infoLocationList;
	}

	@Override
	public InfoLocation getById(int id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int insert(InfoLocation location) {
		int count = 0;
		String sql = "insert into Location (party_id, user_id, longitude, latitude, location_name, location_content)"
				+ " value (?, ?, ?, ?, ?, ?);";
		//查詢下⼀個準備產⽣的號碼
		String idSql = "SELECT AUTO_INCREMENT FROM INFORMATION_SCHEMA.TABLES WHERE TABLE_SCHEMA = 'EcoParty' AND TABLE_NAME = 'Location';";
		try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
				PreparedStatement psId = connection.prepareStatement(idSql);
				PreparedStatement ps = connection.prepareStatement(sql);) {
			ResultSet rs = psId.executeQuery();
			if (rs.next()) {
				ps.setInt(1, location.getPartyId());
				ps.setInt(2, location.getUserId());
				ps.setDouble(3, location.getLongitude());
				ps.setDouble(4, location.getLatitude());
				ps.setString(5, location.getName());
				ps.setString(6, location.getContent());
				count = ps.executeUpdate() == 0 ? 0 : rs.getInt(1);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return count;
	}

	@Override
	public int update(InfoLocation location) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int deleteById(int id) {
		int count = 0;
		String sql = "delete from Location where location_id = ?;";
		try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
				PreparedStatement ps = connection.prepareStatement(sql);){
			ps.setInt(1, id);
			count = ps.executeUpdate();
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return count;
	}
	

}
