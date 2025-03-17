INSERT INTO users (id, username, password) VALUES (default, 'admin', 'password');

INSERT INTO categories (id, name) VALUES (default, 'HTML');

INSERT INTO categories (id, name) VALUES (default, 'CSS');

INSERT INTO categories (id, name) VALUES (default, 'JavaScript');

INSERT INTO categories (id, name) VALUES (default, 'Java');

INSERT INTO resources (id, title, file_url, upload_date, category_id) VALUES (default, 'HTML Tutorial', 'https://www.w3schools.com/html/default.asp', '2021-01-01', 1);

INSERT INTO resources (id, title, file_url, upload_date, category_id) VALUES (default, 'CSS Tutorial', 'https://www.w3schools.com/css/default.asp', '2021-02-01', 2);

INSERT INTO resources (id, title, file_url, upload_date, category_id) VALUES (default, 'JavaScript Guide', 'https://developer.mozilla.org/en-US/docs/Web/JavaScript/Guide', '2021-03-01', 3);

INSERT INTO resources (id, title, file_url, upload_date, category_id) VALUES (default, 'Java Documentation', 'https://docs.oracle.com/en/java/', '2021-04-01', 4);
