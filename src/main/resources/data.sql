insert into account (id) values (1);
insert into account (id) values (2);

-- Load Account 1 with money (Source: 42, Target: 1)
insert into activity (id, timestamp, owner_account_id, source_account_id, target_account_id, amount)
values (1001, '2018-08-08 08:00:00.0', 1, 42, 1, 499);

-- Load Account 2 with money (Source: 42, Target: 2)
insert into activity (id, timestamp, owner_account_id, source_account_id, target_account_id, amount)
values (1002, '2018-08-08 08:00:00.0', 2, 42, 2, 499);
