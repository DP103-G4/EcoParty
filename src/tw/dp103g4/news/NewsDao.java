package tw.dp103g4.news;

import java.util.List;

public interface NewsDao {

	public List<News> getAll();
	
	public News getById(int id);
	
<<<<<<< HEAD
	public int insert(News news , byte[] image);
	
	public int update(News news , byte[] image);
=======
	public int insert(News news, byte[] image);
	
	public int update(News news, byte[] image);
>>>>>>> 2160f58577dcb30ca87a9ba7ddc75cbc71534e1c
	
	public int delete(int id);
	
	byte[] getImage(int id);
	
	public byte[] getImageById(int id);
	
}
