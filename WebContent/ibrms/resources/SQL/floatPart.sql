create or replace function floatPart(num in number) 
return number
as
res number;
integerPart number;

begin
  integerPart:=floor(num); 
  
  res:=num-integerPart;
  
  return res;
  
end;