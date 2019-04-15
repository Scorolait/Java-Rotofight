package fr.umlv.game.file;

import java.awt.Color;
import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import fr.umlv.game.world.Player;
import fr.umlv.game.world.Wall;
import fr.umlv.game.world.World;
import fr.umlv.zen5.KeyboardKey;


/**
 * @author LOPES MENDES Ailton
 * @author LIN Gérald
 *
 */
public class File {
	private final List<String> list;
	private int row;
	private int column;

	/**
	 * Constructs an StringBuilder containing every character in the file
	 * @param file
	 * @throws IOException
	 */
	public File(String file) throws IOException {
		var path = Path.of(Objects.requireNonNull(file));
		if (!Files.exists(path, LinkOption.NOFOLLOW_LINKS)) {
			throw new IllegalAccessError(file + 
					" does not exist in the following directory " + 
					path);
		}
		
		list = new ArrayList<>();
		longestAndRead(path);
	}

	
	/**
	 * Read the file and sets the number of rows and column
	 * @throws IOException 
	 * 
	 */
	private void longestAndRead(Path path) throws IOException {
		try (BufferedReader reader = Files.newBufferedReader(path)) {
			var string = reader.readLine();
			list.add(string);
			int i = 0, j = 0;
			i = string.length();
			while (string != null) {
				list.add(string);
				string = reader.readLine();
				j++;
			}
			row = i - 1; column = j;
		}
	}
	
	private void conditions() {
		var first = list.get(0).toUpperCase();
		var last = list.get(list.size() - 1).toUpperCase();
		if (!first.equals(last)) {
			throw new IllegalStateException("The first and last line were not identical");
		}
		
		for (int i = 0; i < first.length(); i++) {
			if (first.charAt(i) != 'W') {
				throw new IllegalStateException("First line does not contain only walls");
			}
			
			else if (last.charAt(i) != 'W') {
				throw new IllegalStateException("Last line does not contain only walls");
			}
		}
		
		for (var string : list) {
			if (!string.toUpperCase().startsWith("W") || !string.toUpperCase().endsWith("W")) {
				throw new IllegalStateException("The sides are not closed with walls");
			}
			
			else if(string.length() != first.length()) {
				throw new IllegalStateException("Not all the lines have the same size");
			}
		}
	}
	
	/**
	 * Gives the attributes to the world.
	 * @param world
	 * @param file
	 */
	public static void worldCreation(World world, String file) {
		try {
			var worldFile = new File(file);
			double x, y, width, height;
			width = world.getRectShape().getWidth() / worldFile.row;
			height = world.getRectShape().getHeight() / worldFile.column;
			x = world.getRectShape().getX(); y = world.getRectShape().getY();
			worldFile.conditions();
			
			for (var string : worldFile.list) {
				for (int i = 0; i < string.length(); i++) {
					switch(string.toUpperCase().charAt(i)) {
						case 'W': 
							world.add(new Wall(x, y, width, height, Color.BLACK));
							x += width;
							break;
						case 'O':
							KeyboardKey[] keys = {
									KeyboardKey.Z, KeyboardKey.S, KeyboardKey.Q, KeyboardKey.D,
									KeyboardKey.R};
							world.add(new Player(x, y, Math.max(width, height) / 1.5, Color.RED, 20, keys));
							x += width;
							break;
						case 'T':
							KeyboardKey[] keys2 = {
									KeyboardKey.I, KeyboardKey.K, KeyboardKey.J, KeyboardKey.L,
									KeyboardKey.P};
							world.add(new Player(x, y, Math.max(width, height) / 1.5, Color.BLUE, 20, keys2));
							x += width;
							break;
						case ' ':
							x += width;
							break;
						default : break;
					}
				}
				x = world.getRectShape().getX();
				y += height;
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
		
}
