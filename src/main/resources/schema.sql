CREATE EXTENSION IF NOT EXISTS pgcrypto;
-- DROP SCHEMA app;

CREATE SCHEMA IF NOT EXISTS app AUTHORIZATION postgres;

-- Drop table

-- DROP TABLE app.users;

CREATE TABLE app.users (
	userid uuid NOT NULL,
	username varchar(20) NOT NULL,
	password_hash varchar(60) NOT NULL,
	created_at timestamp DEFAULT now() NOT NULL,
	last_login timestamp NULL,
	CONSTRAINT users_pkey PRIMARY KEY (userid),
	CONSTRAINT users_username_unique UNIQUE (username)
);

-- Drop table

-- DROP TABLE app.category;

CREATE TABLE app.category (
	categoryid uuid DEFAULT gen_random_uuid() NOT NULL,
	"name" varchar(100) NOT NULL,
	parent_id uuid NULL,
	CONSTRAINT category_name_key UNIQUE (name),
	CONSTRAINT category_pkey PRIMARY KEY (categoryid),
	CONSTRAINT category_parent_id_fkey FOREIGN KEY (parent_id) REFERENCES app.category(categoryid) ON DELETE RESTRICT
);

-- Drop table

-- DROP TABLE app.expense;

CREATE TABLE app.expense (
	expenseid uuid DEFAULT gen_random_uuid() NOT NULL,
	description varchar(250) NULL,
	category_id uuid NOT NULL,
	amount numeric(7, 2) NOT NULL,
	user_id uuid NOT NULL,
	created_at timestamp DEFAULT now() NOT NULL,
	CONSTRAINT expense_amount_check CHECK ((amount > (0)::numeric)),
	CONSTRAINT expense_pkey PRIMARY KEY (expenseid),
	CONSTRAINT expense_category_id_fkey FOREIGN KEY (category_id) REFERENCES app.category(categoryid) ON DELETE RESTRICT,
	CONSTRAINT expense_user_id_fkey FOREIGN KEY (user_id) REFERENCES app.users(userid) ON DELETE CASCADE
);

-- Category Inserts

-- Main categories
-- Categories, that will have Sub Categories
INSERT INTO app.category
(categoryid, "name", parent_id)
VALUES(gen_random_uuid(), 'pet', null); --sub cat

INSERT INTO app.category
(categoryid, "name", parent_id)
VALUES(gen_random_uuid(), 'car', null); -- sub cat

INSERT INTO app.category
(categoryid, "name", parent_id)
VALUES(gen_random_uuid(), 'housing', null); -- sub cat

INSERT INTO app.category
(categoryid, "name", parent_id)
VALUES(gen_random_uuid(), 'service_subscription', null); --sub cat

INSERT INTO app.category
(categoryid, "name", parent_id)
VALUES(gen_random_uuid(), 'transportation', null);

INSERT INTO app.category
(categoryid, "name", parent_id)
VALUES(gen_random_uuid(), 'sport', null); 

INSERT INTO app.category
(categoryid, "name", parent_id)
VALUES(gen_random_uuid(), 'holidays', null);

INSERT INTO app.category
(categoryid, "name", parent_id)
VALUES(gen_random_uuid(), 'school', null);

INSERT INTO app.category
(categoryid, "name", parent_id)
VALUES(gen_random_uuid(), 'health', null);

-- Categories, that won't have sub categories
INSERT INTO app.category
(categoryid, "name", parent_id)
VALUES(gen_random_uuid(), 'groceries', null); --none

INSERT INTO app.category
(categoryid, "name", parent_id)
VALUES(gen_random_uuid(), 'dining_out', null); --none

INSERT INTO app.category
(categoryid, "name", parent_id)
VALUES(gen_random_uuid(), 'hairdresser', null);

INSERT INTO app.category
(categoryid, "name", parent_id)
VALUES(gen_random_uuid(), 'clothing', null);

INSERT INTO app.category
(categoryid, "name", parent_id)
VALUES(gen_random_uuid(), 'outings', null);


-- sub categories
-- pet
INSERT INTO app.category
(categoryid, "name", parent_id)
VALUES(gen_random_uuid(), 'pet_food', (select categoryid from app.category where name='pet'));

INSERT INTO app.category
(categoryid, "name", parent_id)
VALUES(gen_random_uuid(), 'veterinary_visits', (select categoryid from app.category where name='pet'));

INSERT INTO app.category
(categoryid, "name", parent_id)
VALUES(gen_random_uuid(), 'pet_meds', (select categoryid from app.category where name='pet'));

INSERT INTO app.category
(categoryid, "name", parent_id)
VALUES(gen_random_uuid(), 'pet_toys', (select categoryid from app.category where name='pet'));

-- car
INSERT INTO app.category
(categoryid, "name", parent_id)
VALUES(gen_random_uuid(), 'car_insurance', (select categoryid from app.category where name='car'));

INSERT INTO app.category
(categoryid, "name", parent_id)
VALUES(gen_random_uuid(), 'gas', (select categoryid from app.category where name='car'));

