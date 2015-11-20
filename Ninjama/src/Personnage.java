
import com.asap.JGLibrary.core.GameObjects.PhysicalObject;

public class Personnage extends PhysicalObject {

     public Personnage(String name, boolean isKinematic)
     {
          super(name, false);
          // TODO Auto-generated constructor stub
     }

     @Override
     public void updateLogic()
     {
          // TODO Auto-generated method stub
          
     }

	/*//inputs
	Input leftDirection;
	Input rightDirection;
	Input upDirection;
	Input downDirection;
		
	public Personnage(String name, boolean isKinematic) {
		super(name, isKinematic);
		
		leftDirection  = Input.getInput("Left");
		rightDirection = Input.getInput("Right");
		upDirection    = Input.getInput("Up");
		downDirection  = Input.getInput("Down");

		//Etat de course
		AnimatedSprite runningSprite = new AnimatedSprite("Running", Sprite.generateFromSpriteSheet("assets/hero/running.png", 27, 7, 4));
		this.addSprite(runningSprite);
		
		//Etat de saut
		AnimatedSprite jumpingSprite = new AnimatedSprite("Jumping", Sprite.generateFromSpriteSheet("assets/hero/jumping.png", 14, 14, 1));
		this.addSprite(jumpingSprite);
		
		//Etat de non action
		AnimatedSprite idlingSprite = new AnimatedSprite("Idling", Sprite.generateFromSpriteSheet("assets/hero/idle.png", 21, 12, 2));
		this.addSprite(idlingSprite);
		
		this.getPosition().setLocation(0, 0);
	}

	@Override
	public void updateLogic() {
		updateKeys();
		
		if (this.getPosition().getY() >= 300)
		{
			//this.addForce(new Point2D.Double(0, -this.defaultForceY));
			//this.setVelocity(new Point2D.Double(this.getVelocity().getX(), 0));
		}
	}
	
	public void updateKeys()
	{
		if (upDirection.isPressed())
			this.startJump();
		
		/*
		if (leftDirection.getIsPressed())
			this.moveLeft();

		else if (rightDirection.getIsPressed())
			this.moveRight();
		
		//Intérompre un saut en lachant la touche
		if (!this.getIsOnGround() && !upDirection.getIsPressed())
			this.endJump();
		
		//Empêcher le personnage d'avancer en l'air quand la touche est relachée
		if (!this.getIsOnGround() && (!leftDirection.getIsPressed() && !rightDirection.getIsPressed()))
			this.cancelVelocityX();
		
		if (!upDirection.getIsPressed() && !leftDirection.getIsPressed() && !rightDirection.getIsPressed() && this.getIsOnGround())
			this.idle();
			
		
	}
	
	public void startJump()
	{
		this.setCurrentSprite(this.findSprite("Jumping"));
		
		//this.addForce(new Point2D.Double(0, -10));
		//if(this.getIsOnGround())
	    //{
	        //this.setVelocityY(-12.0f);
	        //this.setIsOnGround(false);
	    //}
		
		//this.direction.setY(1);
	}
	
	

	/*
	//Variables
	private Vector2D direction = new Vector2D(0, 0);
	private float 	 basePositionY;

	//Getters
	public Vector2D getDirection()
	{ return direction; }

	//Setters
	public void updateDirection()
	{

		for (State state : this.getStates()) {
			state.getAnimation().setDirection(this.direction);
		}
	}	


	//Constructeur
	public Personnage(String name)
	{
		//super(name);

		leftDirection  = Input.getInput("Left");
		rightDirection = Input.getInput("Right");
		upDirection    = Input.getInput("Up");
		downDirection  = Input.getInput("Down");

		
		
		/*
		State idle = new State("Idling", "assets/hero/idle.png", 21, 12, 2, 0.11f);
		this.addState(idle);

		State running = new State("Running", "assets/hero/running.png", 27, 7, 4, 0.1f);		
		//running.getAnimation().setFirstFrame(10);
		this.addState(running);

		State jumping = new State("Jumping", "assets/hero/jumping.png", 14, 14, 1, 0.2f);
		//jumping.getAnimation().playOnce(true);
		this.addState(jumping);

		//basePositionY = 600 - this.getHeight() + 10;

		
		
		
		this.setPositionX(200);
		this.setPositionY(basePositionY);
	}

	//Traitement des inputs
	
	
	//Méthode d'update
	public void update()
	{
		updateKeys();
		updateDirection();

		/*
		float newPositionX 	   = getPositionX();
		float newPositionY 	   = getPositionY();
		float defaultAnimSpeed = this.getCurrentState().getAnimation().getDefaultSpeed();

		
		if (this.getDirection().getX() != 0)	//Déplacement axe X
		{
			this.setVelocityX(this.getVelocityX() + this.getAccelerationX());
			
			if (this.getVelocityX() > this.getMaxSpeedX())
				this.setVelocityX(this.getMaxSpeedX());

			this.getState("Running").getAnimation().setSpeed(defaultAnimSpeed*this.getVelocityX());

			newPositionX += this.getVelocityX() * this.getDirection().getX();
		}
		
		
		if (this.getDirection().getY() != 0)	//Déplacement axe Y
		{
			this.setVelocityY(this.getVelocityY() + this.getGravity());
			
			this.getState("Running").getAnimation().setSpeed(defaultAnimSpeed*this.getVelocityX());
			
			newPositionY += this.getVelocityY();
			
			if (newPositionY >= this.basePositionY)
			{
				newPositionY = this.basePositionY;
				if (!this.getIsOnGround())
				{
					this.setIsOnGround(true);
					
					setDirection(new Vector2D(this.direction.getX(), 0));
				}
			}
		}
		
		this.setPositionY(newPositionY);
		this.setPositionX(newPositionX);
		
	}

	//Courir à droite
	public void moveRight() {
		if (this.getDirection().getX() == -1)
		{
			this.setVelocityX(0);
		}
			
		
		this.direction.setX(1);		
		//if (this.getIsOnGround())
			//this.setCurrentState(this.getState("Running"));
	}

	//Courir à gauche
	public void moveLeft() {
		if (this.getDirection().getX() == 1)
		{
			this.setVelocityX(0);
		}
		
		this.direction.setX(-1);
		//if (this.getIsOnGround())
			//this.setCurrentState(this.getState("Running"));
	}
	
	//Annuler mouvement (relachement de touche de direction en l'air)
	public void cancelVelocityX()
	{
		this.setVelocityX(0);
	}

	//Sauter
	
	public void endJump()
	{
		if(this.getVelocityY() < -6.0) this.setVelocityY(-6.0f);
	}

	//Crouch
	public void crouch()
	{
		//this.setCurrentState(this.getState("Idling"));
		this.direction.setY(-1);
	}

	//Ne rien faire
	public void idle() {
		//this.setCurrentState(this.getState("Idling"));
		if (this.direction.getY() == 0 && this.direction.getX() == 0)
		{
			this.direction.setX(0);
			this.direction.setY(0);
			this.setVelocityX(0);
		}
	}
	*/
}