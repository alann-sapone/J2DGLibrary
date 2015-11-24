package J2DGLibrary.Utils;

import java.awt.event.KeyEvent;
import java.lang.reflect.Field;
import java.util.ArrayList;

public class Input
{
     //All registered Inputs
     private static ArrayList<Input> inputs = new ArrayList<Input>();

     public static ArrayList<Input> getInputs()
     {
          return inputs;
     }

     private int     id;
     private boolean isPressed = false;

     //Getters
     public static Input getInput(int id)
     {
          for (Input input : inputs)
          {
               if (input.getId() == id)
                    return input;
          }
          return null;
     }

     public int getId()
     {
          return this.id;
     }

     public boolean isPressed()
     {
          return this.isPressed;
     }

     //Setters
     public void setId(int id)
     {
          this.id = id;
     }

     public void isPressed(boolean isPressed)
     {
          this.isPressed = isPressed;
     }

     //Constructeur
     public Input(int id)
     {
          this.id = id;
          inputs.add(this);
     }

     //Initializer
     public static void initializeAllInputs()
     {
          Field[] fields = KeyEvent.class.getDeclaredFields();
          for (Field field : fields)
          {
               if (field.getName().contains("VK_"))
               {
                    try
                    {
                         new Input(field.getInt(field));
                    }
                    catch (Exception e)
                    {
                         new AsapError(e);
                    }
               }
          }
     }
}