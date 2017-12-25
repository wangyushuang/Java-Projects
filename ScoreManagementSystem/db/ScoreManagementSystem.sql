-- MySQL dump 10.13  Distrib 5.7.20, for Win64 (x86_64)
--
-- Host: localhost    Database: test
-- ------------------------------------------------------
-- Server version	5.7.20-log

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
-- Table structure for table `class`
--

DROP TABLE IF EXISTS `class`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `class` (
  `class_id` char(6) NOT NULL,
  `dept_id` char(4) NOT NULL,
  `coll_id` char(2) NOT NULL,
  `class_name` varchar(6) NOT NULL,
  PRIMARY KEY (`class_id`),
  KEY `class_fk1` (`dept_id`),
  KEY `class_fk2` (`coll_id`),
  CONSTRAINT `class_fk1` FOREIGN KEY (`dept_id`) REFERENCES `dept` (`dept_id`),
  CONSTRAINT `class_fk2` FOREIGN KEY (`coll_id`) REFERENCES `college` (`coll_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `class`
--

LOCK TABLES `class` WRITE;
/*!40000 ALTER TABLE `class` DISABLE KEYS */;
INSERT INTO `class` VALUES ('010101','0101','01','一班'),('010102','0101','01','二班'),('010103','0101','01','三班'),('010104','0101','01','四班'),('010201','0102','01','一班'),('010202','0102','01','二班'),('010203','0102','01','三班'),('010301','0103','01','一班'),('010302','0103','01','二班');
/*!40000 ALTER TABLE `class` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `college`
--

DROP TABLE IF EXISTS `college`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `college` (
  `coll_id` char(2) NOT NULL,
  `coll_name` varchar(30) NOT NULL,
  PRIMARY KEY (`coll_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `college`
--

LOCK TABLES `college` WRITE;
/*!40000 ALTER TABLE `college` DISABLE KEYS */;
INSERT INTO `college` VALUES ('01','建筑学院'),('02','机械工程学院'),('03','能源与环境学院'),('04','信息科学与工程学院'),('05','土木工程学院'),('06','电子科学与工程学院'),('07','数学学院'),('08','自动化学院'),('09','计算机科学与工程学院'),('10','物理学院'),('11','生物科学与医学工程学院'),('12','材料科学与工程学院'),('13','人文学院'),('14','经济管理学院'),('15','电气工程学院'),('16','外国语学院'),('17','体育学院'),('18','化学化工学院'),('19','交通学院'),('20','仪器科学与工程学院'),('21','艺术学院'),('22','法学院'),('23','医学院'),('24','公共卫生学院'),('25','吴健雄学院'),('26','海外教育学院'),('27','软件学院'),('28','微电子学院'),('29','马克思主义学院'),('30','生命科学研究院'),('31','学习科学研究中心'),('32','蒙纳士大学苏州联合研究生院'),('33','网络空间安全学院');
/*!40000 ALTER TABLE `college` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `course`
--

DROP TABLE IF EXISTS `course`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `course` (
  `cou_id` char(6) NOT NULL,
  `cou_name` varchar(30) NOT NULL,
  `xuefen` decimal(3,1) NOT NULL,
  `coll_id` char(2) NOT NULL,
  `dept_id` char(4) NOT NULL,
  PRIMARY KEY (`cou_id`),
  KEY `cou_fk1` (`dept_id`),
  KEY `cou_fk2` (`coll_id`),
  CONSTRAINT `cou_fk1` FOREIGN KEY (`dept_id`) REFERENCES `dept` (`dept_id`),
  CONSTRAINT `cou_fk2` FOREIGN KEY (`coll_id`) REFERENCES `college` (`coll_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `course`
--

LOCK TABLES `course` WRITE;
/*!40000 ALTER TABLE `course` DISABLE KEYS */;
/*!40000 ALTER TABLE `course` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `courseinfo`
--

DROP TABLE IF EXISTS `courseinfo`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `courseinfo` (
  `cou_id` char(6) NOT NULL,
  `cou_day` char(1) NOT NULL,
  `cou_time` char(1) NOT NULL,
  `teacher` varchar(20) NOT NULL,
  `onchosing` char(1) DEFAULT '0',
  PRIMARY KEY (`cou_id`,`cou_day`,`cou_time`),
  CONSTRAINT `couinfo_fk1` FOREIGN KEY (`cou_id`) REFERENCES `course` (`cou_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `courseinfo`
--

LOCK TABLES `courseinfo` WRITE;
/*!40000 ALTER TABLE `courseinfo` DISABLE KEYS */;
/*!40000 ALTER TABLE `courseinfo` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `dept`
--

DROP TABLE IF EXISTS `dept`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `dept` (
  `dept_id` char(4) NOT NULL,
  `coll_id` char(2) NOT NULL,
  `dept_name` varchar(30) NOT NULL,
  PRIMARY KEY (`dept_id`),
  KEY `dept_fk` (`coll_id`),
  CONSTRAINT `dept_fk` FOREIGN KEY (`coll_id`) REFERENCES `college` (`coll_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `dept`
--

LOCK TABLES `dept` WRITE;
/*!40000 ALTER TABLE `dept` DISABLE KEYS */;
INSERT INTO `dept` VALUES ('0101','01','建筑学'),('0102','01','城乡规划学'),('0103','01','景观学'),('0201','02','机械工程与自动化'),('0202','02','工业工程'),('0301','03','建筑环境与设备工程'),('0302','03','环境工程'),('0303','03','核工程及核技术'),('0401','04','信息与通信工程'),('0402','04','电子科学与技术'),('0501','05','土木工程'),('0502','05','工程管理'),('0503','05','工程力学'),('0504','05','给排水科学与工程');
/*!40000 ALTER TABLE `dept` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `grade`
--

DROP TABLE IF EXISTS `grade`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `grade` (
  `stu_id` char(12) NOT NULL,
  `cou_id` char(6) NOT NULL,
  `score` decimal(4,1) DEFAULT '0.0',
  `isdual` decimal(1,0) DEFAULT '0',
  PRIMARY KEY (`stu_id`,`cou_id`),
  KEY `grade_fk1` (`cou_id`),
  CONSTRAINT `grade_fk1` FOREIGN KEY (`cou_id`) REFERENCES `course` (`cou_id`),
  CONSTRAINT `grade_fk2` FOREIGN KEY (`stu_id`) REFERENCES `student` (`stu_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `grade`
--

LOCK TABLES `grade` WRITE;
/*!40000 ALTER TABLE `grade` DISABLE KEYS */;
/*!40000 ALTER TABLE `grade` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `student`
--

DROP TABLE IF EXISTS `student`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `student` (
  `stu_id` char(12) NOT NULL,
  `stu_name` varchar(20) NOT NULL,
  `stu_gender` char(2) DEFAULT NULL,
  `stu_birth` date NOT NULL,
  `nativeplace` varchar(60) DEFAULT NULL,
  `coll_id` char(2) NOT NULL,
  `dept_id` char(4) NOT NULL,
  `class_id` char(6) NOT NULL,
  `cometime` date DEFAULT NULL,
  PRIMARY KEY (`stu_id`),
  KEY `stu_fk1` (`class_id`),
  KEY `stu_fk2` (`dept_id`),
  KEY `str_fk3` (`coll_id`),
  CONSTRAINT `str_fk3` FOREIGN KEY (`coll_id`) REFERENCES `college` (`coll_id`),
  CONSTRAINT `stu_fk1` FOREIGN KEY (`class_id`) REFERENCES `class` (`class_id`),
  CONSTRAINT `stu_fk2` FOREIGN KEY (`dept_id`) REFERENCES `dept` (`dept_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `student`
--

LOCK TABLES `student` WRITE;
/*!40000 ALTER TABLE `student` DISABLE KEYS */;
INSERT INTO `student` VALUES ('201601010101','张三','男','1997-01-01','江苏省南京市','01','0101','010101','2016-09-01'),('201601010102','李四','男','1997-01-01','湖北省武汉市','01','0101','010101','2016-09-01'),('201601010103','王五','女','1997-04-03','安徽省合肥市','01','0101','010101','2017-09-01'),('201601010104','周麟','男','1996-08-29','湖南省浏阳市','01','0101','010101','2017-09-01'),('201601010105','赵六','男','1996-11-04','山西省太原市','01','0101','010101','2017-09-01'),('201601010201','李月','女','1996-07-07','浙江省杭州市','01','0101','010102','2017-09-01');
/*!40000 ALTER TABLE `student` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user_stu`
--

DROP TABLE IF EXISTS `user_stu`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `user_stu` (
  `stu_id` char(12) NOT NULL,
  `pwd` char(12) NOT NULL,
  PRIMARY KEY (`stu_id`),
  CONSTRAINT `user2_fk` FOREIGN KEY (`stu_id`) REFERENCES `student` (`stu_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user_stu`
--

LOCK TABLES `user_stu` WRITE;
/*!40000 ALTER TABLE `user_stu` DISABLE KEYS */;
INSERT INTO `user_stu` VALUES ('201601010101','201601010101'),('201601010102','201601010102'),('201601010103','201601010103'),('201601010104','201601010104'),('201601010105','201601010105'),('201601010201','201601010201');
/*!40000 ALTER TABLE `user_stu` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user_teacher`
--

DROP TABLE IF EXISTS `user_teacher`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `user_teacher` (
  `uid` char(6) NOT NULL,
  `pwd` char(12) NOT NULL,
  `coll_id` char(2) NOT NULL,
  PRIMARY KEY (`uid`),
  KEY `user1_fk` (`coll_id`),
  CONSTRAINT `user1_fk` FOREIGN KEY (`coll_id`) REFERENCES `college` (`coll_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user_teacher`
--

LOCK TABLES `user_teacher` WRITE;
/*!40000 ALTER TABLE `user_teacher` DISABLE KEYS */;
INSERT INTO `user_teacher` VALUES ('111111','111111111111','01');
/*!40000 ALTER TABLE `user_teacher` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2017-12-25 14:13:05
