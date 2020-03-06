package tw.dp103g4.iccTable;

import static tw.dp103g4.main.Common.PASSWORD;
import static tw.dp103g4.main.Common.URL;
import static tw.dp103g4.main.Common.USER;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import tw.dp103g4.participant.Participant;
import tw.dp103g4.participant.ParticipantInfo;
import tw.dp103g4.party.Party;
import tw.dp103g4.party.PartyInfo;

public class IccTableDaoImpl implements IccTableDao {

	@Override
	public List<IccTableInfo> getAllByParty(int partyId) {
		List<IccTableInfo> iccTableInfos = new ArrayList<IccTableInfo>();
		
		String sql = "SELECT u.user_name, i.* FROM IccTable i " 
				+ "join User u on u.user_id = i.user_id where party_id = ?;";
		Connection connection = null;
		PreparedStatement ps = null;
		try {
			connection = DriverManager.getConnection(URL, USER, PASSWORD);
			ps = connection.prepareStatement(sql);
			ps.setInt(1, partyId);
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				String name = rs.getString(1);
				int userId = rs.getInt(2);
				partyId = rs.getInt(3);
				Double weight = rs.getDouble(4);
				int plastic01 = rs.getInt(5);
				int plastic02 = rs.getInt(6);
				int plastic03 = rs.getInt(7);
				int plastic04 = rs.getInt(8);
				int plasticBag01 = rs.getInt(9);
				int plasticBag02 = rs.getInt(10);
				int washless01 = rs.getInt(11);
				int washless02 = rs.getInt(12);
				int washless03 = rs.getInt(13);
				int others01 = rs.getInt(14);
				int others02 = rs.getInt(15);
				int others03 = rs.getInt(16);
				int fishery01 = rs.getInt(17);
				int fishery02 = rs.getInt(18);
				int fishery03 = rs.getInt(19);
				int personal01 = rs.getInt(20);
				int personal02 = rs.getInt(21);
				int smoke01 = rs.getInt(22);
				int smoke02 = rs.getInt(23);
				int care01 = rs.getInt(24);
				int care02 = rs.getInt(25);
				int care03 = rs.getInt(26);
				int care04 = rs.getInt(27);
				String care01Name = rs.getString(28);
				String care02Name = rs.getString(29);
				String care03Name = rs.getString(30);
				String care04Name = rs.getString(31);

				
				IccTable iccTable = new IccTable(userId, partyId, weight, plastic01, plastic02, plastic03, plastic04, plasticBag01, plasticBag02, washless01, washless02, washless03, others01, others02, others03, fishery01, fishery02, fishery03, personal01, personal02, smoke01, smoke02, care01, care02, care03, care04, care01Name, care02Name, care03Name, care04Name);
				int count = plastic01 + plastic02 + plastic03 + plastic04
						+ plasticBag01 + plasticBag02
						+ washless01 + washless02 + washless03
						+ others01 + others02 +others03
						+ fishery01 + fishery02 + fishery03
						+ personal01 + personal02
						+ smoke01 + smoke02
						+ care01 + care02 + care03 + care04;
				IccTableInfo iccTableInfo = new IccTableInfo(iccTable, name, count);
				iccTableInfos.add(iccTableInfo);
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
		
		return iccTableInfos;
	}

	@Override
	public int insert(int userId, int partyId) {
		int count = 0;
		String sql;
		sql = "INSERT INTO IccTable(user_id, party_id) VALUES(?, ?);";
		Connection connection = null;
		PreparedStatement ps = null;
		
		try {
			connection = DriverManager.getConnection(URL, USER, PASSWORD);
			ps = connection.prepareStatement(sql);
			ps.setInt(1, userId);
			ps.setInt(2, partyId);
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
	public int update(IccTable iccTable) {
		int count = 0;
		String sql = "UPDATE IccTable SET "
				+ "plastic_01 = ?, plastic_02 = ?, plastic_03 = ?, plastic_04 = ?, "
				+ "plastic_bag_01 = ?, plastic_bag_02 = ?, "
				+ "washless_01 = ?, washless_02 = ?, washless_03 = ?, "
				+ "others_01 = ?, others_02 = ?, others_03 = ?, "
				+ "fishery_01 = ?, fishery_02 = ?, fishery_03 = ?, "
				+ "personal_01 = ?, personal_02 = ?, smoke_01 = ?, smoke_02 = ?, "
				+ "care_01 = ?, care_02 = ?, care_03 = ?, care_04 = ?, "
				+ "weight = ?, "
				+ "care_01_name = ?, care_02_name = ?, care_03_name = ?, care_04_name = ? "
				+ "where user_id = ? and party_id = ?;";
		Connection connection = null;
		PreparedStatement ps = null;
		try {
			connection = DriverManager.getConnection(URL, USER, PASSWORD);
			ps = connection.prepareStatement(sql);
			int index = 1;
			
			while (index <= 23) {
				ps.setInt(index, (int)iccTable.getIcc(index-1));
				index++;
			}
			
			ps.setDouble(24, (Double)iccTable.getIcc(23));
			ps.setString(25, (String)iccTable.getIccName(19));
			ps.setString(26, (String)iccTable.getIccName(20));
			ps.setString(27, (String)iccTable.getIccName(21));
			ps.setString(28, (String)iccTable.getIccName(22));
			ps.setInt(29, iccTable.getUserId());
			ps.setInt(30, iccTable.getPartyId());

			count = ps.executeUpdate();
			
		} catch (Exception e) {
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
	public int delete(int userId, int partyId) {
		int count = 0;
		String sql;
		sql = "delete from IccTable where user_id = ? and party_id = ?";
		Connection connection = null;
		PreparedStatement ps = null;
		
		try {
			connection = DriverManager.getConnection(URL, USER, PASSWORD);
			ps = connection.prepareStatement(sql);
			ps.setInt(1, userId);
			ps.setInt(2, partyId);
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
	public IccTableInfo findById(int userId, int partyId) {
		IccTable iccTable = null;
		IccTableInfo iccTableInfo = null;
		
		String sql = "SELECT u.user_name, i.* FROM IccTable i " 
				+ "join User u on u.user_id = i.user_id where u.user_id = ? and i.party_id = ?;";
		Connection connection = null;
		PreparedStatement ps = null;
		try {
			connection = DriverManager.getConnection(URL, USER, PASSWORD);
			ps = connection.prepareStatement(sql);
			ps.setInt(1, userId);
			ps.setInt(2, partyId);
			ResultSet rs = ps.executeQuery();
			if (rs.next()) {
				String name = rs.getString(1);
				userId = rs.getInt(2);
				partyId = rs.getInt(3);
				Double weight = rs.getDouble(4);
				int plastic01 = rs.getInt(5);
				int plastic02 = rs.getInt(6);
				int plastic03 = rs.getInt(7);
				int plastic04 = rs.getInt(8);
				int plasticBag01 = rs.getInt(9);
				int plasticBag02 = rs.getInt(10);
				int washless01 = rs.getInt(11);
				int washless02 = rs.getInt(12);
				int washless03 = rs.getInt(13);
				int others01 = rs.getInt(14);
				int others02 = rs.getInt(15);
				int others03 = rs.getInt(16);
				int fishery01 = rs.getInt(17);
				int fishery02 = rs.getInt(18);
				int fishery03 = rs.getInt(19);
				int personal01 = rs.getInt(20);
				int personal02 = rs.getInt(21);
				int smoke01 = rs.getInt(22);
				int smoke02 = rs.getInt(23);
				int care01 = rs.getInt(24);
				int care02 = rs.getInt(25);
				int care03 = rs.getInt(26);
				int care04 = rs.getInt(27);
				String care01Name = rs.getString(28);
				String care02Name = rs.getString(29);
				String care03Name = rs.getString(30);
				String care04Name = rs.getString(31);
				
				iccTable = new IccTable(userId, partyId, weight, plastic01, plastic02, plastic03, plastic04, plasticBag01, plasticBag02, washless01, washless02, washless03, others01, others02, others03, fishery01, fishery02, fishery03, personal01, personal02, smoke01, smoke02, care01, care02, care03, care04, care01Name, care02Name, care03Name, care04Name);
				int count = plastic01 + plastic02 + plastic03 + plastic04
						+ plasticBag01 + plasticBag02
						+ washless01 + washless02 + washless03
						+ others01 + others02 +others03
						+ fishery01 + fishery02 + fishery03
						+ personal01 + personal02
						+ smoke01 + smoke02
						+ care01 + care02 + care03 + care04;
				iccTableInfo = new IccTableInfo(iccTable, name, count);
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
		
		return iccTableInfo;
	}

}
