-- MySQL Script generated by MySQL Workbench
-- Fri Jan 12 22:23:08 2024
-- Model: New Model    Version: 1.0
-- MySQL Workbench Forward Engineering

SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION';

-- -----------------------------------------------------
-- Schema centrosacad
-- -----------------------------------------------------

-- -----------------------------------------------------
-- Schema centrosacad
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `centrosacad` DEFAULT CHARACTER SET utf8 COLLATE utf8_spanish_ci ;
USE `centrosacad` ;

-- -----------------------------------------------------
-- Table `centrosacad`.`tbtipdoc`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `centrosacad`.`tbtipdoc` (
  `Id` INT NOT NULL,
  `NomTDo` VARCHAR(25) NOT NULL,
  PRIMARY KEY (`Id`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `centrosacad`.`tbcpo`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `centrosacad`.`tbcpo` (
  `NCodigo` VARCHAR(2) NOT NULL,
  `Ciudad` VARCHAR(30) NOT NULL,
  PRIMARY KEY (`NCodigo`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `centrosacad`.`tbProf`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `centrosacad`.`tbProf` (
  `IdProf` VARCHAR(6) NOT NULL,
  `Nombre` VARCHAR(15) NOT NULL,
  `Apellidos` VARCHAR(31) NOT NULL,
  `Id` INT NOT NULL COMMENT 'Tipo de documento',
  `numDoc` VARCHAR(50) NOT NULL,
  `FecNac` DATETIME NOT NULL,
  `FecAlt` DATETIME NOT NULL,
  `Direccion` VARCHAR(255) NOT NULL,
  `Localidad` VARCHAR(255) NOT NULL,
  `nCodigo` VARCHAR(2) NOT NULL COMMENT 'Código de provincia',
  `codPos` VARCHAR(5) NOT NULL,
  `Observ` VARCHAR(255) NULL,
  `Tel` VARCHAR(9) NULL,
  `Mov` VARCHAR(9) NULL,
  `email` VARCHAR(100) NOT NULL,
  `activo` TINYINT(1) NOT NULL,
  PRIMARY KEY (`IdProf`, `Id`, `nCodigo`),
  INDEX `fk_tbtipdoc_idx` (`Id` ASC) VISIBLE,
  INDEX `fk_tbProvincias_idx` (`nCodigo` ASC) VISIBLE,
  CONSTRAINT `fk_tbtipdoc2`
    FOREIGN KEY (`Id`)
    REFERENCES `centrosacad`.`tbtipdoc` (`Id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_tbProvincias2`
    FOREIGN KEY (`nCodigo`)
    REFERENCES `centrosacad`.`tbcpo` (`NCodigo`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `centrosacad`.`tbcen`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `centrosacad`.`tbcen` (
  `idCen` INT NOT NULL,
  `nomCen` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`idCen`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `centrosacad`.`tbUsuarios`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `centrosacad`.`tbUsuarios` (
  `nombreUsuario` VARCHAR(8) NOT NULL COMMENT 'Nombre de usuario con el que acceder a la aplicación',
  `password` VARCHAR(100) NOT NULL,
  `nivelAcceso` INT NOT NULL,
  `nombreCompleto` VARCHAR(50) NOT NULL,
  `codCentro` INT NOT NULL,
  `activo` TINYINT(1) NULL,
  `idProfesor` VARCHAR(6) NOT NULL,
  PRIMARY KEY (`nombreUsuario`, `idProfesor`, `codCentro`),
  INDEX `fk_tbProf_idx_idx` (`idProfesor` ASC) VISIBLE,
  INDEX `fk_tbUsuarios_tbcen1_idx` (`codCentro` ASC) VISIBLE,
  CONSTRAINT `fk_tbProf_idx`
    FOREIGN KEY (`idProfesor`)
    REFERENCES `centrosacad`.`tbProf` (`IdProf`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_tbUsuarios_tbcen1`
    FOREIGN KEY (`codCentro`)
    REFERENCES `centrosacad`.`tbcen` (`idCen`)
    ON DELETE CASCADE
    ON UPDATE CASCADE)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8
COLLATE = utf8_spanish_ci
COMMENT = 'Tabla que almacena los datos de acceso de los usuarios';


-- -----------------------------------------------------
-- Table `centrosacad`.`tbRoles`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `centrosacad`.`tbRoles` (
  `idRole` VARCHAR(8) NOT NULL,
  `roleName` VARCHAR(50) NOT NULL,
  PRIMARY KEY (`idRole`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `centrosacad`.`tbUsuariosRoles`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `centrosacad`.`tbUsuariosRoles` (
  `nombreUsuario` VARCHAR(8) NOT NULL,
  `idRole` VARCHAR(8) NOT NULL,
  PRIMARY KEY (`nombreUsuario`, `idRole`),
  INDEX `fk_tbUsuarios_has_tbRoles_tbRoles1_idx` (`idRole` ASC) VISIBLE,
  INDEX `fk_tbUsuarios_has_tbRoles_tbUsuarios_idx` (`nombreUsuario` ASC) VISIBLE,
  CONSTRAINT `fk_tbUsuarios_has_tbRoles_tbUsuarios`
    FOREIGN KEY (`nombreUsuario`)
    REFERENCES `centrosacad`.`tbUsuarios` (`nombreUsuario`)
    ON DELETE CASCADE
    ON UPDATE CASCADE,
  CONSTRAINT `fk_tbUsuarios_has_tbRoles_tbRoles1`
    FOREIGN KEY (`idRole`)
    REFERENCES `centrosacad`.`tbRoles` (`idRole`)
    ON DELETE CASCADE
    ON UPDATE CASCADE)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8
COLLATE = utf8_spanish_ci;


-- -----------------------------------------------------
-- Table `centrosacad`.`tbNivFor`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `centrosacad`.`tbNivFor` (
  `idNivel` INT NOT NULL,
  `NomNivel` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`idNivel`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `centrosacad`.`tbAlu`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `centrosacad`.`tbAlu` (
  `idAlu` VARCHAR(8) NOT NULL COMMENT 'Código de alumno',
  `TDoAlu` INT NOT NULL COMMENT 'Tippo de documento de identificación del alumno',
  `NDoAlu` VARCHAR(20) NOT NULL COMMENT 'Número de documento identificativo',
  `NomAlu` VARCHAR(50) NOT NULL COMMENT 'Nomnbre del alumno',
  `Ap1Alu` VARCHAR(50) NOT NULL COMMENT 'Primer apellido',
  `Ap2Alu` VARCHAR(45) NULL COMMENT 'Segundo apellido',
  `MovAlu` VARCHAR(9) NULL COMMENT 'Número de teléfono movil del alumno',
  `FijAlu` INT NULL COMMENT 'Némero de teléfono fijo del alumno',
  `DirAlu` VARCHAR(150) NOT NULL COMMENT 'Dirección del alumno',
  `CpoAlu` VARCHAR(5) NOT NULL COMMENT 'Código postal',
  `LocAlu` VARCHAR(45) NOT NULL COMMENT 'Localidad ',
  `NFoAlu` INT NOT NULL COMMENT 'Nivel formativo del alumno',
  `EmaAlu` VARCHAR(150) NULL COMMENT 'Email del alumno',
  `DesAlu` TINYINT(1) NOT NULL COMMENT 'Indica si el alumno está desempleado',
  `EmpAlu` VARCHAR(8) NULL COMMENT 'Código de la empresa a la que pertenece el alumno',
  `IntAlu` LONGTEXT NULL,
  `codProAlu` VARCHAR(2) NOT NULL COMMENT 'Código de provincia',
  `IdCen` INT NOT NULL,
  `FecNac` DATETIME NULL,
  `alND` TINYINT(1) NOT NULL COMMENT 'Indica si el alumno ha causado algún problema en cursos anteriores',
  `autCesDat` TINYINT(1) NULL COMMENT 'Indica si autoriza la cesión de datos',
  `autComCom` TINYINT(1) NOT NULL COMMENT 'Indica si el alumno autoriza las comunicaciones comerciales',
  `responsable` VARCHAR(50) NULL COMMENT 'Responsable legal del alumno si es menor de edad. Fuera de uso',
  `colegio` VARCHAR(100) NULL COMMENT 'Colegio del alumno si está en edad escolar. Fuera de uso',
  `curso` VARCHAR(25) NULL COMMENT 'Curso del alumno si está en edad escolar. Fuera de uso',
  PRIMARY KEY (`idAlu`, `codProAlu`, `NFoAlu`, `TDoAlu`),
  INDEX `fk_tbNiveles_idx` (`NFoAlu` ASC) VISIBLE,
  INDEX `fk_tbProvincias_idx` (`codProAlu` ASC) VISIBLE,
  INDEX `fk_tbTipDoc_idx` (`TDoAlu` ASC) VISIBLE,
  CONSTRAINT `fk_tbNiveles1`
    FOREIGN KEY (`NFoAlu`)
    REFERENCES `centrosacad`.`tbNivFor` (`idNivel`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_tbProvincias1`
    FOREIGN KEY (`codProAlu`)
    REFERENCES `centrosacad`.`tbcpo` (`NCodigo`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_tbTipDoc1`
    FOREIGN KEY (`TDoAlu`)
    REFERENCES `centrosacad`.`tbtipdoc` (`Id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `centrosacad`.`tbArea`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `centrosacad`.`tbArea` (
  `idArea` VARCHAR(4) NOT NULL,
  `Area` VARCHAR(100) NOT NULL,
  PRIMARY KEY (`idArea`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `centrosacad`.`tbprofarea`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `centrosacad`.`tbprofarea` (
  `IdProf` VARCHAR(6) NOT NULL,
  `IdArea` VARCHAR(6) NOT NULL,
  PRIMARY KEY (`IdArea`, `IdProf`),
  CONSTRAINT `fk_prof_has_area`
    FOREIGN KEY (`IdArea`)
    REFERENCES `centrosacad`.`tbProf` (`IdProf`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_tbprofarea_tbArea1`
    FOREIGN KEY (`IdArea`)
    REFERENCES `centrosacad`.`tbArea` (`idArea`)
    ON DELETE CASCADE
    ON UPDATE CASCADE)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `centrosacad`.`tbAul`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `centrosacad`.`tbAul` (
  `idAula` VARCHAR(4) NOT NULL,
  `nombre` VARCHAR(100) NOT NULL,
  `idCen` INT NOT NULL,
  `numPla` INT NOT NULL,
  `descripcion` VARCHAR(100) NOT NULL,
  `esAulaInf` TINYINT NULL,
  `tieneProy` TINYINT NULL,
  `tieneTv` TINYINT NULL,
  `tieneAc` TINYINT NULL,
  `tieneImp` TINYINT NULL,
  `tieneInt` TINYINT NULL,
  PRIMARY KEY (`idAula`),
  INDEX `fk_centros_idx` (`idCen` ASC) VISIBLE,
  CONSTRAINT `fk_centros`
    FOREIGN KEY (`idCen`)
    REFERENCES `centrosacad`.`tbcen` (`idCen`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `centrosacad`.`tbtipcur`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `centrosacad`.`tbtipcur` (
  `IdTipCur` INT NOT NULL,
  `nomTipCur` VARCHAR(30) NOT NULL,
  PRIMARY KEY (`IdTipCur`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `centrosacad`.`tbCur`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `centrosacad`.`tbCur` (
  `IdCur` VARCHAR(8) NOT NULL,
  `NomCur` VARCHAR(150) NOT NULL,
  `TipCur` INT NOT NULL,
  `CenCur` INT NOT NULL,
  `Contenido` LONGTEXT NOT NULL,
  PRIMARY KEY (`IdCur`, `TipCur`),
  INDEX `fk_tipCur_cur_idx` (`TipCur` ASC) VISIBLE,
  CONSTRAINT `fk_tipCur_cur`
    FOREIGN KEY (`TipCur`)
    REFERENCES `centrosacad`.`tbtipcur` (`IdTipCur`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `centrosacad`.`tbniv`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `centrosacad`.`tbniv` (
  `IdNiv` VARCHAR(8) NOT NULL,
  `NomNiv` VARCHAR(100) NOT NULL,
  `ContNiv` VARCHAR(250) NOT NULL,
  `IdCur` VARCHAR(8) NOT NULL,
  PRIMARY KEY (`IdNiv`, `IdCur`),
  INDEX `fk_tbniv_tbCur1_idx` (`IdCur` ASC) VISIBLE,
  CONSTRAINT `fk_tbniv_tbCur1`
    FOREIGN KEY (`IdCur`)
    REFERENCES `centrosacad`.`tbCur` (`IdCur`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `centrosacad`.`tbmod`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `centrosacad`.`tbmod` (
  `IdMod` VARCHAR(8) NOT NULL,
  `Nombre` VARCHAR(100) NOT NULL,
  `Descripcion` VARCHAR(255) NOT NULL,
  `NumHor` INT NOT NULL,
  `idCur` VARCHAR(8) NOT NULL,
  `idArea` VARCHAR(4) NOT NULL,
  PRIMARY KEY (`IdMod`, `idCur`, `idArea`),
  INDEX `fk_tbmod_tbCur1_idx` (`idCur` ASC) VISIBLE,
  INDEX `fk_tbmod_tbArea1_idx` (`idArea` ASC) VISIBLE,
  CONSTRAINT `fk_tbmod_tbCur1`
    FOREIGN KEY (`idCur`)
    REFERENCES `centrosacad`.`tbCur` (`IdCur`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_tbmod_tbArea1`
    FOREIGN KEY (`idArea`)
    REFERENCES `centrosacad`.`tbArea` (`idArea`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `centrosacad`.`tbedi`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `centrosacad`.`tbedi` (
  `IdEdi` VARCHAR(10) NOT NULL,
  `IdCur` VARCHAR(8) NOT NULL,
  `IdNiv` VARCHAR(8) NOT NULL,
  `FecIn` DATETIME NOT NULL,
  `FecFi` DATETIME NOT NULL,
  `NumPla` INT NOT NULL,
  `NumHor` INT NOT NULL,
  `HorIn` INT NOT NULL,
  `HorFin` INT NOT NULL,
  `PrecioM` DOUBLE(15,5) NOT NULL,
  `PrecioR` DOUBLE(15,5) NOT NULL,
  `PrecioT` DOUBLE(15,5) NOT NULL,
  `HayLun` TINYINT(1) NOT NULL,
  `HayMar` TINYINT(1) NOT NULL,
  `HayMie` TINYINT(1) NOT NULL,
  `HayJue` TINYINT(1) NOT NULL,
  `HayVie` TINYINT(1) NOT NULL,
  `HaySab` TINYINT(1) NOT NULL,
  `idCen` INT NOT NULL,
  `MinIn` INT NOT NULL,
  `MinFin` INT NOT NULL,
  `HorDis` INT NOT NULL,
  `HorTelf` INT NOT NULL,
  `idAula` VARCHAR(4) NOT NULL,
  `bonif` VARCHAR(50) NOT NULL,
  `Plazos` TINYINT(1) NOT NULL,
  `Ene` TINYINT(1) NOT NULL,
  `Feb` TINYINT(1) NOT NULL,
  `Mar` TINYINT(1) NOT NULL,
  `Abr` TINYINT(1) NOT NULL,
  `May` TINYINT(1) NOT NULL,
  `Jun` TINYINT(1) NOT NULL,
  `Jul` TINYINT(1) NOT NULL,
  `Ago` TINYINT(1) NOT NULL,
  `Sep` TINYINT(1) NOT NULL,
  `Oct` TINYINT(1) NOT NULL,
  `Nov` TINYINT(1) NOT NULL,
  `Dic` TINYINT(1) NOT NULL,
  `aplaz` VARCHAR(8) NOT NULL,
  `descripcion` VARCHAR(50) NOT NULL,
  `idProf` VARCHAR(6) NULL,
  PRIMARY KEY (`IdEdi`, `IdCur`, `IdNiv`, `idCen`, `idAula`, `idProf`),
  INDEX `fk_cur_edi_idx` (`IdCur` ASC) VISIBLE,
  INDEX `fk_niv_edi_idx` (`IdNiv` ASC) VISIBLE,
  INDEX `fk_cen_edi_idx` (`idCen` ASC) VISIBLE,
  INDEX `fk_aul_edi_idx` (`idAula` ASC) VISIBLE,
  INDEX `fk_prof_Edi_idx` (`idProf` ASC) VISIBLE,
  CONSTRAINT `fk_cur_edi`
    FOREIGN KEY (`IdCur`)
    REFERENCES `centrosacad`.`tbCur` (`IdCur`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_niv_edi`
    FOREIGN KEY (`IdNiv`)
    REFERENCES `centrosacad`.`tbniv` (`IdNiv`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_cen_edi`
    FOREIGN KEY (`idCen`)
    REFERENCES `centrosacad`.`tbcen` (`idCen`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_aul_edi`
    FOREIGN KEY (`idAula`)
    REFERENCES `centrosacad`.`tbAul` (`idAula`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_prof_Edi`
    FOREIGN KEY (`idProf`)
    REFERENCES `centrosacad`.`tbProf` (`IdProf`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `centrosacad`.`tbaluedi`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `centrosacad`.`tbaluedi` (
  `IdAlu` VARCHAR(8) NOT NULL,
  `IdEdi` VARCHAR(10) NOT NULL,
  `fecAlta` DATETIME NULL,
  `esBaja` TINYINT(1) NULL,
  `observ` VARCHAR(255) NULL,
  `numCuenta` VARCHAR(20) NULL,
  `esCong` TINYINT(1) NULL,
  PRIMARY KEY (`IdAlu`, `IdEdi`),
  INDEX `fk_edi_edi_alu_idx` (`IdEdi` ASC) VISIBLE,
  CONSTRAINT `fk_edi_edi_alu`
    FOREIGN KEY (`IdEdi`)
    REFERENCES `centrosacad`.`tbedi` (`IdEdi`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_alu_edi_aluj`
    FOREIGN KEY (`IdAlu`)
    REFERENCES `centrosacad`.`tbAlu` (`idAlu`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `centrosacad`.`tbcuralu`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `centrosacad`.`tbcuralu` (
  `IdAlu` VARCHAR(8) NOT NULL,
  `IdCur` VARCHAR(8) NOT NULL,
  `IdNiv` VARCHAR(8) NOT NULL,
  `fecInt` DATETIME NOT NULL,
  PRIMARY KEY (`IdAlu`, `IdCur`, `IdNiv`, `fecInt`),
  INDEX `fk_cur_curalu_idx` (`IdCur` ASC) VISIBLE,
  INDEX `fk_niv_curalu_idx` (`IdNiv` ASC) VISIBLE,
  CONSTRAINT `fk_alu_curalu`
    FOREIGN KEY (`IdAlu`)
    REFERENCES `centrosacad`.`tbAlu` (`idAlu`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_cur_curalu`
    FOREIGN KEY (`IdCur`)
    REFERENCES `centrosacad`.`tbCur` (`IdCur`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_niv_curalu`
    FOREIGN KEY (`IdNiv`)
    REFERENCES `centrosacad`.`tbniv` (`IdNiv`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `centrosacad`.`tbedimodproaul`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `centrosacad`.`tbedimodproaul` (
  `IdEdi` VARCHAR(10) NOT NULL,
  `IdMod` VARCHAR(8) NOT NULL,
  `IdProf` VARCHAR(6) NOT NULL,
  `IdAul` VARCHAR(4) NOT NULL,
  `fecInic` DATETIME NULL,
  `fecFin` DATETIME NULL,
  `horIni` INT NULL,
  `horFin` INT NULL,
  `HayLun` TINYINT(1) NULL,
  `HayMar` TINYINT(1) NULL,
  `HayMie` TINYINT(1) NULL,
  `HayJue` TINYINT(1) NULL,
  `HayVie` TINYINT(1) NULL,
  `HaySab` TINYINT(1) NULL,
  PRIMARY KEY (`IdEdi`, `IdMod`, `IdProf`, `IdAul`),
  INDEX `fk_mod_this_idx` (`IdMod` ASC) VISIBLE,
  INDEX `fk_prog_this_idx` (`IdProf` ASC) VISIBLE,
  INDEX `fk_aul_this_idx` (`IdAul` ASC) VISIBLE,
  CONSTRAINT `fk_edi_this`
    FOREIGN KEY (`IdEdi`)
    REFERENCES `centrosacad`.`tbedi` (`IdEdi`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_mod_this`
    FOREIGN KEY (`IdMod`)
    REFERENCES `centrosacad`.`tbmod` (`IdMod`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_prof_this`
    FOREIGN KEY (`IdProf`)
    REFERENCES `centrosacad`.`tbProf` (`IdProf`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_aul_this`
    FOREIGN KEY (`IdAul`)
    REFERENCES `centrosacad`.`tbAul` (`idAula`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;
