-- 创建舌诊数据库
CREATE DATABASE IF NOT EXISTS glossoscopy;

-- 当前使用舌诊数据库
USE glossoscopy;


-- 创建用户表
CREATE TABLE IF NOT EXISTS tb_user
(
    id           BIGINT UNSIGNED AUTO_INCREMENT PRIMARY KEY COMMENT '用户id',
    username     VARCHAR(20)        NOT NULL COMMENT '用户名',
    avatar       VARCHAR(100)       NOT NULL COMMENT '头像',
    account      VARCHAR(20) UNIQUE NOT NULL COMMENT '账号',
    password     VARCHAR(60)        NOT NULL COMMENT '密码',
    phone_number VARCHAR(11) UNIQUE NOT NULL COMMENT '手机号',
    status       BOOLEAN            NOT NULL COMMENT '是否可用',
    delete_flag  BOOLEAN            NOT NULL COMMENT '是否删除',
    create_time  DATETIME           NOT NULL COMMENT '创建时间',
    update_time  DATETIME           NOT NULL COMMENT '修改时间'
) ENGINE = InnoDB
  AUTO_INCREMENT = 1
  DEFAULT CHARSET = utf8mb4 COMMENT ='用户表';

-- 唯一索引
CREATE UNIQUE INDEX idx_phone_number_on_user ON tb_user (phone_number);

-- 唯一索引
CREATE UNIQUE INDEX idx_account_on_user ON tb_user (account);

-- 复合索引
CREATE INDEX idx_account_password_on_user ON tb_user (account, password);

-- 插入用户A
INSERT INTO tb_user (username, avatar, account, password, phone_number, status, delete_flag, create_time, update_time)
VALUES ('用户A', 'https://itxiaoqi.oss-cn-hangzhou.aliyuncs.com/static/peopleAvatar.jpg', 'userA123456',
        '$2a$10$iGJ5t6tbffIJI1K0QmPtB.dgVGZP6bf5d7VP1nhekAFYMimu7p6PG',
        '13800138001', true, false, NOW(), NOW());

-- 插入用户B
INSERT INTO tb_user (username, avatar, account, password, phone_number, status, delete_flag, create_time, update_time)
VALUES ('用户B', 'https://itxiaoqi.oss-cn-hangzhou.aliyuncs.com/static/peopleAvatar.jpg', 'userB123456',
        '$2a$10$iGJ5t6tbffIJI1K0QmPtB.dgVGZP6bf5d7VP1nhekAFYMimu7p6PG',
        '13800138002', true, false, NOW(), NOW());

-- 插入用户C
INSERT INTO tb_user (username, avatar, account, password, phone_number, status, delete_flag, create_time, update_time)
VALUES ('用户C', 'https://itxiaoqi.oss-cn-hangzhou.aliyuncs.com/static/peopleAvatar.jpg', 'userC123456',
        '$2a$10$iGJ5t6tbffIJI1K0QmPtB.dgVGZP6bf5d7VP1nhekAFYMimu7p6PG',
        '13800138003', true, false, NOW(), NOW());


-- 创建用户成员表
CREATE TABLE IF NOT EXISTS tb_member
(
    id            BIGINT UNSIGNED AUTO_INCREMENT PRIMARY KEY COMMENT '成员id',
    user_id       BIGINT UNSIGNED NOT NULL COMMENT '用户id',
    member_name   VARCHAR(20)     NOT NULL COMMENT '成员名',
    avatar        VARCHAR(100)    NOT NULL COMMENT '头像',
    sex           INT UNSIGNED    NOT NULL COMMENT '性别',
    birthday      DATE            NOT NULL COMMENT '生日',
    health_status INT UNSIGNED COMMENT '体质',
    address       VARCHAR(100)    NOT NULL COMMENT '住址',
    anamnesis     VARCHAR(100)    NOT NULL COMMENT '病史',
    occupation    VARCHAR(100)    NOT NULL COMMENT '职业',
    delete_flag   BOOLEAN         NOT NULL COMMENT '是否删除',
    create_time   DATETIME        NOT NULL COMMENT '创建时间',
    update_time   DATETIME        NOT NULL COMMENT '修改时间',
    CONSTRAINT fk_user_id_on_member FOREIGN KEY (user_id) REFERENCES tb_user (id)
) ENGINE = InnoDB
  AUTO_INCREMENT = 1
  DEFAULT CHARSET = utf8mb4 COMMENT ='成员表';

-- 外键索引
CREATE INDEX idx_user_id_on_member ON tb_member (user_id);

-- 用户A的成员
INSERT INTO tb_member (user_id, member_name, avatar, sex, birthday, health_status, address, anamnesis, occupation,
                       delete_flag, create_time, update_time)
VALUES (1, '成员A1', 'https://itxiaoqi.oss-cn-hangzhou.aliyuncs.com/static/son.jpg', 1, '1990-01-01', 1, '上海', '无',
        '工程师', false, NOW(), NOW()),
       (1, '成员A2', 'https://itxiaoqi.oss-cn-hangzhou.aliyuncs.com/static/daughter.jpg', 0, '1995-05-05', 2, '天津',
        '高血压', '医生', false, NOW(), NOW()),
       (1, '成员A3', 'https://itxiaoqi.oss-cn-hangzhou.aliyuncs.com/static/son.jpg', 1, '2000-10-10', 3, '北京',
        '糖尿病', '教师', false, NOW(), NOW());

-- 用户B的成员
INSERT INTO tb_member (user_id, member_name, avatar, sex, birthday, health_status, address, anamnesis, occupation,
                       delete_flag, create_time, update_time)
VALUES (2, '成员B1', 'https://itxiaoqi.oss-cn-hangzhou.aliyuncs.com/static/daughter.jpg', 0, '1985-03-15', 1, '武汉',
        '无', '设计师', false, NOW(), NOW()),
       (2, '成员B2', 'https://itxiaoqi.oss-cn-hangzhou.aliyuncs.com/static/son.jpg', 1, '1992-07-20', 2, '杭州', '哮喘',
        '律师', false, NOW(), NOW()),
       (2, '成员B3', 'https://itxiaoqi.oss-cn-hangzhou.aliyuncs.com/static/daughter.jpg', 0, '1998-12-25', 3, '广州',
        '心脏病', '护士', false, NOW(), NOW());


-- 创建诊断记录表
CREATE TABLE IF NOT EXISTS tb_record
(
    id               BIGINT UNSIGNED AUTO_INCREMENT PRIMARY KEY COMMENT '记录id',
    user_id          BIGINT UNSIGNED NOT NULL COMMENT '用户id',
    member_id        BIGINT UNSIGNED NOT NULL COMMENT '成员id',
    image            VARCHAR(100)    NOT NULL COMMENT '图片',
    health_status    INT UNSIGNED    NOT NULL COMMENT '体质',
    tooth_mark       INT UNSIGNED    NOT NULL COMMENT '齿痕',
    tongue_thickness INT UNSIGNED    NOT NULL COMMENT '厚薄',
    tongue_size      INT UNSIGNED    NOT NULL COMMENT '胖瘦',
    coating_color    INT UNSIGNED    NOT NULL COMMENT '苔色',
    delete_flag      BOOLEAN         NOT NULL COMMENT '是否删除',
    create_time      DATETIME        NOT NULL COMMENT '诊断时间',
    CONSTRAINT fk_user_id_on_record FOREIGN KEY (user_id) REFERENCES tb_user (id),
    CONSTRAINT fk_member_id_on_record FOREIGN KEY (member_id) REFERENCES tb_member (id)
) ENGINE = InnoDB
  AUTO_INCREMENT = 1
  DEFAULT CHARSET = utf8mb4 COMMENT ='诊断记录表';

