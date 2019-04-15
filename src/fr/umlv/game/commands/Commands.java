package fr.umlv.game.commands;

import java.util.HashMap;
import java.util.Map;
import fr.umlv.zen5.KeyboardKey;

public class Commands {
	
	private final Map<KeyboardKey, Controls> controls;

	/**
	 * Constructs and initializes a Commands with the specified keys.
	 * @param keys
	 */
	public Commands(KeyboardKey[] keys) {
		controls = new HashMap<KeyboardKey, Controls>();
		setControls(keys);
	}
	
	/**
	 * Sets the controls map with keys.
	 * Respect the order of the enum Controls to attribute the keys.
	 * @param keys
	 */
	private void setControls(KeyboardKey[] keys) {
		var com = Controls.values();
		if (com.length - 2 != keys.length) {
			throw new IllegalArgumentException("Should gave " + com.length + 
					"keys but gave " + keys.length);
		}
		
		for (int i = 0; i < keys.length; i++) {
			controls.put(keys[i], com[i]);
		}
		controls.put(KeyboardKey.UNDEFINED, Controls.Invalid);
		
		if (controls.size() != com.length - 1) {
			throw new IllegalArgumentException("Not all keys are different");
		}
	}
	
	/**
	 * @param key
	 * @return the command
	 */
	public Controls getControl(KeyboardKey key) {
		return controls.getOrDefault(key, Controls.Invalid);
	}
	
	
}
