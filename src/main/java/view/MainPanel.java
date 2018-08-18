package view;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.AbstractTableModel;
import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;

import static view.DefaultView.*;

final class MainPanel extends JPanel {
    private static final JPanel cardsPanel = createDoubleBufferedPanel(new CardLayout());
    private static final JPanel buttonsPanel = createDoubleBufferedPanel(null);

    MainPanel(final String name, final String fontName, final DefaultView.RowCountGetter rowCountGetter, final DefaultView.ValueGetter valueGetter, final DefaultView.TableViewShowingHandler tableViewShowingHandler, final String... columnNames) {
        super(new BorderLayout(), true);
        this.setName(name);

        final JPanel cardsPanel = createDoubleBufferedPanel(new CardLayout());

        this.add(cardsPanel, BorderLayout.CENTER);

        final JPanel buttonsPanel = createDoubleBufferedPanel(null);
        buttonsPanel.setLayout(new BoxLayout(buttonsPanel, BoxLayout.Y_AXIS));

        final Dimension dimension = new Dimension(270, 50);

        buttonsPanel.add(createLabel("Wyświetl " + name.toLowerCase(), fontName, dimension));
        setEmptyBorder(buttonsPanel, 30, 20, 30, 50);

        this.add(buttonsPanel, BorderLayout.EAST);
        //addTableView("All", cardsPanel, "wszystkie", fontName, dimension, buttonsPanel, rowCountGetter, valueGetter, tableViewShowingHandler, columnNames);
        addTableView("wszystkie", fontName, cardsPanel, "wszystkie", dimension, buttonsPanel, rowCountGetter, valueGetter, tableViewShowingHandler);
    }

    static final void addTableView(final String name, final String fontName, final JPanel cardsPanel, final String text, final Dimension dimension, final JPanel buttonsPanel, final DefaultView.RowCountGetter rowCountGetter, final DefaultView.ValueGetter valueGetter, final TableViewShowingHandler tableViewShowingHandler, final String... columnNames) {
        final JPanel tablePanel = createDoubleBufferedPanel(new BorderLayout());
        tablePanel.setName(name);

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
                    return rowCountGetter.getRowCount();
                } catch (final NullPointerException e) {
                    return 0;
                }
            }

            @Override
            public final Object getValueAt(final int rowIndex, final int columnIndex) {
                return valueGetter.getValueAt();
            }
        });
        table.setAutoCreateRowSorter(true);

        final JScrollPane scrollPane = new JScrollPane(table);
        setEmptyBorder(scrollPane, 20, 10, 10, 10);

        tablePanel.add(scrollPane, BorderLayout.CENTER);

        final JPanel internalButtonsPanel = createDoubleBufferedPanel(new FlowLayout());
        internalButtonsPanel.add(createAdjustedButton("Dodaj rekord", fontName, null));
        internalButtonsPanel.add(createAdjustedButton("Usuń rekordy", fontName, null));
        setEmptyBorder(internalButtonsPanel, 10, 10, 80, 10);

        tablePanel.add(internalButtonsPanel, BorderLayout.SOUTH);

        cardsPanel.add(tablePanel);

        final JButton button = createAdjustedButton(text, fontName, e1 -> {
            try {
                tableViewShowingHandler.showTableView();
            } catch (final NullPointerException e) {
                ((CardLayout)cardsPanel.getLayout()).show(cardsPanel, name);
            }
        });
        setDefaultJComponentProperties(button, dimension);

        buttonsPanel.add(Box.createRigidArea(new Dimension(0, 15)));
        buttonsPanel.add(button);
    }

    public JPanel getCardsPanel() {
        return cardsPanel;
    }

    public JPanel getButtonsPanel() {
        return buttonsPanel;
    }
}