-- 外键索引
CREATE INDEX idx_user_id_on_record ON tb_record (user_id);

-- 外键索引
CREATE INDEX idx_member_id_on_record ON tb_record (member_id);

-- 成员A1的诊断记录
INSERT INTO tb_record (user_id, member_id, image, health_status, tooth_mark, tongue_thickness, tongue_size,
                       coating_color, delete_flag, create_time)
VALUES (1, 1, 'https://itxiaoqi.oss-cn-hangzhou.aliyuncs.com/02378b6f-73de-44d1-a633-07d4be65f9fa.jpg', 1, 1, 1, 1, 1,
        false, NOW()),
       (1, 1, 'https://itxiaoqi.oss-cn-hangzhou.aliyuncs.com/02378b6f-73de-44d1-a633-07d4be65f9fa.jpg', 2, 0, 0, 0, 2,
        false, NOW()),
       (1, 1, 'https://itxiaoqi.oss-cn-hangzhou.aliyuncs.com/02378b6f-73de-44d1-a633-07d4be65f9fa.jpg', 3, 0, 0, 0, 3,
        false, NOW());

-- 成员A2的诊断记录
INSERT INTO tb_record (user_id, member_id, image, health_status, tooth_mark, tongue_thickness, tongue_size,
                       coating_color, delete_flag, create_time)
VALUES (1, 2, 'https://itxiaoqi.oss-cn-hangzhou.aliyuncs.com/02378b6f-73de-44d1-a633-07d4be65f9fa.jpg', 1, 0, 0, 0, 1,
        false, NOW()),
       (1, 2, 'https://itxiaoqi.oss-cn-hangzhou.aliyuncs.com/02378b6f-73de-44d1-a633-07d4be65f9fa.jpg', 2, 0, 0, 0, 2,
        false, NOW()),
       (1, 2, 'https://itxiaoqi.oss-cn-hangzhou.aliyuncs.com/02378b6f-73de-44d1-a633-07d4be65f9fa.jpg', 3, 1, 1, 1, 3,
        false, NOW());

-- 成员A3的诊断记录
INSERT INTO tb_record (user_id, member_id, image, health_status, tooth_mark, tongue_thickness, tongue_size,
                       coating_color, delete_flag, create_time)
VALUES (1, 3, 'https://itxiaoqi.oss-cn-hangzhou.aliyuncs.com/02378b6f-73de-44d1-a633-07d4be65f9fa.jpg', 1, 0, 0, 0, 1,
        false, NOW()),
       (1, 3, 'https://itxiaoqi.oss-cn-hangzhou.aliyuncs.com/02378b6f-73de-44d1-a633-07d4be65f9fa.jpg', 2, 1, 1, 1, 2,
        false, NOW()),
       (1, 3, 'https://itxiaoqi.oss-cn-hangzhou.aliyuncs.com/02378b6f-73de-44d1-a633-07d4be65f9fa.jpg', 3, 0, 0, 0, 3,
        false, NOW());

-- 成员B1的诊断记录
INSERT INTO tb_record (user_id, member_id, image, health_status, tooth_mark, tongue_thickness, tongue_size,
                       coating_color, delete_flag, create_time)
VALUES (2, 4, 'https://itxiaoqi.oss-cn-hangzhou.aliyuncs.com/02378b6f-73de-44d1-a633-07d4be65f9fa.jpg', 1, 1, 1, 1, 0,
        false, NOW()),
       (2, 4, 'https://itxiaoqi.oss-cn-hangzhou.aliyuncs.com/02378b6f-73de-44d1-a633-07d4be65f9fa.jpg', 2, 0, 0, 1, 2,
        false, NOW()),
       (2, 4, 'https://itxiaoqi.oss-cn-hangzhou.aliyuncs.com/02378b6f-73de-44d1-a633-07d4be65f9fa.jpg', 3, 1, 1, 0, 3,
        false, NOW());

-- 成员B2的诊断记录
INSERT INTO tb_record (user_id, member_id, image, health_status, tooth_mark, tongue_thickness, tongue_size,
                       coating_color, delete_flag, create_time)
VALUES (2, 5, 'https://itxiaoqi.oss-cn-hangzhou.aliyuncs.com/02378b6f-73de-44d1-a633-07d4be65f9fa.jpg', 1, 0, 1, 1, 1,
        false, NOW()),
       (2, 5, 'https://itxiaoqi.oss-cn-hangzhou.aliyuncs.com/02378b6f-73de-44d1-a633-07d4be65f9fa.jpg', 2, 1, 1, 0, 2,
        false, NOW()),
       (2, 5, 'https://itxiaoqi.oss-cn-hangzhou.aliyuncs.com/02378b6f-73de-44d1-a633-07d4be65f9fa.jpg', 3, 0, 0, 1, 3,
        false, NOW());

-- 成员B3的诊断记录
INSERT INTO tb_record (user_id, member_id, image, health_status, tooth_mark, tongue_thickness, tongue_size,
                       coating_color, delete_flag, create_time)
VALUES (2, 6, 'https://itxiaoqi.oss-cn-hangzhou.aliyuncs.com/02378b6f-73de-44d1-a633-07d4be65f9fa.jpg', 1, 1, 0, 1, 1,
        false, NOW()),
       (2, 6, 'https://itxiaoqi.oss-cn-hangzhou.aliyuncs.com/02378b6f-73de-44d1-a633-07d4be65f9fa.jpg', 2, 1, 0, 1, 2,
        false, NOW()),
       (2, 6, 'https://itxiaoqi.oss-cn-hangzhou.aliyuncs.com/02378b6f-73de-44d1-a633-07d4be65f9fa.jpg', 3, 0, 1, 0, 3,
        false, NOW());


-- 创建体质信息表
CREATE TABLE IF NOT EXISTS tb_health_status
(
    id                                        BIGINT UNSIGNED AUTO_INCREMENT PRIMARY KEY COMMENT '体质id',
    description                               VARCHAR(10) NOT NULL COMMENT '外在特征',
    external_characteristics                  TEXT        NOT NULL COMMENT '外在特征',
    intrinsic_characteristics                 TEXT        NOT NULL COMMENT '内在特征',
    tendency_to_fall_ill                      TEXT        NOT NULL COMMENT '发病倾向',
    psychological_conditioning                TEXT        NOT NULL COMMENT '心理调养',
    exercise_conditioning                     TEXT        NOT NULL COMMENT '运动调养',
    traditional_chinese_medicine_conditioning TEXT        NOT NULL COMMENT '中医药调养',
    dietary_conditioning                      TEXT        NOT NULL COMMENT '饮食调养',
    contraindication                          TEXT        NOT NULL COMMENT '禁忌'
) ENGINE = InnoDB
  AUTO_INCREMENT = 1
  DEFAULT CHARSET = utf8mb4 COMMENT ='体质状态表';

