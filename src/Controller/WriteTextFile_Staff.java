package Controller;

import java.io.IOException;
import java.io.File;
import java.io.FileWriter;
import Design.Form_Login;

public class WriteTextFile_Staff {
    public static void writeToFile(String studentID, String fullName, String gender, String dob, String address,
                                    String phoneNumber, String email, String position) {
        try {
        	String staffname = Form_Login.staffName;
            File file = new File("thongtin_staff_" + staffname +".txt");
            FileWriter writer = new FileWriter(file);

            writer.write("Mã nhân viên: " + studentID + "\n");
            writer.write("Họ và tên: " + fullName + "\n");
            writer.write("Giới tính: " + gender + "\n");
            writer.write("Ngày sinh: " + dob + "\n");
            writer.write("Địa chỉ: " + address + "\n");
            writer.write("Số điện thoại: " + phoneNumber + "\n");
            writer.write("Email: " + email + "\n");
            writer.write("Vị trí: " + position + "\n");

            writer.close();
            System.out.println("Thông tin đã được lưu vào tệp thongtin_staff_" + staffname + ".txt");
        } catch (IOException e) {
            System.out.println("Đã xảy ra lỗi khi ghi vào tệp.");
            e.printStackTrace();
        }
    }
}