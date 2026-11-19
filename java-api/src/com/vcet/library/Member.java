package com.vcet.library;
public class Member extends LibraryEntity {
    private String registerNo, name, department, joinedDate;
    public Member(int id, String regNo, String name, String dept, String joined) {
        super(id); this.registerNo = regNo; this.name = name; this.department = dept; this.joinedDate = joined;
    }
    public String getRegisterNo() { return registerNo; }
    public String getName() { return name; }
    @Override public boolean matches(String q) { return registerNo.equalsIgnoreCase(q) || name.toLowerCase().contains(q.toLowerCase()); }
    @Override public String toJson() {
        return String.format("{\"memberId\":%d,\"registerNo\":\"%s\",\"name\":\"%s\",\"department\":\"%s\",\"joinedDate\":\"%s\"}", id, registerNo, name, department, joinedDate);
    }
}