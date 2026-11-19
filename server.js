const http = require("http");

const PORT = 8080;

let books = [
  { bookId: 1, isbn: "978-0078022159", barcode: "BK100234", title: "Database System Concepts", author: "Abraham Silberschatz", category: "Database Systems", bookType: "PHYSICAL", totalCopies: 22, availableCopies: 17 },
  { bookId: 2, isbn: "978-0133943030", barcode: "BK100235", title: "Software Engineering", author: "Ian Sommerville", category: "Software & Java", bookType: "PHYSICAL", totalCopies: 15, availableCopies: 10 },
  { bookId: 3, isbn: "978-0132126953", barcode: "BK100236", title: "Computer Networks", author: "Andrew S. Tanenbaum", category: "Computer Networks", bookType: "PHYSICAL", totalCopies: 12, availableCopies: 9 },
  { bookId: 4, isbn: "978-1491912058", barcode: "BK100237", title: "Python Data Science Handbook", author: "Jake VanderPlas", category: "Science & AI", bookType: "EBOOK", totalCopies: 10, availableCopies: 8 },
  { bookId: 5, isbn: "978-1260440232", barcode: "BK100238", title: "Java: The Complete Reference", author: "Herbert Schildt", category: "Software & Java", bookType: "PHYSICAL", totalCopies: 16, availableCopies: 14 },
  { bookId: 6, isbn: "978-0134549897", barcode: "BK100239", title: "Digital Design & Computer Arch", author: "M. Morris Mano", category: "Engineering", bookType: "PHYSICAL", totalCopies: 13, availableCopies: 0 }
];

let members = [
  { memberId: 1, registerNo: "25IT034", name: "Harshana", department: "IT", joinedDate: "2026-07-01" },
  { memberId: 2, registerNo: "25IT004", name: "Arun Kumar", department: "IT", joinedDate: "2026-07-05" },
  { memberId: 3, registerNo: "25CS018", name: "Priya Singh", department: "CSE", joinedDate: "2026-07-10" },
  { memberId: 4, registerNo: "25EC042", name: "Rahul Sharma", department: "ECE", joinedDate: "2026-07-15" },
  { memberId: 5, registerNo: "25IT064", name: "Neha Patel", department: "IT", joinedDate: "2026-07-20" }
];

let transactions = [
  { transactionId: 1007, bookId: 1, bookTitle: "Database System Concepts", barcode: "BK100234", regNo: "25IT064", memberName: "Neha Patel", issueDate: "2026-10-05", dueDate: "2026-10-19", returnDate: null, daysOverdue: 12, fineAmount: 60, status: "OVERDUE" },
  { transactionId: 1008, bookId: 3, bookTitle: "Computer Networks", barcode: "BK100236", regNo: "25IT004", memberName: "Arun Kumar", issueDate: "2026-10-08", dueDate: "2026-10-22", returnDate: null, daysOverdue: 10, fineAmount: 50, status: "OVERDUE" },
  { transactionId: 1009, bookId: 6, bookTitle: "Digital Design & Computer Arch", barcode: "BK100239", regNo: "25CS018", memberName: "Priya Singh", issueDate: "2026-10-12", dueDate: "2026-10-26", returnDate: null, daysOverdue: 8, fineAmount: 40, status: "OVERDUE" },
  { transactionId: 1012, bookId: 2, bookTitle: "Software Engineering", barcode: "BK100235", regNo: "25IT034", memberName: "Harshana", issueDate: "2026-11-10", dueDate: "2026-11-24", returnDate: null, daysOverdue: 0, fineAmount: 0, status: "ISSUED" },
  { transactionId: 1013, bookId: 5, bookTitle: "Java: The Complete Reference", barcode: "BK100238", regNo: "25CS018", memberName: "Priya Singh", issueDate: "2026-11-12", dueDate: "2026-11-26", returnDate: null, daysOverdue: 0, fineAmount: 0, status: "ISSUED" },
  { transactionId: 1001, bookId: 1, bookTitle: "Database System Concepts", barcode: "BK100234", regNo: "25IT034", memberName: "Harshana", issueDate: "2026-07-10", dueDate: "2026-07-24", returnDate: "2026-07-22", daysOverdue: 0, fineAmount: 0, status: "RETURNED" }
];

