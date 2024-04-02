CREATE TABLE IF NOT EXISTS `users`
(
    `id` INTEGER PRIMARY KEY AUTOINCREMENT,
    `player_uuid` TEXT,
    `username` TEXT,
    `LogOutLocation` TEXT,
    `DeathLocation` TEXT,
    `PreviousLocation` TEXT,
    `Homes` TEXT,
    `LastReceipt` TEXT,
    `Vanished` TEXT
)
