/*  Name: Cooper Urich 
 *  Course: CNT 4714Spring 2021Assignment 
 *  title: Project2–Synchronized, Cooperating ThreadsUnder Locking
 *  Due Date:February 14, 2021
 */

import java.util.*;
import java.util.concurrent.locks.*;

public class BankAccount {
	public int BankAccountBalance;
	private Lock lock;
	
	
	public BankAccount() {
		BankAccountBalance = 0;
		// Reentrant lock per Project Guidelines
		lock = new ReentrantLock();
	}
	
	public void deposit(String s) {
		int num = randDeposit();
		
		// using locks to ensure threads are synchronized (per assignment guidelines)
		lock.lock();
		
		// add random number to balance
		BankAccountBalance += num;
		
		// print to console
		System.out.println("Thread " + s + " Deposits $ " + num + "   \t                 \t\t(+) Balance is $" + BankAccountBalance);
		// unlocking to keep synchronousness of threads
		lock.unlock();
	}

	public void withdrawal(String s) {
		int num = randWithdrawal();
		
		// using locks to ensure threads are synchronized
		lock.lock();
		if (BankAccountBalance - num >= 0) {
			BankAccountBalance -= num;
			System.out.println("\t\t            Thread " + s + " withdraws $ " + num + "\t\t(-) Balance is $" + BankAccountBalance);
			
		} else {
			System.out.println("\t\t            Thread " + s + " withdraws $ " + num + "\t\t(******)WITHDRAWAL BLOCKED - INSUFFICIENT FUNDS!!!");
		}
		// unlocking to keep synchronousness of threads
		lock.unlock();
	}

	// Gets random number for deposit
	public int randDeposit() {
		int max = 250;
		int min = 1;
		
		Random rand = new Random();
		return min + rand.nextInt(max);

	}
	// Gets random number for withdraw
	public int randWithdrawal() {
		int max = 50;
		int min = 1;
		
		Random rand = new Random();
		return min + rand.nextInt(max);
	}
}