-- 平和体质信息
INSERT INTO tb_health_status (description, external_characteristics, intrinsic_characteristics, tendency_to_fall_ill,
                              psychological_conditioning, exercise_conditioning,
                              traditional_chinese_medicine_conditioning, dietary_conditioning, contraindication)
VALUES ('平和体质',
        '平和体质的人在外在特征上，通常表现为体型匀称，面色红润，皮肤光滑有光泽，精力充沛，睡眠质量好，脉搏平稳，整体看起来健康有活力。他们能轻松适应环境变化，情绪稳定，精神状态良好。',
        '在内在特征上，平和体质的人阴阳平衡，气血充足，脏腑功能正常，代谢良好。他们的免疫力强，身体各项功能协调，能够自我调节，抵抗力较高，不容易生病。整体而言，平和体质的人具有较强的生理适应能力和健康状态。',
        '平和体质的人因阴阳平衡、气血充足、脏腑功能正常，通常不易生病，发病倾向较低。然而，随着年龄增长或生活方式不当，如饮食不规律、长期精神压力大，可能导致气血不畅或阴阳失调，从而引发一些轻微的健康问题。平和体质的人虽然免疫力强，但若不注意调节，仍可能受到一些常见病和生活方式相关疾病的影响，如消化不良、过敏性问题和代谢性疾病等。',
        '应避免过度的压力和负面情绪，如愤怒、焦虑等，这些情绪可能影响气血运行。通过冥想、深呼吸等放松技巧，以及培养乐观心态和宽容心，能够帮助调节情绪，保持心理的平稳。此外，参与社交活动和兴趣爱好，适当放松心情，也是有效的心理调理方式。保持规律作息和充足睡眠有助于神经系统的恢复，提升心理承受力，进一步促进身心健康。',
        '适度运动能增强免疫力，保持健康。平和体质的人应选择散步、慢跑、瑜伽等，每周三到四次，每次30至60分钟。运动应以有氧为主，保持专注和均匀呼吸。保持规律的睡眠，每晚7到8小时，睡前避免疲劳和使用电子产品。运动和睡眠要适度，年轻人可跑步、打球，老年人可散步、打太极。生活要规律，避免过劳，饮食避免过饱或过饥，保持积极心态，培养兴趣爱好。',
        '平和体质的人应通过饮食调理而非药物补益来保持健康，因为阴阳已平衡，过度使用大补药物如人参可能破坏平衡。饮食要清淡，避免过度偏嗜五味，以免伤脏腑。应顺应四季调整饮食：春季食辛甘，夏季食清淡，秋季润肺生津，冬季温补。中草药如黄芪、党参、枸杞等有助于滋补和提升免疫力，针灸和按摩也可增强体质。若体质失衡，建议咨询中医师进行个性化调理。',
        '平和体质的人饮食应保持营养均衡，荤素搭配，避免重复食物，少吃油腻、辛辣和生冷食物。遵循“早饭宜好，午饭宜饱，晚饭宜少”的原则，并保证规律作息。建议多吃新鲜蔬菜、水果、粗粮和瘦肉，适量摄入水分。饮食应根据季节调整，如春食辛甘、夏食清淡、秋食润阴、冬食温热食物。适合平和体质的食物有羊肉、牛肉、山药、红枣等，且可食用有助于健脾益气的山药、枸杞等食物。避免过饱过饥，保持饮食清淡、温和。',
        '过度依赖药物，尤其是大补类药物，可能破坏体内平衡。长时间静养或久坐不动也会导致气血不畅，影响身体代谢。此外，过度节食或饮食不规律会使身体缺乏必要的营养，影响脏腑功能。情感过度压抑或环境过于寒冷或炎热，也可能对身体产生负面影响，干扰气血流通。因此，平和体质的人应保持饮食、作息、情绪和环境的平衡，避免过度或极端行为，以维持健康。');

-- 阳虚体质信息
INSERT INTO tb_health_status (description, external_characteristics, intrinsic_characteristics, tendency_to_fall_ill,
                              psychological_conditioning, exercise_conditioning,
                              traditional_chinese_medicine_conditioning, dietary_conditioning, contraindication)
VALUES ('阳虚体质',
        '面色苍白、手脚冰冷、畏寒怕冷等。皮肤通常缺乏光泽，呈现干燥状态，反映出体内阳气不足。手足发冷是阳虚的典型标志，尤其在寒冷天气中更加明显。此外，阳虚体质者的舌质偏淡，舌苔白滑，显示出阳气亏损。',
        '阳虚体质的核心表现为阳气不足，主要影响脾胃的运化功能，导致消化能力减弱。阳虚者常出现食欲不振、消化不良、腹胀等问题。容易感到疲劳、虚弱，精神状态较差，长期处于低能量状态。此外，阳虚体质的免疫力较低，容易受到外界环境的影响，导致频繁感染和疾病发生。',
        '阳虚体质容易引发的疾病主要包括呼吸系统疾病（如感冒、支气管炎等）和消化系统疾病（如胃寒、腹泻等）。研究表明，阳虚体质的人群由于体内阳气不足，免疫力较低，更容易受到寒冷或湿气的侵袭，导致频繁感冒或慢性疾病。此外，阳虚体质者的精神状态较差，容易出现慢性疲劳综合症，长期的低能量状态可能加重体质虚弱。',
        '阳虚体质者因精力不足，易产生焦虑、抑郁等情绪问题，因此心理调养同样重要。研究发现，阳虚体质者通过调节情绪、保持愉悦心态，能够有效缓解身体的不适。心理调养方法包括冥想、深呼吸、适量社交等，能帮助阳虚体质者放松心情、恢复体力。',
        '阳虚体质者应保持规律作息，避免熬夜和过度疲劳，特别是在寒冷季节要注意保暖。规律的作息能够帮助阳气恢复和增强免疫力。适量的温和运动，如散步、太极、瑜伽等，有助于改善气血循环，促进阳气的恢复。过度剧烈的运动则应避免，因为这可能会消耗体内的阳气，导致体力更加虚弱。',
        '中医治疗阳虚体质的常见方法是通过温阳补气的药物调理，如黄芪、党参、人参、枸杞、桂枝等中草药。通过调理中药方剂，可以帮助恢复阳气，增强体力和免疫力。中医认为，阳虚体质需要根据个人的具体症状进行辩证施治，以便更有效地提高治疗效果。',
        '饮食调养对于阳虚体质的改善至关重要。阳虚体质的人应以温补阳气的食物为主，避免寒凉食物。常见的温补食物包括羊肉、鸡肉、生姜、大葱等，此外，枸杞、大枣、桂圆等也是有益的调理食材。温热性质的食物能够帮助增强体内阳气，改善寒冷感和消化问题。避免食用生冷、寒凉的食物，如冷饮、冰淇淋等，以免加重阳虚症状。',
        '阳虚体质者应避免寒冷刺激，包括避免长时间待在寒冷环境中，避免穿着过于单薄的衣物，避免过度食用生冷食物。寒凉食物如冰淇淋、冷饮、生蔬菜等容易损伤脾胃，进一步加重阳虚症状。此外，阳虚体质者还应避免熬夜和过度疲劳，这会耗损体内阳气，导致症状加重。');

