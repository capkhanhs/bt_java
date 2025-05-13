package SQL;

import java.awt.Taskbar.State;
import java.security.AlgorithmParametersSpi;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import javax.print.attribute.standard.NumberOfInterveningJobs;
import javax.swing.table.DefaultTableModel;

import Date.KiemTraNgayThang;

public class DBHelper {
	private static final String url = "jdbc:mysql://127.0.0.1:3306/DoAn_Java";
	private static final String username = "root";
	private static final String password = "khanh12345";
	
	//Kết nối SQL
	public static Connection getConnection() throws SQLException {
		return DriverManager.getConnection(url, username, password);
	}
	
	//Insert HV vào DB
	public static void insert_sql(String hoTen, boolean gioiTinh, String sdt, String ngayDK, String MaGoi, String ngayHH ) {
		String sql = "INSERT INTO HOIVIEN (MaTV, HoTen, GioiTinh, SoDT, NgayDK, NgayHH, TrangThai, MaGoi) VALUES(?, ?, ?, ?, ?, ?, ?, ?)";
		String maHV = set_MaHV();
		String trangThai = "Hoạt Động";
		
		try(Connection cnt = getConnection();
				PreparedStatement pstm = cnt.prepareStatement(sql))
		{
			pstm.setString(1, maHV);
			pstm.setString(2, hoTen);
			pstm.setBoolean(3, gioiTinh);
			pstm.setString(4, sdt);
			pstm.setString(5, ngayDK);
			pstm.setString(6, ngayHH);
			pstm.setString(7, trangThai);
			pstm.setString(8, MaGoi);
			
			pstm.executeUpdate();
			System.out.println("Da them HV co ma: " + maHV);
		}
		catch(SQLException e)
		{
			System.out.println("Them HV khong thanh cong!\n" + e.getMessage());
		}
	}
	
	//Lấy mã gói dựa vào giá trị String của CBB
	public static String get_maGoi(String TenGoi) {
		String maGoi = null;
		String sql = "SELECT MaGoi FROM GoiTap WHERE GoiTap.TenGoi = ?";
		try(Connection cnt = getConnection();
				PreparedStatement pstm = cnt.prepareStatement(sql))
		{
			pstm.setString(1, TenGoi);
			ResultSet rs = pstm.executeQuery();
			if(rs.next()) {
				maGoi = rs.getString("MaGoi");
			}
		}
		catch(SQLException e)
		{
			e.getMessage();
		}
		return maGoi;
	}
	
	//Lấy Tên gói tập add vào cbb
	public static String[] get_TenGoiTap(){
		List<String> tenGoi = new ArrayList<String>();
		
		String sql = "SELECT * FROM GoiTap ORDER BY ThoiHan ASC";
		
		try 
		{
			Connection cnt = getConnection();
			
			Statement stt = cnt.createStatement();
			ResultSet rs = stt.executeQuery(sql);
			
			
			while(rs.next())
			{
				tenGoi.add(rs.getString("TenGoi"));
			}
		}
		catch(SQLException e)
		{
			System.out.println("Loi load ccb!");
		}
		
		return tenGoi.toArray(new String[0]);
	}
	
	//tự động tăng mã HV
	public static String set_MaHV() {
		String MaHV = null;
		
		String sql = "SELECT MaTV FROM HoiVien ORDER BY MaTV DESC LIMIT 1";
		
		try(Connection cnt = getConnection();
				Statement stm = cnt.createStatement();
					ResultSet rs = stm.executeQuery(sql))
		{
			if(rs.next())
			{
				String maTV = rs.getString("MaTV");
				
				String number = maTV.replaceAll("\\D+", ""); //Lấy phần số (replace D là xóa những chữ không phải số D là Digit)
				int nb = Integer.parseInt(number) + 1;
				String new_nb = String.format("%0" + number.length() + "d", nb); //đảm bảo có 3 chữ số và các chữ số đầu tiên nếu chưa có sẽ là 0
				MaHV = "HV" + new_nb;
			}
			else
			{
				//Chưa có records
				MaHV = "HV001";
			}
		}
		catch (SQLException e) {
			// TODO: handle exception
			System.out.println(e.getMessage());
		}
		
		return MaHV;
	}
	
