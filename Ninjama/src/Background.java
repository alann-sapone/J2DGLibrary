import com.asap.JGLibrary.core.GameObjects.VisualObject;
import com.asap.JGLibrary.core.GameObjects.Sprites.StaticSprite;

public class Background extends VisualObject{


	public Background(String name) {
		super(name);
		StaticSprite backgroundSprite = new StaticSprite(name, "assets/background.jpg");
		this.getSprites().add(backgroundSprite);
	}

     @Override
     public void updateLogic()
     { }
}