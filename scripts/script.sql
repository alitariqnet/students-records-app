create table students(
        id serial not null primary key,
        name varchar(50) not null,
        birth_date date null,
        grade varchar(2) not null,
        phone varchar(15) null
        );

