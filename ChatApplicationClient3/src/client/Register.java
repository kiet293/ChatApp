package client;

import java.awt.Color;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import dao.DAO;

import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import javax.swing.JSeparator;
import javax.swing.JTextField;
import javax.swing.JPasswordField;
import javax.swing.SwingConstants;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.awt.event.ActionEvent;
import java.awt.SystemColor;

public class Register extends JFrame {

	private JPanel contentPane;
	private JTextField txtUsername_register;
	private JPasswordField txtPassword_register;
	private JPasswordField txtRePassword;
	private JFrame frmLoginSystem;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Register frame = new Register();
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

	public Register() {
		
		setTitle("Register");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(200, 200, 597, 336);
		contentPane = new JPanel();
		contentPane.setBackground(SystemColor.inactiveCaption);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblNewLabel = new JLabel("Register System");
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel.setBounds(146, 10, 253, 31);
		lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 25));
		contentPane.add(lblNewLabel);
		
		JSeparator separator_1 = new JSeparator();
		separator_1.setBounds(45, 51, 472, 2);
		contentPane.add(separator_1);
		
		JLabel lblUsername = new JLabel("Username");
		lblUsername.setFont(new Font("Tahoma", Font.PLAIN, 20));
		lblUsername.setBounds(70, 63, 115, 21);
		contentPane.add(lblUsername);
		
		txtUsername_register = new JTextField();
		txtUsername_register.setBackground(SystemColor.controlHighlight);
		txtUsername_register.setFont(new Font("Tahoma", Font.PLAIN, 20));
		txtUsername_register.setColumns(10);
		txtUsername_register.setBounds(195, 63, 295, 24);
		contentPane.add(txtUsername_register);
		
		JLabel lblPassword = new JLabel("Password");
		lblPassword.setFont(new Font("Tahoma", Font.PLAIN, 20));
		lblPassword.setBounds(70, 121, 85, 21);
		contentPane.add(lblPassword);
		
		txtPassword_register = new JPasswordField();
		txtPassword_register.setBackground(SystemColor.controlHighlight);
		txtPassword_register.setFont(new Font("Tahoma", Font.PLAIN, 20));
		txtPassword_register.setBounds(195, 119, 295, 24);
		contentPane.add(txtPassword_register);
		
		JLabel lblRegister = new JLabel("You already have an account. Please select ");
		lblRegister.setHorizontalAlignment(SwingConstants.CENTER);
		lblRegister.setFont(new Font("Tahoma", Font.ITALIC, 17));
		lblRegister.setBounds(10, 206, 400, 28);
		contentPane.add(lblRegister);
		
		JButton btnRegister = new JButton("Register");
		btnRegister.setBackground(Color.GREEN);
		btnRegister.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				StringBuilder sb = new StringBuilder();
				String rpw = txtRePassword.getText();
				String pw = txtPassword_register.getText();
				String us = txtUsername_register.getText();	
				if(DAO.register(us, pw) != null && JOptionPane.showConfirmDialog(null, "Comfirm if you want to register", "Register Systems",
						JOptionPane.YES_NO_OPTION)== JOptionPane.YES_NO_OPTION) {

					if(us.equals("")) {
						txtUsername_register.requestFocus();
						JOptionPane.showMessageDialog(txtUsername_register, "Username is required \n", "ERROR!", JOptionPane.WARNING_MESSAGE);
					}
					
//					String password = new String(txtPassword_register.getPassword());
					if(!us.equals("") && pw.equals("")) {
						JOptionPane.showMessageDialog(txtPassword_register, "Password is required \n", "ERROR!", JOptionPane.WARNING_MESSAGE);
					}
					
//					String re_password = new String(txtRePassword.getPassword());
					if(rpw.equals("")) {
						JOptionPane.showMessageDialog(txtPassword_register, "Re-enter Password is required \n", "ERROR!", JOptionPane.WARNING_MESSAGE);
					}
					if(!rpw.equals("") && !pw.equals("") && !pw.equals(rpw)) {
						JOptionPane.showMessageDialog(txtPassword_register, "Password and Re-enter Password must be the same \n", "ERROR!", JOptionPane.WARNING_MESSAGE);
						txtRePassword.requestFocus();
					} 
					
							
					if(!us.equals("") && !pw.equals("") && !rpw.equals("") && pw.contains(pw) && us.contains(us) && pw.equals(rpw)) {
						txtUsername_register.requestFocus();
						txtPassword_register.setText(null);
						txtUsername_register.setText(null);
						txtRePassword.setText(null);
						JOptionPane.showMessageDialog(null, "Register success!", "Notification" ,JOptionPane.INFORMATION_MESSAGE);
					}
				} else {
					txtUsername_register.requestFocus();
					txtPassword_register.setText(null);
					txtUsername_register.setText(null);
					txtRePassword.setText(null);
				}
				
				
			}
		});
		btnRegister.setFont(new Font("Tahoma", Font.PLAIN, 20));
		btnRegister.setBounds(70, 259, 115, 30);
		contentPane.add(btnRegister);
		
		JButton btnLogin = new JButton("Login");
		btnLogin.setForeground(SystemColor.desktop);
		btnLogin.setBackground(SystemColor.inactiveCaption);
		btnLogin.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				frmLoginSystem = new JFrame("Login");
				if(JOptionPane.showConfirmDialog(frmLoginSystem, "Comfirm if you want to login", "Login",
						JOptionPane.YES_NO_OPTION)== JOptionPane.YES_NO_OPTION) {
					Login login = new Login();
					login.main(null);
				}
			}
		});
		btnLogin.setFont(new Font("Tahoma", Font.ITALIC, 17));
		btnLogin.setBounds(375, 205, 115, 30);
		contentPane.add(btnLogin);
		
		JButton btnReset = new JButton("Reset");
		btnReset.setBackground(Color.LIGHT_GRAY);
		btnReset.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				txtUsername_register.setText(null);
				txtPassword_register.setText(null);
				txtRePassword.setText(null);
			}
		});
		btnReset.setFont(new Font("Tahoma", Font.PLAIN, 20));
		btnReset.setBounds(214, 259, 115, 30);
		contentPane.add(btnReset);
		
		JButton btnExit = new JButton("Exit");
		btnExit.setBackground(Color.RED);
		btnExit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				frmLoginSystem = new JFrame("Exit");
				if(JOptionPane.showConfirmDialog(frmLoginSystem, "Comfirm if you want to exit", "Register Systems",
						JOptionPane.YES_NO_OPTION)== JOptionPane.YES_NO_OPTION) {
						System.exit(0);
				}
			}
		});
		btnExit.setFont(new Font("Tahoma", Font.PLAIN, 20));
		btnExit.setBounds(355, 259, 115, 30);
		contentPane.add(btnExit);
		
		JLabel lblReenterPassword = new JLabel("Re-enter Password");
		lblReenterPassword.setHorizontalAlignment(SwingConstants.CENTER);
		lblReenterPassword.setFont(new Font("Tahoma", Font.PLAIN, 18));
		lblReenterPassword.setBounds(0, 172, 175, 21);
		contentPane.add(lblReenterPassword);
		
		txtRePassword = new JPasswordField();
		txtRePassword.setBackground(SystemColor.controlHighlight);
		txtRePassword.setFont(new Font("Tahoma", Font.PLAIN, 20));
		txtRePassword.setBounds(195, 169, 295, 24);
		contentPane.add(txtRePassword);
		
		JSeparator separator_1_1 = new JSeparator();
		separator_1_1.setBounds(57, 246, 472, 2);
		contentPane.add(separator_1_1);
	}
}