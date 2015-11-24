package J2DGLibrary.Events;

import java.util.EventObject;

import J2DGLibrary.core.GameObjects.PhysicalObject;

public class CollisionEvent extends EventObject
{
	private static final long serialVersionUID = 7031234987962847675L;

	public CollisionEvent(PhysicalObject source)
	{
		super(source);
	}
}
