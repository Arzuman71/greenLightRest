insert  into `preference`(`id`,`speak`,`smoke`,`with_animals`,`music`) values
 (1,'SOMETIMES','SOMETIMES','SOMETIMES','SOMETIMES'),
 (2,'AGREE','AGREE','SOMETIMES','SOMETIMES'),
 (3,'SOMETIMES','SOMETIMES','SOMETIMES','SOMETIMES'),
 (4,'SOMETIMES','SOMETIMES','SOMETIMES','SOMETIMES');


insert  into `user`(`id`,`name`,`surname`,`age`,`password`,`phone_number`,`email`,`gender`,`pic_url`,`preference_id`,`role`,`status`,`phone_active`,`otp`,`created_date`,`updated_date`,`deleted_date`) values
(22,'Arzuman','Kochoyan','2020-08-07','$2a$10$7V3q9ojVeUdQslZRMrHlWue0LOX/DoNar4W3ABBwP3WAh.MrX8kZ2','0','arzuman.@mail.ru','MALE',NULL,2,'USER','ACTIVE','1','','2020-12-08 14:10:35','2020-12-08 14:10:35',NULL),
(39,'Arzuman','Arzuman','2020-07-27','$2a$10$0C.v0sW2KPRpr1al4odc4um08oLM9exAvkRprUomVhdjw1WWv2QYy','0','arzuman.kochoyan@mail.ru','MALE',NULL,2,'USER','ACTIVE','0','','2020-10-23 16:27:22','2020-10-23 16:27:22',NULL),
(50,'Arzuman','Kochoyan',NULL,'$2a$10$QbwPIdhaYAAdQLR7iCgFf.PmcJu0u.dEzlrx/L4bbNaVUsramRiFK',NULL,'arzuman.k@mail.ru','MALE',NULL,1,'USER','ACTIVE','0','d96d38b6-8cd8-4031-ab0b-e63268bd585c','2020-12-15 13:48:50','2020-12-15 13:48:50',NULL),
(51,'Parol@ arzuman e','jan','2021-01-19','$2a$10$118DXJtg3pjuRTU/ibbnLOSJ/XfaGFwE4bzFZlQAkwJeJ9d2rZwlS',NULL,'poxostest@mail.ru','MALE',NULL,1,'USER','ACTIVE','0','e570c78e-81b8-40a5-bdcc-98fe790f6464','2021-01-18 17:03:28','2021-01-18 17:03:28',NULL);


insert  into `rating`(`id`,`number`,`to_id`,`from_id`) values
 (3,5,22,22);


insert  into `item`(`id`,`outset`,`end`,`start_date`,`price`,`user_id`,`parcel_type`,`car_id`,`type`,`number_of_passengers`,`status`,`created_date`,`updated_date`,`deleted_date`) values
(3,'test','test','2021-01-09 23:16:00','11',22,'OTHER',NULL,'CAR_DRIVER',1,'ACTIVE','2020-09-29 17:08:00','2020-09-29 17:11:45',NULL),
(4,'test','test','2021-01-09 16:25:08',NULL,22,'PARCEL',NULL,'CAR_DRIVER',2,'ARCHIVED','2020-09-28 17:08:11','2020-09-29 17:11:48',NULL),
(5,'fr','wh','2020-12-08 14:26:18','0.0',22,NULL,NULL,'CAR_DRIVER',0,'ACTIVE','2020-11-18 18:02:28','2020-11-18 18:02:28',NULL),
(6,'fr','wh','2020-12-08 14:26:20','0.0',22,NULL,NULL,'CAR_DRIVER',0,'ACTIVE','2020-11-19 01:19:58','2020-11-19 01:19:58',NULL),
(8,'test','test','2021-01-09 18:22:06','33',22,'PARCEL',NULL,'CAR_DRIVER',0,'ACTIVE','2021-01-09 15:41:44','2021-01-09 15:41:44',NULL);


insert  into `car`(`id`,`car_brand`,`car_type`,`car_number`,`pic_url`,`year`,`car_model`,`user_id`,`color`,`status`,`created_date`,`updated_date`,`deleted_date`) values
 (2,'vv','CAR','22',NULL,'2020-10-28','tt',22,'BLACK','ACTIVE','2020-10-28 18:10:18','2020-10-28 18:10:35',NULL),
 (4,'carBrand','CAR','17777',NULL,'1717-10-10','carModel',50,'BLUE','ACTIVE','2020-12-10 23:24:16',NULL,NULL),
 (7,'ok','BUS','4441712235',NULL,'2021-01-13','test',51,'BURGUNDY','ARCHIVED','2021-01-19 13:46:07',NULL,NULL);
