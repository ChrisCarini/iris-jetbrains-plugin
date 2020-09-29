package com.chriscarini.jetbrains.iris.plugin.action;

import com.chriscarini.jetbrains.iris.client.model.Incident;
import com.chriscarini.jetbrains.iris.plugin.IrisIcons;
import com.intellij.notification.Notification;
import com.intellij.notification.NotificationAction;
import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.DataContext;
import com.intellij.openapi.actionSystem.ex.ActionUtil;
import com.intellij.openapi.application.ApplicationManager;
import com.intellij.openapi.ui.DialogBuilder;
import com.intellij.openapi.ui.DialogWrapper;
import com.intellij.openapi.ui.VerticalFlowLayout;
import com.intellij.ui.components.JBPanel;
import com.intellij.ui.components.labels.LinkLabel;
import com.intellij.util.ui.FormBuilder;
import java.util.Collection;
import javax.swing.*;
import org.jetbrains.annotations.NotNull;


public class MultipleIncidentsAction extends NotificationAction {
  private final String baseHostname;
  private final Collection<Incident> incidents;

  public MultipleIncidentsAction(@NotNull final String baseHostname, @NotNull final Collection<Incident> incidents) {
    super(String.format("You received %s notifications.", incidents.size()));
    this.baseHostname = baseHostname;
    this.incidents = incidents;
  }

  @Override
  public void actionPerformed(@NotNull AnActionEvent e, @NotNull Notification notification) {
    ApplicationManager.getApplication().invokeLater(() -> {
      final DialogBuilder dialogBuilder = new DialogBuilder(e.getProject());

      final JPanel centerPanel = new JBPanel();
      centerPanel.setLayout(new VerticalFlowLayout(true, false));

      final FormBuilder fb = FormBuilder.createFormBuilder();
      for (final Incident incident : incidents) {
        // TODO: Have this working, let's get the layout cleaned up a bit more and maybe provide more information to the user
        final LinkLabel foo =
            new LinkLabel<>(String.format("Claim %s", incident.getId()), IrisIcons.IrisClaim, (aSource, aLinkData) -> {
              final AnAction action = new ClaimIncidentAction(this.baseHostname, aLinkData.getId(), null);
              action.getTemplatePresentation().setEnabledAndVisible(true);
              ActionUtil.performActionDumbAwareWithCallbacks(action, e, DataContext.EMPTY_CONTEXT);
              dialogBuilder.getDialogWrapper().close(DialogWrapper.OK_EXIT_CODE);
            }, incident);
        fb.addComponent(foo);
      }
      centerPanel.add(fb.getPanel());
      dialogBuilder.setCenterPanel(centerPanel);

      dialogBuilder.setOkActionEnabled(true);

      boolean result = dialogBuilder.showAndGet();
      if (result) {

      }
    });
  }
}
