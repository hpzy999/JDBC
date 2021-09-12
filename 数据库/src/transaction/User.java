package transaction;

import java.math.BigDecimal;

public class User {
	private String id;
	private String name;
	private BigDecimal balance;

	public User() {
		super();
	}

	public User(String id, String name, BigDecimal balance) {
		super();
		this.id = id;
		this.name = name;
		this.balance = balance;
	}

	public String getUser() {
		return id;
	}

	public void setUser(String id) {
		this.id = id;
	}

	public String getPassword() {
		return name;
	}

	public void setPassword(String name) {
		this.name = name;
	}

	public BigDecimal getBalance() {
		return balance;
	}

	public void setBalance(BigDecimal balance) {
		this.balance = balance;
	}

	@Override
	public String toString() {
		return "User [id=" + id + ", name=" + name + ", balance=" + balance + "]";
	}

}
