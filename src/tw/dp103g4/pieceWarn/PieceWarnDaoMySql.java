package tw.dp103g4.pieceWarn;

import static tw.dp103g4.main.Common.*;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import tw.dp103g4.msgWarn.MsgWarn;

public class PieceWarnDaoMySql implements PieceWarnDao {

	public PieceWarnDaoMySql() {
		super();
		try {
			Class.forName(CLASS_NAME);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}

	@Override
	public List<PieceWarn> getAll() {
		String sql = "SELECT piece_warn_id, piece_id, piece_warn_user_id, piece_warn_time, piece_warn_content "
				+ "FROM Piece_warn ORDER BY piece_warn_time ASC;";
		Connection connection = null;
		PreparedStatement ps = null;
		List<PieceWarn> pieceWarnList = new ArrayList<PieceWarn>();
		try {
			connection = DriverManager.getConnection(URL, USER, PASSWORD);
			ps = connection.prepareStatement(sql);
			ResultSet resultSet = ps.executeQuery();
			while (resultSet.next()) {
				int id = resultSet.getInt(1);
				int pieceId = resultSet.getInt(2);
				int userId = resultSet.getInt(3);
				Date time = resultSet.getDate(4);
				String content = resultSet.getString(5);
				PieceWarn pieceWarn = new PieceWarn(id, pieceId, userId, time, content);
				pieceWarnList.add(pieceWarn);
			}
			return pieceWarnList;
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
		return pieceWarnList;
	}

	@Override
	public int insert(PieceWarn pieceWarn) {
		int count = 0;
		String sql = "INSERT INTO Piece_warn " + "(piece_id,piece_warn_user_id,piece_warn_content) "
				+ "VALUES (?,?,?);";
		Connection connection = null;
		PreparedStatement ps = null;
		try {
			connection = DriverManager.getConnection(URL, USER, PASSWORD);
			ps = connection.prepareStatement(sql);
			ps.setInt(1, pieceWarn.getPieceId());
			ps.setInt(2, pieceWarn.getUserId());
			ps.setString(3, pieceWarn.getContent());
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
		String sql = "DELETE FROM Piece_warn WHERE piece_warn_id = ?;";
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
					// When a Statement object is closed,
					// its current ResultSet object is also closed
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

}
