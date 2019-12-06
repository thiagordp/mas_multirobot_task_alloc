package utils;

import java.util.List;

public class MoveControls {

	public static boolean contains(List<Location> list, Location l) {

		for (Location location : list)
			if (location.x == l.x && location.y == l.y)
				return true;
		return false;
	}
}
