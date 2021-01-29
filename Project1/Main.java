/* Name: Cooper Urich
 * Course: CNT 4714 - Spring 2021
 * Assignment title: Project 1
 * Date: January 19th 2021
 
 */
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.DecimalFormat;

import javax.swing.*;
import java.util.*;

public class Main extends JFrame {
	public int totalOrders = 0;
	public int totalItems  = 0;
	public int maxItems;
	public int temp;
	public boolean inStock;
	public double subtotal = 0;
	private ArrayList<Item> inventory;
	private Order order = new Order();

	
	// Making the Text Fields, buttons, and labels for the GUI
	public JLabel subTotalLabel 		= new JLabel ("Order Subtotal for 0 item(s):");	
	public JLabel IDLabel 				= new JLabel ("Enter Item ID for Item #1:"   );	
	public JLabel itemQuantityLabel 	= new JLabel ("Enter Quantity for Item #1:"  );	
	public JLabel infoLabel 			= new JLabel ("Item #1 Info:"                );
	private JButton processButton 		= new JButton("Process Item #1"              );
	private JButton confirmButton 		= new JButton("Confirm Item #1"              );
	private JButton viewButton 			= new JButton("View Order"                   );
	private JButton finishButton 		= new JButton("Finish Order"				 );
	private JButton newOrderButtob 		= new JButton("New Order"					 );
	private JButton exitButton 			= new JButton("Exit"						 );
	private JTextField MaxItemsInOrder	= new JTextField();
	private JTextField ItemID 			= new JTextField();
	private JTextField ItemQuantity 	= new JTextField();
	private JTextField ItemInformation	= new JTextField();
	private JTextField OrderSubtotal 	= new JTextField();
	
	
	public Main() throws FileNotFoundException 
	{
		this.ReadAndProcessFile();
		// add them the labels and inputs one at a time
		// add one label and then one input field so it creates a nice looking grid
		JPanel EnterFields = new JPanel(new GridLayout(5,2));
		EnterFields.add(new JLabel("Enter number of items in this order:"));
		EnterFields.add(MaxItemsInOrder  );
		EnterFields.add(IDLabel			 );
		EnterFields.add(ItemID			 );
		EnterFields.add(itemQuantityLabel);
		EnterFields.add(ItemQuantity	 );
		EnterFields.add(infoLabel		 );
		EnterFields.add(ItemInformation	 );
		EnterFields.add(subTotalLabel	 );
		EnterFields.add(OrderSubtotal	 );
		
		
		
		//panel Buttons holds the six buttons
		JPanel Buttons = new JPanel(new FlowLayout(FlowLayout.CENTER));
		Buttons.add(processButton );
		Buttons.add(confirmButton );
		Buttons.add(viewButton	  );
		Buttons.add(finishButton  );
		Buttons.add(newOrderButtob);
		Buttons.add(exitButton	  );
		
		//update button text
//		this.processButton.setText("Process Item #" + totalOrders);
		//this.confirmButton.setText("Confirm Item #" + totalOrders);
		
		// Buttons are initially disables because they cannot confirm or finish anything before
		// first entry
		this.confirmButton.setEnabled(false);
		this.finishButton.setEnabled(false);
		this.viewButton.setEnabled(true);
		
		// these text fields only show the total and information, it is not editable
		this.OrderSubtotal.setEnabled(false);
		this.ItemInformation.setEnabled(false);
		
		//add the panels to the main frame
		add(EnterFields, BorderLayout.NORTH);
		add(Buttons, BorderLayout.SOUTH);
		
		
		//actionlisteners for all buttons
		processButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				
				double totalPrice = 0;
				String ID 		  = ItemID.getText();
				int max 		  = Integer.parseInt(MaxItemsInOrder.getText());
				
				confirmButton.setEnabled(true);
				order.setMaximumItemsInOrder(max);
				MaxItemsInOrder.setEnabled(false);
				
				int i 		 	  		  = searchForID(ID);
				int quantity 	  		  = Integer.parseInt(ItemQuantity.getText());
				int discount 	  		  = 0;
				double discountPercentage = 0;
				
				if (i == -1) {
					JOptionPane.showMessageDialog(null, "item ID " + ID + " not in File");
				}
				
				if (i != -1) {
					String itemID 		= inventory.get(i).getID();
					String itemName 	= inventory.get(i).getName();
					Double itemPrice	= inventory.get(i).getPrice();
					boolean inStock 	= inventory.get(i).getInStock();

					if (!inStock ) {
						JOptionPane.showMessageDialog(null, "item is not in stock");
						confirmButton.setEnabled(false);
						
					}
					if ( inStock ) {
						
						if (quantity >= 1 && quantity <= 4) {
							inventory.get(i).setDiscount(0);
							discountPercentage = 1;
						}
						
						if (quantity >= 5 && quantity <= 9) {
							inventory.get(i).setDiscount(10);
							discountPercentage = 0.9;
						}
						
						if (quantity >= 10 && quantity <= 14) {
							inventory.get(i).setDiscount(15);
							discountPercentage = 0.85;
						}
						
						if (quantity >= 15) {
							inventory.get(i).setDiscount(20);
							discountPercentage = 0.8;
						}
						
						discount 	= inventory.get(i).getDiscount();
						totalPrice 	= (itemPrice * quantity) * discountPercentage;
						String info = itemID + "" + itemName + " $" + String.format("%.2f", itemPrice) + " " + quantity + " " + discount + "% $" + String.format("%.2f", totalPrice);
						
						confirmButton.setEnabled(true);
						processButton.setEnabled(false);
						ItemInformation.setText(info);
					}
				}
				
			}
//				
		});
		
		
		// Confirm Order Action Listener
		// ---------------------------------------------------------------------------------------------------------------

		confirmButton.addActionListener(new ActionListener(){		
			@Override
			public void actionPerformed(ActionEvent e) 
			{
				int num = 0;
				
				// Gets max number of items
				maxItems = Integer.parseInt(MaxItemsInOrder.getText());
				// Sets max number of item, use variable for cleaner code
				order.setMaximumItemsInOrder(maxItems);
				
				
				
				
				
				
				// Gets the ID
				String ID 	 = ItemID.getText();
				int quantity = Integer.parseInt(ItemQuantity.getText());
				int i 		 = searchForID(ID);
				double finalDiscount = 0;
				
				if (i != -1) {
					// Returns all of the items information
					Double itemPrice = inventory.get(i).getPrice();
					int discount = inventory.get(i).getDiscount();
					double discountPercentage = 1;
					
					if (discount == 0) {
						discountPercentage = 1;
					}
					if (discount == 10) {
						discountPercentage = 0.9;
					}
					if (discount == 15) {
						discountPercentage = 0.85;
					}
					if (discount == 20) {
						discountPercentage = 0.8;
					}
					

					subtotal += (itemPrice * quantity * discountPercentage);
					order.setSubtotal(subtotal);
					
//					if ((order.getTotalItemsInOrder() + quantity) > order.getMaximumItemsInOrder()) {
//						JOptionPane.showMessageDialog(null, "That item goes above the maximum items for this order");
//					}
					if (num <= order.getMaximumItemsInOrder()) {
						
						JOptionPane.showMessageDialog(null, "Item #" + (order.getTransactions() + 1) + " accepted");
						totalItems += Integer.parseInt(ItemQuantity.getText());
						
						order.setTotalItemsInOrder(totalItems);
						order.setTransactions();
						num += order.getTransactions();
				
						// Updates the subtotal 
	//					subTotalLabel.setText("Order Subtotal for " + (totalItems) + " item(s):");
						OrderSubtotal.setText(String.format("%.2f", subtotal));
						
						// You may now view the order
						viewButton.setEnabled(true);
					
						if (num < (maxItems + 1)) {
							
	
							order.setTotalItemsInOrder(totalItems);
							processButton.setText("Process Item #" + (num + 1));
							confirmButton.setText("Confirm Item #" + (num + 1));
							
							subTotalLabel.setText("Order Subtotal for " + (num) + " item(s):");
							
							
	//						public JLabel IDLabel = new JLabel("Enter Item ID for Item #1:");	
							IDLabel.setText("Enter Item ID for Item #" + (num + 1) + ":");
							//public JLabel itemQuantityLabel = new JLabel("Enter Quantity for Item #1:");
							itemQuantityLabel.setText("Enter Quantity for Item #" +  (num + 1) + ":");
	//						public JLabel infoLabel = new JLabel("Item #1 Info:");
							infoLabel.setText("Item #" + (num + 1) + " Info:");
							
							
							ItemInformation.setText("");
							ItemID.setText("");
							ItemQuantity.setText("");
							
							String itemID = inventory.get(i).getID();
							String itemName = inventory.get(i).getName();
							double totalPrice = (itemPrice * quantity) * discountPercentage;
							
							String info = itemID + "" + itemName + "$" + String.format("%.2f", itemPrice) + " " + quantity + " " + discount + "% $" + String.format("%.2f", totalPrice);
							
							
							if (discount == 0) {
								finalDiscount = 0;
							}
							else if (discount == 10) {
								finalDiscount = 0.1;
							}
							else if (discount == 15) {
								finalDiscount = 0.15;
							}
							else if (discount == 20) {
								finalDiscount = 0.2;
							}
							
							
							String info1 = itemID + "," + itemName + ", " + String.format("%.2f", itemPrice) + ", " + quantity + ", " + finalDiscount + ", $" + String.format("%.2f", totalPrice) + ", ";
							order.addToFinalOrder(info1);
							order.addToOrder(info);
							
							
							confirmButton.setEnabled(false);
							processButton.setEnabled(true);
							
						}
					
					
					
					// hit maximum number of items for order
					// must finish
//					System.out.println("Max: " + maxItems + " total: " + totalOrders);
						if (maxItems <= num) {
							JOptionPane.showMessageDialog(null, "Maximum Orders reached, click Finish Order button!");
							// enable finishing buttons
							finishButton.setEnabled(true);
							// disable buttons used to add new items;
							processButton.setEnabled(false);
							confirmButton.setEnabled(false);
							// update the final total for the order
							order.setTotal(subtotal);
						}
					}
				}
				
				
			}
//		
//		
		});
		// -----------------------------------------------------------------------------------------------------
		
		
		// Add Order Action Listener
		// ---------------------------------------------------------------------------------------------------------------
		viewButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				JOptionPane.showMessageDialog(null, order.printOrder());
				order.printOrder();
			}
		});
		// ---------------------------------------------------------------------------------------------------------------
