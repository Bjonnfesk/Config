BEGIN TRANSACTION;
CREATE TABLE IF NOT EXISTS `settingCategory` (
	`id`	INTEGER PRIMARY KEY AUTOINCREMENT,
	`name`	TEXT,
	`friendlyName`	TEXT
);
INSERT INTO `settingCategory` (id,name,friendlyName) VALUES (1,'general','General');
INSERT INTO `settingCategory` (id,name,friendlyName) VALUES (2,'testing','Testing');
INSERT INTO `settingCategory` (id,name,friendlyName) VALUES (3,'cats','Things I like about cats');
INSERT INTO `settingCategory` (id,name,friendlyName) VALUES (4,'many','MANY SETTINGS!');
CREATE TABLE IF NOT EXISTS `setting` (
	`id`	INTEGER PRIMARY KEY AUTOINCREMENT,
	`name`	TEXT,
	`value`	TEXT,
	`friendlyName`	TEXT,
	`settingCategory`	INTEGER,
	`settingType`	TEXT,
	`data`	TEXT
);
INSERT INTO `setting` (id,name,value,friendlyName,settingCategory,settingType,data) VALUES (1,'logLevel','4','Log Level:',1,'Spinner','{"validValues": [0, 1, 2, 3, 4], "substitutions":["SILENT",  "ERROR", "WARNING", "DEBUG", "INFO"]}');
INSERT INTO `setting` (id,name,value,friendlyName,settingCategory,settingType,data) VALUES (2,'radioButtonTest1','0','FM',2,'RadioButton','{"buttonGroup":"modulation"}');
INSERT INTO `setting` (id,name,value,friendlyName,settingCategory,settingType,data) VALUES (3,'radioButtonTest2','1','AM',2,'RadioButton','{"buttonGroup":"modulation"}');
INSERT INTO `setting` (id,name,value,friendlyName,settingCategory,settingType,data) VALUES (4,'bar','♫...where everybody knows your name...♪♫','Bar: ',1,'TextField','');
INSERT INTO `setting` (id,name,value,friendlyName,settingCategory,settingType,data) VALUES (6,'TrueCheckBox','1','CATS ARE NICE?',3,'CheckBox','');
INSERT INTO `setting` (id,name,value,friendlyName,settingCategory,settingType,data) VALUES (7,'foo3','NICE!','CATS ARE:',3,'TextField','');
INSERT INTO `setting` (id,name,value,friendlyName,settingCategory,settingType,data) VALUES (8,'foo','10000','CATS ARE HOW NICE!?',3,'Spinner','{"validValues": [1000, 2000, 5000, 10000, 100000000, 133742069]}');
INSERT INTO `setting` (id,name,value,friendlyName,settingCategory,settingType,data) VALUES (9,'FalseCheckBox','0','CATS ARE UGLY?',3,'CheckBox',NULL);
INSERT INTO `setting` (id,name,value,friendlyName,settingCategory,settingType,data) VALUES (10,'test2','A Test Value','A Test Setting:',2,'TextField',NULL);
INSERT INTO `setting` (id,name,value,friendlyName,settingCategory,settingType,data) VALUES (11,'foo5','AESTHETICALLY PLEASING','CATS ARE:',3,'TextField','');
INSERT INTO `setting` (id,name,value,friendlyName,settingCategory,settingType,data) VALUES (13,'threeSettings','1','Three Settings = Three Rows!',1,'CheckBox',NULL);
INSERT INTO `setting` (id,name,value,friendlyName,settingCategory,settingType,data) VALUES (14,'1','0',NULL,4,'CheckBox',NULL);
INSERT INTO `setting` (id,name,value,friendlyName,settingCategory,settingType,data) VALUES (15,'2','1',NULL,4,'CheckBox',NULL);
INSERT INTO `setting` (id,name,value,friendlyName,settingCategory,settingType,data) VALUES (16,'3','1000',NULL,4,'Spinner','{"validValues": [1000, 2000, 3000, 4000, 5000]}');
INSERT INTO `setting` (id,name,value,friendlyName,settingCategory,settingType,data) VALUES (17,'4','0',NULL,4,'CheckBox',NULL);
INSERT INTO `setting` (id,name,value,friendlyName,settingCategory,settingType,data) VALUES (18,'5','1','',4,'CheckBox',NULL);
INSERT INTO `setting` (id,name,value,friendlyName,settingCategory,settingType,data) VALUES (19,'6','HELLO!',NULL,4,'TextField',NULL);
INSERT INTO `setting` (id,name,value,friendlyName,settingCategory,settingType,data) VALUES (20,'7','1',NULL,4,'CheckBox',NULL);
INSERT INTO `setting` (id,name,value,friendlyName,settingCategory,settingType,data) VALUES (21,'8','1',NULL,4,'CheckBox',NULL);
INSERT INTO `setting` (id,name,value,friendlyName,settingCategory,settingType,data) VALUES (22,'9','0',NULL,4,'CheckBox',NULL);
INSERT INTO `setting` (id,name,value,friendlyName,settingCategory,settingType,data) VALUES (23,'10','1001',NULL,4,'Spinner','{"validValues": [1001, 1002, 1003]}');
INSERT INTO `setting` (id,name,value,friendlyName,settingCategory,settingType,data) VALUES (24,'11','1',NULL,4,'CheckBox',NULL);
INSERT INTO `setting` (id,name,value,friendlyName,settingCategory,settingType,data) VALUES (25,'12','1',NULL,4,'CheckBox',NULL);
INSERT INTO `setting` (id,name,value,friendlyName,settingCategory,settingType,data) VALUES (26,'13','BYE!',NULL,4,'TextField',NULL);
INSERT INTO `setting` (id,name,value,friendlyName,settingCategory,settingType,data) VALUES (33,'foo4','fluffy','CATS ARE: ',3,'Spinner','{"validValues": ["fluffy", "cute", "long", "soft", "cuddly", "warm", "funny"]}');
INSERT INTO `setting` (id,name,value,friendlyName,settingCategory,settingType,data) VALUES (34,'radioButtonTest3','1','Off',2,'RadioButton','{"buttonGroup":"mode"}');
INSERT INTO `setting` (id,name,value,friendlyName,settingCategory,settingType,data) VALUES (35,'radioButtonTest4','0','Tuner',2,'RadioButton','{"buttonGroup":"mode"}');
INSERT INTO `setting` (id,name,value,friendlyName,settingCategory,settingType,data) VALUES (36,'radioButtonTest5','0','Alarm',2,'RadioButton','{"buttonGroup":"mode"}');
COMMIT;
