
import java.awt.*;

import javax.swing.*;

import java.util.Random;

public class Canvas extends JPanel{
	
	// variables that get passed through
	protected double angle;
	protected int speed;
	protected String color;
	protected int firework;
	
	// variables calculated within Canvas
	private int width = 850;
	private int height = 600;
	protected double g = 9.86;
	protected double explosionTime;
	protected double x;
	protected double y;
	
	public Canvas(double angle, int speed, String color, int firework){
		setPreferredSize(new Dimension(width, height));
		this.angle = angle;
		this.speed = speed;
		this.color = color;
		this.firework = firework;
	}
	
	// drawing methods
	
	// creates a black canvas space for the fireworks to be rendered in
	public void drawBorder(Graphics g){
		g.setColor(Color.BLACK);
		g.fillRect(0, 0, width-1, height-1);
	}
	
	// creates the 5 different types of firework explosions
	public void drawExplosion(Graphics g){
		
		/* calculating total time the projectile spends in the air in order to create different internal 
		 * fuse times for each of the different firework types. Fuse time is some fraction of tTotal
		 */
		double tPeak = speed/this.g;
		double tTotal = tPeak * 2;
		Random rand = new Random();
		if(firework == 1){
			
			// creates an explosion in the shape of a word
			explosionTime = tTotal/2;
			x = speed*Math.cos(Math.toRadians(angle))*explosionTime;
			y = speed*Math.sin(Math.toRadians(angle))*explosionTime - 0.5*this.g*Math.pow(explosionTime, 2);
			
			g.setColor(Color.GREEN);
			g.drawString("BOOM", (int) x, height - (int) y);
		}else if(firework == 2){
			
			// creates an explosion in the shape of a face 
			explosionTime = tTotal/3;
			x = speed*Math.cos(Math.toRadians(angle))*explosionTime;
			y = speed*Math.sin(Math.toRadians(angle))*explosionTime - 0.5*this.g*Math.pow(explosionTime, 2);
			
			g.setColor(Color.RED);
			g.drawOval((int) x + 20, height - (int) y, 5, 5);
			g.drawOval((int) x + 10, height - (int) y, 5, 5);
			g.setColor(Color.WHITE);
			g.drawLine((int) x + 12, height + 10 - (int) y, (int) x + 22, height + 10 - (int) y);
		}else if(firework == 3){
			
			// creates a standard looking explosion
			explosionTime = tTotal/4;
			x = speed*Math.cos(Math.toRadians(angle))*explosionTime;
			y = speed*Math.sin(Math.toRadians(angle))*explosionTime - 0.5*this.g*Math.pow(explosionTime, 2);
			
			g.setColor(Color.CYAN);
			g.fillOval((int) x, height - (int) y, 10, 10);
			
			// vertical lines of the explosion
			g.drawLine((int) x + 5 , height - 5 - (int) y, (int) x + 5, height - 15 - (int) y);
			g.drawLine((int) x + 5 , height + 15 - (int) y, (int) x + 5, height + 25 - (int) y);
			
			// horizontal lines of the explosion
			g.drawLine((int) x + 15, height + 5 -(int) y, (int) x + 25, height + 5 - (int) y); 
			g.drawLine((int) x - 5, height + 5 -(int) y, (int) x - 15, height + 5 - (int) y); 
		}else if(firework == 4){
			
			// an explosion comprised of nested, randomly sized arcs
			explosionTime = tTotal/5;
			x = speed*Math.cos(Math.toRadians(angle))*explosionTime;
			y = speed*Math.sin(Math.toRadians(angle))*explosionTime - 0.5*this.g*Math.pow(explosionTime, 2);
			for(int i = 0; i <= 2; i++){
				
				int startAngle1 = rand.nextInt((45-0)+1) + 0;
				int arcAngle1 = rand.nextInt((360-0)+1) - 360;
				int startAngle2 = rand.nextInt((90-0)+1) + 0;
				int arcAngle2 = rand.nextInt((270-0)+1) - 270;
				int startAngle3 = rand.nextInt((90-0)+1) - 90;
				int arcAngle3 = rand.nextInt((180-0)+1) - 180;
				
				g.setColor(Color.ORANGE);
				g.drawArc((int) x + 2, height - 28 -(int) y, 100, 100, startAngle1, arcAngle1);
				g.setColor(Color.RED);
				g.drawArc((int) x + 6, height - 18 - (int) y, 75, 75, startAngle2, arcAngle3);
				g.setColor(Color.YELLOW);
				g.drawArc((int) x + 8, height - 12 - (int) y, 50, 50, startAngle3, arcAngle2);
				g.setColor(Color.WHITE);
				g.drawArc((int) x + 10, height - 6 - (int) y, 25, 25, startAngle3, arcAngle2);
			}
		}else if(firework == 5){
			
			// figured out how to make the concentric circles from hw 10 and used that as an explosion type 
			explosionTime = tTotal/6;
			x = speed*Math.cos(Math.toRadians(angle))*explosionTime;
			y = speed*Math.sin(Math.toRadians(angle))*explosionTime - 0.5*this.g*Math.pow(explosionTime, 2);
			
			int r = 10;
			for(int i = 1; i <= 5; i++){
				
				// randomly generates values from 0-255 for r,g,b
				int re = rand.nextInt((255-0)+1) + 0;
				int gr = rand.nextInt((255-0)+1) + 0;
				int bl = rand.nextInt((255-0)+1) + 0;
				g.setColor(new Color(re, gr, bl));
				g.drawOval((int) x, height - (int) y, r, r);
				x -= 5;
				y += 5;
				r += 10;
			}
		}
	}
	
	public void drawTrajectory(Graphics g){
		
		// decodes the hex code from the colors array in the GUI class
		g.setColor(Color.decode(color));
		
		// trajectory will only be drawn until explosionTime - 1 
		for(int i = 0; i <= explosionTime; i++){
			x = speed*Math.cos(Math.toRadians(angle))*i;
			y = speed*Math.sin(Math.toRadians(angle))*i - 0.5*this.g*Math.pow(i, 2);
			g.fillOval((int) x, height - (int) y, 4, 4);
		}
	}
	
	@Override
	public void paintComponent(Graphics g){
		super.paintComponent(g);
		height = getHeight();
		width = getWidth();
		drawBorder(g);
		drawExplosion(g);
		drawTrajectory(g);
	}
}
