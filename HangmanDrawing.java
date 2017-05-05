import java.awt.*;
import javax.swing.*;

public class HangmanDrawing extends JComponent {

        public HangmanDrawing() {
            setPreferredSize(new Dimension(500, 100));
        }

        public void paintComponent(Graphics g) {
            super.paintComponent(g);
            g.fillRect(200, 62, 30, 10);
        }
    }