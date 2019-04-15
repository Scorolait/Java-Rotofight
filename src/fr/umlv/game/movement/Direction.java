package fr.umlv.game.movement;


/**
 * @author LOPES MENDES Ailton
 * @author LIN Gérald
 *
 */
public enum Direction {
	
	Nord(0, -1), South(0, 1), East(-1, 0), Ouest(1, 0);
	private final double x;
	private final double y;
	
	private Direction(double x, double y) {
		this.x = x;
		this.y = y;
	}
	
	/**
	 * Return the opposite Direction.
	 * @param dir
	 * @return
	 */
	public static Direction oppositeDirection(Direction dir) {
		switch (dir) {
			case East: return Ouest;
			case Nord: return South;
			case Ouest: return East;
			case South: return Nord;
			default:
				throw new IllegalArgumentException("Invalid direction");
		
		}
	}
	
	
	/**
	 * @return x
	 */
	public double getX() {
		return x;
	}
	/**
	 * @return y
	 */
	public double getY() {
		return y;
	}
	
}
