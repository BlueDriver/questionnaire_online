

CREATE TABLE  `student` (
 `std_number` VARCHAR( 12 ) NOT NULL ,
 `name` VARCHAR( 32 ) NOT NULL ,
 `age` INT NOT NULL DEFAULT  '0',
 `birth` DATETIME NOT NULL ,
PRIMARY KEY (  `std_number` ) ,
UNIQUE (
`name`
)
) ENGINE = INNODB CHARACTER SET utf8 COLLATE utf8_general_ci;