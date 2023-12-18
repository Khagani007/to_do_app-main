insert into users(

    name,
    email,
    password,
    is_active,
    username,
    account_non_expired,
    account_non_locked,
    credentials_non_expired ,
    enabled,
    uuid,
    attempt_count,
    otp_code
)
values (
        'Ali',
        'test@test.com',
        '$2a$12$F7ro2BXCWqpSpnQ21UWgfe0JXy2DNiBcxRuHFfxOtZ98HDY0LvvaW',
        true,
        null,
        true,
        true,
        true,
        true,
        null,
        0,
        null
       );


insert into permissions(id,name)
values (1,'CREATE_USER'),(2,'READ_USER');


insert into roles(id,name)
values (1,'ROLE_ADMIN');
insert into roles_permission(role_id, permission_id)
values (1,1),(1,2);
insert into users_roles(user_id, roles_id)
values (1,1);