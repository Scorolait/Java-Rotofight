package fr.umlv.game.world;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.Arc2D;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Rectangle2D;
import java.awt.geom.RectangularShape;
import java.util.function.BiConsumer;
import fr.umlv.game.commands.*;
import fr.umlv.game.gravity.Gravity;
import fr.umlv.game.movement.Direction;
import fr.umlv.zen5.ApplicationContext;
import fr.umlv.zen5.Event;
import fr.umlv.zen5.Event.Action;
import fr.umlv.zen5.KeyboardKey;

/**
 * @author LOPES MENDES Ailton
 * @author LIN Gérald
 *
 */
public class Player extends MobileEntity{
	private final SetCommands setCom;
	private final Eyebrows eyebrow;
	private final static int fullHp = 10;
	private final static int deadHp = 0;
	private int hp;
	private int swordNumber;
	private final MultiEntity<Sword> swords;
	
	private class Eyebrows  extends Entity {
		
		private int numberDraws;
		private static final int maxNumberDraws = 10;
		/**
		 * @param rectShape
		 * @param color
		 */
		private Eyebrows(RectangularShape rectShape, Color color) {
			super(rectShape, color);
			numberDraws = 0;
		}
		
		private void setEyebrows(Player player, Gravity gravity) {
			double w = player.getRectShape().getWidth() / 6, h = player.getRectShape().getHeight() / 6;
			switch(gravity.gravityDirection()) {
				case East: getRectShape().setFrame(
					player.getRectShape().getMaxX() - w * 1.8,  player.getRectShape().getCenterY() - h / 1.5,
					w, h
				); break;
				case Nord: getRectShape().setFrame(
					player.getRectShape().getCenterX() - w / 2, player.getRectShape().getMaxY() - h * 3,
					w, h
				); break;
				case Ouest: getRectShape().setFrame(
					player.getRectShape().getMinX() + w * 1.8, player.getRectShape().getCenterY() - h / 2,
					w, h
				); break;
				case South: getRectShape().setFrame(
					player.getRectShape().getCenterX() - w / 2, player.getRectShape().getMinY() + h + h / 2,
					w, h
				); break;
				default: break;
			}
			setEyebrows(player);
		}
		
		/**
		 * @param player
		 */
		private void setEyebrows(Player player) {
			double x, y, w, h;
			x = getRectShape().getX(); y = getRectShape().getY();
			w = getRectShape().getWidth(); h = getRectShape().getHeight();
			switch(player.getDir()) {
				case East: eyebrow.getRectShape().setFrame(player.getRectShape().getMinX() + w * 2, y, w, h); break;
				case Nord: eyebrow.getRectShape().setFrame(x, y - h * 1.3, w, h); break;
				case Ouest: eyebrow.getRectShape().setFrame(player.getRectShape().getMaxX() - w * 2, y, w, h); break;
				case South: eyebrow.getRectShape().setFrame(x, y + h + h / 1.5, w, h); break;
				default:
					break;
				}
		}
		
		private void drawX(Graphics2D g2, Player player) {
			fillEntity(g2, player.getColor());
			var firstEyebrow =  new Eyebrows(new Arc2D.Double(
					player.getRectShape().getMinX() - player.getRectShape().getWidth() / 4,
					player.getRectShape().getCenterY() - player.getRectShape().getHeight() / 4,
					player.getRectShape().getWidth(), player.getRectShape().getHeight(),
					0, 90, Arc2D.CHORD), Color.BLACK);
			
			var secondEyebrow = new Eyebrows(new Arc2D.Double(
					player.getRectShape().getMinX() + player.getRectShape().getWidth() / 4,
					player.getRectShape().getCenterY() - player.getRectShape().getHeight() / 4,
					player.getRectShape().getWidth(), player.getRectShape().getHeight(),
					90, 90, Arc2D.CHORD), Color.BLACK);
			
			firstEyebrow.fillEntity(g2);
			secondEyebrow.fillEntity(g2);
			numberDraws++;
		}
		
