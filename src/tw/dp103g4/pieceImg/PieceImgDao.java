package tw.dp103g4.pieceImg;

import java.util.List;

public interface PieceImgDao {
	public List<PieceImg> getAllByPiece(int pieceId);
	
	public int insert(int pieceId, byte[] data);
	
	public int delete(int id);
	
	public byte[] getImage(int id);
	
}
