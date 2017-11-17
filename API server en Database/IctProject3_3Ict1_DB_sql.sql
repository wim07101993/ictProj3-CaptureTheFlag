-- --------------------------------------------------------
-- Host:                         localhost
-- Server versie:                10.1.28-MariaDB - mariadb.org binary distribution
-- Server OS:                    Win32
-- HeidiSQL Versie:              9.4.0.5125
-- --------------------------------------------------------

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET NAMES utf8 */;
/*!50503 SET NAMES utf8mb4 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;


-- Databasestructuur van ictproject3_3ict1 wordt geschreven
CREATE DATABASE IF NOT EXISTS `ictproject3_3ict1` /*!40100 DEFAULT CHARACTER SET latin1 */;
USE `ictproject3_3ict1`;

-- Structuur van  tabel ictproject3_3ict1.answer wordt geschreven
CREATE TABLE IF NOT EXISTS `answer` (
  `Answer_ID` int(11) NOT NULL AUTO_INCREMENT,
  `Answer` varchar(50) NOT NULL,
  `Question_ID` int(11) NOT NULL,
  `Correct` tinyint(4) NOT NULL,
  PRIMARY KEY (`Answer_ID`),
  KEY `Question_ID` (`Question_ID`),
  CONSTRAINT `Question_ID_Answer` FOREIGN KEY (`Question_ID`) REFERENCES `question` (`Question_ID`)
) ENGINE=InnoDB AUTO_INCREMENT=13 DEFAULT CHARSET=latin1;

-- Dumpen data van tabel ictproject3_3ict1.answer: ~11 rows (ongeveer)
/*!40000 ALTER TABLE `answer` DISABLE KEYS */;
REPLACE INTO `answer` (`Answer_ID`, `Answer`, `Question_ID`, `Correct`) VALUES
	(1, 'Angst voor Sinterklaas.', 1, 0),
	(2, 'Angst voor kleine ruimtes.', 1, 1),
	(3, 'Angst voor grote groepen mensen', 1, 0),
	(5, 'George W. Bush', 2, 0),
	(6, 'Barack Obama', 2, 0),
	(7, 'Donald Trump', 2, 1),
	(8, '144', 3, 1),
	(9, '132', 3, 0),
	(10, '120', 3, 0),
	(11, '156', 3, 0),
	(12, '168', 3, 0);
/*!40000 ALTER TABLE `answer` ENABLE KEYS */;

-- Structuur van  tabel ictproject3_3ict1.category wordt geschreven
CREATE TABLE IF NOT EXISTS `category` (
  `Category_ID` int(11) NOT NULL AUTO_INCREMENT,
  `Name` varchar(50) NOT NULL,
  PRIMARY KEY (`Category_ID`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=latin1;

-- Dumpen data van tabel ictproject3_3ict1.category: ~3 rows (ongeveer)
/*!40000 ALTER TABLE `category` DISABLE KEYS */;
REPLACE INTO `category` (`Category_ID`, `Name`) VALUES
	(1, 'Alle'),
	(2, 'psychology'),
	(3, 'wiskunde');
/*!40000 ALTER TABLE `category` ENABLE KEYS */;

-- Structuur van  tabel ictproject3_3ict1.question wordt geschreven
CREATE TABLE IF NOT EXISTS `question` (
  `Question_ID` int(11) NOT NULL AUTO_INCREMENT,
  `Question` varchar(50) NOT NULL,
  PRIMARY KEY (`Question_ID`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=latin1;

-- Dumpen data van tabel ictproject3_3ict1.question: ~3 rows (ongeveer)
/*!40000 ALTER TABLE `question` DISABLE KEYS */;
REPLACE INTO `question` (`Question_ID`, `Question`) VALUES
	(1, 'Wat is Claustrofobie?'),
	(2, 'Wie is de 45ste president van de verenigde staten.'),
	(3, 'wat is 12 x 12');
/*!40000 ALTER TABLE `question` ENABLE KEYS */;

-- Structuur van  tabel ictproject3_3ict1.question_per_category wordt geschreven
CREATE TABLE IF NOT EXISTS `question_per_category` (
  `Question_Per_Category_ID` int(11) NOT NULL AUTO_INCREMENT,
  `Question_ID` int(11),
  `Category_ID` int(11),
  PRIMARY KEY (`Question_Per_Category_ID`),
  KEY `Kolom 2` (`Question_ID`),
  KEY `Kolom 3` (`Category_ID`),
  CONSTRAINT `Category_ID` FOREIGN KEY (`Category_ID`) REFERENCES `category` (`Category_ID`),
  CONSTRAINT `Question_ID_Category` FOREIGN KEY (`Question_ID`) REFERENCES `question` (`Question_ID`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=latin1;

-- Dumpen data van tabel ictproject3_3ict1.question_per_category: ~5 rows (ongeveer)
/*!40000 ALTER TABLE `question_per_category` DISABLE KEYS */;
REPLACE INTO `question_per_category` (`Question_Per_Category_ID`, `Question_ID`, `Category_ID`) VALUES
	(1, 1, 1),
	(2, 1, 2),
	(3, 2, 1),
	(5, 3, 1),
	(6, 3, 3);
/*!40000 ALTER TABLE `question_per_category` ENABLE KEYS */;

/*!40101 SET SQL_MODE=IFNULL(@OLD_SQL_MODE, '') */;
/*!40014 SET FOREIGN_KEY_CHECKS=IF(@OLD_FOREIGN_KEY_CHECKS IS NULL, 1, @OLD_FOREIGN_KEY_CHECKS) */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
