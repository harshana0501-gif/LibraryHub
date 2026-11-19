"""
Course: 25ITC31 - FOUNDATIONS OF DATA SCIENCE
Library Book Lending & Usage Analysis System (Register No: 34)
"""
import sqlite3
import numpy as np
import pandas as pd

conn = sqlite3.connect("library_ilms.db")
df = pd.read_sql_query("""
SELECT t.transaction_id, t.issue_date, t.due_date, t.return_date, t.fine_amount, t.status,
       b.title AS book_title, b.book_type, c.category_name, m.register_no, m.department
FROM transactions t
JOIN books b ON t.book_id = b.book_id
JOIN categories c ON b.category_id = c.category_id
JOIN members m ON t.member_id = m.member_id
""", conn)
conn.close()

df["issue_date"] = pd.to_datetime(df["issue_date"])
df["due_date"] = pd.to_datetime(df["due_date"])
df["return_date"] = pd.to_datetime(df["return_date"])
df["loan_days"] = (df["return_date"].fillna(pd.Timestamp("2026-11-18")) - df["issue_date"]).dt.days
df["fine_zscore"] = (df["fine_amount"] - df["fine_amount"].mean()) / (df["fine_amount"].std() or 1)

print("=== FDS Descriptive Statistics ===")
print(df[["loan_days", "fine_amount"]].describe())
print("\n=== Pivot Table: Department vs Borrowing Status ===")
print(pd.pivot_table(df, values="transaction_id", index="department", columns="status", aggfunc="count", fill_value=0))
