/*  Name: Cooper Urich 
 *  Course: CNT 4714Spring 2021Assignment 
 *  title: Project2–Synchronized, Cooperating ThreadsUnder Locking
 *  Due Date:February 14, 2021
 */

import java.util.*;

public class Threads {

	public static void main(String[] args) {
		// Make new bank account
		BankAccount account = new BankAccount();
		
		// Make the Deposit threads 
		Bank_Deposit_Threads d1 = new Bank_Deposit_Threads(account);
		Bank_Deposit_Threads d2 = new Bank_Deposit_Threads(account);
		Bank_Deposit_Threads d3 = new Bank_Deposit_Threads(account);
		Bank_Deposit_Threads d4 = new Bank_Deposit_Threads(account);
		Bank_Deposit_Threads d5 = new Bank_Deposit_Threads(account);
		
		
		// Make the Withdrawal threads 
		Bank_Withdrawal_Threads w1 = new Bank_Withdrawal_Threads(account);
		Bank_Withdrawal_Threads w2 = new Bank_Withdrawal_Threads(account);
		Bank_Withdrawal_Threads w3 = new Bank_Withdrawal_Threads(account);
		Bank_Withdrawal_Threads w4 = new Bank_Withdrawal_Threads(account);
		Bank_Withdrawal_Threads w5 = new Bank_Withdrawal_Threads(account);
		Bank_Withdrawal_Threads w6 = new Bank_Withdrawal_Threads(account);
		Bank_Withdrawal_Threads w7 = new Bank_Withdrawal_Threads(account);
		Bank_Withdrawal_Threads w8 = new Bank_Withdrawal_Threads(account);
		Bank_Withdrawal_Threads w9 = new Bank_Withdrawal_Threads(account);
		
		// Name the deposit threads (d1 - d5)
		d1.setName("d1");
		d2.setName("d2"); 
		d3.setName("d3");
		d4.setName("d4");
		d5.setName("d5");
		
		
		// Name 9 withdrawal threads (w1 - w9)
		w1.setName("w1");
		w2.setName("w2");
		w3.setName("w3");
		w4.setName("w4");
		w5.setName("w5");
		w6.setName("w6");
		w7.setName("w7");
		w8.setName("w8");
		w9.setName("w9");
		
		// Print out the header information
		println("Deposit Threads\t\t\tWithdrawl Threads\t\t     Balance     \t\t\t");
		println("---------------\t\t\t-----------------\t\t-----------------\t\t\t"); 

		// start() is built in method for threads that begins running each thread
		
		// Start and run the threads
		d1.start();
		// Run more withdraw threads so the account can be populated at first
		w1.start();
		w2.start();
		w3.start();
		w4.start();
		// Second Deposit Thread
		d2.start();
		// Start the next few deposit threads
		w5.start();
		w6.start();
		// Third Deposit Thread
		d3.start();
		// Fourth Deposit Thread
		d4.start();
		
		w7.start();
		w8.start();
		// Fifth Deposit Thread		
		d5.start();

	
		w9.start();	
		
	}
	
	// print methods for cleaner looking print statements
	public static void print(String s) {
		System.out.print(s);
	}
	public static void println(String s) {
		System.out.println(s);
	}
	

}

