package fr.umlv.game.movement;

import java.util.HashMap;
import java.util.Map;

import fr.umlv.game.world.Entity;
import fr.umlv.game.world.MobileEntity;
import fr.umlv.game.world.MultiEntity;
import fr.umlv.game.world.World;

/**
 * @author LOPES MENDES Ailton
 * @author LIN Gérald
 *
 */
public class Move {
	private final static double frame = 1;
	private final static Map<MobileEntity, Entity> map = new HashMap<>();
	
	private static  double[] ableToMove(MobileEntity e1, World world, double spX, double spY) {
		double move[] = {0, 0};
		double signeX, signeY, x, y;
		 
		signeX = spX < 0 ? -1 : 1; signeY = spY < 0 ? -1 : 1;
	
		while (move[0] * signeX < spX * signeX || move[1] * signeY < spY * signeY) {
			for(var list : world.getWorldFields()) {
			
				x = spX != 0 ? move[0] + frame * signeX : 0;
				y = spY != 0 ? move[1] + frame * signeY : 0;
				if (intersect(e1, list, x, y)) {
					return move;
				}	
			}
			move[0] = spX != 0 ? move[0] + frame * signeX : 0;
			move[1] = spY != 0 ? move[1] + frame * signeY : 0;
		
		}
		move[0] = spX; move[1] = spY;
		return move;
	} 

	private static boolean pixelMove(MobileEntity e1, World world, double spX, double spY) {
		double move[] = ableToMove(e1, world, spX, spY);
		if (move[0] == 0 && move[1] == 0) {
			return false;
		}
		
		move(e1, move[0], move[1]);
		
		return true;
	} 
	
	private static boolean intersect(MobileEntity e1, Entity e2, double spX, double spY) {
		double x, y, w, h;

		x = e1.getRectShape().getMinX() + spX;
		y = e1.getRectShape().getMinY() + spY;
		w = e1.getRectShape().getWidth();
		h = e1.getRectShape().getHeight();
		
		return e2.getRectShape().intersects(x - 1, y - 1, w + 2, h + 2);
	}
		
	private static boolean intersect(MobileEntity e1, MultiEntity<?> multiEntity, double spX, double spY) {
		for (var entity : multiEntity) {
			if (e1 != entity && intersect(e1, entity, spX, spY)) {
				map.put(e1, entity);
				return true;
			}
		}
		return false;
	}
	
	private static void move(MobileEntity entity, double spX, double spY) {
		double x, y, w, h;

		x = entity.getRectShape().getX() + spX; 
		y = entity.getRectShape().getY() + spY;
		w = entity.getRectShape().getWidth(); 
		h = entity.getRectShape().getHeight();
		entity.getRectShape().setFrame(x, y, w, h);
	}
	
	
	
	/**
	 * Moves e1 according to spX and spY of how many pixels before reaching e1 speed. .
	 * @param e1
	 * @param world
	 * @param spX
	 * @param spY
	 * @return
	 */
	public static boolean move(MobileEntity e1, World world, double spX, double spY) {
		if (pixelMove(e1, world, spX, spY)) {
			return true;
		} 
		else {
			return false;
		}
	}
	
	/**
	 * Moves according to the gravity in world.
	 * @param e1
	 * @param world
	 * @return
	 */
	public static boolean moveGravity(MobileEntity e1, World world) {
		return move(e1, world, 
				world.getGravity().getX() * e1.getSpeed(),
				world.getGravity().getY() * e1.getSpeed());
	}
	
	/**
	 * Return true if it's possible to move according to the gravity.
	 * @param e1
	 * @param world
	 * @return
	 */
	public static boolean ableToMoveGravity(MobileEntity e1, World world) {
		double[] m = ableToMove(e1, world, 
				world.getGravity().getX() * e1.getSpeed(),
				world.getGravity().getY() * e1.getSpeed());
		return m[0] != 0 || m[1] != 0;
	}
	

	/**
	 * Return the map of intersection.
	 * @return map
	 */
	public static Map<MobileEntity, Entity> getMap() {
		return map;
	}
	
	
	

}
