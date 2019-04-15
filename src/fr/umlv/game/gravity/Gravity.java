package fr.umlv.game.gravity;

import fr.umlv.game.movement.Direction;

/**
 * @author LOPES MENDES Ailton
 * @author LIN Gérald
 *
 */
public class Gravity{
	
	private final double x;
	private final double y;
	
	
	 /**
	 * Constructs Gravity
	 * @param timeGravity
	 * @param type
	 */
	public Gravity(double angle) {
		if (angle <= 90) {
			x = angle / 90;
			y = angle / 90 - 1;
		}
		else if (angle > 90 && angle <= 180) {
			y = angle / 180;
			x = 1 - angle / 180;
		}
		
		else if (angle > 180 && angle <= 270) {
			x = -(angle / 270);
			y = 1 - angle / 270;
		}
		
		else if (angle > 270 && angle <= 360) {
			y = -(angle / 360);
			x = angle / 360 - 1;
		}
		else {
			x = 0; y = 0;
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
	
	/**
	 * @return gravity direction
	 */
	public Direction gravityDirection() {
		if (Math.pow(x, 2) > Math.pow(y, 2)) {
			return x < 0 ? Direction.East : Direction.Ouest;
		}
		else {
			return y < 0 ? Direction.Nord : Direction.South;
		}
	}
	
	/**
	 * @return -x
	 */
	public double getOppositeX() {
		return -x;
	}
	
	/**
	 * @return -y
	 */
	public double getOppositeY() {
		return -y;
	}
	
	/**
	 * Updates the angle.
	 */
	public void update(){}
	
	
	
}
