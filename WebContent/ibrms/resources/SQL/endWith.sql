create or replace function endWith(str1 in varchar2,str2 in varchar2) 
return number as
 res number;
 startIndex number;
 len number;
 
 begin 
 
     startIndex:=length(str1)-length(str2)+1;
     len:=length(str2);
     
     if(substr(str1,startIndex,len)=str2) then
        res:=1;
     else 
        res:=0;
     end if;
        
     return res;
 end ;