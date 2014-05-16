package anuf.exemplo.shoppinglist;

import java.io.Serializable;

public class Product implements Serializable {
	private static final long serialVersionUID = 1L;

	private int ID;
	private String name;
	private int quantity;
	private String units;
	
	public Product(int ID, String name, int quantity) {
		this.ID = ID;
		this.name = name;
		this.quantity = quantity;
		this.units = units;
	}

	public int getID() {
		return ID;
	}

	public String getName() {
		return name;
	}

	public String getUnits() {
		return units;
	}
	
	public int getQuantity() {
		return quantity;
	}
	
	@Override
	public String toString() {
		return name + "-  " + quantity + " "+ units;
	}
}
