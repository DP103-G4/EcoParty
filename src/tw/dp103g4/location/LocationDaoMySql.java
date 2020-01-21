package tw.dp103g4.location;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static tw.dp103g4.main.Common.*;

public class LocationDaoMySql implements LocationDao{

	public LocationDaoMySql() {
		super();
		try {
			Class.forName(CLASS_NAME);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}

	@Override
	public List<Location> getAll(int partyId) {
		String sql = "select location_id, user_id, longitude, latitude, location_name, "
				+ "location_content from Location where party_id = ?;";
		List<Location> locationList = new ArrayList<Location>();
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
					Location location = new Location(id, partyId, userId, latitude, longitude, name, content);
					locationList.add(location);
				}
			}
			return locationList;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return locationList;
	}

	@Override
	public Location getById(int id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int insert(Location location) {
		int count = 0;
		String sql = "insert into Location (party_id, user_id, longitude, latitude, location_name, location_content)"
				+ " value (?, ?, ?, ?, ?, ?);";
		try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
				PreparedStatement ps = connection.prepareStatement(sql);) {
			ps.setInt(1, location.getPartyId());
			ps.setInt(2, location.getUserId());
			ps.setDouble(3, location.getLongitude());
			ps.setDouble(4, location.getLatitude());
			ps.setString(5, location.getName());
			ps.setString(6, location.getContent());
			count = ps.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return count;
	}

	@Override
	public int update(Location location) {
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
