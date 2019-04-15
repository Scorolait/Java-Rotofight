package fr.umlv.game.main;

import java.awt.Color;
import java.awt.geom.Rectangle2D;

import fr.umlv.game.world.*;
import fr.umlv.zen5.Application;
import fr.umlv.zen5.ScreenInfo;



/**
 * @author LOPES MENDES Ailton
 * @author LIN Gérald
 *
 */
public class Main {
	
	public static void main(String[] args) {
		Application.run(Color.BLACK, context -> {
		      
			ScreenInfo screenInfo = context.getScreenInfo();
			double width = screenInfo.getWidth();
			double height = screenInfo.getHeight();
			
			var world = new World(context, "worlds/world1.txt");

				context.renderFrame(graphics -> {
					graphics.setColor(Color.BLACK);
			    	graphics.fill(new  Rectangle2D.Double(0, 0, width, height));
			    	world.fillEntity(graphics);
				});
				
				for(;;) {
					
				    world.drawUpdate(context);
				}
					
		});
	}

}