	//Lấy giá tiền theo từng gói
	public static String get_giaTien(String MaGoi) {
		String sql = "SELECT GiaTien FROM GoiTap WHERE GoiTap.MaGoi = ?";
		String giaTien = null;
		
		try(Connection cnt = getConnection();
				PreparedStatement pstm = cnt.prepareStatement(sql))
		{
			pstm.setString(1, MaGoi);
			
			ResultSet rs = pstm.executeQuery();
			if(rs.next()) {
				giaTien = format_GiaTien(rs.getInt("GiaTien"));
			}
		}
		catch(SQLException e)
		{
			System.out.println("Loi lay gia tien!\n" + e.getMessage());
		}
		return giaTien;
	}
	
	//Hàm định dạng giá tiền
	public static String format_GiaTien(int giatien)
	{
		Long gt = Long.valueOf(giatien) * 1000;
		DecimalFormat df = new DecimalFormat("#,###"); //Định dạng số ngăn cách hàng nghìn, là dấu phẩy, dấu chấm là hàng thập phân
		String giaTien = df.format(gt).replace(",", ".") + " VNĐ"; //đổi dấu , thành dấu .
		return giaTien;
	}
	
	//Hàm trả về DefaultTableModel để hiển thị trên JTable
	public static DefaultTableModel get_danhsachHV() {
		String[] tenCot = {"Mã TV", "Họ tên", "Giới tính", "Số ĐT", "Gói tập", "Ngày ĐK", "Ngày HH", "Trạng Thái"};
		
		String sql = "SELECT HoiVien.MaTV, HoiVien.HoTen, HoiVien.GioiTinh, HoiVien.SoDT, GoiTap.TenGoi, HoiVien.NgayDK, HoiVien.NgayHH, HoiVien.TrangThai "
				+ " FROM HoiVien INNER JOIN GoiTap ON HoiVien.MaGoi = GoiTap.MaGoi ORDER BY MaTV ASC";
		
		try (Connection cnt = getConnection())
		{
			Statement stm = cnt.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY); //Chỉnh dòng này để cho phép RS di chuyển tới bất kì dòng nào
			ResultSet rs = stm.executeQuery(sql);
			ResultSetMetaData rsdt = rs.getMetaData();
			int soCot = rsdt.getColumnCount();
			
			rs.last(); //đưa con trỏ rs về cuối dòng
			int soDong = rs.getRow(); //Lấy số thứ tự của dòng cuối -> biết có mấy dòng
			rs.beforeFirst(); //trả con trỏ về dòng đầu tiên
			
			//Lấy dữ liệu từ RS
			Object[][] data = new Object[soDong][soCot];
			int indexDong = 0;
			while(rs.next()) 
			{
				data[indexDong][0] = rs.getString("MaTV");
				data[indexDong][1] = rs.getString("HoTen");
				data[indexDong][2] = rs.getBoolean("GioiTinh") ? "Nam" : "Nữ";
				data[indexDong][3] = rs.getString("SoDT");
				data[indexDong][4] = rs.getString("TenGoi");

				Date ngayDK = rs.getDate("NgayDK");
				Date ngayHH = rs.getDate("NgayHH");

				//Đổi về LocalDate để sử dụng hàm khởi tạo trong KiemTraNgayThang
				LocalDate localNgayDK = ngayDK.toLocalDate();
				LocalDate localNgayHH = ngayHH.toLocalDate();
				
				
				KiemTraNgayThang dt_dk = new KiemTraNgayThang(localNgayDK);
				KiemTraNgayThang dt_hh = new KiemTraNgayThang(localNgayHH);
				
				data[indexDong][5] = dt_dk.toString();
				data[indexDong][6] = dt_hh.toString();
				//endtest

				data[indexDong][7] = rs.getString("TrangThai");

				
				indexDong ++;
			}
			
			//Trả về 1 DefaultTableModel để hiển thị
			return new DefaultTableModel(data, tenCot)
			{
				//Hàm không cho người dùng thay đổi trên table
				@Override
	            public boolean isCellEditable(int row, int column) {
	                return false;
	            }
				
				//Set kiểu dữ liệu mỗi cột
	            @Override
	            public Class<?> getColumnClass(int columnIndex) {
	                return String.class;
	            }
			};
		}
		catch (SQLException e) {
			// TODO: handle exception
			System.out.println("Loi lay du lieu HoiVien!\n" + e.getMessage() );
		}
		return null;
	}
	
	//Hàm tìm record dựa vào HoTen và maGoi
	public static DefaultTableModel get_record_bySearch(String hoTen, String maGoi)
	{
		String sql;
		String[] tenCot = {"Mã TV", "Họ tên", "Giới tính", "Số ĐT", "Gói tập", "Ngày ĐK", "Ngày HH", "Trạng Thái"};
	    
	    // Chuẩn bị câu truy vấn SQL dựa trên điều kiện
	    if (!maGoi.equals("All")) {
	        String maGoiValue = get_maGoi(maGoi);
	        sql = "SELECT HoiVien.MaTV, HoiVien.HoTen, HoiVien.GioiTinh, HoiVien.SoDT, GoiTap.TenGoi, " +
	              "HoiVien.NgayDK, HoiVien.NgayHH, HoiVien.TrangThai " +
	              "FROM HoiVien INNER JOIN GoiTap ON HoiVien.MaGoi = GoiTap.MaGoi " +
	              "WHERE HoiVien.HoTen LIKE ? AND HoiVien.MaGoi = ? ORDER BY MaTV ASC";
	    } else {
	        sql = "SELECT HoiVien.MaTV, HoiVien.HoTen, HoiVien.GioiTinh, HoiVien.SoDT, GoiTap.TenGoi, " +
	              "HoiVien.NgayDK, HoiVien.NgayHH, HoiVien.TrangThai " +
	              "FROM HoiVien INNER JOIN GoiTap ON HoiVien.MaGoi = GoiTap.MaGoi " +
	              "WHERE HoiVien.HoTen LIKE ? ORDER BY MaTV ASC";
	    }
	    
		try(Connection cnt = getConnection();
				PreparedStatement pstm = cnt.prepareStatement(sql, 
			            ResultSet.TYPE_SCROLL_INSENSITIVE, 
			            ResultSet.CONCUR_READ_ONLY);
				)
		{
			pstm.setString(1, "%" + hoTen + "%"); // Sửa cách sử dụng wildcard trong LIKE
	        
	        if (!"All".equals(maGoi)) {
	            pstm.setString(2, get_maGoi(maGoi));
	        }
	        
			ResultSet rs = pstm.executeQuery();
			ResultSetMetaData rsdt = rs.getMetaData();
			int soCot = rsdt.getColumnCount();
			
			rs.last(); //đưa con trỏ rs về cuối dòng
			int soDong = rs.getRow(); //Lấy số thứ tự của dòng cuối -> biết có mấy dòng
			rs.beforeFirst(); //trả con trỏ về dòng đầu tiên
			
			//Lấy dữ liệu từ RS
			Object[][] data = new Object[soDong][soCot];
			int indexDong = 0;
			while(rs.next()) 
			{
				data[indexDong][0] = rs.getString("MaTV");
				data[indexDong][1] = rs.getString("HoTen");
				data[indexDong][2] = rs.getBoolean("GioiTinh") ? "Nam" : "Nữ";
				data[indexDong][3] = rs.getString("SoDT");
				data[indexDong][4] = rs.getString("TenGoi");

				Date ngayDK = rs.getDate("NgayDK");
				Date ngayHH = rs.getDate("NgayHH");
				
				LocalDate localNgayDK = ngayDK.toLocalDate();
				LocalDate localNgayHH = ngayHH.toLocalDate();
				
				
				KiemTraNgayThang dt_dk = new KiemTraNgayThang(localNgayDK);
				KiemTraNgayThang dt_hh = new KiemTraNgayThang(localNgayHH);
				 
				data[indexDong][5] = dt_dk.toString();
				data[indexDong][6] = dt_hh.toString();

				data[indexDong][7] = rs.getString("TrangThai");

				
				indexDong ++;
			}
			
			//Trả về 1 DefaultTableModel để hiển thị
			return new DefaultTableModel(data, tenCot)
			{
				//Hàm không cho người dùng thay đổi trên table
				@Override
	            public boolean isCellEditable(int row, int column) {
	                return false;
	            }
				
				//Set kiểu dữ liệu mỗi cột
	            @Override
	            public Class<?> getColumnClass(int columnIndex) {
	                return String.class;
	            }
			};
		}
		catch(SQLException e)
		{
			System.out.println("Loi lay record tim kiem!\n" + e.getMessage());
		}
		return new DefaultTableModel(new Object[0][], tenCot);
	}
	
	//Hàm dùng để update Records
	public static void update_info(String maHV, String hoTen, Boolean gioiTinh, String sdt, String ngayDK, String ngayHH, String maGoi)
	{
		String TrangThai = "Hoạt Động";
		String sql = "UPDATE HoiVien SET HoTen = ?, GioiTinh = ?, SoDT = ?, NgayDK = ?, NgayHH = ?, TrangThai = ?, MaGoi = ? WHERE MaTV = ?";
		try(Connection cnt = getConnection();
				PreparedStatement pstm = cnt.prepareStatement(sql);
				)
		{
			pstm.setString(1, hoTen);
			pstm.setBoolean(2, gioiTinh);
			pstm.setString(3, sdt);
			pstm.setString(4, ngayDK);
			pstm.setString(5, ngayHH);
			pstm.setString(6, TrangThai);
			pstm.setString(7, maGoi);
			pstm.setString(8, maHV);
			
			int soCot_AnhHuong = pstm.executeUpdate();
			if (soCot_AnhHuong > 0) {
			    System.out.println("Da update record " + maHV + "!");
			} else {
			    System.out.println("Khong tim thay ban ghi voi MaTV = " + maHV);
			}
		}
		catch(SQLException e)
		{
			System.out.println("Loi Update Records\n" + e.getMessage());
		}
	}
	
	//Hàm để check lại info và cập nhật trạng thái
	public static void update_trangThai_allRecords() {
		KiemTraNgayThang dt_now = new KiemTraNgayThang(LocalDate.now());
		String date_now = dt_now.toString();
		
		String sql = "SELECT * FROM HOIVIEN WHERE HOIVIEN.NgayHH < ?";
		try(Connection cnt = getConnection();
				PreparedStatement pstm = cnt.prepareStatement(sql))
		{
			pstm.setString(1, date_now);
			
			ResultSet rs = pstm.executeQuery();
			
			//Lấy mã các TV có ngày đã hết hạn
			List<String> data = new ArrayList<String>();
			while(rs.next())
			{
				data.add(rs.getString("MaTV"));
			}
			
			for(String maTV : data)
			{
				update_tt_record(maTV);
			}
		}
		catch(SQLException e)
		{
			System.out.println("Loi update trang thai tat ca Records!\n" + e.getMessage());
		}
	}
	
	//Hàm để update Trạng Thái hội viên -> được sử dụng trong hàm ngay ở trên
	public static void update_tt_record(String MaTV)
	{
		String sql = "UPDATE HoiVien SET TrangThai = 'Tạm Ngưng' WHERE MaTV = ?";
		try(Connection cnt = getConnection();
				PreparedStatement pstm = cnt.prepareStatement(sql);)
		{
			pstm.setString(1, MaTV);
			
			pstm.executeUpdate();
		}
		catch (SQLException e) {
			// TODO: handle exception
			System.out.println("Loi update trang thai 1 records co maTV: " + MaTV + "\n" + e.getMessage());
		}
	}
}
