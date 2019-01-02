package com.chriscarini.jetbrains.iris.plugin.utils;

import com.intellij.openapi.diagnostic.Logger;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.project.ProjectManager;
import com.intellij.openapi.wm.IdeFocusManager;
import com.intellij.openapi.wm.IdeFrame;
import org.jetbrains.annotations.Nullable;


public final class ProjectUtils {
  public static final Logger LOG = Logger.getInstance(ProjectUtils.class);
  /**
   * Get the last focused or opened project; this is a best-effort attempt.
   *  Code pulled from {org.jetbrains.ide.RestService}
   * @return Project that was last in focus or open.
   */
  @Nullable
  public static Project getLastFocusedOrOpenedProject() {
    final IdeFrame lastFocusedFrame = IdeFocusManager.getGlobalInstance().getLastFocusedFrame();
    final Project project = lastFocusedFrame == null ? null : lastFocusedFrame.getProject();
    if (project == null) {
      final Project[] openProjects = ProjectManager.getInstance().getOpenProjects();
      return openProjects.length > 0 ? openProjects[0] : null;
    }
    return project;
  }
}
