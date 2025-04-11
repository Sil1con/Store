DELIMITER $$
DROP PROCEDURE IF EXISTS findAllByCategory; 
CREATE PROCEDURE findAllByCategory(IN cat VARCHAR(50))
BEGIN
	SELECT p.product_id, p.product_name, p.descr, p.stock_amount, p.price, p.image, c.category
	FROM ((products p
	JOIN products_categories pc ON p.product_id = pc.product_id)
	JOIN category c ON pc.category_id = c.category_id)
	WHERE c.category = cat;
END$$
DELIMITER ;

DELIMITER $$
DROP PROCEDURE IF EXISTS findAllCartProducts;
CREATE PROCEDURE findAllCartProducts(IN id_cart INT)
BEGIN
 	SELECT cart_id, c.product_id, p.title, p.descr, c.product_amount, p.price, p.image
     FROM carts c
     JOIN products p ON c.product_id = p.product_id
     WHERE cart_id = id_cart;
 END$$
DELIMITER ;

DELIMITER $$
DROP PROCEDURE IF EXISTS findAllProductCategories;
CREATE PROCEDURE findAllProductCategories (IN pn VARCHAR(50))
BEGIN
	SELECT p.product_id, c.category_id, c.category
	FROM ((products p
	JOIN products_categories pc ON p.product_id = pc.product_id)
	JOIN category c ON pc.category_id = c.category_id)
	WHERE p.product_name = pn;
END$$
DELIMITER ;

DELIMITER $$
DROP PROCEDURE IF EXISTS makeDiscount;
CREATE PROCEDURE makeDiscount (IN percent INT, IN cat_id INT)
BEGIN
	SELECT p.product_id, p.product_name, p.price - p.price*percent/100
    FROM ((products p
    JOIN products_categories pc ON p.product_id = pc.product_id)
    JOIN category c ON pc.category_id = c.category_id)
    WHERE c.category_id = cat_id;
END$$
DELIMITER ;

DELIMITER $$
DROP PROCEDURE IF EXISTS updateUser;
CREATE PROCEDURE updateUser (IN id INT, IN fname VARCHAR(50), IN lname VARCHAR(50), IN email VARCHAR(255),  IN phone VARCHAR(15), IN user_password VARCHAR(255), IN privilege_id INT, IN salt VARCHAR(255))
BEGIN
 	UPDATE users
     SET users.fname = fname, users.lname = lname, users.email = email, users.user_password = user_password, users.privilege_id = privilege_id, users.salt = salt
     WHERE user_id = id;
 END$$
DELIMITER ;

DELIMITER \\
DROP PROCEDURE IF EXISTS soldProductsByMonth;
CREATE PROCEDURE soldProductsByMonth(IN month int)
BEGIN
	SELECT COUNT(op.product_id) AS products, SUM(op.product_amount) AS amount, SUM(unit_price*op.product_amount) AS revenue
	FROM products p
	JOIN order_products op ON p.product_id = op.product_id
    JOIN order_details od ON op.order_id = od.order_id
    WHERE MONTH(od.created_at) = month;
END \\
DELIMITER ;

DELIMITER \\
DROP PROCEDURE IF EXISTS mostSoldProductsByMonth;
CREATE PROCEDURE mostSoldProductsByMonth(IN month INT)
BEGIN
	SELECT p.product_id, SUM(op.product_amount) AS quantity, SUM(op.unit_price*op.product_amount) AS total_revenue
	FROM products p
	JOIN order_products op ON p.product_id = op.product_id
    JOIN order_details od ON op.order_id = od.order_id
    WHERE MONTH(od.created_at) = month
	GROUP BY p.product_id
	ORDER BY quantity DESC
    LIMIT 3;
END \\
DELIMITER ;

DELIMITER \\
DROP PROCEDURE IF EXISTS avgOrderValue;
CREATE PROCEDURE avgOrderValue()
BEGIN	
	SELECT COUNT(op.order_id), SUM(op.product_amount) AS quantity, SUM(unit_price*op.product_amount) AS revenue, SUM(op.unit_price*op.product_amount)/COUNT(op.order_id) AS average
	FROM order_products op;
END \\
DELIMITER ;