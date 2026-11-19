# Software Engineering Documentation & Course Mapping (Register No: 34)
**Project:** Library Book Lending and Usage Analysis System (ILMS Dashboard)  
**Department of Information Technology | Velalar College of Engineering and Technology**

## 1. Syllabus Alignment (Strictly 4 Courses)
1. **Software Engineering (`25ITT33`)**:
   - **Unit 1–2 (Process & SRS)**: Agile incremental delivery; Functional Requirements FR1–FR8 (Catalog search, zero-stock issue prevention, automated Rs. 5/day overdue fine calculation, usage reporting).
   - **Unit 3 (Design)**: Decoupled 3-tier architecture (Frontend SPA -> Java REST API -> 3NF Relational Database + Python Analytics).
   - **Unit 4–5 (Testing & Risk)**: Boundary value testing on `available_copies == 0` and overdue fine edge cases (`return_date == due_date`).
2. **Database Management Systems (`25ITT31`)**:
   - **Unit 1–3**: 3NF normalized schema (`categories`, `authors`, `books`, `members`, `transactions`) with `CHECK` constraints and B-tree indexes.
   - **Unit 2 & 4**: SQL Views (`vw_available_books`, `vw_overdue_books`) and Triggers (`trg_after_book_issue`, `trg_before_book_return`).
   - **Unit 5**: NoSQL JSON audit log schema (`04_nosql_audit_schema.json`).
3. **Java Programming (`25ITT32`)**:
   - **Unit 1–2**: Inheritance (`LibraryEntity` -> `Book`, `Member`) and `Searchable` interface.
   - **Unit 3**: Custom `LibraryException`, `synchronized` thread-safe circulation methods, and background daemon overdue monitor thread.
   - **Unit 4–5**: Built-in `com.sun.net.httpserver.HttpServer` REST API, Java Collections (`LinkedHashMap`, `ArrayList`), and Stream API pipelines.
4. **Foundations of Data Science (`25ITC31`)**:
   - **Unit 1–5**: SQLite-to-Pandas extraction, missing return-date handling (`fillna`), Z-score normalization, department-wise pivot tables, and Matplotlib/Seaborn charts.
