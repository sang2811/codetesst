package Design;

import javax.swing.JPanel;
import javax.swing.JPasswordField;

import java.awt.CardLayout;
import java.awt.Color;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import Design.Form_Login;
import java.awt.Font;
import javax.swing.SwingConstants;

import Controller.DBController;

import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class Register extends JPanel {

	private static final long serialVersionUID = 1L;
	private JTextField tfuser;
	private JPasswordField tfpass;
	private JPasswordField tfconfirm;
	Controller.DBController dbController = new Controller.DBController();
	/**
	 * Create the panel.
	 */
	
	public Register() {
		setBackground(new Color(255, 255, 255));
		setSize(400,300);
		setLayout(null);
		
		JLabel lbuser_login = new JLabel("REGISTER");
		lbuser_login.setHorizontalAlignment(SwingConstants.CENTER);
		lbuser_login.setForeground(Color.RED);
		lbuser_login.setFont(new Font("Consolas", Font.BOLD, 25));
		lbuser_login.setBounds(109, 38, 200, 25);
		add(lbuser_login);
		
		JLabel lbuser_user = new JLabel("Username");
		lbuser_user.setHorizontalAlignment(SwingConstants.RIGHT);
		lbuser_user.setFont(new Font("Consolas", Font.BOLD, 15));
		lbuser_user.setBounds(67, 75, 96, 25);
		add(lbuser_user);
		
		tfuser = new JTextField();
		tfuser.setFont(new Font("Consolas", Font.BOLD, 15));
		tfuser.setColumns(10);
		tfuser.setBounds(189, 73, 170, 30);
		add(tfuser);
		
		JLabel lbuser_pass = new JLabel("Password");
		lbuser_pass.setHorizontalAlignment(SwingConstants.RIGHT);
		lbuser_pass.setFont(new Font("Consolas", Font.BOLD, 15));
		lbuser_pass.setBounds(83, 114, 80, 25);
		add(lbuser_pass);
		
		tfpass = new JPasswordField();
		tfpass.setFont(new Font("Consolas", Font.BOLD, 15));
		tfpass.setColumns(10);
		tfpass.setBounds(189, 112, 170, 30);
		add(tfpass);
		
		JButton btregister = new JButton("Register");
		
		btregister.setForeground(Color.WHITE);
		btregister.setFont(new Font("Consolas", Font.BOLD, 15));
		btregister.setBackground(new Color(65, 105, 225));
		btregister.setBounds(133, 216, 150, 30);
		add(btregister);
		
		JLabel lbuser_pass_1 = new JLabel("Confirm Password");
		lbuser_pass_1.setFont(new Font("Consolas", Font.BOLD, 15));
		lbuser_pass_1.setBounds(35, 155, 144, 25);
		add(lbuser_pass_1);
		
		tfconfirm = new JPasswordField();
		tfconfirm.setFont(new Font("Consolas", Font.BOLD, 15));
		tfconfirm.setColumns(10);
		tfconfirm.setBounds(189, 151, 170, 30);
		add(tfconfirm);
		
		JLabel lblNewLabel = new JLabel("Already have an account? Sign in now");
		
		lblNewLabel.setForeground(new Color(50, 205, 50));
		lblNewLabel.setFont(new Font("Tahoma", Font.BOLD | Font.ITALIC, 15));
		lblNewLabel.setBounds(55, 256, 292, 26);
		add(lblNewLabel);
		
		btregister.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(tfuser.getText().isEmpty() || tfpass.getText().isEmpty() || tfconfirm.getText().isEmpty()) {
					JOptionPane.showMessageDialog(null, "Vui lòng điền đầy đủ thông tin");
					return; 
				}
				else if (tfpass.getText().equals(tfconfirm.getText())) {
					dbController.addUser(tfuser.getText(), new String(tfpass.getPassword()));
	                JOptionPane.showMessageDialog(null, "Đăng ký thành công");
				} else {
					JOptionPane.showMessageDialog(null, "Hai mật khẩu không giống nhau");
				}
			}
		});
		
		lblNewLabel.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				Form_Login.switchPanel("1");
			}
		});
	}
}
