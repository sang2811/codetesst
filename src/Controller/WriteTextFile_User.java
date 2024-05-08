package Controller;

import java.io.IOException;
import java.io.File;
import java.io.FileWriter;
import Design.Form_Login;

public class WriteTextFile_User {
    public static void writeToFile(String studentID, String fullName, String gender, String dob, String phoneNumber,
                                    String email, String address, String parentName, String parentPhoneNumber, String joinDate) {
        try {
        	String username = Form_Login.userName;
            File file = new File("thongtinuser_" + username +".txt");
            FileWriter writer = new FileWriter(file);

            writer.write("Mã học viên: " + studentID + "\n");
            writer.write("Họ và tên: " + fullName + "\n");
            writer.write("Giới tính: " + gender + "\n");
            writer.write("Ngày sinh: " + dob + "\n");
            writer.write("Số điện thoại: " + phoneNumber + "\n");
            writer.write("Email: " + email + "\n");
            writer.write("Địa chỉ: " + address + "\n");
            writer.write("Tên phụ huynh: " + parentName + "\n");
            writer.write("Số điện thoại phụ huynh: " + parentPhoneNumber + "\n");
            writer.write("Ngày đến trung tâm: " + joinDate + "\n");

            writer.close();
            System.out.println("Thông tin đã được lưu vào tệp thongtinuser_" + username +".txt");
        } catch (IOException e) {
            System.out.println("Đã xảy ra lỗi khi ghi vào tệp.");
            e.printStackTrace();
        }
    }
}