package view;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import javax.swing.*;
import javax.swing.table.AbstractTableModel;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.function.IntSupplier;
import java.util.function.Supplier;

final class TableView {
    @FunctionalInterface
    interface StringSupplier extends Supplier<String> {}

    private final JPanel tablePanel = MainPanel.createJPanel(new BorderLayout(), new Insets(0, 0, 0, 0));

    private final JButton mainButton;

    private final MainPanel ownerMainPanel;

    TableView(@NotNull final MainPanel ownerMainPanel, final String tablePanelName, final IntSupplier tableRowCountSupplier, final Supplier tableValueAtSupplier, final Runnable insertRowRunnable, final String buttonText, final Runnable showTableViewRunnable, final Object choosingDialogTitle, final IntSupplier choosingForShowSizeSupplier, final StringSupplier choosingForShowElementAtSupplier, final String[] columnNames) {
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
                    return tableRowCountSupplier.getAsInt();
                } catch (final NullPointerException e) {
                    return 100;
                }
            }

            @Override
            public final Object getValueAt(final int rowIndex, final int columnIndex) {
                try {
                    return tableValueAtSupplier.get();
                } catch (final NullPointerException e) {
                    return "Morszcz";
                }
            }
        });
        jTable.setAutoCreateRowSorter(true);
        DefaultView.setComponentBoldFont(jTable.getTableHeader(), 12);
        addScrollableComponentToContainer(tablePanel, jTable, BorderLayout.CENTER, new Insets(0, 0, 0, 10));

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
                    final JDialog jDialog = new JDialog(ownerMainPanel.getOwnerWindow(), "Wybierz " + choosingDialogTitle);
                    MainPanel.setJComponentEmptyBorder(jDialog.getRootPane(), new Insets(10, 10, 10, 10));

                    final JList<String> jList = new JList<>(new AbstractListModel<>() {
                        @Override
                        public final int getSize() {
                            try {
                                return choosingForShowSizeSupplier.getAsInt();
                            } catch (final NullPointerException e2) {
                                return 100;
                            }
                        }

                        @Override
                        public final String getElementAt(final int index) {
                            try {
                                return choosingForShowElementAtSupplier.get();
                            } catch (final NullPointerException e2) {
                                return "Test " + (char)index;
                            }
                        }
                    });
                    jList.addMouseListener(new MouseAdapter() {
                        @Override
                        public final void mouseClicked(final MouseEvent e) {
                            if (e.getClickCount() == 2) {
                                closeWindow(jDialog);
                            }
                        }
                    });
                    jList.setSelectedIndex(0);
                    jList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
                    jList.setVisibleRowCount(10);
                    addScrollableComponentToContainer(jDialog, jList, BorderLayout.CENTER, new Insets(0, 0, 10, 0));

                    final JPanel southDialogPanel = createJPanel(new Insets(10, 0, 0, 0));

                    final JButton okButton = createJButton("Ok", e2 -> closeWindow(jDialog));

                    southDialogPanel.add(okButton);
                    southDialogPanel.add(createJButton("Anuluj", e2 -> jDialog.dispose()));

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
    }

    private static void addScrollableComponentToContainer(@NotNull final Container container, final Component component, final Object constraints, final Insets borderInsets) {
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
        final JButton jButton = createJButton(text, actionListener);
        DefaultView.setComponentFont(jButton, Font.PLAIN, fontSize);
        return jButton;
    }

    private static JButton createJButton(final String text, final ActionListener actionListener) {
        final JButton jButton = new JButton(text);
        jButton.addActionListener(actionListener);
        return jButton;
    }

    private void closeWindow(@NotNull final Window window) {
        window.dispose();
        showTablePanel(tablePanel.getName());
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
