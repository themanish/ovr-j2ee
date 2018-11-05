-- phpMyAdmin SQL Dump
-- version 4.2.11
-- http://www.phpmyadmin.net
--
-- Host: 127.0.0.1
-- Generation Time: Jul 19, 2015 at 08:26 AM
-- Server version: 5.6.21
-- PHP Version: 5.6.3

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;

--
-- Database: `db_ovr`
--

-- --------------------------------------------------------

--
-- Table structure for table `tbl_admin`
--

CREATE TABLE IF NOT EXISTS `tbl_admin` (
`id` int(11) NOT NULL,
  `username` varchar(25) NOT NULL,
  `password` varchar(25) NOT NULL
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=latin1;

--
-- Dumping data for table `tbl_admin`
--

INSERT INTO `tbl_admin` (`id`, `username`, `password`) VALUES
(1, 'admin', 'admin');

-- --------------------------------------------------------

--
-- Table structure for table `tbl_bookings`
--

CREATE TABLE IF NOT EXISTS `tbl_bookings` (
`id` int(11) NOT NULL,
  `start_location` varchar(255) NOT NULL,
  `end_location` varchar(255) NOT NULL,
  `start_date` date NOT NULL,
  `end_date` date NOT NULL,
  `days` int(11) NOT NULL,
  `total_fare` double(10,2) NOT NULL,
  `payment_method` varchar(25) NOT NULL,
  `additional_message` text NOT NULL,
  `review` text NOT NULL,
  `reviewed_date` date DEFAULT NULL,
  `user_id` int(11) NOT NULL,
  `vehicle_id` int(11) NOT NULL
) ENGINE=InnoDB AUTO_INCREMENT=14 DEFAULT CHARSET=latin1;

--
-- Dumping data for table `tbl_bookings`
--

-- --------------------------------------------------------

--
-- Table structure for table `tbl_districts`
--

CREATE TABLE IF NOT EXISTS `tbl_districts` (
`id` int(11) NOT NULL,
  `name` varchar(55) NOT NULL
) ENGINE=InnoDB AUTO_INCREMENT=76 DEFAULT CHARSET=latin1;

--
-- Dumping data for table `tbl_districts`
--

INSERT INTO `tbl_districts` (`id`, `name`) VALUES
(1, 'Achham'),
(2, 'Arghakhanchi'),
(3, 'Baglung'),
(4, 'Baitadi'),
(5, 'Bajhang'),
(6, 'Bajura'),
(7, 'Banke'),
(8, 'Bara'),
(9, 'Bardiya'),
(10, 'Bhaktapur'),
(11, 'Bhojpur'),
(12, 'Chitwan'),
(13, 'Dadeldhura'),
(14, 'Dailekh'),
(15, 'Dang Deukhuri'),
(16, 'Darchula'),
(17, 'Dhading'),
(18, 'Dhankuta'),
(19, 'Dhanusa'),
(20, 'Dholkha'),
(21, 'Dolpa'),
(22, 'Doti'),
(23, 'Gorkha'),
(24, 'Gulmi'),
(25, 'Humla'),
(26, 'Ilam'),
(27, 'Jajarkot'),
(28, 'Jhapa'),
(29, 'Jumla'),
(30, 'Kailali'),
(31, 'Kalikot'),
(32, 'Kanchanpur'),
(33, 'Kapilvastu'),
(34, 'Kaski'),
(35, 'Kathmandu'),
(36, 'Kavrepalanchok'),
(37, 'Khotang'),
(38, 'Lalitpur'),
(39, 'Lamjung'),
(40, 'Mahottari'),
(41, 'Makwanpur'),
(42, 'Manang'),
(43, 'Morang'),
(44, 'Mugu'),
(45, 'Mustang'),
(46, 'Myagdi'),
(47, 'Nawalparasi'),
(48, 'Nuwakot'),
(49, 'Okhaldhunga'),
(50, 'Palpa'),
(51, 'Panchthar'),
(52, 'Parbat'),
(53, 'Parsa'),
(54, 'Pyuthan'),
(55, 'Ramechhap'),
(56, 'Rasuwa'),
(57, 'Rautahat'),
(58, 'Rolpa'),
(59, 'Rukum'),
(60, 'Rupandehi'),
(61, 'Salyan'),
(62, 'Sankhuwasabha'),
(63, 'Saptari'),
(64, 'Sarlahi'),
(65, 'Sindhuli'),
(66, 'Sindhupalchok'),
(67, 'Siraha'),
(68, 'Solukhumbu'),
(69, 'Sunsari'),
(70, 'Surkhet'),
(71, 'Syangja'),
(72, 'Tanahu'),
(73, 'Taplejung'),
(74, 'Terhathum'),
(75, 'Udayapur');

-- --------------------------------------------------------

--
-- Table structure for table `tbl_owners`
--

CREATE TABLE IF NOT EXISTS `tbl_owners` (
`id` int(11) NOT NULL,
  `fname` varchar(25) NOT NULL,
  `lname` varchar(25) NOT NULL,
  `phone` varchar(25) NOT NULL,
  `email` varchar(55) NOT NULL,
  `password` varchar(25) NOT NULL,
  `profile_image` longblob NOT NULL,
  `status` tinyint(1) NOT NULL DEFAULT '1'
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=latin1;

--
-- Dumping data for table `tbl_owners`
--

-- --------------------------------------------------------

--
-- Table structure for table `tbl_users`
--

CREATE TABLE IF NOT EXISTS `tbl_users` (
`id` int(11) NOT NULL,
  `email` varchar(55) NOT NULL,
  `password` varchar(55) NOT NULL,
  `fullname` varchar(55) DEFAULT '',
  `contact_no` varchar(25) DEFAULT '',
  `address` varchar(55) DEFAULT '',
  `profile_image` longblob,
  `citizenship` longblob,
  `driving_license` longblob
) ENGINE=InnoDB AUTO_INCREMENT=13 DEFAULT CHARSET=latin1;

--
-- Dumping data for table `tbl_users`
--

-- --------------------------------------------------------

--
-- Table structure for table `tbl_vehicles`
--

CREATE TABLE IF NOT EXISTS `tbl_vehicles` (
`id` int(11) NOT NULL,
  `manufacturer` varchar(55) NOT NULL,
  `model` varchar(55) NOT NULL,
  `from_date` date NOT NULL,
  `to_date` date NOT NULL,
  `description` text NOT NULL,
  `image` longblob NOT NULL,
  `daily_fare` double(10,2) NOT NULL,
  `feature_status` enum('requested','featured','rejected','') NOT NULL DEFAULT '',
  `owner_id` int(11) NOT NULL,
  `vehicle_current_location_id` int(11) NOT NULL
) ENGINE=InnoDB AUTO_INCREMENT=25 DEFAULT CHARSET=latin1;

--
-- Dumping data for table `tbl_vehicles`
--

-- --------------------------------------------------------

--
-- Table structure for table `vehicle_available_loc`
--

CREATE TABLE IF NOT EXISTS `vehicle_available_loc` (
`id` int(11) NOT NULL,
  `vehicle_id` int(11) NOT NULL,
  `end_location_id` int(11) NOT NULL
) ENGINE=InnoDB AUTO_INCREMENT=600 DEFAULT CHARSET=latin1;

--
-- Dumping data for table `vehicle_available_loc`
--

--
-- Indexes for dumped tables
--

--
-- Indexes for table `tbl_admin`
--
ALTER TABLE `tbl_admin`
 ADD PRIMARY KEY (`id`);

--
-- Indexes for table `tbl_bookings`
--
ALTER TABLE `tbl_bookings`
 ADD PRIMARY KEY (`id`), ADD KEY `user_id` (`user_id`), ADD KEY `vehicle_id` (`vehicle_id`);

--
-- Indexes for table `tbl_districts`
--
ALTER TABLE `tbl_districts`
 ADD PRIMARY KEY (`id`), ADD UNIQUE KEY `name` (`name`);

--
-- Indexes for table `tbl_owners`
--
ALTER TABLE `tbl_owners`
 ADD PRIMARY KEY (`id`);

--
-- Indexes for table `tbl_users`
--
ALTER TABLE `tbl_users`
 ADD PRIMARY KEY (`id`), ADD UNIQUE KEY `email` (`email`);

--
-- Indexes for table `tbl_vehicles`
--
ALTER TABLE `tbl_vehicles`
 ADD PRIMARY KEY (`id`), ADD KEY `owner_id` (`owner_id`), ADD KEY `vehicle_current_location` (`vehicle_current_location_id`);

--
-- Indexes for table `vehicle_available_loc`
--
ALTER TABLE `vehicle_available_loc`
 ADD PRIMARY KEY (`id`), ADD KEY `end_location_id` (`end_location_id`), ADD KEY `vehicle_id` (`vehicle_id`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `tbl_admin`
--
ALTER TABLE `tbl_admin`
MODIFY `id` int(11) NOT NULL AUTO_INCREMENT,AUTO_INCREMENT=3;
--
-- AUTO_INCREMENT for table `tbl_bookings`
--
ALTER TABLE `tbl_bookings`
MODIFY `id` int(11) NOT NULL AUTO_INCREMENT,AUTO_INCREMENT=14;
--
-- AUTO_INCREMENT for table `tbl_districts`
--
ALTER TABLE `tbl_districts`
MODIFY `id` int(11) NOT NULL AUTO_INCREMENT,AUTO_INCREMENT=76;
--
-- AUTO_INCREMENT for table `tbl_owners`
--
ALTER TABLE `tbl_owners`
MODIFY `id` int(11) NOT NULL AUTO_INCREMENT,AUTO_INCREMENT=9;
--
-- AUTO_INCREMENT for table `tbl_users`
--
ALTER TABLE `tbl_users`
MODIFY `id` int(11) NOT NULL AUTO_INCREMENT,AUTO_INCREMENT=13;
--
-- AUTO_INCREMENT for table `tbl_vehicles`
--
ALTER TABLE `tbl_vehicles`
MODIFY `id` int(11) NOT NULL AUTO_INCREMENT,AUTO_INCREMENT=25;
--
-- AUTO_INCREMENT for table `vehicle_available_loc`
--
ALTER TABLE `vehicle_available_loc`
MODIFY `id` int(11) NOT NULL AUTO_INCREMENT,AUTO_INCREMENT=600;
--
-- Constraints for dumped tables
--

--
-- Constraints for table `tbl_bookings`
--
ALTER TABLE `tbl_bookings`
ADD CONSTRAINT `fk_bookings_user_id` FOREIGN KEY (`user_id`) REFERENCES `tbl_users` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
ADD CONSTRAINT `fk_bookings_vehicle_id` FOREIGN KEY (`vehicle_id`) REFERENCES `tbl_vehicles` (`id`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Constraints for table `tbl_vehicles`
--
ALTER TABLE `tbl_vehicles`
ADD CONSTRAINT `fk_vehicles_district_id` FOREIGN KEY (`vehicle_current_location_id`) REFERENCES `tbl_districts` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
ADD CONSTRAINT `fk_vehicles_owner_id` FOREIGN KEY (`owner_id`) REFERENCES `tbl_owners` (`id`) ON DELETE CASCADE;

--
-- Constraints for table `vehicle_available_loc`
--
ALTER TABLE `vehicle_available_loc`
ADD CONSTRAINT `fk_district_id` FOREIGN KEY (`end_location_id`) REFERENCES `tbl_districts` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
ADD CONSTRAINT `fk_vehicle_id` FOREIGN KEY (`vehicle_id`) REFERENCES `tbl_vehicles` (`id`) ON DELETE CASCADE ON UPDATE CASCADE;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
