create table localization (
  id bigserial not null,
  name varchar(50) not null,
  description varchar(512) not null,
  primary key (id)
);

create table category (
  id bigserial not null,
  name varchar(50) not null,
  description varchar(512) not null,
  primary key (id)
);

create table item (
  id bigserial not null,
  localization_id bigint references localization (id),
  category_id bigint references category (id),
  name varchar(50) not null,
  description varchar(512) not null,
  primary key (id)
);