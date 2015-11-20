package com.asap.JGLibrary.core.GameObjects;

import java.awt.geom.Rectangle2D;
import java.util.ArrayList;
import java.util.List;

import com.asap.JGLibrary.HitBox;
import com.asap.JGLibrary.Events.CollisionAdapter;
import com.asap.JGLibrary.Events.CollisionEvent;
import com.asap.JGLibrary.Utils.Vector2D;

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
	private Vector2D		maxVelocityX	= new Vector2D(-3, 3);
	private Vector2D		maxVelocityY	= new Vector2D(-7, 7);

	// Getters
	public boolean IsKinematic()
	{
		return this.isKinematic;
	}

	public boolean IsOnFloor()
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
	public void IsKinematic(boolean bool)
	{
		this.isKinematic = bool;
	}

	public void IsOnFloor(boolean onFloor)
	{
		this.isOnFloor = onFloor;
	}

	public void setMass(float mass)
	{
		this.mass = mass;
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
		if (!this.IsKinematic())
		{
			this.getHitBox().addCollisionListener(new CollisionAdapter() {
				public void onCollisionEnter(CollisionEvent evt, Rectangle2D collisionBounds)
				{
					System.out.println("collision");
					//double direction = PhysicalObject.this.getDirection();
					//double reversedDirection = direction + Math.PI;
					
					while (PhysicalObject.this.getHitBox().checkCollision())
					{
						PhysicalObject.this.getPosition().setY(PhysicalObject.this.getPosition().getY()-1);
					}
					
					//PhysicalObject.this.getPosition().setY(PhysicalObject.this.getPosition().getY() + 15);
					
					

					//PhysicalObject.this.getVelocity().setY(0);
					//PhysicalObject.this.getPosition().setX((float)collisionBounds.getMinX());
					//PhysicalObject.this.getPosition().setY((float)collisionBounds.getMinY() - (float)PhysicalObject.this.getHitBox().getBounds().getHeight() + 2);
					//System.out.println("test");
					PhysicalObject.this.IsOnFloor(true);
				}

				public void onCollisionLeave(CollisionEvent evt, Rectangle2D collisionBounds)
				{
					System.out.println("fin de collision");
					PhysicalObject.this.IsOnFloor(false);
				}
			});
		}
	}

	// Constructeur
	public PhysicalObject(String name, boolean isKinematic)
	{
		super(name);
		this.isKinematic = isKinematic;
	}

	@Override
	public void applyPhysics()
	{
		Vector2D forceToApply = new Vector2D(0, 0);
		boolean mustInertiaX = false;

		if (this.forces.size() == 0)
			mustInertiaX = true;

		for (int i = this.getForces().size() - 1; i >= 0; i--)
		{
			Vector2D force = this.getForces().get(i);
			forceToApply.add(force.getX(), force.getY());
			this.getForces().remove(force);
		}

		if (forceToApply.getX() != 0)
			forceToApply.setX(forceToApply.getX() * this.inertia);

		// Adding gravity
		if (!this.IsOnFloor() && !this.isKinematic)
			forceToApply.setY(forceToApply.getY() + gravityForce.getY());

		// Adding Force to the current Velocity
		float xVel = this.getVelocity().getX() + forceToApply.getX();

		//Speed handing
		if (mustInertiaX && xVel > 0)
		{
			xVel -= this.getInertia();
			if (xVel < 0)
				xVel = 0;
		}
		else if (mustInertiaX && xVel < 0)
		{
			xVel += this.getInertia();
			if (xVel > 0)
				xVel = 0;
		}

		float yVel = this.getVelocity().getY() + forceToApply.getY();

		// Speed cap X
		if (xVel < maxVelocityX.getX())
			xVel = maxVelocityX.getX();
		else if (xVel > maxVelocityX.getY())
			xVel = maxVelocityX.getY();

		// Speed cap Y
		if (yVel < maxVelocityY.getX())
			yVel = maxVelocityY.getX();
		else if (yVel > maxVelocityY.getY())
			yVel = maxVelocityY.getY();

		this.getVelocity().setX(xVel);
		this.getVelocity().setY(yVel);

		// Déplacement en fonction de la vélocité
		this.getPosition().setX(this.getPosition().getX() + this.getVelocity().getX());
		this.getPosition().setY(this.getPosition().getY() + this.getVelocity().getY());
	}
}
