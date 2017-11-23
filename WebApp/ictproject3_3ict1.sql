-- phpMyAdmin SQL Dump
-- version 4.7.4
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Gegenereerd op: 23 nov 2017 om 00:57
-- Serverversie: 10.1.28-MariaDB
-- PHP-versie: 7.1.11

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET AUTOCOMMIT = 0;
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `ictproject3_3ict1`
--

-- --------------------------------------------------------

--
-- Tabelstructuur voor tabel `answer`
--

CREATE TABLE `answer` (
  `Answer_ID` int(11) NOT NULL,
  `Answer` varchar(50) NOT NULL,
  `Question_ID` int(11) NOT NULL,
  `Correct` tinyint(4) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Gegevens worden geëxporteerd voor tabel `answer`
--

INSERT INTO `answer` (`Answer_ID`, `Answer`, `Question_ID`, `Correct`) VALUES
(1, 'Angst voor Sinterklaas', 1, 0),
(2, 'Angst voor kleine ruimtes.', 1, 1),
(4, 'Ja', 2, 1),
(5, 'Neen.', 2, 0);

-- --------------------------------------------------------

--
-- Tabelstructuur voor tabel `category`
--

CREATE TABLE `category` (
  `Category_ID` int(11) NOT NULL,
  `Name` varchar(50) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Gegevens worden geëxporteerd voor tabel `category`
--

INSERT INTO `category` (`Category_ID`, `Name`) VALUES
(1, 'Alle'),
(2, 'psychology'),
(3, 'wiskunde');

-- --------------------------------------------------------

--
-- Tabelstructuur voor tabel `question`
--

CREATE TABLE `question` (
  `Question_ID` int(11) NOT NULL,
  `Question` varchar(50) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Gegevens worden geëxporteerd voor tabel `question`
--

INSERT INTO `question` (`Question_ID`, `Question`) VALUES
(1, 'Wat is Claustrofobie?'),
(2, 'Is dit een Test?');

-- --------------------------------------------------------

--
-- Tabelstructuur voor tabel `question_per_category`
--

CREATE TABLE `question_per_category` (
  `Question_Per_Category_ID` int(11) NOT NULL,
  `Question_ID` int(11),
  `Category_ID` int(11)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Gegevens worden geëxporteerd voor tabel `question_per_category`
--

INSERT INTO `question_per_category` (`Question_Per_Category_ID`, `Question_ID`, `Category_ID`) VALUES
(1, 1, 1),
(2, 1, 2);

--
-- Indexen voor geëxporteerde tabellen
--

--
-- Indexen voor tabel `answer`
--
ALTER TABLE `answer`
  ADD PRIMARY KEY (`Answer_ID`),
  ADD KEY `Question_ID` (`Question_ID`);

--
-- Indexen voor tabel `category`
--
ALTER TABLE `category`
  ADD PRIMARY KEY (`Category_ID`);

--
-- Indexen voor tabel `question`
--
ALTER TABLE `question`
  ADD PRIMARY KEY (`Question_ID`);

--
-- Indexen voor tabel `question_per_category`
--
ALTER TABLE `question_per_category`
  ADD PRIMARY KEY (`Question_Per_Category_ID`),
  ADD KEY `Kolom 2` (`Question_ID`),
  ADD KEY `Kolom 3` (`Category_ID`);

--
-- AUTO_INCREMENT voor geëxporteerde tabellen
--

--
-- AUTO_INCREMENT voor een tabel `answer`
--
ALTER TABLE `answer`
  MODIFY `Answer_ID` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=6;

--
-- AUTO_INCREMENT voor een tabel `category`
--
ALTER TABLE `category`
  MODIFY `Category_ID` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=4;

--
-- AUTO_INCREMENT voor een tabel `question`
--
ALTER TABLE `question`
  MODIFY `Question_ID` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=3;

--
-- AUTO_INCREMENT voor een tabel `question_per_category`
--
ALTER TABLE `question_per_category`
  MODIFY `Question_Per_Category_ID` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=3;

--
-- Beperkingen voor geëxporteerde tabellen
--

--
-- Beperkingen voor tabel `answer`
--
ALTER TABLE `answer`
  ADD CONSTRAINT `Question_ID` FOREIGN KEY (`Question_ID`) REFERENCES `question` (`Question_ID`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Beperkingen voor tabel `question_per_category`
--
ALTER TABLE `question_per_category`
  ADD CONSTRAINT `Category_ID` FOREIGN KEY (`Category_ID`) REFERENCES `category` (`Category_ID`),
  ADD CONSTRAINT `Question_ID_Category` FOREIGN KEY (`Question_ID`) REFERENCES `question` (`Question_ID`) ON DELETE CASCADE;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
