-- MySQL dump 10.13  Distrib 8.0.43, for Win64 (x86_64)
--
-- ------------------------------------------------------

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
-- Table structure for table `coding_questions`
--

DROP TABLE IF EXISTS `coding_questions`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `coding_questions` (
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `coding_questions`
--

LOCK TABLES `coding_questions` WRITE;
/*!40000 ALTER TABLE `coding_questions` DISABLE KEYS */;
/*!40000 ALTER TABLE `coding_questions` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `courses`
--

DROP TABLE IF EXISTS `courses`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `courses` (
  `create_at` datetime DEFAULT CURRENT_TIMESTAMP,
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `courses`
--

LOCK TABLES `courses` WRITE;
/*!40000 ALTER TABLE `courses` DISABLE KEYS */;
INSERT INTO `courses` VALUES (1,'Lập trình Java cơ bản','Cấu trúc ngôn ngữ, biến, điều kiện, vòng lặp, và nhập xuất dữ liệu','2025-10-19 12:52:43',0),(2,'Lập trình OOP với Java','Lập trình hướng đối tượng, kế thừa, đa hình, đóng gói','2025-10-19 12:52:43',399000),(3,'Lập trình Python','Xử lý dữ liệu, lập trình hướng đối tượng, thư viện phổ biến','2025-10-19 12:52:43',0),(4,'Lập trình C','Lập trình cơ bản, con trỏ, cấp phát bộ nhớ, file I/O','2025-10-19 12:52:43',0),(5,'Lập trình C++ cơ bản','Cấu trúc dữ liệu, hàm, lớp, đối tượng, và STL cơ bản','2025-10-19 12:52:43',0),(6,'Lập trình C++ nâng cao','Mẫu (template), lập trình tổng quát, xử lý ngoại lệ, đa luồng, và tối ưu hiệu năng','2025-10-19 12:52:43',299000),(7,'Cấu trúc dữ liệu và Giải thuật','Mảng, danh sách, ngăn xếp, hàng đợi, cây, đồ thị, thuật toán tìm kiếm & sắp xếp','2025-10-19 12:52:43',499000),(8,'Lập trình Pascal','Cấu trúc điều khiển, hàm, thủ tục, và kiểu dữ liệu cơ bản','2025-10-19 12:52:43',0),(9,'Cơ sở dữ liệu và SQL','Thiết kế cơ sở dữ liệu, câu lệnh SELECT, JOIN, và tối ưu truy vấn','2025-10-19 12:52:43',349000),(10,'Lập trình C#','Lập trình hướng đối tượng, Windows Form, LINQ và .NET Framework','2025-10-19 12:52:43',449000);
/*!40000 ALTER TABLE `courses` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `enrollments`
--

DROP TABLE IF EXISTS `enrollments`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `enrollments` (
  `enrolled_at` datetime DEFAULT CURRENT_TIMESTAMP,
  KEY `user_id` (`user_id`),
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `enrollments`
--

LOCK TABLES `enrollments` WRITE;
/*!40000 ALTER TABLE `enrollments` DISABLE KEYS */;
/*!40000 ALTER TABLE `enrollments` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `lessons`
--

DROP TABLE IF EXISTS `lessons`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `lessons` (
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `lessons`
--

LOCK TABLES `lessons` WRITE;
/*!40000 ALTER TABLE `lessons` DISABLE KEYS */;
/*!40000 ALTER TABLE `lessons` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `submissions`
--

DROP TABLE IF EXISTS `submissions`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `submissions` (
  `submit_time` datetime DEFAULT CURRENT_TIMESTAMP,
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `submissions`
--

LOCK TABLES `submissions` WRITE;
/*!40000 ALTER TABLE `submissions` DISABLE KEYS */;
/*!40000 ALTER TABLE `submissions` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `test_cases`
--

DROP TABLE IF EXISTS `test_cases`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `test_cases` (
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `test_cases`
--

LOCK TABLES `test_cases` WRITE;
/*!40000 ALTER TABLE `test_cases` DISABLE KEYS */;
/*!40000 ALTER TABLE `test_cases` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `users`
--

DROP TABLE IF EXISTS `users`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `users` (
  `create_at` datetime DEFAULT CURRENT_TIMESTAMP,
  `token_expiry` timestamp NULL DEFAULT NULL,
  UNIQUE KEY `user_name` (`user_name`),
  UNIQUE KEY `email` (`email`)
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `users`
--

LOCK TABLES `users` WRITE;
/*!40000 ALTER TABLE `users` DISABLE KEYS */;
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

