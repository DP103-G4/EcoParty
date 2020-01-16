package tw.dp103g4.talk;
import java.sql.Timestamp;
public class NewestTalk {

	
		private int receiverId;
		private int senderId;
		private String content;
		private Timestamp newMsgTime;
		private String account;
		
		public NewestTalk(int senderId, String content, Timestamp newMsgTime, String account) {
			super();
			this.senderId = senderId;
			this.content = content;
			this.newMsgTime = newMsgTime;
			this.account = account;
		}
		
		public int getReceiverId() {
			return receiverId;
		}

		public void setReceiverId(int receiverId) {
			this.receiverId = receiverId;
		}

		public int getSenderId() {
			return senderId;
		}
		public void setSenderId(int senderId) {
			this.senderId = senderId;
		}
		public String getContent() {
			return content;
		}
		public void setContent(String content) {
			this.content = content;
		}
		public Timestamp getNewMsgTime() {
			return newMsgTime;
		}
		public void setNewMsgTime(Timestamp newMsgTime) {
			this.newMsgTime = newMsgTime;
		}
		public String getAccount() {
			return account;
		}
		public void setAccount(String account) {
			this.account = account;
		}
		
	}

