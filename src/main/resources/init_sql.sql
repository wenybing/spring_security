CREATE TABLE tb_user (
  id          INT(11) PRIMARY KEY AUTO_INCREMENT
  COMMENT '用户ID',
  username    VARCHAR(32) COMMENT '用户名',
  PASSWORD    VARCHAR(255) COMMENT '密码',
  enabled     TINYINT(1) COMMENT '用户禁用状态',
  locked      TINYINT(1) COMMENT '用户锁定状态',
  create_time TIMESTAMP NOT NULL  DEFAULT CURRENT_TIMESTAMP
  COMMENT '创建时间',
  update_time TIMESTAMP NOT NULL  DEFAULT CURRENT_TIMESTAMP
  COMMENT '修改时间'
)
  COMMENT '用户表';

INSERT INTO tb_user (id, username, PASSWORD, enabled, locked)
VALUES
  (1, 'admin', '$2a$10$K0uU50HPrlkFdij/vVdckuV2Waja3Wza6Jb3YulDk/f51.7tEjumG', 1, 0),
  (2, 'root', '$2a$10$K0uU50HPrlkFdij/vVdckuV2Waja3Wza6Jb3YulDk/f51.7tEjumG', 1, 0),
  (3, 'zhangsan', '$2a$10$K0uU50HPrlkFdij/vVdckuV2Waja3Wza6Jb3YulDk/f51.7tEjumG', 1, 0);


CREATE TABLE tb_role (
  id        INT(11) PRIMARY KEY AUTO_INCREMENT
  COMMENT '角色id',
  role      VARCHAR(32) NOT NULL
  COMMENT '角色',
  role_name VARCHAR(32) NOT NULL
  COMMENT '角色名称'
)
  COMMENT '角色表';

INSERT INTO tb_role (id, role, role_name)
VALUES
  (1, 'ROLE_admin', '系统管理员'),
  (2, 'ROLE_dba', '数据库管理员'),
  (3, 'ROLE_user', '普通用户');


CREATE TABLE tb_user_role (
  id  INT(11) PRIMARY KEY,
  uid INT(11) NOT NULL
  COMMENT '用户ID',
  rid INT(11) NOT NULL
  COMMENT '角色ID'
)
  COMMENT '用户角色表';

INSERT INTO tb_user_role (id, uid, rid)
VALUES
  (1, 1, 1),
  (2, 1, 2),
  (3, 2, 2),
  (4, 3, 3);


CREATE TABLE tb_menu (
  id      INT(11) PRIMARY KEY AUTO_INCREMENT
  COMMENT '菜单id',
  pattern VARCHAR(255) NOT NULL
  COMMENT '匹配url'
)
  COMMENT '菜单表';

INSERT INTO tb_menu (pattern)
VALUES
  ('/admin/**'),
  ('/db/**'),
  ('/user/**');

CREATE TABLE tb_menu_role (
  id  INT(11) PRIMARY KEY AUTO_INCREMENT,
  MID INT(11) NOT NULL
  COMMENT '菜单id',
  rid INT(11) NOT NULL
  COMMENT '角色id'
)
  COMMENT '菜单角色表';

INSERT INTO tb_menu_role (MID, rid)
VALUES
  (1, 1),
  (2, 2),
  (3, 3);