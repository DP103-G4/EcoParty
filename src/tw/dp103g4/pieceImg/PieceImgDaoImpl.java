package tw.dp103g4.pieceImg;

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

public class PieceImgDaoImpl implements PieceImgDao {


	@Override
	public int insert(int pieceId, byte[] data) {
		int count = 0;
		String sql;
		sql = "INSERT INTO Piece_img(piece_id, piece_img_data) VALUES(?, ?);";
		Connection connection = null;
		PreparedStatement ps = null;
		
		try {
			connection = DriverManager.getConnection(URL, USER, PASSWORD);
			ps = connection.prepareStatement(sql);
			ps.setInt(1, pieceId);
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
		int count = 0;
		String sql;
		sql = "delete from Piece_img where piece_id = ?;";
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
	public byte[] getImage(int id) {
		String sql = "SELECT piece_img_data FROM Piece_img WHERE piece_img_id = ?;";
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
	public List<PieceImg> getAllByPiece(int pieceId) {
		String sql = "select piece_img_id, piece_id from piece_img where piece_id = ? order by piece_img_id desc;";
		List<PieceImg> pieceImgs = new ArrayList<PieceImg>();
		try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
				PreparedStatement ps = connection.prepareStatement(sql);) {
			ps.setInt(1, pieceId);
			try (ResultSet rs = ps.executeQuery();) {
				while (rs.next()) {
					int id = rs.getInt(1);
					int piece_id = rs.getInt(2);
					PieceImg pieceImg = new PieceImg(id, piece_id);
					pieceImgs.add(pieceImg);
				}
			}
			return pieceImgs;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return pieceImgs;
	}

}
