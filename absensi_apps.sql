-- phpMyAdmin SQL Dump
-- version 4.9.0.1
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Generation Time: Nov 15, 2022 at 11:37 AM
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
('nopal2', 12, 12);

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
('CUT202211007', 'doe', 'Cuti Baptis anak (Special Leave)', 'baptis', 1, '2022-11-14', '2022-11-15', 'nfl', 'Disetujui', 'oke gas aja bro');

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
('DIN202211004', 'doe', 'mengikuti g20s pki ', 2, 500000, '2022-11-24', '2022-11-26', 'Ditolak', 'gaboleh pergi pokonya');

-- --------------------------------------------------------

--
-- Table structure for table `gaji_logs`
--

CREATE TABLE `gaji_logs` (
  `id_user` varchar(25) NOT NULL,
  `gaji` bigint(20) NOT NULL,
  `date` timestamp NOT NULL DEFAULT current_timestamp() ON UPDATE current_timestamp()
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `gaji_logs`
--

INSERT INTO `gaji_logs` (`id_user`, `gaji`, `date`) VALUES
('nopal2', 14500000, '2022-11-09 04:15:41'),
('nopal2', 14950000, '2022-11-09 04:22:36'),
('doe', 12000000, '2022-11-12 11:13:46'),
('doe', 14000000, '2022-11-12 11:37:31'),
('nfl', 12000000, '2022-11-14 06:58:56'),
('nfl', 12500000, '2022-11-14 07:08:52');

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
('nfl', '%IßxIxÃÒÍü«)´¾', 'jkt-123', 'naufal', 'GA', 'depok', 'ISLAM', '085777777777', 'sta', 12500000, 'doe'),
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
('LMB202211006', 'nfl', 3, '2022-11-14', '13:55:00', '16:55:00', 'lembur', 300000, 'Disetujui', 'oke siap boy');

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
('nfl', 'LMB202211006', 'Lembur', '2022-11-14', 300000, 3);

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
('REM202211003', 'doe', 'masih berhubungan dengan komodo', 'biaya rumah sakit digigit komodo', 2500000, '2022-11-19', 'Disetujui', 'Oke boy, semoga belum meninggal');

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
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
