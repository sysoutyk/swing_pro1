package scheduler;

import java.sql.Date;

public class MemberVo {
	private String username;
	private String password;
	private String email;
	private Date joindate;
	
	public MemberVo() {
		super();
		
	}

	public MemberVo(String username, String password, String email, Date joindate) {
		super();
		this.username = username;
		this.password = password;
		this.email = email;
		this.joindate = joindate;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Date getJoindate() {
		return joindate;
	}

	public void setJoindate(Date joindate) {
		this.joindate = joindate;
	}

	@Override
	public String toString() {
		return "MemberVo [username=" + username + ", password=" + password + ", email=" + email + ", joindate="
				+ joindate + "]";
	}

	
}