-- 阴虚体质信息
INSERT INTO tb_health_status (description, external_characteristics, intrinsic_characteristics, tendency_to_fall_ill,
                              psychological_conditioning, exercise_conditioning,
                              traditional_chinese_medicine_conditioning, dietary_conditioning, contraindication)
VALUES ('阴虚体质',
        '阴虚体质者通常表现为面色偏红或发干，眼睛干涩，皮肤缺乏润泽。舌质红，舌苔少或无苔，脉细数。常常感到口干、咽喉干痛，体力较差，容易疲劳，特别是晚上入睡困难，失眠多梦。',
        '阴虚体质通常伴随有内热症状，体内的津液不足，导致脏腑功能失调。脾胃阴虚导致消化不良，心肾阴虚可能引发失眠、焦虑等问题。肝阴虚可能引起头晕目眩、干眼等症状。整体表现为阴液不足，内热亢盛，容易出现烦躁、口渴等现象。',
        '阴虚体质者容易患有高血压、失眠、便秘、口干舌燥等疾病。由于体内阴液不足，易出现虚火上升，可能引发内热引起的多种健康问题。此外，阴虚体质也容易导致免疫力下降，容易感到疲劳。',
        '由于阴虚体质者常伴有失眠、焦虑等症状，保持心理平衡至关重要。避免过度焦虑、压力过大，保持心情舒畅，冥想或轻度瑜伽可以有助于减轻症状。',
        '阴虚体质者需要保持规律作息，避免熬夜，确保充足的睡眠。过度劳累会消耗阴液，导致体力和免疫力下降。适量的运动可以促进体液的循环，但应避免过度剧烈的运动。',
        '阴虚体质的调理可以通过滋阴补肾的中药，如知母、黄柏、枸杞等来增强体内的阴液。同时，针灸和拔罐等治疗方法有助于改善气血流通，调理阴阳平衡。',
        '阴虚体质者应以滋阴养液、清热解毒的食物为主，推荐食用百合、枸杞、桑葚、梨、银耳、豆腐等滋阴的食材。避免食用辛辣刺激和油腻食物，这些食物可能助火生热，进一步消耗阴液。',
        '阴虚体质者应避免辛辣食物、酒精和咖啡，这些会助热生火，消耗阴液。避免熬夜和过度劳累，因为这会加重阴虚症状。应避免干燥环境，避免情绪过度波动，特别是焦虑和愤怒。');

-- 气虚体质信息
INSERT INTO tb_health_status (description, external_characteristics, intrinsic_characteristics, tendency_to_fall_ill,
                              psychological_conditioning, exercise_conditioning,
                              traditional_chinese_medicine_conditioning, dietary_conditioning, contraindication)
VALUES ('气虚体质',
        '气虚体质者常面色萎黄、气色差，形态消瘦或体形较弱，肌肉松弛。常出现乏力、气短等症状，体温调节能力差，容易感到寒冷。舌质淡，舌苔薄白。',
        '气虚体质的人脾胃功能较弱，常表现为食欲不振、消化不良、腹胀等。由于肺气虚，容易出现气短、咳嗽等症状。心气虚则可能引发心悸、失眠等症状。总体表现为精力不足，容易感到疲倦。',
        '气虚体质的人易患感冒、慢性支气管炎、贫血、胃肠功能失调等疾病。由于免疫力低下，身体容易受到外界环境的影响，特别是在季节变化时，容易感冒和感染。消化系统脆弱，容易出现消化不良等问题。',
        '气虚体质的人因精力不足，可能出现情绪低落、焦虑等心理问题。保持积极乐观的心态、避免过度忧虑和紧张，能有效缓解疲劳感，改善身体状况。',
        '气虚体质的人应避免过度劳累和熬夜，保持规律作息，确保充足的睡眠。适度的运动，如散步、太极等温和运动，有助于增强体力和气血，但应避免剧烈运动。',
        '中医认为，通过使用补气药物（如人参、黄芪、党参等）和调理方剂，可以有效改善气虚体质。针灸、拔罐等方法也有助于激发体内气血，增强体力和免疫力。',
        '饮食上应以补气养血为主，推荐食用具有温补作用的食物，如黄芪、党参、枸杞、桂圆、红枣等。此外，鸡肉、牛肉等易消化的高蛋白食品也有助于气虚体质的调理。避免寒冷和油腻食物，以免损伤脾胃。',
        '气虚体质者禁忌过度劳累、熬夜、寒凉食物（如冷饮、生冷食物）、久坐不动、剧烈运动以及情绪波动。过度劳累和熬夜会加重气虚，寒凉食物损伤脾胃，久坐和剧烈运动影响气血流通，情绪波动则扰乱气血，导致体质更加虚弱。');

-- 痰湿体质信息
INSERT INTO tb_health_status (description, external_characteristics, intrinsic_characteristics, tendency_to_fall_ill,
                              psychological_conditioning, exercise_conditioning,
                              traditional_chinese_medicine_conditioning, dietary_conditioning, contraindication)
VALUES ('痰湿体质',
        '痰湿体质的外在表现通常为肥胖、身体沉重，尤其是腹部或大腿容易堆积脂肪。面部和皮肤可能呈现油腻感，面色暗黄或泛白。舌苔厚腻，舌质偏胖，容易出现浮肿，四肢无力。经常感到沉重、困倦，活动后不易恢复。',
        '痰湿体质者通常脾胃功能弱，湿气无法排出体外，导致水液代谢紊乱，形成痰湿积聚。脾虚不能运化水湿，湿气内生，容易引发食欲不振、腹胀、便秘等消化问题。由于体内湿气过重，气血流动不畅，常伴有头重、眩晕、胸闷等症状，长期积累可导致体内痰湿凝滞，影响脏腑功能。',
        '痰湿体质容易引发肥胖、糖尿病、高血脂等代谢性疾病，还易患呼吸系统疾病，如慢性支气管炎、哮喘等。痰湿体质者由于湿气困扰，消化系统功能较弱，容易导致食欲不振、消化不良、腹胀、便秘等问题。此外，长期湿气积聚还可能导致肝胆疾病、结石等健康问题。',
        '情绪过度抑郁、焦虑会加重痰湿体质的症状。痰湿体质者应保持愉悦心情，避免长时间的负面情绪，以免影响脾胃功能，导致湿气积聚。保持良好的心理状态有助于气血流畅，增强免疫力。',
        '痰湿体质者应保持规律的作息时间，避免熬夜。适度的有氧运动，如快走、游泳、骑行等，有助于促进血液循环，排除体内湿气，增强身体的代谢功能。但避免剧烈运动或过度疲劳，以免损伤脾胃。',
        '中医认为，通过应用健脾化湿、祛痰除湿的中药（如茯苓、薏米、半夏等）和针灸、拔罐等方法，可以有效调理痰湿体质，帮助改善脾胃功能，促进湿气的排泄和气血的流畅。',
        '痰湿体质者应避免油腻、甜食及过量的高热量食物，以免增加湿气的生成。适宜食用具有健脾化湿、利水排痰的食物，如红豆、冬瓜、薏米、山药等。此外，保持清淡饮食，避免暴饮暴食，以减轻脾胃负担，防止湿气积聚。',
        '痰湿体质者禁忌食用油腻、甜食和高热量食物，这些食物会加重体内湿气。应避免久坐不动和剧烈运动，久坐会导致湿气滞留，而剧烈运动则可能引发脾胃负担过重。避免情绪压抑和焦虑，以免影响脾胃功能，进一步加重湿气积聚。');

