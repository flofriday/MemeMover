package dev.flofriday.mememoverplugin;

import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Map;
import java.util.Objects;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.WindowConstants;

/**
 * The window/application in which the meme/complex ui is resized.
 */
public class MemeMover extends JFrame {
  private Edge enteredFrom = null;
  private JComponent target;
  private final JLabel hintLabel;

  enum Edge {
    LEFT, RIGHT, TOP, BOTTOM
  }

  public MemeMover() {
    super("MemeMover");

    var initialWindowSize = 800;
    setLayout(null);
    setSize(initialWindowSize, initialWindowSize);

    // The hint label is always the ful window size and knows how to center the text inside it.
    hintLabel = new JLabel("Move the mouse over the window. Click to toggle content.",
        SwingConstants.CENTER);
    hintLabel.setBounds(0, 0, initialWindowSize, initialWindowSize);
    add(hintLabel);

    target = new MemeLabel(Objects.requireNonNull(getClass().getResource("/serverdown.jpg")));
    add(target);

    // Setup the mouse listener on the glass pane instead of directly the window, so that hovering
    // over a button or textarea still triggers the listener.
    var glassPane = (JPanel) getRootPane().getGlassPane();
    glassPane.setOpaque(false);
    glassPane.setVisible(true);
    var mouseListener = new CustomMouseListener();
    this.getRootPane().getGlassPane().addMouseListener(mouseListener);
    this.getRootPane().getGlassPane().addMouseMotionListener(mouseListener);

    addComponentListener(new ComponentAdapter() {
      @Override
      public void componentResized(ComponentEvent e) {
        hintLabel.setBounds(0, 0, getSize().width, getSize().height);
      }
    });
  }

  /**
   * The size the images reaches when it stops growing.
   * Scales to the proportions of the window.
   *
   * @return the max size in pixel.
   */
  private int getMaxImgSize() {
    return Math.min(getSize().width, getSize().height) / 2;
  }

  /**
   * The progress of the scaling transformation.
   *
   * @param x position of the mouse.
   * @param y position of the mouse.
   * @return the progress from 0.0(at the edge) to 1.0(center of the window).
   */
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

  /**
   * Adjust the size and position of the target.
   *
   * @param x position of the mouse.
   * @param y position of the mouse.
   */
  private void moveScaleTarget(int x, int y) {
    // Note: I wasn't sure if "quarter of original size" meant the area or the edge of the meme.
    // However, a quarter of the edge looked better, so I went with that.
    double initialScale = 0.25;
    int scaledSize =
        (int) (getMaxImgSize() * (initialScale + (1 - initialScale) * getProgress(x, y)));
    int size = Math.min(getMaxImgSize(), scaledSize);

    target.setBounds(x - size / 2, y - size / 2, size, size);
  }

  class CustomMouseListener extends MouseAdapter {
    @Override
    public void mouseClicked(MouseEvent e) {
      remove(target);
      if (target instanceof MemeLabel) {
        target = new SlowComplexPanel();
      } else {
        target = new MemeLabel(Objects.requireNonNull(getClass().getResource("/serverdown.jpg")));
      }
      add(target);
      moveScaleTarget(e.getX(), e.getY());
      revalidate();
    }

    @Override
    public void mouseMoved(MouseEvent e) {
      moveScaleTarget(e.getX(), e.getY());
      revalidate();
    }

    @Override
    public void mouseEntered(MouseEvent e) {
      hintLabel.setVisible(false);
      target.setVisible(true);
      enteredFrom = getClosestEdge(e.getX(), e.getY());
      moveScaleTarget(e.getX(), e.getY());
      revalidate();
    }

    @Override
    public void mouseExited(MouseEvent e) {
      target.setVisible(false);
      hintLabel.setVisible(true);
      revalidate();
    }
  }

  public static void main(String[] args) {
    SwingUtilities.invokeLater(() -> {
      var window = new MemeMover();
      window.setVisible(true);
      window.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    });
  }
}
