package utils;

public class Location implements Comparable<Location> {

	public int x;
	public int y;
	public int distance;

	public Location() {
		x = 0;
		y = 0;
		distance = Integer.MAX_VALUE;
	}

	public Location(int x, int y) {
		this.x = x;
		this.y = y;
		this.distance = Integer.MAX_VALUE;
	}

	public Location(int x, int y, int distance) {
		this.x = x;
		this.y = y;
		this.distance = distance;
	}

	@Override
	public boolean equals(Object obj) {
		Location cmp = (Location) obj;

		return (cmp.x == this.x && cmp.y == this.y) ? true : false;
	}

	@Override
	public int hashCode() {
		return (this.x + this.y) + (2 * this.x) + (this.x * this.y);
	}

	public int compareTo(Location l) {

		int lengthThis = (int) Math.round(Math.sqrt(l.x * l.x + l.y * l.y));
		int lengthL = (int) Math.round(Math.sqrt(l.x * l.x + l.y * l.y));

		if (lengthL == lengthThis)
			return 0;
		else if (lengthThis < lengthL)
			return -1;
		return 1;
	}

}
