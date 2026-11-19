package com.vcet.library;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

public class LibraryService {
    private final Map<Integer, Book> books = new LinkedHashMap<>();
    private final Map<String, Member> members = new LinkedHashMap<>();
    private final List<Transaction> transactions = new ArrayList<>();
    private int nextTxnId = 1015;

    public LibraryService() {
        books.put(1, new Book(1, "978-0078022159", "BK100234", "Database System Concepts", "Abraham Silberschatz", "Database Systems", "PHYSICAL", 6, 4));
        books.put(2, new Book(2, "978-0133943030", "BK100235", "Software Engineering", "Ian Sommerville", "Software & Java", "PHYSICAL", 5, 3));
        books.put(3, new Book(3, "978-0132126953", "BK100236", "Computer Networks", "Andrew S. Tanenbaum", "Computer Networks", "PHYSICAL", 5, 4));
        books.put(4, new Book(4, "978-1491912058", "BK100237", "Python Data Science Handbook", "Jake VanderPlas", "Science & AI", "EBOOK", 10, 8));
        books.put(5, new Book(5, "978-1260440232", "BK100238", "Java: The Complete Reference", "Herbert Schildt", "Software & Java", "PHYSICAL", 6, 4));
        books.put(6, new Book(6, "978-0134549897", "BK100239", "Digital Design & Computer Arch", "M. Morris Mano", "Engineering", "PHYSICAL", 3, 0));

        members.put("25IT034", new Member(1, "25IT034", "Student Reg 34", "IT", "2026-07-01"));
        members.put("25IT004", new Member(2, "25IT004", "Arun Kumar", "IT", "2026-07-05"));
        members.put("25CS018", new Member(3, "25CS018", "Priya Singh", "CSE", "2026-07-10"));
        members.put("25EC042", new Member(4, "25EC042", "Rahul Sharma", "ECE", "2026-07-15"));
        members.put("25IT064", new Member(5, "25IT064", "Neha Patel", "IT", "2026-07-20"));

        transactions.add(new Transaction(1007, 1, "Database System Concepts", "BK100234", "25IT064", "Neha Patel", LocalDate.of(2026,10,5), LocalDate.of(2026,10,19), "OVERDUE", 60.0));
        transactions.add(new Transaction(1008, 3, "Computer Networks", "BK100236", "25IT004", "Arun Kumar", LocalDate.of(2026,10,8), LocalDate.of(2026,10,22), "OVERDUE", 50.0));
        transactions.add(new Transaction(1009, 6, "Digital Design & Computer Arch", "BK100239", "25CS018", "Priya Singh", LocalDate.of(2026,10,12), LocalDate.of(2026,10,26), "OVERDUE", 40.0));
        transactions.add(new Transaction(1012, 2, "Software Engineering", "BK100235", "25IT034", "Student Reg 34", LocalDate.of(2026,11,10), LocalDate.of(2026,11,24), "ISSUED", 0.0));
        transactions.add(new Transaction(1013, 5, "Java: The Complete Reference", "BK100238", "25CS018", "Priya Singh", LocalDate.of(2026,11,12), LocalDate.of(2026,11,26), "ISSUED", 0.0));

        // Multithreading: Background Overdue Monitor Daemon (Syllabus Unit 3)
        Thread monitor = new Thread(() -> {
            synchronized (this) {
                transactions.forEach(Transaction::markOverdueIfLate);
            }
        });
        monitor.setDaemon(true);
        monitor.start();
    }

    public synchronized Transaction issueBook(int bookId, String regNo, LocalDate issueDate, LocalDate dueDate) throws LibraryException {
        Book book = books.get(bookId);
        if (book == null) throw new LibraryException("Book ID not found.");
        Member member = members.get(regNo.toUpperCase());
        if (member == null) throw new LibraryException("Member Register No not found: " + regNo);
        book.issueCopy();
        Transaction txn = new Transaction(nextTxnId++, bookId, book.getTitle(), "BK10023" + bookId, member.getRegisterNo(), member.getName(), issueDate, dueDate, "ISSUED", 0.0);
        transactions.add(0, txn);
        return txn;
    }

    public synchronized Transaction returnBook(int txnId, LocalDate returnDate) throws LibraryException {
        Transaction txn = transactions.stream()
            .filter(t -> t.getTransactionId() == txnId && !"RETURNED".equals(t.getStatus()))
            .findFirst()
            .orElseThrow(() -> new LibraryException("Active transaction not found: #" + txnId));
        txn.processReturn(returnDate);
        Book book = books.get(txn.getBookId());
        if (book != null) book.returnCopy();
        return txn;
    }

    public synchronized Member addMember(String regNo, String name, String dept) throws LibraryException {
        String key = regNo.toUpperCase();
        if (members.containsKey(key)) throw new LibraryException("Member already registered: " + key);
        Member m = new Member(members.size() + 1, key, name, dept, LocalDate.now().toString());
        members.put(key, m);
        return m;
    }

    public List<Book> getBooks() { return new ArrayList<>(books.values()); }
    public List<Member> getMembers() { return new ArrayList<>(members.values()); }
    public List<Transaction> getTransactions() { return transactions; }
}