/*
SQLyog Ultimate v12.09 (64 bit)
MySQL - 5.7.18-log : Database - green_light
*********************************************************************
*/

/*!40101 SET NAMES utf8 */;

/*!40101 SET SQL_MODE=''*/;

/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;
CREATE DATABASE /*!32312 IF NOT EXISTS*/`green_light` /*!40100 DEFAULT CHARACTER SET utf8mb4 */;

USE `green_light`;

/*Table structure for table `advertisement` */

DROP TABLE IF EXISTS `advertisement`;

CREATE TABLE `advertisement` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `link` varchar(150) CHARACTER SET dec8 NOT NULL,
  `pic_url` varchar(255) CHARACTER SET dec8 NOT NULL,
  `deadline` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `description` text NOT NULL,
  `user_id` int(11) NOT NULL,
  `status` enum('ACTIVE','ARCHIVED','DELETED') NOT NULL,
  `created_date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `deleted_date` timestamp NULL DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `user_id` (`user_id`),
  CONSTRAINT `advertisement_ibfk_1` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4;

/*Data for the table `advertisement` */

insert  into `advertisement`(`id`,`link`,`pic_url`,`deadline`,`description`,`user_id`,`status`,`created_date`,`deleted_date`) values (1,'sdff','xxqqw','2020-09-15 23:34:00','sdc',22,'ACTIVE','2020-09-27 23:34:22',NULL);

/*Table structure for table `car` */

DROP TABLE IF EXISTS `car`;

CREATE TABLE `car` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `car_brand` varchar(50) NOT NULL,
  `car_type` enum('CAR','BUS','TRUCK') NOT NULL,
  `car_number` varchar(50) DEFAULT NULL,
  `pic_url` varchar(255) DEFAULT 'str',
  `year` date DEFAULT NULL,
  `car_model` varchar(50) DEFAULT NULL,
  `user_id` int(11) NOT NULL,
  `color` enum('BLACK','WHITE','DARK_GREY','GREY','BURGUNDY','RED','DARK_BLUE','BLUE','DARK_GREEN','GREEN','BROWN','BEIGE','ORANGE','YELLOW','PURPLE','PINK') NOT NULL,
  `status` enum('ACTIVE','ARCHIVED','DELETED') NOT NULL,
  `created_date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `updated_date` timestamp NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
  `deleted_date` timestamp NULL DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `user_id` (`user_id`),
  CONSTRAINT `car_ibfk_1` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8mb4;

/*Data for the table `car` */

insert  into `car`(`id`,`car_brand`,`car_type`,`car_number`,`pic_url`,`year`,`car_model`,`user_id`,`color`,`status`,`created_date`,`updated_date`,`deleted_date`) values (2,'vv','CAR','22',NULL,'2020-10-28','tt',22,'BLACK','ACTIVE','2020-10-28 18:10:18','2020-10-28 18:10:35',NULL),(4,'carBrand','CAR','17777',NULL,'1717-10-10','carModel',50,'BLUE','ACTIVE','2020-12-10 23:24:16',NULL,NULL),(7,'ok','BUS','4441712235',NULL,'2021-01-13','test',51,'BURGUNDY','ARCHIVED','2021-01-19 13:46:07',NULL,NULL),(8,'kk','BUS','44417',NULL,'2021-01-14','29 October',51,'RED','ACTIVE','2021-01-30 14:35:16',NULL,NULL),(9,'kk','BUS','44417',NULL,'2021-01-14','29 October',51,'RED','ACTIVE','2021-01-30 14:35:16',NULL,NULL);

/*Table structure for table `images` */

DROP TABLE IF EXISTS `images`;

