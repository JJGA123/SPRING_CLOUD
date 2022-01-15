package com.test.userservice.errors;

public class AccountNotAssociatedException extends RuntimeException {
	
	public AccountNotAssociatedException() {
		super("The account does not belong to this user.");
	}

	private static final long serialVersionUID = 1L;
	
	

}
