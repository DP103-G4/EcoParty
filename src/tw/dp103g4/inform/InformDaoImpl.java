package tw.dp103g4.inform;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import tw.dp103g4.news.News;

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
		String sql = "select inform_id, user_id, party_id, inform_time, inform_content, "
				+ "inform_isRead from Inform order by inform_time desc;";
		List<Inform> informList = new ArrayList<Inform>();
		try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
				PreparedStatement ps = connection.prepareStatement(sql);) {
			try (ResultSet rs = ps.executeQuery();) {
				while (rs.next()) {
					int id = rs.getInt(1);
					int userId = rs.getInt(2);
					int partyId = rs.getInt(3);
					Date time = rs.getDate(4);
					String content = rs.getString(5);
					boolean isRead = rs.getBoolean(6);
					Inform inform = new Inform(id, userId, partyId, time, content, isRead);
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
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int delete(int id) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int setRead(int id) {
		// TODO Auto-generated method stub
		return 0;
	}

}
