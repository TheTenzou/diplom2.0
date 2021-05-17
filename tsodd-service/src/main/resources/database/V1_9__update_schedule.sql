ALTER TABLE schedules RENAME COLUMN data_time TO created_date;

alter table schedules add column start_date date;
alter table schedules add column end_date date;
