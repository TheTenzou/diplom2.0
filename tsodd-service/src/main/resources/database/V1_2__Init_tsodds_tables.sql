create table tsodd_conditions
(
    id   int8 not null,
    name varchar(255),
    primary key (id)
);

create table tsodd_names
(
    id      int8 not null,
    name    varchar(255),
    type_id int8,
    primary key (id)
);

create table tsodd_types
(
    id   int8 not null,
    name varchar(255),
    primary key (id)
);

create table tsodds
(
    id           int8 not null,
    coordinates  GEOMETRY,
    visibility   float8,
    condition_id int8,
    name_id      int8,
    primary key (id)
);

alter table tsodd_names
    add constraint tsodd_names__tsodd_types__foreign_key foreign key (type_id) references tsodd_types;

alter table tsodds
    add constraint tsodds__tsodd_conditions__foreign_key foreign key (condition_id) references tsodd_conditions;

alter table tsodds
    add constraint tsodds__tsodd_condition__foreign_key foreign key (name_id) references tsodd_names;
