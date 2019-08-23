CREATE TABLE `application` (
  `id`          varchar(32) NOT NULL,
  `name`        varchar(64) NOT NULL,
  `secret`      varchar(64) NOT NULL,
  `description` varchar(64) DEFAULT NULL,
  PRIMARY KEY (`id`)
)
  ENGINE = InnoDB
  DEFAULT CHARSET = utf8;

CREATE TABLE `api_group` (
  `id`          varchar(32)  NOT NULL,
  `name`        varchar(64)  NOT NULL,
  `secret`      varchar(64)  NOT NULL,
  `url`         varchar(512) NOT NULL,
  `api_prefix`  varchar(64) default ''
  comment 'api前缀，如http://api.xkw.com/qbm/v1/stages,/qbm/v1就是前缀',
  `description` varchar(64) DEFAULT NULL,
  PRIMARY KEY (`id`)
)
  ENGINE = InnoDB
  DEFAULT CHARSET = utf8;

CREATE TABLE `permission` (
  `id`         int(11)      NOT NULL AUTO_INCREMENT,
  `app_id`     varchar(32)  NOT NULL,
  `group_id`   varchar(32)  NULL
  comment '权限属于那个api group',
  `permission` varchar(512) NOT NULL,
  `type`       tinyint      not null
  comment '0表示url权限，1表示数据权限',
  PRIMARY KEY (`id`)
)
  ENGINE = InnoDB
  AUTO_INCREMENT = 6
  DEFAULT CHARSET = utf8;