CREATE TABLE `images` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) NOT NULL DEFAULT 'str',
  `car_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `car_id` (`car_id`),
  CONSTRAINT `images_ibfk_1` FOREIGN KEY (`car_id`) REFERENCES `car` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8mb4;

/*Data for the table `images` */

insert  into `images`(`id`,`name`,`car_id`) values (2,'18test.jpg',8),(3,'test',NULL),(5,'test',2);

/*Table structure for table `item` */

DROP TABLE IF EXISTS `item`;

CREATE TABLE `item` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `outset` varchar(50) NOT NULL,
  `end` varchar(50) NOT NULL,
  `start_date` timestamp NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
  `price` varchar(50) DEFAULT NULL,
  `user_id` int(11) NOT NULL,
  `parcel_type` varchar(200) DEFAULT NULL,
  `car_id` int(11) DEFAULT NULL,
  `type` enum('CAR_DRIVER','TRUCK_DRIVER') NOT NULL,
  `number_of_passengers` int(3) DEFAULT '0',
  `status` enum('ACTIVE','ARCHIVED','DELETED') NOT NULL,
  `created_date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `updated_date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `deleted_date` timestamp NULL DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `user_id` (`user_id`),
  KEY `car_id` (`car_id`),
  CONSTRAINT `item_ibfk_1` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`),
  CONSTRAINT `item_ibfk_2` FOREIGN KEY (`car_id`) REFERENCES `car` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8mb4;

/*Data for the table `item` */

insert  into `item`(`id`,`outset`,`end`,`start_date`,`price`,`user_id`,`parcel_type`,`car_id`,`type`,`number_of_passengers`,`status`,`created_date`,`updated_date`,`deleted_date`) values (3,'test','test','2021-01-09 23:16:00','11',22,'OTHER',NULL,'CAR_DRIVER',1,'ACTIVE','2020-09-29 17:08:00','2020-09-29 17:11:45',NULL),(4,'test','test','2021-01-09 16:25:08',NULL,22,'PARCEL',NULL,'CAR_DRIVER',2,'ARCHIVED','2020-09-28 17:08:11','2020-09-29 17:11:48',NULL),(5,'fr','wh','2020-12-08 14:26:18','0.0',22,NULL,NULL,'CAR_DRIVER',0,'ACTIVE','2020-11-18 18:02:28','2020-11-18 18:02:28',NULL),(6,'fr','wh','2020-12-08 14:26:20','0.0',22,NULL,NULL,'CAR_DRIVER',0,'ACTIVE','2020-11-19 01:19:58','2020-11-19 01:19:58',NULL),(8,'test','test','2021-01-09 18:22:06','33',22,'PARCEL',NULL,'CAR_DRIVER',0,'ACTIVE','2021-01-09 15:41:44','2021-01-09 15:41:44',NULL),(9,'98','98',NULL,'98',51,NULL,8,'TRUCK_DRIVER',98,'ACTIVE','2021-01-30 14:41:33','2021-01-30 14:41:33',NULL);

/*Table structure for table `preference` */

DROP TABLE IF EXISTS `preference`;

CREATE TABLE `preference` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `speak` enum('AGAINST','SOMETIMES','AGREE') NOT NULL DEFAULT 'SOMETIMES',
  `smoke` enum('AGAINST','SOMETIMES','AGREE') NOT NULL DEFAULT 'SOMETIMES',
  `with_animals` enum('AGAINST','SOMETIMES','AGREE') NOT NULL DEFAULT 'SOMETIMES',
  `music` enum('AGAINST','SOMETIMES','AGREE') NOT NULL DEFAULT 'SOMETIMES',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb4;

/*Data for the table `preference` */

insert  into `preference`(`id`,`speak`,`smoke`,`with_animals`,`music`) values (1,'SOMETIMES','SOMETIMES','SOMETIMES','SOMETIMES'),(2,'AGREE','AGREE','SOMETIMES','SOMETIMES'),(3,'SOMETIMES','SOMETIMES','SOMETIMES','SOMETIMES'),(4,'SOMETIMES','SOMETIMES','SOMETIMES','SOMETIMES');

/*Table structure for table `rating` */

DROP TABLE IF EXISTS `rating`;

