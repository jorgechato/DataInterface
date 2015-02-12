-- phpMyAdmin SQL Dump
-- version 4.2.12deb1
-- http://www.phpmyadmin.net
--
-- Servidor: localhost
-- Tiempo de generación: 12-02-2015 a las 20:48:59
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
) ENGINE=InnoDB AUTO_INCREMENT=21 DEFAULT CHARSET=latin1;

--
-- RELACIONES PARA LA TABLA `boxer`:
--   `id_dojo`
--       `dojo` -> `id`
--   `id_coach`
--       `coach` -> `id`
--

--
-- Volcado de datos para la tabla `boxer`
--

INSERT INTO `boxer` (`id`, `name`, `wins`, `lose`, `weight`, `id_dojo`, `id_coach`) VALUES
(17, 'jorge', 15, 25, 0, 9, 1),
(19, 'luisma', 20, 20, 20, 9, 1),
(20, 'david', 20, 20, 20, 9, 1);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `boxer_fight`
--

DROP TABLE IF EXISTS `boxer_fight`;
CREATE TABLE IF NOT EXISTS `boxer_fight` (
  `id_boxer` int(10) unsigned NOT NULL DEFAULT '0',
  `id_fight` int(10) unsigned NOT NULL DEFAULT '0'
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- RELACIONES PARA LA TABLA `boxer_fight`:
--   `id_boxer`
--       `boxer` -> `id`
--   `id_fight`
--       `fight` -> `id`
--

--
-- Volcado de datos para la tabla `boxer_fight`
--

INSERT INTO `boxer_fight` (`id_boxer`, `id_fight`) VALUES
(17, 1),
(19, 1);

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
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=latin1;

--
-- RELACIONES PARA LA TABLA `coach`:
--   `id_dojo`
--       `dojo` -> `id`
--

--
-- Volcado de datos para la tabla `coach`
--

INSERT INTO `coach` (`id`, `name`, `birthday`, `sperience`, `id_dojo`) VALUES
(1, 'Javier', '1944-08-06', 57, 9),
(2, 'Nico', '1965-01-05', 30, 10);

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
) ENGINE=InnoDB AUTO_INCREMENT=18 DEFAULT CHARSET=latin1;

--
-- Volcado de datos para la tabla `dojo`
--

INSERT INTO `dojo` (`id`, `name`, `street`, `inauguration`) VALUES
(9, 'karatekan', 'c/maria claret', '1950-01-03'),
(10, 'olimpic', 'c/ garcia galdeano', '1970-01-05'),
(14, 'bbb', 'aaa', '2015-02-11');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `fight`
--

DROP TABLE IF EXISTS `fight`;
CREATE TABLE IF NOT EXISTS `fight` (
`id` int(10) unsigned NOT NULL,
  `name` varchar(50) NOT NULL,
  `street` varchar(150) DEFAULT NULL,
  `day` date DEFAULT NULL
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=latin1;

--
-- Volcado de datos para la tabla `fight`
--

INSERT INTO `fight` (`id`, `name`, `street`, `day`) VALUES
(1, 'Muerte subita', 'wfds', '2015-02-11');

--
-- Índices para tablas volcadas
--

--
-- Indices de la tabla `boxer`
--
ALTER TABLE `boxer`
 ADD PRIMARY KEY (`id`), ADD KEY `id_dojo` (`id_dojo`), ADD KEY `id_coach` (`id_coach`);

--
-- Indices de la tabla `boxer_fight`
--
ALTER TABLE `boxer_fight`
 ADD PRIMARY KEY (`id_boxer`,`id_fight`), ADD KEY `id_boxer` (`id_boxer`), ADD KEY `id_fight` (`id_fight`);

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
-- Indices de la tabla `fight`
--
ALTER TABLE `fight`
 ADD PRIMARY KEY (`id`);

--
-- AUTO_INCREMENT de las tablas volcadas
--

--
-- AUTO_INCREMENT de la tabla `boxer`
--
ALTER TABLE `boxer`
MODIFY `id` int(10) unsigned NOT NULL AUTO_INCREMENT,AUTO_INCREMENT=21;
--
-- AUTO_INCREMENT de la tabla `coach`
--
ALTER TABLE `coach`
MODIFY `id` int(10) unsigned NOT NULL AUTO_INCREMENT,AUTO_INCREMENT=3;
--
-- AUTO_INCREMENT de la tabla `dojo`
--
ALTER TABLE `dojo`
MODIFY `id` int(10) unsigned NOT NULL AUTO_INCREMENT,AUTO_INCREMENT=18;
--
-- AUTO_INCREMENT de la tabla `fight`
--
ALTER TABLE `fight`
MODIFY `id` int(10) unsigned NOT NULL AUTO_INCREMENT,AUTO_INCREMENT=2;
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
-- Filtros para la tabla `boxer_fight`
--
ALTER TABLE `boxer_fight`
ADD CONSTRAINT `boxer_fight_ibfk_1` FOREIGN KEY (`id_boxer`) REFERENCES `boxer` (`id`) ON DELETE CASCADE ON UPDATE NO ACTION,
ADD CONSTRAINT `boxer_fight_ibfk_2` FOREIGN KEY (`id_fight`) REFERENCES `fight` (`id`) ON DELETE CASCADE ON UPDATE NO ACTION;

--
-- Filtros para la tabla `coach`
--
ALTER TABLE `coach`
ADD CONSTRAINT `coach_ibfk_1` FOREIGN KEY (`id_dojo`) REFERENCES `dojo` (`id`) ON DELETE SET NULL ON UPDATE NO ACTION;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
