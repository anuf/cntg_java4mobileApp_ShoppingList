package anuf.exemplo.shoppinglist;

import java.io.Serializable;

public class Product implements Serializable {
	private static final long serialVersionUID = 1L;

	private int ID;
	private String name;
	private int quantity;
	private String unit;
	
	public Product(int ID, String name, int quantity,String unit) {
		this.ID = ID;
		this.name = name;
		this.quantity = quantity;
		this.unit = unit;
	}

	public int getID() {
		return ID;
	}

	public String getName() {
		return name;
	}

	public String getUnit() {
		return unit;
	}
	
	public int getQuantity() {
		return quantity;
	}
	
	@Override
	public String toString() {
		return ID+ " : "+ name + " - " + quantity + " "+ unit;
	}
}
