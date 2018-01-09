create or replace function instrExt(strColumn in varchar2,
                                    strInput in varchar2,
                                    flag in number)
  return number as con number;
begin
  if (flag = 1) then
    select count(*)
      into con
      from dual
     where strInput in
           (select distinct comcode
              from ldcom
             start with instr(strColumn, ';' || upcomcode || ';') > 0
            connect by nocycle prior comcode = upcomcode  union all
            select distinct comcode from ldcom where 
            instr(strColumn, ';' || comcode || ';')>0 );
  else
    con := instr(strColumn, strInput);
  end if;

  return con;
end;
