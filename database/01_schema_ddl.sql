-- Course: 25ITT31 - DATABASE MANAGEMENT SYSTEMS (3NF Normalized Schema)
DROP TABLE IF EXISTS transactions;
DROP TABLE IF EXISTS books;
DROP TABLE IF EXISTS authors;
DROP TABLE IF EXISTS categories;
DROP TABLE IF EXISTS members;

CREATE TABLE categories (
    category_id INT PRIMARY KEY,
    category_name VARCHAR(100) NOT NULL UNIQUE,
    shelf_location VARCHAR(50) NOT NULL
);

CREATE TABLE authors (
    author_id INT PRIMARY KEY,
    author_name VARCHAR(150) NOT NULL,
    country VARCHAR(80)
);

CREATE TABLE books (
    book_id INT PRIMARY KEY,
    isbn VARCHAR(20) NOT NULL UNIQUE,
    barcode VARCHAR(20) NOT NULL UNIQUE,
    title VARCHAR(200) NOT NULL,
    author_id INT NOT NULL,
    category_id INT NOT NULL,
    book_type VARCHAR(20) NOT NULL CHECK (book_type IN ('PHYSICAL', 'EBOOK')),
    total_copies INT NOT NULL CHECK (total_copies >= 0),
    available_copies INT NOT NULL CHECK (available_copies >= 0 AND available_copies <= total_copies),
    FOREIGN KEY (author_id) REFERENCES authors(author_id),
    FOREIGN KEY (category_id) REFERENCES categories(category_id)
);

CREATE TABLE members (
    member_id INT PRIMARY KEY,
    register_no VARCHAR(20) NOT NULL UNIQUE,
    member_name VARCHAR(150) NOT NULL,
    department VARCHAR(50) NOT NULL,
    year_of_study INT CHECK (year_of_study BETWEEN 1 AND 4),
    joined_date DATE NOT NULL,
    email VARCHAR(120) UNIQUE
);

CREATE TABLE transactions (
    transaction_id INT PRIMARY KEY,
    book_id INT NOT NULL,
    member_id INT NOT NULL,
    issue_date DATE NOT NULL,
    due_date DATE NOT NULL,
    return_date DATE NULL,
    fine_amount DECIMAL(8, 2) DEFAULT 0.00,
    status VARCHAR(20) NOT NULL CHECK (status IN ('ISSUED', 'RETURNED', 'OVERDUE')),
    FOREIGN KEY (book_id) REFERENCES books(book_id),
    FOREIGN KEY (member_id) REFERENCES members(member_id)
);

CREATE INDEX idx_books_category ON books(category_id);
CREATE INDEX idx_transactions_status ON transactions(status);
CREATE INDEX idx_transactions_issue_date ON transactions(issue_date);
