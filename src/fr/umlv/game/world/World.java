package fr.umlv.game.world;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.Rectangle2D;
import java.util.List;
import fr.umlv.game.file.File;
import fr.umlv.game.gravity.Gravity;
import fr.umlv.game.gravity.Gravity360;
import fr.umlv.game.gravity.MultiGravity;
import fr.umlv.zen5.ApplicationContext;
import fr.umlv.zen5.Event;
import fr.umlv.zen5.Event.Action;
import fr.umlv.zen5.KeyboardKey;
import fr.umlv.zen5.ScreenInfo;


/**
 * @author LOPES MENDES Ailton
 * @author LIN Gérald
 *
 */
public class World extends Entity{	
	
	
	private final MultiEntity<Player> players;
	private final MultiEntity<Wall> walls;
	private final MultiGravity gravity;
	
	private World(ScreenInfo screenInfo, double width, double height, String file) {
		super(new Rectangle2D.Double(
				(screenInfo.getWidth() - width) / 2,
				(screenInfo.getHeight() - height) / 2,
				width, 
				height), Color.GRAY);
		players = new MultiEntity<>();
		walls = new MultiEntity<>();
		File.worldCreation(this, file);
		gravity = new MultiGravity(1000, new Gravity(180));
	} 	
	
	/**
	 * Constructs a world with the fields of context and with the given file.
	 * @param context
	 * @param file
	 */
	public World(ApplicationContext context, String file) {

		this(
				context.getScreenInfo(), 
				context.getScreenInfo().getWidth() / 1.5, 
				context.getScreenInfo().getHeight() / 1.5,
				file
			);
	}
	

	
	/**
	 * Draws and updates the aspects of the world. 
	 * @param context
	 */
	public void drawUpdate(ApplicationContext context) {
		Event event = context.pollOrWaitEvent(45);

		context.renderFrame(graphics -> {
			zoom(graphics, event, context.getScreenInfo());
			players.removeIf(graphics, this, p -> p.isDead());
			players.forEach(player -> {
				player.updatePlayer(context, event, graphics, this);
			});
			walls.fillEntitys(graphics);
			
		});
		
	}
	
	/**
	 * Adds a player to the world
	 * @param fig
	 * @return
	 */
	public boolean add(Player fig) {
		return players.add(fig);
	}
	
	/**
	 * Adds a wall to the world.
	 * @param wall
	 * @return
	 */
	public boolean add(Wall wall) {
		return walls.add(wall);
	}
	
	/**
	 * Return the world fields.
	 * @return world fields.
	 */
	public List<MultiEntity<?>> getWorldFields() {
		return List.of(players, walls);
	}
	
	private boolean zoom(int size, Graphics2D g2, ScreenInfo screen) {
		double x, y, w, h, init_w, init_h;
		init_w = getRectShape().getWidth(); init_h = getRectShape().getHeight();
		w = init_w - size; h = init_h - size;
		x = (screen.getWidth() - w) / 2; y = (screen.getHeight() - h) / 2;
		
		g2.clearRect(0, 0, (int)screen.getWidth(), (int)screen.getHeight());
		getRectShape().setFrame(x, y, w, h);
		fillEntity(g2);
		for (var list : getWorldFields()) {
			for (var entity : list) {
				w = getRectShape().getWidth() / (init_w / entity.getRectShape().getWidth());
				h = getRectShape().getHeight()  / (init_h / entity.getRectShape().getHeight());
				
				entity.getRectShape().setFrame(
						(x - entity.getRectShape().getX()) + w * size,
						(y - entity.getRectShape().getY()) + h * size,
						w, h
					);
				entity.fillEntity(g2);
				
			}
		}
	
		return true;
		
	}
	
	private void zoom(Graphics2D g2, Event event, ScreenInfo screen) {
		if (event != null && event.getAction() == Action.KEY_PRESSED) {
			if (event.getKey() == KeyboardKey.UP) {
				zoom(-1, g2, screen);
			}
			else if (event.getKey() == KeyboardKey.DOWN) {
				zoom(1, g2, screen);
			}
			
		}
	}
	/**
	 * Return the world gravity.
	 * @return the gravity
	 */
	public Gravity getGravity() {
		return gravity.getGravity(players, this);
	}

}

