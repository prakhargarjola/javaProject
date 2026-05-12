package components;

import utils.Theme;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class CustomButton extends JButton {

    public CustomButton(String text) {

        super(text);

        setFocusPainted(false);

        setFont(Theme.NORMAL_FONT);

        setBackground(Theme.ACCENT);

        setForeground(Theme.TEXT);

        setCursor(new Cursor(Cursor.HAND_CURSOR));

        setPreferredSize(new Dimension(180, 40));

        addMouseListener(new MouseAdapter() {

            @Override
            public void mouseEntered(MouseEvent e) {

                setBackground(Theme.BUTTON_HOVER);
            }

            @Override
            public void mouseExited(MouseEvent e) {

                setBackground(Theme.ACCENT);
            }
        });
    }
}