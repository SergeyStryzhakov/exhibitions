create table if not exists final.exhibition_status
(
    id   int auto_increment
        primary key,
    name varchar(45) not null
);

create table if not exists final.halls
(
    id       int auto_increment
        primary key,
    name     varchar(100) not null,
    address  varchar(150) not null,
    capacity int          not null
);

create table if not exists final.themes
(
    id   int auto_increment
        primary key,
    name varchar(100) not null
);

create table if not exists final.exhibitions
(
    id          int auto_increment
        primary key,
    title       varchar(100) charset utf8mb4 not null,
    description text                         null,
    theme_id    int                          not null,
    start_date  date                         not null,
    finish_date date                         not null,
    open_time   time                         not null,
    close_time  time                         not null,
    price       int                          not null,
    image       varchar(50)                  null,
    state       varchar(20)                  not null,
    constraint fk_theme
        foreign key (theme_id) references final.themes (id)
)
    charset = utf8mb3;

create index fk_exhibition_status_idx
    on final.exhibitions (state);

create index fk_theme_idx
    on final.exhibitions (theme_id);

create table if not exists final.exhibitions_halls
(
    exhibition_id int not null,
    hall_id       int not null,
    constraint fk_exh_id
        foreign key (exhibition_id) references final.exhibitions (id)
            on delete cascade,
    constraint fk_hall_id
        foreign key (hall_id) references final.halls (id)
);

create index fk_exh_id_idx
    on final.exhibitions_halls (exhibition_id);

create index fk_hall_id_idx
    on final.exhibitions_halls (hall_id);

create table if not exists final.users
(
    id         int auto_increment
        primary key,
    login      varchar(20)      not null,
    password   text             not null,
    first_name varchar(20)      not null,
    last_name  varchar(50)      null,
    email      varchar(100)     null,
    balance    double default 0 not null,
    role       varchar(20)      not null,
    constraint login_UNIQUE
        unique (login)
);

create table if not exists final.tickets
(
    id             int auto_increment
        primary key,
    user_id        int           not null,
    exhibition_id  int           not null,
    hall_id        int           not null,
    price          int default 0 not null,
    state          varchar(20)   not null,
    operation_date timestamp     null,
    constraint fk_exhibition
        foreign key (exhibition_id) references final.exhibitions (id),
    constraint fk_hall
        foreign key (hall_id) references final.halls (id),
    constraint fk_user
        foreign key (user_id) references final.users (id)
);

create index fk_exhibition_idx
    on final.tickets (exhibition_id);

create index fk_status_idx
    on final.tickets (state);

create index fk_user_idx
    on final.tickets (user_id);

