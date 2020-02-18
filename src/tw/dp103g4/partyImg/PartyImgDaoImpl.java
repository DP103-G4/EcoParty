package tw.dp103g4.partyImg;

import static tw.dp103g4.main.Common.PASSWORD;
import static tw.dp103g4.main.Common.URL;
import static tw.dp103g4.main.Common.USER;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class PartyImgDaoImpl implements PartyImgDao {


	@Override
	public int insert(PartyImg partyImg, byte[] data) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int delete(int id) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public byte[] getImage(int id) {
		String sql = "SELECT party_img_data FROM Party_img WHERE party_img_id = ?;";
		Connection connection = null;
		PreparedStatement ps = null;
		byte[] image = null;
		try {
			connection = DriverManager.getConnection(URL, USER, PASSWORD);
			ps = connection.prepareStatement(sql);
			ps.setInt(1, id);
			ResultSet rs = ps.executeQuery();
			if (rs.next()) {
				image = rs.getBytes(1);
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
		return image;
	}

	@Override
	public List<PartyImg> getAllByParty(int partyId) {
		String sql = "select party_img_id, party_id from party_img where party_id = ? order by party_img_id desc;";
		List<PartyImg> partyImgs = new ArrayList<PartyImg>();
		try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
				PreparedStatement ps = connection.prepareStatement(sql);) {
			ps.setInt(1, partyId);
			try (ResultSet rs = ps.executeQuery();) {
				while (rs.next()) {
					int id = rs.getInt(1);
					int party_id = rs.getInt(2);
					PartyImg partyImg = new PartyImg(id, party_id);
					partyImgs.add(partyImg);
				}
			}
			return partyImgs;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return partyImgs;
	}

}
