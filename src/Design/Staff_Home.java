package Design;

import java.awt.Container;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.border.EmptyBorder;

import com.toedter.calendar.JDateChooser;

import Controller.DBController;
import Controller.WriteTextFile_Staff;
import Controller.WriteTextFile_User;
import Design.*;
import Design.Form_Login;
import Design.Staff_Home.SharedSocketService;

import java.awt.BorderLayout;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import java.awt.Image;

import javax.swing.ImageIcon;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.UIManager;
import java.awt.SystemColor;
import java.awt.Toolkit;

import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.SwingWorker;
import javax.swing.Timer;
import javax.swing.JButton;
import javax.swing.JTextField;
import javax.swing.JComboBox;

public class Staff_Home extends JFrame {
	private JTextField tf_manhanvien;
	private JTextField tf_hovaten;
	private JDateChooser tf_ngaysinh;
	private JTextField tf_sdt;
	private JTextField tf_email;
	private JTextField tf_diachi;
	private JComboBox tf_vitri;
	private JTextField tf_staffname;
	private JComboBox cbb_gioitinh;
	private JTextArea tf_chat;
	private JTextArea tf_giaodien;
	private JButton bt_gui;
	private static Socket socket;
	SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
	
	private static final String url = "172.20.10.2";
	private static final int PORT = 8000;

	public class SharedSocketService {

		public static synchronized Socket getSocket() {
			if (socket == null) {
				try {
					socket = new Socket(url, PORT);
				} catch (Exception e) {
					// TODO: handle exception
				}
			}
			return socket;
		}
	}
	
