package tw.dp103g4.partyPiece;

import static tw.dp103g4.main.Common.*;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import tw.dp103g4.party.Party;
import tw.dp103g4.party.PartyInfo;


public class PartyPieceDaoImpl implements PartyPieceDao {

	public PartyPieceDaoImpl() {
		super();
		try {
			Class.forName(CLASS_NAME);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}

	@Override
	public List<PieceInfo> getAllByParty(int partyId) {
		String sql = "select user_name, piece_id, u.user_id, piece_content, piece_time "
				+ "from Party_piece pp join User u on pp.user_id = u.user_id "
				+ "where party_id = ? "
				+ "order by piece_time desc;";
		List<PieceInfo> pieceInfoList = new ArrayList<PieceInfo>();
		try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
				PreparedStatement ps = connection.prepareStatement(sql);) {
			ps.setInt(1, partyId);
			try (ResultSet rs = ps.executeQuery()) {
				while (rs.next()) {
					String name = rs.getString(1);
					int id = rs.getInt(2);
					int userId = rs.getInt(3);
					String content = rs.getString(4);
					Date time = rs.getTimestamp(5);
					PartyPiece partyPiece = new PartyPiece(id, userId, partyId, content, time);
					PieceInfo pieceInfo = new PieceInfo(partyPiece, name);
					
					pieceInfoList.add(pieceInfo);
				}
			}
			return pieceInfoList;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return pieceInfoList;
	}

	@Override
	public int insert(PartyPiece partyPiece) {
		int count = 0;
		int ai = 0;
		String sql = "insert into Party_piece (user_id, party_id, piece_content) value (?, ?, ?);";
		Connection connection = null;
		PreparedStatement ps = null;
		try {
			connection = DriverManager.getConnection(URL, USER, PASSWORD);
			ps = connection.prepareStatement(sql);
			ps.setInt(1, partyPiece.getUserId());
			ps.setInt(2, partyPiece.getPartyId());
			ps.setString(3, partyPiece.getContent());
			count = ps.executeUpdate();
			if (count != 0) {
				sql = "SELECT LAST_INSERT_ID() " + 
						"	FROM  INFORMATION_SCHEMA.TABLES  " + 
						"	WHERE TABLE_SCHEMA = 'EcoParty'  " + 
						"		AND   TABLE_NAME   = 'Party_piece';";
				ps = connection.prepareStatement(sql);
				
				ResultSet rs = ps.executeQuery();
				if (rs.next()) {
					count = rs.getInt(1);
				}
			
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
		return count;
	}

	@Override
	public int update(PartyPiece partyPiece) {
		int count = 0;
		String sql = "update Party_piece set user_id = ?, party_id = ?, piece_content = ? where piece_id = ?;";
		try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
				PreparedStatement ps = connection.prepareStatement(sql)) {
			ps.setInt(1, partyPiece.getUserId());
			ps.setInt(2, partyPiece.getPartyId());
			ps.setString(3, partyPiece.getContent());
			count = ps.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return count;
	}

	@Override
	public int deleteOne(int id) {
		int count = 0;
		String sql = "delete from Piece_warn where piece_id = ?";
		Connection connection = null;
		PreparedStatement ps = null;
		try {
			connection = DriverManager.getConnection(URL, USER, PASSWORD);
			ps = connection.prepareStatement(sql);
			ps.setInt(1, id);
			ps.executeUpdate();
			
			sql = "delete from Piece_img where piece_id = ?";
			ps = connection.prepareStatement(sql);
			ps.setInt(1, id);
			count = ps.executeUpdate();
			
			sql = "delete from Party_piece where piece_id = ?";
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
	public PartyPiece getOneById(int pieceMsgId) {
		String sql = "select user_account, u.user_id, piece_content, piece_time " + 
				"from Party_piece pp " + 
				"join User u on pp.user_id = u.user_id " + 
				"where piece_id = ?;";
		PartyPiece partyPiece = null;
		try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
				PreparedStatement ps = connection.prepareStatement(sql);) {
			ps.setInt(1, pieceMsgId);
			try (ResultSet rs = ps.executeQuery()) {
				while (rs.next()) {
					String account = rs.getString(1);
					int userId = rs.getInt(2);
					String content = rs.getString(3);
					Date time = rs.getTimestamp(4);
					partyPiece = new PartyPiece(userId ,content, time, account);
					
				}
			}
			return partyPiece;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return partyPiece;
	}

	@Override
	public int delete(int id) {
		// TODO Auto-generated method stub
		return 0;
	}

}
