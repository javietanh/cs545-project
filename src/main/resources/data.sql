INSERT INTO user (id, first_name, last_name, email, password, phone, address, role, register_date, avatar)
VALUES (1, 'First', 'Buyer', 'buyer@shopping.com', '$2a$10$b.9CsDYMBdFIMB5ja.lg0.3/OHFiv5kMn7yR.FKCZY3JScMRPvE.G', '123-456-7890', '1000 N 4th St, Fairfield, IA', 'BUYER', now(), '/img/avatar/buyer.jpg');

INSERT INTO user (id, first_name, last_name, email, password, phone, address, role, register_date, avatar)
VALUES (2, 'First', 'Seller', 'seller@shopping.com', '$2a$10$13wR9hYkIwBP0WIT525/XO23UfTvtjUKjbHCLlwAzYNzF3IkBlZRy', '123-456-7890', '1000 N 4th St, Fairfield, IA', 'SELLER', now(), '/img/avatar/seller.png');

INSERT INTO user (id, first_name, last_name, email, password, phone, address, role, register_date, avatar)
VALUES (3, 'Shopping', 'Admin', 'admin@shopping.com', '$2a$10$HM.MYd2XpX7VEsNoZMTUmer6X7MZ7/semLLQ/bDqdsrbhn5EeRO3y', '000-000-0000', '1000 N 4th St, Fairfield, IA', 'ADMIN', now(), '/img/avatar/admin.png');

INSERT INTO user (id, first_name, last_name, email, password, phone, address, role, register_date)
VALUES (4, 'Second', 'Buyer', 'buyer2@shopping.com', '$2a$10$b.9CsDYMBdFIMB5ja.lg0.3/OHFiv5kMn7yR.FKCZY3JScMRPvE.G', '123-456-7890', '1000 N 4th St, Fairfield, IA', 'BUYER', now());

INSERT INTO user (id, first_name, last_name, email, password, phone, address, role, register_date)
VALUES (5, 'Third', 'Buyer', 'buyer3@shopping.com', '$2a$10$b.9CsDYMBdFIMB5ja.lg0.3/OHFiv5kMn7yR.FKCZY3JScMRPvE.G', '123-456-7890', '1000 N 4th St, Fairfield, IA', 'BUYER', now());

INSERT INTO user (id, first_name, last_name, email, password, phone, address, role, register_date)
VALUES (6, 'Second', 'Seller', 'seller2@shopping.com', '$2a$10$13wR9hYkIwBP0WIT525/XO23UfTvtjUKjbHCLlwAzYNzF3IkBlZRy', '123-456-7890', '1000 N 4th St, Fairfield, IA', 'SELLER', now());

INSERT INTO user (id, first_name, last_name, email, password, phone, address, role, register_date)
VALUES (7, 'Second', 'Seller', 'seller3@shopping.com', '$2a$10$13wR9hYkIwBP0WIT525/XO23UfTvtjUKjbHCLlwAzYNzF3IkBlZRy', '123-456-7890', '1000 N 4th St, Fairfield, IA', 'SELLER', now());

INSERT INTO buyer (id, points, user_id) VALUES (1, 0, 1);

INSERT INTO buyer (id, points, user_id) VALUES (2, 0, 4);

INSERT INTO buyer (id, points, user_id) VALUES (3, 0, 5);

INSERT INTO seller (id, name, description, user_id, status, picture)
VALUES (1, 'Phoenix', 'Phoenix offers fashion and quality at the best price', 2, 'APPROVED', '/img/shop/4a8ed458-a506-4e36-87f0-67b1614a2eeb.jpg');

INSERT INTO seller (id, name, description, user_id, status, picture)
VALUES (2, 'Blue Sand', 'Blue Sand offers fashion and quality at the best price', 6, 'PENDING', '/img/shop/bluesand-fashion-shop.jpg');

INSERT INTO seller (id, name, description, user_id, status, picture)
VALUES (3, 'Fashion Shop', 'Fashion Shop offers fashion and quality at the best price', 7, 'PENDING', '/img/shop/3D-Fashion-Shop-11-Model-Free-Download-3.jpg');

