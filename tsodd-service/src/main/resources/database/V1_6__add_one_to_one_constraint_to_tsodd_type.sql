alter table tsodd_types add column task_group_id int8;
alter table tsodd_types add constraint tsodd_type_foreign_key foreign key (task_group_id) references task_group;