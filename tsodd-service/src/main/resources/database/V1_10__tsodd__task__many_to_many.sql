alter table tsodd_types
    drop constraint tsodd_type_foreign_key;
alter table tsodd_types
    drop column task_group_id;

create table tsodd_types_task_group
(
    tsodd_type_id int8 not null,
    task_group_id int8 not null
);
alter table tsodd_types_task_group
    add constraint tsodd_types_task_group__task__foreign_key foreign key (task_group_id) references task_group;
alter table tsodd_types_task_group
    add constraint tsodd_types_task_group__tsodd__foreign_key foreign key (tsodd_type_id) references tsodd_types;