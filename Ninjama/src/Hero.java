import java.awt.Color;
import java.awt.event.KeyEvent;
import java.awt.geom.Rectangle2D;

import com.asap.JGLibrary.HitBox;
import com.asap.JGLibrary.Events.CollisionAdapter;
import com.asap.JGLibrary.Events.CollisionEvent;
import com.asap.JGLibrary.Utils.Input;
import com.asap.JGLibrary.Utils.Vector2D;
import com.asap.JGLibrary.core.GameObjects.PhysicalObject;

public class Hero extends PhysicalObject
{
	public Hero(String name, int posX, int posY)
	{
		super(name, false);

		this.setMass(10);

		this.setPosition(new Vector2D(posX, posY));
		this.setOldPosition(new Vector2D(posX, posY));

		// Création de la hitbox
		this.attachHitBox(new HitBox(this, new Vector2D[] { 
			new Vector2D(0, 0),
			new Vector2D(60, 60),
			new Vector2D(120, 0)
		}));

		this.getHitBox().addCollisionListener(new CollisionAdapter() {
			public void onCollisionEnter(CollisionEvent evt, Rectangle2D collisionBounds)
			{
				PhysicalObject otherGameObject = (PhysicalObject) evt.getSource();
				otherGameObject.getHitBox().setColor(Color.CYAN);
			}

			public void onCollisionLeave(CollisionEvent evt, Rectangle2D collisionBounds)
			{
				PhysicalObject otherGameObject = (PhysicalObject) evt.getSource();
				otherGameObject.getHitBox().setColor(Color.RED);
			}
		});

		/*
		AnimatedSprite sprite = new AnimatedSprite("Idle", Sprite.generateFromSpriteSheet("assets/hero/idle.png", 21, 12, 2));
		this.getSprites().add(sprite);
		this.setCurrentSprite(sprite);
		*/
	}

	@Override
	public void updateLogic()
	{
		updateKeys();
		System.out.println(this.IsOnFloor());
	}

	private void updateKeys()
	{
		if (Input.getInput(KeyEvent.VK_UP).isPressed())
			jump();

		if (Input.getInput(KeyEvent.VK_LEFT).isPressed())
			this.addForce(new Vector2D(-1, 0));

		if (Input.getInput(KeyEvent.VK_RIGHT).isPressed())
			this.addForce(new Vector2D(1, 0));
	}

	private void jump()
	{
		if (this.IsOnFloor())
		{
			this.addForce(new Vector2D(0, -4.2f));
		}
	}
}