	public static int countOffice() {
		Connection conn = new Controller.DBController().getConnection();
		String sql = "select count(*) from staylearn.office" ;
		PreparedStatement stm;
		int count = 0;
		try {
			stm = conn.prepareStatement(sql);
			ResultSet rs = stm.executeQuery();
			if (rs.next()) {
				count = rs.getInt("count(*)");
            }
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		return count;
    }
	
	public void load_data() {
		Connection conn = new Controller.DBController().getConnection();
		String sql = "SELECT IDStaff, name, gender, dateofbirth, address, phonenumber, email, position FROM staylearn.staff WHERE staffname = ?;" ;
		PreparedStatement stm;
		try {
			stm = conn.prepareStatement(sql);
			stm.setString(1, tf_staffname.getText());
			ResultSet rs = stm.executeQuery();
			if (rs.next()) {
				
	            
				tf_manhanvien.setText(rs.getString("IDStaff"));
				tf_hovaten.setText(rs.getString("name"));
				cbb_gioitinh.setSelectedItem(rs.getString("gender"));
				
				String ngaySinhStr = rs.getString("dateofbirth");
				if (ngaySinhStr != null && !ngaySinhStr.isEmpty()) {
				    try {
				        Date ngaySinhDate = sdf.parse(ngaySinhStr);
				        tf_ngaysinh.setDate(ngaySinhDate);
				    } catch (ParseException ex) {
				        
				        ex.printStackTrace();
				    }
				}
				
				tf_diachi.setText(rs.getString("address"));
				tf_sdt.setText(rs.getString("phonenumber"));
				tf_email.setText(rs.getString("email"));
				String position = rs.getString("position");
				tf_vitri.setSelectedItem(position != null ? position : "Giáo viên");
            }
			else {
				return;
			}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	}
	
	public static int countStudent() {
		Connection conn = new Controller.DBController().getConnection();
		String sql = "select count(*) from staylearn.student" ;
		PreparedStatement stm;
		int count = 0;
		try {
			stm = conn.prepareStatement(sql);
			ResultSet rs = stm.executeQuery();
			if (rs.next()) {
				count = rs.getInt("count(*)");
            }
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		return count;
    }
	
	public static int countStaff() {
		Connection conn = new Controller.DBController().getConnection();
		String sql = "select count(*) from staylearn.staff" ;
		PreparedStatement stm;
		int count = 0;
		try {
			stm = conn.prepareStatement(sql);
			ResultSet rs = stm.executeQuery();
			if (rs.next()) {
				count = rs.getInt("count(*)");
            }
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		return count;
    }
	
	public static int countClass() {
		Connection conn = new Controller.DBController().getConnection();
		String sql = "select count(*) from staylearn.class" ;
		PreparedStatement stm;
		int count = 0;
		try {
			stm = conn.prepareStatement(sql);
			ResultSet rs = stm.executeQuery();
			if (rs.next()) {
				count = rs.getInt("count(*)");
            }
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		return count;
    }
	
	public Staff_Home() {
		Container con = getContentPane();
		getContentPane().setLayout(null);
		
		URL url_hhd = Staff_Home.class.getResource("logo.png");
		Image img = Toolkit.getDefaultToolkit().createImage(url_hhd);
		this.setIconImage(img);
		
		JPanel pn_home = new JPanel();
		pn_home.setBackground(new Color(192, 192, 192));
		pn_home.setBounds(0, 0, 1280, 750);
		getContentPane().add(pn_home);
		pn_home.setLayout(null);
		
		JPanel menu = new JPanel();
		menu.setBackground(new Color(224, 255, 255));
		menu.setBounds(0, 0, 0, 750);
		pn_home.add(menu);
		menu.setLayout(null);
		
		JLabel lbl_title = new JLabel("ANH NGỮ STAYLEARN");
		lbl_title.setHorizontalAlignment(SwingConstants.CENTER);
		lbl_title.setFont(new Font("Tahoma", Font.BOLD, 15));
		lbl_title.setBounds(0, 0, 189, 66);
		menu.add(lbl_title);
		
		JLabel lb_trangchu = new JLabel("TRANG CHỦ");
		lb_trangchu.setHorizontalAlignment(SwingConstants.CENTER);
		
		lb_trangchu.setFont(new Font("Tahoma", Font.BOLD, 14));
		lb_trangchu.setBounds(0, 88, 244, 42);
		menu.add(lb_trangchu);
		
		JLabel lb_thongtin = new JLabel("THÔNG TIN CÁ NHÂN");
		lb_thongtin.setHorizontalAlignment(SwingConstants.CENTER);
		lb_thongtin.setFont(new Font("Tahoma", Font.BOLD, 14));
		lb_thongtin.setBounds(0, 130, 244, 42);
		menu.add(lb_thongtin);
		
		
		JLabel lb_lichhoc = new JLabel("LỊCH HỌC");
		lb_lichhoc.setHorizontalAlignment(SwingConstants.CENTER);
		lb_lichhoc.setFont(new Font("Tahoma", Font.BOLD, 14));
		lb_lichhoc.setBounds(0, 171, 244, 42);
		menu.add(lb_lichhoc);
		
		JLabel lb_hocphi = new JLabel("HỌC PHÍ");
		lb_hocphi.setHorizontalAlignment(SwingConstants.CENTER);
		lb_hocphi.setFont(new Font("Tahoma", Font.BOLD, 14));
		lb_hocphi.setBounds(0, 214, 244, 42);
		menu.add(lb_hocphi);
		
		JLabel lb_cauhinh = new JLabel("CẤU HÌNH");
		lb_cauhinh.setHorizontalAlignment(SwingConstants.CENTER);
		lb_cauhinh.setFont(new Font("Tahoma", Font.BOLD, 14));
		lb_cauhinh.setBounds(0, 257, 244, 42);
		menu.add(lb_cauhinh);
		
		JLabel lb_trogiup = new JLabel("TRỢ GIÚP");
		lb_trogiup.setHorizontalAlignment(SwingConstants.CENTER);
		lb_trogiup.setFont(new Font("Tahoma", Font.BOLD, 14));
		lb_trogiup.setBounds(0, 301, 244, 42);
		menu.add(lb_trogiup);
		
		JLabel lb_chinhsach = new JLabel("CHÍNH SÁCH");
		lb_chinhsach.setHorizontalAlignment(SwingConstants.CENTER);
		lb_chinhsach.setFont(new Font("Tahoma", Font.BOLD, 14));
		lb_chinhsach.setBounds(0, 344, 244, 42);
		menu.add(lb_chinhsach);
		
		JLabel lb_message = new JLabel("TRÒ CHUYỆN");
		lb_message.setHorizontalAlignment(SwingConstants.CENTER);
		lb_message.setFont(new Font("Tahoma", Font.BOLD, 14));
		lb_message.setBounds(0, 387, 244, 42);
		menu.add(lb_message);
		
		JLabel lb_dangxuat = new JLabel("ĐĂNG XUẤT");
		lb_dangxuat.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				setVisible(false);
				Form_Login loginForm = new Form_Login();
		        loginForm.setVisible(true);
			}
		});
		lb_dangxuat.setHorizontalAlignment(SwingConstants.CENTER);
		lb_dangxuat.setFont(new Font("Tahoma", Font.BOLD, 14));
		lb_dangxuat.setBounds(0, 430, 244, 42);
		menu.add(lb_dangxuat);
		
		JLabel lb_close = new JLabel("");
		lb_close.setIcon(new ImageIcon(Staff_Home.class.getResource("/Design/cancel.jpg")));;
		lb_close.setBounds(209, 0, 24, 30);
		menu.add(lb_close);
		
		JLabel lb_menu = new JLabel("");
		lb_menu.setBounds(21, 21, 34, 24);
		int width = 244;
		int height = 750;
		

		lb_menu.setIcon(new ImageIcon(Staff_Home.class.getResource("/Design/menu.jpg")));
		pn_home.add(lb_menu);
		
		JPanel pn_admin = new JPanel();
		pn_admin.setBackground(new Color(0, 128, 128));
		pn_admin.setBounds(0, 67, 236, 653);
		pn_home.add(pn_admin);
		pn_admin.setLayout(null);
		
		Form_Login dk = new Form_Login();
		String staffname = Form_Login.staffName;
		
		tf_staffname = new JTextField();
		tf_staffname.setText(staffname);
		tf_staffname.setFont(new Font("Tahoma", Font.BOLD, 20));
		tf_staffname.setColumns(10);
		tf_staffname.setBackground(new Color(102, 205, 170));
		tf_staffname.setBounds(91, 40, 120, 25);
		pn_admin.add(tf_staffname);
		
		JLabel lb_avatar = new JLabel("");
		lb_avatar.setBounds(10, 11, 85, 81);
		pn_admin.add(lb_avatar);
		lb_avatar.setIcon(new ImageIcon(Staff_Home.class.getResource("/Design/avatar.png")));
		
		JPanel container = new JPanel();
		container.setBounds(0, 67, 1280, 683);
		pn_home.add(container);
		container.setLayout(new CardLayout(0, 0));
		
		JPanel home = new JPanel();
		home.setBackground(SystemColor.window);
		container.add(home, "home");
		home.setLayout(null);
		
		JPanel pn_qltt = new JPanel();
		pn_qltt.setBounds(244, 56, 1012, 151);
		pn_qltt.setLayout(null);
		pn_qltt.setBackground(Color.WHITE);
		pn_qltt.setOpaque(false);	
		home.add(pn_qltt);
		
		JPanel pn_coso = new JPanel();
		pn_coso.setLayout(null);
		pn_coso.setBackground(new Color(255, 128, 192));
		pn_coso.setBounds(10, 11, 200, 115);
		pn_qltt.add(pn_coso);
		
		String numberOfOffice = Integer.toString(countOffice());

		JLabel lb_coso_count = new JLabel(numberOfOffice);
		lb_coso_count.setForeground(new Color(255, 255, 255));
		lb_coso_count.setFont(new Font("Tahoma", Font.BOLD, 40));
		lb_coso_count.setBounds(10, 22, 38, 39);
		pn_coso.add(lb_coso_count);
		
		JLabel lb_coso = new JLabel("Cơ sở");
		lb_coso.setForeground(Color.WHITE);
		lb_coso.setFont(new Font("Tahoma", Font.BOLD, 20));
		lb_coso.setBounds(10, 65, 123, 39);
		pn_coso.add(lb_coso);
		
		JLabel lb_anh_coso = new JLabel("");
		lb_anh_coso.setIcon(new ImageIcon(Staff_Home.class.getResource("/Design/office.png")));
		lb_anh_coso.setBounds(90, 10, 107, 95);
		pn_coso.add(lb_anh_coso);
		
		JPanel pn_hocvien = new JPanel();
		pn_hocvien.setLayout(null);
		pn_hocvien.setBackground(new Color(0, 255, 64));
		pn_hocvien.setBounds(269, 11, 200, 115);
		pn_qltt.add(pn_hocvien);
		
		JLabel lb_hocvien = new JLabel("Học viên");
		lb_hocvien.setForeground(Color.WHITE);
		lb_hocvien.setFont(new Font("Tahoma", Font.BOLD, 20));
		lb_hocvien.setBounds(10, 65, 123, 39);
		pn_hocvien.add(lb_hocvien);
		
		
		String countStudent = Integer.toString(countStudent());

		JLabel lb_hocvien_count = new JLabel(countStudent);
		lb_hocvien_count.setForeground(new Color(255, 255, 255));
		lb_hocvien_count.setFont(new Font("Tahoma", Font.BOLD, 40));
		lb_hocvien_count.setBounds(10, 22, 38, 39);
		pn_hocvien.add(lb_hocvien_count);
		
		JLabel lb_anh_hocvien = new JLabel("");
		lb_anh_hocvien.setIcon(new ImageIcon(Staff_Home.class.getResource("/Design/person_add.png")));
		lb_anh_hocvien.setBounds(100, 10, 107, 95);
		pn_hocvien.add(lb_anh_hocvien);
		
		JPanel pn_giaovien = new JPanel();
		pn_giaovien.setLayout(null);
		pn_giaovien.setBackground(new Color(255, 128, 0));
		pn_giaovien.setBounds(526, 11, 200, 115);
		pn_qltt.add(pn_giaovien);
		
		JLabel lb_giaovien = new JLabel("Giáo viên");
		lb_giaovien.setForeground(Color.WHITE);
		lb_giaovien.setFont(new Font("Tahoma", Font.BOLD, 20));
		lb_giaovien.setBounds(10, 65, 123, 39);
		pn_giaovien.add(lb_giaovien);
		
		String countStaff = Integer.toString(countStaff());

		JLabel lb_giaovien_count = new JLabel(countStaff);
		lb_giaovien_count.setForeground(Color.WHITE);
		lb_giaovien_count.setFont(new Font("Tahoma", Font.BOLD, 40));
		lb_giaovien_count.setBounds(10, 22, 38, 39);
		pn_giaovien.add(lb_giaovien_count);
		
		JLabel lb_anh_giaovien = new JLabel("");
		lb_anh_giaovien.setIcon(new ImageIcon(Staff_Home.class.getResource("/Design/staff.png")));
		lb_anh_giaovien.setBounds(110, 10, 107, 95);
		pn_giaovien.add(lb_anh_giaovien);
		
		JPanel pn_lopdanghoc = new JPanel();
		pn_lopdanghoc.setLayout(null);
		pn_lopdanghoc.setBackground(Color.RED);
		pn_lopdanghoc.setBounds(784, 11, 200, 115);
		pn_qltt.add(pn_lopdanghoc);
		
		JLabel lb_lopdanghoc = new JLabel("Lớp đang học");
		lb_lopdanghoc.setForeground(Color.WHITE);
		lb_lopdanghoc.setFont(new Font("Tahoma", Font.BOLD, 20));
		lb_lopdanghoc.setBounds(10, 65, 146, 39);
		pn_lopdanghoc.add(lb_lopdanghoc);
		
		JLabel lb_giaovien_count_1 = new JLabel("1");
		lb_giaovien_count_1.setForeground(Color.WHITE);
		lb_giaovien_count_1.setFont(new Font("Tahoma", Font.BOLD, 40));
		lb_giaovien_count_1.setBounds(10, 22, 38, 39);
		pn_lopdanghoc.add(lb_giaovien_count_1);
		
		JLabel lb_anh_giaovien_1 = new JLabel("");
		lb_anh_giaovien_1.setIcon(new ImageIcon(Staff_Home.class.getResource("/Design/class.png")));
		lb_anh_giaovien_1.setBounds(110, 10, 107, 95);
		pn_lopdanghoc.add(lb_anh_giaovien_1);
		
		JLabel lb_qltt = new JLabel("Quản lý trung tâm");
		lb_qltt.setBounds(244, 11, 244, 31);
		lb_qltt.setFont(new Font("Tahoma", Font.BOLD, 25));
		home.add(lb_qltt);
		
		JPanel pn_qltt1 = new JPanel();
		pn_qltt1.setBounds(0, 0, 1280, 650);
		home.add(pn_qltt1);
		pn_qltt1.setLayout(null);
		
		JPanel pn_admin1 = new JPanel();
		pn_admin1.setBackground(new Color(0, 128, 128));
		pn_admin1.setBounds(0, 0, 236, 655);
		pn_qltt1.add(pn_admin1);
		pn_admin1.setLayout(null);
		
		JLabel lblNewLabel_111 = new JLabel("");
		lblNewLabel_111.setBounds(10, 11, 85, 81);
		pn_admin1.add(lblNewLabel_111);
		lblNewLabel_111.setIcon(new ImageIcon(Staff_Home.class.getResource("/Design/avatar.png")));
		
		JPanel pn_anh1 = new JPanel();
		pn_anh1.setBounds(246, 234, 478, 390);
		pn_qltt1.add(pn_anh1);
		pn_anh1.setLayout(null);
		
		JLabel lblNewLabel_10 = new JLabel("");
		lblNewLabel_10.setBounds(10, 0, 468, 390);
		pn_anh1.add(lblNewLabel_10);
		lblNewLabel_10.setIcon(new ImageIcon(Staff_Home.class.getResource("/Design/hoctap1.jpg")));
		
		JPanel pn_anh2 = new JPanel();
		pn_anh2.setLayout(null);
		pn_anh2.setBounds(759, 234, 478, 390);
		pn_qltt1.add(pn_anh2);
		
		JLabel lblNewLabel_10_1 = new JLabel("");
		lblNewLabel_10_1.setBounds(0, 0, 490, 390);
		pn_anh2.add(lblNewLabel_10_1);
		lblNewLabel_10_1.setIcon(new ImageIcon(Staff_Home.class.getResource("/Design/hoctap2.jpg")));
		
		JLabel pn_anhnen = new JLabel("");
		pn_anhnen.setBounds(0, 5, 1270, 650);
		pn_qltt1.add(pn_anhnen);
		pn_anhnen.setIcon(new ImageIcon(Staff_Home.class.getResource("/Design/cool.jpg")));

	
		JPanel account = new JPanel();
		account.setBackground(new Color(255, 255, 255));
		container.add(account, "account");
		account.setLayout(null);
		
		JPanel pn_button = new JPanel();
		pn_button.setBounds(244, 11, 988, 34);
		pn_button.setBackground(Color.WHITE);
		account.add(pn_button);
		pn_button.setLayout(null);
		pn_button.setOpaque(false);
		
		JButton bt_quaylai = new JButton("Quay lại");
		bt_quaylai.setFont(new Font("Tahoma", Font.BOLD, 11));
		bt_quaylai.setBackground(new Color(240, 255, 240));
		bt_quaylai.setBounds(590, 0, 101, 34);
		bt_quaylai.setForeground(new Color(0, 0, 0));
		
		pn_button.add(bt_quaylai);
		
		JButton bt_sua = new JButton("Sửa thông tin");
		bt_sua.setFont(new Font("Tahoma", Font.BOLD, 11));
		bt_sua.setBackground(new Color(240, 255, 240));
		bt_sua.setBounds(710, 0, 150, 34);
		bt_sua.setForeground(new Color(0, 0, 0));
		pn_button.add(bt_sua);
		
		JButton bt_in = new JButton("In");
		bt_in.setFont(new Font("Tahoma", Font.BOLD, 11));
		bt_in.setBackground(new Color(240, 255, 240));
		bt_in.setBounds(880, 0, 89, 34);
		bt_in.setForeground(new Color(0, 0, 0));
		pn_button.add(bt_in);
		
		JPanel pn_ttcn = new JPanel();
		pn_ttcn.setBounds(235, 56, 1035, 650);
		pn_ttcn.setBackground(new Color(255, 255, 255));
		account.add(pn_ttcn);
		
	    
	    URL iconURL_quaylai = Staff_Home.class.getResource("quaylai.png");
	    ImageIcon icon1 = new ImageIcon(iconURL_quaylai);
	    bt_quaylai.setIcon(icon1);
	    
	    URL iconURL_xoa = Staff_Home.class.getResource("xoa.png");
	    ImageIcon icon2 = new ImageIcon(iconURL_xoa);
	    
	    URL iconURL_them = Staff_Home.class.getResource("them.png");
	    ImageIcon icon3 = new ImageIcon(iconURL_them);
	    bt_sua.setIcon(icon3);
	    
	    URL iconURL_luu = Staff_Home.class.getResource("luu.png");
	    ImageIcon icon4 = new ImageIcon(iconURL_luu);
	    bt_in.setIcon(icon4);
		pn_ttcn.setLayout(null);
		
		JLabel lb_ttcn = new JLabel("Thông tin cá nhân");
		lb_ttcn.setBounds(10, 11, 291, 50);
		lb_ttcn.setBackground(new Color(255, 255, 255));
		lb_ttcn.setFont(new Font("Tahoma", Font.PLAIN, 30));
		pn_ttcn.add(lb_ttcn);
		

		URL iconURL_thongtin = Staff_Home.class.getResource("thongtin.icon.png");
	    ImageIcon icon0 = new ImageIcon(iconURL_thongtin);
	    lb_ttcn.setIcon(icon0);
		
		JLabel lb_manhanvien = new JLabel("Mã nhân viên *");
		lb_manhanvien.setBounds(20, 90, 120, 31);
		lb_manhanvien.setFont(new Font("Tahoma", Font.PLAIN, 16));
		pn_ttcn.add(lb_manhanvien);
		
		tf_manhanvien = new JTextField();
		tf_manhanvien.setBounds(155, 92, 210, 30);
		tf_manhanvien.setColumns(10);
		tf_manhanvien.setEditable(false);
		pn_ttcn.add(tf_manhanvien);
		
		tf_hovaten = new JTextField();
		tf_hovaten.setBounds(155, 147, 210, 30);
		tf_hovaten.setColumns(10);
		pn_ttcn.add(tf_hovaten);
		
		tf_ngaysinh = new JDateChooser();
		tf_ngaysinh.setBounds(155, 260, 210, 30);
		tf_ngaysinh.setDateFormatString("yyyy-MM-dd");
		pn_ttcn.add(tf_ngaysinh);
		
		JButton btnGetDate1 = new JButton("Get Selected Date");
        btnGetDate1.addActionListener(e -> {
            // Lấy ngày được chọn và hiển thị trong console
            Date selectedDate1 = tf_ngaysinh.getDate();
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            System.out.println("Selected Date: " + dateFormat.format(selectedDate1));
        });
		
		JLabel lb_hovaten = new JLabel("Họ và tên *");
		lb_hovaten.setBounds(20, 145, 100, 30);
		lb_hovaten.setFont(new Font("Tahoma", Font.PLAIN, 16));
		pn_ttcn.add(lb_hovaten);
		
		JLabel lb_gt = new JLabel("Giới tính");
		lb_gt.setBounds(20, 200, 104, 30);
		lb_gt.setFont(new Font("Tahoma", Font.PLAIN, 16));
		pn_ttcn.add(lb_gt);
		
		String [] gt = {"Nam", "Nữ"};
		cbb_gioitinh = new JComboBox(gt);
		cbb_gioitinh.setBounds(155, 200, 210, 30);
		cbb_gioitinh.setSelectedIndex(-1);
		pn_ttcn.add(cbb_gioitinh);
		
		JLabel lb_sdt = new JLabel("Số điện thoại *");
		lb_sdt.setBounds(461, 90, 125, 31);
		lb_sdt.setFont(new Font("Tahoma", Font.PLAIN, 16));
		pn_ttcn.add(lb_sdt);
		
		tf_sdt = new JTextField();
		tf_sdt.setBounds(618, 91, 210, 30);
		tf_sdt.setColumns(10);
		pn_ttcn.add(tf_sdt);
		
		tf_email = new JTextField();
		tf_email.setBounds(618, 146, 210, 30);
		tf_email.setColumns(10);
		pn_ttcn.add(tf_email);
		
		JLabel lb_ngaysinh = new JLabel("Ngày sinh");
		lb_ngaysinh.setBounds(20, 260, 104, 31);
		lb_ngaysinh.setFont(new Font("Tahoma", Font.PLAIN, 16));
		pn_ttcn.add(lb_ngaysinh);
		
		JLabel lb_email = new JLabel("Email");
		lb_email.setBounds(461, 144, 104, 30);
		lb_email.setFont(new Font("Tahoma", Font.PLAIN, 16));
		pn_ttcn.add(lb_email);
		
		tf_diachi = new JTextField();
		tf_diachi.setBounds(155, 322, 210, 30);
		tf_diachi.setColumns(10);
		pn_ttcn.add(tf_diachi);
		
		tf_vitri = new JComboBox(new String[]{"Giám đốc", "Giáo viên", "Quản lý", "Trợ lý", "Tư vấn viên", "IT Support", "Kế toán"});
		tf_vitri.setBounds(618, 201, 210, 30);
		pn_ttcn.add(tf_vitri);
		
		JLabel lb_diachi = new JLabel("Địa chỉ");
		lb_diachi.setBounds(20, 320, 81, 32);
		lb_diachi.setFont(new Font("Tahoma", Font.PLAIN, 16));
		pn_ttcn.add(lb_diachi);
		
		JLabel lb_vitri = new JLabel("Vị trí");
		lb_vitri.setBounds(461, 199, 125, 30);
		lb_vitri.setFont(new Font("Tahoma", Font.PLAIN, 16));
		pn_ttcn.add(lb_vitri);
		
		JPanel pn_ttcn1 = new JPanel();
		pn_ttcn1.setBounds(1, 0, 298, 67);
		pn_ttcn1.setBackground(new Color(192, 192, 192));
		pn_ttcn.add(pn_ttcn1);
		
		JLabel lb_ttcn1 = new JLabel("");
		lb_ttcn1.setBounds(1, 0, 1053, 602);
		pn_ttcn.add(lb_ttcn1);
		lb_ttcn1.setIcon(new ImageIcon(Staff_Home.class.getResource("/Design/anhnen2.jpg")));
		
		JButton bt_email = new JButton("");
		bt_email.setBackground(new Color(255, 255, 255));
		bt_email.setBounds(1056, 11, 34, 30);
		bt_email.setIcon(new ImageIcon(Staff_Home.class.getResource("/Design/email.png")));
		pn_home.add(bt_email);
		
		JButton bt_thongbao = new JButton("");
		bt_thongbao.setBackground(new Color(255, 255, 255));
		bt_thongbao.setBounds(1106, 11, 34, 30);
		bt_thongbao.setIcon(new ImageIcon(Staff_Home.class.getResource("/Design/thongbao.png")));
		pn_home.add(bt_thongbao);
		
		JButton bt_cake = new JButton("");
		bt_cake.setBackground(new Color(255, 255, 255));
		bt_cake.setBounds(1156, 11, 34, 30);
		bt_cake.setIcon(new ImageIcon(Staff_Home.class.getResource("/Design/cake.png")));
		pn_home.add(bt_cake);
		
		
		JPanel message = new JPanel();
		// account.setBackground(new Color(255, 255, 255));
		container.add(message, "message");
		message.setLayout(null);

		tf_chat = new JTextArea();
		tf_chat.setBackground(new Color(128, 255, 255));
		tf_chat.setBounds(271, 580, 900, 50);
		message.add(tf_chat);

		bt_gui = new JButton("GỬI");
		bt_gui.setBounds(1181, 580, 60, 50);
		message.add(bt_gui);

		JTextArea tf_giaodien = new JTextArea();
		tf_giaodien.setBounds(271, 10, 900, 530);
		JScrollPane thanhcuon = new JScrollPane(tf_giaodien);

		thanhcuon.setBounds(271, 10, 900, 530);
		message.add(thanhcuon);
		
	    bt_in.addActionListener(new ActionListener() {
	        public void actionPerformed(ActionEvent e) {
	            String studentID = tf_manhanvien.getText();
	            String fullName = tf_hovaten.getText();
	            String gender = cbb_gioitinh.getSelectedItem().toString();      
	            String dob = sdf.format(tf_ngaysinh.getDate());
	            String address = tf_diachi.getText();
	            String phoneNumber = tf_sdt.getText();
	            String email = tf_email.getText();
	    
	            String position = (String) tf_vitri.getSelectedItem();
	            WriteTextFile_Staff.writeToFile(studentID, fullName, gender, dob, address, phoneNumber, email, position);
	        }
	    });
		
	    lb_menu.addMouseListener(new MouseAdapter() {
		    @Override
		    public void mouseClicked(MouseEvent e) {
		        int targetWidth = 244; 
		        int step = 10;

		        Timer timer = new Timer(10, new ActionListener() {
		            int menuWidth = 0;

		            @Override
		            public void actionPerformed(ActionEvent e) {
		                menuWidth += step;
		                menu.setSize(menuWidth, height);
		                menu.revalidate(); // Cập nhật lại giao diện

		                if (menuWidth >= targetWidth) {
		                    ((Timer) e.getSource()).stop();
		                }
		            }
		        });

		        timer.start();
		    }
		});
		
		lb_close.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				new Thread(() -> {
					for (int i = width; i>0; i--){
		                menu.setSize(i, height);
		            }
		            try {
						Thread.sleep(30);
					} catch (InterruptedException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
		            
		        }).start();
			}
		});
		
		
		lb_trangchu.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				CardLayout c1 = (CardLayout)(container.getLayout());
				c1.show(container, "home");
				menu.setSize(0,750);
			}
		});
		
		lb_thongtin.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				CardLayout c1 = (CardLayout)(container.getLayout());
				c1.show(container, "account");
				menu.setSize(0,750);
				load_data();
			}
		});
		
		bt_sua.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				Connection con = new Controller.DBController().getConnection();
				String sql = "Update staylearn.staff set name = ?, gender = ?, dateofbirth = ?, address = ?, phonenumber = ?, email = ?, position = ? WHERE staffname = ?";
				try {
		            String formattedDate1 = sdf.format(tf_ngaysinh.getDate());
					PreparedStatement stm = con.prepareStatement(sql);
					stm.setString(1, tf_hovaten.getText());
					stm.setString(2, cbb_gioitinh.getSelectedItem()+"");
					stm.setString(3, formattedDate1);
					stm.setString(4, tf_diachi.getText());
					stm.setString(5, tf_sdt.getText());
					stm.setString(6, tf_email.getText());
					stm.setString(7, (String) tf_vitri.getSelectedItem());
					stm.setString(8, staffname);
					stm.execute();
					
					stm.execute();
					JOptionPane.showMessageDialog(null, "Cập nhập thành công");
					
				} catch (Exception e2) {
					// TODO: handle exception
					JOptionPane.showMessageDialog(null, "Cập nhập không thành công");
					e2.printStackTrace();
				}
			}
		});
		
		SwingWorker<Void, String> worker = new SwingWorker<Void, String>() {
			@Override
			protected Void doInBackground() throws Exception {
				try {
					System.out.println("Kết nối đến server...");
					String sang = Form_Login.staffName;
					System.out.println(sang);

					socket = SharedSocketService.getSocket();
					
					System.out.println("Đã kết nối xong");

					bt_gui.addActionListener(new ActionListener() {
						@Override
						public void actionPerformed(ActionEvent e) {
							String message = tf_chat.getText();
							try {
								
								if(message != null && !message.isEmpty()) {
								OutputStream output = socket.getOutputStream();
								PrintWriter writer = new PrintWriter(output, true);
								writer.println(staffname+" :"+message);
								tf_chat.setText("");
								System.out.println("Đã gửi tin nhắn: " + message);}
							} catch (IOException ex) {
								ex.printStackTrace();
							}
						}
					});

					 BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			            String line;
			            while ((line = reader.readLine()) != null) {
			                final String message1 = line;
			                
			                SwingUtilities.invokeLater(() -> tf_giaodien.append(message1 + "\n"));

			                // Kiểm tra xem kết nối đã đóng chưa
			                if (socket.isClosed()) {
			                    break; // Thoát khỏi vòng lặp nếu kết nối đã đóng
			                }
			            }

				} catch (Exception e1) {
					e1.printStackTrace();
				}

				return null;
			}
		};

		lb_message.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				CardLayout c1 = (CardLayout) (container.getLayout());
				c1.show(container, "message");
				menu.setSize(0, 750);
				worker.execute(); // Khởi chạy SwingWorker ở đây
			}
		});
		
		setTitle("STAYLEARN");
		setSize(1280,750);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setVisible(true);
		setLocationRelativeTo(null);
		setResizable(false);
	}

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Staff_Home frame = new Staff_Home();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
}