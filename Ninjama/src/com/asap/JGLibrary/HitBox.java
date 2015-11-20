package com.asap.JGLibrary;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.Area;
import java.awt.geom.Path2D;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;

import javax.swing.event.EventListenerList;

import com.asap.JGLibrary.Events.CollisionEvent;
import com.asap.JGLibrary.Events.CollisionListener;
import com.asap.JGLibrary.Utils.Vector2D;
import com.asap.JGLibrary.core.GameObjects.PhysicalObject;

public class HitBox extends Path2D.Double
{
	// Serial VID
	private static final long			serialVersionUID	= 8297889540002061718L;

	private static ArrayList<HitBox>	hitBoxes			= new ArrayList<HitBox>();
	protected EventListenerList			listenerList		= new EventListenerList();

	private Color						color				= Color.RED;
	private PhysicalObject				owner;
	private boolean						isColliding			= false;
	private Vector2D[]					points;

	public static ArrayList<HitBox> getHitBoxes()
	{
		return hitBoxes;
	}

	public Color getColor()
	{
		return this.color;
	}

	public PhysicalObject getOwner()
	{
		return owner;
	}

	public Vector2D[] getPoints()
	{
		return this.points;
	}

	public void setColor(Color color)
	{
		this.color = color;
	}

	public void generatePath(double delta)
	{
		this.reset();
		Vector2D ownerPosition = this.getOwner().getPosition();

		double offsetX = +ownerPosition.getX() + this.getOwner().getVelocity().getX() * delta;
		double offsetY = +ownerPosition.getY() + this.getOwner().getVelocity().getY() * delta;

		offsetX = +ownerPosition.getX();
		offsetY = +ownerPosition.getY();

		for (int i = 0; i < this.getPoints().length; i++)
		{
			if (i == 0)
			{
				this.moveTo(this.getPoints()[i].getX() + offsetX, this.getPoints()[i].getY() + offsetY);
			}
			else
			{
				this.lineTo(this.getPoints()[i].getX() + offsetX, this.getPoints()[i].getY() + offsetY);
			}
		}

		this.lineTo(this.getPoints()[0].getX() + offsetX, this.getPoints()[0].getY() + offsetY);
	}

	public HitBox(PhysicalObject owner, Vector2D[] points)
	{
		this.owner = owner;
		this.points = points;

		this.generatePath(1.0d);
		hitBoxes.add(this);
	}

	public void render(Graphics2D g2d, double delta)
	{
		// Rendering
		g2d.setColor(this.getColor());
		g2d.draw(this);
	}

	public boolean checkCollision()
	{
		updateLogic();
		for (int i = HitBox.getHitBoxes().size() - 1; i >= 0; i--)
		{
			HitBox externalHitBox = HitBox.getHitBoxes().get(i);

			if (externalHitBox.getOwner() != this.getOwner())
			{
				Area thisArea = new Area(this);
				Area otherArea = new Area(externalHitBox);
				thisArea.intersect(otherArea);

				if (!thisArea.isEmpty()) // Si contact entre les deux shapes
				{
					// on Collision enter
					if (!this.isColliding)
					{
						this.isColliding = true;
						this.fireOnCollisionEnter(new CollisionEvent(externalHitBox.getOwner()), thisArea.getBounds2D());
					}

					// on Collision
					this.fireOnCollision(new CollisionEvent(externalHitBox.getOwner()), thisArea.getBounds2D());
				}
				else // Si pas de contact entre les deux shapes
				{

					// on Collision leave
					if (this.isColliding)
					{
						this.isColliding = false;
						this.fireOnCollisionLeave(new CollisionEvent(externalHitBox.getOwner()), thisArea.getBounds2D());
					}
				}
			}
		}

		return this.isColliding;
	}

	public void updateLogic()
	{
		generatePath(1.0d);
		
	}

	// Events
	public void addCollisionListener(CollisionListener listener)
	{
		listenerList.add(CollisionListener.class, listener);
	}

	public void removeCollisionListener(CollisionListener listener)
	{
		listenerList.remove(CollisionListener.class, listener);
	}

	private void fireOnCollision(CollisionEvent evt, Rectangle2D collisionBox)
	{
		Object[] listeners = listenerList.getListenerList();
		for (int i = 0; i < listeners.length; i++)
		{
			if (listeners[i] == CollisionListener.class)
			{
				((CollisionListener) listeners[i + 1]).onCollision(evt, collisionBox);
			}
		}
	}

	private void fireOnCollisionEnter(CollisionEvent evt, Rectangle2D collisionBox)
	{
		Object[] listeners = listenerList.getListenerList();
		for (int i = 0; i < listeners.length; i++)
		{
			if (listeners[i] == CollisionListener.class)
			{
				((CollisionListener) listeners[i + 1]).onCollisionEnter(evt, collisionBox);
			}
		}
	}

	private void fireOnCollisionLeave(CollisionEvent evt, Rectangle2D collisionBox)
	{
		Object[] listeners = listenerList.getListenerList();
		for (int i = 0; i < listeners.length; i++)
		{
			if (listeners[i] == CollisionListener.class)
			{
				((CollisionListener) listeners[i + 1]).onCollisionLeave(evt, collisionBox);
			}
		}
	}
}
