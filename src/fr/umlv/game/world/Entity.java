package fr.umlv.game.world;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.RectangularShape;
import java.util.Objects;

import fr.umlv.game.movement.Direction;

public abstract class Entity{
	private final RectangularShape rectShape;
	private Direction dir;
	private final Color color;
	/**
	 * Constructs a Figure with the parameters RectangularShape and Color.
	 * @param rectShape
	 * @param color
	 */
	public Entity(RectangularShape rectShape, Color color) {
		this.rectShape = Objects.requireNonNull(rectShape);
		this.color = Objects.requireNonNull(color);
		dir = Direction.South;
	}
	
	/**
	 * Fills the shape of the entity with a color.
	 * Throws an exception if the referenced object is not a figure.
	 * @param g2
	 * @param color
	 * @throws ClassCastException
	 */
	public void fillEntity(Graphics2D g2, Color color) throws ClassCastException {
		try {
			
			g2.setColor(color);
			g2.fill(rectShape);
		
		} catch(ClassCastException e) {
			System.err.println("The following object does not inherit from Entity " 
					+ e.getMessage());
			System.exit(1);
		}
	}
	
	/**
	 * Calls fillFigure(Graphics2D g2, Color color).
	 * @param g2
	 * @throws ClassCastException
	 */
	public void fillEntity(Graphics2D g2) throws ClassCastException {
		fillEntity(g2, color);
	}
	
	/**
	 * Returns the shape of the figure.
	 * @return rectShape
	 */
	public RectangularShape getRectShape() {
		return rectShape;
	}
	
	/**
	 * Returns the color of an entity.
	 * @return color
	 */
	public Color getColor() {
		return color;
	}
	
	/**
	 * Return the direction of an entity.
	 * @return dir
	 */
	public Direction getDir() {
		return dir;
	}

	/**
	 * Set the direction.
	 * @param dir
	 */
	public void setDir(Direction dir) {
		this.dir = dir;
	}
	
	/**
	 * Return true if entity intersects another entity.
	 * @param e
	 * @return
	 */
	public boolean intersect(Entity e) {
		
		return rectShape.intersects(e.rectShape.getBounds2D()) && this != e;
	}
	
}
