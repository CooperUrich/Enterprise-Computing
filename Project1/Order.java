import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.time.*;

public class Order {
	
	// Keeps track of the items full information
	// Name, Quantity, Price, Discount
	String[] fullItemInformation;
	String   view;
	String   returnedView = "";
	String   finalView = "";
	String   finalReturnedView = "";
	public double 	subtotal;
	public double 	total;
	public int 		maxItems;
	public int 		numItems;
	public int 		numTransactions = 0;
	public int 		number = 1;
	File transactions = new File("transactions.txt");
	ArrayList<String> orders = new ArrayList<String>();
	ArrayList<String> ordersFinal = new ArrayList<String>();
	StringBuilder finishOrder = new StringBuilder(); 
	SimpleDateFormat formatter= new SimpleDateFormat("DD/MM/YY, HH:MM:ss Z");
	Date date = new Date(System.currentTimeMillis());
	
	
	
	public String FinishOrderAndPrint() throws IOException {
		double taxAmount;
//		String date = formatter.format(new Date());
		finishOrder.append("Date: " + date + "\n\n");
		finishOrder.append("Number of line items: " + numTransactions + "\n\n");
		finishOrder.append("Item#/ID/Title/Price/Qty/Disc%/Subtotal\n\n");
		finishOrder.append(printOrder() + "\n\n");
		finishOrder.append("Subtotal:\t$" + String.format("%.2f", subtotal) + "\n\n");
		finishOrder.append("Tax rate:\t6%\n\n");
		taxAmount = total - subtotal;
		finishOrder.append("Tax Amount:\t$" + String.format("%.2f", taxAmount) + "\n\n");
		finishOrder.append("Order total:\t$" + String.format("%.2f", total) + "\n\n");
		finishOrder.append("Thanks for shopping at Nile Dot Com!");
		
		printFinalTransactionList();
		
		return finishOrder.toString();
	}
	// Fix this to account for everything for the final text file
	public void addToOrder(String s) {
		orders.add(number + ": " + s);
		this.number = number + 1;
	}
	
	// Call this one first
	public void addToFinalOrder(String s) {
		String id = "";
		int month = Calendar.getInstance().get(Calendar.MONTH) + 1;
		int day = Calendar.getInstance().get(Calendar.DAY_OF_MONTH);
		int year = Calendar.getInstance().get(Calendar.YEAR);
		int hour = Calendar.getInstance().get(Calendar.HOUR_OF_DAY);
		int min = Calendar.getInstance().get(Calendar.MINUTE);
//		String date = formatter.format(new Date());;
		
		if (month < 10) {
			id += Integer.toString(day) + "0" + Integer.toString(month) + Integer.toString(year) + Integer.toString(hour) + Integer.toString(min);
		}
		else {
			id += Integer.toString(day) + Integer.toString(month) + Integer.toString(year) + Integer.toString(hour) + Integer.toString(min);
		}
		
		
		ordersFinal.add(id + ", " + s + date);
	}
	
	public String printOrder() {
		
		returnedView = "";
		
		view = orders.toString();
		for (int i = 1; i < view.length() - 1; i++) {
				if (view.charAt(i) == ',') {
					
					returnedView += '\n';
					i++;
					
				}
				else {
					returnedView += view.charAt(i);
				}			
				
		}
//		System.out.println(returnedView);
		return returnedView;
	}
	
	public void printFinalTransactionList() throws IOException {
		
		if(transactions.exists() == false) {
			transactions.createNewFile();
		}
		
		
		PrintWriter FinalTransaction = new PrintWriter(new FileWriter(transactions, true));
		FinalTransaction.append(printOrderTransactions());
		
		FinalTransaction.close();
	}
	
	public String printOrderTransactions() {
		int count = 0;
		
		
		finalView = ordersFinal.toString();
		System.out.println("\n\n\n\n\n" + finalView);
		for (int i = 1; i < finalView.length() - 1; i++) {
				if (finalView.charAt(i) == ',') {
					count++;
					if (count % 8 == 0) {	
						finalReturnedView += "\n";
						i++;
					}
					else {
						finalReturnedView += ',';
					}
				}
				else {
					finalReturnedView += finalView.charAt(i);
				}			
				
		}
		System.out.println(finalReturnedView);
		return finalReturnedView;
	}


	public void setTransactions() {
		this.numTransactions++;
	}
	public int getTransactions() {
		return numTransactions;
	}
	
	// Getter and Setter for subtotal
	public double getSubtoal() {
		return subtotal;
	}
	public void setSubtotal(double subtotal) {
		this.subtotal = subtotal;
	}
	// Getters and Setters for Total (subtotal * 1.06)
	public double getTotal() {
		return subtotal * 1.06;
	}
	
	// dont know if this setter is necessary
	
	public void setTotal(double subtotal) {
		this.total = this.subtotal * 1.06;
	}
	public void setTotalItemsInOrder(int totalItems) {
		this.numItems = totalItems;
	}
	public int getTotalItemsInOrder() {
		return numItems;
	}
	public void setMaximumItemsInOrder(int maxItems) {
		this.maxItems = maxItems;
	}
	public int getMaximumItemsInOrder() {
		return maxItems;
	}
	
	
	
}
