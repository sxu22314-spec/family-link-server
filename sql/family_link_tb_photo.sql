-- MySQL dump 10.13  Distrib 8.0.43, for Win64 (x86_64)
--
-- Host: localhost    Database: family_link
-- ------------------------------------------------------
-- Server version	8.0.43

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!50503 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `tb_photo`
--

DROP TABLE IF EXISTS `tb_photo`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `tb_photo` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `title` varchar(100) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '照片主题(如: 郊游)',
  `image_url` varchar(255) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '照片URL',
  `audio_url` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '故事录音URL',
  `type` tinyint DEFAULT '1' COMMENT '内容类型: 0-普通, 1-拼图',
  `is_locked` tinyint(1) NOT NULL DEFAULT '0' COMMENT '是否锁定：0-否，1-是',
  `photo_date` datetime DEFAULT NULL,
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP,
  `theme` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '图片主题',
  PRIMARY KEY (`id`),
  KEY `idx_type` (`type`),
  KEY `idx_photo_date` (`photo_date`)
) ENGINE=InnoDB AUTO_INCREMENT=37 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tb_photo`
--

LOCK TABLES `tb_photo` WRITE;
/*!40000 ALTER TABLE `tb_photo` DISABLE KEYS */;
INSERT INTO `tb_photo` VALUES (24,'play chess','photo/16aac85f-aa91-4702-bb91-85ba5631081e.png',NULL,1,0,NULL,'2026-05-04 16:16:25','sports'),(25,'fly kite','photo/eb8443d6-5357-4417-8024-9888dfc77b33.png',NULL,1,1,NULL,'2026-05-04 16:18:11','sports'),(26,'cooking','photo/5483cccd-595e-4769-afda-29904a4a48e8.png',NULL,1,1,NULL,'2026-05-04 16:18:29','food'),(27,'gardening','photo/f541a3cb-0867-4cae-8693-765e91ab22b4.png',NULL,1,1,NULL,'2026-05-04 16:18:52','daily-life'),(28,'puzzle','photo/b9272052-55b8-4802-b821-85f7da2b3c30.png',NULL,1,1,NULL,'2026-05-04 16:19:18','holiday'),(36,'a','photo/36d20dc8-bf68-4d1e-be54-c6c483b2a958.png',NULL,1,1,'2026-05-04 00:00:00','2026-05-04 22:54:01','daily-life');
/*!40000 ALTER TABLE `tb_photo` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2026-05-06 21:55:57
