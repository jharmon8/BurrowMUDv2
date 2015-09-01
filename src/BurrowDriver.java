
/*
 * Driver for the server.
 */
public class BurrowDriver {
	
	/*
	 * Learn how to code.
	 */
	public static void main(String[] args) {
		World w = new World();
		w.init();
		NetworkManager.beginListening(2323);
	}
	
	/*
	 * Temporary globals storage
	 */
	public static final int USER_LINES = 11;
	public static final int NUMSTATS = 6;
	
	public static final int PLAYERNUM_LINE = 0;
	public static final int USERNAME_LINE = 1;
	public static final int PASSWORD_LINE = 2;
	public static final int LEVEL_LINE = 3;
	public static final int EXP_LINE = 4;
	public static final int STATS_LINE = 5;
	public static final int COIN_LINE = 6;
	public static final int INVENTORY_LINE = 7;
	public static final int GENDER_LINE = 8;
	public static final int QUEST_FLAG_LINE = 9;
	public static final int ROOM_LINE = 10;
	
	public static final int DESC_LINE = 0;
	public static final int ITEMS_LINE = 1;
	
	public static final char parseChar = '~';
}
