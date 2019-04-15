package fr.umlv.game.commands;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import fr.umlv.zen5.KeyboardKey;

public class SetCommands implements Iterable<Controls>{
	private final Set<Controls> com;
	private final Commands command;

	/**
	 * @param com
	 */
	public SetCommands(KeyboardKey[] keys) {
		com = new HashSet<>();
		command = new Commands(keys);
	}

	@Override
	public Iterator<Controls> iterator() {
		return com.iterator();
	}
	
	public boolean add(Controls control) {
		return com.add(control);
	}
	
	public boolean remove(Controls control) {
		
		if (!control.possibleControl()) {
			return com.remove(control);
		}
		return false;
	}
	
	public boolean add(KeyboardKey key) {
		return add(command.getControl(key));
	}
	
	public boolean remove(KeyboardKey key) {
		return remove(command.getControl(key));
	}
	
	public Controls getControl(KeyboardKey key) {
		return command.getControl(key);
	}
	
	public int size() {
		return com.size();
	}
	
	
	
	@Override
	public String toString() {
		StringBuilder s = new StringBuilder();
		for (var control : com) {
			s.append("[ ").append(control).append("]");
		}
		return s.toString();
	}
}
