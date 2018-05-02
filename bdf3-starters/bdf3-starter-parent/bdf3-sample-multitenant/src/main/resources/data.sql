DROP TABLE bdf3_group;
CREATE TABLE bdf3_group (id_ varchar(64) NOT NULL, all_ bit, create_time_ datetime, creator_ varchar(64), description_ varchar(512), icon_ varchar(255), last_notice_id_ varchar(64), last_notice_send_time_ datetime, member_count_ int, name_ varchar(255), private_letter_ bit, system_ bit, temporary_ bit, url_ varchar(512), template_id_ varchar(64), PRIMARY KEY (id_)) ENGINE=MyISAM DEFAULT CHARSET=utf8;
INSERT INTO bdf3_group (id_, all_, create_time_, creator_, description_, icon_, last_notice_id_, last_notice_send_time_, member_count_, name_, private_letter_, system_, temporary_, url_, template_id_) VALUES ('35e08a4e-a557-4ab6-8e00-5660ce6223fa', false, '2018-03-25 17:21:26', 'admin', '用于管理系统功能', 'fa fa-balance-scale blue-text', null, null, 3, '系统管理群', false, true, false, null, null);
INSERT INTO bdf3_group (id_, all_, create_time_, creator_, description_, icon_, last_notice_id_, last_notice_send_time_, member_count_, name_, private_letter_, system_, temporary_, url_, template_id_) VALUES ('5c367bca-604e-4bc4-8793-401cfb31d35e', false, '2018-03-25 18:05:03', 'admin', '用于系统运维', 'fa fa-heartbeat green-text', '945abe20-9082-40ce-a2c3-5edda1f9ced2', '2018-03-29 23:52:43', 3, '运维群', false, false, false, null, 'd3753b1d-a256-44b6-a391-8a2c1a34e93f');
INSERT INTO bdf3_group (id_, all_, create_time_, creator_, description_, icon_, last_notice_id_, last_notice_send_time_, member_count_, name_, private_letter_, system_, temporary_, url_, template_id_) VALUES ('4f758d04-3366-4f4f-a7be-e65ef991a1b2', false, '2018-03-29 21:42:42', 'admin', null, 'fa fa-diamond purple-text', 'ff46911c-847f-4476-92fd-bf6630b0f2c3', '2018-03-29 23:50:26', 5, '个人办公', false, true, false, null, null);
INSERT INTO bdf3_group (id_, all_, create_time_, creator_, description_, icon_, last_notice_id_, last_notice_send_time_, member_count_, name_, private_letter_, system_, temporary_, url_, template_id_) VALUES ('f35bd80c-26d2-477c-8c8d-55d737736692', true, '2018-03-25 22:17:57', 'admin', null, 'fa fa-bullhorn blue-text', '4d85b216-facc-434d-9466-144a806b3d27', '2018-03-29 23:51:20', 2, '系统公告', false, false, false, null, null);
INSERT INTO bdf3_group (id_, all_, create_time_, creator_, description_, icon_, last_notice_id_, last_notice_send_time_, member_count_, name_, private_letter_, system_, temporary_, url_, template_id_) VALUES ('a1d0777e-60ad-4623-a563-5f9875fd14e6', false, '2018-03-26 14:58:50', 'admin', null, null, null, null, 2, null, true, false, false, null, null);
DROP TABLE bdf3_group_member;
CREATE TABLE bdf3_group_member (id_ varchar(64) NOT NULL, active_ bit, administrator_ bit, exited_ bit, group_id_ varchar(64), member_id_ varchar(64), member_type_ varchar(64), nickname_ varchar(64), read_only_ bit, PRIMARY KEY (id_)) ENGINE=MyISAM DEFAULT CHARSET=utf8;
INSERT INTO bdf3_group_member (id_, active_, administrator_, exited_, group_id_, member_id_, member_type_, nickname_, read_only_) VALUES ('ebd38a3b-4faf-45bc-a186-2b5ce1d4c10f', true, true, false, '35e08a4e-a557-4ab6-8e00-5660ce6223fa', 'admin', null, '系统管理员', false);
INSERT INTO bdf3_group_member (id_, active_, administrator_, exited_, group_id_, member_id_, member_type_, nickname_, read_only_) VALUES ('1c66b36d-7122-49a7-9113-a4a029779855', true, false, false, '35e08a4e-a557-4ab6-8e00-5660ce6223fa', 'lisi', null, '李四', true);
INSERT INTO bdf3_group_member (id_, active_, administrator_, exited_, group_id_, member_id_, member_type_, nickname_, read_only_) VALUES ('9031d5e9-e6b1-45de-a53b-46d3636dcc01', true, true, false, '5c367bca-604e-4bc4-8793-401cfb31d35e', 'admin', null, '系统管理员', false);
INSERT INTO bdf3_group_member (id_, active_, administrator_, exited_, group_id_, member_id_, member_type_, nickname_, read_only_) VALUES ('73e0e766-6e55-4024-93b0-32f04b759a22', true, false, false, '5c367bca-604e-4bc4-8793-401cfb31d35e', 'xiaoming', null, '小明', false);
INSERT INTO bdf3_group_member (id_, active_, administrator_, exited_, group_id_, member_id_, member_type_, nickname_, read_only_) VALUES ('703e04b1-92f9-43de-8968-7617d4c7d9f6', true, false, false, '5c367bca-604e-4bc4-8793-401cfb31d35e', 'wangwu', null, '王五', false);
INSERT INTO bdf3_group_member (id_, active_, administrator_, exited_, group_id_, member_id_, member_type_, nickname_, read_only_) VALUES ('13da9fb7-9b5f-4f27-8523-221483e01f40', true, false, false, '4f758d04-3366-4f4f-a7be-e65ef991a1b2', 'lisi', null, '李四', false);
INSERT INTO bdf3_group_member (id_, active_, administrator_, exited_, group_id_, member_id_, member_type_, nickname_, read_only_) VALUES ('0c8764de-718a-4e22-ab6a-423dabc9b025', true, true, false, 'f35bd80c-26d2-477c-8c8d-55d737736692', 'admin', null, '系统管理员', false);
INSERT INTO bdf3_group_member (id_, active_, administrator_, exited_, group_id_, member_id_, member_type_, nickname_, read_only_) VALUES ('a7b36526-22ac-43c8-a2a5-6c8183ae074c', false, false, true, '35e08a4e-a557-4ab6-8e00-5660ce6223fa', 'zhangsan', null, '张三', false);
INSERT INTO bdf3_group_member (id_, active_, administrator_, exited_, group_id_, member_id_, member_type_, nickname_, read_only_) VALUES ('3656b042-89c0-4fdf-a934-810d55a86c68', false, false, false, 'a1d0777e-60ad-4623-a563-5f9875fd14e6', 'admin', null, '系统管理员', false);
INSERT INTO bdf3_group_member (id_, active_, administrator_, exited_, group_id_, member_id_, member_type_, nickname_, read_only_) VALUES ('4aa4d30a-7b47-4091-a880-12445b2c26dd', true, false, false, 'a1d0777e-60ad-4623-a563-5f9875fd14e6', 'lisi', null, '李四', false);
INSERT INTO bdf3_group_member (id_, active_, administrator_, exited_, group_id_, member_id_, member_type_, nickname_, read_only_) VALUES ('1528df4b-4056-40b2-8490-ba54ff842a78', true, false, false, '35e08a4e-a557-4ab6-8e00-5660ce6223fa', 'xiaoming', null, '小明', false);
INSERT INTO bdf3_group_member (id_, active_, administrator_, exited_, group_id_, member_id_, member_type_, nickname_, read_only_) VALUES ('9c529736-a6d2-4aee-84d8-64f767fa88db', true, false, true, '35e08a4e-a557-4ab6-8e00-5660ce6223fa', 'wangwu', null, '王五', false);
INSERT INTO bdf3_group_member (id_, active_, administrator_, exited_, group_id_, member_id_, member_type_, nickname_, read_only_) VALUES ('2fd68e2a-6cd3-47bf-bf3a-36002894ce83', true, false, false, 'f35bd80c-26d2-477c-8c8d-55d737736692', 'xiaoming', null, '小明', false);
INSERT INTO bdf3_group_member (id_, active_, administrator_, exited_, group_id_, member_id_, member_type_, nickname_, read_only_) VALUES ('d9abe87f-bd74-4fcb-b7b7-f22a4a9c5a63', true, true, false, '4f758d04-3366-4f4f-a7be-e65ef991a1b2', 'admin', null, '系统管理员', false);
INSERT INTO bdf3_group_member (id_, active_, administrator_, exited_, group_id_, member_id_, member_type_, nickname_, read_only_) VALUES ('58f5c889-955e-4d49-aaa4-46a2a581103b', true, false, false, '4f758d04-3366-4f4f-a7be-e65ef991a1b2', 'wangwu', null, '王五', false);
INSERT INTO bdf3_group_member (id_, active_, administrator_, exited_, group_id_, member_id_, member_type_, nickname_, read_only_) VALUES ('24f74384-2d1f-4e86-8c43-db5b96193d56', true, false, false, '4f758d04-3366-4f4f-a7be-e65ef991a1b2', 'xiaoming', null, '小明', false);
INSERT INTO bdf3_group_member (id_, active_, administrator_, exited_, group_id_, member_id_, member_type_, nickname_, read_only_) VALUES ('4cce2fe0-250e-43f2-975c-491b1b1c3623', true, false, false, '4f758d04-3366-4f4f-a7be-e65ef991a1b2', 'zhangsan', null, '张三', false);
DROP TABLE bdf3_group_template;
CREATE TABLE bdf3_group_template (id_ varchar(64) NOT NULL, group_id_ varchar(64), template_id_ varchar(64), PRIMARY KEY (id_)) ENGINE=MyISAM DEFAULT CHARSET=utf8;
INSERT INTO bdf3_group_template (id_, group_id_, template_id_) VALUES ('8c00decd-ff44-4638-a60c-a65de3ac44ac', '4f758d04-3366-4f4f-a7be-e65ef991a1b2', 'a0b7efe9-8b34-407b-a6ad-568846ca6ee3');
INSERT INTO bdf3_group_template (id_, group_id_, template_id_) VALUES ('34cb7ed6-836d-4e4a-8e32-f0e2a10179ed', '35e08a4e-a557-4ab6-8e00-5660ce6223fa', 'd82052cc-7027-4cf3-bed4-9de1e775cf70');
INSERT INTO bdf3_group_template (id_, group_id_, template_id_) VALUES ('8ecfd12f-8888-4c64-b6a4-b665ad376d12', '35e08a4e-a557-4ab6-8e00-5660ce6223fa', '6900e36f-62dd-4b45-8f98-d71875771e13');
INSERT INTO bdf3_group_template (id_, group_id_, template_id_) VALUES ('82867fa5-d5ef-4aa5-b560-e96211b4ff9e', '35e08a4e-a557-4ab6-8e00-5660ce6223fa', '8e8aa5fc-412f-42c8-840e-282c90f20a19');
INSERT INTO bdf3_group_template (id_, group_id_, template_id_) VALUES ('14ad80a1-5a6f-44ee-ac99-7f6129e177d1', '35e08a4e-a557-4ab6-8e00-5660ce6223fa', '36f834d5-8314-4f48-92bd-23c298e5738e');
INSERT INTO bdf3_group_template (id_, group_id_, template_id_) VALUES ('febbfd10-27d9-480a-a4ce-fb2ec05af912', '35e08a4e-a557-4ab6-8e00-5660ce6223fa', '22cbb691-354c-4bb4-9ddc-4969d8d111ca');
INSERT INTO bdf3_group_template (id_, group_id_, template_id_) VALUES ('f31c8041-ce2f-45e5-a566-896ee43b5c49', '35e08a4e-a557-4ab6-8e00-5660ce6223fa', 'ca0dcb00-86c1-4dee-9312-c6ccacbf4d57');
INSERT INTO bdf3_group_template (id_, group_id_, template_id_) VALUES ('7be8f670-4dc0-4323-bcd6-2fdc08919954', '5c367bca-604e-4bc4-8793-401cfb31d35e', 'ef2eeb71-eec0-4264-aa41-820f06b9918c');
INSERT INTO bdf3_group_template (id_, group_id_, template_id_) VALUES ('6165c0a4-ad39-4c2f-b881-629d76ff51e6', '4f758d04-3366-4f4f-a7be-e65ef991a1b2', 'ef2eeb71-eec0-4264-aa41-820f06b9918c');
INSERT INTO bdf3_group_template (id_, group_id_, template_id_) VALUES ('83bf3a98-afe7-4c44-a372-cd031cdfe639', '5c367bca-604e-4bc4-8793-401cfb31d35e', 'ffa83b56-9610-42b1-97fe-41ceee9207fe');
INSERT INTO bdf3_group_template (id_, group_id_, template_id_) VALUES ('8b89eeb8-351b-492b-98c4-7ed6de39a8bd', '5c367bca-604e-4bc4-8793-401cfb31d35e', '2081965d-fdcb-49ca-9763-6b497338f04d');
INSERT INTO bdf3_group_template (id_, group_id_, template_id_) VALUES ('47263593-0e82-43ec-87e3-de73097dabb5', '35e08a4e-a557-4ab6-8e00-5660ce6223fa', 'a6714752-3a17-41cc-abce-a426a41ee14c');
INSERT INTO bdf3_group_template (id_, group_id_, template_id_) VALUES ('a909182e-8f77-44b8-8456-e17d8772f8d8', '5c367bca-604e-4bc4-8793-401cfb31d35e', 'd3753b1d-a256-44b6-a391-8a2c1a34e93f');
DROP TABLE bdf3_log_info;
CREATE TABLE bdf3_log_info (id_ varchar(36) NOT NULL, ip_ varchar(20), category_ varchar(100), desc_ longtext, module_ varchar(255), operation_ varchar(100), operation_date_ datetime, operation_user_ varchar(30), operation_user_nickname_ varchar(30), source_ varchar(255), title_ varchar(255), PRIMARY KEY (id_)) ENGINE=MyISAM DEFAULT CHARSET=utf8;
INSERT INTO bdf3_log_info (id_, ip_, category_, desc_, module_, operation_, operation_date_, operation_user_, operation_user_nickname_, source_, title_) VALUES ('3f7edf81-cf6e-46af-b578-3b2efbbe0f05', '0:0:0:0:0:0:0:1', '系统日志', 'id : null -> 35e08a4e-a557-4ab6-8e00-5660ce6223fa
群名称 : null -> 系统管理群
群图标 : null -> fa fa-balance-scale blue-text
创建者 : null -> admin
创建于 : null -> Sun Mar 25 17:21:26 CST 2018
公告群 : null -> false
临时群 : null -> false
系统群 : null -> false
privateLetter : null -> false
成员数 : null -> 1
链接数 : null -> 0
', '系统模块', '新增', '2018-03-25 17:21:27', 'admin', '系统管理员', 'http://localhost:8080/bdf3.notice.ui.view.Chat.d', '系统管理群');
INSERT INTO bdf3_log_info (id_, ip_, category_, desc_, module_, operation_, operation_date_, operation_user_, operation_user_nickname_, source_, title_) VALUES ('a7abc605-8846-480b-a4d5-4a9780e960f0', '0:0:0:0:0:0:0:1', '系统日志', 'administrator : false -> true
', '系统模块', '新增', '2018-03-25 17:21:27', 'admin', '系统管理员', 'http://localhost:8080/bdf3.notice.ui.view.Chat.d', '9a5dbd04-7d0a-45a5-bae9-7f9e182313f9');
INSERT INTO bdf3_log_info (id_, ip_, category_, desc_, module_, operation_, operation_date_, operation_user_, operation_user_nickname_, source_, title_) VALUES ('c68aa5e4-84ab-46fa-83ad-da2d5af1c437', '0:0:0:0:0:0:0:1', '系统日志', '链接数 : 0 -> 2
', '系统模块', '修改', '2018-03-25 17:29:01', 'admin', '系统管理员', 'http://localhost:8080/bdf3.notice.ui.view.Chat.d', '系统管理群');
INSERT INTO bdf3_log_info (id_, ip_, category_, desc_, module_, operation_, operation_date_, operation_user_, operation_user_nickname_, source_, title_) VALUES ('a5d147eb-d397-40dc-857f-e6677070dcb9', '0:0:0:0:0:0:0:1', '系统日志', '', '系统模块', '新增', '2018-03-25 17:29:01', 'admin', '系统管理员', 'http://localhost:8080/bdf3.notice.ui.view.Chat.d', '用户管理');
INSERT INTO bdf3_log_info (id_, ip_, category_, desc_, module_, operation_, operation_date_, operation_user_, operation_user_nickname_, source_, title_) VALUES ('27c5fadf-0fdf-4c2e-b115-1adeb5142b7e', '0:0:0:0:0:0:0:1', '系统日志', '', '系统模块', '新增', '2018-03-25 17:29:02', 'admin', '系统管理员', 'http://localhost:8080/bdf3.notice.ui.view.Chat.d', '菜单管理');
INSERT INTO bdf3_log_info (id_, ip_, category_, desc_, module_, operation_, operation_date_, operation_user_, operation_user_nickname_, source_, title_) VALUES ('d9e1c4a5-c539-4d67-9a1b-e0798a0733c7', '0:0:0:0:0:0:0:1', '系统日志', '用户名 : null -> test1
昵称 : null -> 测试用户1
密码 : null -> {bcrypt}$2a$10$Km0YH0iBf4l6mStaniwZeObYX6RnCDeBFy0Xp1Wwt4vAJ7sU9iUby
账户未过期 : null -> true
账户未锁定 : null -> true
证书未过期 : null -> true
可用 : null -> true
是管理员 : null -> false
', '系统模块', '新增', '2018-03-25 17:30:36', 'admin', '系统管理员', 'http://localhost:8080/bdf3.security.ui.view.UserMaintain.d', null);
INSERT INTO bdf3_log_info (id_, ip_, category_, desc_, module_, operation_, operation_date_, operation_user_, operation_user_nickname_, source_, title_) VALUES ('8b5c9995-dbbe-4cab-bc78-271ff8c85acb', '0:0:0:0:0:0:0:1', '系统日志', '用户名 : null -> zhangsan
昵称 : null -> 张三
密码 : null -> {bcrypt}$2a$10$s8oeM/HzocE0uwt4t/crlOANEziY3QacHYUDmRhiwjPOBTjl0RKjq
账户未过期 : null -> true
账户未锁定 : null -> true
证书未过期 : null -> true
可用 : null -> true
是管理员 : null -> false
', '系统模块', '新增', '2018-03-25 17:31:08', 'admin', '系统管理员', 'http://localhost:8080/bdf3.security.ui.view.UserMaintain.d', null);
INSERT INTO bdf3_log_info (id_, ip_, category_, desc_, module_, operation_, operation_date_, operation_user_, operation_user_nickname_, source_, title_) VALUES ('292b5457-2e20-4e00-96ad-f69ca37322ca', '0:0:0:0:0:0:0:1', '系统日志', '', '系统模块', '删除', '2018-03-25 17:31:15', 'admin', '系统管理员', 'http://localhost:8080/bdf3.security.ui.view.UserMaintain.d', null);
INSERT INTO bdf3_log_info (id_, ip_, category_, desc_, module_, operation_, operation_date_, operation_user_, operation_user_nickname_, source_, title_) VALUES ('aa4ee9e1-b938-4b47-bb9e-b03109763850', '0:0:0:0:0:0:0:1', '系统日志', '用户名 : null -> li
昵称 : null -> 李四
密码 : null -> {bcrypt}$2a$10$KyxLwLVr9HCfQa4NSd6SOOOqofqzuj5TUyvIITqjJjO0calmfSC.m
账户未过期 : null -> true
账户未锁定 : null -> true
证书未过期 : null -> true
可用 : null -> true
是管理员 : null -> false
', '系统模块', '新增', '2018-03-25 17:31:33', 'admin', '系统管理员', 'http://localhost:8080/bdf3.security.ui.view.UserMaintain.d', null);
INSERT INTO bdf3_log_info (id_, ip_, category_, desc_, module_, operation_, operation_date_, operation_user_, operation_user_nickname_, source_, title_) VALUES ('b363bb12-5524-4360-9312-8f72f8684142', '0:0:0:0:0:0:0:1', '系统日志', '', '系统模块', '删除', '2018-03-25 17:31:40', 'admin', '系统管理员', 'http://localhost:8080/bdf3.security.ui.view.UserMaintain.d', null);
INSERT INTO bdf3_log_info (id_, ip_, category_, desc_, module_, operation_, operation_date_, operation_user_, operation_user_nickname_, source_, title_) VALUES ('79faed91-794e-4d0a-867d-0e9535f73365', '0:0:0:0:0:0:0:1', '系统日志', '用户名 : null -> lisi
昵称 : null -> 李四
密码 : null -> {bcrypt}$2a$10$83Q9W.mq8KetdcmAog3nnuDoC5t3MABKT.3vea.izS6iAQXvZ3Sx.
账户未过期 : null -> true
账户未锁定 : null -> true
证书未过期 : null -> true
可用 : null -> true
是管理员 : null -> false
', '系统模块', '新增', '2018-03-25 17:31:50', 'admin', '系统管理员', 'http://localhost:8080/bdf3.security.ui.view.UserMaintain.d', null);
INSERT INTO bdf3_log_info (id_, ip_, category_, desc_, module_, operation_, operation_date_, operation_user_, operation_user_nickname_, source_, title_) VALUES ('c93e5adf-ed0e-4536-80c2-3d86d49cd88b', '0:0:0:0:0:0:0:1', '系统日志', '用户名 : null -> wangwu
昵称 : null -> 王五
密码 : null -> {bcrypt}$2a$10$U70itykX6yvUSIWgcByRg.nA9fOg4NoAGdXRjQmBVDhw0B3GzJXwy
账户未过期 : null -> true
账户未锁定 : null -> true
证书未过期 : null -> true
可用 : null -> true
是管理员 : null -> false
', '系统模块', '新增', '2018-03-25 17:32:05', 'admin', '系统管理员', 'http://localhost:8080/bdf3.security.ui.view.UserMaintain.d', null);
INSERT INTO bdf3_log_info (id_, ip_, category_, desc_, module_, operation_, operation_date_, operation_user_, operation_user_nickname_, source_, title_) VALUES ('dc13aa85-95af-463e-8c2e-b8bc6a7df202', '0:0:0:0:0:0:0:1', '系统日志', '用户名 : null -> xiaoming
昵称 : null -> 小明
密码 : null -> {bcrypt}$2a$10$U10owlQc3kXhLRBes/hdMOKgZiZxUZUr3iDBHuisFxaUuZ.LJPFz.
账户未过期 : null -> true
账户未锁定 : null -> true
证书未过期 : null -> true
可用 : null -> true
是管理员 : null -> false
', '系统模块', '新增', '2018-03-25 17:32:40', 'admin', '系统管理员', 'http://localhost:8080/bdf3.security.ui.view.UserMaintain.d', null);
INSERT INTO bdf3_log_info (id_, ip_, category_, desc_, module_, operation_, operation_date_, operation_user_, operation_user_nickname_, source_, title_) VALUES ('af53cfa3-0b73-4697-bf68-cac67fdee776', '0:0:0:0:0:0:0:1', '系统日志', '成员数 : 1 -> 2
链接数 : 0 -> 2
', '系统模块', '修改', '2018-03-25 17:32:51', 'admin', '系统管理员', 'http://localhost:8080/bdf3.notice.ui.view.Chat.d', '系统管理群');
INSERT INTO bdf3_log_info (id_, ip_, category_, desc_, module_, operation_, operation_date_, operation_user_, operation_user_nickname_, source_, title_) VALUES ('bcf9ab51-26d4-459a-9f08-a69cbfce91e3', '0:0:0:0:0:0:0:1', '系统日志', '', '系统模块', '新增', '2018-03-25 17:32:51', 'admin', '系统管理员', 'http://localhost:8080/bdf3.notice.ui.view.Chat.d', '4c3550c6-6295-4100-8fc3-245b5ef6bab1');
INSERT INTO bdf3_log_info (id_, ip_, category_, desc_, module_, operation_, operation_date_, operation_user_, operation_user_nickname_, source_, title_) VALUES ('14d39509-de07-4c06-825e-5a09cbabdb18', '0:0:0:0:0:0:0:1', '系统日志', 'readOnly : false -> true
', '系统模块', '修改', '2018-03-25 17:41:46', 'admin', '系统管理员', 'http://localhost:8080/bdf3.notice.ui.view.Chat.d', '1c66b36d-7122-49a7-9113-a4a029779855');
INSERT INTO bdf3_log_info (id_, ip_, category_, desc_, module_, operation_, operation_date_, operation_user_, operation_user_nickname_, source_, title_) VALUES ('00d044c0-5b33-454f-9797-751ed942516d', '0:0:0:0:0:0:0:1', '系统日志', '系统群 : false -> true
链接数 : 0 -> 2
', '系统模块', '修改', '2018-03-25 17:44:10', 'admin', '系统管理员', 'http://localhost:8080/bdf3.notice.ui.view.Chat.d', '系统管理群');
INSERT INTO bdf3_log_info (id_, ip_, category_, desc_, module_, operation_, operation_date_, operation_user_, operation_user_nickname_, source_, title_) VALUES ('626b81f4-bee6-4281-be1a-7e49698dc0c9', '0:0:0:0:0:0:0:1', '系统日志', '群描述 : null -> 用于管理系统功能
链接数 : 0 -> 2
', '系统模块', '修改', '2018-03-25 18:02:32', 'admin', '系统管理员', 'http://localhost:8080/bdf3.notice.ui.view.Chat.d', '系统管理群');
INSERT INTO bdf3_log_info (id_, ip_, category_, desc_, module_, operation_, operation_date_, operation_user_, operation_user_nickname_, source_, title_) VALUES ('5f78703f-d947-4835-9e8a-b7a38d8afbd2', '0:0:0:0:0:0:0:1', '系统日志', 'id : null -> 5c367bca-604e-4bc4-8793-401cfb31d35e
群名称 : null -> 运维群
群图标 : null -> fa fa-heartbeat green-text
创建者 : null -> admin
创建于 : null -> Sun Mar 25 18:05:03 CST 2018
公告群 : null -> false
临时群 : null -> false
系统群 : null -> false
privateLetter : null -> false
成员数 : null -> 1
链接数 : null -> 0
群描述 : null -> 用于系统运维
', '系统模块', '新增', '2018-03-25 18:05:03', 'admin', '系统管理员', 'http://localhost:8080/bdf3.notice.ui.view.Chat.d', '运维群');
INSERT INTO bdf3_log_info (id_, ip_, category_, desc_, module_, operation_, operation_date_, operation_user_, operation_user_nickname_, source_, title_) VALUES ('d4ad41c0-a334-45ba-88ed-3f1874992f24', '0:0:0:0:0:0:0:1', '系统日志', 'administrator : false -> true
', '系统模块', '新增', '2018-03-25 18:05:03', 'admin', '系统管理员', 'http://localhost:8080/bdf3.notice.ui.view.Chat.d', '69dd4c54-3194-4634-8dd1-89a5265d1de1');
INSERT INTO bdf3_log_info (id_, ip_, category_, desc_, module_, operation_, operation_date_, operation_user_, operation_user_nickname_, source_, title_) VALUES ('a8e843c3-77b8-4234-93e7-7eea01b1613f', '0:0:0:0:0:0:0:1', '系统日志', '成员数 : 1 -> 2
', '系统模块', '修改', '2018-03-25 18:05:16', 'admin', '系统管理员', 'http://localhost:8080/bdf3.notice.ui.view.Chat.d', '运维群');
INSERT INTO bdf3_log_info (id_, ip_, category_, desc_, module_, operation_, operation_date_, operation_user_, operation_user_nickname_, source_, title_) VALUES ('be832c98-386c-4192-91cc-2407421377a7', '0:0:0:0:0:0:0:1', '系统日志', '', '系统模块', '新增', '2018-03-25 18:05:16', 'admin', '系统管理员', 'http://localhost:8080/bdf3.notice.ui.view.Chat.d', '4758a57b-4eeb-4ece-b342-7f11bc4d6327');
INSERT INTO bdf3_log_info (id_, ip_, category_, desc_, module_, operation_, operation_date_, operation_user_, operation_user_nickname_, source_, title_) VALUES ('f3bb76fe-78be-484e-8047-61e70adb3498', '0:0:0:0:0:0:0:1', '系统日志', '成员数 : 2 -> 3
', '系统模块', '修改', '2018-03-25 18:05:17', 'admin', '系统管理员', 'http://localhost:8080/bdf3.notice.ui.view.Chat.d', '运维群');
INSERT INTO bdf3_log_info (id_, ip_, category_, desc_, module_, operation_, operation_date_, operation_user_, operation_user_nickname_, source_, title_) VALUES ('2e7249e3-c173-4cc9-bd88-7891aed19d8b', '0:0:0:0:0:0:0:1', '系统日志', '', '系统模块', '新增', '2018-03-25 18:05:17', 'admin', '系统管理员', 'http://localhost:8080/bdf3.notice.ui.view.Chat.d', '54ecb150-e8c7-4bfa-852d-d9967c976acc');
INSERT INTO bdf3_log_info (id_, ip_, category_, desc_, module_, operation_, operation_date_, operation_user_, operation_user_nickname_, source_, title_) VALUES ('5bf6e0ca-02ff-451b-a55b-c2fdb14d204f', '0:0:0:0:0:0:0:1', '系统日志', '链接数 : 0 -> 2
', '系统模块', '修改', '2018-03-25 18:08:53', 'admin', '系统管理员', 'http://localhost:8080/bdf3.notice.ui.view.Chat.d', '运维群');
INSERT INTO bdf3_log_info (id_, ip_, category_, desc_, module_, operation_, operation_date_, operation_user_, operation_user_nickname_, source_, title_) VALUES ('2b4783d4-d7c7-4675-aa03-4078f2371e2e', '0:0:0:0:0:0:0:1', '系统日志', '', '系统模块', '新增', '2018-03-25 18:08:53', 'admin', '系统管理员', 'http://localhost:8080/bdf3.notice.ui.view.Chat.d', '系统日志');
INSERT INTO bdf3_log_info (id_, ip_, category_, desc_, module_, operation_, operation_date_, operation_user_, operation_user_nickname_, source_, title_) VALUES ('05d22968-63cf-4631-b538-e4ea3bc63e8c', '0:0:0:0:0:0:0:1', '系统日志', '', '系统模块', '新增', '2018-03-25 18:08:53', 'admin', '系统管理员', 'http://localhost:8080/bdf3.notice.ui.view.Chat.d', '云数据库');
INSERT INTO bdf3_log_info (id_, ip_, category_, desc_, module_, operation_, operation_date_, operation_user_, operation_user_nickname_, source_, title_) VALUES ('b0e8b4ba-e6b9-4beb-b293-5cb0bb94ee7f', '0:0:0:0:0:0:0:1', '系统日志', '链接数 : 0 -> 2
', '系统模块', '修改', '2018-03-25 18:14:47', 'admin', '系统管理员', 'http://localhost:8080/bdf3.notice.ui.view.Chat.d', '运维群');
INSERT INTO bdf3_log_info (id_, ip_, category_, desc_, module_, operation_, operation_date_, operation_user_, operation_user_nickname_, source_, title_) VALUES ('25dd2a40-8b0c-4378-bc6b-7fc97a1717e8', '0:0:0:0:0:0:0:1', '系统日志', '链接 : bdf3.log.view.LogMaintain.d -> bdf3.log.view.LogInfoMaintain.d
', '系统模块', '修改', '2018-03-25 18:14:47', 'admin', '系统管理员', 'http://localhost:8080/bdf3.notice.ui.view.Chat.d', '系统日志');
INSERT INTO bdf3_log_info (id_, ip_, category_, desc_, module_, operation_, operation_date_, operation_user_, operation_user_nickname_, source_, title_) VALUES ('47682ab3-a6cc-46eb-aab7-be92ec17946f', '0:0:0:0:0:0:0:1', '系统日志', 'id : null -> 4a757609-54a2-4a2c-8b4a-489651b4ae5f
群名称 : null -> 个人办公
群图标 : null -> fa fa-diamond purple-text
创建者 : null -> lisi
创建于 : null -> Sun Mar 25 18:39:30 CST 2018
公告群 : null -> false
临时群 : null -> false
系统群 : null -> false
privateLetter : null -> false
成员数 : null -> 1
链接数 : null -> 0
群描述 : null -> 用于自己办公
', '系统模块', '新增', '2018-03-25 18:39:30', 'lisi', '李四', 'http://localhost:8080/bdf3.notice.ui.view.Chat.d', '个人办公');
INSERT INTO bdf3_log_info (id_, ip_, category_, desc_, module_, operation_, operation_date_, operation_user_, operation_user_nickname_, source_, title_) VALUES ('00c56149-42a9-4da6-8fdc-566e623f05d5', '0:0:0:0:0:0:0:1', '系统日志', 'administrator : false -> true
', '系统模块', '新增', '2018-03-25 18:39:30', 'lisi', '李四', 'http://localhost:8080/bdf3.notice.ui.view.Chat.d', '3bd4f17a-5a27-4971-b9d6-4561d40c0eac');
INSERT INTO bdf3_log_info (id_, ip_, category_, desc_, module_, operation_, operation_date_, operation_user_, operation_user_nickname_, source_, title_) VALUES ('678cf76a-56c5-494c-8e0c-13f044c466d4', '0:0:0:0:0:0:0:1', '系统日志', 'id : null -> f35bd80c-26d2-477c-8c8d-55d737736692
群名称 : null -> 系统公告
群图标 : null -> fa fa-bullhorn blue-text
创建者 : null -> admin
创建于 : null -> Sun Mar 25 22:17:57 CST 2018
公告群 : null -> true
临时群 : null -> false
系统群 : null -> false
privateLetter : null -> false
成员数 : null -> 1
链接数 : null -> 0
', '系统模块', '新增', '2018-03-25 22:17:57', 'admin', '系统管理员', 'http://localhost:8080/bdf3.notice.ui.view.Chat.d', '系统公告');
INSERT INTO bdf3_log_info (id_, ip_, category_, desc_, module_, operation_, operation_date_, operation_user_, operation_user_nickname_, source_, title_) VALUES ('c970e5d7-fa5f-40e8-8ccc-13aa726dfd97', '0:0:0:0:0:0:0:1', '系统日志', 'administrator : false -> true
', '系统模块', '新增', '2018-03-25 22:17:57', 'admin', '系统管理员', 'http://localhost:8080/bdf3.notice.ui.view.Chat.d', '61298828-6948-4411-bd46-b0cfcf056eb2');
INSERT INTO bdf3_log_info (id_, ip_, category_, desc_, module_, operation_, operation_date_, operation_user_, operation_user_nickname_, source_, title_) VALUES ('8c622d50-fb04-47f6-95e2-d21a7d5ab05e', '0:0:0:0:0:0:0:1', '系统日志', 'id : null -> a1d0777e-60ad-4623-a563-5f9875fd14e6
creator : null -> admin
createTime : null -> Mon Mar 26 14:58:50 CST 2018
all : null -> false
temporary : null -> false
system : null -> false
privateLetter : null -> true
memberCount : null -> 2
', '系统模块', '新增', '2018-03-26 14:58:50', 'admin', '系统管理员', 'http://localhost:8080/bdf3.notice.ui.view.Chat.d', 'a1d0777e-60ad-4623-a563-5f9875fd14e6');
INSERT INTO bdf3_log_info (id_, ip_, category_, desc_, module_, operation_, operation_date_, operation_user_, operation_user_nickname_, source_, title_) VALUES ('68004fb2-fccd-4bbe-8de8-3cf2183db574', '0:0:0:0:0:0:0:1', '系统日志', 'active : false -> true
', '系统模块', '新增', '2018-03-26 14:58:51', 'admin', '系统管理员', 'http://localhost:8080/bdf3.notice.ui.view.Chat.d', 'f01d1724-667c-40a0-937d-19c1b2eee2c3');
INSERT INTO bdf3_log_info (id_, ip_, category_, desc_, module_, operation_, operation_date_, operation_user_, operation_user_nickname_, source_, title_) VALUES ('74ae213e-8df2-4ee8-abd6-dbdc09b50c9e', '0:0:0:0:0:0:0:1', '系统日志', 'active : false -> true
', '系统模块', '新增', '2018-03-26 14:58:51', 'admin', '系统管理员', 'http://localhost:8080/bdf3.notice.ui.view.Chat.d', '7f5e4926-d89f-45c3-b160-03588d31902c');
INSERT INTO bdf3_log_info (id_, ip_, category_, desc_, module_, operation_, operation_date_, operation_user_, operation_user_nickname_, source_, title_) VALUES ('e0ecd7ad-d3c1-4f4e-bf6a-a8398e5dee2d', '0:0:0:0:0:0:0:1', '系统日志', '', '系统模块', '删除', '2018-03-26 16:36:39', 'admin', '系统管理员', 'http://localhost:8080/bdf3.notice.ui.view.Chat.d', 'a7b36526-22ac-43c8-a2a5-6c8183ae074c');
INSERT INTO bdf3_log_info (id_, ip_, category_, desc_, module_, operation_, operation_date_, operation_user_, operation_user_nickname_, source_, title_) VALUES ('3e074b88-d0d3-4d88-86cb-0941e6e34233', '0:0:0:0:0:0:0:1', '系统日志', '', '系统模块', '删除', '2018-03-26 16:39:21', 'admin', '系统管理员', 'http://localhost:8080/bdf3.notice.ui.view.Chat.d', 'a7b36526-22ac-43c8-a2a5-6c8183ae074c');
INSERT INTO bdf3_log_info (id_, ip_, category_, desc_, module_, operation_, operation_date_, operation_user_, operation_user_nickname_, source_, title_) VALUES ('25a07729-da6c-498e-82a5-8049d70d353b', '0:0:0:0:0:0:0:1', '系统日志', '成员数 : 2 -> 3
链接数 : 0 -> 2
', '系统模块', '修改', '2018-03-26 16:42:50', 'admin', '系统管理员', 'http://localhost:8080/bdf3.notice.ui.view.Chat.d', '系统管理群');
INSERT INTO bdf3_log_info (id_, ip_, category_, desc_, module_, operation_, operation_date_, operation_user_, operation_user_nickname_, source_, title_) VALUES ('901f1472-a7b2-4e53-be22-727c441e9387', '0:0:0:0:0:0:0:1', '系统日志', '', '系统模块', '新增', '2018-03-26 16:42:50', 'admin', '系统管理员', 'http://localhost:8080/bdf3.notice.ui.view.Chat.d', 'e2b2dd22-85f4-45ee-8e31-8d7e0c5db3b1');
INSERT INTO bdf3_log_info (id_, ip_, category_, desc_, module_, operation_, operation_date_, operation_user_, operation_user_nickname_, source_, title_) VALUES ('9163e979-e8e9-485f-8502-1291075afb08', '0:0:0:0:0:0:0:1', '系统日志', '', '系统模块', '删除', '2018-03-26 16:43:46', 'admin', '系统管理员', 'http://localhost:8080/bdf3.notice.ui.view.Chat.d', '1528df4b-4056-40b2-8490-ba54ff842a78');
INSERT INTO bdf3_log_info (id_, ip_, category_, desc_, module_, operation_, operation_date_, operation_user_, operation_user_nickname_, source_, title_) VALUES ('724bf56c-cd61-4192-96fa-8d01cb0c2109', '0:0:0:0:0:0:0:1', '系统日志', '成员数 : 3 -> 4
链接数 : 0 -> 2
', '系统模块', '修改', '2018-03-26 16:51:14', 'admin', '系统管理员', 'http://localhost:8080/bdf3.notice.ui.view.Chat.d', '系统管理群');
INSERT INTO bdf3_log_info (id_, ip_, category_, desc_, module_, operation_, operation_date_, operation_user_, operation_user_nickname_, source_, title_) VALUES ('85b93591-9d08-4584-8bd6-1fa1191236fc', '0:0:0:0:0:0:0:1', '系统日志', '', '系统模块', '新增', '2018-03-26 16:51:14', 'admin', '系统管理员', 'http://localhost:8080/bdf3.notice.ui.view.Chat.d', '8f06e772-a63e-4524-be68-9e2c05c017d3');
INSERT INTO bdf3_log_info (id_, ip_, category_, desc_, module_, operation_, operation_date_, operation_user_, operation_user_nickname_, source_, title_) VALUES ('23b880e5-f3e6-4eca-994b-1810289faf5f', '0:0:0:0:0:0:0:1', '系统日志', '成员数 : 4 -> 3
链接数 : 0 -> 2
', '系统模块', '修改', '2018-03-26 16:57:09', 'admin', '系统管理员', 'http://localhost:8080/bdf3.notice.ui.view.Chat.d', '系统管理群');
INSERT INTO bdf3_log_info (id_, ip_, category_, desc_, module_, operation_, operation_date_, operation_user_, operation_user_nickname_, source_, title_) VALUES ('3ea84ff8-b763-4e8e-9bc8-6365f45c7a5b', '0:0:0:0:0:0:0:1', '系统日志', '', '系统模块', '删除', '2018-03-26 16:57:09', 'admin', '系统管理员', 'http://localhost:8080/bdf3.notice.ui.view.Chat.d', '1528df4b-4056-40b2-8490-ba54ff842a78');
INSERT INTO bdf3_log_info (id_, ip_, category_, desc_, module_, operation_, operation_date_, operation_user_, operation_user_nickname_, source_, title_) VALUES ('680f9d42-90eb-4d36-b360-4ab4aab63159', '0:0:0:0:0:0:0:1', '系统日志', '成员数 : 3 -> 2
链接数 : 0 -> 2
', '系统模块', '修改', '2018-03-26 17:01:12', 'admin', '系统管理员', 'http://localhost:8080/bdf3.notice.ui.view.Chat.d', '系统管理群');
INSERT INTO bdf3_log_info (id_, ip_, category_, desc_, module_, operation_, operation_date_, operation_user_, operation_user_nickname_, source_, title_) VALUES ('0f8bfdb1-e8f6-41a6-8d97-effd2ff29f3c', '0:0:0:0:0:0:0:1', '系统日志', '', '系统模块', '删除', '2018-03-26 17:01:12', 'admin', '系统管理员', 'http://localhost:8080/bdf3.notice.ui.view.Chat.d', '1c66b36d-7122-49a7-9113-a4a029779855');
INSERT INTO bdf3_log_info (id_, ip_, category_, desc_, module_, operation_, operation_date_, operation_user_, operation_user_nickname_, source_, title_) VALUES ('01e0e871-0363-4ec9-8154-febacfc06904', '0:0:0:0:0:0:0:1', '系统日志', '成员数 : 2 -> 3
链接数 : 0 -> 2
', '系统模块', '修改', '2018-03-26 17:02:12', 'admin', '系统管理员', 'http://localhost:8080/bdf3.notice.ui.view.Chat.d', '系统管理群');
INSERT INTO bdf3_log_info (id_, ip_, category_, desc_, module_, operation_, operation_date_, operation_user_, operation_user_nickname_, source_, title_) VALUES ('2f6cea21-e9b2-43d8-8f95-72e63a9c2eb8', '0:0:0:0:0:0:0:1', '系统日志', '', '系统模块', '新增', '2018-03-26 17:02:12', 'admin', '系统管理员', 'http://localhost:8080/bdf3.notice.ui.view.Chat.d', 'fa7b7748-d3b5-484b-8913-c363748d5510');
INSERT INTO bdf3_log_info (id_, ip_, category_, desc_, module_, operation_, operation_date_, operation_user_, operation_user_nickname_, source_, title_) VALUES ('60c5f73c-96a9-4afc-bb21-9a0de7690bca', '0:0:0:0:0:0:0:1', '系统日志', '成员数 : 3 -> 4
链接数 : 0 -> 2
', '系统模块', '修改', '2018-03-26 17:03:05', 'admin', '系统管理员', 'http://localhost:8080/bdf3.notice.ui.view.Chat.d', '系统管理群');
INSERT INTO bdf3_log_info (id_, ip_, category_, desc_, module_, operation_, operation_date_, operation_user_, operation_user_nickname_, source_, title_) VALUES ('e94faf17-0701-4acd-8d8a-bf5cd81c3851', '0:0:0:0:0:0:0:1', '系统日志', '', '系统模块', '新增', '2018-03-26 17:03:05', 'admin', '系统管理员', 'http://localhost:8080/bdf3.notice.ui.view.Chat.d', 'ce8a722c-4ef9-413a-9501-d1edecbaca45');
INSERT INTO bdf3_log_info (id_, ip_, category_, desc_, module_, operation_, operation_date_, operation_user_, operation_user_nickname_, source_, title_) VALUES ('c6cb6c0c-7485-456e-8b93-656193f36469', '0:0:0:0:0:0:0:1', '系统日志', '成员数 : 4 -> 3
链接数 : 0 -> 2
', '系统模块', '修改', '2018-03-26 17:03:22', 'admin', '系统管理员', 'http://localhost:8080/bdf3.notice.ui.view.Chat.d', '系统管理群');
INSERT INTO bdf3_log_info (id_, ip_, category_, desc_, module_, operation_, operation_date_, operation_user_, operation_user_nickname_, source_, title_) VALUES ('a183fa76-dc91-4869-aa45-baba7e31c943', '0:0:0:0:0:0:0:1', '系统日志', '', '系统模块', '删除', '2018-03-26 17:03:22', 'admin', '系统管理员', 'http://localhost:8080/bdf3.notice.ui.view.Chat.d', 'ce8a722c-4ef9-413a-9501-d1edecbaca45');
INSERT INTO bdf3_log_info (id_, ip_, category_, desc_, module_, operation_, operation_date_, operation_user_, operation_user_nickname_, source_, title_) VALUES ('d9295594-4cbf-47bc-91d6-ab2c25b63400', '0:0:0:0:0:0:0:1', '系统日志', '成员数 : 1 -> 2
', '系统模块', '修改', '2018-03-26 17:04:47', 'admin', '系统管理员', 'http://localhost:8080/bdf3.notice.ui.view.Chat.d', '系统公告');
INSERT INTO bdf3_log_info (id_, ip_, category_, desc_, module_, operation_, operation_date_, operation_user_, operation_user_nickname_, source_, title_) VALUES ('1fa3850e-0da8-44f1-b0ea-c375160e586d', '0:0:0:0:0:0:0:1', '系统日志', '', '系统模块', '新增', '2018-03-26 17:04:47', 'admin', '系统管理员', 'http://localhost:8080/bdf3.notice.ui.view.Chat.d', '5eeeaaed-24c0-4c04-a433-32d2505e827c');
INSERT INTO bdf3_log_info (id_, ip_, category_, desc_, module_, operation_, operation_date_, operation_user_, operation_user_nickname_, source_, title_) VALUES ('3e014a4f-e61e-4fb9-b09c-497ae54b636b', '0:0:0:0:0:0:0:1', '系统日志', '成员数 : 3 -> 2
链接数 : 0 -> 2
', '系统模块', '修改', '2018-03-26 17:06:39', 'admin', '系统管理员', 'http://localhost:8080/bdf3.notice.ui.view.Chat.d', '系统管理群');
INSERT INTO bdf3_log_info (id_, ip_, category_, desc_, module_, operation_, operation_date_, operation_user_, operation_user_nickname_, source_, title_) VALUES ('255ef9bc-69df-4b64-98d7-a6ded02012c2', '0:0:0:0:0:0:0:1', '系统日志', '', '系统模块', '删除', '2018-03-26 17:06:39', 'admin', '系统管理员', 'http://localhost:8080/bdf3.notice.ui.view.Chat.d', '9c529736-a6d2-4aee-84d8-64f767fa88db');
INSERT INTO bdf3_log_info (id_, ip_, category_, desc_, module_, operation_, operation_date_, operation_user_, operation_user_nickname_, source_, title_) VALUES ('bec9e3b1-482a-4c01-9aff-3b910805d3c1', '0:0:0:0:0:0:0:1', '系统日志', '成员数 : 2 -> 3
链接数 : 0 -> 2
', '系统模块', '修改', '2018-03-26 17:06:42', 'admin', '系统管理员', 'http://localhost:8080/bdf3.notice.ui.view.Chat.d', '系统管理群');
INSERT INTO bdf3_log_info (id_, ip_, category_, desc_, module_, operation_, operation_date_, operation_user_, operation_user_nickname_, source_, title_) VALUES ('1d0f6547-b12d-4f99-b753-0aa457c1963c', '0:0:0:0:0:0:0:1', '系统日志', '', '系统模块', '新增', '2018-03-26 17:06:42', 'admin', '系统管理员', 'http://localhost:8080/bdf3.notice.ui.view.Chat.d', '0bf150aa-75b0-49eb-bd74-53a8a5c561a6');
INSERT INTO bdf3_log_info (id_, ip_, category_, desc_, module_, operation_, operation_date_, operation_user_, operation_user_nickname_, source_, title_) VALUES ('142483a5-ddd5-4840-9706-986c42838d0b', '0:0:0:0:0:0:0:1', '系统日志', '成员数 : 3 -> 2
链接数 : 0 -> 2
', '系统模块', '修改', '2018-03-26 17:06:43', 'admin', '系统管理员', 'http://localhost:8080/bdf3.notice.ui.view.Chat.d', '系统管理群');
INSERT INTO bdf3_log_info (id_, ip_, category_, desc_, module_, operation_, operation_date_, operation_user_, operation_user_nickname_, source_, title_) VALUES ('1e43285b-5a44-4d48-8b83-f2f6c5e612e1', '0:0:0:0:0:0:0:1', '系统日志', '', '系统模块', '删除', '2018-03-26 17:06:43', 'admin', '系统管理员', 'http://localhost:8080/bdf3.notice.ui.view.Chat.d', '0bf150aa-75b0-49eb-bd74-53a8a5c561a6');
INSERT INTO bdf3_log_info (id_, ip_, category_, desc_, module_, operation_, operation_date_, operation_user_, operation_user_nickname_, source_, title_) VALUES ('de927ca4-0cf3-494e-a384-bdb12c445ca6', '0:0:0:0:0:0:0:1', '系统日志', '成员数 : 2 -> 3
链接数 : 0 -> 2
', '系统模块', '修改', '2018-03-26 17:08:15', 'admin', '系统管理员', 'http://localhost:8080/bdf3.notice.ui.view.Chat.d', '系统管理群');
INSERT INTO bdf3_log_info (id_, ip_, category_, desc_, module_, operation_, operation_date_, operation_user_, operation_user_nickname_, source_, title_) VALUES ('2e7bdbc7-f5b0-47e4-9e43-2aac0bf4c296', '0:0:0:0:0:0:0:1', '系统日志', '', '系统模块', '新增', '2018-03-26 17:08:15', 'admin', '系统管理员', 'http://localhost:8080/bdf3.notice.ui.view.Chat.d', '3ca5c101-21c7-4a3c-9730-9222e1d0067b');
INSERT INTO bdf3_log_info (id_, ip_, category_, desc_, module_, operation_, operation_date_, operation_user_, operation_user_nickname_, source_, title_) VALUES ('5ed2b786-38ed-46d6-a983-b20435b2f9c5', '0:0:0:0:0:0:0:1', '系统日志', '成员数 : 3 -> 2
链接数 : 0 -> 2
', '系统模块', '修改', '2018-03-26 17:08:17', 'admin', '系统管理员', 'http://localhost:8080/bdf3.notice.ui.view.Chat.d', '系统管理群');
INSERT INTO bdf3_log_info (id_, ip_, category_, desc_, module_, operation_, operation_date_, operation_user_, operation_user_nickname_, source_, title_) VALUES ('856f1e83-a883-4318-8dff-0a9b32353fbc', '0:0:0:0:0:0:0:1', '系统日志', '', '系统模块', '删除', '2018-03-26 17:08:17', 'admin', '系统管理员', 'http://localhost:8080/bdf3.notice.ui.view.Chat.d', '3ca5c101-21c7-4a3c-9730-9222e1d0067b');
INSERT INTO bdf3_log_info (id_, ip_, category_, desc_, module_, operation_, operation_date_, operation_user_, operation_user_nickname_, source_, title_) VALUES ('780b686a-bdef-4b22-9fa3-8d755c63f669', '0:0:0:0:0:0:0:1', '系统日志', '成员数 : 2 -> 3
链接数 : 0 -> 2
', '系统模块', '修改', '2018-03-26 17:08:21', 'admin', '系统管理员', 'http://localhost:8080/bdf3.notice.ui.view.Chat.d', '系统管理群');
INSERT INTO bdf3_log_info (id_, ip_, category_, desc_, module_, operation_, operation_date_, operation_user_, operation_user_nickname_, source_, title_) VALUES ('b067cb6d-8d3c-4374-b424-603728dbe9d5', '0:0:0:0:0:0:0:1', '系统日志', '', '系统模块', '新增', '2018-03-26 17:08:21', 'admin', '系统管理员', 'http://localhost:8080/bdf3.notice.ui.view.Chat.d', 'dd0de89b-2d89-4b4f-bd4f-07b617a8155e');
INSERT INTO bdf3_log_info (id_, ip_, category_, desc_, module_, operation_, operation_date_, operation_user_, operation_user_nickname_, source_, title_) VALUES ('4d09744a-92df-4e15-b271-46144d3a9d39', '0:0:0:0:0:0:0:1', '系统日志', '成员数 : 3 -> 2
链接数 : 0 -> 2
', '系统模块', '修改', '2018-03-26 17:14:10', 'admin', '系统管理员', 'http://localhost:8080/bdf3.notice.ui.view.Chat.d', '系统管理群');
INSERT INTO bdf3_log_info (id_, ip_, category_, desc_, module_, operation_, operation_date_, operation_user_, operation_user_nickname_, source_, title_) VALUES ('6de52890-e903-4d7c-87d3-eda60dfe7c53', '0:0:0:0:0:0:0:1', '系统日志', '', '系统模块', '删除', '2018-03-26 17:14:10', 'admin', '系统管理员', 'http://localhost:8080/bdf3.notice.ui.view.Chat.d', '9c529736-a6d2-4aee-84d8-64f767fa88db');
INSERT INTO bdf3_log_info (id_, ip_, category_, desc_, module_, operation_, operation_date_, operation_user_, operation_user_nickname_, source_, title_) VALUES ('f763a780-e6d8-4891-8b21-51d860d7d3ee', '0:0:0:0:0:0:0:1', '系统日志', '成员数 : 2 -> 3
链接数 : 0 -> 2
', '系统模块', '修改', '2018-03-26 17:14:20', 'admin', '系统管理员', 'http://localhost:8080/bdf3.notice.ui.view.Chat.d', '系统管理群');
INSERT INTO bdf3_log_info (id_, ip_, category_, desc_, module_, operation_, operation_date_, operation_user_, operation_user_nickname_, source_, title_) VALUES ('c0bf2c57-0c5e-498e-8c06-5713c8ac28a9', '0:0:0:0:0:0:0:1', '系统日志', '', '系统模块', '新增', '2018-03-26 17:14:20', 'admin', '系统管理员', 'http://localhost:8080/bdf3.notice.ui.view.Chat.d', '50a34677-3a9d-4f36-b755-20e9e1c1dad4');
INSERT INTO bdf3_log_info (id_, ip_, category_, desc_, module_, operation_, operation_date_, operation_user_, operation_user_nickname_, source_, title_) VALUES ('acdecc0b-0b44-49ff-b727-0b94f9eb2193', '0:0:0:0:0:0:0:1', '系统日志', '成员数 : 3 -> 2
链接数 : 0 -> 2
', '系统模块', '修改', '2018-03-26 17:14:43', 'admin', '系统管理员', 'http://localhost:8080/bdf3.notice.ui.view.Chat.d', '系统管理群');
INSERT INTO bdf3_log_info (id_, ip_, category_, desc_, module_, operation_, operation_date_, operation_user_, operation_user_nickname_, source_, title_) VALUES ('73606dbe-f1ec-4cd8-ac6d-dd9d245d92b9', '0:0:0:0:0:0:0:1', '系统日志', '', '系统模块', '删除', '2018-03-26 17:14:43', 'admin', '系统管理员', 'http://localhost:8080/bdf3.notice.ui.view.Chat.d', '9c529736-a6d2-4aee-84d8-64f767fa88db');
INSERT INTO bdf3_log_info (id_, ip_, category_, desc_, module_, operation_, operation_date_, operation_user_, operation_user_nickname_, source_, title_) VALUES ('1b765f15-7eb7-41b8-a111-17971551b7b1', '0:0:0:0:0:0:0:1', '系统日志', '成员数 : 2 -> 1
链接数 : 0 -> 2
', '系统模块', '修改', '2018-03-26 17:14:44', 'admin', '系统管理员', 'http://localhost:8080/bdf3.notice.ui.view.Chat.d', '系统管理群');
INSERT INTO bdf3_log_info (id_, ip_, category_, desc_, module_, operation_, operation_date_, operation_user_, operation_user_nickname_, source_, title_) VALUES ('76168902-a101-4a6f-a591-c989a49f165d', '0:0:0:0:0:0:0:1', '系统日志', '', '系统模块', '删除', '2018-03-26 17:14:44', 'admin', '系统管理员', 'http://localhost:8080/bdf3.notice.ui.view.Chat.d', '1528df4b-4056-40b2-8490-ba54ff842a78');
INSERT INTO bdf3_log_info (id_, ip_, category_, desc_, module_, operation_, operation_date_, operation_user_, operation_user_nickname_, source_, title_) VALUES ('4bf01145-2a5f-48ab-b297-5ecfcb4c2db0', '0:0:0:0:0:0:0:1', '系统日志', '', '系统模块', '新增', '2018-03-26 17:24:50', 'lisi', '李四', 'http://localhost:8080/bdf3.notice.ui.view.Chat.d', '21f833e1-7c04-4aef-abd7-e31d35bf3629');
INSERT INTO bdf3_log_info (id_, ip_, category_, desc_, module_, operation_, operation_date_, operation_user_, operation_user_nickname_, source_, title_) VALUES ('a261b9e5-b48b-4a26-81ea-12cb46504f6a', '0:0:0:0:0:0:0:1', '系统日志', '', '系统模块', '删除', '2018-03-26 17:25:28', 'lisi', '李四', 'http://localhost:8080/bdf3.notice.ui.view.Chat.d', 'ba30f232-d842-4f4f-ad93-ba9c6df4a9ec');
INSERT INTO bdf3_log_info (id_, ip_, category_, desc_, module_, operation_, operation_date_, operation_user_, operation_user_nickname_, source_, title_) VALUES ('77d65853-4b87-4bcb-98f9-e1dc991f31b4', '0:0:0:0:0:0:0:1', '系统日志', '', '系统模块', '新增', '2018-03-26 17:36:30', 'lisi', '李四', 'http://localhost:8080/bdf3.notice.ui.view.Chat.d', 'afc43317-f9bc-4651-bd60-465d2faec860');
INSERT INTO bdf3_log_info (id_, ip_, category_, desc_, module_, operation_, operation_date_, operation_user_, operation_user_nickname_, source_, title_) VALUES ('d2630c83-7809-417d-8fbc-365bb19f184a', '0:0:0:0:0:0:0:1', '系统日志', '', '系统模块', '新增', '2018-03-26 17:37:37', 'admin', '系统管理员', 'http://localhost:8080/bdf3.notice.ui.view.Chat.d', '2acd280e-ff44-4edc-bd22-4746ac966d81');
INSERT INTO bdf3_log_info (id_, ip_, category_, desc_, module_, operation_, operation_date_, operation_user_, operation_user_nickname_, source_, title_) VALUES ('f3685219-c331-465a-8c61-7cba952a7086', '0:0:0:0:0:0:0:1', '系统日志', 'id : null -> d3753b1d-a256-44b6-a391-8a2c1a34e93f
名称 : null -> 大号红字
图标 : null -> fa fa-font red-text
CSS : null -> .big-size-red-word {
  font-size: 2em;
  color: rgb(255, 0, 0);
}
HTML : null -> <div class="big-size-red-word">{{ message.content }}</div>

全局 : null -> true
下线 : null -> false
微程序 : null -> false
描述 : null -> 大号红色字体的消息模版
可显示 : null -> false
', '系统模块', '新增', '2018-03-29 19:04:44', 'admin', '系统管理员', 'http://localhost:8080/bdf3.notice.ui.view.TemplateMaintain.d', '大号红字');
INSERT INTO bdf3_log_info (id_, ip_, category_, desc_, module_, operation_, operation_date_, operation_user_, operation_user_nickname_, source_, title_) VALUES ('187ac4ba-414b-439a-be68-47b23869b405', '0:0:0:0:0:0:0:1', '系统日志', 'id : null -> ece99ee9-577e-4148-9037-d3f4ed553f7a
名称 : null -> 常用消息
图标 : null -> fa fa-newspaper-o
CSS : null -> .tpl-message {
  color: rgba(0, 0, 0, .7);
  line-height: 1.5em;
}

.tpl-message:hover {
  background-color: #fafafa;
}
HTML : null -> <!---->
<!---->
<div clas="tpl-message">我正在开会，稍后给你回复</div>
<div clas="tpl-message">再见，下次再聊</div>
<div clas="tpl-message">走，吃饭去</div>
<div clas="tpl-message">这个问题很严重</div>
<div clas="tpl-message">我下班了，明天在讨论</div>
<div clas="tpl-message">稍等，马上给你答复</div>
<div clas="tpl-message">谢谢你的帮助</div>
全局 : null -> false
下线 : null -> false
微程序 : null -> false
可显示 : null -> false
', '系统模块', '新增', '2018-03-29 19:59:35', 'admin', '系统管理员', 'http://localhost:8080/bdf3.notice.ui.view.TemplateMaintain.d', '常用消息');
INSERT INTO bdf3_log_info (id_, ip_, category_, desc_, module_, operation_, operation_date_, operation_user_, operation_user_nickname_, source_, title_) VALUES ('9ef6043c-ee9b-4c2d-99a9-6a6300d7f8fe', '0:0:0:0:0:0:0:1', '系统日志', 'CSS : .tpl-message {
  color: rgba(0, 0, 0, .7);
  line-height: 1.5em;
}

.tpl-message:hover {
  background-color: #fafafa;
} -> /****/
/****/
.tpl-message {
  color: rgba(0, 0, 0, .7);
  line-height: 1.5em;
}

.tpl-message:hover {
  background-color: #fafafa;
}
', '系统模块', '修改', '2018-03-29 20:07:43', 'admin', '系统管理员', 'http://localhost:8080/bdf3.notice.ui.view.TemplateMaintain.d', '常用消息');
INSERT INTO bdf3_log_info (id_, ip_, category_, desc_, module_, operation_, operation_date_, operation_user_, operation_user_nickname_, source_, title_) VALUES ('16925ccd-81be-488e-939d-43f4f8d04e91', '0:0:0:0:0:0:0:1', '系统日志', '全局 : false -> true
', '系统模块', '修改', '2018-03-29 20:08:20', 'admin', '系统管理员', 'http://localhost:8080/bdf3.notice.ui.view.TemplateMaintain.d', '常用消息');
INSERT INTO bdf3_log_info (id_, ip_, category_, desc_, module_, operation_, operation_date_, operation_user_, operation_user_nickname_, source_, title_) VALUES ('622445e0-afad-4542-95cb-ddf569145412', '0:0:0:0:0:0:0:1', '系统日志', '可显示 : false -> true
', '系统模块', '修改', '2018-03-29 20:08:23', 'admin', '系统管理员', 'http://localhost:8080/bdf3.notice.ui.view.TemplateMaintain.d', '常用消息');
INSERT INTO bdf3_log_info (id_, ip_, category_, desc_, module_, operation_, operation_date_, operation_user_, operation_user_nickname_, source_, title_) VALUES ('7e4b498a-1959-4a1c-bf9a-53407cb4529c', '0:0:0:0:0:0:0:1', '系统日志', 'HTML : <!---->
<!---->
<div clas="tpl-message">我正在开会，稍后给你回复</div>
<div clas="tpl-message">再见，下次再聊</div>
<div clas="tpl-message">走，吃饭去</div>
<div clas="tpl-message">这个问题很严重</div>
<div clas="tpl-message">我下班了，明天在讨论</div>
<div clas="tpl-message">稍等，马上给你答复</div>
<div clas="tpl-message">谢谢你的帮助</div> -> <!---->
<!---->
<div class="tpl-message">我正在开会，稍后给你回复</div>
<div class="tpl-message">再见，下次再聊</div>
<div class="tpl-message">走，吃饭去</div>
<div class="tpl-message">这个问题很严重</div>
<div class="tpl-message">我下班了，明天在讨论</div>
<div class="tpl-message">稍等，马上给你答复</div>
<div class="tpl-message">谢谢你的帮助</div>
', '系统模块', '修改', '2018-03-29 20:09:42', 'admin', '系统管理员', 'http://localhost:8080/bdf3.notice.ui.view.TemplateMaintain.d', '常用消息');
INSERT INTO bdf3_log_info (id_, ip_, category_, desc_, module_, operation_, operation_date_, operation_user_, operation_user_nickname_, source_, title_) VALUES ('8b2b930f-e1ff-4c22-8aa6-9ae29cd00805', '0:0:0:0:0:0:0:1', '系统日志', 'CSS : /****/
/****/
.tpl-message {
  color: rgba(0, 0, 0, .7);
  line-height: 1.5em;
}

.tpl-message:hover {
  background-color: #fafafa;
} -> /****/
/****/
.tpl-message {
  color: rgba(0, 0, 0, .7);
  line-height: 2.5em;
  font-size: 1.2em;
}

.tpl-message:hover {
  background-color: #fafafa;
}
', '系统模块', '修改', '2018-03-29 20:10:26', 'admin', '系统管理员', 'http://localhost:8080/bdf3.notice.ui.view.TemplateMaintain.d', '常用消息');
INSERT INTO bdf3_log_info (id_, ip_, category_, desc_, module_, operation_, operation_date_, operation_user_, operation_user_nickname_, source_, title_) VALUES ('e507ac21-180a-4beb-9e21-bf5661a72448', '0:0:0:0:0:0:0:1', '系统日志', 'CSS : /****/
/****/
.tpl-message {
  color: rgba(0, 0, 0, .7);
  line-height: 2.5em;
  font-size: 1.2em;
}

.tpl-message:hover {
  background-color: #fafafa;
} -> /****/
/****/
.tpl-message {
  color: rgba(0, 0, 0, .7);
  line-height: 2.5em;
  font-size: 1.2em;
  padding: 0 5px;
  border-radius: 3px;
}

.tpl-message:hover {
  background-color: #fafafa;
}
', '系统模块', '修改', '2018-03-29 20:12:00', 'admin', '系统管理员', 'http://localhost:8080/bdf3.notice.ui.view.TemplateMaintain.d', '常用消息');
INSERT INTO bdf3_log_info (id_, ip_, category_, desc_, module_, operation_, operation_date_, operation_user_, operation_user_nickname_, source_, title_) VALUES ('43c90276-fa66-4f9a-a87f-4912ee28f8cd', '0:0:0:0:0:0:0:1', '系统日志', 'CSS : /****/
/****/
.tpl-message {
  color: rgba(0, 0, 0, .7);
  line-height: 2.5em;
  font-size: 1.2em;
  padding: 0 5px;
  border-radius: 3px;
}

.tpl-message:hover {
  background-color: #fafafa;
} -> /****/
/****/
.tpl-message {
  color: rgba(0, 0, 0, .7);
  line-height: 2.5em;
  font-size: 1.2em;
  padding: 0 5px;
  border-radius: 3px;
  cursor: pointer;
}

.tpl-message:hover {
  background-color: #f7f7f7;
}
', '系统模块', '修改', '2018-03-29 20:13:03', 'admin', '系统管理员', 'http://localhost:8080/bdf3.notice.ui.view.TemplateMaintain.d', '常用消息');
INSERT INTO bdf3_log_info (id_, ip_, category_, desc_, module_, operation_, operation_date_, operation_user_, operation_user_nickname_, source_, title_) VALUES ('83435bc4-cdd3-46e1-acac-5339999dc3c6', '0:0:0:0:0:0:0:1', '系统日志', 'JAVASCRIPT : null -> $(tipDom).find(".tpl-message").click({
  alert(getContent());
});
', '系统模块', '修改', '2018-03-29 20:14:16', 'admin', '系统管理员', 'http://localhost:8080/bdf3.notice.ui.view.TemplateMaintain.d', '常用消息');
INSERT INTO bdf3_log_info (id_, ip_, category_, desc_, module_, operation_, operation_date_, operation_user_, operation_user_nickname_, source_, title_) VALUES ('6ed6b8d8-2804-4f0a-90fb-aa5c62da2499', '0:0:0:0:0:0:0:1', '系统日志', 'JAVASCRIPT : $(tipDom).find(".tpl-message").click({
  alert(getContent());
}); -> /****/
/****/
$(tipDom).find(".tpl-message").click({
  alert(getContent());
});
', '系统模块', '修改', '2018-03-29 20:14:39', 'admin', '系统管理员', 'http://localhost:8080/bdf3.notice.ui.view.TemplateMaintain.d', '常用消息');
INSERT INTO bdf3_log_info (id_, ip_, category_, desc_, module_, operation_, operation_date_, operation_user_, operation_user_nickname_, source_, title_) VALUES ('2d9a8a69-0668-45a0-adce-6ec9a1f601dd', '0:0:0:0:0:0:0:1', '系统日志', 'JAVASCRIPT : /****/
/****/
$(tipDom).find(".tpl-message").click({
  alert(getContent());
}); -> /****/
/****/
$(tipDom).find(".tpl-message").click(function() {
  alert(getContent());
});
', '系统模块', '修改', '2018-03-29 20:15:13', 'admin', '系统管理员', 'http://localhost:8080/bdf3.notice.ui.view.TemplateMaintain.d', '常用消息');
INSERT INTO bdf3_log_info (id_, ip_, category_, desc_, module_, operation_, operation_date_, operation_user_, operation_user_nickname_, source_, title_) VALUES ('4063626d-2ba1-4098-ba3d-2a8cc464c1de', '0:0:0:0:0:0:0:1', '系统日志', 'JAVASCRIPT : /****/
/****/
$(tipDom).find(".tpl-message").click(function() {
  alert(getContent());
}); -> /****/
/****/
$(tipDom).find(".tpl-message").click(function() {
  message.content = $(this).text();
  Chat.send(message);
});
', '系统模块', '修改', '2018-03-29 20:16:14', 'admin', '系统管理员', 'http://localhost:8080/bdf3.notice.ui.view.TemplateMaintain.d', '常用消息');
INSERT INTO bdf3_log_info (id_, ip_, category_, desc_, module_, operation_, operation_date_, operation_user_, operation_user_nickname_, source_, title_) VALUES ('2afff71b-0474-443e-9251-7a0da4469ee2', '0:0:0:0:0:0:0:1', '系统日志', 'JAVASCRIPT : /****/
/****/
$(tipDom).find(".tpl-message").click(function() {
  message.content = $(this).text();
  Chat.send(message);
}); -> /****/
/****/
$(tipDom).find(".tpl-message").click(function() {
  alert($(this).text())
  //message.content = $(this).text();
  //Chat.send(message);
});
', '系统模块', '修改', '2018-03-29 20:17:05', 'admin', '系统管理员', 'http://localhost:8080/bdf3.notice.ui.view.TemplateMaintain.d', '常用消息');
INSERT INTO bdf3_log_info (id_, ip_, category_, desc_, module_, operation_, operation_date_, operation_user_, operation_user_nickname_, source_, title_) VALUES ('3529931d-e7c4-48db-a19c-ae0107f55c30', '0:0:0:0:0:0:0:1', '系统日志', 'JAVASCRIPT : /****/
/****/
$(tipDom).find(".tpl-message").click(function() {
  alert($(this).text())
  //message.content = $(this).text();
  //Chat.send(message);
}); -> /****/
/****/
$(tipDom).find(".tpl-message").click(function() {
  console.log(message)
  //message.content = $(this).text();
  //Chat.send(message);
});
', '系统模块', '修改', '2018-03-29 20:21:45', 'admin', '系统管理员', 'http://localhost:8080/bdf3.notice.ui.view.TemplateMaintain.d', '常用消息');
INSERT INTO bdf3_log_info (id_, ip_, category_, desc_, module_, operation_, operation_date_, operation_user_, operation_user_nickname_, source_, title_) VALUES ('b8ffc078-95b3-4d3e-85c1-ffc7baf53794', '0:0:0:0:0:0:0:1', '系统日志', 'JAVASCRIPT : /****/
/****/
$(tipDom).find(".tpl-message").click(function() {
  console.log(message)
  //message.content = $(this).text();
  //Chat.send(message);
}); -> /****/
/****/
$(tipDom).find(".tpl-message").click(function() {
  message.content = $(this).text();
  console.log(message)
  Chat.send(message);
});
', '系统模块', '修改', '2018-03-29 20:22:43', 'admin', '系统管理员', 'http://localhost:8080/bdf3.notice.ui.view.TemplateMaintain.d', '常用消息');
INSERT INTO bdf3_log_info (id_, ip_, category_, desc_, module_, operation_, operation_date_, operation_user_, operation_user_nickname_, source_, title_) VALUES ('d41c9807-6c71-4200-9812-9adcb767d0db', '0:0:0:0:0:0:0:1', '系统日志', '', '系统模块', '修改', '2018-03-29 20:45:31', 'admin', '系统管理员', 'http://localhost:8080/bdf3.notice.ui.view.TemplateMaintain.d', '常用消息');
INSERT INTO bdf3_log_info (id_, ip_, category_, desc_, module_, operation_, operation_date_, operation_user_, operation_user_nickname_, source_, title_) VALUES ('5180dd2f-55ec-4897-b84b-15dc8d4c60a8', '0:0:0:0:0:0:0:1', '系统日志', 'id : null -> a0b7efe9-8b34-407b-a6ad-568846ca6ee3
名称 : null -> 请假单
图标 : null -> fa fa-hotel
HTML : null -> <!---->
<!---->
<div class="tpl-leave-flow">
  <div class="title">请假单</div>
  <div class="edit-item">
    <label>请假事由</label>
    <input type="text"/>
  </div>
  <div class="edit-item">
    <label>请假天数</label>
    <input type="number"/>
  </div>
  <button class="submit">提交申请</button>
<div>
全局 : null -> false
下线 : null -> false
微程序 : null -> false
可显示 : null -> false
', '系统模块', '新增', '2018-03-29 20:45:31', 'admin', '系统管理员', 'http://localhost:8080/bdf3.notice.ui.view.TemplateMaintain.d', '请假单');
INSERT INTO bdf3_log_info (id_, ip_, category_, desc_, module_, operation_, operation_date_, operation_user_, operation_user_nickname_, source_, title_) VALUES ('2842fd41-1384-44ca-af89-2a5d186f0615', '0:0:0:0:0:0:0:1', '系统日志', 'CSS : null -> .tpl-leave-flow .title {
  text-align: center;
  font-size: 1.3em;
  padding: 5px;
}

.tpl-leave-flow .item {
  padding: 10px;
}

.tpl-leave-flow .line {
  border-bottom: 1px solid #eee;
}

.tpl-leave-flow .item input {
  border-radius: 3px;
  border: 1px solid #ccc;
  padding: 5px;
  width: 200px;
}

.tpl-leave-flow button {
  border: 1px solid #eee;
  padding: 5px 10px;
  margin: 10px;
  float: right;
  border-radius: 3px;
}

.tpl-leave-flow .submit {
  background-color: #07c181;
  color: #fff;
  border: 1px solid #07c18;
}
HTML : <!---->
<!---->
<div class="tpl-leave-flow">
  <div class="title">请假单</div>
  <div class="edit-item">
    <label>请假事由</label>
    <input type="text"/>
  </div>
  <div class="edit-item">
    <label>请假天数</label>
    <input type="number"/>
  </div>
  <button class="submit">提交申请</button>
<div> -> <div class="tpl-leave-flow">
  <div class="title line">请假单</div>
  <div class="item line">
    <label>请假事由：</label>
    <label> {data?.dayNum}}</label>
  </div>
  <div class="item line">
    <label>请假天数：</label>
    <label> 请假天数</label>
  </div>
  <button class="deal">现在处理</button>
<div>
<!---->
<div>{{ message.senderGroupMember.nickname }}发起请假申请</div>
<!---->
<div class="tpl-leave-flow">
  <div class="title line">请假单</div>
  <div class="item">
    <label>请假事由</label>
    <input type="text"/>
  </div>
  <div class="item line">
    <label>请假天数</label>
    <input type="number"/>
  </div>
  <button class="submit">提交申请</button>
<div>
', '系统模块', '修改', '2018-03-29 21:24:56', 'admin', '系统管理员', 'http://localhost:8080/bdf3.notice.ui.view.TemplateMaintain.d', '请假单');
INSERT INTO bdf3_log_info (id_, ip_, category_, desc_, module_, operation_, operation_date_, operation_user_, operation_user_nickname_, source_, title_) VALUES ('16f6b63b-b41e-4ea8-a630-8ec31217efd1', '0:0:0:0:0:0:0:1', '系统日志', 'CSS : .tpl-leave-flow .title {
  text-align: center;
  font-size: 1.3em;
  padding: 5px;
}

.tpl-leave-flow .item {
  padding: 10px;
}

.tpl-leave-flow .line {
  border-bottom: 1px solid #eee;
}

.tpl-leave-flow .item input {
  border-radius: 3px;
  border: 1px solid #ccc;
  padding: 5px;
  width: 200px;
}

.tpl-leave-flow button {
  border: 1px solid #eee;
  padding: 5px 10px;
  margin: 10px;
  float: right;
  border-radius: 3px;
}

.tpl-leave-flow .submit {
  background-color: #07c181;
  color: #fff;
  border: 1px solid #07c18;
} -> .tpl-leave-flow .title {
  text-align: center;
  font-size: 1.3em;
  padding: 5px;
}

.tpl-leave-flow .item {
  padding: 10px;
}

.tpl-leave-flow .line {
  border-bottom: 1px solid #eee;
}

.tpl-leave-flow .item input {
  border-radius: 3px;
  border: 1px solid #ccc;
  padding: 5px;
  width: 200px;
}

.tpl-leave-flow button {
  border: 1px solid #eee;
  padding: 5px 10px;
  margin: 10px;
  float: right;
  border-radius: 3px;
}
/****/
/****/
.tpl-leave-flow .title {
  text-align: center;
  font-size: 1.3em;
  padding: 5px;
}

.tpl-leave-flow .item {
  padding: 10px;
}

.tpl-leave-flow .line {
  border-bottom: 1px solid #eee;
}

.tpl-leave-flow .item input {
  border-radius: 3px;
  border: 1px solid #ccc;
  padding: 5px;
  width: 200px;
}

.tpl-leave-flow button {
  border: 1px solid #eee;
  padding: 5px 10px;
  margin: 10px;
  float: right;
  border-radius: 3px;
}

.tpl-leave-flow .submit {
  background-color: #07c181;
  color: #fff;
  border: 1px solid #07c18;
}
HTML : <div class="tpl-leave-flow">
  <div class="title line">请假单</div>
  <div class="item line">
    <label>请假事由：</label>
    <label> {data?.dayNum}}</label>
  </div>
  <div class="item line">
    <label>请假天数：</label>
    <label> 请假天数</label>
  </div>
  <button class="deal">现在处理</button>
<div>
<!---->
<div>{{ message.senderGroupMember.nickname }}发起请假申请</div>
<!---->
<div class="tpl-leave-flow">
  <div class="title line">请假单</div>
  <div class="item">
    <label>请假事由</label>
    <input type="text"/>
  </div>
  <div class="item line">
    <label>请假天数</label>
    <input type="number"/>
  </div>
  <button class="submit">提交申请</button>
<div> -> <div class="tpl-leave-flow">
  <div class="title line">请假单</div>
  <div class="item line">
    <label>请假事由：</label>
    <label>{{data.reason}}</label>
  </div>
  <div class="item line">
    <label>请假天数：</label>
    <label>{{data.dayNum}}天</label>
  </div>
  <button class="deal">现在处理</button>
<div>
<!---->
<div>{{ message.senderGroupMember.nickname }}发起请假申请</div>
<!---->
<div class="tpl-leave-flow">
  <div class="title line">请假单</div>
  <div class="item">
    <label>请假事由</label>
    <input class="reson" type="text"/>
  </div>
  <div class="item line">
    <label>请假天数</label>
    <input class="day-num" type="number"/>
  </div>
  <button class="submit">提交申请</button>
<div>
JAVASCRIPT : null -> $(".tpl-leave-flow .deal").click(function() {
  dorado.MessageBox.confirm("确定审核通过？", function() {
    
  })
});
/****/
/****/
$(".tpl-leave-flow .submit").click(function() {
  dorado.MessageBox.confirm("确定提交请假申请？", function() {
    var data = {
      dayNum: $(".tpl-leave-flow .day-num").val(),
      reason: $(".tpl-leave-flow .reason").val()
    };
    message.content = JSON.stringify(data);
    Chat.send(message);
  })
});
', '系统模块', '修改', '2018-03-29 21:39:46', 'admin', '系统管理员', 'http://localhost:8080/bdf3.notice.ui.view.TemplateMaintain.d', '请假单');
INSERT INTO bdf3_log_info (id_, ip_, category_, desc_, module_, operation_, operation_date_, operation_user_, operation_user_nickname_, source_, title_) VALUES ('d7b63332-abf3-4a5c-b3c9-a6ab8820fe53', '0:0:0:0:0:0:0:1', '系统日志', '可显示 : false -> true
', '系统模块', '修改', '2018-03-29 21:40:03', 'admin', '系统管理员', 'http://localhost:8080/bdf3.notice.ui.view.TemplateMaintain.d', '请假单');
INSERT INTO bdf3_log_info (id_, ip_, category_, desc_, module_, operation_, operation_date_, operation_user_, operation_user_nickname_, source_, title_) VALUES ('68f86222-0ce9-4b3b-8d96-171f551df4f7', '0:0:0:0:0:0:0:1', '系统日志', '', '系统模块', '删除', '2018-03-29 21:40:55', 'lisi', '李四', 'http://localhost:8080/bdf3.notice.ui.view.Chat.d', '个人办公');
INSERT INTO bdf3_log_info (id_, ip_, category_, desc_, module_, operation_, operation_date_, operation_user_, operation_user_nickname_, source_, title_) VALUES ('84b39974-4118-47ae-bdaf-930efb334fbe', '0:0:0:0:0:0:0:1', '系统日志', 'id : null -> 4f758d04-3366-4f4f-a7be-e65ef991a1b2
群名称 : null -> 请输入群组名称
群图标 : null -> fa fa-diamond purple-text
创建者 : null -> admin
创建于 : null -> Thu Mar 29 21:42:42 CST 2018
公告群 : null -> false
临时群 : null -> false
系统群 : null -> true
privateLetter : null -> false
成员数 : null -> 1
功能项 : null -> 0
', '系统模块', '新增', '2018-03-29 21:42:43', 'admin', '系统管理员', 'http://localhost:8080/bdf3.notice.ui.view.Chat.d', '请输入群组名称');
INSERT INTO bdf3_log_info (id_, ip_, category_, desc_, module_, operation_, operation_date_, operation_user_, operation_user_nickname_, source_, title_) VALUES ('bbf11185-c61d-4c41-8fe1-e25d862e387c', '0:0:0:0:0:0:0:1', '系统日志', 'administrator : false -> true
', '系统模块', '新增', '2018-03-29 21:42:43', 'admin', '系统管理员', 'http://localhost:8080/bdf3.notice.ui.view.Chat.d', '412e3b58-06ef-483c-ae04-7911c2cf7e70');
INSERT INTO bdf3_log_info (id_, ip_, category_, desc_, module_, operation_, operation_date_, operation_user_, operation_user_nickname_, source_, title_) VALUES ('bd28030d-a0e5-4ae7-9473-92b44be00709', '0:0:0:0:0:0:0:1', '系统日志', '', '系统模块', '新增', '2018-03-29 21:42:44', 'admin', '系统管理员', 'http://localhost:8080/bdf3.notice.ui.view.Chat.d', '4feee145-ed56-4636-be80-548905e19ead');
INSERT INTO bdf3_log_info (id_, ip_, category_, desc_, module_, operation_, operation_date_, operation_user_, operation_user_nickname_, source_, title_) VALUES ('98b80acd-7d2d-428c-b3cf-e3a156f57012', '0:0:0:0:0:0:0:1', '系统日志', '', '系统模块', '新增', '2018-03-29 21:42:45', 'admin', '系统管理员', 'http://localhost:8080/bdf3.notice.ui.view.Chat.d', '666b377c-671f-4193-ac23-399113d7a65a');
INSERT INTO bdf3_log_info (id_, ip_, category_, desc_, module_, operation_, operation_date_, operation_user_, operation_user_nickname_, source_, title_) VALUES ('51d11442-ea88-4b0f-9b1b-82aaee6c5423', '0:0:0:0:0:0:0:1', '系统日志', '', '系统模块', '新增', '2018-03-29 21:42:46', 'admin', '系统管理员', 'http://localhost:8080/bdf3.notice.ui.view.Chat.d', '5cae7e11-a8b8-4c61-bdac-30f84b7ea87b');
INSERT INTO bdf3_log_info (id_, ip_, category_, desc_, module_, operation_, operation_date_, operation_user_, operation_user_nickname_, source_, title_) VALUES ('c9c0ef69-f93b-4b45-b491-44ff2b3c7ffd', '0:0:0:0:0:0:0:1', '系统日志', '', '系统模块', '新增', '2018-03-29 21:42:46', 'admin', '系统管理员', 'http://localhost:8080/bdf3.notice.ui.view.Chat.d', '83b374aa-d317-4eb7-bc1d-2df4468eda9e');
INSERT INTO bdf3_log_info (id_, ip_, category_, desc_, module_, operation_, operation_date_, operation_user_, operation_user_nickname_, source_, title_) VALUES ('c767945e-d968-4cf4-854e-6d747fd645c8', '0:0:0:0:0:0:0:1', '系统日志', '群名称 : 请输入群组名称 -> 个人办公
', '系统模块', '修改', '2018-03-29 21:43:10', 'admin', '系统管理员', 'http://localhost:8080/bdf3.notice.ui.view.Chat.d', '个人办公');
INSERT INTO bdf3_log_info (id_, ip_, category_, desc_, module_, operation_, operation_date_, operation_user_, operation_user_nickname_, source_, title_) VALUES ('d10bdbfd-d8bc-4f55-951b-07d98835e0ac', '0:0:0:0:0:0:0:1', '系统日志', '功能项 : 0 -> 1
', '系统模块', '修改', '2018-03-29 21:43:23', 'admin', '系统管理员', 'http://localhost:8080/bdf3.notice.ui.view.Chat.d', '个人办公');
INSERT INTO bdf3_log_info (id_, ip_, category_, desc_, module_, operation_, operation_date_, operation_user_, operation_user_nickname_, source_, title_) VALUES ('756bbb80-4e8e-4b53-9f2e-bfac5a9eebb4', '0:0:0:0:0:0:0:1', '系统日志', 'id : null -> a0b7efe9-8b34-407b-a6ad-568846ca6ee3
名称 : null -> 请假单
图标 : null -> fa fa-hotel
CSS : null -> .tpl-leave-flow .title {
  text-align: center;
  font-size: 1.3em;
  padding: 5px;
}

.tpl-leave-flow .item {
  padding: 10px;
}

.tpl-leave-flow .line {
  border-bottom: 1px solid #eee;
}

.tpl-leave-flow .item input {
  border-radius: 3px;
  border: 1px solid #ccc;
  padding: 5px;
  width: 200px;
}

.tpl-leave-flow button {
  border: 1px solid #eee;
  padding: 5px 10px;
  margin: 10px;
  float: right;
  border-radius: 3px;
}
/****/
/****/
.tpl-leave-flow .title {
  text-align: center;
  font-size: 1.3em;
  padding: 5px;
}

.tpl-leave-flow .item {
  padding: 10px;
}

.tpl-leave-flow .line {
  border-bottom: 1px solid #eee;
}

.tpl-leave-flow .item input {
  border-radius: 3px;
  border: 1px solid #ccc;
  padding: 5px;
  width: 200px;
}

.tpl-leave-flow button {
  border: 1px solid #eee;
  padding: 5px 10px;
  margin: 10px;
  float: right;
  border-radius: 3px;
}

.tpl-leave-flow .submit {
  background-color: #07c181;
  color: #fff;
  border: 1px solid #07c18;
}
JAVASCRIPT : null -> $(".tpl-leave-flow .deal").click(function() {
  dorado.MessageBox.confirm("确定审核通过？", function() {
    
  })
});
/****/
/****/
$(".tpl-leave-flow .submit").click(function() {
  dorado.MessageBox.confirm("确定提交请假申请？", function() {
    var data = {
      dayNum: $(".tpl-leave-flow .day-num").val(),
      reason: $(".tpl-leave-flow .reason").val()
    };
    message.content = JSON.stringify(data);
    Chat.send(message);
  })
});
HTML : null -> <div class="tpl-leave-flow">
  <div class="title line">请假单</div>
  <div class="item line">
    <label>请假事由：</label>
    <label>{{data.reason}}</label>
  </div>
  <div class="item line">
    <label>请假天数：</label>
    <label>{{data.dayNum}}天</label>
  </div>
  <button class="deal">现在处理</button>
<div>
<!---->
<div>{{ message.senderGroupMember.nickname }}发起请假申请</div>
<!---->
<div class="tpl-leave-flow">
  <div class="title line">请假单</div>
  <div class="item">
    <label>请假事由</label>
    <input class="reson" type="text"/>
  </div>
  <div class="item line">
    <label>请假天数</label>
    <input class="day-num" type="number"/>
  </div>
  <button class="submit">提交申请</button>
<div>
全局 : null -> false
下线 : null -> false
微程序 : null -> false
可显示 : null -> true
', '系统模块', '新增', '2018-03-29 21:43:23', 'admin', '系统管理员', 'http://localhost:8080/bdf3.notice.ui.view.Chat.d', '请假单');
INSERT INTO bdf3_log_info (id_, ip_, category_, desc_, module_, operation_, operation_date_, operation_user_, operation_user_nickname_, source_, title_) VALUES ('a2ac41a4-404e-42e2-be5a-77ff39203c63', '0:0:0:0:0:0:0:1', '系统日志', 'JAVASCRIPT : $(".tpl-leave-flow .deal").click(function() {
  dorado.MessageBox.confirm("确定审核通过？", function() {
    
  })
});
/****/
/****/
$(".tpl-leave-flow .submit").click(function() {
  dorado.MessageBox.confirm("确定提交请假申请？", function() {
    var data = {
      dayNum: $(".tpl-leave-flow .day-num").val(),
      reason: $(".tpl-leave-flow .reason").val()
    };
    message.content = JSON.stringify(data);
    Chat.send(message);
  })
}); -> $(".tpl-leave-flow .deal").click(function() {
  dorado.MessageBox.confirm("确定审核通过？", function() {
    
  })
});
/****/
/****/
$(".tpl-leave-flow .submit").click(function() {
  var data = {
    dayNum: $(".tpl-leave-flow .day-num").val(),
    reason: $(".tpl-leave-flow .reason").val()
  };
  dorado.MessageBox.confirm("确定提交请假申请？", function() {
    message.content = JSON.stringify(data);
    Chat.send(message);
  })
});
', '系统模块', '修改', '2018-03-29 21:49:02', 'admin', '系统管理员', 'http://localhost:8080/bdf3.notice.ui.view.TemplateMaintain.d', '请假单');
INSERT INTO bdf3_log_info (id_, ip_, category_, desc_, module_, operation_, operation_date_, operation_user_, operation_user_nickname_, source_, title_) VALUES ('77fb6c30-6092-48f4-96a8-dd7802d63126', '0:0:0:0:0:0:0:1', '系统日志', 'HTML : <div class="tpl-leave-flow">
  <div class="title line">请假单</div>
  <div class="item line">
    <label>请假事由：</label>
    <label>{{data.reason}}</label>
  </div>
  <div class="item line">
    <label>请假天数：</label>
    <label>{{data.dayNum}}天</label>
  </div>
  <button class="deal">现在处理</button>
<div>
<!---->
<div>{{ message.senderGroupMember.nickname }}发起请假申请</div>
<!---->
<div class="tpl-leave-flow">
  <div class="title line">请假单</div>
  <div class="item">
    <label>请假事由</label>
    <input class="reson" type="text"/>
  </div>
  <div class="item line">
    <label>请假天数</label>
    <input class="day-num" type="number"/>
  </div>
  <button class="submit">提交申请</button>
<div> -> <div class="tpl-leave-flow">
  <div class="title line">请假单</div>
  <div class="item line">
    <label>请假事由：</label>
    <label>{{data.reason}}</label>
  </div>
  <div class="item line">
    <label>请假天数：</label>
    <label>{{data.dayNum}}天</label>
  </div>
  <button class="deal">现在处理</button>
<div>
<!---->
<div>{{ message.senderGroupMember.nickname }}发起请假申请</div>
<!---->
<div class="tpl-leave-flow">
  <div class="title line">请假单</div>
  <div class="item">
    <label>请假事由</label>
    <input class="reason" type="text"/>
  </div>
  <div class="item line">
    <label>请假天数</label>
    <input class="day-num" type="number"/>
  </div>
  <button class="submit">提交申请</button>
<div>
', '系统模块', '修改', '2018-03-29 21:55:47', 'admin', '系统管理员', 'http://localhost:8080/bdf3.notice.ui.view.TemplateMaintain.d', '请假单');
INSERT INTO bdf3_log_info (id_, ip_, category_, desc_, module_, operation_, operation_date_, operation_user_, operation_user_nickname_, source_, title_) VALUES ('7417b6c3-06ff-4106-8c13-c530aeb6bc18', '0:0:0:0:0:0:0:1', '系统日志', 'CSS : .tpl-leave-flow .title {
  text-align: center;
  font-size: 1.3em;
  padding: 5px;
}

.tpl-leave-flow .item {
  padding: 10px;
}

.tpl-leave-flow .line {
  border-bottom: 1px solid #eee;
}

.tpl-leave-flow .item input {
  border-radius: 3px;
  border: 1px solid #ccc;
  padding: 5px;
  width: 200px;
}

.tpl-leave-flow button {
  border: 1px solid #eee;
  padding: 5px 10px;
  margin: 10px;
  float: right;
  border-radius: 3px;
}
/****/
/****/
.tpl-leave-flow .title {
  text-align: center;
  font-size: 1.3em;
  padding: 5px;
}

.tpl-leave-flow .item {
  padding: 10px;
}

.tpl-leave-flow .line {
  border-bottom: 1px solid #eee;
}

.tpl-leave-flow .item input {
  border-radius: 3px;
  border: 1px solid #ccc;
  padding: 5px;
  width: 200px;
}

.tpl-leave-flow button {
  border: 1px solid #eee;
  padding: 5px 10px;
  margin: 10px;
  float: right;
  border-radius: 3px;
}

.tpl-leave-flow .submit {
  background-color: #07c181;
  color: #fff;
  border: 1px solid #07c18;
} -> .tpl-leave-flow .title {
  text-align: center;
  font-size: 1.3em;
  padding: 5px;
}

!.d-tip > .tpl-leave-flow {
  background-color:#fff;	
}

.tpl-leave-flow .item {
  padding: 10px;
}

.tpl-leave-flow .line {
  border-bottom: 1px solid #eee;
}

.tpl-leave-flow .item input {
  border-radius: 3px;
  border: 1px solid #ccc;
  padding: 5px;
  width: 200px;
}

.tpl-leave-flow button {
  border: 1px solid #eee;
  padding: 5px 10px;
  margin: 10px;
  float: right;
  border-radius: 3px;
}
/****/
/****/
.tpl-leave-flow .title {
  text-align: center;
  font-size: 1.3em;
  padding: 5px;
}

.tpl-leave-flow .item {
  padding: 10px;
}

.tpl-leave-flow .line {
  border-bottom: 1px solid #eee;
}

.tpl-leave-flow .item input {
  border-radius: 3px;
  border: 1px solid #ccc;
  padding: 5px;
  width: 200px;
}

.tpl-leave-flow button {
  border: 1px solid #eee;
  padding: 5px 10px;
  margin: 10px;
  float: right;
  border-radius: 3px;
}

.tpl-leave-flow .submit {
  background-color: #07c181;
  color: #fff;
  border: 1px solid #07c18;
}
HTML : <div class="tpl-leave-flow">
  <div class="title line">请假单</div>
  <div class="item line">
    <label>请假事由：</label>
    <label>{{data.reason}}</label>
  </div>
  <div class="item line">
    <label>请假天数：</label>
    <label>{{data.dayNum}}天</label>
  </div>
  <button class="deal">现在处理</button>
<div>
<!---->
<div>{{ message.senderGroupMember.nickname }}发起请假申请</div>
<!---->
<div class="tpl-leave-flow">
  <div class="title line">请假单</div>
  <div class="item">
    <label>请假事由</label>
    <input class="reason" type="text"/>
  </div>
  <div class="item line">
    <label>请假天数</label>
    <input class="day-num" type="number"/>
  </div>
  <button class="submit">提交申请</button>
<div> -> <div class="tpl-leave-flow">
  <div class="title line">请假单</div>
  <div class="item line">
    <label>请假事由：</label>
    <label>{{data.reason}}</label>
  </div>
  <div class="item line">
    <label>请假天数：</label>
    <label>{{data.dayNum}}天</label>
  </div>
  <button class="deal">现在处理</button>
<div>
<!---->
<div>{{ nickname }}发起请假申请</div>
<!---->
<div class="tpl-leave-flow">
  <div class="title line">请假单</div>
  <div class="item">
    <label>请假事由</label>
    <input class="reason" type="text"/>
  </div>
  <div class="item line">
    <label>请假天数</label>
    <input class="day-num" type="number"/>
  </div>
  <button class="submit">提交申请</button>
<div>
JAVASCRIPT : $(".tpl-leave-flow .deal").click(function() {
  dorado.MessageBox.confirm("确定审核通过？", function() {
    
  })
});
/****/
/****/
$(".tpl-leave-flow .submit").click(function() {
  var data = {
    dayNum: $(".tpl-leave-flow .day-num").val(),
    reason: $(".tpl-leave-flow .reason").val()
  };
  dorado.MessageBox.confirm("确定提交请假申请？", function() {
    message.content = JSON.stringify(data);
    Chat.send(message);
  })
}); -> $(".tpl-leave-flow .deal").click(function() {
  dorado.MessageBox.confirm("确定审核通过？", function() {
    
  })
});
/****/
/****/
$(".tpl-leave-flow .submit").click(function() {
  var data = {
    dayNum: $(".tpl-leave-flow .day-num").val(),
    reason: $(".tpl-leave-flow .reason").val(),
    nickname: message.senderGroupMember.nickname
  };
  dorado.MessageBox.confirm("确定提交请假申请？", function() {
    message.content = JSON.stringify(data);
    Chat.send(message);
  })
});
', '系统模块', '修改', '2018-03-29 21:59:33', 'admin', '系统管理员', 'http://localhost:8080/bdf3.notice.ui.view.TemplateMaintain.d', '请假单');
INSERT INTO bdf3_log_info (id_, ip_, category_, desc_, module_, operation_, operation_date_, operation_user_, operation_user_nickname_, source_, title_) VALUES ('25c9fec9-c944-420e-9108-828a816d4010', '0:0:0:0:0:0:0:1', '系统日志', 'JAVASCRIPT : $(".tpl-leave-flow .deal").click(function() {
  dorado.MessageBox.confirm("确定审核通过？", function() {
    
  })
});
/****/
/****/
$(".tpl-leave-flow .submit").click(function() {
  var data = {
    dayNum: $(".tpl-leave-flow .day-num").val(),
    reason: $(".tpl-leave-flow .reason").val(),
    nickname: message.senderGroupMember.nickname
  };
  dorado.MessageBox.confirm("确定提交请假申请？", function() {
    message.content = JSON.stringify(data);
    Chat.send(message);
  })
}); -> $(".tpl-leave-flow .deal").click(function() {
  dorado.MessageBox.confirm("确定审核通过？", function() {
    
  })
});
/****/
/****/
$(".tpl-leave-flow .submit").click(function() {
  var data = {
    dayNum: $(".tpl-leave-flow .day-num").val(),
    reason: $(".tpl-leave-flow .reason").val(),
    nickname: message.senderGroupMember.nickname
  };
  console.log(data);
  dorado.MessageBox.confirm("确定提交请假申请？", function() {
    message.content = JSON.stringify(data);
    //Chat.send(message);
  })
});
', '系统模块', '修改', '2018-03-29 22:02:39', 'admin', '系统管理员', 'http://localhost:8080/bdf3.notice.ui.view.TemplateMaintain.d', '请假单');
INSERT INTO bdf3_log_info (id_, ip_, category_, desc_, module_, operation_, operation_date_, operation_user_, operation_user_nickname_, source_, title_) VALUES ('08107b1b-989c-44cc-92bf-338a2825eaab', '0:0:0:0:0:0:0:1', '系统日志', 'HTML : <div class="tpl-leave-flow">
  <div class="title line">请假单</div>
  <div class="item line">
    <label>请假事由：</label>
    <label>{{data.reason}}</label>
  </div>
  <div class="item line">
    <label>请假天数：</label>
    <label>{{data.dayNum}}天</label>
  </div>
  <button class="deal">现在处理</button>
<div>
<!---->
<div>{{ nickname }}发起请假申请</div>
<!---->
<div class="tpl-leave-flow">
  <div class="title line">请假单</div>
  <div class="item">
    <label>请假事由</label>
    <input class="reason" type="text"/>
  </div>
  <div class="item line">
    <label>请假天数</label>
    <input class="day-num" type="number"/>
  </div>
  <button class="submit">提交申请</button>
<div> -> <div class="tpl-leave-flow">
  <div class="title line">请假单</div>
  <div class="item line">
    <label>请假事由：</label>
    <label>{{data.reason}}</label>
  </div>
  <div class="item line">
    <label>请假天数：</label>
    <label>{{data.dayNum}}天</label>
  </div>
  <button class="deal">现在处理</button>
<div>
<!---->
<div>{{ data.nickname }}发起请假申请</div>
<!---->
<div class="tpl-leave-flow">
  <div class="title line">请假单</div>
  <div class="item">
    <label>请假事由</label>
    <input class="reason" type="text"/>
  </div>
  <div class="item line">
    <label>请假天数</label>
    <input class="day-num" type="number"/>
  </div>
  <button class="submit">提交申请</button>
<div>
JAVASCRIPT : $(".tpl-leave-flow .deal").click(function() {
  dorado.MessageBox.confirm("确定审核通过？", function() {
    
  })
});
/****/
/****/
$(".tpl-leave-flow .submit").click(function() {
  var data = {
    dayNum: $(".tpl-leave-flow .day-num").val(),
    reason: $(".tpl-leave-flow .reason").val(),
    nickname: message.senderGroupMember.nickname
  };
  console.log(data);
  dorado.MessageBox.confirm("确定提交请假申请？", function() {
    message.content = JSON.stringify(data);
    //Chat.send(message);
  })
}); -> $(".tpl-leave-flow .deal").click(function() {
  dorado.MessageBox.confirm("确定审核通过？", function() {
    
  })
});
/****/
/****/
$(".tpl-leave-flow .submit").click(function() {
  var data = {
    dayNum: $(".tpl-leave-flow .day-num").val(),
    reason: $(".tpl-leave-flow .reason").val(),
    nickname: message.senderGroupMember.nickname
  };
  dorado.MessageBox.confirm("确定提交请假申请？", function() {
    message.content = JSON.stringify(data);
    Chat.send(message);
  })
});
', '系统模块', '修改', '2018-03-29 22:05:54', 'admin', '系统管理员', 'http://localhost:8080/bdf3.notice.ui.view.TemplateMaintain.d', '请假单');
INSERT INTO bdf3_log_info (id_, ip_, category_, desc_, module_, operation_, operation_date_, operation_user_, operation_user_nickname_, source_, title_) VALUES ('bf6cc86a-3fdc-45dc-a50f-858cce8a4113', '0:0:0:0:0:0:0:1', '系统日志', 'JAVASCRIPT : $(".tpl-leave-flow .deal").click(function() {
  dorado.MessageBox.confirm("确定审核通过？", function() {
    
  })
});
/****/
/****/
$(".tpl-leave-flow .submit").click(function() {
  var data = {
    dayNum: $(".tpl-leave-flow .day-num").val(),
    reason: $(".tpl-leave-flow .reason").val(),
    nickname: message.senderGroupMember.nickname
  };
  dorado.MessageBox.confirm("确定提交请假申请？", function() {
    message.content = JSON.stringify(data);
    Chat.send(message);
  })
}); -> $(".tpl-leave-flow .deal").click(function() {
  dorado.MessageBox.confirm("确定审核通过？", function() {
    
  })
});
/****/
/****/
$(".tpl-leave-flow .submit").click(function() {
  var data = {
    dayNum: $(".tpl-leave-flow .day-num").val(),
    reason: $(".tpl-leave-flow .reason").val(),
    nickname: message.senderGroupMember.nickname
  };
  message.content = JSON.stringify(data);
  Chat.send(message);
});
', '系统模块', '修改', '2018-03-29 22:08:51', 'admin', '系统管理员', 'http://localhost:8080/bdf3.notice.ui.view.TemplateMaintain.d', '请假单');
INSERT INTO bdf3_log_info (id_, ip_, category_, desc_, module_, operation_, operation_date_, operation_user_, operation_user_nickname_, source_, title_) VALUES ('36e0b518-ca4c-40d3-ac11-9dcdcba2c559', '0:0:0:0:0:0:0:1', '系统日志', 'id : null -> 22cbb691-354c-4bb4-9ddc-4969d8d111ca
名称 : null -> 功能管理
图标 : null -> fa fa-superscript
视图 : null -> bdf3.notice.ui.view.TemplateMaintain.d
全局 : null -> false
下线 : null -> false
微程序 : null -> false
可显示 : null -> true
', '系统模块', '新增', '2018-03-29 23:26:38', 'admin', '系统管理员', 'http://localhost:8080/bdf3.notice.ui.view.TemplateMaintain.d', '功能管理');
INSERT INTO bdf3_log_info (id_, ip_, category_, desc_, module_, operation_, operation_date_, operation_user_, operation_user_nickname_, source_, title_) VALUES ('dff88d92-2771-4475-a19b-1da55c68116d', '0:0:0:0:0:0:0:1', '系统日志', 'id : null -> d82052cc-7027-4cf3-bed4-9de1e775cf70
名称 : null -> 用户管理
图标 : null -> fa fa-user blue-text
视图 : null -> bdf3.security.ui.view.UserMaintain.d
全局 : null -> false
下线 : null -> false
微程序 : null -> false
可显示 : null -> true
', '系统模块', '新增', '2018-03-29 23:28:36', 'admin', '系统管理员', 'http://localhost:8080/bdf3.notice.ui.view.TemplateMaintain.d', '用户管理');
INSERT INTO bdf3_log_info (id_, ip_, category_, desc_, module_, operation_, operation_date_, operation_user_, operation_user_nickname_, source_, title_) VALUES ('e1f2c25b-7c96-4815-8067-19a52a00d679', '0:0:0:0:0:0:0:1', '系统日志', 'id : null -> 6900e36f-62dd-4b45-8f98-d71875771e13
名称 : null -> 菜单管理
图标 : null -> fa fa-bars yellow-text
视图 : null -> bdf3.security.ui.view.UrlMaintain.d
全局 : null -> false
下线 : null -> false
微程序 : null -> false
可显示 : null -> true
', '系统模块', '新增', '2018-03-29 23:28:36', 'admin', '系统管理员', 'http://localhost:8080/bdf3.notice.ui.view.TemplateMaintain.d', '菜单管理');
INSERT INTO bdf3_log_info (id_, ip_, category_, desc_, module_, operation_, operation_date_, operation_user_, operation_user_nickname_, source_, title_) VALUES ('05e77e22-c1ce-4635-bb79-f6d7a42b0450', '0:0:0:0:0:0:0:1', '系统日志', 'id : null -> 8e8aa5fc-412f-42c8-840e-282c90f20a19
名称 : null -> 角色管理
图标 : null -> fa fa-user-md blue-text
视图 : null -> bdf3.security.ui.view.RoleMaintain.d
全局 : null -> false
下线 : null -> false
微程序 : null -> false
可显示 : null -> true
', '系统模块', '新增', '2018-03-29 23:28:36', 'admin', '系统管理员', 'http://localhost:8080/bdf3.notice.ui.view.TemplateMaintain.d', '角色管理');
INSERT INTO bdf3_log_info (id_, ip_, category_, desc_, module_, operation_, operation_date_, operation_user_, operation_user_nickname_, source_, title_) VALUES ('aab8c038-ef24-437a-ae82-2a3372ae5355', '0:0:0:0:0:0:0:1', '系统日志', 'id : null -> 36f834d5-8314-4f48-92bd-23c298e5738e
名称 : null -> 权限分配
图标 : null -> fa fa-shield yellow-text
视图 : null -> bdf3.security.ui.view.PermissionMaintain.d
全局 : null -> false
下线 : null -> false
微程序 : null -> false
可显示 : null -> true
', '系统模块', '新增', '2018-03-29 23:28:36', 'admin', '系统管理员', 'http://localhost:8080/bdf3.notice.ui.view.TemplateMaintain.d', '权限分配');
INSERT INTO bdf3_log_info (id_, ip_, category_, desc_, module_, operation_, operation_date_, operation_user_, operation_user_nickname_, source_, title_) VALUES ('a5a737ea-5dfb-477c-a34a-df0466689797', '0:0:0:0:0:0:0:1', '系统日志', 'id : null -> c29f21a1-83a9-41a0-9aa6-8de4878d5954
名称 : null -> 菜单权限
图标 : null -> fa fa-code-fork green-text
视图 : null -> bdf3.security.ui.view.RoleUrlMaintain.d
全局 : null -> false
下线 : null -> false
微程序 : null -> false
可显示 : null -> true
', '系统模块', '新增', '2018-03-29 23:28:36', 'admin', '系统管理员', 'http://localhost:8080/bdf3.notice.ui.view.TemplateMaintain.d', '菜单权限');
INSERT INTO bdf3_log_info (id_, ip_, category_, desc_, module_, operation_, operation_date_, operation_user_, operation_user_nickname_, source_, title_) VALUES ('d90a2ade-245e-4e8a-aba1-97df3aa3341c', '0:0:0:0:0:0:0:1', '系统日志', 'id : null -> ca0dcb00-86c1-4dee-9312-c6ccacbf4d57
名称 : null -> 导入管理
图标 : null -> fa fa-file-excel-o orange-text
视图 : null -> bdf3.importer.view.ImporterSolutionMaintain.d
全局 : null -> false
下线 : null -> false
微程序 : null -> false
可显示 : null -> true
', '系统模块', '新增', '2018-03-29 23:28:36', 'admin', '系统管理员', 'http://localhost:8080/bdf3.notice.ui.view.TemplateMaintain.d', '导入管理');
INSERT INTO bdf3_log_info (id_, ip_, category_, desc_, module_, operation_, operation_date_, operation_user_, operation_user_nickname_, source_, title_) VALUES ('45df273b-7f41-4279-aec2-3be3096f160d', '0:0:0:0:0:0:0:1', '系统日志', 'id : null -> a6714752-3a17-41cc-abce-a426a41ee14c
名称 : null -> 字典管理
图标 : null -> fa fa-book yellow-text
视图 : null -> bdf3.dictionary.ui.view.DictionaryMaintain.d
全局 : null -> false
下线 : null -> false
微程序 : null -> false
可显示 : null -> true
', '系统模块', '新增', '2018-03-29 23:28:36', 'admin', '系统管理员', 'http://localhost:8080/bdf3.notice.ui.view.TemplateMaintain.d', '字典管理');
INSERT INTO bdf3_log_info (id_, ip_, category_, desc_, module_, operation_, operation_date_, operation_user_, operation_user_nickname_, source_, title_) VALUES ('98b1989c-0800-4530-834e-ffb342a85c1c', '0:0:0:0:0:0:0:1', '系统日志', 'id : null -> ffa83b56-9610-42b1-97fe-41ceee9207fe
名称 : null -> 云数据库
图标 : null -> fa fa-database blue-text
视图 : null -> bdf3.dbconsole.view.DbConsoleMaintain.d
全局 : null -> false
下线 : null -> false
微程序 : null -> false
可显示 : null -> true
', '系统模块', '新增', '2018-03-29 23:28:36', 'admin', '系统管理员', 'http://localhost:8080/bdf3.notice.ui.view.TemplateMaintain.d', '云数据库');
INSERT INTO bdf3_log_info (id_, ip_, category_, desc_, module_, operation_, operation_date_, operation_user_, operation_user_nickname_, source_, title_) VALUES ('7038ad57-2f91-4d69-a42b-0ee17d11a30f', '0:0:0:0:0:0:0:1', '系统日志', 'id : null -> 2081965d-fdcb-49ca-9763-6b497338f04d
名称 : null -> 日志查询
图标 : null -> fa fa-clock-o blue-text
视图 : null -> bdf3.log.view.LogInfoMaintain.d
全局 : null -> false
下线 : null -> false
微程序 : null -> false
可显示 : null -> true
', '系统模块', '新增', '2018-03-29 23:28:36', 'admin', '系统管理员', 'http://localhost:8080/bdf3.notice.ui.view.TemplateMaintain.d', '日志查询');
INSERT INTO bdf3_log_info (id_, ip_, category_, desc_, module_, operation_, operation_date_, operation_user_, operation_user_nickname_, source_, title_) VALUES ('02e6451c-b586-4384-92ec-12fb74b96739', '0:0:0:0:0:0:0:1', '系统日志', 'id : null -> ef2eeb71-eec0-4264-aa41-820f06b9918c
名称 : null -> 个人中心
图标 : null -> fa fa-tachometer red-text
视图 : null -> bdf3.security.ui.view.PersonalCenter.d
全局 : null -> false
下线 : null -> false
微程序 : null -> false
可显示 : null -> true
', '系统模块', '新增', '2018-03-29 23:28:36', 'admin', '系统管理员', 'http://localhost:8080/bdf3.notice.ui.view.TemplateMaintain.d', '个人中心');
INSERT INTO bdf3_log_info (id_, ip_, category_, desc_, module_, operation_, operation_date_, operation_user_, operation_user_nickname_, source_, title_) VALUES ('0f288211-15a4-4e0e-96c5-9d474f2b8fac', '0:0:0:0:0:0:0:1', '系统日志', '功能项 : 0 -> 1
', '系统模块', '修改', '2018-03-29 23:29:21', 'admin', '系统管理员', 'http://localhost:8080/bdf3.notice.ui.view.Chat.d', '系统管理群');
INSERT INTO bdf3_log_info (id_, ip_, category_, desc_, module_, operation_, operation_date_, operation_user_, operation_user_nickname_, source_, title_) VALUES ('c5a085a3-932a-4f1f-8169-d5215e58b337', '0:0:0:0:0:0:0:1', '系统日志', 'id : null -> d82052cc-7027-4cf3-bed4-9de1e775cf70
名称 : null -> 用户管理
图标 : null -> fa fa-user blue-text
视图 : null -> bdf3.security.ui.view.UserMaintain.d
全局 : null -> false
下线 : null -> false
微程序 : null -> false
可显示 : null -> true
', '系统模块', '新增', '2018-03-29 23:29:21', 'admin', '系统管理员', 'http://localhost:8080/bdf3.notice.ui.view.Chat.d', '用户管理');
INSERT INTO bdf3_log_info (id_, ip_, category_, desc_, module_, operation_, operation_date_, operation_user_, operation_user_nickname_, source_, title_) VALUES ('c3320eb2-8885-458b-89d3-e13f40899ed2', '0:0:0:0:0:0:0:1', '系统日志', 'id : null -> 6900e36f-62dd-4b45-8f98-d71875771e13
名称 : null -> 菜单管理
图标 : null -> fa fa-bars yellow-text
视图 : null -> bdf3.security.ui.view.UrlMaintain.d
全局 : null -> false
下线 : null -> false
微程序 : null -> false
可显示 : null -> true
', '系统模块', '新增', '2018-03-29 23:29:24', 'admin', '系统管理员', 'http://localhost:8080/bdf3.notice.ui.view.Chat.d', '菜单管理');
INSERT INTO bdf3_log_info (id_, ip_, category_, desc_, module_, operation_, operation_date_, operation_user_, operation_user_nickname_, source_, title_) VALUES ('e4ac4e20-40ae-46f6-9e98-c5d49cfd2585', '0:0:0:0:0:0:0:1', '系统日志', 'id : null -> 8e8aa5fc-412f-42c8-840e-282c90f20a19
名称 : null -> 角色管理
图标 : null -> fa fa-user-md blue-text
视图 : null -> bdf3.security.ui.view.RoleMaintain.d
全局 : null -> false
下线 : null -> false
微程序 : null -> false
可显示 : null -> true
', '系统模块', '新增', '2018-03-29 23:29:27', 'admin', '系统管理员', 'http://localhost:8080/bdf3.notice.ui.view.Chat.d', '角色管理');
INSERT INTO bdf3_log_info (id_, ip_, category_, desc_, module_, operation_, operation_date_, operation_user_, operation_user_nickname_, source_, title_) VALUES ('180470b3-48e2-4b68-8cd3-697a7c1147f5', '0:0:0:0:0:0:0:1', '系统日志', 'id : null -> 36f834d5-8314-4f48-92bd-23c298e5738e
名称 : null -> 权限分配
图标 : null -> fa fa-shield yellow-text
视图 : null -> bdf3.security.ui.view.PermissionMaintain.d
全局 : null -> false
下线 : null -> false
微程序 : null -> false
可显示 : null -> true
', '系统模块', '新增', '2018-03-29 23:29:30', 'admin', '系统管理员', 'http://localhost:8080/bdf3.notice.ui.view.Chat.d', '权限分配');
INSERT INTO bdf3_log_info (id_, ip_, category_, desc_, module_, operation_, operation_date_, operation_user_, operation_user_nickname_, source_, title_) VALUES ('e37837ac-3f56-4fc5-8c3d-9acbb7d07916', '0:0:0:0:0:0:0:1', '系统日志', 'id : null -> 22cbb691-354c-4bb4-9ddc-4969d8d111ca
名称 : null -> 功能管理
图标 : null -> fa fa-superscript
视图 : null -> bdf3.notice.ui.view.TemplateMaintain.d
全局 : null -> false
下线 : null -> false
微程序 : null -> false
可显示 : null -> true
', '系统模块', '新增', '2018-03-29 23:29:38', 'admin', '系统管理员', 'http://localhost:8080/bdf3.notice.ui.view.Chat.d', '功能管理');
INSERT INTO bdf3_log_info (id_, ip_, category_, desc_, module_, operation_, operation_date_, operation_user_, operation_user_nickname_, source_, title_) VALUES ('5f11be83-f5ef-4dfd-80bc-84fdcb68118f', '0:0:0:0:0:0:0:1', '系统日志', 'id : null -> ca0dcb00-86c1-4dee-9312-c6ccacbf4d57
名称 : null -> 导入管理
图标 : null -> fa fa-file-excel-o orange-text
视图 : null -> bdf3.importer.view.ImporterSolutionMaintain.d
全局 : null -> false
下线 : null -> false
微程序 : null -> false
可显示 : null -> true
', '系统模块', '新增', '2018-03-29 23:29:42', 'admin', '系统管理员', 'http://localhost:8080/bdf3.notice.ui.view.Chat.d', '导入管理');
INSERT INTO bdf3_log_info (id_, ip_, category_, desc_, module_, operation_, operation_date_, operation_user_, operation_user_nickname_, source_, title_) VALUES ('de84e27a-6ec5-4e20-8eb4-d61bdd92ba52', '0:0:0:0:0:0:0:1', '系统日志', 'id : null -> a6714752-3a17-41cc-abce-a426a41ee14c
名称 : null -> 字典管理
图标 : null -> fa fa-book yellow-text
视图 : null -> bdf3.dictionary.ui.view.DictionaryMaintain.d
全局 : null -> false
下线 : null -> false
微程序 : null -> false
可显示 : null -> true
', '系统模块', '新增', '2018-03-29 23:29:45', 'admin', '系统管理员', 'http://localhost:8080/bdf3.notice.ui.view.Chat.d', '字典管理');
INSERT INTO bdf3_log_info (id_, ip_, category_, desc_, module_, operation_, operation_date_, operation_user_, operation_user_nickname_, source_, title_) VALUES ('1fafba5e-4410-40c5-8eb6-ade8fa05c2ca', '0:0:0:0:0:0:0:1', '系统日志', '功能项 : 0 -> 2
', '系统模块', '修改', '2018-03-29 23:31:33', 'admin', '系统管理员', 'http://localhost:8080/bdf3.notice.ui.view.Chat.d', '个人办公');
INSERT INTO bdf3_log_info (id_, ip_, category_, desc_, module_, operation_, operation_date_, operation_user_, operation_user_nickname_, source_, title_) VALUES ('6de30116-0497-414b-b549-8bca9beac71f', '0:0:0:0:0:0:0:1', '系统日志', 'id : null -> ef2eeb71-eec0-4264-aa41-820f06b9918c
名称 : null -> 个人中心
图标 : null -> fa fa-tachometer red-text
视图 : null -> bdf3.security.ui.view.PersonalCenter.d
全局 : null -> false
下线 : null -> false
微程序 : null -> false
可显示 : null -> true
', '系统模块', '新增', '2018-03-29 23:31:33', 'admin', '系统管理员', 'http://localhost:8080/bdf3.notice.ui.view.Chat.d', '个人中心');
INSERT INTO bdf3_log_info (id_, ip_, category_, desc_, module_, operation_, operation_date_, operation_user_, operation_user_nickname_, source_, title_) VALUES ('e3da56df-b780-4128-8f4a-1c57487975ab', '0:0:0:0:0:0:0:1', '系统日志', '功能项 : 0 -> 1
', '系统模块', '修改', '2018-03-29 23:31:47', 'admin', '系统管理员', 'http://localhost:8080/bdf3.notice.ui.view.Chat.d', '运维群');
INSERT INTO bdf3_log_info (id_, ip_, category_, desc_, module_, operation_, operation_date_, operation_user_, operation_user_nickname_, source_, title_) VALUES ('0775d5e6-ee64-48cf-9d40-7e3b6a25ff57', '0:0:0:0:0:0:0:1', '系统日志', 'id : null -> ffa83b56-9610-42b1-97fe-41ceee9207fe
名称 : null -> 云数据库
图标 : null -> fa fa-database blue-text
视图 : null -> bdf3.dbconsole.view.DbConsoleMaintain.d
全局 : null -> false
下线 : null -> false
微程序 : null -> false
可显示 : null -> true
', '系统模块', '新增', '2018-03-29 23:31:47', 'admin', '系统管理员', 'http://localhost:8080/bdf3.notice.ui.view.Chat.d', '云数据库');
INSERT INTO bdf3_log_info (id_, ip_, category_, desc_, module_, operation_, operation_date_, operation_user_, operation_user_nickname_, source_, title_) VALUES ('2cb9237f-4080-4b09-a2d4-f8bfa6ff5255', '0:0:0:0:0:0:0:1', '系统日志', 'id : null -> 2081965d-fdcb-49ca-9763-6b497338f04d
名称 : null -> 日志查询
图标 : null -> fa fa-clock-o blue-text
视图 : null -> bdf3.log.view.LogInfoMaintain.d
全局 : null -> false
下线 : null -> false
微程序 : null -> false
可显示 : null -> true
', '系统模块', '新增', '2018-03-29 23:31:49', 'admin', '系统管理员', 'http://localhost:8080/bdf3.notice.ui.view.Chat.d', '日志查询');
INSERT INTO bdf3_log_info (id_, ip_, category_, desc_, module_, operation_, operation_date_, operation_user_, operation_user_nickname_, source_, title_) VALUES ('ccac5a57-1efe-4d19-b739-5e9f5a765d6b', '0:0:0:0:0:0:0:1', '系统日志', '功能项 : 0 -> 6
', '系统模块', '修改', '2018-03-29 23:35:23', 'admin', '系统管理员', 'http://localhost:8080/bdf3.notice.ui.view.Chat.d', '系统管理群');
INSERT INTO bdf3_log_info (id_, ip_, category_, desc_, module_, operation_, operation_date_, operation_user_, operation_user_nickname_, source_, title_) VALUES ('f51aaa19-86bd-481b-822b-6aa39c249285', '0:0:0:0:0:0:0:1', '系统日志', 'id : null -> a6714752-3a17-41cc-abce-a426a41ee14c
名称 : null -> 字典管理
图标 : null -> fa fa-book yellow-text
视图 : null -> bdf3.dictionary.ui.view.DictionaryMaintain.d
全局 : null -> false
下线 : null -> false
微程序 : null -> false
可显示 : null -> true
', '系统模块', '新增', '2018-03-29 23:35:27', 'admin', '系统管理员', 'http://localhost:8080/bdf3.notice.ui.view.Chat.d', '字典管理');
INSERT INTO bdf3_log_info (id_, ip_, category_, desc_, module_, operation_, operation_date_, operation_user_, operation_user_nickname_, source_, title_) VALUES ('5f1a8d60-33b6-4ac1-b1cd-3c8bbc4b91cc', '0:0:0:0:0:0:0:1', '系统日志', '功能项 : 0 -> 7
', '系统模块', '修改', '2018-03-29 23:35:44', 'admin', '系统管理员', 'http://localhost:8080/bdf3.notice.ui.view.Chat.d', '系统管理群');
INSERT INTO bdf3_log_info (id_, ip_, category_, desc_, module_, operation_, operation_date_, operation_user_, operation_user_nickname_, source_, title_) VALUES ('bff1f51f-223f-42c2-b938-ddbb375f437f', '0:0:0:0:0:0:0:1', '系统日志', '功能项 : 0 -> 7
', '系统模块', '修改', '2018-03-29 23:36:29', 'admin', '系统管理员', 'http://localhost:8080/bdf3.notice.ui.view.Chat.d', '系统管理群');
INSERT INTO bdf3_log_info (id_, ip_, category_, desc_, module_, operation_, operation_date_, operation_user_, operation_user_nickname_, source_, title_) VALUES ('6f0af137-5416-4d50-8867-9b96e0b49b94', '0:0:0:0:0:0:0:1', '系统日志', '功能项 : 0 -> 7
', '系统模块', '修改', '2018-03-29 23:37:21', 'admin', '系统管理员', 'http://localhost:8080/bdf3.notice.ui.view.Chat.d', '系统管理群');
INSERT INTO bdf3_log_info (id_, ip_, category_, desc_, module_, operation_, operation_date_, operation_user_, operation_user_nickname_, source_, title_) VALUES ('b53a1237-cfbb-49b8-9515-633b5be77ca8', '0:0:0:0:0:0:0:1', '系统日志', '功能项 : 0 -> 7
', '系统模块', '修改', '2018-03-29 23:38:30', 'admin', '系统管理员', 'http://localhost:8080/bdf3.notice.ui.view.Chat.d', '系统管理群');
INSERT INTO bdf3_log_info (id_, ip_, category_, desc_, module_, operation_, operation_date_, operation_user_, operation_user_nickname_, source_, title_) VALUES ('60fff426-ead0-4dc6-a3b6-24d4890b40a9', '0:0:0:0:0:0:0:1', '系统日志', '功能项 : 0 -> 7
', '系统模块', '修改', '2018-03-29 23:38:50', 'admin', '系统管理员', 'http://localhost:8080/bdf3.notice.ui.view.Chat.d', '系统管理群');
INSERT INTO bdf3_log_info (id_, ip_, category_, desc_, module_, operation_, operation_date_, operation_user_, operation_user_nickname_, source_, title_) VALUES ('e545a108-922c-4325-92f8-b6e482d22a12', '0:0:0:0:0:0:0:1', '系统日志', '功能项 : 0 -> 7
', '系统模块', '修改', '2018-03-29 23:39:40', 'admin', '系统管理员', 'http://localhost:8080/bdf3.notice.ui.view.Chat.d', '系统管理群');
INSERT INTO bdf3_log_info (id_, ip_, category_, desc_, module_, operation_, operation_date_, operation_user_, operation_user_nickname_, source_, title_) VALUES ('7b9fcac5-bd31-45f3-9e8c-150dea794fd5', '0:0:0:0:0:0:0:1', '系统日志', '功能项 : 0 -> 7
', '系统模块', '修改', '2018-03-29 23:40:20', 'admin', '系统管理员', 'http://localhost:8080/bdf3.notice.ui.view.Chat.d', '系统管理群');
INSERT INTO bdf3_log_info (id_, ip_, category_, desc_, module_, operation_, operation_date_, operation_user_, operation_user_nickname_, source_, title_) VALUES ('c9e01377-ae44-4e5d-83c5-8ba5d5ba0b33', '0:0:0:0:0:0:0:1', '系统日志', '', '系统模块', '删除', '2018-03-29 23:40:20', 'admin', '系统管理员', 'http://localhost:8080/bdf3.notice.ui.view.Chat.d', '字典管理');
INSERT INTO bdf3_log_info (id_, ip_, category_, desc_, module_, operation_, operation_date_, operation_user_, operation_user_nickname_, source_, title_) VALUES ('4776482c-0481-4c95-9baa-70d9e34d5e2a', '0:0:0:0:0:0:0:1', '系统日志', '功能项 : 0 -> 2
', '系统模块', '修改', '2018-03-29 23:40:43', 'admin', '系统管理员', 'http://localhost:8080/bdf3.notice.ui.view.Chat.d', '个人办公');
INSERT INTO bdf3_log_info (id_, ip_, category_, desc_, module_, operation_, operation_date_, operation_user_, operation_user_nickname_, source_, title_) VALUES ('444b90ea-c7ad-4348-99c2-a8877828f09e', '0:0:0:0:0:0:0:1', '系统日志', '功能项 : 0 -> 7
', '系统模块', '修改', '2018-03-29 23:40:43', 'admin', '系统管理员', 'http://localhost:8080/bdf3.notice.ui.view.Chat.d', '系统管理群');
INSERT INTO bdf3_log_info (id_, ip_, category_, desc_, module_, operation_, operation_date_, operation_user_, operation_user_nickname_, source_, title_) VALUES ('4d16e620-bf12-4ee5-a76e-9daddcf966a2', '0:0:0:0:0:0:0:1', '系统日志', 'id : null -> a6714752-3a17-41cc-abce-a426a41ee14c
名称 : null -> 字典管理
图标 : null -> fa fa-book yellow-text
视图 : null -> bdf3.dictionary.ui.view.DictionaryMaintain.d
全局 : null -> false
下线 : null -> false
微程序 : null -> false
可显示 : null -> true
', '系统模块', '新增', '2018-03-29 23:40:43', 'admin', '系统管理员', 'http://localhost:8080/bdf3.notice.ui.view.Chat.d', '字典管理');
INSERT INTO bdf3_log_info (id_, ip_, category_, desc_, module_, operation_, operation_date_, operation_user_, operation_user_nickname_, source_, title_) VALUES ('1350e016-4598-4feb-92cf-fe15133bbda5', '0:0:0:0:0:0:0:1', '系统日志', '功能项 : 0 -> 7
', '系统模块', '修改', '2018-03-29 23:41:34', 'admin', '系统管理员', 'http://localhost:8080/bdf3.notice.ui.view.Chat.d', '系统管理群');
INSERT INTO bdf3_log_info (id_, ip_, category_, desc_, module_, operation_, operation_date_, operation_user_, operation_user_nickname_, source_, title_) VALUES ('63cff5a7-c573-4e02-9b5f-3a5cc5b93d33', '0:0:0:0:0:0:0:1', '系统日志', '功能项 : 0 -> 3
', '系统模块', '修改', '2018-03-29 23:41:34', 'admin', '系统管理员', 'http://localhost:8080/bdf3.notice.ui.view.Chat.d', '运维群');
INSERT INTO bdf3_log_info (id_, ip_, category_, desc_, module_, operation_, operation_date_, operation_user_, operation_user_nickname_, source_, title_) VALUES ('82bd281b-9e0a-48bd-8282-fe3ef642d86c', '0:0:0:0:0:0:0:1', '系统日志', 'id : null -> ef2eeb71-eec0-4264-aa41-820f06b9918c
名称 : null -> 个人中心
图标 : null -> fa fa-tachometer red-text
视图 : null -> bdf3.security.ui.view.PersonalCenter.d
全局 : null -> false
下线 : null -> false
微程序 : null -> false
可显示 : null -> true
', '系统模块', '新增', '2018-03-29 23:41:34', 'admin', '系统管理员', 'http://localhost:8080/bdf3.notice.ui.view.Chat.d', '个人中心');
INSERT INTO bdf3_log_info (id_, ip_, category_, desc_, module_, operation_, operation_date_, operation_user_, operation_user_nickname_, source_, title_) VALUES ('5acd09e7-b31c-490e-b081-5bf428530d4d', '0:0:0:0:0:0:0:1', '系统日志', '', '系统模块', '删除', '2018-03-29 23:48:57', 'lisi', '李四', 'http://localhost:8080/bdf3.notice.ui.view.Chat.d', '个人办公');
INSERT INTO bdf3_log_info (id_, ip_, category_, desc_, module_, operation_, operation_date_, operation_user_, operation_user_nickname_, source_, title_) VALUES ('cf23f2c4-559d-49b7-930b-40383cc06a7e', '0:0:0:0:0:0:0:1', '系统日志', '功能项 : 0 -> 4
', '系统模块', '修改', '2018-03-29 23:52:04', 'admin', '系统管理员', 'http://localhost:8080/bdf3.notice.ui.view.Chat.d', '运维群');
INSERT INTO bdf3_log_info (id_, ip_, category_, desc_, module_, operation_, operation_date_, operation_user_, operation_user_nickname_, source_, title_) VALUES ('73e687bc-333b-4eb6-a299-32b69801ae60', '0:0:0:0:0:0:0:1', '系统日志', 'id : null -> d3753b1d-a256-44b6-a391-8a2c1a34e93f
名称 : null -> 大号红字
图标 : null -> fa fa-font red-text
CSS : null -> .big-size-red-word {
  font-size: 2em;
  color: rgb(255, 0, 0);
}
HTML : null -> <div class="big-size-red-word">{{ message.content }}</div>

全局 : null -> true
下线 : null -> false
微程序 : null -> false
描述 : null -> 大号红色字体的消息模版
可显示 : null -> false
', '系统模块', '新增', '2018-03-29 23:52:04', 'admin', '系统管理员', 'http://localhost:8080/bdf3.notice.ui.view.Chat.d', '大号红字');
INSERT INTO bdf3_log_info (id_, ip_, category_, desc_, module_, operation_, operation_date_, operation_user_, operation_user_nickname_, source_, title_) VALUES ('aa108aa9-0577-4efc-83b9-67cfe662d0f4', '0:0:0:0:0:0:0:1', '系统日志', '功能项 : 0 -> 4
templateId : null -> d3753b1d-a256-44b6-a391-8a2c1a34e93f
', '系统模块', '修改', '2018-03-29 23:52:06', 'admin', '系统管理员', 'http://localhost:8080/bdf3.notice.ui.view.Chat.d', '运维群');
DROP TABLE bdf3_notice;
CREATE TABLE bdf3_notice (id_ varchar(64) NOT NULL, all_ bit, content_ varchar(512), expire_time_ datetime, group_id_ varchar(64), send_time_ datetime, sender_ varchar(64), title_ varchar(255), type_ varchar(64), url_ varchar(512), template_id_ varchar(64), PRIMARY KEY (id_)) ENGINE=MyISAM DEFAULT CHARSET=utf8;
INSERT INTO bdf3_notice (id_, all_, content_, expire_time_, group_id_, send_time_, sender_, title_, type_, url_, template_id_) VALUES ('c22b4f4c-208b-47cd-88aa-e0cca96082a1', false, '我正在开会，稍后给你回复', null, '4f758d04-3366-4f4f-a7be-e65ef991a1b2', '2018-03-29 23:49:33', 'lisi', null, 'message', null, 'ece99ee9-577e-4148-9037-d3f4ed553f7a');
INSERT INTO bdf3_notice (id_, all_, content_, expire_time_, group_id_, send_time_, sender_, title_, type_, url_, template_id_) VALUES ('2187517d-dc21-4df9-892c-5b165db8c62b', false, '走，吃饭去', null, '4f758d04-3366-4f4f-a7be-e65ef991a1b2', '2018-03-29 23:49:35', 'lisi', null, 'message', null, 'ece99ee9-577e-4148-9037-d3f4ed553f7a');
INSERT INTO bdf3_notice (id_, all_, content_, expire_time_, group_id_, send_time_, sender_, title_, type_, url_, template_id_) VALUES ('8745ea94-7d58-4d19-9a0f-3231d37282df', false, '这个问题很严重', null, '4f758d04-3366-4f4f-a7be-e65ef991a1b2', '2018-03-29 23:49:36', 'lisi', null, 'message', null, 'ece99ee9-577e-4148-9037-d3f4ed553f7a');
INSERT INTO bdf3_notice (id_, all_, content_, expire_time_, group_id_, send_time_, sender_, title_, type_, url_, template_id_) VALUES ('892fa5c1-7711-46ce-97df-90692d5bf5fc', false, '我下班了，明天在讨论', null, '4f758d04-3366-4f4f-a7be-e65ef991a1b2', '2018-03-29 23:49:37', 'lisi', null, 'message', null, 'ece99ee9-577e-4148-9037-d3f4ed553f7a');
INSERT INTO bdf3_notice (id_, all_, content_, expire_time_, group_id_, send_time_, sender_, title_, type_, url_, template_id_) VALUES ('ee939ac5-9f49-4bb2-a314-589cc3d2e5b8', false, '稍等，马上给你答复', null, '4f758d04-3366-4f4f-a7be-e65ef991a1b2', '2018-03-29 23:49:38', 'lisi', null, 'message', null, 'ece99ee9-577e-4148-9037-d3f4ed553f7a');
INSERT INTO bdf3_notice (id_, all_, content_, expire_time_, group_id_, send_time_, sender_, title_, type_, url_, template_id_) VALUES ('f640c90e-3d9c-4272-9dc6-182e2b0f42bb', false, '谢谢你的帮助', null, '4f758d04-3366-4f4f-a7be-e65ef991a1b2', '2018-03-29 23:49:39', 'lisi', null, 'message', null, 'ece99ee9-577e-4148-9037-d3f4ed553f7a');
INSERT INTO bdf3_notice (id_, all_, content_, expire_time_, group_id_, send_time_, sender_, title_, type_, url_, template_id_) VALUES ('7ae1b5df-3793-471f-901c-62c285864346', false, '这个问题很严重', null, '4f758d04-3366-4f4f-a7be-e65ef991a1b2', '2018-03-29 23:49:41', 'lisi', null, 'message', null, 'ece99ee9-577e-4148-9037-d3f4ed553f7a');
INSERT INTO bdf3_notice (id_, all_, content_, expire_time_, group_id_, send_time_, sender_, title_, type_, url_, template_id_) VALUES ('ff46911c-847f-4476-92fd-bf6630b0f2c3', false, '{"dayNum":"2","reason":"身体不舒服","nickname":"李四"}', null, '4f758d04-3366-4f4f-a7be-e65ef991a1b2', '2018-03-29 23:50:26', 'lisi', null, 'message', null, 'a0b7efe9-8b34-407b-a6ad-568846ca6ee3');
INSERT INTO bdf3_notice (id_, all_, content_, expire_time_, group_id_, send_time_, sender_, title_, type_, url_, template_id_) VALUES ('4d85b216-facc-434d-9466-144a806b3d27', true, '通知：系统今晚上线', null, 'f35bd80c-26d2-477c-8c8d-55d737736692', '2018-03-29 23:51:20', 'admin', null, 'message', null, null);
INSERT INTO bdf3_notice (id_, all_, content_, expire_time_, group_id_, send_time_, sender_, title_, type_, url_, template_id_) VALUES ('d35bae13-0f81-4414-852b-3b9965d3d7be', false, '大家好', null, '5c367bca-604e-4bc4-8793-401cfb31d35e', '2018-03-29 23:52:27', 'admin', null, 'message', null, null);
INSERT INTO bdf3_notice (id_, all_, content_, expire_time_, group_id_, send_time_, sender_, title_, type_, url_, template_id_) VALUES ('945abe20-9082-40ce-a2c3-5edda1f9ced2', false, '晚上好', null, '5c367bca-604e-4bc4-8793-401cfb31d35e', '2018-03-29 23:52:43', 'admin', null, 'message', null, 'd3753b1d-a256-44b6-a391-8a2c1a34e93f');
DROP TABLE bdf3_template;
CREATE TABLE bdf3_template (id_ varchar(64) NOT NULL, css_ longtext, description_ varchar(255), displayable_ bit, global_ bit, html_ longtext, icon_ varchar(255), javascript_ longtext, micro_program_ bit, name_ varchar(255), offline_ bit, url_ varchar(512), PRIMARY KEY (id_)) ENGINE=MyISAM DEFAULT CHARSET=utf8;
INSERT INTO bdf3_template (id_, css_, description_, displayable_, global_, html_, icon_, javascript_, micro_program_, name_, offline_, url_) VALUES ('d3753b1d-a256-44b6-a391-8a2c1a34e93f', '.big-size-red-word {
  font-size: 2em;
  color: rgb(255, 0, 0);
}', '大号红色字体的消息模版', true, false, '<div class="big-size-red-word">{{ message.content }}</div>
', 'fa fa-font red-text', null, false, '大号红字', false, null);
INSERT INTO bdf3_template (id_, css_, description_, displayable_, global_, html_, icon_, javascript_, micro_program_, name_, offline_, url_) VALUES ('ece99ee9-577e-4148-9037-d3f4ed553f7a', '/****/
/****/
.tpl-message {
  color: rgba(0, 0, 0, .7);
  line-height: 2.5em;
  font-size: 1.2em;
  padding: 0 5px;
  border-radius: 3px;
  cursor: pointer;
}

.tpl-message:hover {
  background-color: #f7f7f7;
}', null, true, true, '<!---->
<!---->
<div class="tpl-message">我正在开会，稍后给你回复</div>
<div class="tpl-message">再见，下次再聊</div>
<div class="tpl-message">走，吃饭去</div>
<div class="tpl-message">这个问题很严重</div>
<div class="tpl-message">我下班了，明天在讨论</div>
<div class="tpl-message">稍等，马上给你答复</div>
<div class="tpl-message">谢谢你的帮助</div>', 'fa fa-newspaper-o', '/****/
/****/
$(tipDom).find(".tpl-message").click(function() {
  message.content = $(this).text();
  console.log(message)
  Chat.send(message);
});', false, '常用消息', false, null);
INSERT INTO bdf3_template (id_, css_, description_, displayable_, global_, html_, icon_, javascript_, micro_program_, name_, offline_, url_) VALUES ('a0b7efe9-8b34-407b-a6ad-568846ca6ee3', '.tpl-leave-flow .title {
  text-align: center;
  font-size: 1.3em;
  padding: 5px;
}

!.d-tip > .tpl-leave-flow {
  background-color:#fff;	
}

.tpl-leave-flow .item {
  padding: 10px;
}

.tpl-leave-flow .line {
  border-bottom: 1px solid #eee;
}

.tpl-leave-flow .item input {
  border-radius: 3px;
  border: 1px solid #ccc;
  padding: 5px;
  width: 200px;
}

.tpl-leave-flow button {
  border: 1px solid #eee;
  padding: 5px 10px;
  margin: 10px;
  float: right;
  border-radius: 3px;
}
/****/
/****/
.tpl-leave-flow .title {
  text-align: center;
  font-size: 1.3em;
  padding: 5px;
}

.tpl-leave-flow .item {
  padding: 10px;
}

.tpl-leave-flow .line {
  border-bottom: 1px solid #eee;
}

.tpl-leave-flow .item input {
  border-radius: 3px;
  border: 1px solid #ccc;
  padding: 5px;
  width: 200px;
}

.tpl-leave-flow button {
  border: 1px solid #eee;
  padding: 5px 10px;
  margin: 10px;
  float: right;
  border-radius: 3px;
}

.tpl-leave-flow .submit {
  background-color: #07c181;
  color: #fff;
  border: 1px solid #07c18;
}', null, true, false, '<div class="tpl-leave-flow">
  <div class="title line">请假单</div>
  <div class="item line">
    <label>请假事由：</label>
    <label>{{data.reason}}</label>
  </div>
  <div class="item line">
    <label>请假天数：</label>
    <label>{{data.dayNum}}天</label>
  </div>
  <button class="deal">现在处理</button>
<div>
<!---->
<div>{{ data.nickname }}发起请假申请</div>
<!---->
<div class="tpl-leave-flow">
  <div class="title line">请假单</div>
  <div class="item">
    <label>请假事由</label>
    <input class="reason" type="text"/>
  </div>
  <div class="item line">
    <label>请假天数</label>
    <input class="day-num" type="number"/>
  </div>
  <button class="submit">提交申请</button>
<div>', 'fa fa-hotel', '$(".tpl-leave-flow .deal").click(function() {
  dorado.MessageBox.confirm("确定审核通过？", function() {
    
  })
});
/****/
/****/
$(".tpl-leave-flow .submit").click(function() {
  var data = {
    dayNum: $(".tpl-leave-flow .day-num").val(),
    reason: $(".tpl-leave-flow .reason").val(),
    nickname: message.senderGroupMember.nickname
  };
  message.content = JSON.stringify(data);
  Chat.send(message);
});', false, '请假单', false, null);
INSERT INTO bdf3_template (id_, css_, description_, displayable_, global_, html_, icon_, javascript_, micro_program_, name_, offline_, url_) VALUES ('22cbb691-354c-4bb4-9ddc-4969d8d111ca', null, null, true, false, null, 'fa fa-superscript', null, false, '功能管理', false, 'bdf3.notice.ui.view.TemplateMaintain.d');
INSERT INTO bdf3_template (id_, css_, description_, displayable_, global_, html_, icon_, javascript_, micro_program_, name_, offline_, url_) VALUES ('d82052cc-7027-4cf3-bed4-9de1e775cf70', null, null, true, false, null, 'fa fa-user blue-text', null, false, '用户管理', false, 'bdf3.security.ui.view.UserMaintain.d');
INSERT INTO bdf3_template (id_, css_, description_, displayable_, global_, html_, icon_, javascript_, micro_program_, name_, offline_, url_) VALUES ('6900e36f-62dd-4b45-8f98-d71875771e13', null, null, true, false, null, 'fa fa-bars yellow-text', null, false, '菜单管理', false, 'bdf3.security.ui.view.UrlMaintain.d');
INSERT INTO bdf3_template (id_, css_, description_, displayable_, global_, html_, icon_, javascript_, micro_program_, name_, offline_, url_) VALUES ('8e8aa5fc-412f-42c8-840e-282c90f20a19', null, null, true, false, null, 'fa fa-user-md blue-text', null, false, '角色管理', false, 'bdf3.security.ui.view.RoleMaintain.d');
INSERT INTO bdf3_template (id_, css_, description_, displayable_, global_, html_, icon_, javascript_, micro_program_, name_, offline_, url_) VALUES ('36f834d5-8314-4f48-92bd-23c298e5738e', null, null, true, false, null, 'fa fa-shield yellow-text', null, false, '权限分配', false, 'bdf3.security.ui.view.PermissionMaintain.d');
INSERT INTO bdf3_template (id_, css_, description_, displayable_, global_, html_, icon_, javascript_, micro_program_, name_, offline_, url_) VALUES ('c29f21a1-83a9-41a0-9aa6-8de4878d5954', null, null, true, false, null, 'fa fa-code-fork green-text', null, false, '菜单权限', false, 'bdf3.security.ui.view.RoleUrlMaintain.d');
INSERT INTO bdf3_template (id_, css_, description_, displayable_, global_, html_, icon_, javascript_, micro_program_, name_, offline_, url_) VALUES ('ca0dcb00-86c1-4dee-9312-c6ccacbf4d57', null, null, true, false, null, 'fa fa-file-excel-o orange-text', null, false, '导入管理', false, 'bdf3.importer.view.ImporterSolutionMaintain.d');
INSERT INTO bdf3_template (id_, css_, description_, displayable_, global_, html_, icon_, javascript_, micro_program_, name_, offline_, url_) VALUES ('a6714752-3a17-41cc-abce-a426a41ee14c', null, null, true, false, null, 'fa fa-book yellow-text', null, false, '字典管理', false, 'bdf3.dictionary.ui.view.DictionaryMaintain.d');
INSERT INTO bdf3_template (id_, css_, description_, displayable_, global_, html_, icon_, javascript_, micro_program_, name_, offline_, url_) VALUES ('ffa83b56-9610-42b1-97fe-41ceee9207fe', null, null, true, false, null, 'fa fa-database blue-text', null, false, '云数据库', false, 'bdf3.dbconsole.view.DbConsoleMaintain.d');
INSERT INTO bdf3_template (id_, css_, description_, displayable_, global_, html_, icon_, javascript_, micro_program_, name_, offline_, url_) VALUES ('2081965d-fdcb-49ca-9763-6b497338f04d', null, null, true, false, null, 'fa fa-clock-o blue-text', null, false, '日志查询', false, 'bdf3.log.view.LogInfoMaintain.d');
INSERT INTO bdf3_template (id_, css_, description_, displayable_, global_, html_, icon_, javascript_, micro_program_, name_, offline_, url_) VALUES ('ef2eeb71-eec0-4264-aa41-820f06b9918c', null, null, true, false, null, 'fa fa-tachometer red-text', null, false, '个人中心', false, 'bdf3.security.ui.view.PersonalCenter.d');
DROP TABLE bdf3_user;
CREATE TABLE bdf3_user (username_ varchar(64) NOT NULL, account_non_expired_ bit, account_non_locked_ bit, administrator_ bit, credentials_non_expired_ bit, enabled_ bit, nickname_ varchar(64), password_ varchar(125), PRIMARY KEY (username_)) ENGINE=MyISAM DEFAULT CHARSET=utf8;
INSERT INTO bdf3_user (username_, account_non_expired_, account_non_locked_, administrator_, credentials_non_expired_, enabled_, nickname_, password_) VALUES ('admin', true, true, true, true, true, '系统管理员', '{bcrypt}$2a$10$J9ryMi4/lw84.7JfYAInn.4DLlyTTjdMvTdqXXu5cokylxHM4orZm');
INSERT INTO bdf3_user (username_, account_non_expired_, account_non_locked_, administrator_, credentials_non_expired_, enabled_, nickname_, password_) VALUES ('lisi', true, true, false, true, true, '李四', '{bcrypt}$2a$10$83Q9W.mq8KetdcmAog3nnuDoC5t3MABKT.3vea.izS6iAQXvZ3Sx.');
INSERT INTO bdf3_user (username_, account_non_expired_, account_non_locked_, administrator_, credentials_non_expired_, enabled_, nickname_, password_) VALUES ('zhangsan', true, true, false, true, true, '张三', '{bcrypt}$2a$10$s8oeM/HzocE0uwt4t/crlOANEziY3QacHYUDmRhiwjPOBTjl0RKjq');
INSERT INTO bdf3_user (username_, account_non_expired_, account_non_locked_, administrator_, credentials_non_expired_, enabled_, nickname_, password_) VALUES ('wangwu', true, true, false, true, true, '王五', '{bcrypt}$2a$10$U70itykX6yvUSIWgcByRg.nA9fOg4NoAGdXRjQmBVDhw0B3GzJXwy');
INSERT INTO bdf3_user (username_, account_non_expired_, account_non_locked_, administrator_, credentials_non_expired_, enabled_, nickname_, password_) VALUES ('xiaoming', true, true, false, true, true, '小明', '{bcrypt}$2a$10$U10owlQc3kXhLRBes/hdMOKgZiZxUZUr3iDBHuisFxaUuZ.LJPFz.');
