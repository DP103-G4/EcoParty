package tw.dp103g4.news;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import static tw.dp103g4.main.Common.*;

public class NewsDaoMySql implements NewsDao {

	public NewsDaoMySql() {
		super();
		try {
			Class.forName(CLASS_NAME);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}

	@Override
	public List<News> getAll() {
		String sql = "select news_id, news_title, news_content, news_time from Inform order by news_time desc;";
		List<News> newsList = new ArrayList<News>();
		try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
				PreparedStatement ps = connection.prepareStatement(sql);) {
			try (ResultSet rs = ps.executeQuery();) {
				while(rs.next()) {
					int news_id = rs.getInt(1);
					String news_title = rs.getString(2);
					String news_content = rs.getString(3);
					Date news_time = rs.getDate(4);
					News news = new News(news_id, news_title, news_content, news_time);
					newsList.add(news);
				}
			}
			return newsList;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return newsList;
	}

	@Override
	public News getById() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int Insert(News news) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int Update(News news) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int deleteById(int id) {
		// TODO Auto-generated method stub
		return 0;
	}

}
