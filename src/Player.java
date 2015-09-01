import java.util.ArrayList;


public class Player {
	int playernum;
	String username;
	String password;
	int level;
	int[] stats;
	int exp;
	int coin;
	ArrayList<Item> inventory = new ArrayList<Item>();
	String gender;
	ArrayList<Integer> questFlags = new ArrayList<Integer>();
	String room;
	
	public Player() {
	}
	
	/*
	 * Gets player from memory
	 * 
	 * returns false on a failure
	 * TODO: Bunch of error checking here
	 * 
	 */
	public boolean loadPlayer(String username) {
		
		String[] input = UserFileUtils.readCharacter(username);
		if(input == null) return false;
		
		playernum = Integer.parseInt(input[BurrowDriver.PLAYERNUM_LINE].replace('#', ' ').trim());
		this.username = input[BurrowDriver.USERNAME_LINE];
		password = input[BurrowDriver.PASSWORD_LINE];
		level = Integer.parseInt(input[BurrowDriver.LEVEL_LINE]);
		exp = Integer.parseInt(input[BurrowDriver.EXP_LINE]);
		stats = new int[BurrowDriver.NUMSTATS];
		
		for(int i = 0; i < input[BurrowDriver.STATS_LINE].split(" ").length; i++) {
			stats[i] = Integer.parseInt(input[BurrowDriver.STATS_LINE].split(" ")[i]);
		}
		
		coin = Integer.parseInt(input[BurrowDriver.COIN_LINE]);
		
		String[] invline = input[BurrowDriver.INVENTORY_LINE].trim().split(",");
		for(int i = 0; i < invline.length; i++) {
			inventory.add(Item.parseItem(invline[i]));
		}
		
		gender = input[BurrowDriver.GENDER_LINE];
		
		String[] questLine = input[BurrowDriver.QUEST_FLAG_LINE].trim().split(" ");
		for(int i = 0; i < questLine.length; i++) {
			questFlags.add(Integer.parseInt(questLine[i]));
		}
		
		room = input[BurrowDriver.ROOM_LINE];
		
		return true;
	}
	
	/*
	 * Writes player to memory
	 * 
	 * returns false on a failure
	 */
	public boolean writePlayer(FormattedPrintWriter out) {
		Item[] invArray = new Item[inventory.size()];
		inventory.toArray(invArray);

		int[] questArray = new int[questFlags.size()];
		for(int i = 0; i < questFlags.size(); i++) questArray[i] = questFlags.get(i);
		
		return UserFileUtils.writeCharacter(username, password, level, exp, stats, 
											coin, invArray, gender, questArray, out, room);
	}
	
	/*
	 * Useful for debugging
	 */
	public void printPlayer() {
		System.out.println(playernum);
		System.out.println(username);
		System.out.println(password);
		System.out.println(level);
		System.out.println(exp);

		for(int i : stats) System.out.print(i + " ");
		System.out.println();
		
		System.out.println(coin);
//		System.out.println(inventory);
		System.out.println(gender);
		
		for(int i : questFlags) System.out.print(i + " ");
		System.out.println();
		
		System.out.println(room);
	}
}
