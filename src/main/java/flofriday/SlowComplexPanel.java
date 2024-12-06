package flofriday;

import java.awt.Color;
import java.awt.GridBagLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JProgressBar;

public class SlowComplexPanel extends JPanel {
  public SlowComplexPanel() {
    setLayout(new GridBagLayout());
    setBackground(Color.RED);

    var label = new JLabel("Loading...");
    add(label);

    var progressBar = new JProgressBar(0, 100);
    progressBar.setValue(20);
    add(progressBar);
  }
}

