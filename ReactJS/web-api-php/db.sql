CREATE DATABASE IF NOT EXISTS `web_api_php_MD18101`;

USE `web_api_php_MD18101`;

-- Path: web-api-php/db.sql

CREATE TABLE
    IF NOT EXISTS `users` (
        `id` int(11) NOT NULL AUTO_INCREMENT,
        `name` nvarchar(255) NOT NULL,
        `email` varchar(255) NOT NULL,
        `password` varchar(255) NOT NULL,
        `phone` varchar(255) NOT NULL,
        `address` varchar(255) NOT NULL,
        `role` varchar(255) NOT NULL,
        `created_at` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
        `deleted` tinyint(1) NOT NULL DEFAULT 0,
        `deleted_at` datetime NULL DEFAULT CURRENT_TIMESTAMP,
        PRIMARY KEY (`id`)
    ) ENGINE = InnoDB AUTO_INCREMENT = 2 DEFAULT CHARSET = utf8;

CREATE TABLE
    IF NOT EXISTS `topics` (
        `id` int(11) NOT NULL AUTO_INCREMENT,
        `name` nvarchar(255) NOT NULL,
        `description` nvarchar(255) NOT NULL,
        `created_at` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
        `deleted` tinyint(1) NOT NULL DEFAULT 0,
        `deleted_at` datetime NULL DEFAULT CURRENT_TIMESTAMP,
        PRIMARY KEY (`id`)
    ) ENGINE = InnoDB AUTO_INCREMENT = 2 DEFAULT CHARSET = utf8;

CREATE TABLE
    IF NOT EXISTS `news` (
        `id` int(11) NOT NULL AUTO_INCREMENT,
        `title` nvarchar(255) NOT NULL,
        `content` nvarchar(255) NOT NULL,
        `image` varchar(255) NULL,
        `created_at` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
        `updated_at` datetime NOT NULL DEFAULT current_timestamp() ON UPDATE current_timestamp(),
        `created_by` int(11) NOT NULL,
        `updated_by` int(11) NOT NULL,
        `topic_id` int(11) NOT NULL,
        `user_id` int(11) NOT NULL,
        `deleted` tinyint(1) NOT NULL DEFAULT 0,
        `deleted_at` datetime NULL DEFAULT CURRENT_TIMESTAMP,
        PRIMARY KEY (`id`),
        FOREIGN KEY (`topic_id`) REFERENCES `topics`(`id`),
        FOREIGN KEY (`user_id`) REFERENCES `users`(`id`)
    ) ENGINE = InnoDB AUTO_INCREMENT = 2 DEFAULT CHARSET = utf8;

CREATE TABLE
    IF NOT EXISTS `password_resets` (
        `id` int(11) NOT NULL AUTO_INCREMENT,
        `email` varchar(255) NOT NULL,
        `token` varchar(255) NOT NULL,
        `created_at` timestamp NOT NULL DEFAULT current_timestamp(),
        `available` tinyint(1) NOT NULL DEFAULT 1,
        PRIMARY KEY (`id`)
    ) ENGINE = InnoDB AUTO_INCREMENT = 2 DEFAULT CHARSET = utf8;

-- Inserting data for table `users`

INSERT INTO
    `users` (
        `id`,
        `name`,
        `email`,
        `password`,
        `phone`,
        `address`,
        `role`,
        `created_at`
    )
VALUES (
        1,
        'Lun',
        'email1@gmail.com',
        '123456',
        '0123456789',
        'address1',
        'admin',
        '2021-04-01 00:00:00'
    );

INSERT INTO
    `users` (
        `id`,
        `name`,
        `email`,
        `password`,
        `phone`,
        `address`,
        `role`,
        `created_at`
    )
VALUES (
        2,
        'Teo',
        'email2@gmail.com',
        '123456',
        '0123456789',
        'address2',
        'admin',
        '2021-04-01 00:00:00'
    );

