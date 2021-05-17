create table crews (id int8 not null, name varchar(255), primary key (id));

alter table scheduled_tasks add column crew_id int8;
alter table scheduled_tasks add constraint scheduled_tasks__crew__foreign_key foreign key (crew_id) references crews;