package flofriday;

import java.awt.Image;
import java.net.URL;
import javax.swing.ImageIcon;
import javax.swing.JLabel;

/**
 * Just a simple label that displays a meme.
 * Automatically resizes the meme according to the size.
 */
public class MemeLabel  extends JLabel {
  private final ImageIcon icon;
  private final Image originalImage;

  public MemeLabel(URL path) {
    super();
    icon = new ImageIcon(path);
    setIcon(icon);
    originalImage = icon.getImage();
  }

  @Override
  public void setBounds(int x, int y, int width, int height) {
    super.setBounds(x, y, width, height);
    icon.setImage(originalImage.getScaledInstance(width, height, Image.SCALE_SMOOTH));
  }

}
