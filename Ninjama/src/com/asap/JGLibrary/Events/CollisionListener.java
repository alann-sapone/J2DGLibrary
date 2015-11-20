package com.asap.JGLibrary.Events;

import java.awt.geom.Rectangle2D;
import java.util.EventListener;

public interface CollisionListener extends EventListener
{
	public void onCollision(CollisionEvent evt, Rectangle2D collisionBox);
	public void onCollisionEnter(CollisionEvent evt, Rectangle2D collisionBox);
	public void onCollisionLeave(CollisionEvent evt, Rectangle2D collisionBox);
}
