package flofriday;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagLayout;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.Timer;

public class SlowComplexPanel extends JPanel {
  private Timer progressTimer;
  private final JPanel loadingPanel;

  public SlowComplexPanel() {
    setLayout(new GridBagLayout());
    setBackground(Color.WHITE);

    // Setup the loading content
    loadingPanel = new JPanel();
    loadingPanel.setBackground(Color.WHITE);
    loadingPanel.setLayout(new BoxLayout(loadingPanel, BoxLayout.Y_AXIS));

    var label = new JLabel("Loading...");
    label.setAlignmentX(Component.CENTER_ALIGNMENT);

    var progressBar = new JProgressBar(0, 100);
    progressBar.setValue(0);
    progressBar.setAlignmentX(Component.CENTER_ALIGNMENT);

    loadingPanel.add(Box.createVerticalGlue());
    loadingPanel.add(label);
    loadingPanel.add(Box.createRigidArea(new Dimension(0, 10)));
    loadingPanel.add(progressBar);
    loadingPanel.add(Box.createVerticalGlue());

    add(loadingPanel);

    progressTimer = new Timer(5, e -> {
      int currentValue = progressBar.getValue();
      if (currentValue < 100) {
        progressBar.setValue(currentValue + 1);
      } else {
        progressTimer.stop();
        replaceLoadingPanel();
      }
    });
    progressTimer.start();
  }

  // Replace the loading content with some placeholder
  private void replaceLoadingPanel() {
    remove(loadingPanel);

    setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
    JLabel headerLabel = new JLabel("Slow Complex Panel");
    headerLabel.setFont(new Font("Arial", Font.BOLD, 18));
    add(headerLabel);

    String turingText = "Alan Turing was a pioneering mathematician and computer scientist " +
        "whose work was crucial to the Allied victory in World War II. During his time at Bletchley Park, " +
        "he played a pivotal role in cracking the Nazi Enigma code. Despite his incredible contributions " +
        "to science and the war effort, Turing faced profound personal challenges due to societal " +
        "discrimination against his homosexuality. In 1952, he was prosecuted for 'gross indecency' " +
        "and subjected to chemical castration as an alternative to imprisonment. Tragically, he died " +
        "in 1954 from cyanide poisoning, an event long believed to be suicide. It wasn't until 2009 " +
        "that the British government officially apologized for his treatment, and in 2013, Queen Elizabeth II " +
        "granted him a posthumous pardon, recognizing his extraordinary contributions to science and society.";

    JTextArea textArea = new JTextArea(turingText);
    textArea.setFont(new Font("Arial", Font.PLAIN, 14));
    textArea.setLineWrap(true);
    textArea.setWrapStyleWord(true);
    textArea.setEditable(false);
    textArea.setBackground(getBackground());
    add(new JScrollPane(textArea));

    JButton shareButton = new JButton("Share");
    add(shareButton);

    revalidate();
  }
}

