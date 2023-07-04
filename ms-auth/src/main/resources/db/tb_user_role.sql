CREATE TABLE IF NOT EXISTS tb_user_role (
   `user_id` BIGINT NOT NULL,
   `role_id` BIGINT NOT NULL,
   FOREIGN KEY (`user_id`) REFERENCES tb_user(id),
   FOREIGN KEY (`role_id`) REFERENCES tb_role(id)
);