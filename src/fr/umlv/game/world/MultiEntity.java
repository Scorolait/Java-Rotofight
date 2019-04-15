package fr.umlv.game.world;
import java.awt.Graphics2D;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import java.util.function.Predicate;



/**
 * @author LOPES MENDES Ailton
 * @author LIN Gérald
 * 
 * @param <T>
 */
public class MultiEntity<T extends Entity> implements Iterable<T>{
	
	private Set<T> entitys;
	
	/**
	 * Constructs a MultiFigures.
	 */
	public MultiEntity() {
		entitys = new HashSet<>();
	}

	/**
	 * Returns true if the figure was added, otherwise returns false.
	 * @param fig
	 * @return
	 */
	public boolean add(T fig) {
		return entitys.add(fig);
	}
	
	/**
	 * Removes Object o of our set.
	 * @param o
	 * @return entitys.remove(o);
	 */
	public boolean remove(Object o) {
		return entitys.remove(o);
	}
	
	
	/**
	 * Fills all the entity.
	 * @param g2
	 */
	public void fillEntitys(Graphics2D g2) {
		for (var entity : entitys) {
			entity.fillEntity(g2);
		}
	}
	
	/**
	 * Removes an element if filter is true and returns true otherwise returns false.
	 * @param g2
	 * @param world
	 * @param filter
	 * @return true or false
	 */
	public boolean removeIf(Graphics2D g2, World world, Predicate<T> filter) {
		var ite = entitys.iterator();
		while (ite.hasNext()) {
			var entity = ite.next();
			if (filter.test(entity)) {
				entity.fillEntity(g2, world.getColor());
				ite.remove();
				return true;
			}
		}
		return false;
	}
	
	/**
	 * Return the size of the set.
	 * @return entitys.size()
	 */
	public int size() {
		return entitys.size();
	}

	@Override
	public Iterator<T> iterator() {
		return entitys.iterator();
	}
	
}
