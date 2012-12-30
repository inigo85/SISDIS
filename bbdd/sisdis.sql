-- phpMyAdmin SQL Dump
-- version 3.5.2.2
-- http://www.phpmyadmin.net
--
-- Servidor: 127.0.0.1
-- Tiempo de generación: 30-12-2012 a las 20:00:43
-- Versión del servidor: 5.5.27
-- Versión de PHP: 5.4.7

SET SQL_MODE="NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;

--
-- Base de datos: `sisdis`
--

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `tarea`
--

CREATE TABLE IF NOT EXISTS `tarea` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `nombre` varchar(100) NOT NULL,
  `descripcion` text NOT NULL,
  `fecha_inicio` datetime NOT NULL,
  `fecha_fin` datetime NOT NULL,
  `todo_el_dia` tinyint(1) NOT NULL DEFAULT '0',
  `idUsuario` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `idUsuario` (`idUsuario`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=34 ;

--
-- Volcado de datos para la tabla `tarea`
--

INSERT INTO `tarea` (`id`, `nombre`, `descripcion`, `fecha_inicio`, `fecha_fin`, `todo_el_dia`, `idUsuario`) VALUES
(25, 'evento111', 'evento111', '2012-12-04 00:00:00', '2012-12-04 00:00:00', 1, 3),
(26, 'evento2', 'evento2', '2012-12-12 00:00:00', '2012-12-12 00:00:00', 0, 3),
(28, 'evento4', 'evento4', '2012-12-07 09:22:00', '2012-12-07 00:00:00', 1, 3),
(29, 'juanevento', '', '2012-12-05 00:00:00', '2012-12-05 07:00:00', 0, 8),
(30, 'juanevento22', 'juanevento22', '2012-12-13 00:00:00', '2012-12-13 00:00:00', 1, 8),
(31, 'asdfasd', 'sdfa', '2012-12-28 00:00:00', '2012-12-28 00:00:00', 1, 3),
(32, 'dfa', '', '2012-12-11 00:00:00', '2012-12-11 07:00:00', 0, 8),
(33, 'dafsd', '', '2012-12-19 06:30:00', '2012-12-19 07:30:00', 0, 8);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `usuario`
--

CREATE TABLE IF NOT EXISTS `usuario` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `nombre` varchar(100) NOT NULL,
  `tipo` char(1) NOT NULL DEFAULT 'N',
  `email` varchar(40) NOT NULL,
  `contraseña` varchar(100) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `id_2` (`id`),
  KEY `id` (`id`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=10 ;

--
-- Volcado de datos para la tabla `usuario`
--

INSERT INTO `usuario` (`id`, `nombre`, `tipo`, `email`, `contraseña`) VALUES
(3, 'a', 'A', 'a@a.com', 'a'),
(8, 'juan', 'N', 'juan@juanchop.com', '1234'),
(9, 'jose', 'N', 'jose@hotmail.com', 'jose');

--
-- Restricciones para tablas volcadas
--

--
-- Filtros para la tabla `tarea`
--
ALTER TABLE `tarea`
  ADD CONSTRAINT `tarea_ibfk_1` FOREIGN KEY (`idUsuario`) REFERENCES `usuario` (`id`) ON DELETE CASCADE ON UPDATE CASCADE;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
