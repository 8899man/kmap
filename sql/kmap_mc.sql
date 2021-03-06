CREATE DATABASE  IF NOT EXISTS `kmap` /*!40100 DEFAULT CHARACTER SET utf8 */;
USE `kmap`;
-- MySQL dump 10.13  Distrib 5.7.9, for osx10.9 (x86_64)
--
-- Host: 127.0.0.1    Database: kmap
-- ------------------------------------------------------
-- Server version	5.7.9

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `mc`
--

DROP TABLE IF EXISTS `mc`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `mc` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `major` int(10) unsigned DEFAULT NULL,
  `course` int(10) unsigned DEFAULT NULL,
  `credit` double DEFAULT NULL COMMENT '学分',
  PRIMARY KEY (`id`),
  KEY `major` (`major`),
  KEY `course` (`course`),
  CONSTRAINT `mc_ibfk_1` FOREIGN KEY (`major`) REFERENCES `major` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `mc_ibfk_2` FOREIGN KEY (`course`) REFERENCES `course` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=74 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `mc`
--

LOCK TABLES `mc` WRITE;
/*!40000 ALTER TABLE `mc` DISABLE KEYS */;
INSERT INTO `mc` VALUES (1,2,1,6),(2,2,2,5),(4,2,4,2),(5,2,5,4),(6,2,6,1),(7,2,7,0),(8,2,8,3),(9,2,9,2),(10,2,10,4),(11,2,11,3),(12,2,12,4),(13,2,13,3),(14,2,14,2),(15,2,15,2),(16,2,16,3),(17,2,17,4),(18,2,18,3.5),(19,2,19,3),(20,2,20,4),(21,2,21,3),(22,2,22,1),(23,2,23,3),(24,2,24,3),(25,2,25,2.5),(26,2,26,4),(27,2,27,3),(28,2,28,2),(29,2,29,3),(30,2,30,4.5),(31,2,31,3.5),(32,2,32,2),(33,2,33,3),(34,2,34,3),(35,2,35,3),(36,2,36,1),(37,2,37,1),(38,2,38,2),(39,2,39,2),(40,2,40,4),(41,2,41,3),(42,2,42,4),(43,2,43,1),(44,2,44,2),(45,2,45,1),(46,2,46,12),(47,2,1,6),(48,2,2,5),(49,9,1,6),(50,9,2,5),(51,10,47,3),(52,10,48,3),(54,10,50,4),(55,10,53,4),(56,10,51,4),(57,10,52,4),(58,10,54,4.5),(59,10,55,3),(60,10,56,4),(61,10,57,3),(62,10,58,3),(63,10,59,3),(64,11,61,4),(66,11,62,4.5),(67,11,63,3),(68,11,64,4),(69,11,65,4),(71,11,66,3),(73,11,60,3);
/*!40000 ALTER TABLE `mc` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2016-03-17 11:23:58