-- 湿热体质信息
INSERT INTO tb_health_status (description, external_characteristics, intrinsic_characteristics, tendency_to_fall_ill,
                              psychological_conditioning, exercise_conditioning,
                              traditional_chinese_medicine_conditioning, dietary_conditioning, contraindication)
VALUES ('湿热体质',
        '面部油光、痘痘、皮肤油腻、容易出汗等。体型偏胖，尤其是腹部脂肪较多，给人一种沉重、湿气沉积的感觉。舌质红、舌苔黄腻，是湿热体质常见的舌象。',
        '脾胃功能失调，湿气滞留，消化吸收能力较差，常有食欲不振、恶心、腹胀等症状。体内热邪内生，容易导致便秘、口干、口苦等症状，且易出现口腔溃疡、尿黄等。湿热体质的人通常精神状态不佳，常感到沉重、困倦。',
        '湿热体质易患的疾病包括皮肤病（如湿疹、痤疮）、消化系统问题（如胃炎、肠炎）、泌尿系统疾病（如尿路感染）等。此外，湿热体质者也容易肥胖，且体重管理较困难。',
        '湿热体质的人常感到沉重和困倦，心理上容易出现焦虑、烦躁等情绪。保持情绪平稳，避免过度压力，可以有助于缓解湿热症状。',
        '湿热体质者应保持规律作息，避免熬夜。适度的运动有助于促进体内湿气的排出，如慢跑、快走、游泳等。此外，避免过度的劳累和长时间待在潮湿环境中。',
        '湿热体质的中医药调养主要以清热利湿为主，常用药物如茯苓、泽泻、黄连、龙胆草等，能够帮助排湿清热。针灸、拔罐等治疗方法也有助于调理湿热体质，改善体内湿气积聚的情况。',
        '湿热体质的人应避免油腻、辛辣、煎炸等食物，这些食物会加重湿热。宜选择清淡、利湿的食物，如绿豆、苦瓜、冬瓜、黄瓜等，能够帮助排湿清热。此外，少吃或避免摄入甜食和酒类，以免加重湿热的积累。',
        '湿热体质者应避免食用油腻、辛辣、煎炸、甜食和酒类，以免加重湿热。避免长时间处于潮湿环境，避免熬夜和过度劳累，避免情绪波动过大，保持心态平稳。这些禁忌有助于防止湿热症状加重。');

-- 血瘀体质信息
INSERT INTO tb_health_status(description, external_characteristics, intrinsic_characteristics, tendency_to_fall_ill,
                             psychological_conditioning, exercise_conditioning,
                             traditional_chinese_medicine_conditioning, dietary_conditioning, contraindication)
VALUES ('血瘀体质',
        '血瘀体质者的外在表现通常包括面色暗沉、皮肤无光泽、嘴唇或舌头呈紫暗色，舌质厚腻，脉象弦涩。局部可能出现肿块、淤血、水肿等症状，尤其在肢体上，容易感到麻木、冰冷、疼痛。血瘀体质还可能伴随瘀斑、色素沉积等皮肤问题。',
        '内在上，血瘀体质表现为气血运行不畅，常伴有胁肋疼痛、胸闷、头痛、月经不调等症状。由于血液循环障碍，血瘀体质可能导致内脏器官的功能失调，女性月经不调、痛经等症状较为常见，男性可能会出现精力下降、腰膝酸软等问题。长期血瘀还会影响脏腑功能，导致慢性疾病的发生。',
        '血瘀体质者容易患有与血液循环不畅相关的疾病，如高血压、冠心病、动脉硬化、静脉曲张等。女性特别容易出现月经不调、痛经、子宫肌瘤等妇科问题。由于血液滞留，容易导致淤血症状如瘀斑、血栓等，若不加以调理，可能引发更严重的疾病，如肿瘤、血栓等。',
        '血瘀体质者的情绪管理尤为重要，长期情绪压抑、愤怒或焦虑会加重气滞血瘀的状况。应学会调整情绪，避免情绪过度波动。可以通过冥想、深呼吸、放松训练等方式来减轻心理压力，改善气血流畅，有助于缓解血瘀症状。',
        '血瘀体质者应保持规律的作息，避免熬夜，保持充足的睡眠。合理的作息有助于气血的恢复与循环。运动方面，适度的有氧运动如散步、慢跑、太极等有助于促进气血流通，改善血液循环，减少血液淤滞。避免过于剧烈的运动，特别是过度消耗体力的运动，以免加重血瘀症状。',
        '中医药调养血瘀体质常用活血化瘀的方药，如丹参、川芎、赤芍等草药。此外，针灸、推拿等中医治疗方法能够帮助疏通经络，促进气血流通，缓解血瘀症状，改善整体体质。',
        '血瘀体质的饮食调养应以活血化瘀为主，适宜食用如丹参、当归、枸杞、红花等具有活血化瘀作用的食物。日常饮食可以多摄入黑枸杞、红枣、桂圆等补血益气的食物，同时应避免过多食用油腻、辛辣、刺激性食物，因为这些食物容易加重血瘀，阻碍气血流通。',
        '血瘀体质者禁忌长时间保持不动或久坐，避免过度劳累、熬夜等，以免加重血液滞留。避免暴饮暴食、食用过多油腻、辛辣和刺激性食物，这些食物会加重血瘀症状。此外，避免剧烈运动，过度消耗体力会导致血液循环更加不畅。情绪应保持平稳，避免情绪压抑、愤怒等情绪波动。');

-- 气郁体质信息
INSERT INTO tb_health_status(description, external_characteristics, intrinsic_characteristics, tendency_to_fall_ill,
                             psychological_conditioning, exercise_conditioning,
                             traditional_chinese_medicine_conditioning, dietary_conditioning, contraindication)
VALUES ('气郁体质',
        '气郁体质者通常表现为面色暗沉、眼神不明亮，给人一种压抑、无力的感觉。由于气滞，身体经常感到疲倦，易出现头痛、胸闷、胁肋胀痛等不适症状，甚至有时会影响到肌肉的紧张和疼痛。舌苔多为薄白，脉象多偏沉或弦。',
        '气郁体质的内在特征表现为情绪的郁结。气机郁滞导致内脏功能的失调，尤其是肝脏和脾胃功能的紊乱。气郁常表现为情绪不畅、易怒、焦虑，容易有失眠、心悸、食欲不振等症状。长期气郁会导致气血两虚，甚至诱发其他疾病，如高血压、胃肠道疾病等。',
        '气郁体质者容易出现消化系统问题，如食欲不振、胃胀、便秘等，气滞也可能导致月经不调、痛经等妇科问题。此外，气郁容易引发情绪问题，如抑郁症、焦虑症，甚至可能引发心血管疾病、高血压等。',
        '气郁体质的人常感到情绪不畅，容易焦虑和烦躁。心理调养的关键是保持心情舒畅，采取冥想、放松训练等方法减轻心理压力，避免情绪过度波动。建议通过自我调节、心理疏导或进行深呼吸等放松训练，帮助舒解压力。',
        '气郁体质的人应保持规律作息，避免长期熬夜、压力过大，保证足够的睡眠时间，帮助舒缓压力。适度的运动，如太极、瑜伽和散步等，有助于疏解郁气，促进气血流通，缓解身体不适。',
        '中医药调养方面，气郁体质的人常使用疏肝解郁的药物，如柴胡、香附、薄荷等，配合调和脾胃的方剂，有助于缓解气郁。针灸、推拿等治疗方法也能有效调节气机，疏通经络，缓解身体不适。',
        '气郁体质的人在饮食上应注重舒解肝气，常吃具有疏肝解郁作用的食物，如菊花、枸杞、陈皮、香橼等。同时，可以选择具有养胃健脾作用的食物，如大枣、山药、红枣等，以调理脾胃功能。避免过食辛辣、油腻和刺激性食物。',
        '禁忌长期压抑情绪、过度劳累和熬夜，避免高强度的体育活动，尤其是剧烈运动，以免加重气机郁滞。禁忌暴饮暴食，特别是过多食用辛辣、油腻和刺激性强的食物，这些都会加重肝气郁结。应避免情绪过度波动，避免陷入长期的焦虑或愤怒状态。');

