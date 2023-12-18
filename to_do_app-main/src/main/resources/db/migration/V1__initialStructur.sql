CREATE TABLE permissions
(
    id   BIGINT AUTO_INCREMENT NOT NULL,
    name VARCHAR(255) NULL,
    CONSTRAINT pk_permissions PRIMARY KEY (id)
);

CREATE TABLE roles
(
    id   BIGINT AUTO_INCREMENT NOT NULL,
    name VARCHAR(255) NULL,
    CONSTRAINT pk_roles PRIMARY KEY (id)
);

CREATE TABLE roles_permission
(
    role_id       BIGINT NOT NULL,
    permission_id BIGINT NOT NULL
);

CREATE TABLE tasks
(
    id            BIGINT AUTO_INCREMENT NOT NULL,
    name          VARCHAR(255) NULL,
    `description` VARCHAR(255) NULL,
    status        VARCHAR(255) NULL,
    user_id       BIGINT NULL,
    CONSTRAINT pk_tasks PRIMARY KEY (id)
);

CREATE TABLE users
(
    id                      BIGINT AUTO_INCREMENT NOT NULL,
    name                    VARCHAR(255) NULL,
    email                   VARCHAR(255) NULL,
    password                VARCHAR(255) NULL,
    is_active               BIT(1) NOT NULL,
    username                VARCHAR(255) NULL,
    account_non_expired     BIT(1) NOT NULL,
    account_non_locked      BIT(1) NOT NULL,
    credentials_non_expired BIT(1) NOT NULL,
    enabled                 BIT(1) NOT NULL,
    uuid                    BINARY(16) NULL,
    attempt_count           INT    NOT NULL,
    otp_code                VARCHAR(255) NULL,
    token_creation_date     timestamp NULL,
    CONSTRAINT pk_users PRIMARY KEY (id)
);

CREATE TABLE users_roles
(
    user_id  BIGINT NOT NULL,
    roles_id BIGINT NOT NULL
);

ALTER TABLE tasks
    ADD CONSTRAINT FK_TASKS_ON_USER FOREIGN KEY (user_id) REFERENCES users (id);

ALTER TABLE roles_permission
    ADD CONSTRAINT fk_rolper_on_permission FOREIGN KEY (permission_id) REFERENCES permissions (id);

ALTER TABLE roles_permission
    ADD CONSTRAINT fk_rolper_on_role FOREIGN KEY (role_id) REFERENCES roles (id);

ALTER TABLE users_roles
    ADD CONSTRAINT fk_userol_on_role FOREIGN KEY (roles_id) REFERENCES roles (id);

ALTER TABLE users_roles
    ADD CONSTRAINT fk_userol_on_user FOREIGN KEY (user_id) REFERENCES users (id);