-- phpMyAdmin SQL Dump
-- version 4.1.14
-- http://www.phpmyadmin.net
--
-- Host: 127.0.0.1
-- Generation Time: Dec 05, 2015 at 06:44 AM
-- Server version: 5.6.17
-- PHP Version: 5.5.12

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;

--
-- Database: `icarus`
--

-- --------------------------------------------------------

--
-- Table structure for table `navigator_tabs`
--

CREATE TABLE IF NOT EXISTS `navigator_tabs` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `child_id` int(11) NOT NULL DEFAULT '-1',
  `tab_name` varchar(50) NOT NULL,
  `title` varchar(50) NOT NULL DEFAULT '',
  `button_setting` int(11) NOT NULL DEFAULT '1',
  `closed` tinyint(1) NOT NULL DEFAULT '1',
  `thumbnail` tinyint(1) NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=14 ;

--
-- Dumping data for table `navigator_tabs`
--

INSERT INTO `navigator_tabs` (`id`, `child_id`, `tab_name`, `title`, `button_setting`, `closed`, `thumbnail`) VALUES
(1, -1, 'official_view', '', 1, 1, 0),
(2, -1, 'hotel_view', '', 1, 1, 0),
(3, -1, 'roomads_view', '', 1, 1, 0),
(4, -1, 'myworld_view', '', 1, 1, 0),
(5, 1, 'official_view', '', 0, 0, 1),
(6, 1, 'staffpicks', '', 0, 1, 0),
(7, 2, 'popular', '', 0, 0, 0),
(8, 3, 'roomads_view', '', 0, 0, 0),
(9, 4, 'my', '', 1, 0, 0),
(10, 4, 'favorites', '', 1, 0, 0),
(11, 4, 'my_groups', '', 1, 1, 0),
(12, 4, 'history', '', 1, 1, 0),
(13, 4, 'friends_rooms', '', 1, 1, 0);

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
