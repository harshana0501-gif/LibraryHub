INSERT INTO categories VALUES
(1, 'Engineering', 'Rack A1'),
(2, 'Science & AI', 'Rack B2'),
(3, 'Software & Java', 'Rack C1'),
(4, 'Database Systems', 'Rack D3'),
(5, 'Computer Networks', 'Rack E2');

INSERT INTO authors VALUES
(101, 'Abraham Silberschatz', 'USA'),
(102, 'Ian Sommerville', 'UK'),
(103, 'Andrew S. Tanenbaum', 'Netherlands'),
(104, 'Jake VanderPlas', 'USA'),
(105, 'Herbert Schildt', 'USA'),
(106, 'M. Morris Mano', 'USA');

INSERT INTO books VALUES
(1, '978-0078022159', 'BK100234', 'Database System Concepts', 101, 4, 'PHYSICAL', 6, 4),
(2, '978-0133943030', 'BK100235', 'Software Engineering', 102, 3, 'PHYSICAL', 5, 3),
(3, '978-0132126953', 'BK100236', 'Computer Networks', 103, 5, 'PHYSICAL', 5, 4),
(4, '978-1491912058', 'BK100237', 'Python Data Science Handbook', 104, 2, 'EBOOK', 10, 8),
(5, '978-1260440232', 'BK100238', 'Java: The Complete Reference', 105, 3, 'PHYSICAL', 6, 4),
(6, '978-0134549897', 'BK100239', 'Digital Design & Computer Arch', 106, 1, 'PHYSICAL', 3, 0);

INSERT INTO members VALUES
(1, '25IT034', 'Student Reg 34', 'IT', 2, '2026-07-01', 'reg34@vcet.ac.in'),
(2, '25IT004', 'Arun Kumar', 'IT', 2, '2026-07-05', 'arun04@vcet.ac.in'),
(3, '25CS018', 'Priya Singh', 'CSE', 2, '2026-07-10', 'priya18@vcet.ac.in'),
(4, '25EC042', 'Rahul Sharma', 'ECE', 3, '2026-07-15', 'rahul42@vcet.ac.in'),
(5, '25IT064', 'Neha Patel', 'IT', 2, '2026-07-20', 'neha64@vcet.ac.in');

INSERT INTO transactions VALUES
(1001, 1, 1, '2026-07-10', '2026-07-24', '2026-07-22', 0.00, 'RETURNED'),
(1002, 2, 4, '2026-07-15', '2026-07-29', '2026-08-03', 25.00, 'RETURNED'),
(1003, 4, 3, '2026-08-05', '2026-08-19', '2026-08-18', 0.00, 'RETURNED'),
(1004, 5, 1, '2026-08-12', '2026-08-26', '2026-08-30', 20.00, 'RETURNED'),
(1005, 3, 2, '2026-09-02', '2026-09-16', '2026-09-15', 0.00, 'RETURNED'),
(1006, 4, 5, '2026-09-10', '2026-09-24', '2026-09-29', 25.00, 'RETURNED'),
(1007, 1, 5, '2026-10-05', '2026-10-19', NULL, 60.00, 'OVERDUE'),
(1008, 3, 2, '2026-10-08', '2026-10-22', NULL, 50.00, 'OVERDUE'),
(1009, 6, 3, '2026-10-12', '2026-10-26', NULL, 40.00, 'OVERDUE'),
(1010, 6, 4, '2026-10-14', '2026-10-28', NULL, 30.00, 'OVERDUE'),
(1011, 6, 1, '2026-10-15', '2026-10-29', NULL, 25.00, 'OVERDUE'),
(1012, 2, 1, '2026-11-10', '2026-11-24', NULL, 0.00, 'ISSUED'),
(1013, 5, 3, '2026-11-12', '2026-11-26', NULL, 0.00, 'ISSUED'),
(1014, 4, 4, '2026-11-14', '2026-11-28', NULL, 0.00, 'ISSUED');
