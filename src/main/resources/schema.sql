create table if not exists deposit  (
    id serial primary key,
    datetime timestamp with time zone not null,
    amount numeric(35,18) not null
);