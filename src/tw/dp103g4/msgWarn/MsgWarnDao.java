package tw.dp103g4.msgWarn;

import java.util.List;

public interface MsgWarnDao {
	List<MsgWarn> getAll();
//	int getUserId(int id);
	int insert(MsgWarn msgWarn);
	int delete(int id);
	int deleteMsg(int id,int msgId);
	
}
