CREATE TABLE IF NOT EXISTS tb_product_category (
   `product_id` BIGINT NOT NULL,
   `category_id` BIGINT NOT NULL,
   FOREIGN KEY (`product_id`) REFERENCES tb_product(id) ON DELETE CASCADE,
   FOREIGN KEY (`category_id`) REFERENCES tb_category(id)
);