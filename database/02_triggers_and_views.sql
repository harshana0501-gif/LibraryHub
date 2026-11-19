-- Course: 25ITT31 - SQL Views & Triggers (Integrity & Automated Fine Rules)
CREATE VIEW vw_available_books AS
SELECT b.book_id, b.isbn, b.barcode, b.title, a.author_name, c.category_name, b.book_type, b.available_copies, b.total_copies
FROM books b
JOIN authors a ON b.author_id = a.author_id
JOIN categories c ON b.category_id = c.category_id
WHERE b.available_copies > 0;

CREATE VIEW vw_overdue_books AS
SELECT t.transaction_id, m.register_no, m.member_name, m.department, b.title AS book_title,
       t.issue_date, t.due_date, t.fine_amount
FROM transactions t
JOIN members m ON t.member_id = m.member_id
JOIN books b ON t.book_id = b.book_id
WHERE t.status = 'OVERDUE' OR (t.return_date IS NULL AND t.due_date < CURRENT_DATE);

DELIMITER //
CREATE TRIGGER trg_after_book_issue
AFTER INSERT ON transactions
FOR EACH ROW
BEGIN
    IF NEW.status = 'ISSUED' THEN
        UPDATE books
        SET available_copies = available_copies - 1
        WHERE book_id = NEW.book_id AND available_copies > 0;
    END IF;
END //

CREATE TRIGGER trg_before_book_return
BEFORE UPDATE ON transactions
FOR EACH ROW
BEGIN
    DECLARE days_overdue INT;
    IF OLD.return_date IS NULL AND NEW.return_date IS NOT NULL THEN
        SET days_overdue = DATEDIFF(NEW.return_date, OLD.due_date);
        SET NEW.fine_amount = IF(days_overdue > 0, days_overdue * 5.00, 0.00);
        SET NEW.status = 'RETURNED';
        UPDATE books SET available_copies = available_copies + 1 WHERE book_id = NEW.book_id;
    END IF;
END //
DELIMITER ;
