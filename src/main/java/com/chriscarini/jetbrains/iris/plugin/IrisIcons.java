package com.chriscarini.jetbrains.iris.plugin;

import com.intellij.icons.AllIcons;
import com.intellij.openapi.util.IconLoader;
import com.intellij.ui.LayeredIcon;
import com.intellij.util.IconUtil;
import javax.swing.*;


/**
 * Icons for Iris
 */
public final class IrisIcons {
  public static final Icon Iris = IconLoader.getIcon("/icons/iris.png");

  /**
   * The Iris icon with a green check mark on top in the lower right corner.
   */
  public static final Icon IrisClaim =
      LayeredIcon.create(Iris, IconUtil.scale(AllIcons.General.InspectionsOK, null, 1.75f));
}
