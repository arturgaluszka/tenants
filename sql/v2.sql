CREATE DATABASE  IF NOT EXISTS `android` /*!40100 DEFAULT CHARACTER SET utf8 */;
USE `android`;
-- MySQL dump 10.13  Distrib 5.6.24, for Win64 (x86_64)
--
-- Host: localhost    Database: android
-- ------------------------------------------------------
-- Server version	5.7.9-log

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
-- Table structure for table `archive`
--

DROP TABLE IF EXISTS `archive`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `archive` (
  `id` int(11) NOT NULL,
  `done` int(11) DEFAULT NULL,
  `price` decimal(12,0) DEFAULT NULL,
  `description` varchar(45) DEFAULT NULL,
  `flat` int(11) DEFAULT NULL,
  `user` int(11) DEFAULT NULL,
  `creator` int(11) DEFAULT NULL,
  `modificationDate` bigint(12) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `archive`
--

LOCK TABLES `archive` WRITE;
/*!40000 ALTER TABLE `archive` DISABLE KEYS */;
INSERT INTO `archive` VALUES (21,1,3,'Cola',1,2,2,1461742318600),(22,1,4,'Nasiona',1,2,3,1461742318600),(23,1,5,'Szklanka',1,2,4,1461742318600),(24,1,3,'Maslo',1,2,5,1461742318600),(25,1,4,'Ketchup',1,2,2,1461742318600),(26,1,12,'Kawa',1,2,3,1461742318600),(27,1,19,'Herbata',1,2,4,1461742318600),(28,1,15,'Mieso',1,2,5,1461742318600),(29,1,9,'Syrop',1,2,2,1461742318600),(30,1,2,'Pieprz',1,2,3,1461742318600),(31,1,8,'Baterie',1,2,4,1461742318600),(32,1,7,'Lody',1,2,5,1461742318600),(33,1,30,'Wegiel',2,2,2,1461742318600),(34,1,50,'Maka',2,2,6,1461742318600),(35,1,34,'E666',2,2,7,1461742318600),(36,1,33,'E667',2,2,2,1461742318600),(37,1,5,'Sezam',2,2,6,1461742318600),(38,1,22,'Przepis',2,2,7,1461742318600),(39,1,6,'Pepsi',1,3,4,1461742318600),(40,1,4,'Worki',1,4,5,1461742318600),(41,1,7,'Dlugopis',1,5,2,1461742318600),(42,1,12,'Kartki',1,3,5,1461742318600),(43,1,4,'Gabki',1,4,3,1461742318600),(44,1,3,'Serek',1,5,4,1461742318600),(45,1,50,'Maka',2,6,2,1461742318600),(46,1,44,'Noz',2,7,2,1461742318600),(47,1,6,'Tacka',2,6,7,1461742318600),(48,1,3,'Chleb',2,7,6,1461742318600);
/*!40000 ALTER TABLE `archive` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `flats`
--

DROP TABLE IF EXISTS `flats`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `flats` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `password` varchar(45) DEFAULT NULL,
  `name` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `flats`
--

LOCK TABLES `flats` WRITE;
/*!40000 ALTER TABLE `flats` DISABLE KEYS */;
INSERT INTO `flats` VALUES (1,'388755dff29377f3288247a1f943bddd1aa5970b','kobierzynska'),(2,'d09e0ccb191adcf639b3b74280edf157b545952a','piekarnia');
/*!40000 ALTER TABLE `flats` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `flatsigns`
--

DROP TABLE IF EXISTS `flatsigns`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `flatsigns` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `userID` int(11) DEFAULT NULL,
  `flatID` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=49 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `flatsigns`
--

LOCK TABLES `flatsigns` WRITE;
/*!40000 ALTER TABLE `flatsigns` DISABLE KEYS */;
INSERT INTO `flatsigns` VALUES (1,2,1),(2,3,1),(3,4,1),(4,5,1),(5,2,2),(6,6,2),(7,7,2);
/*!40000 ALTER TABLE `flatsigns` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `products`
--

DROP TABLE IF EXISTS `products`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `products` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `done` int(11) DEFAULT NULL,
  `price` decimal(12,0) DEFAULT NULL,
  `description` varchar(45) DEFAULT NULL,
  `flat` int(11) DEFAULT NULL,
  `user` int(11) DEFAULT NULL,
  `creator` int(11) DEFAULT NULL,
  `modificationDate` bigint(12) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=21 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `products`
--

LOCK TABLES `products` WRITE;
/*!40000 ALTER TABLE `products` DISABLE KEYS */;
INSERT INTO `products` VALUES (1,0,0,'Mleko',1,0,2,1461742318555),(2,0,0,'Papier',1,2,3,1461742318555),(3,0,0,'Szynka',1,0,4,1461742318555),(4,0,0,'Plyn',1,0,5,1461742318555),(5,0,0,'Jajka',1,2,2,1461742318555),(6,0,0,'Orzechy',1,2,3,1461742318555),(7,0,0,'Jablka',1,0,4,1461742318555),(8,0,0,'Gruszki',1,0,5,1461742318555),(9,0,0,'Talerz',1,0,2,1461742318555),(10,0,0,'Widelec',1,2,3,1461742318555),(11,0,0,'Czekolada',1,0,4,1461742318555),(12,0,0,'Kwiatek',1,0,5,1461742318555),(13,0,0,'Lody',1,0,2,1461742318555),(14,0,0,'Proszek',1,0,3,1461742318555),(15,0,0,'Maka',2,2,2,1461742318555),(16,0,0,'Drozdze',2,2,2,1461742318555),(17,0,0,'Woda',2,0,2,1461742318555),(18,0,0,'Sol',2,2,2,1461742318555),(19,0,0,'Cukier',2,0,6,1461742318555),(20,0,0,'Worki',2,0,7,1461742318555);
/*!40000 ALTER TABLE `products` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `stats`
--

DROP TABLE IF EXISTS `stats`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `stats` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `userID` int(11) DEFAULT '0',
  `sum` decimal(12,0) DEFAULT '0',
  `flatID` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `stats`
--

LOCK TABLES `stats` WRITE;
/*!40000 ALTER TABLE `stats` DISABLE KEYS */;
INSERT INTO `stats` VALUES (1,2,91,1),(2,3,18,1),(3,4,8,1),(4,5,10,1),(5,6,56,2),(6,7,47,2),(7,0,127,1),(8,0,277,2),(9,2,174,2);
/*!40000 ALTER TABLE `stats` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `users`
--

DROP TABLE IF EXISTS `users`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `users` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `username` varchar(45) DEFAULT NULL,
  `password` varchar(45) DEFAULT NULL,
  `language` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `users`
--

LOCK TABLES `users` WRITE;
/*!40000 ALTER TABLE `users` DISABLE KEYS */;
INSERT INTO `users` VALUES (1,'test','a94a8fe5ccb19ba61c4c0873d391e987982fbbd3','PL'),(2,'Kuba','1d60c7d63f19450cd67c2331630ab0e5df3b9a5d','PL'),(3,'Pawel','53755a9fffb23470a040f163a7c621749a239af8','PL'),(4,'Adrian','5815f22f5440e0a5747fca85e5da3f942434da18','PL'),(5,'Mikolaj','2b8c1c2994fd0a237d7559a9cfd3370b93cb37e0','PL'),(6,'Piekarz','a387757aabd0ab15067ee958320fda92209fc60b','PL'),(7,'Sprzedawca','c66c76d1a1f3b65b6d481c2ceea2a3d56a15946f','PL');
/*!40000 ALTER TABLE `users` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2016-05-20  6:53:11
