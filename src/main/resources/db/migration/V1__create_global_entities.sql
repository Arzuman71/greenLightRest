
CREATE TABLE preference (
  id int(11) NOT NULL AUTO_INCREMENT,
  speak enum('AGAINST','SOMETIMES','AGREE') NOT NULL DEFAULT 'SOMETIMES',
  smoke enum('AGAINST','SOMETIMES','AGREE') NOT NULL DEFAULT 'SOMETIMES',
  with_animals enum('AGAINST','SOMETIMES','AGREE') NOT NULL DEFAULT 'SOMETIMES',
  music enum('AGAINST','SOMETIMES','AGREE') NOT NULL DEFAULT 'SOMETIMES',
  PRIMARY KEY (id)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8mb4;


CREATE TABLE user (
  id int(11) NOT NULL AUTO_INCREMENT,
  name varchar(50) NOT NULL,
  surname varchar(50) NOT NULL,
  age date DEFAULT NULL,
  password varchar(200) NOT NULL,
  phone_number varchar(50) DEFAULT '0',
  email varchar(50) NOT NULL,
  gender enum('MALE','FEMALE') NOT NULL,
  pic_url varchar(255) DEFAULT 'str',
  preference_id int(11) NOT NULL DEFAULT 1,
  role enum('USER','ADMIN') NOT NULL,
  status enum('ACTIVE','ARCHIVED','DELETED') NOT NULL DEFAULT 'ACTIVE',
  phone_active bit(1) NOT NULL,
  otp varchar(255) DEFAULT NULL,
  created_date timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  updated_date timestamp NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
  deleted_date timestamp NULL DEFAULT NULL,
  PRIMARY KEY (id),
  KEY preference_id (preference_id),
  CONSTRAINT user_ibfk_1 FOREIGN KEY (preference_id) REFERENCES preference (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;


CREATE TABLE rating (
  id int(11) NOT NULL AUTO_INCREMENT,
  number double NOT NULL,
  to_id int(11) NOT NULL,
  from_id int(11) NOT NULL,
  PRIMARY KEY (id),
  KEY user_id (to_id),
  KEY appreciater_id (from_id),
  CONSTRAINT rating_ibfk_1 FOREIGN KEY (to_id) REFERENCES user (id),
  CONSTRAINT rating_ibfk_2 FOREIGN KEY (from_id) REFERENCES user (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE advertisement (
  id int(11) NOT NULL AUTO_INCREMENT,
  link varchar(150) CHARACTER SET dec8 NOT NULL,
  pic_url varchar(255) CHARACTER SET dec8 NOT NULL,
  deadline timestamp NULL DEFAULT NULL,
  description text NOT NULL,
  user_id int(11) NOT NULL,
  status enum('ACTIVE','ARCHIVED','DELETED') NOT NULL DEFAULT 'ACTIVE',
  created_date timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  deleted_date timestamp NULL DEFAULT NULL,
  PRIMARY KEY (id),
  KEY user_id (user_id),
  CONSTRAINT advertisement_ibfk_1 FOREIGN KEY (user_id) REFERENCES user (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE car (
  id int(11) NOT NULL AUTO_INCREMENT,
  car_brand varchar(50) NOT NULL,
  car_type enum('CAR','BUS','TRUCK') NOT NULL,
  car_number varchar(50) DEFAULT NULL,
  pic_url varchar(255) DEFAULT 'str',
  year date DEFAULT NULL,
  car_model varchar(50) DEFAULT NULL,
  user_id int(11) NOT NULL,
  color enum('BLACK','WHITE','DARK_GREY','GREY','BURGUNDY','RED','DARK_BLUE','BLUE','DARK_GREEN','GREEN','BROWN','BEIGE','ORANGE','YELLOW','PURPLE','PINK') NOT NULL,
  status enum('ACTIVE','ARCHIVED','DELETED') NOT NULL DEFAULT 'ACTIVE',
  created_date timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  updated_date timestamp NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
  deleted_date timestamp NULL DEFAULT NULL,
  PRIMARY KEY (id),
  KEY user_id (user_id),
  CONSTRAINT car_ibfk_1 FOREIGN KEY (user_id) REFERENCES user (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE images (
  id int(11) NOT NULL AUTO_INCREMENT,
  name varchar(255) NOT NULL DEFAULT 'str',
  car_id int(11) NOT NULL,
  PRIMARY KEY (id),
  KEY car_id (car_id),
  CONSTRAINT images_ibfk_1 FOREIGN KEY (car_id) REFERENCES car (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE item (
  id int(11) NOT NULL AUTO_INCREMENT,
  outset varchar(50) NOT NULL,
  end varchar(50) NOT NULL,
  start_date timestamp NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
  price varchar(50) DEFAULT NULL,
  user_id int(11) NOT NULL,
  parcel_type varchar(200) DEFAULT NULL,
  car_id int(11) NOT NULL,
  type enum('CAR_DRIVER','TRUCK_DRIVER') NOT NULL,
  number_of_passengers int(3) DEFAULT '0',
  status enum('ACTIVE','ARCHIVED','DELETED') NOT NULL DEFAULT 'ACTIVE',
  created_date timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  updated_date timestamp NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
  deleted_date timestamp NULL DEFAULT NULL,
  PRIMARY KEY (id),
  KEY user_id (user_id),
  KEY car_id (car_id),
  CONSTRAINT item_ibfk_1 FOREIGN KEY (user_id) REFERENCES user (id),
  CONSTRAINT item_ibfk_2 FOREIGN KEY (car_id) REFERENCES car (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;
