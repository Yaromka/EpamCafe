-- Host: localhost    Database: epam_cafe_db
-- ------------------------------------------------------

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
-- Table structure for table `categories`
--

DROP TABLE IF EXISTS `categories`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `categories` (
  `category_id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'category id NOT NULL',
  `category_name` varchar(45) NOT NULL COMMENT 'Index has been added in order to use it with WHERE queries.',
  PRIMARY KEY (`category_id`),
  UNIQUE KEY `category_id_UNIQUE` (`category_id`),
  KEY `category_name_idx` (`category_name`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8 COMMENT='Current table contains and describes dish categories.';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `categories`
--

LOCK TABLES `categories` WRITE;
/*!40000 ALTER TABLE `categories` DISABLE KEYS */;
INSERT INTO `categories` VALUES (1,'Drinks'),(3,'Garnish'),(5,'Meat dishes'),(4,'Pizzas'),(2,'Salads'),(6,'Sushi');
/*!40000 ALTER TABLE `categories` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `dishes`
--

DROP TABLE IF EXISTS `dishes`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `dishes` (
  `dish_id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'Synthetic primary key INT NOT NULL ',
  `dish_name` varchar(60) NOT NULL COMMENT 'NOT NULL. 60 symbols are quite enough to name different dishes, even really big. \nUnique index - it is meaningless to create dishes with the same name. Index has been added in order to use it with WHERE queries and to sort by alphabet.',
  `dish_description` tinytext COMMENT 'dish_description has been added in order to give details for customer. This field could be NULL, cause if there is no description (for example something obvious like cola).TINYTEXT - 255 symbols - it is enough to give full description. Different dishes could have the same description (different amount of water in the bottle).',
  `dish_price` mediumint(11) NOT NULL COMMENT 'Using DECIMAL(6,2) max price of dish costing up to 9999.99 y.e. No matter is it USD, BYN or RUB. It is more than necessary for cafe. Price cannot be negative. Index has been added in order to sort by price.',
  `dish_isEnable` tinyint(1) NOT NULL DEFAULT '1' COMMENT 'dish_isEnable 1- dish is available for ordering, 0 - not. Users might see the only enabled dishes.(menu_isEnable = 1).Admin might see all the dishes. Index has been added in order to use it with WHERE queries.',
  `categories_category_id` int(11) NOT NULL COMMENT 'Category - drinks, salads, garnish and so on. ON UPDATE - CASCADE, ON DELETE - RESTRICT you cant delete category when there is at least one dish inside.',
  `dish_picture` blob NOT NULL DEFAULT '',
  `dish_weight` int(11) NOT NULL,
  PRIMARY KEY (`dish_id`),
  UNIQUE KEY `menu_dishName_UNIQUE` (`dish_name`),
  KEY `menu_isEnable_idx` (`dish_isEnable`),
  KEY `fk_dishes_categories1_idx` (`categories_category_id`),
  KEY `menu_dish_price` (`dish_price`),
  CONSTRAINT `fk_dishes_categories1` FOREIGN KEY (`categories_category_id`) REFERENCES `categories` (`category_id`) ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=20 DEFAULT CHARSET=utf8 COMMENT='Current table contains list of dishes. Fields: name of dish, description, weight, price, and information concerning availability.';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `dishes`
--

LOCK TABLES `dishes` WRITE;
/*!40000 ALTER TABLE `dishes` DISABLE KEYS */;
INSERT INTO `dishes` VALUES (1,'Burger','Our burger consisting of two cooked patties of ground beef, placed inside a sliced bread roll. The patty is barbecued. Hamburger served with cheese, lettuce, tomato, bacon, onion and pickles.',700,1,5,LOAD_FILE('D:\Epam\projects\EpamCafe\src\main\webapp\img\burger.jpg'),250),(2,'Hotdog','A hot dog also known as a frankfurter (sometimes shortened to frank), dog, or wiener, is a cooked sausage, traditionally grilled and served in a partially sliced bun. It is a type of sausage sandwich. Garnish include mustard, ketchup, onions, mayonnaise, relish, coleslaw, cheese and sauerkraut.',300,1,5,'',200),(3,'Barbecue','A fresh, big and sexy piece of meat is going to blow you away. Made by real fire with a help of our chef make it not only over tasty, but also beautiful!',900,0,5,'',350),(4,'Fried chicken','Fried chicken (also referred to as Southern fried chicken for the variant in the United States) is a dish consisting of chicken pieces which have been floured and then deep fried. The breading adds a crisp coating to the exterior of the chicken. What separates fried chicken from other fried forms of chicken is that chicken is cut at the joints, and the bones and skin are left intact. Crisp well-seasoned skin, rendered of excess fat, is a hallmark of well made fried chicken.',750,1,5,'',300),(5,'Fanta','Well-known drink that do not need to be presented.',200,1,1,'',500),(6,'Juice','Fresh juice made from fresh fruits. You will be totally satisfied!',250,1,1,'',500),(7,'Red Bull','Popular energy drink.',300,1,1,'',500),(8,'Cola','The most famous drink in the world.',200,0,1,'',500),(9,'Greek salad','Greek salad is made with pieces of tomatoes, sliced cucumbers, onion, feta cheese served as a slice on top of the other ingredients, and Kalamata olives, seasoned with salt and oregano, and dressed with olive oil.',250,1,2,'',250),(10,'Russian salad','Russian salad is a traditional salad dish in Russian cuisine, which is also popular in old Soviet Union. it is made with diced boiled potatoes, carrots, brined dill pickles, green peas, eggs, celeriac, onions, diced boiled chicken with salt, pepper, and mustard added to enhance flavor, dressed with mayonnaise.',300,1,2,'',356),(11,'Caesar salad','A Caesar salad is a green salad of romaine lettuce and croutons dressed with lemon juice, olive oil, egg, Worcestershire sauce, garlic, Parmesan cheese, and black pepper.',325,1,2,'',327),(12,'Rice','Rice that has been cooked either by steaming',200,1,3,'',250),(13,'Fried potatoes','While KFC thinks they have the most delicious chicken (and we do not agree with that), our fried potato is absolutely winner. Try it and you will understand what do i mean.',180,1,3,'',300),(14,'Pasta','Carbonara is an Italian pasta dish from Rome made with egg, hard cheese, guanciale (or pancetta), and pepper.',220,1,3,'',250),(15,'Italian pizza','Pizza is a traditional Italian dish consisting of a yeasted flatbread topped with tomato sauce and cheese and baked in an oven. It is also topped with additional vegetables, and condiments. And of course meat.',1000,1,4,'',700),(16,'Vegetarian pizza','Pizza is a traditional Italian dish consisting of a yeasted flatbread topped with tomato sauce and cheese and baked in an oven. It is also topped with additional vegetables, and condiments. And of course WITHOUT meat.',1200,1,4,'',800),(17,'La Blanca','Lorem ipsum dolor sit amet, consectetur adipiscing elit. Donec eget ante gravida enim condimentum bibendum. Proin mauris eros, placerat non tristique viverra, suscipit ut massa.',1100,0,4,'',760),(18,'Hosomaki','Sushi is a Japanese dish of specially prepared vinegared rice, with some sugar and salt, combined with a variety of ingredients, such as seafood, vegetables, and occasionally tropical fruits.',850,1,6,'',400),(19,'Maki','Sushi is a special type of sushi specially prepared from vinegared rice, with some sugar and salt, combined with a variety of ingredients, such as seafood, vegetables, and occasionally tropical fruits.',760,1,6,'',440);
/*!40000 ALTER TABLE `dishes` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `orders`
--

DROP TABLE IF EXISTS orders;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE orders (
  `order_id` int(11) NOT NULL AUTO_INCREMENT,
  `order_status` enum('PAID','VIOLATED','CANCELED','EXPECTED') NOT NULL DEFAULT 'EXPECTED' COMMENT 'order_status ENUM. NOT NULL. This field contain the status of current order, which can change administrator. Expected - client should receive it in the nearest future. Payed - client have already payed for order. Violated - did not receive order. Canceled - client can cancel order beforehand. Index has been added in order to use it with WHERE queries.',
  `order_receipt_time` datetime NOT NULL COMMENT 'order_receipt_time field is used to set time when client would like to receive his order. DATETIME was selected because of convenience during display and insert operations, even if it costs 8 bite except of 4 in TIMESTAMP. NOT NULL. Index has been added in order to use it with WHERE queries.',
  `order_payment_method` enum('CLIENTBILL','CASH') NOT NULL DEFAULT 'CASH' COMMENT 'Payment method. Client could pay in cash or by client bill. Default - in cash.',
  `order_rating` enum('1','2','3','4','5') DEFAULT NULL COMMENT 'User can asses his orders. It also can be NULL when user did not rate his order.',
  `order_review` tinytext COMMENT 'User can leave some feedback about his order or not.',
  `users_ownerId` int(11) DEFAULT NULL COMMENT 'ON UPDATE - CASCADE during updating user id in the table users, the id of order owner will be also changed. ON DELETE - SET NULL. If user will be deleted, his orders would remain with the field "owner" NULL.',
  PRIMARY KEY (`order_id`),
  KEY `order_receipt_time_idx` (`order_receipt_time`),
  KEY `order_status_idx` (`order_status`),
  KEY `fk_orders_users1_idx` (`users_ownerId`),
  CONSTRAINT `fk_orders_users1` FOREIGN KEY (`users_ownerId`) REFERENCES `users` (`user_id`) ON DELETE SET NULL ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=37 DEFAULT CHARSET=utf8 COMMENT='This table contains orders, which consists of following parameters(id of order, id of client,time by which the order should be ready, status:"Payed","Violated","Canceled","Expected")';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `orders`
--

LOCK TABLES orders WRITE;
/*!40000 ALTER TABLE orders DISABLE KEYS */;
INSERT INTO orders VALUES (36,'PAID','2018-02-05 11:11:00','CASH','5','Nice!',2);
/*!40000 ALTER TABLE orders ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `orders_has_dishes`
--

DROP TABLE IF EXISTS `orders_has_dishes`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `orders_has_dishes` (
  `orders_order_id` int(11) NOT NULL COMMENT 'Many-to-many relation. Foreign key to concrete order from orders table.',
  `orders_has_dishes_quantity` tinyint(3) unsigned NOT NULL DEFAULT '1' COMMENT 'Portion quantity. Unsigned. ',
  `dishes_dish_id` int(11) NOT NULL COMMENT 'Many-to-many relation. Foreign key to concrete dish from dishes table.',
  PRIMARY KEY (`orders_order_id`,`dishes_dish_id`),
  KEY `fk_orders_has_dishes_dishes1_idx` (`dishes_dish_id`),
  KEY `fk_orders_has_dishes_orders_idx` (`orders_order_id`),
  CONSTRAINT `fk_orders_has_dishes_dishes1` FOREIGN KEY (`dishes_dish_id`) REFERENCES `dishes` (`dish_id`) ON UPDATE CASCADE,
  CONSTRAINT `fk_orders_has_dishes_orders` FOREIGN KEY (`orders_order_id`) REFERENCES orders (`order_id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='Many-to-many relation. Order can contain lots of different dishes with different portion quantity.';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `orders_has_dishes`
--

LOCK TABLES `orders_has_dishes` WRITE;
/*!40000 ALTER TABLE `orders_has_dishes` DISABLE KEYS */;
INSERT INTO `orders_has_dishes` VALUES (36,1,1);
/*!40000 ALTER TABLE `orders_has_dishes` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `roles`
--

DROP TABLE IF EXISTS `roles`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `roles` (
  `role_id` int(11) NOT NULL,
  `role_name` enum('CLIENT','ADMIN') NOT NULL,
  PRIMARY KEY (`role_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='Table with roles.';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `roles`
--

LOCK TABLES `roles` WRITE;
/*!40000 ALTER TABLE `roles` DISABLE KEYS */;
INSERT INTO `roles` VALUES (1,'ADMIN'),(2,'CLIENT');
/*!40000 ALTER TABLE `roles` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `users`
--

DROP TABLE IF EXISTS `users`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `users` (
  `user_id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'user_ id Synthetic primary key.',
  `user_name` varchar(45) NOT NULL,
  `user_surname` varchar(45) NOT NULL,
  `user_passport` varchar(45) NOT NULL,
  `user_phone` varchar(45) NOT NULL,
  `user_email` varchar(320) NOT NULL COMMENT 'user_email  NOT NULL because it is used for entrance to app.VARCHAR(320) because on the standard: 64 symbols for username + 1 symbol "@" + 255 symbols for domain name. Generally it is 320. Unique index (One e-mail - one user). Index has been added in order to use it with WHERE queries.',
  `user_password` char(40) NOT NULL COMMENT 'user_password field for password after sha-1 hash',
  `user_money` mediumint(11) NOT NULL DEFAULT '0' COMMENT 'user_money field with monet on user\'s client bill (max: 9999,99 у.е). UNSIGNED - client can not buy anything without money or when it is not enough money.',
  `user_loyaltyPoints` mediumint(11) NOT NULL DEFAULT '0' COMMENT 'user_loyaltyPoints this point user could receive for pre-ordering (max: 99.99) NOT UNSIGNED because administrator can reduce points until banning (When user violated pre-ordering rules. There will be min value, beyond which have possibility to ban this user.',
  `roles_role_id` int(11) NOT NULL,
  PRIMARY KEY (`user_id`),
  UNIQUE KEY `user_email_UNIQUE` (`user_email`),
  KEY `fk_users_roles2_idx` (`roles_role_id`),
  CONSTRAINT `fk_users_roles2` FOREIGN KEY (`roles_role_id`) REFERENCES `roles` (`role_id`) ON DELETE NO ACTION ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=19 DEFAULT CHARSET=utf8 COMMENT='This table contain information about users: id,e-mail, password, quantity of money in client bill and loyalty points. Also there is a field which define is it admin or not.';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `users`
--

LOCK TABLES `users` WRITE;
/*!40000 ALTER TABLE `users` DISABLE KEYS */;
INSERT INTO `users` VALUES (1,'John','Torety','MP1234567','+375 (29) 666-08-19','TESTADMIN@MAIL.RU','e34b27716b2cfe8064f47dcc7c3432fb9aeed66f',0,0,1),(2,'John','Moren','RP1234568','+375 (29) 777-08-19','TESTUSER@MAIL.RU','12dea96fec20593566ab75692c9949596833adc9',100,1,2),(3,'Tester','Tester','MP9872370','+375 (23) 777-32-37','TESTUSER1@MAIL.RU','12dea96fec20593566ab75692c9949596833adc9',0,0,2),(4,'Bob','Villo','MP9872370','+375 (44) 111-32-37','TESTUSER2@MAIL.RU','12dea96fec20593566ab75692c9949596833adc9',0,1,2),(5,'Tom','Hreffer','MP9872370','+375 (29) 222-32-37','TESTUSER3@MAIL.RU','12dea96fec20593566ab75692c9949596833adc9',0,5,2),(6,'Alex','Shunsky','MP9872370','+375 (29) 333-32-37','TESTUSER4@MAIL.RU','12dea96fec20593566ab75692c9949596833adc9',0,8,2),(7,'Bill','Mor','MP9872370','+375 (44) 444-32-37','TESTUSER5@MAIL.RU','12dea96fec20593566ab75692c9949596833adc9',0,3,2),(8,'Mary','Smith','MP9872370','+375 (25) 555-32-37','TESTUSER6@MAIL.RU','12dea96fec20593566ab75692c9949596833adc9',0,99,2),(9,'Si','Hog','MP9872370','+375 (29) 666-32-37','TESTUSER7@MAIL.RU','12dea96fec20593566ab75692c9949596833adc9',0,1,2),(10,'Ho','Jo','MP9872370','+375 (29) 777-32-00','TESTUSER8@MAIL.RU','12dea96fec20593566ab75692c9949596833adc9',0,1,2);
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

-- Dump completed on 2018-02-11 23:11:31
