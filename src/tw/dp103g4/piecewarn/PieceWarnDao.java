package tw.dp103g4.piecewarn;

import java.util.List;

public interface PieceWarnDao {
	List<PieceWarn> getAll();
//	int getUserId(int id);
	int inster(PieceWarn pieceWarn);
	int delete(int id);

}