//		
		
		// Finish Order Action Listener
		// ---------------------------------------------------------------------------------------------------------------
		finishButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					// Calls function to print to transactions.txt
					JOptionPane.showMessageDialog(null,order.FinishOrderAndPrint());
				} catch (HeadlessException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				
				
//				Main.super.dispose(); //dispose frame
			}
		});
		// ---------------------------------------------------------------------------------------------------------------
		
//		 Finish Order Action Listener
//		 ---------------------------------------------------------------------------------------------------------------
		newOrderButtob.addActionListener(new ActionListener() {
//
//			@Override
			public void actionPerformed(ActionEvent e) {
				
				JOptionPane.showMessageDialog(null, "New Order Created");
				// Make the frame invisible
				Main.super.setVisible(false);
				// Get rid of it
				Main.super.dispose(); 
					try {
						Main.main(null);
					} catch (FileNotFoundException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
//			
			}
		});
		// ---------------------------------------------------------------------------------------------------------------
		
		
		// Finish Order Action Listener
		// ---------------------------------------------------------------------------------------------------------------
		exitButton.addActionListener(new ActionListener() {
//
			@Override
			public void actionPerformed(ActionEvent e) {
				// Makes the frame disapear
				Main.super.setVisible(false);
				// Essentially closes the frame
				Main.super.dispose();
				
			}
		});
		// ---------------------------------------------------------------------------------------------------------------
		
	}

	
	// Processes all of the information in the file in order to easily access information
	// FileNotFoundException auto generated
	public void ReadAndProcessFile() throws FileNotFoundException {

		this.inventory = new ArrayList<Item>();
		File file = new File("inventory.txt");
		Scanner scnr = new Scanner(file);

		while (scnr.hasNextLine()) {
			// grab next inventory line and parse into 3 strings
			String item = scnr.nextLine();
			String[] information = item.split(",");

			// create a item and set fields
			Item selectedItem = new Item();
			
			// Assigning the ID to the Item
			selectedItem.setID((information[0]));
			
			// Assingning the name to the item
			selectedItem.setName(information[1]);

			// If its in stock, assign the value to true
			if (information[2].contains("true")) {
				selectedItem.setInStock(true);
			}
			// Set inStock to false otherwise 
			else {
				selectedItem.setInStock(false);
			}
			
			// Store price of item as a double so its easier to add
			selectedItem.setPrice(Double.parseDouble(information[3]));
			
			

			// add the whole object (item) to the inventory
			// This gives us every item stored in an array list and all of its info 
			inventory.add(selectedItem);
		}
		
		scnr.close();
	}
	
	// Searches through the ArrayList in O(n) runtime for the user ID
		public int searchForID(String ID) {
			int size = this.inventory.size();
			Item searchedItem;
			
			// Search Through the entire file to see
			for(int i = 0; i < size; i++) {
				searchedItem = inventory.get(i);
				
				// returned the index of the id in the arraylist
				if(searchedItem.getID().equals(ID)) {
					return i;
				}
			}
			return -1;
		}

		
	public static void main(String[] args) throws FileNotFoundException {
		// Set up the frames
		Main frame = new Main();
		frame.pack(); // fit windows for screen
		frame.setTitle("Nile Dot Com - Spring 2021");
		frame.setLocationRelativeTo(null); // center windows
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); // close window
		frame.setVisible(true); // display window
	}
}

