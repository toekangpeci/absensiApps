-- phpMyAdmin SQL Dump
-- version 4.9.0.1
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Generation Time: Feb 13, 2023 at 07:25 AM
-- Server version: 10.3.15-MariaDB
-- PHP Version: 7.3.6

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET AUTOCOMMIT = 0;
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `absensi_apps`
--

-- --------------------------------------------------------

--
-- Table structure for table `cuti_jml`
--

CREATE TABLE `cuti_jml` (
  `id_user` varchar(25) NOT NULL,
  `cuti_tahunan` int(25) DEFAULT NULL,
  `cuti_sisa` int(25) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `cuti_jml`
--

INSERT INTO `cuti_jml` (`id_user`, `cuti_tahunan`, `cuti_sisa`) VALUES
('nfl', 12, 12),
('doe', 12, 10),
('nopal', 12, 12),
('nopal2', 12, 12),
('jkt002', 12, 12),
('jkt003', 12, 12),
('jkt004', 12, 12),
('jkt005', 12, 12),
('jkt006', 12, 12),
('jkt007', 12, 12),
('jkt008', 12, 12),
('jkt009', 12, 12);

-- --------------------------------------------------------

--
-- Table structure for table `cuti_trans`
--

CREATE TABLE `cuti_trans` (
  `id` varchar(15) NOT NULL,
  `id_user` varchar(25) NOT NULL,
  `jenis_cuti` varchar(50) NOT NULL,
  `alasan` varchar(250) NOT NULL,
  `jumlah_cuti` int(10) NOT NULL,
  `mulai_cuti` date NOT NULL,
  `akhir_cuti` date NOT NULL,
  `delegasi` varchar(25) DEFAULT NULL,
  `status` varchar(50) NOT NULL,
  `keterangan_status` varchar(50) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `cuti_trans`
--

INSERT INTO `cuti_trans` (`id`, `id_user`, `jenis_cuti`, `alasan`, `jumlah_cuti`, `mulai_cuti`, `akhir_cuti`, `delegasi`, `status`, `keterangan_status`) VALUES
('CUT202211001', 'doe', 'Cuti Tahunan', 'healing', 1, '2022-11-07', '2022-11-08', 'test', '\'Diajukan\'', ''),
('CUT202211002', 'doe', 'Sakit Potong Cuti Tahunan', 'sakit', 1, '2022-11-14', '2022-11-14', 'test', 'Revisi', 'revisi'),
('CUT202211003', 'doe', 'Cuti Menikah (Special Leave)', 'nikah', 1, '2022-11-18', '2022-11-19', 'test', 'Ditolak', 'ga'),
('CUT202211004', 'doe', 'Cuti Tahunan', 'healing', 1, '2022-11-14', '2022-11-15', 'doe', 'Disetujui', 'Oke gas'),
('CUT202211005', 'doe', 'Cuti Menikah (Special Leave)', 'healign', 1, '2022-11-15', '2022-11-16', 'doe', 'Disetujui', 'oke gas'),
('CUT202211006', 'doe', 'Cuti Tahunan', 'healing', 1, '2022-11-14', '2022-11-15', 'nfl', 'Disetujui', 'oke gas'),
('CUT202211007', 'doe', 'Cuti Baptis anak (Special Leave)', 'baptis', 1, '2022-11-14', '2022-11-15', 'nfl', 'Disetujui', 'oke gas aja bro'),
('CUT202212001', 'doe', 'Cuti Tahunan', 'mau nonton pildun', 2, '2022-12-07', '2022-12-09', 'doe', 'Diajukan', ''),
('CUT202302001', 'doe', 'Cuti Tahunan', 'cuti sabung komodo', 2, '2023-02-13', '2023-02-14', 'nopal', 'Diajukan', '');

-- --------------------------------------------------------

--
-- Table structure for table `department`
--

CREATE TABLE `department` (
  `kode` varchar(10) NOT NULL,
  `nama` varchar(50) NOT NULL,
  `keterangan` varchar(250) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `department`
--

INSERT INTO `department` (`kode`, `nama`, `keterangan`) VALUES
('FA', 'Finance & Accounting', 'tim keuangan'),
('GA', 'Generals Affair', 'bagian umum'),
('IT', 'Technology Infomation', 'tim it'),
('PUR', 'Purchasing', 'bagian pembelian');

-- --------------------------------------------------------

--
-- Table structure for table `dinas_luar`
--

CREATE TABLE `dinas_luar` (
  `id_dinas` varchar(15) NOT NULL,
  `id_user` varchar(25) NOT NULL,
  `alasan_dinas` varchar(250) NOT NULL,
  `lama_dinas` int(10) NOT NULL,
  `pendapatan_dinas` bigint(20) NOT NULL,
  `mulai_dinas` date NOT NULL,
  `selesai_dinas` date NOT NULL,
  `status` varchar(25) NOT NULL,
  `ket_status` varchar(250) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `dinas_luar`
--

INSERT INTO `dinas_luar` (`id_dinas`, `id_user`, `alasan_dinas`, `lama_dinas`, `pendapatan_dinas`, `mulai_dinas`, `selesai_dinas`, `status`, `ket_status`) VALUES
('DIN202211001', 'doe', 'pitching ke mandalika', 2, 0, '2022-11-15', '2022-11-16', 'Diajukan', ''),
('DIN202211002', 'doe', 'mau tarung dengan komodo', 1, 500000, '2022-11-24', '2022-11-25', 'Disetujui', 'oek gas'),
('DIN202211003', 'doe', 'mengikuti g20', 2, 500000, '2022-11-24', '2022-11-26', 'Disetujui', 'oke gas boy'),
('DIN202211004', 'doe', 'mengikuti g20s pki ', 2, 500000, '2022-11-24', '2022-11-26', 'Ditolak', 'gaboleh pergi pokonya'),
('DIN202212001', 'nfl', 'ke mandalika, nonton komodo', 2, 500000, '2022-12-06', '2022-12-08', 'Disetujui', 'oke gas');

-- --------------------------------------------------------

--
-- Table structure for table `gaji_logs`
--

CREATE TABLE `gaji_logs` (
  `id` int(11) NOT NULL,
  `id_user` varchar(25) NOT NULL,
  `gaji` bigint(20) NOT NULL,
  `date` timestamp NOT NULL DEFAULT current_timestamp() ON UPDATE current_timestamp(),
  `periode` varchar(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `gaji_logs`
--

INSERT INTO `gaji_logs` (`id`, `id_user`, `gaji`, `date`, `periode`) VALUES
(1, 'nopal2', 14500000, '2023-02-12 14:48:32', '11-2022'),
(2, 'nopal2', 14950000, '2023-02-12 14:48:32', '11-2022'),
(3, 'doe', 12000000, '2023-02-12 14:48:32', '11-2022'),
(4, 'doe', 14000000, '2023-02-12 14:48:32', '11-2022'),
(5, 'nfl', 12000000, '2023-02-12 14:48:32', '11-2022'),
(6, 'nfl', 12500000, '2023-02-12 14:48:32', '11-2022'),
(7, 'doe', 12000000, '2023-02-12 14:48:32', '02-2023'),
(8, 'nfl', 12500000, '2023-02-12 14:48:32', '02-2023'),
(15, 'doe', 12000000, '2023-02-12 14:48:32', '02-2023'),
(18, 'nfl', 12500000, '2023-02-12 14:48:32', '02-2023'),
(19, 'nfl', 12500000, '2023-02-12 14:48:32', '02-2023'),
(20, 'nfl', 12500000, '2023-02-12 14:48:32', '02-2023'),
(21, 'jkt002', 10000000, '2023-02-12 14:48:32', '02-2023'),
(22, 'jkt003', 6000000, '2023-02-12 14:44:16', '02-2023'),
(23, 'jkt003', 6000000, '2023-02-12 14:48:32', '02-2023'),
(24, 'jkt003', 6000000, '2023-02-12 14:48:32', '02-2023'),
(25, 'jkt008', 9000000, '2023-02-12 14:44:16', '02-2023'),
(26, 'jkt009', 9000000, '2023-02-12 14:35:30', '02-2023'),
(27, 'doe', 12000000, '2023-02-12 14:53:09', '1-2023'),
(28, 'doe', 12000000, '2023-02-12 14:54:15', '1-2023'),
(29, 'jkt008', 9000000, '2023-02-12 15:15:55', '01-2023'),
(30, 'jkt008', 9000000, '2023-02-12 15:16:02', '01-2023'),
(31, 'jkt008', 9000000, '2023-02-12 15:37:17', '01-2023'),
(32, 'jkt008', 9000000, '2023-02-12 15:37:26', '02-2023'),
(33, 'jkt009', 9000000, '2023-02-13 06:12:57', '02-2023'),
(34, 'jkt009', 9000000, '2023-02-13 06:13:04', '01-2023'),
(35, 'doe', 12000000, '2023-02-13 06:13:13', '02-2023'),
(36, 'doe', 12000000, '2023-02-13 06:13:19', '01-2023');

-- --------------------------------------------------------

--
-- Table structure for table `jabatan`
--

CREATE TABLE `jabatan` (
  `kode_jabatan` varchar(10) NOT NULL,
  `nama_jabatan` varchar(50) NOT NULL,
  `keterangan` varchar(250) DEFAULT NULL,
  `upah_lembur` bigint(20) NOT NULL,
  `upah_dinas` bigint(20) NOT NULL,
  `panelAppRep` varchar(10) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `jabatan`
--

INSERT INTO `jabatan` (`kode_jabatan`, `nama_jabatan`, `keterangan`, `upah_lembur`, `upah_dinas`, `panelAppRep`) VALUES
('mgr', 'Manager', '', 150000, 500000, '111'),
('PRES', 'President Direktur', '', 200000, 500000, '111'),
('spv', 'Supervisor', '', 125000, 500000, '011'),
('sta', 'Staff', '', 100000, 500000, '000');

-- --------------------------------------------------------

--
-- Table structure for table `karyawan`
--

CREATE TABLE `karyawan` (
  `id` varchar(25) NOT NULL DEFAULT '0',
  `password` varchar(50) NOT NULL,
  `nik` varchar(25) NOT NULL,
  `nama` varchar(30) NOT NULL,
  `department` varchar(10) NOT NULL,
  `alamat` varchar(255) NOT NULL,
  `agama` varchar(25) NOT NULL,
  `no_telp` varchar(25) NOT NULL,
  `jabatan` varchar(25) NOT NULL,
  `gaji` bigint(20) NOT NULL,
  `id_atasan` varchar(25) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `karyawan`
--

INSERT INTO `karyawan` (`id`, `password`, `nik`, `nama`, `department`, `alamat`, `agama`, `no_telp`, `jabatan`, `gaji`, `id_atasan`) VALUES
('doe', '%IßxIxÃÒÍü«)´¾', 'bdg-10000', 'doeflamingo', 'GA', 'depok', 'ISLAM', '00000000000', 'mgr', 12000000, 'nfl'),
('jkt002', '%IßxIxÃÒÍü«)´¾', 'jkt002', 'abdul', 'FA', 'depok', 'ISLAM', '08529999999', 'mgr', 10000000, 'doe'),
('jkt003', '%IßxIxÃÒÍü«)´¾', 'jkt003', 'ghani', 'FA', 'depok', 'ISLAM', '0210000000', 'sta', 6000000, 'jkt002'),
('jkt004', '%IßxIxÃÒÍü«)´¾', 'jkt004', 'toyo', 'FA', 'bandung', 'ISLAM', '099999', 'spv', 8000000, 'jkt002'),
('jkt005', '%IßxIxÃÒÍü«)´¾', 'jkt005', 'aryo', 'FA', 'depok', 'ISLAM', '021222', 'sta', 7000000, 'jkt002'),
('jkt006', '%IßxIxÃÒÍü«)´¾', 'jkt006', 'ahabi', 'FA', 'd', 'ISLAM', '2222', 'spv', 5000000, 'jkt002'),
('jkt007', '%IßxIxÃÒÍü«)´¾', 'jkt007', 'jaka', 'FA', 'surakarta', 'ISLAM', '09999999', 'spv', 9000000, 'jkt002'),
('jkt008', '%IßxIxÃÒÍü«)´¾', 'jkt008', 'asaya', 'FA', 'depok', 'ISLAM', '0219999999', 'spv', 9000000, 'jkt002'),
('jkt009', '%IßxIxÃÒÍü«)´¾', 'jkt009', 'asmodeus', 'FA', 'bandung', 'ISLAM', '000', 'spv', 9000000, 'jkt002'),
('nfl', '%IßxIxÃÒÍü«)´¾', 'jkt-123', 'naufal', 'GA', 'depok', 'ISLAM', '085777777777', 'mgr', 12500000, 'doe'),
('nopal', '‹Å2€\"«!ë|:õˆ×(z-', '12345', 'nopal', 'IT', 'depok', 'BUDDHA', '099999999999', 'sta', 15000000, 'nfl'),
('nopal2', '%IßxIxÃÒÍü«)´¾', '12346', 'naufal', 'IT', 'depok', 'BUDDHA', '888888888888', 'PRES', 14950000, 'nfl');

-- --------------------------------------------------------

--
-- Table structure for table `lembur_trans`
--

CREATE TABLE `lembur_trans` (
  `id` varchar(15) NOT NULL,
  `id_user` varchar(25) NOT NULL,
  `durasi` int(10) NOT NULL,
  `tanggal` date NOT NULL,
  `mulai` time NOT NULL,
  `akhir` time NOT NULL,
  `alasan_lembur` varchar(250) NOT NULL,
  `total_lembur` bigint(20) NOT NULL,
  `status` varchar(25) NOT NULL,
  `ket_status` varchar(250) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `lembur_trans`
--

INSERT INTO `lembur_trans` (`id`, `id_user`, `durasi`, `tanggal`, `mulai`, `akhir`, `alasan_lembur`, `total_lembur`, `status`, `ket_status`) VALUES
('LMB202211001', 'doe', 1, '2022-11-08', '21:39:00', '22:39:00', 'masih semangat berkerja', 150000, 'Diajukan', ''),
('LMB202211002', 'doe', 2, '2022-11-08', '10:31:00', '12:31:00', '', 300000, 'Disetujui', 'oke coy'),
('LMB202211003', 'doe', 2, '2022-11-08', '14:17:00', '16:17:00', 'masih pengen kerja aja', 300000, 'Disetujui', 'OKE CAER'),
('LMB202211004', 'doe', 2, '2022-11-11', '16:36:00', '18:36:00', 'mood lembur aja', 300000, 'Disetujui', 'oke mantap boy'),
('LMB202211005', 'doe', 1, '2022-11-11', '16:36:00', '17:36:00', 'cari uang aja', 150000, 'Diajukan', ''),
('LMB202211006', 'nfl', 3, '2022-11-14', '13:55:00', '16:55:00', 'lembur', 300000, 'Disetujui', 'oke siap boy'),
('LMB202212001', 'doe', 2, '2022-12-13', '19:07:00', '21:07:00', 'ada kerjaan', 300000, 'Diajukan', ''),
('LMB202212002', 'nfl', 2, '2022-12-06', '20:23:00', '22:23:00', 'pengen lembur', 200000, 'Disetujui', 'oke gas'),
('LMB202302001', 'doe', 2, '2023-02-06', '16:33:00', '18:33:00', 'masih giat kerja', 300000, 'Disetujui', 'oke boy');

-- --------------------------------------------------------

--
-- Table structure for table `logs_transaksi`
--

CREATE TABLE `logs_transaksi` (
  `id_user` varchar(25) NOT NULL,
  `id_transaksi` varchar(15) NOT NULL,
  `jenis` varchar(25) NOT NULL,
  `tanggal` date NOT NULL,
  `total` bigint(20) NOT NULL,
  `lama` int(10) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `logs_transaksi`
--

INSERT INTO `logs_transaksi` (`id_user`, `id_transaksi`, `jenis`, `tanggal`, `total`, `lama`) VALUES
('doe', 'LMB202211001', 'Lembur', '2022-11-11', 0, 0),
('doe', 'LMB202211002', 'Lembur', '2022-11-11', 0, 0),
('doe', 'LMB202211003', 'Lembur', '2022-11-11', 0, 0),
('doe', 'REM202211001', 'Reimbursement', '2022-11-11', 5000000, 0),
('doe', 'DIN202211001', 'Dinas Luar', '2022-11-11', 0, 0),
('doe', 'DIN202211002', 'Dinas Luar', '2022-11-11', 0, 0),
('doe', 'LMB202211004', 'Lembur', '2022-11-11', 300000, 0),
('doe', 'REM202211003', 'Reimbursement', '2022-11-19', 2500000, 0),
('doe', 'DIN202211003', 'Dinas Luar', '2022-11-24', 500000, 0),
('nfl', 'LMB202211006', 'Lembur', '2022-11-14', 300000, 3),
('nfl', 'LMB202212002', 'Lembur', '2022-12-06', 200000, 2),
('nfl', 'REM202212002', 'Reimbursement', '2022-12-21', 5000000, 0),
('nfl', 'DIN202212001', 'Dinas Luar', '2022-12-06', 500000, 2),
('doe', 'LMB202302001', 'Lembur', '2023-02-06', 300000, 2);

-- --------------------------------------------------------

--
-- Table structure for table `reimburse`
--

CREATE TABLE `reimburse` (
  `id` varchar(15) NOT NULL,
  `id_user` varchar(25) NOT NULL,
  `nama_kegiatan` varchar(250) NOT NULL,
  `alasan` varchar(250) NOT NULL,
  `total` bigint(20) NOT NULL,
  `date` date NOT NULL,
  `status` varchar(25) NOT NULL,
  `ket_status` varchar(250) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `reimburse`
--

INSERT INTO `reimburse` (`id`, `id_user`, `nama_kegiatan`, `alasan`, `total`, `date`, `status`, `ket_status`) VALUES
('REM202211001', 'doe', 'makan di cafe oi depok', 'pitching client', 5000000, '2022-11-15', 'Disetujui', 'oke mantap'),
('REM202211002', 'doe', 'mengikuti seminar budidaya komodo', 'buat beli pakan komodo', 5000000, '2022-11-18', 'Disetujui', 'oke lanjut boy'),
('REM202211003', 'doe', 'masih berhubungan dengan komodo', 'biaya rumah sakit digigit komodo', 2500000, '2022-11-19', 'Disetujui', 'Oke boy, semoga belum meninggal'),
('REM202212001', 'doe', 'visit tenant', 'visit tenant ', 2000000, '2022-12-06', 'Diajukan', ''),
('REM202212002', 'nfl', 'outing', 'outing bersama vendor', 5000000, '2022-12-21', 'Disetujui', 'oke gass');

--
-- Indexes for dumped tables
--

--
-- Indexes for table `cuti_trans`
--
ALTER TABLE `cuti_trans`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `department`
--
ALTER TABLE `department`
  ADD PRIMARY KEY (`kode`);

--
-- Indexes for table `gaji_logs`
--
ALTER TABLE `gaji_logs`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `jabatan`
--
ALTER TABLE `jabatan`
  ADD PRIMARY KEY (`kode_jabatan`);

--
-- Indexes for table `karyawan`
--
ALTER TABLE `karyawan`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `lembur_trans`
--
ALTER TABLE `lembur_trans`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `reimburse`
--
ALTER TABLE `reimburse`
  ADD PRIMARY KEY (`id`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `gaji_logs`
--
ALTER TABLE `gaji_logs`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=37;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
