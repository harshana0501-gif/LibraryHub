package com.vcet.library;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

public class Transaction {
    private int transactionId, bookId;
    private String memberRegNo, memberName, bookTitle, barcode, status;
    private LocalDate issueDate, dueDate, returnDate;
    private double fineAmount;

    public Transaction(int id, int bookId, String bookTitle, String barcode, String regNo, String memberName, LocalDate issue, LocalDate due, String status, double fine) {
        this.transactionId = id; this.bookId = bookId; this.bookTitle = bookTitle; this.barcode = barcode;
        this.memberRegNo = regNo; this.memberName = memberName; this.issueDate = issue; this.dueDate = due;
        this.status = status; this.fineAmount = fine;
    }

    public void processReturn(LocalDate retDate) {
        this.returnDate = retDate;
        long overdueDays = ChronoUnit.DAYS.between(dueDate, retDate);
        this.fineAmount = overdueDays > 0 ? overdueDays * 5.0 : 0.0;
        this.status = "RETURNED";
    }

    public int getTransactionId() { return transactionId; }
    public int getBookId() { return bookId; }
    public String getStatus() { return status; }
    public LocalDate getDueDate() { return dueDate; }
    public void markOverdueIfLate() {
        if ("ISSUED".equals(status) && LocalDate.now().isAfter(dueDate)) {
            status = "OVERDUE";
            fineAmount = ChronoUnit.DAYS.between(dueDate, LocalDate.now()) * 5.0;
        }
    }

    public String toJson() {
        long daysOverdue = "OVERDUE".equals(status) ? Math.max(1, ChronoUnit.DAYS.between(dueDate, LocalDate.now())) : 0;
        return String.format(
            "{\"transactionId\":%d,\"bookId\":%d,\"bookTitle\":\"%s\",\"barcode\":\"%s\",\"regNo\":\"%s\",\"memberName\":\"%s\",\"issueDate\":\"%s\",\"dueDate\":\"%s\",\"returnDate\":%s,\"daysOverdue\":%d,\"fineAmount\":%.2f,\"status\":\"%s\"}",
            transactionId, bookId, bookTitle, barcode, memberRegNo, memberName, issueDate, dueDate,
            returnDate == null ? "null" : "\"" + returnDate + "\"", daysOverdue, fineAmount, status
        );
    }
}