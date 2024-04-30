CREATE TABLE todo
(
    id          bigint auto_increment,
    header      VARCHAR(255),
    content     VARCHAR(255),
    isCompleted int
);

CREATE TABLE todo_user
(
    id       bigint auto_increment,
    username VARCHAR(255),
    password VARCHAR(255)
);


--INSERT INTO todo(id, header, content, isCompleted) VALUES (1, 'test1', 'content1', 0);
--INSERT INTO todo(id, header, content, isCompleted) VALUES (2, 'test2', 'content2', 1);
--INSERT INTO todo(id, header, content, isCompleted) VALUES (3, 'test3', 'content3', 0);
--
--INSERT INTO todo_user(id, username, password) VALUES (1, 'ferhatb', 'test123');
--INSERT INTO todo_user(id, username, password) VALUES (2, 'ahmet', 'test123');
