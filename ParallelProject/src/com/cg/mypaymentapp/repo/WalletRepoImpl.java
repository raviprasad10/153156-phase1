package com.cg.mypaymentapp.repo;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

import com.cg.mypaymentapp.beans.Customer;
import com.cg.mypaymentapp.beans.Wallet;

public class WalletRepoImpl implements WalletRepo{

	private Map<String, Customer> data; 

	
	public WalletRepoImpl() {
		data = new HashMap<>();
	}
	
	public boolean save(Customer customer) {
		if(data.get(customer.getMobileNo()) == null) {
			data.put(customer.getMobileNo(), customer);
			return true;
		}
		return false;
	}
	
	public void remove(String mobileNo) {
		data.remove(mobileNo);
	}

	public Customer findOne(String mobileNo) {
		if(data.get(mobileNo) != null)
			return data.get(mobileNo);
		return null;
	}
}