function sendJson(res, statusCode, data) {
  res.writeHead(statusCode, {
    "Content-Type": "application/json",
    "Access-Control-Allow-Origin": "*",
    "Access-Control-Allow-Methods": "GET, POST, OPTIONS",
    "Access-Control-Allow-Headers": "Content-Type"
  });
  res.end(JSON.stringify(data));
}

function parseBody(req) {
  return new Promise((resolve) => {
    let body = "";
    req.on("data", (chunk) => (body += chunk));
    req.on("end", () => {
      try {
        resolve(body ? JSON.parse(body) : {});
      } catch {
        resolve({});
      }
    });
  });
}

const server = http.createServer(async (req, res) => {
  if (req.method === "OPTIONS") {
    res.writeHead(204, {
      "Access-Control-Allow-Origin": "*",
      "Access-Control-Allow-Methods": "GET, POST, OPTIONS",
      "Access-Control-Allow-Headers": "Content-Type"
    });
    return res.end();
  }

  const url = req.url.split("?")[0];

  if (req.method === "GET" && url === "/api/books") {
    return sendJson(res, 200, books);
  }

  if (req.method === "POST" && url === "/api/books") {
    const data = await parseBody(req);
    const nextId = books.length ? Math.max(...books.map((b) => b.bookId)) + 1 : 1;
    const total = Number(data.totalCopies) > 0 ? Number(data.totalCopies) : 1;
    const newBook = {
      bookId: nextId,
      isbn: data.isbn || "978-0000000000",
      barcode: data.barcode || `BK${Math.floor(100000 + Math.random() * 900000)}`,
      title: data.title || "Untitled Book",
      author: data.author || "Unknown",
      category: data.category || "General",
      bookType: data.bookType || "PHYSICAL",
      totalCopies: total,
      availableCopies: Number.isFinite(Number(data.availableCopies)) ? Number(data.availableCopies) : total
    };
    books.push(newBook);
    return sendJson(res, 201, newBook);
  }

  if (req.method === "GET" && url === "/api/members") {
    return sendJson(res, 200, members);
  }

  if (req.method === "POST" && url === "/api/members") {
    const data = await parseBody(req);
    const newMember = {
      memberId: members.length + 1,
      registerNo: (data.registerNo || "").toUpperCase(),
      name: data.name || "Member",
      department: data.department || "IT",
      joinedDate: new Date().toISOString().split("T")[0]
    };
    members.push(newMember);
    return sendJson(res, 201, newMember);
  }

  if (req.method === "GET" && url === "/api/transactions") {
    return sendJson(res, 200, transactions);
  }

  if (req.method === "POST" && url === "/api/issue") {
    const data = await parseBody(req);
    const book = books.find((b) => b.bookId === Number(data.bookId));
    const member = members.find((m) => m.registerNo === (data.regNo || "").toUpperCase());
    if (!book || book.availableCopies <= 0) {
      return sendJson(res, 400, { error: "No copies available!" });
    }
    if (!member) {
      return sendJson(res, 400, { error: "Invalid member register no!" });
    }
    book.availableCopies--;
    const txn = {
      transactionId: Date.now() % 10000,
      bookId: book.bookId,
      bookTitle: book.title,
      barcode: book.barcode,
      regNo: member.registerNo,
      memberName: member.name,
      issueDate: data.issueDate || new Date().toISOString().split("T")[0],
      dueDate: data.dueDate || new Date(Date.now() + 14 * 86400000).toISOString().split("T")[0],
      returnDate: null,
      daysOverdue: 0,
      fineAmount: 0,
      status: "ISSUED"
    };
    transactions.unshift(txn);
    return sendJson(res, 201, txn);
  }

  if (req.method === "POST" && url === "/api/return") {
    const data = await parseBody(req);
    const txn = transactions.find((t) => t.transactionId === Number(data.transactionId));
    if (!txn) {
      return sendJson(res, 404, { error: "Transaction not found!" });
    }
    const retDate = data.returnDate || new Date().toISOString().split("T")[0];
    const lateDays = Math.floor((new Date(retDate) - new Date(txn.dueDate)) / 86400000);
    txn.fineAmount = lateDays > 0 ? lateDays * 5 : 0;
    txn.returnDate = retDate;
    txn.status = "RETURNED";
    const book = books.find((b) => b.bookId === txn.bookId);
    if (book) book.availableCopies++;
    return sendJson(res, 200, txn);
  }

  sendJson(res, 404, { error: "Endpoint not found" });
});

server.listen(PORT, () => {
  console.log(`Node ILMS Backend running at http://localhost:${PORT}`);
});