-- MySQL dump 10.13  Distrib 5.7.26, for Win64 (x86_64)
--
-- Host: 127.0.0.1    Database: oyster
-- ------------------------------------------------------
-- Server version	5.7.26

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
-- Table structure for table `q_in_room`
--

DROP TABLE IF EXISTS `q_in_room`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `q_in_room` (
  `roomId` int(11) DEFAULT NULL,
  `questions` varchar(1024) DEFAULT NULL
) ENGINE=MyISAM DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `q_in_room`
--

LOCK TABLES `q_in_room` WRITE;
/*!40000 ALTER TABLE `q_in_room` DISABLE KEYS */;
INSERT INTO `q_in_room` VALUES (39,'1,4,2,3'),(40,'1,2,3,4'),(41,'2,4,3,1'),(42,'2,3,1,4'),(43,'4,2,3,1'),(44,'2,3,1,4'),(45,'3,2,4,1'),(46,'3,4,2,1'),(47,'3,4,1,2'),(48,'1,4,3,2'),(49,'2,3,4,1'),(50,'10,12,11,6,4,15,5,9,8,7');
/*!40000 ALTER TABLE `q_in_room` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `question`
--

DROP TABLE IF EXISTS `question`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `question` (
  `qid` int(11) DEFAULT NULL,
  `desp` varchar(256) DEFAULT NULL,
  `choices` varchar(255) DEFAULT NULL,
  `answer` varchar(255) DEFAULT NULL,
  `chinese` varchar(255) DEFAULT NULL,
  `action` int(11) DEFAULT NULL
) ENGINE=MyISAM DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `question`
--

LOCK TABLES `question` WRITE;
/*!40000 ALTER TABLE `question` DISABLE KEYS */;
INSERT INTO `question` VALUES (1,'b_nana','A:a,B:b,C:c,D:d','A','香蕉',0),(2,'b_nana','A:a,B:b,C:c,D:d','A','香蕉',0),(3,'b_nana','A:a,B:b,C:c,D:d','A','香蕉',0),(4,'b_nana','A:a,B:b,C:c,D:d','A','香蕉',0),(5,'b_nana','A:a,B:b,C:c,D:d','A','香蕉',0),(6,'b_nana','A:a,B:b,C:c,D:d','A','香蕉',0),(7,'b_nana','A:a,B:b,C:c,D:d','A','香蕉',0),(8,'b_nana','A:a,B:b,C:c,D:d','A','香蕉',0),(9,'b_nana','A:a,B:b,C:c,D:d','A','香蕉',0),(10,'b_nana','A:a,B:b,C:c,D:d','A','香蕉',0),(11,'b_nana','A:a,B:b,C:c,D:d','A','香蕉',0),(12,'b_nana','A:a,B:b,C:c,D:d','A','香蕉',0),(13,'b_nana','A:a,B:b,C:c,D:d','A','香蕉',0),(14,'b_nana','A:a,B:b,C:c,D:d','A','香蕉',0),(15,'b_nana','A:a,B:b,C:c,D:d','A','香蕉',0),(16,'b_nana','A:a,B:b,C:c,D:d','A','香蕉',0);
/*!40000 ALTER TABLE `question` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `room`
--

DROP TABLE IF EXISTS `room`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `room` (
  `roomId` int(11) NOT NULL,
  `hostId` int(11) NOT NULL,
  `guestId` int(11) DEFAULT '-1',
  `guestStatus` int(11) DEFAULT '0',
  `roomStatus` int(11) DEFAULT '0',
  `hostScore` int(11) DEFAULT '0',
  `guestScore` int(11) DEFAULT '0',
  `hostIndex` int(11) DEFAULT '0',
  `guestIndex` int(11) DEFAULT '0'
) ENGINE=MyISAM DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `room`
--

LOCK TABLES `room` WRITE;
/*!40000 ALTER TABLE `room` DISABLE KEYS */;
INSERT INTO `room` VALUES (43,14,12,1,-1,0,0,0,0),(44,14,12,1,0,0,0,0,0),(46,12,14,1,0,0,0,0,0),(48,12,-1,0,0,0,0,0,0),(47,12,-1,0,0,0,0,0,0),(45,12,14,1,0,0,0,0,0),(49,14,-1,0,-1,0,0,0,0),(42,12,-1,0,-1,0,0,0,0),(50,12,14,1,-3,477,478,8,10);
/*!40000 ALTER TABLE `room` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user`
--

DROP TABLE IF EXISTS `user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `user` (
  `userId` int(11) DEFAULT NULL,
  `username` varchar(48) DEFAULT NULL,
  `password` varchar(32) DEFAULT NULL,
  `score` int(11) DEFAULT NULL,
  `faceId` int(11) DEFAULT NULL,
  `anonymous` int(11) DEFAULT NULL,
  `winCnt` int(11) DEFAULT NULL
) ENGINE=MyISAM DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user`
--

LOCK TABLES `user` WRITE;
/*!40000 ALTER TABLE `user` DISABLE KEYS */;
INSERT INTO `user` VALUES (1,'匿名用户','Guest',0,5,1,NULL),(2,'匿名用户','Guest',0,4,1,NULL),(3,'匿名用户','Guest',0,17,1,NULL),(4,'匿名用户','Guest',0,1,1,NULL),(5,'匿名用户','Guest',0,15,1,NULL),(6,'匿名用户','Guest',0,1,1,NULL),(7,NULL,NULL,0,15,0,NULL),(8,NULL,NULL,0,7,0,NULL),(9,NULL,NULL,0,8,0,NULL),(10,NULL,NULL,0,12,0,NULL),(11,NULL,NULL,0,12,0,NULL),(12,'tototo','totototo',477,5,0,NULL),(13,'匿名用户','Guest',0,14,1,NULL),(14,'host','12345678',478,1,0,NULL);
/*!40000 ALTER TABLE `user` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2022-12-06 21:01:49
