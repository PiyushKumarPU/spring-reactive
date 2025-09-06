-- Enabling if not enable to execute uuid_generate_v4() of postgresql
CREATE EXTENSION IF NOT EXISTS "uuid-ossp";

-- Inserting system roles
INSERT INTO "roles" ("id", "name", "description") VALUES
    (uuid_generate_v4(), 'ROLE_USER', 'Represents a learner or participant in the system.');

INSERT INTO "roles" ("id", "name", "description") VALUES
    (uuid_generate_v4(), 'ROLE_ADMIN', 'Represents a super administrator with unrestricted access to all system features and settings.');

INSERT INTO "roles" ("id", "name", "description") VALUES
    (uuid_generate_v4(), 'ROLE_SALES', 'Represents an administrator with access to sales-related features, such as managing leads, tracking sales, and generating reports.');

INSERT INTO "roles" ("id", "name", "description") VALUES
    (uuid_generate_v4(), 'ROLE_OPERATIONS', 'Represents an administrator with access to operational features, such as managing workflows, overseeing processes, and handling day-to-day operations.');