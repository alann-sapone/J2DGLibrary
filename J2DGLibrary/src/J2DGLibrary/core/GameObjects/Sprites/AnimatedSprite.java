package J2DGLibrary.core.GameObjects.Sprites;

import java.awt.image.BufferedImage;

public class AnimatedSprite extends Sprite
{
	long			 lastFrameTime	 = System.currentTimeMillis();
	BufferedImage[] images;
	private int	 nextFrameIndex = 0;
	private float	 speedModifier	 = 1;	// animation speed multiplier
	private float	 baseSpeed	 = 50;	// default : 1frame every 50ms

	// Getters
	public float getSpeedModifier()
	{
		return this.speedModifier;
	}

	public float getBaseSpeed()
	{
		return this.baseSpeed;
	}

	// Setters
	public void setSpeedModifier(float speedModifier)
	{
		this.speedModifier = speedModifier;
	}

	public void setBaseSpeed(float baseSpeed)
	{
		this.baseSpeed = baseSpeed;
	}

	public AnimatedSprite(String name, BufferedImage[] images)
	{
		super(name);
		this.images = images;
	}

	// Méthodes
	@Override
	public BufferedImage getNextFrame()
	{
		long now = System.currentTimeMillis();

		BufferedImage nextImage = this.images[nextFrameIndex];

		if (now - lastFrameTime >= this.getBaseSpeed() / this.getSpeedModifier())
		{
			this.nextFrameIndex = (this.nextFrameIndex == this.images.length - 1) ? 0 : this.nextFrameIndex + 1;
			lastFrameTime = System.currentTimeMillis();
		}
		return nextImage;
	}
}