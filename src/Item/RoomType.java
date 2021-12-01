package Item;
import java.util.ArrayList;

public enum RoomType {
	BEDROOMS, BEDROOML, BEDROOMR, OUT;

	public String toString() {
		switch(this) {
			case BEDROOMS: return "bedroom map";
			case BEDROOML: return "bedroom left";
			case BEDROOMR: return "bedroom right";
			case OUT: return "Out of the house";
		}
		return "n/a";
	}
}
