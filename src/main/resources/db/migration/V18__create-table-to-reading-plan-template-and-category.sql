CREATE TABLE IF NOT EXISTS TB_PLAN_CATEGORY(
    plan_category_id BIGSERIAL PRIMARY KEY,
    name varchar(255) NOT NULL UNIQUE
);

CREATE TABLE IF NOT EXISTS TB_PLAN(
    plan_id BIGSERIAL PRIMARY KEY,
    title VARCHAR(255) NOT NULL,
    description VARCHAR(255),
    public BOOLEAN,
    goal_date TIMESTAMP,
    user_id BIGINT NOT NULL,

    CONSTRAINT fk_plan_to_user FOREIGN KEY (user_id) REFERENCES TB_USER(user_id) ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS TB_PLAN_BOOK_TEMPLATE(
    plan_book_template_id BIGSERIAL PRIMARY KEY,
    plan_id BIGINT NOT NULL,
    book_template_id BIGINT NOT NULL,
    description VARCHAR(255),
    position INT,

    CONSTRAINT fk_pbt_to_plan FOREIGN KEY (plan_id) REFERENCES TB_PLAN(plan_id) ON DELETE CASCADE,
    CONSTRAINT fk_pbt_to_book_template FOREIGN KEY (book_template_id) REFERENCES TB_BOOK_TEMPLATE(book_template_id) ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS TB_JOIN_PLAN_CATEGORY(
    plan_id BIGINT NOT NULL,
    plan_category_id BIGINT NOT NULL,

    PRIMARY KEY(plan_id, plan_category_id),
    CONSTRAINT fk_jpc_to_plan FOREIGN KEY (plan_id) REFERENCES TB_PLAN(plan_id) ON DELETE CASCADE,
    CONSTRAINT fk_jpc_to_plan_category FOREIGN KEY (plan_category_id) REFERENCES TB_PLAN_CATEGORY(plan_category_id) ON DELETE CASCADE
);

CREATE INDEX IF NOT EXISTS idx_TB_PLAN_CATEGORY_name ON TB_PLAN_CATEGORY(LOWER(name));
CREATE INDEX IF NOT EXISTS idx_TB_PLAN_title ON TB_PLAN(LOWER(title));