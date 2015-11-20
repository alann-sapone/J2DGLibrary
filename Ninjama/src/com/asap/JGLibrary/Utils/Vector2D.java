package com.asap.JGLibrary.Utils;

public class Vector2D
{
	private float x;
	private float y;
	
	public Vector2D(float x, float y)
	{
		this.x = x;
		this.y = y;
	}
	
	public Vector2D(Vector2D vector)
	{
		this.x = vector.x;
		this.y = vector.y;
	}
	
	//Getters
	public float getX()
	{
		return this.x;
	}
	
	public float getY()
	{
		return this.y;
	}
	
	//Setters
	public void setX(float x)
	{
		this.x = x;
	}
	
	public void setY(float y)
	{
		this.y = y;
	}

	//Methodes
	public void add(float x, float y)
	{
		this.setX(this.getX() + x);
		this.setY(this.getY() + y);
	}
	
	public void addToX(float x)
	{
		this.setX(this.getX() + x);
	}
	
	public void addToY(float y)
	{
		this.setY(this.getY() + y);
	}
	
	//Override
	@Override 
	public String toString()
	{
		return "X = " + this.getX() + " | Y = " + this.getY();
	}
}
