package view;

import controller.Controller;
import org.jetbrains.annotations.NotNull;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JRootPane;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.WindowConstants;
import javax.swing.table.AbstractTableModel;
import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Frame;
import java.awt.Toolkit;
import java.awt.Window;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import java.util.List;

import static java.awt.Component.CENTER_ALIGNMENT;

public final class MainView implements View {
    @Override
    public final void showLoginWindow() {
        final JDialog dialog = new JDialog((Frame) null, "Okno logowania", true);

        final Controller controller = null;

        addWindowListener(dialog, controller);

        final Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();

        final int screenWidth = screenSize.width;
        final int screenHeight = screenSize.height;

        final JButton loginButton = createJButton("Zaloguj", e -> {
            if (controller != null) {
                controller.login();
            } else {
                dialog.dispose();

                final JFrame frame = new JFrame("Komunikacja miejska");

                final JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.LEFT);
                tabbedPane.setDoubleBuffered(true);
                tabbedPane.setTabLayoutPolicy(JTabbedPane.SCROLL_TAB_LAYOUT);

                final List<JPanel> mainPanelsList = new ArrayList<>();

                final JPanel mainPanel = new JPanel(new BorderLayout(), true);

                final String string = "Gminy";

                mainPanel.setName(string);

                final JPanel cardsPanel = new JPanel(new CardLayout(), true);

                final JPanel tablePanel = new JPanel(new BorderLayout(), true);

                final String ALL = "All";

                tablePanel.setName(ALL);

                final JTable table = new JTable(new AbstractTableModel() {
                    // TODO
                    @Override
                    public int getRowCount() {
                        return 0;
                    }

                    @Override
                    public int getColumnCount() {
                        return 0;
                    }

                    @Override
                    public Object getValueAt(int rowIndex, int columnIndex) {
                        return null;
                    }
                });
                table.setAutoCreateRowSorter(true);

                final JScrollPane scrollPane = new JScrollPane(table);
                setBorder(scrollPane, 20, 10);

                tablePanel.add(scrollPane, BorderLayout.CENTER);

                final JPanel internalButtonsPanel = new JPanel(true);
                internalButtonsPanel.add(createJButton("Dodaj rekord", null));
                internalButtonsPanel.add(createJButton("Usuń rekordy", null));
                setBorder(internalButtonsPanel, 10, 80);

                tablePanel.add(internalButtonsPanel, BorderLayout.SOUTH);

                cardsPanel.add(tablePanel, ALL);

                mainPanel.add(cardsPanel, BorderLayout.CENTER);

                final JPanel buttonsPanel = new JPanel(true);
                buttonsPanel.setLayout(new BoxLayout(buttonsPanel, BoxLayout.Y_AXIS));

                final Dimension dimension = new Dimension(270, 50);

                buttonsPanel.add(createJLabel("Wyświetl " + string.toLowerCase(), dimension));
                buttonsPanel.add(Box.createRigidArea(new Dimension(0, 15)));

                final JButton button = createJButton("wszystkie", e1 -> {
                    if (controller != null) {
                        controller.showGminy();
                    } else {
                        ((CardLayout)cardsPanel.getLayout()).show(cardsPanel, ALL);
                    }
                });
                setJComponentProperties(button, Font.PLAIN, dimension);
                button.setHorizontalAlignment(SwingConstants.CENTER);

                buttonsPanel.add(button);
                buttonsPanel.setBorder(BorderFactory.createEmptyBorder(30, 20,30, 50));

                mainPanel.add(buttonsPanel, BorderLayout.EAST);

                mainPanelsList.add(mainPanel);

                for (int i = 0; i < mainPanelsList.size(); i++) {
                    tabbedPane.add(mainPanelsList.get(i));
                    tabbedPane.setTabComponentAt(i, createJLabel(tabbedPane.getTitleAt(i), new Dimension(180, 50)));
                }
                frame.add(tabbedPane);
                addWindowListener(frame, controller);
                frame.setSize(screenWidth, screenHeight);
                frame.setExtendedState(Frame.MAXIMIZED_BOTH);
                frame.setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
                frame.setVisible(true);
            }
        });

        final GroupLayout groupLayout = new GroupLayout(dialog.getContentPane());
        groupLayout.setAutoCreateGaps(true);
        groupLayout.setAutoCreateContainerGaps(true);

        final JLabel usernameLabel = new JLabel("Nazwa użytkownika: ");
        final JLabel passwordLabel = new JLabel("Hasło: ");
        final JTextField usernameField = new JTextField("", 20);
        final JPasswordField passwordField = new JPasswordField("", 20);

        final JButton cancelButton = createJButton("Anuluj", e -> closeWindow(controller, dialog));

        groupLayout.setHorizontalGroup(groupLayout.createParallelGroup(GroupLayout.Alignment.CENTER).addGroup(groupLayout.createSequentialGroup().addGroup(groupLayout.createParallelGroup(GroupLayout.Alignment.TRAILING).addComponent(usernameLabel).addComponent(passwordLabel)).addGroup(groupLayout.createParallelGroup(GroupLayout.Alignment.LEADING).addComponent(usernameField).addComponent(passwordField))).addGroup(groupLayout.createSequentialGroup().addComponent(loginButton).addComponent(cancelButton)));
        groupLayout.setVerticalGroup(groupLayout.createSequentialGroup().addGroup(groupLayout.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(usernameLabel).addComponent(usernameField)).addGroup(groupLayout.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(passwordLabel).addComponent(passwordField)).addGroup(groupLayout.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(loginButton).addComponent(cancelButton)));

        dialog.setLayout(groupLayout);
        dialog.pack();
        dialog.setLocation((screenWidth - dialog.getWidth()) / 2, (screenHeight - dialog.getHeight()) / 2);
        dialog.setResizable(false);
        dialog.setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);

        final JRootPane rootPane = dialog.getRootPane();
        rootPane.setDefaultButton(loginButton);
        rootPane.setDoubleBuffered(true);

        dialog.setVisible(true);
    }

    private static void addWindowListener(@NotNull final Window window, final Controller controller) {
        window.addWindowListener(new WindowAdapter() {
            @Override
            public final void windowClosing(final WindowEvent e) {
                closeWindow(controller, window);
            }
        });
    }

    private static void closeWindow(final Controller controller, final Window window) {
        if (controller != null) {
            controller.exit();
        } else {
            window.dispose();
        }
    }

    private static JButton createJButton(final String text, final ActionListener actionListener) {
        final JButton button = new JButton(text);
        button.addActionListener(actionListener);
        return button;
    }

    private static void setBorder(@NotNull final JComponent component, final int top, final int bottom) {
        component.setBorder(BorderFactory.createEmptyBorder(top, 10, bottom, 10));
    }

    private static JLabel createJLabel(final String text, final Dimension dimension) {
        final JLabel label = new JLabel(text, SwingConstants.CENTER);
        setJComponentProperties(label, Font.BOLD, dimension);
        return label;
    }

    private static void setJComponentProperties(@NotNull final JComponent component, final int fontStyle, final Dimension dimension) {
        component.setAlignmentX(CENTER_ALIGNMENT);
        component.setFont(new Font("Tahoma", fontStyle, 20));
        component.setPreferredSize(dimension);
    }
}