-- 特禀体质信息（过敏体质信息）
INSERT INTO tb_health_status(description, external_characteristics, intrinsic_characteristics, tendency_to_fall_ill,
                             psychological_conditioning, exercise_conditioning,
                             traditional_chinese_medicine_conditioning, dietary_conditioning, contraindication)
VALUES ('特禀体质（过敏体质）',
        '过敏体质常表现为皮肤过敏反应，如荨麻疹、湿疹及反复皮肤瘙痒。呼吸系统表现为鼻塞、喷嚏及哮喘，眼睛易发红、流泪、痒等。面色偏暗，容易受环境变化引发过敏反应。',
        '免疫系统对常见物质过度反应，导致气血失调。脾胃功能弱，容易引起消化不良。气血流畅性差，内脏易受外界刺激，形成过敏症状。长时间过敏反应可能导致免疫力降低。',
        '过敏体质者易患过敏性鼻炎、哮喘、湿疹、荨麻疹等。食物过敏也常见，特别是对海鲜、牛奶、坚果等食物敏感。免疫系统的过度反应使其易受到环境过敏源的刺激。',
        '保持心理平衡，避免长期焦虑和压力，这些因素可加重过敏症状。通过冥想、深呼吸等放松技巧调节情绪，减轻心理压力，有助于增强免疫力，减缓过敏反应。',
        '保持规律作息，避免熬夜与过度劳累。适度运动（如散步、瑜伽）有助增强免疫力，改善气血流畅，但避免剧烈运动以防引发过敏症状。充分休息有助于免疫系统恢复。',
        '中医认为，过敏体质可通过增强脾胃、调理免疫系统来治疗。常用药物如黄芪、党参等，有助增强抵抗力。针灸、拔罐等疗法也有助于缓解过敏症状，改善体质。',
        '过敏体质者应避免食用易引起过敏的食物，如海鲜、牛奶、坚果等。饮食应清淡、易消化，多摄入新鲜蔬果、绿茶等富含抗氧化成分的食物，有助缓解过敏症状。',
        '过敏体质者禁忌接触过敏源，如花粉、尘螨、某些食物等。避免过度疲劳和熬夜，避免极端气候环境刺激，避免情绪波动过大，以免加重过敏症状。禁忌过度依赖药物，特别是抗过敏药物。');


-- 创建角色表
CREATE TABLE IF NOT EXISTS tb_role
(
    id          BIGINT UNSIGNED AUTO_INCREMENT PRIMARY KEY COMMENT '角色id',
    name        VARCHAR(20)     NOT NULL COMMENT '角色名',
    role_key    VARCHAR(100)    NOT NULL COMMENT '角色权限字符串',
    create_by   BIGINT UNSIGNED NOT NULL COMMENT '创建人',
    create_time DATETIME        NOT NULL COMMENT '创建时间',
    update_by   BIGINT UNSIGNED NOT NULL COMMENT '更新人',
    update_time DATETIME        NOT NULL COMMENT '更新时间',
    remark      VARCHAR(100)    NOT NULL COMMENT '备注'
) ENGINE = InnoDB
  AUTO_INCREMENT = 1
  DEFAULT CHARSET = utf8mb4 COMMENT ='角色表';

-- 角色信息
INSERT INTO tb_role (name, role_key, create_by, create_time, update_by, update_time, remark)
VALUES ('家庭用户', 'FAMILY', 1, NOW(), 1, NOW(), '家庭角色'),
       ('医生用户', 'DOCTOR', 1, NOW(), 1, NOW(), '医生角色'),
       ('管理员', 'ADMIN', 1, NOW(), 1, NOW(), '管理员角色');


-- 创建菜单表
CREATE TABLE IF NOT EXISTS tb_menu
(
    id          BIGINT UNSIGNED AUTO_INCREMENT PRIMARY KEY COMMENT '菜单id',
    menu_name   VARCHAR(20)     NOT NULL COMMENT '菜单名',
    permission  VARCHAR(100)    NOT NULL COMMENT '权限标识',
    create_by   BIGINT UNSIGNED NOT NULL COMMENT '创建人',
    create_time DATETIME        NOT NULL COMMENT '创建时间',
    update_by   BIGINT UNSIGNED NOT NULL COMMENT '更新人',
    update_time DATETIME        NOT NULL COMMENT '更新时间',
    remark      VARCHAR(100)    NOT NULL COMMENT '备注'
) ENGINE = InnoDB
  AUTO_INCREMENT = 1
  DEFAULT CHARSET = utf8mb4 COMMENT ='菜单表';

-- 权限信息
INSERT INTO tb_menu (menu_name, permission, create_by, create_time, update_by, update_time, remark)
VALUES ('用户管理', 'TCM:USER', 1, NOW(), 1, NOW(), '用户管理权限'),
       ('家庭管理', 'TCM:FAMILY', 1, NOW(), 1, NOW(), '家庭管理权限'),
       ('医生管理', 'TCM:DOCTOR', 1, NOW(), 1, NOW(), '医生管理权限'),
       ('管理员管理', 'TCM:ADMIN', 1, NOW(), 1, NOW(), '管理员管理权限');


