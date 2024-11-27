-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Máy chủ: 127.0.0.1
-- Thời gian đã tạo: Th10 27, 2024 lúc 09:30 AM
-- Phiên bản máy phục vụ: 10.4.32-MariaDB
-- Phiên bản PHP: 8.2.12

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Cơ sở dữ liệu: `ecommerce_db`
--

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `brand`
--

CREATE TABLE `brand` (
  `id` bigint(20) NOT NULL,
  `name` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Đang đổ dữ liệu cho bảng `brand`
--

INSERT INTO `brand` (`id`, `name`) VALUES
(1, 'Logitech'),
(2, 'Mchose'),
(3, 'Corsair'),
(4, 'Keychron'),
(5, 'MSI'),
(6, 'Apple'),
(7, 'Samsung'),
(9, 'ASUS'),
(11, 'Oppo');

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `category`
--

CREATE TABLE `category` (
  `id` bigint(20) NOT NULL,
  `image` varchar(255) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Đang đổ dữ liệu cho bảng `category`
--

INSERT INTO `category` (`id`, `image`, `name`) VALUES
(1, 'laptop.jpg', 'Laptop'),
(2, 'mouse.jfif', 'Mouse'),
(3, 'keyboard.jfif', 'keyboard'),
(15, 'headphone.jfif', 'Headphone');

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `order`
--

CREATE TABLE `order` (
  `id` bigint(20) NOT NULL,
  `order_date` datetime(6) NOT NULL,
  `status` varchar(255) NOT NULL,
  `total_amount` double NOT NULL,
  `user_id` bigint(20) NOT NULL,
  `payment_method` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Đang đổ dữ liệu cho bảng `order`
--

INSERT INTO `order` (`id`, `order_date`, `status`, `total_amount`, `user_id`, `payment_method`) VALUES
(9, '2024-11-25 19:52:54.000000', 'Completed', 900, 1, 'COD'),
(10, '2024-11-25 19:53:10.000000', 'Completed', 50, 1, 'COD'),
(11, '2024-11-25 19:53:56.000000', 'In Progress', 1838, 1, 'COD'),
(12, '2024-11-25 20:01:11.000000', 'In Progress', 1628, 1, ''),
(13, '2024-11-25 20:02:25.000000', 'In Progress', 4299, 1, 'COD'),
(14, '2024-11-26 15:57:55.000000', 'In Progress', 1599, 1, 'COD'),
(15, '2024-11-27 14:06:43.000000', 'In Progress', 12401, 1, 'COD'),
(16, '2024-11-26 17:08:39.000000', 'In Progress', 1599, 11, 'COD'),
(17, '2024-11-27 14:07:48.000000', 'In Progress', 900, 8, 'COD'),
(18, '2024-11-27 15:18:59.000000', 'In Progress', 4449, 1, 'COD');

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `order_item`
--

CREATE TABLE `order_item` (
  `id` bigint(20) NOT NULL,
  `price` double NOT NULL,
  `quantity` int(11) NOT NULL,
  `order_id` bigint(20) NOT NULL,
  `product_id` bigint(20) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Đang đổ dữ liệu cho bảng `order_item`
--

INSERT INTO `order_item` (`id`, `price`, `quantity`, `order_id`, `product_id`) VALUES
(11, 900, 1, 9, 5),
(12, 50, 1, 10, 4),
(13, 900, 1, 11, 5),
(14, 799, 1, 11, 7),
(15, 139, 1, 11, 14),
(16, 900, 1, 12, 5),
(17, 699, 1, 12, 6),
(18, 29, 1, 12, 10),
(19, 1800, 2, 13, 5),
(20, 699, 1, 13, 6),
(21, 900, 1, 14, 5),
(22, 699, 1, 14, 6),
(23, 799, 1, 15, 7),
(24, 1398, 2, 15, 6),
(25, 2700, 3, 15, 5),
(26, 100, 1, 15, 3),
(27, 278, 2, 15, 14),
(28, 900, 1, 16, 5),
(29, 699, 1, 16, 6),
(30, 50, 1, 15, 4),
(31, 900, 1, 17, 5),
(32, 1800, 2, 18, 5),
(33, 50, 1, 18, 4),
(34, 799, 1, 18, 7);

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `product`
--

CREATE TABLE `product` (
  `id` bigint(20) NOT NULL,
  `available` bit(1) DEFAULT NULL,
  `color` varchar(255) DEFAULT NULL,
  `description` varchar(255) DEFAULT NULL,
  `image` varchar(255) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  `price` double DEFAULT NULL,
  `quantity` int(11) DEFAULT NULL,
  `brand_id` bigint(20) DEFAULT NULL,
  `category_id` bigint(20) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Đang đổ dữ liệu cho bảng `product`
--

INSERT INTO `product` (`id`, `available`, `color`, `description`, `image`, `name`, `price`, `quantity`, `brand_id`, `category_id`) VALUES
(3, b'1', 'Black', 'TKL 3-mode aluminum frame mechanical keyboard, with weights', 'MCHOSEGx87.JPG', 'Mchose GX87', 100, 90, 2, 3),
(4, b'1', 'Black', 'The Logitech G502 Hero uses Logitech G HUB to program your favorite commands and macros to up to 11 buttons.', 'G502Hero.JPG', 'Logitech G502 Hero', 50, 23, 1, 2),
(5, b'1', 'black', 'i5 12450H/16GB/512GB/6GB RTX4050/144Hz/Win11 (460VN)', 'msi-gf63-thin-12ve-i5.jpg', 'Laptop MSI Gaming GF63 Thin 12VE', 900, 7, 5, 1),
(6, b'1', 'Black', 'i5 12450H/16GB/512GB/4GB RTX2050/144Hz/Balo/Win11 (2046VN)', 'msi-thin-15-b12ucx-i5.jpg', 'Laptop MSI Gaming Thin 15 B12UCX', 699, 4, 5, 1),
(7, b'1', 'White', 'Máy mới 100%, đầy đủ phụ kiện từ nhà sản xuất. Sản phẩm có mã SA/A (được Apple Việt Nam phân phối chính thức).', 'MacBookAirM1.jpg', 'Apple MacBook Air M1 256GB 2020', 799, 7, 6, 1),
(8, b'1', 'Blue', 'QMK/Gasket Mount/HOTSWAP/RGB - Layout 6x', 'mins-keychron.png', 'Keychron Q2 Navy Blue', 199, 10, 4, 3),
(9, b'1', 'White', 'Thiết kế nhỏ gọn, sang trọng', 'MagicMouse.jfif', 'Apple Magic Mouse', 59, 10, 6, 2),
(10, b'1', 'Black', 'Độ dài dây (cm)210, Led RGB. Số lượng nút bấm 6. Mắt đọc: Mercury Sensor', 'G102.jfif', 'Logitech G102 LightSync', 29, 22, 1, 2),
(11, b'1', 'White', 'Loại: Khung nhựa, tạ đáy, led viền và led gầm', 'MchoseK99.jpg', 'Mchose K99 full size', 69, 10, 2, 3),
(12, b'1', 'Silver', 'Intel Celeron - N4500', 'samsung-galaxy-chromebook.jpg', 'Samsung Galaxy Chromebook Go XE310XDA', 399, 10, 7, 1),
(13, b'1', 'Black', 'AMD Ryzen 7 7435HS | RTX 4050 |16GB | 512GB | 15.6 inch FHD 144Hz | Win 11 | Xám', 'laptop_asus_tuf_.jpg', 'Laptop gaming ASUS TUF Gaming A15 FA507NUR LP101W', 1099, 10, 9, 1),
(14, b'1', 'White', 'USB, 3.5mm, Wireless', 'G933.jfif', 'Logitech G933 Artemis Spectrum 7.1 Wireless ', 139, 2, 1, 15),
(15, b'1', 'Black', 'Được thiết kế với và dành cho các chuyên gia. Âm thanh vòm 7.1 thế hệ mới và các màng loa PRO-G 50 mm đảm bảo âm thanh chơi game cao cấp.', 'GProX.png', 'Logitech GPRO X', 149, 24, 1, 15);

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `user`
--

CREATE TABLE `user` (
  `id` bigint(20) NOT NULL,
  `address` varchar(255) NOT NULL,
  `email` varchar(255) NOT NULL,
  `is_enable` bit(1) NOT NULL,
  `password` varchar(255) NOT NULL,
  `phone` varchar(255) NOT NULL,
  `role` varchar(255) NOT NULL,
  `username` varchar(255) NOT NULL,
  `full_name` varchar(255) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Đang đổ dữ liệu cho bảng `user`
--

INSERT INTO `user` (`id`, `address`, `email`, `is_enable`, `password`, `phone`, `role`, `username`, `full_name`) VALUES
(1, '1769 Phạm Thế Hiển P6 Q8', 'tranminhduc04@gmail.com', b'1', '$2a$10$t2xte95YK1kvCc0NRl6v4exWLAAJyatg26QRdIhJ2U2mhvVbqUBSm', '1234567890', 'ROLE_USER', 'MinhDuc04', 'Trần Minh Đức'),
(6, '1243 admin addresss', 'admin@gmail.com', b'1', '$2a$10$JmA5cxVQd6uqJImc9iRabuANTUaH.8aA5DV1exmNTWufVJ0MHtwV.', '42353623', 'ROLE_ADMIN', 'admin', 'admin'),
(8, 'user1 addresss', 'user1@gmail.com', b'1', '$2a$10$Xp.xLaUMlYxtcnsZv7cyneF2APSCp8YdszZZVA.up3ZYvGyKUl/yi', '543254235432', 'ROLE_USER', 'user1', 'user1'),
(9, 'user2@gmail.com', 'user2@gmail.com', b'1', '$2a$10$BbCHILv9Gxc9nDMKBcz/SOGg5fjrhYcMtK8BACh6q7bWdumUqln7S', '6432114143', 'ROLE_USER', 'user2', 'user2'),
(10, 'user3@gmail.com', 'user3@gmail.com', b'1', '$2a$10$Z/wh4keVOIVlL6AybASbO.zr9dj9Gqhb39AVBHBtFDm7Bs9OjIFwO', '54312546465', 'ROLE_USER', 'user3', 'user3'),
(11, 'user4@gmail.com', 'user4@gmail.com', b'1', '$2a$10$JAWBB4wFmMWOw3vbxJGVVuSHMY58yIEty8sZgOpNQz8YdqAZYZ2na', '01759214', 'ROLE_USER', 'user4', 'user4'),
(12, 'user6 address', 'user6@gmail.com', b'1', '$2a$10$RMkH/i/S2kx9P0WyxZ75/O88MKXIL/bHIkq/3B8kmWGdT8FbxsHNS', '46123818141', 'ROLE_USER', 'user6', 'user6'),
(13, 'user 5 address', 'user5@gmail.com', b'1', '$2a$10$PxwS5tDD9uFu5q1EADgWH.hUFf36Y2W7Pwvm6Nm6Jy48qPYaPvTD6', '5424136532', 'ROLE_USER', 'user5', 'user5');

--
-- Chỉ mục cho các bảng đã đổ
--

--
-- Chỉ mục cho bảng `brand`
--
ALTER TABLE `brand`
  ADD PRIMARY KEY (`id`);

--
-- Chỉ mục cho bảng `category`
--
ALTER TABLE `category`
  ADD PRIMARY KEY (`id`);

--
-- Chỉ mục cho bảng `order`
--
ALTER TABLE `order`
  ADD PRIMARY KEY (`id`),
  ADD KEY `FKcpl0mjoeqhxvgeeeq5piwpd3i` (`user_id`);

--
-- Chỉ mục cho bảng `order_item`
--
ALTER TABLE `order_item`
  ADD PRIMARY KEY (`id`),
  ADD KEY `FKs234mi6jususbx4b37k44cipy` (`order_id`),
  ADD KEY `FK551losx9j75ss5d6bfsqvijna` (`product_id`);

--
-- Chỉ mục cho bảng `product`
--
ALTER TABLE `product`
  ADD PRIMARY KEY (`id`),
  ADD KEY `FKs6cydsualtsrprvlf2bb3lcam` (`brand_id`),
  ADD KEY `FK1mtsbur82frn64de7balymq9s` (`category_id`);

--
-- Chỉ mục cho bảng `user`
--
ALTER TABLE `user`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `UKob8kqyqqgmefl0aco34akdtpe` (`email`),
  ADD UNIQUE KEY `UK589idila9li6a4arw1t8ht1gx` (`phone`),
  ADD UNIQUE KEY `UKsb8bbouer5wak8vyiiy4pf2bx` (`username`);

--
-- AUTO_INCREMENT cho các bảng đã đổ
--

--
-- AUTO_INCREMENT cho bảng `brand`
--
ALTER TABLE `brand`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=12;

--
-- AUTO_INCREMENT cho bảng `category`
--
ALTER TABLE `category`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=16;

--
-- AUTO_INCREMENT cho bảng `order`
--
ALTER TABLE `order`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=19;

--
-- AUTO_INCREMENT cho bảng `order_item`
--
ALTER TABLE `order_item`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=35;

--
-- AUTO_INCREMENT cho bảng `product`
--
ALTER TABLE `product`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=16;

--
-- AUTO_INCREMENT cho bảng `user`
--
ALTER TABLE `user`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=14;

--
-- Các ràng buộc cho các bảng đã đổ
--

--
-- Các ràng buộc cho bảng `order`
--
ALTER TABLE `order`
  ADD CONSTRAINT `FKcpl0mjoeqhxvgeeeq5piwpd3i` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`);

--
-- Các ràng buộc cho bảng `order_item`
--
ALTER TABLE `order_item`
  ADD CONSTRAINT `FK551losx9j75ss5d6bfsqvijna` FOREIGN KEY (`product_id`) REFERENCES `product` (`id`),
  ADD CONSTRAINT `FKs234mi6jususbx4b37k44cipy` FOREIGN KEY (`order_id`) REFERENCES `order` (`id`);

--
-- Các ràng buộc cho bảng `product`
--
ALTER TABLE `product`
  ADD CONSTRAINT `FK1mtsbur82frn64de7balymq9s` FOREIGN KEY (`category_id`) REFERENCES `category` (`id`),
  ADD CONSTRAINT `FKs6cydsualtsrprvlf2bb3lcam` FOREIGN KEY (`brand_id`) REFERENCES `brand` (`id`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
