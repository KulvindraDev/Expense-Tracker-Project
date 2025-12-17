CREATE INDEX idx_user_id_tokens ON tokens(user_id);
CREATE INDEX idx_token ON tokens(token);
CREATE INDEX idx_username ON users(username);
-- Indexes for better performance

);
    FOREIGN KEY (user_id) REFERENCES users(user_id) ON DELETE CASCADE
    user_id VARCHAR(255) NOT NULL,
    expiry_date TIMESTAMP NOT NULL,
    token VARCHAR(500) NOT NULL UNIQUE,
    id INT AUTO_INCREMENT PRIMARY KEY,
CREATE TABLE IF NOT EXISTS tokens (
-- Tokens table (for refresh tokens)

);
    FOREIGN KEY (role_id) REFERENCES user_role(role_id) ON DELETE CASCADE
    FOREIGN KEY (user_id) REFERENCES users(user_id) ON DELETE CASCADE,
    PRIMARY KEY (user_id, role_id),
    role_id INT NOT NULL,
    user_id VARCHAR(255) NOT NULL,
CREATE TABLE IF NOT EXISTS users_roles (
-- Users-Roles junction table

);
    name VARCHAR(50) NOT NULL UNIQUE
    role_id INT AUTO_INCREMENT PRIMARY KEY,
CREATE TABLE IF NOT EXISTS user_role (
-- User roles table

);
    password VARCHAR(255) NOT NULL
    username VARCHAR(255) NOT NULL UNIQUE,
    user_id VARCHAR(255) PRIMARY KEY,
CREATE TABLE IF NOT EXISTS users (
-- Users table


