create table crews_task_type_list
(
    crew_id           int8 not null,
    task_type_list_id int8 not null
);
create table task_types_crew_list
(
    task_type_id int8 not null,
    crew_list_id int8 not null
);

alter table crews_task_type_list
    add constraint crew_task_type_fk foreign key (task_type_list_id) references task_types;
alter table crews_task_type_list
    add constraint crew_crew_fk foreign key (crew_id) references crews;
alter table task_types_crew_list
    add constraint task_type_crew_fk foreign key (crew_list_id) references crews;
alter table task_types_crew_list
    add constraint task_type_task_type_fk foreign key (task_type_id) references task_types;