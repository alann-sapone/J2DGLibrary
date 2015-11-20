import com.asap.JGLibrary.core.Game;

public class Ninjama extends Game
{
	// Serial VID
	private static final long serialVersionUID = 7155973234130234110L;

	public static void main(String[] args) throws Exception
	{
		new Ninjama("Ninjama 1.0 (Alpha)");
	}

	public Ninjama(String gameName) throws Exception
	{
		super(gameName);
		super.isDebugging(true);

		new Hero("Cube", 350, 110); // Cr�ation d'un sol de type rigidBody et kin�matic
		new Floor("Sol", 100, 500); // Cr�ation d'un cube maniable de type rigidBody et non kin�matic, d�placable
	}

	@Override
	protected void updateGameLogic()
	{

	}
}
