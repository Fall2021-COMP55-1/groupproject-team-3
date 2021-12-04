package Item;
public enum MapType {
	LIVINGR, BEDR;
	
	public String toString() {
		switch(this) {
			case LIVINGR: return "living room";
			case BEDR: return "bed room";
		}
		return "n/a";
	}
}