CREATE TABLE `rating` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `number` double NOT NULL,
  `to_id` int(11) NOT NULL,
  `from_id` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `user_id` (`to_id`),
  KEY `appreciater_id` (`from_id`),
  CONSTRAINT `rating_ibfk_1` FOREIGN KEY (`to_id`) REFERENCES `user` (`id`),
  CONSTRAINT `rating_ibfk_2` FOREIGN KEY (`from_id`) REFERENCES `user` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8mb4;

/*Data for the table `rating` */

insert  into `rating`(`id`,`number`,`to_id`,`from_id`) values (3,5,22,22),(5,2,22,39);

/*Table structure for table `user` */

DROP TABLE IF EXISTS `user`;

CREATE TABLE `user` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(50) NOT NULL,
  `surname` varchar(50) NOT NULL,
  `age` date DEFAULT NULL,
  `password` varchar(255) NOT NULL,
  `phone_number` varchar(50) DEFAULT '0',
  `email` varchar(50) DEFAULT NULL,
  `gender` enum('MALE','FEMALE') NOT NULL,
  `pic_url` varchar(255) DEFAULT 'str',
  `preference_id` int(11) DEFAULT NULL,
  `role` enum('USER','ADMIN') NOT NULL,
  `status` enum('ACTIVE','ARCHIVED','DELETED') NOT NULL DEFAULT 'ACTIVE',
  `phone_active` bit(1) NOT NULL,
  `otp` varchar(255) DEFAULT NULL,
  `created_date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `updated_date` timestamp NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
  `deleted_date` timestamp NULL DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `preference_id` (`preference_id`),
  CONSTRAINT `user_ibfk_1` FOREIGN KEY (`preference_id`) REFERENCES `preference` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=53 DEFAULT CHARSET=utf8mb4;

/*Data for the table `user` */

insert  into `user`(`id`,`name`,`surname`,`age`,`password`,`phone_number`,`email`,`gender`,`pic_url`,`preference_id`,`role`,`status`,`phone_active`,`otp`,`created_date`,`updated_date`,`deleted_date`) values (22,'Arzuman','Kochoyan','2020-08-07','$2a$10$7V3q9ojVeUdQslZRMrHlWue0LOX/DoNar4W3ABBwP3WAh.MrX8kZ2','0','arzuman.@mail.ru','MALE',NULL,2,'USER','ACTIVE','','','2020-12-08 14:10:35','2020-12-08 14:10:35',NULL),(39,'Arzuman','Arzuman','2020-07-27','$2a$10$0C.v0sW2KPRpr1al4odc4um08oLM9exAvkRprUomVhdjw1WWv2QYy','0','arzuman.kochoyan@mail.ru','MALE',NULL,2,'USER','ACTIVE','','','2020-10-23 16:27:22','2020-10-23 16:27:22',NULL),(50,'Arzuman','Kochoyan',NULL,'$2a$10$QbwPIdhaYAAdQLR7iCgFf.PmcJu0u.dEzlrx/L4bbNaVUsramRiFK','0','arzuman.k@mail.ru','MALE',NULL,1,'USER','ACTIVE','\0','d96d38b6-8cd8-4031-ab0b-e63268bd585c','2021-01-25 20:52:09','2021-01-25 20:52:09',NULL),(51,'Parol@ arzuman e','jan','2021-01-19','$2a$10$118DXJtg3pjuRTU/ibbnLOSJ/XfaGFwE4bzFZlQAkwJeJ9d2rZwlS','0','poxostest@mail.ru','MALE','faa37c4359874019999049c8aac56832tree2.png',1,'USER','ACTIVE','\0','e570c78e-81b8-40a5-bdcc-98fe790f6464','2021-01-25 20:52:14','2021-01-25 20:52:14',NULL),(52,'Arzuman','Kochoyan',NULL,'$2a$10$psM2yqmMBDEWAzIK1Ef64ODP6f7WglApHkSbRmtMaHYzazF.hzACG',NULL,'arzuman.kochoyan98@mail.ru','MALE',NULL,1,'USER','ACTIVE','\0','0ae0287b-5299-4a20-85ce-d9944e2f9247','2021-02-02 13:56:35','2021-02-02 13:56:35',NULL);

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
