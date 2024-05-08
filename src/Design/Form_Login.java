package Design;

import javax.swing.*;

import javax.swing.border.EmptyBorder;
import Controller.DBController;
import java.awt.*;
import java.awt.event.*;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Form_Login extends JFrame {
	public static String staffName;
	public static String userName;

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private CardLayout cardLayout;
	private JTextField tfdangnhaptk;
	private JPasswordField tfdangnhapmk;
	private JTextField tfdangnhaptk1;
	private JPasswordField tfdangnhapmk1;
	Controller.DBController dbController = new Controller.DBController();
	public static JPanel pnmain;
	
	public static void switchPanel(String card) {
	    CardLayout cl = (CardLayout)(pnmain.getLayout());
	    cl.show(pnmain, card);
	}

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Form_Login frame = new Form_Login();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public void giaovien() {
		Connection con = new Controller.DBController().getConnection();
		String sql1 = "SELECT staffname, staffpassword FROM staylearn.staff_account WHERE staffname = ? AND staffpassword = ?";
		
		if(tfdangnhaptk1.getText().isEmpty() || tfdangnhapmk1.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "Vui lòng điền đầy đủ thông tin");
			return; 
		}
		
		try {
			PreparedStatement stm = con.prepareStatement(sql1);
			stm.setString(1, tfdangnhaptk1.getText());
			stm.setString(2, tfdangnhapmk1.getText());
			ResultSet rs = stm.executeQuery();
			
			if (rs.next()) {
				staffName = tfdangnhaptk1.getText();
				JOptionPane.showMessageDialog(null, "Đăng nhập thành công");
				Staff_Home staffHome = new Staff_Home(); 
			    staffHome.setVisible(true); 
			    setVisible(false); 
			}
			else
				JOptionPane.showMessageDialog(null, "Tài khoản và mật khẩu không chính xác");
		} catch (SQLException e2) {
			// TODO: handle exception
			e2.printStackTrace();
		}
		
		
	}

	public Form_Login() {
		setTitle("STAYLEARN");
		setSize(1280,750);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLocationRelativeTo(null);
		setResizable(false);
		
		Register regis = new Register();
		regis.setBackground(new Color(255, 255, 255, 200));
		
		URL url_hhd = Form_Login.class.getResource("logo.png");
		Image img = Toolkit.getDefaultToolkit().createImage(url_hhd);
		this.setIconImage(img);
		
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		pnmain = new JPanel();
		pnmain.setBounds(450, 200, 400, 300);
		pnmain.setOpaque(false);
		contentPane.add(pnmain);
		pnmain.setLayout(new CardLayout(0, 0));

		JPanel controlPanel = new JPanel();
		controlPanel.setBackground(new Color(255, 255, 255, 200));
		pnmain.add(controlPanel, "1");
		controlPanel.setLayout(null);

		JLabel lbuser_login = new JLabel("STUDENT LOGIN");
		lbuser_login.setForeground(new Color(255, 0, 0));
		lbuser_login.setBounds(120, 73, 200, 25);
		lbuser_login.setFont(new Font("Consolas", Font.BOLD, 21));
		controlPanel.add(lbuser_login);

		JLabel lbuser_user = new JLabel("Username");
		lbuser_user.setBounds(69, 108, 96, 25);
		lbuser_user.setFont(new Font("Consolas", Font.BOLD, 15));
		controlPanel.add(lbuser_user);

		tfdangnhaptk = new JTextField();
		tfdangnhaptk.setBounds(175, 110, 170, 30);
		tfdangnhaptk.setFont(new Font("Consolas", Font.BOLD, 15));
		tfdangnhaptk.setColumns(10);
		controlPanel.add(tfdangnhaptk);

		JLabel lbuser_pass = new JLabel("Password");
		lbuser_pass.setBounds(69, 153, 80, 25);
		lbuser_pass.setFont(new Font("Consolas", Font.BOLD, 15));
		controlPanel.add(lbuser_pass);

		tfdangnhapmk = new JPasswordField();
		tfdangnhapmk.setBounds(175, 149, 170, 30);
		tfdangnhapmk.setFont(new Font("Consolas", Font.BOLD, 15));
		tfdangnhapmk.setColumns(10);
		controlPanel.add(tfdangnhapmk);

		JButton btlogin1 = new JButton("Login");
		btlogin1.setForeground(new Color(255, 255, 255));
		btlogin1.setBackground(new Color(65, 105, 225));
		btlogin1.setBounds(120, 208, 150, 30);
		btlogin1.setFont(new Font("Consolas", Font.BOLD, 15));
		controlPanel.add(btlogin1);
		btlogin1.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				if(tfdangnhaptk.getText().isEmpty() || tfdangnhapmk.getText().isEmpty()) {
					JOptionPane.showMessageDialog(null, "Vui lòng điền đầy đủ thông tin");
					return; 
				}
				if (dbController.checkUser(tfdangnhaptk.getText(), new String(tfdangnhapmk.getPassword()))) {
					userName = tfdangnhaptk.getText();
					JOptionPane.showMessageDialog(null, "Welcome!");
					setVisible(false);
					new User_Home();
				}
				else {
					JOptionPane.showMessageDialog(null, "Tài khoản và mật khẩu không chính xác");
					return;
				}
			}
		});

		JButton bt_stu = new JButton("STUDENT");
		bt_stu.setForeground(new Color(255, 20, 147));
		bt_stu.setBackground(new Color(255, 255, 255));
		bt_stu.setBounds(69, 30, 120, 21);
		bt_stu.setFont(new Font("Consolas", Font.BOLD, 15));
		bt_stu.setFocusable(false);
		controlPanel.add(bt_stu);

		JButton bt_staff = new JButton("STAFF");
		bt_staff.setBackground(new Color(255, 255, 255));
		bt_staff.setForeground(new Color(0, 128, 0));
		bt_staff.setBounds(220, 30, 120, 21);
		bt_staff.setFont(new Font("Consolas", Font.BOLD, 15));
		bt_staff.setFocusable(false);
		controlPanel.add(bt_staff);
		
		JLabel lblNewLabel = new JLabel("New Student? Register here");
		
		lblNewLabel.setForeground(new Color(220, 20, 60));
		lblNewLabel.setFont(new Font("Tahoma", Font.BOLD | Font.ITALIC, 15));
		lblNewLabel.setBounds(89, 258, 221, 21);
		controlPanel.add(lblNewLabel);
		JPanel pn_login = new JPanel();
		pn_login.setBackground(new Color(255, 255, 255, 200));
		pn_login.setLayout(null);
		
		pnmain.add(regis, "2");
		
		JLabel lbmanager = new JLabel("STAFF LOGIN");
		lbmanager.setForeground(new Color(51, 0, 255));
		lbmanager.setFont(new Font("Consolas", Font.BOLD, 21));
		lbmanager.setBounds(135, 71, 140, 25);
		pn_login.add(lbmanager);

		JLabel lbusername = new JLabel("Username");
		lbusername.setFont(new Font("Consolas", Font.BOLD, 15));
		lbusername.setBounds(69, 108, 96, 25);
		pn_login.add(lbusername);

		tfdangnhaptk1 = new JTextField();
		tfdangnhaptk1.setFont(new Font("Consolas", Font.BOLD, 14));
		tfdangnhaptk1.setColumns(10);
		tfdangnhaptk1.setBounds(175, 110, 170, 30);
		pn_login.add(tfdangnhaptk1);

		JLabel lbpassword = new JLabel("Password");
		lbpassword.setFont(new Font("Consolas", Font.BOLD, 15));
		lbpassword.setBounds(69, 153, 80, 25);
		pn_login.add(lbpassword);

		tfdangnhapmk1 = new JPasswordField();
		tfdangnhapmk1.setFont(new Font("Consolas", Font.BOLD, 14));
		tfdangnhapmk1.setColumns(10);
		tfdangnhapmk1.setBounds(175, 149, 170, 30);
		pn_login.add(tfdangnhapmk1);

		JButton btlogin2 = new JButton("Login");
		btlogin2.setForeground(new Color(255, 255, 255));
		btlogin2.setBackground(new Color(65, 105, 225));
		btlogin2.setBounds(128, 220, 150, 30);
		btlogin2.setFont(new Font("Consolas", Font.BOLD, 14));
		pn_login.add(btlogin2);
		btlogin2.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				Thread thread = new Thread(new Runnable() {
					@Override
					public void run() {
						giaovien();
					}
					
				});
				// Khởi động luồng
				thread.start();
			}
		});

		JButton bt_stu1 = new JButton("STUDENT");
		bt_stu1.setForeground(new Color(255, 20, 147));
		bt_stu1.setBackground(new Color(255, 255, 255));
		bt_stu1.setBounds(69, 30, 120, 21);
		bt_stu1.setFont(new Font("Consolas", Font.BOLD, 14));
		bt_stu1.setFocusable(false);
		pn_login.add(bt_stu1);

		JButton bt_staff1 = new JButton("STAFF");
		bt_staff1.setBackground(new Color(255, 255, 255));
		bt_staff1.setForeground(new Color(0, 128, 0));
		bt_staff1.setBounds(220, 30, 120, 21);
		bt_staff1.setFont(new Font("Consolas", Font.BOLD, 14));
		bt_staff1.setFocusable(false);
		pn_login.add(bt_staff1);
		pnmain.add(pn_login, "3");

		JLabel lb_nen = new JLabel("");
		lb_nen.setBackground(new Color(255, 255, 255));
		lb_nen.setIcon(new ImageIcon(Form_Login.class.getResource("/Design/login.jpg")));
		lb_nen.setBounds(0, 0, 1280, 750);
		contentPane.add(lb_nen);
		bt_staff.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				CardLayout c2 = (CardLayout) (pnmain.getLayout());
				c2.show(pnmain, "3");
			}
		});
		lblNewLabel.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				CardLayout c2 = (CardLayout) (pnmain.getLayout());
				c2.show(pnmain, "2");
			}
		});
		bt_stu1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				CardLayout c3 = (CardLayout) (pnmain.getLayout());
				c3.show(pnmain, "1");
			}
		});

	}
}
//( ͡° ͜ʖ ͡°)
//(´• ω •`) ♡
//c♪(๑ᴖ◡ᴖ๑)♪
//(・ω・)ﾉ
//(˶>ω<˶)