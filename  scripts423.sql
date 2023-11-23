--Составить первый JOIN-запрос, чтобы получить информацию обо всех студентах (достаточно получить только имя и возраст студента)
--школы Хогвартс вместе с названиями факультетов.
SELECT  student.id , student."name", student.age, faculty.id
from student
JOIN faculty   ON student.faculty_id = faculty.id


--Составить второй JOIN-запрос, чтобы получить только тех студентов, у которых есть аватарки.
SELECT * from student s
JOIN avatar a   ON s.id = a.student_id;

