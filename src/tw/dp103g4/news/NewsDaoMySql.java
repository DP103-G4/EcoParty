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
		String sql = "select news_id, news_title, news_content, news_time from News order by news_time desc;";
		List<News> newsList = new ArrayList<News>();
		try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
				PreparedStatement ps = connection.prepareStatement(sql);) {
			try (ResultSet rs = ps.executeQuery();) {
				while (rs.next()) {
					int id = rs.getInt(1);
					String title = rs.getString(2);
					String content = rs.getString(3);
					Date time = rs.getDate(4);
					News news = new News(id, title, content, time);
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
	public News getById(int id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public byte[] getImageById(int id) {
		String sql = "select news_img from News where news_id = ?;";
		byte[] image = null;
		try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
				PreparedStatement ps = connection.prepareStatement(sql);) {
			ps.setInt(1, id);
			try (ResultSet rs = ps.executeQuery();) {
				if (rs.next()) {
					image = rs.getBytes(1);
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return image;
	}

	@Override
	public int insert(News news, byte[] image) {
		int count = 0;
		String sql = "insert into News (news_title, news_content, news_img) value (?, ?, ?);";
		try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
				PreparedStatement ps = connection.prepareStatement(sql);) {
			ps.setString(1, news.getTitle());
			ps.setString(2, news.getContent());
			ps.setBytes(3, image);
			count = ps.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return count;
	}
	
	@Override
	public int update(News news, byte[] image) {
		int count = 0;
		String sql = "";
		if (image != null) {
			sql = "update News set news_title = ?, news_content = ? news_img = ? where news_id = ?;";
		} else {
			sql = "update News set news_title = ?, news_content = ? where news_id = ?;";
		}

		try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
				PreparedStatement ps = connection.prepareStatement(sql)) {

			ps.setString(1, news.getTitle());
			ps.setString(2, news.getContent());
			if (image != null) {
				ps.setBytes(3, image);
				ps.setInt(4, news.getId());
			} else {
				ps.setInt(3, news.getId());
			}
			count = ps.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return count;
	}
	
	public int delete(int id) {
		int count = 0;
		String sql = "delete from News where news_id = ?;";
		try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
				PreparedStatement ps = connection.prepareStatement(sql)) {
			ps.setInt(1, id);
			count = ps.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return count;
	}

}
