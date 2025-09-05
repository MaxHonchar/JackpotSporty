CREATE TABLE jackpot
(
    id                      BIGINT PRIMARY KEY AUTO_INCREMENT,
    total_amount            DECIMAL,
    configuration_id        BIGINT,
    reward_configuration_id BIGINT NOT NULL
);

CREATE TABLE jackpot_configuration
(
    id               BIGINT PRIMARY KEY AUTO_INCREMENT,
    contributor_type VARCHAR(255),
    initial_pool     DECIMAL,
    fixed_percentage DECIMAL,
    min_percentage   DECIMAL,
    max_percentage   DECIMAL,
    threshold_amount DECIMAL
);

CREATE TABLE jackpot_contribution
(
    id                     BIGINT PRIMARY KEY AUTO_INCREMENT,
    jackpot_id             BIGINT NOT NULL,
    user_id                BIGINT NOT NULL,
    bet_id                 BIGINT NOT NULL,
    stake_amount           DECIMAL,
    contribution_amount    DECIMAL,
    current_jackpot_amount DECIMAL,
    created_at             timestamp without time zone
);

CREATE TABLE jackpot_reward
(
    id                    BIGINT PRIMARY KEY AUTO_INCREMENT,
    jackpot_id            BIGINT NOT NULL,
    user_id               BIGINT NOT NULL,
    bet_id                BIGINT NOT NULL,
    jackpot_reward_amount DECIMAL,
    created_at            timestamp without time zone
);

CREATE TABLE jackpot_reward_configuration
(
    id                  BIGINT PRIMARY KEY AUTO_INCREMENT,
    contributor_type    VARCHAR(255),
    fixed_chance_reward DECIMAL,
    min_chance_reward   DECIMAL,
    max_chance_reward   DECIMAL,
    threshold_limit     DECIMAL
);

INSERT INTO jackpot_configuration(id, contributor_type, initial_pool, fixed_percentage, min_percentage,
                                  max_percentage, threshold_amount)
VALUES (1, 'FIXED', 1000, 10, null, null, null),
       (2, 'VARIABLE', 1000, null, 5, 20, 100000);


INSERT INTO jackpot_reward_configuration(id, contributor_type, fixed_chance_reward, min_chance_reward,
                                         max_chance_reward, threshold_limit)
VALUES (1, 'FIXED', 15, null, null, null),
       (2, 'VARIABLE', null, 10, 30, 20);


INSERT INTO jackpot(id, total_amount, configuration_id, reward_configuration_id)
VALUES (1, 0, 1, 1),
       (2, 0, 2, 2);

