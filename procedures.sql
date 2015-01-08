CREATE PROCEDURE deleteboxers()
procedimiento:begin
    DELETE FROM boxer;
end procedimiento;

create function win_boxer()
returns int
begin

    return (select id from boxer where id = (select max(wins)
            from boxer));

end
