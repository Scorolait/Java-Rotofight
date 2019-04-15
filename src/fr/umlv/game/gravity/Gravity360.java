package fr.umlv.game.gravity;


/**
 * @author LOPES MENDES Ailton
 * @author LIN Gérald
 *
 */
public class Gravity360 extends Gravity{
	private double angle;
	
	/**
	 * Constructs Gravity360 with 20 degree angle.
	 */
	public Gravity360() {
		super(20);
		this.angle = 20;
	}
		
	/* (non-Javadoc)
	 * @see fr.umlv.game.gravity.Gravity#update()
	 */
	public void update() {
		angle = (angle + 20) % 360;
	}
	
	/* (non-Javadoc)
	 * @see fr.umlv.game.gravity.Gravity#getX()
	 */
	public double getX() {
		return new Gravity(angle).getX();
	}
	
	/* (non-Javadoc)
	 * @see fr.umlv.game.gravity.Gravity#getY()
	 */
	public double getY() {
		return new Gravity(angle).getY();
	}

}
