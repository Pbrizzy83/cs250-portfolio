import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.Desktop;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Rectangle;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.net.URL;

import javax.swing.BorderFactory;
import javax.swing.DefaultListModel;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ListCellRenderer;
import javax.swing.ListSelectionModel;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.WindowConstants;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;

public class TopFiveDestinationList {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                TopDestinationListFrame topDestinationListFrame = new TopDestinationListFrame();
                topDestinationListFrame.setTitle("SNHU Travel - Top 5 Destination List");
                topDestinationListFrame.setVisible(true);
            }
        });
    }
}

class TopDestinationListFrame extends JFrame {
    private static final Color OCEAN_BLUE = new Color(23, 92, 125);
    private static final Color SAND = new Color(248, 243, 232);
    private static final Color SUNSET = new Color(210, 107, 78);

    private DefaultListModel<TextAndIcon> listModel;
    private JPanel listPanel;

    public TopDestinationListFrame() {
        super("SNHU Travel - Top Five Destination List");

        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setSize(980, 760);
        setLocationRelativeTo(null);

        listModel = new DefaultListModel<TextAndIcon>();

        // The destinations below satisfy the ordered top-five user story.
        addDestination("Kyoto, Japan",
                "Historic temples, peaceful gardens, and colorful shrines make Kyoto a favorite cultural getaway.",
                "Kyoto Cherry Blossom Explorer", "/resources/packages/kyoto.html",
                "/resources/kyoto.jpg");
        addDestination("Paris, France",
                "Paris blends world-famous landmarks, art museums, and cafe-lined streets into a classic city escape.",
                "Paris Highlights Package", "/resources/packages/paris.html",
                "/resources/paris.jpg");
        addDestination("Bali, Indonesia",
                "Bali offers tropical beaches, rice terraces, and wellness retreats for a relaxing island vacation.",
                "Bali Beach and Culture Package", "/resources/packages/bali.html",
                "/resources/bali.jpg");
        addDestination("Rome, Italy",
                "Rome surrounds travelers with ancient ruins, lively piazzas, and unforgettable Italian food.",
                "Rome History and Cuisine Tour", "/resources/packages/rome.html",
                "/resources/rome.jpg");
        addDestination("Santorini, Greece",
                "Santorini is known for whitewashed villages, blue-domed churches, and sunsets over the Aegean Sea.",
                "Santorini Island Escape", "/resources/packages/santorini.html",
                "/resources/santorini.jpg");

        getContentPane().setLayout(new BorderLayout());
        getContentPane().add(createLandingPanel(), BorderLayout.CENTER);
    }

    private JPanel createLandingPanel() {
        JPanel panel = new JPanel(new GridLayout(3, 1));
        panel.setBackground(SAND);
        panel.setBorder(BorderFactory.createEmptyBorder(90, 80, 90, 80));

        JLabel title = new JLabel("SNHU Travel", SwingConstants.CENTER);
        title.setFont(new Font("Segoe UI", Font.BOLD, 42));
        title.setForeground(OCEAN_BLUE);

        JButton viewListButton = new JButton("<html><u>View the Top Five Destinations</u></html>");
        viewListButton.setFont(new Font("Segoe UI", Font.BOLD, 26));
        viewListButton.setForeground(Color.WHITE);
        viewListButton.setBackground(SUNSET);
        viewListButton.setFocusPainted(false);
        viewListButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        viewListButton.addActionListener(event -> showDestinationList());

        JLabel nameLabel = new JLabel("Prepared by Patrick Brizuela", SwingConstants.CENTER);
        nameLabel.setFont(new Font("Segoe UI", Font.PLAIN, 18));
        nameLabel.setForeground(OCEAN_BLUE);

        panel.add(title);
        panel.add(viewListButton);
        panel.add(nameLabel);

        return panel;
    }

    private void showDestinationList() {
        if (listPanel == null) {
            listPanel = createDestinationListPanel();
        }

        getContentPane().removeAll();
        getContentPane().add(listPanel, BorderLayout.CENTER);
        revalidate();
        repaint();
    }

    private JPanel createDestinationListPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(SAND);

        JLabel header = new JLabel("Top Five Travel Destinations", SwingConstants.CENTER);
        header.setOpaque(true);
        header.setBackground(OCEAN_BLUE);
        header.setForeground(Color.WHITE);
        header.setFont(new Font("Segoe UI", Font.BOLD, 28));
        header.setBorder(BorderFactory.createEmptyBorder(18, 12, 18, 12));

