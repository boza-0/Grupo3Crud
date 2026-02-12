-- =========================================================
-- BDRURAL (MySQL 5.6)
-- =========================================================

DROP DATABASE IF EXISTS BDRURAL;
CREATE DATABASE BDRURAL
  DEFAULT CHARACTER SET utf8
  DEFAULT COLLATE utf8_general_ci;

USE BDRURAL;

-- ---------------------------------------------------------
-- Tabla: tipos
-- ---------------------------------------------------------
DROP TABLE IF EXISTS tipos;
CREATE TABLE tipos (
  codigo INT NOT NULL,
  descripcion VARCHAR(60) NOT NULL,
  CONSTRAINT pk_tipos PRIMARY KEY (codigo)
) ENGINE=InnoDB;

-- ---------------------------------------------------------
-- Tabla: alojamientos
-- ---------------------------------------------------------
DROP TABLE IF EXISTS alojamientos;
CREATE TABLE alojamientos (
  referencia INT NOT NULL AUTO_INCREMENT,
  nombre VARCHAR(80) NOT NULL,
  poblacion VARCHAR(80) NOT NULL,
  provincia VARCHAR(60) NOT NULL,
  capacidad INT NOT NULL,
  tipo INT NOT NULL,
  ubicacion VARCHAR(20) NOT NULL,
  alquilado TINYINT(1) NOT NULL DEFAULT 0,

  CONSTRAINT pk_alojamientos PRIMARY KEY (referencia),
  CONSTRAINT fk_alojamientos_tipos
    FOREIGN KEY (tipo)
    REFERENCES tipos(codigo)
    ON UPDATE CASCADE
    ON DELETE RESTRICT
) ENGINE=InnoDB;

-- ---------------------------------------------------------
-- Índices básicos para consultas
-- ---------------------------------------------------------
CREATE INDEX idx_alojamientos_alquilado ON alojamientos(alquilado);
CREATE INDEX idx_alojamientos_provincia ON alojamientos(provincia);
CREATE INDEX idx_alojamientos_tipo ON alojamientos(tipo);
CREATE INDEX idx_alojamientos_capacidad ON alojamientos(capacidad);
CREATE INDEX idx_alojamientos_ubicacion ON alojamientos(ubicacion);

INSERT INTO tipos (codigo, descripcion) VALUES
(1, 'Casa rural'),
(2, 'Apartamento'),
(3, 'Hotel'),
(4, 'Hostal'),
(5, 'Albergue'),
(6, 'Camping');