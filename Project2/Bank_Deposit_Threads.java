/*  Name: Cooper Urich 
 *  Course: CNT 4714Spring 2021Assignment 
 *  title: Project2–Synchronized, Cooperating ThreadsUnder Locking
 *  Due Date:February 14, 2021
 */

import java.util.Random;


public class Bank_Deposit_Threads  extends Thread {
	private BankAccount account;
	
	public Bank_Deposit_Threads(BankAccount ActiveThread){
		account = ActiveThread;
	}
	
	public void run() { 
		int num;
		String name;
		while (true) {
			name = Thread.currentThread().getName();
			account.deposit(name);
			try {
				// sleep for a random number of seconds
				Thread.sleep((long)(Math.random() * 500));
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} 
		}
	}
}
