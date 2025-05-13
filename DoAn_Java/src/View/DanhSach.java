package View;

import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Color;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.time.LocalDate;

import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

import Date.KiemTraNgayThang;
import SQL.DBHelper;

import javax.swing.JTable;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JScrollPane;

public class DanhSach extends JPanel {

	private static final long serialVersionUID = 1L;
	private JTextField txt_timkiem;
	private JTable table;
	private JScrollPane src_pane;
	private JButton btn_reload;
	private DefaultTableModel model;

	/**
	 * Create the panel.
	 */ 
	public DanhSach() {
		setLayout(null);
		
		JLabel lb_timkiem = new JLabel("Tìm Kiếm: ");
		lb_timkiem.setFont(new Font("Tahoma", Font.PLAIN, 20));
		lb_timkiem.setBounds(48, 35, 112, 30);
		add(lb_timkiem);
		
		Object[][] data = new Object[0][8];
		String[] columnNames = {"Mã TV", "Họ tên", "Giới tính", "Số ĐT", "Gói tập", "Ngày ĐK", "Ngày HH", "Trạng Thái"};

		model = new DefaultTableModel(data, columnNames) {
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

		table = new JTable(model);
		table.setBounds(10, 81, 1430, 713);
		//Set trong table
		table.setFont(new Font("Tahoma", Font.PLAIN ,18));
		//Set ở tiêu đề
		table.getTableHeader().setFont(new Font("Tahoma", Font.BOLD ,20));
		table.getTableHeader().setBackground(Color.LIGHT_GRAY);
		
		table.setRowHeight(25); 
		
		table.getTableHeader().setReorderingAllowed(false);  // Không cho phép thay đổi vị trí của header
		table.setDefaultEditor(Object.class, null);  // Không cho sửa ô
		
		//Tạo sự kiện click đúp vào record
		table.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				// Kiểm tra nếu click đúp (số lần click = 2)
                if (e.getClickCount() == 2 && table.getSelectedRow() != -1) {
                    // Lấy dòng được chọn
                    int selectedRow = table.getSelectedRow();
                    
                    //Lấy ngày hết hạn để check xem gia hạn được không?
                    String ngayHH = table.getValueAt(selectedRow, 6).toString();
                    KiemTraNgayThang ngay_HH = new KiemTraNgayThang(ngayHH);
                    KiemTraNgayThang ngay_hienTai = new KiemTraNgayThang(LocalDate.now());
                    
                    if(ngay_hienTai.sauNgay(ngay_HH))
                    {
	                    // Lấy dữ liệu từ dòng được chọn
	                    String maTV = table.getValueAt(selectedRow, 0).toString();
	                    String hoTen = table.getValueAt(selectedRow, 1).toString();
	                    String gioiTinh = table.getValueAt(selectedRow, 2).toString();
	                    String soDT = table.getValueAt(selectedRow, 3).toString();
	                    String goiTap = table.getValueAt(selectedRow, 4).toString();
	                    String ngayDK = table.getValueAt(selectedRow, 5).toString();
	                    String trangThai = table.getValueAt(selectedRow, 7).toString();
	
	                    // Lấy JFrame chứa JPanel này
	                    JFrame frame = (JFrame) SwingUtilities.getWindowAncestor(DanhSach.this);
	
	                    // Nếu frame đúng là MyFrame thì gọi hàm
	                    if (frame instanceof view) {
	                        ((view) frame).update_info_HV(maTV, hoTen, gioiTinh, soDT, ngayDK, ngayHH, trangThai, DBHelper.get_maGoi(goiTap));
	                    }
                    }
                    else
                    {
                    	JOptionPane.showMessageDialog(
                    			null,
                    			"Không thể gia hạn hoặc chỉnh sửa cho hội viên này vì hội viên đang còn trong gói đã đăng kí!",
                    			"Thông Báo Lỗi!",
                    			JOptionPane.ERROR_MESSAGE
                    			);
                    }
                }
			}
			
		});
		
		src_pane = new JScrollPane(table);
		src_pane.setBounds(10, 81, 1430, 713);
		add(src_pane);
		
		JComboBox cbb_timkiem = new JComboBox();
		cbb_timkiem.setBounds(829, 31, 150, 40);
		add(cbb_timkiem);
		
		//Add thằng All vào trước
		cbb_timkiem.addItem("All");
		for(String tenGoi : DBHelper.get_TenGoiTap())
		{
			cbb_timkiem.addItem(tenGoi);
		}
		cbb_timkiem.setSelectedIndex(0);
		
		txt_timkiem = new JTextField();
		txt_timkiem.setFont(new Font("Tahoma", Font.PLAIN, 18));
		txt_timkiem.setBounds(170, 31, 620, 40);
		add(txt_timkiem);
		txt_timkiem.setColumns(10);
		
		JButton btn_timkiem = new JButton("");
		btn_timkiem.setBounds(1007, 21, 50, 50);
		add(btn_timkiem);
		
		ImageIcon icon_tk = new ImageIcon(getClass().getResource("/resource/search.png"));
		btn_timkiem.setIcon(icon_tk);
		btn_timkiem.setHorizontalAlignment(SwingConstants.CENTER);
		btn_timkiem.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				String src = txt_timkiem.getText().trim();
				String maGoi = String.valueOf(cbb_timkiem.getSelectedItem());
				
				DefaultTableModel dtm = DBHelper.get_record_bySearch(src, maGoi);
				
				table.setModel(dtm);//add vào table
				
				//Set lại các thuộc tính
				table.setBounds(10, 81, 1430, 713);
				table.setFont(new Font("Tahoma", Font.PLAIN ,18));
				table.getTableHeader().setFont(new Font("Tahoma", Font.BOLD ,20));
				table.getTableHeader().setBackground(Color.LIGHT_GRAY);
				
				table.setRowHeight(25); 
				
				table.getTableHeader().setReorderingAllowed(false);  // Không cho phép thay đổi vị trí của header
				table.setDefaultEditor(Object.class, null);  // Không cho sửa ô
			}
		});
		
		btn_reload = new JButton("Làm Mới");
		btn_reload.setFont(new Font("Tahoma", Font.PLAIN, 20));
		btn_reload.setBounds(1281, 31, 159, 40);
		add(btn_reload);
		btn_reload.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				DefaultTableModel dtm = DBHelper.get_danhsachHV();
				if(dtm != null)
				{
					DBHelper.update_trangThai_allRecords();
					
					table.setModel(dtm);//add vào table
					
					//Set lại các thuộc tính
					table.setBounds(10, 81, 1430, 713);
					table.setFont(new Font("Tahoma", Font.PLAIN ,18));
					table.getTableHeader().setFont(new Font("Tahoma", Font.BOLD ,20));
					table.getTableHeader().setBackground(Color.LIGHT_GRAY);
					
					table.setRowHeight(25); 
					
					table.getTableHeader().setReorderingAllowed(false);  // Không cho phép thay đổi vị trí của header
					table.setDefaultEditor(Object.class, null);  // Không cho sửa ô
				}
				else
				{
					JOptionPane.showMessageDialog(
							null,
							"Lỗi Hệ Thống!",
							"Lỗi Lấy Danh Sách Hội Viên!",
							JOptionPane.ERROR_MESSAGE
							);
				}
			}
		});
		
		ImageIcon icon_capnhat = new ImageIcon(getClass().getResource("/resource/update.png"));
		btn_reload.setIcon(icon_capnhat);
		btn_reload.setHorizontalAlignment(SwingConstants.LEFT);
		btn_reload.setHorizontalTextPosition(SwingConstants.RIGHT);
	}
}
