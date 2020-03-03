package tw.dp103g4.reviewImg;

import static tw.dp103g4.main.Common.PASSWORD;
import static tw.dp103g4.main.Common.URL;
import static tw.dp103g4.main.Common.USER;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import tw.dp103g4.main.Common;


public class ReviewImgDaoImpl implements ReviewImgDao {
	
	public ReviewImgDaoImpl() {
		try {
			Class.forName(Common.CLASS_NAME);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}

	@Override
	public List<ReviewImg> getAllByParty(int partyId) {
		String sql = "SELECT review_img_id FROM Review_img WHERE party_id = ?;";
		
		List<ReviewImg> reviewList = new ArrayList<ReviewImg>();
		try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
				PreparedStatement ps = connection.prepareStatement(sql);) {
				ps.setInt(1, partyId);
			try (ResultSet rs = ps.executeQuery();) {
				while (rs.next()) {
					int review_id = rs.getInt(1);				
					ReviewImg reviewImg = new ReviewImg(review_id);
					reviewList.add(reviewImg);
				}
			}
			return reviewList;
		
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return reviewList;
	}

	@Override
	public int insert(int partyId, byte[] data) {
		int count = 0;
		String sql = "insert into Review_img (party_id, review_img_data) value (?, ?);";
		try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
				PreparedStatement ps = connection.prepareStatement(sql);) {
			ps.setInt(1, partyId);
			ps.setBytes(2, data);
			count = ps.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return count;
	}

	@Override
	public int delete(int id) {
		int count = 0;
		String sql = "delete from Review_img where review_img_id = ?;";
		try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
				PreparedStatement ps = connection.prepareStatement(sql)) {
			ps.setInt(1, id);
			count = ps.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return count;
	}

	@Override
	public byte[] getImage(int id) {
		byte[] image = null;
		String sql = "select review_img_data from Review_img where review_img_id = ?;";
		
		try {
			Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
			PreparedStatement ps = connection.prepareStatement(sql);
			ps.setInt(1, id);
			ResultSet rs = ps.executeQuery();
			if (rs.next()) {
				image = rs.getBytes(1);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return image;
	}



}