INSERT INTO app.category
(categoryid, "name", parent_id)
VALUES(gen_random_uuid(), 'repairs', (select categoryid from app.category where name='car'));

INSERT INTO app.category
(categoryid, "name", parent_id)
VALUES(gen_random_uuid(), 'maintenance', (select categoryid from app.category where name='car'));

INSERT INTO app.category
(categoryid, "name", parent_id)
VALUES(gen_random_uuid(), 'road_tax', (select categoryid from app.category where name='car'));

-- housing
INSERT INTO app.category
(categoryid, "name", parent_id)
VALUES(gen_random_uuid(), 'rent', (select categoryid from app.category where name='housing'));

INSERT INTO app.category
(categoryid, "name", parent_id)
VALUES(gen_random_uuid(), 'utilities', (select categoryid from app.category where name='housing'));

INSERT INTO app.category
(categoryid, "name", parent_id)
VALUES(gen_random_uuid(), 'home_maintenance', (select categoryid from app.category where name='housing'));

INSERT INTO app.category
(categoryid, "name", parent_id)
VALUES(gen_random_uuid(), 'home_supplies', (select categoryid from app.category where name='housing'));

-- service_subscription
INSERT INTO app.category
(categoryid, "name", parent_id)
VALUES(gen_random_uuid(), 'mobile_phone', (select categoryid from app.category where name='service_subscription'));

INSERT INTO app.category
(categoryid, "name", parent_id)
VALUES(gen_random_uuid(), 'internet', (select categoryid from app.category where name='service_subscription'));

INSERT INTO app.category
(categoryid, "name", parent_id)
VALUES(gen_random_uuid(), 'tv', (select categoryid from app.category where name='service_subscription'));

INSERT INTO app.category
(categoryid, "name", parent_id)
VALUES(gen_random_uuid(), 'streaming', (select categoryid from app.category where name='service_subscription'));

-- transportation
INSERT INTO app.category
(categoryid, "name", parent_id)
VALUES(gen_random_uuid(), 'public_transportation', (select categoryid from app.category where name='transportation'));

INSERT INTO app.category
(categoryid, "name", parent_id)
VALUES(gen_random_uuid(), 'taxi_rides', (select categoryid from app.category where name='transportation'));

INSERT INTO app.category
(categoryid, "name", parent_id)
VALUES(gen_random_uuid(), 'bicycle', (select categoryid from app.category where name='transportation'));

-- sport
INSERT INTO app.category
(categoryid, "name", parent_id)
VALUES(gen_random_uuid(), 'hiking', (select categoryid from app.category where name='sport'));

INSERT INTO app.category
(categoryid, "name", parent_id)
VALUES(gen_random_uuid(), 'bowling', (select categoryid from app.category where name='sport'));

INSERT INTO app.category
(categoryid, "name", parent_id)
VALUES(gen_random_uuid(), 'skiing', (select categoryid from app.category where name='sport'));

INSERT INTO app.category
(categoryid, "name", parent_id)
VALUES(gen_random_uuid(), 'gym', (select categoryid from app.category where name='sport'));

INSERT INTO app.category
(categoryid, "name", parent_id)
VALUES(gen_random_uuid(), 'diving', (select categoryid from app.category where name='sport'));


INSERT INTO app.category
(categoryid, "name", parent_id)
VALUES(gen_random_uuid(), 'gymnastics_club', (select categoryid from app.category where name='sport'));

-- holidays
INSERT INTO app.category
(categoryid, "name", parent_id)
VALUES(gen_random_uuid(), 'flight', (select categoryid from app.category where name='holidays'));

INSERT INTO app.category
(categoryid, "name", parent_id)
VALUES(gen_random_uuid(), 'hotel', (select categoryid from app.category where name='holidays'));

INSERT INTO app.category
(categoryid, "name", parent_id)
VALUES(gen_random_uuid(), 'other', (select categoryid from app.category where name='holidays'));

-- school
INSERT INTO app.category
(categoryid, "name", parent_id)
VALUES(gen_random_uuid(), 'school_fees', (select categoryid from app.category where name='school'));

INSERT INTO app.category
(categoryid, "name", parent_id)
VALUES(gen_random_uuid(), 'supplies', (select categoryid from app.category where name='school'));

INSERT INTO app.category
(categoryid, "name", parent_id)
VALUES(gen_random_uuid(), 'software', (select categoryid from app.category where name='school'));

-- health
INSERT INTO app.category
(categoryid, "name", parent_id)
VALUES(gen_random_uuid(), 'health_insurance', (select categoryid from app.category where name='health'));

INSERT INTO app.category
(categoryid, "name", parent_id)
VALUES(gen_random_uuid(), 'meds', (select categoryid from app.category where name='health'));

INSERT INTO app.category
(categoryid, "name", parent_id)
VALUES(gen_random_uuid(), 'doctor_visits', (select categoryid from app.category where name='health'));