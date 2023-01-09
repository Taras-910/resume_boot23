ALTER SEQUENCE GLOBAL_SEQ RESTART WITH 100000;

INSERT INTO USERS (NAME, EMAIL, PASSWORD, REGISTERED)
VALUES ('Admin', 'admin@gmail.com', '{noop}admin', '2023-01-05 8:00:00'),
       ('User', 'user@yandex.ru', '{noop}password', '2023-01-05 8:00:00');

INSERT INTO USER_ROLE (ROLE, USER_ID)
VALUES ('ADMIN', 100000),
       ('USER', 100000),
       ('USER', 100001);

INSERT INTO FRESHEN (RECORDED_DATE, LANGUAGE , LEVEL , WORKPLACE, USER_ID)
VALUES ('2020-10-25 12:00:00', 'java', 'middle', 'киев', 100000),
       ('2020-10-25 13:00:00', 'php', 'middle', 'днепр', 100001);

INSERT INTO FRESHEN_GOAL (GOAL, FRESHEN_ID)
VALUES ('UPGRADE', 100002),
       ('FILTER', 100003);

INSERT INTO RESUME (TITLE, NAME, AGE, ADDRESS, SALARY, WORK_BEFORE, URL, SKILLS, RELEASE_DATE, FRESHEN_ID)
VALUES ('Middle Game Developer', 'Василь Васильевич', '21', 'Киев', 3000, 'КСПО, 10 лет и 2 месяца',
        'https://grc.ua/resume/40006938?query=java', 'Spring, SQL, REST, PHP', '2021-10-20', 100002),
       ('Middle Java-разработчик', 'Виктор Михайлович', '22', 'Днепр', 2000, 'Днепросталь, 4 года и 5 месяцев',
        'https://grc.ua/resume/40006938?query=java',
        'Понимание JVM. Умение отлаживать и профилировать java-приложения', '2021-10-20', 100003);

INSERT INTO VOTE (LOCAL_DATE, RESUME_ID, USER_ID)
VALUES ('2020-10-25', 100004, 100000),
       ('2020-10-25', 100005, 100001);

INSERT INTO RATE (NAME, VALUE_RATE, LOCAL_DATE)
VALUES ('USDUSD', 1.0, '2020-10-25'),
       ('USDUAH', 36.53, '2020-10-25'),
       ('USDPLN', 4.8544, '2020-10-25'),
       ('USDKZT', 469.5, '2020-10-25'),
       ('USDGBP', 0.87148, '2020-10-25'),
       ('USDEUR', 1.00711, '2020-10-25'),
       ('USDCZK', 24.7275, '2020-10-25'),
       ('USDCAD', 1.35791, '2020-10-25'),
       ('USDBGN', 1.9701, '2020-10-25'),
       ('USDBYR', 2.52, '2020-10-25');
