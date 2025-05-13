package View;

import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.time.LocalDate;
import java.util.Properties;

import javax.swing.JRadioButton;
import javax.swing.JCheckBox;
import javax.swing.JRadioButtonMenuItem;

import com.mysql.cj.util.Util;

import Date.KiemTraNgayThang;
import SQL.DBHelper;

import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.plaf.basic.BasicBorders.RadioButtonBorder;
import javax.swing.ButtonGroup;
import javax.swing.DefaultComboBoxModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;

public class ThemTV extends JPanel {

	private static final long serialVersionUID = 1L;
	private JTextField txt_hoten;
	private JTextField txt_ngayDK;
	private JTextField txt_thangDK;
	private JTextField txt_namDK;
	private JTextField txt_sdt;
	private JTextField txt_ngayHH;
	private JTextField txt_thangHH;
	private JTextField txt_namHH;
	private JTextField txt_giatien;
	private JRadioButton rdb_nam;
	private JRadioButton rdb_nu;
	private JComboBox cbb_goi;
	private ButtonGroup rd_btn_gr;
	
	private LocalDate dt_now;
	private KiemTraNgayThang dt_dk;
	private KiemTraNgayThang dt_hh;
	
	private JLabel lb_name;
	
	private JButton btn_huy;
	private JButton btn_xacnhan;

