package de.whiletrue.processinggame.game.startmenu;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import de.whiletrue.processinggame.rendering.Renderer;

public class ParticleEngine {

	private List<Particle> particles = new ArrayList<>();
	private Renderer renderer;
	private Random rdm;
	
	public ParticleEngine(Renderer renderer) {
		this.renderer = renderer;
		this.rdm = new Random();

		//Shorts the variables for width and height
		int w = renderer.window.width,h = renderer.window.height;
		
		//Iterates over i 20 times, to generate 20 particles
		for(int i = 0; i < 20; i++) {
			//Sets the radius
			int radius = 40;

			int x=0,y=0;
			
			//Generates the width and height inside of the window
			while(x==0&&y==0) {
				//Generates 2 random coordinates on the window
				int nx = this.rdm.nextInt(w-radius*2)+radius;
				int ny = this.rdm.nextInt(h-radius*2)+radius;
				
				//Checks if the coordiantes are valid and dont overlap any other particles
				boolean isColliding = this.particles.stream()
						//Filters any particles that are colliding
						.filter(p->p.distanceTo(nx,ny) < p.radius/2+radius/2)
						.findAny()
						.isPresent();
				
				//Checks if any particles are colliding
				if(!isColliding) {
					//Sets the right values
					x = nx;
					y = ny;
				}
			}

			//Calculates the motion for the paricle
			float motionX = this.rdm.nextBoolean()?2:-2;
			float motionY = this.rdm.nextBoolean()?2:-2;
			
			//Creates the particle
			this.particles.add(new Particle(x, y, radius, 1, motionX, motionY));
		}
	}
	
	/*
	 * Handles every render
	 * */
	public void handleRender(int mouseX,int mouseY,boolean mousePressed) {
		//Renders every particle
		this.particles.forEach(i->i.render(this.renderer));
	}
	
	/*
	 * Handles every tick for the particle
	 * */
	public void handleTick() {
		//Iterates over every particle
		for(int i = 0; i < this.particles.size(); i++) {
			//Gets the particle
			Particle p = this.particles.get(i);
			//Updates the particle
			p.update();
			
			//Iterates over all other particles
			for(int c = 0; c < this.particles.size(); c++) {
				//Checks if the same particle is taged
				if(i==c)
					continue;
				//Gets the secound particle
				Particle p2 = this.particles.get(c);
				
				//Checks if the particles are colliding
				if(p.isColliding(p2))
					this.resolveCollision(p, p2);
			}
			
			//Checks if the particle should bounce of the wall
			if(p.x-p.radius/2<=0) {
				p.motionX*=-1;
				p.x=p.radius/2+1;
			}else if(p.x+p.radius/2>=this.renderer.window.width) {
				p.motionX*=-1;
				p.x=this.renderer.window.width-p.radius/2-1;	
			}
			if(p.y-p.radius/2<=0) {
				p.motionY*=-1;
				p.y=p.radius/2+1;
			}else if(p.y+p.radius/2>=this.renderer.window.height) {
				p.motionY*=-1;
				p.y=this.renderer.window.height-p.radius/2-1;	
			}
			
		}
		
	}
	
	/*
	 * Lets 2 particles bounce of each other when they are colliding
	 * */
	private void resolveCollision(Particle particle,Particle otherParticle) {
		float xVelocityDiff = particle.motionX - otherParticle.motionX;
	    float yVelocityDiff = particle.motionY - otherParticle.motionY;

	    float xDist = otherParticle.x - particle.x;
	    float yDist = otherParticle.y - particle.y;

	    // Prevent accidental overlap of particles
	    if (xVelocityDiff * xDist + yVelocityDiff * yDist >= 0) {

	        // Grab angle between the two colliding particles
	        float angle = (float) -Math.atan2(otherParticle.y - particle.y, otherParticle.x - particle.x);

	        //Store mass in var for better readability in collision equation
	        float m1 = particle.mass;
	        float m2 = otherParticle.mass;

	        // Velocity before equation
	        float[] u1 = rotate(new float[] {particle.motionX,particle.motionY}, angle);
	        float[] u2 = rotate(new float[] {otherParticle.motionX,otherParticle.motionY}, angle);

	        // Velocity after 1d collision equation
	        float[] v1 = new float[]{
	        		u1[0] * (m1 - m2) / (m1 + m2) + u2[0] * 2 * m2 / (m1 + m2),
	        		u1[1]
	        };
	        float[] v2 = new float[] {
        		u2[0] * (m1 - m2) / (m1 + m2) + u1[0] * 2 * m2 / (m1 + m2),
        		u2[1]
	        };

	        // Final velocity after rotating axis back to original location
	        float[] vFinal1 = rotate(v1, -angle);
	        float[] vFinal2 = rotate(v2, -angle);

	        // Swap particle velocities for realistic bounce effect
	        particle.motionX = vFinal1[0];
	        particle.motionY = vFinal1[1];

	        otherParticle.motionX = vFinal2[0];
	        otherParticle.motionY = vFinal2[1];
	    }
	}
	
	private float[] rotate(float[] velocity,float angle) {
	    float[] rotatedVelocities = new float[]{
	        (float) (velocity[0] * Math.cos(angle) - velocity[1] * Math.sin(angle)),
	        (float) (velocity[0] * Math.sin(angle) + velocity[1] * Math.cos(angle))
	    };

	    return rotatedVelocities;
	}
	
	private class Particle {
		
		private float x,y,radius,motionX,motionY,mass;
		
		public Particle(int x,int y,int radius,float mass,float motionX,float motionY) {
			this.x = x;
			this.y = y;
			this.mass = mass;
			this.radius = radius;
			this.motionX = motionX;
			this.motionY = motionY;
		}
		
		/*
		 * Renders the particle
		 * */
		public void render(Renderer renderer) {
			renderer.renderCircleWithStroke(this.x, this.y, this.radius, Color.green.getRGB(), Color.black.getRGB(), 4);
		}
		
		/*
		 * Updates the particle
		 * */
		public void update() {
			this.x+=this.motionX;
			this.y+=this.motionY;
		}
		
		public boolean isColliding(Particle p) {
			return this.distanceTo(p)<this.radius/2+p.radius/2;
		}
		
		/*
		 * Calculates the distance between two particles
		 * */
		private float distanceTo(Particle p) {
			return this.distanceTo(p.x, p.y);
		}
		
		/*
		 * Calculates the distance between the particle and a given position
		 * */
		private float distanceTo(float x,float y) {
			//Calculates using pytagoras the distance between te two particles
			return (float) Math.sqrt(Math.pow(this.x-x, 2)+Math.pow(this.y-y, 2));
		}
	}
}
