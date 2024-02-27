package Terraria;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.Timer;
import java.util.TimerTask;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;


import static Terraria.Helper.time;
import static Terraria.Helper.character;
import static Terraria.Helper.grounds;
import static Terraria.Helper.hotbar;
import static Terraria.Helper.inAir;
import static Terraria.Helper.xCord;
import static Terraria.Helper.yCord;
import static Terraria.Helper.tempTime;
import static Terraria.Helper.pressed;
import static Terraria.Helper.jumpTime;
import static Terraria.Helper.movedInAir;
import static Terraria.Helper.pos;
import static Terraria.Helper.world;
import static Terraria.Helper.x;
import static Terraria.Helper.y;

class Helper extends TimerTask {
     Font font = new Font("", Font.PLAIN, 50);
    public static int time = 0;
    public static JLabel character = new JLabel();
    public static  JLabel pos=new JLabel();
    public static boolean inAir = true;
    public static int tempTime;
    public static double jumpTime = 0;
    public static  boolean pressed=false;
    public static boolean movedInAir = false;
    public static boolean underBlock=false;
    public static int xCord;
    public static int yCord;
    public static int x = 9;
    public static int y = 32;
    public static JLabel grounds[][] = new JLabel[x][y];
    public static int world[][] = new int[x][y];
    public static int inventory []=new int[24];
    public static JLabel hotbar[]=new JLabel[8];

    @Override
    public void run() {
        if (pressed==true) {
            tempTime++;
        }
         if (tempTime>1) {
            pressed=false;
            grounds[xCord][yCord].setIcon(null);
            world[xCord][yCord]=0;
             tempTime=0;
        }
        //init fix
        if (time>3) {
        //jump Gravity
        if (inAir == true) {
            if (jumpTime + 0.2 == time || jumpTime + 0.2 > time) {
                for (int i = 0; i < x; i++) {
                    for (int j = 0; j < y; j++) {
                      if (character.getY() + 60 == grounds[i][j].getY() && character.getX() - 10 == grounds[i][j].getX() && world[i][j] !=0) {
                          character.setLocation(character.getY(), character.getX());
                      underBlock=true;
                }  
                    }
                    
                }
                if (underBlock==false) {
                      character.setLocation(character.getX(), character.getY()+60);
                }
            }

                inAir = false;
                movedInAir = false;
            }
        }

        //Off screen fix
        if (character.getX() < 0 || character.getX() > 1900) {
            character.setBounds(370, 420, 60, 120);
        }
 
        //Cur pos
        pos.setFont(font);
        pos.setBounds(500, 100, 300,300);
        pos.setText(character.getX()+" "+character.getY());
        time++;
        
    }
}

public class Game extends JFrame implements KeyListener, MouseListener {

    Game() {

        Timer timer = new Timer();

        TimerTask task = new Helper();

        timer.schedule(task, 0, 1000);

        //Ground
        int xAxis = 0;
        int yAxis = 540;

        ImageIcon dirt = new ImageIcon("dirt.png");
        ImageIcon stone = new ImageIcon("stone.png");
        ImageIcon grass = new ImageIcon("grass.png");
        ImageIcon iron = new ImageIcon("iron.png");
        for (int i = 0; i < x; i++) {
            for (int j = 0; j < y; j++) {
                grounds[i][j] = new JLabel();
                grounds[i][j].setBounds(xAxis, yAxis, 60, 60);
                grounds[i][j].setLayout(null);
                grounds[i][j].setText(grounds[i][j].getX()+","+grounds[i][j].getY());
                grounds[i][j].addMouseListener(this);
//                grounds[i][j].setVisible(false);
                
                if (i < 1) {
                    grounds[i][j].setIcon(grass);
                    world[i][j] = 1;
                } else if (i < 3) {
                    grounds[i][j].setIcon(dirt);
                    world[i][j] = 2;
                } else {
                    grounds[i][j].setIcon(stone);
                    world[i][j] = 3;
                }
                xAxis += 60;

            }

            yAxis += 60;
            xAxis = 0;
        }
        
        //ores
        for (int i = 0; i < 5; i++) {
            int rng = (int) (Math.random() * (x));
        int rng2 = (int) (Math.random() * (y));
        grounds[rng][rng2].setIcon(iron);
        world[rng][rng2] = 3;         
        }
        
        
        //Hotbar
        xAxis=640;
         ImageIcon hotBarPic = new ImageIcon("hotbar.png");
        for (int i = 0; i < hotbar.length; i++) {
            hotbar[i]=new JLabel();
            hotbar[i].setIcon(hotBarPic);
            hotbar[i].setLayout(null);
            hotbar[i].setVisible(true);
            hotbar[i].setFocusable(true);
            hotbar[i].setBounds(xAxis,950,80,80);
            xAxis+=75;
        }
        
        //Character
        ImageIcon icon = new ImageIcon("character.png");
        character.setIcon(icon);
        character.setBounds(70, 420, 60, 120);
        character.requestFocus();

        this.getContentPane().setBackground(Color.cyan);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setTitle("Terraria"); //title of the window
        this.setLayout(null);   //Custom layout
        this.setSize(1920, 1080);  //X + Y dimension
        this.setExtendedState(JFrame.MAXIMIZED_BOTH);
        this.setUndecorated(true);
        this.setVisible(true);
        
        this.add(character);
        this.add(pos);
        this.addKeyListener(this);
        for (int i = 0; i < hotbar.length; i++) {
            this.add(hotbar[i]);
        }
        
        for (int i = 0; i < x; i++) {
            for (int j = 0; j < y; j++) {
                this.add(grounds[i][j]);
            }
        }
        

    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        switch (e.getKeyChar()) {
            case 'a':
               left();     

                break;
            case 'd':
               right();

                break;
            case 'w':
//                up();

                break;
            case 's':
                down();
                break;

        }
    }

