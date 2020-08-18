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
  `create_date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `deadline` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00',
  `description` text NOT NULL,
  `user_id` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `user_id` (`user_id`),
  CONSTRAINT `advertisement_ibfk_1` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

/*Data for the table `advertisement` */

/*Table structure for table `announcement` */

DROP TABLE IF EXISTS `announcement`;

CREATE TABLE `announcement` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `from_is` varchar(255) NOT NULL,
  `where_is` varchar(255) NOT NULL,
  `start_date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `price` varchar(255) DEFAULT NULL,
  `user_id` int(11) NOT NULL,
  `parcel_type` enum('DOCUMENT','PARCEL','ALIVE','GLASS','OTHER') DEFAULT 'PARCEL',
  `car_id` int(11) DEFAULT NULL,
  `announcement_type` enum('CAR_DRIVER','TRUCK_DRIVER','PASSENGER','SEEKER_TRUCK') NOT NULL,
  `created_date` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00',
  `number_of_passengers` int(3) DEFAULT NULL,
  `state` enum('ACTIVE','ARCHIVED','DELETED') NOT NULL DEFAULT 'ACTIVE',
  PRIMARY KEY (`id`),
  KEY `user_id` (`user_id`),
  KEY `car_id` (`car_id`),
  CONSTRAINT `announcement_ibfk_1` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`),
  CONSTRAINT `announcement_ibfk_2` FOREIGN KEY (`car_id`) REFERENCES `car` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4;

/*Data for the table `announcement` */

insert  into `announcement`(`id`,`from_is`,`where_is`,`start_date`,`price`,`user_id`,`parcel_type`,`car_id`,`announcement_type`,`created_date`,`number_of_passengers`,`state`) values (1,'test','test','2020-08-07 22:08:00','0.0',22,'PARCEL',NULL,'SEEKER_TRUCK','2020-08-11 22:08:27',0,'ACTIVE'),(2,'test2','test2','2021-08-14 13:38:07','11',22,'PARCEL',NULL,'SEEKER_TRUCK','2020-08-14 13:38:56',0,'ACTIVE');

/*Table structure for table `car` */

DROP TABLE IF EXISTS `car`;

CREATE TABLE `car` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `car_brand` varchar(255) NOT NULL,
  `car_type` enum('CAR','BUS','TRUCK') NOT NULL,
  `car_number` varchar(255) DEFAULT NULL,
  `pic_car` varchar(255) DEFAULT NULL,
  `year` int(4) DEFAULT NULL,
  `car_model` varchar(255) DEFAULT NULL,
  `user_id` int(11) NOT NULL,
  `color` enum('BLACK','WHITE','DARK_GREY','GREY','BURGUNDY','RED','DARK_BLUE','BLUE','DARK_GREEN','GREEN','BROWN','BEIGE','ORANGE','YELLOW','PURPLE','PINK') DEFAULT NULL,
  `state` enum('ACTIVE','ARCHIVED','DELETED') NOT NULL,
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
  `number` double NOT NULL DEFAULT '3',
  `to_id` int(11) NOT NULL,
  `from_id` int(11) NOT NULL,
  PRIMARY KEY (`id`,`from_id`),
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
  `about_me` text,
  `preference_id` int(11) DEFAULT NULL,
  `role` enum('USER','ADMIN') NOT NULL,
  `created_date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `state` enum('ACTIVE','ARCHIVED','DELETED') NOT NULL DEFAULT 'ACTIVE',
  `active` bit(1) DEFAULT NULL,
  `token` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `preference_id` (`preference_id`),
  CONSTRAINT `user_ibfk_1` FOREIGN KEY (`preference_id`) REFERENCES `preference` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=25 DEFAULT CHARSET=utf8mb4;

/*Data for the table `user` */

insert  into `user`(`id`,`name`,`surname`,`age`,`password`,`phone_number`,`email`,`gender`,`pic_url`,`about_me`,`preference_id`,`role`,`created_date`,`state`,`active`,`token`) values (22,'Arzuman','Kochoyan','2020-08-07','$2a$10$7V3q9ojVeUdQslZRMrHlWue0LOX/DoNar4W3ABBwP3WAh.MrX8kZ2','0','arzuman.kochoyan98@mail.ru','MALE',NULL,NULL,1,'USER','2020-08-09 00:00:00','ACTIVE','',''),(23,'pokpok','okpokp','2020-07-27','$2a$10$TmN6RSHSrr54YGH09LDXtuRcpbOOYIKFim1PJkI1PKvQjHbw.nAXa','0','opjpojpo.jijij@mail.yfbnn','MALE',NULL,NULL,1,'USER','2020-08-13 20:02:21','ACTIVE','\0','91853bb6-9cec-4d86-a0ce-c460191a2104'),(24,'pokpok','okpokp','2020-07-27','$2a$10$LuqUnNUoBkmncoB0EDCxI.M67hUglRJIV8rjF5g4N4pKZlrYjDApO','0','opjpojpo.jijij@mail.yfbnn','MALE',NULL,NULL,1,'USER','2020-08-13 20:07:06','ACTIVE','\0','dccd1590-756e-44ba-afe4-b41c18a779c5');

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
