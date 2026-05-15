package View.Scenes;

import View.GameView;

import javax.swing.*;
import java.awt.*;

public class MainMenu extends JPanel{
    private JButton newGame;
    private JButton loadGame;

    public MainMenu(GameView gameView){
        newGame = new JButton("New Game");
        newGame.setPreferredSize(new Dimension(100, 50));
        newGame.setBorder(BorderFactory.createLineBorder(Color.BLACK, 4));
        newGame.addActionListener(ae -> gameView.showNewGameMenu());

        loadGame = new JButton("Load Game");
        loadGame.setPreferredSize(new Dimension(100, 50));
        loadGame.setBorder(BorderFactory.createLineBorder(Color.BLACK, 4));
        loadGame.addActionListener(ae -> gameView.showLoadGameMenu());

        setLayout(new GridBagLayout());

        JPanel buttons = new JPanel();
        buttons.setLayout(new GridLayout(1,4, 15, 0));
        buttons.setOpaque(false);

        buttons.add(newGame);
        buttons.add(loadGame);

        add(buttons);
    }
}
