package tw.dp103g4.inform;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static tw.dp103g4.main.Common.*;


public class InformDaoImpl implements InformDao {

	public InformDaoImpl() {
		super();
		try {
			Class.forName(CLASS_NAME);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}

	@Override
	public List<Inform> getAllbyReceiver(int receiverId) {
		String sql = "select inform_id, party_id, inform_time, inform_content, "
				+ "inform_isRead from Inform where user_id = ? order by inform_time desc;";
		List<Inform> informList = new ArrayList<Inform>();
		try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
				PreparedStatement ps = connection.prepareStatement(sql);) {
				ps.setInt(1, receiverId);
			try (ResultSet rs = ps.executeQuery();) {
				while (rs.next()) {
					int id = rs.getInt(1);
					int partyId = rs.getInt(2);
					Date time = rs.getDate(3);
					String content = rs.getString(4);
					boolean isRead = rs.getBoolean(5);
					Inform inform = new Inform(id, receiverId, partyId, time, content, isRead);
					informList.add(inform);
				}
			}
			return informList;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return informList;
	}

	@Override
	public int insert(Inform inform) {
		int count = 0;
		String sql = "INSERT INTO Inform (`user_id`, `party_id`, `inform_content`) VALUES (?, ?, ?);";
		try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
				PreparedStatement ps = connection.prepareStatement(sql);) {
			ps.setInt(1, inform.getUserId());
			ps.setInt(2, inform.getPartyId());
			ps.setString(3, inform.getContent());
			count = ps.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return count;
	}

	@Override
	public int delete(int id) {
		int count = 0;
		String sql = "delete from Inform where inform_id = ?;";
		try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
				PreparedStatement ps = connection.prepareStatement(sql);) {
			ps.setInt(1, id);
			count = ps.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return count;
	}

	@Override
	public int setAllRead(int receiverId) {
		int count = 0;
		String sql = "update Inform set Inform_isRead = 1 where user_id = ?;";
		try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
				PreparedStatement ps = connection.prepareStatement(sql);) {
			ps.setInt(1, receiverId);
			count = ps.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return count;
	}

	@Override
	public int setRead(int id) {
		int count = 0;
		String sql = "update Inform set Inform_isRead = 1 where inform_id = ?;";
		try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
				PreparedStatement ps = connection.prepareStatement(sql);) {
			ps.setInt(1, id);
			count = ps.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return count;
	}

}