        JList<TextAndIcon> list = new JList<TextAndIcon>(listModel);
        list.setCellRenderer(new TextAndIconListCellRenderer(14));
        list.setBackground(SAND);
        list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        list.setFixedCellHeight(128);
        list.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        list.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent event) {
                int index = list.locationToIndex(event.getPoint());
                if (index >= 0) {
                    TextAndIcon destination = listModel.getElementAt(index);
                    openPackageLink(destination.getPackageUrl());
                }
            }
        });

        JScrollPane scrollPane = new JScrollPane(list);
        scrollPane.setBorder(BorderFactory.createEmptyBorder(18, 24, 12, 24));
        scrollPane.getViewport().setBackground(SAND);

        JLabel footer = new JLabel("Photo credits: Wikimedia Commons photographers; see resources/PHOTO_CREDITS.txt.",
                SwingConstants.CENTER);
        footer.setFont(new Font("Segoe UI", Font.ITALIC, 13));
        footer.setForeground(new Color(75, 75, 75));
        footer.setBorder(BorderFactory.createEmptyBorder(0, 12, 16, 12));

        panel.add(header, BorderLayout.NORTH);
        panel.add(scrollPane, BorderLayout.CENTER);
        panel.add(footer, BorderLayout.SOUTH);

        return panel;
    }

    private void addDestination(String name, String description, String packageName, String packageResource, String imagePath) {
        ImageIcon icon = new ImageIcon(getClass().getResource(imagePath));
        TextAndIcon tai = new TextAndIcon(name, description, packageName, packageResource, icon, listModel.getSize() + 1);
        listModel.addElement(tai);
    }

    private void openPackageLink(String packageResource) {
        try {
            URL packagePage = getClass().getResource(packageResource);
            Desktop.getDesktop().browse(packagePage.toURI());
        } catch (Exception exception) {
            JOptionPane.showMessageDialog(this, "Package page: " + packageResource, "Top-Selling Travel Package",
                    JOptionPane.INFORMATION_MESSAGE);
        }
    }
}

class TextAndIcon {
    private String name;
    private String description;
    private String packageName;
    private String packageResource;
    private Icon icon;
    private int rank;

    public TextAndIcon(String name, String description, String packageName, String packageResource, Icon icon, int rank) {
        this.name = name;
        this.description = description;
        this.packageName = packageName;
        this.packageResource = packageResource;
        this.icon = icon;
        this.rank = rank;
    }

    public String getDisplayText() {
        return "<html><body style='width: 650px; padding: 4px 8px;'>"
                + "<span style='font-size: 16px; font-weight: bold; color: #175c7d;'>" + rank + ". " + name
                + "</span><br>"
                + "<span style='font-size: 12px; color: #333333;'>" + description + "</span><br>"
                + "<span style='font-size: 12px; color: #9f3f2c;'>Top-selling package: <u>" + packageName
                + "</u></span></body></html>";
    }

    public String getPackageUrl() {
        return packageResource;
    }

    public Icon getIcon() {
        return icon;
    }
}

class TextAndIconListCellRenderer extends JLabel implements ListCellRenderer<TextAndIcon> {
    private static final Border NO_FOCUS_BORDER = new EmptyBorder(1, 1, 1, 1);

    private Border insideBorder;

    public TextAndIconListCellRenderer() {
        this(0, 0, 0, 0);
    }

    public TextAndIconListCellRenderer(int padding) {
        this(padding, padding, padding, padding);
    }

    public TextAndIconListCellRenderer(int topPadding, int rightPadding, int bottomPadding, int leftPadding) {
        insideBorder = BorderFactory.createEmptyBorder(topPadding, leftPadding, bottomPadding, rightPadding);
        setOpaque(true);
        setIconTextGap(18);
        setPreferredSize(new Dimension(860, 128));
    }

    public Component getListCellRendererComponent(JList<? extends TextAndIcon> list, TextAndIcon value,
            int index, boolean isSelected, boolean hasFocus) {
        TextAndIcon tai = value;

        setText(tai.getDisplayText());
        setIcon(tai.getIcon());

        if (isSelected) {
            setBackground(new Color(226, 239, 241));
        } else {
            setBackground(index % 2 == 0 ? Color.WHITE : new Color(252, 247, 238));
        }

        setForeground(list.getForeground());

        Border outsideBorder;

        if (hasFocus) {
            outsideBorder = UIManager.getBorder("List.focusCellHighlightBorder");
        } else {
            outsideBorder = NO_FOCUS_BORDER;
        }

        setBorder(BorderFactory.createCompoundBorder(outsideBorder, insideBorder));
        setComponentOrientation(list.getComponentOrientation());
        setEnabled(list.isEnabled());
        setFont(new Font("Segoe UI", Font.PLAIN, 14));

        return this;
    }

    // These methods are overridden to keep list rendering responsive.
    public void validate() {}
    public void invalidate() {}
    public void repaint() {}
    public void revalidate() {}
    public void repaint(long tm, int x, int y, int width, int height) {}
    public void repaint(Rectangle r) {}
}
