package tw.dp103g4.news;

import java.util.List;

public interface NewsDao {

	public List<News> getAll();
	
	public News getById(int id);
	
	public int insert(News news , byte[] image);
	
	public int update(News news , byte[] image);

	
	public int deleteById(int id);
	
	byte[] getImage(int id);
	
	public byte[] getImageById(int id);
	
}
