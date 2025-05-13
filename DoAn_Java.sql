create database DoAn_Java;
use DoAn_Java;

create table GoiTap(
	MaGoi nvarchar(10) primary key,
    TenGoi nvarchar(50),
    ThoiHan int,
    GiaTien int
    );

create table HoiVien(
	MaTV nvarchar(10) primary key,
    HoTen nvarchar(100),
    GioiTinh bit,
    SoDT nvarchar(10),
    NgayDK date,
    NgayHH date,
    TrangThai nvarchar(50),
    MaGoi nvarchar(10),
    foreign key (MaGoi) references GoiTap(MaGoi)
    );		


INSERT INTO GoiTap (MaGoi, TenGoi, ThoiHan, GiaTien)
VALUES 	
(N'GT1Thang', N'Gói 1 tháng', 1, 300),
(N'GT3Thang', N'Gói 3 tháng', 3, 800),
(N'GT6Thang', N'Gói 6 tháng', 6, 1500),
(N'GT12Thang', N'Gói 12 tháng', 12, 2700);

INSERT INTO HoiVien
  (MaTV,    HoTen,            GioiTinh, SoDT,        NgayDK,     NgayHH,     TrangThai,      MaGoi)
VALUES
  (N'HV001', N'Nguyễn Văn An',   1, N'0912345678','2025-01-10','2025-07-10', N'Hoạt Động',   N'GT1Thang'),
  (N'HV002', N'Trần Thị Bình',   0, N'0987654321','2025-02-05','2025-08-05', N'Hoạt Động',   N'GT3Thang'),
  (N'HV003', N'Lê Văn Cường',    1, N'0901122334','2025-03-12','2025-09-12', N'Hoạt Động',     N'GT6Thang'),
  (N'HV004', N'Phạm Thị Dung',   0, N'0934455667','2025-04-20','2025-10-20', N'Tạm Ngưng',   N'GT1Thang'),
  (N'HV005', N'Hoàng Văn Em',    1, N'0977889900','2025-05-01','2025-11-01', N'Hoạt Động',   N'GT12Thang');
  

