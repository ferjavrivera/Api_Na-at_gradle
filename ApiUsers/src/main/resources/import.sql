/*  ROLES */
    INSERT INTO roles (role_id) VALUES('DEVELOPER');
    INSERT INTO roles (role_id) VALUES('ADMINISTRATOR');

/* USERS */
    INSERT INTO users(user_id, user_name,last_name,email,role_id)
    VALUES(unhex('00000000000000000000000000000000'),'Admin','Default','adefault@na-at.com.mx','ADMINISTRATOR');

    INSERT INTO users(user_id, user_name,last_name,email,role_id)
    VALUES(unhex('00000000000000000000000000000001'),'Developer','Default','ddefault@na-at.com.mx','DEVELOPER');

    INSERT INTO users(user_id, user_name,last_name,email,role_id)
    VALUES(unhex('00000000000000000000000000000002'),'Arturo','Lopez','alopez@na-at.com.mx','DEVELOPER');

    INSERT INTO users(user_id, user_name,last_name,email,role_id)
    VALUES(unhex('00000000000000000000000000000003'),'Montserrat','Salinas','msalinas@na-at.com.mx','DEVELOPER');

    INSERT INTO users(user_id, user_name,last_name,email,role_id)
    VALUES(unhex('00000000000000000000000000000004'),'Ana','Franco','afranco@na-at.com.mx','DEVELOPER');

    INSERT INTO users(user_id, user_name,last_name,email,role_id)
    VALUES(unhex('00000000000000000000000000000005'),'Nayeli','Dominguez','ndominguez@na-at.com.mx','DEVELOPER');

    INSERT INTO users(user_id, user_name,last_name,email,role_id)
    VALUES(unhex('00000000000000000000000000000006'),'Andrea','Nuñez','anunez@na-at.com.mx','DEVELOPER');

    INSERT INTO users(user_id, user_name,last_name,email,role_id)
    VALUES(unhex('00000000000000000000000000000007'),'Enrique','Ordoñez','eordonez@na-at.com.mx','DEVELOPER');

    INSERT INTO users(user_id, user_name,last_name,email,role_id)
    VALUES(unhex('00000000000000000000000000000008'),'Karen','León','kleon@na-at.com.mx','DEVELOPER');

    INSERT INTO users(user_id, user_name,last_name,email,role_id)
    VALUES(unhex('00000000000000000000000000000009'),'Ricardo','Herrera','rherrera@na-at.com.mx','DEVELOPER');

    INSERT INTO users(user_id, user_name,last_name,email,role_id)
    VALUES(unhex('00000000000000000000000000000010'),'Jaime','Aparicio','japaricio@na-at.com.mx','DEVELOPER');
