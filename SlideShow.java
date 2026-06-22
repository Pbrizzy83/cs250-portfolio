import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.EventQueue;
import java.awt.FlowLayout;
import java.awt.HeadlessException;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import java.awt.Color;

public class SlideShow extends JFrame {

	//Declare Variables
	private JPanel slidePane;
	private JPanel textPane;
	private JPanel buttonPane;
	private JPanel bottomPane;
	private CardLayout card;
	private CardLayout cardText;
	private JButton btnPrev;
	private JButton btnNext;
	private JLabel lblSlide;
	private JLabel lblTextArea;
	
	/*
	 * Product Owner update: SNHU Travel is now focusing the booking tool on
	 * detox/wellness travel. These arrays keep the new wellness photos and text
	 * together so each card stays easy to review and update.
	 * Photo sources: Wikimedia Commons
	 * 1. https://commons.wikimedia.org/wiki/File:Yoga_Retreat_(51261924655).jpg
	 * 2. https://commons.wikimedia.org/wiki/File:Meditation_2.jpg
	 * 3. https://commons.wikimedia.org/wiki/File:Iceland_Blue_Lagoon.jpg
	 * 4. https://commons.wikimedia.org/wiki/File:Hot_Springs_National_Park_007.jpg
	 * 5. https://commons.wikimedia.org/wiki/File:Negombo_Beach_resort_pool_(Unsplash).jpg
	 */
	private static final String[] IMAGE_FILES = {
		"WellnessImage1.jpg",
		"WellnessImage2.jpg",
		"WellnessImage3.jpg",
		"WellnessImage4.jpg",
		"WellnessImage5.jpg"
	};
	
	private static final String[] SLIDE_TEXT = {
		"#1 Yoga Retreat: Slow down, reset, and build a trip around movement and mindfulness.",
		"#2 Meditation Escape: A quiet wellness stay for travelers who want rest, reflection, and less screen time.",
		"#3 Blue Lagoon, Iceland: Geothermal water, spa treatments, and a once-in-a-lifetime wellness stop.",
		"#4 Hot Springs, Arkansas: Natural thermal water and historic bathhouse experiences close to home.",
		"#5 Beach Wellness Resort: Sunshine, pools, and recovery-focused travel for a calmer vacation."
	};

	/**
	 * Create the application.
	 */
	public SlideShow() throws HeadlessException {
		initComponent();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initComponent() {
		//Initialize variables to empty objects
		card = new CardLayout();
		cardText = new CardLayout();
		slidePane = new JPanel();
		textPane = new JPanel();
		textPane.setBackground(Color.BLUE);
		textPane.setBounds(5, 470, 790, 50);
		textPane.setVisible(true);
		buttonPane = new JPanel();
		bottomPane = new JPanel();
		btnPrev = new JButton();
		btnNext = new JButton();
		lblSlide = new JLabel();
		lblTextArea = new JLabel();

		//Setup frame attributes
		setSize(800, 600);
		setLocationRelativeTo(null);
		setTitle("SNHU Travel Detox/Wellness SlideShow");
		getContentPane().setLayout(new BorderLayout(10, 50));
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		//Setting the layouts for the panels
		slidePane.setLayout(card);
		textPane.setLayout(cardText);
		bottomPane.setLayout(new BorderLayout());
		
		// Add each updated wellness slide and its matching caption.
		for (int i = 0; i < IMAGE_FILES.length; i++) {
			lblSlide = new JLabel();
			lblTextArea = new JLabel();
			lblSlide.setHorizontalAlignment(JLabel.CENTER);
			lblTextArea.setHorizontalAlignment(JLabel.CENTER);
			lblTextArea.setForeground(Color.WHITE);
			lblSlide.setText(getResizeIcon(i));
			lblTextArea.setText(getTextDescription(i));
			slidePane.add(lblSlide, "card" + i);
			textPane.add(lblTextArea, "cardText" + i);
		}

		getContentPane().add(slidePane, BorderLayout.CENTER);

		buttonPane.setLayout(new FlowLayout(FlowLayout.CENTER, 20, 10));

		btnPrev.setText("Previous");
		btnPrev.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				goPrevious();
			}
		});
		buttonPane.add(btnPrev);

		btnNext.setText("Next");
		btnNext.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				goNext();
			}
		});
		buttonPane.add(btnNext);

		/*
		 * The original project placed captions and buttons in the same BorderLayout
		 * area. This bottom panel keeps both visible so the new text requirement is met.
		 */
		bottomPane.add(textPane, BorderLayout.CENTER);
		bottomPane.add(buttonPane, BorderLayout.SOUTH);
		getContentPane().add(bottomPane, BorderLayout.SOUTH);
	}

	/**
	 * Previous Button Functionality
	 */
	private void goPrevious() {
		card.previous(slidePane);
		cardText.previous(textPane);
	}
	
	/**
	 * Next Button Functionality
	 */
	private void goNext() {
		card.next(slidePane);
		cardText.next(textPane);
	}

	/**
	 * Method to get the images
	 */
	private String getResizeIcon(int i) {
		String imagePath = "/resources/" + IMAGE_FILES[i];
		return "<html><body><img width='800' height='500' src='" + getClass().getResource(imagePath) + "'></body></html>";
	}
	
	/**
	 * Method to get the text values
	 */
	private String getTextDescription(int i) {
		return "<html><body><font size='4'>" + SLIDE_TEXT[i] + "</font></body></html>";
	}
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {

			@Override
			public void run() {
				SlideShow ss = new SlideShow();
				ss.setVisible(true);
			}
		});
	}
}
