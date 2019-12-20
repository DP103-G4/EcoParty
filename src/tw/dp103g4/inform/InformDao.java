package tw.dp103g4.inform;

import java.util.List;

public interface InformDao {
	public List<Inform> getAllbyReceiver(int receiverId);
	
	public int insert(Inform inform);
	
	public int delete(int id);
	
	public int setRead(int id);
}
