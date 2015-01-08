-- phpMyAdmin SQL Dump
-- version 4.2.12deb1
-- http://www.phpmyadmin.net
--
-- Servidor: localhost
-- Tiempo de generación: 08-01-2015 a las 17:34:36
-- Versión del servidor: 5.5.40-1
-- Versión de PHP: 5.6.2-1

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;

--
-- Base de datos: `fightclub`
--
CREATE DATABASE IF NOT EXISTS `fightclub` DEFAULT CHARACTER SET latin1 COLLATE latin1_swedish_ci;
USE `fightclub`;

DELIMITER $$
--
-- Procedimientos
--
DROP PROCEDURE IF EXISTS `deleteboxer`$$
CREATE DEFINER=`root`@`localhost` PROCEDURE `deleteboxer`()
    READS SQL DATA
    SQL SECURITY INVOKER
DELETE FROM boxer$$

DROP PROCEDURE IF EXISTS `plus_sperience`$$
CREATE DEFINER=`root`@`localhost` PROCEDURE `plus_sperience`()
    NO SQL
procedimiento:begin
declare id_old_coach int;
declare sperience_old int;

set id_old_coach = (select id from coach where birthday = (select min(birthday) from coach));

set sperience_old = (select sperience from coach where id = id_old_coach);

update coach set sperience = sperience_old+1  where id = id_old_coach;
end procedimiento$$

--
-- Funciones
--
DROP FUNCTION IF EXISTS `oldest_coach`$$
CREATE DEFINER=`root`@`localhost` FUNCTION `oldest_coach`() RETURNS varchar(150) CHARSET latin1
return (select name from coach where birthday = (select min(birthday) from coach))$$

DROP FUNCTION IF EXISTS `win_boxer`$$
CREATE DEFINER=`root`@`localhost` FUNCTION `win_boxer`() RETURNS varchar(150) CHARSET latin1
return (select name from boxer where wins = (select max(wins)
            from boxer))$$

DELIMITER ;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `boxer`
--

DROP TABLE IF EXISTS `boxer`;
CREATE TABLE IF NOT EXISTS `boxer` (
`id` int(10) unsigned NOT NULL,
  `name` varchar(50) NOT NULL,
  `wins` int(11) DEFAULT '0',
  `lose` int(11) DEFAULT '0',
  `weight` double DEFAULT NULL,
  `id_dojo` int(10) unsigned DEFAULT NULL,
  `id_coach` int(10) unsigned DEFAULT NULL
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=latin1;

--
-- Volcado de datos para la tabla `boxer`
--

INSERT INTO `boxer` (`id`, `name`, `wins`, `lose`, `weight`, `id_dojo`, `id_coach`) VALUES
(7, 'khg', 0, 0, 0, 9, 1);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `coach`
--

DROP TABLE IF EXISTS `coach`;
CREATE TABLE IF NOT EXISTS `coach` (
`id` int(10) unsigned NOT NULL,
  `name` varchar(50) NOT NULL,
  `birthday` date DEFAULT NULL,
  `sperience` int(11) DEFAULT NULL,
  `id_dojo` int(10) unsigned DEFAULT NULL
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=latin1;

--
-- Volcado de datos para la tabla `coach`
--

INSERT INTO `coach` (`id`, `name`, `birthday`, `sperience`, `id_dojo`) VALUES
(1, 'Javier', '1944-08-06', 55, 9),
(2, 'Nico', '1965-01-05', 30, NULL);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `dojo`
--

DROP TABLE IF EXISTS `dojo`;
CREATE TABLE IF NOT EXISTS `dojo` (
`id` int(10) unsigned NOT NULL,
  `name` varchar(50) NOT NULL,
  `street` varchar(150) DEFAULT NULL,
  `inauguration` date DEFAULT NULL
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=latin1;

--
-- Volcado de datos para la tabla `dojo`
--

INSERT INTO `dojo` (`id`, `name`, `street`, `inauguration`) VALUES
(9, 'karatekan', 'c/maria claret', '1950-01-03'),
(10, 'olimpic', 'c/garcia galdeano', '1970-01-05');

--
-- Índices para tablas volcadas
--

--
-- Indices de la tabla `boxer`
--
ALTER TABLE `boxer`
 ADD PRIMARY KEY (`id`), ADD KEY `id_dojo` (`id_dojo`), ADD KEY `id_coach` (`id_coach`);

--
-- Indices de la tabla `coach`
--
ALTER TABLE `coach`
 ADD PRIMARY KEY (`id`), ADD KEY `id_dojo` (`id_dojo`);

--
-- Indices de la tabla `dojo`
--
ALTER TABLE `dojo`
 ADD PRIMARY KEY (`id`);

--
-- AUTO_INCREMENT de las tablas volcadas
--

--
-- AUTO_INCREMENT de la tabla `boxer`
--
ALTER TABLE `boxer`
MODIFY `id` int(10) unsigned NOT NULL AUTO_INCREMENT,AUTO_INCREMENT=8;
--
-- AUTO_INCREMENT de la tabla `coach`
--
ALTER TABLE `coach`
MODIFY `id` int(10) unsigned NOT NULL AUTO_INCREMENT,AUTO_INCREMENT=4;
--
-- AUTO_INCREMENT de la tabla `dojo`
--
ALTER TABLE `dojo`
MODIFY `id` int(10) unsigned NOT NULL AUTO_INCREMENT,AUTO_INCREMENT=11;
--
-- Restricciones para tablas volcadas
--

--
-- Filtros para la tabla `boxer`
--
ALTER TABLE `boxer`
ADD CONSTRAINT `boxer_ibfk_1` FOREIGN KEY (`id_dojo`) REFERENCES `dojo` (`id`) ON DELETE SET NULL ON UPDATE NO ACTION,
ADD CONSTRAINT `boxer_ibfk_2` FOREIGN KEY (`id_coach`) REFERENCES `coach` (`id`) ON DELETE SET NULL ON UPDATE NO ACTION;

--
-- Filtros para la tabla `coach`
--
ALTER TABLE `coach`
ADD CONSTRAINT `coach_ibfk_1` FOREIGN KEY (`id_dojo`) REFERENCES `dojo` (`id`) ON DELETE SET NULL ON UPDATE NO ACTION;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
