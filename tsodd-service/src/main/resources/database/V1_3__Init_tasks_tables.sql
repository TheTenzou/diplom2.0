create table task_group
(
    id   int8 not null,
    name varchar(255),
    primary key (id)
);

create table tasks
(
    id            int8 not null,
    name          varchar(255),
    task_group_id int8,
    primary key (id)
);

create table completed_tasks
(
    id       int8 not null,
    date     timestamp,
    task_id  int8,
    tsodd_id int8,
    primary key (id)
);

alter table tasks
    add constraint tasks__task_group__foreign_key foreign key (task_group_id) references task_group;

alter table completed_tasks
    add constraint completed_tasks__tasks__foreign_key foreign key (task_id) references tasks;

alter table completed_tasks
    add constraint completed_tasks__tsodds__foreign_key foreign key (tsodd_id) references tsodds;
