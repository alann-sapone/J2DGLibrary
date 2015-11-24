package J2DGLibrary.Utils;

public class Vector2D
{
	private double	x;
	private double	y;

	public Vector2D(double x, double y)
	{
		this.x = x;
		this.y = y;
	}

	public Vector2D(Vector2D vector)
	{
		this.x = vector.x;
		this.y = vector.y;
	}

	// Getters
	public double getX()
	{
		return this.x;
	}

	public double getY()
	{
		return this.y;
	}

	// Setters
	public void setX(double x)
	{
		this.x = x;
	}

	public void setY(double y)
	{
		this.y = y;
	}

	// Methodes
	public void addToX(double x)
	{
		this.setX(this.getX() + x);
	}

	public void addToY(double y)
	{
		this.setY(this.getY() + y);
	}

	// Permet de récupérer une direction moyenne entre 2.
	// Exemple, vers le haut et la droite = diagonale haut droite.
	public static Vector2D add(Vector2D vectorA, Vector2D vectorB)
	{
		return new Vector2D(vectorA.getX() + vectorB.getX(), vectorA.getY() + vectorB.getY());
	}

	// Permet de connaitre la direction entre deux vecteur
	// Exemple, déplacer l'objet A vers l'objet B :
	// Il vous faudra connaître la direction dans laquelle déplacer A, la soustraction de vecteurs est inévitable.
	public static Vector2D sub(Vector2D vectorA, Vector2D vectorB)
	{
		return new Vector2D(vectorA.getX() - vectorB.getX(), vectorA.getY() - vectorB.getY());
	}

	public static Vector2D multiply(Vector2D vectorA, double factor)
	{
		return new Vector2D(vectorA.getX() * factor, vectorA.getY() * factor);
	}

	// Permet de normaliser la longeur d'un vecteur à 1
	// pour récupérer la direction seulement (suppression de l'amplitude)
	public static Vector2D normalize(Vector2D vector)
	{
		double length = (Math.sqrt((vector.getX() * vector.getX()) + (vector.getY() * vector.getY())));
		if (length != 0)
			return new Vector2D(vector.getX() / length, vector.getY() / length);
		else
			return vector;
	}

	// Permet de connaitre le cos de l'angle formé par ces deux vecteurs (entre 0 et 90degrés)
	public static double dotProduct(Vector2D normalisedVectorA, Vector2D normalisedVectorB)
	{
		return (normalisedVectorA.getX() * normalisedVectorB.getX()) + (normalisedVectorA.getY() * normalisedVectorB.getY());
	}

	public static double getAngle(Vector2D vectorA, Vector2D vectorB)
	{
		double offX = vectorA.getX() - vectorB.getX();
		double offY = vectorA.getY() - vectorB.getY();
		return Math.atan2(offY, offX);
	}

	// Override
	@Override
	public String toString()
	{
		return "X = " + this.getX() + " | Y = " + this.getY();
	}
}
