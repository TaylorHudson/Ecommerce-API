CREATE TABLE IF NOT EXISTS tb_email (
   `id` BIGINT AUTO_INCREMENT PRIMARY KEY,
   `from_email` VARCHAR(150) NOT NULL,
   `from_name` VARCHAR(150) NOT NULL,
   `reply_to` VARCHAR(150) NOT NULL,
   `to_email` VARCHAR(150) NOT NULL,
   `subject` VARCHAR(255) NOT NULL,
   `body` TEXT NOT NULL,
   `content_type` VARCHAR(150) NOT NULL
);