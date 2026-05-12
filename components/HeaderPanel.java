package components;

import utils.Theme;

import javax.swing.*;
import java.awt.*;

public class HeaderPanel extends JPanel {

    public HeaderPanel(String title) {

        setBackground(Theme.PANEL);

        JLabel label = new JLabel(title);

        label.setForeground(Color.WHITE);
        label.setFont(Theme.TITLE_FONT);

        add(label);
    }
}