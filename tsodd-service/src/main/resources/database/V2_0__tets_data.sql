insert into tsodd_types (name, id)
values ('first type', nextval('hibernate_sequence'));
insert into tsodd_types (name, id)
values ('first type', nextval('hibernate_sequence'));
insert into tsodd_names (name, type_id, id)
values ('first tsodd name', 1, nextval('hibernate_sequence'));
insert into tsodd_names (name, type_id, id)
values ('second tsodd name', 2, nextval('hibernate_sequence'));
insert into tsodd_conditions (name, id)
values ('condition', nextval('hibernate_sequence'));
insert into tsodds (condition_id, coordinates, name_id, visibility, id)
values (5, 'GEOMETRYCOLLECTION(POINT(2 0))', 3, 1.0, nextval('hibernate_sequence'));
insert into tsodds (condition_id, coordinates, name_id, visibility, id)
values (NULL, 'GEOMETRYCOLLECTION(POINT(12 21))', 4, 0.7, nextval('hibernate_sequence'));
insert into task_group (name, id)
values ('first group', nextval('hibernate_sequence'));
insert into task_group (name, id)
values ('first group', nextval('hibernate_sequence'));
insert into tasks (duration_hours, effectiveness, money, name, task_group_id, time_interval, id)
values (2, 2, 5.0, 'first task', 8, 10, nextval('hibernate_sequence'));
insert into tasks (duration_hours, effectiveness, money, name, task_group_id, time_interval, id)
values (3, 1, 4.0, 'second task', 8, 5, nextval('hibernate_sequence'));
insert into tasks (duration_hours, effectiveness, money, name, task_group_id, time_interval, id)
values (3, 1, 4.0, 'third task', 8, 5, nextval('hibernate_sequence'));
insert into tasks (duration_hours, effectiveness, money, name, task_group_id, time_interval, id)
values (3, 1, 4.0, 'forth task', 9, 5, nextval('hibernate_sequence'));
insert into crews (name, id)
values ('first crew', nextval('hibernate_sequence'));
insert into crews (name, id)
values ('second crew', nextval('hibernate_sequence'));
insert into crews (name, id)
values ('third crew', nextval('hibernate_sequence'));
insert into tsodd_types_task_group (tsodd_type_id, task_group_id)
values (1, 8);
insert into tsodd_types_task_group (tsodd_type_id, task_group_id)
values (2, 9);