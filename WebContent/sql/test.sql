select * from zdbranch; 
where Name='天津市武清支行';

delete from zdbranch;

update zdbranch set BranchInnerCode='120000095120000003' where  BranchInnerCode='120000003';

select * from zccatalog where parentid = (select id from zccatalog where name='2010年期刊') order by innercode desc;
select id from zccatalog where name='电子杂志期刊'; 
  
  
select * from zccatalog;

select RoleCode,'' as ParentID,'1' as TreeLevel,RoleName,
(select 'Checked' from ZDUserRole b where b.RoleCode=ZDRole.RoleCode and UserName='admin') 
as Checked from zdrole where branchinnercode =(select branchinnercode from zdrole where roleCode='admin');


select RoleCode,'' as ParentID,'1' as TreeLevel,RoleName
from zdrole
where branchinnercode like (select branchinnercode from zdrole where roleCode='admin')+'%'


select id,name,url from ZCCatalog where SiteId='229' and type=1 and parentid='0' order by orderflag asc




select ID,ParentID,TreeLevel,Name,SingleFlag,InnerCode,OrderFlag 
from ZCCatalog
Where Type = 1 and SiteID = 219 and TreeLevel-1 <=? 
order by orderflag,innercode;

select a.ID,a.Name as CatalogName,a.InnerCode,a.TreeLevel,
	(select orderflag from zccatalog where zccatalog.id=a.id) as orderflag,
	sum(case when b.id is null then 0 else 1 end) as ArticleCount,
from zccatalog a left join zcarticle b on a.id=b.catalogid 
where a.SiteID=219 and a.Type=1 
group by a.ID,a.name,a.InnerCode,a.TreeLevel 
order by orderflag


select s.id,s.name,sum(case when a.status = 30 then 1 else 0 end) as PublicCount
from zcsite s left join zcarticle a on s.id = a.siteid 
group by s.id 
order by s.id
	and a.addtime>? and a.addtime=? and a.addtime<? and a.addtime=?
where a.publiced=?
order by s.id

select * from zcarticle where siteid=256 and addtime>'20110201'

select * from zcarticle where catalogid='9285'

select * from zccatalog where id='9477'
select * from zcsite where id='238'

select name 
from zcsite 
where id in (
select siteid
from zccatalog
where id in (select right(prop4,4) from zcarticle where prop4 like '%tss%'))

select s.name s_name,c.name as c_name
from zcsite s left join zccatalog c on s.id=c.siteid 
and c.id in (select right(prop4,4) as prop4 from zcarticle where prop4 like 'tss%') 
group by s.id;

select count(*) cou,right(prop4,4) prop4 
from zcarticle where prop4 like 'tss%'
group by prop4
having count(*)>=1

select * 
from zcarticle 
where prop4 like 'tss%'


select id,title,addtime,status,right(prop4,4) prop4 
from zcarticle where prop4 like 'tss%'
and catalogid=9285
and status=30
and addtime >20101220
and addtime <20101224

select s.id ID,s.name Name,b.aprop4 Pro,b.cou Coun 
from zcsite s left join
	(select c.siteid sid,a.prop4 aprop4,a.cou cou
		from zccatalog c right join 
			(select count(*) cou,right(prop4,4) prop4 
			from zcarticle 
			where prop4 like 'tss%'
			and catalogid=9286
			and status=30
			and addtime >20101220 
			and addtime <20101224
			group by prop4 
			having count(*)>=1) a 
		on c.id= a.prop4
		group by c.siteid) b 
on s.id=b.sid
where s.id > 249
group by s.id
order by b.cou desc,s.id 

			and catalogid=9285
where s.id between 229 and 249


找到每个栏目中最后添加的一篇文章的addtime
select c.siteid,c.id cid,c.type,c.name cname,max(a.addtime) addtime 
from zccatalog c left join zcarticle a 
		on c.id=a.catalogid 
		where c.type=1 
group by c.siteid,c.id

找到所有栏目中最后添加文章距今超过三十天的栏目
select ca.siteid,ca.id,ca.name
from zccatalog ca right join 
(select c.siteid,c.id cid,c.type,c.name cname,max(a.addtime) addtime 
from zccatalog c left join zcarticle a 
		on c.id=a.catalogid and a.status=30 
		where c.type=1 
group by c.siteid,c.id) b
on ca.id = b.cid 
and datediff(now(),b.addtime)>30 
where ca.type=1
group by ca.siteid,ca.id


and datediff(now(),max(a.addtime))>30 

找出所有栏目中最后添加且发布的文章据今的天数
select ca.siteid,ca.id,ca.name,datediff(now(),b.addtime)
from zccatalog ca right join 
(select c.siteid,c.id cid,c.type,c.name cname,max(a.addtime) addtime 
from zccatalog c left join zcarticle a 
		on c.id=a.catalogid and a.status=30 
		where c.type=1 
group by c.siteid,c.id) b
on ca.id = b.cid 
where ca.type=1
group by ca.siteid,ca.id


update zcmessage set content='111111111111' 
where id = '2699' in (
select * from zcmessage  where readflag='0' and  content like '%重点新闻-领导活动栏目%')

select id,touser from zcmessage where id='2699'







