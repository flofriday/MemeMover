package flofriday;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.GridBagLayout;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.border.Border;

public class SlowComplexPanel extends JPanel {
  public SlowComplexPanel() {
    setLayout(new GridBagLayout());
    setBackground(Color.RED);

    JPanel centerPanel = new JPanel();
    centerPanel.setBackground(Color.GREEN);
    centerPanel.setLayout(new BoxLayout(centerPanel, BoxLayout.Y_AXIS));

    var label = new JLabel("Loading...");
    label.setAlignmentX(Component.CENTER_ALIGNMENT);

    var progressBar = new JProgressBar(0, 100);
    progressBar.setValue(20);
    progressBar.setAlignmentX(Component.CENTER_ALIGNMENT);

    centerPanel.add(Box.createVerticalGlue());
    centerPanel.add(label);
    centerPanel.add(Box.createRigidArea(new Dimension(0, 10)));
    centerPanel.add(progressBar);
    centerPanel.add(Box.createVerticalGlue());

    add(centerPanel);

  }
}

