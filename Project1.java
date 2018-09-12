import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.IOException;
import java.util.Stack;
public class Project1 extends JFrame{
	JSplitPane splitPane;
	JPanel rightPanel, controlPanel, controTop, controBottom, broswerSearchPanel, addressBookTab;
	JEditorPane browerTab, webDisplayPanel;
	JTabbedPane tabbedPane;
	JLabel statusLabel;
	JButton rNewPage, rDeletePage, rPageForward, rPageBackward, lBackward, lSearch;
	JTextField urlArea;
	JRadioButton freeForm, rectangle, oval, text; 
	JTable table;
	JScrollPane addressBookScrollPane, infoScroller;
	JTextArea infoDisplay;
	Stack<String> history; 
	public static void main(String[] args) {
		new Project1();
	}
	public Project1() {
		history = new Stack();
		//initialize the frame
		this.setTitle("Project1");
		this.setExtendedState(JFrame.MAXIMIZED_BOTH); 
		this.setUndecorated(false);
		this.setMinimumSize(new Dimension(1100, 800));
		this.setLocationRelativeTo(null);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		//set status label
		statusLabel = new JLabel("status:");
		statusLabel.setFont(new Font("Serif", Font.BOLD, 18));
		statusLabel.setHorizontalAlignment(SwingConstants.CENTER);
		this.add(statusLabel, BorderLayout.SOUTH);
		
		//deal with rightPane
		rightPanel = new JPanel();
		rightPanel.setLayout(new BorderLayout());
		//create controlPanel to hold buttons
		controlPanel = new JPanel();
		rightPanel.add(controlPanel, BorderLayout.SOUTH);
		controlPanel.setLayout(new GridLayout(2, 4, 5, 5));
        //create buttons
		ListenForButton lForButton = new ListenForButton();
		rNewPage = new JButton("New Page");
		rDeletePage = new JButton("Delete Page");
		rPageForward = new JButton("Page Forward");
		rPageBackward = new JButton("Page Backward");
		rNewPage.setPreferredSize(new Dimension(40, 40));//resize the button
		//bond event to button
		rNewPage.addActionListener(lForButton);
		rDeletePage.addActionListener(lForButton);
		rPageForward.addActionListener(lForButton);
		rPageBackward.addActionListener(lForButton);
		controlPanel.add(rNewPage);
		controlPanel.add(rDeletePage);
		controlPanel.add(rPageForward);
		controlPanel.add(rPageBackward);
		//create radio buttons
		freeForm = new JRadioButton("Free Form ink");
		rectangle = new JRadioButton("Rectangle");
		oval = new JRadioButton("Oval");
		text = new JRadioButton("Text");
		ButtonGroup gp1 = new ButtonGroup();
		gp1.add(freeForm);
		gp1.add(rectangle);
		gp1.add(oval);
		gp1.add(text);
		controlPanel.add(freeForm);
		controlPanel.add(rectangle);
		controlPanel.add(oval);
		controlPanel.add(text);
		//bond events to radio button
		freeForm.addActionListener(lForButton);
		rectangle.addActionListener(lForButton);
		oval.addActionListener(lForButton);
		text.addActionListener(lForButton);
		
		//create tabbed left pane
		tabbedPane = new JTabbedPane();
		
		//deal with browser tab
		browerTab = new JEditorPane();
		browerTab.setLayout(new BorderLayout());
		tabbedPane.addTab("Browser", browerTab);	
		urlArea = new JTextField("", 40);//url
		lBackward = new JButton("Backward");
		lBackward.setPreferredSize(new Dimension(100, 40));
		lBackward.addActionListener(lForButton);
		lSearch = new JButton("GO");
		lSearch.setPreferredSize(new Dimension(60, 40));
		browerTab.setEditable(false);
		broswerSearchPanel = new JPanel();
		broswerSearchPanel.add(lBackward);
		broswerSearchPanel.add(urlArea);
		broswerSearchPanel.add(lSearch);
		lSearch.addActionListener(lForButton);
		browerTab.add(broswerSearchPanel, BorderLayout.NORTH);
		webDisplayPanel = new JEditorPane();
		webDisplayPanel.setEditable(false);
		browerTab.add(webDisplayPanel, BorderLayout.CENTER);
		
		//deal with address book tab
		addressBookTab = new JPanel();
		addressBookTab.setLayout(new GridLayout(2, 1));
		String[] columnNames = {"Name",
                "Phone Number",
                "Sport",
                "# of Years"};
		Object[][] data = {
			    {"Kathy", "111111",
			     "Snowboarding", new Integer(5)},
			    {"John", "222222",
			     "Rowing", new Integer(3)},
			    {"Sue", "333333",
			     "Knitting", new Integer(2)},
			    {"Jane", "444444",
			     "Speed reading", new Integer(20)},
			    {"Joe", "555555",
			     "Pool", new Integer(10)}
			};
		JTable table = new JTable(data, columnNames);
		addressBookScrollPane = new JScrollPane(table, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		infoDisplay = new JTextArea(30, 40);
		infoScroller = new JScrollPane(infoDisplay, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		tabbedPane.addTab("Address Book", addressBookTab);
		addressBookTab.add(addressBookScrollPane);
		addressBookTab.add(infoScroller);
		ListSelectionModel model = table.getSelectionModel();
		model.addListSelectionListener(new ListSelectionListener() {

			@Override
			public void valueChanged(ListSelectionEvent e) {
				// TODO Auto-generated method stub
				if (!model.isSelectionEmpty()) {
					int selectedMinRow = model.getMinSelectionIndex();
					int selectedMaxRow = model.getMaxSelectionIndex();
					String result = "";
					for (int i = selectedMinRow; i <= selectedMaxRow; i++) {
						String firstName = table.getModel().getValueAt(i, 0).toString();
						String phoneNumber = table.getModel().getValueAt(i, 1).toString();
						String sport = table.getModel().getValueAt(i, 2).toString();
						String years = table.getModel().getValueAt(i, 3).toString();
						result += "First Name: " + firstName + "\nPhone Number: " + 
						phoneNumber + "\nSport: " + sport + "\n# of Years: " + years + "\n\n\n";
					}
					infoDisplay.setText(result);
				}
			}
			
		});
		//deal with splitPane
		splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, new JScrollPane(tabbedPane), new JScrollPane(rightPanel));
		splitPane.setDividerLocation(this.getWidth() / 2);
		this.add(splitPane);
		this.addComponentListener(new ComponentAdapter(){
			public void componentResized(ComponentEvent evt) {
	            Component c = (Component)evt.getSource();
	            splitPane.setDividerLocation(c.getWidth() / 2);
	        }
		});
		this.setVisible(true);
	}
	private class ListenForButton implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			if (e.getSource() == rNewPage) {
				statusLabel.setText("status: New Page");
			}
			if (e.getSource() == rDeletePage) {
				statusLabel.setText("status: Delete the Page");
			}
			if (e.getSource() == rPageForward) {
				statusLabel.setText("status: Page Forward");
			}
			if (e.getSource() == rPageBackward) {
				statusLabel.setText("status: Page Backward");
			}
			if (e.getSource() == freeForm) {
				statusLabel.setText("status: Free form ink selected");
			}
			if (e.getSource() == rectangle) {
				statusLabel.setText("status: rectangle selected");
			}
			if (e.getSource() == oval) {
				statusLabel.setText("status: Oval selected");
			}
			if (e.getSource() == text) {
				statusLabel.setText("status: Text selected");
			}
			//handle browser searching event
			if (e.getSource() == lSearch) {
				String url = urlArea.getText();
				try {
					webDisplayPanel.setPage(url);
					history.push(url);
			  } catch (IOException exception) {
			      JOptionPane.showMessageDialog(Project1.this, "Attempted to read a bad URL: " + url, "Error", JOptionPane.ERROR_MESSAGE);
			      urlArea.setText("");
			  }
			}
			//handle browser backward event
			if (e.getSource() == lBackward) {
				String prevPage = history.pop();
					statusLabel.setText("URL1:" + prevPage);
					try {
						webDisplayPanel.setPage(prevPage);
						urlArea.setText(prevPage);
						statusLabel.setText("URL2:" + prevPage);
					} catch (IOException e1) {
						JOptionPane.showMessageDialog(Project1.this, "Attempted to read a bad URL: " + prevPage, "Error", JOptionPane.ERROR_MESSAGE);
					    urlArea.setText("");
					}
				
				
			}
		}
		
	}
	
}
