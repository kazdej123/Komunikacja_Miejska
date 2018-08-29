package view;

import org.jetbrains.annotations.NotNull;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.AbstractTableModel;
import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Frame;
import java.awt.LayoutManager;
import java.awt.event.ActionListener;
import java.util.function.IntSupplier;
import java.util.function.Supplier;

final class MainPanel extends JPanel {
    static final String ALL = "All";

    private static final int FONT_SIZE = 18;

    private static final Dimension buttonDimension = new Dimension(14 * FONT_SIZE, 45);

    private final JPanel cardsPanel = createCycleRootFocusedPanel(new CardLayout());
    private final JPanel buttonsPanel = createCycleRootFocusedPanel(null);

    private final Frame ownerFrame;

    static final class TableViewNames {
        private final String tablePanelName;
        private final String choosingDialogTitle;

        TableViewNames(final String tablePanelName, final String choosingDialogTitle) {
            this.tablePanelName = tablePanelName;
            this.choosingDialogTitle = choosingDialogTitle;
        }
    }

    MainPanel(final String name, final Frame ownerFrame) {
        super(new BorderLayout());
        this.ownerFrame = ownerFrame;

        setName(name);
        setFocusCycleRoot(true);
        add(cardsPanel, BorderLayout.CENTER);

        buttonsPanel.setLayout(new BoxLayout(buttonsPanel, BoxLayout.Y_AXIS));
        buttonsPanel.add(DefaultView.createLabel("Wyświetl " + name.toLowerCase(), buttonDimension, (int) (1.1 * FONT_SIZE)));
        setEmptyBorder(buttonsPanel, 30, 5, 30, 20);

        add(buttonsPanel, BorderLayout.EAST);
    }

    final void addTableView(@NotNull final TableViewNames tableViewNames, final IntSupplier intSupplier, final Supplier<Object> objectSupplier, final String buttonText, final Runnable showTableViewRunnable, final Runnable insertRowRunnable, final String... columnNames) {
        final JPanel tablePanel = createCycleRootFocusedPanel(new BorderLayout());

        final String tablePanelName = tableViewNames.tablePanelName;

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
        DefaultView.setJComponentBoldFont(table.getTableHeader(), 12);

        final JScrollPane scrollPane = new JScrollPane(table);
        setEmptyTablePanelBorder(scrollPane, 20, 10);

        tablePanel.add(scrollPane, BorderLayout.CENTER);

        final JPanel internalButtonsPanel = createCycleRootFocusedPanel(new FlowLayout());
        addInternalButton(internalButtonsPanel, "Dodaj rekord", e -> {
            try {
                insertRowRunnable.run();
            } catch (final NullPointerException e1) {
                // TODO
            }
        });
        addInternalButton(internalButtonsPanel, "Usuń rekordy", null); // TODO
        setEmptyTablePanelBorder(internalButtonsPanel, 10, 30);

        tablePanel.add(internalButtonsPanel, BorderLayout.SOUTH);

        cardsPanel.add(tablePanel, tablePanelName);

        final JButton button = DefaultView.createButton(buttonText, e -> {
            try {
                showTableViewRunnable.run();
            } catch (final NullPointerException e1) {
                ((CardLayout) cardsPanel.getLayout()).show(cardsPanel, tablePanelName);
            }
        });
        DefaultView.centerJComponent(button);
        setJComponentPlainFont(button, FONT_SIZE);
        button.setMaximumSize(buttonDimension);

        buttonsPanel.add(Box.createRigidArea(new Dimension(0, 15)));
        buttonsPanel.add(button);
    }

    private static JPanel createCycleRootFocusedPanel(final LayoutManager layout) {
        final JPanel panel = new JPanel(layout);
        panel.setFocusCycleRoot(true);
        return panel;
    }

    private static void setEmptyTablePanelBorder(final JComponent component, final int top, final int bottom) {
        setEmptyBorder(component, top, 10, bottom, 10);
    }

    private static void setEmptyBorder(@NotNull final JComponent component, final int top, final int left, final int bottom, final int right) {
        component.setBorder(BorderFactory.createEmptyBorder(top, left, bottom, right));
    }

    private static void addInternalButton(@NotNull final JPanel internalButtonsPanel, final String text, final ActionListener actionListener) {
        final JButton button = DefaultView.createButton(text, actionListener);
        setJComponentPlainFont(button, 18);
        button.setPreferredSize(new Dimension(180, 40));
        internalButtonsPanel.add(button);
    }

    private static void setJComponentPlainFont(final JComponent jComponent, final int size) {
        DefaultView.setJComponentFont(jComponent, Font.PLAIN, size);
    }
}
