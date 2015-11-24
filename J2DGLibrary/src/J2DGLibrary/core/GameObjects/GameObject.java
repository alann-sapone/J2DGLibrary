package J2DGLibrary.core.GameObjects;

import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import J2DGLibrary.HitBox;
import J2DGLibrary.Utils.Vector2D;
import J2DGLibrary.core.GameObjects.Sprites.Sprite;

public abstract class GameObject
{
	private static List<GameObject>	gameObjects	= Collections.synchronizedList(new ArrayList<GameObject>());

	// GameObject Definition
	private String					name;
	private Vector2D				position	= new Vector2D(0, 0);
	private Vector2D				oldPosition	= new Vector2D(0, 0);
	private Vector2D				direction	= new Vector2D(0, 0);;
	// Visuels
	private List<Sprite>			sprites		= new ArrayList<Sprite>();									// Contient la liste des sprites (ex : Idle, Walking, etc...)
	private Sprite					currentSprite;

	// Getters
	public static List<GameObject> getAllGameObjects()
	{
		return gameObjects;
	}

	public String getName()
	{
		return this.name;
	}

	public Vector2D getPosition()
	{
		return this.position;
	}

	public Vector2D getOldPosition()
	{
		return this.oldPosition;
	}

	public List<Sprite> getSprites()
	{
		return this.sprites;
	}

	public Sprite getCurrentSprite()
	{
		return this.currentSprite;
	}

	public Vector2D getDirection()
	{
		return this.direction;
	}

	// Setters
	public void setCurrentSprite(Sprite currentSprite)
	{
		this.currentSprite = currentSprite;
	}

	public void setPosition(Vector2D position)
	{
		this.position = position;
	}

	public void setOldPosition(Vector2D oldPosition)
	{
		this.oldPosition = oldPosition;
	}

	void setDirection(Vector2D angle)
	{
		this.direction = angle;
	}

	// Constructeur
	public GameObject(String name)
	{
		this.name = name;
		GameObject.gameObjects.add(this);
	}

	public static void destroy(GameObject gameObject)
	{
		if (gameObject instanceof PhysicalObject)
		{
			PhysicalObject rigidBody = (PhysicalObject) gameObject;
			if (rigidBody.getHitBox() != null)
			{
				HitBox hitbox = rigidBody.getHitBox();
				HitBox.getHitBoxes().remove(hitbox);
			}
		}
		gameObjects.remove(gameObject);
	}

	public abstract void applyPhysics();

	public abstract void updateLogic();

	public void render(Graphics2D g2d, double delta)
	{
		double estimatedX = this.getPosition().getX();
		double estimatedY = this.getPosition().getY();

		if (this instanceof PhysicalObject)
		{
			PhysicalObject obj = (PhysicalObject) this;
			estimatedX = this.getPosition().getX() + obj.getVelocity().getX() * delta;
			estimatedY = this.getPosition().getY() + obj.getVelocity().getY() * delta;
		}

		if (currentSprite != null)
		{
			// render Sprite
			AffineTransform tx = new AffineTransform();
			tx.translate(estimatedX, estimatedY);
			g2d.drawImage(this.currentSprite.getNextFrame(), tx, null);
		}
	}

	// Méthodes
	public Sprite findSprite(String name)
	{
		for (Sprite sprite : this.sprites)
		{
			if (sprite.getName().equals(name))
				return sprite;
		}
		return null;
	}

	public void saveCurrentState()
	{
		this.setOldPosition(new Vector2D(this.getPosition().getX(), this.getPosition().getY()));
	}

	public void saveCurrentDirection()
	{
		//Direction from position to OldPosition (A -> B = VectorB - VectorA)
		Vector2D newDirection = Vector2D.normalize(Vector2D.sub(this.getPosition(), this.getOldPosition()));
		this.setDirection(newDirection);
	}
}
