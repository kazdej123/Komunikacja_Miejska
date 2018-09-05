package view;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import javax.swing.AbstractListModel;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.WindowConstants;
import javax.swing.table.AbstractTableModel;
import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Insets;
import java.awt.Toolkit;
import java.awt.Window;
import java.awt.event.ActionListener;
import java.util.function.IntSupplier;
import java.util.function.Supplier;

final class TableView {
    private final JPanel tablePanel = MainPanel.createJPanel(new BorderLayout(), new Insets(0, 0, 0, 0));

    private final JButton mainButton;

    private final MainPanel ownerMainPanel;

    TableView(@NotNull final MainPanel ownerMainPanel, final Window ownerWindow, final String tablePanelName, final IntSupplier intSupplier, final Supplier objectSupplier, final Runnable insertRowRunnable, final String buttonText, final Runnable showTableViewRunnable, final Object choosingDialogTitle, final String[] columnNames) {
        this.ownerMainPanel = ownerMainPanel;

        tablePanel.setName(tablePanelName);

        final JTable jTable = new JTable(new AbstractTableModel() {
            @Override
            public final int getColumnCount() {
                return columnNames.length;
            }

            @Override
            public final String getColumnName(final int columnIndex) {
                return columnNames[columnIndex];
            }

            @Override
            public final int getRowCount() {
                try {
                    return intSupplier.getAsInt();
                } catch (final NullPointerException e) {
                    return 100;
                }
            }

            @Override
            public final Object getValueAt(final int rowIndex, final int columnIndex) {
//                return objectSupplier.get();
                return "Morszcz";
            }
        });
        jTable.setAutoCreateRowSorter(true);
        DefaultView.setComponentBoldFont(jTable.getTableHeader(), 12);

        addScrollableComponentToContainer(tablePanel, BorderLayout.CENTER, jTable, new Insets(0, 0, 0, 10));

        final JPanel southButtonsPanel = createJPanel(new Insets(10, 0, 0, 0));
        addJButtonToContainer(southButtonsPanel, "Dodaj rekord", MainPanel.DEFAULT_FONT_SIZE, e -> {
            try {
                insertRowRunnable.run();
            } catch (final NullPointerException e1) {
                System.out.println("Nie zrobione!!!"); // TODO
            }
        });
        MainPanel.addGapToContainer(southButtonsPanel, 20, 0);
        addJButtonToContainer(southButtonsPanel, "Usuń rekordy", MainPanel.DEFAULT_FONT_SIZE, null);

        tablePanel.add(southButtonsPanel, BorderLayout.SOUTH);

        mainButton = createJButton(buttonText, MainPanel.DEFAULT_FONT_SIZE, e -> {
            try {
                showTableViewRunnable.run();
            } catch (final NullPointerException e1) {
                if (choosingDialogTitle != null) {
                    final JDialog jDialog = new JDialog(ownerWindow, "Wybierz " + choosingDialogTitle);
                    MainPanel.setJComponentEmptyBorder(jDialog.getRootPane(), new Insets(10, 10, 10, 10));

                    final JList<String> jList = new JList<>(new AbstractListModel<>() {
                        @Override
                        public final int getSize() {
                            return 100; // TODO
                        }

                        @Override
                        public final String getElementAt(final int index) {
                            return index + "Test";
                        }
                    });
                    // TODO

                    addScrollableComponentToContainer(jDialog, BorderLayout.CENTER, jList, new Insets(0, 0, 10, 0));

                    final JPanel southDialogPanel = createJPanel(new Insets(10, 0, 0, 0));

                    final int fontSize = 15;

                    final JButton okButton = createJButton("Ok", fontSize, e2 -> {
                        jDialog.dispose();
                        showTablePanel(tablePanelName);
                    });

                    southDialogPanel.add(okButton);
                    southDialogPanel.add(createJButton("Anuluj", fontSize, e2 -> jDialog.dispose()));

                    jDialog.add(southDialogPanel, BorderLayout.SOUTH);
                    jDialog.pack();
                    jDialog.setLocation((DefaultView.SCREEN_WIDTH - jDialog.getWidth()) / 2, (DefaultView.SCREEN_HEIGHT - jDialog.getHeight()) / 2);
                    jDialog.setModal(true);
                    jDialog.setResizable(false);
                    jDialog.getRootPane().setDefaultButton(okButton);
                    jDialog.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
                    jDialog.setVisible(true);
                } else {
                    showTablePanel(tablePanelName);
                }
            }
        });
        DefaultView.centerJComponent(mainButton);
        mainButton.setMaximumSize(MainPanel.defaultJComponentDimension);

        ownerMainPanel.addTableView(this);
    }

    private static void addScrollableComponentToContainer(@NotNull final Container container, final Object constraints, final Component component, final Insets borderInsets) {
        final JScrollPane jScrollPane = new JScrollPane(component);
        MainPanel.setJComponentEmptyBorder(jScrollPane, borderInsets);
        container.add(jScrollPane, constraints);
    }

    private static JPanel createJPanel(final Insets borderInsets) {
        return MainPanel.createJPanel(new FlowLayout(), borderInsets);
    }

    private static void addJButtonToContainer(@NotNull final Container container, final String buttonText, final int fontSize, final ActionListener actionListener) {
        container.add(createJButton(buttonText, fontSize, actionListener));
    }

    private static JButton createJButton(final String text, final int fontSize, final ActionListener actionListener) {
        final JButton jButton = new JButton(text);
        DefaultView.setComponentFont(jButton, Font.PLAIN, fontSize);
        jButton.addActionListener(actionListener);
        return jButton;
    }

    private void showTablePanel(final String tablePanelName) {
        ownerMainPanel.showTablePanel(tablePanelName);
    }

    @Contract(pure = true)
    final JPanel getTablePanel() {
        return tablePanel;
    }

    @Contract(pure = true)
    final JButton getMainButton() {
        return mainButton;
    }
}
