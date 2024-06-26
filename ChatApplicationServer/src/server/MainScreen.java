package server;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.Color;

public class MainScreen extends JFrame implements ActionListener{
	
	private static final long seriaVersionUID = 1L;

	private JPanel contentPane;
	
	JLabel lblPort;
	JTextField txtPort;
	JLabel lblServerName;
	JTextField txtServerName;
	
	static JTable clientTable;
	JButton openCloseButton;
	boolean isSocketOpened = false;
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MainScreen frame = new MainScreen();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public MainScreen() {
		JPanel mainContent = new JPanel(new GridBagLayout());
		GBCBuilder gbc = new GBCBuilder(1, 1).setInsets(5);
		JLabel lblIP = new JLabel("IP: " + SocketController.getThisIP());
		
		lblPort = new JLabel("Port");
		txtPort = new JTextField();
		lblServerName = new JLabel("Server name: ");
		txtServerName = new JTextField();
		openCloseButton = new JButton("Open server");
		openCloseButton.setForeground(Color.BLUE);
		openCloseButton.addActionListener(this);
		
		clientTable = new JTable(new Object[][] {}, new String[] {"Client name", "Port client"});
		clientTable.setRowHeight(25);
		JScrollPane clientScrollPane = new JScrollPane(clientTable);
		clientScrollPane.setBorder(BorderFactory.createTitledBorder("List of connected clients"));
		
		mainContent.add(lblIP, gbc.setFill(GridBagConstraints.BOTH).setWeight(0, 0).setSpan(1, 1));
		mainContent.add(lblPort, gbc.setGrid(2, 1).setWeight(0, 0).setSpan(1, 1));
		mainContent.add(txtPort, gbc.setGrid(3, 1).setWeight(1, 0));
		mainContent.add(lblServerName, gbc.setGrid(1, 2).setWeight(0, 0).setSpan(1, 1));
		mainContent.add(txtServerName, gbc.setGrid(2, 2).setWeight(1, 0).setSpan(2, 1));
		mainContent.add(clientScrollPane,
				gbc.setGrid(1, 3).setFill(GridBagConstraints.BOTH).setWeight(1, 1).setSpan(4, 1)
				);
		mainContent.add(openCloseButton, gbc.setGrid(1, 4).setWeight(1, 0).setSpan(4, 1));
		mainContent.setPreferredSize(new Dimension(250, 300));
		
		this.setTitle("Server chat");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 350, 381);
		contentPane = new JPanel();
//		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		this.setContentPane(mainContent);
		this.getRootPane().setDefaultButton(openCloseButton);
		this.setVisible(true);
		this.pack();
		this.setLocationRelativeTo(null);
		
		Main.socketController = new SocketController();
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if(!isSocketOpened) { 
			try {
				if(txtServerName.getText().isEmpty()) {
					JOptionPane.showMessageDialog(this, "Server name not is empty", "ERROR", JOptionPane.WARNING_MESSAGE);
				} else if(txtPort.getText().isEmpty()) {
					JOptionPane.showMessageDialog(this, "Port not is empty", "ERROR", JOptionPane.WARNING_MESSAGE);
				} else {
					Main.socketController.serverName = txtServerName.getText();
					Main.socketController.serverPort = Integer.parseInt(txtPort.getText());
					
					Main.socketController.openSocket(Main.socketController.serverPort);
					isSocketOpened = true;
					openCloseButton.setText("Close server");
				}
			} catch (NumberFormatException ex) {
				JOptionPane.showMessageDialog(this, "Port must be a positive integer", "ERROR", JOptionPane.WARNING_MESSAGE);
			}
		} else {
			Main.socketController.CloserSocket();
			isSocketOpened = false;
			openCloseButton.setText("Open server");
		}
	}
	
	public void updateClientTable() {
		Object[][] tableContent = new Object[Main.socketController.connectedClient.size()][2];
		for(int i = 0; i < Main.socketController.connectedClient.size(); i++) {
			tableContent[i][0] = Main.socketController.connectedClient.get(i).userName;
			tableContent[i][1] = Main.socketController.connectedClient.get(i).port;
		}
		
		clientTable.setModel(new DefaultTableModel(tableContent, new String[] {"Server name", "Port client"}));
	}
}
