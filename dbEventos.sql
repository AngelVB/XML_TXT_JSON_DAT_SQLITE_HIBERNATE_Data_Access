-- phpMyAdmin SQL Dump
-- version 3.4.11.1deb2+deb7u1
-- http://www.phpmyadmin.net
--
-- Servidor: localhost
-- Tiempo de generación: 19-02-2015 a las 21:30:29
-- Versión del servidor: 5.5.41
-- Versión de PHP: 5.4.36-0+deb7u3

SET SQL_MODE="NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;

--
-- Base de datos: `larrysion`
--

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `contacto`
--

CREATE TABLE IF NOT EXISTS `contacto` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `nombre` varchar(50) DEFAULT NULL,
  `telefono` varchar(12) DEFAULT NULL,
  `email` varchar(45) DEFAULT NULL,
  `user` varchar(45) DEFAULT NULL,
  `password` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `user` (`user`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 AUTO_INCREMENT=1 ;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `contacto_has_contacto`
--

CREATE TABLE IF NOT EXISTS `contacto_has_contacto` (
  `contacto_id` int(11) NOT NULL,
  `contacto_id1` int(11) NOT NULL,
  PRIMARY KEY (`contacto_id`,`contacto_id1`),
  KEY `fk_contacto_has_contacto_contacto2_idx` (`contacto_id1`),
  KEY `fk_contacto_has_contacto_contacto1_idx` (`contacto_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `contacto_has_evento`
--

CREATE TABLE IF NOT EXISTS `contacto_has_evento` (
  `contacto_id` int(11) NOT NULL,
  `evento_id` int(11) NOT NULL,
  `acude` tinyint(1) DEFAULT '0',
  PRIMARY KEY (`contacto_id`,`evento_id`),
  KEY `fk_contacto_has_evento_evento1_idx` (`evento_id`),
  KEY `fk_contacto_has_evento_contacto1_idx` (`contacto_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `evento`
--

CREATE TABLE IF NOT EXISTS `evento` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `descripcion` varchar(300) DEFAULT NULL,
  `inicio` datetime DEFAULT NULL,
  `finaliza` datetime DEFAULT NULL,
  `contacto_id` int(11) NOT NULL,
  `lugar_id` int(11) NOT NULL,
  `titulo` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_evento_contacto_idx` (`contacto_id`),
  KEY `fk_evento_lugar1_idx` (`lugar_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 AUTO_INCREMENT=1 ;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `lugar`
--

CREATE TABLE IF NOT EXISTS `lugar` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `calle` varchar(100) DEFAULT NULL,
  `codpost` varchar(5) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 AUTO_INCREMENT=1 ;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `nota`
--

CREATE TABLE IF NOT EXISTS `nota` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `fecha` datetime DEFAULT NULL,
  `texto` text,
  `importancia` varchar(45) DEFAULT NULL,
  `contacto_id` int(11) NOT NULL,
  `resumen` varchar(45) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_nota_contacto1_idx` (`contacto_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 AUTO_INCREMENT=1 ;

--
-- Restricciones para tablas volcadas
--

--
-- Filtros para la tabla `contacto_has_contacto`
--
ALTER TABLE `contacto_has_contacto`
  ADD CONSTRAINT `fk_contacto_has_contacto_contacto1` FOREIGN KEY (`contacto_id`) REFERENCES `contacto` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `fk_contacto_has_contacto_contacto2` FOREIGN KEY (`contacto_id1`) REFERENCES `contacto` (`id`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Filtros para la tabla `contacto_has_evento`
--
ALTER TABLE `contacto_has_evento`
  ADD CONSTRAINT `fk_contacto_has_evento_contacto1` FOREIGN KEY (`contacto_id`) REFERENCES `contacto` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `fk_contacto_has_evento_evento1` FOREIGN KEY (`evento_id`) REFERENCES `evento` (`id`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Filtros para la tabla `evento`
--
ALTER TABLE `evento`
  ADD CONSTRAINT `fk_evento_contacto` FOREIGN KEY (`contacto_id`) REFERENCES `contacto` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `fk_evento_lugar1` FOREIGN KEY (`lugar_id`) REFERENCES `lugar` (`id`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Filtros para la tabla `nota`
--
ALTER TABLE `nota`
  ADD CONSTRAINT `fk_nota_contacto1` FOREIGN KEY (`contacto_id`) REFERENCES `contacto` (`id`) ON DELETE CASCADE ON UPDATE CASCADE;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
