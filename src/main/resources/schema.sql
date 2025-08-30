CREATE TABLE IF NOT EXISTS players (
    id BIGINT UNSIGNED NOT NULL AUTO_INCREMENT,
    name VARCHAR(40) NOT NULL,
    email VARCHAR(40) NOT NULL,
    balance DECIMAL(15,2) NOT NULL,
    ranking_score BIGINT UNSIGNED DEFAULT 0,
    is_active TINYINT NOT NULL DEFAULT 1,
    registration_date TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    PRIMARY KEY (id),
    UNIQUE KEY player_unique (email)
    );

INSERT IGNORE INTO players (name,email,balance,ranking_score,is_active,registration_date) VALUES
    ('Alan Turin','alant@gmail.dev',100.00,0,1,'2025-07-27 23:13:22'),
    ('Albert Einstein','albert@gmail.dev',100.00,0,1,'2025-07-29 18:51:39'),
    ('Marie Curie','marie@gmail.dev',25.00,0,1,'2025-08-06 22:26:03'),
    ('Isaac Newton','isaac@gmail.dev',100.00,0,1,'2025-08-06 23:07:58'),
    ('Charles Darwin','charles@gmail.dev',50.00,0,1,'2025-08-07 00:14:04'),
    ('Louis Pasteur','xlouis@gmail.dev',90.00,0,1,'2025-08-07 00:15:44'),
    ('Ada Lovelace','ada@example.com',100.00,1,1,'2025-08-10 22:37:51'),
    ('Test Player 01','test01@gmail.dev',90.00,0,1,'2025-08-07 00:15:44'),
    ('Test Player 02','test02@gmail.dev',90.00,0,1,'2025-08-07 00:15:44');
