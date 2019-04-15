package fr.umlv.game.world;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.Arc2D;
import java.awt.geom.Rectangle2D;
import java.util.function.BiConsumer;

import fr.umlv.game.movement.Direction;
import fr.umlv.game.movement.Move;

/**
 * @author LOPES MENDES Ailton
 * @author LIN Gérald
 *
 */
public class Sword extends MobileEntity{
	private final static int damage = 1;
	
	
	private static int setSwordAngle(Direction dir) {
	
		switch(dir) {
			case East:
				return 90;
			case Nord:
				return 0;
			case Ouest:
				return -90;
			case South:
				return -180;
			default:
				throw new IllegalArgumentException("Direction invalid");
	
		}
	}
	
	private static Rectangle2D.Double swordInitialPosition(Player player, Direction dir) {
		double x, y, w, h, div = 4;
		w = player.getRectShape().getWidth() / div; h = player.getRectShape().getHeight() / div;
		x = player.getRectShape().getCenterX() - w / 2;
		y = player.getRectShape().getCenterY() - h / 2;

		switch(dir) {
			case East:
				x = player.getRectShape().getMinX();
				return new Rectangle2D.Double(x - w * 2, y, w, h);
			case Nord:
				y = player.getRectShape().getMinY();
				return new Rectangle2D.Double(x, y - h * 2, w, h);
			case Ouest:
				x = player.getRectShape().getMaxX();
				return new Rectangle2D.Double(x + w * 2, y, w, h);
			case South:
				y = player.getRectShape().getMaxY();
				return new Rectangle2D.Double(x, y + h * 2, w, h);
			default:
				throw new IllegalArgumentException("Direction invalid");
	
		}
	}

	/**
	 * Constructs an Sword
	 * @param player
	 * @param color
	 */
	public Sword(Player player, Color color) {
		super(new Arc2D.Double(swordInitialPosition(player, player.getDir()),
				setSwordAngle(player.getDir()), 180, Arc2D.CHORD)
				, color, 
				player.getSpeed() * 1.5);
		setDir(player.getDir());
		
	}
	
	/**
	 * Return true if a sword touched a player, false otherwise.
	 * @param g2
	 * @return
	 */
	public boolean swordAttack(Graphics2D g2) {
		if (Move.getMap().get(this) != null && Move.getMap().get(this) instanceof Player) {
			var player = (Player)Move.getMap().get(this);
			player.attacked(g2, damage);
			return true;
		}
		return false;
	}
	
	
	/**
	 * @return damage
	 */
	public static int getDamage() {
		return damage;
	}
	
	/**
	 * Updates the swords.
	 * @param g2
	 * @param swords
	 * @param world
	 * @return
	 */
	public static boolean updateSwords(Graphics2D g2, MultiEntity<Sword> swords, World world) {
		int size = swords.size();
		BiConsumer<MobileEntity, World> biConsummer = 
				(s, w) -> {Move.move(s, w, s.getDir().getX() * s.getSpeed(), s.getDir().getY() * s.getSpeed());};
		swords.forEach(s -> {
			s.moveDraw(g2, world, biConsummer);});
		
		swords.removeIf(g2, world, s -> s.swordAttack(g2) || Move.getMap().get(s) != null);
		if (size != swords.size()) {
			return true;
		}
		return false;
	}
}
