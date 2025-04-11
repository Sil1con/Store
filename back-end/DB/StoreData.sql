USE project;

INSERT INTO privileges VALUES
(1, "SUPER_ADMIN"),
(2, "ADMIN"),
(3, "CUSTOMER");

INSERT INTO users VALUES
(1, "Super_admin", "Test1", "test1@gmail.com", "+123456", "$2a$10$rCE4g88rOJ5yWC9YLBRBeOMN/sJKijSvEPubgtmdrdhT1KpO8sWZm", 1, "$2a$10$rCE4g88rOJ5yWC9YLBRBeO"),
(2, "Admin", "Test2", "test2@gmail.com", "+654321", "$2a$10$.e.n3fd2gzB9Q1Z4dbCYCegdz3L2.JthQHqgJhTF7eaoKaC.kuYTi", 2, "$2a$10$.e.n3fd2gzB9Q1Z4dbCYCe"),
(3, "User", "Test3", "test3@gmail.com", "87612334", "$2a$10$MQ0B83gxRtUoQGV6V0rBHu.gERWN4WrvC9GsG94UQ2sopJ5g2EV/S", 3, "$2a$10$ohHUa6SpJqmlFaO9PRo2g."),
(4, "User", "Test4", "test4@gmail.com", "012345566", "$2a$10$ohHUa6SpJqmlFaO9PRo2g.k5xzN/r10NyoBGBW5/eywK93VKC.bZC", 3, "$2a$10$MQ0B83gxRtUoQGV6V0rBHu");

INSERT INTO category VALUES
(1, "Accessory"),
(2, "Weapon"),
(3, "Magic"),
(4, "Hero");

INSERT INTO products VALUES
(1, "Magic Wand", "Wand", "Powerful equipment", "Lorem Ipsum", "Lorem Ipsum", 50, 5.5, "something"),
(2, "Magic Cloak", "Cloak", "Wizzard Attire", "Lorem Ipsum", "Lorem Ipsum", 100, 10, "something"),
(3, "Magic Potion", "Potion", "Accelerate walking", "Lorem Ipsum", "Lorem Ipsum", 10, 3, "something"),
(4, "Iron Sword", "Sword", "Steel arms", "Lorem Ipsum", "Lorem Ipsum", 5, 50, "something"),
(5, "Wizzard", "Wizzard", "Wizzard from uncharted lands", "Lorem Ipsum", "Lorem Ipsum", 6, 35, "something");

INSERT INTO products_categories VALUES
(1, 1), (1, 2), (2, 1), (2, 3),
(3, 1), (3, 3), (4, 2), (5, 4),
(5, 3);

