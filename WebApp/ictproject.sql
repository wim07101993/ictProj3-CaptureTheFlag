-- phpMyAdmin SQL Dump
-- version 4.7.4
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Generation Time: Dec 14, 2017 at 12:24 PM
-- Server version: 10.1.28-MariaDB
-- PHP Version: 7.1.10

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET AUTOCOMMIT = 0;
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `ictproject`
--
CREATE DATABASE IF NOT EXISTS `ictproject` DEFAULT CHARACTER SET latin1 COLLATE latin1_swedish_ci;
USE `ictproject`;

-- --------------------------------------------------------

--
-- Table structure for table `answer`
--

CREATE TABLE `answer` (
  `Answer_ID` int(11) NOT NULL,
  `Answer` varchar(50) NOT NULL,
  `Question_ID` int(11) NOT NULL,
  `Correct` tinyint(4) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `answer`
--

INSERT INTO `answer` (`Answer_ID`, `Answer`, `Question_ID`, `Correct`) VALUES
(18, 'De relativiteitstheorie', 14, 0),
(19, 'Het foto-elektrisch effect', 14, 1),
(20, 'Het bestaan van moleculen', 14, 0),
(21, '6', 15, 0),
(25, '9', 15, 1),
(26, 'Vierkantswortel 81', 15, 1),
(27, 'Brussel', 16, 0),
(28, 'Johannesburg', 16, 0),
(30, 'Praag', 16, 0),
(31, 'Heusden-Zolder', 16, 0),
(32, 'Japan', 17, 0),
(33, 'Duitsland', 17, 1),
(34, 'Nederland', 17, 0),
(35, 'Brazilië', 17, 0),
(36, 'Kwijlen', 18, 1),
(37, 'Eten', 18, 0),
(38, 'Poot geven', 18, 0),
(39, 'Een backflip doen', 18, 0),
(40, 'Omrollen', 18, 0),
(41, 'Dood', 19, 1),
(42, 'Levend', 19, 1),
(43, 'Beide', 19, 1),
(44, 'Zombie', 19, 0),
(45, 'God is dood', 20, 1),
(46, 'God leeft', 20, 0),
(47, 'God is wel ne chille kerel', 20, 0);

-- --------------------------------------------------------

--
-- Table structure for table `category`
--

CREATE TABLE `category` (
  `Category_ID` int(11) NOT NULL,
  `Name` varchar(50) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `category`
--

INSERT INTO `category` (`Category_ID`, `Name`) VALUES
(1, 'Alle'),
(19, 'Wiskunde'),
(20, 'Aardrijkskunde'),
(21, 'Fysica'),
(22, 'Psychologie'),
(23, 'Theologie');

-- --------------------------------------------------------

--
-- Table structure for table `migrations`
--

CREATE TABLE `migrations` (
  `id` int(10) UNSIGNED NOT NULL,
  `migration` varchar(255) COLLATE utf8mb4_unicode_ci NOT NULL,
  `batch` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- --------------------------------------------------------

--
-- Table structure for table `question`
--

CREATE TABLE `question` (
  `Question_ID` int(11) NOT NULL,
  `Question` varchar(200) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `question`
--

INSERT INTO `question` (`Question_ID`, `Question`) VALUES
(14, 'Voor welke theorie heeft Einstein de nobelprijs gekregen?'),
(15, 'Wat is de uitkomst van 3² ?'),
(16, 'Wat is de hoofstad van Hongarije?'),
(17, 'Welk land is een buurland van Polen?'),
(18, 'Wat deden de honden van Pavlov als de bel rinkelde?'),
(19, 'Is Schrödinger\'s kat dood of levend?'),
(20, 'Wat zei Nietzsche over God?'),
(21, 'Bestaat God?');

-- --------------------------------------------------------

--
-- Table structure for table `question_per_category`
--

CREATE TABLE `question_per_category` (
  `Question_Per_Category_ID` int(11) NOT NULL,
  `Question_ID` int(11) DEFAULT NULL,
  `Category_ID` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `question_per_category`
--

INSERT INTO `question_per_category` (`Question_Per_Category_ID`, `Question_ID`, `Category_ID`) VALUES
(24, 14, 1),
(25, 14, 21),
(26, 15, 1),
(27, 15, 19),
(28, 16, 1),
(29, 16, 20),
(30, 17, 1),
(31, 17, 20),
(32, 18, 1),
(33, 18, 22),
(34, 19, 1),
(35, 19, 21),
(36, 20, 1),
(37, 20, 23),
(38, 21, 1),
(39, 21, 23);

-- --------------------------------------------------------

--
-- Table structure for table `users`
--

CREATE TABLE `users` (
  `id` int(10) UNSIGNED NOT NULL,
  `name` varchar(255) COLLATE utf8mb4_unicode_ci NOT NULL,
  `email` varchar(255) COLLATE utf8mb4_unicode_ci NOT NULL,
  `password` varchar(255) COLLATE utf8mb4_unicode_ci NOT NULL,
  `remember_token` varchar(100) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `created_at` timestamp NULL DEFAULT NULL,
  `updated_at` timestamp NULL DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

--
-- Dumping data for table `users`
--

INSERT INTO `users` (`id`, `name`, `email`, `password`, `remember_token`, `created_at`, `updated_at`) VALUES
(1, 'Michiel', 'michiel.vanbergen@hotmail.com', '$2y$10$SDR13aSbBEZI2CjBq8y7s.nHJCXsCsNziFTusUhkAkMw07k6yfAF.', 'aHybMVCaDmB9ut6qwvK6ABf4tN6v66vlhM22q2KkmbHFGqhe3uxsIF9yJiil', '2017-11-23 11:00:48', '2017-11-23 11:00:48');

--
-- Indexes for dumped tables
--

--
-- Indexes for table `answer`
--
ALTER TABLE `answer`
  ADD PRIMARY KEY (`Answer_ID`),
  ADD KEY `Question_ID` (`Question_ID`);

--
-- Indexes for table `category`
--
ALTER TABLE `category`
  ADD PRIMARY KEY (`Category_ID`);

--
-- Indexes for table `migrations`
--
ALTER TABLE `migrations`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `question`
--
ALTER TABLE `question`
  ADD PRIMARY KEY (`Question_ID`);

--
-- Indexes for table `question_per_category`
--
ALTER TABLE `question_per_category`
  ADD PRIMARY KEY (`Question_Per_Category_ID`),
  ADD KEY `Kolom 2` (`Question_ID`),
  ADD KEY `Kolom 3` (`Category_ID`);

--
-- Indexes for table `users`
--
ALTER TABLE `users`
  ADD PRIMARY KEY (`id`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `answer`
--
ALTER TABLE `answer`
  MODIFY `Answer_ID` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=51;

--
-- AUTO_INCREMENT for table `category`
--
ALTER TABLE `category`
  MODIFY `Category_ID` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=24;

--
-- AUTO_INCREMENT for table `migrations`
--
ALTER TABLE `migrations`
  MODIFY `id` int(10) UNSIGNED NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT for table `question`
--
ALTER TABLE `question`
  MODIFY `Question_ID` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=22;

--
-- AUTO_INCREMENT for table `question_per_category`
--
ALTER TABLE `question_per_category`
  MODIFY `Question_Per_Category_ID` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=40;

--
-- AUTO_INCREMENT for table `users`
--
ALTER TABLE `users`
  MODIFY `id` int(10) UNSIGNED NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=2;

--
-- Constraints for dumped tables
--

--
-- Constraints for table `answer`
--
ALTER TABLE `answer`
  ADD CONSTRAINT `Question_ID` FOREIGN KEY (`Question_ID`) REFERENCES `question` (`Question_ID`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Constraints for table `question_per_category`
--
ALTER TABLE `question_per_category`
  ADD CONSTRAINT `Category_ID` FOREIGN KEY (`Category_ID`) REFERENCES `category` (`Category_ID`) ON DELETE CASCADE,
  ADD CONSTRAINT `Question_ID_Category` FOREIGN KEY (`Question_ID`) REFERENCES `question` (`Question_ID`) ON DELETE CASCADE;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
