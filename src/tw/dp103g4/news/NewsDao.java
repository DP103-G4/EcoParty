package tw.dp103g4.news;

import java.util.List;

public interface NewsDao {

	public List<News> getAll();
	
	public News getById();
	
	public int insert(News news , byte[] image);
	
	public int update(News news , byte[] image);
	
	public int delete(int id);
	
	byte[] getImage(int id);
	
}
