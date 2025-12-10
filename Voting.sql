
CREATE TABLE Vote (
    Id SERIAL PRIMARY KEY,
    Title VARCHAR(255) NOT NULL,
    DateStart DATE NOT NULL,
    DateFinish DATE NOT NULL,
    Status VARCHAR(50) NOT NULL 
);


CREATE TABLE Question (
    Id SERIAL PRIMARY KEY,
    VoteId INT NOT NULL,
    Content TEXT NOT NULL,
    DateVote TIMESTAMP NOT NULL, 
    FOREIGN KEY (VoteId) REFERENCES Vote(Id)
);


CREATE TABLE "User" (
    Id SERIAL PRIMARY KEY,
    LastName VARCHAR(255) NOT NULL,
    FirstName VARCHAR(255) NOT NULL,
    Email VARCHAR(255) UNIQUE NOT NULL,
    Phone VARCHAR(20),
    Status VARCHAR(50) NOT NULL
);


CREATE TABLE Choice (
    Id SERIAL PRIMARY KEY,
    QuestionId INT NOT NULL,
    UserId INT NOT NULL,
    ChoiceUser VARCHAR(255) NOT NULL,
    FOREIGN KEY (QuestionId) REFERENCES Question(Id),
    FOREIGN KEY (UserId) REFERENCES "User"(Id) 
);


INSERT INTO Vote (Title, DateStart, DateFinish, Status) VALUES
('Выборы президента компании', '2023-10-26', '2023-11-05', 'Активно'),
('Выбор нового логотипа', '2023-11-01', '2023-11-10', 'Активно'),
('Опрос об условиях труда', '2023-11-15', '2023-11-22', 'Завершено');

-- Заполнение Таблицы Question
INSERT INTO Question (VoteId, Content, DateVote) VALUES
(1, 'Кого вы хотите видеть президентом компании?', '2023-10-27 10:00:00'),
(1, 'Какие качества важны в президенте компании?', '2023-10-27 10:00:00'),
(2, 'Какой логотип лучше отражает ценности компании?', '2023-11-02 14:30:00'),
(3, 'Устраивают ли вас текущие условия труда?', '2023-11-16 09:00:00'),
(3, 'Что бы вы хотели изменить в условиях труда?', '2023-11-16 09:00:00');

-- Заполнение Таблицы User
INSERT INTO "User" (LastName, FirstName, Email, Phone, Status) VALUES
('Иванов', 'Иван', 'ivanov@example.com', '+79001234567', 'Проголосовал'),
('Петров', 'Петр', 'petrov@example.com', '+79007654321', 'Проголосовал'),
('Сидоров', 'Сидор', 'sidorov@example.com', '+79001122333', 'Не проголосовал'),
('Смирнова', 'Анна', 'smirnova@example.com', '+79004455666', 'Проголосовал');

-- Заполнение Таблицы Choice
INSERT INTO Choice (QuestionId, UserId, ChoiceUser) VALUES
(1, 1, 'Кандидат A'),
(1, 2, 'Кандидат B'),
(2, 1, 'Лидерство'),
(2, 2, 'Опыт'),
(3, 1, 'Логотип 1'),
(3, 4, 'Логотип 2'),
(4, 1, 'Да'),
(4, 4, 'Нет'),
(5, 1, 'Гибкий график'),
(5, 4, 'Больше выходных');
