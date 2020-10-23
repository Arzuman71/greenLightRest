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
  `link` varchar(255) CHARACTER SET dec8 NOT NULL,
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
  `car_brand` varchar(255) NOT NULL,
  `car_type` enum('CAR','BUS','TRUCK') NOT NULL,
  `car_number` varchar(255) DEFAULT NULL,
  `pic_url` varchar(255) DEFAULT NULL,
  `year` date DEFAULT NULL,
  `car_model` varchar(255) DEFAULT NULL,
  `user_id` int(11) NOT NULL,
  `color` enum('BLACK','WHITE','DARK_GREY','GREY','BURGUNDY','RED','DARK_BLUE','BLUE','DARK_GREEN','GREEN','BROWN','BEIGE','ORANGE','YELLOW','PURPLE','PINK') NOT NULL,
  `status` enum('ACTIVE','ARCHIVED','DELETED') NOT NULL,
  `created_date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `updated_date` timestamp NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
  `deleted_date` timestamp NULL DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `user_id` (`user_id`),
  CONSTRAINT `car_ibfk_1` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

/*Data for the table `car` */

/*Table structure for table `images` */

DROP TABLE IF EXISTS `images`;

CREATE TABLE `images` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) NOT NULL,
  `car_id` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `car_id` (`car_id`),
  CONSTRAINT `images_ibfk_1` FOREIGN KEY (`car_id`) REFERENCES `car` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

/*Data for the table `images` */

/*Table structure for table `item` */

DROP TABLE IF EXISTS `item`;

CREATE TABLE `item` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `from` varchar(255) NOT NULL,
  `where` varchar(255) NOT NULL,
  `start_date` timestamp NULL DEFAULT NULL,
  `price` varchar(255) DEFAULT NULL,
  `user_id` int(11) NOT NULL,
  `parcel_type` enum('DOCUMENT','PARCEL','ALIVE','GLASS','OTHER') DEFAULT 'PARCEL',
  `car_id` int(11) DEFAULT NULL,
  `type` enum('CAR_DRIVER','TRUCK_DRIVER','PASSENGER','SEEKER_TRUCK') NOT NULL,
  `number_of_passengers` int(3) DEFAULT NULL,
  `status` enum('ACTIVE','ARCHIVED','DELETED') NOT NULL DEFAULT 'ACTIVE',
  `created_date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `updated_date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `deleted_date` timestamp NULL DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `user_id` (`user_id`),
  KEY `car_id` (`car_id`),
  CONSTRAINT `item_ibfk_1` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`),
  CONSTRAINT `item_ibfk_2` FOREIGN KEY (`car_id`) REFERENCES `car` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb4;

/*Data for the table `item` */

insert  into `item`(`id`,`from`,`where`,`start_date`,`price`,`user_id`,`parcel_type`,`car_id`,`type`,`number_of_passengers`,`status`,`created_date`,`updated_date`,`deleted_date`) values (3,'test','test','2020-09-29 17:07:25','11',22,'OTHER',NULL,'SEEKER_TRUCK',1,'ACTIVE','2020-09-29 17:08:00','2020-09-29 17:11:45',NULL),(4,'test','test','2020-09-30 17:08:48',NULL,22,'PARCEL',NULL,'SEEKER_TRUCK',2,'ACTIVE','2020-09-28 17:08:11','2020-09-29 17:11:48',NULL);

/*Table structure for table `massage` */

DROP TABLE IF EXISTS `massage`;

CREATE TABLE `massage` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `massage` text NOT NULL,
  `user_id` int(11) NOT NULL,
  `created_date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  KEY `user_id` (`user_id`),
  CONSTRAINT `massage_ibfk_1` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

/*Data for the table `massage` */

/*Table structure for table `preference` */

DROP TABLE IF EXISTS `preference`;

CREATE TABLE `preference` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `speak` enum('AGAINST','SOMETIMES','AGREE') NOT NULL DEFAULT 'SOMETIMES',
  `smoke` enum('AGAINST','SOMETIMES','AGREE') NOT NULL DEFAULT 'SOMETIMES',
  `with_animals` enum('AGAINST','SOMETIMES','AGREE') NOT NULL DEFAULT 'SOMETIMES',
  `music` enum('AGAINST','SOMETIMES','AGREE') NOT NULL DEFAULT 'SOMETIMES',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4;

/*Data for the table `preference` */

insert  into `preference`(`id`,`speak`,`smoke`,`with_animals`,`music`) values (1,'SOMETIMES','SOMETIMES','SOMETIMES','SOMETIMES');

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
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4;

/*Data for the table `rating` */

insert  into `rating`(`id`,`number`,`to_id`,`from_id`) values (1,2,22,23),(2,3,22,23),(3,5,22,22);

/*Table structure for table `user` */

DROP TABLE IF EXISTS `user`;

CREATE TABLE `user` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) NOT NULL,
  `surname` varchar(255) NOT NULL,
  `age` date DEFAULT NULL,
  `password` varchar(255) NOT NULL,
  `phone_number` varchar(255) DEFAULT NULL,
  `email` varchar(255) DEFAULT NULL,
  `gender` enum('MALE','FEMALE') NOT NULL,
  `pic_url` varchar(255) DEFAULT NULL,
  `about` text,
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
) ENGINE=InnoDB AUTO_INCREMENT=40 DEFAULT CHARSET=utf8mb4;

/*Data for the table `user` */

insert  into `user`(`id`,`name`,`surname`,`age`,`password`,`phone_number`,`email`,`gender`,`pic_url`,`about`,`preference_id`,`role`,`status`,`phone_active`,`otp`,`created_date`,`updated_date`,`deleted_date`) values (22,'Arzuman','Kochoyan','2020-08-07','$2a$10$7V3q9ojVeUdQslZRMrHlWue0LOX/DoNar4W3ABBwP3WAh.MrX8kZ2','0','arzuman.@mail.ru','MALE',NULL,NULL,1,'USER','ACTIVE','','','2020-09-17 23:58:37',NULL,NULL),(23,'pokpok','okpokp','2020-07-27','$2a$10$TmN6RSHSrr54YGH09LDXtuRcpbOOYIKFim1PJkI1PKvQjHbw.nAXa','0','opjpojpo.jijij@mail.yfbnn','MALE',NULL,NULL,1,'USER','ACTIVE','\0','91853bb6-9cec-4d86-a0ce-c460191a2104','2020-09-17 23:58:37',NULL,NULL),(38,'polpol','polpol','2020-07-27','$2a$10$YVNfGniZeYH6OK9sA7xD3O2KJSJ3raA1bxdIJW4UltMBVhRjRfpAK','0','arzuman.kochoyan98@mail.ru','MALE',NULL,NULL,1,'USER','ACTIVE','','','2020-09-20 19:00:09','2020-09-20 19:00:09',NULL),(39,'Arzuman','Arzuman','2020-07-27','$2a$10$0C.v0sW2KPRpr1al4odc4um08oLM9exAvkRprUomVhdjw1WWv2QYy','0','arzuman.kochoyan@mail.ru','MALE',NULL,'jjjjm,lnjlnl',1,'USER','ACTIVE','','9952ef0a-edd3-4c3c-9825-263dc47a315e','2020-09-20 19:00:06','2020-09-20 19:00:06',NULL);

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
