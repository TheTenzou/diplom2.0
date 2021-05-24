create sequence hibernate_sequence start 1 increment 1;

create table crews
(
    id   int8 not null,
    name varchar(255),
    primary key (id)
);
create table scheduled_tasks
(
    id           int8 not null,
    date         date,
    crew_id      int8,
    schedule_id  int8,
    task_type_id int8,
    tsodd_id     int8,
    primary key (id)
);
create table schedules
(
    id           int8 not null,
    created_date timestamp,
    end_date     date,
    name         varchar(255),
    start_date   date,
    primary key (id)
);
create table task_group
(
    id   int8 not null,
    name varchar(255),
    primary key (id)
);
create table task_types
(
    id             int8 not null,
    duration_hours int4,
    effectiveness  int4,
    money          float8,
    name           varchar(255),
    time_interval  int4,
    task_group_id  int8,
    primary key (id)
);
create table tasks
(
    id           int8 not null,
    date         timestamp,
    task_type_id int8,
    tsodd_id     int8,
    primary key (id)
);
create table tsodd_conditions
(
    id   int8 not null,
    name varchar(255),
    primary key (id)
);
create table tsodd_groups
(
    id   int8 not null,
    name varchar(255),
    primary key (id)
);
create table tsodd_groups_task_group
(
    tsodd_groups_id int8 not null,
    task_group_id   int8 not null
);
create table tsodd_types
(
    id       int8 not null,
    name     varchar(255),
    group_id int8,
    primary key (id)
);
create table tsodds
(
    id            int8 not null,
    coordinates   GEOMETRY,
    visibility    float8,
    condition_id  int8,
    tsodd_type_id int8,
    primary key (id)
);

alter table scheduled_tasks
    add constraint scheduled_tasks__crews__foreign_key foreign key (crew_id) references crews;
alter table scheduled_tasks
    add constraint scheduled_tasks__schedules__foreign_key foreign key (schedule_id) references schedules;
alter table scheduled_tasks
    add constraint scheduled_tasks__task_types__foreign_key foreign key (task_type_id) references task_types;
alter table scheduled_tasks
    add constraint scheduled_tasks__tsodds__foreign_key foreign key (tsodd_id) references tsodds;

alter table task_types
    add constraint task_types__task_group__foreign_key foreign key (task_group_id) references task_group;

alter table tasks
    add constraint tasks__task_types__foreign_key foreign key (task_type_id) references task_types;
alter table tasks
    add constraint tasks__tsodds__foreign_key foreign key (tsodd_id) references tsodds;

alter table tsodd_groups_task_group
    add constraint tsodd_groups_task_group__task_group__foreign_key foreign key (task_group_id) references task_group;
alter table tsodd_groups_task_group
    add constraint tsodd_groups_task_group__tsodd_groups__foreign_key foreign key (tsodd_groups_id) references tsodd_groups;

alter table tsodd_types
    add constraint tsodd_names__tsodd_groups__foreign_key foreign key (group_id) references tsodd_groups;

alter table tsodds
    add constraint tsodds__tsodd_conditions__foreign_key foreign key (condition_id) references tsodd_conditions;
alter table tsodds
    add constraint tsodds__tsodd_names__foreign_key foreign key (tsodd_type_id) references tsodd_types;