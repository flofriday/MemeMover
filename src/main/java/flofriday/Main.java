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
    setSize(800, 800);
    setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

    icon = new ImageIcon(Objects.requireNonNull(getClass().getResource("/serverdown.jpg")));
    originalImage = icon.getImage();
    label = new JLabel(icon);
    label.setLayout(null);
    add(label);

    var mouseListener = new CustomMouseListener();
    addMouseListener(mouseListener);
    addMouseMotionListener(mouseListener);
  }

  // The size the images reaches when it stops growing.
  // Scales to the proportions of the window.
  private int getMaxImgSize() {
    return Math.min(getSize().width, getSize().height) / 2;
  }

  // The progress of the transformation.
  // Returns somewhere between 0 and 1.
  // 0 meaning the mouse is literally at the edge and 1 at the center of the window.
  private double getProgress(int x, int y) {
    var width = getSize().width;
    var height = getSize().height;

    return switch (enteredFrom) {
      case LEFT -> Math.min(1.0, x / ((double) width / 2));
      case TOP -> Math.min(1.0, y / ((double) height / 2));
      case RIGHT -> Math.min(1.0, (width - x) / ((double) width / 2));
      case BOTTOM -> Math.min(1.0, (height - y) / ((double) height / 2));
    };
  }

  private Edge getClosestEdge(int x, int y) {
    Map<Edge, Integer> distances = Map.of(
        Edge.LEFT, x,
        Edge.TOP, y,
        Edge.RIGHT, getSize().width - x,
        Edge.BOTTOM, getSize().height - y
    );

    return distances.entrySet().stream()
        .min(Map.Entry.comparingByValue())
        .orElseThrow()
        .getKey();
  }

  private void moveScaleImage(int x, int y) {
    // Note: I wasn't sure if "quarter of original size" meant the area or the edge of the meme.
    // However, a quarter of the edge looked better, so I went with that.
    double initialScale = 0.25;
    int scaledSize = (int) (getMaxImgSize() * (initialScale + (1 - initialScale) * getProgress(x, y)));
    int size = Math.min(getMaxImgSize(), scaledSize);

    icon.setImage(originalImage.getScaledInstance(size, size, Image.SCALE_SMOOTH));
    label.setBounds(x - size / 2, y - size / 2, size, size);
  }

  class CustomMouseListener extends MouseAdapter {
    @Override
    public void mouseMoved(MouseEvent e) {
      moveScaleImage(e.getX(), e.getY());
    }

    @Override
    public void mouseEntered(MouseEvent e) {
      label.setVisible(true);
      enteredFrom = getClosestEdge(e.getX(), e.getY());
      moveScaleImage(e.getX(), e.getY());
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
