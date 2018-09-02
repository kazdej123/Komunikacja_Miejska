package view;

import org.jetbrains.annotations.NotNull;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.AbstractTableModel;
import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.LayoutManager;
import java.awt.Window;
import java.awt.event.ActionListener;
import java.util.function.IntSupplier;
import java.util.function.Supplier;

final class MainPanel extends JPanel {
    private static final int FONT_SIZE = 18;

    private static final Dimension buttonDimension = new Dimension(14 * FONT_SIZE, 45);

    @FunctionalInterface
    interface ObjectSupplier extends Supplier<Object> {}

    static final class TableViewNames {
        private final String tablePanelName;
        private final String choosingDialogLabelText;

        TableViewNames(final String tablePanelName, final String choosingDialogLabelText) {
            this.tablePanelName = tablePanelName;
            this.choosingDialogLabelText = choosingDialogLabelText;
        }
    }

    private final JPanel cardsPanel = createFocusCycleRootedPanel(new CardLayout(), 0, 0, 0, 0);
    private final JPanel buttonsPanel = createFocusCycleRootedPanel(null, 0, 0, 0, 0);

    private final Window ownerWindow;

    MainPanel(final String name, final Window ownerWindow) {
        super(new BorderLayout());
        this.ownerWindow = ownerWindow;

        setName(name);
        setFocusCycleRoot(true);
        setEmptyBorder(this, 20, 20, 20, 20);

        cardsPanel.add(new JPanel(), null);
        
        add(cardsPanel, BorderLayout.CENTER);

        buttonsPanel.setLayout(new BoxLayout(buttonsPanel, BoxLayout.Y_AXIS));
        buttonsPanel.add(DefaultView.createBoldLabel("Wyświetl " + name.toLowerCase(), buttonDimension, (int) (1.1 * FONT_SIZE)));

        add(buttonsPanel, BorderLayout.EAST);
    }

    final void addTableView(@NotNull final TableViewNames tableViewNames, final IntSupplier intSupplier, final ObjectSupplier objectSupplier, final Runnable insertRowRunnable, final String buttonText, final Runnable showTableViewRunnable, final String... columnNames) {
        addTableView(tableViewNames.tablePanelName, intSupplier, objectSupplier, insertRowRunnable, buttonText, showTableViewRunnable, tableViewNames.choosingDialogLabelText, columnNames);
    }

    final void addTableView(final String tablePanelName, final IntSupplier intSupplier, final ObjectSupplier objectSupplier, final Runnable insertRowRunnable, final String buttonText, final Runnable showTableViewRunnable, final String choosingDialogLabelText, final String[] columnNames) {
        final JPanel tablePanel = createFocusCycleRootedPanel(new BorderLayout(), 0, 0, 0, 0);
        tablePanel.setName(tablePanelName);

        final JTable table = new JTable(new AbstractTableModel() {
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
                    return 0;
                }
            }

            @Override
            public final Object getValueAt(final int rowIndex, final int columnIndex) {
                return objectSupplier.get();
            }
        });
        table.setAutoCreateRowSorter(true);
        DefaultView.setJComponentFont(table.getTableHeader(), Font.BOLD, 12);

        final JScrollPane scrollPane = new JScrollPane(table);
        setEmptyBorder(scrollPane, 20, 10, 10, 10);

        tablePanel.add(scrollPane, BorderLayout.CENTER);

        final JPanel internalButtonsPanel = createFocusCycleRootedPanel(new FlowLayout(), 0, 0, 0, 0);
        addButtonToContainer(internalButtonsPanel, "Dodaj rekord", e -> {
            try {
                insertRowRunnable.run();
            } catch (final NullPointerException e1) {
                // TODO
            }
        });
        addButtonToContainer(internalButtonsPanel, "Usuń rekordy", null); // TODO

        tablePanel.add(internalButtonsPanel, BorderLayout.SOUTH);

        cardsPanel.add(tablePanel, tablePanelName);

        final JButton button = DefaultView.createButton(buttonText, FONT_SIZE, e -> {
            try {
                showTableViewRunnable.run();
            } catch (final NullPointerException e1) {
                if (choosingDialogLabelText != null) {
                    final JDialog dialog = new JDialog(ownerWindow, "Okno wyboru");
                    dialog.setLayout(new BorderLayout());

                    final JPanel northDialogPanel = createPanel(new FlowLayout(), 0, 0, 0, 0);
                    northDialogPanel.add(DefaultView.createLabel("Wybierz " + choosingDialogLabelText + ":", Font.PLAIN, 15));

                    dialog.add(northDialogPanel, BorderLayout.NORTH);
                    // TODO
                    final int dialogButtonFontSize = 15;

                    final JButton okButton = DefaultView.createButton("Ok", dialogButtonFontSize, e2 -> {
                        dialog.dispose();
                        showTablePanel(tablePanelName);
                    });

                    final JPanel southDialogPanel = createFocusCycleRootedPanel(new FlowLayout(), 0, 0, 0, 0);
                    southDialogPanel.add(okButton);
                    southDialogPanel.add(DefaultView.createButton("Anuluj", dialogButtonFontSize, e2 -> dialog.dispose()));

                    dialog.add(southDialogPanel, BorderLayout.SOUTH);
                    DefaultView.initDialog(dialog, okButton);
                } else {
                    showTablePanel(tablePanelName);
                }
            }
        });
        DefaultView.centerJComponent(button);
        button.setMaximumSize(buttonDimension);

        buttonsPanel.add(Box.createRigidArea(new Dimension(0, 15)));
        buttonsPanel.add(button);
    }

    private static JPanel createFocusCycleRootedPanel(final LayoutManager layout, final int top, final int left, final int bottom, final int right) {
        final JPanel panel = createPanel(layout, top, left, bottom, right);
        panel.setFocusCycleRoot(true);
        return panel;
    }

    private static JPanel createPanel(final LayoutManager layout, final int top, final int left, final int bottom, final int right) {
        final JPanel panel = new JPanel(layout, true);
        setEmptyBorder(panel, top, left, bottom, right);
        return panel;
    }

    private static void setEmptyBorder(@NotNull final JComponent component, final int top, final int left, final int bottom, final int right) {
        component.setBorder(BorderFactory.createEmptyBorder(top, left, bottom, right));
    }

    private static void addButtonToContainer(@NotNull final Container container, final String text, final ActionListener actionListener) {
        container.add(DefaultView.createButton(text, FONT_SIZE, actionListener));
    }

    private void showTablePanel(final String tablePanelName) {
        ((CardLayout) cardsPanel.getLayout()).show(cardsPanel, tablePanelName);
    }
}
