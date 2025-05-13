package Date;

import java.time.LocalDate;

public class KiemTraNgayThang {
    private int ngay;
    private int thang;
    private int nam;

    public KiemTraNgayThang(int ngay, int thang, int nam) {
        this.ngay = ngay;
        this.thang = thang;
        this.nam = nam;
    }
    
    // Khởi tạo đối tượng KiemTraNgayThang từ chuỗi dạng "yyyy-MM-dd"
    public KiemTraNgayThang(String chuoiNgay) {
        if (chuoiNgay != null && chuoiNgay.matches("\\d{4}-\\d{2}-\\d{2}")) {
            String[] phanTach = chuoiNgay.split("-");
            this.nam = Integer.parseInt(phanTach[0]);
            this.thang = Integer.parseInt(phanTach[1]);
            this.ngay = Integer.parseInt(phanTach[2]);
        } else {
            // Giá trị mặc định nếu chuỗi không hợp lệ
            this.ngay = 1;
            this.thang = 1;
            this.nam = 2000;
        }
    }
    
    //Hàm khởi tạo với LocalDate
    public KiemTraNgayThang(LocalDate date)
    {
    	this.ngay = date.getDayOfMonth();
    	this.thang = date.getMonthValue();
    	this.nam = date.getYear();
    }
    
    /**
     * Phương thức kiểm tra ngày tháng có hợp lệ hay không
     */
    public boolean kiemTraHopLe() {
        // Kiểm tra giá trị cơ bản
        if (nam <= 0 || thang < 1 || thang > 12 || ngay < 1 || ngay > 31) {
            return false;
        }
        
        // Kiểm tra số ngày trong tháng
        int soNgayToiDa = laySoNgayTrongThang();
        return ngay <= soNgayToiDa;
    }
    
    /**
     * Lấy số ngày tối đa trong tháng hiện tại
     */
    public int laySoNgayTrongThang() {
        int soNgay;
        
        switch (thang) {
            case 4: case 6: case 9: case 11:
                soNgay = 30;
                break;
            case 2:
                if (kiemTraNamNhuan()) {
                    soNgay = 29;
                } else {
                    soNgay = 28;
                }
                break;
            default:
                soNgay = 31;
                break;
        }
        
        return soNgay;
    }
    
    /** 
     * Kiểm tra xem năm hiện tại có phải là năm nhuận hay không
     */
    public boolean kiemTraNamNhuan() {
        // Năm nhuận là năm chia hết cho 4
        // Ngoại trừ các năm chia hết cho 100 mà không chia hết cho 400
        if ((nam % 4 == 0 && nam % 100 != 0) || (nam % 400 == 0)) {
            return true;
        } else {
            return false;
        }
    }
    
    /**
     * So sánh với một ngày khác xem ngày hiện tại có sau không
     */
    public boolean sauNgay(KiemTraNgayThang ngayKhac) {
        if (this.nam > ngayKhac.nam) {
            return true;
        } else if (this.nam < ngayKhac.nam) {
            return false;
        }
        
        // Cùng năm, kiểm tra tháng
        if (this.thang > ngayKhac.thang) {
            return true;
        } else if (this.thang < ngayKhac.thang) {
            return false;
        }
        
        // Cùng năm và tháng, kiểm tra ngày
        return this.ngay > ngayKhac.ngay;
    }
    
    /**
     * Tính số ngày chênh lệch giữa ngày hiện tại và ngày khác
     */
    public int tinhSoNgayChenhLech(KiemTraNgayThang ngayKhac) {
        // Tính tổng số ngày từ đầu lịch sử
        int tongNgay1 = chuyenSangTongSoNgay();
        int tongNgay2 = ngayKhac.chuyenSangTongSoNgay();
        
        // Lấy giá trị tuyệt đối của hiệu
        return Math.abs(tongNgay1 - tongNgay2);
    }
    
    /**
     * Chuyển đổi ngày tháng năm thành tổng số ngày từ 01/01/0001
     */
    private int chuyenSangTongSoNgay() {
        int tongSoNgay = ngay;
        
        // Thêm số ngày của các tháng trong năm hiện tại
        for (int t = 1; t < thang; t++) {
            int soNgay;
            
            switch (t) {
                case 4: case 6: case 9: case 11:
                    soNgay = 30;
                    break;
                case 2:
                    if ((nam % 4 == 0 && nam % 100 != 0) || (nam % 400 == 0)) {
                        soNgay = 29;
                    } else {
                        soNgay = 28;
                    }
                    break;
                default:
                    soNgay = 31;
                    break;
            }
            
            tongSoNgay += soNgay;
        }
        
        // Thêm số ngày của các năm trước đó
        for (int n = 1; n < nam; n++) {
            if ((n % 4 == 0 && n % 100 != 0) || (n % 400 == 0)) {
                tongSoNgay += 366;
            } else {
                tongSoNgay += 365;
            }
        }
        
        return tongSoNgay;
    }
    
