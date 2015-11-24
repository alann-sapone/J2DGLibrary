package J2DGLibrary.core;

import java.awt.BufferCapabilities;
import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.image.BufferStrategy;
import java.lang.reflect.Constructor;

import javax.swing.JFrame;

import J2DGLibrary.Utils.Input;
import J2DGLibrary.core.GameObjects.GameObject;
import J2DGLibrary.core.GameObjects.PhysicalObject;

public abstract class Game extends Canvas implements Runnable
{
	// Serial VID
	private static final long	serialVersionUID	= 4533321334383830113L;

	// Game definition
	private static boolean		debugging			= false;
	private static final int	ups					= 120;
	private static String		name				= "My Game";
	private static int			width				= 1024;
	private static int			height				= 576;
	private static JFrame		frame				= new JFrame(name);
	private static boolean		running				= false;

	// Getters
	public static boolean isRunning()
	{
		return Game.running;
	}

	private static boolean isDebugging()
	{
		return debugging;
	}

	// Setters
	public static void isRunning(boolean newState)
	{
		Game.running = newState;
	}

	public static void isDebugging(boolean state)
	{
		debugging = true;
	}

	public Game(String gameName) throws Exception
	{
		this(gameName, Game.width, Game.height);
	}

	// Constructeur
	public Game(String gameName, int gameWidth, int gameHeight) throws Exception
	{
		Game.name = gameName;
		Game.width = gameWidth;
		Game.height = gameHeight;

		// Generate all inputs instances
		Input.initializeAllInputs();

		// Preparing key captation
		this.addKeyListener(new KeyAdapter() {
			public void keyPressed(KeyEvent keyEvt)
			{
				processKey(keyEvt);
			}

			public void keyReleased(KeyEvent keyEvt)
			{
				processKey(keyEvt);
			}
		});

		// Preparing this canvas
		this.setPreferredSize(new Dimension(Game.width, Game.height));
		this.setFocusable(true);

		// Creation of the Frame
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setIgnoreRepaint(true);
		frame.setResizable(false);
		frame.setTitle(Game.name);

		frame.add(this);
		frame.pack();
		frame.setVisible(true);

		// Creating the buffer
		this.createBufferStrategy(2);
		this.forceVSync(this.getBufferStrategy());

		// Running the Thread of the game loop
		new Thread(this).start();
		Game.isRunning(true);
	}

	public void processKey(KeyEvent keyEvt)
	{
		for (Input input : Input.getInputs())
		{
			if (keyEvt.getKeyCode() == input.getId())
				input.isPressed(keyEvt.getID() == KeyEvent.KEY_PRESSED);
		}
	}

	@Override
	public void run()
	{
		final int skipUps = 1000 / ups;
		final int maxFrameSkip = 5;
		double nextGameTick = System.currentTimeMillis();
		int loops;
		int fpsCount = 0;
		double fpsTimerStart = System.currentTimeMillis();

		while (Game.isRunning())
		{
			loops = 0;
			while (System.currentTimeMillis() > nextGameTick && loops < maxFrameSkip)
			{
				updateGlobalLogic();
				nextGameTick += skipUps;
				loops++;
			}

			double delta = (double) (System.currentTimeMillis() + skipUps - nextGameTick) / (double) skipUps;
			render(delta);

			if (System.currentTimeMillis() - fpsTimerStart >= 1000)
			{
				frame.setTitle(Game.name + " - FPS : " + fpsCount);
				fpsTimerStart = System.currentTimeMillis();
				fpsCount = 0;
			}
			else
				fpsCount++;
		}
	}

	private void updateGlobalLogic()
	{
		// Updating game logic
		this.updateGameLogic();

		// Updating GameObjects logic and physics
		// Loop reversed to avoid concurrent modifying
		for (GameObject gObject : GameObject.getAllGameObjects())
		{
			gObject.saveCurrentState();
			gObject.updateLogic();
			gObject.applyPhysics();
			gObject.saveCurrentDirection();
			if (gObject instanceof PhysicalObject)
			{
				PhysicalObject pObject = (PhysicalObject) gObject;
				if (pObject.getHitBox() != null)
				{
					pObject.getHitBox().updateLogic();
				}
			}
		}
	}

	protected abstract void updateGameLogic();

	private void render(double delta)
	{
		BufferStrategy buffer = this.getBufferStrategy();
		Graphics2D g2d = (Graphics2D) buffer.getDrawGraphics();

		// Anti-aliasing
		g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

		// Blacking the background
		g2d.setColor(Color.BLACK);
		g2d.fillRect(0, 0, getWidth(), getHeight());

		// Redrawing each GameObject
		for (int i = GameObject.getAllGameObjects().size() - 1; i >= 0; i--)
		{
			GameObject gObject = GameObject.getAllGameObjects().get(i);
			gObject.render(g2d, delta);

			if (Game.isDebugging())
			{
				if (gObject instanceof PhysicalObject)
				{
					PhysicalObject pObject = (PhysicalObject) gObject;
					if (pObject.getHitBox() != null)
					{
						pObject.getHitBox().render(g2d, delta);
					}
				}
			}
		}

		g2d.dispose();
		buffer.show();
	}

	private void forceVSync(BufferStrategy bufferStrategy) throws Exception
	{
		if (bufferStrategy != null)
		{
			Class<?> ebcClass = Class.forName("sun.java2d.pipe.hw.ExtendedBufferCapabilities");
			Class<?> vstClass = Class.forName("sun.java2d.pipe.hw.ExtendedBufferCapabilities$VSyncType");

			Constructor<?> ebcConstructor = ebcClass.getConstructor(new Class[] { BufferCapabilities.class, vstClass });

			BufferCapabilities newCaps = (BufferCapabilities) ebcConstructor.newInstance(new Object[] { bufferStrategy.getCapabilities(), vstClass.getField("VSYNC_ON").get(null) });

			if (newCaps.isMultiBufferAvailable())
			{
				createBufferStrategy(2, newCaps);
			}
		}
	}
}
