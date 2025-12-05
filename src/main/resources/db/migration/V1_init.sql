-- ======================================
-- TABLE: tenants
-- ======================================
CREATE TABLE tenants (
    id CHAR(36) PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    slug VARCHAR(255) NOT NULL,
    email VARCHAR(255),
    domain VARCHAR(255),
    created_at TIMESTAMP NOT NULL,
    updated_at TIMESTAMP NOT NULL
);

-- ======================================
-- TABLE: users (tenant_id optional)
-- ======================================
CREATE TABLE users (
    id CHAR(36) PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    email VARCHAR(255) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL,
    type VARCHAR(50) NOT NULL,
    status VARCHAR(50) NOT NULL,
    tenant_id CHAR(36),
    created_at TIMESTAMP NOT NULL,
    updated_at TIMESTAMP NOT NULL,
    CONSTRAINT fk_user_tenant
        FOREIGN KEY (tenant_id) REFERENCES tenants(id)
        ON DELETE SET NULL
);

-- ======================================
-- TABLE: categories
-- ======================================
CREATE TABLE categories (
    id CHAR(36) PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    tenant_id CHAR(36) NOT NULL,
    created_at TIMESTAMP NOT NULL,
    updated_at TIMESTAMP NOT NULL,
    CONSTRAINT fk_category_tenant 
        FOREIGN KEY (tenant_id) REFERENCES tenants(id)
        ON DELETE CASCADE
);

-- ======================================
-- TABLE: products
-- ======================================
CREATE TABLE products (
    id CHAR(36) PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    description TEXT,
    barcode VARCHAR(255),
    price DECIMAL(15,2),
    tenant_id CHAR(36) NOT NULL,
    category_id CHAR(36) NOT NULL,
    created_at TIMESTAMP NOT NULL,
    updated_at TIMESTAMP NOT NULL,
    CONSTRAINT fk_product_tenant 
        FOREIGN KEY (tenant_id) REFERENCES tenants(id),
    CONSTRAINT fk_product_category 
        FOREIGN KEY (category_id) REFERENCES categories(id)
);

-- ======================================
-- TABLE: access_groups
-- ======================================
CREATE TABLE access_groups (
    id CHAR(36) PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    description TEXT,
    status VARCHAR(50) NOT NULL,
    tenant_id CHAR(36) NOT NULL,
    created_at TIMESTAMP NOT NULL,
    updated_at TIMESTAMP NOT NULL,
    CONSTRAINT fk_accessgroup_tenant 
        FOREIGN KEY (tenant_id) REFERENCES tenants(id)
);

-- ======================================
-- TABLE: views
-- ======================================
CREATE TABLE views (
    id CHAR(36) PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    route VARCHAR(255) NOT NULL,
    icon VARCHAR(255),
    sort_order INT,
    status VARCHAR(50) NOT NULL,
    created_at TIMESTAMP NOT NULL,
    updated_at TIMESTAMP NOT NULL
);

-- ======================================
-- TABLE: permissions
-- ======================================
CREATE TABLE permissions (
    id CHAR(36) PRIMARY KEY,
    access_level VARCHAR(50) NOT NULL,
    view_id CHAR(36) NOT NULL,
    access_group_id CHAR(36) NOT NULL,
    created_at TIMESTAMP NOT NULL,
    updated_at TIMESTAMP NOT NULL,
    CONSTRAINT fk_permission_view 
        FOREIGN KEY (view_id) REFERENCES views(id),
    CONSTRAINT fk_permission_group 
        FOREIGN KEY (access_group_id) REFERENCES access_groups(id)
);
