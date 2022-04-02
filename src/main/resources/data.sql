-- 채널명
INSERT INTO TBL_CHANNEL_INFO (name, desc, cdtm, udtm) VALUES
    ('고길동TV', '테스트', NOW(), NOW());

-- 크리에이터
INSERT INTO TBL_CREATOR_INFO (name, rs, uid, channel, cdtm, udtm) VALUES
    ('고길동', 0.5, 'gd_go', 1, NOW(), NOW());
INSERT INTO TBL_CREATOR_INFO (name, rs, uid, channel, cdtm, udtm) VALUES
    ('둘리', 0.4, 'dulri', 1, NOW(), NOW());

-- 수익금
INSERT INTO TBL_REVENUE (channel, profit, date, cdtm) VALUES
    (1, 500000, '2022-03-10', NOW());
INSERT INTO TBL_REVENUE (channel, profit, date, cdtm) VALUES
    (1, 1000000, '2022-03-10', NOW());
INSERT INTO TBL_REVENUE (channel, profit, date, cdtm) VALUES
    (1, 10000, '2022-04-01', NOW());
INSERT INTO TBL_REVENUE (channel, profit, date, cdtm) VALUES
    (1, 10000, '2022-04-01', NOW());