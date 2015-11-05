package co.za.jjs;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.awt.image.WritableRaster;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JPanel;

public class Board extends JPanel
        implements Runnable {

    private final int B_WIDTH = 600;//350;
    private final int B_HEIGHT = 600;//350;
    private final int INITIAL_X = -40;
    private final int INITIAL_Y = -40;
    private final int DELAY = 25;
    private Lock lock = new ReentrantLock();
    private BufferedImage star;
    private Thread animator;
    private int x, y;
    private ArrayList<Integer> keyList = new ArrayList<Integer>();
    private boolean playPressed = false;
    private boolean dumpAndExit = false;
    private boolean startdumping = false;

    public Board() {

        initBoard();
    }

    private void loadImage() {

    //star = ImageIO.read(new File("/home/johan/images.jpeg"));
	star = new BufferedImage(416, 284, BufferedImage.TYPE_INT_RGB);
    }

    private void initBoard() {

        setBackground(Color.BLACK);
        setPreferredSize(new Dimension(B_WIDTH, B_HEIGHT));
        setDoubleBuffered(true);
        
        addKeyListener(new CustomKeyListener(lock, this.keyList ));
        this.setFocusable(true);

        loadImage();

        x = INITIAL_X;
        y = INITIAL_Y;
    }

    @Override
    public void addNotify() {
        super.addNotify();

        animator = new Thread(this);
        animator.start();
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        drawStar(g);
    }

    private void drawStar(Graphics g) {
//BufferedImage dd=new bu;
//g.drawImage(dd,x,y,this);
//dd.
        g.drawImage(star, 1, 1, this);
        Toolkit.getDefaultToolkit().sync();
    }

    private void cycle() {

        x += 1;
        y += 1;

        if (y > B_HEIGHT) {
        	int w= star.getWidth(); 
        	int h= star.getHeight();
        	int[] pixels = new int[w*h*3];
        	WritableRaster raster = star.getRaster();
        	raster.getPixels(0, 0, w, h, pixels);

        	
        	for (int i =0; i<20000; i++) {
        		if ((i % 3) == 2)
        		  pixels[i] = 255;
        		  else
        			  pixels[i] = 0;
        	}
        	
        	star.getRaster().setPixels(0, 0, w, h, pixels);  
            y = INITIAL_Y;
            x = INITIAL_X;
        }
    }
    
    public int[] getCopyOfKeys() {
    	//lock.lock();
    	int[] keys = new int[keyList.size()];
    	for (int i=0; i<keyList.size(); i++)
    		keys[i] = this.keyList.get(i);
    	//lock.unlock();
    	//System.out.println(x);
    	return keys;
    }

    @Override
    public void run() {

        long beforeTime, timeDiff, sleep;

        beforeTime = System.currentTimeMillis();
        int temp = 0;
        Machine machine = new Machine();
        /*//characters.901225-01.bin
        byte[] charRom= new byte[4 * 1024];
		FileInputStream fis = null;
		try {
			fis = new FileInputStream(new File("/home/johan/characters.901225-01.bin"));
		} catch (FileNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		try {
			fis.read(charRom);

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}*/

        while (true) {
        	if (dumpAndExit) {
        		//machine.getDumpOfMainMem().
        		FileOutputStream fos;
				try {
					fos = new FileOutputStream(new File("/home/johan/dump.bin"));
					fos.write(machine.getDumpOfMainMem());
					fos.close();
					fos = new FileOutputStream(new File("/home/johan/dumpcolorram.bin"));
					fos.write(machine.getDumpOfScreenColor());
					fos.close();
					
				} catch (FileNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
        		
        		break;
        	}
        	if (startdumping) {
        		machine.setStartDumping(true);
        	}
        	int w= star.getWidth(); 
        	int h= star.getHeight();
        	temp = temp % 3;
        	int[] pixels = new int[w*h*3];
        	WritableRaster raster = star.getRaster();
        	//raster.getPixels(0, 0, w, h, machine.getFrame());
        	lock.lock();
        	machine.setKeys(getCopyOfKeys());
        	machine.setPlayPressed(playPressed);
        	/*int[] videoram = machine.getTextRAM();
        	for (int i = 0; i < 1000; i++) {
        		for (int row = 0; row < 8; row++) {
        			int charRow = charRom[videoram[i] * 8 + row] & 0xff;
        			for (int col = 0; col < 8; col++) {
        				int curentrow = (i /40);
        				curentrow = curentrow * 8 + row;
        				int currentcol = ((i % 40) * 8) + col;
        				if ((charRow & (1 << (7-col))) != 0) {
        					pixels[curentrow * 320 * 3 + currentcol * 3 + 0] = 0;
        					pixels[curentrow * 320 * 3 + currentcol * 3 + 1] = 0;
        					pixels[curentrow * 320 * 3 + currentcol * 3 + 2] = 0;
        				} else {
        					pixels[curentrow * 320 * 3 + currentcol * 3 + 0] = 255;
        					pixels[curentrow * 320 * 3 + currentcol * 3 + 1] = 255;
        					pixels[curentrow * 320 * 3 + currentcol * 3 + 2] = 255;
        					
        				}
        			}
        		}
        	}*/
        	/*for (int i =0; i<190000; i++) {
        		if ((i % 3) == temp)
        		  pixels[i] = 255;
        		  else
        			  pixels[i] = 0;
        	}*/
        	temp++;
        	
        	star.getRaster().setPixels(0, 0, w, h, machine.getFrame());  

            //cycle();
            repaint();

            timeDiff = System.currentTimeMillis() - beforeTime;
            sleep = DELAY - timeDiff;

            if (sleep < 0) {
                sleep = 2;
            }

            try {
                Thread.sleep(1);
            } catch (InterruptedException e) {
                System.out.println("Interrupted: " + e.getMessage());
            }

            beforeTime = System.currentTimeMillis();
        }
    }
    
    class CustomKeyListener implements KeyListener{
    	private Lock lock;
    	private ArrayList<Integer> keyArray;
    	public CustomKeyListener (Lock lock, ArrayList<Integer> keyarray) {
    		this.lock = lock;
    		this.keyArray = keyarray;
    	}
        public void keyTyped(KeyEvent e) {
        	//if (e.getKeyCode() == "\\") {
        		
        	//}
        	
        }

        public void keyPressed(KeyEvent e) {
        	//System.out.println(e.getKeyCode());
        	if (e.getKeyCode() == KeyEvent.VK_F12) {
              dumpAndExit = true;
              return;
        	}
        	if (e.getKeyCode() == KeyEvent.VK_F11) {
                startdumping = true;
                return;
          	}

        	if (e.getKeyCode() == 92) {
        		playPressed = true;
        		return;
        	}
          //lock.lock();
        //System.out.println("key pressed" + e.getKeyCode());
        	//System.out.println(e.getKeyCode());
          for (Integer number : keyArray) {
        	  if (number.intValue() == e.getKeyCode())
        		return;        	  
          }
          keyArray.add(e.getKeyCode());      	

          //lock.unlock();
        }

        public void keyReleased(KeyEvent e) {
            //lock.lock();
        	//System.out.println("key released " + e.getKeyCode());
        	int i = 0;
            for (Integer number : keyArray) {
          	  if (number.intValue() == e.getKeyCode()) {
          		keyArray.remove(number);
          		return;
          	  }
          		
            }
      	

            //lock.unlock();
        }    
     } 

}