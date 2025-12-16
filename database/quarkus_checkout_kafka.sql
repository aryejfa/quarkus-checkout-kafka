-- phpMyAdmin SQL Dump
-- version 5.2.0
-- https://www.phpmyadmin.net/
--
-- Host: localhost
-- Generation Time: Dec 16, 2025 at 04:08 PM
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
  `id` bigint(20) NOT NULL,
  `createdAt` datetime(6) DEFAULT NULL,
  `productId` bigint(20) DEFAULT NULL,
  `quantity` int(11) DEFAULT NULL,
  `status` varchar(255) DEFAULT NULL,
  `totalPrice` double DEFAULT NULL,
  `userId` bigint(20) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `orders`
--

INSERT INTO `orders` (`id`, `createdAt`, `productId`, `quantity`, `status`, `totalPrice`, `userId`) VALUES
(151, '2025-12-16 22:05:41.000000', 123, 2, 'COMPLETED', 19.98, 1),
(152, '2025-12-16 22:05:57.000000', 123, 2, 'COMPLETED', 19.98, 1);

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
(201);

-- --------------------------------------------------------

--
-- Table structure for table `payments`
--

CREATE TABLE `payments` (
  `id` bigint(20) NOT NULL,
  `amount` double DEFAULT NULL,
  `createdAt` datetime(6) DEFAULT NULL,
  `orderId` bigint(20) DEFAULT NULL,
  `providerResponse` varchar(255) DEFAULT NULL,
  `status` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `payments`
--

INSERT INTO `payments` (`id`, `amount`, `createdAt`, `orderId`, `providerResponse`, `status`) VALUES
(51, 19.98, '2025-12-16 22:05:41.000000', 151, '{\"status\":\"SUCCESS\",\"transactionId\":\"tx-1765897541889\"}', 'COMPLETED'),
(52, 19.98, '2025-12-16 22:05:57.000000', 152, '{\"status\":\"SUCCESS\",\"transactionId\":\"tx-1765897557744\"}', 'COMPLETED');

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
(101);

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
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
