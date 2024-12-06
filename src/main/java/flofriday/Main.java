package flofriday;

import java.awt.Image;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Map;
import java.util.Objects;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.SwingUtilities;
import javax.swing.WindowConstants;

public class Main extends JFrame {
  final private int windowSize = 800;
  final private int imgSize = 400;
  private Edge enteredFrom = null;
  private final JLabel label;
  private final ImageIcon icon;
  private final Image originalImage;

  enum Edge {
    LEFT, RIGHT, TOP, BOTTOM
  }

  public Main() {
    super("MemeMover");

    setLayout(null);
    setSize(windowSize, windowSize);
    setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

    icon = new ImageIcon(Objects.requireNonNull(getClass().getResource("/serverdown.jpg")));
    originalImage = icon.getImage();
    icon.setImage(originalImage.getScaledInstance(imgSize, imgSize, Image.SCALE_FAST));
    label = new JLabel(icon);
    label.setLayout(null);
    label.setBounds(0, 0, imgSize, imgSize);
    add(label);

    var mouseListener = new CustomMouseListener();
    addMouseListener(mouseListener);
    addMouseMotionListener(mouseListener);
  }

  private int getProgress(int x, int y) {
    return switch (enteredFrom) {
      case LEFT -> x;
      case TOP -> y;
      case RIGHT -> windowSize - x;
      case BOTTOM -> windowSize - y;
    };
  }

  private Edge getClosestEdge(int x, int y) {
    Map<Edge, Integer> distances = Map.of(
        Edge.LEFT, x,
        Edge.TOP, y,
        Edge.RIGHT, windowSize - x,
        Edge.BOTTOM, windowSize - y
    );

    return distances.entrySet().stream()
        .min(Map.Entry.comparingByValue())
        .orElseThrow()
        .getKey();
  }

  private void moveScaleImage(int x, int y) {
    var size = (int)Math.min(imgSize, imgSize * 0.25 + getProgress(x, y) * 0.5);
    icon.setImage(originalImage.getScaledInstance(size, size, Image.SCALE_SMOOTH));
    label.setBounds(x - size/2, y-size/2, size,size);
  }

  class CustomMouseListener extends MouseAdapter {
    @Override
    public void mouseMoved(MouseEvent e) {
      moveScaleImage(e.getX(), e.getY());
    }

    @Override
    public void mouseEntered(MouseEvent e) {
      var x = e.getX();
      var y = e.getY();
      enteredFrom = getClosestEdge(e.getX(), e.getY());

      label.setVisible(true);
      moveScaleImage(x, y);
    }

    @Override
    public void mouseExited(MouseEvent e) {
      label.setVisible(false);
    }
  }

  public static void main(String[] args) {
    SwingUtilities.invokeLater(() -> new Main().setVisible(true));
  }
}
