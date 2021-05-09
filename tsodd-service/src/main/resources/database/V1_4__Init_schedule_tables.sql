create table scheduled_tasks
(
    id          int8 not null,
    date_time   timestamp,
    schedule_id int8,
    task_id     int8,
    tsodd_id    int8,
    primary key (id)
);

create table schedules
(
    id        int8 not null,
    data_time timestamp,
    name      varchar(255),
    primary key (id)
);

create table schedules_scheduled_task
(
    schedule_id       int8 not null,
    scheduled_task_id int8 not null
);

alter table schedules_scheduled_task
    add constraint schedules_scheduled_task__unique unique (scheduled_task_id);

alter table scheduled_tasks
    add constraint scheduled_tasks__schedule__foreign_key foreign key (schedule_id) references schedules;

alter table scheduled_tasks
    add constraint scheduled_tasks__tasks__foreign_key foreign key (task_id) references tasks;

alter table scheduled_tasks
    add constraint scheduled_tasks__tsodds__foreign_key foreign key (tsodd_id) references tsodds;

alter table schedules_scheduled_task
    add constraint schedules_scheduled_task__scheduled_tasks__foreign_key foreign key (scheduled_task_id) references scheduled_tasks;

alter table schedules_scheduled_task
    add constraint schedules_scheduled_task__schedules__foreign_key foreign key (schedule_id) references schedules;
