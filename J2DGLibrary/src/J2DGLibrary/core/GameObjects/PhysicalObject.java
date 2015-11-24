package J2DGLibrary.core.GameObjects;

import java.awt.geom.Rectangle2D;
import java.util.ArrayList;
import java.util.List;

import J2DGLibrary.HitBox;
import J2DGLibrary.Events.CollisionAdapter;
import J2DGLibrary.Events.CollisionEvent;
import J2DGLibrary.Utils.Vector2D;

public abstract class PhysicalObject extends GameObject
{
	// Physics
	public final float		gravityModifier	= 200;
	public final float		G				= 9.80665f / gravityModifier;
	public float			mass			= 60;
	public float			inertia			= G / mass * gravityModifier;
	public final Vector2D	gravityForce	= new Vector2D(0, this.G);
	private List<Vector2D>	forces			= new ArrayList<Vector2D>();
	private Vector2D		velocity		= new Vector2D(0, 0);
	private boolean			isKinematic		= false;
	private boolean			isOnFloor		= false;
	private HitBox			hitBox;
	private float			maxVelocityX	= 3;
	private float			maxVelocityY	= 3;

	// Getters
	public boolean isKinematic()
	{
		return this.isKinematic;
	}

	public boolean isOnFloor()
	{
		return this.isOnFloor;
	}

	public HitBox getHitBox()
	{
		return this.hitBox;
	}

	public float getInertia()
	{
		return this.inertia;
	}

	public Vector2D getVelocity()
	{
		return this.velocity;
	}

	public List<Vector2D> getForces()
	{
		return this.forces;
	}

	public float getMass()
	{
		return this.mass;
	}

	// Setters
	public void isKinematic(boolean bool)
	{
		this.isKinematic = bool;
	}

	public void isOnFloor(boolean onFloor)
	{
		this.isOnFloor = onFloor;
	}

	public void setMass(float mass)
	{
		this.mass = mass;
		this.inertia = G / mass * gravityModifier;
	}

	protected void addForce(Vector2D force)
	{
		if (!this.getForces().contains(force))
		{
			this.getForces().add(force);
		}
	}

	public void attachHitBox(HitBox hitBox)
	{
		this.hitBox = hitBox;
		this.getHitBox().addCollisionListener(new CollisionAdapter() {
			public void onCollisionEnter(CollisionEvent evt, Rectangle2D collisionBounds)
			{
				PhysicalObject.this.solveCollision();
			}

			public void onCollisionLeave(CollisionEvent evt, Rectangle2D collisionBounds)
			{
				//PhysicalObject.this.isOnFloor(false);
			}
		});
	}

	// Constructeur
	public PhysicalObject(String name, boolean isKinematic)
	{
		super(name);
		this.isKinematic = isKinematic;
	}

	public void solveCollision()
	{
		
		/*
		Vector2D oldPosition = this.getPosition();
		if (this.getHitBox().updateCollidingState())
		{
			Vector2D oppositeDirection = Vector2D.sub(new Vector2D(0, 0), this.getDirection());

			oppositeDirection = Vector2D.normalize(oppositeDirection);
			oppositeDirection = Vector2D.multiply(oppositeDirection, 0.5f);

			oldPosition = this.getPosition();
			this.setPosition(Vector2D.add(this.getPosition(), oppositeDirection));
		}
		this.setOldPosition(oldPosition);
		*/
	}

	@Override
	public void applyPhysics()
	{
		Vector2D forceToApply = new Vector2D(0, 0);
		boolean mustApplyInertiaX = false;
		boolean mustApplyInertiaY = false;

		for (int i = this.getForces().size() - 1; i >= 0; i--)
		{
			Vector2D force = this.getForces().get(i);
			forceToApply = Vector2D.add(forceToApply, force);
			this.getForces().remove(force);
		}

		// Inertia X
		if (forceToApply.getX() != 0)
			forceToApply.setX(forceToApply.getX() * this.inertia);
		else
			mustApplyInertiaX = true;

		// Inertia Y
		if (forceToApply.getY() != 0)
			forceToApply.setY(forceToApply.getY() * this.inertia);
		else
			mustApplyInertiaY = true;

		// Adding gravity
		if (!this.isOnFloor() && !this.isKinematic)
			forceToApply.setY(forceToApply.getY() + gravityForce.getY());

		// Adding Force to the current Velocity
		double xVel = this.getVelocity().getX() + forceToApply.getX();
		double yVel = this.getVelocity().getY() + forceToApply.getY();

		// Speed handing
		if (mustApplyInertiaX && xVel > 0)
		{
			xVel -= this.getInertia();
			if (xVel < 0)
				xVel = 0;
		}
		else if (mustApplyInertiaX && xVel < 0)
		{
			xVel += this.getInertia();
			if (xVel > 0)
				xVel = 0;
		}

		if (mustApplyInertiaY && yVel > 0 && this.isKinematic())
		{
			yVel -= this.getInertia();
			if (yVel < 0)
				yVel = 0;
		}
		else if (mustApplyInertiaY && yVel < 0 && this.isKinematic())
		{
			yVel += this.getInertia();
			if (yVel > 0)
				yVel = 0;
		}

		// Speed cap X
		if (xVel < -maxVelocityX)
			xVel = -maxVelocityX;
		else if (xVel > maxVelocityX)
			xVel = maxVelocityX;

		// Speed cap Y
		if (yVel < -maxVelocityY)
			yVel = -maxVelocityY;
		else if (yVel > maxVelocityY)
			yVel = maxVelocityY;

		this.getVelocity().setX(xVel);
		this.getVelocity().setY(yVel);

		// Déplacement en fonction de la vélocité
		this.getPosition().setX(this.getPosition().getX() + this.getVelocity().getX());
		this.getPosition().setY(this.getPosition().getY() + this.getVelocity().getY());
		
		this.solveCollision();
	}
}