	/**
	 * Create the panel.
	 */
	public ThemTV() {
		setLayout(null); 
		
		ImageIcon originalIcon = new ImageIcon("src/resource/bg_gym.jpg");
		Image scaledImage = originalIcon.getImage().getScaledInstance(529, 805, Image.SCALE_SMOOTH);
		//Sau khi tạo image đã fix kích thước thì thêm vào JLabel để hiển thị
		ImageIcon backgroundIcon = new ImageIcon(scaledImage);
		JLabel lb_bgGym = new JLabel(backgroundIcon);
		lb_bgGym.setBounds(932, 0, 529, 694);
		add(lb_bgGym);
		
		lb_name = new JLabel("Thêm Hội Viên Mới");
		lb_name.setFont(new Font("Tahoma", Font.PLAIN, 50));
		lb_name.setBounds(333, 11, 589, 87);
		add(lb_name);
		
		JLabel lb_hoten = new JLabel("Họ Tên: ");
		lb_hoten.setFont(new Font("Tahoma", Font.PLAIN, 25));
		lb_hoten.setBounds(63, 142, 103, 50);
		add(lb_hoten);
		
		rdb_nam = new JRadioButton("NAM");
		rdb_nam.setFont(new Font("Tahoma", Font.PLAIN, 25));
		rdb_nam.setBounds(209, 203, 88, 43);
		add(rdb_nam);
		
		JLabel lb_gioitinh = new JLabel("Giới Tính:");
		lb_gioitinh.setFont(new Font("Tahoma", Font.PLAIN, 25));
		lb_gioitinh.setBounds(63, 203, 124, 50);
		add(lb_gioitinh);
		
		rdb_nu = new JRadioButton("NỮ");
		rdb_nu.setFont(new Font("Tahoma", Font.PLAIN, 25));
		rdb_nu.setBounds(355, 203, 88, 43);
		add(rdb_nu);
		
		rd_btn_gr = new ButtonGroup();
		rd_btn_gr.add(rdb_nam);
		rd_btn_gr.add(rdb_nu);
		
		JLabel lb_sdt = new JLabel("Số Điện Thoại: ");
		lb_sdt.setFont(new Font("Tahoma", Font.PLAIN, 25));
		lb_sdt.setBounds(63, 268, 174, 50);
		add(lb_sdt);
		
		JLabel lb_DK = new JLabel("Đăng Kí:");
		lb_DK.setFont(new Font("Tahoma", Font.PLAIN, 25));
		lb_DK.setBounds(63, 340, 103, 50);
		add(lb_DK);
		
		JLabel lb_ngaydk = new JLabel("Ngày: ");
		lb_ngaydk.setFont(new Font("Tahoma", Font.PLAIN, 25));
		lb_ngaydk.setBounds(217, 340, 80, 50);
		add(lb_ngaydk);
		
		JLabel lb_thangdk = new JLabel("Tháng:");
		lb_thangdk.setFont(new Font("Tahoma", Font.PLAIN, 25));
		lb_thangdk.setBounds(464, 340, 80, 50);
		add(lb_thangdk);
		
		JLabel lb_namdk = new JLabel("Năm:");
		lb_namdk.setFont(new Font("Tahoma", Font.PLAIN, 25));
		lb_namdk.setBounds(723, 340, 69, 50);
		add(lb_namdk);
		
		txt_hoten = new JTextField();
		txt_hoten.setFont(new Font("Tahoma", Font.PLAIN, 25));
		txt_hoten.setBounds(176, 142, 500, 40);
		add(txt_hoten);
		txt_hoten.setColumns(10);
		
		txt_ngayDK = new JTextField();
		txt_ngayDK.setEnabled(false);
		txt_ngayDK.setFont(new Font("Tahoma", Font.PLAIN, 25));
		txt_ngayDK.setColumns(10);
		txt_ngayDK.setBounds(296, 340, 100, 40);
		add(txt_ngayDK);
		
		txt_thangDK = new JTextField();
		txt_thangDK.setEnabled(false);
		txt_thangDK.setFont(new Font("Tahoma", Font.PLAIN, 25));
		txt_thangDK.setColumns(10);
		txt_thangDK.setBounds(557, 340, 100, 40);
		add(txt_thangDK);
		
		txt_namDK = new JTextField();
		txt_namDK.setEnabled(false);
		txt_namDK.setFont(new Font("Tahoma", Font.PLAIN, 25));
		txt_namDK.setColumns(10);
		txt_namDK.setBounds(802, 340, 100, 40);
		add(txt_namDK);
		
		JLabel lb_goi = new JLabel("Gói Tập: ");
		lb_goi.setFont(new Font("Tahoma", Font.PLAIN, 25));
		lb_goi.setBounds(63, 417, 124, 50);
		add(lb_goi);
		
		cbb_goi = new JComboBox();
		cbb_goi.setFont(new Font("Tahoma", Font.PLAIN, 25));
		
		//Thêm thông tin cho cbb
		for(String tenGoi : DBHelper.get_TenGoiTap()) {
			cbb_goi.addItem(tenGoi);
		}
		
		cbb_goi.setSelectedIndex(0);
		cbb_goi.setBounds(209, 424, 200, 40);
		add(cbb_goi);
		cbb_goi.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				String luaChon = String.valueOf(cbb_goi.getSelectedItem());
				
				ThemTV.this.set_infor_ngayHH(ThemTV.this.soThang_cbb(cbb_goi.getSelectedIndex()));
				
				
				txt_giatien.setText(DBHelper.get_giaTien(DBHelper.get_maGoi(String.valueOf(cbb_goi.getSelectedItem()))));
				
				System.out.println("Da thay doi CBB: " + luaChon + cbb_goi.getSelectedIndex()); 
			}
		});
		
		txt_sdt = new JTextField();
		txt_sdt.setFont(new Font("Tahoma", Font.PLAIN, 25));
		txt_sdt.setColumns(10);
		txt_sdt.setBounds(247, 268, 429, 40);
		add(txt_sdt);
		
		JLabel lb_HH = new JLabel("Ngày Hết Hạn: ");
		lb_HH.setFont(new Font("Tahoma", Font.PLAIN, 25));
		lb_HH.setBounds(63, 489, 174, 50);
		add(lb_HH);
		
		JLabel lb_ngayhh = new JLabel("Ngày: ");
		lb_ngayhh.setFont(new Font("Tahoma", Font.PLAIN, 25));
		lb_ngayhh.setBounds(257, 489, 80, 50);
		add(lb_ngayhh);
		
		txt_ngayHH = new JTextField();
		txt_ngayHH.setEnabled(false);
		txt_ngayHH.setFont(new Font("Tahoma", Font.PLAIN, 25));
		txt_ngayHH.setColumns(10);
		txt_ngayHH.setBounds(333, 495, 100, 40);
		add(txt_ngayHH);
		
		JLabel lb_thanghh = new JLabel("Tháng:");
		lb_thanghh.setFont(new Font("Tahoma", Font.PLAIN, 25));
		lb_thanghh.setBounds(492, 489, 80, 50);
		add(lb_thanghh);
		
		txt_thangHH = new JTextField();
		txt_thangHH.setEnabled(false);
		txt_thangHH.setFont(new Font("Tahoma", Font.PLAIN, 25));
		txt_thangHH.setColumns(10);
		txt_thangHH.setBounds(582, 495, 100, 40);
		add(txt_thangHH);
		
		JLabel lb_namhh = new JLabel("Năm:");
		lb_namhh.setFont(new Font("Tahoma", Font.PLAIN, 25));
		lb_namhh.setBounds(749, 489, 69, 50);
		add(lb_namhh);
		
		txt_namHH = new JTextField();
		txt_namHH.setEnabled(false);
		txt_namHH.setFont(new Font("Tahoma", Font.PLAIN, 25));
		txt_namHH.setColumns(10);
		txt_namHH.setBounds(822, 489, 100, 40);
		add(txt_namHH);
		
		JLabel lb_giatien = new JLabel("Đóng Tiền: ");
		lb_giatien.setFont(new Font("Tahoma", Font.PLAIN, 25));
		lb_giatien.setBounds(63, 567, 142, 50);
		add(lb_giatien);
		
		txt_giatien = new JTextField();
		txt_giatien.setEnabled(false);
		txt_giatien.setFont(new Font("Tahoma", Font.PLAIN, 25));
		txt_giatien.setColumns(10);
		txt_giatien.setBounds(209, 567, 224, 40);
		add(txt_giatien);
		//Thêm giá tiền
		txt_giatien.setText(DBHelper.get_giaTien(DBHelper.get_maGoi(String.valueOf(cbb_goi.getSelectedItem()))));
		
		btn_xacnhan = new JButton("Xác Nhận");
		btn_xacnhan.setFont(new Font("Tahoma", Font.PLAIN, 25));
		btn_xacnhan.setBounds(431, 702, 150, 50);
		add(btn_xacnhan);
		btn_xacnhan.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				boolean check_loi = false;
				
				String loi = "";
				String hoTen = txt_hoten.getText().trim();
				String sdt = txt_sdt.getText().trim();
				if(!sdt.matches("\\d{10}")) {
					loi += "Lỗi định dạng số điện thoại!\n";
					check_loi = true;
				}
				
				boolean gioiTinh = true; // Nam là true, nữ false
				if(rd_btn_gr.getSelection() == null) {
					loi += "Chưa chọn giới tính!\n";
					check_loi = true;
				}
				else
				{
					if(rdb_nam.isSelected()) {
						gioiTinh = true;
					}
					else 
					{
						gioiTinh = false;
					}
				}
				String ngayDK = dt_dk.toString();
				String maGoi = DBHelper.get_maGoi(String.valueOf(cbb_goi.getSelectedItem()));
				String ngayHH = dt_hh.toString();
				
				if(check_loi)
				{
					JOptionPane.showMessageDialog(
						    null,
						    loi,
						    "Thất Bại!",
						    JOptionPane.ERROR_MESSAGE
						);

					if(!sdt.matches("\\d{10}"))
					{
						txt_sdt.setText("");
					}
				}
				else
				{
					DBHelper.insert_sql(hoTen, gioiTinh, sdt, ngayDK, maGoi, ngayHH);
					ThemTV.this.resetForm();
					JOptionPane.showMessageDialog(
							null,
							"Thêm Hội Viên Thành Công!",
							"Thành Công!",
							JOptionPane.INFORMATION_MESSAGE
							);
				}
			}
		});
		
		btn_huy = new JButton("Hủy");
		btn_huy.setFont(new Font("Tahoma", Font.PLAIN, 25));
		btn_huy.setBounds(883, 702, 150, 50);
		add(btn_huy);
		btn_huy.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				int choice = JOptionPane.showConfirmDialog(
					    null,
					    "Bạn có chắc chắn muốn thoát không?\nChương trình sẽ xóa toàn bộ dữ liệu bạn vừa nhập khi xác nhận xóa!",
					    "Xác nhận thoát?",
					    JOptionPane.YES_NO_OPTION,
					    JOptionPane.WARNING_MESSAGE
					);

				if (choice == JOptionPane.YES_OPTION) {
					ThemTV.this.resetForm();
					
				    System.out.println("Người dùng chọn: YES");
				} else if (choice == JOptionPane.NO_OPTION) {
				    System.out.println("Người dùng chọn: NO");
				}
			}
		});
		
		
		this.set_info_ngayDK();
		this.set_infor_ngayHH(this.soThang_cbb(cbb_goi.getSelectedIndex()));
	}
	
	//hàm set ngày đăng kí
	private void set_info_ngayDK() {
		dt_now = LocalDate.now();
		dt_dk = new KiemTraNgayThang(dt_now);
		 
		txt_ngayDK.setText(String.valueOf(dt_dk.layNgay()));
		txt_thangDK.setText(String.valueOf(dt_dk.layThang()));
		txt_namDK.setText(String.valueOf(dt_dk.layNam()));
	}
	
	//hàm set ngày hết hạn
	private void set_infor_ngayHH(int index) {
		dt_now = LocalDate.now();
		dt_hh = new KiemTraNgayThang(dt_now);
		
		dt_hh.congThang(index);
		
		txt_ngayHH.setText(String.valueOf(dt_hh.layNgay()));
		txt_thangHH.setText(String.valueOf(dt_hh.layThang()));
		txt_namHH.setText(String.valueOf(dt_hh.layNam()));
	}
	
	//Hàm để lấy số tháng tương ứng với cbb
	private int soThang_cbb(int index) {
		if(index == 1) return 3;
		else if(index == 2) return 6;
		else if(index == 3) return 12;
		
		return 1;
	}
	
	//Hàm dùng để reset lại form Thêm TV
	private void resetForm() {
	    txt_hoten.setText("");
	    rd_btn_gr.clearSelection();
	    txt_sdt.setText("");
	    set_info_ngayDK();
	    cbb_goi.setSelectedIndex(-1);
	    set_infor_ngayHH(soThang_cbb(-1));
	    txt_giatien.setText("");
	}
	
	//Khởi tạo để Gia Hạn Hội Viên
	public ThemTV(String maHV, String hoTen, String gioiTinh, String sdt, String ngayDK, String ngayHH, String TrangThai, String maGoi) {
		this(); //Gọi hàm khởi tạo chính nó
		lb_name.setText("Gia Hạn Hội Viên");
		txt_hoten.setText(hoTen);
		if(gioiTinh.equals("Nam")) {
			rdb_nam.setSelected(true);
		}
		else
		{
			rdb_nu.setSelected(true);
		}
		txt_sdt.setText(sdt);
		
		//Xóa ActionListener cũ 
		for (ActionListener al : btn_huy.getActionListeners()) {
		    btn_huy.removeActionListener(al);
		}
		for (ActionListener al : btn_xacnhan.getActionListeners()) {
		    btn_xacnhan.removeActionListener(al);
		}
		
		//Thêm lại Action mới
		btn_huy.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				int choice = JOptionPane.showConfirmDialog(
						null,
						"Xác Nhận",
						"Xác Nhận Hủy?",
						JOptionPane.YES_NO_OPTION,
						JOptionPane.INFORMATION_MESSAGE
						);
				
				//Nếu yes thì trả về panel Danhsach
				if(choice == JOptionPane.YES_OPTION)
				{
					ThemTV.this.resetForm();
					
					// Lấy JFrame chứa JPanel này
                    JFrame frame = (JFrame) SwingUtilities.getWindowAncestor(ThemTV.this);

                    // Nếu frame đúng là MyFrame thì gọi hàm
                    if (frame instanceof view) {
                        ((view) frame).back_panel_DanhSach();
                    }
				}
			}
		});
		
		btn_xacnhan.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				boolean check_loi = false;
				
				String loi = "";
				String hoTen = txt_hoten.getText().trim();
				String sdt = txt_sdt.getText().trim();
				if(!sdt.matches("\\d{10}")) {
					loi += "Lỗi định dạng số điện thoại!\n";
					check_loi = true;
				}
				
				boolean gioiTinh = true; // Nam là true, nữ false
				if(rd_btn_gr.getSelection() == null) {
					loi += "Chưa chọn giới tính!\n";
					check_loi = true;
				}
				else
				{
					if(rdb_nam.isSelected()) {
						gioiTinh = true;
					}
					else 
					{
						gioiTinh = false;
					}
				}
				String ngayDK = dt_dk.toString();
				String maGoi = DBHelper.get_maGoi(String.valueOf(cbb_goi.getSelectedItem()));
				String ngayHH = dt_hh.toString();
				
				if(check_loi)
				{
					JOptionPane.showMessageDialog(
						    null,
						    loi,
						    "Thất Bại!",
						    JOptionPane.ERROR_MESSAGE
						);

					if(!sdt.matches("\\d{10}"))
					{
						txt_sdt.setText("");
					}
				}
				else
				{
					DBHelper.update_info(maHV, hoTen, gioiTinh, sdt, ngayDK, ngayHH, maGoi);
					ThemTV.this.resetForm();
					JOptionPane.showMessageDialog(
							null,
							"Gia Hạn Hội Viên Thành Công!",
							"Thành Công!",
							JOptionPane.INFORMATION_MESSAGE
							);
					
					//trả về panel Danhsach
					ThemTV.this.resetForm();
					
					// Lấy JFrame chứa JPanel này
                    JFrame frame = (JFrame) SwingUtilities.getWindowAncestor(ThemTV.this);

                    // Nếu frame đúng là MyFrame thì gọi hàm
                    if (frame instanceof view) {
                        ((view) frame).back_panel_DanhSach();
                    }
				}
			}
		});
	}

}
