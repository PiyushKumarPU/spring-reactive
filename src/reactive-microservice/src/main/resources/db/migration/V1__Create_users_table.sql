CREATE EXTENSION IF NOT EXISTS pgcrypto;
-- Creating User Table
CREATE TABLE "users" (
     id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    "first_name" varchar(50) NULL,
    "last_name" varchar(50) NULL,
    "username" varchar(50) UNIQUE NOT NULL,
    "password" varchar(255) NOT NULL,
    "email" varchar(100) UNIQUE NOT NULL,
    "mobile" varchar(15) UNIQUE NOT NULL,
    account_status varchar(50),
    "account_locked" boolean DEFAULT false,
    "account_expired" boolean DEFAULT false,
    "credentials_expired" boolean DEFAULT false,
    "password_expired" boolean DEFAULT false,
    "failed_attempts" int DEFAULT 0,
    "last_failed_attempt" TIMESTAMP,
    last_password_change TIMESTAMP, -- Last password change timestamp
    version bigint DEFAULT 0 NOT NULL, -- Version for optimistic locking
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP, -- Timestamp for creation
    created_by varchar(200) DEFAULT 'System',
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP, -- Timestamp for last update
    updated_by varchar(200) DEFAULT 'System'
);

-- Creating Roles Table
CREATE TABLE "roles" (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    "name" varchar(50) UNIQUE NOT NULL,
    "description" text,
    version bigint DEFAULT 0 NOT NULL, -- Version for optimistic locking
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP, -- Timestamp for creation
    created_by varchar(200) DEFAULT 'System',
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP, -- Timestamp for last update
    updated_by varchar(200) DEFAULT 'System'
);

-- Creating user_roles Table
CREATE TABLE "user_roles" (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    "user_id" UUID NOT NULL,
    "role_id" UUID NOT NULL,
    version bigint DEFAULT 0 NOT NULL, -- Version for optimistic locking
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP, -- Timestamp for creation
    created_by varchar(200) DEFAULT 'System',
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP, -- Timestamp for last update
    updated_by varchar(200) DEFAULT 'System'
);

-- Creating Indexes
CREATE UNIQUE INDEX ON "user_roles" ("user_id", "role_id");

-- Adding foreign keys

ALTER TABLE "user_roles" ADD FOREIGN KEY ("user_id") REFERENCES "users" ("id");

ALTER TABLE "user_roles" ADD FOREIGN KEY ("role_id") REFERENCES "roles" ("id");

-- Adding comment on table columns

COMMENT ON COLUMN "users"."id" IS 'Primary key as UUID';

COMMENT ON COLUMN "users"."username" IS 'Unique username';

COMMENT ON COLUMN "users"."password" IS 'Encrypted password';

COMMENT ON COLUMN "users"."email" IS 'Unique email';

COMMENT ON COLUMN "users"."mobile" IS 'Unique mobile';

COMMENT ON COLUMN "users"."account_status" IS 'Status of the account';

COMMENT ON COLUMN "users"."account_locked" IS 'Whether the account is locked';

COMMENT ON COLUMN "users"."account_expired" IS 'Whether the account is expired';

COMMENT ON COLUMN "users"."credentials_expired" IS 'Whether credentials are expired';

COMMENT ON COLUMN "users"."password_expired" IS 'Whether the password is expired';

COMMENT ON COLUMN "users"."failed_attempts" IS 'Number of failed login attempts';

COMMENT ON COLUMN "users"."last_failed_attempt" IS 'Timestamp of the last failed login attempt';

COMMENT ON COLUMN "users"."last_password_change" IS 'Last password change timestamp';

COMMENT ON COLUMN "users"."version" IS 'Version for optimistic locking';

COMMENT ON COLUMN "users"."created_at" IS 'Timestamp for creation';

COMMENT ON COLUMN "users"."updated_at" IS 'Timestamp for last update';

COMMENT ON COLUMN "roles"."id" IS 'Primary key as UUID';

COMMENT ON COLUMN "roles"."name" IS 'Unique role name (e.g., Admin, Partner)';

COMMENT ON COLUMN "roles"."description" IS 'Optional description of the role';

COMMENT ON COLUMN "roles"."created_at" IS 'Timestamp for creation';

COMMENT ON COLUMN "roles"."updated_at" IS 'Timestamp for last update';

COMMENT ON COLUMN "user_roles"."user_id" IS 'Foreign key to users table';

COMMENT ON COLUMN "user_roles"."role_id" IS 'Foreign key to roles table';

COMMENT ON COLUMN "user_roles"."created_at" IS 'Timestamp for creation';