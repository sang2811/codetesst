package Controller;

import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.mindrot.jbcrypt.BCrypt;

public class DBController {
	public Connection getConnection() {
		String url = "jdbc:mysql://localhost:3306/staylearn";
		String user="root";
		String pass="sang1234";
		try {
			return DriverManager.getConnection(url,user,pass);
		} catch (Exception e) {
			// TODO: handle exception
			throw new RuntimeException("Lỗi khi kết nối với database");
		}
	}
	
	public boolean addUser(String username, String password) {
	    String hashed = BCrypt.hashpw(password, BCrypt.gensalt(12));
	    // Giả sử chỉ có hai cột là username và password
	    String sql = "INSERT INTO user_account(username, passworduser) VALUES(?, ?)"; // Không cần định rõ 'encode.' nếu đã sử dụng database đó
	    try (Connection conn = this.getConnection();
	         PreparedStatement pstmt = conn.prepareStatement(sql)) {
	        pstmt.setString(1, username);
	        pstmt.setString(2, hashed);
	        pstmt.executeUpdate();
	        return true;
	    } catch (SQLException e) {
	        System.out.println("Không thể thêm người dùng: " + e.getMessage()); // Chi tiết hóa thông báo lỗi
	        return false;
	    }
	}

    public boolean checkUser(String username, String password) {
        String sql = "SELECT passworduser FROM staylearn.user_account WHERE username = ?";
        try (Connection conn = this.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, username);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                String storedHash = rs.getString(1);
                return BCrypt.checkpw(password, storedHash);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return false;
    }
    
	public static void main(String[] args) {
		Connection conn = null;
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
            DBController controller = new DBController();
            conn = controller.getConnection();
            System.out.println("Kết nối thành công");
			      
		} catch(Exception e){
			e.printStackTrace();
		}
		
	}
	
}