INSERT INTO following (buyer_id, seller_id)
VALUES (1, 1);

INSERT INTO following (buyer_id, seller_id)
VALUES (2, 1);

INSERT INTO following (buyer_id, seller_id)
VALUES (3, 1);

INSERT INTO category (id, name, description)
VALUES (1, 'Dresses', 'Dresses');

INSERT INTO category (id, name, description)
VALUES (2, 'Shirts', 'Shirts');

INSERT INTO category (id, name, description)
VALUES (3, 'Blazers', 'Blazers');

INSERT INTO category (id, name, description)
VALUES (4, 'Jeans', 'Jeans');

INSERT INTO category (id, name, description)
VALUES (5, 'Sweaters', 'Sweaters');

INSERT INTO category (id, name, description)
VALUES (6, 'Skirts', 'Skirts');

INSERT INTO category (id, name, description)
VALUES (7, 'Sportswear', 'Sportswear');

INSERT INTO category (id, name, description)
VALUES (8, 'Swimwear', 'Swimwear');

INSERT INTO category (id, name, description)
VALUES (9, 'Suits', 'Suits');

INSERT INTO category (id, name, description)
VALUES (10, 'Pants', 'Pants');

INSERT INTO category (id, name, description)
VALUES (11, 'Jackets', 'Jackets');

INSERT INTO product (id, name, description, price, available, image, category_id, seller_id)
VALUES (1, 'Dress with Tie Belt',
        'V-neck, knee-length dress in airy chiffon with details on shoulders. Cap sleeves, buttons at front, and elastication at back of waist. Attached tie belt. Satin lining',
        '34.99', 100, '/img/product/beltdress.jpg', 1, 1);

INSERT INTO product (id, name, description, price, available, image, category_id, seller_id)
VALUES (2, 'Bib Overall Dress',
        'Short, gently fitted bib overall dress in woven fabric. Buttons at front, wide, adjustable shoulder straps, and a seam at waist',
        '34.99', 100, '/img/product/overalldress.jpg', 1, 1);

INSERT INTO product (id, name, description, price, available, image, category_id, seller_id)
VALUES (3, 'Patterned Dress',
        'Calf-length dress in airy, woven fabric with a printed pattern. Jersey liner dress',
        '39.99', 100, '/img/product/patterneddress.jpg', 1, 1);

INSERT INTO product (id, name, description, price, available, image, category_id, seller_id)
VALUES (4, 'Pleated Wrap Dress',
        'Sleeveless, calf-length wrap dress in airy, woven plumeti fabric. Narrow, adjustable shoulder straps and seam below bust with ties',
        '79.99', 100, '/img/product/wrapdress.jpg', 1, 1);

INSERT INTO product (id, name, description, price, available, image, category_id, seller_id)
VALUES (5, 'Sleeveless Shirt Dress',
        'Sleeveless shirt dress in woven viscose fabric with a collar, buttons at front, and removable tie belt at waist. Gently rounded hem.',
        '17.99', 100, '/img/product/shirtdress.jpg', 1, 1);

INSERT INTO product (id, name, description, price, available, image, category_id, seller_id)
VALUES (6, 'Lace V-neck Dress',
        'Short, sleeveless dress in lace with scalloped edges. V-neck, adjustable, extra-narrow shoulder straps, and a concealed zip at back. Gently flared skirt',
        '59.99', 100, '/img/product/vneckdress.jpg', 1, 1);

INSERT INTO product (id, name, description, price, available, image, category_id, seller_id)
VALUES (7, 'Skinny Regular Ankle Jeans',
        'Ankle-length jeans in washed stretch denim with a regular waist. Mock front pockets, regular back pockets, and skinny legs',
        '9.99', 100, '/img/product/skinnyjeans.jpg', 4, 1);

INSERT INTO product (id, name, description, price, available, image, category_id, seller_id)
VALUES (8, 'Slim Jeans',
        '5-pocket jeans in washed denim with a regular waist, zip fly, and slim legs',
        '19.99', 100, '/img/product/slimjeans.jpg', 4, 1);

