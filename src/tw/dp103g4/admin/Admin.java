package tw.dp103g4.admin;

public class Admin {

	private int adminId;
	private String adminAccount = "";
	private String adminPassword = "";

	

	public Admin(int adminId, String adminAccount, String adminPassword) {
		super();
		this.adminId = adminId;
		this.adminAccount = adminAccount;
		this.adminPassword = adminPassword;
	}


	public Admin(String adminAccount, String adminPassword) {
		super();
		this.adminAccount = adminAccount;
		this.adminPassword = adminPassword;
	}

	
	@Override
	public boolean equals(Object obj) {
		if (obj == null || !(obj instanceof Admin)) {
			return false;
		} else {
			Admin admin = (Admin) obj;
			return this.adminAccount.equals(admin.adminAccount);
		}
	}


	


	public int getAdminId() {
		return adminId;
	}


	public void setAdminId(int adminId) {
		this.adminId = adminId;
	}


	public String getAdminAccount() {
		return adminAccount;
	}


	public void setAdminAccount(String adminAccount) {
		this.adminAccount = adminAccount;
	}


	public String getAdminPassword() {
		return adminPassword;
	}


	public void setAdminPassword(String adminPassword) {
		this.adminPassword = adminPassword;
	}




}
