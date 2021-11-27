package Item;
public enum ItemType {
	WEAPON, KEY, HEALING;
	
	public String toString() {
		switch(this) {
			case WEAPON: return "weapon";
			case KEY: return "key";
			case HEALING: return "healing";
		}
		return "n/a";
	}
}
