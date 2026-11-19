package com.vcet.library;
interface Searchable { boolean matches(String query); }
public abstract class LibraryEntity implements Searchable {
    protected int id;
    public LibraryEntity(int id) { this.id = id; }
    public int getId() { return id; }
    public abstract String toJson();
}