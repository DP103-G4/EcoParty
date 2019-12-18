package tw.dp103g4.news;

import java.util.List;

public interface NewsDao {

	public List<News> getAll();
	
	public News getById();
	
	public int Insert(News news);
	
	public int Update(News news);
	
	public int deleteById(int id);
	
}
