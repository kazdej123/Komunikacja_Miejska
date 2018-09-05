package view;

import org.jetbrains.annotations.NotNull;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.AbstractTableModel;
import java.awt.*;
import java.awt.event.ActionListener;
import java.util.function.IntSupplier;
import java.util.function.Supplier;

final class MainPanel extends JPanel {
    private static final int DEFAULT_FONT_SIZE = 18;

    private static final Dimension defaultJComponentDimension = new Dimension(14 * DEFAULT_FONT_SIZE, 45);

    /*@FunctionalInterface
    interface ObjectSupplier extends Supplier<Object> {}*/

    private final JPanel cardsPanel = createJPanel(new CardLayout(), new Insets(0, 10, 0, 10));
    private final JPanel buttonsPanel = createJPanel(null, new Insets(10, 10, 10, 10));

    private final Window ownerWindow;

    static final class TableViewNames {
        private final String tablePanelName;
        private final Object choosingDialogTitle;

        TableViewNames(final String tablePanelName, final Object choosingDialogTitle) {
            this.tablePanelName = tablePanelName;
            this.choosingDialogTitle = choosingDialogTitle;
        }
    }

    MainPanel(final Window ownerWindow, final String name) {
        super(new BorderLayout());
        this.ownerWindow = ownerWindow;

        setName(name);
        setFocusCycleRoot(true);
        setJComponentEmptyBorder(this, new Insets(20, 10, 30, 10));

        cardsPanel.add(new JPanel(), null);
        
        add(cardsPanel, BorderLayout.CENTER);

        buttonsPanel.setLayout(new BoxLayout(buttonsPanel, BoxLayout.Y_AXIS));
        buttonsPanel.add(DefaultView.createBoldJLabel("Wyświetl " + name.toLowerCase(), (int) (1.1 * DEFAULT_FONT_SIZE), defaultJComponentDimension));

        add(buttonsPanel, BorderLayout.EAST);
    }

    final void addTableView(@NotNull final TableViewNames tableViewNames, final IntSupplier intSupplier, final Supplier objectSupplier, final Runnable insertRowRunnable, final String buttonText, final Runnable showTableViewRunnable, final String... columnNames) {
        addTableView(tableViewNames.tablePanelName, intSupplier, objectSupplier, insertRowRunnable, buttonText, showTableViewRunnable, tableViewNames.choosingDialogTitle, columnNames);
    }

    final void addTableView(final String tablePanelName, final IntSupplier intSupplier, final Supplier objectSupplier, final Runnable insertRowRunnable, final String buttonText, final Runnable showTableViewRunnable, final Object choosingDialogTitle, final String[] columnNames) {
        final JPanel tablePanel = createJPanel(new BorderLayout(), new Insets(0, 0, 0, 0));
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
        addJButtonToContainer(southButtonsPanel, "Dodaj rekord", DEFAULT_FONT_SIZE, e -> insertRowRunnable.run());
        addGapToContainer(southButtonsPanel, 20, 0);
        addJButtonToContainer(southButtonsPanel, "Usuń rekordy", DEFAULT_FONT_SIZE, null);

        tablePanel.add(southButtonsPanel, BorderLayout.SOUTH);

        cardsPanel.add(tablePanel, tablePanelName);

        final JButton jButton = DefaultView.createJButton(buttonText, DEFAULT_FONT_SIZE, e -> {
            try {
                showTableViewRunnable.run();
            } catch (final NullPointerException e1) {
                if (choosingDialogTitle != null) {
                    final JDialog jDialog = new JDialog(ownerWindow, "Wybierz " + choosingDialogTitle);
                    setJComponentEmptyBorder(jDialog.getRootPane(), new Insets(10, 10, 10, 10));

                    final JList<String> jList = new JList<>(new AbstractListModel<String>() {
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

                    final JButton okButton = DefaultView.createJButton("Ok", fontSize, e2 -> {
                        jDialog.dispose();
                        showTablePanel(tablePanelName);
                    });

                    southDialogPanel.add(okButton);
                    southDialogPanel.add(DefaultView.createJButton("Anuluj", fontSize, e2 -> jDialog.dispose()));

                    jDialog.add(southDialogPanel, BorderLayout.SOUTH);
                    jDialog.pack();

                    final Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();

                    jDialog.setLocation((screenSize.width - jDialog.getWidth()) / 2, (screenSize.height - jDialog.getHeight()) / 2);
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
        DefaultView.centerJComponent(jButton);
        jButton.setMaximumSize(defaultJComponentDimension);

        addGapToContainer(buttonsPanel, 0, 15);
        buttonsPanel.add(jButton);
    }

    private static void addJButtonToContainer(@NotNull final Container container, final String buttonText, final int fontSize, final ActionListener actionListener) {
        container.add(DefaultView.createJButton(buttonText, fontSize, actionListener));
    }

    private static void addScrollableComponentToContainer(@NotNull final Container container, final Object constraints, final Component component, final Insets borderInsets) {
        final JScrollPane jScrollPane = new JScrollPane(component);
        setJComponentEmptyBorder(jScrollPane, borderInsets);
        container.add(jScrollPane, constraints);
    }

    private static JPanel createJPanel(final Insets borderInsets) {
        return createJPanel(new FlowLayout(), borderInsets);
    }

    private static JPanel createJPanel(final LayoutManager layout, final Insets borderInsets) {
        final JPanel jPanel = new JPanel(layout, true);
        jPanel.setFocusCycleRoot(true);
        setJComponentEmptyBorder(jPanel, borderInsets);
        return jPanel;
    }

    private static void setJComponentEmptyBorder(@NotNull final JComponent jComponent, final Insets borderInsets) {
        jComponent.setBorder(new EmptyBorder(borderInsets));
    }

    private static void addGapToContainer(@NotNull final Container container, final int width, final int height) {
        container.add(Box.createRigidArea(new Dimension(width, height)));
    }

    private void showTablePanel(final String tablePanelName) {
        ((CardLayout) cardsPanel.getLayout()).show(cardsPanel, tablePanelName);
    }
}
