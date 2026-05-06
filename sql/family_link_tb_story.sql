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
-- Table structure for table `tb_story`
--

DROP TABLE IF EXISTS `tb_story`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `tb_story` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `photo_id` bigint DEFAULT NULL,
  `title` varchar(255) NOT NULL,
  `description` text NOT NULL,
  `subject` varchar(255) NOT NULL,
  `audio_url` varchar(1024) DEFAULT NULL,
  `cover_image_url` varchar(1024) DEFAULT NULL,
  `listen_count` int NOT NULL DEFAULT '0',
  `is_locked` tinyint(1) NOT NULL DEFAULT '1',
  `task_type` varchar(64) NOT NULL,
  `task_data` json DEFAULT NULL,
  `created_at` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `updated_at` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  KEY `idx_story_photo_id` (`photo_id`),
  KEY `idx_story_created_at` (`created_at`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tb_story`
--

LOCK TABLES `tb_story` WRITE;
/*!40000 ALTER TABLE `tb_story` DISABLE KEYS */;
INSERT INTO `tb_story` VALUES (1,24,'puzzle afternoon','On a sunny afternoon，grandpa and grandson is playing chess togerther','puzzle afternoon',NULL,NULL,0,1,'question',NULL,'2026-05-04 18:51:41','2026-05-04 18:51:41'),(4,NULL,'The Little Garden Secret','A tiny garden behind the house where tomatoes and peppers grew every summer.','The Little Garden Secret',NULL,NULL,0,1,'question','{\"type\": \"question\", \"prompt\": \"Complete the question task to unlock this story\", \"question\": \"What festival did Grandma celebrate in this story?\"}','2026-05-04 15:59:59','2026-05-04 15:59:59'),(5,NULL,'Grandpa and the Old Bicycle','Grandpa shares how he rode an old bicycle to school and fixed it again and again.','Grandpa and the Old Bicycle',NULL,NULL,0,1,'drawing','{\"type\": \"drawing\", \"prompt\": \"Complete the drawing task to unlock this story\"}','2026-05-04 16:00:31','2026-05-04 16:00:31'),(6,NULL,'The Little Garden Secret','A tiny garden behind the house where tomatoes and peppers grew every summer.','The Little Garden Secret',NULL,NULL,0,1,'question','{\"type\": \"question\", \"prompt\": \"Complete the question task to unlock this story\"}','2026-05-04 16:01:10','2026-05-04 16:01:10'),(7,NULL,'A Rainy Day Storybook','On a rainy afternoon, grandparents read stories together and made warm tea.','A Rainy Day Storybook',NULL,NULL,0,1,'drawing','{\"type\": \"drawing\", \"prompt\": \"Complete the drawing task to unlock this story\"}','2026-05-04 16:03:55','2026-05-04 16:03:55');
/*!40000 ALTER TABLE `tb_story` ENABLE KEYS */;
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
