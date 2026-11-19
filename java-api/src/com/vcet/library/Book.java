package com.vcet.library;
public class Book extends LibraryEntity {
    private String isbn, barcode, title, author, category, bookType;
    private int totalCopies, availableCopies;

    public Book(int id, String isbn, String barcode, String title, String author, String category, String bookType, int total, int avail) {
        super(id);
        this.isbn = isbn; this.barcode = barcode; this.title = title;
        this.author = author; this.category = category; this.bookType = bookType;
        this.totalCopies = total; this.availableCopies = avail;
    }

    public String getTitle() { return title; }
    public String getCategory() { return category; }
    public String getBookType() { return bookType; }
    public int getAvailableCopies() { return availableCopies; }
    public int getTotalCopies() { return totalCopies; }

    public void issueCopy() throws LibraryException {
        if (availableCopies <= 0) {
            throw new LibraryException("Cannot issue '" + title + "': 0 copies available (FR3 Rule).");
        }
        availableCopies--;
    }

    public void returnCopy() {
        if (availableCopies < totalCopies) availableCopies++;
    }

    @Override
    public boolean matches(String q) {
        String k = q.toLowerCase();
        return title.toLowerCase().contains(k) || author.toLowerCase().contains(k) || barcode.toLowerCase().contains(k);
    }

    @Override
    public String toJson() {
        return String.format(
            "{\"bookId\":%d,\"isbn\":\"%s\",\"barcode\":\"%s\",\"title\":\"%s\",\"author\":\"%s\",\"category\":\"%s\",\"bookType\":\"%s\",\"totalCopies\":%d,\"availableCopies\":%d}",
            id, isbn, barcode, title, author, category, bookType, totalCopies, availableCopies
        );
    }
}