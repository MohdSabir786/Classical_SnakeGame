import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class Board extends JPanel implements ActionListener {

    int B_HEIGHT = 400;
    int B_WIDTH = 400;

    int max_dots= B_HEIGHT * B_WIDTH;
    int dot_size =10;
    int dot;

    int apple_x;
    int apple_y;

    Timer timer;
    int s =0;
    int delay =100;
    Image body, head, apple;
    int[] x = new int[max_dots];
    int[] y = new int[max_dots];
    
    boolean leftDirection = true;
    boolean rightDirection = false;
    boolean upDirection = false;
    boolean downDirection = false;

    boolean inGame = true;
    
    Board(){
        TAdapter tAdapter = new TAdapter();
        addKeyListener(tAdapter);
        setFocusable(true);
        setPreferredSize(new Dimension(B_WIDTH,B_HEIGHT));
        setBackground(Color.BLACK);
        initGame();
        loadImages();

    }
    public void initGame(){
        dot =3;
        inGame = true;
        x[0]= 300;
        y[0] = 200;
//        Initialize Snake position
        for(int i=1; i<dot; i++){
            x[i]= x[0]+dot_size * i;
            y[i] = y[0];
        }
//        // Intializ Apple Position
          relocateApple();
//        apple_x = 200;
//        apple_y = 200;

        timer = new Timer(delay, this);
        timer.start();
    }

    public void loadImages(){
        ImageIcon bodyIcon = new ImageIcon("src/resources/dot.png");
        body = bodyIcon.getImage();
        ImageIcon headIcon = new ImageIcon("src/resources/head.png");
        head = headIcon.getImage();
        ImageIcon appleIcon = new ImageIcon("src/resources/apple.png");
        apple = appleIcon.getImage();
    }

    @Override
    public void paintComponent(Graphics g){
        super.paintComponent(g);
        doDrawing(g);
    }

    public void doDrawing(Graphics g){
        if(inGame){
            ScoreDis(g);
            g.drawImage(apple, apple_x,apple_y,this);
            for(int i=0; i<dot; i++){
                if(i==0){
                    g.drawImage(head,x[0], y[0], this);
                }
                else{
                    g.drawImage(body,x[i],y[i],this);
                }
            }
        }
        else {
            gameOver(g);
            timer.stop();
        }
    }

    public void relocateApple(){
        apple_x = ((int)(Math.random()*39))*dot_size;
        apple_y = ((int)(Math.random()*39))*dot_size;
    }
    public void checkCollision(){
        for(int i =1; i<dot; i++){
            if(i>4 && x[0]==x[i] && y[i] == y[0]){
                inGame = false;
            }
        }

        if(x[0]<0) inGame =false;
        if(x[0]>=B_WIDTH) inGame =false;
        if(y[0]<0) inGame =false;
        if(y[0]>=B_HEIGHT) inGame = false;
    }

    public  void ScoreDis(Graphics g){
        String msg = "Score: "+Integer.toString(s);
        Font small = new Font("Helvetica", Font.BOLD, 14);
        FontMetrics fontMetrics = getFontMetrics(small);

        g.setColor(Color.white);
        g.setFont(small);
        g.drawString(msg,(B_WIDTH-fontMetrics.stringWidth(msg))/2,30);
    }


    public void gameOver(Graphics g){
        String msg = "Game Over";
        int score = (dot-3)*5;
        String scoremsg = "Your Score: "+Integer.toString(score);
        Font small = new Font("Helvetica", Font.BOLD, 14);
        FontMetrics fontMetrics = getFontMetrics(small);

        String msg1 = "Click ENTER Restart Game";

        String msg2 = "Click X to Quit Game";
        g.setColor(Color.white);
        g.setFont(small);
        g.drawString(msg,(B_WIDTH-fontMetrics.stringWidth(msg))/2,(B_HEIGHT-140)/2);
        g.drawString(scoremsg,(B_WIDTH-fontMetrics.stringWidth(scoremsg))/2,(B_HEIGHT-70)/2);
        g.drawString(msg1,(B_WIDTH-fontMetrics.stringWidth(msg1))/2,(B_HEIGHT)/2);
        g.drawString(msg2,(B_WIDTH-fontMetrics.stringWidth(msg2))/2,(B_HEIGHT+70)/2);
    }

    @Override
    public void actionPerformed(ActionEvent actionEvent){
        if(inGame){
            checkApple();
            checkCollision();
            move();
            repaint();
        }
    }
    
    public void move(){
        for(int i=dot-1; i>=1; i--){
            x[i] = x[i-1];
            y[i] = y[i-1];
        }
        if(leftDirection){
            x[0] -= dot_size;
        }
        if (rightDirection) {
            x[0]+= dot_size;
        }
        if (upDirection) {
            y[0] -= dot_size;
        }
        if(downDirection)
            y[0] += dot_size;
    }

    public void checkApple(){
        if(apple_x == x[0] && apple_y == y[0]){
            dot++;
            s += 5;
            relocateApple();
        }
    }


    private class TAdapter extends KeyAdapter{
        @Override
        public void keyPressed(KeyEvent keyEvent){
            int key = keyEvent.getKeyCode();
            if(key == keyEvent.VK_LEFT && !rightDirection){
                upDirection = false;
                downDirection =false;
                leftDirection = true;
            }
            if(key == keyEvent.VK_RIGHT && !leftDirection){
                upDirection = false;
                downDirection =false;
                rightDirection =true;
            }
            if(key == keyEvent.VK_UP && !downDirection){
                rightDirection = false;
                leftDirection =false;
                upDirection = true;
            }
            if(key == keyEvent.VK_DOWN && !upDirection){
                rightDirection = false;
                leftDirection =false;
                downDirection =true;
            }
            if(key == keyEvent.VK_ENTER && !inGame){
                s=0;
                initGame();
            }

            if(key == keyEvent.VK_X && !inGame){
               System.exit(0);
            }
        }
    }

}
