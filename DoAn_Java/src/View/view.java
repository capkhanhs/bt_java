package View;

import java.awt.EventQueue;
import java.awt.Image;
import java.awt.Toolkit;

import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import java.awt.Font;
import java.awt.Color;
import java.awt.Dimension;

import javax.swing.JToggleButton;
import javax.swing.SwingConstants;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class view extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel pn_main;
	private JPanel pn_center;
	private String layout_centerNow = "";

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					view frame = new view();
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
	public view() {
		ImageIcon icon = new ImageIcon(getClass().getResource("/resource/logo_Dut.png"));
		
		setIconImage(icon.getImage());
		setTitle("Chương trình quản lý phòng GYM by team 2K2N !");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);	
		setBounds(30, 30, 1662, 942);
		pn_main = new JPanel();
		pn_main.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(pn_main);
		pn_main.setLayout(null);
		
		JLabel lb_logo = new JLabel("DUT");
		lb_logo.setBounds(0, 0, 100, 100);
		
		
		Image img = icon.getImage().getScaledInstance(lb_logo.getWidth(), lb_logo.getHeight(), Image.SCALE_SMOOTH);
		lb_logo.setIcon(new ImageIcon(img));
		
		pn_main.add(lb_logo);
		
		JPanel pn_top = new JPanel();
		pn_top.setBackground(new Color(192, 192, 192));
		pn_top.setBounds(0, 0, 1648, 100);
		pn_main.add(pn_top);
		pn_top.setLayout(null);
		
		pn_center = new JPanel();
		pn_center.setBounds(198, 100, 1450, 805);
		pn_main.add(pn_center);
		pn_center.setLayout(null);
		
		JLabel lb_dut = new JLabel("TRƯỜNG ĐẠI HỌC BÁCH KHOA - ĐẠI HỌC ĐÀ NẴNG");
		lb_dut.setForeground(Color.BLUE);
		lb_dut.setBounds(342, 40, 949, 49);
		pn_top.add(lb_dut);
		lb_dut.setFont(new Font("Tahoma", Font.PLAIN, 40));
		
		JLabel lb_name = new JLabel("CHƯƠNG TRÌNH QUẢN LÝ PHÒNG GYM");
		lb_name.setBounds(536, 11, 531, 36);
		pn_top.add(lb_name);
		lb_name.setFont(new Font("Tahoma", Font.PLAIN, 30));
		
		JLabel lb_team = new JLabel("2K2N");
		lb_team.setBounds(1548, 0, 100, 100);
		
		ImageIcon icon2 = new ImageIcon(getClass().getResource("/resource/2K2N.png"));
		Image img2 = icon2.getImage().getScaledInstance(lb_team.getWidth(), lb_team.getHeight(), Image.SCALE_SMOOTH);
		lb_team.setIcon(new ImageIcon(img2));
		
		pn_top.add(lb_team);
		
		JPanel pn_left = new JPanel();
		pn_left.setBackground(Color.LIGHT_GRAY);
		pn_left.setBounds(0, 100, 200, 805);
		pn_main.add(pn_left);
		pn_left.setLayout(null);
		
		JToggleButton tgbtn_danhsach = new JToggleButton("Danh Sách HV");
		tgbtn_danhsach.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(!layout_centerNow.equals("DanhSach")) {
					pn_center.removeAll();
					DanhSach ds = new DanhSach();
					ds.setBounds(0, 0, 1450, 805);
					pn_center.add(ds);
					
					 // Cập nhật lại giao diện
					pn_center.revalidate();
					pn_center.repaint();
					
					layout_centerNow = "DanhSach";
					
					System.out.println("Da them Layout 1");
				}
			}
		});
		tgbtn_danhsach.setFont(new Font("Tahoma", Font.PLAIN, 18));
		tgbtn_danhsach.setBounds(0, 30, 200, 60);
		
		ImageIcon icon_dshv = new ImageIcon(getClass().getResource("/resource/ThanhVien.png"));
		tgbtn_danhsach.setIcon(icon_dshv);
		tgbtn_danhsach.setHorizontalTextPosition(SwingConstants.RIGHT);
		tgbtn_danhsach.setHorizontalAlignment(SwingConstants.LEFT);
		
		
		pn_left.add(tgbtn_danhsach);
		
		JToggleButton tgbtn_themtv = new JToggleButton("Thêm Hội Viên");
		tgbtn_themtv.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				if(!layout_centerNow.equals("ThemTV")) {
					pn_center.removeAll();
					ThemTV ttv = new ThemTV();
					ttv.setBounds(0, 0, 1450, 805);
					pn_center.add(ttv);
					
					 // Cập nhật lại giao diện
					pn_center.revalidate();
					pn_center.repaint();
					
					layout_centerNow = "ThemTV";
					
					System.out.println("Da them Layout 2");
				}
			}
		});
		tgbtn_themtv.setFont(new Font("Tahoma", Font.PLAIN, 18));
		tgbtn_themtv.setIcon(new ImageIcon(view.class.getResource("/resource/Them_TV.png")));
		tgbtn_themtv.setHorizontalTextPosition(SwingConstants.RIGHT);
		tgbtn_themtv.setHorizontalAlignment(SwingConstants.LEFT);
		tgbtn_themtv.setBounds(0, 90, 200, 60);
		pn_left.add(tgbtn_themtv);
		
		ButtonGroup gr_btn = new ButtonGroup();
		gr_btn.add(tgbtn_danhsach);
		gr_btn.add(tgbtn_themtv);
	}
	
	//Test gọi từ panel
	public void update_info_HV(String maHV, String hoTen, String gioiTinh, String sdt, String ngayDK, String ngayHH, String TrangThai, String maGoi) {
		pn_center.removeAll();
		ThemTV ttv = new ThemTV(maHV, hoTen, gioiTinh, sdt, ngayDK, ngayHH, TrangThai, maGoi);
		ttv.setBounds(0, 0, 1450, 805);
		pn_center.add(ttv);
		
		 // Cập nhật lại giao diện
		pn_center.revalidate();
		pn_center.repaint();
		
		layout_centerNow = "ThemTV";
		
		System.out.println("Da them Layout 2 (Update)");
	}
	
	//Panel gọi để quay lại trở về Panel DanhSach
	public void back_panel_DanhSach() {
		pn_center.removeAll();
		DanhSach ds = new DanhSach();
		ds.setBounds(0, 0, 1450, 805);
		pn_center.add(ds);
		
		 // Cập nhật lại giao diện
		pn_center.revalidate();
		pn_center.repaint();
		
		layout_centerNow = "DanhSach";
	}
}
