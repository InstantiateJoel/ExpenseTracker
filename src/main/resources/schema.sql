CREATE EXTENSION IF NOT EXISTS pgcrypto;
-- DROP SCHEMA app;

CREATE SCHEMA IF NOT EXISTS app AUTHORIZATION expensetracker_user;

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
	payment_date date NOT NULL,
	CONSTRAINT expense_amount_check CHECK ((amount > (0)::numeric)),
	CONSTRAINT expense_pkey PRIMARY KEY (expenseid),
	CONSTRAINT expense_category_id_fkey FOREIGN KEY (category_id) REFERENCES app.category(categoryid) ON DELETE RESTRICT,
	CONSTRAINT expense_user_id_fkey FOREIGN KEY (user_id) REFERENCES app.users(userid) ON DELETE CASCADE
);