-- 创建用户角色表
CREATE TABLE IF NOT EXISTS tb_user_role
(
    user_id BIGINT UNSIGNED NOT NULL COMMENT '用户id',
    role_id BIGINT UNSIGNED NOT NULL COMMENT '角色id',
    PRIMARY KEY (user_id, role_id) COMMENT '复合主键',
    CONSTRAINT fk_user_id_on_user_role FOREIGN KEY (user_id) REFERENCES tb_user (id),
    CONSTRAINT fk_role_id_on_user_role FOREIGN KEY (role_id) REFERENCES tb_role (id)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4 COMMENT '用户角色表';

-- 外键索引
CREATE INDEX idx_user_id_on_user_role ON tb_user_role (user_id);

-- 外键索引
CREATE INDEX idx_role_id_on_user_role ON tb_user_role (role_id);

-- 用户角色信息
INSERT INTO tb_user_role (user_id, role_id)
VALUES (1, 1), -- 用户A是家庭用户
       (2, 1), -- 用户B是家庭用户
       (3, 2);
-- 用户C是医生用户

-- 创建角色权限表
CREATE TABLE IF NOT EXISTS tb_role_menu
(
    role_id BIGINT UNSIGNED NOT NULL COMMENT '角色id',
    menu_id BIGINT UNSIGNED NOT NULL COMMENT '菜单id',
    PRIMARY KEY (role_id, menu_id) COMMENT '复合主键',
    CONSTRAINT fk_role_id_on_role_menu FOREIGN KEY (role_id) REFERENCES tb_role (id),
    CONSTRAINT fk_menu_id_on_role_menu FOREIGN KEY (menu_id) REFERENCES tb_menu (id)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4 COMMENT '角色权限表';

-- 外键索引
CREATE INDEX idx_role_id_on_role_menu ON tb_role_menu (role_id);

-- 外键索引
CREATE INDEX idx_menu_id_on_role_menu ON tb_role_menu (menu_id);

-- 角色权限信息
INSERT INTO tb_role_menu (role_id, menu_id)
VALUES (1, 1), -- 家庭用户角色拥有用户管理权限
       (1, 2), -- 家庭用户角色拥有家庭管理权限
       (2, 1), -- 医生角色拥有用户管理权限
       (2, 3), -- 医生角色拥有医生管理权限
       (3, 1), -- 管理员角色拥有用户管理权限
       (3, 4);
-- 管理员角色拥有管理员管理权限


-- 创建操作日志表
CREATE TABLE tb_operation_log
(
    id               BIGINT UNSIGNED AUTO_INCREMENT PRIMARY KEY COMMENT '操作日志id',
    user_id          BIGINT UNSIGNED COMMENT '用户id',
    ip_address       VARCHAR(45)  NOT NULL COMMENT '用户ip地址',
    request_url      VARCHAR(100) NOT NULL COMMENT '访问接口路径',
    operation        VARCHAR(100) NOT NULL COMMENT '操作描述',
    request_params   TEXT         NOT NULL COMMENT '请求参数',
    operation_status BOOLEAN      NOT NULL COMMENT '操作状态',
    error_message    TEXT COMMENT '错误信息',
    create_time      DATETIME     NOT NULL COMMENT '操作时间'
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4 COMMENT '操作日志表';


-- 创建用户反馈表
CREATE TABLE IF NOT EXISTS tb_user_feedback
(
    id           BIGINT UNSIGNED AUTO_INCREMENT PRIMARY KEY COMMENT '用户反馈id',
    user_id      BIGINT UNSIGNED NOT NULL COMMENT '用户id',
    type         VARCHAR(100)    NOT NULL COMMENT '类型',
    content      TEXT            NOT NULL COMMENT '内容',
    phone_number VARCHAR(11)     NOT NULL COMMENT '联系电话',
    delete_flag  BOOLEAN         NOT NULL COMMENT '是否删除',
    create_time  DATETIME        NOT NULL COMMENT '反馈时间',
    CONSTRAINT fk_user_id_on_user_feedback FOREIGN KEY (user_id) REFERENCES tb_user (id)
) ENGINE = InnoDB
  AUTO_INCREMENT = 1
  DEFAULT CHARSET = utf8mb4 COMMENT ='用户反馈表';

-- 外键索引
CREATE INDEX idx_user_id_on_user_feedback ON tb_user_feedback (user_id);

-- 插入用户反馈信息
INSERT INTO tb_user_feedback (user_id, type, content, phone_number, delete_flag, create_time)
VALUES (1, '系统问题', '登录时经常出现卡顿，希望优化系统性能。', '13800138000', FALSE, NOW()),
       (2, '服务建议', '希望增加在线咨询的功能，方便用户随时咨询医生。', '13900139000', FALSE, NOW()),
       (3, '投诉', '医生聊天界面可以优化', '13700137000', FALSE, NOW());


-- 创建医生简历表
CREATE TABLE IF NOT EXISTS tb_doctor_profile
(
    id              BIGINT UNSIGNED AUTO_INCREMENT PRIMARY KEY COMMENT '医生简历id',
    doctor_id       BIGINT UNSIGNED UNIQUE NOT NULL COMMENT '医生id',
    title           VARCHAR(100)           NOT NULL COMMENT '职称',
    introduction    TEXT                   NOT NULL COMMENT '简介',
    specialty       VARCHAR(100)           NOT NULL COMMENT '专业领域',
    hospital        VARCHAR(100)           NOT NULL COMMENT '所属医院',
    work_experience INT UNSIGNED           NOT NULL COMMENT '工作年限',
    education       VARCHAR(100)           NOT NULL COMMENT '教育背景',
    update_time     DATETIME               NOT NULL COMMENT '修改时间',
    create_time     DATETIME               NOT NULL COMMENT '修改时间',
    CONSTRAINT fk_docker_id_on_doctor_profile FOREIGN KEY (doctor_id) REFERENCES tb_user (id)
) ENGINE = InnoDB
  AUTO_INCREMENT = 1
  DEFAULT CHARSET = utf8mb4 COMMENT ='医生简历表';

-- 外键索引
CREATE INDEX idx_doctor_id_on_doctor_profile ON tb_doctor_profile (doctor_id);

-- 插入医生用户C的简历信息
INSERT INTO tb_doctor_profile (doctor_id, title, introduction, specialty, hospital, work_experience, education,
                               update_time, create_time)
VALUES (3, '主任医师', '拥有丰富的临床经验，擅长口腔疾病的诊断与治疗。', '口腔医学', '北京协和医院', 15,
        '北京大学医学部', NOW(), NOW());


-- 创建医生诊断建议表
CREATE TABLE IF NOT EXISTS tb_doctor_advice
(
    id          BIGINT UNSIGNED AUTO_INCREMENT PRIMARY KEY COMMENT '诊断建议id',
    doctor_id   BIGINT UNSIGNED NOT NULL COMMENT '医生id',
    record_id   BIGINT UNSIGNED NOT NULL COMMENT '诊断记录id',
    advice      TEXT            NOT NULL COMMENT '医生建议',
    delete_flag BOOLEAN         NOT NULL COMMENT '是否删除',
    update_time DATETIME        NOT NULL COMMENT '更新时间',
    create_time DATETIME        NOT NULL COMMENT '建议时间',
    CONSTRAINT fk_doctor_id_on_doctor_advice FOREIGN KEY (doctor_id) REFERENCES tb_user (id),
    CONSTRAINT fk_record_id_on_doctor_advice FOREIGN KEY (record_id) REFERENCES tb_record (id)
) ENGINE = InnoDB
  AUTO_INCREMENT = 1
  DEFAULT CHARSET = utf8mb4 COMMENT ='医生诊断建议表';

-- 外键索引
CREATE INDEX idx_doctor_id_on_doctor_advice ON tb_doctor_advice (doctor_id);

-- 外键索引
CREATE INDEX idx_record_id_on_doctor_advice ON tb_doctor_advice (record_id);

-- 插入对用户A成员诊断记录的建议
INSERT INTO tb_doctor_advice (doctor_id, record_id, advice, delete_flag, update_time, create_time)
VALUES (3, 1, '建议患者定期进行血压监测，并保持低盐饮食。', FALSE, NOW(), NOW()),
       (3, 2, '孩子需要多休息，避免剧烈运动，按时服药。', FALSE, NOW(), NOW()),
       (3, 3, '建议患者进行户外运动康复训练，避免过度负重。', FALSE, NOW(), NOW());

-- 插入对用户B成员诊断记录的建议
INSERT INTO tb_doctor_advice (doctor_id, record_id, advice, delete_flag, update_time, create_time)
VALUES (3, 10, '建议患者每天进行30分钟的有氧运动，如快走或游泳，以改善身心健康。', FALSE, NOW(), NOW()),
       (3, 11, '孩子需要增加蔬菜和水果的摄入量，避免高糖和高脂肪食物。', FALSE, NOW(), NOW()),
       (3, 12, '建议患者每周进行两次物理治疗，以帮助恢复脾胃功能。', FALSE, NOW(), NOW());


-- 创建用户评价表
CREATE TABLE IF NOT EXISTS tb_user_review
(
    id          BIGINT UNSIGNED AUTO_INCREMENT PRIMARY KEY COMMENT '用户评价id',
    user_id     BIGINT UNSIGNED NOT NULL COMMENT '用户id',
    doctor_id   BIGINT UNSIGNED NOT NULL COMMENT '医生id',
    rating      INT UNSIGNED    NOT NULL COMMENT '用户评分',
    comment     TEXT COMMENT '用户评价内容',
    create_time DATETIME        NOT NULL COMMENT '评价时间',
    CONSTRAINT fk_user_id_on_user_review FOREIGN KEY (user_id) REFERENCES tb_user (id),
    CONSTRAINT fk_doctor_id_on_user_review FOREIGN KEY (doctor_id) REFERENCES tb_user (id)
) ENGINE = InnoDB
  AUTO_INCREMENT = 1
  DEFAULT CHARSET = utf8mb4 COMMENT ='用户评价表';

-- 外键索引
CREATE INDEX idx_user_id_on_user_review ON tb_user_review (user_id);

-- 外键索引
CREATE INDEX idx_doctor_id_on_user_review ON tb_user_review (doctor_id);

-- 插入对医生用户C的评价信息
INSERT INTO tb_user_review (user_id, doctor_id, rating, comment, create_time)
VALUES (1, 3, 5, '医生非常专业，诊断准确，服务态度也很好。', NOW()),
       (2, 3, 4, '医生很有耐心，解答问题详细，但等待时间稍长。', NOW());


-- 创建聊天主题表
CREATE TABLE IF NOT EXISTS tb_chat_topic
(
    id          BIGINT UNSIGNED AUTO_INCREMENT PRIMARY KEY COMMENT '聊天主题表',
    user_id     BIGINT UNSIGNED NOT NULL COMMENT '用户id',
    doctor_id   BIGINT UNSIGNED NOT NULL COMMENT '医生id',
    member_id   BIGINT UNSIGNED NOT NULL COMMENT '成员id',
    record_id   BIGINT UNSIGNED NOT NULL COMMENT '诊断记录id',
    delete_flag BOOLEAN         NOT NULL COMMENT '是否删除',
    create_time DATETIME        NOT NULL COMMENT '创建时间',
    CONSTRAINT fk_user_id_on_chat_topic FOREIGN KEY (user_id) REFERENCES tb_user (id),
    CONSTRAINT fk_doctor_id_on_chat_topic FOREIGN KEY (doctor_id) REFERENCES tb_user (id),
    CONSTRAINT fk_member_id_on_chat_topic FOREIGN KEY (member_id) REFERENCES tb_member (id),
    CONSTRAINT fk_record_id_on_chat_topic FOREIGN KEY (record_id) REFERENCES tb_record (id)
) ENGINE = InnoDB
  AUTO_INCREMENT = 1
  DEFAULT CHARSET = utf8mb4 COMMENT ='聊天主题表';

-- 外键索引
CREATE INDEX idx_user_id_on_chat_topic ON tb_chat_topic (user_id);

-- 外键索引
CREATE INDEX idx_doctor_id_on_chat_topic ON tb_chat_topic (doctor_id);

-- 外键索引
CREATE INDEX idx_member_id_on_chat_topic ON tb_chat_topic (member_id);

-- 外键索引
CREATE INDEX idx_record_id_on_chat_topic ON tb_chat_topic (record_id);

-- 插入成员A1与医生C的聊天主题
INSERT INTO tb_chat_topic (user_id, doctor_id, member_id, record_id, delete_flag, create_time)
VALUES (1, 3, 1, 1, FALSE, NOW());


-- 创建聊天记录表
CREATE TABLE IF NOT EXISTS tb_chat_message
(
    id          BIGINT UNSIGNED AUTO_INCREMENT PRIMARY KEY COMMENT '聊天记录id',
    topic_id    BIGINT UNSIGNED NOT NULL COMMENT '主题id',
    user_id     BIGINT UNSIGNED NOT NULL COMMENT '发送者id',
    content     TEXT            NOT NULL COMMENT '消息内容',
    create_time DATETIME        NOT NULL COMMENT '发送时间',
    CONSTRAINT fk_topic_id_on_chat_message FOREIGN KEY (topic_id) REFERENCES tb_chat_topic (id),
    CONSTRAINT fk_user_id_on_chat_message FOREIGN KEY (user_id) REFERENCES tb_user (id)
) ENGINE = InnoDB
  AUTO_INCREMENT = 1
  DEFAULT CHARSET = utf8mb4 COMMENT ='聊天记录表';

-- 外键索引
CREATE INDEX idx_topic_id_on_chat_message ON tb_chat_message (topic_id);

-- 外键索引
CREATE INDEX idx_user_id_on_chat_message ON tb_chat_message (user_id);

-- 插入详细聊天数据
INSERT INTO tb_chat_message (topic_id, user_id, content, create_time)
VALUES (1, 1, '你好，医生！', NOW()),
       (1, 2, '你好，有什么可以帮助您的？', NOW());


-- 创建消息表
CREATE TABLE tb_message
(
    id          BIGINT UNSIGNED AUTO_INCREMENT PRIMARY KEY COMMENT '消息id',
    content     TEXT         NOT NULL COMMENT '消息内容',
    sender_name VARCHAR(100) NOT NULL COMMENT '发送者',
    delete_flag BOOLEAN      NOT NULL COMMENT '是否删除',
    create_time DATETIME     NOT NULL COMMENT '发送时间'
) ENGINE = InnoDB
  AUTO_INCREMENT = 1
  DEFAULT CHARSET = utf8mb4 COMMENT ='消息表';

INSERT INTO tb_message (content, sender_name, delete_flag, create_time)
VALUES ('系统通知：您的预约已确认。', '系统消息', FALSE, NOW()),
       ('提醒：请按时参加明天的体检。', '系统消息', FALSE, NOW()),
       ('欢迎使用我们的服务！', '系统消息', FALSE, NOW());

#
# DELETE FROM tb_message;
#
# DELETE FROM tb_chat_message;
#
# DELETE FROM tb_chat_topic;
#
# DELETE FROM tb_doctor_advice;
#
# DELETE FROM tb_doctor_profile;
#
# DELETE FROM tb_user_review;
#
# DELETE FROM tb_user_feedback;
#
# DELETE FROM tb_role_menu;
#
# DELETE FROM tb_user_role;
#
# DELETE FROM tb_menu;
#
# DELETE FROM tb_role;
#
# DELETE FROM tb_record;
#
# DELETE FROM tb_member;
#
# DELETE FROM tb_user;