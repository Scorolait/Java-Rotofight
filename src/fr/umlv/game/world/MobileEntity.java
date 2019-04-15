package fr.umlv.game.world;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.RectangularShape;
import java.util.function.BiConsumer;


/**
 * @author LOPES MENDES Ailton
 * @author LIN Gérald
 *
 */
public abstract class MobileEntity extends Entity{

	private final double speed;

	public MobileEntity(RectangularShape rectShape, Color color, double speed) {
		super(rectShape, color);
		if (speed < 1) {
			throw new IllegalArgumentException("speed cannot be negatif");
		}
		this.speed = speed;
	}

	/**
	 * @return the speed
	 */
	public double getSpeed() {
		return speed;
	}

	
	/**
	 * Moves and draws an MobileEntity.
	 * @param g2
	 * @param world
	 * @param biConsummer
	 */
	public void moveDraw(Graphics2D g2, World world, BiConsumer<MobileEntity, World> biConsummer) {
		
		if (Math.pow(world.getGravity().getX(), 2) > Math.pow(world.getGravity().getY(), 2)) {
			getRectShape().setFrame(
					getRectShape().getX(), getRectShape().getY(), 
					Math.max(getRectShape().getHeight(), getRectShape().getWidth()),
					Math.min(getRectShape().getHeight(), getRectShape().getWidth())
				);
		}
		
		else {
			getRectShape().setFrame(
					getRectShape().getX(), getRectShape().getY(), 
					Math.min(getRectShape().getHeight(), getRectShape().getWidth()),
					Math.max(getRectShape().getHeight(), getRectShape().getWidth())
				);
		}
		fillEntity(g2, world.getColor());
		biConsummer.accept(this, world);
		fillEntity(g2);
	}

}
