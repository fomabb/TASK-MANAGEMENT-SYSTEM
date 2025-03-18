ALTER TABLE comments
    ADD COLUMN update_at TIMESTAMP(6);

comment on column comments.update_at is 'Обновление комментария';