INSERT INTO product (id, name, description, price, available, image, category_id, seller_id)
VALUES (9, 'Regular Fit Henley Shirt',
        'Shirt in woven cotton-blend fabric. Regular Fit – classic fit with good room for movement and gently shaped waist for a comfortable, tailored silhouette',
        '24.99', 100, '/img/product/fitshirt.jpg', 2, 1);

INSERT INTO product (id, name, description, price, available, image, category_id, seller_id)
VALUES (10, 'Super Skinny Fit Blazer',
        'Single-breasted blazer in woven stretch fabric with narrow, notched lapels. Super skinny fit – slightly shorter style, shaped at chest, tapered sharply at waist, combined with narrow shoulders and sleeves for a completely tailored silhouette',
        '19.99', 100, '/img/product/fitblazer.jpg', 3, 1);

INSERT INTO cart_item (id, product_id, quantity, buyer_id) VALUES (1, 1, 1, 1);

INSERT INTO cart_item (id, product_id, quantity, buyer_id) VALUES (2, 2, 1, 1);

INSERT INTO cart_item (id, product_id, quantity, buyer_id) VALUES (3, 3, 1, 1);

INSERT INTO orders (id, total_amount, buyer_id, shipping_address, billing_address, payment_method, payment_info, status, ordered_date, using_points)
VALUES (1, '64.97', 1, '1000 N 4th St, Fairfield, IA', '1000 N 4th St, Fairfield, IA', 'DEBIT CARD', 'Paid by the card number XXXX XXXX XXXX 1234', 'NEW', now(), false);

INSERT INTO orders (id, total_amount, buyer_id, shipping_address, billing_address, payment_method, payment_info, status, ordered_date, using_points)
VALUES (2, '19.99', 2, '1000 N 4th St, Fairfield, IA', '1000 N 4th St, Fairfield, IA', 'CREDIT CARD', 'Paid by the card number XXXX XXXX XXXX 2345', 'COMPLETED', now(), false);

INSERT INTO orders (id, total_amount, buyer_id, shipping_address, billing_address, payment_method, payment_info, status, ordered_date, using_points)
VALUES (3, '19.99', 3, '1000 N 4th St, Fairfield, IA', '1000 N 4th St, Fairfield, IA', 'CREDIT CARD', 'Paid by the card number XXXX XXXX XXXX 3456', 'COMPLETED', now(), false);

INSERT INTO order_item (id, product_id, order_id, quantity, review, review_status, rating, order_status, review_date)
VALUES (1, 8, 1, 1, 'Nice jeans', 'APPROVED', 4, 'ORDERED', now());

INSERT INTO order_item (id, product_id, order_id, quantity, review, review_status, rating, order_status, review_date)
VALUES (2, 9, 1, 1, 'Nice shirt', 'PENDING', 4, 'ORDERED', now());

INSERT INTO order_item (id, product_id, order_id, quantity, review, review_status, rating, order_status, review_date)
VALUES (3, 10, 1, 1, 'Nice blazer', 'PENDING', 4, 'ORDERED', now());

INSERT INTO order_item (id, product_id, order_id, quantity, review, review_status, rating, order_status, review_date)
VALUES (4, 8, 2, 1, 'Too tight!!!', 'APPROVED', 1, 'DELIVERED', now());

INSERT INTO order_item (id, product_id, order_id, quantity, review, review_status, rating, order_status, review_date)
VALUES (5, 8, 3, 1, 'It does not look like the photo', 'APPROVED', 3, 'DELIVERED', now());

INSERT INTO message (id, content, received_date, read, user_id)
VALUES (1, 'From Phoenix shop: New product added', now(), false, 1);

INSERT INTO message (id, content, received_date, read, user_id)
VALUES (2, 'From Phoenix shop: New product added', now(), false, 1);

INSERT INTO advert (id, title, description , image, url)
VALUES (1 ,'banner1', 'long sleeves', '/img/adverts/5be8da29-cd51-4b56-8ef2-5e16a927027a.jpg', 'banner.com');

INSERT INTO advert (id, title, description , image, url)
VALUES (2, 'banner2', 'women clothing', '/img/adverts/5daab05d-dd51-43f8-9f36-b84fef1a3377.jpg', 'banner2.com');


