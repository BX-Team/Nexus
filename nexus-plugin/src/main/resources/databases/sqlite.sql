CREATE TABLE IF NOT EXISTS `users`
(
    `id` INTEGER PRIMARY KEY AUTOINCREMENT,
    `player_uuid` TEXT UNIQUE,
    `username` TEXT,
    `PreviousLocation` TEXT,
    `Homes` TEXT,
    `LastReceipt` TEXT,
    `Vanished` TEXT
);

CREATE TABLE IF NOT EXISTS `warps`
(
    `id` INTEGER PRIMARY KEY AUTOINCREMENT,
    `warp_name` TEXT NOT NULL,
    `x` DOUBLE NOT NULL,
    `y` DOUBLE NOT NULL,
    `z` DOUBLE NOT NULL,
    `pitch` FLOAT NOT NULL,
    `yaw` FLOAT NOT NULL,
    `world` TEXT NOT NULL
);