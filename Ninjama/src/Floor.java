import com.asap.JGLibrary.HitBox;
import com.asap.JGLibrary.Utils.Vector2D;
import com.asap.JGLibrary.core.GameObjects.PhysicalObject;

public class Floor extends PhysicalObject
{
	public Floor(String name, int posX, int posY)
	{
		super(name, true);

		this.setPosition(new Vector2D(posX, posY));
		this.setOldPosition(new Vector2D(posX, posY));

		this.attachHitBox(new HitBox(this, new Vector2D[] {
			new Vector2D(20, 0),
			new Vector2D(600, 0),
			new Vector2D(600, 60),
			new Vector2D(20, 60)
		}));
	}

	@Override
	public void updateLogic()
	{
		
	}
}
