
package com.cg.mypaymentapp.service;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

import com.cg.mypaymentapp.beans.Customer;
import com.cg.mypaymentapp.beans.Wallet;
import com.cg.mypaymentapp.exception.InsufficientBalanceException;
import com.cg.mypaymentapp.exception.InvalidInputException;
import com.cg.mypaymentapp.repo.WalletRepo;
import com.cg.mypaymentapp.repo.WalletRepoImpl;


public class WalletServiceImpl implements WalletService{

	WalletRepo repo;

	public WalletServiceImpl() {
		repo = new WalletRepoImpl();
	}


	Map<String, Customer> data = new HashMap<>();

	public  Customer createAccount(String name, String mobileno,BigDecimal amount) {

		acceptDetails(name,mobileno,amount);
		Wallet wallet = new Wallet();
		wallet.setBalance(amount);

		Customer customer = new Customer();
		customer.setName(name);
		customer.setMobileNo(mobileno);
		customer.setWallet(wallet);

		if(repo.save(customer))
			return customer;

		return null;
	}

	public Customer showBalance(String mobileNo) 
	{	
		Customer customer=repo.findOne(mobileNo);
		if(customer!=null)
			return customer;
		else
			throw new InvalidInputException("Invalid mobile no ");
	}

	public Customer fundTransfer(String sourceMobileNo, String targetMobileNo, BigDecimal amount) {

		//System.out.println("Inside fund transfer service");
		Customer customer = withdrawAmount(sourceMobileNo, amount);
		depositAmount(targetMobileNo, amount);

		return customer;
	}

	public Customer depositAmount(String mobileNo, BigDecimal amount) {
		Customer customer = repo.findOne(mobileNo);
		repo.remove(mobileNo);
		Wallet wallet = customer.getWallet();
		wallet.setBalance(wallet.getBalance().add(amount));
		customer.setWallet(wallet);

		if(repo.save(customer)) 
			return customer;

		return null;
	}

	public Customer withdrawAmount(String mobileNo, BigDecimal amount) throws InvalidInputException{
		Customer customer = repo.findOne(mobileNo);
		repo.remove(mobileNo);
		Wallet wallet = customer.getWallet();
		if(amount.compareTo(wallet.getBalance()) > 0) 
			throw new InvalidInputException("Not enough amount");
		wallet.setBalance(wallet.getBalance().subtract(amount));
		customer.setWallet(wallet);

		if(repo.save(customer)) 
			return customer;

		return null;
	}
	public boolean validateName(String Name)
	{
		String pattern="[A-Z][a-z]*";
		if(Name.matches(pattern))
		{
			return true;
		}else return false;
	}

	public boolean validatePhoneNumber(String mobileno){
		String pattern="[7-9]?[0-9]{10}";
		if(mobileno.matches(pattern))
		{
			return true;
		}else return false;

	}

	public void acceptDetails(String name,String mobileno,BigDecimal amount)
	{
		Scanner scan=new Scanner(System.in);
		while(true)
		{
			String str=mobileno;
			if(validatePhoneNumber(str)){
				break;
			}
			else 
				System.out.println("wrong entry");
			System.out.println("Enter mobile number again:");
			mobileno=scan.next();

		}



		while(true){
			String str=name;
			if(validateName(str)){
				break;
			}
			else 
				System.out.println("wrong entry");
			System.out.println("Enter name again:");

			name=scan.next();

		}



	}

	public WalletServiceImpl(Map<String, Customer> data) {
		super();
		this.data = data;
	}}
