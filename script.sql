create table match_char
(
    id                      varchar(255)   not null
        primary key,
    type                    varchar(255)   null,
    name                    varchar(255)   null,
    place                   varchar(255)   null,
    competition_start_time  varchar(500)   null,
    competition_end_time    varchar(500)   null,
    registration_start_time varchar(500)   null,
    registration_end_time   varchar(500)   null,
    description             varchar(10000) null
);

create table registration_char
(
    id         varchar(50)  not null
        primary key,
    student_id varchar(255) null,
    match_id   varchar(255) null,
    teacher_id varchar(255) null,
    status     varchar(45)  null
);

create table student_char
(
    user_id         varchar(555)   not null
        primary key,
    username        varchar(225)   null,
    identity_number varchar(225)   null,
    gender          varchar(225)   null,
    age             int            null,
    grade           varchar(255)   null,
    student_class   varchar(255)   null,
    major           varchar(255)   null,
    department      varchar(255)   null,
    profile         varchar(10000) null,
    phone           varchar(45)    null
);

create table teacher_char
(
    user_id    varchar(255)   not null
        primary key,
    username   varchar(255)   null,
    teach_age  varchar(45)    null,
    department varchar(255)   null,
    major      varchar(255)   null,
    profile    varchar(10000) null,
    phone      varchar(255)   null,
    gender     varchar(255)   null,
    age        int            null
);

create table user_char
(
    user_id  varchar(255) not null
        primary key,
    password varchar(255) null,
    role     varchar(45)  null
);


