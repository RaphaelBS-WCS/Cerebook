INSERT INTO cerebook_user(
    id, background, bio, birth_date, genre, password, profil_image, super_powers, user_name, membership_id, user_user_id)
VALUES (nextval('hibernate_sequence'), NULL, 'Hello!', NULL, 'female', 'user', NULL, NULL, 'user', ?, ?);
