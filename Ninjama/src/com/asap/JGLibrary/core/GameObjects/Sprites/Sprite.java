package com.asap.JGLibrary.core.GameObjects.Sprites;

import java.awt.image.BufferedImage;
import java.io.File;

import javax.imageio.ImageIO;

import com.asap.JGLibrary.Utils.AsapError;

public abstract class Sprite
{

     // Variables
     private String name;

     // Getters
     public String getName()
     {
          return this.name;
     }

     // Constructeur
     public Sprite(String name)
     {
          this.name = name;
     }

     // Méthodes
     public abstract BufferedImage getNextFrame();

     public static BufferedImage[] generateFromSpriteSheet(String spriteSheetPath, int framesNb, int colNb, int rowNb)
     {
          BufferedImage spriteSheetImage = null;

          try
          {
               spriteSheetImage = ImageIO.read(new File(spriteSheetPath));

               BufferedImage[] images = new BufferedImage[framesNb];

               int spriteSheetWidth = spriteSheetImage.getWidth();
               int spriteSheetHeight = spriteSheetImage.getHeight();

               int spriteWidth = spriteSheetWidth / colNb;
               int spriteHeight = spriteSheetHeight / rowNb;

               int frameIndex = 0;
               for (int i = 0; i < rowNb; i++)
               {
                    for (int j = 0; j < colNb; j++)
                    {
                         if (frameIndex < framesNb)
                         {
                              BufferedImage subImage = spriteSheetImage.getSubimage(spriteWidth * j, spriteHeight * i, spriteWidth, spriteHeight);
                              images[frameIndex] = subImage;
                              frameIndex++;
                         }
                    }
               }
               return images;
          }
          catch (Exception e)
          {
               new AsapError(e);
          }

          return null;
     }
}
