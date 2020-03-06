package tw.dp103g4.reviewImg;

import static tw.dp103g4.main.Common.PASSWORD;
import static tw.dp103g4.main.Common.URL;
import static tw.dp103g4.main.Common.USER;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

public class ReviewImgDaoImpl implements ReviewImgDao {

	@Override
	public List<ReviewImg> getAllByParty(int partyId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int insert(int partyId, byte[] data) {
		int count = 0;
		String sql;
		sql = "INSERT INTO Review_img(review_img_id, review_img_data) VALUES(?, ?);";
		Connection connection = null;
		PreparedStatement ps = null;
		
		try {
			connection = DriverManager.getConnection(URL, USER, PASSWORD);
			ps = connection.prepareStatement(sql);
			ps.setInt(1, partyId);
			ps.setBytes(2, data);
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
	public int delete(int id) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public byte[] getData(int id) {
		// TODO Auto-generated method stub
		return null;
	}



}
