CREATE TABLE IF NOT EXISTS Employees(
  Id INTEGER PRIMARY KEY AUTO_INCREMENT,
  Name VARCHAR(100) NOT NULL,
  Position VARCHAR(50) NOT NULL
);

INSERT INTO Employees (Name, Position) VALUES
('Aliko', 'CEO'), 
('Bill', 'CTO'),
('Folrunsho', 'CFO');