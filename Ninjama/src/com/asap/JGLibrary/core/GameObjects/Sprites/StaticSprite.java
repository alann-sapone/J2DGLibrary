package com.asap.JGLibrary.core.GameObjects.Sprites;

import java.awt.image.BufferedImage;
import java.io.File;

import javax.imageio.ImageIO;

import com.asap.JGLibrary.Utils.AsapError;

public class StaticSprite extends Sprite
{

     // Variables
     BufferedImage image;

     // Construteur
     public StaticSprite(String name, String imagePath)
     {
          super(name);
          try
          {
               this.image = ImageIO.read(new File(imagePath));
          }
          catch (Exception e)
          {
               new AsapError(e, imagePath);
          }
     }

     // Méthodes
     @Override
     public BufferedImage getNextFrame()
     {
          return this.image;
     }
}