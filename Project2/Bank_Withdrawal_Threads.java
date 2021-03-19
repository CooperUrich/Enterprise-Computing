/*  Name: Cooper Urich 
 *  Course: CNT 4714Spring 2021Assignment 
 *  title: Project2–Synchronized, Cooperating ThreadsUnder Locking
 *  Due Date:February 14, 2021
 */

import java.util.Random;

public class Bank_Withdrawal_Threads  extends Thread {
	private BankAccount account;
	
	public Bank_Withdrawal_Threads(BankAccount ActiveThread){
		account = ActiveThread;
	}
	
	
	// run 
	public void run() {
		String name;
		while (true) {
			
			// get name of the thread
			name = Thread.currentThread().getName();
			
			// pass name to withdrawal thread
			account.withdrawal(name);
			try {
				// sleep for a random number of seconds
				Thread.sleep((long)(Math.random() * 100));
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	
	}
}
