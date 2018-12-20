INSERT INTO user (id, user_name, password, active)
VALUES (nextval('sqn_user'), 'William', '111', true),
       (nextval('sqn_user'), 'Emma', '222', true),
       (nextval('sqn_user'), 'James', '333', false),
       (nextval('sqn_user'), 'Kaylee', '444', true),
       (nextval('sqn_user'), 'David', '555', true);