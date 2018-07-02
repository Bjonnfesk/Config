BEGIN TRANSACTION;
CREATE TABLE IF NOT EXISTS `settingCategory` (
	`id`	INTEGER PRIMARY KEY AUTOINCREMENT,
	`name`	TEXT,
	`friendlyName`	TEXT
);
INSERT INTO `settingCategory` (id,name,friendlyName) VALUES (1,'general','General');
INSERT INTO `settingCategory` (id,name,friendlyName) VALUES (2,'testing','Testing');
INSERT INTO `settingCategory` (id,name,friendlyName) VALUES (3,'cats','Things I like about cats');
CREATE TABLE IF NOT EXISTS `setting` (
	`id`	INTEGER PRIMARY KEY AUTOINCREMENT,
	`name`	TEXT,
	`value`	TEXT,
	`friendlyName`	TEXT,
	`settingCategory`	INTEGER,
	`settingType`	TEXT,
	`data`	TEXT
);
INSERT INTO `setting` (id,name,value,friendlyName,settingCategory,settingType,data) VALUES (1,'logLevel','4','Log Level',1,'Spinner','{"validValues": [0, 1, 2, 3, 4], "substitutions":["SILENT",  "ERROR", "WARNING", "DEBUG", "INFO"]}');
INSERT INTO `setting` (id,name,value,friendlyName,settingCategory,settingType,data) VALUES (2,'test','Your test seems to be working!','Testing:',2,'TextField','');
INSERT INTO `setting` (id,name,value,friendlyName,settingCategory,settingType,data) VALUES (3,'foo4','fluffy','CATS ARE: ',3,'Spinner','{"validValues": ["fluffy", "cute", "long", "soft", "cuddly", "warm"]}');
INSERT INTO `setting` (id,name,value,friendlyName,settingCategory,settingType,data) VALUES (4,'bar','foofoofoofoofoofooo','bar',1,'TextField','');
INSERT INTO `setting` (id,name,value,friendlyName,settingCategory,settingType,data) VALUES (6,'TrueCheckBox','1','CATS ARE NICE?',3,'CheckBox','');
INSERT INTO `setting` (id,name,value,friendlyName,settingCategory,settingType,data) VALUES (7,'foo3','NICE!','CATS ARE:',3,'TextField','');
INSERT INTO `setting` (id,name,value,friendlyName,settingCategory,settingType,data) VALUES (8,'foo','1000000','CATS ARE HOW NICE!?',3,'Spinner','{"validValues": [1000, 2000, 5000, 10000, 1000000]}');
INSERT INTO `setting` (id,name,value,friendlyName,settingCategory,settingType,data) VALUES (9,'FalseCheckBox','0','CATS ARE UGLY?',3,'CheckBox',NULL);
COMMIT;
