package fr.umlv.game.gravity;

import java.util.ArrayList;
import java.util.List;
import fr.umlv.game.world.MultiEntity;
import fr.umlv.game.world.Player;
import fr.umlv.game.world.World;


/**
 * @author LOPES MENDES Ailton
 * @author LIN Gérald
 *
 */
public class MultiGravity {
	private final int maxTime;
	private int time;
	private int index;
	private final List<Gravity> multiGravity;
	
	/**
	 * Constructs MultiGravity with maxTime and gravities
	 * @throws IllegalArgumentException if maxTime is negative. 
	 * @throws IllegalArgumentException if gravities length is equal to 0.
	 * @param maxTime
	 * @param gravities
	 */
	public MultiGravity(int maxTime, Gravity...gravities) {
		if (maxTime < 0) {
			throw new IllegalArgumentException("Time cannot be negatif");
		}
		
		if (gravities.length <= 0) {
			throw new IllegalArgumentException("No gravity was given");
		}
		
		this.maxTime = maxTime;
		time = 0;
		index = 0;
		this.multiGravity = new ArrayList<>();
		
		for (var gravity : gravities) {
			multiGravity.add(gravity);
		}
	}
	
	/**
	 * Return the current gravity.
	 * @param players
	 * @param world
	 * @return
	 */
	public Gravity getGravity(MultiEntity<Player> players, World world) {
		changeIndex(players, world);
		return multiGravity.get(index % multiGravity.size());
	}
	
	private void changeIndex(MultiEntity<Player> players, World world) {
		if (time >= maxTime) {
			//time = 0;
			//index++;
			for (var player : players) {
				for (var list : world.getWorldFields()) {
					for (var e : list) {
						if (player.intersect(e)) {
							return;
						}
					}
				}					
			}
			time = 0;
			index++;
			multiGravity.get(index % multiGravity.size()).update();
		
		}
		
		else {
			time++;
		}
	}
	
	
}
