create or replace function strEquals(column in varchar2,input in varchar2,flag in number)
return number as 
   functionResult number:=0;
begin
   if(input=column) then
     functionResult:=1;
    else if (flag = 1) then
     select count(*)
      into functionResult
      from dual
     where input in
           (select distinct comcode
              from ldcom
             start with upcomcode=column
             connect by nocycle prior comcode = upcomcode);
     end if;
   end if;
   return functionResult;
end;