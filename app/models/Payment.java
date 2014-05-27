package models;

import java.math.BigDecimal;


public class Payment {

	private final String userMail;
	private final String cardNumber;
	private final BigDecimal amount;

	public Payment(String cardNumber, BigDecimal amount, String userMail) {
		this.cardNumber = cardNumber;
		this.amount = amount;
		this.userMail = userMail;
		// TODO Auto-generated constructor stub
	}

	public String getUserMail() {
		return userMail;
	}

	public String getCardNumber() {
		return cardNumber;
	}

	public BigDecimal getAmount() {
		return amount;
	}

	@Override
	public String toString() {
		return "Payment [userMail=" + userMail + ", cardNumber="
				+ cardNumber + ", amount=" + amount + "]";
	}
	
	

}
