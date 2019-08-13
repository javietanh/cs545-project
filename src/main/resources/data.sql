INSERT INTO user (id, first_name, last_name, email, password, phone, address, role, register_date)
    VALUES (1, 'First', 'Buyer', 'buyer@shopping.com', '$2a$10$b.9CsDYMBdFIMB5ja.lg0.3/OHFiv5kMn7yR.FKCZY3JScMRPvE.G', '123-456-7890', '1000 N 4th St, Fairfield, IA', 'BUYER', now());

INSERT INTO user (id, first_name, last_name, email, password, phone, address, role, register_date)
VALUES (2, 'First', 'Seller', 'seller@shopping.com', '$2a$10$13wR9hYkIwBP0WIT525/XO23UfTvtjUKjbHCLlwAzYNzF3IkBlZRy', '123-456-7890', '1000 N 4th St, Fairfield, IA', 'SELLER', now());

INSERT INTO user (id, first_name, last_name, email, password, phone, address, role, register_date, avatar)
VALUES (3, 'Shopping', 'Admin', 'admin@shopping.com', '$2a$10$HM.MYd2XpX7VEsNoZMTUmer6X7MZ7/semLLQ/bDqdsrbhn5EeRO3y', '000-000-0000', '1000 N 4th St, Fairfield, IA', 'ADMIN', now(), '/img/avatar/admin.png');


INSERT INTO buyer (id, points, user_id) VALUES (3, 0, 1);

INSERT INTO seller (id, name, description, user_id, status)
VALUES (4, 'Phoenix', 'Phoenix offers fashion and quality at the best price', 2, 'APPROVED');

INSERT INTO product (id, name, description, price, available, seller_id)
VALUES (5, 'Dress with Tie Belt',
        'V-neck, knee-length dress in airy chiffon with details on shoulders. Cap sleeves, buttons at front, and elastication at back of waist. Attached tie belt. Satin lining',
        '34.99', true, 4);

INSERT INTO product (id, name, description, price, available, seller_id)
VALUES (6, 'Bib Overall Dress',
        'Short, gently fitted bib overall dress in woven fabric. Buttons at front, wide, adjustable shoulder straps, and a seam at waist',
        '34.99', true, 4);

INSERT INTO product (id, name, description, price, available, seller_id)
VALUES (7, 'Skinny Regular Ankle Jeans',
        'Ankle-length jeans in washed stretch denim with a regular waist. Mock front pockets, regular back pockets, and skinny legs',
        '9.99', true, 4);

INSERT INTO cart_item (id, product_id, quantity, buyer_id) VALUES (8, 5, 1, 3);

INSERT INTO cart_item (id, product_id, quantity, buyer_id) VALUES (9, 7, 1, 3);

INSERT INTO message (id, content, received_date, read, user_id)
VALUES (10, 'From Phoenix shop: New product is added', LocalDateTime.now(), false, 1);

INSERT INTO message (id, content, received_date, read, user_id)
VALUES (11, 'From Phoenix shop: New product is added', LocalDateTime.now(), false, 1);

INSERT INTO advert (id, title, description , image, url)
VALUES (1 ,'pirula', 'pirulita pechi', '/img/adverts/8423f0d6-e901-4ded-90aa-a91538ff4af4.jpeg', 'pirula.com');

INSERT INTO advert (id, title, description , image, url)
VALUES (2, 'pirula2', 'pirulita pechi2', '/img/adverts/84ce436c-854a-4440-9d35-0df93a8e05b0.jpg', 'pirula2.com');
