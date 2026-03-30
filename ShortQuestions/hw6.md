# Explain Referential Integrity in RDBMS/SQL with sample queries.
Referential integrity means that relationships between tables remain consistent. It ensures that a foreign key in one table must match a primary key in another table, or be NULL.
This prevents invalid data. For example, an employee cannot reference a department that does not exist.
```
CREATE TABLE Department (
    id INT PRIMARY KEY,
    name VARCHAR(50)
);

CREATE TABLE Employee (
    id INT PRIMARY KEY,
    name VARCHAR(50),
    dept_id INT,
    FOREIGN KEY (dept_id) REFERENCES Department(id)
);
```
In this example, dept_id in Employee must exist in the Department table.

# Explain Join in RDBMS/SQL with sample queries.
A JOIN is used to combine data from multiple tables based on a related column.

It allows us to retrieve related information stored in different tables.
```
SELECT e.name, d.name
FROM Employee e
INNER JOIN Department d
ON e.dept_id = d.id;
```
This returns employees and their department names.
Types of JOIN:
INNER JOIN: only matching rows
LEFT JOIN: all rows from left table + matched rows
RIGHT JOIN: all rows from right table + matched rows

# Compare Developer API vs User API
A Developer API is designed for developers to interact with a system programmatically. It is usually used in code, like REST APIs.

A User API is designed for end users to interact with the system directly, usually through a UI like a website or app.

# Explain components of REST API?
A REST API consists of several key components:

Endpoint (URL): the address of the resource
HTTP Method: defines the action (GET, POST, etc.)
Headers: metadata (authentication, content type)
Request Body: data sent to the server
Response Body: data returned by the server
Status Code: indicates success or failure (e.g., 200, 404)

# Compare each type of HTTP Method
GET: retrieve data (read-only)
POST: create new data
PUT: update entire resource
PATCH: update part of resource
DELETE: remove data

# Explain authentication field in http header?
The authentication field is used to verify the identity of a user or client.

It is usually included in the Authorization header.

like: Authorization: Bearer abc123token -> This tells the server who the user is and whether they have permission.
# Explain cookies field in http header?
Cookies are small pieces of data stored in the browser. They are used to maintain user sessions.

The client sends cookies to the server in the Cookie header.

like Cookie: session_id=abc123 -> This allows the server to remember the user (e.g., login state).

# Explain the purpose of http response headers? Why it is necessary?
HTTP response headers provide additional information about the response.

They tell the client how to handle the response data.

Common examples:
Content-Type: type of data (e.g., JSON)
Set-Cookie: send cookies to client
Cache-Control: control caching

Response headers are necessary because they:
help the client understand the data format
control caching behavior
manage sessions and security