INSERT INTO
    `users` (
        `id`,
        `name`,
        `email`,
        `password`,
        `phone`,
        `address`,
        `role`,
        `created_at`
    )
VALUES (
        3,
        'user2',
        'email3@gmail.com',
        '123456',
        '0123456789',
        'address3',
        'user',
        '2021-04-01 00:00:00'
    );

-- Insert data for table `topics`

INSERT INTO
    `topics` (
        `id`,
        `name`,
        `description`,
        `created_at`
    )
VALUES (
        1,
        'topic1',
        'description1',
        '2021-04-01 00:00:00'
    );

INSERT INTO
    `topics` (
        `id`,
        `name`,
        `description`,
        `created_at`
    )
VALUES (
        2,
        'topic2',
        'description2',
        '2021-04-01 00:00:00'
    );

INSERT INTO
    `topics` (
        `id`,
        `name`,
        `description`,
        `created_at`
    )
VALUES (
        3,
        'topic3',
        'description3',
        '2021-04-01 00:00:00'
    );

-- Inserting data for table `news`

INSERT INTO
    `news` (
        `title`,
        `content`,
        `image`,
        `created_at`,
        `updated_at`,
        `created_by`,
        `updated_by`,
        `topic_id`,
        `user_id`
    )
VALUES (
        'Tin tức số 1',
        'Nội dung tin tức số 1',
        NULL,
        '2023-12-05 08:00:00',
        NULL,
        1,
        NULL,
        1,
        1
    ), (
        'Cập nhật mới nhất 2',
        'Nội dung cập nhật mới nhất 2',
        NULL,
        '2023-12-04 09:30:00',
        NULL,
        2,
        NULL,
        2,
        2
    ), (
        'Câu chuyện thú vị 3',
        'Nội dung câu chuyện thú vị 3',
        NULL,
        '2023-12-03 11:45:00',
        NULL,
        1,
        NULL,
        1,
        3
    ), (
        'Tin tức địa phương 4',
        'Nội dung tin tức địa phương 4',
        NULL,
        '2023-12-02 14:20:00',
        NULL,
        3,
        NULL,
        3,
        1
    ), (
        'Cập nhật công nghệ 5',
        'Nội dung cập nhật công nghệ 5',
        NULL,
        '2023-12-01 16:10:00',
        NULL,
        2,
        NULL,
        4,
        4
    ), (
        'Báo cáo sức khỏe 6',
        'Nội dung báo cáo sức khỏe 6',
        NULL,
        '2023-11-30 17:55:00',
        NULL,
        1,
        NULL,
        2,
        5
    ), (
        'Tin tức du lịch 7',
        'Nội dung tin tức du lịch 7',
        NULL,
        '2023-11-29 20:25:00',
        NULL,
        3,
        NULL,
        5,
        3
    ), (
        'Cập nhật thể thao 8',
        'Nội dung cập nhật thể thao 8',
        NULL,
        '2023-11-28 22:15:00',
        NULL,
        2,
        NULL,
        6,
        2
    ), (
        'Báo cáo tài chính 9',
        'Nội dung báo cáo tài chính 9',
        NULL,
        '2023-11-27 09:00:00',
        NULL,
        1,
        NULL,
        4,
        1
    ), (
        'Tin tức giải trí 10',
        'Nội dung tin tức giải trí 10',
        NULL,
        '2023-11-26 10:45:00',
        NULL,
        3,
        NULL,
        7,
        4
    );

DROP DATABASE IF EXISTS `web_api_php_MD18101`;

DROP TABLE IF EXISTS `users`;

DROP TABLE IF EXISTS `topics`;

SELECT * FROM `users`;

SELECT * FROM `topics`;

SELECT * FROM `news`;

SELECT
    n.id,
    n.title,
    n.content
FROM `news` n
    INNER JOIN `topics` t ON n.topic_id = t.id
WHERE t.name = 'topic1'
LIMIT 100;