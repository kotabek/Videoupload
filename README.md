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


####Spring Security

#####Filters
ProjectAuthenticationProcessingFilter - for login
ProjectSecurityContextPersistenceFilter - restore session and update session data (if nessesory)

#####Authentication provider
ProjectAuthenticationProvider - main logic for logining process

#####Additional
1) If case we need registration - need just add new member to table "Member"
2) Unique constrain can be deleted in case system has to support close or remove account
3) After upload we return duration in message but any time we can extract it in difference field
4) Was used Xuggler (http://www.xuggle.com/xuggler) to extract a duration on the server side.
5) I didn't use roles for user - can be implemented later.
6) I keep sessions in the memory - can be migrated to database + generate UDID and keep it in a cookie 
 
#####How many time spend to task? 4 hours 30 minutes

#####What we need to migrate to Amazon
S2 - Small instance 
    install: Java-8, NGinx, Tomcat-7.75 or later, PostgreSQL-9, PGBouncer (for port 6432)
S3 - to keep uploded files
RDS - for future
Benchtalk - for future 