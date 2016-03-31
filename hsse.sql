-- phpMyAdmin SQL Dump
-- version 4.0.10.7
-- http://www.phpmyadmin.net
--
-- Host: localhost:3306
-- Generation Time: Mar 31, 2016 at 01:13 PM
-- Server version: 5.5.48-cll
-- PHP Version: 5.4.31

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;

--
-- Database: `tomt4765_hsse`
--

-- --------------------------------------------------------

--
-- Table structure for table `batch_area`
--

CREATE TABLE IF NOT EXISTS `batch_area` (
  `batch` varchar(10) COLLATE utf8_unicode_ci NOT NULL DEFAULT '',
  `location` varchar(50) COLLATE utf8_unicode_ci NOT NULL,
  `no_telp` varchar(30) COLLATE utf8_unicode_ci NOT NULL,
  PRIMARY KEY (`batch`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

-- --------------------------------------------------------

--
-- Table structure for table `incident`
--

CREATE TABLE IF NOT EXISTS `incident` (
  `batch` varchar(10) COLLATE utf8_unicode_ci NOT NULL,
  `tgl` date NOT NULL,
  `problem` text COLLATE utf8_unicode_ci NOT NULL,
  `uraian` text COLLATE utf8_unicode_ci NOT NULL,
  `tindakan` text COLLATE utf8_unicode_ci NOT NULL,
  `day` int(11) NOT NULL,
  `nik` varchar(20) COLLATE utf8_unicode_ci NOT NULL
) ENGINE=MyISAM DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

-- --------------------------------------------------------

--
-- Table structure for table `karyawan`
--

CREATE TABLE IF NOT EXISTS `karyawan` (
  `nik` varchar(20) NOT NULL,
  `nama` varchar(35) NOT NULL,
  `tmp_lhr` varchar(35) NOT NULL,
  `tgl_lhr` date NOT NULL,
  `jenkel` varchar(15) NOT NULL,
  `agama` varchar(15) NOT NULL,
  `status` varchar(15) NOT NULL,
  `alamat` varchar(80) NOT NULL,
  `alamat_now` varchar(50) NOT NULL,
  `nohp` varchar(15) NOT NULL,
  `phone` varchar(15) NOT NULL,
  `pendidikan` varchar(20) NOT NULL,
  `jabatan` varchar(15) NOT NULL,
  `tgl_masuk` date NOT NULL,
  `code` varchar(55) NOT NULL,
  `profile_images` varchar(55) NOT NULL,
  `nama_darurat` varchar(35) NOT NULL,
  `no_darurat` varchar(15) NOT NULL,
  `hubungan` varchar(25) NOT NULL,
  `batch` varchar(10) NOT NULL,
  PRIMARY KEY (`nik`),
  KEY `nik` (`nik`)
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Table structure for table `karyawan_training`
--

CREATE TABLE IF NOT EXISTS `karyawan_training` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `nik` varchar(20) COLLATE utf8_unicode_ci NOT NULL,
  `kode` varchar(10) COLLATE utf8_unicode_ci NOT NULL,
  `nilai` int(11) NOT NULL,
  `tgl` date NOT NULL,
  `trainer` varchar(20) COLLATE utf8_unicode_ci NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM  DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci AUTO_INCREMENT=327 ;

-- --------------------------------------------------------

--
-- Table structure for table `login`
--

CREATE TABLE IF NOT EXISTS `login` (
  `level` varchar(10) NOT NULL,
  `username` varchar(15) NOT NULL,
  `password` varchar(15) NOT NULL,
  PRIMARY KEY (`username`)
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Table structure for table `telp_team`
--

CREATE TABLE IF NOT EXISTS `telp_team` (
  `id` varchar(5) COLLATE utf8_unicode_ci NOT NULL,
  `nama` varchar(35) COLLATE utf8_unicode_ci NOT NULL,
  `phone` varchar(15) COLLATE utf8_unicode_ci NOT NULL,
  `hp` varchar(15) COLLATE utf8_unicode_ci NOT NULL,
  `keterangan` varchar(50) COLLATE utf8_unicode_ci NOT NULL,
  `batch` varchar(10) COLLATE utf8_unicode_ci NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

-- --------------------------------------------------------

--
-- Table structure for table `training_license`
--

CREATE TABLE IF NOT EXISTS `training_license` (
  `kode` varchar(10) COLLATE utf8_unicode_ci NOT NULL,
  `training` text COLLATE utf8_unicode_ci NOT NULL,
  `level` varchar(5) COLLATE utf8_unicode_ci NOT NULL,
  `trainer` varchar(30) COLLATE utf8_unicode_ci NOT NULL,
  PRIMARY KEY (`kode`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
