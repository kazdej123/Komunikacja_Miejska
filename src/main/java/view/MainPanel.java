package view;

import org.jetbrains.annotations.NotNull;

import javax.swing.*;
import javax.swing.table.AbstractTableModel;
import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.LayoutManager;
import java.awt.Window;
import java.util.function.IntSupplier;
import java.util.function.Supplier;

final class MainPanel extends JPanel {
    private static final int DEFAULT_FONT_SIZE = 18;

    private static final Dimension defaultJComponentDimension = new Dimension(14 * DEFAULT_FONT_SIZE, 45);

    /*@FunctionalInterface
    interface ObjectSupplier extends Supplier<Object> {}*/

    static final class TableViewNames {
        private final String tablePanelName;
        private final Object choosingDialogTitle;

        TableViewNames(final String tablePanelName, final Object choosingDialogTitle) {
            this.tablePanelName = tablePanelName;
            this.choosingDialogTitle = choosingDialogTitle;
        }
    }

    private final Container cardsPanel = createJPanel(new CardLayout(), 0, 10, 0, 10);
    private final Container buttonsPanel = createJPanel(null, 10, 10, 10, 10);

    private final Window ownerWindow;

    MainPanel(final Window ownerWindow, final String name) {
        super(new BorderLayout());
        this.ownerWindow = ownerWindow;

        setName(name);
        setFocusCycleRoot(true);
        setJComponentEmptyBorder(this, 20, 10, 30, 10);

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
        final Container tablePanel = createJPanel(new BorderLayout(), 0, 0, 0, 0);
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

        final JComponent jScrollPane = new JScrollPane(jTable);
        setJComponentEmptyBorder(jScrollPane, 0, 0, 10, 0);

        tablePanel.add(jScrollPane, BorderLayout.CENTER);

        final Container southButtonsPanel = createJPanel(10, 0, 0, 0);
        southButtonsPanel.add(DefaultView.createJButton("Dodaj rekord", DEFAULT_FONT_SIZE, e -> {
            try {
                insertRowRunnable.run();
            } catch (final NullPointerException e1) {
                // TODO
            }
        }));
        addGapToContainer(southButtonsPanel, 20, 0);
        southButtonsPanel.add(DefaultView.createJButton("Usuń rekordy", DEFAULT_FONT_SIZE, null/*TODO*/));

        tablePanel.add(southButtonsPanel, BorderLayout.SOUTH);

        cardsPanel.add(tablePanel, tablePanelName);

        final JComponent jButton = DefaultView.createJButton(buttonText, DEFAULT_FONT_SIZE, e -> {
            try {
                showTableViewRunnable.run();
            } catch (final NullPointerException e1) {
                if (choosingDialogTitle != null) {
                    final JDialog jDialog = new JDialog(ownerWindow, "Wybierz " + choosingDialogTitle);
                    jDialog.setLayout(new BorderLayout());

                    // TODO
                    final int fontSize = 15;

                    final JButton okButton = DefaultView.createJButton("Ok", fontSize, e2 -> {
                        jDialog.dispose();
                        showTablePanel(tablePanelName);
                    });

                    final Container southDialogPanel = createJPanel(10, 0, 0, 0);
                    southDialogPanel.add(okButton);
                    southDialogPanel.add(DefaultView.createJButton("Anuluj", fontSize, e2 -> jDialog.dispose()));

                    jDialog.add(southDialogPanel, BorderLayout.SOUTH);
                    setJComponentEmptyBorder(jDialog.getRootPane(), 10, 10, 10, 10);
                    DefaultView.initJDialog(jDialog, okButton);
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

    private static JPanel createJPanel(final int top, final int left, final int bottom, final int right) {
        return createJPanel(new FlowLayout(), top, left, bottom, right);
    }

    private static JPanel createJPanel(final LayoutManager layout, final int top, final int left, final int bottom, final int right) {
        final JPanel jPanel = new JPanel(layout, true);
        jPanel.setFocusCycleRoot(true);
        setJComponentEmptyBorder(jPanel, top, left, bottom, right);
        return jPanel;
    }

    private static void setJComponentEmptyBorder(@NotNull final JComponent jComponent, final int top, final int left, final int bottom, final int right) {
        jComponent.setBorder(BorderFactory.createEmptyBorder(top, left, bottom, right));
    }

    private static void addGapToContainer(@NotNull final Container container, final int width, final int height) {
        container.add(Box.createRigidArea(new Dimension(width, height)));
    }

    private void showTablePanel(final String tablePanelName) {
        ((CardLayout) cardsPanel.getLayout()).show(cardsPanel, tablePanelName);
    }
}
