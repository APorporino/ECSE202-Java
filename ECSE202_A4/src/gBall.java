import java.awt.Color;
import acm.graphics.GOval;

public class gBall extends Thread{
	
	double Xi;
	double Yi;
	double bSize;
	Color bColor;
	double bLoss;
	double bVel;
	GOval myBall;
	boolean Running;

	public gBall(double Xi, double Yi, double bSize, Color bColor, double bLoss, double bVel, boolean Running) {
		
		// Get simulation parameters
		this.Xi = Xi;
		this.Yi = Yi;
		this.bSize = bSize;
		this.bColor = bColor;
		this.bLoss = bLoss;
		this.bVel = bVel;
		this.Running = Running; //new boolean argument of gBall to indicate if ball is in motion

		// Create instance of GOval with specified parameters
		myBall = new GOval(10*Xi, (600 - (10*Yi)), 10*bSize, 10*bSize);
		myBall.setFilled(true);
		myBall.setColor(bColor);
		
		
	}

	public void run() {
		//Code from assignment 1:
		this.Running = true; //set running back to true
		// Constants
		final double TIME_INTERVAL = 0.1;
		final double GRAVITY = 9.8;
		
		//Initialize the variables
		double time = 0;
		boolean directionUp = false;
		double height = 0;
		double initialUpPosition = 0;
		double vt = Math.sqrt(2*(GRAVITY)*Yi);
		double energy_loss_factor = Math.sqrt(1-bLoss);
		double xPos = Xi;
		
		//Simulation loop 
		//The loop will now end when the balls run out of energy in the y direction instead of based on time
		
		while ((vt > 0.1) && (this.Running == true)) { //When vt < 0.1 the ball runs out of energy in the y direction and will stop
			if (directionUp == false) { //Ball is descending 
				height = Yi - 0.5*GRAVITY*Math.pow(time, 2); //height of ball decreases
				if (height <= 20)  { //Ball hits the ground
					height = 20; //to make sure ball does not go below the floor
					Yi = height;
					initialUpPosition = height;
					directionUp = true;
					time = 0;
					vt = vt*energy_loss_factor; 

				}
			}

			else //Direction is up
			{ 
				height = initialUpPosition + vt*time - 0.5*GRAVITY*Math.pow(time, 2); //height of ball now increases
				if (height > Yi) { //ball continues to ascend
					Yi = height;
				}
				else { //ball reached its peak
					directionUp = false;
					time = 0;
				}
			}

			//Update xPos, time variable 
			xPos += 10*bVel*TIME_INTERVAL;
			time += TIME_INTERVAL;
			
			//Allows us to see the balls fall
			try {
				// pause for 50 milliseconds
				Thread.sleep(50);
			} catch (InterruptedException e) {
				e.printStackTrace(); }

			//change ball position after each time step
			if (myBall != null) myBall.setLocation(xPos, (800 - (10*height)) - 10*bSize);
		
		}
		this.Running = false; //change boolean to false to indicate that this ball has finished running
	
	}
	
	//this method will move the position of a ball to the indicated position
	void moveTo(double x, double y) {
		this.myBall.setLocation(x,y);
	}
	
	//used in bTree clear method to clear all the balls on the screen
	public void delete() { 
		this.myBall.setVisible(false); //makes the ball not visible
		//this.myBall = null; //erases the gOval 
	
	}


}
