
package com.cg.mypaymentapp.pl;

import java.math.BigDecimal;
import java.util.Scanner;

import com.cg.mypaymentapp.beans.Customer;
import com.cg.mypaymentapp.beans.Wallet;
import com.cg.mypaymentapp.service.WalletService;
import com.cg.mypaymentapp.service.WalletServiceImpl;

public class Client {
	public static void main( String[] args ){
		WalletService service=new WalletServiceImpl();
		Scanner sc = new Scanner(System.in);
		String ans;
		int no;
		do{

			System.out.println("******");
			System.out.println("1.Create Account");
			System.out.println("2.display acc details");
			System.out.println("3.Deposit");
			System.out.println("4.Withdraw");
			System.out.println("5.Fund Transfer");
			System.out.println("6.Print Transactions");
			System.out.println("please enter your choice: ");
			no=sc.nextInt();

			switch(no){

			case 1:
				System.out.println("Enter Account Details");

				System.out.println("Enter Name:");
				String Name=sc.next();

				System.out.println("Enter phonenumber:");
				String phnNo=sc.next();

				System.out.println("Enter Balance");
				BigDecimal balance=sc.nextBigDecimal();

				Customer customer = service.createAccount(Name,phnNo,balance);

				if(customer != null) {
					System.out.println("The details have successfully registered by");

					System.out.println("Name\t : " + customer.getName());
					System.out.println("PhoneNumber : " + customer.getMobileNo());
					Wallet wallet = customer.getWallet();

					System.out.println("Balance\t :" + wallet.getBalance()); 
				}
				else System.out.println("Phone number has already registered");

				break;
			case 2:
				Scanner scan=new Scanner(System.in);

				System.out.println("Enter phonenumber:");
				String phnNo1=sc.next();

				customer = service.showBalance(phnNo1);
				Wallet wallet1 = customer.getWallet();
				
				System.out.println("CustomerName: " + customer.getName());
				System.out.println("balance: " + wallet1.getBalance());
				
				break;
			case 3:
				System.out.println("Enter phonenumber:");
				String mobileNo = sc.next();
				System.out.println("enter the amount to be deposited");
				BigDecimal amount = sc.nextBigDecimal();

				customer = service.depositAmount(mobileNo, amount);

				System.out.println("Amount of Rs. " + amount + " successfully deposited to account " + customer.getName());
				break;
			case 4:
				System.out.println("Enter the phonenumber:");
				String phonenumber = sc.next();
				
				System.out.println("Enter the amount to be withdrawn");
				BigDecimal amount1 = sc.nextBigDecimal();
				
				customer = service.withdrawAmount(phonenumber, amount1);

				System.out.println("Amount of Rs. " + amount1 + " successfully withdrawn from account " + customer.getName());
				break;
			case 5: 
				System.out.println("enter source phonenumber:");
				String sourceMobileNo = sc.next();
				
				System.out.println("enter target phonenumber:");
				String targetMobileNo = sc.next();
				
				System.out.println("enter the amount to be transfered:");
				BigDecimal amount2 = sc.nextBigDecimal();
			
				customer = service.fundTransfer(sourceMobileNo, targetMobileNo, amount2);
				
				System.out.println("Amount of Rs. " + amount2 + " successfully transferred from account " + customer.getName());
				break;
				
			case 0:
				System.out.println("Thank you for using our services");
				System.out.println("Good Bye");
				System.exit(0);
				
			default:
				System.out.println("enter valid input");
				break;
			}
			System.out.println("Do you want to continue yes?no");
			ans=sc.next();
		}while(ans.equals("Yes")||ans.equals("y")||ans.equals("yes"));
	}
}

