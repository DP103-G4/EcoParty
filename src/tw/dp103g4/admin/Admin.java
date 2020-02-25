package tw.dp103g4.admin;





public class Admin {

	private int admin_id;
	private String admin_account;
	private String admin_password;
	
	
	
	
	
	public Admin(int admin_id, String admin_account, String admin_password) {
		super();
		this.admin_id = admin_id;
		this.admin_account = admin_account;
		this.admin_password = admin_password;
	}
	
	
	public Admin(int admin_id) {
		super();
		this.admin_id = admin_id;
		
	}
	
	public Admin(int admin_id, String admin_account) {
		super();
		this.admin_id = admin_id;
		this.admin_account = admin_account;
	}
	
	
	
	public int getAdmin_id() {
		return admin_id;
	}
	public void setAdmin_id(int admin_id) {
		this.admin_id = admin_id;
	}
	public String getAdmin_account() {
		return admin_account;
	}
	public void setAdmin_account(String admin_account) {
		this.admin_account = admin_account;
	}
	public String getAdmin_password() {
		return admin_password;
	}
	public void setAdmin_password(String admin_password) {
		this.admin_password = admin_password;
	}
	

}
