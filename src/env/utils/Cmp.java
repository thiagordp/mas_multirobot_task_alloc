package utils;

import java.util.Comparator;

public class Cmp implements Comparator<Location> {
	public int compare(Location o1, Location o2) {
		if (o1.distance == o2.distance)
			return 0;
		else if (o1.distance < o2.distance)
			return -1;
		return 1;
	}
}