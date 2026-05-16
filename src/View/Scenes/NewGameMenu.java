package View.Scenes;

import View.GameView;

import javax.swing.*;
import java.awt.*;

public class NewGameMenu extends JPanel{
    private JButton play;

    public NewGameMenu(GameView gameView){
        play = new JButton("Play");
        play.setPreferredSize(new Dimension(100, 50));
        play.setBorder(BorderFactory.createLineBorder(Color.BLACK, 4));
        play.addActionListener(ae -> gameView.showGameScene());

        setLayout(new GridBagLayout());

        JPanel buttons = new JPanel();
        buttons.setLayout(new GridLayout(1,4, 15, 0));
        buttons.setOpaque(false);

        buttons.add(play);

        add(buttons);
    }
}
