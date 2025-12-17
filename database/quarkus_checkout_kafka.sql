-- phpMyAdmin SQL Dump
-- version 5.2.0
-- https://www.phpmyadmin.net/
--
-- Host: localhost
-- Generation Time: Dec 17, 2025 at 08:26 AM
-- Server version: 10.4.27-MariaDB
-- PHP Version: 7.4.33

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `quarkus_checkout_kafka`
--

-- --------------------------------------------------------

--
-- Table structure for table `orders`
--

CREATE TABLE `orders` (
  `quantity` int(11) DEFAULT NULL,
  `totalPrice` double DEFAULT NULL,
  `createdAt` datetime(6) DEFAULT NULL,
  `id` bigint(20) NOT NULL,
  `productId` varchar(255) DEFAULT NULL,
  `status` varchar(255) DEFAULT NULL,
  `userId` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `orders`
--

INSERT INTO `orders` (`quantity`, `totalPrice`, `createdAt`, `id`, `productId`, `status`, `userId`) VALUES
(2, 19.98, '2025-12-17 14:25:23.000000', 3, '123', 'COMPLETED', '1'),
(2, 19.98, '2025-12-17 14:25:27.000000', 4, '123', 'COMPLETED', '1');

-- --------------------------------------------------------

--
-- Table structure for table `orders_SEQ`
--

CREATE TABLE `orders_SEQ` (
  `next_val` bigint(20) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `orders_SEQ`
--

INSERT INTO `orders_SEQ` (`next_val`) VALUES
(51);

-- --------------------------------------------------------

--
-- Table structure for table `payments`
--

CREATE TABLE `payments` (
  `amount` double DEFAULT NULL,
  `createdAt` datetime(6) DEFAULT NULL,
  `id` bigint(20) NOT NULL,
  `orderId` bigint(20) DEFAULT NULL,
  `status` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `payments`
--

INSERT INTO `payments` (`amount`, `createdAt`, `id`, `orderId`, `status`) VALUES
(19.98, '2025-12-17 14:25:23.000000', 3, 3, 'SUCCESS'),
(19.98, '2025-12-17 14:25:27.000000', 4, 4, 'SUCCESS');

-- --------------------------------------------------------

--
-- Table structure for table `payments_SEQ`
--

CREATE TABLE `payments_SEQ` (
  `next_val` bigint(20) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `payments_SEQ`
--

INSERT INTO `payments_SEQ` (`next_val`) VALUES
(1);

--
-- Indexes for dumped tables
--

--
-- Indexes for table `orders`
--
ALTER TABLE `orders`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `payments`
--
ALTER TABLE `payments`
  ADD PRIMARY KEY (`id`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `orders`
--
ALTER TABLE `orders`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=5;

--
-- AUTO_INCREMENT for table `payments`
--
ALTER TABLE `payments`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=5;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
