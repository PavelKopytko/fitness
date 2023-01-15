INSERT INTO myschema.user(
	uuid, dt_create, dt_update, mail, nick, password, role, user_status)
	VALUES ('13d89eba-daa6-4f47-8fad-369c2eaf0c96',
	 now(),
	 now(),
	 'admin@mail.ru',
	 'Администратор Администратович',
	 '$2a$10$CZt9X8Yjxj5Z3/ZmdbNYoeCMoRUI/7bfSEKnC7OnXVutKvY8wORHe',
	 'ROLE_ADMIN',
	 'ACTIVATED');