    /**
     * Các phương thức getter và setter
     */
    public int layNgay() {
        return ngay;
    }
    
    public void datNgay(int ngayMoi) {
        this.ngay = ngayMoi;
    }
    
    public int layThang() {
        return thang;
    }
    
    public void datThang(int thangMoi) {
        this.thang = thangMoi;
    }
    
    public int layNam() {
        return nam;
    }
    
    public void datNam(int namMoi) {
        this.nam = namMoi;
    }
    
    /**
     * Cộng một số tháng vào ngày hiện tại và trả về chuỗi ngày mới
     * 
     * @param soThang Số tháng cần cộng thêm (có thể âm để trừ đi)
     * @return Chuỗi biểu diễn ngày mới theo định dạng dd/MM/yyyy
     */
    public void congThang(int soThang) {
        // Tính toán tháng và năm mới
        int thangMoi = this.thang + soThang;
        int namMoi = this.nam;

        // Xử lý nếu thangMoi > 12 hoặc < 1
        if (thangMoi > 12) {
            // Nếu thangMoi > 12, chuyển sang năm tiếp theo
            namMoi += (thangMoi - 1) / 12;
            thangMoi = ((thangMoi - 1) % 12) + 1;
        } else if (thangMoi < 1) {
            // Nếu thangMoi < 1, lùi về năm trước
            int namLui = (Math.abs(thangMoi) / 12) + 1;
            namMoi -= namLui;
            thangMoi = 12 - (Math.abs(thangMoi) % 12);
            if (thangMoi == 12) {
                thangMoi = 12;
                namMoi++;
            }
        }

        // Lưu ngày hiện tại trước khi thay đổi tháng và năm
        int ngayHienTai = this.ngay;
        
        // Cập nhật tháng và năm cho đối tượng hiện tại
        this.thang = thangMoi;
        this.nam = namMoi;
        
        // Kiểm tra nếu ngày không hợp lệ trong tháng mới (ví dụ: 31/2)
        // thì lấy ngày cuối cùng của tháng đó
        if (!this.kiemTraHopLe()) {
            this.ngay = this.laySoNgayTrongThang();
        } else {
            // Giữ nguyên ngày nếu hợp lệ
            this.ngay = ngayHienTai;
        }
    }
    
    
    
    /**
     * Chuyển đổi thành chuỗi ngày tháng (định dạng dd/MM/yyyy)
     */
    @Override
    public String toString() {
    	return String.format("%04d-%02d-%02d", nam, thang, ngay);
    }
    
    /**
     * Phương thức main để kiểm tra nhanh (mẫu sử dụng)
     */
//    public static void main(String[] args) {
//        // Ví dụ sử dụng 1: Kiểm tra ngày 31/04/2025
//        KiemTraNgayThang ngay1 = new KiemTraNgayThang(31, 4, 2025);
//        System.out.println("Ngày: " + ngay1);
//        System.out.println("Hợp lệ: " + ngay1.kiemTraHopLe());  // False vì tháng 4 chỉ có 30 ngày
//        
//        // Ví dụ sử dụng 2: Kiểm tra chuỗi ngày
//        KiemTraNgayThang ngay2 = new KiemTraNgayThang("29/02/2024");
//        System.out.println("Ngày: " + ngay2);
//        System.out.println("Hợp lệ: " + ngay2.kiemTraHopLe());  // True vì 2024 là năm nhuận
//        
//        // Ví dụ sử dụng 3: So sánh hai ngày
//        KiemTraNgayThang ngay3 = new KiemTraNgayThang(15, 5, 2025);
//        System.out.println("Ngày 3 sau ngày 2: " + ngay3.sauNgay(ngay2));
//        
//        // Ví dụ sử dụng 4: Tính số ngày chênh lệch
//        System.out.println("Số ngày chênh lệch: " + ngay3.tinhSoNgayChenhLech(ngay2));
//        
//        // Ví dụ sử dụng 5: Cộng thêm số tháng vào ngày
//        KiemTraNgayThang ngay4 = new KiemTraNgayThang(31, 1, 2025);
//        System.out.println("Ngày ban đầu: " + ngay4);
//        
//        // Cộng thêm 1 tháng
//        ngay4.congThang(1);
//        System.out.println("Sau khi cộng 1 tháng: " + ngay4.toString());
//        
//        // Cộng thêm 3 tháng
//        ngay4.congThang(3);
//        System.out.println("Sau khi cộng 3 tháng: " + ngay4.toString());
//        
//        // Trừ đi 2 tháng
//        //System.out.println("Sau khi trừ 2 tháng: " + ngay4.congThang(-2));
//    }
}
