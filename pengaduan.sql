-- phpMyAdmin SQL Dump
-- version 5.2.0
-- https://www.phpmyadmin.net/
--
-- Host: localhost:3306
-- Generation Time: Oct 15, 2026 at 11:12 AM
-- Server version: 8.0.30
-- PHP Version: 8.1.10

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `db_pengaduan`
--

-- --------------------------------------------------------

--
-- Table structure for table `pengaduan`
--

CREATE TABLE `pengaduan` (
  `id_pengaduan` varchar(10) NOT NULL,
  `nama_pelapor` varchar(100) NOT NULL,
  `jenis_pengaduan` varchar(50) NOT NULL,
  `isi_pengaduan` text NOT NULL,
  `tanggal_pengaduan` varchar(10) NOT NULL,
  `status` varchar(50) NOT NULL,
  `tingkat_urgensi` varchar(10) NOT NULL,
  `target_selesai_hari` int DEFAULT NULL,
  `kontak_darurat` varchar(15) DEFAULT NULL,
  `batas_respon_jam` int DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Dumping data for table `pengaduan`
--

INSERT INTO `pengaduan` (`id_pengaduan`, `nama_pelapor`, `jenis_pengaduan`, `isi_pengaduan`, `tanggal_pengaduan`, `status`, `tingkat_urgensi`, `target_selesai_hari`, `kontak_darurat`, `batas_respon_jam`) VALUES
('P001', 'Budi Santoso', 'Jalan', 'Jalan berlubang cukup dalam di depan gang, mengganggu pengendara motor.', '20/09/2026', 'Menunggu Konfirmasi Petugas', 'BIASA', 7, NULL, NULL),
('P002', 'Siti Aminah', 'Keamanan', 'Terjadi percikan api di panel listrik dekat pasar, berisiko kebakaran.', '21/09/2026', 'Menunggu Konfirmasi Petugas', 'DARURAT', NULL, '081234567890', 24),
('P003', 'Jeremy', 'Keamanan', 'Motor ku dicuri', '09/09/2026', 'Menunggu Konfirmasi Petugas', 'DARURAT', NULL, '081257208584', 24);

--
-- Indexes for dumped tables
--

--
-- Indexes for table `pengaduan`
--
ALTER TABLE `pengaduan`
  ADD PRIMARY KEY (`id_pengaduan`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
