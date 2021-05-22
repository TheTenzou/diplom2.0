ALTER TABLE scheduled_tasks
    ALTER COLUMN date_time TYPE DATE;

ALTER TABLE scheduled_tasks
    RENAME COLUMN date_time TO date;