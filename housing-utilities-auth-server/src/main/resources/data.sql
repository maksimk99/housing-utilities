INSERT INTO user_role (role_id, role) SELECT '1', 'USER'
WHERE NOT EXISTS(SELECT * FROM user_role WHERE role_id = '1' AND role = 'USER');

INSERT INTO user_role (role_id, role) SELECT '2', 'ADMIN'
WHERE NOT EXISTS(SELECT * FROM user_role WHERE role_id = '2' AND role = 'ADMIN');