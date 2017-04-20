# Videoupload


####JDK-1.8

####Database: 
PostgreSQl 9.0 or later
base: localhost
port: 6432
name: video
user: test
password: test
(global.properties)

####Database init script:
##### Create database user
CREATE ROLE test LOGIN
  ENCRYPTED PASSWORD 'md505a671c66aefea124cc08b76ea6d30bb'
  NOSUPERUSER INHERIT NOCREATEDB NOCREATEROLE NOREPLICATION;

##### Create database
CREATE DATABASE video
  WITH OWNER = test
       ENCODING = 'UTF8'
       TABLESPACE = pg_default
       LC_COLLATE = 'en_US.UTF-8'
       LC_CTYPE = 'en_US.UTF-8'
       CONNECTION LIMIT = -1;

##### Create table
CREATE TABLE public.members (
  id bigserial,
  password character varying(255) NOT NULL,
  username character varying(255) NOT NULL,
  CONSTRAINT members_pkey PRIMARY KEY (id),
  CONSTRAINT members_username_key UNIQUE (username)
) WITH (OIDS=FALSE);
ALTER TABLE public.members OWNER TO test;

##### Create first user
insert into public.members(username, password)
    values('Admin', 'admin');


