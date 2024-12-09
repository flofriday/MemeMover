package dev.flofriday.mememoverplugin;

import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import javax.swing.SwingUtilities;
import org.jetbrains.annotations.NotNull;

public class MemeMoverOpenAction extends AnAction {
  @Override
  public void actionPerformed(@NotNull AnActionEvent e) {
    System.out.println("[MemeMover] Opening MemeMover action...");
    SwingUtilities.invokeLater(() -> {
      var window = new MemeMover();
      window.setVisible(true);
    });
  }
}
