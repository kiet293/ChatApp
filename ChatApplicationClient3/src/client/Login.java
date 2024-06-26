package client;

import java.awt.Color;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.event.AncestorListener;

import dao.DAO;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.JPasswordField;
import java.awt.Font;

import javax.swing.JButton;
import javax.swing.JSeparator;
import java.awt.event.ActionListener;
import java.awt.event.MouseListener;
import java.awt.event.ActionEvent;
import javax.swing.SwingConstants;
import javax.swing.JTextArea;
import java.awt.SystemColor;

public class Login extends JFrame {

	private JPanel contentPane;
	private JTextField txtUsername;
	private JPasswordField txtPassword;
	private JFrame frmLoginSystem;
	private JFrame frmRegisterSystem;
	private Register register;
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Login frame = new Login();
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
	public Login() {
		setTitle("Login");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(200, 200, 571, 317);
		contentPane = new JPanel();
		contentPane.setBackground(SystemColor.inactiveCaption);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblNewLabel = new JLabel("Login System");
		lblNewLabel.setBackground(new Color(255, 228, 225));
		lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 25));
		lblNewLabel.setBounds(180, 22, 172, 28);
		contentPane.add(lblNewLabel);
		
		JLabel lblUsername = new JLabel("Username");
		lblUsername.setFont(new Font("Tahoma", Font.PLAIN, 20));
		lblUsername.setBounds(37, 84, 115, 21);
		contentPane.add(lblUsername);
		
		JLabel lblPassword = new JLabel("Password");
		lblPassword.setFont(new Font("Tahoma", Font.PLAIN, 20));
		lblPassword.setBounds(37, 139, 85, 21);
		contentPane.add(lblPassword);
		
		txtUsername = new JTextField();
		txtUsername.setForeground(new Color(255, 255, 255));
		txtUsername.setBackground(SystemColor.controlHighlight);
		txtUsername.setFont(new Font("Tahoma", Font.PLAIN, 20));
		txtUsername.setBounds(173, 81, 209, 24);
		contentPane.add(txtUsername);
		txtUsername.setColumns(10);
		
		txtPassword = new JPasswordField();
		txtPassword.setForeground(new Color(255, 255, 255));
		txtPassword.setBackground(SystemColor.controlHighlight);
		txtPassword.setFont(new Font("Tahoma", Font.PLAIN, 20));
		txtPassword.setBounds(173, 137, 209, 24);
		contentPane.add(txtPassword);
		
		JButton btnLogin = new JButton("Login");
		btnLogin.setFont(new Font("Tahoma", Font.PLAIN, 20));
		btnLogin.setBackground(Color.green);
		btnLogin.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String password = txtPassword.getText();
				String username = txtUsername.getText();
				
				if(DAO.login(username, password)!=null) {
					txtUsername.requestFocus();
					txtPassword.setText(null);
					txtUsername.setText(null);
					
					Main main = new Main();
					main.main(null);

				} else {
					if(JOptionPane.showConfirmDialog(null, "Invalid Login Details. Please re-enter!", "Login Error", JOptionPane.ERROR_MESSAGE) == JOptionPane.ERROR_MESSAGE) {
						txtPassword.setText(null);
						txtUsername.setText(null);
					}
				}
			}
		});
		btnLogin.setBounds(69, 239, 96, 32);
		contentPane.add(btnLogin);
		
		JButton btnReset = new JButton("Reset");
		btnReset.setBackground(Color.LIGHT_GRAY);
		btnReset.setFont(new Font("Tahoma", Font.PLAIN, 20));
		btnReset.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				txtUsername.setText(null);
				txtPassword.setText(null);
			}
		});
		btnReset.setBounds(216, 239, 96, 32);
		contentPane.add(btnReset);
		
		JButton btnExit = new JButton("Exit");
		btnExit.setBackground(Color.RED);
		btnExit.setFont(new Font("Tahoma", Font.PLAIN, 20));
		btnExit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				frmLoginSystem = new JFrame("Exit");
				if(JOptionPane.showConfirmDialog(frmLoginSystem, "Comfirm if you want to exit", "Login Systems",
						JOptionPane.YES_NO_OPTION)== JOptionPane.YES_NO_OPTION) {
						System.exit(0);
				}
			}
		});
		btnExit.setBounds(349, 239, 96, 32);
		contentPane.add(btnExit);
		
		JSeparator separator = new JSeparator();
		separator.setBounds(52, 221, 472, 2);
		contentPane.add(separator);
		
		JSeparator separator_1 = new JSeparator();
		separator_1.setBounds(37, 60, 472, 2);
		contentPane.add(separator_1);
		
		JLabel lblRegister = new JLabel("Don't have account?");
		lblRegister.setHorizontalAlignment(SwingConstants.CENTER);
		lblRegister.setFont(new Font("Tahoma", Font.ITALIC, 17));
		lblRegister.setBounds(81, 183, 227, 28);
		contentPane.add(lblRegister);
		
		JButton btnRegister = new JButton("Register");
		btnRegister.setBackground(Color.GREEN);
		btnRegister.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				frmRegisterSystem = new JFrame("Register");
				if(JOptionPane.showConfirmDialog(frmRegisterSystem, "Comfirm if you want to register", "Register System",
						JOptionPane.YES_NO_OPTION)== JOptionPane.YES_NO_OPTION) {
					Register regist = new Register();
					regist.main(null);
				}
			}
		});
		btnRegister.setFont(new Font("Tahoma", Font.PLAIN, 20));
		btnRegister.setBounds(293, 182, 115, 28);
		contentPane.add(btnRegister);
	
	}
}