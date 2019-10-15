create table localization (
  id bigserial not null,
  title varchar(50) not null,
  description varchar(512) not null,
  primary key (id)
);

create table asset (
  id bigserial not null,
  localization_id bigint not null references localization (id),
  title varchar(50) not null,
  description varchar(512) not null,
  primary key (id)
);