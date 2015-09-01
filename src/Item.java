
/*
 * Just contains the item ID and quantity, with some helper stuff
 */
public class Item {
	int id, quantity;
	
	public Item(int id, int quantity) {
		this.id = id;
		this.quantity = quantity;
	}
	
	/*
	 * Used for writing inventories
	 */
	public String getWriteString() {
		// TODO Auto-generated method stub
		return null;
	}

	/*
	 * Used when making a new character
	 */
	public static Item[] getDefaultStartItems() {
		Item[] output = new Item[1];
		output[0] = new Item(1, 1);
		return output;
	}
	
	@Override
	public String toString() {
		return quantity + " of item " + id + " ";
	}

	/*
	 * For file reading, take a string and make an item
	 */
	public static Item parseItem(String s) {
		String[] strs = s.trim().split(" ");
		return new Item(Integer.parseInt(strs[0]), Integer.parseInt(strs[1]));
	}
}