    @Override
    public void keyReleased(KeyEvent e) {

    }

    @Override
    public void mouseClicked(MouseEvent e) {
//        for (int i = 0; i < x; i++) {
//            for (int j = 0; j < y; j++) {
//                if (grounds[i][j] == e.getSource()) {
//                    grounds[i][j].setIcon(null);
//                    world[i][j] = 0;
//                }
//
//            }
//
//        } 
    }

    @Override
    public void mousePressed(MouseEvent e) {
           pressed=true;
           tempTime=0;
        for (int i = 0; i < x; i++) {
            for (int j = 0; j < y; j++) {
                if (grounds[i][j] == e.getSource()) {
                    xCord=i;
                    yCord=j;
                    break;
                }

            }

        }
                    
      
        
        
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        
       
               

    }

    @Override
    public void mouseEntered(MouseEvent e) {


    }

    @Override
    public void mouseExited(MouseEvent e) {

    }
    
    public static void up()
    {
      if (inAir == true) {

                } else {

                    character.setLocation(character.getX(), character.getY() - 60);

                    inAir = true;
                    jumpTime = time;
                }  
    }
    
    public static void down()
    {
      boolean blocked = false;
                for (int i = 0; i < x; i++) {
                    for (int j = 0; j < y; j++) {

                        if (character.getY() + 120 == grounds[i][j].getY() && character.getX() - 10 == grounds[i][j].getX() && world[i][j] !=0) {
                            blocked = true;
                        }
                    }

                }
                if (blocked == false) {  
                    if (inAir == true) {
                    
                }
                    else{
                        character.setLocation(character.getX(), character.getY() + 60);
                    }
                }
                
    }
    
    public static void right()
    {
      boolean  blocked = false;
      boolean anti2ndLoop=false;
      boolean gravity=false;
        
        for (int i = 0; i < x; i++) {
                        for (int j = 0; j < y; j++) {
                          //Gravity
        if (character.getX() +50  == grounds[i][j].getX() && character.getY()+120 == grounds[i][j].getY() && world[i][j] == 0) {
            character.setLocation(character.getX()+60, character.getY());
            
            for (int k = 0; k < 50; k++) {
                if (i+k>8) {
                    break;
                }
                if ( world[i+k][j] == 0) {
                 character.setLocation(character.getX(), character.getY()+60);
            }
                
            }
            gravity=true;
            anti2ndLoop=true;
            break;
        }   
        

                        }
                        if (anti2ndLoop==true) {
                            break;
                        }
                    }
        
        anti2ndLoop=false;
        
        if (gravity==false) {
                    for (int i = 0; i < x; i++) {
                        for (int j = 0; j < y; j++) {
                            //also block
                                if (character.getX() +50 == grounds[i][j].getX() && character.getY()+60 == grounds[i][j].getY() && world[i][j] !=0) {
                                    character.setLocation(character.getX() , character.getY()-60);
                                    anti2ndLoop=true;
                                    break;
                                }
 
                            //felso block
                            if (character.getX() +50 == grounds[i][j].getX() && character.getY() == grounds[i][j].getY() && world[i][j] !=0) {
                                blocked = true;
                                anti2ndLoop=true;
                                break;

                            }

                        }
                        if (anti2ndLoop==true) {
                            break;
                        }
                    }
                    
                    if (blocked == false) {
                        if (inAir == true) {
                    if (movedInAir == false) {
                        character.setLocation(character.getX() +60, character.getY());
                        movedInAir = true;
                    }
                }
                        else{
                           character.setLocation(character.getX() +60, character.getY()); 
                        }
                        
                    }
        }
    }
    
    public static void left()
    {
        boolean blocked = false;
        boolean anti2ndLoop=false;
        boolean gravity=false;
        
        for (int i = 0; i < x; i++) {
                        for (int j = 0; j < y; j++) {
                          //Gravity
        if (character.getX() -70  == grounds[i][j].getX() && character.getY()+120 == grounds[i][j].getY() && world[i][j] == 0) {
            character.setLocation(character.getX()-60, character.getY());
            for (int k = 0; k < 50; k++) {
                if (i+k>8) {
                    break;
                }
                if ( world[i+k][j] == 0) {
                 character.setLocation(character.getX(), character.getY()+60);
            }
                
            }
            gravity=true;
            anti2ndLoop=true;
                    break;
        }   
        if (anti2ndLoop==true) {
                            break;
                        }

                        }
                    }
        
        anti2ndLoop=false;
        if (gravity==false) {
        
                    for (int i = 0; i < x; i++) {
                        for (int j = 0; j < y; j++) {
                            //also block
                                if (character.getX() - 70 == grounds[i][j].getX() && character.getY()+60 == grounds[i][j].getY() && world[i][j] !=0) {
                                    character.setLocation(character.getX()-60, character.getY()-60);
                                    blocked=true;
                                    anti2ndLoop=true;
                                    break;
                                }
 
                            //felso block
                            if (character.getX() - 70 == grounds[i][j].getX() && character.getY() == grounds[i][j].getY() && world[i][j] !=0) {
                                blocked = true;
                                anti2ndLoop=true;
                                break;

                            }
 
                            if (anti2ndLoop==true) {
                            break;
                        }

                        }
                    }
                    
                    if (blocked == false) {
                        if (inAir == true) {
                    if (movedInAir == false) {
                        character.setLocation(character.getX() - 60, character.getY());
                        movedInAir = true;
                    }
                }
                        else{
                           character.setLocation(character.getX() - 60, character.getY()); 
                        }
                        
                    }
                        
        }
    }
    
}
