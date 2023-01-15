CREATE SCHEMA IF NOT EXISTS myschema;

CREATE TABLE IF NOT EXISTS myschema.user
(
    uuid uuid NOT NULL,
    dt_create timestamp(3) without time zone,
    dt_update timestamp(3) without time zone,
    mail character varying(50),
    nick character varying(50),
    password character varying(50),
    role character varying(50),
    user_status character varying(50),
    CONSTRAINT user_pkey PRIMARY KEY (uuid),
    CONSTRAINT mail_uniq UNIQUE (mail)
        INCLUDE(mail),
    CONSTRAINT nick_uniq UNIQUE (nick)
        INCLUDE(nick)
);


CREATE TABLE IF NOT EXISTS myschema.product
(
    uuid uuid NOT NULL,
    calories integer NOT NULL,
    carbohydrates double precision NOT NULL,
    dt_create timestamp(3) without time zone,
    dt_update timestamp(3) without time zone,
    fats double precision NOT NULL,
    proteins double precision NOT NULL,
    weight integer NOT NULL,
    user_uuid uuid,
    title character varying(50),
    CONSTRAINT product_pkey PRIMARY KEY (uuid),
    CONSTRAINT fkjghyk74mk900scaq7uhrd058a FOREIGN KEY (user_uuid)
        REFERENCES myschema.user (uuid) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION
);


CREATE TABLE IF NOT EXISTS myschema.dish
(
    uuid uuid NOT NULL,
    dt_create timestamp(3) without time zone,
    dt_update timestamp(3) without time zone,
    title character varying(50),
    user_uuid uuid,
    CONSTRAINT dish_pkey PRIMARY KEY (uuid),
    CONSTRAINT fko22oxpxm2gq6y1y60lq5m00jg FOREIGN KEY (user_uuid)
        REFERENCES myschema.user (uuid) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION
);


CREATE TABLE IF NOT EXISTS myschema.ingredient
(
    uuid uuid NOT NULL,
    weight integer NOT NULL,
    product_id uuid,
    dish_id uuid,
    CONSTRAINT ingredient_pkey PRIMARY KEY (uuid),
    CONSTRAINT fkgg86fhhgm573id99ipfd2br19 FOREIGN KEY (product_id)
        REFERENCES myschema.product (uuid) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION,
    CONSTRAINT fkl5flopow34ng2dywdl2p3l5b8 FOREIGN KEY (dish_id)
        REFERENCES myschema.dish (uuid) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION
);


CREATE TABLE IF NOT EXISTS myschema.audit
(
    uuid uuid NOT NULL,
    dt_create timestamp(3) without time zone,
    id character varying(50),
    text character varying(100),
    type character varying(100),
    user_uuid uuid,

    CONSTRAINT audit_pkey PRIMARY KEY (uuid),
    CONSTRAINT fkiji34myx5168y8vq5bimg3hcn FOREIGN KEY (user_uuid)
        REFERENCES myschema.user (uuid) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION
);

CREATE TABLE IF NOT EXISTS myschema.profile
(
    uuid uuid NOT NULL,
    activity_type character varying(50),
    dt_birthday timestamp(3) without time zone,
    dt_create timestamp(3) without time zone,
    dt_update timestamp(3) without time zone,
    height integer NOT NULL,
    sex character varying(15),
    target double precision NOT NULL,
    weight double precision NOT NULL,
    user_id uuid,
    CONSTRAINT profile_pkey PRIMARY KEY (uuid),
    CONSTRAINT user_uniq UNIQUE (user_id)
        INCLUDE(user_id),
    CONSTRAINT fkawh070wpue34wqvytjqr4hj5e FOREIGN KEY (user_id)
        REFERENCES myschema.user (uuid) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION
);

CREATE TABLE IF NOT EXISTS myschema.diary
(
    uuid uuid NOT NULL,
    dt_create timestamp(3) without time zone,
    dt_supply timestamp(3) without time zone,
    dt_update timestamp(3) without time zone,
    weight integer NOT NULL,
    dish_id uuid,
    product_id uuid,
    profile_uuid uuid,
    CONSTRAINT diary_pkey PRIMARY KEY (uuid),
    CONSTRAINT fkbe9d18f177lkfq5tj3jtmrdto FOREIGN KEY (profile_uuid)
        REFERENCES myschema.profile (uuid) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION,
    CONSTRAINT fkgsqbl0x036k5bsopu2ylcg8am FOREIGN KEY (dish_id)
        REFERENCES myschema.dish (uuid) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION,
    CONSTRAINT fkn5ggepcc4hw5ji2dq04h80kc0 FOREIGN KEY (product_id)
        REFERENCES myschema.product (uuid) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION
);

