-- --------------------------------------------------------
-- 主機:                           127.0.0.1
-- 伺服器版本:                        11.2.0-MariaDB - mariadb.org binary distribution
-- 伺服器作業系統:                      Win64
-- HeidiSQL 版本:                  12.3.0.6589
-- --------------------------------------------------------

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET NAMES utf8 */;
/*!50503 SET NAMES utf8mb4 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;


-- 傾印 demo 的資料庫結構
CREATE DATABASE IF NOT EXISTS `demo` /*!40100 DEFAULT CHARACTER SET latin1 COLLATE latin1_swedish_ci */;
USE `demo`;

-- 傾印  資料表 demo.blacklist 結構
CREATE TABLE IF NOT EXISTS `blacklist` (
  `account` varchar(40) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1 COLLATE=latin1_swedish_ci;

-- 正在傾印表格  demo.blacklist 的資料：~0 rows (近似值)

-- 傾印  資料表 demo.books 結構
CREATE TABLE IF NOT EXISTS `books` (
  `bookId` int(11) NOT NULL AUTO_INCREMENT,
  `bookName` varchar(40) NOT NULL,
  `author` varchar(40) NOT NULL,
  `summary` varchar(40) NOT NULL,
  `pricing` int(11) NOT NULL,
  `sellingPrice` int(11) NOT NULL,
  PRIMARY KEY (`bookId`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=latin1 COLLATE=latin1_swedish_ci;

-- 正在傾印表格  demo.books 的資料：~3 rows (近似值)
INSERT INTO `books` (`bookId`, `bookName`, `author`, `summary`, `pricing`, `sellingPrice`) VALUES
	(1, 'halyn', 'halyn', 'halyn', 350, 300),
	(2, 'cheyenne', 'cheyenne', 'cheyenne', 450, 500),
	(7, 'aaa', 'aaa', 'aaa', 250, 200);

-- 傾印  資料表 demo.users 結構
CREATE TABLE IF NOT EXISTS `users` (
  `userId` int(11) NOT NULL AUTO_INCREMENT,
  `userAccount` varchar(40) NOT NULL,
  `userPassword` varchar(40) NOT NULL,
  `userName` varchar(40) NOT NULL,
  `userPhone` int(11) NOT NULL,
  `userEmail` varchar(40) NOT NULL,
  `role` int(11) NOT NULL,
  PRIMARY KEY (`userId`)
) ENGINE=InnoDB AUTO_INCREMENT=15 DEFAULT CHARSET=latin1 COLLATE=latin1_swedish_ci;

-- 正在傾印表格  demo.users 的資料：~9 rows (近似值)
INSERT INTO `users` (`userId`, `userAccount`, `userPassword`, `userName`, `userPhone`, `userEmail`, `role`) VALUES
	(2, 'sss', 'ss123123', 'sss', 987654321, 'sss@example.com', 0),
	(3, 'hhh', '8888', 'hhh', 999999999, 'hhh@example.com', 1),
	(4, 'ggg', '9876', 'ggg', 987987987, 'ggg@mail.com', 0),
	(7, '000', '0000', 'string', 963963963, 'string', 1),
	(9, 'string', 'string', 'string', 951951951, 'string', 0),
	(10, 'ttt', '1111', 'ttt', 952952952, 'ttt@example.com', 1),
	(11, 'bbb', 'aaaa', 'bbb', 941941941, 'bbb', 0),
	(13, 'qqq', 'qqqq', 'qqq', 943943943, 'qqq', 1),
	(14, 'halyn', '1234', 'halyn', 984621305, 'halyn@mail.com', 0);

/*!40103 SET TIME_ZONE=IFNULL(@OLD_TIME_ZONE, 'system') */;
/*!40101 SET SQL_MODE=IFNULL(@OLD_SQL_MODE, '') */;
/*!40014 SET FOREIGN_KEY_CHECKS=IFNULL(@OLD_FOREIGN_KEY_CHECKS, 1) */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40111 SET SQL_NOTES=IFNULL(@OLD_SQL_NOTES, 1) */;