		private void continueDrawX(Graphics2D g2, Player player) {
			if (numberDraws > 0 && numberDraws <= maxNumberDraws) {
				drawX(g2, player);
			}
			else {
				numberDraws = 0;
			}
		}
	}

	
	/**
	 * Constructs a player with an ellipse, it's color and creates the commands with keys.
	 * @param arc
	 * @param color
	 * @param keys
	 */
	public Player(Ellipse2D.Double ellipse, Color color, int swordNumber, KeyboardKey[] keys) {
		super(ellipse, color, 10);
		this.hp = fullHp;
		eyebrow = new Eyebrows(
				new Rectangle2D.Double(
						ellipse.getX() + ellipse.getWidth() / 3, ellipse.getY() + ellipse.getHeight() / 3, 
						ellipse.getWidth() / 6, ellipse.getHeight() / 6),
				Color.BLACK
				);
		setCom = new SetCommands(keys);
		this.swordNumber = swordNumber;
		swords = new MultiEntity<>();
	}

	/**
	 * Constructs a player, it's color and creates the commands with keys.
	 * @param x
	 * @param y
	 * @param w
	 * @param h
	 * @param color
	 * @param keys
	 */
	public Player(double x, double y, double size, Color color, int swordNumber, KeyboardKey[] keys) {
		this(new Ellipse2D.Double(x, y, size, size), color, swordNumber, keys);
	}

	/**
	 * @return true if hp == deadHp
	 */
	public boolean isDead() {
		return hp == deadHp;
	}
	
	private void playerAction(Graphics2D g2, World world) {
		for (var control : setCom) {
			Controls.action(g2, this, control, world);
		}
		
		setCom.remove(Controls.Jump);
	}
	
	/**
	 * Reduces the player hp.
	 * @param g2
	 * @param damage
	 */
	public void attacked(Graphics2D g2, int damage) {
		hp = Math.max(hp - damage, deadHp);
		eyebrow.drawX(g2, this);
		
	}
	
	/**
	 * Return true if the player direction could be set, false otherwise.
	 * @param dir
	 * @param gravity
	 * @return true || false
	 */
	public boolean setDir(Direction dir, Gravity gravity) {
		if (dir != gravity.gravityDirection()) {
			if (dir == Direction.oppositeDirection(gravity.gravityDirection())) {
				return false;
			}
			setDir(dir);
			return true;
		}
		return false;
	}
	
	/**
	 * Updates the direction of the player.
	 * @param context
	 * @param event
	 */
	public void updatePlayer(ApplicationContext context, Event event, Graphics2D g2, World world) {
		if (event == null) {  
			setCom.add(Controls.Invalid);
	    }
		else {
			Action action = event.getAction();
			
			if (action == Action.KEY_PRESSED) {
				var d = Controls.controlDirectionOrDefault(setCom.getControl(event.getKey()), getDir());
				if (d == Direction.oppositeDirection(world.getGravity().gravityDirection())) {
					setCom.add(Controls.Jump);
				}
				
				else if (d != world.getGravity().gravityDirection()) {
					setCom.add(event.getKey());
				}
		    }
			
			else if (action == Action.KEY_RELEASED) {
				setCom.remove(event.getKey());
			}
		}
		
		playerAction(g2, world);
		eyebrow.continueDrawX(g2, this);
		Sword.updateSwords(g2, swords, world);
	}
	
	/**
	 * Tries to add a sword if is not possible return false otherwise true.
	 * @param sword
	 * @return
	 */
	public boolean add(Sword sword) {
		if (swords.size() >= swordNumber) {
			return false;
		}
		
		return swords.add(sword);
	}
	
	/**
	 * Moves the player.
	 * @param g2
	 * @param world
	 * @param biConsumer
	 */
	public void move(Graphics2D g2, World world, BiConsumer<MobileEntity, World> biConsumer) {
		moveDraw(g2, world, biConsumer);
		eyebrow.setEyebrows(this, world.getGravity());
		eyebrow.fillEntity(g2);
	}
}
