import javax.swing.*;

public class Main {
    public static void main(String[] args) {

        try {

            TicTacToeGUI ticTacToeGUI = new TicTacToeGUI();
            ticTacToeGUI.setTitle("Tic-Tac-Toe");
            ticTacToeGUI.setSize(350, 350);
            ticTacToeGUI.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            ticTacToeGUI.setVisible(true);

        }
        catch(Exception e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
        }

    }
}




