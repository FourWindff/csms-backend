create table match_char
(
    id                      varchar(50)    not null
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

create table team
(
    id           varchar(50) not null
        primary key,
    member_count int         not null
);

create table registration_char
(
    id       varchar(50)  not null
        primary key,
    match_id varchar(255) not null,
    status   varchar(45)  null,
    team_id  varchar(50)  not null,
    constraint registration_char_team_id_fk
        foreign key (team_id) references team (id)
);

create index registration_char_match_char_id_fk
    on registration_char (match_id);

create table user_char
(
    user_id  varchar(50)  not null
        primary key,
    password varchar(255) null,
    role     varchar(45)  null
);

create table member
(
    user_id varchar(50) not null,
    team_id varchar(50) not null,
    role    varchar(50) not null,
    primary key (user_id, team_id),
    constraint member_team_id_fk
        foreign key (team_id) references team (id),
    constraint member_user_char_user_id_fk
        foreign key (user_id) references user_char (user_id)
);

create table student_char
(
    user_id         varchar(50)    not null
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
    phone           varchar(45)    null,
    constraint student_char_user_char_user_id_fk
        foreign key (user_id) references user_char (user_id)
);

create table teacher_char
(
    user_id    varchar(50)    not null
        primary key,
    username   varchar(255)   null,
    teach_age  varchar(45)    null,
    department varchar(255)   null,
    major      varchar(255)   null,
    profile    varchar(10000) null,
    phone      varchar(255)   null,
    gender     varchar(255)   null,
    age        int            null,
    constraint teacher_char_user_char_user_id_fk
        foreign key (user_id) references user_char (user_id)
);


