package tw.dp103g4.partyLike;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import static tw.dp103g4.main.Common.*;

public class PartyLikeDaoImpl implements PartyLikeDao {

	public PartyLikeDaoImpl() {
		super();
		try {
			Class.forName(CLASS_NAME);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}

	@Override
	public List<PartyLike> getAllByUser(int id) {
		String sql = "select party_id from Party_like where user_id = ?";
		List<PartyLike> partyLikeList = new ArrayList<PartyLike>();
		try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
				PreparedStatement ps = connection.prepareStatement(sql);) {
			ps.setInt(1, id);
			try (ResultSet rs = ps.executeQuery()) {
				while (rs.next()) {
					int partyId = rs.getInt(1);
					PartyLike partyLike = new PartyLike(id, partyId);
					partyLikeList.add(partyLike); 
				}
			}
			return partyLikeList;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return partyLikeList;
	}

	@Override
	public int insert(PartyLike partylike) {
		int count = 0;
		String sql = "insert into Party_like (user_id, party_id) value (?, ?);";
		try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
				PreparedStatement ps = connection.prepareStatement(sql);) {
			ps.setInt(1, partylike.getId());
			ps.setInt(2, partylike.getPartyId());
			count = ps.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return count;
	}

	@Override
	public int delete(int id, int partyId) {
		int count = 0;
		String sql = "delete from Party_like where user_id = ? and party_id = ?";
		try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
				PreparedStatement ps = connection.prepareStatement(sql);) {
			ps.setInt(1, id);
			ps.setInt(2, partyId);
			count = ps.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return count;
	}




}
