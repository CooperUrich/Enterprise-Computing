public class Item {
	private double price;
	private String itemID;
	private String Name;
	private boolean inStock;
	private int discount;
	private String finalID;
	
	// Getters and Setters
	public double getPrice() {
		return price;
	}
	public void setPrice(double price) {
		this.price = price;
	}
	public String getID() {
		return itemID;
	}
	public void setID(String ID) {
		this.itemID = ID;
	}
	public String getName() {
		return Name;
	}
	public void setName(String Name) {
		this.Name = Name;
	}
	
	public void setInStock(boolean inStock) {
		this.inStock = inStock;
	}
	public boolean getInStock() {
		return inStock;
	}
	
	public void setDiscount(int discount) {
		this.discount = discount;
	}
	public int getDiscount() {
		return discount;
	}
	
	public void setFinalID(String s) {
		this.finalID = s;
	}
	
	public String getFinalID() {
		return finalID;
	}
	
	
}
