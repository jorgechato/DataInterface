CREATE DATABASE IF NOT EXISTS fightclub;
USE fightclub;

CREATE TABLE IF NOT EXISTS dojo(
    id INT UNSIGNED PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR (50) NOT NULL,
    street VARCHAR(150),
    inauguration DATE
);
CREATE TABLE IF NOT EXISTS coach(
    id INT UNSIGNED PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR (50) NOT NULL,
    birthday DATE,
    sperience INT,
    id_dojo INT UNSIGNED,
    INDEX(id_dojo),
    FOREIGN KEY (id_dojo)
        REFERENCES dojo(id)
        ON DELETE SET NULL ON UPDATE NO ACTION
);
CREATE TABLE IF NOT EXISTS boxer(
    id INT UNSIGNED PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(50) NOT NULL,
    wins INT DEFAULT 0,
    lose INT DEFAULT 0,
    weight REAL,
    id_dojo INT UNSIGNED,
    INDEX(id_dojo),
    FOREIGN KEY (id_dojo)
        REFERENCES dojo(id)
        ON DELETE SET NULL ON UPDATE NO ACTION,

    id_coach INT UNSIGNED,
    INDEX(id_coach),
    FOREIGN KEY (id_coach)
        REFERENCES coach(id)
        ON DELETE SET NULL ON UPDATE NO ACTION
);
