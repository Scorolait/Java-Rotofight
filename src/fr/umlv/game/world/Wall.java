package fr.umlv.game.world;

import java.awt.Color;
import java.awt.geom.Rectangle2D;
/**
 * @author LOPES MENDES Ailton
 * @author LIN Gérald
 *
 */
public final class Wall extends Entity{
	
	/**
	 * Constructs a wall with a Rectangle2D shape and a color.
	 * @param wall
	 * @param color
	 */
	public Wall(Rectangle2D.Double wall, Color color) {
		super(wall, color);
	}
	
	/**
	 * Constructs a wall with a color.
	 * @param x
	 * @param y
	 * @param w
	 * @param h
	 * @param color
	 */
	public Wall(double x, double y, double w, double h, Color color) {
		super(new Rectangle2D.Double(x, y, w, h), color);
	}

}
