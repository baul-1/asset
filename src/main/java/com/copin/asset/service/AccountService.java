package com.copin.asset.service;

import java.util.Map;

import org.mindrot.jbcrypt.BCrypt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.copin.asset.mapper.AccountMapper;

@Service
public class AccountService {
	
	private AccountMapper amapper;
	
	@Autowired
	public AccountService(AccountMapper ampper) {
		this.amapper = ampper;
	}
	
	
	
	public int accountLogin(String id, String password) {
		
		String pw = amapper.getidCheck(id);

		if( pw != null ) {
			
			boolean bPw = BCrypt.checkpw(password, pw);
			
			if(bPw == true) {
				return 3;
			}else {
				return 2;
			}
			
		}else {
			return 1;
		}
	}
	
	public Map<String, Object> getAccount(String id) {
		return amapper.getAccount(id, amapper.getidCheck(id));
	}
	
	
	

}
