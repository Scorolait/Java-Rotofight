package fr.umlv.game.commands;

import java.awt.Graphics2D;
import java.util.function.BiConsumer;

import fr.umlv.game.movement.Direction;
import fr.umlv.game.movement.Move;
import fr.umlv.game.world.MobileEntity;
import fr.umlv.game.world.Player;
import fr.umlv.game.world.Sword;
import fr.umlv.game.world.World;

public enum Controls {
	Up(1, 0), Down(1, 0), Left(1, 0), Right(1, 0), Fire(1, 0), Jump(30, 0), Invalid(0, 0);
	
	private final int times;
	private int done;
	

	/**
	 * @param times
	 * @param done
	 */
	private Controls(int times, int done) {
		this.times = times;
		this.done = done;
	}

	private static boolean jump(Graphics2D g2, Player player, World world) {
		if (Jump.done == 0 && !ableToJump(player, world)) {
			action(g2, player, Controls.Invalid, world);
			return false;
		}
		
		BiConsumer<MobileEntity, World> biConsumer = 
				(p, w) -> { 
					Move.move(p, w,
						world.getGravity().getOppositeX() *  p.getSpeed() * 2,
						world.getGravity().getOppositeY() * p.getSpeed() * 2);
					};
		
		player.move(g2, world, biConsumer);
		Jump.done();
		return true;
	}
	
	private void done() {
		done = done < times ? done + 1 : 0;
	}
	
	
	public static Direction controlDirectionOrDefault(Controls control, Direction dir) {
		switch(control) {
			case Down : return Direction.South;
			case Up : return Direction.Nord;
			case Left : return Direction.East;
			case Right : return Direction.Ouest;
			case Jump : case Fire : case Invalid: return dir;
			default : throw new IllegalStateException("control does not exist");
		}
	}
	
	public static void action(Graphics2D g2, Player player, Controls control, World world) {
		BiConsumer<MobileEntity, World> biConsumer;
		switch (control) {
			case Down: if (!player.setDir(Direction.South, world.getGravity())) {return;}
				break;
			case Fire: var sword = new Sword(player, player.getColor());
				player.add(sword); Fire.done();
				return;
			case Invalid: biConsumer = (p, w) -> {Move.moveGravity(p, w);};
				player.move(g2, world, biConsumer);
				return;
			case Jump: jump(g2, player, world);
				return;
			case Left: if (!player.setDir(Direction.East, world.getGravity())) {return;}
				break;
			case Right: if (!player.setDir(Direction.Ouest, world.getGravity())) {return;}
				break;
			case Up: if (!player.setDir(Direction.Nord, world.getGravity())) {return;}
				break;
			default:
				throw new IllegalStateException("Control does not existed and is not considered as invalid");
		}
		biConsumer = (p, w) -> {Move.move(p, w, p.getDir().getX() * p.getSpeed(), p.getDir().getY() * p.getSpeed());};
		player.move(g2, world, biConsumer); control.done();
	}
	
	
	public boolean possibleControl() {
		return done + 1 < times;
	}
	
	private static boolean ableToJump(Player player, World world) {
		if (Move.ableToMoveGravity(player, world)) {
			return false;
		}
		return true;
	}
	
	@Override
	public String toString() {
		return super.name() + " -> "+ done + " : " + times;
	}
}
