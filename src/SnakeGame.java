import javax.swing.*;

public class SnakeGame extends JFrame {

    Board board;
    SnakeGame(){
        board = new Board();
        add(board);
        pack();
        setResizable(false);
        setTitle("Snake Game");
        setLocationRelativeTo(null);
        setVisible(true);
    }
    public static void main(String[] args) {

        SnakeGame snakeGame = new SnakeGame();
    }
}