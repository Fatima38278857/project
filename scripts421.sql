--Возраст студента не может быть меньше 16 лет.
alter table student
add constraint student_age check(age > 16);
-- Имена студентов должны быть уникальными и не равны нулю.
alter table student
add constraint student_name unique(name);
--Пара “значение названия” - “цвет факультета” должна быть уникальной.
alter table faculty
add constraint faculty_name_color_name unique(name, color);
-- При создании студента без возраста ему автоматически должно присваиваться 20 лет.
alter table student
alter column age set default 20;





