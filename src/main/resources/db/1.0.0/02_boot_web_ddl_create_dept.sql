CREATE TABLE IF NOT EXISTS dept (
    id BIGINT COMMENT '主键id',
    `dept_name` VARCHAR (128) NOT NULL COMMENT '部门名称',
    PRIMARY KEY (`id`),
    KEY `idx_dept_name`(`dept_name`)
  )ENGINE=INNODB DEFAULT CHARSET=utf8mb4 COMMENT '部门表' ;