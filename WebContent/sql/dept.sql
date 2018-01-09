select * from zdbranch where BranchInnerCode='120000095120000003';
update zdbranch set BranchInnerCode='120000095120000003' where  BranchInnerCode='120000003';
delete from zdbranch;

wisecodecmstest 成功

INSERT INTO zdbranch(BranchInnerCode, BranchCode, ParentInnerCode, Type, OrderFlag, Name, TreeLevel, IsLeaf, Phone, Fax, Manager, Leader1, 
Leader2, Prop1, Prop2, Prop3, Prop4, Memo, AddTime, AddUser, ModifyTime, ModifyUser)
  VALUES('120000095', '2120000000000000D', '0', '0', 95, '天津市分行', 1, 'N', NULL, NULL, 'admin', 'administrator', 'admin',
   '02', '120000095', NULL, NULL, NULL, '20100824', 'admin', NULL, NULL);

INSERT INTO zdbranch(BranchInnerCode, BranchCode, ParentInnerCode, Type, OrderFlag, Name, TreeLevel, IsLeaf, Phone, Fax, Manager, Leader1, 
Leader2, Prop1, Prop2, Prop3, Prop4, Memo, AddTime, AddUser, ModifyTime, ModifyUser)
  VALUES('120000095120000001', '21200010000000002', '120000095', '0', 1, '分行机关', 2, 'Y', NULL, NULL, 'admin', 'administrator', 'admin',
   '02', '120000001', NULL, NULL, NULL, '20100824', 'admin', NULL, NULL);

INSERT INTO zdbranch(BranchInnerCode, BranchCode, ParentInnerCode, Type, OrderFlag, Name, TreeLevel, IsLeaf, Phone, Fax, Manager, Leader1, 
Leader2, Prop1, Prop2, Prop3, Prop4, Memo, AddTime, AddUser, ModifyTime, ModifyUser)
  VALUES('120000095120000001120000002', '2120001000010000R', '120000095120000001', '0', 2, '分行其他', 3, 'Y', NULL, NULL, 'admin', 'administrator', 'admin',
   '02', '120000002', NULL, NULL, NULL, '20100824', 'admin', NULL, NULL);
   
INSERT INTO zdbranch(BranchInnerCode, BranchCode, ParentInnerCode, Type, OrderFlag, Name, TreeLevel, IsLeaf, Phone, Fax, Manager, Leader1, 
Leader2, Prop1, Prop2, Prop3, Prop4, Memo, AddTime, AddUser, ModifyTime, ModifyUser)
  VALUES('120000095120000001120000003', '2120001000008000O', '120000095120000001', '0', 3, '分行办公室', 3, 'Y', NULL, NULL, 'admin', 'administrator', 'admin',
   '02', '120000003', NULL, NULL, NULL, '20100824', 'admin', NULL, NULL);
   
INSERT INTO zdbranch(BranchInnerCode, BranchCode, ParentInnerCode, Type, OrderFlag, Name, TreeLevel, IsLeaf, Phone, Fax, Manager, Leader1, 
Leader2, Prop1, Prop2, Prop3, Prop4, Memo, AddTime, AddUser, ModifyTime, ModifyUser)
  VALUES('120000095120000001120000004', '2120001000009000J', '120000095120000001', '0', 4, '分行内控合规部', 3, 'Y', NULL, NULL, 'admin', 'administrator', 'admin',
   '02', '120000004', NULL, NULL, NULL, '20100824', 'admin', NULL, NULL);
   
INSERT INTO zdbranch(BranchInnerCode, BranchCode, ParentInnerCode, Type, OrderFlag, Name, TreeLevel, IsLeaf, Phone, Fax, Manager, Leader1, 
Leader2, Prop1, Prop2, Prop3, Prop4, Memo, AddTime, AddUser, ModifyTime, ModifyUser)
  VALUES('120000095120000001120000005', '2120001000011000M', '120000095120000001', '0', 5, '分行拓展部', 3, 'Y', NULL, NULL, 'admin', 'administrator', 'admin', 
  '02', '120000005', NULL, NULL, NULL, '20100824', 'admin', NULL, NULL);
  
INSERT INTO zdbranch(BranchInnerCode, BranchCode, ParentInnerCode, Type, OrderFlag, Name, TreeLevel, IsLeaf, Phone, Fax, Manager, Leader1, 
Leader2, Prop1, Prop2, Prop3, Prop4, Memo, AddTime, AddUser, ModifyTime, ModifyUser)
  VALUES('120000095120000001120000006', '2120001000001000S', '120000095120000001', '0', 6, '分行行长室', 3, 'Y', NULL, NULL, 'admin', 'administrator', 'admin',
   '02', '120000006', NULL, NULL, NULL, '20100824', 'admin', NULL, NULL);
   
INSERT INTO zdbranch(BranchInnerCode, BranchCode, ParentInnerCode, Type, OrderFlag, Name, TreeLevel, IsLeaf, Phone, Fax, Manager, Leader1, 
Leader2, Prop1, Prop2, Prop3, Prop4, Memo, AddTime, AddUser, ModifyTime, ModifyUser)
  VALUES('120000095120000001120000007', '2120001000002000N', '120000095120000001', '0', 7, '分行内退人员', 3, 'Y', NULL, NULL, 'admin', 'administrator', 'admin',
   '02', '120000007', NULL, NULL, NULL, '20100824', 'admin', NULL, NULL);
   
INSERT INTO zdbranch(BranchInnerCode, BranchCode, ParentInnerCode, Type, OrderFlag, Name, TreeLevel, IsLeaf, Phone, Fax, Manager, Leader1, 
Leader2, Prop1, Prop2, Prop3, Prop4, Memo, AddTime, AddUser, ModifyTime, ModifyUser)
  VALUES('120000095120000001120000008', '2120001000003000I', '120000095120000001', '0', 8, '分行培训中心', 3, 'Y', NULL, NULL, 'admin', 'administrator', 'admin',
   '02', '120000008', NULL, NULL, NULL, '20100824', 'admin', NULL, NULL);
   
INSERT INTO zdbranch(BranchInnerCode, BranchCode, ParentInnerCode, Type, OrderFlag, Name, TreeLevel, IsLeaf, Phone, Fax, Manager, Leader1, 
Leader2, Prop1, Prop2, Prop3, Prop4, Memo, AddTime, AddUser, ModifyTime, ModifyUser)
  VALUES('120000095120000001120000009', '2120001000004000D', '120000095120000001', '0', 9, '分行其他人员', 3, 'Y', NULL, NULL, 'admin', 'administrator', 'admin', 
  '02', '120000009', NULL, NULL, NULL, '20100824', 'admin', NULL, NULL);
  
INSERT INTO zdbranch(BranchInnerCode, BranchCode, ParentInnerCode, Type, OrderFlag, Name, TreeLevel, IsLeaf, Phone, Fax, Manager, Leader1, 

Leader2, Prop1, Prop2, Prop3, Prop4, Memo, AddTime, AddUser, ModifyTime, ModifyUser)
  VALUES('120000095120000001120000010', '21200010000050008', '120000095120000001', '0', 10, '分行保险代理部', 3, 'Y', NULL, NULL, 'admin', 'administrator', 'admin', '02', 

'120000010', NULL, NULL, NULL, 

'20100824', 'admin', NULL, NULL)
;


INSERT INTO zdbranch(BranchInnerCode, BranchCode, ParentInnerCode, Type, OrderFlag, Name, TreeLevel, IsLeaf, Phone, Fax, Manager, Leader1, 

Leader2, Prop1, Prop2, Prop3, Prop4, Memo, AddTime, AddUser, ModifyTime, ModifyUser)
  VALUES('120000095120000001120000011', '21200010000060003', '120000095120000001', '0', 11, '分行财务核算部', 3, 'Y', NULL, NULL, 'admin', 'administrator', 'admin', '02', 

'120000011', NULL, NULL, NULL, 

'20100824', 'admin', NULL, NULL)
;
INSERT INTO zdbranch(BranchInnerCode, BranchCode, ParentInnerCode, Type, OrderFlag, Name, TreeLevel, IsLeaf, Phone, Fax, Manager, Leader1, 

Leader2, Prop1, Prop2, Prop3, Prop4, Memo, AddTime, AddUser, ModifyTime, ModifyUser)
  VALUES('120000095120000001120000012', '2120001000007000T', '120000095120000001', '0', 12, '分行电子银行部', 3, 'Y', NULL, NULL, 'admin', 'administrator', 'admin', '02', 

'120000012', NULL, NULL, NULL, 

'20100824', 'admin', NULL, NULL)
;
INSERT INTO zdbranch(BranchInnerCode, BranchCode, ParentInnerCode, Type, OrderFlag, Name, TreeLevel, IsLeaf, Phone, Fax, Manager, Leader1, 

Leader2, Prop1, Prop2, Prop3, Prop4, Memo, AddTime, AddUser, ModifyTime, ModifyUser)
  VALUES('120000095120000001120000013', '2120001000012000H', '120000095120000001', '0', 13, '分行风险资产部', 3, 'Y', NULL, NULL, 'admin', 'administrator', 'admin', '02', 

'120000013', NULL, NULL, NULL, 

'20100824', 'admin', NULL, NULL)
;
INSERT INTO zdbranch(BranchInnerCode, BranchCode, ParentInnerCode, Type, OrderFlag, Name, TreeLevel, IsLeaf, Phone, Fax, Manager, Leader1, 

Leader2, Prop1, Prop2, Prop3, Prop4, Memo, AddTime, AddUser, ModifyTime, ModifyUser)
  VALUES('120000095120000001120000014', '2120001000013000C', '120000095120000001', '0', 14, '分行个人金融部', 3, 'Y', NULL, NULL, 'admin', 'administrator', 'admin', '02', 

'120000014', NULL, NULL, NULL, 

'20100824', 'admin', NULL, NULL)
;
INSERT INTO zdbranch(BranchInnerCode, BranchCode, ParentInnerCode, Type, OrderFlag, Name, TreeLevel, IsLeaf, Phone, Fax, Manager, Leader1, 

Leader2, Prop1, Prop2, Prop3, Prop4, Memo, AddTime, AddUser, ModifyTime, ModifyUser)
  VALUES('120000095120000001120000015', '21200010000140007', '120000095120000001', '0', 15, '分行公司业务部', 3, 'Y', NULL, NULL, 'admin', 'administrator', 'admin', '02', 

'120000015', NULL, NULL, NULL, 

'20100824', 'admin', NULL, NULL)
;
INSERT INTO zdbranch(BranchInnerCode, BranchCode, ParentInnerCode, Type, OrderFlag, Name, TreeLevel, IsLeaf, Phone, Fax, Manager, Leader1, 

Leader2, Prop1, Prop2, Prop3, Prop4, Memo, AddTime, AddUser, ModifyTime, ModifyUser)
  VALUES('120000095120000001120000016', '21200010000150002', '120000095120000001', '0', 16, '分行国际业务部', 3, 'Y', NULL, NULL, 'admin', 'administrator', 'admin', '02', 

'120000016', NULL, NULL, NULL, 

'20100824', 'admin', NULL, NULL)
;
INSERT INTO zdbranch(BranchInnerCode, BranchCode, ParentInnerCode, Type, OrderFlag, Name, TreeLevel, IsLeaf, Phone, Fax, Manager, Leader1, 

Leader2, Prop1, Prop2, Prop3, Prop4, Memo, AddTime, AddUser, ModifyTime, ModifyUser)
  VALUES('120000095120000001120000017', '2120001000016000S', '120000095120000001', '0', 17, '分行机构业务部', 3, 'Y', NULL, NULL, 'admin', 'administrator', 'admin', '02', 

'120000017', NULL, NULL, NULL, 

'20100824', 'admin', NULL, NULL)
;
INSERT INTO zdbranch(BranchInnerCode, BranchCode, ParentInnerCode, Type, OrderFlag, Name, TreeLevel, IsLeaf, Phone, Fax, Manager, Leader1, 

Leader2, Prop1, Prop2, Prop3, Prop4, Memo, AddTime, AddUser, ModifyTime, ModifyUser)
  VALUES('120000095120000001120000018', '2120001000017000N', '120000095120000001', '0', 18, '分行计划财会部', 3, 'Y', NULL, NULL, 'admin', 'administrator', 'admin', '02', 

'120000018', NULL, NULL, NULL, 

'20100824', 'admin', NULL, NULL)
;
INSERT INTO zdbranch(BranchInnerCode, BranchCode, ParentInnerCode, Type, OrderFlag, Name, TreeLevel, IsLeaf, Phone, Fax, Manager, Leader1, 

Leader2, Prop1, Prop2, Prop3, Prop4, Memo, AddTime, AddUser, ModifyTime, ModifyUser)
  VALUES('120000095120000001120000019', '2120001000018000I', '120000095120000001', '0', 19, '分行监察部', 3, 'Y', NULL, NULL, 'admin', 'administrator', 'admin', '02', '120000019', 

NULL, NULL, NULL, '20100824', 'admin', NULL, NULL);
INSERT INTO zdbranch(BranchInnerCode, BranchCode, ParentInnerCode, Type, OrderFlag, Name, TreeLevel, IsLeaf, Phone, Fax, Manager, Leader1, 

Leader2, Prop1, Prop2, Prop3, Prop4, Memo, AddTime, AddUser, ModifyTime, ModifyUser)
  VALUES('120000095120000001120000020', '2120001000019000D', '120000095120000001', '0', 20, '分行离退休人员', 3, 'Y', NULL, NULL, 'admin', 'administrator', 'admin', '02', 

'120000020', NULL, NULL, NULL, 

'20100824', 'admin', NULL, NULL)
;
INSERT INTO zdbranch(BranchInnerCode, BranchCode, ParentInnerCode, Type, OrderFlag, Name, TreeLevel, IsLeaf, Phone, Fax, Manager, Leader1, 

Leader2, Prop1, Prop2, Prop3, Prop4, Memo, AddTime, AddUser, ModifyTime, ModifyUser)
  VALUES('120000095120000001120000021', '2120001000020000L', '120000095120000001', '0', 21, '分行零售业务部', 3, 'Y', NULL, NULL, 'admin', 'administrator', 'admin', '02', 

'120000021', NULL, NULL, NULL, 

'20100824', 'admin', NULL, NULL)
;
INSERT INTO zdbranch(BranchInnerCode, BranchCode, ParentInnerCode, Type, OrderFlag, Name, TreeLevel, IsLeaf, Phone, Fax, Manager, Leader1, 

Leader2, Prop1, Prop2, Prop3, Prop4, Memo, AddTime, AddUser, ModifyTime, ModifyUser)
  VALUES('120000095120000001120000022', '2120001000021000G', '120000095120000001', '0', 22, '分行人力资源部', 3, 'Y', NULL, NULL, 'admin', 'administrator', 'admin', '02', 

'120000022', NULL, NULL, NULL, 

'20100824', 'admin', NULL, NULL)
;
INSERT INTO zdbranch(BranchInnerCode, BranchCode, ParentInnerCode, Type, OrderFlag, Name, TreeLevel, IsLeaf, Phone, Fax, Manager, Leader1, 

Leader2, Prop1, Prop2, Prop3, Prop4, Memo, AddTime, AddUser, ModifyTime, ModifyUser)
  VALUES('120000095120000001120000023', '2120001000022000B', '120000095120000001', '0', 23, '分行信贷管理部', 3, 'Y', NULL, NULL, 'admin', 'administrator', 'admin', '02', 

'120000023', NULL, NULL, NULL, 

'20100824', 'admin', NULL, NULL)
;
INSERT INTO zdbranch(BranchInnerCode, BranchCode, ParentInnerCode, Type, OrderFlag, Name, TreeLevel, IsLeaf, Phone, Fax, Manager, Leader1, 

Leader2, Prop1, Prop2, Prop3, Prop4, Memo, AddTime, AddUser, ModifyTime, ModifyUser)
  VALUES('120000095120000001120000024', '21200010000230006', '120000095120000001', '0', 24, '分行信用卡中心', 3, 'Y', NULL, NULL, 'admin', 'administrator', 'admin', '02', 

'120000024', NULL, NULL, NULL, 

'20100824', 'admin', NULL, NULL)
;
INSERT INTO zdbranch(BranchInnerCode, BranchCode, ParentInnerCode, Type, OrderFlag, Name, TreeLevel, IsLeaf, Phone, Fax, Manager, Leader1, 

Leader2, Prop1, Prop2, Prop3, Prop4, Memo, AddTime, AddUser, ModifyTime, ModifyUser)
  VALUES('120000095120000001120000025', '21200010000240001', '120000095120000001', '0', 25, '分行房地产信贷部', 3, 'Y', NULL, NULL, 'admin', 'administrator', 'admin', '02', 

'120000025', NULL, NULL, NULL, 

'20100824', 'admin', NULL, NULL)
;
INSERT INTO zdbranch(BranchInnerCode, BranchCode, ParentInnerCode, Type, OrderFlag, Name, TreeLevel, IsLeaf, Phone, Fax, Manager, Leader1, 

Leader2, Prop1, Prop2, Prop3, Prop4, Memo, AddTime, AddUser, ModifyTime, ModifyUser)
  VALUES('120000095120000001120000026', '2120001000025000R', '120000095120000001', '0', 26, '分行房地产信贷部', 3, 'Y', NULL, NULL, 'admin', 'administrator', 'admin', '02', 

'120000026', NULL, NULL, NULL, 

'20100824', 'admin', NULL, NULL)
;
INSERT INTO zdbranch(BranchInnerCode, BranchCode, ParentInnerCode, Type, OrderFlag, Name, TreeLevel, IsLeaf, Phone, Fax, Manager, Leader1, 

Leader2, Prop1, Prop2, Prop3, Prop4, Memo, AddTime, AddUser, ModifyTime, ModifyUser)
  VALUES('120000095120000001120000027', '2120001000026000M', '120000095120000001', '0', 27, '分行总务部', 3, 'Y', NULL, NULL, 'admin', 'administrator', 'admin', '02', '120000027', 

NULL, NULL, NULL, '20100824', 'admin', NULL, NULL);
INSERT INTO zdbranch(BranchInnerCode, BranchCode, ParentInnerCode, Type, OrderFlag, Name, TreeLevel, IsLeaf, Phone, Fax, Manager, Leader1, 

Leader2, Prop1, Prop2, Prop3, Prop4, Memo, AddTime, AddUser, ModifyTime, ModifyUser)
  VALUES('120000095120000001120000028', '2120001000027000H', '120000095120000001', '0', 28, '分行信息技术管理部', 3, 'Y', NULL, NULL, 'admin', 'administrator', 'admin', '02', 

'120000028', NULL, NULL, NULL, 

'20100824', 'admin', NULL, NULL)
;
INSERT INTO zdbranch(BranchInnerCode, BranchCode, ParentInnerCode, Type, OrderFlag, Name, TreeLevel, IsLeaf, Phone, Fax, Manager, Leader1, 

Leader2, Prop1, Prop2, Prop3, Prop4, Memo, AddTime, AddUser, ModifyTime, ModifyUser)
  VALUES('120000095120000001120000029', '2120001000028000C', '120000095120000001', '0', 29, '分行资产处置部', 3, 'Y', NULL, NULL, 'admin', 'administrator', 'admin', '02', 

'120000029', NULL, NULL, NULL, 

'20100824', 'admin', NULL, NULL)
;
INSERT INTO zdbranch(BranchInnerCode, BranchCode, ParentInnerCode, Type, OrderFlag, Name, TreeLevel, IsLeaf, Phone, Fax, Manager, Leader1, 

Leader2, Prop1, Prop2, Prop3, Prop4, Memo, AddTime, AddUser, ModifyTime, ModifyUser)
  VALUES('120000095120000001120000030', '2120999119134000I', '120000095120000001', '0', 30, '静海县大邱庄支行', 3, 'Y', NULL, NULL, 'admin', 'administrator', 'admin', '06', 

'120000030', NULL, NULL, NULL, 

'20100824', 'admin', NULL, NULL)
;
INSERT INTO zdbranch(BranchInnerCode, BranchCode, ParentInnerCode, Type, OrderFlag, Name, TreeLevel, IsLeaf, Phone, Fax, Manager, Leader1, 

Leader2, Prop1, Prop2, Prop3, Prop4, Memo, AddTime, AddUser, ModifyTime, ModifyUser)
  VALUES('120000095120000135120000031', '21209991031260009', '120000095120000135', '0', 31, '金信支行', 3, 'Y', NULL, NULL, 'admin', 'administrator', 'admin', '06', '120000031', 

NULL, NULL, NULL, '20100824', 'admin', NULL, NULL)
;
INSERT INTO zdbranch(BranchInnerCode, BranchCode, ParentInnerCode, Type, OrderFlag, Name, TreeLevel, IsLeaf, Phone, Fax, Manager, Leader1, 

Leader2, Prop1, Prop2, Prop3, Prop4, Memo, AddTime, AddUser, ModifyTime, ModifyUser)
  VALUES('120000095120000637120000658120000032', '2120999118102101H', '120000095120000637120000658', '0', 32, '分行蓟县支行津承路储蓄所', 4, 'Y', NULL, NULL, 'admin', 'administrator', 'admin', 

'10', '120000032', NULL, NULL, 

NULL, '20100824', 'admin', NULL, NULL)
;
INSERT INTO zdbranch(BranchInnerCode, BranchCode, ParentInnerCode, Type, OrderFlag, Name, TreeLevel, IsLeaf, Phone, Fax, Manager, Leader1, 

Leader2, Prop1, Prop2, Prop3, Prop4, Memo, AddTime, AddUser, ModifyTime, ModifyUser)
  VALUES('120000095120000233120000033', '21209991061190007', '120000095120000233', '0', 33, '河北区金益分理处', 3, 'Y', NULL, NULL, 'admin', 'administrator', 'admin', '06', 

'120000033', NULL, NULL, NULL, 

'20100824', 'admin', NULL, NULL)
;
INSERT INTO zdbranch(BranchInnerCode, BranchCode, ParentInnerCode, Type, OrderFlag, Name, TreeLevel, IsLeaf, Phone, Fax, Manager, Leader1, 

Leader2, Prop1, Prop2, Prop3, Prop4, Memo, AddTime, AddUser, ModifyTime, ModifyUser)
  VALUES('120000095120000102120000034', '2120999102110000M', '120000095120000102', '0', 34, '芥园西道支行', 3, 'Y', NULL, NULL, 'admin', 'administrator', 'admin', '06', '120000034', 

NULL, NULL, NULL, 

'20100824', 'admin', NULL, NULL)
;
INSERT INTO zdbranch(BranchInnerCode, BranchCode, ParentInnerCode, Type, OrderFlag, Name, TreeLevel, IsLeaf, Phone, Fax, Manager, Leader1, 

Leader2, Prop1, Prop2, Prop3, Prop4, Memo, AddTime, AddUser, ModifyTime, ModifyUser)
  VALUES('120000095120000135120000098120000035', '2120999103105000Q', '120000095120000135120000098', '0', 35, '分行解放路支行新兆路分理处', 4, 'Y', NULL, NULL, 'admin', 'administrator', 

'admin', '06', '120000035', NULL, NULL, 

NULL, '20100824', 'admin', NULL, NULL)
;
INSERT INTO zdbranch(BranchInnerCode, BranchCode, ParentInnerCode, Type, OrderFlag, Name, TreeLevel, IsLeaf, Phone, Fax, Manager, Leader1, 

Leader2, Prop1, Prop2, Prop3, Prop4, Memo, AddTime, AddUser, ModifyTime, ModifyUser)
  VALUES('120000095120000135120000098120000036', '2120999103106000L', '120000095120000135120000098', '0', 36, '分行解放路支行三马路分理处', 4, 'Y', NULL, NULL, 'admin', 'administrator', 

'admin', '06', '120000036', NULL, NULL, 

NULL, '20100824', 'admin', NULL, NULL)
;
INSERT INTO zdbranch(BranchInnerCode, BranchCode, ParentInnerCode, Type, OrderFlag, Name, TreeLevel, IsLeaf, Phone, Fax, Manager, Leader1, 

Leader2, Prop1, Prop2, Prop3, Prop4, Memo, AddTime, AddUser, ModifyTime, ModifyUser)
  VALUES('120000095120000925120000037', '2120999127107000S', '120000095120000925', '0', 37, '空港物流加工区支行', 3, 'Y', NULL, NULL, 'admin', 'administrator', 'admin', '06', 

'120000037', NULL, NULL, NULL, 

'20100824', 'admin', NULL, NULL)
;
INSERT INTO zdbranch(BranchInnerCode, BranchCode, ParentInnerCode, Type, OrderFlag, Name, TreeLevel, IsLeaf, Phone, Fax, Manager, Leader1, 

Leader2, Prop1, Prop2, Prop3, Prop4, Memo, AddTime, AddUser, ModifyTime, ModifyUser)
  VALUES('120000095120000135120000098120000038', '2120999103107000G', '120000095120000135120000098', '0', 38, '黑牛城东道支行', 4, 'Y', NULL, NULL, 'admin', 'administrator', 'admin', '06', '120000038', 

NULL, NULL, NULL, 

'20100824', 'admin', NULL, NULL)
;
INSERT INTO zdbranch(BranchInnerCode, BranchCode, ParentInnerCode, Type, OrderFlag, Name, TreeLevel, IsLeaf, Phone, Fax, Manager, Leader1, 

Leader2, Prop1, Prop2, Prop3, Prop4, Memo, AddTime, AddUser, ModifyTime, ModifyUser)
  VALUES('120000095120000199120000039', '2120999105118000K', '120000095120000199', '0', 39, '河东支行津塘路分理处', 3, 'Y', NULL, NULL, 'admin', 'administrator', 'admin', 

'06', '120000039', NULL, NULL, NULL, 

'20100824', 'admin', NULL, NULL)
;
INSERT INTO zdbranch(BranchInnerCode, BranchCode, ParentInnerCode, Type, OrderFlag, Name, TreeLevel, IsLeaf, Phone, Fax, Manager, Leader1, 

Leader2, Prop1, Prop2, Prop3, Prop4, Memo, AddTime, AddUser, ModifyTime, ModifyUser)
  VALUES('120000095120000199120000040', '2120999105109000L', '120000095120000199', '0', 40, '分行河东支行大直沽分理处', 3, 'Y', NULL, NULL, 'admin', 'administrator', 'admin', 

'06', '120000040', NULL, NULL, 

NULL, '20100824', 'admin', NULL, NULL)
;
INSERT INTO zdbranch(BranchInnerCode, BranchCode, ParentInnerCode, Type, OrderFlag, Name, TreeLevel, IsLeaf, Phone, Fax, Manager, Leader1, 

Leader2, Prop1, Prop2, Prop3, Prop4, Memo, AddTime, AddUser, ModifyTime, ModifyUser)
  VALUES('120000095120000165120000041', '21209991041110001', '120000095120000165', '0', 41, '河西区浦口道分理处', 3, 'Y', NULL, NULL, 'admin', 'administrator', 'admin', '06', 

'120000041', NULL, NULL, NULL, 

'20100824', 'admin', NULL, NULL)
;
INSERT INTO zdbranch(BranchInnerCode, BranchCode, ParentInnerCode, Type, OrderFlag, Name, TreeLevel, IsLeaf, Phone, Fax, Manager, Leader1, 

Leader2, Prop1, Prop2, Prop3, Prop4, Memo, AddTime, AddUser, ModifyTime, ModifyUser)
  VALUES('120000095120000165120000042', '2120999104113000M', '120000095120000165', '0', 42, '宾水道支行', 3, 'Y', NULL, NULL, 'admin', 'administrator', 'admin', '06', '120000042', 

NULL, NULL, NULL, '20100824', 'admin', NULL, NULL);
INSERT INTO zdbranch(BranchInnerCode, BranchCode, ParentInnerCode, Type, OrderFlag, Name, TreeLevel, IsLeaf, Phone, Fax, Manager, Leader1, 

Leader2, Prop1, Prop2, Prop3, Prop4, Memo, AddTime, AddUser, ModifyTime, ModifyUser)
  VALUES('120000095120000233120000043', '2120999106112000B', '120000095120000233', '0', 43, '河北区金丰分理处', 3, 'Y', NULL, NULL, 'admin', 'administrator', 'admin', '06', 

'120000043', NULL, NULL, NULL, 

'20100824', 'admin', NULL, NULL)
;
INSERT INTO zdbranch(BranchInnerCode, BranchCode, ParentInnerCode, Type, OrderFlag, Name, TreeLevel, IsLeaf, Phone, Fax, Manager, Leader1, 

Leader2, Prop1, Prop2, Prop3, Prop4, Memo, AddTime, AddUser, ModifyTime, ModifyUser)
  VALUES('120000095120000233120000044', '2120999106109000D', '120000095120000233', '0', 44, '河北支行金嘉分理处', 3, 'Y', NULL, NULL, 'admin', 'administrator', 'admin', '06', 

'120000044', NULL, NULL, NULL, 

'20100824', 'admin', NULL, NULL)
;
INSERT INTO zdbranch(BranchInnerCode, BranchCode, ParentInnerCode, Type, OrderFlag, Name, TreeLevel, IsLeaf, Phone, Fax, Manager, Leader1, 

Leader2, Prop1, Prop2, Prop3, Prop4, Memo, AddTime, AddUser, ModifyTime, ModifyUser)
  VALUES('120000095120000135120000367120000045', '2120999103124000J', '120000095120000135120000367', '0', 45, '蓝水支行', 4, 'Y', NULL, NULL, 'admin', 'administrator', 'admin', '06', '120000045', NULL, 

NULL, NULL, '20100824', 'admin', NULL, NULL);
INSERT INTO zdbranch(BranchInnerCode, BranchCode, ParentInnerCode, Type, OrderFlag, Name, TreeLevel, IsLeaf, Phone, Fax, Manager, Leader1, 

Leader2, Prop1, Prop2, Prop3, Prop4, Memo, AddTime, AddUser, ModifyTime, ModifyUser)
  VALUES('120000095120000135120000367120000046', '2120999103116000F', '120000095120000135120000367', '0', 46, '西南楼支行', 4, 'Y', NULL, NULL, 'admin', 'administrator', 'admin', '06', '120000046', 

NULL, NULL, NULL, '20100824', 'admin', NULL, NULL);
INSERT INTO zdbranch(BranchInnerCode, BranchCode, ParentInnerCode, Type, OrderFlag, Name, TreeLevel, IsLeaf, Phone, Fax, Manager, Leader1, 

Leader2, Prop1, Prop2, Prop3, Prop4, Memo, AddTime, AddUser, ModifyTime, ModifyUser)
  VALUES('120000095120000329120000047', '2120999109113000D', '120000095120000329', '0', 47, '世贸支行隆昌路分理处', 3, 'Y', NULL, NULL, 'admin', 'administrator', 'admin', 

'06', '120000047', NULL, NULL, NULL, 

'20100824', 'admin', NULL, NULL)
;
INSERT INTO zdbranch(BranchInnerCode, BranchCode, ParentInnerCode, Type, OrderFlag, Name, TreeLevel, IsLeaf, Phone, Fax, Manager, Leader1, 

Leader2, Prop1, Prop2, Prop3, Prop4, Memo, AddTime, AddUser, ModifyTime, ModifyUser)
  VALUES('120000095120000329120000048', '21209991091140008', '120000095120000329', '0', 48, '世贸支行黄浦南路分理处', 3, 'Y', NULL, NULL, 'admin', 'administrator', 'admin', 

'06', '120000048', NULL, NULL, 

NULL, '20100824', 'admin', NULL, NULL)
;
INSERT INTO zdbranch(BranchInnerCode, BranchCode, ParentInnerCode, Type, OrderFlag, Name, TreeLevel, IsLeaf, Phone, Fax, Manager, Leader1, 

Leader2, Prop1, Prop2, Prop3, Prop4, Memo, AddTime, AddUser, ModifyTime, ModifyUser)
  VALUES('120000095120000329120000049', '21209991091150003', '120000095120000329', '0', 49, '分行世贸支行水上北路分理处', 3, 'Y', NULL, NULL, 'admin', 'administrator', 

'admin', '06', '120000049', NULL, NULL, 

NULL, '20100824', 'admin', NULL, NULL)
;
INSERT INTO zdbranch(BranchInnerCode, BranchCode, ParentInnerCode, Type, OrderFlag, Name, TreeLevel, IsLeaf, Phone, Fax, Manager, Leader1, 

Leader2, Prop1, Prop2, Prop3, Prop4, Memo, AddTime, AddUser, ModifyTime, ModifyUser)
  VALUES('120000095120000135120000352120000050', '2120999103104000V', '120000095120000135120000352', '0', 50, '昆明路支行', 4, 'Y', NULL, NULL, 'admin', 'administrator', 'admin', '06', '120000050', 

NULL, NULL, NULL, '20100824', 'admin', NULL, NULL);
INSERT INTO zdbranch(BranchInnerCode, BranchCode, ParentInnerCode, Type, OrderFlag, Name, TreeLevel, IsLeaf, Phone, Fax, Manager, Leader1, 

Leader2, Prop1, Prop2, Prop3, Prop4, Memo, AddTime, AddUser, ModifyTime, ModifyUser)
  VALUES('120000095120000909120000051', '21209991261070005', '120000095120000909', '0', 51, '经济开发区分行金海岸分理处', 3, 'Y', NULL, NULL, 'admin', 'administrator', 'admin', 

'06', '120000051', NULL, NULL, 

NULL, '20100824', 'admin', NULL, NULL)
;
INSERT INTO zdbranch(BranchInnerCode, BranchCode, ParentInnerCode, Type, OrderFlag, Name, TreeLevel, IsLeaf, Phone, Fax, Manager, Leader1, 

Leader2, Prop1, Prop2, Prop3, Prop4, Memo, AddTime, AddUser, ModifyTime, ModifyUser)
  VALUES('120000095120000909120000052', '2120999126106000A', '120000095120000909', '0', 52, '经济技术开发区明珠园分理处', 3, 'Y', NULL, NULL, 'admin', 'administrator', 

'admin', '06', '120000052', NULL, NULL, 

NULL, '20100824', 'admin', NULL, NULL)
;
INSERT INTO zdbranch(BranchInnerCode, BranchCode, ParentInnerCode, Type, OrderFlag, Name, TreeLevel, IsLeaf, Phone, Fax, Manager, Leader1, 

Leader2, Prop1, Prop2, Prop3, Prop4, Memo, AddTime, AddUser, ModifyTime, ModifyUser)
  VALUES('120000095120000909120000053', '2120999126111000T', '120000095120000909', '0', 53, '开发分行友谊北路支行', 3, 'Y', NULL, NULL, 'admin', 'administrator', 'admin', 

'06', '120000053', NULL, NULL, NULL, 

'20100824', 'admin', NULL, NULL)
;
INSERT INTO zdbranch(BranchInnerCode, BranchCode, ParentInnerCode, Type, OrderFlag, Name, TreeLevel, IsLeaf, Phone, Fax, Manager, Leader1, 

Leader2, Prop1, Prop2, Prop3, Prop4, Memo, AddTime, AddUser, ModifyTime, ModifyUser)
  VALUES('120000095120000165120000054', '2120999104112000R', '120000095120000165', '0', 54, '围堤道支行', 3, 'Y', NULL, NULL, 'admin', 'administrator', 'admin', '06', '120000054', 

NULL, NULL, NULL, '20100824', 'admin', NULL, NULL);
INSERT INTO zdbranch(BranchInnerCode, BranchCode, ParentInnerCode, Type, OrderFlag, Name, TreeLevel, IsLeaf, Phone, Fax, Manager, Leader1, 

Leader2, Prop1, Prop2, Prop3, Prop4, Memo, AddTime, AddUser, ModifyTime, ModifyUser)
  VALUES('120000095120000102120000055', '21209991021130007', '120000095120000102', '0', 55, '南开区南门外大街分理处', 5, 'Y', NULL, NULL, 'admin', 'administrator', 'admin', 

'06', '120000055', NULL, NULL, 

NULL, '20100824', 'admin', NULL, NULL)
;
INSERT INTO zdbranch(BranchInnerCode, BranchCode, ParentInnerCode, Type, OrderFlag, Name, TreeLevel, IsLeaf, Phone, Fax, Manager, Leader1, 

Leader2, Prop1, Prop2, Prop3, Prop4, Memo, AddTime, AddUser, ModifyTime, ModifyUser)
  VALUES('120000095120000102120000056', '2120999102115000S', '120000095120000102', '0', 56, '南开支行三马路分理处', 5, 'Y', NULL, NULL, 'admin', 'administrator', 'admin', '06', 

'120000056', NULL, NULL, NULL, 

'20100824', 'admin', NULL, NULL)
;
INSERT INTO zdbranch(BranchInnerCode, BranchCode, ParentInnerCode, Type, OrderFlag, Name, TreeLevel, IsLeaf, Phone, Fax, Manager, Leader1, 

Leader2, Prop1, Prop2, Prop3, Prop4, Memo, AddTime, AddUser, ModifyTime, ModifyUser)
  VALUES('120000095120000135120000031120000057', '21209991031030005', '120000095120000135120000031', '0', 57, '围堤道支行', 4, 'Y', NULL, NULL, 'admin', 'administrator', 'admin', '06', '120000057', 

NULL, NULL, NULL, '20100824', 'admin', NULL, NULL);
INSERT INTO zdbranch(BranchInnerCode, BranchCode, ParentInnerCode, Type, OrderFlag, Name, TreeLevel, IsLeaf, Phone, Fax, Manager, Leader1, 

Leader2, Prop1, Prop2, Prop3, Prop4, Memo, AddTime, AddUser, ModifyTime, ModifyUser)
  VALUES('120000095120000102120000058', '2120999102116000N', '120000095120000102', '0', 58, '南开区万德庄分理处', 3, 'Y', NULL, NULL, 'admin', 'administrator', 'admin', '06', 

'120000058', NULL, NULL, NULL, 

'20100824', 'admin', NULL, NULL)
;
INSERT INTO zdbranch(BranchInnerCode, BranchCode, ParentInnerCode, Type, OrderFlag, Name, TreeLevel, IsLeaf, Phone, Fax, Manager, Leader1, 

Leader2, Prop1, Prop2, Prop3, Prop4, Memo, AddTime, AddUser, ModifyTime, ModifyUser)
  VALUES('120000095120000301120000059', '21209991081170001', '120000095120000301', '0', 59, '新技术产业园区支行金属市场储蓄所', 3, 'Y', NULL, NULL, 'admin', 'administrator', 

'admin', '10', '120000059', NULL, 

NULL, NULL, '20100824', 'admin', NULL, NULL)
;
INSERT INTO zdbranch(BranchInnerCode, BranchCode, ParentInnerCode, Type, OrderFlag, Name, TreeLevel, IsLeaf, Phone, Fax, Manager, Leader1, 

Leader2, Prop1, Prop2, Prop3, Prop4, Memo, AddTime, AddUser, ModifyTime, ModifyUser)
  VALUES('120000095120000135120000367120000060', '21209991031210003', '120000095120000135120000367', '0', 60, '海河支行胜利路储蓄所', 4, 'Y', NULL, NULL, 'admin', 'administrator', 'admin', '10', 

'120000060', NULL, NULL, NULL, 

'20100824', 'admin', NULL, NULL)
;
INSERT INTO zdbranch(BranchInnerCode, BranchCode, ParentInnerCode, Type, OrderFlag, Name, TreeLevel, IsLeaf, Phone, Fax, Manager, Leader1, 

Leader2, Prop1, Prop2, Prop3, Prop4, Memo, AddTime, AddUser, ModifyTime, ModifyUser)
  VALUES('120000095120000925120000926120000061', '21209991270010011', '120000095120000925120000926', '0', 61, '港保税区分行营业部', 4, 'Y', NULL, NULL, 'admin', 'administrator', 'admin', '04', 

'120000061', NULL, NULL, NULL, 

'20100824', 'admin', NULL, NULL)
;
INSERT INTO zdbranch(BranchInnerCode, BranchCode, ParentInnerCode, Type, OrderFlag, Name, TreeLevel, IsLeaf, Phone, Fax, Manager, Leader1, 

Leader2, Prop1, Prop2, Prop3, Prop4, Memo, AddTime, AddUser, ModifyTime, ModifyUser)
  VALUES('120000095120000871120000872120000062', '2120999125001001H', '120000095120000871120000872', '0', 62, '大港支行营业部', 4, 'Y', NULL, NULL, 'admin', 'administrator', 'admin', '04', '120000062', 

NULL, NULL, NULL, 

'20100824', 'admin', NULL, NULL)
;
INSERT INTO zdbranch(BranchInnerCode, BranchCode, ParentInnerCode, Type, OrderFlag, Name, TreeLevel, IsLeaf, Phone, Fax, Manager, Leader1, 

Leader2, Prop1, Prop2, Prop3, Prop4, Memo, AddTime, AddUser, ModifyTime, ModifyUser)
  VALUES('120000095120000805120000790120000063', '2120999121001001I', '120000095120000805120000790', '0', 63, '宁河支行营业部', 4, 'Y', NULL, NULL, 'admin', 'administrator', 'admin', '04', '120000063', 

NULL, NULL, NULL, 

'20100824', 'admin', NULL, NULL)
;
INSERT INTO zdbranch(BranchInnerCode, BranchCode, ParentInnerCode, Type, OrderFlag, Name, TreeLevel, IsLeaf, Phone, Fax, Manager, Leader1, 

Leader2, Prop1, Prop2, Prop3, Prop4, Memo, AddTime, AddUser, ModifyTime, ModifyUser)
  VALUES('120000095120000738120000739120000064', '2120999120001015F', '120000095120000738120000739', '0', 64, '宝坻支行营业部', 4, 'Y', NULL, NULL, 'admin', 'administrator', 'admin', '04', '120000064', 

NULL, NULL, NULL, 

'20100824', 'admin', NULL, NULL)
;
INSERT INTO zdbranch(BranchInnerCode, BranchCode, ParentInnerCode, Type, OrderFlag, Name, TreeLevel, IsLeaf, Phone, Fax, Manager, Leader1, 

Leader2, Prop1, Prop2, Prop3, Prop4, Memo, AddTime, AddUser, ModifyTime, ModifyUser)
  VALUES('120000095120000574120000575120000065', '21209991170010214', '120000095120000574120000575', '0', 65, '武清支行营业部', 4, 'Y', NULL, NULL, 'admin', 'administrator', 'admin', '04', '120000065', 

NULL, NULL, NULL, 

'20100824', 'admin', NULL, NULL)
;
INSERT INTO zdbranch(BranchInnerCode, BranchCode, ParentInnerCode, Type, OrderFlag, Name, TreeLevel, IsLeaf, Phone, Fax, Manager, Leader1, 

Leader2, Prop1, Prop2, Prop3, Prop4, Memo, AddTime, AddUser, ModifyTime, ModifyUser)
  VALUES('120000095120000495120000481120000066', '2120999115001014H', '120000095120000495120000481', '0', 66, '津南支行营业部', 4, 'Y', NULL, NULL, 'admin', 'administrator', 'admin', '04', '120000066', 

NULL, NULL, NULL, 

'20100824', 'admin', NULL, NULL)
;
INSERT INTO zdbranch(BranchInnerCode, BranchCode, ParentInnerCode, Type, OrderFlag, Name, TreeLevel, IsLeaf, Phone, Fax, Manager, Leader1, 

Leader2, Prop1, Prop2, Prop3, Prop4, Memo, AddTime, AddUser, ModifyTime, ModifyUser)
  VALUES('120000095120000135120000367120000368120000067', '2120999103119019A', '120000095120000135120000367120000368', '0', 67, '海河支行营业部', 5, 'Y', NULL, NULL, 'admin', 'administrator', 'admin', '04', '120000067', 

NULL, NULL, NULL, 

'20100824', 'admin', NULL, NULL)
;
INSERT INTO zdbranch(BranchInnerCode, BranchCode, ParentInnerCode, Type, OrderFlag, Name, TreeLevel, IsLeaf, Phone, Fax, Manager, Leader1, 

Leader2, Prop1, Prop2, Prop3, Prop4, Memo, AddTime, AddUser, ModifyTime, ModifyUser)
  VALUES('120000095120000135120000352120000353120000068', '21209991031060182', '120000095120000135120000352120000353', '0', 68, '广厦支行营业部', 5, 'Y', NULL, NULL, 'admin', 'administrator', 'admin', '06', '120000068', 

NULL, NULL, NULL, 

'20100824', 'admin', NULL, NULL)
;
INSERT INTO zdbranch(BranchInnerCode, BranchCode, ParentInnerCode, Type, OrderFlag, Name, TreeLevel, IsLeaf, Phone, Fax, Manager, Leader1, 

Leader2, Prop1, Prop2, Prop3, Prop4, Memo, AddTime, AddUser, ModifyTime, ModifyUser)
  VALUES('120000095120000329120000330120000069', '2120999109001014P', '120000095120000329120000330', '0', 69, '世贸支行营业部', 4, 'Y', NULL, NULL, 'admin', 'administrator', 'admin', '04', '120000069', 

NULL, NULL, NULL, 

'20100824', 'admin', NULL, NULL)
;

INSERT INTO zdbranch(BranchInnerCode, BranchCode, ParentInnerCode, Type, OrderFlag, Name, TreeLevel, IsLeaf, Phone, Fax, Manager, Leader1, 

Leader2, Prop1, Prop2, Prop3, Prop4, Memo, AddTime, AddUser, ModifyTime, ModifyUser)
  VALUES('120000095120000301120000302120000070', '21209991080010126', '120000095120000301120000302', '0', 70, '新技术产业园区支行营业部', 4, 'Y', NULL, NULL, 'admin', 'administrator', 'admin', 

'04', '120000070', NULL, NULL, 

NULL, '20100824', 'admin', NULL, NULL)
;

INSERT INTO zdbranch(BranchInnerCode, BranchCode, ParentInnerCode, Type, OrderFlag, Name, TreeLevel, IsLeaf, Phone, Fax, Manager, Leader1, 

Leader2, Prop1, Prop2, Prop3, Prop4, Memo, AddTime, AddUser, ModifyTime, ModifyUser)
  VALUES('120000095120000199120000200120000071', '2120999105001014Q', '120000095120000199120000200', '0', 71, '河东支行营业部', 4, 'Y', NULL, NULL, 'admin', 'administrator', 'admin', '04', '120000071', 

NULL, NULL, NULL, 

'20100824', 'admin', NULL, NULL)
;
INSERT INTO zdbranch(BranchInnerCode, BranchCode, ParentInnerCode, Type, OrderFlag, Name, TreeLevel, IsLeaf, Phone, Fax, Manager, Leader1, 

Leader2, Prop1, Prop2, Prop3, Prop4, Memo, AddTime, AddUser, ModifyTime, ModifyUser)
  VALUES('120000095120000102120000103120000072', '2120999102001014J', '120000095120000102120000103', '0', 72, '南开支行营业部', 4, 'Y', NULL, NULL, 'admin', 'administrator', 'admin', '04', '120000072', 

NULL, NULL, NULL, 

'20100824', 'admin', NULL, NULL)
;
INSERT INTO zdbranch(BranchInnerCode, BranchCode, ParentInnerCode, Type, OrderFlag, Name, TreeLevel, IsLeaf, Phone, Fax, Manager, Leader1, 

Leader2, Prop1, Prop2, Prop3, Prop4, Memo, AddTime, AddUser, ModifyTime, ModifyUser)
  VALUES('120000095120000135120000136120000073', '2120999103001013D', '120000095120000135120000136', '0', 73, '和平支行营业部', 4, 'Y', NULL, NULL, 'admin', 'administrator', 'admin', '04', '120000073', 

NULL, NULL, NULL, 

'20100824', 'admin', NULL, NULL)
;
INSERT INTO zdbranch(BranchInnerCode, BranchCode, ParentInnerCode, Type, OrderFlag, Name, TreeLevel, IsLeaf, Phone, Fax, Manager, Leader1, 

Leader2, Prop1, Prop2, Prop3, Prop4, Memo, AddTime, AddUser, ModifyTime, ModifyUser)
  VALUES('120000095120000233120000235120000074', '2120999106001016E', '120000095120000233120000235', '0', 74, '河北区支行营业部', 4, 'Y', NULL, NULL, 'admin', 'administrator', 'admin', '04', 

'120000074', NULL, NULL, NULL, 

'20100824', 'admin', NULL, NULL)
;
INSERT INTO zdbranch(BranchInnerCode, BranchCode, ParentInnerCode, Type, OrderFlag, Name, TreeLevel, IsLeaf, Phone, Fax, Manager, Leader1, 

Leader2, Prop1, Prop2, Prop3, Prop4, Memo, AddTime, AddUser, ModifyTime, ModifyUser)
  VALUES('120000095120000687120000688120000075', '2120999119001001P', '120000095120000687120000688', '0', 75, '静海支行营业部', 4, 'Y', NULL, NULL, 'admin', 'administrator', 'admin', '04', '120000075', 

NULL, NULL, NULL, 

'20100824', 'admin', NULL, NULL)
;
INSERT INTO zdbranch(BranchInnerCode, BranchCode, ParentInnerCode, Type, OrderFlag, Name, TreeLevel, IsLeaf, Phone, Fax, Manager, Leader1, 

Leader2, Prop1, Prop2, Prop3, Prop4, Memo, AddTime, AddUser, ModifyTime, ModifyUser)
  VALUES('120000095120000387120000419120000076', '2120999113001001B', '120000095120000387120000419', '0', 76, '东丽支行营业部', 4, 'Y', NULL, NULL, 'admin', 'administrator', 'admin', '04', '120000076', 

NULL, NULL, NULL, 

'20100824', 'admin', NULL, NULL)
;
INSERT INTO zdbranch(BranchInnerCode, BranchCode, ParentInnerCode, Type, OrderFlag, Name, TreeLevel, IsLeaf, Phone, Fax, Manager, Leader1, 

Leader2, Prop1, Prop2, Prop3, Prop4, Memo, AddTime, AddUser, ModifyTime, ModifyUser)
  VALUES('120000095120000135120000031120000077', '2120999103105000Q', '120000095120000135120000031', '0', 77, '金信支行机关', 4, 'Y', NULL, NULL, 'admin', 'administrator', 'admin', '06', '120000077', 

NULL, NULL, NULL, '20100824', 'admin', NULL, NULL);
INSERT INTO zdbranch(BranchInnerCode, BranchCode, ParentInnerCode, Type, OrderFlag, Name, TreeLevel, IsLeaf, Phone, Fax, Manager, Leader1, 

Leader2, Prop1, Prop2, Prop3, Prop4, Memo, AddTime, AddUser, ModifyTime, ModifyUser)
  VALUES('120000095120000135120000031120000077120000078', '2120999103105015D', '120000095120000135120000031120000077', '0', 78, '金信支行其他', 5, 'Y', NULL, NULL, 'admin', 'administrator', 'admin', '06', '120000078', 

NULL, NULL, NULL, '20100824', 'admin', NULL, NULL);
INSERT INTO zdbranch(BranchInnerCode, BranchCode, ParentInnerCode, Type, OrderFlag, Name, TreeLevel, IsLeaf, Phone, Fax, Manager, Leader1, 

Leader2, Prop1, Prop2, Prop3, Prop4, Memo, AddTime, AddUser, ModifyTime, ModifyUser)
  VALUES('120000095120000135120000031120000077120000079', '2120999103105023E', '120000095120000135120000031120000077', '0', 79, '金信支行办公室', 5, 'Y', NULL, NULL, 'admin', 'administrator', 'admin', '06', '120000079', 

NULL, NULL, NULL, 

'20100824', 'admin', NULL, NULL)
;
INSERT INTO zdbranch(BranchInnerCode, BranchCode, ParentInnerCode, Type, OrderFlag, Name, TreeLevel, IsLeaf, Phone, Fax, Manager, Leader1, 

Leader2, Prop1, Prop2, Prop3, Prop4, Memo, AddTime, AddUser, ModifyTime, ModifyUser)
  VALUES('120000095120000135120000031120000077120000080', '21209991031050195', '120000095120000135120000031120000077', '0', 80, '金信支行行长室', 5, 'Y', NULL, NULL, 'admin', 'administrator', 'admin', '06', '120000080', 

NULL, NULL, NULL, 

'20100824', 'admin', NULL, NULL)
;
INSERT INTO zdbranch(BranchInnerCode, BranchCode, ParentInnerCode, Type, OrderFlag, Name, TreeLevel, IsLeaf, Phone, Fax, Manager, Leader1, 

Leader2, Prop1, Prop2, Prop3, Prop4, Memo, AddTime, AddUser, ModifyTime, ModifyUser)
  VALUES('120000095120000135120000031120000077120000081', '2120999103105024C', '120000095120000135120000031120000077', '0', 81, '金信支行银行卡部', 5, 'Y', NULL, NULL, 'admin', 'administrator', 'admin', '06', 

'120000081', NULL, NULL, NULL, 

'20100824', 'admin', NULL, NULL)
;
INSERT INTO zdbranch(BranchInnerCode, BranchCode, ParentInnerCode, Type, OrderFlag, Name, TreeLevel, IsLeaf, Phone, Fax, Manager, Leader1, 

Leader2, Prop1, Prop2, Prop3, Prop4, Memo, AddTime, AddUser, ModifyTime, ModifyUser)
  VALUES('120000095120000135120000031120000077120000082', '2120999103105021I', '120000095120000135120000031120000077', '0', 82, '金信支行保险代理部', 5, 'Y', NULL, NULL, 'admin', 'administrator', 'admin', '06', 

'120000082', NULL, NULL, NULL, 

'20100824', 'admin', NULL, NULL)
;
INSERT INTO zdbranch(BranchInnerCode, BranchCode, ParentInnerCode, Type, OrderFlag, Name, TreeLevel, IsLeaf, Phone, Fax, Manager, Leader1, 

Leader2, Prop1, Prop2, Prop3, Prop4, Memo, AddTime, AddUser, ModifyTime, ModifyUser)
  VALUES('120000095120000135120000031120000077120000083', '2120999103105014F', '120000095120000135120000031120000077', '0', 83, '金信支行国际业务部', 5, 'Y', NULL, NULL, 'admin', 'administrator', 'admin', '06', 

'120000083', NULL, NULL, NULL, 

'20100824', 'admin', NULL, NULL)
;
INSERT INTO zdbranch(BranchInnerCode, BranchCode, ParentInnerCode, Type, OrderFlag, Name, TreeLevel, IsLeaf, Phone, Fax, Manager, Leader1, 

Leader2, Prop1, Prop2, Prop3, Prop4, Memo, AddTime, AddUser, ModifyTime, ModifyUser)
  VALUES('120000095120000135120000031120000077120000084', '21209991031050187', '120000095120000135120000031120000077', '0', 84, '金信支行计划财务部', 5, 'Y', NULL, NULL, 'admin', 'administrator', 'admin', '06', 

'120000084', NULL, NULL, NULL, 

'20100824', 'admin', NULL, NULL)
;
INSERT INTO zdbranch(BranchInnerCode, BranchCode, ParentInnerCode, Type, OrderFlag, Name, TreeLevel, IsLeaf, Phone, Fax, Manager, Leader1, 

Leader2, Prop1, Prop2, Prop3, Prop4, Memo, AddTime, AddUser, ModifyTime, ModifyUser)
  VALUES('120000095120000135120000031120000077120000085', '2120999103105022G', '120000095120000135120000031120000077', '0', 85, '金信支行监督保障部', 5, 'Y', NULL, NULL, 'admin', 'administrator', 'admin', '06', 

'120000085', NULL, NULL, NULL, 

'20100824', 'admin', NULL, NULL)
;
INSERT INTO zdbranch(BranchInnerCode, BranchCode, ParentInnerCode, Type, OrderFlag, Name, TreeLevel, IsLeaf, Phone, Fax, Manager, Leader1, 

Leader2, Prop1, Prop2, Prop3, Prop4, Memo, AddTime, AddUser, ModifyTime, ModifyUser)
  VALUES('120000095120000135120000031120000077120000086', '2120999103105013H', '120000095120000135120000031120000077', '0', 86, '金信支行离退休人员', 5, 'Y', NULL, NULL, 'admin', 'administrator', 'admin', '06', 

'120000086', NULL, NULL, NULL, 

'20100824', 'admin', NULL, NULL)
;
INSERT INTO zdbranch(BranchInnerCode, BranchCode, ParentInnerCode, Type, OrderFlag, Name, TreeLevel, IsLeaf, Phone, Fax, Manager, Leader1, 

Leader2, Prop1, Prop2, Prop3, Prop4, Memo, AddTime, AddUser, ModifyTime, ModifyUser)
  VALUES('120000095120000135120000031120000077120000087', '21209991031050179', '120000095120000135120000031120000077', '0', 87, '金信支行信贷管理部', 5, 'Y', NULL, NULL, 'admin', 'administrator', 'admin', '06', 

'120000087', NULL, NULL, NULL, 

'20100824', 'admin', NULL, NULL)
;
INSERT INTO zdbranch(BranchInnerCode, BranchCode, ParentInnerCode, Type, OrderFlag, Name, TreeLevel, IsLeaf, Phone, Fax, Manager, Leader1, 

Leader2, Prop1, Prop2, Prop3, Prop4, Memo, AddTime, AddUser, ModifyTime, ModifyUser)
  VALUES('120000095120000135120000031120000077120000088', '2120999103105020K', '120000095120000135120000031120000077', '0', 88, '金信支行业务拓展部', 5, 'Y', NULL, NULL, 'admin', 'administrator', 'admin', '06', 

'120000088', NULL, NULL, NULL, 

'20100824', 'admin', NULL, NULL)
;
INSERT INTO zdbranch(BranchInnerCode, BranchCode, ParentInnerCode, Type, OrderFlag, Name, TreeLevel, IsLeaf, Phone, Fax, Manager, Leader1, 

Leader2, Prop1, Prop2, Prop3, Prop4, Memo, AddTime, AddUser, ModifyTime, ModifyUser)
  VALUES('120000095120000135120000031120000077120000089', '2120999103105016B', '120000095120000135120000031120000077', '0', 89, '金信支行中间业务部', 5, 'Y', NULL, NULL, 'admin', 'administrator', 'admin', '06', 

'120000089', NULL, NULL, NULL, 

'20100824', 'admin', NULL, NULL)
;
INSERT INTO zdbranch(BranchInnerCode, BranchCode, ParentInnerCode, Type, OrderFlag, Name, TreeLevel, IsLeaf, Phone, Fax, Manager, Leader1, 

Leader2, Prop1, Prop2, Prop3, Prop4, Memo, AddTime, AddUser, ModifyTime, ModifyUser)
  VALUES('120000095120000925120000090', '2120999127108000N', '120000095120000925', '0', 90, '航空城支行', 3, 'Y', NULL, NULL, 'admin', 'administrator', 'admin', '06', '120000090', 

NULL, NULL, NULL, '20100824', 'admin', NULL, NULL);
INSERT INTO zdbranch(BranchInnerCode, BranchCode, ParentInnerCode, Type, OrderFlag, Name, TreeLevel, IsLeaf, Phone, Fax, Manager, Leader1, 

Leader2, Prop1, Prop2, Prop3, Prop4, Memo, AddTime, AddUser, ModifyTime, ModifyUser)
  VALUES('120000095120000135120000367120000091', '2120999103122000T', '120000095120000135120000367', '0', 91, '海河支行七纬路储蓄所', 4, 'Y', NULL, NULL, 'admin', 'administrator', 'admin', '10', 

'120000091', NULL, NULL, NULL, 

'20100824', 'admin', NULL, NULL)
;
INSERT INTO zdbranch(BranchInnerCode, BranchCode, ParentInnerCode, Type, OrderFlag, Name, TreeLevel, IsLeaf, Phone, Fax, Manager, Leader1, 

Leader2, Prop1, Prop2, Prop3, Prop4, Memo, AddTime, AddUser, ModifyTime, ModifyUser)
  VALUES('120000095120000925120000092', '2120999127111000L', '120000095120000925', '0', 92, '新天地支行', 3, 'Y', NULL, NULL, 'admin', 'administrator', 'admin', '06', '120000092', 

NULL, NULL, NULL, '20100824', 'admin', NULL, NULL);
INSERT INTO zdbranch(BranchInnerCode, BranchCode, ParentInnerCode, Type, OrderFlag, Name, TreeLevel, IsLeaf, Phone, Fax, Manager, Leader1, 

Leader2, Prop1, Prop2, Prop3, Prop4, Memo, AddTime, AddUser, ModifyTime, ModifyUser)
  VALUES('120000095120000135120000352120000093', '2120999103105000Q', '120000095120000135120000352', '0', 93, '友谊北路支行', 4, 'Y', NULL, NULL, 'admin', 'administrator', 'admin', '06', '120000093', 

NULL, NULL, NULL, '20100824', 'admin', NULL, NULL);
INSERT INTO zdbranch(BranchInnerCode, BranchCode, ParentInnerCode, Type, OrderFlag, Name, TreeLevel, IsLeaf, Phone, Fax, Manager, Leader1, 

Leader2, Prop1, Prop2, Prop3, Prop4, Memo, AddTime, AddUser, ModifyTime, ModifyUser)
  VALUES('120000095120000094', '2120003000000000B', '120000095', '0', 94, '分行营业部', 2, 'Y', NULL, NULL, 'admin', 'administrator', 'admin', '04', '120000094', 

NULL, NULL, NULL, '20100824', 'admin', NULL, NULL);

INSERT INTO zdbranch(BranchInnerCode, BranchCode, ParentInnerCode, Type, OrderFlag, Name, TreeLevel, IsLeaf, Phone, Fax, Manager, Leader1, 

Leader2, Prop1, Prop2, Prop3, Prop4, Memo, AddTime, AddUser, ModifyTime, ModifyUser)
  VALUES('120000095120000266120000096', '2120999107000101K', '120000095120000266', '0', 96, '红桥支行双安里储蓄所', 3, 'Y', NULL, NULL, 'admin', 'administrator', 'admin', 

'10', '120000096', NULL, NULL, NULL, 

'20100824', 'admin', NULL, NULL)
;
INSERT INTO zdbranch(BranchInnerCode, BranchCode, ParentInnerCode, Type, OrderFlag, Name, TreeLevel, IsLeaf, Phone, Fax, Manager, Leader1, 

Leader2, Prop1, Prop2, Prop3, Prop4, Memo, AddTime, AddUser, ModifyTime, ModifyUser)
  VALUES('120000095120000909120000097', '2120999126105000F', '120000095120000909', '0', 97, '津滨支行', 3, 'Y', NULL, NULL, 'admin', 'administrator', 'admin', '06', '120000097', 

NULL, NULL, NULL, '20100824', 'admin', NULL, NULL)
;
INSERT INTO zdbranch(BranchInnerCode, BranchCode, ParentInnerCode, Type, OrderFlag, Name, TreeLevel, IsLeaf, Phone, Fax, Manager, Leader1, 

Leader2, Prop1, Prop2, Prop3, Prop4, Memo, AddTime, AddUser, ModifyTime, ModifyUser)
  VALUES('120000095120000135120000098', '2120999103124000J', '120000095120000135', '0', 98, '解放路支行', 3, 'Y', NULL, NULL, 'admin', 'administrator', 'admin', '06', '120000098', 

NULL, NULL, NULL, '20100824', 'admin', NULL, NULL)
;
INSERT INTO zdbranch(BranchInnerCode, BranchCode, ParentInnerCode, Type, OrderFlag, Name, TreeLevel, IsLeaf, Phone, Fax, Manager, Leader1, 

Leader2, Prop1, Prop2, Prop3, Prop4, Memo, AddTime, AddUser, ModifyTime, ModifyUser)
  VALUES('120000095120000135120000098120000099', '21209991031000000K', '120000095120000135120000098', '0', 99, '解放路支行机关', 4, 'Y', NULL, NULL, 'admin', 'administrator', 'admin', '06', 

'120000099', NULL, NULL, NULL, 

'20100824', 'admin', NULL, NULL)
;
INSERT INTO zdbranch(BranchInnerCode, BranchCode, ParentInnerCode, Type, OrderFlag, Name, TreeLevel, IsLeaf, Phone, Fax, Manager, Leader1, 

Leader2, Prop1, Prop2, Prop3, Prop4, Memo, AddTime, AddUser, ModifyTime, ModifyUser)
  VALUES('120000095120000135120000098120000100', '2120999103108000B', '120000095120000135120000098', '0', 100, '云翔大厦支行', 4, 'Y', NULL, NULL, 'admin', 'administrator', 'admin', '06', '120000100', 

NULL, NULL, NULL, 

'20100824', 'admin', NULL, NULL)
;
INSERT INTO zdbranch(BranchInnerCode, BranchCode, ParentInnerCode, Type, OrderFlag, Name, TreeLevel, IsLeaf, Phone, Fax, Manager, Leader1, 

Leader2, Prop1, Prop2, Prop3, Prop4, Memo, AddTime, AddUser, ModifyTime, ModifyUser)
  VALUES('120000095120000135120000098120000101', '21209991031000000K', '120000095120000135120000098', '0', 101, '解放路支行解放北路储蓄所', 4, 'Y', NULL, NULL, 'admin', 'administrator', 'admin', 

'10', '120000101', NULL, NULL, 

NULL, '20100824', 'admin', NULL, NULL)
;
INSERT INTO zdbranch(BranchInnerCode, BranchCode, ParentInnerCode, Type, OrderFlag, Name, TreeLevel, IsLeaf, Phone, Fax, Manager, Leader1, 

Leader2, Prop1, Prop2, Prop3, Prop4, Memo, AddTime, AddUser, ModifyTime, ModifyUser)
  VALUES('120000095120000102', '21209991020000004', '120000095', '0', 102, '南开支行', 2, 'Y', NULL, NULL, 'admin', 'administrator', 'admin', '04', '120000102', 

NULL, NULL, NULL, '20100824', 'admin', NULL, NULL)
;
INSERT INTO zdbranch(BranchInnerCode, BranchCode, ParentInnerCode, Type, OrderFlag, Name, TreeLevel, IsLeaf, Phone, Fax, Manager, Leader1, 

Leader2, Prop1, Prop2, Prop3, Prop4, Memo, AddTime, AddUser, ModifyTime, ModifyUser)
  VALUES('120000095120000102120000103', '2120999102001000U', '120000095120000102', '0', 103, '南开支行机关', 3, 'Y', NULL, NULL, 'admin', 'administrator', 'admin', '04', '120000103', 

NULL, NULL, NULL, 

'20100824', 'admin', NULL, NULL)
;
INSERT INTO zdbranch(BranchInnerCode, BranchCode, ParentInnerCode, Type, OrderFlag, Name, TreeLevel, IsLeaf, Phone, Fax, Manager, Leader1, 

Leader2, Prop1, Prop2, Prop3, Prop4, Memo, AddTime, AddUser, ModifyTime, ModifyUser)
  VALUES('120000095120000102120000103120000104', '2120999102001003O', '120000095120000102120000103', '0', 104, '南开支行其他', 4, 'Y', NULL, NULL, 'admin', 'administrator', 'admin', '04', '120000104', 

NULL, NULL, NULL, 

'20100824', 'admin', NULL, NULL)
;
INSERT INTO zdbranch(BranchInnerCode, BranchCode, ParentInnerCode, Type, OrderFlag, Name, TreeLevel, IsLeaf, Phone, Fax, Manager, Leader1, 

Leader2, Prop1, Prop2, Prop3, Prop4, Memo, AddTime, AddUser, ModifyTime, ModifyUser)
  VALUES('120000095120000102120000103120000105', '2120999102001001S', '120000095120000102120000103', '0', 105, '南开支行综合管理部', 4, 'Y', NULL, NULL, 'admin', 'administrator', 'admin', '04', 

'120000105', NULL, NULL, NULL, 

'20100824', 'admin', NULL, NULL)
;
INSERT INTO zdbranch(BranchInnerCode, BranchCode, ParentInnerCode, Type, OrderFlag, Name, TreeLevel, IsLeaf, Phone, Fax, Manager, Leader1, 

Leader2, Prop1, Prop2, Prop3, Prop4, Memo, AddTime, AddUser, ModifyTime, ModifyUser)
  VALUES('120000095120000102120000103120000106', '2120999102001002Q', '120000095120000102120000103', '0', 106, '南开支行行长室', 4, 'Y', NULL, NULL, 'admin', 'administrator', 'admin', '04', 

'120000106', NULL, NULL, NULL, 

'20100824', 'admin', NULL, NULL)
;
INSERT INTO zdbranch(BranchInnerCode, BranchCode, ParentInnerCode, Type, OrderFlag, Name, TreeLevel, IsLeaf, Phone, Fax, Manager, Leader1, 

Leader2, Prop1, Prop2, Prop3, Prop4, Memo, AddTime, AddUser, ModifyTime, ModifyUser)
  VALUES('120000095120000102120000103120000107', '2120999102001004M', '120000095120000102120000103', '0', 107, '南开支行内退人员', 4, 'Y', NULL, NULL, 'admin', 'administrator', 'admin', '04', 

'120000107', NULL, NULL, NULL, 

'20100824', 'admin', NULL, NULL)
;
INSERT INTO zdbranch(BranchInnerCode, BranchCode, ParentInnerCode, Type, OrderFlag, Name, TreeLevel, IsLeaf, Phone, Fax, Manager, Leader1, 

Leader2, Prop1, Prop2, Prop3, Prop4, Memo, AddTime, AddUser, ModifyTime, ModifyUser)
  VALUES('120000095120000102120000103120000108', '2120999102001005K', '120000095120000102120000103', '0', 108, '南开支行代理保险部', 4, 'Y', NULL, NULL, 'admin', 'administrator', 'admin', '04', 

'120000108', NULL, NULL, NULL, 

'20100824', 'admin', NULL, NULL)
;
INSERT INTO zdbranch(BranchInnerCode, BranchCode, ParentInnerCode, Type, OrderFlag, Name, TreeLevel, IsLeaf, Phone, Fax, Manager, Leader1, 

Leader2, Prop1, Prop2, Prop3, Prop4, Memo, AddTime, AddUser, ModifyTime, ModifyUser)
  VALUES('120000095120000102120000103120000109', '2120999102001006I', '120000095120000102120000103', '0', 109, '南开支行国际业务部', 4, 'Y', NULL, NULL, 'admin', 'administrator', 'admin', '04', 

'120000109', NULL, NULL, NULL, 

'20100821', 'admin', NULL, NULL)
;
INSERT INTO zdbranch(BranchInnerCode, BranchCode, ParentInnerCode, Type, OrderFlag, Name, TreeLevel, IsLeaf, Phone, Fax, Manager, Leader1, 

Leader2, Prop1, Prop2, Prop3, Prop4, Memo, AddTime, AddUser, ModifyTime, ModifyUser)
  VALUES('120000095120000102120000103120000110', '2120999102001007G', '120000095120000102120000103', '0', 110, '南开支行财会运营部', 4, 'Y', NULL, NULL, 'admin', 'administrator', 'admin', '04', 

'120000110', NULL, NULL, NULL, 

'20100822', 'admin', NULL, NULL)
;
INSERT INTO zdbranch(BranchInnerCode, BranchCode, ParentInnerCode, Type, OrderFlag, Name, TreeLevel, IsLeaf, Phone, Fax, Manager, Leader1, 

Leader2, Prop1, Prop2, Prop3, Prop4, Memo, AddTime, AddUser, ModifyTime, ModifyUser)
  VALUES('120000095120000102120000103120000111', '2120999102001008E', '120000095120000102120000103', '0', 111, '南开支行监察保卫/内控合规部', 4, 'Y', NULL, NULL, 'admin', 'administrator', 

'admin', '04', '120000111', NULL, NULL, 

NULL, '20100823', 'admin', NULL, NULL)
;
INSERT INTO zdbranch(BranchInnerCode, BranchCode, ParentInnerCode, Type, OrderFlag, Name, TreeLevel, IsLeaf, Phone, Fax, Manager, Leader1, 

Leader2, Prop1, Prop2, Prop3, Prop4, Memo, AddTime, AddUser, ModifyTime, ModifyUser)
  VALUES('120000095120000102120000103120000112', '2120999102001009C', '120000095120000102120000103', '0', 112, '南开支行离退休人员', 4, 'Y', NULL, NULL, 'admin', 'administrator', 'admin', '04', 

'120000112', NULL, NULL, NULL, 

'20100824', 'admin', NULL, NULL)
;
INSERT INTO zdbranch(BranchInnerCode, BranchCode, ParentInnerCode, Type, OrderFlag, Name, TreeLevel, IsLeaf, Phone, Fax, Manager, Leader1, 

Leader2, Prop1, Prop2, Prop3, Prop4, Memo, AddTime, AddUser, ModifyTime, ModifyUser)
  VALUES('120000095120000102120000103120000113', '2120999102001010R', '120000095120000102120000103', '0', 113, '南开支行双清办公室', 4, 'Y', NULL, NULL, 'admin', 'administrator', 'admin', '04', 

'120000113', NULL, NULL, NULL, 

'20100824', 'admin', NULL, NULL)
;
INSERT INTO zdbranch(BranchInnerCode, BranchCode, ParentInnerCode, Type, OrderFlag, Name, TreeLevel, IsLeaf, Phone, Fax, Manager, Leader1, 

Leader2, Prop1, Prop2, Prop3, Prop4, Memo, AddTime, AddUser, ModifyTime, ModifyUser)
  VALUES('120000095120000102120000103120000114', '2120999102001011P', '120000095120000102120000103', '0', 114, '南开支行信贷管理部', 4, 'Y', NULL, NULL, 'admin', 'administrator', 'admin', '04', 

'120000114', NULL, NULL, NULL, 

'20100824', 'admin', NULL, NULL)
;
INSERT INTO zdbranch(BranchInnerCode, BranchCode, ParentInnerCode, Type, OrderFlag, Name, TreeLevel, IsLeaf, Phone, Fax, Manager, Leader1, 

Leader2, Prop1, Prop2, Prop3, Prop4, Memo, AddTime, AddUser, ModifyTime, ModifyUser)
  VALUES('120000095120000102120000103120000115', '2120999102001012N', '120000095120000102120000103', '0', 115, '南开支行公司业务部', 4, 'Y', NULL, NULL, 'admin', 'administrator', 'admin', '04', 

'120000115', NULL, NULL, NULL, 

'20100824', 'admin', NULL, NULL)
;
INSERT INTO zdbranch(BranchInnerCode, BranchCode, ParentInnerCode, Type, OrderFlag, Name, TreeLevel, IsLeaf, Phone, Fax, Manager, Leader1, 

Leader2, Prop1, Prop2, Prop3, Prop4, Memo, AddTime, AddUser, ModifyTime, ModifyUser)
  VALUES('120000095120000102120000103120000116', '2120999102001013L', '120000095120000102120000103', '0', 116, '南开支行个人金融部', 4, 'Y', NULL, NULL, 'admin', 'administrator', 'admin', '04', 

'120000116', NULL, NULL, NULL, 

'20100824', 'admin', NULL, NULL)
;
INSERT INTO zdbranch(BranchInnerCode, BranchCode, ParentInnerCode, Type, OrderFlag, Name, TreeLevel, IsLeaf, Phone, Fax, Manager, Leader1, 

Leader2, Prop1, Prop2, Prop3, Prop4, Memo, AddTime, AddUser, ModifyTime, ModifyUser)
  VALUES('120000095120000102120000117', '2120999102102000I', '120000095120000102', '0', 117, '龙城支行', 3, 'Y', NULL, NULL, 'admin', 'administrator', 'admin', '06', '120000117', 

NULL, NULL, NULL, '20100824', 'admin', NULL, NULL)
;
INSERT INTO zdbranch(BranchInnerCode, BranchCode, ParentInnerCode, Type, OrderFlag, Name, TreeLevel, IsLeaf, Phone, Fax, Manager, Leader1, 

Leader2, Prop1, Prop2, Prop3, Prop4, Memo, AddTime, AddUser, ModifyTime, ModifyUser)
  VALUES('120000095120000102120000118', '2120999102103000D', '120000095120000102', '0', 118, '宾水西道支行', 3, 'Y', NULL, NULL, 'admin', 'administrator', 'admin', '06', '120000118', 

NULL, NULL, NULL, 

'20100824', 'admin', NULL, NULL)
;
INSERT INTO zdbranch(BranchInnerCode, BranchCode, ParentInnerCode, Type, OrderFlag, Name, TreeLevel, IsLeaf, Phone, Fax, Manager, Leader1, 

Leader2, Prop1, Prop2, Prop3, Prop4, Memo, AddTime, AddUser, ModifyTime, ModifyUser)
  VALUES('120000095120000102120000119', '21209991021040008', '120000095120000102', '0', 119, '向阳支行', 3, 'Y', NULL, NULL, 'admin', 'administrator', 'admin', '06', '120000119', 

NULL, NULL, NULL, '20100824', 'admin', NULL, NULL);
INSERT INTO zdbranch(BranchInnerCode, BranchCode, ParentInnerCode, Type, OrderFlag, Name, TreeLevel, IsLeaf, Phone, Fax, Manager, Leader1, 

Leader2, Prop1, Prop2, Prop3, Prop4, Memo, AddTime, AddUser, ModifyTime, ModifyUser)
  VALUES('120000095120000102120000120', '21209991021050003', '120000095120000102', '0', 120, '黄河道支行', 3, 'Y', NULL, NULL, 'admin', 'administrator', 'admin', '06', '120000120', 

NULL, NULL, NULL, 

'20100824', 'admin', NULL, NULL)
;
INSERT INTO zdbranch(BranchInnerCode, BranchCode, ParentInnerCode, Type, OrderFlag, Name, TreeLevel, IsLeaf, Phone, Fax, Manager, Leader1, 

Leader2, Prop1, Prop2, Prop3, Prop4, Memo, AddTime, AddUser, ModifyTime, ModifyUser)
  VALUES('120000095120000102120000121', '2120999102106000T', '120000095120000102', '0', 121, '红旗南路支行', 3, 'Y', NULL, NULL, 'admin', 'administrator', 'admin', '06', '120000121', 

NULL, NULL, NULL, 

'20100824', 'admin', NULL, NULL)
;
INSERT INTO zdbranch(BranchInnerCode, BranchCode, ParentInnerCode, Type, OrderFlag, Name, TreeLevel, IsLeaf, Phone, Fax, Manager, Leader1, 

Leader2, Prop1, Prop2, Prop3, Prop4, Memo, AddTime, AddUser, ModifyTime, ModifyUser)
  VALUES('120000095120000102120000122', '2120999102107000O', '120000095120000102', '0', 122, '东马路支行', 3, 'Y', NULL, NULL, 'admin', 'administrator', 'admin', '06', '120000122', 

NULL, NULL, NULL, 

'20100824', 'admin', NULL, NULL)
;
INSERT INTO zdbranch(BranchInnerCode, BranchCode, ParentInnerCode, Type, OrderFlag, Name, TreeLevel, IsLeaf, Phone, Fax, Manager, Leader1, 

Leader2, Prop1, Prop2, Prop3, Prop4, Memo, AddTime, AddUser, ModifyTime, ModifyUser)
  VALUES('120000095120000102120000123', '2120999102108000J', '120000095120000102', '0', 123, '西湖道支行', 3, 'Y', NULL, NULL, 'admin', 'administrator', 'admin', '06', '120000123', 

NULL, NULL, NULL, 

'20100824', 'admin', NULL, NULL)
;
INSERT INTO zdbranch(BranchInnerCode, BranchCode, ParentInnerCode, Type, OrderFlag, Name, TreeLevel, IsLeaf, Phone, Fax, Manager, Leader1, 

Leader2, Prop1, Prop2, Prop3, Prop4, Memo, AddTime, AddUser, ModifyTime, ModifyUser)
  VALUES('120000095120000102120000124', '2120999102109000E', '120000095120000102', '0', 124, '瀛寰大厦支行', 3, 'Y', NULL, NULL, 'admin', 'administrator', 'admin', '06', 

'120000124', NULL, NULL, NULL, 

'20100824', 'admin', NULL, NULL)
;
INSERT INTO zdbranch(BranchInnerCode, BranchCode, ParentInnerCode, Type, OrderFlag, Name, TreeLevel, IsLeaf, Phone, Fax, Manager, Leader1, 

Leader2, Prop1, Prop2, Prop3, Prop4, Memo, AddTime, AddUser, ModifyTime, ModifyUser)
  VALUES('120000095120000102120000125', '21209991021220006', '120000095120000102', '0', 125, '南开支行天拖南储蓄所', 3, 'Y', NULL, NULL, 'admin', 'administrator', 'admin', 

'10', '120000125', NULL, NULL, NULL, 

'20100824', 'admin', NULL, NULL)
;
INSERT INTO zdbranch(BranchInnerCode, BranchCode, ParentInnerCode, Type, OrderFlag, Name, TreeLevel, IsLeaf, Phone, Fax, Manager, Leader1, 

Leader2, Prop1, Prop2, Prop3, Prop4, Memo, AddTime, AddUser, ModifyTime, ModifyUser)
  VALUES('120000095120000102120000126', '2120999102111000H', '120000095120000102', '0', 126, '南开支行灵隐道分理处', 3, 'Y', NULL, NULL, 'admin', 'administrator', 'admin', 

'06', '120000126', NULL, NULL, NULL, 

'20100824', 'admin', NULL, NULL)
;
INSERT INTO zdbranch(BranchInnerCode, BranchCode, ParentInnerCode, Type, OrderFlag, Name, TreeLevel, IsLeaf, Phone, Fax, Manager, Leader1, 

Leader2, Prop1, Prop2, Prop3, Prop4, Memo, AddTime, AddUser, ModifyTime, ModifyUser)
  VALUES('120000095120000102120000127', '21209991021190008', '120000095120000102', '0', 127, '世纪花园支行', 3, 'Y', NULL, NULL, 'admin', 'administrator', 'admin', '06', '120000127', 

NULL, NULL, NULL, 

'20100824', 'admin', NULL, NULL)
;
INSERT INTO zdbranch(BranchInnerCode, BranchCode, ParentInnerCode, Type, OrderFlag, Name, TreeLevel, IsLeaf, Phone, Fax, Manager, Leader1, 

Leader2, Prop1, Prop2, Prop3, Prop4, Memo, AddTime, AddUser, ModifyTime, ModifyUser)
  VALUES('120000095120000102120000128', '2120999102120000G', '120000095120000102', '0', 128, '南开支行咸阳路分理处', 3, 'Y', NULL, NULL, 'admin', 'administrator', 'admin', 

'06', '120000128', NULL, NULL, NULL, 

'20100824', 'admin', NULL, NULL)
;
INSERT INTO zdbranch(BranchInnerCode, BranchCode, ParentInnerCode, Type, OrderFlag, Name, TreeLevel, IsLeaf, Phone, Fax, Manager, Leader1, 

Leader2, Prop1, Prop2, Prop3, Prop4, Memo, AddTime, AddUser, ModifyTime, ModifyUser)
  VALUES('120000095120000102120000129', '21209991021230001', '120000095120000102', '0', 129, '南开支行嘉陵道分理处', 3, 'Y', NULL, NULL, 'admin', 'administrator', 'admin', 

'06', '120000129', NULL, NULL, NULL, 

'20100824', 'admin', NULL, NULL)
;
INSERT INTO zdbranch(BranchInnerCode, BranchCode, ParentInnerCode, Type, OrderFlag, Name, TreeLevel, IsLeaf, Phone, Fax, Manager, Leader1, 

Leader2, Prop1, Prop2, Prop3, Prop4, Memo, AddTime, AddUser, ModifyTime, ModifyUser)
  VALUES('120000095120000102120000130', '2120999102121000B', '120000095120000102', '0', 130, '南开区黄河道进步里分理处', 3, 'Y', NULL, NULL, 'admin', 'administrator', 

'admin', '06', '120000130', NULL, NULL, 

NULL, '20100824', 'admin', NULL, NULL)
;
INSERT INTO zdbranch(BranchInnerCode, BranchCode, ParentInnerCode, Type, OrderFlag, Name, TreeLevel, IsLeaf, Phone, Fax, Manager, Leader1, 

Leader2, Prop1, Prop2, Prop3, Prop4, Memo, AddTime, AddUser, ModifyTime, ModifyUser)
  VALUES('120000095120000102120000131', '21209991021140002', '120000095120000102', '0', 131, '南开支行苑东路分理处', 3, 'Y', NULL, NULL, 'admin', 'administrator', 'admin', 

'06', '120000131', NULL, NULL, NULL, 

'20100824', 'admin', NULL, NULL)
;
INSERT INTO zdbranch(BranchInnerCode, BranchCode, ParentInnerCode, Type, OrderFlag, Name, TreeLevel, IsLeaf, Phone, Fax, Manager, Leader1, 

Leader2, Prop1, Prop2, Prop3, Prop4, Memo, AddTime, AddUser, ModifyTime, ModifyUser)
  VALUES('120000095120000102120000132', '2120999102112000C', '120000095120000102', '0', 132, '南开支行华苑信美道分理处', 3, 'Y', NULL, NULL, 'admin', 'administrator', 'admin', 

'06', '120000132', NULL, NULL, 

NULL, '20100824', 'admin', NULL, NULL)
;
INSERT INTO zdbranch(BranchInnerCode, BranchCode, ParentInnerCode, Type, OrderFlag, Name, TreeLevel, IsLeaf, Phone, Fax, Manager, Leader1, 

Leader2, Prop1, Prop2, Prop3, Prop4, Memo, AddTime, AddUser, ModifyTime, ModifyUser)
  VALUES('120000095120000102120000133', '2120999102124000R', '120000095120000102', '0', 133, '南开区雅安道分理处', 3, 'Y', NULL, NULL, 'admin', 'administrator', 'admin', 

'06', '120000133', NULL, NULL, NULL, 

'20100824', 'admin', NULL, NULL)
;
INSERT INTO zdbranch(BranchInnerCode, BranchCode, ParentInnerCode, Type, OrderFlag, Name, TreeLevel, IsLeaf, Phone, Fax, Manager, Leader1, 

Leader2, Prop1, Prop2, Prop3, Prop4, Memo, AddTime, AddUser, ModifyTime, ModifyUser)
  VALUES('120000095120000102120000134', '2120999102125000M', '120000095120000102', '0', 134, '南开支行凯立花园分理处', 3, 'Y', NULL, NULL, 'admin', 'administrator', 'admin', 

'06', '120000134', NULL, NULL, 

NULL, '20100824', 'admin', NULL, NULL)
;
INSERT INTO zdbranch(BranchInnerCode, BranchCode, ParentInnerCode, Type, OrderFlag, Name, TreeLevel, IsLeaf, Phone, Fax, Manager, Leader1, 

Leader2, Prop1, Prop2, Prop3, Prop4, Memo, AddTime, AddUser, ModifyTime, ModifyUser)
  VALUES('120000095120000135', '2120999103000000R', '120000095', '0', 135, '和平区支行', 2, 'Y', NULL, NULL, 'admin', 'administrator', 'admin', '04', '120000135', 

NULL, NULL, NULL, 

'20100824', 'admin', NULL, NULL)
;
INSERT INTO zdbranch(BranchInnerCode, BranchCode, ParentInnerCode, Type, OrderFlag, Name, TreeLevel, IsLeaf, Phone, Fax, Manager, Leader1, 

Leader2, Prop1, Prop2, Prop3, Prop4, Memo, AddTime, AddUser, ModifyTime, ModifyUser)
  VALUES('120000095120000135120000136', '2120999103001000M', '120000095120000135', '0', 136, '和平支行机关', 3, 'Y', NULL, NULL, 'admin', 'administrator', 'admin', '04', 

'120000136', NULL, NULL, NULL, 

'20100824', 'admin', NULL, NULL)
;
INSERT INTO zdbranch(BranchInnerCode, BranchCode, ParentInnerCode, Type, OrderFlag, Name, TreeLevel, IsLeaf, Phone, Fax, Manager, Leader1, 

Leader2, Prop1, Prop2, Prop3, Prop4, Memo, AddTime, AddUser, ModifyTime, ModifyUser)
  VALUES('120000095120000135120000136120000137', '2120999103001002I', '120000095120000135120000136', '0', 137, '和平支行其他', 4, 'Y', NULL, NULL, 'admin', 'administrator', 'admin', '04', '120000137', 

NULL, NULL, NULL, 

'20100824', 'admin', NULL, NULL)
;
INSERT INTO zdbranch(BranchInnerCode, BranchCode, ParentInnerCode, Type, OrderFlag, Name, TreeLevel, IsLeaf, Phone, Fax, Manager, Leader1, 

Leader2, Prop1, Prop2, Prop3, Prop4, Memo, AddTime, AddUser, ModifyTime, ModifyUser)
  VALUES('120000095120000135120000136120000138', '2120999103001003G', '120000095120000135120000136', '0', 138, '和平支行综合管理部', 4, 'Y', NULL, NULL, 'admin', 'administrator', 'admin', '04', 

'120000138', NULL, NULL, NULL, 

'20100824', 'admin', NULL, NULL)
;
INSERT INTO zdbranch(BranchInnerCode, BranchCode, ParentInnerCode, Type, OrderFlag, Name, TreeLevel, IsLeaf, Phone, Fax, Manager, Leader1, 

Leader2, Prop1, Prop2, Prop3, Prop4, Memo, AddTime, AddUser, ModifyTime, ModifyUser)
  VALUES('120000095120000135120000136120000139', '2120999103001001K', '120000095120000135120000136', '0', 139, '和平支行行长室', 4, 'Y', NULL, NULL, 'admin', 'administrator', 'admin', '04', 

'120000139', NULL, NULL, NULL, 

'20100824', 'admin', NULL, NULL)
;
INSERT INTO zdbranch(BranchInnerCode, BranchCode, ParentInnerCode, Type, OrderFlag, Name, TreeLevel, IsLeaf, Phone, Fax, Manager, Leader1, 

Leader2, Prop1, Prop2, Prop3, Prop4, Memo, AddTime, AddUser, ModifyTime, ModifyUser)
  VALUES('120000095120000135120000136120000140', '2120999103001004E', '120000095120000135120000136', '0', 140, '和平支行内退人员', 4, 'Y', NULL, NULL, 'admin', 'administrator', 'admin', '04', 

'120000140', NULL, NULL, NULL, 

'20100824', 'admin', NULL, NULL)
;
INSERT INTO zdbranch(BranchInnerCode, BranchCode, ParentInnerCode, Type, OrderFlag, Name, TreeLevel, IsLeaf, Phone, Fax, Manager, Leader1, 

Leader2, Prop1, Prop2, Prop3, Prop4, Memo, AddTime, AddUser, ModifyTime, ModifyUser)
  VALUES('120000095120000135120000136120000141', '2120999103001005C', '120000095120000135120000136', '0', 141, '和平支行财会运营部', 4, 'Y', NULL, NULL, 'admin', 'administrator', 'admin', '04', 

'120000141', NULL, NULL, NULL, 

'20100824', 'admin', NULL, NULL)
;
INSERT INTO zdbranch(BranchInnerCode, BranchCode, ParentInnerCode, Type, OrderFlag, Name, TreeLevel, IsLeaf, Phone, Fax, Manager, Leader1, 

Leader2, Prop1, Prop2, Prop3, Prop4, Memo, AddTime, AddUser, ModifyTime, ModifyUser)
  VALUES('120000095120000135120000136120000142', '2120999103001006A', '120000095120000135120000136', '0', 142, '和平支行代理保险部', 4, 'Y', NULL, NULL, 'admin', 'administrator', 'admin', '04', 

'120000142', NULL, NULL, NULL, 

'20100824', 'admin', NULL, NULL)
;
INSERT INTO zdbranch(BranchInnerCode, BranchCode, ParentInnerCode, Type, OrderFlag, Name, TreeLevel, IsLeaf, Phone, Fax, Manager, Leader1, 

Leader2, Prop1, Prop2, Prop3, Prop4, Memo, AddTime, AddUser, ModifyTime, ModifyUser)
  VALUES('120000095120000135120000136120000143', '21209991030010078', '120000095120000135120000136', '0', 143, '和平支行信贷管理部', 4, 'Y', NULL, NULL, 'admin', 'administrator', 'admin', '04', 

'120000143', NULL, NULL, NULL, 

'20100824', 'admin', NULL, NULL)
;
INSERT INTO zdbranch(BranchInnerCode, BranchCode, ParentInnerCode, Type, OrderFlag, Name, TreeLevel, IsLeaf, Phone, Fax, Manager, Leader1, 

Leader2, Prop1, Prop2, Prop3, Prop4, Memo, AddTime, AddUser, ModifyTime, ModifyUser)
  VALUES('120000095120000135120000136120000144', '21209991030010086', '120000095120000135120000136', '0', 144, '和平支行国际业务部', 4, 'Y', NULL, NULL, 'admin', 'administrator', 'admin', '04', 

'120000144', NULL, NULL, NULL, 

'20100824', 'admin', NULL, NULL)
;
INSERT INTO zdbranch(BranchInnerCode, BranchCode, ParentInnerCode, Type, OrderFlag, Name, TreeLevel, IsLeaf, Phone, Fax, Manager, Leader1, 

Leader2, Prop1, Prop2, Prop3, Prop4, Memo, AddTime, AddUser, ModifyTime, ModifyUser)
  VALUES('120000095120000135120000136120000145', '21209991030010094', '120000095120000135120000136', '0', 145, '和平支行监察保卫/内控合规部', 4, 'Y', NULL, NULL, 'admin', 'administrator', 

'admin', '04', '120000145', NULL, NULL, 

NULL, '20100824', 'admin', NULL, NULL)
;
INSERT INTO zdbranch(BranchInnerCode, BranchCode, ParentInnerCode, Type, OrderFlag, Name, TreeLevel, IsLeaf, Phone, Fax, Manager, Leader1, 

Leader2, Prop1, Prop2, Prop3, Prop4, Memo, AddTime, AddUser, ModifyTime, ModifyUser)
  VALUES('120000095120000135120000136120000146', '2120999103001010J', '120000095120000135120000136', '0', 146, '和平支行离退休人员', 4, 'Y', NULL, NULL, 'admin', 'administrator', 'admin', '04', 

'120000146', NULL, NULL, NULL, 

'20100824', 'admin', NULL, NULL)
;
INSERT INTO zdbranch(BranchInnerCode, BranchCode, ParentInnerCode, Type, OrderFlag, Name, TreeLevel, IsLeaf, Phone, Fax, Manager, Leader1, 

Leader2, Prop1, Prop2, Prop3, Prop4, Memo, AddTime, AddUser, ModifyTime, ModifyUser)
  VALUES('120000095120000135120000136120000147', '2120999103001011H', '120000095120000135120000136', '0', 147, '和平支行公司业务部', 4, 'Y', NULL, NULL, 'admin', 'administrator', 'admin', '04', 

'120000147', NULL, NULL, NULL, 

'20100824', 'admin', NULL, NULL)
;
INSERT INTO zdbranch(BranchInnerCode, BranchCode, ParentInnerCode, Type, OrderFlag, Name, TreeLevel, IsLeaf, Phone, Fax, Manager, Leader1, 

Leader2, Prop1, Prop2, Prop3, Prop4, Memo, AddTime, AddUser, ModifyTime, ModifyUser)
  VALUES('120000095120000135120000136120000148', '2120999103001012F', '120000095120000135120000136', '0', 148, '和平支行个人金融部', 4, 'Y', NULL, NULL, 'admin', 'administrator', 'admin', '04', 

'120000148', NULL, NULL, NULL, 

'20100824', 'admin', NULL, NULL)
;
INSERT INTO zdbranch(BranchInnerCode, BranchCode, ParentInnerCode, Type, OrderFlag, Name, TreeLevel, IsLeaf, Phone, Fax, Manager, Leader1, 

Leader2, Prop1, Prop2, Prop3, Prop4, Memo, AddTime, AddUser, ModifyTime, ModifyUser)
  VALUES('120000095120000135120000149', '2120999103102000A', '120000095120000135', '0', 149, '港澳大厦支行', 3, 'Y', NULL, NULL, 'admin', 'administrator', 'admin', '06', 

'120000149', NULL, NULL, NULL, 

'20100824', 'admin', NULL, NULL)
;
INSERT INTO zdbranch(BranchInnerCode, BranchCode, ParentInnerCode, Type, OrderFlag, Name, TreeLevel, IsLeaf, Phone, Fax, Manager, Leader1, 

Leader2, Prop1, Prop2, Prop3, Prop4, Memo, AddTime, AddUser, ModifyTime, ModifyUser)
  VALUES('120000095120000135120000150', '21209991031030005', '120000095120000135', '0', 150, '宏达大厦支行', 3, 'Y', NULL, NULL, 'admin', 'administrator', 'admin', '06', '120000150', 

NULL, NULL, NULL, 

'20100824', 'admin', NULL, NULL)
;
INSERT INTO zdbranch(BranchInnerCode, BranchCode, ParentInnerCode, Type, OrderFlag, Name, TreeLevel, IsLeaf, Phone, Fax, Manager, Leader1, 

Leader2, Prop1, Prop2, Prop3, Prop4, Memo, AddTime, AddUser, ModifyTime, ModifyUser)
  VALUES('120000095120000135120000151', '21209991031040000', '120000095120000135', '0', 151, '新文化花园支行', 3, 'Y', NULL, NULL, 'admin', 'administrator', 'admin', '06', 

'120000151', NULL, NULL, NULL, 

'20100824', 'admin', NULL, NULL)
;
INSERT INTO zdbranch(BranchInnerCode, BranchCode, ParentInnerCode, Type, OrderFlag, Name, TreeLevel, IsLeaf, Phone, Fax, Manager, Leader1, 

Leader2, Prop1, Prop2, Prop3, Prop4, Memo, AddTime, AddUser, ModifyTime, ModifyUser)
  VALUES('120000095120000135120000152', '2120999103105000Q', '120000095120000135', '0', 152, '哈尔滨道支行', 3, 'Y', NULL, NULL, 'admin', 'administrator', 'admin', '06', 

'120000152', NULL, NULL, NULL, 

'20100824', 'admin', NULL, NULL)
;
INSERT INTO zdbranch(BranchInnerCode, BranchCode, ParentInnerCode, Type, OrderFlag, Name, TreeLevel, IsLeaf, Phone, Fax, Manager, Leader1, 

Leader2, Prop1, Prop2, Prop3, Prop4, Memo, AddTime, AddUser, ModifyTime, ModifyUser)
  VALUES('120000095120000135120000153', '2120999103106000L', '120000095120000135', '0', 153, '和平支行营口道分理处', 3, 'Y', NULL, NULL, 'admin', 'administrator', 'admin', 

'06', '120000153', NULL, NULL, NULL, 

'20100824', 'admin', NULL, NULL)
;
INSERT INTO zdbranch(BranchInnerCode, BranchCode, ParentInnerCode, Type, OrderFlag, Name, TreeLevel, IsLeaf, Phone, Fax, Manager, Leader1, 

Leader2, Prop1, Prop2, Prop3, Prop4, Memo, AddTime, AddUser, ModifyTime, ModifyUser)
  VALUES('120000095120000135120000154', '2120999103107000G', '120000095120000135', '0', 154, '四平道支行', 3, 'Y', NULL, NULL, 'admin', 'administrator', 'admin', '06', '120000154', 

NULL, NULL, NULL, 

'20100824', 'admin', NULL, NULL)
;
INSERT INTO zdbranch(BranchInnerCode, BranchCode, ParentInnerCode, Type, OrderFlag, Name, TreeLevel, IsLeaf, Phone, Fax, Manager, Leader1, 

Leader2, Prop1, Prop2, Prop3, Prop4, Memo, AddTime, AddUser, ModifyTime, ModifyUser)
  VALUES('120000095120000135120000155', '2120999103108000B', '120000095120000135', '0', 155, '吉利花园支行', 3, 'Y', NULL, NULL, 'admin', 'administrator', 'admin', '06', 

'120000155', NULL, NULL, NULL, 

'20100824', 'admin', NULL, NULL)
;
INSERT INTO zdbranch(BranchInnerCode, BranchCode, ParentInnerCode, Type, OrderFlag, Name, TreeLevel, IsLeaf, Phone, Fax, Manager, Leader1, 

Leader2, Prop1, Prop2, Prop3, Prop4, Memo, AddTime, AddUser, ModifyTime, ModifyUser)
  VALUES('120000095120000135120000156', '21209991031110009', '120000095120000135', '0', 156, '解放南路支行', 3, 'Y', NULL, NULL, 'admin', 'administrator', 'admin', '06', 

'120000156', NULL, NULL, NULL, 

'20100824', 'admin', NULL, NULL)
;
INSERT INTO zdbranch(BranchInnerCode, BranchCode, ParentInnerCode, Type, OrderFlag, Name, TreeLevel, IsLeaf, Phone, Fax, Manager, Leader1, 

Leader2, Prop1, Prop2, Prop3, Prop4, Memo, AddTime, AddUser, ModifyTime, ModifyUser)
  VALUES('120000095120000135120000157', '21209991031090006', '120000095120000135', '0', 157, '和平支行宜昌道分理处', 3, 'Y', NULL, NULL, 'admin', 'administrator', 'admin', 

'06', '120000157', NULL, NULL, NULL, 

'20100824', 'admin', NULL, NULL)
;
INSERT INTO zdbranch(BranchInnerCode, BranchCode, ParentInnerCode, Type, OrderFlag, Name, TreeLevel, IsLeaf, Phone, Fax, Manager, Leader1, 

Leader2, Prop1, Prop2, Prop3, Prop4, Memo, AddTime, AddUser, ModifyTime, ModifyUser)
  VALUES('120000095120000135120000158', '21209991031180005', '120000095120000135', '0', 158, '和平区新兴里储蓄所', 3, 'Y', NULL, NULL, 'admin', 'administrator', 'admin', 

'06', '120000158', NULL, NULL, NULL, 

'20100824', 'admin', NULL, NULL)
;
INSERT INTO zdbranch(BranchInnerCode, BranchCode, ParentInnerCode, Type, OrderFlag, Name, TreeLevel, IsLeaf, Phone, Fax, Manager, Leader1, 

Leader2, Prop1, Prop2, Prop3, Prop4, Memo, AddTime, AddUser, ModifyTime, ModifyUser)
  VALUES('120000095120000135120000159', '21209991031120004', '120000095120000135', '0', 159, '和平支行新华路储蓄所', 3, 'Y', NULL, NULL, 'admin', 'administrator', 'admin', 

'10', '120000159', NULL, NULL, NULL, 

'20100824', 'admin', NULL, NULL)
;
INSERT INTO zdbranch(BranchInnerCode, BranchCode, ParentInnerCode, Type, OrderFlag, Name, TreeLevel, IsLeaf, Phone, Fax, Manager, Leader1, 

Leader2, Prop1, Prop2, Prop3, Prop4, Memo, AddTime, AddUser, ModifyTime, ModifyUser)
  VALUES('120000095120000135120000160', '2120999103115000K', '120000095120000135', '0', 160, '和平支行云南路储蓄所', 3, 'Y', NULL, NULL, 'admin', 'administrator', 'admin', 

'10', '120000160', NULL, NULL, NULL, 

'20100824', 'admin', NULL, NULL)
;
INSERT INTO zdbranch(BranchInnerCode, BranchCode, ParentInnerCode, Type, OrderFlag, Name, TreeLevel, IsLeaf, Phone, Fax, Manager, Leader1, 

Leader2, Prop1, Prop2, Prop3, Prop4, Memo, AddTime, AddUser, ModifyTime, ModifyUser)
  VALUES('120000095120000135120000161', '2120999103117000A', '120000095120000135', '0', 161, '和平支行西安道分理处', 3, 'Y', NULL, NULL, 'admin', 'administrator', 'admin', 

'06', '120000161', NULL, NULL, NULL, 

'20100824', 'admin', NULL, NULL)
;
INSERT INTO zdbranch(BranchInnerCode, BranchCode, ParentInnerCode, Type, OrderFlag, Name, TreeLevel, IsLeaf, Phone, Fax, Manager, Leader1, 

Leader2, Prop1, Prop2, Prop3, Prop4, Memo, AddTime, AddUser, ModifyTime, ModifyUser)
  VALUES('120000095120000135120000162', '2120999103114000P', '120000095120000135', '0', 162, '和平支行河北路分理处', 3, 'Y', NULL, NULL, 'admin', 'administrator', 'admin', 

'06', '120000162', NULL, NULL, NULL, 

'20100824', 'admin', NULL, NULL)
;
INSERT INTO zdbranch(BranchInnerCode, BranchCode, ParentInnerCode, Type, OrderFlag, Name, TreeLevel, IsLeaf, Phone, Fax, Manager, Leader1, 

Leader2, Prop1, Prop2, Prop3, Prop4, Memo, AddTime, AddUser, ModifyTime, ModifyUser)
  VALUES('120000095120000135120000163', '2120999103116000F', '120000095120000135', '0', 163, '和平支行河沿路储蓄所', 3, 'Y', NULL, NULL, 'admin', 'administrator', 'admin', 

'10', '120000163', NULL, NULL, NULL, 

'20100824', 'admin', NULL, NULL)
;
INSERT INTO zdbranch(BranchInnerCode, BranchCode, ParentInnerCode, Type, OrderFlag, Name, TreeLevel, IsLeaf, Phone, Fax, Manager, Leader1, 

Leader2, Prop1, Prop2, Prop3, Prop4, Memo, AddTime, AddUser, ModifyTime, ModifyUser)
  VALUES('120000095120000135120000164', '2120999103113000U', '120000095120000135', '0', 164, '和平支行哈密道储蓄所', 3, 'Y', NULL, NULL, 'admin', 'administrator', 'admin', 

'10', '120000164', NULL, NULL, NULL, 

'20100824', 'admin', NULL, NULL)
;
INSERT INTO zdbranch(BranchInnerCode, BranchCode, ParentInnerCode, Type, OrderFlag, Name, TreeLevel, IsLeaf, Phone, Fax, Manager, Leader1, 

Leader2, Prop1, Prop2, Prop3, Prop4, Memo, AddTime, AddUser, ModifyTime, ModifyUser)
  VALUES('120000095120000165', '2120999104000000J', '120000095', '0', 165, '河西支行', 2, 'Y', NULL, NULL, 'admin', 'administrator', 'admin', '04', '120000165', 

NULL, NULL, NULL, '20100824', 'admin', NULL, NULL);
INSERT INTO zdbranch(BranchInnerCode, BranchCode, ParentInnerCode, Type, OrderFlag, Name, TreeLevel, IsLeaf, Phone, Fax, Manager, Leader1, 

Leader2, Prop1, Prop2, Prop3, Prop4, Memo, AddTime, AddUser, ModifyTime, ModifyUser)
  VALUES('120000095120000165120000166', '2120999104001000E', '120000095120000165', '0', 166, '河西支行支行机关', 3, 'Y', NULL, NULL, 'admin', 'administrator', 'admin', '04', 

'120000166', NULL, NULL, NULL, 

'20100824', 'admin', NULL, NULL)
;
INSERT INTO zdbranch(BranchInnerCode, BranchCode, ParentInnerCode, Type, OrderFlag, Name, TreeLevel, IsLeaf, Phone, Fax, Manager, Leader1, 

Leader2, Prop1, Prop2, Prop3, Prop4, Memo, AddTime, AddUser, ModifyTime, ModifyUser)
  VALUES('120000095120000165120000166120000167', '2120999104001001C', '120000095120000165120000166', '0', 167, '河西支行支行其他', 4, 'Y', NULL, NULL, 'admin', 'administrator', 'admin', '04', 

'120000167', NULL, NULL, NULL, 

'20100824', 'admin', NULL, NULL)
;
INSERT INTO zdbranch(BranchInnerCode, BranchCode, ParentInnerCode, Type, OrderFlag, Name, TreeLevel, IsLeaf, Phone, Fax, Manager, Leader1, 

Leader2, Prop1, Prop2, Prop3, Prop4, Memo, AddTime, AddUser, ModifyTime, ModifyUser)
  VALUES('120000095120000165120000166120000168', '2120999104001002A', '120000095120000165120000166', '0', 168, '河西支行综合管理部', 4, 'Y', NULL, NULL, 'admin', 'administrator', 'admin', '04', 

'120000168', NULL, NULL, NULL, 

'20100824', 'admin', NULL, NULL)
;
INSERT INTO zdbranch(BranchInnerCode, BranchCode, ParentInnerCode, Type, OrderFlag, Name, TreeLevel, IsLeaf, Phone, Fax, Manager, Leader1, 

Leader2, Prop1, Prop2, Prop3, Prop4, Memo, AddTime, AddUser, ModifyTime, ModifyUser)
  VALUES('120000095120000165120000166120000169', '21209991040010038', '120000095120000165120000166', '0', 169, '河西支行行长室', 4, 'Y', NULL, NULL, 'admin', 'administrator', 'admin', '04', 

'120000169', NULL, NULL, NULL, 

'20100824', 'admin', NULL, NULL)
;
INSERT INTO zdbranch(BranchInnerCode, BranchCode, ParentInnerCode, Type, OrderFlag, Name, TreeLevel, IsLeaf, Phone, Fax, Manager, Leader1, 

Leader2, Prop1, Prop2, Prop3, Prop4, Memo, AddTime, AddUser, ModifyTime, ModifyUser)
  VALUES('120000095120000165120000166120000170', '21209991040010046', '120000095120000165120000166', '0', 170, '河西支行支行内退人员', 4, 'Y', NULL, NULL, 'admin', 'administrator', 'admin', 

'04', '120000170', NULL, NULL, NULL, 

'20100824', 'admin', NULL, NULL)
;
INSERT INTO zdbranch(BranchInnerCode, BranchCode, ParentInnerCode, Type, OrderFlag, Name, TreeLevel, IsLeaf, Phone, Fax, Manager, Leader1, 

Leader2, Prop1, Prop2, Prop3, Prop4, Memo, AddTime, AddUser, ModifyTime, ModifyUser)
  VALUES('120000095120000165120000166120000171', '21209991040010054', '120000095120000165120000166', '0', 171, '河西支行客户一部', 4, 'Y', NULL, NULL, 'admin', 'administrator', 'admin', '04', 

'120000171', NULL, NULL, NULL, 

'20100824', 'admin', NULL, NULL)
;
INSERT INTO zdbranch(BranchInnerCode, BranchCode, ParentInnerCode, Type, OrderFlag, Name, TreeLevel, IsLeaf, Phone, Fax, Manager, Leader1, 

Leader2, Prop1, Prop2, Prop3, Prop4, Memo, AddTime, AddUser, ModifyTime, ModifyUser)
  VALUES('120000095120000165120000166120000172', '21209991040010062', '120000095120000165120000166', '0', 172, '河西支行支行公司业务部', 4, 'Y', NULL, NULL, 'admin', 'administrator', 'admin', 

'04', '120000172', NULL, NULL, 

NULL, '20100824', 'admin', NULL, NULL)
;
INSERT INTO zdbranch(BranchInnerCode, BranchCode, ParentInnerCode, Type, OrderFlag, Name, TreeLevel, IsLeaf, Phone, Fax, Manager, Leader1, 

Leader2, Prop1, Prop2, Prop3, Prop4, Memo, AddTime, AddUser, ModifyTime, ModifyUser)
  VALUES('120000095120000165120000166120000173', '2120999104001007V', '120000095120000165120000166', '0', 173, '河西支行财会运营部', 4, 'Y', NULL, NULL, 'admin', 'administrator', 'admin', '04', 

'120000173', NULL, NULL, NULL, 

'20100824', 'admin', NULL, NULL)
;
INSERT INTO zdbranch(BranchInnerCode, BranchCode, ParentInnerCode, Type, OrderFlag, Name, TreeLevel, IsLeaf, Phone, Fax, Manager, Leader1, 

Leader2, Prop1, Prop2, Prop3, Prop4, Memo, AddTime, AddUser, ModifyTime, ModifyUser)
  VALUES('120000095120000165120000166120000174', '2120999104001008T', '120000095120000165120000166', '0', 174, '河西支行监察保卫部', 4, 'Y', NULL, NULL, 'admin', 'administrator', 'admin', '04', 

'120000174', NULL, NULL, NULL, 

'20100824', 'admin', NULL, NULL)
;
INSERT INTO zdbranch(BranchInnerCode, BranchCode, ParentInnerCode, Type, OrderFlag, Name, TreeLevel, IsLeaf, Phone, Fax, Manager, Leader1, 

Leader2, Prop1, Prop2, Prop3, Prop4, Memo, AddTime, AddUser, ModifyTime, ModifyUser)
  VALUES('120000095120000165120000166120000175', '2120999104001009R', '120000095120000165120000166', '0', 175, '河西支行支行离退休人员', 4, 'Y', NULL, NULL, 'admin', 'administrator', 'admin', 

'04', '120000175', NULL, NULL, 

NULL, '20100824', 'admin', NULL, NULL)
;
INSERT INTO zdbranch(BranchInnerCode, BranchCode, ParentInnerCode, Type, OrderFlag, Name, TreeLevel, IsLeaf, Phone, Fax, Manager, Leader1, 

Leader2, Prop1, Prop2, Prop3, Prop4, Memo, AddTime, AddUser, ModifyTime, ModifyUser)
  VALUES('120000095120000165120000166120000176', '2120999104001010B', '120000095120000165120000166', '0', 176, '河西支行个人金融部', 4, 'Y', NULL, NULL, 'admin', 'administrator', 'admin', '04', 

'120000176', NULL, NULL, NULL, 

'20100824', 'admin', NULL, NULL)
;
INSERT INTO zdbranch(BranchInnerCode, BranchCode, ParentInnerCode, Type, OrderFlag, Name, TreeLevel, IsLeaf, Phone, Fax, Manager, Leader1, 

Leader2, Prop1, Prop2, Prop3, Prop4, Memo, AddTime, AddUser, ModifyTime, ModifyUser)
  VALUES('120000095120000165120000166120000177', '21209991040010119', '120000095120000165120000166', '0', 177, '河西支行支行风险管理部', 4, 'Y', NULL, NULL, 'admin', 'administrator', 'admin', 

'04', '120000177', NULL, NULL, 

NULL, '20100824', 'admin', NULL, NULL)
;
INSERT INTO zdbranch(BranchInnerCode, BranchCode, ParentInnerCode, Type, OrderFlag, Name, TreeLevel, IsLeaf, Phone, Fax, Manager, Leader1, 

Leader2, Prop1, Prop2, Prop3, Prop4, Memo, AddTime, AddUser, ModifyTime, ModifyUser)
  VALUES('120000095120000165120000166120000178', '21209991040010127', '120000095120000165120000166', '0', 178, '河西支行公司业务部', 4, 'Y', NULL, NULL, 'admin', 'administrator', 'admin', '04', 

'120000178', NULL, NULL, NULL, 

'20100824', 'admin', NULL, NULL)
;
INSERT INTO zdbranch(BranchInnerCode, BranchCode, ParentInnerCode, Type, OrderFlag, Name, TreeLevel, IsLeaf, Phone, Fax, Manager, Leader1, 

Leader2, Prop1, Prop2, Prop3, Prop4, Memo, AddTime, AddUser, ModifyTime, ModifyUser)
  VALUES('120000095120000165120000179', '21209991041010007', '120000095120000165', '0', 179, '长青支行', 3, 'Y', NULL, NULL, 'admin', 'administrator', 'admin', '06', '120000179', 

NULL, NULL, NULL, '20100824', 'admin', NULL, NULL);
INSERT INTO zdbranch(BranchInnerCode, BranchCode, ParentInnerCode, Type, OrderFlag, Name, TreeLevel, IsLeaf, Phone, Fax, Manager, Leader1, 

Leader2, Prop1, Prop2, Prop3, Prop4, Memo, AddTime, AddUser, ModifyTime, ModifyUser)
  VALUES('120000095120000165120000180', '2120999104122000L', '120000095120000165', '0', 180, '河西支行上海道储蓄所', 3, 'Y', NULL, NULL, 'admin', 'administrator', 'admin', 

'10', '120000180', NULL, NULL, 

NULL, '20100824', 'admin', NULL, NULL)
;
INSERT INTO zdbranch(BranchInnerCode, BranchCode, ParentInnerCode, Type, OrderFlag, Name, TreeLevel, IsLeaf, Phone, Fax, Manager, Leader1, 

Leader2, Prop1, Prop2, Prop3, Prop4, Memo, AddTime, AddUser, ModifyTime, ModifyUser)
  VALUES('120000095120000165120000181', '2120999104115000C', '120000095120000165', '0', 181, '河西区大沽南路分理处', 3, 'Y', NULL, NULL, 'admin', 'administrator', 'admin', 

'06', '120000181', NULL, NULL, 

NULL, '20100824', 'admin', NULL, NULL)
;
INSERT INTO zdbranch(BranchInnerCode, BranchCode, ParentInnerCode, Type, OrderFlag, Name, TreeLevel, IsLeaf, Phone, Fax, Manager, Leader1, 

Leader2, Prop1, Prop2, Prop3, Prop4, Memo, AddTime, AddUser, ModifyTime, ModifyUser)
  VALUES('120000095120000165120000182', '21209991041020002', '120000095120000165', '0', 182, '天塔支行', 3, 'Y', NULL, NULL, 'admin', 'administrator', 'admin', '06', '120000182', 

NULL, NULL, NULL, '20100824', 'admin', NULL, NULL)
;
INSERT INTO zdbranch(BranchInnerCode, BranchCode, ParentInnerCode, Type, OrderFlag, Name, TreeLevel, IsLeaf, Phone, Fax, Manager, Leader1, 

Leader2, Prop1, Prop2, Prop3, Prop4, Memo, AddTime, AddUser, ModifyTime, ModifyUser)
  VALUES('120000095120000165120000183', '2120999104103000S', '120000095120000165', '0', 183, '大沽路支行', 3, 'Y', NULL, NULL, 'admin', 'administrator', 'admin', '06', '120000183', 

NULL, NULL, NULL, 

'20100824', 'admin', NULL, NULL)
;
INSERT INTO zdbranch(BranchInnerCode, BranchCode, ParentInnerCode, Type, OrderFlag, Name, TreeLevel, IsLeaf, Phone, Fax, Manager, Leader1, 

Leader2, Prop1, Prop2, Prop3, Prop4, Memo, AddTime, AddUser, ModifyTime, ModifyUser)
  VALUES('120000095120000165120000184', '21209991041170002', '120000095120000165', '0', 184, '河西支行利民道储蓄所', 3, 'Y', NULL, NULL, 'admin', 'administrator', 'admin', 

'10', '120000184', NULL, NULL, NULL, 

'20100824', 'admin', NULL, NULL)
;
INSERT INTO zdbranch(BranchInnerCode, BranchCode, ParentInnerCode, Type, OrderFlag, Name, TreeLevel, IsLeaf, Phone, Fax, Manager, Leader1, 

Leader2, Prop1, Prop2, Prop3, Prop4, Memo, AddTime, AddUser, ModifyTime, ModifyUser)
  VALUES('120000095120000165120000185', '21209991041100006', '120000095120000165', '0', 185, '金牛支行', 3, 'Y', NULL, NULL, 'admin', 'administrator', 'admin', '06', '120000185', 

NULL, NULL, NULL, '20100824', 'admin', NULL, NULL)
;
INSERT INTO zdbranch(BranchInnerCode, BranchCode, ParentInnerCode, Type, OrderFlag, Name, TreeLevel, IsLeaf, Phone, Fax, Manager, Leader1, 

Leader2, Prop1, Prop2, Prop3, Prop4, Memo, AddTime, AddUser, ModifyTime, ModifyUser)
  VALUES('120000095120000165120000186', '21209991041160007', '120000095120000165', '0', 186, '河西支行儿童大世界储蓄所', 3, 'Y', NULL, NULL, 'admin', 'administrator', 'admin', 

'10', '120000186', NULL, NULL, 

NULL, '20100824', 'admin', NULL, NULL)
;
INSERT INTO zdbranch(BranchInnerCode, BranchCode, ParentInnerCode, Type, OrderFlag, Name, TreeLevel, IsLeaf, Phone, Fax, Manager, Leader1, 

Leader2, Prop1, Prop2, Prop3, Prop4, Memo, AddTime, AddUser, ModifyTime, ModifyUser)
  VALUES('120000095120000165120000187', '2120999104123000G', '120000095120000165', '0', 187, '河西支行文明北里分理处', 3, 'Y', NULL, NULL, 'admin', 'administrator', 'admin', 

'06', '120000187', NULL, NULL, 

NULL, '20100824', 'admin', NULL, NULL)
;
INSERT INTO zdbranch(BranchInnerCode, BranchCode, ParentInnerCode, Type, OrderFlag, Name, TreeLevel, IsLeaf, Phone, Fax, Manager, Leader1, 

Leader2, Prop1, Prop2, Prop3, Prop4, Memo, AddTime, AddUser, ModifyTime, ModifyUser)
  VALUES('120000095120000165120000188', '2120999104104000N', '120000095120000165', '0', 188, '河西区佟楼分理处', 3, 'Y', NULL, NULL, 'admin', 'administrator', 'admin', '06', 

'120000188', NULL, NULL, NULL, 

'20100824', 'admin', NULL, NULL)
;
INSERT INTO zdbranch(BranchInnerCode, BranchCode, ParentInnerCode, Type, OrderFlag, Name, TreeLevel, IsLeaf, Phone, Fax, Manager, Leader1, 

Leader2, Prop1, Prop2, Prop3, Prop4, Memo, AddTime, AddUser, ModifyTime, ModifyUser)
  VALUES('120000095120000165120000189', '2120999104114000H', '120000095120000165', '0', 189, '河西区绍兴道分理处', 3, 'Y', NULL, NULL, 'admin', 'administrator', 'admin', 

'06', '120000189', NULL, NULL, NULL, 

'20100824', 'admin', NULL, NULL)
;
INSERT INTO zdbranch(BranchInnerCode, BranchCode, ParentInnerCode, Type, OrderFlag, Name, TreeLevel, IsLeaf, Phone, Fax, Manager, Leader1, 

Leader2, Prop1, Prop2, Prop3, Prop4, Memo, AddTime, AddUser, ModifyTime, ModifyUser)
  VALUES('120000095120000165120000190', '2120999104118000S', '120000095120000165', '0', 190, '河西区信济里分理处', 3, 'Y', NULL, NULL, 'admin', 'administrator', 'admin', 

'06', '120000190', NULL, NULL, NULL, 

'20100824', 'admin', NULL, NULL)
;
INSERT INTO zdbranch(BranchInnerCode, BranchCode, ParentInnerCode, Type, OrderFlag, Name, TreeLevel, IsLeaf, Phone, Fax, Manager, Leader1, 

Leader2, Prop1, Prop2, Prop3, Prop4, Memo, AddTime, AddUser, ModifyTime, ModifyUser)
  VALUES('120000095120000165120000191', '2120999104105000I', '120000095120000165', '0', 191, '体北道支行', 3, 'Y', NULL, NULL, 'admin', 'administrator', 'admin', '06', '120000191', 

NULL, NULL, NULL, '20100824', 'admin', NULL, NULL)
;
INSERT INTO zdbranch(BranchInnerCode, BranchCode, ParentInnerCode, Type, OrderFlag, Name, TreeLevel, IsLeaf, Phone, Fax, Manager, Leader1, 

Leader2, Prop1, Prop2, Prop3, Prop4, Memo, AddTime, AddUser, ModifyTime, ModifyUser)
  VALUES('120000095120000165120000192', '2120999104106000D', '120000095120000165', '0', 192, '友谊路金融街支行', 3, 'Y', NULL, NULL, 'admin', 'administrator', 'admin', '06', 

'120000192', NULL, NULL, NULL, 

'20100824', 'admin', NULL, NULL)
;
INSERT INTO zdbranch(BranchInnerCode, BranchCode, ParentInnerCode, Type, OrderFlag, Name, TreeLevel, IsLeaf, Phone, Fax, Manager, Leader1, 

Leader2, Prop1, Prop2, Prop3, Prop4, Memo, AddTime, AddUser, ModifyTime, ModifyUser)
  VALUES('120000095120000165120000193', '21209991041070008', '120000095120000165', '0', 193, '微山路支行', 3, 'Y', NULL, NULL, 'admin', 'administrator', 'admin', '06', '120000193', 

NULL, NULL, NULL, '20100824', 'admin', NULL, NULL);
INSERT INTO zdbranch(BranchInnerCode, BranchCode, ParentInnerCode, Type, OrderFlag, Name, TreeLevel, IsLeaf, Phone, Fax, Manager, Leader1, 

Leader2, Prop1, Prop2, Prop3, Prop4, Memo, AddTime, AddUser, ModifyTime, ModifyUser)
  VALUES('120000095120000165120000194', '21209991041200000', '120000095120000165', '0', 194, '河西支行双水道储蓄所', 3, 'Y', NULL, NULL, 'admin', 'administrator', 'admin', 

'10', '120000194', NULL, NULL, 

NULL, '20100824', 'admin', NULL, NULL)
;
INSERT INTO zdbranch(BranchInnerCode, BranchCode, ParentInnerCode, Type, OrderFlag, Name, TreeLevel, IsLeaf, Phone, Fax, Manager, Leader1, 

Leader2, Prop1, Prop2, Prop3, Prop4, Memo, AddTime, AddUser, ModifyTime, ModifyUser)
  VALUES('120000095120000165120000195', '2120999104119000N', '120000095120000165', '0', 195, '河西支行越秀路分理处', 3, 'Y', NULL, NULL, 'admin', 'administrator', 'admin', 

'10', '120000195', NULL, NULL, 

NULL, '20100824', 'admin', NULL, NULL)
;
INSERT INTO zdbranch(BranchInnerCode, BranchCode, ParentInnerCode, Type, OrderFlag, Name, TreeLevel, IsLeaf, Phone, Fax, Manager, Leader1, 

Leader2, Prop1, Prop2, Prop3, Prop4, Memo, AddTime, AddUser, ModifyTime, ModifyUser)
  VALUES('120000095120000165120000196', '21209991041080003', '120000095120000165', '0', 196, '人民公园支行', 3, 'Y', NULL, NULL, 'admin', 'administrator', 'admin', '06', 

'120000196', NULL, NULL, NULL, 

'20100824', 'admin', NULL, NULL)
;
INSERT INTO zdbranch(BranchInnerCode, BranchCode, ParentInnerCode, Type, OrderFlag, Name, TreeLevel, IsLeaf, Phone, Fax, Manager, Leader1, 

Leader2, Prop1, Prop2, Prop3, Prop4, Memo, AddTime, AddUser, ModifyTime, ModifyUser)
  VALUES('120000095120000165120000197', '2120999104109000T', '120000095120000165', '0', 197, '广东路支行', 3, 'Y', NULL, NULL, 'admin', 'administrator', 'admin', '06', '120000197', 

NULL, NULL, NULL, 

'20100824', 'admin', NULL, NULL)
;
INSERT INTO zdbranch(BranchInnerCode, BranchCode, ParentInnerCode, Type, OrderFlag, Name, TreeLevel, IsLeaf, Phone, Fax, Manager, Leader1, 

Leader2, Prop1, Prop2, Prop3, Prop4, Memo, AddTime, AddUser, ModifyTime, ModifyUser)
  VALUES('120000095120000165120000198', '2120999104121000Q', '120000095120000165', '0', 198, '河西支行南昌路分理处', 3, 'Y', NULL, NULL, 'admin', 'administrator', 'admin', 

'10', '120000198', NULL, NULL, 

NULL, '20100824', 'admin', NULL, NULL)
;
INSERT INTO zdbranch(BranchInnerCode, BranchCode, ParentInnerCode, Type, OrderFlag, Name, TreeLevel, IsLeaf, Phone, Fax, Manager, Leader1, 

Leader2, Prop1, Prop2, Prop3, Prop4, Memo, AddTime, AddUser, ModifyTime, ModifyUser)
  VALUES('120000095120000199', '2120999105000000B', '120000095', '0', 199, '河东支行', 2, 'Y', NULL, NULL, 'admin', 'administrator', 'admin', '04', '120000199', 

NULL, NULL, NULL, '20100824', 'admin', NULL, NULL)
;
INSERT INTO zdbranch(BranchInnerCode, BranchCode, ParentInnerCode, Type, OrderFlag, Name, TreeLevel, IsLeaf, Phone, Fax, Manager, Leader1, 

Leader2, Prop1, Prop2, Prop3, Prop4, Memo, AddTime, AddUser, ModifyTime, ModifyUser)
  VALUES('120000095120000199120000200', '21209991050010006', '120000095120000199', '0', 200, '河东支行机关', 3, 'Y', NULL, NULL, 'admin', 'administrator', 'admin', '04', '120000200', 

NULL, NULL, NULL, 

'20100824', 'admin', NULL, NULL)
;
INSERT INTO zdbranch(BranchInnerCode, BranchCode, ParentInnerCode, Type, OrderFlag, Name, TreeLevel, IsLeaf, Phone, Fax, Manager, Leader1, 

Leader2, Prop1, Prop2, Prop3, Prop4, Memo, AddTime, AddUser, ModifyTime, ModifyUser)
  VALUES('120000095120000199120000200120000201', '2120999105001003V', '120000095120000199120000200', '0', 201, '河东支行其他', 4, 'Y', NULL, NULL, 'admin', 'administrator', 'admin', '04', '120000201', 

NULL, NULL, NULL, 

'20100824', 'admin', NULL, NULL)
;
INSERT INTO zdbranch(BranchInnerCode, BranchCode, ParentInnerCode, Type, OrderFlag, Name, TreeLevel, IsLeaf, Phone, Fax, Manager, Leader1, 

Leader2, Prop1, Prop2, Prop3, Prop4, Memo, AddTime, AddUser, ModifyTime, ModifyUser)
  VALUES('120000095120000199120000200120000202', '21209991050010014', '120000095120000199120000200', '0', 202, '河东支行综合管理部', 4, 'Y', NULL, NULL, 'admin', 'administrator', 'admin', '04', 

'120000202', NULL, NULL, NULL, 

'20100824', 'admin', NULL, NULL)
;
INSERT INTO zdbranch(BranchInnerCode, BranchCode, ParentInnerCode, Type, OrderFlag, Name, TreeLevel, IsLeaf, Phone, Fax, Manager, Leader1, 

Leader2, Prop1, Prop2, Prop3, Prop4, Memo, AddTime, AddUser, ModifyTime, ModifyUser)
  VALUES('120000095120000199120000200120000203', '21209991050010022', '120000095120000199120000200', '0', 203, '河东支行行长室', 4, 'Y', NULL, NULL, 'admin', 'administrator', 'admin', '04', 

'120000203', NULL, NULL, NULL, 

'20100824', 'admin', NULL, NULL)
;
INSERT INTO zdbranch(BranchInnerCode, BranchCode, ParentInnerCode, Type, OrderFlag, Name, TreeLevel, IsLeaf, Phone, Fax, Manager, Leader1, 

Leader2, Prop1, Prop2, Prop3, Prop4, Memo, AddTime, AddUser, ModifyTime, ModifyUser)
  VALUES('120000095120000199120000200120000204', '2120999105001004T', '120000095120000199120000200', '0', 204, '河东支行内退人员', 4, 'Y', NULL, NULL, 'admin', 'administrator', 'admin', '04', 

'120000204', NULL, NULL, NULL, 

'20100824', 'admin', NULL, NULL)
;
INSERT INTO zdbranch(BranchInnerCode, BranchCode, ParentInnerCode, Type, OrderFlag, Name, TreeLevel, IsLeaf, Phone, Fax, Manager, Leader1, 

Leader2, Prop1, Prop2, Prop3, Prop4, Memo, AddTime, AddUser, ModifyTime, ModifyUser)
  VALUES('120000095120000199120000200120000205', '2120999105001005R', '120000095120000199120000200', '0', 205, '河东支行保险代理部', 4, 'Y', NULL, NULL, 'admin', 'administrator', 'admin', '04', 

'120000205', NULL, NULL, NULL, 

'20100824', 'admin', NULL, NULL)
;
INSERT INTO zdbranch(BranchInnerCode, BranchCode, ParentInnerCode, Type, OrderFlag, Name, TreeLevel, IsLeaf, Phone, Fax, Manager, Leader1, 

Leader2, Prop1, Prop2, Prop3, Prop4, Memo, AddTime, AddUser, ModifyTime, ModifyUser)
  VALUES('120000095120000199120000200120000206', '2120999105001006P', '120000095120000199120000200', '0', 206, '河东支行财会运营部', 4, 'Y', NULL, NULL, 'admin', 'administrator', 'admin', '04', 

'120000206', NULL, NULL, NULL, 

'20100824', 'admin', NULL, NULL)
;
INSERT INTO zdbranch(BranchInnerCode, BranchCode, ParentInnerCode, Type, OrderFlag, Name, TreeLevel, IsLeaf, Phone, Fax, Manager, Leader1, 

Leader2, Prop1, Prop2, Prop3, Prop4, Memo, AddTime, AddUser, ModifyTime, ModifyUser)
  VALUES('120000095120000199120000200120000207', '2120999105001007N', '120000095120000199120000200', '0', 207, '河东支行监察保卫/内控合规部', 4, 'Y', NULL, NULL, 'admin', 'administrator', 

'admin', '04', '120000207', NULL, NULL, 

NULL, '20100824', 'admin', NULL, NULL)
;
INSERT INTO zdbranch(BranchInnerCode, BranchCode, ParentInnerCode, Type, OrderFlag, Name, TreeLevel, IsLeaf, Phone, Fax, Manager, Leader1, 

Leader2, Prop1, Prop2, Prop3, Prop4, Memo, AddTime, AddUser, ModifyTime, ModifyUser)
  VALUES('120000095120000199120000200120000208', '2120999105001008L', '120000095120000199120000200', '0', 208, '河东支行监督保障部', 4, 'Y', NULL, NULL, 'admin', 'administrator', 'admin', '04', 

'120000208', NULL, NULL, NULL, 

'20100824', 'admin', NULL, NULL)
;
INSERT INTO zdbranch(BranchInnerCode, BranchCode, ParentInnerCode, Type, OrderFlag, Name, TreeLevel, IsLeaf, Phone, Fax, Manager, Leader1, 

Leader2, Prop1, Prop2, Prop3, Prop4, Memo, AddTime, AddUser, ModifyTime, ModifyUser)
  VALUES('120000095120000199120000200120000209', '2120999105001009J', '120000095120000199120000200', '0', 209, '河东支行借调辞职处', 4, 'Y', NULL, NULL, 'admin', 'administrator', 'admin', '04', 

'120000209', NULL, NULL, NULL, 

'20100824', 'admin', NULL, NULL)
;
INSERT INTO zdbranch(BranchInnerCode, BranchCode, ParentInnerCode, Type, OrderFlag, Name, TreeLevel, IsLeaf, Phone, Fax, Manager, Leader1, 

Leader2, Prop1, Prop2, Prop3, Prop4, Memo, AddTime, AddUser, ModifyTime, ModifyUser)
  VALUES('120000095120000199120000200120000210', '21209991050010103', '120000095120000199120000200', '0', 210, '河东支行离退休人员', 4, 'Y', NULL, NULL, 'admin', 'administrator', 'admin', '04', 

'120000210', NULL, NULL, NULL, 

'20100824', 'admin', NULL, NULL)
;
INSERT INTO zdbranch(BranchInnerCode, BranchCode, ParentInnerCode, Type, OrderFlag, Name, TreeLevel, IsLeaf, Phone, Fax, Manager, Leader1, 

Leader2, Prop1, Prop2, Prop3, Prop4, Memo, AddTime, AddUser, ModifyTime, ModifyUser)
  VALUES('120000095120000199120000200120000211', '21209991050010111', '120000095120000199120000200', '0', 211, '河东支行信贷管理部', 4, 'Y', NULL, NULL, 'admin', 'administrator', 'admin', '04', 

'120000211', NULL, NULL, NULL, 

'20100824', 'admin', NULL, NULL)
;
INSERT INTO zdbranch(BranchInnerCode, BranchCode, ParentInnerCode, Type, OrderFlag, Name, TreeLevel, IsLeaf, Phone, Fax, Manager, Leader1, 

Leader2, Prop1, Prop2, Prop3, Prop4, Memo, AddTime, AddUser, ModifyTime, ModifyUser)
  VALUES('120000095120000199120000200120000212', '2120999105001012U', '120000095120000199120000200', '0', 212, '河东支行公司业务部', 4, 'Y', NULL, NULL, 'admin', 'administrator', 'admin', '04', 

'120000212', NULL, NULL, NULL, 

'20100824', 'admin', NULL, NULL)
;
INSERT INTO zdbranch(BranchInnerCode, BranchCode, ParentInnerCode, Type, OrderFlag, Name, TreeLevel, IsLeaf, Phone, Fax, Manager, Leader1, 

Leader2, Prop1, Prop2, Prop3, Prop4, Memo, AddTime, AddUser, ModifyTime, ModifyUser)
  VALUES('120000095120000199120000200120000213', '2120999105001013S', '120000095120000199120000200', '0', 213, '河东支行个人金融部', 4, 'Y', NULL, NULL, 'admin', 'administrator', 'admin', '04', 

'120000213', NULL, NULL, NULL, 

'20100824', 'admin', NULL, NULL)
;
INSERT INTO zdbranch(BranchInnerCode, BranchCode, ParentInnerCode, Type, OrderFlag, Name, TreeLevel, IsLeaf, Phone, Fax, Manager, Leader1, 

Leader2, Prop1, Prop2, Prop3, Prop4, Memo, AddTime, AddUser, ModifyTime, ModifyUser)
  VALUES('120000095120000199120000214', '2120999105102000P', '120000095120000199', '0', 214, '六纬路支行', 3, 'Y', NULL, NULL, 'admin', 'administrator', 'admin', '06', '120000214', 

NULL, NULL, NULL, 

'20100824', 'admin', NULL, NULL)
;
INSERT INTO zdbranch(BranchInnerCode, BranchCode, ParentInnerCode, Type, OrderFlag, Name, TreeLevel, IsLeaf, Phone, Fax, Manager, Leader1, 

Leader2, Prop1, Prop2, Prop3, Prop4, Memo, AddTime, AddUser, ModifyTime, ModifyUser)
  VALUES('120000095120000199120000215', '2120999105120000N', '120000095120000199', '0', 215, '河东支行二号桥分理处', 3, 'Y', NULL, NULL, 'admin', 'administrator', 'admin', 

'06', '120000215', NULL, NULL, NULL, 

'20100824', 'admin', NULL, NULL)
;
INSERT INTO zdbranch(BranchInnerCode, BranchCode, ParentInnerCode, Type, OrderFlag, Name, TreeLevel, IsLeaf, Phone, Fax, Manager, Leader1, 

Leader2, Prop1, Prop2, Prop3, Prop4, Memo, AddTime, AddUser, ModifyTime, ModifyUser)
  VALUES('120000095120000199120000216', '21209991051060005', '120000095120000199', '0', 216, '华兴街支行', 3, 'Y', NULL, NULL, 'admin', 'administrator', 'admin', '06', '120000216', 

NULL, NULL, NULL, 

'20100824', 'admin', NULL, NULL)
;
INSERT INTO zdbranch(BranchInnerCode, BranchCode, ParentInnerCode, Type, OrderFlag, Name, TreeLevel, IsLeaf, Phone, Fax, Manager, Leader1, 

Leader2, Prop1, Prop2, Prop3, Prop4, Memo, AddTime, AddUser, ModifyTime, ModifyUser)
  VALUES('120000095120000199120000217', '2120999105127000J', '120000095120000199', '0', 217, '河东区滨港分理处', 3, 'Y', NULL, NULL, 'admin', 'administrator', 'admin', '06', 

'120000217', NULL, NULL, NULL, 

'20100824', 'admin', NULL, NULL)
;
INSERT INTO zdbranch(BranchInnerCode, BranchCode, ParentInnerCode, Type, OrderFlag, Name, TreeLevel, IsLeaf, Phone, Fax, Manager, Leader1, 

Leader2, Prop1, Prop2, Prop3, Prop4, Memo, AddTime, AddUser, ModifyTime, ModifyUser)
  VALUES('120000095120000199120000218', '21209991051070000', '120000095120000199', '0', 218, '河东支行裕阳分理处', 3, 'Y', NULL, NULL, 'admin', 'administrator', 'admin', 

'06', '120000218', NULL, NULL, NULL, 

'20100824', 'admin', NULL, NULL)
;
INSERT INTO zdbranch(BranchInnerCode, BranchCode, ParentInnerCode, Type, OrderFlag, Name, TreeLevel, IsLeaf, Phone, Fax, Manager, Leader1, 

Leader2, Prop1, Prop2, Prop3, Prop4, Memo, AddTime, AddUser, ModifyTime, ModifyUser)
  VALUES('120000095120000199120000219', '2120999105103000K', '120000095120000199', '0', 219, '富民路支行', 3, 'Y', NULL, NULL, 'admin', 'administrator', 'admin', '06', '120000219', 

NULL, NULL, NULL, 

'20100824', 'admin', NULL, NULL)
;
INSERT INTO zdbranch(BranchInnerCode, BranchCode, ParentInnerCode, Type, OrderFlag, Name, TreeLevel, IsLeaf, Phone, Fax, Manager, Leader1, 

Leader2, Prop1, Prop2, Prop3, Prop4, Memo, AddTime, AddUser, ModifyTime, ModifyUser)
  VALUES('120000095120000199120000220', '21209991051230008', '120000095120000199', '0', 220, '河东支行广宁路分理处', 3, 'Y', NULL, NULL, 'admin', 'administrator', 'admin', 

'06', '120000220', NULL, NULL, 

NULL, '20100824', 'admin', NULL, NULL)
;
INSERT INTO zdbranch(BranchInnerCode, BranchCode, ParentInnerCode, Type, OrderFlag, Name, TreeLevel, IsLeaf, Phone, Fax, Manager, Leader1, 

Leader2, Prop1, Prop2, Prop3, Prop4, Memo, AddTime, AddUser, ModifyTime, ModifyUser)
  VALUES('120000095120000199120000221', '2120999105122000D', '120000095120000199', '0', 221, '河东区滨河分理处', 3, 'Y', NULL, NULL, 'admin', 'administrator', 'admin', '06', 

'120000221', NULL, NULL, NULL, 

'20100824', 'admin', NULL, NULL)
;
INSERT INTO zdbranch(BranchInnerCode, BranchCode, ParentInnerCode, Type, OrderFlag, Name, TreeLevel, IsLeaf, Phone, Fax, Manager, Leader1, 

Leader2, Prop1, Prop2, Prop3, Prop4, Memo, AddTime, AddUser, ModifyTime, ModifyUser)
  VALUES('120000095120000199120000222', '2120999105105000A', '120000095120000199', '0', 222, '红星路支行', 3, 'Y', NULL, NULL, 'admin', 'administrator', 'admin', '06', '120000222', 

NULL, NULL, NULL, 

'20100824', 'admin', NULL, NULL)
;
INSERT INTO zdbranch(BranchInnerCode, BranchCode, ParentInnerCode, Type, OrderFlag, Name, TreeLevel, IsLeaf, Phone, Fax, Manager, Leader1, 

Leader2, Prop1, Prop2, Prop3, Prop4, Memo, AddTime, AddUser, ModifyTime, ModifyUser)
  VALUES('120000095120000199120000223', '2120999105126000O', '120000095120000199', '0', 223, '河东区成林储蓄所', 3, 'Y', NULL, NULL, 'admin', 'administrator', 'admin', '06', 

'120000223', NULL, NULL, NULL, 

'20100824', 'admin', NULL, NULL)
;
INSERT INTO zdbranch(BranchInnerCode, BranchCode, ParentInnerCode, Type, OrderFlag, Name, TreeLevel, IsLeaf, Phone, Fax, Manager, Leader1, 

Leader2, Prop1, Prop2, Prop3, Prop4, Memo, AddTime, AddUser, ModifyTime, ModifyUser)
  VALUES('120000095120000199120000224', '2120999105121000I', '120000095120000199', '0', 224, '河东区华昌大街储蓄所', 3, 'Y', NULL, NULL, 'admin', 'administrator', 'admin', 

'06', '120000224', NULL, NULL, 

NULL, '20100824', 'admin', NULL, NULL)
;
INSERT INTO zdbranch(BranchInnerCode, BranchCode, ParentInnerCode, Type, OrderFlag, Name, TreeLevel, IsLeaf, Phone, Fax, Manager, Leader1, 

Leader2, Prop1, Prop2, Prop3, Prop4, Memo, AddTime, AddUser, ModifyTime, ModifyUser)
  VALUES('120000095120000199120000225', '2120999105112000J', '120000095120000199', '0', 225, '河东区长城公寓分理处', 3, 'Y', NULL, NULL, 'admin', 'administrator', 'admin', 

'06', '120000225', NULL, NULL, 

NULL, '20100824', 'admin', NULL, NULL)
;
INSERT INTO zdbranch(BranchInnerCode, BranchCode, ParentInnerCode, Type, OrderFlag, Name, TreeLevel, IsLeaf, Phone, Fax, Manager, Leader1, 

Leader2, Prop1, Prop2, Prop3, Prop4, Memo, AddTime, AddUser, ModifyTime, ModifyUser)
  VALUES('120000095120000199120000226', '2120999105104000F', '120000095120000199', '0', 226, '万新庄支行', 3, 'Y', NULL, NULL, 'admin', 'administrator', 'admin', '06', '120000226', 

NULL, NULL, NULL, 

'20100824', 'admin', NULL, NULL)
;
INSERT INTO zdbranch(BranchInnerCode, BranchCode, ParentInnerCode, Type, OrderFlag, Name, TreeLevel, IsLeaf, Phone, Fax, Manager, Leader1, 

Leader2, Prop1, Prop2, Prop3, Prop4, Memo, AddTime, AddUser, ModifyTime, ModifyUser)
  VALUES('120000095120000199120000227', '21209991051240003', '120000095120000199', '0', 227, '河东支行运输工程学院储蓄所', 3, 'Y', NULL, NULL, 'admin', 'administrator', 

'admin', '06', '120000227', NULL, NULL, 

NULL, '20100824', 'admin', NULL, NULL)
;
INSERT INTO zdbranch(BranchInnerCode, BranchCode, ParentInnerCode, Type, OrderFlag, Name, TreeLevel, IsLeaf, Phone, Fax, Manager, Leader1, 

Leader2, Prop1, Prop2, Prop3, Prop4, Memo, AddTime, AddUser, ModifyTime, ModifyUser)
  VALUES('120000095120000199120000228', '2120999105113000E', '120000095120000199', '0', 228, '中山门支行', 3, 'Y', NULL, NULL, 'admin', 'administrator', 'admin', '06', '120000228', 

NULL, NULL, NULL, 

'20100824', 'admin', NULL, NULL)
;
INSERT INTO zdbranch(BranchInnerCode, BranchCode, ParentInnerCode, Type, OrderFlag, Name, TreeLevel, IsLeaf, Phone, Fax, Manager, Leader1, 

Leader2, Prop1, Prop2, Prop3, Prop4, Memo, AddTime, AddUser, ModifyTime, ModifyUser)
  VALUES('120000095120000199120000229', '2120999105111000O', '120000095120000199', '0', 229, '河东区第六大道分理处', 3, 'Y', NULL, NULL, 'admin', 'administrator', 'admin', 

'06', '120000229', NULL, NULL, 

NULL, '20100824', 'admin', NULL, NULL)
;
INSERT INTO zdbranch(BranchInnerCode, BranchCode, ParentInnerCode, Type, OrderFlag, Name, TreeLevel, IsLeaf, Phone, Fax, Manager, Leader1, 

Leader2, Prop1, Prop2, Prop3, Prop4, Memo, AddTime, AddUser, ModifyTime, ModifyUser)
  VALUES('120000095120000199120000230', '2120999105125000T', '120000095120000199', '0', 230, '河东区天山路分理处', 3, 'Y', NULL, NULL, 'admin', 'administrator', 'admin', 

'06', '120000230', NULL, NULL, NULL, 

'20100824', 'admin', NULL, NULL)
;
INSERT INTO zdbranch(BranchInnerCode, BranchCode, ParentInnerCode, Type, OrderFlag, Name, TreeLevel, IsLeaf, Phone, Fax, Manager, Leader1, 

Leader2, Prop1, Prop2, Prop3, Prop4, Memo, AddTime, AddUser, ModifyTime, ModifyUser)
  VALUES('120000095120000199120000231', '2120999105108000Q', '120000095120000199', '0', 231, '八经路支行', 3, 'Y', NULL, NULL, 'admin', 'administrator', 'admin', '06', '120000231', 

NULL, NULL, NULL, 

'20100824', 'admin', NULL, NULL)
;
INSERT INTO zdbranch(BranchInnerCode, BranchCode, ParentInnerCode, Type, OrderFlag, Name, TreeLevel, IsLeaf, Phone, Fax, Manager, Leader1, 

Leader2, Prop1, Prop2, Prop3, Prop4, Memo, AddTime, AddUser, ModifyTime, ModifyUser)
  VALUES('120000095120000199120000232', '2120999105128000E', '120000095120000199', '0', 232, '河东支行九经路分理处', 3, 'Y', NULL, NULL, 'admin', 'administrator', 'admin', 

'06', '120000232', NULL, NULL, NULL, 

'20100824', 'admin', NULL, NULL)
;
INSERT INTO zdbranch(BranchInnerCode, BranchCode, ParentInnerCode, Type, OrderFlag, Name, TreeLevel, IsLeaf, Phone, Fax, Manager, Leader1, 
Leader2, Prop1, Prop2, Prop3, Prop4, Memo, AddTime, AddUser, ModifyTime, ModifyUser)
  VALUES('120000095120000233', '21209991060000003', '120000095', '0', 233, '河北支行', 2, 'Y', NULL, NULL, 'admin', 'administrator', 'admin', '04', '120000233', 

NULL, NULL, NULL, '20100824', 'admin', NULL, NULL);
INSERT INTO zdbranch(BranchInnerCode, BranchCode, ParentInnerCode, Type, OrderFlag, Name, TreeLevel, IsLeaf, Phone, Fax, Manager, Leader1, 

Leader2, Prop1, Prop2, Prop3, Prop4, Memo, AddTime, AddUser, ModifyTime, ModifyUser)
  VALUES('120000095120000233120000234', '2120999106101000M', '120000095120000233', '0', 234, '黄纬路支行', 3, 'Y', NULL, NULL, 'admin', 'administrator', 'admin', '06', '120000234', 

NULL, NULL, NULL, '20100824', 'admin', NULL, NULL);
INSERT INTO zdbranch(BranchInnerCode, BranchCode, ParentInnerCode, Type, OrderFlag, Name, TreeLevel, IsLeaf, Phone, Fax, Manager, Leader1, 

Leader2, Prop1, Prop2, Prop3, Prop4, Memo, AddTime, AddUser, ModifyTime, ModifyUser)
  VALUES('120000095120000233120000235', '2120999106001000T', '120000095120000233', '0', 235, '河北支行机关', 3, 'Y', NULL, NULL, 'admin', 'administrator', 'admin', '04', '120000235', 

NULL, NULL, NULL, 

'20100824', 'admin', NULL, NULL)
;
INSERT INTO zdbranch(BranchInnerCode, BranchCode, ParentInnerCode, Type, OrderFlag, Name, TreeLevel, IsLeaf, Phone, Fax, Manager, Leader1, 

Leader2, Prop1, Prop2, Prop3, Prop4, Memo, AddTime, AddUser, ModifyTime, ModifyUser)
  VALUES('120000095120000233120000235120000236', '2120999106001001R', '120000095120000233120000235', '0', 236, '河北支行退休', 4, 'Y', NULL, NULL, 'admin', 'administrator', 'admin', '04', '120000236', 

NULL, NULL, NULL, 

'20100824', 'admin', NULL, NULL)
;
INSERT INTO zdbranch(BranchInnerCode, BranchCode, ParentInnerCode, Type, OrderFlag, Name, TreeLevel, IsLeaf, Phone, Fax, Manager, Leader1, 

Leader2, Prop1, Prop2, Prop3, Prop4, Memo, AddTime, AddUser, ModifyTime, ModifyUser)
  VALUES('120000095120000233120000235120000237', '2120999106001002P', '120000095120000233120000235', '0', 237, '河北支行综合管理部', 4, 'Y', NULL, NULL, 'admin', 'administrator', 'admin', '04', 

'120000237', NULL, NULL, NULL, 

'20100824', 'admin', NULL, NULL)
;
INSERT INTO zdbranch(BranchInnerCode, BranchCode, ParentInnerCode, Type, OrderFlag, Name, TreeLevel, IsLeaf, Phone, Fax, Manager, Leader1, 

Leader2, Prop1, Prop2, Prop3, Prop4, Memo, AddTime, AddUser, ModifyTime, ModifyUser)
  VALUES('120000095120000233120000235120000238', '2120999106001003N', '120000095120000233120000235', '0', 238, '河北支行国际部', 4, 'Y', NULL, NULL, 'admin', 'administrator', 'admin', '04', 

'120000238', NULL, NULL, NULL, 

'20100824', 'admin', NULL, NULL)
;
INSERT INTO zdbranch(BranchInnerCode, BranchCode, ParentInnerCode, Type, OrderFlag, Name, TreeLevel, IsLeaf, Phone, Fax, Manager, Leader1, 

Leader2, Prop1, Prop2, Prop3, Prop4, Memo, AddTime, AddUser, ModifyTime, ModifyUser)
  VALUES('120000095120000233120000235120000239', '2120999106001004L', '120000095120000233120000235', '0', 239, '河北支行拓展部', 4, 'Y', NULL, NULL, 'admin', 'administrator', 'admin', '04', 

'120000239', NULL, NULL, NULL, 

'20100824', 'admin', NULL, NULL)
;
INSERT INTO zdbranch(BranchInnerCode, BranchCode, ParentInnerCode, Type, OrderFlag, Name, TreeLevel, IsLeaf, Phone, Fax, Manager, Leader1, 

Leader2, Prop1, Prop2, Prop3, Prop4, Memo, AddTime, AddUser, ModifyTime, ModifyUser)
  VALUES('120000095120000233120000235120000240', '2120999106001005J', '120000095120000233120000235', '0', 240, '河北支行行长室', 4, 'Y', NULL, NULL, 'admin', 'administrator', 'admin', '04', 

'120000240', NULL, NULL, NULL, 

'20100824', 'admin', NULL, NULL)
;
INSERT INTO zdbranch(BranchInnerCode, BranchCode, ParentInnerCode, Type, OrderFlag, Name, TreeLevel, IsLeaf, Phone, Fax, Manager, Leader1, 

Leader2, Prop1, Prop2, Prop3, Prop4, Memo, AddTime, AddUser, ModifyTime, ModifyUser)
  VALUES('120000095120000233120000235120000241', '2120999106001006H', '120000095120000233120000235', '0', 241, '河北支行机关临时', 4, 'Y', NULL, NULL, 'admin', 'administrator', 'admin', '04', 

'120000241', NULL, NULL, NULL, 

'20100824', 'admin', NULL, NULL)
;
INSERT INTO zdbranch(BranchInnerCode, BranchCode, ParentInnerCode, Type, OrderFlag, Name, TreeLevel, IsLeaf, Phone, Fax, Manager, Leader1, 

Leader2, Prop1, Prop2, Prop3, Prop4, Memo, AddTime, AddUser, ModifyTime, ModifyUser)
  VALUES('120000095120000233120000235120000242', '2120999106001007F', '120000095120000233120000235', '0', 242, '河北支行个人金融部', 4, 'Y', NULL, NULL, 'admin', 'administrator', 'admin', '04', 

'120000242', NULL, NULL, NULL, 

'20100824', 'admin', NULL, NULL)
;
INSERT INTO zdbranch(BranchInnerCode, BranchCode, ParentInnerCode, Type, OrderFlag, Name, TreeLevel, IsLeaf, Phone, Fax, Manager, Leader1, 

Leader2, Prop1, Prop2, Prop3, Prop4, Memo, AddTime, AddUser, ModifyTime, ModifyUser)
  VALUES('120000095120000233120000235120000243', '2120999106001008D', '120000095120000233120000235', '0', 243, '河北支行财务会计部', 4, 'Y', NULL, NULL, 'admin', 'administrator', 'admin', '04', 

'120000243', NULL, NULL, NULL, 

'20100824', 'admin', NULL, NULL)
;
INSERT INTO zdbranch(BranchInnerCode, BranchCode, ParentInnerCode, Type, OrderFlag, Name, TreeLevel, IsLeaf, Phone, Fax, Manager, Leader1, 

Leader2, Prop1, Prop2, Prop3, Prop4, Memo, AddTime, AddUser, ModifyTime, ModifyUser)
  VALUES('120000095120000233120000235120000244', '2120999106001009B', '120000095120000233120000235', '0', 244, '河北支行财会运营部', 4, 'Y', NULL, NULL, 'admin', 'administrator', 'admin', '04', 

'120000244', NULL, NULL, NULL, 

'20100824', 'admin', NULL, NULL)
;
INSERT INTO zdbranch(BranchInnerCode, BranchCode, ParentInnerCode, Type, OrderFlag, Name, TreeLevel, IsLeaf, Phone, Fax, Manager, Leader1, 

Leader2, Prop1, Prop2, Prop3, Prop4, Memo, AddTime, AddUser, ModifyTime, ModifyUser)
  VALUES('120000095120000233120000235120000245', '2120999106001010Q', '120000095120000233120000235', '0', 245, '河北支行监察保卫/内控合规部', 4, 'Y', NULL, NULL, 'admin', 'administrator', 

'admin', '04', '120000245', NULL, NULL, 

NULL, '20100824', 'admin', NULL, NULL)
;
INSERT INTO zdbranch(BranchInnerCode, BranchCode, ParentInnerCode, Type, OrderFlag, Name, TreeLevel, IsLeaf, Phone, Fax, Manager, Leader1, 

Leader2, Prop1, Prop2, Prop3, Prop4, Memo, AddTime, AddUser, ModifyTime, ModifyUser)
  VALUES('120000095120000233120000235120000246', '2120999106001011O', '120000095120000233120000235', '0', 246, '河北支行离退休人员', 4, 'Y', NULL, NULL, 'admin', 'administrator', 'admin', '04', 

'120000246', NULL, NULL, NULL, 

'20100824', 'admin', NULL, NULL)
;
INSERT INTO zdbranch(BranchInnerCode, BranchCode, ParentInnerCode, Type, OrderFlag, Name, TreeLevel, IsLeaf, Phone, Fax, Manager, Leader1, 

Leader2, Prop1, Prop2, Prop3, Prop4, Memo, AddTime, AddUser, ModifyTime, ModifyUser)
  VALUES('120000095120000233120000235120000247', '2120999106001012M', '120000095120000233120000235', '0', 247, '河北支行市场拓展部', 4, 'Y', NULL, NULL, 'admin', 'administrator', 'admin', '04', 

'120000247', NULL, NULL, NULL, 

'20100824', 'admin', NULL, NULL)
;
INSERT INTO zdbranch(BranchInnerCode, BranchCode, ParentInnerCode, Type, OrderFlag, Name, TreeLevel, IsLeaf, Phone, Fax, Manager, Leader1, 

Leader2, Prop1, Prop2, Prop3, Prop4, Memo, AddTime, AddUser, ModifyTime, ModifyUser)
  VALUES('120000095120000233120000235120000248', '2120999106001013K', '120000095120000233120000235', '0', 248, '河北支行信贷管理部', 4, 'Y', NULL, NULL, 'admin', 'administrator', 'admin', '04', 

'120000248', NULL, NULL, NULL, 

'20100824', 'admin', NULL, NULL)
;
INSERT INTO zdbranch(BranchInnerCode, BranchCode, ParentInnerCode, Type, OrderFlag, Name, TreeLevel, IsLeaf, Phone, Fax, Manager, Leader1, 

Leader2, Prop1, Prop2, Prop3, Prop4, Memo, AddTime, AddUser, ModifyTime, ModifyUser)
  VALUES('120000095120000233120000235120000249', '2120999106001014I', '120000095120000233120000235', '0', 249, '河北支行公司业务部', 4, 'Y', NULL, NULL, 'admin', 'administrator', 'admin', '04', 

'120000249', NULL, NULL, NULL, 

'20100824', 'admin', NULL, NULL)
;
INSERT INTO zdbranch(BranchInnerCode, BranchCode, ParentInnerCode, Type, OrderFlag, Name, TreeLevel, IsLeaf, Phone, Fax, Manager, Leader1, 

Leader2, Prop1, Prop2, Prop3, Prop4, Memo, AddTime, AddUser, ModifyTime, ModifyUser)
  VALUES('120000095120000233120000235120000250', '2120999106001015G', '120000095120000233120000235', '0', 250, '河北支行资产风险部', 4, 'Y', NULL, NULL, 'admin', 'administrator', 'admin', '04', 

'120000250', NULL, NULL, NULL, 

'20100824', 'admin', NULL, NULL)
;
INSERT INTO zdbranch(BranchInnerCode, BranchCode, ParentInnerCode, Type, OrderFlag, Name, TreeLevel, IsLeaf, Phone, Fax, Manager, Leader1, 

Leader2, Prop1, Prop2, Prop3, Prop4, Memo, AddTime, AddUser, ModifyTime, ModifyUser)
  VALUES('120000095120000233120000251', '2120999106103000C', '120000095120000233', '0', 251, '津桥支行', 3, 'Y', NULL, NULL, 'admin', 'administrator', 'admin', '06', '120000251', NULL, NULL, NULL, '20100824', 'admin', NULL, NULL);
INSERT INTO zdbranch(BranchInnerCode, BranchCode, ParentInnerCode, Type, OrderFlag, Name, TreeLevel, IsLeaf, Phone, Fax, Manager, Leader1, 
Leader2, Prop1, Prop2, Prop3, Prop4, Memo, AddTime, AddUser, ModifyTime, ModifyUser)
  VALUES('120000095120000233120000252', '2120999106121000A', '120000095120000233', '0', 252, '河北支行金泰储蓄所', 3, 'Y', NULL, NULL, 'admin', 'administrator', 'admin', '10', 

'120000252', NULL, NULL, NULL, '20100824', 'admin', NULL, NULL);
INSERT INTO zdbranch(BranchInnerCode, BranchCode, ParentInnerCode, Type, OrderFlag, Name, TreeLevel, IsLeaf, Phone, Fax, Manager, Leader1, 
Leader2, Prop1, Prop2, Prop3, Prop4, Memo, AddTime, AddUser, ModifyTime, ModifyUser)
  VALUES('120000095120000233120000253', '21209991061040007', '120000095120000233', '0', 253, '环宇支行', 3, 'Y', NULL, NULL, 'admin', 'administrator', 'admin', '06', '120000253', NULL, NULL, NULL, '20100824', 'admin', NULL, NULL);
INSERT INTO zdbranch(BranchInnerCode, BranchCode, ParentInnerCode, Type, OrderFlag, Name, TreeLevel, IsLeaf, Phone, Fax, Manager, Leader1, 
Leader2, Prop1, Prop2, Prop3, Prop4, Memo, AddTime, AddUser, ModifyTime, ModifyUser)
  VALUES('120000095120000233120000253120000254', '21209991061041011', '120000095120000233120000253', '0', 254, '分行河北支行环宇分理处环宇储蓄所', 4, 'Y', NULL, NULL, 'admin', 'administrator', 'admin', '10', '120000254', NULL, NULL, NULL, '20100824', 'admin', NULL, NULL);
INSERT INTO zdbranch(BranchInnerCode, BranchCode, ParentInnerCode, Type, OrderFlag, Name, TreeLevel, IsLeaf, Phone, Fax, Manager, Leader1, 
Leader2, Prop1, Prop2, Prop3, Prop4, Memo, AddTime, AddUser, ModifyTime, ModifyUser)
  VALUES('120000095120000233120000255', '21209991061220005', '120000095120000233', '0', 255, '河北支行金海储蓄所', 3, 'Y', NULL, NULL, 'admin', 'administrator', 'admin', '10', '120000255', NULL, NULL, NULL, '20100824', 'admin', NULL, NULL);
INSERT INTO zdbranch(BranchInnerCode, BranchCode, ParentInnerCode, Type, OrderFlag, Name, TreeLevel, IsLeaf, Phone, Fax, Manager, Leader1, 
Leader2, Prop1, Prop2, Prop3, Prop4, Memo, AddTime, AddUser, ModifyTime, ModifyUser)
  VALUES('120000095120000233120000256', '2120999106125000L', '120000095120000233', '0', 256, '站支行', 3, 'Y', NULL, NULL, 'admin', 'administrator', 'admin', '06', '120000256', NULL, NULL, NULL, '20100824', 'admin', NULL, NULL);
INSERT INTO zdbranch(BranchInnerCode, BranchCode, ParentInnerCode, Type, OrderFlag, Name, TreeLevel, IsLeaf, Phone, Fax, Manager, Leader1, 
Leader2, Prop1, Prop2, Prop3, Prop4, Memo, AddTime, AddUser, ModifyTime, ModifyUser)
  VALUES('120000095120000233120000257', '21209991061050002', '120000095120000233', '0', 257, '中山路支行', 3, 'Y', NULL, NULL, 'admin', 'administrator', 'admin', '06', '120000257', 
NULL, NULL, NULL, '20100824', 'admin', NULL, NULL);
INSERT INTO zdbranch(BranchInnerCode, BranchCode, ParentInnerCode, Type, OrderFlag, Name, TreeLevel, IsLeaf, Phone, Fax, Manager, Leader1, 
Leader2, Prop1, Prop2, Prop3, Prop4, Memo, AddTime, AddUser, ModifyTime, ModifyUser)
  VALUES('120000095120000233120000258', '21209991061230000', '120000095120000233', '0', 258, '河北支行中山路储蓄所', 3, 'Y', NULL, NULL, 'admin', 'administrator', 'admin', '10', '120000258', NULL, NULL, NULL, '20100824', 'admin', NULL, NULL);
INSERT INTO zdbranch(BranchInnerCode, BranchCode, ParentInnerCode, Type, OrderFlag, Name, TreeLevel, IsLeaf, Phone, Fax, Manager, Leader1, 
Leader2, Prop1, Prop2, Prop3, Prop4, Memo, AddTime, AddUser, ModifyTime, ModifyUser)
  VALUES('120000095120000233120000259', '2120999106124000Q', '120000095120000233', '0', 259, '河北支行昆纬路储蓄所', 3, 'Y', NULL, NULL, 'admin', 'administrator', 'admin', '10', '120000259', NULL, NULL, NULL, '20100824', 'admin', NULL, NULL);
INSERT INTO zdbranch(BranchInnerCode, BranchCode, ParentInnerCode, Type, OrderFlag, Name, TreeLevel, IsLeaf, Phone, Fax, Manager, Leader1, 
Leader2, Prop1, Prop2, Prop3, Prop4, Memo, AddTime, AddUser, ModifyTime, ModifyUser)
  VALUES('120000095120000233120000260', '2120999106106000S', '120000095120000233', '0', 260, '江都路支行', 3, 'Y', NULL, NULL, 'admin', 'administrator', 'admin', '06', '120000260', 
NULL, NULL, NULL, '20100824', 'admin', NULL, NULL);
INSERT INTO zdbranch(BranchInnerCode, BranchCode, ParentInnerCode, Type, OrderFlag, Name, TreeLevel, IsLeaf, Phone, Fax, Manager, Leader1, 
Leader2, Prop1, Prop2, Prop3, Prop4, Memo, AddTime, AddUser, ModifyTime, ModifyUser)
  VALUES('120000095120000233120000261', '2120999106126000G', '120000095120000233', '0', 261, '河北支行靖江路分理处', 3, 'Y', NULL, NULL, 'admin', 'administrator', 'admin', '06', '120000261', NULL, NULL, NULL, '20100824', 'admin', NULL, NULL);
INSERT INTO zdbranch(BranchInnerCode, BranchCode, ParentInnerCode, Type, OrderFlag, Name, TreeLevel, IsLeaf, Phone, Fax, Manager, Leader1, 

Leader2, Prop1, Prop2, Prop3, Prop4, Memo, AddTime, AddUser, ModifyTime, ModifyUser)
  VALUES('120000095120000233120000262', '2120999106107000N', '120000095120000233', '0', 262, '建国道支行', 3, 'Y', NULL, NULL, 'admin', 'administrator', 'admin', '06', '120000262', NULL, NULL, NULL, '20100824', 'admin', NULL, NULL);
INSERT INTO zdbranch(BranchInnerCode, BranchCode, ParentInnerCode, Type, OrderFlag, Name, TreeLevel, IsLeaf, Phone, Fax, Manager, Leader1, 
Leader2, Prop1, Prop2, Prop3, Prop4, Memo, AddTime, AddUser, ModifyTime, ModifyUser)
  VALUES('120000095120000233120000264', '2120999106108000I', '120000095120000233', '0', 263, '万科支行', 3, 'Y', NULL, NULL, 'admin', 'administrator', 'admin', '06', '120000264', NULL, NULL, NULL, '20100824', 'admin', NULL, NULL);
INSERT INTO zdbranch(BranchInnerCode, BranchCode, ParentInnerCode, Type, OrderFlag, Name, TreeLevel, IsLeaf, Phone, Fax, Manager, Leader1, 
Leader2, Prop1, Prop2, Prop3, Prop4, Memo, AddTime, AddUser, ModifyTime, ModifyUser)
  VALUES('120000095120000233120000265', '2120999106127000B', '120000095120000233', '0', 264, '河北支行王串场储蓄所', 3, 'Y', NULL, NULL, 'admin', 'administrator', 'admin', '11', '120000265', NULL, NULL, NULL, '20100824', 'admin', NULL, NULL);
INSERT INTO zdbranch(BranchInnerCode, BranchCode, ParentInnerCode, Type, OrderFlag, Name, TreeLevel, IsLeaf, Phone, Fax, Manager, Leader1, 
Leader2, Prop1, Prop2, Prop3, Prop4, Memo, AddTime, AddUser, ModifyTime, ModifyUser)
  VALUES('120000095120000266', '2120999107000000Q', '120000095', '0', 265, '红桥区支行', 2, 'Y', NULL, NULL, 'admin', 'administrator', 'admin', '04', '120000266', NULL, NULL, NULL, '20100824', 'admin', NULL, NULL);
INSERT INTO zdbranch(BranchInnerCode, BranchCode, ParentInnerCode, Type, OrderFlag, Name, TreeLevel, IsLeaf, Phone, Fax, Manager, Leader1, 
Leader2, Prop1, Prop2, Prop3, Prop4, Memo, AddTime, AddUser, ModifyTime, ModifyUser)
  VALUES('120000095120000266120000267', '2120999107101000E', '120000095120000266', '0', 266, '正东支行', 3, 'Y', NULL, NULL, 'admin', 'administrator', 'admin', '06', '120000267', NULL, NULL, NULL, '20100824', 'admin', NULL, NULL);
INSERT INTO zdbranch(BranchInnerCode, BranchCode, ParentInnerCode, Type, OrderFlag, Name, TreeLevel, IsLeaf, Phone, Fax, Manager, Leader1, 
Leader2, Prop1, Prop2, Prop3, Prop4, Memo, AddTime, AddUser, ModifyTime, ModifyUser)
  VALUES('120000095120000266120000268', '2120999107104000U', '120000095120000266', '0', 267, '北洋支行', 3, 'Y', NULL, NULL, 'admin', 'administrator', 'admin', '06', '120000268', NULL, NULL, NULL, '20100824', 'admin', NULL, NULL);
INSERT INTO zdbranch(BranchInnerCode, BranchCode, ParentInnerCode, Type, OrderFlag, Name, TreeLevel, IsLeaf, Phone, Fax, Manager, Leader1, 
Leader2, Prop1, Prop2, Prop3, Prop4, Memo, AddTime, AddUser, ModifyTime, ModifyUser)
  VALUES('120000095120000266120000269', '21209991071170009', '120000095120000266', '0', 268, '红桥支行咸阳北路分理处', 3, 'Y', NULL, NULL, 'admin', 'administrator', 'admin', '07', '120000269', NULL, NULL, NULL, '20100824', 'admin', NULL, NULL);
INSERT INTO zdbranch(BranchInnerCode, BranchCode, ParentInnerCode, Type, OrderFlag, Name, TreeLevel, IsLeaf, Phone, Fax, Manager, Leader1, 
Leader2, Prop1, Prop2, Prop3, Prop4, Memo, AddTime, AddUser, ModifyTime, ModifyUser)
  VALUES('120000095120000266120000268120000270', '2120999107104101O', '120000095120000266120000268', '0', 269, '红桥支行河工大储蓄所', 4, 'Y', NULL, NULL, 'admin', 'administrator', 'admin', '10', '120000270', NULL, NULL, NULL, '20100824', 'admin', NULL, NULL);
INSERT INTO zdbranch(BranchInnerCode, BranchCode, ParentInnerCode, Type, OrderFlag, Name, TreeLevel, IsLeaf, Phone, Fax, Manager, Leader1, 
Leader2, Prop1, Prop2, Prop3, Prop4, Memo, AddTime, AddUser, ModifyTime, ModifyUser)
  VALUES('120000095120000266120000271', '2120999107105000P', '120000095120000266', '0', 270, '光荣道支行', 3, 'Y', NULL, NULL, 'admin', 'administrator', 'admin', '06', '120000271', NULL, NULL, NULL, '20100824', 'admin', NULL, NULL);
INSERT INTO zdbranch(BranchInnerCode, BranchCode, ParentInnerCode, Type, OrderFlag, Name, TreeLevel, IsLeaf, Phone, Fax, Manager, Leader1, 
Leader2, Prop1, Prop2, Prop3, Prop4, Memo, AddTime, AddUser, ModifyTime, ModifyUser)
  VALUES('120000095120000266120000272', '21209991071020009', '120000095120000266', '0', 271, '红桥支行大胡同商城分理处', 3, 'Y', NULL, NULL, 'admin', 'administrator', 
'admin', '06', '120000272', NULL, NULL, 
NULL, '20100824', 'admin', NULL, NULL);
INSERT INTO zdbranch(BranchInnerCode, BranchCode, ParentInnerCode, Type, OrderFlag, Name, TreeLevel, IsLeaf, Phone, Fax, Manager, Leader1, 
Leader2, Prop1, Prop2, Prop3, Prop4, Memo, AddTime, AddUser, ModifyTime, ModifyUser)
  VALUES('120000095120000266120000273', '21209991071030004', '120000095120000266', '0', 272, '红桥区盛达园分理处', 3, 'Y', NULL, NULL, 'admin', 'administrator', 'admin', 
'06', '120000273', NULL, NULL, NULL, '20100824', 'admin', NULL, NULL);
INSERT INTO zdbranch(BranchInnerCode, BranchCode, ParentInnerCode, Type, OrderFlag, Name, TreeLevel, IsLeaf, Phone, Fax, Manager, Leader1, 
Leader2, Prop1, Prop2, Prop3, Prop4, Memo, AddTime, AddUser, ModifyTime, ModifyUser)
  VALUES('120000095120000266120000274', '2120999107119000U', '120000095120000266', '0', 273, '红桥区秀水苑分理处', 3, 'Y', NULL, NULL, 'admin', 'administrator', 'admin', 
'06', '120000274', NULL, NULL, NULL, '20100824', 'admin', NULL, NULL);
INSERT INTO zdbranch(BranchInnerCode, BranchCode, ParentInnerCode, Type, OrderFlag, Name, TreeLevel, IsLeaf, Phone, Fax, Manager, Leader1, 
Leader2, Prop1, Prop2, Prop3, Prop4, Memo, AddTime, AddUser, ModifyTime, ModifyUser)
  VALUES('120000095120000266120000275', '21209991071120003', '120000095120000266', '0', 274, '红桥支行青年路储蓄所', 3, 'Y', NULL, NULL, 'admin', 'administrator', 'admin', '10', '120000275', NULL, NULL, NULL, '20100824', 'admin', NULL, NULL);
INSERT INTO zdbranch(BranchInnerCode, BranchCode, ParentInnerCode, Type, OrderFlag, Name, TreeLevel, IsLeaf, Phone, Fax, Manager, Leader1, 
Leader2, Prop1, Prop2, Prop3, Prop4, Memo, AddTime, AddUser, ModifyTime, ModifyUser)
  VALUES('120000095120000266120000276', '2120999107001000L', '120000095120000266', '0', 275, '红桥支行机关', 3, 'Y', NULL, NULL, 'admin', 'administrator', 'admin', '04', '120000276', NULL, NULL, NULL, '20100824', 'admin', NULL, NULL);
INSERT INTO zdbranch(BranchInnerCode, BranchCode, ParentInnerCode, Type, OrderFlag, Name, TreeLevel, IsLeaf, Phone, Fax, Manager, Leader1, 
Leader2, Prop1, Prop2, Prop3, Prop4, Memo, AddTime, AddUser, ModifyTime, ModifyUser)
  VALUES('120000095120000266120000276120000277', '2120999107001003F', '120000095120000266120000276', '0', 276, '红桥支行基层', 4, 'Y', NULL, NULL, 'admin', 'administrator', 'admin', '04', '120000277', NULL, NULL, NULL, '20100824', 'admin', NULL, NULL);
INSERT INTO zdbranch(BranchInnerCode, BranchCode, ParentInnerCode, Type, OrderFlag, Name, TreeLevel, IsLeaf, Phone, Fax, Manager, Leader1, 
Leader2, Prop1, Prop2, Prop3, Prop4, Memo, AddTime, AddUser, ModifyTime, ModifyUser)
  VALUES('120000095120000266120000276120000278', '2120999107001002H', '120000095120000266120000276', '0', 277, '红桥支行内退', 4, 'Y', NULL, NULL, 'admin', 'administrator', 'admin', '04', '120000278', NULL, NULL, NULL, '20100824', 'admin', NULL, NULL);
INSERT INTO zdbranch(BranchInnerCode, BranchCode, ParentInnerCode, Type, OrderFlag, Name, TreeLevel, IsLeaf, Phone, Fax, Manager, Leader1, 
Leader2, Prop1, Prop2, Prop3, Prop4, Memo, AddTime, AddUser, ModifyTime, ModifyUser)
  VALUES('120000095120000266120000276120000279', '2120999107001004D', '120000095120000266120000276', '0', 278, '红桥支行综合管理部', 4, 'Y', NULL, NULL, 'admin', 'administrator', 'admin', '04', '120000279', NULL, NULL, NULL, '20100824', 'admin', NULL, NULL);
INSERT INTO zdbranch(BranchInnerCode, BranchCode, ParentInnerCode, Type, OrderFlag, Name, TreeLevel, IsLeaf, Phone, Fax, Manager, Leader1, 
Leader2, Prop1, Prop2, Prop3, Prop4, Memo, AddTime, AddUser, ModifyTime, ModifyUser)
  VALUES('120000095120000266120000276120000280', '21209991070010069', '120000095120000266120000276', '0', 279, '红桥支行拓展部', 4, 'Y', NULL, NULL, 'admin', 'administrator', 'admin', '04', '120000280', NULL, NULL, NULL, '20100824', 'admin', NULL, NULL);
INSERT INTO zdbranch(BranchInnerCode, BranchCode, ParentInnerCode, Type, OrderFlag, Name, TreeLevel, IsLeaf, Phone, Fax, Manager, Leader1, 
Leader2, Prop1, Prop2, Prop3, Prop4, Memo, AddTime, AddUser, ModifyTime, ModifyUser)
  VALUES('120000095120000266120000276120000281', '2120999107001005B', '120000095120000266120000276', '0', 280, '红桥支行行长室', 4, 'Y', NULL, NULL, 'admin', 'administrator', 'admin', '04', 
'120000281', NULL, NULL, NULL, '20100824', 'admin', NULL, NULL);
INSERT INTO zdbranch(BranchInnerCode, BranchCode, ParentInnerCode, Type, OrderFlag, Name, TreeLevel, IsLeaf, Phone, Fax, Manager, Leader1, 

Leader2, Prop1, Prop2, Prop3, Prop4, Memo, AddTime, AddUser, ModifyTime, ModifyUser)
  VALUES('120000095120000266120000276120000282', '21209991070010077', '120000095120000266120000276', '0', 281, '红桥支行客户一部', 4, 'Y', NULL, NULL, 'admin', 'administrator', 'admin', '04', 
'120000282', NULL, NULL, NULL, '20100824', 'admin', NULL, NULL);
INSERT INTO zdbranch(BranchInnerCode, BranchCode, ParentInnerCode, Type, OrderFlag, Name, TreeLevel, IsLeaf, Phone, Fax, Manager, Leader1, 

Leader2, Prop1, Prop2, Prop3, Prop4, Memo, AddTime, AddUser, ModifyTime, ModifyUser)
  VALUES('120000095120000266120000276120000283', '21209991070010085', '120000095120000266120000276', '0', 282, '红桥支行个人信贷部', 4, 'Y', NULL, NULL, 'admin', 'administrator', 'admin', '04', 

'120000283', NULL, NULL, NULL, '20100824', 'admin', NULL, NULL);
INSERT INTO zdbranch(BranchInnerCode, BranchCode, ParentInnerCode, Type, OrderFlag, Name, TreeLevel, IsLeaf, Phone, Fax, Manager, Leader1, 

Leader2, Prop1, Prop2, Prop3, Prop4, Memo, AddTime, AddUser, ModifyTime, ModifyUser)
  VALUES('120000095120000266120000276120000284', '21209991070010093', '120000095120000266120000276', '0', 283, '红桥支行客户二部', 4, 'Y', NULL, NULL, 'admin', 'administrator', 'admin', '04', 

'120000284', NULL, NULL, NULL, '20100824', 'admin', NULL, NULL);
INSERT INTO zdbranch(BranchInnerCode, BranchCode, ParentInnerCode, Type, OrderFlag, Name, TreeLevel, IsLeaf, Phone, Fax, Manager, Leader1, 

Leader2, Prop1, Prop2, Prop3, Prop4, Memo, AddTime, AddUser, ModifyTime, ModifyUser)
  VALUES('120000095120000266120000276120000285', '2120999107001010I', '120000095120000266120000276', '0', 284, '红桥支行财会运营部', 4, 'Y', NULL, NULL, 'admin', 'administrator', 'admin', '04', 

'120000285', NULL, NULL, NULL, '20100824', 'admin', NULL, NULL);
INSERT INTO zdbranch(BranchInnerCode, BranchCode, ParentInnerCode, Type, OrderFlag, Name, TreeLevel, IsLeaf, Phone, Fax, Manager, Leader1, 

Leader2, Prop1, Prop2, Prop3, Prop4, Memo, AddTime, AddUser, ModifyTime, ModifyUser)
  VALUES('120000095120000266120000276120000286', '2120999107001011G', '120000095120000266120000276', '0', 285, '红桥支行监察保卫/内控合规部', 4, 'Y', NULL, NULL, 'admin', 'administrator', 
'admin', '04', '120000286', NULL, NULL, 
NULL, '20100824', 'admin', NULL, NULL);
INSERT INTO zdbranch(BranchInnerCode, BranchCode, ParentInnerCode, Type, OrderFlag, Name, TreeLevel, IsLeaf, Phone, Fax, Manager, Leader1, 
Leader2, Prop1, Prop2, Prop3, Prop4, Memo, AddTime, AddUser, ModifyTime, ModifyUser)
  VALUES('120000095120000266120000276120000287', '2120999107001012E', '120000095120000266120000276', '0', 286, '红桥支行离退休人员', 4, 'Y', NULL, NULL, 'admin', 'administrator', 'admin', '04', 
'120000287', NULL, NULL, NULL, '20100824', 'admin', NULL, NULL);
INSERT INTO zdbranch(BranchInnerCode, BranchCode, ParentInnerCode, Type, OrderFlag, Name, TreeLevel, IsLeaf, Phone, Fax, Manager, Leader1, 
Leader2, Prop1, Prop2, Prop3, Prop4, Memo, AddTime, AddUser, ModifyTime, ModifyUser)
  VALUES('120000095120000266120000276120000288', '2120999107001013C', '120000095120000266120000276', '0', 287, '红桥支行信贷管理部', 4, 'Y', NULL, NULL, 'admin', 'administrator', 'admin', '04', 
'120000288', NULL, NULL, NULL, '20100824', 'admin', NULL, NULL);
INSERT INTO zdbranch(BranchInnerCode, BranchCode, ParentInnerCode, Type, OrderFlag, Name, TreeLevel, IsLeaf, Phone, Fax, Manager, Leader1, 

Leader2, Prop1, Prop2, Prop3, Prop4, Memo, AddTime, AddUser, ModifyTime, ModifyUser)
  VALUES('120000095120000266120000276120000289', '2120999107001014A', '120000095120000266120000276', '0', 288, '红桥支行公司业务部', 4, 'Y', NULL, NULL, 'admin', 'administrator', 'admin', '04', 
'120000289', NULL, NULL, NULL, '20100824', 'admin', NULL, NULL);
INSERT INTO zdbranch(BranchInnerCode, BranchCode, ParentInnerCode, Type, OrderFlag, Name, TreeLevel, IsLeaf, Phone, Fax, Manager, Leader1, 
Leader2, Prop1, Prop2, Prop3, Prop4, Memo, AddTime, AddUser, ModifyTime, ModifyUser)
  VALUES('120000095120000266120000276120000290', '21209991070010158', '120000095120000266120000276', '0', 289, '红桥支行个人金融部', 4, 'Y', NULL, NULL, 'admin', 'administrator', 'admin', '04', 
'120000290', NULL, NULL, NULL, '20100824', 'admin', NULL, NULL);
INSERT INTO zdbranch(BranchInnerCode, BranchCode, ParentInnerCode, Type, OrderFlag, Name, TreeLevel, IsLeaf, Phone, Fax, Manager, Leader1, 
Leader2, Prop1, Prop2, Prop3, Prop4, Memo, AddTime, AddUser, ModifyTime, ModifyUser)
  VALUES('120000095120000266120000276120000291', '21209991070010166', '120000095120000266120000276', '0', 290, '红桥支行风险资产经营部', 4, 'Y', NULL, NULL, 'admin', 'administrator', 'admin', 
'04', '120000291', NULL, NULL, NULL, '20100824', 'admin', NULL, NULL);
INSERT INTO zdbranch(BranchInnerCode, BranchCode, ParentInnerCode, Type, OrderFlag, Name, TreeLevel, IsLeaf, Phone, Fax, Manager, Leader1, 
Leader2, Prop1, Prop2, Prop3, Prop4, Memo, AddTime, AddUser, ModifyTime, ModifyUser)
  VALUES('120000095120000266120000276120000292', '21209991070010174', '120000095120000266120000276', '0', 291, '红桥支行资产风险管理部', 4, 'Y', NULL, NULL, 'admin', 'administrator', 'admin', '04', '120000292', NULL, NULL, NULL, '20100824', 'admin', NULL, NULL);
INSERT INTO zdbranch(BranchInnerCode, BranchCode, ParentInnerCode, Type, OrderFlag, Name, TreeLevel, IsLeaf, Phone, Fax, Manager, Leader1, 
Leader2, Prop1, Prop2, Prop3, Prop4, Memo, AddTime, AddUser, ModifyTime, ModifyUser)
  VALUES('120000095120000266120000293', '21209991071110008', '120000095120000266', '0', 292, '西青道支行', 3, 'Y', NULL, NULL, 'admin', 'administrator', 'admin', '06', '120000293', 
NULL, NULL, NULL, '20100824', 'admin', NULL, NULL);
INSERT INTO zdbranch(BranchInnerCode, BranchCode, ParentInnerCode, Type, OrderFlag, Name, TreeLevel, IsLeaf, Phone, Fax, Manager, Leader1, 
Leader2, Prop1, Prop2, Prop3, Prop4, Memo, AddTime, AddUser, ModifyTime, ModifyUser)
  VALUES('120000095120000266120000294', '2120999107106000K', '120000095120000266', '0', 293, '大胡同支行', 3, 'Y', NULL, NULL, 'admin', 'administrator', 'admin', '06', '120000294', 
NULL, NULL, NULL, '20100824', 'admin', NULL, NULL);
INSERT INTO zdbranch(BranchInnerCode, BranchCode, ParentInnerCode, Type, OrderFlag, Name, TreeLevel, IsLeaf, Phone, Fax, Manager, Leader1, 
Leader2, Prop1, Prop2, Prop3, Prop4, Memo, AddTime, AddUser, ModifyTime, ModifyUser)
  VALUES('120000095120000266120000295', '21209991071090005', '120000095120000266', '0', 294, '红桥支行河北工业大学分理处', 3, 'Y', NULL, NULL, 'admin', 'administrator', 'admin', '07', '120000295', NULL, 
NULL, NULL, '20100824', 'admin', NULL, NULL);
INSERT INTO zdbranch(BranchInnerCode, BranchCode, ParentInnerCode, Type, OrderFlag, Name, TreeLevel, IsLeaf, Phone, Fax, Manager, Leader1, 
Leader2, Prop1, Prop2, Prop3, Prop4, Memo, AddTime, AddUser, ModifyTime, ModifyUser)
  VALUES('120000095120000266120000296', '2120999107107000F', '120000095120000266', '0', 295, '中嘉支行', 3, 'Y', NULL, NULL, 'admin', 'administrator', 'admin', '06', '120000296', 
NULL, NULL, NULL, '20100824', 'admin', NULL, NULL);
INSERT INTO zdbranch(BranchInnerCode, BranchCode, ParentInnerCode, Type, OrderFlag, Name, TreeLevel, IsLeaf, Phone, Fax, Manager, Leader1, 
Leader2, Prop1, Prop2, Prop3, Prop4, Memo, AddTime, AddUser, ModifyTime, ModifyUser)
  VALUES('120000095120000266120000297', '21209991071180004', '120000095120000266', '0', 296, '红桥支行洪湖里分理处', 3, 'Y', NULL, NULL, 'admin', 'administrator', 'admin', 
'10', '120000297', NULL, NULL, NULL, '20100824', 'admin', NULL, NULL);
INSERT INTO zdbranch(BranchInnerCode, BranchCode, ParentInnerCode, Type, OrderFlag, Name, TreeLevel, IsLeaf, Phone, Fax, Manager, Leader1, 
Leader2, Prop1, Prop2, Prop3, Prop4, Memo, AddTime, AddUser, ModifyTime, ModifyUser)
  VALUES('120000095120000266120000298', '2120999107116000E', '120000095120000266', '0', 297, '红桥支行新红桥储蓄所', 3, 'Y', NULL, NULL, 'admin', 'administrator', 'admin', 
'10', '120000298', NULL, NULL, NULL, '20100824', 'admin', NULL, NULL);
INSERT INTO zdbranch(BranchInnerCode, BranchCode, ParentInnerCode, Type, OrderFlag, Name, TreeLevel, IsLeaf, Phone, Fax, Manager, Leader1, 

Leader2, Prop1, Prop2, Prop3, Prop4, Memo, AddTime, AddUser, ModifyTime, ModifyUser)
  VALUES('120000095120000266120000276120000299', '2120999107001001J', '120000095120000266120000276', '0', 298, '红桥支行营业部', 4, 'Y', NULL, NULL, 'admin', 'administrator', 'admin', '04', 
'120000299', NULL, NULL, NULL, '20100824', 'admin', NULL, NULL);
INSERT INTO zdbranch(BranchInnerCode, BranchCode, ParentInnerCode, Type, OrderFlag, Name, TreeLevel, IsLeaf, Phone, Fax, Manager, Leader1, 
Leader2, Prop1, Prop2, Prop3, Prop4, Memo, AddTime, AddUser, ModifyTime, ModifyUser)
  VALUES('120000095120000266120000300', '2120999107115000J', '120000095120000266', '0', 299, '洪湖支行', 3, 'Y', NULL, NULL, 'admin', 'administrator', 'admin', '06', '120000300', 
NULL, NULL, NULL, '20100824', 'admin', NULL, NULL);
INSERT INTO zdbranch(BranchInnerCode, BranchCode, ParentInnerCode, Type, OrderFlag, Name, TreeLevel, IsLeaf, Phone, Fax, Manager, Leader1, 

Leader2, Prop1, Prop2, Prop3, Prop4, Memo, AddTime, AddUser, ModifyTime, ModifyUser)
  VALUES('120000095120000301', '2120999108000000I', '120000095', '0', 300, '新技术产业园区支行', 2, 'Y', NULL, NULL, 'admin', 'administrator', 'admin', '04', '120000301', NULL, NULL, NULL, '20100824', 'admin', NULL, NULL);
INSERT INTO zdbranch(BranchInnerCode, BranchCode, ParentInnerCode, Type, OrderFlag, Name, TreeLevel, IsLeaf, Phone, Fax, Manager, Leader1, 

Leader2, Prop1, Prop2, Prop3, Prop4, Memo, AddTime, AddUser, ModifyTime, ModifyUser)
  VALUES('120000095120000301120000302', '2120999108001000D', '120000095120000301', '0', 301, '新技术产业园区支行机关', 3, 'Y', NULL, NULL, 'admin', 'administrator', 'admin', '04', '120000302', NULL, NULL, NULL, '20100824', 'admin', NULL, NULL);
INSERT INTO zdbranch(BranchInnerCode, BranchCode, ParentInnerCode, Type, OrderFlag, Name, TreeLevel, IsLeaf, Phone, Fax, Manager, Leader1, 

Leader2, Prop1, Prop2, Prop3, Prop4, Memo, AddTime, AddUser, ModifyTime, ModifyUser)
  VALUES('120000095120000301120000302120000303', '2120999108001001B', '120000095120000301120000302', '0', 302, '新技术产业园区支行其他', 4, 'Y', NULL, NULL, 'admin', 'administrator', 'admin', 
'04', '120000303', NULL, NULL, NULL, '20100824', 'admin', NULL, NULL);
INSERT INTO zdbranch(BranchInnerCode, BranchCode, ParentInnerCode, Type, OrderFlag, Name, TreeLevel, IsLeaf, Phone, Fax, Manager, Leader1, 
Leader2, Prop1, Prop2, Prop3, Prop4, Memo, AddTime, AddUser, ModifyTime, ModifyUser)
  VALUES('120000095120000301120000302120000304', '21209991080010029', '120000095120000301120000302', '0', 303, '新技术产业园区支行综合管理部', 4, 'Y', NULL, NULL, 'admin', 'administrator', 'admin', '04', '120000304', NULL, NULL, NULL, '20100824', 'admin', NULL, NULL);
INSERT INTO zdbranch(BranchInnerCode, BranchCode, ParentInnerCode, Type, OrderFlag, Name, TreeLevel, IsLeaf, Phone, Fax, Manager, Leader1, 
Leader2, Prop1, Prop2, Prop3, Prop4, Memo, AddTime, AddUser, ModifyTime, ModifyUser)
  VALUES('120000095120000301120000302120000305', '21209991080010045', '120000095120000301120000302', '0', 304, '新技术产业园区支行行长室', 4, 'Y', NULL, NULL, 'admin', 'administrator', 'admin', 
'04', '120000305', NULL, NULL, NULL, '20100824', 'admin', NULL, NULL);
INSERT INTO zdbranch(BranchInnerCode, BranchCode, ParentInnerCode, Type, OrderFlag, Name, TreeLevel, IsLeaf, Phone, Fax, Manager, Leader1, 

Leader2, Prop1, Prop2, Prop3, Prop4, Memo, AddTime, AddUser, ModifyTime, ModifyUser)
  VALUES('120000095120000301120000302120000306', '21209991080010053', '120000095120000301120000302', '0', 305, '新技术产业园区支行内退人员', 4, 'Y', NULL, NULL, 'admin', 'administrator', 
'admin', '04', '120000306', NULL, NULL, NULL, '20100824', 'admin', NULL, NULL);
INSERT INTO zdbranch(BranchInnerCode, BranchCode, ParentInnerCode, Type, OrderFlag, Name, TreeLevel, IsLeaf, Phone, Fax, Manager, Leader1, 

Leader2, Prop1, Prop2, Prop3, Prop4, Memo, AddTime, AddUser, ModifyTime, ModifyUser)
  VALUES('120000095120000301120000302120000307', '21209991080010061', '120000095120000301120000302', '0', 306, '新技术产业园区支行风险资产部', 4, 'Y', NULL, NULL, 'admin', 'administrator', 
'admin', '04', '120000307', NULL, NULL, NULL, '20100824', 'admin', NULL, NULL);
INSERT INTO zdbranch(BranchInnerCode, BranchCode, ParentInnerCode, Type, OrderFlag, Name, TreeLevel, IsLeaf, Phone, Fax, Manager, Leader1, 

Leader2, Prop1, Prop2, Prop3, Prop4, Memo, AddTime, AddUser, ModifyTime, ModifyUser)
  VALUES('120000095120000301120000302120000308', '21209991080010037', '120000095120000301120000302', '0', 307, '新技术产业园区支行财会运营部', 4, 'Y', NULL, NULL, 'admin', 'administrator', 
'admin', '04', '120000308', NULL, NULL, NULL, '20100824', 'admin', NULL, NULL);
INSERT INTO zdbranch(BranchInnerCode, BranchCode, ParentInnerCode, Type, OrderFlag, Name, TreeLevel, IsLeaf, Phone, Fax, Manager, Leader1, 

Leader2, Prop1, Prop2, Prop3, Prop4, Memo, AddTime, AddUser, ModifyTime, ModifyUser)
  VALUES('120000095120000301120000302120000309', '2120999108001007U', '120000095120000301120000302', '0', 308, '新技术产业园区支行监察保卫/内控合规部', 4, 'Y', NULL, NULL, 'admin', 
'administrator', 'admin', '04', '120000309', NULL, NULL, NULL, '20100824', 'admin', NULL, NULL);
INSERT INTO zdbranch(BranchInnerCode, BranchCode, ParentInnerCode, Type, OrderFlag, Name, TreeLevel, IsLeaf, Phone, Fax, Manager, Leader1, 

Leader2, Prop1, Prop2, Prop3, Prop4, Memo, AddTime, AddUser, ModifyTime, ModifyUser)
  VALUES('120000095120000301120000302120000310', '2120999108001008S', '120000095120000301120000302', '0', 309, '新技术产业园区支行离退休人员', 4, 'Y', NULL, NULL, 'admin', 'administrator', 
'admin', '04', '120000310', NULL, NULL, NULL, '20100824', 'admin', NULL, NULL);
INSERT INTO zdbranch(BranchInnerCode, BranchCode, ParentInnerCode, Type, OrderFlag, Name, TreeLevel, IsLeaf, Phone, Fax, Manager, Leader1, 

Leader2, Prop1, Prop2, Prop3, Prop4, Memo, AddTime, AddUser, ModifyTime, ModifyUser)
  VALUES('120000095120000301120000302120000311', '2120999108001009Q', '120000095120000301120000302', '0', 310, '新技术产业园区支行信贷管理部', 4, 'Y', NULL, NULL, 'admin', 'administrator', 
'admin', '04', '120000311', NULL, NULL, NULL, '20100824', 'admin', NULL, NULL);
INSERT INTO zdbranch(BranchInnerCode, BranchCode, ParentInnerCode, Type, OrderFlag, Name, TreeLevel, IsLeaf, Phone, Fax, Manager, Leader1, 

Leader2, Prop1, Prop2, Prop3, Prop4, Memo, AddTime, AddUser, ModifyTime, ModifyUser)
  VALUES('120000095120000301120000302120000312', '2120999108001010A', '120000095120000301120000302', '0', 311, '新技术产业园区支行公司业务部', 4, 'Y', NULL, NULL, 'admin', 'administrator', 'admin', '04', '120000312', NULL, 
NULL, NULL, '20100824', 'admin', NULL, NULL);
INSERT INTO zdbranch(BranchInnerCode, BranchCode, ParentInnerCode, Type, OrderFlag, Name, TreeLevel, IsLeaf, Phone, Fax, Manager, Leader1, 

Leader2, Prop1, Prop2, Prop3, Prop4, Memo, AddTime, AddUser, ModifyTime, ModifyUser)
  VALUES('120000095120000301120000302120000313', '21209991080010118', '120000095120000301120000302', '0', 312, '新技术产业园区支行个人金融部', 4, 'Y', NULL, NULL, 'admin', 'administrator', 
'admin', '04', '120000313', NULL, 
NULL, NULL, '20100824', 'admin', NULL, NULL);
INSERT INTO zdbranch(BranchInnerCode, BranchCode, ParentInnerCode, Type, OrderFlag, Name, TreeLevel, IsLeaf, Phone, Fax, Manager, Leader1, 

Leader2, Prop1, Prop2, Prop3, Prop4, Memo, AddTime, AddUser, ModifyTime, ModifyUser)
  VALUES('120000095120000301120000314', '2120999108119000M', '120000095120000301', '0', 313, '新技术产业园区支行柳荫路分理处', 3, 'Y', NULL, NULL, 'admin', 'administrator', 

'admin', '10', '120000314', NULL, 

NULL, NULL, '20100824', 'admin', NULL, NULL)
;
INSERT INTO zdbranch(BranchInnerCode, BranchCode, ParentInnerCode, Type, OrderFlag, Name, TreeLevel, IsLeaf, Phone, Fax, Manager, Leader1, 

Leader2, Prop1, Prop2, Prop3, Prop4, Memo, AddTime, AddUser, ModifyTime, ModifyUser)
  VALUES('120000095120000301120000315', '21209991081020001', '120000095120000301', '0', 314, '西营门支行', 3, 'Y', NULL, NULL, 'admin', 'administrator', 'admin', '06', '120000315', 

NULL, NULL, NULL, '20100824', 'admin', NULL, NULL);
INSERT INTO zdbranch(BranchInnerCode, BranchCode, ParentInnerCode, Type, OrderFlag, Name, TreeLevel, IsLeaf, Phone, Fax, Manager, Leader1, 

Leader2, Prop1, Prop2, Prop3, Prop4, Memo, AddTime, AddUser, ModifyTime, ModifyUser)
  VALUES('120000095120000301120000316', '21209991081160006', '120000095120000301', '0', 315, '新技术产业园区支行平湖里储蓄所', 3, 'Y', NULL, NULL, 'admin', 'administrator', 
'admin', '10', '120000316', NULL, 
NULL, NULL, '20100824', 'admin', NULL, NULL);
INSERT INTO zdbranch(BranchInnerCode, BranchCode, ParentInnerCode, Type, OrderFlag, Name, TreeLevel, IsLeaf, Phone, Fax, Manager, Leader1, 

Leader2, Prop1, Prop2, Prop3, Prop4, Memo, AddTime, AddUser, ModifyTime, ModifyUser)
  VALUES('120000095120000301120000317', '2120999108122000K', '120000095120000301', '0', 316, '榕苑路支行', 3, 'Y', NULL, NULL, 'admin', 'administrator', 'admin', '06', '120000317', 

NULL, NULL, NULL, '20100824', 'admin', NULL, NULL);
INSERT INTO zdbranch(BranchInnerCode, BranchCode, ParentInnerCode, Type, OrderFlag, Name, TreeLevel, IsLeaf, Phone, Fax, Manager, Leader1, 
Leader2, Prop1, Prop2, Prop3, Prop4, Memo, AddTime, AddUser, ModifyTime, ModifyUser)
  VALUES('120000095120000301120000318', '21209991081100005', '120000095120000301', '0', 317, '新技术产业园区支行复康路分理处', 3, 'Y', NULL, NULL, 'admin', 'administrator', 
'admin', '06', '120000318', NULL, 
NULL, NULL, '20100824', 'admin', NULL, NULL);
INSERT INTO zdbranch(BranchInnerCode, BranchCode, ParentInnerCode, Type, OrderFlag, Name, TreeLevel, IsLeaf, Phone, Fax, Manager, Leader1, 

Leader2, Prop1, Prop2, Prop3, Prop4, Memo, AddTime, AddUser, ModifyTime, ModifyUser)
  VALUES('120000095120000301120000319', '2120999108103000R', '120000095120000301', '0', 318, '华苑软件大厦支行', 3, 'Y', NULL, NULL, 'admin', 'administrator', 'admin', '06', 

'120000319', NULL, NULL, NULL, '20100824', 'admin', NULL, NULL);
INSERT INTO zdbranch(BranchInnerCode, BranchCode, ParentInnerCode, Type, OrderFlag, Name, TreeLevel, IsLeaf, Phone, Fax, Manager, Leader1, 

Leader2, Prop1, Prop2, Prop3, Prop4, Memo, AddTime, AddUser, ModifyTime, ModifyUser)
  VALUES('120000095120000301120000320', '2120999108118000R', '120000095120000301', '0', 319, '新技术产业园区支行宝山道储蓄所', 3, 'Y', NULL, NULL, 'admin', 'administrator', 
'admin', '10', '120000320', NULL, 
NULL, NULL, '20100824', 'admin', NULL, NULL);
INSERT INTO zdbranch(BranchInnerCode, BranchCode, ParentInnerCode, Type, OrderFlag, Name, TreeLevel, IsLeaf, Phone, Fax, Manager, Leader1, 
Leader2, Prop1, Prop2, Prop3, Prop4, Memo, AddTime, AddUser, ModifyTime, ModifyUser)
  VALUES('120000095120000301120000321', '2120999108104000M', '120000095120000301', '0', 320, '长江道支行', 3, 'Y', NULL, NULL, 'admin', 'administrator', 'admin', '06', '120000321', 
NULL, NULL, NULL, '20100824', 'admin', NULL, NULL);
INSERT INTO zdbranch(BranchInnerCode, BranchCode, ParentInnerCode, Type, OrderFlag, Name, TreeLevel, IsLeaf, Phone, Fax, Manager, Leader1, 

Leader2, Prop1, Prop2, Prop3, Prop4, Memo, AddTime, AddUser, ModifyTime, ModifyUser)
  VALUES('120000095120000301120000322', '2120999108120000U', '120000095120000301', '0', 321, '新技术产业园区支行凯兴储蓄所', 3, 'Y', NULL, NULL, 'admin', 'administrator', 
'admin', '10', '120000322', NULL, NULL, NULL, '20100824', 'admin', NULL, NULL);
INSERT INTO zdbranch(BranchInnerCode, BranchCode, ParentInnerCode, Type, OrderFlag, Name, TreeLevel, IsLeaf, Phone, Fax, Manager, Leader1, 

Leader2, Prop1, Prop2, Prop3, Prop4, Memo, AddTime, AddUser, ModifyTime, ModifyUser)
  VALUES('120000095120000301120000323', '2120999108105000H', '120000095120000301', '0', 322, '天诚支行', 3, 'Y', NULL, NULL, 'admin', 'administrator', 'admin', '06', '120000323', 
NULL, NULL, NULL, '20100824', 'admin', NULL, NULL);
INSERT INTO zdbranch(BranchInnerCode, BranchCode, ParentInnerCode, Type, OrderFlag, Name, TreeLevel, IsLeaf, Phone, Fax, Manager, Leader1, 
Leader2, Prop1, Prop2, Prop3, Prop4, Memo, AddTime, AddUser, ModifyTime, ModifyUser)
  VALUES('120000095120000301120000324', '2120999108121000P', '120000095120000301', '0', 323, '新技术产业园区白堤路分理处', 3, 'Y', NULL, NULL, 'admin', 'administrator', 
'admin', '06', '120000324', NULL, NULL, NULL, '20100824', 'admin', NULL, NULL);
INSERT INTO zdbranch(BranchInnerCode, BranchCode, ParentInnerCode, Type, OrderFlag, Name, TreeLevel, IsLeaf, Phone, Fax, Manager, Leader1, 

Leader2, Prop1, Prop2, Prop3, Prop4, Memo, AddTime, AddUser, ModifyTime, ModifyUser)
  VALUES('120000095120000301120000325', '2120999108106000C', '120000095120000301', '0', 324, '新技术产业园区支行双峰道支行', 3, 'Y', NULL, NULL, 'admin', 'administrator', 
'admin', '06', '120000325', NULL, 
NULL, NULL, '20100824', 'admin', NULL, NULL);
INSERT INTO zdbranch(BranchInnerCode, BranchCode, ParentInnerCode, Type, OrderFlag, Name, TreeLevel, IsLeaf, Phone, Fax, Manager, Leader1, 

Leader2, Prop1, Prop2, Prop3, Prop4, Memo, AddTime, AddUser, ModifyTime, ModifyUser)
  VALUES('120000095120000301120000326', '21209991081070007', '120000095120000301', '0', 325, '馨名园支行', 3, 'Y', NULL, NULL, 'admin', 'administrator', 'admin', '06', '120000326', 
NULL, NULL, NULL, '20100824', 'admin', NULL, NULL);
INSERT INTO zdbranch(BranchInnerCode, BranchCode, ParentInnerCode, Type, OrderFlag, Name, TreeLevel, IsLeaf, Phone, Fax, Manager, Leader1, 

Leader2, Prop1, Prop2, Prop3, Prop4, Memo, AddTime, AddUser, ModifyTime, ModifyUser)
  VALUES('120000095120000301120000327', '21209991081080002', '120000095120000301', '0', 326, '卫津南路支行', 3, 'Y', NULL, NULL, 'admin', 'administrator', 'admin', '06', '120000327', 
NULL, NULL, NULL, '20100824', 'admin', NULL, NULL);
INSERT INTO zdbranch(BranchInnerCode, BranchCode, ParentInnerCode, Type, OrderFlag, Name, TreeLevel, IsLeaf, Phone, Fax, Manager, Leader1, 
Leader2, Prop1, Prop2, Prop3, Prop4, Memo, AddTime, AddUser, ModifyTime, ModifyUser)
  VALUES('120000095120000301120000328', '2120999108109000S', '120000095120000301', '0', 327, '鞍山西道支行', 3, 'Y', NULL, NULL, 'admin', 'administrator', 'admin', '06', 
'120000328', NULL, NULL, NULL, '20100824', 'admin', NULL, NULL);
INSERT INTO zdbranch(BranchInnerCode, BranchCode, ParentInnerCode, Type, OrderFlag, Name, TreeLevel, IsLeaf, Phone, Fax, Manager, Leader1, 

Leader2, Prop1, Prop2, Prop3, Prop4, Memo, AddTime, AddUser, ModifyTime, ModifyUser)
  VALUES('120000095120000329', '2120999109000000A', '120000095', '0', 328, '世贸支行', 2, 'Y', NULL, NULL, 'admin', 'administrator', 'admin', '04', '120000329', 
NULL, NULL, NULL, '20100824', 'admin', NULL, NULL);
INSERT INTO zdbranch(BranchInnerCode, BranchCode, ParentInnerCode, Type, OrderFlag, Name, TreeLevel, IsLeaf, Phone, Fax, Manager, Leader1, 

Leader2, Prop1, Prop2, Prop3, Prop4, Memo, AddTime, AddUser, ModifyTime, ModifyUser)
  VALUES('120000095120000329120000330', '21209991090010005', '120000095120000329', '0', 329, '世贸支行机关', 3, 'Y', NULL, NULL, 'admin', 'administrator', 'admin', '04', 
'120000330', NULL, NULL, NULL, '20100824', 'admin', NULL, NULL);
INSERT INTO zdbranch(BranchInnerCode, BranchCode, ParentInnerCode, Type, OrderFlag, Name, TreeLevel, IsLeaf, Phone, Fax, Manager, Leader1, 
Leader2, Prop1, Prop2, Prop3, Prop4, Memo, AddTime, AddUser, ModifyTime, ModifyUser)
  VALUES('120000095120000329120000330120000331', '21209991090010013', '120000095120000329120000330', '0', 330, '世贸支行其他', 4, 'Y', NULL, NULL, 'admin', 'administrator', 'admin', '04', '120000331', 
NULL, NULL, NULL, '20100824', 'admin', NULL, NULL);
INSERT INTO zdbranch(BranchInnerCode, BranchCode, ParentInnerCode, Type, OrderFlag, Name, TreeLevel, IsLeaf, Phone, Fax, Manager, Leader1, 

Leader2, Prop1, Prop2, Prop3, Prop4, Memo, AddTime, AddUser, ModifyTime, ModifyUser)
  VALUES('120000095120000329120000330120000332', '21209991090010021', '120000095120000329120000330', '0', 331, '世贸支行综合管理部', 4, 'Y', NULL, NULL, 'admin', 'administrator', 'admin', '04', 
'120000332', NULL, NULL, NULL, '20100824', 'admin', NULL, NULL);
INSERT INTO zdbranch(BranchInnerCode, BranchCode, ParentInnerCode, Type, OrderFlag, Name, TreeLevel, IsLeaf, Phone, Fax, Manager, Leader1, 

Leader2, Prop1, Prop2, Prop3, Prop4, Memo, AddTime, AddUser, ModifyTime, ModifyUser)
  VALUES('120000095120000329120000330120000333', '2120999109001003U', '120000095120000329120000330', '0', 332, '世贸支行行长室', 4, 'Y', NULL, NULL, 'admin', 'administrator', 'admin', '04', '120000333', NULL, NULL, NULL, '20100824', 'admin', NULL, NULL);
INSERT INTO zdbranch(BranchInnerCode, BranchCode, ParentInnerCode, Type, OrderFlag, Name, TreeLevel, IsLeaf, Phone, Fax, Manager, Leader1, 

Leader2, Prop1, Prop2, Prop3, Prop4, Memo, AddTime, AddUser, ModifyTime, ModifyUser)
  VALUES('120000095120000329120000330120000334', '2120999109001004S', '120000095120000329120000330', '0', 333, '世贸支行内退人员', 4, 'Y', NULL, NULL, 'admin', 'administrator', 'admin', '04', 

'120000334', NULL, NULL, NULL, '20100824', 'admin', NULL, NULL);
INSERT INTO zdbranch(BranchInnerCode, BranchCode, ParentInnerCode, Type, OrderFlag, Name, TreeLevel, IsLeaf, Phone, Fax, Manager, Leader1, 

Leader2, Prop1, Prop2, Prop3, Prop4, Memo, AddTime, AddUser, ModifyTime, ModifyUser)
  VALUES('120000095120000329120000330120000335', '2120999109001005Q', '120000095120000329120000330', '0', 334, '世贸支行保险代理部', 4, 'Y', NULL, NULL, 'admin', 'administrator', 'admin', '04', 

'120000335', NULL, NULL, NULL, '20100824', 'admin', NULL, NULL);
INSERT INTO zdbranch(BranchInnerCode, BranchCode, ParentInnerCode, Type, OrderFlag, Name, TreeLevel, IsLeaf, Phone, Fax, Manager, Leader1, 

Leader2, Prop1, Prop2, Prop3, Prop4, Memo, AddTime, AddUser, ModifyTime, ModifyUser)
  VALUES('120000095120000329120000330120000336', '2120999109001006O', '120000095120000329120000330', '0', 335, '世贸支行风险资产部', 4, 'Y', NULL, NULL, 'admin', 'administrator', 'admin', '04', 
'120000336', NULL, NULL, NULL, '20100824', 'admin', NULL, NULL);
INSERT INTO zdbranch(BranchInnerCode, BranchCode, ParentInnerCode, Type, OrderFlag, Name, TreeLevel, IsLeaf, Phone, Fax, Manager, Leader1, 

Leader2, Prop1, Prop2, Prop3, Prop4, Memo, AddTime, AddUser, ModifyTime, ModifyUser)
  VALUES('120000095120000329120000330120000337', '2120999109001007M', '120000095120000329120000330', '0', 336, '世贸支行国际业务部', 4, 'Y', NULL, NULL, 'admin', 'administrator', 'admin', '04', 
'120000337', NULL, NULL, NULL, '20100824', 'admin', NULL, NULL);
INSERT INTO zdbranch(BranchInnerCode, BranchCode, ParentInnerCode, Type, OrderFlag, Name, TreeLevel, IsLeaf, Phone, Fax, Manager, Leader1, 

Leader2, Prop1, Prop2, Prop3, Prop4, Memo, AddTime, AddUser, ModifyTime, ModifyUser)
  VALUES('120000095120000329120000330120000338', '2120999109001008K', '120000095120000329120000330', '0', 337, '世贸支行财会运营部', 4, 'Y', NULL, NULL, 'admin', 'administrator', 'admin', '04', 
'120000338', NULL, NULL, NULL, '20100824', 'admin', NULL, NULL);
INSERT INTO zdbranch(BranchInnerCode, BranchCode, ParentInnerCode, Type, OrderFlag, Name, TreeLevel, IsLeaf, Phone, Fax, Manager, Leader1, 

Leader2, Prop1, Prop2, Prop3, Prop4, Memo, AddTime, AddUser, ModifyTime, ModifyUser)
  VALUES('120000095120000329120000330120000339', '2120999109001009I', '120000095120000329120000330', '0', 338, '世贸支行监察保卫/内控合规部', 4, 'Y', NULL, NULL, 'admin', 'administrator', 
'admin', '04', '120000339', NULL, NULL, NULL, '20100824', 'admin', NULL, NULL);
INSERT INTO zdbranch(BranchInnerCode, BranchCode, ParentInnerCode, Type, OrderFlag, Name, TreeLevel, IsLeaf, Phone, Fax, Manager, Leader1, 

Leader2, Prop1, Prop2, Prop3, Prop4, Memo, AddTime, AddUser, ModifyTime, ModifyUser)
  VALUES('120000095120000329120000330120000340', '21209991090010102', '120000095120000329120000330', '0', 339, '世贸支行离退休人员', 4, 'Y', NULL, NULL, 'admin', 'administrator', 'admin', '04', 
'120000340', NULL, NULL, NULL, '20100824', 'admin', NULL, NULL);
INSERT INTO zdbranch(BranchInnerCode, BranchCode, ParentInnerCode, Type, OrderFlag, Name, TreeLevel, IsLeaf, Phone, Fax, Manager, Leader1, 

Leader2, Prop1, Prop2, Prop3, Prop4, Memo, AddTime, AddUser, ModifyTime, ModifyUser)
  VALUES('120000095120000329120000330120000341', '2120999109001011V', '120000095120000329120000330', '0', 340, '世贸支行信贷管理部', 4, 'Y', NULL, NULL, 'admin', 'administrator', 'admin', '04', 
'120000341', NULL, NULL, NULL, '20100824', 'admin', NULL, NULL);
INSERT INTO zdbranch(BranchInnerCode, BranchCode, ParentInnerCode, Type, OrderFlag, Name, TreeLevel, IsLeaf, Phone, Fax, Manager, Leader1, 

Leader2, Prop1, Prop2, Prop3, Prop4, Memo, AddTime, AddUser, ModifyTime, ModifyUser)
  VALUES('120000095120000329120000330120000342', '2120999109001012T', '120000095120000329120000330', '0', 341, '世贸支行公司业务部', 4, 'Y', NULL, NULL, 'admin', 'administrator', 'admin', '04', 
'120000342', NULL, NULL, NULL, '20100824', 'admin', NULL, NULL);
INSERT INTO zdbranch(BranchInnerCode, BranchCode, ParentInnerCode, Type, OrderFlag, Name, TreeLevel, IsLeaf, Phone, Fax, Manager, Leader1, 

Leader2, Prop1, Prop2, Prop3, Prop4, Memo, AddTime, AddUser, ModifyTime, ModifyUser)
  VALUES('120000095120000329120000330120000343', '2120999109001013R', '120000095120000329120000330', '0', 342, '世贸支行个人金融部', 4, 'Y', NULL, NULL, 'admin', 'administrator', 'admin', '04', 
'120000343', NULL, NULL, NULL, '20100824', 'admin', NULL, NULL);
INSERT INTO zdbranch(BranchInnerCode, BranchCode, ParentInnerCode, Type, OrderFlag, Name, TreeLevel, IsLeaf, Phone, Fax, Manager, Leader1, 

Leader2, Prop1, Prop2, Prop3, Prop4, Memo, AddTime, AddUser, ModifyTime, ModifyUser)
  VALUES('120000095120000329120000344', '2120999109101000T', '120000095120000329', '0', 343, '南京路支行', 3, 'Y', NULL, NULL, 'admin', 'administrator', 'admin', '06', '120000344', 
NULL, NULL, NULL, '20100824', 'admin', NULL, NULL);
INSERT INTO zdbranch(BranchInnerCode, BranchCode, ParentInnerCode, Type, OrderFlag, Name, TreeLevel, IsLeaf, Phone, Fax, Manager, Leader1, 
Leader2, Prop1, Prop2, Prop3, Prop4, Memo, AddTime, AddUser, ModifyTime, ModifyUser)
  VALUES('120000095120000329120000345', '2120999109102000O', '120000095120000329', '0', 344, '信诚大厦支行', 3, 'Y', NULL, NULL, 'admin', 'administrator', 'admin', '06', '120000345', 
NULL, NULL, NULL, '20100824', 'admin', NULL, NULL);
INSERT INTO zdbranch(BranchInnerCode, BranchCode, ParentInnerCode, Type, OrderFlag, Name, TreeLevel, IsLeaf, Phone, Fax, Manager, Leader1, 
Leader2, Prop1, Prop2, Prop3, Prop4, Memo, AddTime, AddUser, ModifyTime, ModifyUser)
  VALUES('120000095120000329120000346', '2120999109104000E', '120000095120000329', '0', 345, '成都道支行', 3, 'Y', NULL, NULL, 'admin', 'administrator', 'admin', '06', '120000346', 
NULL, NULL, NULL, '20100824', 'admin', NULL, NULL);
INSERT INTO zdbranch(BranchInnerCode, BranchCode, ParentInnerCode, Type, OrderFlag, Name, TreeLevel, IsLeaf, Phone, Fax, Manager, Leader1, 

Leader2, Prop1, Prop2, Prop3, Prop4, Memo, AddTime, AddUser, ModifyTime, ModifyUser)
  VALUES('120000095120000329120000347', '21209991091050009', '120000095120000329', '0', 346, '滨湖支行', 3, 'Y', NULL, NULL, 'admin', 'administrator', 'admin', '06', '120000347', 
NULL, NULL, NULL, '20100824', 'admin', NULL, NULL);
INSERT INTO zdbranch(BranchInnerCode, BranchCode, ParentInnerCode, Type, OrderFlag, Name, TreeLevel, IsLeaf, Phone, Fax, Manager, Leader1, 

Leader2, Prop1, Prop2, Prop3, Prop4, Memo, AddTime, AddUser, ModifyTime, ModifyUser)
  VALUES('120000095120000329120000348', '2120999109107000U', '120000095120000329', '0', 347, '碧轩园支行', 3, 'Y', NULL, NULL, 'admin', 'administrator', 'admin', '06', '120000348', 
NULL, NULL, NULL, '20100824', 'admin', NULL, NULL);
INSERT INTO zdbranch(BranchInnerCode, BranchCode, ParentInnerCode, Type, OrderFlag, Name, TreeLevel, IsLeaf, Phone, Fax, Manager, Leader1, 

Leader2, Prop1, Prop2, Prop3, Prop4, Memo, AddTime, AddUser, ModifyTime, ModifyUser)
  VALUES('120000095120000329120000349', '2120999109103000J', '120000095120000329', '0', 348, '海光寺支行', 3, 'Y', NULL, NULL, 'admin', 'administrator', 'admin', '06', '120000349', 
NULL, NULL, NULL, '20100824', 'admin', NULL, NULL);
INSERT INTO zdbranch(BranchInnerCode, BranchCode, ParentInnerCode, Type, OrderFlag, Name, TreeLevel, IsLeaf, Phone, Fax, Manager, Leader1, 

Leader2, Prop1, Prop2, Prop3, Prop4, Memo, AddTime, AddUser, ModifyTime, ModifyUser)
  VALUES('120000095120000329120000350', '2120999109108000P', '120000095120000329', '0', 349, '世贸支行广开四马路分理处', 3, 'Y', NULL, NULL, 'admin', 'administrator', 'admin', 
'06', '120000350', NULL, NULL, NULL, '20100824', 'admin', NULL, NULL);
INSERT INTO zdbranch(BranchInnerCode, BranchCode, ParentInnerCode, Type, OrderFlag, Name, TreeLevel, IsLeaf, Phone, Fax, Manager, Leader1, 
Leader2, Prop1, Prop2, Prop3, Prop4, Memo, AddTime, AddUser, ModifyTime, ModifyUser)
  VALUES('120000095120000329120000351', '2120999109109000K', '120000095120000329', '0', 350, '世贸支行李公楼分理处', 3, 'Y', NULL, NULL, 'admin', 'administrator', 'admin', '06', 
'120000351', NULL, NULL, NULL, '20100824', 'admin', NULL, NULL);
INSERT INTO zdbranch(BranchInnerCode, BranchCode, ParentInnerCode, Type, OrderFlag, Name, TreeLevel, IsLeaf, Phone, Fax, Manager, Leader1, 
Leader2, Prop1, Prop2, Prop3, Prop4, Memo, AddTime, AddUser, ModifyTime, ModifyUser)
  VALUES('120000095120000135120000352', '2120999103125000E', '120000095120000135', '0', 351, '广厦支行', 3, 'Y', NULL, NULL, 'admin', 'administrator', 'admin', '06', '120000352', 
NULL, NULL, NULL, '20100824', 'admin', NULL, NULL);
INSERT INTO zdbranch(BranchInnerCode, BranchCode, ParentInnerCode, Type, OrderFlag, Name, TreeLevel, IsLeaf, Phone, Fax, Manager, Leader1, 

Leader2, Prop1, Prop2, Prop3, Prop4, Memo, AddTime, AddUser, ModifyTime, ModifyUser)
  VALUES('120000095120000135120000352120000353', '2120999103106000L', '120000095120000135120000352', '0', 352, '广厦支行机关', 4, 'Y', NULL, NULL, 'admin', 'administrator', 'admin', '06', '120000353', 
NULL, NULL, NULL, '20100824', 'admin', NULL, NULL);
INSERT INTO zdbranch(BranchInnerCode, BranchCode, ParentInnerCode, Type, OrderFlag, Name, TreeLevel, IsLeaf, Phone, Fax, Manager, Leader1, 

Leader2, Prop1, Prop2, Prop3, Prop4, Memo, AddTime, AddUser, ModifyTime, ModifyUser)
  VALUES('120000095120000135120000352120000353120000354', '2120999103106022B', '120000095120000135120000352120000353', '0', 353, '广厦支行其他', 5, 'Y', NULL, NULL, 'admin', 'administrator', 'admin', '06', '120000354', 
NULL, NULL, NULL, '20100824', 'admin', NULL, NULL);
INSERT INTO zdbranch(BranchInnerCode, BranchCode, ParentInnerCode, Type, OrderFlag, Name, TreeLevel, IsLeaf, Phone, Fax, Manager, Leader1, 

Leader2, Prop1, Prop2, Prop3, Prop4, Memo, AddTime, AddUser, ModifyTime, ModifyUser)
  VALUES('120000095120000135120000352120000353120000355', '21209991031060255', '120000095120000135120000352120000353', '0', 354, '广厦支行办公室', 5, 'Y', NULL, NULL, 'admin', 'administrator', 'admin', '06', '120000355', NULL, NULL, NULL, '20100824', 'admin', NULL, NULL);
INSERT INTO zdbranch(BranchInnerCode, BranchCode, ParentInnerCode, Type, OrderFlag, Name, TreeLevel, IsLeaf, Phone, Fax, Manager, Leader1, 

Leader2, Prop1, Prop2, Prop3, Prop4, Memo, AddTime, AddUser, ModifyTime, ModifyUser)
  VALUES('120000095120000135120000352120000353120000356', '21209991031060247', '120000095120000135120000352120000353', '0', 355, '广厦支行行长室', 5, 'Y', NULL, NULL, 'admin', 'administrator', 'admin', '06', 

'120000356', NULL, NULL, NULL, '20100824', 'admin', NULL, NULL);
INSERT INTO zdbranch(BranchInnerCode, BranchCode, ParentInnerCode, Type, OrderFlag, Name, TreeLevel, IsLeaf, Phone, Fax, Manager, Leader1, 

Leader2, Prop1, Prop2, Prop3, Prop4, Memo, AddTime, AddUser, ModifyTime, ModifyUser)
  VALUES('120000095120000135120000352120000353120000357', '2120999103106021D', '120000095120000135120000352120000353', '0', 356, '广厦支行内退人员', 5, 'Y', NULL, NULL, 'admin', 'administrator', 'admin', '06', 
'120000357', NULL, NULL, NULL, '20100824', 'admin', NULL, NULL);
INSERT INTO zdbranch(BranchInnerCode, BranchCode, ParentInnerCode, Type, OrderFlag, Name, TreeLevel, IsLeaf, Phone, Fax, Manager, Leader1, 

Leader2, Prop1, Prop2, Prop3, Prop4, Memo, AddTime, AddUser, ModifyTime, ModifyUser)
  VALUES('120000095120000135120000352120000353120000358', '21209991031060174', '120000095120000135120000352120000353', '0', 357, '广厦支行银行卡部', 5, 'Y', NULL, NULL, 'admin', 'administrator', 'admin', '06', 
'120000358', NULL, NULL, NULL, '20100824', 'admin', NULL, NULL);
INSERT INTO zdbranch(BranchInnerCode, BranchCode, ParentInnerCode, Type, OrderFlag, Name, TreeLevel, IsLeaf, Phone, Fax, Manager, Leader1, 

Leader2, Prop1, Prop2, Prop3, Prop4, Memo, AddTime, AddUser, ModifyTime, ModifyUser)
  VALUES('120000095120000135120000352120000353120000359', '21209991031060166', '120000095120000135120000352120000353', '0', 358, '广厦支行保险代理部', 5, 'Y', NULL, NULL, 'admin', 'administrator', 'admin', '06', 
'120000359', NULL, NULL, NULL, '20100824', 'admin', NULL, NULL);
INSERT INTO zdbranch(BranchInnerCode, BranchCode, ParentInnerCode, Type, OrderFlag, Name, TreeLevel, IsLeaf, Phone, Fax, Manager, Leader1, 

Leader2, Prop1, Prop2, Prop3, Prop4, Memo, AddTime, AddUser, ModifyTime, ModifyUser)
  VALUES('120000095120000135120000352120000353120000360', '2120999103106019V', '120000095120000135120000352120000353', '0', 359, '广厦支行风险控管部', 5, 'Y', NULL, NULL, 'admin', 'administrator', 'admin', '06', 
'120000360', NULL, NULL, NULL, '20100824', 'admin', NULL, NULL);





INSERT INTO zdbranch(BranchInnerCode, BranchCode, ParentInnerCode, Type, OrderFlag, Name, TreeLevel, IsLeaf, Phone, Fax, Manager, Leader1, 

Leader2, Prop1, Prop2, Prop3, Prop4, Memo, AddTime, AddUser, ModifyTime, ModifyUser)
  VALUES('120000095120000135120000352120000353120000361', '21209991031060263', '120000095120000135120000352120000353', '0', 360, '广厦支行个人业务部', 5, 'Y', NULL, NULL, 'admin', 'administrator', 'admin', '06', 
'120000361', NULL, NULL, NULL, '20100824', 'admin', NULL, NULL);
INSERT INTO zdbranch(BranchInnerCode, BranchCode, ParentInnerCode, Type, OrderFlag, Name, TreeLevel, IsLeaf, Phone, Fax, Manager, Leader1, 

Leader2, Prop1, Prop2, Prop3, Prop4, Memo, AddTime, AddUser, ModifyTime, ModifyUser)
  VALUES('120000095120000135120000352120000353120000362', '21209991031060239', '120000095120000135120000352120000353', '0', 361, '广厦支行国际业务部', 5, 'Y', NULL, NULL, 'admin', 'administrator', 'admin', '06', 
'120000362', NULL, NULL, NULL, '20100824', 'admin', NULL, NULL);
INSERT INTO zdbranch(BranchInnerCode, BranchCode, ParentInnerCode, Type, OrderFlag, Name, TreeLevel, IsLeaf, Phone, Fax, Manager, Leader1, 

Leader2, Prop1, Prop2, Prop3, Prop4, Memo, AddTime, AddUser, ModifyTime, ModifyUser)
  VALUES('120000095120000135120000352120000353120000363', '21209991031060158', '120000095120000135120000352120000353', '0', 362, '广厦支行计划财务部', 5, 'Y', NULL, NULL, 'admin', 'administrator', 'admin', '06', 
'120000363', NULL, NULL, NULL, '20100824', 'admin', NULL, NULL);
INSERT INTO zdbranch(BranchInnerCode, BranchCode, ParentInnerCode, Type, OrderFlag, Name, TreeLevel, IsLeaf, Phone, Fax, Manager, Leader1, 

Leader2, Prop1, Prop2, Prop3, Prop4, Memo, AddTime, AddUser, ModifyTime, ModifyUser)
  VALUES('120000095120000135120000352120000353120000364', '2120999103106020F', '120000095120000135120000352120000353', '0', 363, '广厦支行离退休人员', 5, 'Y', NULL, NULL, 'admin', 'administrator', 'admin', '06', 
'120000364', NULL, NULL, NULL, '20100824', 'admin', NULL, NULL);
INSERT INTO zdbranch(BranchInnerCode, BranchCode, ParentInnerCode, Type, OrderFlag, Name, TreeLevel, IsLeaf, Phone, Fax, Manager, Leader1, 

Leader2, Prop1, Prop2, Prop3, Prop4, Memo, AddTime, AddUser, ModifyTime, ModifyUser)
  VALUES('120000095120000135120000352120000353120000365', '2120999103106028U', '120000095120000135120000352120000353', '0', 364, '广厦支行信贷管理部', 5, 'Y', NULL, NULL, 'admin', 'administrator', 'admin', '06', 
'120000365', NULL, NULL, NULL, '20100824', 'admin', NULL, NULL);
INSERT INTO zdbranch(BranchInnerCode, BranchCode, ParentInnerCode, Type, OrderFlag, Name, TreeLevel, IsLeaf, Phone, Fax, Manager, Leader1, 

Leader2, Prop1, Prop2, Prop3, Prop4, Memo, AddTime, AddUser, ModifyTime, ModifyUser)
  VALUES('120000095120000135120000352120000353120000366', '21209991031060271', '120000095120000135120000352120000353', '0', 365, '广厦支行业务拓展部', 5, 'Y', NULL, NULL, 'admin', 'administrator', 'admin', '06', 
NULL, NULL, NULL, NULL, '20100824', 'admin', NULL, NULL);
INSERT INTO zdbranch(BranchInnerCode, BranchCode, ParentInnerCode, Type, OrderFlag, Name, TreeLevel, IsLeaf, Phone, Fax, Manager, Leader1, 

Leader2, Prop1, Prop2, Prop3, Prop4, Memo, AddTime, AddUser, ModifyTime, ModifyUser)
  VALUES('120000095120000135120000367', '2120999103123000O', '120000095120000135', '0', 366, '海河支行', 3, 'Y', NULL, NULL, 'admin', 'administrator', 'admin', '06', '120000367', 
NULL, NULL, NULL, '20100824', 'admin', NULL, NULL);
INSERT INTO zdbranch(BranchInnerCode, BranchCode, ParentInnerCode, Type, OrderFlag, Name, TreeLevel, IsLeaf, Phone, Fax, Manager, Leader1, 

Leader2, Prop1, Prop2, Prop3, Prop4, Memo, AddTime, AddUser, ModifyTime, ModifyUser)
  VALUES('120000095120000135120000367120000368', '2120999103119000V', '120000095120000135120000367', '0', 367, '海河支行机关', 4, 'Y', NULL, NULL, 'admin', 'administrator', 'admin', '04', '120000368', 

NULL, NULL, NULL, '20100824', 'admin', NULL, NULL);
INSERT INTO zdbranch(BranchInnerCode, BranchCode, ParentInnerCode, Type, OrderFlag, Name, TreeLevel, IsLeaf, Phone, Fax, Manager, Leader1, 

Leader2, Prop1, Prop2, Prop3, Prop4, Memo, AddTime, AddUser, ModifyTime, ModifyUser)
  VALUES('120000095120000135120000367120000368120000369', '2120999103119023J', '120000095120000135120000367120000368', '0', 368, '海河支行其他', 5, 'Y', NULL, NULL, 'admin', 'administrator', 'admin', '04', '120000369', 

NULL, NULL, NULL, '20100824', 'admin', NULL, NULL);
INSERT INTO zdbranch(BranchInnerCode, BranchCode, ParentInnerCode, Type, OrderFlag, Name, TreeLevel, IsLeaf, Phone, Fax, Manager, Leader1, 

Leader2, Prop1, Prop2, Prop3, Prop4, Memo, AddTime, AddUser, ModifyTime, ModifyUser)
  VALUES('120000095120000135120000367120000368120000370', '2120999103119021N', '120000095120000135120000367120000368', '0', 369, '海河支行办公室', 5, 'Y', NULL, NULL, 'admin', 'administrator', 'admin', '04', 
'120000370', NULL, NULL, NULL, '20100824', 'admin', NULL, NULL);

INSERT INTO zdbranch(BranchInnerCode, BranchCode, ParentInnerCode, Type, OrderFlag, Name, TreeLevel, IsLeaf, Phone, Fax, Manager, Leader1, 

Leader2, Prop1, Prop2, Prop3, Prop4, Memo, AddTime, AddUser, ModifyTime, ModifyUser)
  VALUES('120000095120000135120000367120000368120000371', '2120999103119015I', '120000095120000135120000367120000368', '0', 370, '海河支行行长室', 5, 'Y', NULL, NULL, 'admin', 'administrator', 'admin', '04', 

'120000371', NULL, NULL, NULL, '20100824', 'admin', NULL, NULL);
INSERT INTO zdbranch(BranchInnerCode, BranchCode, ParentInnerCode, Type, OrderFlag, Name, TreeLevel, IsLeaf, Phone, Fax, Manager, Leader1, 

Leader2, Prop1, Prop2, Prop3, Prop4, Memo, AddTime, AddUser, ModifyTime, ModifyUser)
  VALUES('120000095120000135120000367120000368120000372', '2120999103119020P', '120000095120000135120000367120000368', '0', 371, '海河支行保险代理部', 5, 'Y', NULL, NULL, 'admin', 'administrator', 'admin', '04', 
'120000372', NULL, NULL, NULL, '20100824', 'admin', NULL, NULL);
INSERT INTO zdbranch(BranchInnerCode, BranchCode, ParentInnerCode, Type, OrderFlag, Name, TreeLevel, IsLeaf, Phone, Fax, Manager, Leader1, 

Leader2, Prop1, Prop2, Prop3, Prop4, Memo, AddTime, AddUser, ModifyTime, ModifyUser)
  VALUES('120000095120000135120000367120000368120000373', '2120999103119024H', '120000095120000135120000367120000368', '0', 372, '海河支行国际业务部', 5, 'Y', NULL, NULL, 'admin', 'administrator', 'admin', '04', 
'120000373', NULL, NULL, NULL, '20100824', 'admin', NULL, NULL);
INSERT INTO zdbranch(BranchInnerCode, BranchCode, ParentInnerCode, Type, OrderFlag, Name, TreeLevel, IsLeaf, Phone, Fax, Manager, Leader1, 
Leader2, Prop1, Prop2, Prop3, Prop4, Memo, AddTime, AddUser, ModifyTime, ModifyUser)
  VALUES('120000095120000135120000367120000368120000374', '2120999103119017E', '120000095120000135120000367120000368', '0', 373, '海河支行计划财务部', 5, 'Y', NULL, NULL, 'admin', 'administrator', 'admin', '04', 
'120000374', NULL, NULL, NULL, '20100824', 'admin', NULL, NULL);
INSERT INTO zdbranch(BranchInnerCode, BranchCode, ParentInnerCode, Type, OrderFlag, Name, TreeLevel, IsLeaf, Phone, Fax, Manager, Leader1, 
Leader2, Prop1, Prop2, Prop3, Prop4, Memo, AddTime, AddUser, ModifyTime, ModifyUser)
  VALUES('120000095120000135120000367120000368120000375', '2120999103119018C', '120000095120000135120000367120000368', '0', 374, '海河支行监督保障部', 5, 'Y', NULL, NULL, 'admin', 'administrator', 'admin', '04', 
'120000375', NULL, NULL, NULL, '20100824', 'admin', NULL, NULL);
INSERT INTO zdbranch(BranchInnerCode, BranchCode, ParentInnerCode, Type, OrderFlag, Name, TreeLevel, IsLeaf, Phone, Fax, Manager, Leader1, 
Leader2, Prop1, Prop2, Prop3, Prop4, Memo, AddTime, AddUser, ModifyTime, ModifyUser)
  VALUES('120000095120000135120000367120000368120000376', '2120999103119014K', '120000095120000135120000367120000368', '0', 375, '海河支行离退休人员', 5, 'Y', NULL, NULL, 'admin', 'administrator', 'admin', '04', '120000376', NULL, NULL, NULL, 
'20100824', 'admin', NULL, NULL);
INSERT INTO zdbranch(BranchInnerCode, BranchCode, ParentInnerCode, Type, OrderFlag, Name, TreeLevel, IsLeaf, Phone, Fax, Manager, Leader1, 

Leader2, Prop1, Prop2, Prop3, Prop4, Memo, AddTime, AddUser, ModifyTime, ModifyUser)
  VALUES('120000095120000135120000367120000368120000377', '2120999103119022L', '120000095120000135120000367120000368', '0', 376, '海河支行信贷管理部', 5, 'Y', NULL, NULL, 'admin', 'administrator', 'admin', '04', 
'120000377', NULL, NULL, NULL, '20100824', 'admin', NULL, NULL);
INSERT INTO zdbranch(BranchInnerCode, BranchCode, ParentInnerCode, Type, OrderFlag, Name, TreeLevel, IsLeaf, Phone, Fax, Manager, Leader1, 

Leader2, Prop1, Prop2, Prop3, Prop4, Memo, AddTime, AddUser, ModifyTime, ModifyUser)
  VALUES('120000095120000135120000367120000368120000378', '2120999103119013M', '120000095120000135120000367120000368', '0', 377, '海河支行业务拓展部', 5, 'Y', NULL, NULL, 'admin', 'administrator', 'admin', '04', 
'120000378', NULL, NULL, NULL, '20100824', 'admin', NULL, NULL);
INSERT INTO zdbranch(BranchInnerCode, BranchCode, ParentInnerCode, Type, OrderFlag, Name, TreeLevel, IsLeaf, Phone, Fax, Manager, Leader1, 

Leader2, Prop1, Prop2, Prop3, Prop4, Memo, AddTime, AddUser, ModifyTime, ModifyUser)
  VALUES('120000095120000135120000367120000368120000379', '2120999103119016G', '120000095120000135120000367120000368', '0', 378, '海河支行中间业务部', 5, 'Y', NULL, NULL, 'admin', 'administrator', 'admin', '04', 
'120000379', NULL, NULL, NULL, '20100824', 'admin', NULL, NULL);
INSERT INTO zdbranch(BranchInnerCode, BranchCode, ParentInnerCode, Type, OrderFlag, Name, TreeLevel, IsLeaf, Phone, Fax, Manager, Leader1, 

Leader2, Prop1, Prop2, Prop3, Prop4, Memo, AddTime, AddUser, ModifyTime, ModifyUser)
  VALUES('120000095120000135120000367120000380', '2120999103125000E', '120000095120000135120000367', '0', 379, '金耀支行', 4, 'Y', NULL, NULL, 'admin', 'administrator', 'admin', '06', '120000380', 
NULL, NULL, NULL, '20100824', 'admin', NULL, NULL);
INSERT INTO zdbranch(BranchInnerCode, BranchCode, ParentInnerCode, Type, OrderFlag, Name, TreeLevel, IsLeaf, Phone, Fax, Manager, Leader1, 

Leader2, Prop1, Prop2, Prop3, Prop4, Memo, AddTime, AddUser, ModifyTime, ModifyUser)
  VALUES('120000095120000135120000367120000381', '2120999103117000A', '120000095120000135120000367', '0', 380, '丰盈支行', 4, 'Y', NULL, NULL, 'admin', 'administrator', 'admin', '06', '120000381', 
NULL, NULL, NULL, '20100824', 'admin', NULL, NULL);
INSERT INTO zdbranch(BranchInnerCode, BranchCode, ParentInnerCode, Type, OrderFlag, Name, TreeLevel, IsLeaf, Phone, Fax, Manager, Leader1, 

Leader2, Prop1, Prop2, Prop3, Prop4, Memo, AddTime, AddUser, ModifyTime, ModifyUser)
  VALUES('120000095120000135120000367120000382', '21209991031260009', '120000095120000135120000367', '0', 381, '永安道支行', 4, 'Y', NULL, NULL, 'admin', 'administrator', 'admin', '06', '120000382', 
NULL, NULL, NULL, '20100824', 'admin', NULL, NULL);
INSERT INTO zdbranch(BranchInnerCode, BranchCode, ParentInnerCode, Type, OrderFlag, Name, TreeLevel, IsLeaf, Phone, Fax, Manager, Leader1, 

Leader2, Prop1, Prop2, Prop3, Prop4, Memo, AddTime, AddUser, ModifyTime, ModifyUser)
  VALUES('120000095120000135120000367120000383', '21209991031180005', '120000095120000135120000367', '0', 382, '海河支行广开中街分理处', 4, 'Y', NULL, NULL, 'admin', 'administrator', 'admin', 
'06', '120000383', NULL, NULL, NULL, '20100824', 'admin', NULL, NULL);
INSERT INTO zdbranch(BranchInnerCode, BranchCode, ParentInnerCode, Type, OrderFlag, Name, TreeLevel, IsLeaf, Phone, Fax, Manager, Leader1, 

Leader2, Prop1, Prop2, Prop3, Prop4, Memo, AddTime, AddUser, ModifyTime, ModifyUser)
  VALUES('120000095120000135120000367120000384', '2120999103123000O', '120000095120000135120000367', '0', 383, '海河支行胜利路分理处', 4, 'Y', NULL, NULL, 'admin', 'administrator', 'admin', 
'06', '120000384', NULL, NULL, NULL, '20100824', 'admin', NULL, NULL);
INSERT INTO zdbranch(BranchInnerCode, BranchCode, ParentInnerCode, Type, OrderFlag, Name, TreeLevel, IsLeaf, Phone, Fax, Manager, Leader1, 

Leader2, Prop1, Prop2, Prop3, Prop4, Memo, AddTime, AddUser, ModifyTime, ModifyUser)
  VALUES('120000095120000135120000367120000385', '21209991031200008', '120000095120000135120000367', '0', 384, '海河支行核三院储蓄所', 4, 'Y', NULL, NULL, 'admin', 'administrator', 'admin', 
'10', '120000385', NULL, NULL, NULL, '20100824', 'admin', NULL, NULL);
INSERT INTO zdbranch(BranchInnerCode, BranchCode, ParentInnerCode, Type, OrderFlag, Name, TreeLevel, IsLeaf, Phone, Fax, Manager, Leader1, 

Leader2, Prop1, Prop2, Prop3, Prop4, Memo, AddTime, AddUser, ModifyTime, ModifyUser)
  VALUES('120000095120000135120000367120000386', '21209991031270004', '120000095120000135120000367', '0', 385, '太阳城支行', 4, 'Y', NULL, NULL, 'admin', 'administrator', 'admin', '06', '120000386', 
NULL, NULL, NULL, '20100824', 'admin', NULL, NULL);
INSERT INTO zdbranch(BranchInnerCode, BranchCode, ParentInnerCode, Type, OrderFlag, Name, TreeLevel, IsLeaf, Phone, Fax, Manager, Leader1, 

Leader2, Prop1, Prop2, Prop3, Prop4, Memo, AddTime, AddUser, ModifyTime, ModifyUser)
  VALUES('120000095120000387', '2120999113000000I', '120000095', '0', 386, '东丽区支行', 2, 'Y', NULL, NULL, 'admin', 'administrator', 'admin', '04', '120000387', 
NULL, NULL, NULL, '20100824', 'admin', NULL, NULL);
INSERT INTO zdbranch(BranchInnerCode, BranchCode, ParentInnerCode, Type, OrderFlag, Name, TreeLevel, IsLeaf, Phone, Fax, Manager, Leader1, 

Leader2, Prop1, Prop2, Prop3, Prop4, Memo, AddTime, AddUser, ModifyTime, ModifyUser)
  VALUES('120000095120000387120000388', '21209991131010006', '120000095120000387', '0', 387, '新立村支行', 3, 'Y', NULL, NULL, 'admin', 'administrator', 'admin', '06', '120000388', 
NULL, NULL, NULL, '20100824', 'admin', NULL, NULL);
INSERT INTO zdbranch(BranchInnerCode, BranchCode, ParentInnerCode, Type, OrderFlag, Name, TreeLevel, IsLeaf, Phone, Fax, Manager, Leader1, 
Leader2, Prop1, Prop2, Prop3, Prop4, Memo, AddTime, AddUser, ModifyTime, ModifyUser)
  VALUES('120000095120000387120000389', '21209991131160006', '120000095120000387', '0', 388, '东丽区津塘路分理处', 3, 'Y', NULL, NULL, 'admin', 'administrator', 'admin', 
'06', '120000389', NULL, NULL, NULL, '20100824', 'admin', NULL, NULL);
INSERT INTO zdbranch(BranchInnerCode, BranchCode, ParentInnerCode, Type, OrderFlag, Name, TreeLevel, IsLeaf, Phone, Fax, Manager, Leader1, 

Leader2, Prop1, Prop2, Prop3, Prop4, Memo, AddTime, AddUser, ModifyTime, ModifyUser)
  VALUES('120000095120000387120000390', '21209991131170001', '120000095120000387', '0', 389, '东丽支行招远路分理处', 3, 'Y', NULL, NULL, 'admin', 'administrator', 'admin', 

'06', '120000390', NULL, NULL, NULL, 

'20100824', 'admin', NULL, NULL)
;
INSERT INTO zdbranch(BranchInnerCode, BranchCode, ParentInnerCode, Type, OrderFlag, Name, TreeLevel, IsLeaf, Phone, Fax, Manager, Leader1, 

Leader2, Prop1, Prop2, Prop3, Prop4, Memo, AddTime, AddUser, ModifyTime, ModifyUser)
  VALUES('120000095120000387120000388120000391', '2120999113101104P', '120000095120000387120000388', '0', 390, '东丽支行信号厂储蓄所', 4, 'Y', NULL, NULL, 'admin', 'administrator', 'admin', 

'10', '120000391', NULL, NULL, 

NULL, '20100824', 'admin', NULL, NULL)
;
INSERT INTO zdbranch(BranchInnerCode, BranchCode, ParentInnerCode, Type, OrderFlag, Name, TreeLevel, IsLeaf, Phone, Fax, Manager, Leader1, 

Leader2, Prop1, Prop2, Prop3, Prop4, Memo, AddTime, AddUser, ModifyTime, ModifyUser)
  VALUES('120000095120000387120000392', '21209991131020001', '120000095120000387', '0', 391, '军粮城支行', 3, 'Y', NULL, NULL, 'admin', 'administrator', 'admin', '06', '120000392', 

NULL, NULL, NULL, 

'20100824', 'admin', NULL, NULL)
;
INSERT INTO zdbranch(BranchInnerCode, BranchCode, ParentInnerCode, Type, OrderFlag, Name, TreeLevel, IsLeaf, Phone, Fax, Manager, Leader1, 

Leader2, Prop1, Prop2, Prop3, Prop4, Memo, AddTime, AddUser, ModifyTime, ModifyUser)
  VALUES('120000095120000387120000392120000393', '2120999113102101Q', '120000095120000387120000392', '0', 392, '东丽支行苗二储蓄所', 4, 'Y', NULL, NULL, 'admin', 'administrator', 'admin', 

'10', '120000393', NULL, NULL, NULL, 

'20100824', 'admin', NULL, NULL)
;
INSERT INTO zdbranch(BranchInnerCode, BranchCode, ParentInnerCode, Type, OrderFlag, Name, TreeLevel, IsLeaf, Phone, Fax, Manager, Leader1, 

Leader2, Prop1, Prop2, Prop3, Prop4, Memo, AddTime, AddUser, ModifyTime, ModifyUser)
  VALUES('120000095120000387120000392120000394', '2120999113102102O', '120000095120000387120000392', '0', 393, '东丽支行苗街储蓄所', 4, 'Y', NULL, NULL, 'admin', 'administrator', 'admin', 

'10', '120000394', NULL, NULL, NULL, 

'20100824', 'admin', NULL, NULL)
;
INSERT INTO zdbranch(BranchInnerCode, BranchCode, ParentInnerCode, Type, OrderFlag, Name, TreeLevel, IsLeaf, Phone, Fax, Manager, Leader1, 

Leader2, Prop1, Prop2, Prop3, Prop4, Memo, AddTime, AddUser, ModifyTime, ModifyUser)
  VALUES('120000095120000387120000392120000395', '2120999113102103M', '120000095120000387120000392', '0', 394, '东丽支行二]村储蓄所', 4, 'Y', NULL, NULL, 'admin', 'administrator', 'admin', 

'10', '120000395', NULL, NULL, NULL, 

'20100824', 'admin', NULL, NULL)
;
INSERT INTO zdbranch(BranchInnerCode, BranchCode, ParentInnerCode, Type, OrderFlag, Name, TreeLevel, IsLeaf, Phone, Fax, Manager, Leader1, 

Leader2, Prop1, Prop2, Prop3, Prop4, Memo, AddTime, AddUser, ModifyTime, ModifyUser)
  VALUES('120000095120000387120000396', '2120999113119000M', '120000095120000387', '0', 395, '东丽支行福东里分理处', 3, 'Y', NULL, NULL, 'admin', 'administrator', 'admin', 

'06', '120000396', NULL, NULL, NULL, 

'20100824', 'admin', NULL, NULL)
;
INSERT INTO zdbranch(BranchInnerCode, BranchCode, ParentInnerCode, Type, OrderFlag, Name, TreeLevel, IsLeaf, Phone, Fax, Manager, Leader1, 

Leader2, Prop1, Prop2, Prop3, Prop4, Memo, AddTime, AddUser, ModifyTime, ModifyUser)
  VALUES('120000095120000387120000392120000397', '2120999113102105I', '120000095120000387120000392', '0', 396, '东丽区军粮城储蓄所', 4, 'Y', NULL, NULL, 'admin', 'administrator', 'admin', 

'10', '120000397', NULL, NULL, NULL, 

'20100824', 'admin', NULL, NULL)
;
INSERT INTO zdbranch(BranchInnerCode, BranchCode, ParentInnerCode, Type, OrderFlag, Name, TreeLevel, IsLeaf, Phone, Fax, Manager, Leader1, 

Leader2, Prop1, Prop2, Prop3, Prop4, Memo, AddTime, AddUser, ModifyTime, ModifyUser)
  VALUES('120000095120000387120000398', '2120999113103000R', '120000095120000387', '0', 397, '梅江支行', 3, 'Y', NULL, NULL, 'admin', 'administrator', 'admin', '06', '120000398', 
NULL, NULL, NULL, '20100824', 'admin', NULL, NULL);
INSERT INTO zdbranch(BranchInnerCode, BranchCode, ParentInnerCode, Type, OrderFlag, Name, TreeLevel, IsLeaf, Phone, Fax, Manager, Leader1, 

Leader2, Prop1, Prop2, Prop3, Prop4, Memo, AddTime, AddUser, ModifyTime, ModifyUser)
  VALUES('120000095120000387120000398120000399', '2120999113103101L', '120000095120000387120000398', '0', 398, '东丽支行李明庄储蓄所', 4, 'Y', NULL, NULL, 'admin', 'administrator', 'admin', 

'10', '120000399', NULL, NULL, 

NULL, '20100824', 'admin', NULL, NULL)
;
INSERT INTO zdbranch(BranchInnerCode, BranchCode, ParentInnerCode, Type, OrderFlag, Name, TreeLevel, IsLeaf, Phone, Fax, Manager, Leader1, 

Leader2, Prop1, Prop2, Prop3, Prop4, Memo, AddTime, AddUser, ModifyTime, ModifyUser)
  VALUES('120000095120000387120000400', '2120999113120000U', '120000095120000387', '0', 399, '东丽区华明分理处', 3, 'Y', NULL, NULL, 'admin', 'administrator', 'admin', '06', 

'120000400', NULL, NULL, NULL, 

'20100824', 'admin', NULL, NULL)
;
INSERT INTO zdbranch(BranchInnerCode, BranchCode, ParentInnerCode, Type, OrderFlag, Name, TreeLevel, IsLeaf, Phone, Fax, Manager, Leader1, 

Leader2, Prop1, Prop2, Prop3, Prop4, Memo, AddTime, AddUser, ModifyTime, ModifyUser)
  VALUES('120000095120000387120000401', '2120999113104000M', '120000095120000387', '0', 400, '大毕庄支行', 3, 'Y', NULL, NULL, 'admin', 'administrator', 'admin', '06', '120000401', 

NULL, NULL, NULL, 

'20100824', 'admin', NULL, NULL)
;
INSERT INTO zdbranch(BranchInnerCode, BranchCode, ParentInnerCode, Type, OrderFlag, Name, TreeLevel, IsLeaf, Phone, Fax, Manager, Leader1, 

Leader2, Prop1, Prop2, Prop3, Prop4, Memo, AddTime, AddUser, ModifyTime, ModifyUser)
  VALUES('120000095120000387120000402', '2120999113121000P', '120000095120000387', '0', 401, '东丽支行金钟路分理处', 3, 'Y', NULL, NULL, 'admin', 'administrator', 'admin', 

'06', '120000402', NULL, NULL, NULL, 

'20100824', 'admin', NULL, NULL)
;
INSERT INTO zdbranch(BranchInnerCode, BranchCode, ParentInnerCode, Type, OrderFlag, Name, TreeLevel, IsLeaf, Phone, Fax, Manager, Leader1, 

Leader2, Prop1, Prop2, Prop3, Prop4, Memo, AddTime, AddUser, ModifyTime, ModifyUser)
  VALUES('120000095120000387120000403', '21209991131110000', '120000095120000387', '0', 402, '幸福道支行', 3, 'Y', NULL, NULL, 'admin', 'administrator', 'admin', '06', '120000403', 

NULL, NULL, NULL, '20100824', 'admin', NULL, NULL);
INSERT INTO zdbranch(BranchInnerCode, BranchCode, ParentInnerCode, Type, OrderFlag, Name, TreeLevel, IsLeaf, Phone, Fax, Manager, Leader1, 

Leader2, Prop1, Prop2, Prop3, Prop4, Memo, AddTime, AddUser, ModifyTime, ModifyUser)
  VALUES('120000095120000387120000404', '2120999113123000F', '120000095120000387', '0', 403, '东丽支行靖江路分理处', 3, 'Y', NULL, NULL, 'admin', 'administrator', 'admin', 

'06', '120000404', NULL, NULL, NULL, 

'20100824', 'admin', NULL, NULL)
;
INSERT INTO zdbranch(BranchInnerCode, BranchCode, ParentInnerCode, Type, OrderFlag, Name, TreeLevel, IsLeaf, Phone, Fax, Manager, Leader1, 

Leader2, Prop1, Prop2, Prop3, Prop4, Memo, AddTime, AddUser, ModifyTime, ModifyUser)
  VALUES('120000095120000387120000405', '2120999113105000H', '120000095120000387', '0', 404, '程林庄支行', 3, 'Y', NULL, NULL, 'admin', 'administrator', 'admin', '06', '120000405', 

NULL, NULL, NULL, 

'20100824', 'admin', NULL, NULL)
;
INSERT INTO zdbranch(BranchInnerCode, BranchCode, ParentInnerCode, Type, OrderFlag, Name, TreeLevel, IsLeaf, Phone, Fax, Manager, Leader1, 

Leader2, Prop1, Prop2, Prop3, Prop4, Memo, AddTime, AddUser, ModifyTime, ModifyUser)
  VALUES('120000095120000387120000406', '2120999113122000K', '120000095120000387', '0', 405, '晨阳道支行', 3, 'Y', NULL, NULL, 'admin', 'administrator', 'admin', '06', '120000406', 

NULL, NULL, NULL, '20100824', 'admin', NULL, NULL);
INSERT INTO zdbranch(BranchInnerCode, BranchCode, ParentInnerCode, Type, OrderFlag, Name, TreeLevel, IsLeaf, Phone, Fax, Manager, Leader1, 

Leader2, Prop1, Prop2, Prop3, Prop4, Memo, AddTime, AddUser, ModifyTime, ModifyUser)
  VALUES('120000095120000925120000407', '2120999127101000R', '120000095120000925', '0', 406, '保税分行新天地分理处', 3, 'Y', NULL, NULL, 'admin', 'administrator', 'admin', 

'06', '120000407', NULL, NULL, NULL, 

'20100824', 'admin', NULL, NULL)
;
INSERT INTO zdbranch(BranchInnerCode, BranchCode, ParentInnerCode, Type, OrderFlag, Name, TreeLevel, IsLeaf, Phone, Fax, Manager, Leader1, 

Leader2, Prop1, Prop2, Prop3, Prop4, Memo, AddTime, AddUser, ModifyTime, ModifyUser)
  VALUES('120000095120000387120000405120000408', '21209991131051037', '120000095120000387120000405', '0', 407, '东丽支行程林储蓄所', 4, 'Y', NULL, NULL, 'admin', 'administrator', 'admin', '10', 

'120000408', NULL, NULL, NULL, 

'20100824', 'admin', NULL, NULL)
;
INSERT INTO zdbranch(BranchInnerCode, BranchCode, ParentInnerCode, Type, OrderFlag, Name, TreeLevel, IsLeaf, Phone, Fax, Manager, Leader1, 

Leader2, Prop1, Prop2, Prop3, Prop4, Memo, AddTime, AddUser, ModifyTime, ModifyUser)
  VALUES('120000095120000387120000409', '2120999113106000C', '120000095120000387', '0', 408, '无瑕街支行', 3, 'Y', NULL, NULL, 'admin', 'administrator', 'admin', '06', '120000409', 

NULL, NULL, NULL, 

'20100824', 'admin', NULL, NULL)
;
INSERT INTO zdbranch(BranchInnerCode, BranchCode, ParentInnerCode, Type, OrderFlag, Name, TreeLevel, IsLeaf, Phone, Fax, Manager, Leader1, 

Leader2, Prop1, Prop2, Prop3, Prop4, Memo, AddTime, AddUser, ModifyTime, ModifyUser)
  VALUES('120000095120000102120000410', '21209991131061016', '120000095120000102', '0', 409, '东丽支行津塘路储蓄所', 5, 'Y', NULL, NULL, 'admin', 'administrator', 'admin', 

'10', '120000410', NULL, NULL, 

NULL, '20100824', 'admin', NULL, NULL)
;
INSERT INTO zdbranch(BranchInnerCode, BranchCode, ParentInnerCode, Type, OrderFlag, Name, TreeLevel, IsLeaf, Phone, Fax, Manager, Leader1, 

Leader2, Prop1, Prop2, Prop3, Prop4, Memo, AddTime, AddUser, ModifyTime, ModifyUser)
  VALUES('120000095120000387120000411', '21209991131070007', '120000095120000387', '0', 410, '津东支行', 3, 'Y', NULL, NULL, 'admin', 'administrator', 'admin', '06', '120000411', 

NULL, NULL, NULL, '20100824', 'admin', NULL, NULL);
INSERT INTO zdbranch(BranchInnerCode, BranchCode, ParentInnerCode, Type, OrderFlag, Name, TreeLevel, IsLeaf, Phone, Fax, Manager, Leader1, 

Leader2, Prop1, Prop2, Prop3, Prop4, Memo, AddTime, AddUser, ModifyTime, ModifyUser)
  VALUES('120000095120000387120000412', '21209991131080002', '120000095120000387', '0', 411, '东丽支行营业部', 3, 'Y', NULL, NULL, 'admin', 'administrator', 'admin', '06', 

'120000412', NULL, NULL, NULL, 

'20100824', 'admin', NULL, NULL)
;
INSERT INTO zdbranch(BranchInnerCode, BranchCode, ParentInnerCode, Type, OrderFlag, Name, TreeLevel, IsLeaf, Phone, Fax, Manager, Leader1, 

Leader2, Prop1, Prop2, Prop3, Prop4, Memo, AddTime, AddUser, ModifyTime, ModifyUser)
  VALUES('120000095120000387120000413', '2120999113109000S', '120000095120000387', '0', 412, '东丽湖支行', 3, 'Y', NULL, NULL, 'admin', 'administrator', 'admin', '06', '120000413', 

NULL, NULL, NULL, 

'20100824', 'admin', NULL, NULL)
;
INSERT INTO zdbranch(BranchInnerCode, BranchCode, ParentInnerCode, Type, OrderFlag, Name, TreeLevel, IsLeaf, Phone, Fax, Manager, Leader1, 

Leader2, Prop1, Prop2, Prop3, Prop4, Memo, AddTime, AddUser, ModifyTime, ModifyUser)
  VALUES('120000095120000387120000413120000414', '2120999113109101M', '120000095120000387120000413', '0', 413, '东丽支行赤土镇储蓄所', 4, 'Y', NULL, NULL, 'admin', 'administrator', 'admin', 

'10', '120000414', NULL, NULL, 

NULL, '20100824', 'admin', NULL, NULL)
;
INSERT INTO zdbranch(BranchInnerCode, BranchCode, ParentInnerCode, Type, OrderFlag, Name, TreeLevel, IsLeaf, Phone, Fax, Manager, Leader1, 

Leader2, Prop1, Prop2, Prop3, Prop4, Memo, AddTime, AddUser, ModifyTime, ModifyUser)
  VALUES('120000095120000387120000413120000415', '2120999113109102K', '120000095120000387120000413', '0', 414, '东丽支行赤海路储蓄所', 4, 'Y', NULL, NULL, 'admin', 'administrator', 'admin', 

'10', '120000415', NULL, NULL, 

NULL, '20100824', 'admin', NULL, NULL)
;
INSERT INTO zdbranch(BranchInnerCode, BranchCode, ParentInnerCode, Type, OrderFlag, Name, TreeLevel, IsLeaf, Phone, Fax, Manager, Leader1, 

Leader2, Prop1, Prop2, Prop3, Prop4, Memo, AddTime, AddUser, ModifyTime, ModifyUser)
  VALUES('120000095120000387120000416', '21209991131100005', '120000095120000387', '0', 415, '东丽开发区支行', 3, 'Y', NULL, NULL, 'admin', 'administrator', 'admin', '06', 

'120000416', NULL, NULL, NULL, 

'20100824', 'admin', NULL, NULL)
;
INSERT INTO zdbranch(BranchInnerCode, BranchCode, ParentInnerCode, Type, OrderFlag, Name, TreeLevel, IsLeaf, Phone, Fax, Manager, Leader1, 

Leader2, Prop1, Prop2, Prop3, Prop4, Memo, AddTime, AddUser, ModifyTime, ModifyUser)
  VALUES('120000095120000387120000417', '2120999113112000Q', '120000095120000387', '0', 416, '东丽支行万东路营业所', 3, 'Y', NULL, NULL, 'admin', 'administrator', 'admin', 

'06', '120000417', NULL, NULL, NULL, 

'20100824', 'admin', NULL, NULL)
;
INSERT INTO zdbranch(BranchInnerCode, BranchCode, ParentInnerCode, Type, OrderFlag, Name, TreeLevel, IsLeaf, Phone, Fax, Manager, Leader1, 

Leader2, Prop1, Prop2, Prop3, Prop4, Memo, AddTime, AddUser, ModifyTime, ModifyUser)
  VALUES('120000095120000387120000418', '2120999113113000L', '120000095120000387', '0', 417, '金钟河大街支行', 3, 'Y', NULL, NULL, 'admin', 'administrator', 'admin', '06', 

'120000418', NULL, NULL, NULL, 

'20100824', 'admin', NULL, NULL)
;
INSERT INTO zdbranch(BranchInnerCode, BranchCode, ParentInnerCode, Type, OrderFlag, Name, TreeLevel, IsLeaf, Phone, Fax, Manager, Leader1, 

Leader2, Prop1, Prop2, Prop3, Prop4, Memo, AddTime, AddUser, ModifyTime, ModifyUser)
  VALUES('120000095120000387120000419', '2120999113001000D', '120000095120000387', '0', 418, '东丽区支行机关', 3, 'Y', NULL, NULL, 'admin', 'administrator', 'admin', '04', 

'120000419', NULL, NULL, NULL, 

'20100824', 'admin', NULL, NULL)
;
INSERT INTO zdbranch(BranchInnerCode, BranchCode, ParentInnerCode, Type, OrderFlag, Name, TreeLevel, IsLeaf, Phone, Fax, Manager, Leader1, 

Leader2, Prop1, Prop2, Prop3, Prop4, Memo, AddTime, AddUser, ModifyTime, ModifyUser)
  VALUES('120000095120000387120000419120000420', '21209991130010061', '120000095120000387120000419', '0', 419, '东丽支行其他', 4, 'Y', NULL, NULL, 'admin', 'administrator', 'admin', '04', '120000420', 

NULL, NULL, NULL, 

'20100824', 'admin', NULL, NULL)
;
INSERT INTO zdbranch(BranchInnerCode, BranchCode, ParentInnerCode, Type, OrderFlag, Name, TreeLevel, IsLeaf, Phone, Fax, Manager, Leader1, 

Leader2, Prop1, Prop2, Prop3, Prop4, Memo, AddTime, AddUser, ModifyTime, ModifyUser)
  VALUES('120000095120000387120000419120000421', '21209991130010037', '120000095120000387120000419', '0', 420, '东丽支行综合管理部', 4, 'Y', NULL, NULL, 'admin', 'administrator', 'admin', '04', 

'120000421', NULL, NULL, NULL, 

'20100824', 'admin', NULL, NULL)
;
INSERT INTO zdbranch(BranchInnerCode, BranchCode, ParentInnerCode, Type, OrderFlag, Name, TreeLevel, IsLeaf, Phone, Fax, Manager, Leader1, 

Leader2, Prop1, Prop2, Prop3, Prop4, Memo, AddTime, AddUser, ModifyTime, ModifyUser)
  VALUES('120000095120000387120000419120000422', '21209991130010045', '120000095120000387120000419', '0', 421, '东丽支行行长室', 4, 'Y', NULL, NULL, 'admin', 'administrator', 'admin', '04', 

'120000422', NULL, NULL, NULL, 

'20100824', 'admin', NULL, NULL)
;
INSERT INTO zdbranch(BranchInnerCode, BranchCode, ParentInnerCode, Type, OrderFlag, Name, TreeLevel, IsLeaf, Phone, Fax, Manager, Leader1, 

Leader2, Prop1, Prop2, Prop3, Prop4, Memo, AddTime, AddUser, ModifyTime, ModifyUser)
  VALUES('120000095120000387120000419120000423', '21209991130010029', '120000095120000387120000419', '0', 422, '东丽支行离退人员', 4, 'Y', NULL, NULL, 'admin', 'administrator', 'admin', '04', 

'120000423', NULL, NULL, NULL, 

'20100824', 'admin', NULL, NULL)
;
INSERT INTO zdbranch(BranchInnerCode, BranchCode, ParentInnerCode, Type, OrderFlag, Name, TreeLevel, IsLeaf, Phone, Fax, Manager, Leader1, 

Leader2, Prop1, Prop2, Prop3, Prop4, Memo, AddTime, AddUser, ModifyTime, ModifyUser)
  VALUES('120000095120000387120000419120000424', '21209991130010053', '120000095120000387120000419', '0', 423, '东丽支行内退人员', 4, 'Y', NULL, NULL, 'admin', 'administrator', 'admin', '04', 

'120000424', NULL, NULL, NULL, 

'20100824', 'admin', NULL, NULL)
;
INSERT INTO zdbranch(BranchInnerCode, BranchCode, ParentInnerCode, Type, OrderFlag, Name, TreeLevel, IsLeaf, Phone, Fax, Manager, Leader1, 

Leader2, Prop1, Prop2, Prop3, Prop4, Memo, AddTime, AddUser, ModifyTime, ModifyUser)
  VALUES('120000095120000387120000419120000425', '2120999113001007U', '120000095120000387120000419', '0', 424, '东丽支行保险代理部', 4, 'Y', NULL, NULL, 'admin', 'administrator', 'admin', '04', 

'120000425', NULL, NULL, NULL, 

'20100824', 'admin', NULL, NULL)
;
INSERT INTO zdbranch(BranchInnerCode, BranchCode, ParentInnerCode, Type, OrderFlag, Name, TreeLevel, IsLeaf, Phone, Fax, Manager, Leader1, 

Leader2, Prop1, Prop2, Prop3, Prop4, Memo, AddTime, AddUser, ModifyTime, ModifyUser)
  VALUES('120000095120000387120000419120000426', '2120999113001008S', '120000095120000387120000419', '0', 425, '东丽支行风险资产部', 4, 'Y', NULL, NULL, 'admin', 'administrator', 'admin', '04', 

'120000426', NULL, NULL, NULL, 

'20100824', 'admin', NULL, NULL)
;
INSERT INTO zdbranch(BranchInnerCode, BranchCode, ParentInnerCode, Type, OrderFlag, Name, TreeLevel, IsLeaf, Phone, Fax, Manager, Leader1, 

Leader2, Prop1, Prop2, Prop3, Prop4, Memo, AddTime, AddUser, ModifyTime, ModifyUser)
  VALUES('120000095120000387120000419120000427', '2120999113001009Q', '120000095120000387120000419', '0', 426, '东丽区支行财会运营部', 4, 'Y', NULL, NULL, 'admin', 'administrator', 'admin', 

'04', '120000427', NULL, NULL, 

NULL, '20100824', 'admin', NULL, NULL)
;
INSERT INTO zdbranch(BranchInnerCode, BranchCode, ParentInnerCode, Type, OrderFlag, Name, TreeLevel, IsLeaf, Phone, Fax, Manager, Leader1, 

Leader2, Prop1, Prop2, Prop3, Prop4, Memo, AddTime, AddUser, ModifyTime, ModifyUser)
  VALUES('120000095120000387120000419120000428', '2120999113001010A', '120000095120000387120000419', '0', 427, '东丽区支行监察保卫/内控合规部', 4, 'Y', NULL, NULL, 'admin', 'administrator', 

'admin', '04', '120000428', NULL, 

NULL, NULL, '20100824', 'admin', NULL, NULL)
;
INSERT INTO zdbranch(BranchInnerCode, BranchCode, ParentInnerCode, Type, OrderFlag, Name, TreeLevel, IsLeaf, Phone, Fax, Manager, Leader1, 

Leader2, Prop1, Prop2, Prop3, Prop4, Memo, AddTime, AddUser, ModifyTime, ModifyUser)
  VALUES('120000095120000387120000419120000429', '21209991130010118', '120000095120000387120000419', '0', 428, '东丽支行人力资源部', 4, 'Y', NULL, NULL, 'admin', 'administrator', 'admin', '04', 

'120000429', NULL, NULL, NULL, 

'20100824', 'admin', NULL, NULL)
;
INSERT INTO zdbranch(BranchInnerCode, BranchCode, ParentInnerCode, Type, OrderFlag, Name, TreeLevel, IsLeaf, Phone, Fax, Manager, Leader1, 

Leader2, Prop1, Prop2, Prop3, Prop4, Memo, AddTime, AddUser, ModifyTime, ModifyUser)
  VALUES('120000095120000387120000419120000430', '21209991130010126', '120000095120000387120000419', '0', 429, '东丽支行信贷管理部', 4, 'Y', NULL, NULL, 'admin', 'administrator', 'admin', '04', 

'120000430', NULL, NULL, NULL, 

'20100824', 'admin', NULL, NULL)
;
INSERT INTO zdbranch(BranchInnerCode, BranchCode, ParentInnerCode, Type, OrderFlag, Name, TreeLevel, IsLeaf, Phone, Fax, Manager, Leader1, 

Leader2, Prop1, Prop2, Prop3, Prop4, Memo, AddTime, AddUser, ModifyTime, ModifyUser)
  VALUES('120000095120000387120000419120000431', '21209991130010134', '120000095120000387120000419', '0', 430, '东丽支行公司业务部', 4, 'Y', NULL, NULL, 'admin', 'administrator', 'admin', '04', 

'120000431', NULL, NULL, NULL, 

'20100824', 'admin', NULL, NULL)
;
INSERT INTO zdbranch(BranchInnerCode, BranchCode, ParentInnerCode, Type, OrderFlag, Name, TreeLevel, IsLeaf, Phone, Fax, Manager, Leader1, 

Leader2, Prop1, Prop2, Prop3, Prop4, Memo, AddTime, AddUser, ModifyTime, ModifyUser)
  VALUES('120000095120000387120000419120000432', '21209991130010142', '120000095120000387120000419', '0', 431, '东丽支行个人金融部', 4, 'Y', NULL, NULL, 'admin', 'administrator', 'admin', '04', 

'120000432', NULL, NULL, NULL, 

'20100824', 'admin', NULL, NULL)
;
INSERT INTO zdbranch(BranchInnerCode, BranchCode, ParentInnerCode, Type, OrderFlag, Name, TreeLevel, IsLeaf, Phone, Fax, Manager, Leader1, 

Leader2, Prop1, Prop2, Prop3, Prop4, Memo, AddTime, AddUser, ModifyTime, ModifyUser)
  VALUES('120000095120000387120000419120000433', '2120999113001015V', '120000095120000387120000419', '0', 432, '东丽支行内部分流人员', 4, 'Y', NULL, NULL, 'admin', 'administrator', 'admin', 

'04', '120000433', NULL, NULL, NULL, 

'20100824', 'admin', NULL, NULL)
;
INSERT INTO zdbranch(BranchInnerCode, BranchCode, ParentInnerCode, Type, OrderFlag, Name, TreeLevel, IsLeaf, Phone, Fax, Manager, Leader1, 

Leader2, Prop1, Prop2, Prop3, Prop4, Memo, AddTime, AddUser, ModifyTime, ModifyUser)
  VALUES('120000095120000387120000388120000434', '21209991131011010', '120000095120000387120000388', '0', 433, '东丽支行新立村储蓄所', 4, 'Y', NULL, NULL, 'admin', 'administrator', 'admin', 

'10', '120000434', NULL, NULL, 

NULL, '20100824', 'admin', NULL, NULL)
;
INSERT INTO zdbranch(BranchInnerCode, BranchCode, ParentInnerCode, Type, OrderFlag, Name, TreeLevel, IsLeaf, Phone, Fax, Manager, Leader1, 

Leader2, Prop1, Prop2, Prop3, Prop4, Memo, AddTime, AddUser, ModifyTime, ModifyUser)
  VALUES('120000095120000435', '2120999114000000A', '120000095', '0', 434, '西青区支行', 2, 'Y', NULL, NULL, 'admin', 'administrator', 'admin', '04', '120000435', 

NULL, NULL, NULL, 

'20100824', 'admin', NULL, NULL)
;
INSERT INTO zdbranch(BranchInnerCode, BranchCode, ParentInnerCode, Type, OrderFlag, Name, TreeLevel, IsLeaf, Phone, Fax, Manager, Leader1, 

Leader2, Prop1, Prop2, Prop3, Prop4, Memo, AddTime, AddUser, ModifyTime, ModifyUser)
  VALUES('120000095120000435120000436', '21209991140010005', '120000095120000435', '0', 435, '西青区支行机关', 3, 'Y', NULL, NULL, 'admin', 'administrator', 'admin', '04', 

'120000436', NULL, NULL, NULL, 

'20100824', 'admin', NULL, NULL)
;
INSERT INTO zdbranch(BranchInnerCode, BranchCode, ParentInnerCode, Type, OrderFlag, Name, TreeLevel, IsLeaf, Phone, Fax, Manager, Leader1, 

Leader2, Prop1, Prop2, Prop3, Prop4, Memo, AddTime, AddUser, ModifyTime, ModifyUser)
  VALUES('120000095120000435120000436120000437', '21209991140010021', '120000095120000435120000436', '0', 436, '西青支行其他', 4, 'Y', NULL, NULL, 'admin', 'administrator', 'admin', '04', '120000437', 

NULL, NULL, NULL, 

'20100824', 'admin', NULL, NULL)
;
INSERT INTO zdbranch(BranchInnerCode, BranchCode, ParentInnerCode, Type, OrderFlag, Name, TreeLevel, IsLeaf, Phone, Fax, Manager, Leader1, 

Leader2, Prop1, Prop2, Prop3, Prop4, Memo, AddTime, AddUser, ModifyTime, ModifyUser)
  VALUES('120000095120000435120000436120000438', '2120999114001003U', '120000095120000435120000436', '0', 437, '西青支行综合管理部', 4, 'Y', NULL, NULL, 'admin', 'administrator', 'admin', '04', 

'120000438', NULL, NULL, NULL, 

'20100824', 'admin', NULL, NULL)
;
INSERT INTO zdbranch(BranchInnerCode, BranchCode, ParentInnerCode, Type, OrderFlag, Name, TreeLevel, IsLeaf, Phone, Fax, Manager, Leader1, 

Leader2, Prop1, Prop2, Prop3, Prop4, Memo, AddTime, AddUser, ModifyTime, ModifyUser)
  VALUES('120000095120000435120000436120000439', '2120999114001004S', '120000095120000435120000436', '0', 438, '西青支行拓展部', 4, 'Y', NULL, NULL, 'admin', 'administrator', 'admin', '04', 

'120000439', NULL, NULL, NULL, 

'20100824', 'admin', NULL, NULL)
;
INSERT INTO zdbranch(BranchInnerCode, BranchCode, ParentInnerCode, Type, OrderFlag, Name, TreeLevel, IsLeaf, Phone, Fax, Manager, Leader1, 

Leader2, Prop1, Prop2, Prop3, Prop4, Memo, AddTime, AddUser, ModifyTime, ModifyUser)
  VALUES('120000095120000435120000436120000440', '2120999114001005Q', '120000095120000435120000436', '0', 439, '西青支行行长室', 4, 'Y', NULL, NULL, 'admin', 'administrator', 'admin', '04', 

'120000440', NULL, NULL, NULL, 

'20100824', 'admin', NULL, NULL)
;
INSERT INTO zdbranch(BranchInnerCode, BranchCode, ParentInnerCode, Type, OrderFlag, Name, TreeLevel, IsLeaf, Phone, Fax, Manager, Leader1, 

Leader2, Prop1, Prop2, Prop3, Prop4, Memo, AddTime, AddUser, ModifyTime, ModifyUser)
  VALUES('120000095120000435120000436120000441', '2120999114001006O', '120000095120000435120000436', '0', 440, '西青支行离退人员', 4, 'Y', NULL, NULL, 'admin', 'administrator', 'admin', '04', 

'120000441', NULL, NULL, NULL, 

'20100824', 'admin', NULL, NULL)
;
INSERT INTO zdbranch(BranchInnerCode, BranchCode, ParentInnerCode, Type, OrderFlag, Name, TreeLevel, IsLeaf, Phone, Fax, Manager, Leader1, 

Leader2, Prop1, Prop2, Prop3, Prop4, Memo, AddTime, AddUser, ModifyTime, ModifyUser)
  VALUES('120000095120000435120000436120000442', '2120999114001007M', '120000095120000435120000436', '0', 441, '西青支行内退人员', 4, 'Y', NULL, NULL, 'admin', 'administrator', 'admin', '04', 

'120000442', NULL, NULL, NULL, 

'20100824', 'admin', NULL, NULL)
;
INSERT INTO zdbranch(BranchInnerCode, BranchCode, ParentInnerCode, Type, OrderFlag, Name, TreeLevel, IsLeaf, Phone, Fax, Manager, Leader1, 

Leader2, Prop1, Prop2, Prop3, Prop4, Memo, AddTime, AddUser, ModifyTime, ModifyUser)
  VALUES('120000095120000435120000436120000443', '2120999114001008K', '120000095120000435120000436', '0', 442, '西青支行下岗分流', 4, 'Y', NULL, NULL, 'admin', 'administrator', 'admin', '04', 

'120000443', NULL, NULL, NULL, 

'20100824', 'admin', NULL, NULL)
;
INSERT INTO zdbranch(BranchInnerCode, BranchCode, ParentInnerCode, Type, OrderFlag, Name, TreeLevel, IsLeaf, Phone, Fax, Manager, Leader1, 

Leader2, Prop1, Prop2, Prop3, Prop4, Memo, AddTime, AddUser, ModifyTime, ModifyUser)
  VALUES('120000095120000435120000436120000444', '2120999114001009I', '120000095120000435120000436', '0', 443, '西青支行财会运营部', 4, 'Y', NULL, NULL, 'admin', 'administrator', 'admin', '04', 

'120000444', NULL, NULL, NULL, 

'20100824', 'admin', NULL, NULL)
;
INSERT INTO zdbranch(BranchInnerCode, BranchCode, ParentInnerCode, Type, OrderFlag, Name, TreeLevel, IsLeaf, Phone, Fax, Manager, Leader1, 

Leader2, Prop1, Prop2, Prop3, Prop4, Memo, AddTime, AddUser, ModifyTime, ModifyUser)
  VALUES('120000095120000435120000436120000445', '21209991140010102', '120000095120000435120000436', '0', 444, '西青支行监察保卫/内控合规部', 4, 'Y', NULL, NULL, 'admin', 'administrator', 

'admin', '04', '120000445', NULL, NULL, 

NULL, '20100824', 'admin', NULL, NULL)
;
INSERT INTO zdbranch(BranchInnerCode, BranchCode, ParentInnerCode, Type, OrderFlag, Name, TreeLevel, IsLeaf, Phone, Fax, Manager, Leader1, 

Leader2, Prop1, Prop2, Prop3, Prop4, Memo, AddTime, AddUser, ModifyTime, ModifyUser)
  VALUES('120000095120000435120000436120000446', '2120999114001011V', '120000095120000435120000436', '0', 445, '西青支行离退休人员', 4, 'Y', NULL, NULL, 'admin', 'administrator', 'admin', '04', 

'120000446', NULL, NULL, NULL, 

'20100824', 'admin', NULL, NULL)
;
INSERT INTO zdbranch(BranchInnerCode, BranchCode, ParentInnerCode, Type, OrderFlag, Name, TreeLevel, IsLeaf, Phone, Fax, Manager, Leader1, 

Leader2, Prop1, Prop2, Prop3, Prop4, Memo, AddTime, AddUser, ModifyTime, ModifyUser)
  VALUES('120000095120000435120000436120000447', '2120999114001012T', '120000095120000435120000436', '0', 446, '西青支行个人金融部', 4, 'Y', NULL, NULL, 'admin', 'administrator', 'admin', '04', 

'120000447', NULL, NULL, NULL, 

'20100824', 'admin', NULL, NULL)
;
INSERT INTO zdbranch(BranchInnerCode, BranchCode, ParentInnerCode, Type, OrderFlag, Name, TreeLevel, IsLeaf, Phone, Fax, Manager, Leader1, 

Leader2, Prop1, Prop2, Prop3, Prop4, Memo, AddTime, AddUser, ModifyTime, ModifyUser)
  VALUES('120000095120000435120000436120000448', '2120999114001013R', '120000095120000435120000436', '0', 447, '西青支行人力资源部', 4, 'Y', NULL, NULL, 'admin', 'administrator', 'admin', '04', 

'120000448', NULL, NULL, NULL, 

'20100824', 'admin', NULL, NULL)
;
INSERT INTO zdbranch(BranchInnerCode, BranchCode, ParentInnerCode, Type, OrderFlag, Name, TreeLevel, IsLeaf, Phone, Fax, Manager, Leader1, 

Leader2, Prop1, Prop2, Prop3, Prop4, Memo, AddTime, AddUser, ModifyTime, ModifyUser)
  VALUES('120000095120000435120000436120000449', '2120999114001014P', '120000095120000435120000436', '0', 448, '西青支行客户一部', 4, 'Y', NULL, NULL, 'admin', 'administrator', 'admin', '04', 

'120000449', NULL, NULL, NULL, 

'20100824', 'admin', NULL, NULL)
;
INSERT INTO zdbranch(BranchInnerCode, BranchCode, ParentInnerCode, Type, OrderFlag, Name, TreeLevel, IsLeaf, Phone, Fax, Manager, Leader1, 

Leader2, Prop1, Prop2, Prop3, Prop4, Memo, AddTime, AddUser, ModifyTime, ModifyUser)
  VALUES('120000095120000435120000436120000450', '2120999114001015N', '120000095120000435120000436', '0', 449, '西青支行信贷管理部', 4, 'Y', NULL, NULL, 'admin', 'administrator', 'admin', '04', 

'120000450', NULL, NULL, NULL, 

'20100824', 'admin', NULL, NULL)
;
INSERT INTO zdbranch(BranchInnerCode, BranchCode, ParentInnerCode, Type, OrderFlag, Name, TreeLevel, IsLeaf, Phone, Fax, Manager, Leader1, 

Leader2, Prop1, Prop2, Prop3, Prop4, Memo, AddTime, AddUser, ModifyTime, ModifyUser)
  VALUES('120000095120000435120000436120000451', '2120999114001016L', '120000095120000435120000436', '0', 450, '西青支行公司业务部', 4, 'Y', NULL, NULL, 'admin', 'administrator', 'admin', '04', 

'120000451', NULL, NULL, NULL, 

'20100824', 'admin', NULL, NULL)
;
INSERT INTO zdbranch(BranchInnerCode, BranchCode, ParentInnerCode, Type, OrderFlag, Name, TreeLevel, IsLeaf, Phone, Fax, Manager, Leader1, 

Leader2, Prop1, Prop2, Prop3, Prop4, Memo, AddTime, AddUser, ModifyTime, ModifyUser)
  VALUES('120000095120000435120000436120000452', '2120999114001017J', '120000095120000435120000436', '0', 451, '西青支行内部分流人员', 4, 'Y', NULL, NULL, 'admin', 'administrator', 'admin', 

'04', '120000452', NULL, NULL, NULL, 

'20100824', 'admin', NULL, NULL)
;
INSERT INTO zdbranch(BranchInnerCode, BranchCode, ParentInnerCode, Type, OrderFlag, Name, TreeLevel, IsLeaf, Phone, Fax, Manager, Leader1, 

Leader2, Prop1, Prop2, Prop3, Prop4, Memo, AddTime, AddUser, ModifyTime, ModifyUser)
  VALUES('120000095120000435120000453', '2120999114101000T', '120000095120000435', '0', 452, '李七庄支行', 3, 'Y', NULL, NULL, 'admin', 'administrator', 'admin', '06', '120000453', 

NULL, NULL, NULL, '20100824', 'admin', NULL, NULL);
INSERT INTO zdbranch(BranchInnerCode, BranchCode, ParentInnerCode, Type, OrderFlag, Name, TreeLevel, IsLeaf, Phone, Fax, Manager, Leader1, 

Leader2, Prop1, Prop2, Prop3, Prop4, Memo, AddTime, AddUser, ModifyTime, ModifyUser)
  VALUES('120000095120000435120000454', '2120999114102000O', '120000095120000435', '0', 453, '西青区津西支行', 3, 'Y', NULL, NULL, 'admin', 'administrator', 'admin', '06', 

'120000454', NULL, NULL, NULL, 

'20100824', 'admin', NULL, NULL)
;
INSERT INTO zdbranch(BranchInnerCode, BranchCode, ParentInnerCode, Type, OrderFlag, Name, TreeLevel, IsLeaf, Phone, Fax, Manager, Leader1, 

Leader2, Prop1, Prop2, Prop3, Prop4, Memo, AddTime, AddUser, ModifyTime, ModifyUser)
  VALUES('120000095120000435120000455', '2120999114103000J', '120000095120000435', '0', 454, '杨柳青支行', 3, 'Y', NULL, NULL, 'admin', 'administrator', 'admin', '06', '120000455', 

NULL, NULL, NULL, 

'20100824', 'admin', NULL, NULL)
;
INSERT INTO zdbranch(BranchInnerCode, BranchCode, ParentInnerCode, Type, OrderFlag, Name, TreeLevel, IsLeaf, Phone, Fax, Manager, Leader1, 

Leader2, Prop1, Prop2, Prop3, Prop4, Memo, AddTime, AddUser, ModifyTime, ModifyUser)
  VALUES('120000095120000435120000456', '2120999114104000E', '120000095120000435', '0', 455, '南河工业区支行', 3, 'Y', NULL, NULL, 'admin', 'administrator', 'admin', '06', 

'120000456', NULL, NULL, NULL, 

'20100824', 'admin', NULL, NULL)
;
INSERT INTO zdbranch(BranchInnerCode, BranchCode, ParentInnerCode, Type, OrderFlag, Name, TreeLevel, IsLeaf, Phone, Fax, Manager, Leader1, 

Leader2, Prop1, Prop2, Prop3, Prop4, Memo, AddTime, AddUser, ModifyTime, ModifyUser)
  VALUES('120000095120000435120000457', '21209991141050009', '120000095120000435', '0', 456, '阳光支行', 3, 'Y', NULL, NULL, 'admin', 'administrator', 'admin', '06', '120000457', 

NULL, NULL, NULL, '20100824', 'admin', NULL, NULL);
INSERT INTO zdbranch(BranchInnerCode, BranchCode, ParentInnerCode, Type, OrderFlag, Name, TreeLevel, IsLeaf, Phone, Fax, Manager, Leader1, 

Leader2, Prop1, Prop2, Prop3, Prop4, Memo, AddTime, AddUser, ModifyTime, ModifyUser)
  VALUES('120000095120000435120000458', '21209991141060004', '120000095120000435', '0', 457, '西青支行友谊路分理处', 3, 'Y', NULL, NULL, 'admin', 'administrator', 'admin', 

'06', '120000458', NULL, NULL, 

NULL, '20100824', 'admin', NULL, NULL)
;
INSERT INTO zdbranch(BranchInnerCode, BranchCode, ParentInnerCode, Type, OrderFlag, Name, TreeLevel, IsLeaf, Phone, Fax, Manager, Leader1, 

Leader2, Prop1, Prop2, Prop3, Prop4, Memo, AddTime, AddUser, ModifyTime, ModifyUser)
  VALUES('120000095120000435120000459', '2120999114107000U', '120000095120000435', '0', 458, '咸阳路支行', 3, 'Y', NULL, NULL, 'admin', 'administrator', 'admin', '06', '120000459', 

NULL, NULL, NULL, 

'20100824', 'admin', NULL, NULL)
;
INSERT INTO zdbranch(BranchInnerCode, BranchCode, ParentInnerCode, Type, OrderFlag, Name, TreeLevel, IsLeaf, Phone, Fax, Manager, Leader1, 

Leader2, Prop1, Prop2, Prop3, Prop4, Memo, AddTime, AddUser, ModifyTime, ModifyUser)
  VALUES('120000095120000435120000460', '2120999114108000P', '120000095120000435', '0', 459, '西青开发区支行', 3, 'Y', NULL, NULL, 'admin', 'administrator', 'admin', '06', 

'120000460', NULL, NULL, NULL, 

'20100824', 'admin', NULL, NULL)
;
INSERT INTO zdbranch(BranchInnerCode, BranchCode, ParentInnerCode, Type, OrderFlag, Name, TreeLevel, IsLeaf, Phone, Fax, Manager, Leader1, 

Leader2, Prop1, Prop2, Prop3, Prop4, Memo, AddTime, AddUser, ModifyTime, ModifyUser)
  VALUES('120000095120000435120000461', '2120999114109000K', '120000095120000435', '0', 460, '张家窝支行', 3, 'Y', NULL, NULL, 'admin', 'administrator', 'admin', '06', '120000461', 

NULL, NULL, NULL, 

'20100824', 'admin', NULL, NULL)
;
INSERT INTO zdbranch(BranchInnerCode, BranchCode, ParentInnerCode, Type, OrderFlag, Name, TreeLevel, IsLeaf, Phone, Fax, Manager, Leader1, 

Leader2, Prop1, Prop2, Prop3, Prop4, Memo, AddTime, AddUser, ModifyTime, ModifyUser)
  VALUES('120000095120000435120000462', '2120999114110000S', '120000095120000435', '0', 461, '体育中心支行', 3, 'Y', NULL, NULL, 'admin', 'administrator', 'admin', '06', '120000462', 

NULL, NULL, NULL, 

'20100824', 'admin', NULL, NULL)
;
INSERT INTO zdbranch(BranchInnerCode, BranchCode, ParentInnerCode, Type, OrderFlag, Name, TreeLevel, IsLeaf, Phone, Fax, Manager, Leader1, 

Leader2, Prop1, Prop2, Prop3, Prop4, Memo, AddTime, AddUser, ModifyTime, ModifyUser)
  VALUES('120000095120000435120000436120000463', '21209991140010013', '120000095120000435120000436', '0', 462, '西青支行营业部', 3, 'Y', NULL, NULL, 'admin', 'administrator', 'admin', '04', 

'120000463', NULL, NULL, NULL, 

'20100824', 'admin', NULL, NULL)
;
INSERT INTO zdbranch(BranchInnerCode, BranchCode, ParentInnerCode, Type, OrderFlag, Name, TreeLevel, IsLeaf, Phone, Fax, Manager, Leader1, 

Leader2, Prop1, Prop2, Prop3, Prop4, Memo, AddTime, AddUser, ModifyTime, ModifyUser)
  VALUES('120000095120000435120000464', '2120999114112000I', '120000095120000435', '0', 463, '华苑新区支行', 3, 'Y', NULL, NULL, 'admin', 'administrator', 'admin', '06', 

'120000464', NULL, NULL, NULL, 

'20100824', 'admin', NULL, NULL)
;
INSERT INTO zdbranch(BranchInnerCode, BranchCode, ParentInnerCode, Type, OrderFlag, Name, TreeLevel, IsLeaf, Phone, Fax, Manager, Leader1, 

Leader2, Prop1, Prop2, Prop3, Prop4, Memo, AddTime, AddUser, ModifyTime, ModifyUser)
  VALUES('120000095120000435120000465', '2120999114113000D', '120000095120000435', '0', 464, '西青支行黑牛城道储蓄所', 3, 'Y', NULL, NULL, 'admin', 'administrator', 'admin', 

'10', '120000465', NULL, NULL, 

NULL, '20100824', 'admin', NULL, NULL)
;
INSERT INTO zdbranch(BranchInnerCode, BranchCode, ParentInnerCode, Type, OrderFlag, Name, TreeLevel, IsLeaf, Phone, Fax, Manager, Leader1, 

Leader2, Prop1, Prop2, Prop3, Prop4, Memo, AddTime, AddUser, ModifyTime, ModifyUser)
  VALUES('120000095120000435120000466', '21209991141140008', '120000095120000435', '0', 465, '西青区王稳庄分理处', 3, 'Y', NULL, NULL, 'admin', 'administrator', 'admin', 

'06', '120000466', NULL, NULL, NULL, 

'20100824', 'admin', NULL, NULL)
;
INSERT INTO zdbranch(BranchInnerCode, BranchCode, ParentInnerCode, Type, OrderFlag, Name, TreeLevel, IsLeaf, Phone, Fax, Manager, Leader1, 

Leader2, Prop1, Prop2, Prop3, Prop4, Memo, AddTime, AddUser, ModifyTime, ModifyUser)
  VALUES('120000095120000435120000467', '21209991141150003', '120000095120000435', '0', 466, '西青支行气象台路分理处', 3, 'Y', NULL, NULL, 'admin', 'administrator', 'admin', 

'10', '120000467', NULL, NULL, 

NULL, '20100824', 'admin', NULL, NULL)
;
INSERT INTO zdbranch(BranchInnerCode, BranchCode, ParentInnerCode, Type, OrderFlag, Name, TreeLevel, IsLeaf, Phone, Fax, Manager, Leader1, 

Leader2, Prop1, Prop2, Prop3, Prop4, Memo, AddTime, AddUser, ModifyTime, ModifyUser)
  VALUES('120000095120000435120000454120000468', '2120999114102101I', '120000095120000435120000454', '0', 467, '西青支行津西储蓄所', 4, 'Y', NULL, NULL, 'admin', 'administrator', 'admin', 

'10', '120000468', NULL, NULL, NULL, 

'20100824', 'admin', NULL, NULL)
;
INSERT INTO zdbranch(BranchInnerCode, BranchCode, ParentInnerCode, Type, OrderFlag, Name, TreeLevel, IsLeaf, Phone, Fax, Manager, Leader1, 

Leader2, Prop1, Prop2, Prop3, Prop4, Memo, AddTime, AddUser, ModifyTime, ModifyUser)
  VALUES('120000095120000435120000469', '2120999114117000O', '120000095120000435', '0', 468, '西青区和平路分理处', 3, 'Y', NULL, NULL, 'admin', 'administrator', 'admin', 

'06', '120000469', NULL, NULL, NULL, 

'20100824', 'admin', NULL, NULL)
;
INSERT INTO zdbranch(BranchInnerCode, BranchCode, ParentInnerCode, Type, OrderFlag, Name, TreeLevel, IsLeaf, Phone, Fax, Manager, Leader1, 

Leader2, Prop1, Prop2, Prop3, Prop4, Memo, AddTime, AddUser, ModifyTime, ModifyUser)
  VALUES('120000095120000435120000470', '2120999114118000J', '120000095120000435', '0', 469, '西青支行杨柳青镇营业所', 3, 'Y', NULL, NULL, 'admin', 'administrator', 'admin', 

'10', '120000470', NULL, NULL, 

NULL, '20100824', 'admin', NULL, NULL)
;
INSERT INTO zdbranch(BranchInnerCode, BranchCode, ParentInnerCode, Type, OrderFlag, Name, TreeLevel, IsLeaf, Phone, Fax, Manager, Leader1, 

Leader2, Prop1, Prop2, Prop3, Prop4, Memo, AddTime, AddUser, ModifyTime, ModifyUser)
  VALUES('120000095120000435120000471', '2120999114119000E', '120000095120000435', '0', 470, '西青区南河营业所', 3, 'Y', NULL, NULL, 'admin', 'administrator', 'admin', '06', 

'120000471', NULL, NULL, NULL, 

'20100824', 'admin', NULL, NULL)
;
INSERT INTO zdbranch(BranchInnerCode, BranchCode, ParentInnerCode, Type, OrderFlag, Name, TreeLevel, IsLeaf, Phone, Fax, Manager, Leader1, 

Leader2, Prop1, Prop2, Prop3, Prop4, Memo, AddTime, AddUser, ModifyTime, ModifyUser)
  VALUES('120000095120000435120000472', '2120999114106101T', '120000095120000435', '0', 471, '西青支行光明路储蓄所', 3, 'Y', NULL, NULL, 'admin', 'administrator', 'admin', 

'10', '120000472', NULL, NULL, 

NULL, '20100824', 'admin', NULL, NULL)
;
INSERT INTO zdbranch(BranchInnerCode, BranchCode, ParentInnerCode, Type, OrderFlag, Name, TreeLevel, IsLeaf, Phone, Fax, Manager, Leader1, 

Leader2, Prop1, Prop2, Prop3, Prop4, Memo, AddTime, AddUser, ModifyTime, ModifyUser)
  VALUES('120000095120000435120000473', '2120999114120000M', '120000095120000435', '0', 472, '西青支行迎水道储蓄所', 3, 'Y', NULL, NULL, 'admin', 'administrator', 'admin', 

'10', '120000473', NULL, NULL, 

NULL, '20100824', 'admin', NULL, NULL)
;
INSERT INTO zdbranch(BranchInnerCode, BranchCode, ParentInnerCode, Type, OrderFlag, Name, TreeLevel, IsLeaf, Phone, Fax, Manager, Leader1, 

Leader2, Prop1, Prop2, Prop3, Prop4, Memo, AddTime, AddUser, ModifyTime, ModifyUser)
  VALUES('120000095120000435120000474', '21209991141240002', '120000095120000435', '0', 473, '西青区安华里分理处', 3, 'Y', NULL, NULL, 'admin', 'administrator', 'admin', 

'06', '120000474', NULL, NULL, NULL, 

'20100824', 'admin', NULL, NULL)
;
INSERT INTO zdbranch(BranchInnerCode, BranchCode, ParentInnerCode, Type, OrderFlag, Name, TreeLevel, IsLeaf, Phone, Fax, Manager, Leader1, 

Leader2, Prop1, Prop2, Prop3, Prop4, Memo, AddTime, AddUser, ModifyTime, ModifyUser)
  VALUES('120000095120000435120000475', '2120999114122000C', '120000095120000435', '0', 474, '西青区冬云里分理处', 3, 'Y', NULL, NULL, 'admin', 'administrator', 'admin', 

'06', '120000475', NULL, NULL, NULL, 

'20100824', 'admin', NULL, NULL)
;
INSERT INTO zdbranch(BranchInnerCode, BranchCode, ParentInnerCode, Type, OrderFlag, Name, TreeLevel, IsLeaf, Phone, Fax, Manager, Leader1, 

Leader2, Prop1, Prop2, Prop3, Prop4, Memo, AddTime, AddUser, ModifyTime, ModifyUser)
  VALUES('120000095120000435120000460120000476', '2120999114108101J', '120000095120000435120000460', '0', 475, '西青支行龙居花园储蓄所', 4, 'Y', NULL, NULL, 'admin', 'administrator', 'admin', 

'10', '120000476', NULL, NULL, 

NULL, '20100824', 'admin', NULL, NULL)
;
INSERT INTO zdbranch(BranchInnerCode, BranchCode, ParentInnerCode, Type, OrderFlag, Name, TreeLevel, IsLeaf, Phone, Fax, Manager, Leader1, 

Leader2, Prop1, Prop2, Prop3, Prop4, Memo, AddTime, AddUser, ModifyTime, ModifyUser)
  VALUES('120000095120000435120000477', '21209991141230007', '120000095120000435', '0', 476, '西青支行体育馆分理处', 3, 'Y', NULL, NULL, 'admin', 'administrator', 'admin', 

'06', '120000477', NULL, NULL, 

NULL, '20100824', 'admin', NULL, NULL)
;
INSERT INTO zdbranch(BranchInnerCode, BranchCode, ParentInnerCode, Type, OrderFlag, Name, TreeLevel, IsLeaf, Phone, Fax, Manager, Leader1, 

Leader2, Prop1, Prop2, Prop3, Prop4, Memo, AddTime, AddUser, ModifyTime, ModifyUser)
  VALUES('120000095120000435120000478', '2120999114111102F', '120000095120000435', '0', 477, '西青支行当杨路储蓄所', 3, 'Y', NULL, NULL, 'admin', 'administrator', 'admin', 

'10', '120000478', NULL, NULL, 

NULL, '20100824', 'admin', NULL, NULL)
;
INSERT INTO zdbranch(BranchInnerCode, BranchCode, ParentInnerCode, Type, OrderFlag, Name, TreeLevel, IsLeaf, Phone, Fax, Manager, Leader1, 

Leader2, Prop1, Prop2, Prop3, Prop4, Memo, AddTime, AddUser, ModifyTime, ModifyUser)
  VALUES('120000095120000435120000479', '2120999114116000T', '120000095120000435', '0', 478, '西青区上辛口分理处', 3, 'Y', NULL, NULL, 'admin', 'administrator', 'admin', 

'06', '120000479', NULL, NULL, NULL, 

'20100824', 'admin', NULL, NULL)
;
INSERT INTO zdbranch(BranchInnerCode, BranchCode, ParentInnerCode, Type, OrderFlag, Name, TreeLevel, IsLeaf, Phone, Fax, Manager, Leader1, 

Leader2, Prop1, Prop2, Prop3, Prop4, Memo, AddTime, AddUser, ModifyTime, ModifyUser)
  VALUES('120000095120000435120000480', '2120999114121000H', '120000095120000435', '0', 479, '西青区中北镇储蓄所', 3, 'Y', NULL, NULL, 'admin', 'administrator', 'admin', 

'06', '120000480', NULL, NULL, NULL, 

'20100824', 'admin', NULL, NULL)
;
INSERT INTO zdbranch(BranchInnerCode, BranchCode, ParentInnerCode, Type, OrderFlag, Name, TreeLevel, IsLeaf, Phone, Fax, Manager, Leader1, 

Leader2, Prop1, Prop2, Prop3, Prop4, Memo, AddTime, AddUser, ModifyTime, ModifyUser)
  VALUES('120000095120000495120000481', '2120999115001000S', '120000095120000495', '0', 480, '津南支行机关', 3, 'Y', NULL, NULL, 'admin', 'administrator', 'admin', '04', '120000481', 

NULL, NULL, NULL, 

'20100824', 'admin', NULL, NULL)
;
INSERT INTO zdbranch(BranchInnerCode, BranchCode, ParentInnerCode, Type, OrderFlag, Name, TreeLevel, IsLeaf, Phone, Fax, Manager, Leader1, 

Leader2, Prop1, Prop2, Prop3, Prop4, Memo, AddTime, AddUser, ModifyTime, ModifyUser)
  VALUES('120000095120000495120000481120000482', '2120999115001002O', '120000095120000495120000481', '0', 481, '津南支行其他', 4, 'Y', NULL, NULL, 'admin', 'administrator', 'admin', '04', '120000482', 

NULL, NULL, NULL, 

'20100824', 'admin', NULL, NULL)
;
INSERT INTO zdbranch(BranchInnerCode, BranchCode, ParentInnerCode, Type, OrderFlag, Name, TreeLevel, IsLeaf, Phone, Fax, Manager, Leader1, 

Leader2, Prop1, Prop2, Prop3, Prop4, Memo, AddTime, AddUser, ModifyTime, ModifyUser)
  VALUES('120000095120000495120000481120000483', '2120999115001001Q', '120000095120000495120000481', '0', 482, '津南支行综合管理部', 4, 'Y', NULL, NULL, 'admin', 'administrator', 'admin', '04', 

'120000483', NULL, NULL, NULL, 

'20100824', 'admin', NULL, NULL)
;
INSERT INTO zdbranch(BranchInnerCode, BranchCode, ParentInnerCode, Type, OrderFlag, Name, TreeLevel, IsLeaf, Phone, Fax, Manager, Leader1, 

Leader2, Prop1, Prop2, Prop3, Prop4, Memo, AddTime, AddUser, ModifyTime, ModifyUser)
  VALUES('120000095120000495120000481120000484', '2120999115001003M', '120000095120000495120000481', '0', 483, '津南支行行长室', 4, 'Y', NULL, NULL, 'admin', 'administrator', 'admin', '04', 

'120000484', NULL, NULL, NULL, 

'20100824', 'admin', NULL, NULL)
;
INSERT INTO zdbranch(BranchInnerCode, BranchCode, ParentInnerCode, Type, OrderFlag, Name, TreeLevel, IsLeaf, Phone, Fax, Manager, Leader1, 

Leader2, Prop1, Prop2, Prop3, Prop4, Memo, AddTime, AddUser, ModifyTime, ModifyUser)
  VALUES('120000095120000495120000481120000485', '2120999115001005I', '120000095120000495120000481', '0', 484, '津南支行内退人员', 4, 'Y', NULL, NULL, 'admin', 'administrator', 'admin', '04', 

'120000485', NULL, NULL, NULL, 

'20100824', 'admin', NULL, NULL)
;
INSERT INTO zdbranch(BranchInnerCode, BranchCode, ParentInnerCode, Type, OrderFlag, Name, TreeLevel, IsLeaf, Phone, Fax, Manager, Leader1, 

Leader2, Prop1, Prop2, Prop3, Prop4, Memo, AddTime, AddUser, ModifyTime, ModifyUser)
  VALUES('120000095120000495120000481120000486', '2120999115001004K', '120000095120000495120000481', '0', 485, '津南支行保险代理部', 4, 'Y', NULL, NULL, 'admin', 'administrator', 'admin', '04', 

'120000486', NULL, NULL, NULL, 

'20100824', 'admin', NULL, NULL)
;
INSERT INTO zdbranch(BranchInnerCode, BranchCode, ParentInnerCode, Type, OrderFlag, Name, TreeLevel, IsLeaf, Phone, Fax, Manager, Leader1, 

Leader2, Prop1, Prop2, Prop3, Prop4, Memo, AddTime, AddUser, ModifyTime, ModifyUser)
  VALUES('120000095120000495120000481120000487', '2120999115001007E', '120000095120000495120000481', '0', 486, '津南支行财会运营部', 4, 'Y', NULL, NULL, 'admin', 'administrator', 'admin', '04', 

'120000487', NULL, NULL, NULL, 

'20100824', 'admin', NULL, NULL)
;
INSERT INTO zdbranch(BranchInnerCode, BranchCode, ParentInnerCode, Type, OrderFlag, Name, TreeLevel, IsLeaf, Phone, Fax, Manager, Leader1, 

Leader2, Prop1, Prop2, Prop3, Prop4, Memo, AddTime, AddUser, ModifyTime, ModifyUser)
  VALUES('120000095120000495120000481120000488', '2120999115001006G', '120000095120000495120000481', '0', 487, '津南支行监察保卫/内控合规部', 4, 'Y', NULL, NULL, 'admin', 'administrator', 

'admin', '04', '120000488', NULL, NULL, 

NULL, '20100824', 'admin', NULL, NULL)
;
INSERT INTO zdbranch(BranchInnerCode, BranchCode, ParentInnerCode, Type, OrderFlag, Name, TreeLevel, IsLeaf, Phone, Fax, Manager, Leader1, 

Leader2, Prop1, Prop2, Prop3, Prop4, Memo, AddTime, AddUser, ModifyTime, ModifyUser)
  VALUES('120000095120000495120000481120000489', '2120999115001008C', '120000095120000495120000481', '0', 488, '津南支行离退休人员', 4, 'Y', NULL, NULL, 'admin', 'administrator', 'admin', '04', 

'120000489', NULL, NULL, NULL, 

'20100824', 'admin', NULL, NULL)
;
INSERT INTO zdbranch(BranchInnerCode, BranchCode, ParentInnerCode, Type, OrderFlag, Name, TreeLevel, IsLeaf, Phone, Fax, Manager, Leader1, 

Leader2, Prop1, Prop2, Prop3, Prop4, Memo, AddTime, AddUser, ModifyTime, ModifyUser)
  VALUES('120000095120000495120000481120000490', '2120999115001009A', '120000095120000495120000481', '0', 489, '津南支行客户部', 4, 'Y', NULL, NULL, 'admin', 'administrator', 'admin', '04', 

'120000490', NULL, NULL, NULL, 

'20100824', 'admin', NULL, NULL)
;
INSERT INTO zdbranch(BranchInnerCode, BranchCode, ParentInnerCode, Type, OrderFlag, Name, TreeLevel, IsLeaf, Phone, Fax, Manager, Leader1, 

Leader2, Prop1, Prop2, Prop3, Prop4, Memo, AddTime, AddUser, ModifyTime, ModifyUser)
  VALUES('120000095120000495120000481120000491', '2120999115001010P', '120000095120000495120000481', '0', 490, '津南支行信贷管理部', 4, 'Y', NULL, NULL, 'admin', 'administrator', 'admin', '04', 

'120000491', NULL, NULL, NULL, 

'20100824', 'admin', NULL, NULL)
;
INSERT INTO zdbranch(BranchInnerCode, BranchCode, ParentInnerCode, Type, OrderFlag, Name, TreeLevel, IsLeaf, Phone, Fax, Manager, Leader1, 

Leader2, Prop1, Prop2, Prop3, Prop4, Memo, AddTime, AddUser, ModifyTime, ModifyUser)
  VALUES('120000095120000495120000481120000492', '2120999115001011N', '120000095120000495120000481', '0', 491, '津南支行个人金融部', 4, 'Y', NULL, NULL, 'admin', 'administrator', 'admin', '04', 

'120000492', NULL, NULL, NULL, 

'20100824', 'admin', NULL, NULL)
;
INSERT INTO zdbranch(BranchInnerCode, BranchCode, ParentInnerCode, Type, OrderFlag, Name, TreeLevel, IsLeaf, Phone, Fax, Manager, Leader1, 

Leader2, Prop1, Prop2, Prop3, Prop4, Memo, AddTime, AddUser, ModifyTime, ModifyUser)
  VALUES('120000095120000495120000481120000493', '2120999115001012L', '120000095120000495120000481', '0', 492, '津南支行公司业务部', 4, 'Y', NULL, NULL, 'admin', 'administrator', 'admin', '04', 

'120000493', NULL, NULL, NULL, 

'20100824', 'admin', NULL, NULL)
;
INSERT INTO zdbranch(BranchInnerCode, BranchCode, ParentInnerCode, Type, OrderFlag, Name, TreeLevel, IsLeaf, Phone, Fax, Manager, Leader1, 

Leader2, Prop1, Prop2, Prop3, Prop4, Memo, AddTime, AddUser, ModifyTime, ModifyUser)
  VALUES('120000095120000495120000481120000494', '2120999115001013J', '120000095120000495120000481', '0', 493, '津南支行内部分流人员', 4, 'Y', NULL, NULL, 'admin', 'administrator', 'admin', 

'04', '120000494', NULL, NULL, NULL, 

'20100824', 'admin', NULL, NULL)
;
INSERT INTO zdbranch(BranchInnerCode, BranchCode, ParentInnerCode, Type, OrderFlag, Name, TreeLevel, IsLeaf, Phone, Fax, Manager, Leader1, 

Leader2, Prop1, Prop2, Prop3, Prop4, Memo, AddTime, AddUser, ModifyTime, ModifyUser)
  VALUES('120000095120000495', '21209991150000002', '120000095', '0', 494, '津南支行', 2, 'Y', NULL, NULL, 'admin', 'administrator', 'admin', '04', '120000495', 

NULL, NULL, NULL, '20100824', 'admin', NULL, NULL);
INSERT INTO zdbranch(BranchInnerCode, BranchCode, ParentInnerCode, Type, OrderFlag, Name, TreeLevel, IsLeaf, Phone, Fax, Manager, Leader1, 

Leader2, Prop1, Prop2, Prop3, Prop4, Memo, AddTime, AddUser, ModifyTime, ModifyUser)
  VALUES('120000095120000495120000496', '2120999115101000L', '120000095120000495', '0', 495, '北闸口支行', 3, 'Y', NULL, NULL, 'admin', 'administrator', 'admin', '06', '120000496', 

NULL, NULL, NULL, 

'20100824', 'admin', NULL, NULL)
;
INSERT INTO zdbranch(BranchInnerCode, BranchCode, ParentInnerCode, Type, OrderFlag, Name, TreeLevel, IsLeaf, Phone, Fax, Manager, Leader1, 

Leader2, Prop1, Prop2, Prop3, Prop4, Memo, AddTime, AddUser, ModifyTime, ModifyUser)
  VALUES('120000095120000495120000497', '2120999115102000G', '120000095120000495', '0', 496, '八里台开发区支行', 3, 'Y', NULL, NULL, 'admin', 'administrator', 'admin', '06', 

'120000497', NULL, NULL, NULL, 

'20100824', 'admin', NULL, NULL)
;
INSERT INTO zdbranch(BranchInnerCode, BranchCode, ParentInnerCode, Type, OrderFlag, Name, TreeLevel, IsLeaf, Phone, Fax, Manager, Leader1, 

Leader2, Prop1, Prop2, Prop3, Prop4, Memo, AddTime, AddUser, ModifyTime, ModifyUser)
  VALUES('120000095120000495120000498', '2120999115103000B', '120000095120000495', '0', 497, '津南支行瑞江支行', 3, 'Y', NULL, NULL, 'admin', 'administrator', 'admin', '06', 

'120000498', NULL, NULL, NULL, 

'20100824', 'admin', NULL, NULL)
;
INSERT INTO zdbranch(BranchInnerCode, BranchCode, ParentInnerCode, Type, OrderFlag, Name, TreeLevel, IsLeaf, Phone, Fax, Manager, Leader1, 

Leader2, Prop1, Prop2, Prop3, Prop4, Memo, AddTime, AddUser, ModifyTime, ModifyUser)
  VALUES('120000095120000495120000499', '21209991151040006', '120000095120000495', '0', 498, '津沽路支行', 3, 'Y', NULL, NULL, 'admin', 'administrator', 'admin', '06', '120000499', 

NULL, NULL, NULL, 

'20100824', 'admin', NULL, NULL)
;
INSERT INTO zdbranch(BranchInnerCode, BranchCode, ParentInnerCode, Type, OrderFlag, Name, TreeLevel, IsLeaf, Phone, Fax, Manager, Leader1, 

Leader2, Prop1, Prop2, Prop3, Prop4, Memo, AddTime, AddUser, ModifyTime, ModifyUser)
  VALUES('120000095120000102120000500', '2120999102118000D', '120000095120000102', '0', 499, '南开支行泗水道分理处', 3, 'Y', NULL, NULL, 'admin', 'administrator', 'admin', 

'06', '120000500', NULL, NULL, NULL, 

'20100824', 'admin', NULL, NULL)
;
INSERT INTO zdbranch(BranchInnerCode, BranchCode, ParentInnerCode, Type, OrderFlag, Name, TreeLevel, IsLeaf, Phone, Fax, Manager, Leader1, 

Leader2, Prop1, Prop2, Prop3, Prop4, Memo, AddTime, AddUser, ModifyTime, ModifyUser)
  VALUES('120000095120000495120000501', '21209991151050001', '120000095120000495', '0', 500, '紫金山路支行', 3, 'Y', NULL, NULL, 'admin', 'administrator', 'admin', '06', '120000501', 

NULL, NULL, NULL, 

'20100824', 'admin', NULL, NULL)
;
INSERT INTO zdbranch(BranchInnerCode, BranchCode, ParentInnerCode, Type, OrderFlag, Name, TreeLevel, IsLeaf, Phone, Fax, Manager, Leader1, 

Leader2, Prop1, Prop2, Prop3, Prop4, Memo, AddTime, AddUser, ModifyTime, ModifyUser)
  VALUES('120000095120000495120000501120000502', '2120999115105101Q', '120000095120000495120000501', '0', 501, '津南支行咸水沽体育场储蓄所', 4, 'Y', NULL, NULL, 'admin', 'administrator', 

'admin', '10', '120000502', NULL, 

NULL, NULL, '20100824', 'admin', NULL, NULL)
;
INSERT INTO zdbranch(BranchInnerCode, BranchCode, ParentInnerCode, Type, OrderFlag, Name, TreeLevel, IsLeaf, Phone, Fax, Manager, Leader1, 

Leader2, Prop1, Prop2, Prop3, Prop4, Memo, AddTime, AddUser, ModifyTime, ModifyUser)
  VALUES('120000095120000495120000503', '2120999115115000Q', '120000095120000495', '0', 502, '津南区咸水沽储蓄所', 3, 'Y', NULL, NULL, 'admin', 'administrator', 'admin', 

'06', '120000503', NULL, NULL, NULL, 

'20100824', 'admin', NULL, NULL)
;
INSERT INTO zdbranch(BranchInnerCode, BranchCode, ParentInnerCode, Type, OrderFlag, Name, TreeLevel, IsLeaf, Phone, Fax, Manager, Leader1, 

Leader2, Prop1, Prop2, Prop3, Prop4, Memo, AddTime, AddUser, ModifyTime, ModifyUser)
  VALUES('120000095120000495120000504', '2120999115116000L', '120000095120000495', '0', 503, '津南支行解放南路汽车城分理处', 3, 'Y', NULL, NULL, 'admin', 'administrator', 

'admin', '06', '120000504', NULL, 

NULL, NULL, '20100824', 'admin', NULL, NULL)
;
INSERT INTO zdbranch(BranchInnerCode, BranchCode, ParentInnerCode, Type, OrderFlag, Name, TreeLevel, IsLeaf, Phone, Fax, Manager, Leader1, 

Leader2, Prop1, Prop2, Prop3, Prop4, Memo, AddTime, AddUser, ModifyTime, ModifyUser)
  VALUES('120000095120000495120000501120000505', '2120999115105104K', '120000095120000495120000501', '0', 504, '津南支行咸水沽营业所建国大街储蓄所', 4, 'Y', NULL, NULL, 'admin', 

'administrator', 'admin', '10', '120000505', 

NULL, NULL, NULL, '20100824', 'admin', NULL, NULL)
;
INSERT INTO zdbranch(BranchInnerCode, BranchCode, ParentInnerCode, Type, OrderFlag, Name, TreeLevel, IsLeaf, Phone, Fax, Manager, Leader1, 

Leader2, Prop1, Prop2, Prop3, Prop4, Memo, AddTime, AddUser, ModifyTime, ModifyUser)
  VALUES('120000095120000495120000506', '2120999115117000G', '120000095120000495', '0', 505, '津南区新城储蓄所', 3, 'Y', NULL, NULL, 'admin', 'administrator', 'admin', '06', 

'120000506', NULL, NULL, NULL, 

'20100824', 'admin', NULL, NULL)
;
INSERT INTO zdbranch(BranchInnerCode, BranchCode, ParentInnerCode, Type, OrderFlag, Name, TreeLevel, IsLeaf, Phone, Fax, Manager, Leader1, 

Leader2, Prop1, Prop2, Prop3, Prop4, Memo, AddTime, AddUser, ModifyTime, ModifyUser)
  VALUES('120000095120000495120000507', '2120999115106000R', '120000095120000495', '0', 506, '双港支行', 3, 'Y', NULL, NULL, 'admin', 'administrator', 'admin', '06', '120000507', 

NULL, NULL, NULL, '20100824', 'admin', NULL, NULL);
INSERT INTO zdbranch(BranchInnerCode, BranchCode, ParentInnerCode, Type, OrderFlag, Name, TreeLevel, IsLeaf, Phone, Fax, Manager, Leader1, 

Leader2, Prop1, Prop2, Prop3, Prop4, Memo, AddTime, AddUser, ModifyTime, ModifyUser)
  VALUES('120000095120000495120000508', '2120999115118000B', '120000095120000495', '0', 507, '津南区十八局分理处', 3, 'Y', NULL, NULL, 'admin', 'administrator', 'admin', 

'06', '120000508', NULL, NULL, NULL, 

'20100824', 'admin', NULL, NULL)
;
INSERT INTO zdbranch(BranchInnerCode, BranchCode, ParentInnerCode, Type, OrderFlag, Name, TreeLevel, IsLeaf, Phone, Fax, Manager, Leader1, 

Leader2, Prop1, Prop2, Prop3, Prop4, Memo, AddTime, AddUser, ModifyTime, ModifyUser)
  VALUES('120000095120000495120000509', '2120999115111000F', '120000095120000495', '0', 508, '津南支行灰堆分理处', 3, 'Y', NULL, NULL, 'admin', 'administrator', 'admin', '06', 

'120000509', NULL, NULL, NULL, 

'20100824', 'admin', NULL, NULL)
;
INSERT INTO zdbranch(BranchInnerCode, BranchCode, ParentInnerCode, Type, OrderFlag, Name, TreeLevel, IsLeaf, Phone, Fax, Manager, Leader1, 

Leader2, Prop1, Prop2, Prop3, Prop4, Memo, AddTime, AddUser, ModifyTime, ModifyUser)
  VALUES('120000095120000495120000507120000510', '2120999115106103H', '120000095120000495120000507', '0', 509, '津南支行双港营业所柳林营业所', 4, 'Y', NULL, NULL, 'admin', 'administrator', 

'admin', '10', '120000510', NULL, 

NULL, NULL, '20100824', 'admin', NULL, NULL)
;
INSERT INTO zdbranch(BranchInnerCode, BranchCode, ParentInnerCode, Type, OrderFlag, Name, TreeLevel, IsLeaf, Phone, Fax, Manager, Leader1, 

Leader2, Prop1, Prop2, Prop3, Prop4, Memo, AddTime, AddUser, ModifyTime, ModifyUser)
  VALUES('120000095120000495120000511', '2120999115107000M', '120000095120000495', '0', 510, '小站支行', 3, 'Y', NULL, NULL, 'admin', 'administrator', 'admin', '06', '120000511', 

NULL, NULL, NULL, '20100824', 'admin', NULL, NULL);
INSERT INTO zdbranch(BranchInnerCode, BranchCode, ParentInnerCode, Type, OrderFlag, Name, TreeLevel, IsLeaf, Phone, Fax, Manager, Leader1, 

Leader2, Prop1, Prop2, Prop3, Prop4, Memo, AddTime, AddUser, ModifyTime, ModifyUser)
  VALUES('120000095120000495120000511120000512', '2120999115107101G', '120000095120000495120000511', '0', 511, '津南支行小站营业所小站第二储蓄所', 4, 'Y', NULL, NULL, 'admin', 'administrator', 

'admin', '10', '120000512', 

NULL, NULL, NULL, '20100824', 'admin', NULL, NULL)
;
INSERT INTO zdbranch(BranchInnerCode, BranchCode, ParentInnerCode, Type, OrderFlag, Name, TreeLevel, IsLeaf, Phone, Fax, Manager, Leader1, 

Leader2, Prop1, Prop2, Prop3, Prop4, Memo, AddTime, AddUser, ModifyTime, ModifyUser)
  VALUES('120000095120000495120000513', '2120999115120000E', '120000095120000495', '0', 512, '津南区开发区分理处', 3, 'Y', NULL, NULL, 'admin', 'administrator', 'admin', 

'06', '120000513', NULL, NULL, NULL, 

'20100824', 'admin', NULL, NULL)
;
INSERT INTO zdbranch(BranchInnerCode, BranchCode, ParentInnerCode, Type, OrderFlag, Name, TreeLevel, IsLeaf, Phone, Fax, Manager, Leader1, 

Leader2, Prop1, Prop2, Prop3, Prop4, Memo, AddTime, AddUser, ModifyTime, ModifyUser)
  VALUES('120000095120000495120000514', '21209991151190006', '120000095120000495', '0', 513, '津南支行柳林储蓄所', 3, 'Y', NULL, NULL, 'admin', 'administrator', 'admin', 

'10', '120000514', NULL, NULL, NULL, 

'20100824', 'admin', NULL, NULL)
;
INSERT INTO zdbranch(BranchInnerCode, BranchCode, ParentInnerCode, Type, OrderFlag, Name, TreeLevel, IsLeaf, Phone, Fax, Manager, Leader1, 

Leader2, Prop1, Prop2, Prop3, Prop4, Memo, AddTime, AddUser, ModifyTime, ModifyUser)
  VALUES('120000095120000495120000515', '2120999115112000A', '120000095120000495', '0', 514, '津南区辛庄储蓄所', 3, 'Y', NULL, NULL, 'admin', 'administrator', 'admin', '06', 

'120000515', NULL, NULL, NULL, 

'20100824', 'admin', NULL, NULL)
;
INSERT INTO zdbranch(BranchInnerCode, BranchCode, ParentInnerCode, Type, OrderFlag, Name, TreeLevel, IsLeaf, Phone, Fax, Manager, Leader1, 

Leader2, Prop1, Prop2, Prop3, Prop4, Memo, AddTime, AddUser, ModifyTime, ModifyUser)
  VALUES('120000095120000495120000516', '21209991151210009', '120000095120000495', '0', 515, '津南支行葛沽镇储蓄所', 3, 'Y', NULL, NULL, 'admin', 'administrator', 'admin', 

'10', '120000516', NULL, NULL, 

NULL, '20100824', 'admin', NULL, NULL)
;
INSERT INTO zdbranch(BranchInnerCode, BranchCode, ParentInnerCode, Type, OrderFlag, Name, TreeLevel, IsLeaf, Phone, Fax, Manager, Leader1, 

Leader2, Prop1, Prop2, Prop3, Prop4, Memo, AddTime, AddUser, ModifyTime, ModifyUser)
  VALUES('120000095120000495120000517', '21209991151220004', '120000095120000495', '0', 516, '津南支行太平广场分理处', 3, 'Y', NULL, NULL, 'admin', 'administrator', 'admin', 

'06', '120000517', NULL, NULL, 

NULL, '20100824', 'admin', NULL, NULL)
;
INSERT INTO zdbranch(BranchInnerCode, BranchCode, ParentInnerCode, Type, OrderFlag, Name, TreeLevel, IsLeaf, Phone, Fax, Manager, Leader1, 

Leader2, Prop1, Prop2, Prop3, Prop4, Memo, AddTime, AddUser, ModifyTime, ModifyUser)
  VALUES('120000095120000495120000518', '2120999115108000H', '120000095120000495', '0', 517, '葛沽支行', 3, 'Y', NULL, NULL, 'admin', 'administrator', 'admin', '06', '120000518', 

NULL, NULL, NULL, '20100824', 'admin', NULL, NULL);
INSERT INTO zdbranch(BranchInnerCode, BranchCode, ParentInnerCode, Type, OrderFlag, Name, TreeLevel, IsLeaf, Phone, Fax, Manager, Leader1, 

Leader2, Prop1, Prop2, Prop3, Prop4, Memo, AddTime, AddUser, ModifyTime, ModifyUser)
  VALUES('120000095120000495120000519', '21209991151140000', '120000095120000495', '0', 518, '双闸支行', 3, 'Y', NULL, NULL, 'admin', 'administrator', 'admin', '06', '120000519', 

NULL, NULL, NULL, '20100824', 'admin', NULL, NULL);
INSERT INTO zdbranch(BranchInnerCode, BranchCode, ParentInnerCode, Type, OrderFlag, Name, TreeLevel, IsLeaf, Phone, Fax, Manager, Leader1, 

Leader2, Prop1, Prop2, Prop3, Prop4, Memo, AddTime, AddUser, ModifyTime, ModifyUser)
  VALUES('120000095120000495120000520', '21209991151130005', '120000095120000495', '0', 519, '津南支行登发分理处', 3, 'Y', NULL, NULL, 'admin', 'administrator', 'admin', 

'06', '120000520', NULL, NULL, NULL, 

'20100824', 'admin', NULL, NULL)
;
INSERT INTO zdbranch(BranchInnerCode, BranchCode, ParentInnerCode, Type, OrderFlag, Name, TreeLevel, IsLeaf, Phone, Fax, Manager, Leader1, 

Leader2, Prop1, Prop2, Prop3, Prop4, Memo, AddTime, AddUser, ModifyTime, ModifyUser)
  VALUES('120000095120000495120000518120000521', '21209991151081037', '120000095120000495120000518', '0', 520, '津南支行葛沽营业所葛沽第二储蓄所', 4, 'Y', NULL, NULL, 'admin', 'administrator', 

'admin', '10', '120000521', 

NULL, NULL, NULL, '20100824', 'admin', NULL, NULL)
;
INSERT INTO zdbranch(BranchInnerCode, BranchCode, ParentInnerCode, Type, OrderFlag, Name, TreeLevel, IsLeaf, Phone, Fax, Manager, Leader1, 

Leader2, Prop1, Prop2, Prop3, Prop4, Memo, AddTime, AddUser, ModifyTime, ModifyUser)
  VALUES('120000095120000522', '2120999116000000P', '120000095', '0', 521, '北辰支行', 2, 'Y', NULL, NULL, 'admin', 'administrator', 'admin', '04', '120000522', 

NULL, NULL, NULL, '20100824', 'admin', NULL, NULL);
INSERT INTO zdbranch(BranchInnerCode, BranchCode, ParentInnerCode, Type, OrderFlag, Name, TreeLevel, IsLeaf, Phone, Fax, Manager, Leader1, 

Leader2, Prop1, Prop2, Prop3, Prop4, Memo, AddTime, AddUser, ModifyTime, ModifyUser)
  VALUES('120000095120000522120000523', '2120999116001000K', '120000095120000522', '0', 522, '北辰支行机关', 3, 'Y', NULL, NULL, 'admin', 'administrator', 'admin', '04', '120000523', 

NULL, NULL, NULL, 

'20100824', 'admin', NULL, NULL)
;
INSERT INTO zdbranch(BranchInnerCode, BranchCode, ParentInnerCode, Type, OrderFlag, Name, TreeLevel, IsLeaf, Phone, Fax, Manager, Leader1, 

Leader2, Prop1, Prop2, Prop3, Prop4, Memo, AddTime, AddUser, ModifyTime, ModifyUser)
  VALUES('120000095120000522120000523120000524', '2120999116001001I', '120000095120000522120000523', '0', 523, '北辰支行调出', 4, 'Y', NULL, NULL, 'admin', 'administrator', 'admin', '04', '120000524', 

NULL, NULL, NULL, 

'20100824', 'admin', NULL, NULL)
;
INSERT INTO zdbranch(BranchInnerCode, BranchCode, ParentInnerCode, Type, OrderFlag, Name, TreeLevel, IsLeaf, Phone, Fax, Manager, Leader1, 

Leader2, Prop1, Prop2, Prop3, Prop4, Memo, AddTime, AddUser, ModifyTime, ModifyUser)
  VALUES('120000095120000522120000523120000525', '2120999116001002G', '120000095120000522120000523', '0', 524, '北辰支行离退', 4, 'Y', NULL, NULL, 'admin', 'administrator', 'admin', '04', '120000525', 

NULL, NULL, NULL, 

'20100824', 'admin', NULL, NULL)
;
INSERT INTO zdbranch(BranchInnerCode, BranchCode, ParentInnerCode, Type, OrderFlag, Name, TreeLevel, IsLeaf, Phone, Fax, Manager, Leader1, 

Leader2, Prop1, Prop2, Prop3, Prop4, Memo, AddTime, AddUser, ModifyTime, ModifyUser)
  VALUES('120000095120000522120000523120000526', '2120999116001003E', '120000095120000522120000523', '0', 525, '北辰支行其他', 4, 'Y', NULL, NULL, 'admin', 'administrator', 'admin', '04', '120000526', 

NULL, NULL, NULL, 

'20100824', 'admin', NULL, NULL)
;
INSERT INTO zdbranch(BranchInnerCode, BranchCode, ParentInnerCode, Type, OrderFlag, Name, TreeLevel, IsLeaf, Phone, Fax, Manager, Leader1, 

Leader2, Prop1, Prop2, Prop3, Prop4, Memo, AddTime, AddUser, ModifyTime, ModifyUser)
  VALUES('120000095120000522120000523120000527', '2120999116001004C', '120000095120000522120000523', '0', 526, '北辰支行司机', 4, 'Y', NULL, NULL, 'admin', 'administrator', 'admin', '04', '120000527', 

NULL, NULL, NULL, 

'20100824', 'admin', NULL, NULL)
;
INSERT INTO zdbranch(BranchInnerCode, BranchCode, ParentInnerCode, Type, OrderFlag, Name, TreeLevel, IsLeaf, Phone, Fax, Manager, Leader1, 

Leader2, Prop1, Prop2, Prop3, Prop4, Memo, AddTime, AddUser, ModifyTime, ModifyUser)
  VALUES('120000095120000522120000523120000528', '2120999116001005A', '120000095120000522120000523', '0', 527, '北辰支行死亡', 4, 'Y', NULL, NULL, 'admin', 'administrator', 'admin', '04', '120000528', 

NULL, NULL, NULL, 

'20100824', 'admin', NULL, NULL)
;
INSERT INTO zdbranch(BranchInnerCode, BranchCode, ParentInnerCode, Type, OrderFlag, Name, TreeLevel, IsLeaf, Phone, Fax, Manager, Leader1, 

Leader2, Prop1, Prop2, Prop3, Prop4, Memo, AddTime, AddUser, ModifyTime, ModifyUser)
  VALUES('120000095120000522120000523120000529', '21209991160010068', '120000095120000522120000523', '0', 528, '北辰支行内控合规岗', 4, 'Y', NULL, NULL, 'admin', 'administrator', 'admin', '04', 

'120000529', NULL, NULL, NULL, 

'20100824', 'admin', NULL, NULL)
;
INSERT INTO zdbranch(BranchInnerCode, BranchCode, ParentInnerCode, Type, OrderFlag, Name, TreeLevel, IsLeaf, Phone, Fax, Manager, Leader1, 

Leader2, Prop1, Prop2, Prop3, Prop4, Memo, AddTime, AddUser, ModifyTime, ModifyUser)
  VALUES('120000095120000522120000523120000530', '21209991160010076', '120000095120000522120000523', '0', 529, '北辰支行储蓄组', 4, 'Y', NULL, NULL, 'admin', 'administrator', 'admin', '04', 

'120000530', NULL, NULL, NULL, 

'20100824', 'admin', NULL, NULL)
;
INSERT INTO zdbranch(BranchInnerCode, BranchCode, ParentInnerCode, Type, OrderFlag, Name, TreeLevel, IsLeaf, Phone, Fax, Manager, Leader1, 

Leader2, Prop1, Prop2, Prop3, Prop4, Memo, AddTime, AddUser, ModifyTime, ModifyUser)
  VALUES('120000095120000522120000523120000531', '21209991160010084', '120000095120000522120000523', '0', 530, '北辰支行会计组', 4, 'Y', NULL, NULL, 'admin', 'administrator', 'admin', '04', 

'120000531', NULL, NULL, NULL, 

'20100824', 'admin', NULL, NULL)
;
INSERT INTO zdbranch(BranchInnerCode, BranchCode, ParentInnerCode, Type, OrderFlag, Name, TreeLevel, IsLeaf, Phone, Fax, Manager, Leader1, 

Leader2, Prop1, Prop2, Prop3, Prop4, Memo, AddTime, AddUser, ModifyTime, ModifyUser)
  VALUES('120000095120000522120000523120000532', '21209991160010092', '120000095120000522120000523', '0', 531, '北辰支行拓展部', 4, 'Y', NULL, NULL, 'admin', 'administrator', 'admin', '04', 

'120000532', NULL, NULL, NULL, 

'20100824', 'admin', NULL, NULL)
;
INSERT INTO zdbranch(BranchInnerCode, BranchCode, ParentInnerCode, Type, OrderFlag, Name, TreeLevel, IsLeaf, Phone, Fax, Manager, Leader1, 

Leader2, Prop1, Prop2, Prop3, Prop4, Memo, AddTime, AddUser, ModifyTime, ModifyUser)
  VALUES('120000095120000522120000523120000533', '2120999116001010H', '120000095120000522120000523', '0', 532, '北辰支行外汇部', 4, 'Y', NULL, NULL, 'admin', 'administrator', 'admin', '04', 

'120000533', NULL, NULL, NULL, 

'20100824', 'admin', NULL, NULL)
;
INSERT INTO zdbranch(BranchInnerCode, BranchCode, ParentInnerCode, Type, OrderFlag, Name, TreeLevel, IsLeaf, Phone, Fax, Manager, Leader1, 

Leader2, Prop1, Prop2, Prop3, Prop4, Memo, AddTime, AddUser, ModifyTime, ModifyUser)
  VALUES('120000095120000522120000523120000534', '2120999116001011F', '120000095120000522120000523', '0', 533, '北辰支行行长室', 4, 'Y', NULL, NULL, 'admin', 'administrator', 'admin', '04', 

'120000534', NULL, NULL, NULL, 

'20100824', 'admin', NULL, NULL)
;
INSERT INTO zdbranch(BranchInnerCode, BranchCode, ParentInnerCode, Type, OrderFlag, Name, TreeLevel, IsLeaf, Phone, Fax, Manager, Leader1, 

Leader2, Prop1, Prop2, Prop3, Prop4, Memo, AddTime, AddUser, ModifyTime, ModifyUser)
  VALUES('120000095120000522120000523120000535', '2120999116001012D', '120000095120000522120000523', '0', 534, '北辰支行内退人员', 4, 'Y', NULL, NULL, 'admin', 'administrator', 'admin', '04', 

'120000535', NULL, NULL, NULL, 

'20100824', 'admin', NULL, NULL)
;
INSERT INTO zdbranch(BranchInnerCode, BranchCode, ParentInnerCode, Type, OrderFlag, Name, TreeLevel, IsLeaf, Phone, Fax, Manager, Leader1, 

Leader2, Prop1, Prop2, Prop3, Prop4, Memo, AddTime, AddUser, ModifyTime, ModifyUser)
  VALUES('120000095120000522120000523120000536', '2120999116001013B', '120000095120000522120000523', '0', 535, '北辰支行客户三部', 4, 'Y', NULL, NULL, 'admin', 'administrator', 'admin', '04', 

'120000536', NULL, NULL, NULL, 

'20100824', 'admin', NULL, NULL)
;
INSERT INTO zdbranch(BranchInnerCode, BranchCode, ParentInnerCode, Type, OrderFlag, Name, TreeLevel, IsLeaf, Phone, Fax, Manager, Leader1, 

Leader2, Prop1, Prop2, Prop3, Prop4, Memo, AddTime, AddUser, ModifyTime, ModifyUser)
  VALUES('120000095120000522120000523120000537', '21209991160010149', '120000095120000522120000523', '0', 536, '北辰支行公司业务部', 4, 'Y', NULL, NULL, 'admin', 'administrator', 'admin', '04', 

'120000537', NULL, NULL, NULL, 

'20100824', 'admin', NULL, NULL)
;
INSERT INTO zdbranch(BranchInnerCode, BranchCode, ParentInnerCode, Type, OrderFlag, Name, TreeLevel, IsLeaf, Phone, Fax, Manager, Leader1, 

Leader2, Prop1, Prop2, Prop3, Prop4, Memo, AddTime, AddUser, ModifyTime, ModifyUser)
  VALUES('120000095120000522120000523120000538', '21209991160010157', '120000095120000522120000523', '0', 537, '北辰支行财会运营部', 4, 'Y', NULL, NULL, 'admin', 'administrator', 'admin', '04', 

'120000538', NULL, NULL, NULL, 

'20100824', 'admin', NULL, NULL)
;
INSERT INTO zdbranch(BranchInnerCode, BranchCode, ParentInnerCode, Type, OrderFlag, Name, TreeLevel, IsLeaf, Phone, Fax, Manager, Leader1, 

Leader2, Prop1, Prop2, Prop3, Prop4, Memo, AddTime, AddUser, ModifyTime, ModifyUser)
  VALUES('120000095120000522120000523120000539', '21209991160010165', '120000095120000522120000523', '0', 538, '北辰支行监察保卫部', 4, 'Y', NULL, NULL, 'admin', 'administrator', 'admin', '04', 

'120000539', NULL, NULL, NULL, 

'20100824', 'admin', NULL, NULL)
;
INSERT INTO zdbranch(BranchInnerCode, BranchCode, ParentInnerCode, Type, OrderFlag, Name, TreeLevel, IsLeaf, Phone, Fax, Manager, Leader1, 

Leader2, Prop1, Prop2, Prop3, Prop4, Memo, AddTime, AddUser, ModifyTime, ModifyUser)
  VALUES('120000095120000522120000523120000540', '21209991160010173', '120000095120000522120000523', '0', 539, '北辰支行监督保障部', 4, 'Y', NULL, NULL, 'admin', 'administrator', 'admin', '04', 

'120000540', NULL, NULL, NULL, 

'20100824', 'admin', NULL, NULL)
;
INSERT INTO zdbranch(BranchInnerCode, BranchCode, ParentInnerCode, Type, OrderFlag, Name, TreeLevel, IsLeaf, Phone, Fax, Manager, Leader1, 

Leader2, Prop1, Prop2, Prop3, Prop4, Memo, AddTime, AddUser, ModifyTime, ModifyUser)
  VALUES('120000095120000522120000523120000541', '21209991160010181', '120000095120000522120000523', '0', 540, '北辰支行离退休人员', 4, 'Y', NULL, NULL, 'admin', 'administrator', 'admin', '04', 

'120000541', NULL, NULL, NULL, 

'20100824', 'admin', NULL, NULL)
;
INSERT INTO zdbranch(BranchInnerCode, BranchCode, ParentInnerCode, Type, OrderFlag, Name, TreeLevel, IsLeaf, Phone, Fax, Manager, Leader1, 

Leader2, Prop1, Prop2, Prop3, Prop4, Memo, AddTime, AddUser, ModifyTime, ModifyUser)
  VALUES('120000095120000522120000523120000542', '2120999116001019U', '120000095120000522120000523', '0', 541, '北辰支行综合管理部', 4, 'Y', NULL, NULL, 'admin', 'administrator', 'admin', '04', 

'120000542', NULL, NULL, NULL, 

'20100824', 'admin', NULL, NULL)
;
INSERT INTO zdbranch(BranchInnerCode, BranchCode, ParentInnerCode, Type, OrderFlag, Name, TreeLevel, IsLeaf, Phone, Fax, Manager, Leader1, 

Leader2, Prop1, Prop2, Prop3, Prop4, Memo, AddTime, AddUser, ModifyTime, ModifyUser)
  VALUES('120000095120000522120000523120000543', '2120999116001020E', '120000095120000522120000523', '0', 542, '北辰支行客户二部', 4, 'Y', NULL, NULL, 'admin', 'administrator', 'admin', '04', 

'120000543', NULL, NULL, NULL, 

'20100824', 'admin', NULL, NULL)
;
INSERT INTO zdbranch(BranchInnerCode, BranchCode, ParentInnerCode, Type, OrderFlag, Name, TreeLevel, IsLeaf, Phone, Fax, Manager, Leader1, 

Leader2, Prop1, Prop2, Prop3, Prop4, Memo, AddTime, AddUser, ModifyTime, ModifyUser)
  VALUES('120000095120000522120000523120000544', '2120999116001021C', '120000095120000522120000523', '0', 543, '北辰支行信贷管理部', 4, 'Y', NULL, NULL, 'admin', 'administrator', 'admin', '04', 

'120000544', NULL, NULL, NULL, 

'20100824', 'admin', NULL, NULL)
;
INSERT INTO zdbranch(BranchInnerCode, BranchCode, ParentInnerCode, Type, OrderFlag, Name, TreeLevel, IsLeaf, Phone, Fax, Manager, Leader1, 

Leader2, Prop1, Prop2, Prop3, Prop4, Memo, AddTime, AddUser, ModifyTime, ModifyUser)
  VALUES('120000095120000522120000523120000545', '2120999116001022A', '120000095120000522120000523', '0', 544, '北辰支行个人金融部', 4, 'Y', NULL, NULL, 'admin', 'administrator', 'admin', '04', 

'120000545', NULL, NULL, NULL, 

'20100824', 'admin', NULL, NULL)
;
INSERT INTO zdbranch(BranchInnerCode, BranchCode, ParentInnerCode, Type, OrderFlag, Name, TreeLevel, IsLeaf, Phone, Fax, Manager, Leader1, 

Leader2, Prop1, Prop2, Prop3, Prop4, Memo, AddTime, AddUser, ModifyTime, ModifyUser)
  VALUES('120000095120000522120000523120000546', '21209991160010238', '120000095120000522120000523', '0', 545, '北辰支行内部分流人员', 4, 'Y', NULL, NULL, 'admin', 'administrator', 'admin', 

'04', '120000546', NULL, NULL, NULL, 

'20100824', 'admin', NULL, NULL)
;
INSERT INTO zdbranch(BranchInnerCode, BranchCode, ParentInnerCode, Type, OrderFlag, Name, TreeLevel, IsLeaf, Phone, Fax, Manager, Leader1, 

Leader2, Prop1, Prop2, Prop3, Prop4, Memo, AddTime, AddUser, ModifyTime, ModifyUser)
  VALUES('120000095120000522120000547', '2120999116101000D', '120000095120000522', '0', 546, '北辰支行北仓营业所', 3, 'Y', NULL, NULL, 'admin', 'administrator', 'admin', 

'06', '120000547', NULL, NULL, NULL, 

'20100824', 'admin', NULL, NULL)
;
INSERT INTO zdbranch(BranchInnerCode, BranchCode, ParentInnerCode, Type, OrderFlag, Name, TreeLevel, IsLeaf, Phone, Fax, Manager, Leader1, 

Leader2, Prop1, Prop2, Prop3, Prop4, Memo, AddTime, AddUser, ModifyTime, ModifyUser)
  VALUES('120000095120000522120000523120000548', '21209991160010246', '120000095120000522120000523', '0', 547, '北辰支行营业部', 4, 'Y', NULL, NULL, 'admin', 'administrator', 'admin', '04', 

'120000548', NULL, NULL, NULL, 

'20100824', 'admin', NULL, NULL)
;
INSERT INTO zdbranch(BranchInnerCode, BranchCode, ParentInnerCode, Type, OrderFlag, Name, TreeLevel, IsLeaf, Phone, Fax, Manager, Leader1, 

Leader2, Prop1, Prop2, Prop3, Prop4, Memo, AddTime, AddUser, ModifyTime, ModifyUser)
  VALUES('120000095120000522120000549', '21209991161210001', '120000095120000522', '0', 548, '北辰区新村分理处', 3, 'Y', NULL, NULL, 'admin', 'administrator', 'admin', '06', 

'120000549', NULL, NULL, NULL, 

'20100824', 'admin', NULL, NULL)
;
INSERT INTO zdbranch(BranchInnerCode, BranchCode, ParentInnerCode, Type, OrderFlag, Name, TreeLevel, IsLeaf, Phone, Fax, Manager, Leader1, 

Leader2, Prop1, Prop2, Prop3, Prop4, Memo, AddTime, AddUser, ModifyTime, ModifyUser)
  VALUES('120000095120000522120000550', '21209991161200006', '120000095120000522', '0', 549, '北辰区化工储蓄所', 3, 'Y', NULL, NULL, 'admin', 'administrator', 'admin', '10', 

'120000550', NULL, NULL, NULL, 

'20100824', 'admin', NULL, NULL)
;
INSERT INTO zdbranch(BranchInnerCode, BranchCode, ParentInnerCode, Type, OrderFlag, Name, TreeLevel, IsLeaf, Phone, Fax, Manager, Leader1, 

Leader2, Prop1, Prop2, Prop3, Prop4, Memo, AddTime, AddUser, ModifyTime, ModifyUser)
  VALUES('120000095120000522120000551', '2120999116119000T', '120000095120000522', '0', 550, '北辰区集贤储蓄所', 3, 'Y', NULL, NULL, 'admin', 'administrator', 'admin', '06', 

'120000551', NULL, NULL, NULL, 

'20100824', 'admin', NULL, NULL)
;
INSERT INTO zdbranch(BranchInnerCode, BranchCode, ParentInnerCode, Type, OrderFlag, Name, TreeLevel, IsLeaf, Phone, Fax, Manager, Leader1, 

Leader2, Prop1, Prop2, Prop3, Prop4, Memo, AddTime, AddUser, ModifyTime, ModifyUser)
  VALUES('120000095120000522120000552', '2120999116102104R', '120000095120000522', '0', 551, '北辰支行双发储蓄所', 3, 'Y', NULL, NULL, 'admin', 'administrator', 'admin', 

'10', '120000552', NULL, NULL, NULL, 

'20100824', 'admin', NULL, NULL)
;
INSERT INTO zdbranch(BranchInnerCode, BranchCode, ParentInnerCode, Type, OrderFlag, Name, TreeLevel, IsLeaf, Phone, Fax, Manager, Leader1, 

Leader2, Prop1, Prop2, Prop3, Prop4, Memo, AddTime, AddUser, ModifyTime, ModifyUser)
  VALUES('120000095120000522120000553', '2120999116102105P', '120000095120000522', '0', 552, '北辰支行振华储蓄所', 3, 'Y', NULL, NULL, 'admin', 'administrator', 'admin', 

'10', '120000553', NULL, NULL, NULL, 

'20100824', 'admin', NULL, NULL)
;
INSERT INTO zdbranch(BranchInnerCode, BranchCode, ParentInnerCode, Type, OrderFlag, Name, TreeLevel, IsLeaf, Phone, Fax, Manager, Leader1, 

Leader2, Prop1, Prop2, Prop3, Prop4, Memo, AddTime, AddUser, ModifyTime, ModifyUser)
  VALUES('120000095120000522120000554', '21209991161110007', '120000095120000522', '0', 553, '北辰区大通绿岛储蓄所', 3, 'Y', NULL, NULL, 'admin', 'administrator', 'admin', 

'06', '120000554', NULL, NULL, 

NULL, '20100824', 'admin', NULL, NULL)
;
INSERT INTO zdbranch(BranchInnerCode, BranchCode, ParentInnerCode, Type, OrderFlag, Name, TreeLevel, IsLeaf, Phone, Fax, Manager, Leader1, 

Leader2, Prop1, Prop2, Prop3, Prop4, Memo, AddTime, AddUser, ModifyTime, ModifyUser)
  VALUES('120000095120000522120000555', '21209991161030003', '120000095120000522', '0', 554, '北辰开发区支行', 3, 'Y', NULL, NULL, 'admin', 'administrator', 'admin', '06', 

'120000555', NULL, NULL, NULL, 

'20100824', 'admin', NULL, NULL)
;
INSERT INTO zdbranch(BranchInnerCode, BranchCode, ParentInnerCode, Type, OrderFlag, Name, TreeLevel, IsLeaf, Phone, Fax, Manager, Leader1, 

Leader2, Prop1, Prop2, Prop3, Prop4, Memo, AddTime, AddUser, ModifyTime, ModifyUser)
  VALUES('120000095120000522120000556', '2120999116104000T', '120000095120000522', '0', 555, '宜兴埠支行', 3, 'Y', NULL, NULL, 'admin', 'administrator', 'admin', '06', '120000556', 

NULL, NULL, NULL, 

'20100824', 'admin', NULL, NULL)
;
INSERT INTO zdbranch(BranchInnerCode, BranchCode, ParentInnerCode, Type, OrderFlag, Name, TreeLevel, IsLeaf, Phone, Fax, Manager, Leader1, 

Leader2, Prop1, Prop2, Prop3, Prop4, Memo, AddTime, AddUser, ModifyTime, ModifyUser)
  VALUES('120000095120000522120000557', '21209991161120002', '120000095120000522', '0', 556, '北辰区宜北分理处', 3, 'Y', NULL, NULL, 'admin', 'administrator', 'admin', '06', 

'120000557', NULL, NULL, NULL, 

'20100824', 'admin', NULL, NULL)
;
INSERT INTO zdbranch(BranchInnerCode, BranchCode, ParentInnerCode, Type, OrderFlag, Name, TreeLevel, IsLeaf, Phone, Fax, Manager, Leader1, 

Leader2, Prop1, Prop2, Prop3, Prop4, Memo, AddTime, AddUser, ModifyTime, ModifyUser)
  VALUES('120000095120000522120000558', '2120999116114000N', '120000095120000522', '0', 557, '北辰区宜白路储蓄所', 3, 'Y', NULL, NULL, 'admin', 'administrator', 'admin', 

'06', '120000558', NULL, NULL, NULL, 

'20100824', 'admin', NULL, NULL)
;
INSERT INTO zdbranch(BranchInnerCode, BranchCode, ParentInnerCode, Type, OrderFlag, Name, TreeLevel, IsLeaf, Phone, Fax, Manager, Leader1, 

Leader2, Prop1, Prop2, Prop3, Prop4, Memo, AddTime, AddUser, ModifyTime, ModifyUser)
  VALUES('120000095120000522120000556120000559', '2120999116104103J', '120000095120000522120000556', '0', 558, '北辰支行桥北储蓄所', 4, 'Y', NULL, NULL, 'admin', 'administrator', 'admin', 

'10', '120000559', NULL, NULL, NULL, 

'20100824', 'admin', NULL, NULL)
;
INSERT INTO zdbranch(BranchInnerCode, BranchCode, ParentInnerCode, Type, OrderFlag, Name, TreeLevel, IsLeaf, Phone, Fax, Manager, Leader1, 

Leader2, Prop1, Prop2, Prop3, Prop4, Memo, AddTime, AddUser, ModifyTime, ModifyUser)
  VALUES('120000095120000522120000560', '2120999116115000I', '120000095120000522', '0', 559, '北辰支行北三环营业所', 3, 'Y', NULL, NULL, 'admin', 'administrator', 'admin', 

'10', '120000560', NULL, NULL, 

NULL, '20100824', 'admin', NULL, NULL)
;
INSERT INTO zdbranch(BranchInnerCode, BranchCode, ParentInnerCode, Type, OrderFlag, Name, TreeLevel, IsLeaf, Phone, Fax, Manager, Leader1, 

Leader2, Prop1, Prop2, Prop3, Prop4, Memo, AddTime, AddUser, ModifyTime, ModifyUser)
  VALUES('120000095120000522120000561', '2120999116105000O', '120000095120000522', '0', 560, '小淀支行', 3, 'Y', NULL, NULL, 'admin', 'administrator', 'admin', '06', '120000561', 

NULL, NULL, NULL, '20100824', 'admin', NULL, NULL);
INSERT INTO zdbranch(BranchInnerCode, BranchCode, ParentInnerCode, Type, OrderFlag, Name, TreeLevel, IsLeaf, Phone, Fax, Manager, Leader1, 

Leader2, Prop1, Prop2, Prop3, Prop4, Memo, AddTime, AddUser, ModifyTime, ModifyUser)
  VALUES('120000095120000522120000562', '2120999116106000J', '120000095120000522', '0', 561, '青光支行', 3, 'Y', NULL, NULL, 'admin', 'administrator', 'admin', '06', '120000562', 

NULL, NULL, NULL, '20100824', 'admin', NULL, NULL);
INSERT INTO zdbranch(BranchInnerCode, BranchCode, ParentInnerCode, Type, OrderFlag, Name, TreeLevel, IsLeaf, Phone, Fax, Manager, Leader1, 

Leader2, Prop1, Prop2, Prop3, Prop4, Memo, AddTime, AddUser, ModifyTime, ModifyUser)
  VALUES('120000095120000522120000563', '2120999116116000D', '120000095120000522', '0', 562, '北辰支行韩家墅营业所', 3, 'Y', NULL, NULL, 'admin', 'administrator', 'admin', 

'10', '120000563', NULL, NULL, 

NULL, '20100824', 'admin', NULL, NULL)
;
INSERT INTO zdbranch(BranchInnerCode, BranchCode, ParentInnerCode, Type, OrderFlag, Name, TreeLevel, IsLeaf, Phone, Fax, Manager, Leader1, 

Leader2, Prop1, Prop2, Prop3, Prop4, Memo, AddTime, AddUser, ModifyTime, ModifyUser)
  VALUES('120000095120000522120000564', '21209991161170008', '120000095120000522', '0', 563, '北辰区双街分理处', 3, 'Y', NULL, NULL, 'admin', 'administrator', 'admin', '06', 

'120000564', NULL, NULL, NULL, 

'20100824', 'admin', NULL, NULL)
;
INSERT INTO zdbranch(BranchInnerCode, BranchCode, ParentInnerCode, Type, OrderFlag, Name, TreeLevel, IsLeaf, Phone, Fax, Manager, Leader1, 

Leader2, Prop1, Prop2, Prop3, Prop4, Memo, AddTime, AddUser, ModifyTime, ModifyUser)
  VALUES('120000095120000522120000565', '2120999116107000E', '120000095120000522', '0', 564, '西堤头支行', 3, 'Y', NULL, NULL, 'admin', 'administrator', 'admin', '06', '120000565', 

NULL, NULL, NULL, 

'20100824', 'admin', NULL, NULL)
;
INSERT INTO zdbranch(BranchInnerCode, BranchCode, ParentInnerCode, Type, OrderFlag, Name, TreeLevel, IsLeaf, Phone, Fax, Manager, Leader1, 

Leader2, Prop1, Prop2, Prop3, Prop4, Memo, AddTime, AddUser, ModifyTime, ModifyUser)
  VALUES('120000095120000522120000565120000566', '21209991161071018', '120000095120000522120000565', '0', 565, '北辰支行霍庄子营业所', 4, 'Y', NULL, NULL, 'admin', 'administrator', 'admin', 

'10', '120000566', NULL, NULL, 

NULL, '20100824', 'admin', NULL, NULL)
;
INSERT INTO zdbranch(BranchInnerCode, BranchCode, ParentInnerCode, Type, OrderFlag, Name, TreeLevel, IsLeaf, Phone, Fax, Manager, Leader1, 

Leader2, Prop1, Prop2, Prop3, Prop4, Memo, AddTime, AddUser, ModifyTime, ModifyUser)
  VALUES('120000095120000522120000567', '21209991161080009', '120000095120000522', '0', 566, '柳滩支行', 3, 'Y', NULL, NULL, 'admin', 'administrator', 'admin', '06', '120000567', 

NULL, NULL, NULL, '20100824', 'admin', NULL, NULL);
INSERT INTO zdbranch(BranchInnerCode, BranchCode, ParentInnerCode, Type, OrderFlag, Name, TreeLevel, IsLeaf, Phone, Fax, Manager, Leader1, Leader2, Prop1, Prop2, Prop3, Prop4, Memo, AddTime, AddUser, ModifyTime, ModifyUser)
  VALUES('120000095120000522120000568', '21209991161180003', '120000095120000522', '0', 567, '北辰支行民族储蓄所', 3, 'Y', NULL, NULL, 'admin', 'administrator', 'admin', '10', '120000568', 
NULL, NULL, NULL, '20100824', 'admin', NULL, NULL);


INSERT INTO zdbranch(BranchInnerCode, BranchCode, ParentInnerCode, Type, OrderFlag, Name, TreeLevel, IsLeaf, Phone, Fax, Manager, Leader1, 

Leader2, Prop1, Prop2, Prop3, Prop4, Memo, AddTime, AddUser, ModifyTime, ModifyUser)
  VALUES('120000095120000522120000569', '2120999116113000S', '120000095120000522', '0', 568, '北辰区天穆分理处', 3, 'Y', NULL, NULL, 'admin', 'administrator', 'admin', '06', 

'120000569', NULL, NULL, NULL, 

'20100824', 'admin', NULL, NULL)
;
INSERT INTO zdbranch(BranchInnerCode, BranchCode, ParentInnerCode, Type, OrderFlag, Name, TreeLevel, IsLeaf, Phone, Fax, Manager, Leader1, 

Leader2, Prop1, Prop2, Prop3, Prop4, Memo, AddTime, AddUser, ModifyTime, ModifyUser)
  VALUES('120000095120000522120000570', '21209991161090004', '120000095120000522', '0', 569, '刘家房子支行', 3, 'Y', NULL, NULL, 'admin', 'administrator', 'admin', '06', 

'120000570', NULL, NULL, NULL, 

'20100824', 'admin', NULL, NULL)
;
INSERT INTO zdbranch(BranchInnerCode, BranchCode, ParentInnerCode, Type, OrderFlag, Name, TreeLevel, IsLeaf, Phone, Fax, Manager, Leader1, 

Leader2, Prop1, Prop2, Prop3, Prop4, Memo, AddTime, AddUser, ModifyTime, ModifyUser)
  VALUES('120000095120000522120000571', '2120999116122000R', '120000095120000522', '0', 570, '北辰区商学院营业所', 3, 'Y', NULL, NULL, 'admin', 'administrator', 'admin', 

'06', '120000571', NULL, NULL, NULL, 

'20100824', 'admin', NULL, NULL)
;
INSERT INTO zdbranch(BranchInnerCode, BranchCode, ParentInnerCode, Type, OrderFlag, Name, TreeLevel, IsLeaf, Phone, Fax, Manager, Leader1, 

Leader2, Prop1, Prop2, Prop3, Prop4, Memo, AddTime, AddUser, ModifyTime, ModifyUser)
  VALUES('120000095120000522120000572', '2120999116110000C', '120000095120000522', '0', 571, '佳荣里支行', 3, 'Y', NULL, NULL, 'admin', 'administrator', 'admin', '06', '120000572', 

NULL, NULL, NULL, 

'20100824', 'admin', NULL, NULL)
;
INSERT INTO zdbranch(BranchInnerCode, BranchCode, ParentInnerCode, Type, OrderFlag, Name, TreeLevel, IsLeaf, Phone, Fax, Manager, Leader1, 

Leader2, Prop1, Prop2, Prop3, Prop4, Memo, AddTime, AddUser, ModifyTime, ModifyUser)
  VALUES('120000095120000522120000573', '2120999116123000M', '120000095120000522', '0', 572, '北辰支行礼貌大街分理处', 3, 'Y', NULL, NULL, 'admin', 'administrator', 'admin', '10', 

'120000573', NULL, NULL, NULL, 

'20100824', 'admin', NULL, NULL)
;
INSERT INTO zdbranch(BranchInnerCode, BranchCode, ParentInnerCode, Type, OrderFlag, Name, TreeLevel, IsLeaf, Phone, Fax, Manager, Leader1, 

Leader2, Prop1, Prop2, Prop3, Prop4, Memo, AddTime, AddUser, ModifyTime, ModifyUser)
  VALUES('120000095120000574', '2120999117000000H', '120000095', '0', 573, '武清支行', 2, 'Y', NULL, NULL, 'admin', 'administrator', 'admin', '04', '120000574', 

NULL, NULL, NULL, '20100824', 'admin', NULL, NULL);
INSERT INTO zdbranch(BranchInnerCode, BranchCode, ParentInnerCode, Type, OrderFlag, Name, TreeLevel, IsLeaf, Phone, Fax, Manager, Leader1, 

Leader2, Prop1, Prop2, Prop3, Prop4, Memo, AddTime, AddUser, ModifyTime, ModifyUser)
  VALUES('120000095120000574120000575', '2120999117001000C', '120000095120000574', '0', 574, '武清支行机关', 3, 'Y', NULL, NULL, 'admin', 'administrator', 'admin', '04', '120000575', 

NULL, NULL, NULL, 

'20100824', 'admin', NULL, NULL)
;
INSERT INTO zdbranch(BranchInnerCode, BranchCode, ParentInnerCode, Type, OrderFlag, Name, TreeLevel, IsLeaf, Phone, Fax, Manager, Leader1, 

Leader2, Prop1, Prop2, Prop3, Prop4, Memo, AddTime, AddUser, ModifyTime, ModifyUser)
  VALUES('120000095120000574120000575120000576', '21209991170010036', '120000095120000574120000575', '0', 575, '武清支行离退', 4, 'Y', NULL, NULL, 'admin', 'administrator', 'admin', '04', '120000576', 

NULL, NULL, NULL, 

'20100824', 'admin', NULL, NULL)
;
INSERT INTO zdbranch(BranchInnerCode, BranchCode, ParentInnerCode, Type, OrderFlag, Name, TreeLevel, IsLeaf, Phone, Fax, Manager, Leader1, 

Leader2, Prop1, Prop2, Prop3, Prop4, Memo, AddTime, AddUser, ModifyTime, ModifyUser)
  VALUES('120000095120000574120000575120000577', '21209991170010044', '120000095120000574120000575', '0', 576, '武清支行内退', 4, 'Y', NULL, NULL, 'admin', 'administrator', 'admin', '04', '120000577', 

NULL, NULL, NULL, 

'20100824', 'admin', NULL, NULL)
;
INSERT INTO zdbranch(BranchInnerCode, BranchCode, ParentInnerCode, Type, OrderFlag, Name, TreeLevel, IsLeaf, Phone, Fax, Manager, Leader1, 

Leader2, Prop1, Prop2, Prop3, Prop4, Memo, AddTime, AddUser, ModifyTime, ModifyUser)
  VALUES('120000095120000574120000575120000578', '21209991170010028', '120000095120000574120000575', '0', 577, '武清支行综合管理部', 4, 'Y', NULL, NULL, 'admin', 'administrator', 'admin', '04', 

'120000578', NULL, NULL, NULL, 

'20100824', 'admin', NULL, NULL)
;
INSERT INTO zdbranch(BranchInnerCode, BranchCode, ParentInnerCode, Type, OrderFlag, Name, TreeLevel, IsLeaf, Phone, Fax, Manager, Leader1, 

Leader2, Prop1, Prop2, Prop3, Prop4, Memo, AddTime, AddUser, ModifyTime, ModifyUser)
  VALUES('120000095120000574120000575120000579', '2120999117001006V', '120000095120000574120000575', '0', 578, '武清支行离退休', 4, 'Y', NULL, NULL, 'admin', 'administrator', 'admin', '04', 

'120000579', NULL, NULL, NULL, 

'20100824', 'admin', NULL, NULL)
;
INSERT INTO zdbranch(BranchInnerCode, BranchCode, ParentInnerCode, Type, OrderFlag, Name, TreeLevel, IsLeaf, Phone, Fax, Manager, Leader1, 

Leader2, Prop1, Prop2, Prop3, Prop4, Memo, AddTime, AddUser, ModifyTime, ModifyUser)
  VALUES('120000095120000574120000575120000580', '21209991170010052', '120000095120000574120000575', '0', 579, '武清支行行长室', 4, 'Y', NULL, NULL, 'admin', 'administrator', 'admin', '04', 

'120000580', NULL, NULL, NULL, 

'20100824', 'admin', NULL, NULL)
;
INSERT INTO zdbranch(BranchInnerCode, BranchCode, ParentInnerCode, Type, OrderFlag, Name, TreeLevel, IsLeaf, Phone, Fax, Manager, Leader1, 

Leader2, Prop1, Prop2, Prop3, Prop4, Memo, AddTime, AddUser, ModifyTime, ModifyUser)
  VALUES('120000095120000574120000575120000581', '2120999117001007T', '120000095120000574120000575', '0', 580, '武清支行金融超市', 4, 'Y', NULL, NULL, 'admin', 'administrator', 'admin', '04', 

'120000581', NULL, NULL, NULL, 

'20100824', 'admin', NULL, NULL)
;
INSERT INTO zdbranch(BranchInnerCode, BranchCode, ParentInnerCode, Type, OrderFlag, Name, TreeLevel, IsLeaf, Phone, Fax, Manager, Leader1, 

Leader2, Prop1, Prop2, Prop3, Prop4, Memo, AddTime, AddUser, ModifyTime, ModifyUser)
  VALUES('120000095120000574120000575120000582', '2120999117001008R', '120000095120000574120000575', '0', 581, '武清支行客户一部', 4, 'Y', NULL, NULL, 'admin', 'administrator', 'admin', '04', 

'120000582', NULL, NULL, NULL, 

'20100824', 'admin', NULL, NULL)
;
INSERT INTO zdbranch(BranchInnerCode, BranchCode, ParentInnerCode, Type, OrderFlag, Name, TreeLevel, IsLeaf, Phone, Fax, Manager, Leader1, 

Leader2, Prop1, Prop2, Prop3, Prop4, Memo, AddTime, AddUser, ModifyTime, ModifyUser)
  VALUES('120000095120000574120000575120000583', '2120999117001009P', '120000095120000574120000575', '0', 582, '武清支行财会运营部', 4, 'Y', NULL, NULL, 'admin', 'administrator', 'admin', '04', 

'120000583', NULL, NULL, NULL, 

'20100824', 'admin', NULL, NULL)
;
INSERT INTO zdbranch(BranchInnerCode, BranchCode, ParentInnerCode, Type, OrderFlag, Name, TreeLevel, IsLeaf, Phone, Fax, Manager, Leader1, 

Leader2, Prop1, Prop2, Prop3, Prop4, Memo, AddTime, AddUser, ModifyTime, ModifyUser)
  VALUES('120000095120000574120000575120000584', '21209991170010109', '120000095120000574120000575', '0', 583, '武清支行第三储蓄所', 4, 'Y', NULL, NULL, 'admin', 'administrator', 'admin', '04', 

'120000584', NULL, NULL, NULL, 

'20100824', 'admin', NULL, NULL)
;
INSERT INTO zdbranch(BranchInnerCode, BranchCode, ParentInnerCode, Type, OrderFlag, Name, TreeLevel, IsLeaf, Phone, Fax, Manager, Leader1, 

Leader2, Prop1, Prop2, Prop3, Prop4, Memo, AddTime, AddUser, ModifyTime, ModifyUser)
  VALUES('120000095120000574120000575120000585', '21209991170010117', '120000095120000574120000575', '0', 584, '武清支行客户三部', 4, 'Y', NULL, NULL, 'admin', 'administrator', 'admin', '04', 

'120000585', NULL, NULL, NULL, 

'20100824', 'admin', NULL, NULL)
;
INSERT INTO zdbranch(BranchInnerCode, BranchCode, ParentInnerCode, Type, OrderFlag, Name, TreeLevel, IsLeaf, Phone, Fax, Manager, Leader1, 

Leader2, Prop1, Prop2, Prop3, Prop4, Memo, AddTime, AddUser, ModifyTime, ModifyUser)
  VALUES('120000095120000574120000575120000586', '21209991170010125', '120000095120000574120000575', '0', 585, '武清支行计划财务部', 4, 'Y', NULL, NULL, 'admin', 'administrator', 'admin', '04', 

'120000586', NULL, NULL, NULL, 

'20100824', 'admin', NULL, NULL)
;
INSERT INTO zdbranch(BranchInnerCode, BranchCode, ParentInnerCode, Type, OrderFlag, Name, TreeLevel, IsLeaf, Phone, Fax, Manager, Leader1, 

Leader2, Prop1, Prop2, Prop3, Prop4, Memo, AddTime, AddUser, ModifyTime, ModifyUser)
  VALUES('120000095120000574120000575120000587', '21209991170010133', '120000095120000574120000575', '0', 586, '武清支行监督保卫/内控合规部', 4, 'Y', NULL, NULL, 'admin', 'administrator', 

'admin', '04', '120000587', NULL, NULL, 

NULL, '20100824', 'admin', NULL, NULL)
;
INSERT INTO zdbranch(BranchInnerCode, BranchCode, ParentInnerCode, Type, OrderFlag, Name, TreeLevel, IsLeaf, Phone, Fax, Manager, Leader1, 

Leader2, Prop1, Prop2, Prop3, Prop4, Memo, AddTime, AddUser, ModifyTime, ModifyUser)
  VALUES('120000095120000574120000575120000588', '21209991170010141', '120000095120000574120000575', '0', 587, '武清支行离退休人员', 4, 'Y', NULL, NULL, 'admin', 'administrator', 'admin', '04', 

'120000588', NULL, NULL, NULL, 

'20100824', 'admin', NULL, NULL)
;
INSERT INTO zdbranch(BranchInnerCode, BranchCode, ParentInnerCode, Type, OrderFlag, Name, TreeLevel, IsLeaf, Phone, Fax, Manager, Leader1, 

Leader2, Prop1, Prop2, Prop3, Prop4, Memo, AddTime, AddUser, ModifyTime, ModifyUser)
  VALUES('120000095120000574120000575120000589', '2120999117001015U', '120000095120000574120000575', '0', 588, '武清支行客户二部', 4, 'Y', NULL, NULL, 'admin', 'administrator', 'admin', '04', 

'120000589', NULL, NULL, NULL, 

'20100824', 'admin', NULL, NULL)
;
INSERT INTO zdbranch(BranchInnerCode, BranchCode, ParentInnerCode, Type, OrderFlag, Name, TreeLevel, IsLeaf, Phone, Fax, Manager, Leader1, 

Leader2, Prop1, Prop2, Prop3, Prop4, Memo, AddTime, AddUser, ModifyTime, ModifyUser)
  VALUES('120000095120000574120000575120000590', '2120999117001016S', '120000095120000574120000575', '0', 589, '武清支行公司业务部', 4, 'Y', NULL, NULL, 'admin', 'administrator', 'admin', '04', 

'120000590', NULL, NULL, NULL, 

'20100824', 'admin', NULL, NULL)
;
INSERT INTO zdbranch(BranchInnerCode, BranchCode, ParentInnerCode, Type, OrderFlag, Name, TreeLevel, IsLeaf, Phone, Fax, Manager, Leader1, 

Leader2, Prop1, Prop2, Prop3, Prop4, Memo, AddTime, AddUser, ModifyTime, ModifyUser)
  VALUES('120000095120000574120000575120000591', '2120999117001017Q', '120000095120000574120000575', '0', 590, '武清支行信贷管理部', 4, 'Y', NULL, NULL, 'admin', 'administrator', 'admin', '04', 

'120000591', NULL, NULL, NULL, 

'20100824', 'admin', NULL, NULL)
;
INSERT INTO zdbranch(BranchInnerCode, BranchCode, ParentInnerCode, Type, OrderFlag, Name, TreeLevel, IsLeaf, Phone, Fax, Manager, Leader1, 

Leader2, Prop1, Prop2, Prop3, Prop4, Memo, AddTime, AddUser, ModifyTime, ModifyUser)
  VALUES('120000095120000574120000575120000592', '2120999117001018O', '120000095120000574120000575', '0', 591, '武清支行个人金融部', 4, 'Y', NULL, NULL, 'admin', 'administrator', 'admin', '04', 

'120000592', NULL, NULL, NULL, 

'20100824', 'admin', NULL, NULL)
;
INSERT INTO zdbranch(BranchInnerCode, BranchCode, ParentInnerCode, Type, OrderFlag, Name, TreeLevel, IsLeaf, Phone, Fax, Manager, Leader1, 

Leader2, Prop1, Prop2, Prop3, Prop4, Memo, AddTime, AddUser, ModifyTime, ModifyUser)
  VALUES('120000095120000574120000575120000593', '2120999117001019M', '120000095120000574120000575', '0', 592, '武清支行内部分流人员', 4, 'Y', NULL, NULL, 'admin', 'administrator', 'admin', 

'04', '120000593', NULL, NULL, NULL, 

'20100824', 'admin', NULL, NULL)
;
INSERT INTO zdbranch(BranchInnerCode, BranchCode, ParentInnerCode, Type, OrderFlag, Name, TreeLevel, IsLeaf, Phone, Fax, Manager, Leader1, 

Leader2, Prop1, Prop2, Prop3, Prop4, Memo, AddTime, AddUser, ModifyTime, ModifyUser)
  VALUES('120000095120000574120000575120000594', '21209991170010206', '120000095120000574120000575', '0', 593, '武清支行团结路储蓄所', 4, 'Y', NULL, NULL, 'admin', 'administrator', 'admin', 

'04', '120000594', NULL, NULL, NULL, 

'20100824', 'admin', NULL, NULL)
;
INSERT INTO zdbranch(BranchInnerCode, BranchCode, ParentInnerCode, Type, OrderFlag, Name, TreeLevel, IsLeaf, Phone, Fax, Manager, Leader1, 

Leader2, Prop1, Prop2, Prop3, Prop4, Memo, AddTime, AddUser, ModifyTime, ModifyUser)
  VALUES('120000095120000574120000575120000595', '2120999117001001A', '120000095120000574120000575', '0', 594, '武清支行育才北路储蓄所', 4, 'Y', NULL, NULL, 'admin', 'administrator', 'admin', 

'04', '120000595', NULL, NULL, 

NULL, '20100824', 'admin', NULL, NULL)
;
INSERT INTO zdbranch(BranchInnerCode, BranchCode, ParentInnerCode, Type, OrderFlag, Name, TreeLevel, IsLeaf, Phone, Fax, Manager, Leader1, 

Leader2, Prop1, Prop2, Prop3, Prop4, Memo, AddTime, AddUser, ModifyTime, ModifyUser)
  VALUES('120000095120000574120000596', '21209991171010005', '120000095120000574', '0', 595, '王庆坨支行', 3, 'Y', NULL, NULL, 'admin', 'administrator', 'admin', '06', '120000596', 

NULL, NULL, NULL, '20100824', 'admin', NULL, NULL);
INSERT INTO zdbranch(BranchInnerCode, BranchCode, ParentInnerCode, Type, OrderFlag, Name, TreeLevel, IsLeaf, Phone, Fax, Manager, Leader1, 

Leader2, Prop1, Prop2, Prop3, Prop4, Memo, AddTime, AddUser, ModifyTime, ModifyUser)
  VALUES('120000095120000574120000597', '2120999117131000I', '120000095120000574', '0', 596, '武清新开区分理处', 3, 'Y', NULL, NULL, 'admin', 'administrator', 'admin', '06', 

'120000597', NULL, NULL, NULL, 

'20100824', 'admin', NULL, NULL)
;
INSERT INTO zdbranch(BranchInnerCode, BranchCode, ParentInnerCode, Type, OrderFlag, Name, TreeLevel, IsLeaf, Phone, Fax, Manager, Leader1, 

Leader2, Prop1, Prop2, Prop3, Prop4, Memo, AddTime, AddUser, ModifyTime, ModifyUser)
  VALUES('120000095120000574120000598', '21209991171020000', '120000095120000574', '0', 597, '武清大良支行', 3, 'Y', NULL, NULL, 'admin', 'administrator', 'admin', '06', '120000598', 

NULL, NULL, NULL, 

'20100824', 'admin', NULL, NULL)
;
INSERT INTO zdbranch(BranchInnerCode, BranchCode, ParentInnerCode, Type, OrderFlag, Name, TreeLevel, IsLeaf, Phone, Fax, Manager, Leader1, 

Leader2, Prop1, Prop2, Prop3, Prop4, Memo, AddTime, AddUser, ModifyTime, ModifyUser)
  VALUES('120000095120000574120000599', '2120999117126000U', '120000095120000574', '0', 598, '河北屯分理处', 3, 'Y', NULL, NULL, 'admin', 'administrator', 'admin', '06', '120000599', 

NULL, NULL, NULL, 

'20100824', 'admin', NULL, NULL)
;
INSERT INTO zdbranch(BranchInnerCode, BranchCode, ParentInnerCode, Type, OrderFlag, Name, TreeLevel, IsLeaf, Phone, Fax, Manager, Leader1, 

Leader2, Prop1, Prop2, Prop3, Prop4, Memo, AddTime, AddUser, ModifyTime, ModifyUser)
  VALUES('120000095120000574120000600', '2120999117120000T', '120000095120000574', '0', 599, '下伍旗分理处', 3, 'Y', NULL, NULL, 'admin', 'administrator', 'admin', '06', '120000600', 

NULL, NULL, NULL, 

'20100824', 'admin', NULL, NULL)
;
INSERT INTO zdbranch(BranchInnerCode, BranchCode, ParentInnerCode, Type, OrderFlag, Name, TreeLevel, IsLeaf, Phone, Fax, Manager, Leader1, 

Leader2, Prop1, Prop2, Prop3, Prop4, Memo, AddTime, AddUser, ModifyTime, ModifyUser)
  VALUES('120000095120000574120000601', '2120999117103000Q', '120000095120000574', '0', 600, '武清崔黄口支行', 3, 'Y', NULL, NULL, 'admin', 'administrator', 'admin', '06', 

'120000601', NULL, NULL, NULL, 

'20100824', 'admin', NULL, NULL)
;
INSERT INTO zdbranch(BranchInnerCode, BranchCode, ParentInnerCode, Type, OrderFlag, Name, TreeLevel, IsLeaf, Phone, Fax, Manager, Leader1, 

Leader2, Prop1, Prop2, Prop3, Prop4, Memo, AddTime, AddUser, ModifyTime, ModifyUser)
  VALUES('120000095120000574120000602', '2120999117121000O', '120000095120000574', '0', 601, '大黄堡分理处', 3, 'Y', NULL, NULL, 'admin', 'administrator', 'admin', '06', '120000602', 

NULL, NULL, NULL, 

'20100824', 'admin', NULL, NULL)
;
INSERT INTO zdbranch(BranchInnerCode, BranchCode, ParentInnerCode, Type, OrderFlag, Name, TreeLevel, IsLeaf, Phone, Fax, Manager, Leader1, 

Leader2, Prop1, Prop2, Prop3, Prop4, Memo, AddTime, AddUser, ModifyTime, ModifyUser)
  VALUES('120000095120000574120000603', '2120999117104000L', '120000095120000574', '0', 602, '大碱厂分理处', 3, 'Y', NULL, NULL, 'admin', 'administrator', 'admin', '06', '120000603', 

NULL, NULL, NULL, 

'20100824', 'admin', NULL, NULL)
;
INSERT INTO zdbranch(BranchInnerCode, BranchCode, ParentInnerCode, Type, OrderFlag, Name, TreeLevel, IsLeaf, Phone, Fax, Manager, Leader1, 

Leader2, Prop1, Prop2, Prop3, Prop4, Memo, AddTime, AddUser, ModifyTime, ModifyUser)
  VALUES('120000095120000574120000604', '2120999117105000G', '120000095120000574', '0', 603, '武清城关分理处', 3, 'Y', NULL, NULL, 'admin', 'administrator', 'admin', '06', 

'120000604', NULL, NULL, NULL, 

'20100824', 'admin', NULL, NULL)
;
INSERT INTO zdbranch(BranchInnerCode, BranchCode, ParentInnerCode, Type, OrderFlag, Name, TreeLevel, IsLeaf, Phone, Fax, Manager, Leader1, 

Leader2, Prop1, Prop2, Prop3, Prop4, Memo, AddTime, AddUser, ModifyTime, ModifyUser)
  VALUES('120000095120000574120000605', '2120999117106000B', '120000095120000574', '0', 604, '河西务支行', 3, 'Y', NULL, NULL, 'admin', 'administrator', 'admin', '06', '120000605', 

NULL, NULL, NULL, '20100824', 'admin', NULL, NULL);
INSERT INTO zdbranch(BranchInnerCode, BranchCode, ParentInnerCode, Type, OrderFlag, Name, TreeLevel, IsLeaf, Phone, Fax, Manager, Leader1, 

Leader2, Prop1, Prop2, Prop3, Prop4, Memo, AddTime, AddUser, ModifyTime, ModifyUser)
  VALUES('120000095120000574120000606', '2120999117136000O', '120000095120000574', '0', 605, '武清支行河西务储蓄所', 3, 'Y', NULL, NULL, 'admin', 'administrator', 'admin', 

'10', '120000606', NULL, NULL, 

NULL, '20100824', 'admin', NULL, NULL)
;
INSERT INTO zdbranch(BranchInnerCode, BranchCode, ParentInnerCode, Type, OrderFlag, Name, TreeLevel, IsLeaf, Phone, Fax, Manager, Leader1, 

Leader2, Prop1, Prop2, Prop3, Prop4, Memo, AddTime, AddUser, ModifyTime, ModifyUser)
  VALUES('120000095120000574120000607', '2120999117137000J', '120000095120000574', '0', 606, '高村储蓄所', 3, 'Y', NULL, NULL, 'admin', 'administrator', 'admin', '10', '120000607', 

NULL, NULL, NULL, '20100824', 'admin', NULL, NULL);
INSERT INTO zdbranch(BranchInnerCode, BranchCode, ParentInnerCode, Type, OrderFlag, Name, TreeLevel, IsLeaf, Phone, Fax, Manager, Leader1, 

Leader2, Prop1, Prop2, Prop3, Prop4, Memo, AddTime, AddUser, ModifyTime, ModifyUser)
  VALUES('120000095120000574120000608', '2120999117138000E', '120000095120000574', '0', 607, '武清支行大沙河储蓄所', 3, 'Y', NULL, NULL, 'admin', 'administrator', 'admin', 

'10', '120000608', NULL, NULL, 

NULL, '20100824', 'admin', NULL, NULL)
;
INSERT INTO zdbranch(BranchInnerCode, BranchCode, ParentInnerCode, Type, OrderFlag, Name, TreeLevel, IsLeaf, Phone, Fax, Manager, Leader1, 

Leader2, Prop1, Prop2, Prop3, Prop4, Memo, AddTime, AddUser, ModifyTime, ModifyUser)
  VALUES('120000095120000574120000609', '21209991171070006', '120000095120000574', '0', 608, '南蔡村支行', 3, 'Y', NULL, NULL, 'admin', 'administrator', 'admin', '06', '120000609', 

NULL, NULL, NULL, '20100824', 'admin', NULL, NULL);
INSERT INTO zdbranch(BranchInnerCode, BranchCode, ParentInnerCode, Type, OrderFlag, Name, TreeLevel, IsLeaf, Phone, Fax, Manager, Leader1, 

Leader2, Prop1, Prop2, Prop3, Prop4, Memo, AddTime, AddUser, ModifyTime, ModifyUser)
  VALUES('120000095120000574120000610', '2120999117122000J', '120000095120000574', '0', 609, '武清支行泗村店营业所', 3, 'Y', NULL, NULL, 'admin', 'administrator', 'admin', 

'06', '120000610', NULL, NULL, 

NULL, '20100824', 'admin', NULL, NULL)
;
INSERT INTO zdbranch(BranchInnerCode, BranchCode, ParentInnerCode, Type, OrderFlag, Name, TreeLevel, IsLeaf, Phone, Fax, Manager, Leader1, 

Leader2, Prop1, Prop2, Prop3, Prop4, Memo, AddTime, AddUser, ModifyTime, ModifyUser)
  VALUES('120000095120000574120000611', '2120999117118000Q', '120000095120000574', '0', 610, '大孟庄分理处', 3, 'Y', NULL, NULL, 'admin', 'administrator', 'admin', '06', '120000611', 

NULL, NULL, NULL, 

'20100824', 'admin', NULL, NULL)
;
INSERT INTO zdbranch(BranchInnerCode, BranchCode, ParentInnerCode, Type, OrderFlag, Name, TreeLevel, IsLeaf, Phone, Fax, Manager, Leader1, 

Leader2, Prop1, Prop2, Prop3, Prop4, Memo, AddTime, AddUser, ModifyTime, ModifyUser)
  VALUES('120000095120000574120000612', '21209991171080001', '120000095120000574', '0', 611, '黄花店支行', 3, 'Y', NULL, NULL, 'admin', 'administrator', 'admin', '06', '120000612', 

NULL, NULL, NULL, '20100824', 'admin', NULL, NULL);
INSERT INTO zdbranch(BranchInnerCode, BranchCode, ParentInnerCode, Type, OrderFlag, Name, TreeLevel, IsLeaf, Phone, Fax, Manager, Leader1, 

Leader2, Prop1, Prop2, Prop3, Prop4, Memo, AddTime, AddUser, ModifyTime, ModifyUser)
  VALUES('120000095120000574120000613', '2120999117127000P', '120000095120000574', '0', 612, '石各庄支行', 3, 'Y', NULL, NULL, 'admin', 'administrator', 'admin', '06', '120000613', 

NULL, NULL, NULL, '20100824', 'admin', NULL, NULL);
INSERT INTO zdbranch(BranchInnerCode, BranchCode, ParentInnerCode, Type, OrderFlag, Name, TreeLevel, IsLeaf, Phone, Fax, Manager, Leader1, 

Leader2, Prop1, Prop2, Prop3, Prop4, Memo, AddTime, AddUser, ModifyTime, ModifyUser)
  VALUES('120000095120000574120000614', '2120999117109000R', '120000095120000574', '0', 613, '梅厂支行', 3, 'Y', NULL, NULL, 'admin', 'administrator', 'admin', '06', '120000614', 

NULL, NULL, NULL, '20100824', 'admin', NULL, NULL);
INSERT INTO zdbranch(BranchInnerCode, BranchCode, ParentInnerCode, Type, OrderFlag, Name, TreeLevel, IsLeaf, Phone, Fax, Manager, Leader1, 

Leader2, Prop1, Prop2, Prop3, Prop4, Memo, AddTime, AddUser, ModifyTime, ModifyUser)
  VALUES('120000095120000574120000615', '21209991171100004', '120000095120000574', '0', 614, '杨村支行', 3, 'Y', NULL, NULL, 'admin', 'administrator', 'admin', '06', '120000615', 

NULL, NULL, NULL, '20100824', 'admin', NULL, NULL);
INSERT INTO zdbranch(BranchInnerCode, BranchCode, ParentInnerCode, Type, OrderFlag, Name, TreeLevel, IsLeaf, Phone, Fax, Manager, Leader1, 

Leader2, Prop1, Prop2, Prop3, Prop4, Memo, AddTime, AddUser, ModifyTime, ModifyUser)
  VALUES('120000095120000574120000616', '2120999117128000K', '120000095120000574', '0', 615, '栖仙分理处', 3, 'Y', NULL, NULL, 'admin', 'administrator', 'admin', '06', '120000616', 

NULL, NULL, NULL, '20100824', 'admin', NULL, NULL);
INSERT INTO zdbranch(BranchInnerCode, BranchCode, ParentInnerCode, Type, OrderFlag, Name, TreeLevel, IsLeaf, Phone, Fax, Manager, Leader1, 

Leader2, Prop1, Prop2, Prop3, Prop4, Memo, AddTime, AddUser, ModifyTime, ModifyUser)
  VALUES('120000095120000574120000617', '21209991171420007', '120000095120000574', '0', 616, '武清西环北路储蓄所', 3, 'Y', NULL, NULL, 'admin', 'administrator', 'admin', '10', 

'120000617', NULL, NULL, NULL, 

'20100824', 'admin', NULL, NULL)
;
INSERT INTO zdbranch(BranchInnerCode, BranchCode, ParentInnerCode, Type, OrderFlag, Name, TreeLevel, IsLeaf, Phone, Fax, Manager, Leader1, 

Leader2, Prop1, Prop2, Prop3, Prop4, Memo, AddTime, AddUser, ModifyTime, ModifyUser)
  VALUES('120000095120000574120000618', '21209991171430002', '120000095120000574', '0', 617, '武清支行松鹤园储蓄所', 3, 'Y', NULL, NULL, 'admin', 'administrator', 'admin', 

'10', '120000618', NULL, NULL, 

NULL, '20100824', 'admin', NULL, NULL)
;
INSERT INTO zdbranch(BranchInnerCode, BranchCode, ParentInnerCode, Type, OrderFlag, Name, TreeLevel, IsLeaf, Phone, Fax, Manager, Leader1, 

Leader2, Prop1, Prop2, Prop3, Prop4, Memo, AddTime, AddUser, ModifyTime, ModifyUser)
  VALUES('120000095120000574120000619', '2120999117123000E', '120000095120000574', '0', 618, '亨通花园分理处', 3, 'Y', NULL, NULL, 'admin', 'administrator', 'admin', '06', 

'120000619', NULL, NULL, NULL, 

'20100824', 'admin', NULL, NULL)
;
INSERT INTO zdbranch(BranchInnerCode, BranchCode, ParentInnerCode, Type, OrderFlag, Name, TreeLevel, IsLeaf, Phone, Fax, Manager, Leader1, 

Leader2, Prop1, Prop2, Prop3, Prop4, Memo, AddTime, AddUser, ModifyTime, ModifyUser)
  VALUES('120000095120000574120000620', '2120999117144000S', '120000095120000574', '0', 619, '武清支行招商场储蓄所', 3, 'Y', NULL, NULL, 'admin', 'administrator', 'admin', 

'10', '120000620', NULL, NULL, 

NULL, '20100824', 'admin', NULL, NULL)
;
INSERT INTO zdbranch(BranchInnerCode, BranchCode, ParentInnerCode, Type, OrderFlag, Name, TreeLevel, IsLeaf, Phone, Fax, Manager, Leader1, 

Leader2, Prop1, Prop2, Prop3, Prop4, Memo, AddTime, AddUser, ModifyTime, ModifyUser)
  VALUES('120000095120000574120000621', '21209991171240009', '120000095120000574', '0', 620, '武清支行豆张庄营业所', 3, 'Y', NULL, NULL, 'admin', 'administrator', 'admin', 

'06', '120000621', NULL, NULL, 

NULL, '20100824', 'admin', NULL, NULL)
;
INSERT INTO zdbranch(BranchInnerCode, BranchCode, ParentInnerCode, Type, OrderFlag, Name, TreeLevel, IsLeaf, Phone, Fax, Manager, Leader1, 

Leader2, Prop1, Prop2, Prop3, Prop4, Memo, AddTime, AddUser, ModifyTime, ModifyUser)
  VALUES('120000095120000574120000622', '2120999117112000P', '120000095120000574', '0', 621, '东马圈分理处', 3, 'Y', NULL, NULL, 'admin', 'administrator', 'admin', '06', '120000622', 

NULL, NULL, NULL, 

'20100824', 'admin', NULL, NULL)
;
INSERT INTO zdbranch(BranchInnerCode, BranchCode, ParentInnerCode, Type, OrderFlag, Name, TreeLevel, IsLeaf, Phone, Fax, Manager, Leader1, 

Leader2, Prop1, Prop2, Prop3, Prop4, Memo, AddTime, AddUser, ModifyTime, ModifyUser)
  VALUES('120000095120000574120000623', '2120999117111000U', '120000095120000574', '0', 622, '汊沽港支行', 3, 'Y', NULL, NULL, 'admin', 'administrator', 'admin', '06', '120000623', 

NULL, NULL, NULL, '20100824', 'admin', NULL, NULL);
INSERT INTO zdbranch(BranchInnerCode, BranchCode, ParentInnerCode, Type, OrderFlag, Name, TreeLevel, IsLeaf, Phone, Fax, Manager, Leader1, 

Leader2, Prop1, Prop2, Prop3, Prop4, Memo, AddTime, AddUser, ModifyTime, ModifyUser)
  VALUES('120000095120000574120000624', '21209991171250004', '120000095120000574', '0', 623, '武清支行六道口营业所', 3, 'Y', NULL, NULL, 'admin', 'administrator', 'admin', 

'06', '120000624', NULL, NULL, 

NULL, '20100824', 'admin', NULL, NULL)
;
INSERT INTO zdbranch(BranchInnerCode, BranchCode, ParentInnerCode, Type, OrderFlag, Name, TreeLevel, IsLeaf, Phone, Fax, Manager, Leader1, 

Leader2, Prop1, Prop2, Prop3, Prop4, Memo, AddTime, AddUser, ModifyTime, ModifyUser)
  VALUES('120000095120000574120000625', '2120999117115000A', '120000095120000574', '0', 624, '武清支行营业部', 3, 'Y', NULL, NULL, 'admin', 'administrator', 'admin', '06', 

'120000625', NULL, NULL, NULL, 

'20100824', 'admin', NULL, NULL)
;
INSERT INTO zdbranch(BranchInnerCode, BranchCode, ParentInnerCode, Type, OrderFlag, Name, TreeLevel, IsLeaf, Phone, Fax, Manager, Leader1, 

Leader2, Prop1, Prop2, Prop3, Prop4, Memo, AddTime, AddUser, ModifyTime, ModifyUser)
  VALUES('120000095120000574120000626', '21209991171390009', '120000095120000574', '0', 625, '育才北路储蓄所', 3, 'Y', NULL, NULL, 'admin', 'administrator', 'admin', '10', 

'120000626', NULL, NULL, NULL, 

'20100824', 'admin', NULL, NULL)
;
INSERT INTO zdbranch(BranchInnerCode, BranchCode, ParentInnerCode, Type, OrderFlag, Name, TreeLevel, IsLeaf, Phone, Fax, Manager, Leader1, 

Leader2, Prop1, Prop2, Prop3, Prop4, Memo, AddTime, AddUser, ModifyTime, ModifyUser)
  VALUES('120000095120000574120000627', '2120999117140000H', '120000095120000574', '0', 626, '武清支行第三储蓄所', 3, 'Y', NULL, NULL, 'admin', 'administrator', 'admin', 

'10', '120000627', NULL, NULL, NULL, 

'20100824', 'admin', NULL, NULL)
;
INSERT INTO zdbranch(BranchInnerCode, BranchCode, ParentInnerCode, Type, OrderFlag, Name, TreeLevel, IsLeaf, Phone, Fax, Manager, Leader1, 

Leader2, Prop1, Prop2, Prop3, Prop4, Memo, AddTime, AddUser, ModifyTime, ModifyUser)
  VALUES('120000095120000574120000628', '2120999117129000F', '120000095120000574', '0', 627, '振华西道分理处', 3, 'Y', NULL, NULL, 'admin', 'administrator', 'admin', '06', 

'120000628', NULL, NULL, NULL, 

'20100824', 'admin', NULL, NULL)
;
INSERT INTO zdbranch(BranchInnerCode, BranchCode, ParentInnerCode, Type, OrderFlag, Name, TreeLevel, IsLeaf, Phone, Fax, Manager, Leader1, 

Leader2, Prop1, Prop2, Prop3, Prop4, Memo, AddTime, AddUser, ModifyTime, ModifyUser)
  VALUES('120000095120000574120000629', '2120999117141000C', '120000095120000574', '0', 628, '武清支行团结路储蓄所', 3, 'Y', NULL, NULL, 'admin', 'administrator', 'admin', 

'10', '120000629', NULL, NULL, 

NULL, '20100824', 'admin', NULL, NULL)
;
INSERT INTO zdbranch(BranchInnerCode, BranchCode, ParentInnerCode, Type, OrderFlag, Name, TreeLevel, IsLeaf, Phone, Fax, Manager, Leader1, 

Leader2, Prop1, Prop2, Prop3, Prop4, Memo, AddTime, AddUser, ModifyTime, ModifyUser)
  VALUES('120000095120000574120000630', '21209991171160005', '120000095120000574', '0', 629, '曹子里分理处', 3, 'Y', NULL, NULL, 'admin', 'administrator', 'admin', '06', '120000630', 

NULL, NULL, NULL, 

'20100824', 'admin', NULL, NULL)
;
INSERT INTO zdbranch(BranchInnerCode, BranchCode, ParentInnerCode, Type, OrderFlag, Name, TreeLevel, IsLeaf, Phone, Fax, Manager, Leader1, 

Leader2, Prop1, Prop2, Prop3, Prop4, Memo, AddTime, AddUser, ModifyTime, ModifyUser)
  VALUES('120000095120000574120000631', '21209991171170000', '120000095120000574', '0', 630, '武清开发区支行', 3, 'Y', NULL, NULL, 'admin', 'administrator', 'admin', '06', 

'120000631', NULL, NULL, NULL, 

'20100824', 'admin', NULL, NULL)
;
INSERT INTO zdbranch(BranchInnerCode, BranchCode, ParentInnerCode, Type, OrderFlag, Name, TreeLevel, IsLeaf, Phone, Fax, Manager, Leader1, 

Leader2, Prop1, Prop2, Prop3, Prop4, Memo, AddTime, AddUser, ModifyTime, ModifyUser)
  VALUES('120000095120000574120000632', '2120999117119000L', '120000095120000574', '0', 631, '建国南路支行', 3, 'Y', NULL, NULL, 'admin', 'administrator', 'admin', '06', '120000632', 

NULL, NULL, NULL, 

'20100824', 'admin', NULL, NULL)
;
INSERT INTO zdbranch(BranchInnerCode, BranchCode, ParentInnerCode, Type, OrderFlag, Name, TreeLevel, IsLeaf, Phone, Fax, Manager, Leader1, 

Leader2, Prop1, Prop2, Prop3, Prop4, Memo, AddTime, AddUser, ModifyTime, ModifyUser)
  VALUES('120000095120000574120000633', '2120999117130000N', '120000095120000574', '0', 632, '武清支行建设南路营业所', 3, 'Y', NULL, NULL, 'admin', 'administrator', 'admin', 

'06', '120000633', NULL, NULL, 

NULL, '20100824', 'admin', NULL, NULL)
;
INSERT INTO zdbranch(BranchInnerCode, BranchCode, ParentInnerCode, Type, OrderFlag, Name, TreeLevel, IsLeaf, Phone, Fax, Manager, Leader1, 

Leader2, Prop1, Prop2, Prop3, Prop4, Memo, AddTime, AddUser, ModifyTime, ModifyUser)
  VALUES('120000095120000574120000634', '2120999117132000D', '120000095120000574', '0', 633, '和平里分理处', 3, 'Y', NULL, NULL, 'admin', 'administrator', 'admin', '06', '120000634', 

NULL, NULL, NULL, 

'20100824', 'admin', NULL, NULL)
;
INSERT INTO zdbranch(BranchInnerCode, BranchCode, ParentInnerCode, Type, OrderFlag, Name, TreeLevel, IsLeaf, Phone, Fax, Manager, Leader1, 

Leader2, Prop1, Prop2, Prop3, Prop4, Memo, AddTime, AddUser, ModifyTime, ModifyUser)
  VALUES('120000095120000574120000635', '2120999117145000N', '120000095120000574', '0', 634, '平安里储蓄所', 3, 'Y', NULL, NULL, 'admin', 'administrator', 'admin', '10', '120000635', 

NULL, NULL, NULL, 

'20100824', 'admin', NULL, NULL)
;
INSERT INTO zdbranch(BranchInnerCode, BranchCode, ParentInnerCode, Type, OrderFlag, Name, TreeLevel, IsLeaf, Phone, Fax, Manager, Leader1, 

Leader2, Prop1, Prop2, Prop3, Prop4, Memo, AddTime, AddUser, ModifyTime, ModifyUser)
  VALUES('120000095120000574120000636', '2120999117146000I', '120000095120000574', '0', 635, '武清支行广厦里储蓄所', 3, 'Y', NULL, NULL, 'admin', 'administrator', 'admin', 

'10', '120000636', NULL, NULL, 

NULL, '20100824', 'admin', NULL, NULL)
;
INSERT INTO zdbranch(BranchInnerCode, BranchCode, ParentInnerCode, Type, OrderFlag, Name, TreeLevel, IsLeaf, Phone, Fax, Manager, Leader1, 

Leader2, Prop1, Prop2, Prop3, Prop4, Memo, AddTime, AddUser, ModifyTime, ModifyUser)
  VALUES('120000095120000637', '21209991180000009', '120000095', '0', 636, '蓟县支行', 2, 'Y', NULL, NULL, 'admin', 'administrator', 'admin', '04', '120000637', 

NULL, NULL, NULL, '20100824', 'admin', NULL, NULL);
INSERT INTO zdbranch(BranchInnerCode, BranchCode, ParentInnerCode, Type, OrderFlag, Name, TreeLevel, IsLeaf, Phone, Fax, Manager, Leader1, 

Leader2, Prop1, Prop2, Prop3, Prop4, Memo, AddTime, AddUser, ModifyTime, ModifyUser)
  VALUES('120000095120000637120000638', '21209991180010004', '120000095120000637', '0', 637, '蓟县支行机关', 3, 'Y', NULL, NULL, 'admin', 'administrator', 'admin', '04', 

'120000638', NULL, NULL, NULL, 

'20100824', 'admin', NULL, NULL)
;
INSERT INTO zdbranch(BranchInnerCode, BranchCode, ParentInnerCode, Type, OrderFlag, Name, TreeLevel, IsLeaf, Phone, Fax, Manager, Leader1, 

Leader2, Prop1, Prop2, Prop3, Prop4, Memo, AddTime, AddUser, ModifyTime, ModifyUser)
  VALUES('120000095120000637120000638120000639', '21209991180010012', '120000095120000637120000638', '0', 638, '蓟县支行其他', 4, 'Y', NULL, NULL, 'admin', 'administrator', 'admin', '04', '120000639', 

NULL, NULL, NULL, 

'20100824', 'admin', NULL, NULL)
;
INSERT INTO zdbranch(BranchInnerCode, BranchCode, ParentInnerCode, Type, OrderFlag, Name, TreeLevel, IsLeaf, Phone, Fax, Manager, Leader1, 

Leader2, Prop1, Prop2, Prop3, Prop4, Memo, AddTime, AddUser, ModifyTime, ModifyUser)
  VALUES('120000095120000637120000638120000640', '2120999118001006N', '120000095120000637120000638', '0', 639, '蓟县综合管理部', 4, 'Y', NULL, NULL, 'admin', 'administrator', 'admin', '04', 

'120000640', NULL, NULL, NULL, 

'20100824', 'admin', NULL, NULL)
;
INSERT INTO zdbranch(BranchInnerCode, BranchCode, ParentInnerCode, Type, OrderFlag, Name, TreeLevel, IsLeaf, Phone, Fax, Manager, Leader1, 

Leader2, Prop1, Prop2, Prop3, Prop4, Memo, AddTime, AddUser, ModifyTime, ModifyUser)
  VALUES('120000095120000637120000638120000641', '2120999118001007L', '120000095120000637120000638', '0', 640, '蓟县支行行长室', 4, 'Y', NULL, NULL, 'admin', 'administrator', 'admin', '04', 

'120000641', NULL, NULL, NULL, 

'20100824', 'admin', NULL, NULL)
;
INSERT INTO zdbranch(BranchInnerCode, BranchCode, ParentInnerCode, Type, OrderFlag, Name, TreeLevel, IsLeaf, Phone, Fax, Manager, Leader1, 

Leader2, Prop1, Prop2, Prop3, Prop4, Memo, AddTime, AddUser, ModifyTime, ModifyUser)
  VALUES('120000095120000637120000638120000642', '2120999118001008J', '120000095120000637120000638', '0', 641, '蓟县支行内退人员', 4, 'Y', NULL, NULL, 'admin', 'administrator', 'admin', '04', 

'120000642', NULL, NULL, NULL, 

'20100824', 'admin', NULL, NULL)
;
INSERT INTO zdbranch(BranchInnerCode, BranchCode, ParentInnerCode, Type, OrderFlag, Name, TreeLevel, IsLeaf, Phone, Fax, Manager, Leader1, 

Leader2, Prop1, Prop2, Prop3, Prop4, Memo, AddTime, AddUser, ModifyTime, ModifyUser)
  VALUES('120000095120000637120000638120000643', '2120999118001009H', '120000095120000637120000638', '0', 642, '蓟县支行保险代理部', 4, 'Y', NULL, NULL, 'admin', 'administrator', 'admin', '04', 

'120000643', NULL, NULL, NULL, 

'20100824', 'admin', NULL, NULL)
;
INSERT INTO zdbranch(BranchInnerCode, BranchCode, ParentInnerCode, Type, OrderFlag, Name, TreeLevel, IsLeaf, Phone, Fax, Manager, Leader1, 

Leader2, Prop1, Prop2, Prop3, Prop4, Memo, AddTime, AddUser, ModifyTime, ModifyUser)
  VALUES('120000095120000637120000638120000644', '21209991180010101', '120000095120000637120000638', '0', 643, '蓟县支行风险资产部', 4, 'Y', NULL, NULL, 'admin', 'administrator', 'admin', '04', 

'120000644', NULL, NULL, NULL, 

'20100824', 'admin', NULL, NULL)
;
INSERT INTO zdbranch(BranchInnerCode, BranchCode, ParentInnerCode, Type, OrderFlag, Name, TreeLevel, IsLeaf, Phone, Fax, Manager, Leader1, 

Leader2, Prop1, Prop2, Prop3, Prop4, Memo, AddTime, AddUser, ModifyTime, ModifyUser)
  VALUES('120000095120000637120000638120000645', '2120999118001011U', '120000095120000637120000638', '0', 644, '蓟县支行国际业务部', 4, 'Y', NULL, NULL, 'admin', 'administrator', 'admin', '04', 

'120000645', NULL, NULL, NULL, 

'20100824', 'admin', NULL, NULL)
;
INSERT INTO zdbranch(BranchInnerCode, BranchCode, ParentInnerCode, Type, OrderFlag, Name, TreeLevel, IsLeaf, Phone, Fax, Manager, Leader1, 

Leader2, Prop1, Prop2, Prop3, Prop4, Memo, AddTime, AddUser, ModifyTime, ModifyUser)
  VALUES('120000095120000637120000638120000646', '2120999118001012S', '120000095120000637120000638', '0', 645, '蓟县财会运营部', 4, 'Y', NULL, NULL, 'admin', 'administrator', 'admin', '04', 

'120000646', NULL, NULL, NULL, 

'20100824', 'admin', NULL, NULL)
;
INSERT INTO zdbranch(BranchInnerCode, BranchCode, ParentInnerCode, Type, OrderFlag, Name, TreeLevel, IsLeaf, Phone, Fax, Manager, Leader1, 

Leader2, Prop1, Prop2, Prop3, Prop4, Memo, AddTime, AddUser, ModifyTime, ModifyUser)
  VALUES('120000095120000637120000638120000647', '2120999118001013Q', '120000095120000637120000638', '0', 646, '蓟县监察保卫/内控合规部', 4, 'Y', NULL, NULL, 'admin', 'administrator', 'admin', 

'04', '120000647', NULL, NULL, 

NULL, '20100824', 'admin', NULL, NULL)
;
INSERT INTO zdbranch(BranchInnerCode, BranchCode, ParentInnerCode, Type, OrderFlag, Name, TreeLevel, IsLeaf, Phone, Fax, Manager, Leader1, 

Leader2, Prop1, Prop2, Prop3, Prop4, Memo, AddTime, AddUser, ModifyTime, ModifyUser)
  VALUES('120000095120000637120000638120000648', '2120999118001014O', '120000095120000637120000638', '0', 647, '蓟县支行离退休人员', 4, 'Y', NULL, NULL, 'admin', 'administrator', 'admin', '04', 

'120000648', NULL, NULL, NULL, 

'20100824', 'admin', NULL, NULL)
;
INSERT INTO zdbranch(BranchInnerCode, BranchCode, ParentInnerCode, Type, OrderFlag, Name, TreeLevel, IsLeaf, Phone, Fax, Manager, Leader1, 

Leader2, Prop1, Prop2, Prop3, Prop4, Memo, AddTime, AddUser, ModifyTime, ModifyUser)
  VALUES('120000095120000637120000638120000649', '2120999118001015M', '120000095120000637120000638', '0', 648, '蓟县支行人力资源部', 4, 'Y', NULL, NULL, 'admin', 'administrator', 'admin', '04', 

'120000649', NULL, NULL, NULL, 

'20100824', 'admin', NULL, NULL)
;
INSERT INTO zdbranch(BranchInnerCode, BranchCode, ParentInnerCode, Type, OrderFlag, Name, TreeLevel, IsLeaf, Phone, Fax, Manager, Leader1, 

Leader2, Prop1, Prop2, Prop3, Prop4, Memo, AddTime, AddUser, ModifyTime, ModifyUser)
  VALUES('120000095120000637120000638120000650', '2120999118001016K', '120000095120000637120000638', '0', 649, '蓟县信贷管理部', 4, 'Y', NULL, NULL, 'admin', 'administrator', 'admin', '04', 

'120000650', NULL, NULL, NULL, 

'20100824', 'admin', NULL, NULL)
;
INSERT INTO zdbranch(BranchInnerCode, BranchCode, ParentInnerCode, Type, OrderFlag, Name, TreeLevel, IsLeaf, Phone, Fax, Manager, Leader1, 

Leader2, Prop1, Prop2, Prop3, Prop4, Memo, AddTime, AddUser, ModifyTime, ModifyUser)
  VALUES('120000095120000637120000638120000651', '2120999118001002V', '120000095120000637120000638', '0', 650, '蓟县个人金融部', 4, 'Y', NULL, NULL, 'admin', 'administrator', 'admin', '04', 

'120000651', NULL, NULL, NULL, 

'20100824', 'admin', NULL, NULL)
;
INSERT INTO zdbranch(BranchInnerCode, BranchCode, ParentInnerCode, Type, OrderFlag, Name, TreeLevel, IsLeaf, Phone, Fax, Manager, Leader1, 

Leader2, Prop1, Prop2, Prop3, Prop4, Memo, AddTime, AddUser, ModifyTime, ModifyUser)
  VALUES('120000095120000637120000638120000652', '2120999118001003T', '120000095120000637120000638', '0', 651, '蓟县公司业务部', 4, 'Y', NULL, NULL, 'admin', 'administrator', 'admin', '04', 

'120000652', NULL, NULL, NULL, 

'20100824', 'admin', NULL, NULL)
;
INSERT INTO zdbranch(BranchInnerCode, BranchCode, ParentInnerCode, Type, OrderFlag, Name, TreeLevel, IsLeaf, Phone, Fax, Manager, Leader1, 

Leader2, Prop1, Prop2, Prop3, Prop4, Memo, AddTime, AddUser, ModifyTime, ModifyUser)
  VALUES('120000095120000637120000638120000653', '2120999118001004R', '120000095120000637120000638', '0', 652, '蓟县支行内部分流人员', 4, 'Y', NULL, NULL, 'admin', 'administrator', 'admin', 

'04', '120000653', NULL, NULL, NULL, 

'20100824', 'admin', NULL, NULL)
;
INSERT INTO zdbranch(BranchInnerCode, BranchCode, ParentInnerCode, Type, OrderFlag, Name, TreeLevel, IsLeaf, Phone, Fax, Manager, Leader1, 

Leader2, Prop1, Prop2, Prop3, Prop4, Memo, AddTime, AddUser, ModifyTime, ModifyUser)
  VALUES('120000095120000637120000654', '2120999118101000S', '120000095120000637', '0', 653, '蓟县文昌街支行', 3, 'Y', NULL, NULL, 'admin', 'administrator', 'admin', '06', 

'120000654', NULL, NULL, NULL, 

'20100824', 'admin', NULL, NULL)
;
INSERT INTO zdbranch(BranchInnerCode, BranchCode, ParentInnerCode, Type, OrderFlag, Name, TreeLevel, IsLeaf, Phone, Fax, Manager, Leader1, 

Leader2, Prop1, Prop2, Prop3, Prop4, Memo, AddTime, AddUser, ModifyTime, ModifyUser)
  VALUES('120000095120000637120000655', '2120999118122000B', '120000095120000637', '0', 654, '蓟县支行鑫海苑储蓄所', 3, 'Y', NULL, NULL, 'admin', 'administrator', 'admin', 

'10', '120000655', NULL, NULL, NULL, 

'20100824', 'admin', NULL, NULL)
;
INSERT INTO zdbranch(BranchInnerCode, BranchCode, ParentInnerCode, Type, OrderFlag, Name, TreeLevel, IsLeaf, Phone, Fax, Manager, Leader1, 

Leader2, Prop1, Prop2, Prop3, Prop4, Memo, AddTime, AddUser, ModifyTime, ModifyUser)
  VALUES('120000095120000637120000656', '21209991181230006', '120000095120000637', '0', 655, '蓟县支行南大街储蓄所', 3, 'Y', NULL, NULL, 'admin', 'administrator', 'admin', 

'10', '120000656', NULL, NULL, NULL, 

'20100824', 'admin', NULL, NULL)
;
INSERT INTO zdbranch(BranchInnerCode, BranchCode, ParentInnerCode, Type, OrderFlag, Name, TreeLevel, IsLeaf, Phone, Fax, Manager, Leader1, 

Leader2, Prop1, Prop2, Prop3, Prop4, Memo, AddTime, AddUser, ModifyTime, ModifyUser)
  VALUES('120000095120000637120000657', '21209991181240001', '120000095120000637', '0', 656, '蓟县支行中昌路储蓄所', 3, 'Y', NULL, NULL, 'admin', 'administrator', 'admin', 

'10', '120000657', NULL, NULL, NULL, 

'20100824', 'admin', NULL, NULL)
;
INSERT INTO zdbranch(BranchInnerCode, BranchCode, ParentInnerCode, Type, OrderFlag, Name, TreeLevel, IsLeaf, Phone, Fax, Manager, Leader1, 

Leader2, Prop1, Prop2, Prop3, Prop4, Memo, AddTime, AddUser, ModifyTime, ModifyUser)
  VALUES('120000095120000637120000658', '2120999118102000N', '120000095120000637', '0', 657, '蓟县渔阳支行', 3, 'Y', NULL, NULL, 'admin', 'administrator', 'admin', '06', 

'120000658', NULL, NULL, NULL, 

'20100824', 'admin', NULL, NULL)
;
INSERT INTO zdbranch(BranchInnerCode, BranchCode, ParentInnerCode, Type, OrderFlag, Name, TreeLevel, IsLeaf, Phone, Fax, Manager, Leader1, 

Leader2, Prop1, Prop2, Prop3, Prop4, Memo, AddTime, AddUser, ModifyTime, ModifyUser)
  VALUES('120000095120000637120000659', '2120999118125000R', '120000095120000637', '0', 658, '蓟县中昌新村储蓄所', 3, 'Y', NULL, NULL, 'admin', 'administrator', 'admin', 

'06', '120000659', NULL, NULL, NULL, 

'20100824', 'admin', NULL, NULL)
;
INSERT INTO zdbranch(BranchInnerCode, BranchCode, ParentInnerCode, Type, OrderFlag, Name, TreeLevel, IsLeaf, Phone, Fax, Manager, Leader1, 

Leader2, Prop1, Prop2, Prop3, Prop4, Memo, AddTime, AddUser, ModifyTime, ModifyUser)
  VALUES('120000095120000637120000658120000660', '2120999118102103D', '120000095120000637120000658', '0', 659, '蓟县交通储蓄所', 4, 'Y', NULL, NULL, 'admin', 'administrator', 'admin', '10', 

'120000660', NULL, NULL, NULL, 

'20100824', 'admin', NULL, NULL)
;
INSERT INTO zdbranch(BranchInnerCode, BranchCode, ParentInnerCode, Type, OrderFlag, Name, TreeLevel, IsLeaf, Phone, Fax, Manager, Leader1, 

Leader2, Prop1, Prop2, Prop3, Prop4, Memo, AddTime, AddUser, ModifyTime, ModifyUser)
  VALUES('120000095120000637120000661', '2120999118103000I', '120000095120000637', '0', 660, '蓟县开发区营业所', 3, 'Y', NULL, NULL, 'admin', 'administrator', 'admin', '06', 

'120000661', NULL, NULL, NULL, 

'20100824', 'admin', NULL, NULL)
;
INSERT INTO zdbranch(BranchInnerCode, BranchCode, ParentInnerCode, Type, OrderFlag, Name, TreeLevel, IsLeaf, Phone, Fax, Manager, Leader1, 

Leader2, Prop1, Prop2, Prop3, Prop4, Memo, AddTime, AddUser, ModifyTime, ModifyUser)
  VALUES('120000095120000637120000662', '2120999118104000D', '120000095120000637', '0', 661, '蓟县别山营业所', 3, 'Y', NULL, NULL, 'admin', 'administrator', 'admin', '06', 

'120000662', NULL, NULL, NULL, 

'20100824', 'admin', NULL, NULL)
;
INSERT INTO zdbranch(BranchInnerCode, BranchCode, ParentInnerCode, Type, OrderFlag, Name, TreeLevel, IsLeaf, Phone, Fax, Manager, Leader1, 

Leader2, Prop1, Prop2, Prop3, Prop4, Memo, AddTime, AddUser, ModifyTime, ModifyUser)
  VALUES('120000095120000637120000663', '21209991181050008', '120000095120000637', '0', 662, '蓟县城关营业所', 3, 'Y', NULL, NULL, 'admin', 'administrator', 'admin', '06', 

'120000663', NULL, NULL, NULL, 

'20100824', 'admin', NULL, NULL)
;
INSERT INTO zdbranch(BranchInnerCode, BranchCode, ParentInnerCode, Type, OrderFlag, Name, TreeLevel, IsLeaf, Phone, Fax, Manager, Leader1, 

Leader2, Prop1, Prop2, Prop3, Prop4, Memo, AddTime, AddUser, ModifyTime, ModifyUser)
  VALUES('120000095120000637120000664', '21209991181060003', '120000095120000637', '0', 663, '蓟县邦均支行', 3, 'Y', NULL, NULL, 'admin', 'administrator', 'admin', '06', 

'120000664', NULL, NULL, NULL, 

'20100824', 'admin', NULL, NULL)
;
INSERT INTO zdbranch(BranchInnerCode, BranchCode, ParentInnerCode, Type, OrderFlag, Name, TreeLevel, IsLeaf, Phone, Fax, Manager, Leader1, 

Leader2, Prop1, Prop2, Prop3, Prop4, Memo, AddTime, AddUser, ModifyTime, ModifyUser)
  VALUES('120000095120000637120000665', '2120999118128000C', '120000095120000637', '0', 664, '蓟县邦均第二储蓄所', 3, 'Y', NULL, NULL, 'admin', 'administrator', 'admin', 

'06', '120000665', NULL, NULL, NULL, 

'20100824', 'admin', NULL, NULL)
;
INSERT INTO zdbranch(BranchInnerCode, BranchCode, ParentInnerCode, Type, OrderFlag, Name, TreeLevel, IsLeaf, Phone, Fax, Manager, Leader1, 

Leader2, Prop1, Prop2, Prop3, Prop4, Memo, AddTime, AddUser, ModifyTime, ModifyUser)
  VALUES('120000095120000637120000666', '2120999118107000T', '120000095120000637', '0', 665, '蓟县马伸桥营业所', 3, 'Y', NULL, NULL, 'admin', 'administrator', 'admin', '06', 

'120000666', NULL, NULL, NULL, 

'20100824', 'admin', NULL, NULL)
;
INSERT INTO zdbranch(BranchInnerCode, BranchCode, ParentInnerCode, Type, OrderFlag, Name, TreeLevel, IsLeaf, Phone, Fax, Manager, Leader1, 

Leader2, Prop1, Prop2, Prop3, Prop4, Memo, AddTime, AddUser, ModifyTime, ModifyUser)
  VALUES('120000095120000637120000666120000667', '2120999118107101N', '120000095120000637120000666', '0', 666, '蓟县支行孔庄子储蓄所', 4, 'Y', NULL, NULL, 'admin', 'administrator', 'admin', 

'10', '120000667', NULL, NULL, 

NULL, '20100824', 'admin', NULL, NULL)
;
INSERT INTO zdbranch(BranchInnerCode, BranchCode, ParentInnerCode, Type, OrderFlag, Name, TreeLevel, IsLeaf, Phone, Fax, Manager, Leader1, 

Leader2, Prop1, Prop2, Prop3, Prop4, Memo, AddTime, AddUser, ModifyTime, ModifyUser)
  VALUES('120000095120000637120000668', '2120999118108000O', '120000095120000637', '0', 667, '蓟县尤古庄营业所', 3, 'Y', NULL, NULL, 'admin', 'administrator', 'admin', '06', 

'120000668', NULL, NULL, NULL, 

'20100824', 'admin', NULL, NULL)
;
INSERT INTO zdbranch(BranchInnerCode, BranchCode, ParentInnerCode, Type, OrderFlag, Name, TreeLevel, IsLeaf, Phone, Fax, Manager, Leader1, 

Leader2, Prop1, Prop2, Prop3, Prop4, Memo, AddTime, AddUser, ModifyTime, ModifyUser)
  VALUES('120000095120000637120000669', '2120999118109000J', '120000095120000637', '0', 668, '蓟县出头岭营业所', 3, 'Y', NULL, NULL, 'admin', 'administrator', 'admin', '06', 

'120000669', NULL, NULL, NULL, 

'20100824', 'admin', NULL, NULL)
;
INSERT INTO zdbranch(BranchInnerCode, BranchCode, ParentInnerCode, Type, OrderFlag, Name, TreeLevel, IsLeaf, Phone, Fax, Manager, Leader1, 

Leader2, Prop1, Prop2, Prop3, Prop4, Memo, AddTime, AddUser, ModifyTime, ModifyUser)
  VALUES('120000095120000637120000670', '2120999118110000R', '120000095120000637', '0', 669, '蓟县下仓营业所', 3, 'Y', NULL, NULL, 'admin', 'administrator', 'admin', '06', 

'120000670', NULL, NULL, NULL, 

'20100824', 'admin', NULL, NULL)
;
INSERT INTO zdbranch(BranchInnerCode, BranchCode, ParentInnerCode, Type, OrderFlag, Name, TreeLevel, IsLeaf, Phone, Fax, Manager, Leader1, 

Leader2, Prop1, Prop2, Prop3, Prop4, Memo, AddTime, AddUser, ModifyTime, ModifyUser)
  VALUES('120000095120000637120000671', '2120999118111000M', '120000095120000637', '0', 670, '蓟县上仓营业所', 3, 'Y', NULL, NULL, 'admin', 'administrator', 'admin', '06', 

'120000671', NULL, NULL, NULL, 

'20100824', 'admin', NULL, NULL)
;
INSERT INTO zdbranch(BranchInnerCode, BranchCode, ParentInnerCode, Type, OrderFlag, Name, TreeLevel, IsLeaf, Phone, Fax, Manager, Leader1, 

Leader2, Prop1, Prop2, Prop3, Prop4, Memo, AddTime, AddUser, ModifyTime, ModifyUser)
  VALUES('120000095120000637120000672', '2120999118112000H', '120000095120000637', '0', 671, '蓟县东塔营业所', 3, 'Y', NULL, NULL, 'admin', 'administrator', 'admin', '06', 

'120000672', NULL, NULL, NULL, 

'20100824', 'admin', NULL, NULL)
;
INSERT INTO zdbranch(BranchInnerCode, BranchCode, ParentInnerCode, Type, OrderFlag, Name, TreeLevel, IsLeaf, Phone, Fax, Manager, Leader1, 

Leader2, Prop1, Prop2, Prop3, Prop4, Memo, AddTime, AddUser, ModifyTime, ModifyUser)
  VALUES('120000095120000637120000673', '2120999118113000C', '120000095120000637', '0', 672, '蓟县官庄营业所', 3, 'Y', NULL, NULL, 'admin', 'administrator', 'admin', '06', 

'120000673', NULL, NULL, NULL, 

'20100824', 'admin', NULL, NULL)
;
INSERT INTO zdbranch(BranchInnerCode, BranchCode, ParentInnerCode, Type, OrderFlag, Name, TreeLevel, IsLeaf, Phone, Fax, Manager, Leader1, 

Leader2, Prop1, Prop2, Prop3, Prop4, Memo, AddTime, AddUser, ModifyTime, ModifyUser)
  VALUES('120000095120000637120000674', '21209991181140007', '120000095120000637', '0', 673, '蓟县洇溜营业所', 3, 'Y', NULL, NULL, 'admin', 'administrator', 'admin', '06', 

'120000674', NULL, NULL, NULL, 

'20100824', 'admin', NULL, NULL)
;
INSERT INTO zdbranch(BranchInnerCode, BranchCode, ParentInnerCode, Type, OrderFlag, Name, TreeLevel, IsLeaf, Phone, Fax, Manager, Leader1, 

Leader2, Prop1, Prop2, Prop3, Prop4, Memo, AddTime, AddUser, ModifyTime, ModifyUser)
  VALUES('120000095120000637120000638120000675', '2120999118001005P', '120000095120000637120000638', '0', 674, '蓟县支行中心营业所', 4, 'Y', NULL, NULL, 'admin', 'administrator', 'admin', '04', 

'120000675', NULL, NULL, NULL, 

'20100824', 'admin', NULL, NULL)
;
INSERT INTO zdbranch(BranchInnerCode, BranchCode, ParentInnerCode, Type, OrderFlag, Name, TreeLevel, IsLeaf, Phone, Fax, Manager, Leader1, 

Leader2, Prop1, Prop2, Prop3, Prop4, Memo, AddTime, AddUser, ModifyTime, ModifyUser)
  VALUES('120000095120000637120000676', '21209991181290007', '120000095120000637', '0', 675, '蓟县支行安裕储蓄所', 3, 'Y', NULL, NULL, 'admin', 'administrator', 'admin', '10', 

'120000676', NULL, NULL, NULL, 

'20100824', 'admin', NULL, NULL)
;
INSERT INTO zdbranch(BranchInnerCode, BranchCode, ParentInnerCode, Type, OrderFlag, Name, TreeLevel, IsLeaf, Phone, Fax, Manager, Leader1, 

Leader2, Prop1, Prop2, Prop3, Prop4, Memo, AddTime, AddUser, ModifyTime, ModifyUser)
  VALUES('120000095120000637120000677', '2120999118130000F', '120000095120000637', '0', 676, '蓟县商贸街储蓄所', 3, 'Y', NULL, NULL, 'admin', 'administrator', 'admin', '06', 

'120000677', NULL, NULL, NULL, 

'20100824', 'admin', NULL, NULL)
;
INSERT INTO zdbranch(BranchInnerCode, BranchCode, ParentInnerCode, Type, OrderFlag, Name, TreeLevel, IsLeaf, Phone, Fax, Manager, Leader1, 

Leader2, Prop1, Prop2, Prop3, Prop4, Memo, AddTime, AddUser, ModifyTime, ModifyUser)
  VALUES('120000095120000637120000678', '2120999118116000S', '120000095120000637', '0', 677, '蓟县侯家营营业所', 3, 'Y', NULL, NULL, 'admin', 'administrator', 'admin', '06', 

'120000678', NULL, NULL, NULL, 

'20100824', 'admin', NULL, NULL)
;
INSERT INTO zdbranch(BranchInnerCode, BranchCode, ParentInnerCode, Type, OrderFlag, Name, TreeLevel, IsLeaf, Phone, Fax, Manager, Leader1, 

Leader2, Prop1, Prop2, Prop3, Prop4, Memo, AddTime, AddUser, ModifyTime, ModifyUser)
  VALUES('120000095120000637120000679', '2120999118117000N', '120000095120000637', '0', 678, '蓟县下营营业所', 3, 'Y', NULL, NULL, 'admin', 'administrator', 'admin', '06', 

'120000679', NULL, NULL, NULL, 

'20100824', 'admin', NULL, NULL)
;
INSERT INTO zdbranch(BranchInnerCode, BranchCode, ParentInnerCode, Type, OrderFlag, Name, TreeLevel, IsLeaf, Phone, Fax, Manager, Leader1, 

Leader2, Prop1, Prop2, Prop3, Prop4, Memo, AddTime, AddUser, ModifyTime, ModifyUser)
  VALUES('120000095120000637120000680', '2120999118118000I', '120000095120000637', '0', 679, '蓟县杨津庄营业所', 3, 'Y', NULL, NULL, 'admin', 'administrator', 'admin', '06', 

'120000680', NULL, NULL, NULL, 

'20100824', 'admin', NULL, NULL)
;
INSERT INTO zdbranch(BranchInnerCode, BranchCode, ParentInnerCode, Type, OrderFlag, Name, TreeLevel, IsLeaf, Phone, Fax, Manager, Leader1, 

Leader2, Prop1, Prop2, Prop3, Prop4, Memo, AddTime, AddUser, ModifyTime, ModifyUser)
  VALUES('120000095120000637120000681', '2120999118119000D', '120000095120000637', '0', 680, '蓟县支行商贸街营业所', 3, 'Y', NULL, NULL, 'admin', 'administrator', 'admin', 

'06', '120000681', NULL, NULL, 

NULL, '20100824', 'admin', NULL, NULL)
;
INSERT INTO zdbranch(BranchInnerCode, BranchCode, ParentInnerCode, Type, OrderFlag, Name, TreeLevel, IsLeaf, Phone, Fax, Manager, Leader1, 

Leader2, Prop1, Prop2, Prop3, Prop4, Memo, AddTime, AddUser, ModifyTime, ModifyUser)
  VALUES('120000095120000637120000682', '2120999118127000H', '120000095120000637', '0', 681, '蓟县人民东路分理处', 3, 'Y', NULL, NULL, 'admin', 'administrator', 'admin', 

'06', '120000682', NULL, NULL, NULL, 

'20100824', 'admin', NULL, NULL)
;
INSERT INTO zdbranch(BranchInnerCode, BranchCode, ParentInnerCode, Type, OrderFlag, Name, TreeLevel, IsLeaf, Phone, Fax, Manager, Leader1, 

Leader2, Prop1, Prop2, Prop3, Prop4, Memo, AddTime, AddUser, ModifyTime, ModifyUser)
  VALUES('120000095120000637120000662120000683', '21209991181041017', '120000095120000637120000662', '0', 682, '蓟县支行别山第一储蓄所', 4, 'Y', NULL, NULL, 'admin', 'administrator', 'admin', 

'10', '120000683', NULL, NULL, 

NULL, '20100824', 'admin', NULL, NULL)
;
INSERT INTO zdbranch(BranchInnerCode, BranchCode, ParentInnerCode, Type, OrderFlag, Name, TreeLevel, IsLeaf, Phone, Fax, Manager, Leader1, 

Leader2, Prop1, Prop2, Prop3, Prop4, Memo, AddTime, AddUser, ModifyTime, ModifyUser)
  VALUES('120000095120000637120000669120000684', '2120999118109101D', '120000095120000637120000669', '0', 683, '蓟县支行官场储蓄所', 4, 'Y', NULL, NULL, 'admin', 'administrator', 'admin', 

'10', '120000684', NULL, NULL, NULL, 

'20100824', 'admin', NULL, NULL)
;
INSERT INTO zdbranch(BranchInnerCode, BranchCode, ParentInnerCode, Type, OrderFlag, Name, TreeLevel, IsLeaf, Phone, Fax, Manager, Leader1, 

Leader2, Prop1, Prop2, Prop3, Prop4, Memo, AddTime, AddUser, ModifyTime, ModifyUser)
  VALUES('120000095120000637120000685', '2120999118126000M', '120000095120000637', '0', 684, '蓟县五里桥分理处', 3, 'Y', NULL, NULL, 'admin', 'administrator', 'admin', '06', 

'120000685', NULL, NULL, NULL, 

'20100824', 'admin', NULL, NULL)
;
INSERT INTO zdbranch(BranchInnerCode, BranchCode, ParentInnerCode, Type, OrderFlag, Name, TreeLevel, IsLeaf, Phone, Fax, Manager, Leader1, 

Leader2, Prop1, Prop2, Prop3, Prop4, Memo, AddTime, AddUser, ModifyTime, ModifyUser)
  VALUES('120000095120000637120000686', '2120999118121000G', '120000095120000637', '0', 685, '蓟县兴华大街营业所', 3, 'Y', NULL, NULL, 'admin', 'administrator', 'admin', 

'06', '120000686', NULL, NULL, NULL, 

'20100824', 'admin', NULL, NULL)
;
INSERT INTO zdbranch(BranchInnerCode, BranchCode, ParentInnerCode, Type, OrderFlag, Name, TreeLevel, IsLeaf, Phone, Fax, Manager, Leader1, 

Leader2, Prop1, Prop2, Prop3, Prop4, Memo, AddTime, AddUser, ModifyTime, ModifyUser)
  VALUES('120000095120000687', '21209991190000001', '120000095', '0', 686, '静海支行', 2, 'Y', NULL, NULL, 'admin', 'administrator', 'admin', '04', '120000687', 

NULL, NULL, NULL, '20100824', 'admin', NULL, NULL);
INSERT INTO zdbranch(BranchInnerCode, BranchCode, ParentInnerCode, Type, OrderFlag, Name, TreeLevel, IsLeaf, Phone, Fax, Manager, Leader1, 

Leader2, Prop1, Prop2, Prop3, Prop4, Memo, AddTime, AddUser, ModifyTime, ModifyUser)
  VALUES('120000095120000687120000688', '2120999119001000R', '120000095120000687', '0', 687, '静海支行机关', 3, 'Y', NULL, NULL, 'admin', 'administrator', 'admin', '04', '120000688', 

NULL, NULL, NULL, 

'20100824', 'admin', NULL, NULL)
;
INSERT INTO zdbranch(BranchInnerCode, BranchCode, ParentInnerCode, Type, OrderFlag, Name, TreeLevel, IsLeaf, Phone, Fax, Manager, Leader1, 

Leader2, Prop1, Prop2, Prop3, Prop4, Memo, AddTime, AddUser, ModifyTime, ModifyUser)
  VALUES('120000095120000687120000688120000689', '2120999119001006F', '120000095120000687120000688', '0', 688, '静海支行其他', 4, 'Y', NULL, NULL, 'admin', 'administrator', 'admin', '04', '120000689', 

NULL, NULL, NULL, 

'20100824', 'admin', NULL, NULL)
;
INSERT INTO zdbranch(BranchInnerCode, BranchCode, ParentInnerCode, Type, OrderFlag, Name, TreeLevel, IsLeaf, Phone, Fax, Manager, Leader1, 

Leader2, Prop1, Prop2, Prop3, Prop4, Memo, AddTime, AddUser, ModifyTime, ModifyUser)
  VALUES('120000095120000687120000688120000690', '2120999119001005H', '120000095120000687120000688', '0', 689, '静海支行综合管理部', 4, 'Y', NULL, NULL, 'admin', 'administrator', 'admin', '04', 

'120000690', NULL, NULL, NULL, 

'20100824', 'admin', NULL, NULL)
;
INSERT INTO zdbranch(BranchInnerCode, BranchCode, ParentInnerCode, Type, OrderFlag, Name, TreeLevel, IsLeaf, Phone, Fax, Manager, Leader1, 

Leader2, Prop1, Prop2, Prop3, Prop4, Memo, AddTime, AddUser, ModifyTime, ModifyUser)
  VALUES('120000095120000687120000688120000691', '2120999119001007D', '120000095120000687120000688', '0', 690, '静海支行拓展部', 4, 'Y', NULL, NULL, 'admin', 'administrator', 'admin', '04', 

'120000691', NULL, NULL, NULL, 

'20100824', 'admin', NULL, NULL)
;
INSERT INTO zdbranch(BranchInnerCode, BranchCode, ParentInnerCode, Type, OrderFlag, Name, TreeLevel, IsLeaf, Phone, Fax, Manager, Leader1, 

Leader2, Prop1, Prop2, Prop3, Prop4, Memo, AddTime, AddUser, ModifyTime, ModifyUser)
  VALUES('120000095120000687120000688120000692', '2120999119001008B', '120000095120000687120000688', '0', 691, '静海支行信贷组', 4, 'Y', NULL, NULL, 'admin', 'administrator', 'admin', '04', 

'120000692', NULL, NULL, NULL, 

'20100824', 'admin', NULL, NULL)
;
INSERT INTO zdbranch(BranchInnerCode, BranchCode, ParentInnerCode, Type, OrderFlag, Name, TreeLevel, IsLeaf, Phone, Fax, Manager, Leader1, 

Leader2, Prop1, Prop2, Prop3, Prop4, Memo, AddTime, AddUser, ModifyTime, ModifyUser)
  VALUES('120000095120000687120000688120000693', '21209991190010099', '120000095120000687120000688', '0', 692, '静海支行行长室', 4, 'Y', NULL, NULL, 'admin', 'administrator', 'admin', '04', 

'120000693', NULL, NULL, NULL, 

'20100824', 'admin', NULL, NULL)
;
INSERT INTO zdbranch(BranchInnerCode, BranchCode, ParentInnerCode, Type, OrderFlag, Name, TreeLevel, IsLeaf, Phone, Fax, Manager, Leader1, 

Leader2, Prop1, Prop2, Prop3, Prop4, Memo, AddTime, AddUser, ModifyTime, ModifyUser)
  VALUES('120000095120000687120000688120000694', '2120999119001010O', '120000095120000687120000688', '0', 693, '静海支行保险代理部', 4, 'Y', NULL, NULL, 'admin', 'administrator', 'admin', '04', 

'120000694', NULL, NULL, NULL, 

'20100824', 'admin', NULL, NULL)
;
INSERT INTO zdbranch(BranchInnerCode, BranchCode, ParentInnerCode, Type, OrderFlag, Name, TreeLevel, IsLeaf, Phone, Fax, Manager, Leader1, 

Leader2, Prop1, Prop2, Prop3, Prop4, Memo, AddTime, AddUser, ModifyTime, ModifyUser)
  VALUES('120000095120000687120000688120000695', '2120999119001011M', '120000095120000687120000688', '0', 694, '静海支行公司业务部', 4, 'Y', NULL, NULL, 'admin', 'administrator', 'admin', '04', 

'120000695', NULL, NULL, NULL, 

'20100824', 'admin', NULL, NULL)
;
INSERT INTO zdbranch(BranchInnerCode, BranchCode, ParentInnerCode, Type, OrderFlag, Name, TreeLevel, IsLeaf, Phone, Fax, Manager, Leader1, 

Leader2, Prop1, Prop2, Prop3, Prop4, Memo, AddTime, AddUser, ModifyTime, ModifyUser)
  VALUES('120000095120000687120000688120000696', '2120999119001012K', '120000095120000687120000688', '0', 695, '静海支行财会运营部', 4, 'Y', NULL, NULL, 'admin', 'administrator', 'admin', '04', 

'120000696', NULL, NULL, NULL, 

'20100824', 'admin', NULL, NULL)
;
INSERT INTO zdbranch(BranchInnerCode, BranchCode, ParentInnerCode, Type, OrderFlag, Name, TreeLevel, IsLeaf, Phone, Fax, Manager, Leader1, 

Leader2, Prop1, Prop2, Prop3, Prop4, Memo, AddTime, AddUser, ModifyTime, ModifyUser)
  VALUES('120000095120000687120000688120000697', '2120999119001013I', '120000095120000687120000688', '0', 696, '静海支行监察保卫/内控合规部', 4, 'Y', NULL, NULL, 'admin', 'administrator', 

'admin', '04', '120000697', NULL, NULL, 

NULL, '20100824', 'admin', NULL, NULL)
;
INSERT INTO zdbranch(BranchInnerCode, BranchCode, ParentInnerCode, Type, OrderFlag, Name, TreeLevel, IsLeaf, Phone, Fax, Manager, Leader1, 

Leader2, Prop1, Prop2, Prop3, Prop4, Memo, AddTime, AddUser, ModifyTime, ModifyUser)
  VALUES('120000095120000687120000688120000698', '2120999119001014G', '120000095120000687120000688', '0', 697, '静海支行离退休人员', 4, 'Y', NULL, NULL, 'admin', 'administrator', 'admin', '04', 

'120000698', NULL, NULL, NULL, 

'20100824', 'admin', NULL, NULL)
;
INSERT INTO zdbranch(BranchInnerCode, BranchCode, ParentInnerCode, Type, OrderFlag, Name, TreeLevel, IsLeaf, Phone, Fax, Manager, Leader1, 

Leader2, Prop1, Prop2, Prop3, Prop4, Memo, AddTime, AddUser, ModifyTime, ModifyUser)
  VALUES('120000095120000687120000688120000699', '2120999119001015E', '120000095120000687120000688', '0', 698, '静海支行人力资源部', 4, 'Y', NULL, NULL, 'admin', 'administrator', 'admin', '04', 

'120000699', NULL, NULL, NULL, 

'20100824', 'admin', NULL, NULL)
;
INSERT INTO zdbranch(BranchInnerCode, BranchCode, ParentInnerCode, Type, OrderFlag, Name, TreeLevel, IsLeaf, Phone, Fax, Manager, Leader1, 

Leader2, Prop1, Prop2, Prop3, Prop4, Memo, AddTime, AddUser, ModifyTime, ModifyUser)
  VALUES('120000095120000687120000688120000700', '2120999119001016C', '120000095120000687120000688', '0', 699, '静海支行信贷管理部', 4, 'Y', NULL, NULL, 'admin', 'administrator', 'admin', '04', 

'120000700', NULL, NULL, NULL, 

'20100824', 'admin', NULL, NULL)
;





INSERT INTO zdbranch(BranchInnerCode, BranchCode, ParentInnerCode, Type, OrderFlag, Name, TreeLevel, IsLeaf, Phone, Fax, Manager, Leader1, 

Leader2, Prop1, Prop2, Prop3, Prop4, Memo, AddTime, AddUser, ModifyTime, ModifyUser)
  VALUES('120000095120000687120000688120000701', '2120999119001017A', '120000095120000687120000688', '0', 700, '静海支行业务拓展部', 4, 'Y', NULL, NULL, 'admin', 'administrator', 'admin', '04', 

'120000701', NULL, NULL, NULL, 

'20100824', 'admin', NULL, NULL)
;
INSERT INTO zdbranch(BranchInnerCode, BranchCode, ParentInnerCode, Type, OrderFlag, Name, TreeLevel, IsLeaf, Phone, Fax, Manager, Leader1, 

Leader2, Prop1, Prop2, Prop3, Prop4, Memo, AddTime, AddUser, ModifyTime, ModifyUser)
  VALUES('120000095120000687120000688120000702', '2120999119001002N', '120000095120000687120000688', '0', 701, '静海支行个人金融部', 4, 'Y', NULL, NULL, 'admin', 'administrator', 'admin', '04', 

'120000702', NULL, NULL, NULL, 

'20100824', 'admin', NULL, NULL)
;
INSERT INTO zdbranch(BranchInnerCode, BranchCode, ParentInnerCode, Type, OrderFlag, Name, TreeLevel, IsLeaf, Phone, Fax, Manager, Leader1, 

Leader2, Prop1, Prop2, Prop3, Prop4, Memo, AddTime, AddUser, ModifyTime, ModifyUser)
  VALUES('120000095120000687120000688120000703', '2120999119001003L', '120000095120000687120000688', '0', 702, '静海支行内部分流人员', 4, 'Y', NULL, NULL, 'admin', 'administrator', 'admin', 

'04', '120000703', NULL, NULL, NULL, 

'20100824', 'admin', NULL, NULL)
;
INSERT INTO zdbranch(BranchInnerCode, BranchCode, ParentInnerCode, Type, OrderFlag, Name, TreeLevel, IsLeaf, Phone, Fax, Manager, Leader1, 

Leader2, Prop1, Prop2, Prop3, Prop4, Memo, AddTime, AddUser, ModifyTime, ModifyUser)
  VALUES('120000095120000687120000704', '2120999119101000K', '120000095120000687', '0', 703, '静海县乾隆湖分理处', 3, 'Y', NULL, NULL, 'admin', 'administrator', 'admin', 

'06', '120000704', NULL, NULL, NULL, 

'20100824', 'admin', NULL, NULL)
;
INSERT INTO zdbranch(BranchInnerCode, BranchCode, ParentInnerCode, Type, OrderFlag, Name, TreeLevel, IsLeaf, Phone, Fax, Manager, Leader1, 

Leader2, Prop1, Prop2, Prop3, Prop4, Memo, AddTime, AddUser, ModifyTime, ModifyUser)
  VALUES('120000095120000687120000705', '2120999119102000F', '120000095120000687', '0', 704, '静海县大丰堆支行', 3, 'Y', NULL, NULL, 'admin', 'administrator', 'admin', '06', 

'120000705', NULL, NULL, NULL, 

'20100824', 'admin', NULL, NULL)
;
INSERT INTO zdbranch(BranchInnerCode, BranchCode, ParentInnerCode, Type, OrderFlag, Name, TreeLevel, IsLeaf, Phone, Fax, Manager, Leader1, 

Leader2, Prop1, Prop2, Prop3, Prop4, Memo, AddTime, AddUser, ModifyTime, ModifyUser)
  VALUES('120000095120000687120000706', '2120999119103000A', '120000095120000687', '0', 705, '静海县东方红路支行', 3, 'Y', NULL, NULL, 'admin', 'administrator', 'admin', 

'06', '120000706', NULL, NULL, NULL, 

'20100824', 'admin', NULL, NULL)
;
INSERT INTO zdbranch(BranchInnerCode, BranchCode, ParentInnerCode, Type, OrderFlag, Name, TreeLevel, IsLeaf, Phone, Fax, Manager, Leader1, 

Leader2, Prop1, Prop2, Prop3, Prop4, Memo, AddTime, AddUser, ModifyTime, ModifyUser)
  VALUES('120000095120000687120000704120000707', '2120999119101102C', '120000095120000687120000704', '0', 706, '静海支行联盟大街储蓄所', 4, 'Y', NULL, NULL, 'admin', 'administrator', 'admin', 

'10', '120000707', NULL, NULL, 

NULL, '20100824', 'admin', NULL, NULL)
;
INSERT INTO zdbranch(BranchInnerCode, BranchCode, ParentInnerCode, Type, OrderFlag, Name, TreeLevel, IsLeaf, Phone, Fax, Manager, Leader1, 

Leader2, Prop1, Prop2, Prop3, Prop4, Memo, AddTime, AddUser, ModifyTime, ModifyUser)
  VALUES('120000095120000687120000708', '21209991191040005', '120000095120000687', '0', 707, '静海县开发区支行', 3, 'Y', NULL, NULL, 'admin', 'administrator', 'admin', '06', 

'120000708', NULL, NULL, NULL, 

'20100824', 'admin', NULL, NULL)
;
INSERT INTO zdbranch(BranchInnerCode, BranchCode, ParentInnerCode, Type, OrderFlag, Name, TreeLevel, IsLeaf, Phone, Fax, Manager, Leader1, 

Leader2, Prop1, Prop2, Prop3, Prop4, Memo, AddTime, AddUser, ModifyTime, ModifyUser)
  VALUES('120000095120000687120000708120000709', '2120999119104101U', '120000095120000687120000708', '0', 708, '静海支行津福路储蓄所', 4, 'Y', NULL, NULL, 'admin', 'administrator', 'admin', 

'10', '120000709', NULL, NULL, 

NULL, '20100824', 'admin', NULL, NULL)
;
INSERT INTO zdbranch(BranchInnerCode, BranchCode, ParentInnerCode, Type, OrderFlag, Name, TreeLevel, IsLeaf, Phone, Fax, Manager, Leader1, 

Leader2, Prop1, Prop2, Prop3, Prop4, Memo, AddTime, AddUser, ModifyTime, ModifyUser)
  VALUES('120000095120000687120000710', '21209991191050000', '120000095120000687', '0', 709, '静海县子牙支行', 3, 'Y', NULL, NULL, 'admin', 'administrator', 'admin', '06', 

'120000710', NULL, NULL, NULL, 

'20100824', 'admin', NULL, NULL)
;
INSERT INTO zdbranch(BranchInnerCode, BranchCode, ParentInnerCode, Type, OrderFlag, Name, TreeLevel, IsLeaf, Phone, Fax, Manager, Leader1, 

Leader2, Prop1, Prop2, Prop3, Prop4, Memo, AddTime, AddUser, ModifyTime, ModifyUser)
  VALUES('120000095120000687120000711', '21209991191190005', '120000095120000687', '0', 710, '静海县元蒙口储蓄所', 3, 'Y', NULL, NULL, 'admin', 'administrator', 'admin', 

'06', '120000711', NULL, NULL, NULL, 

'20100824', 'admin', NULL, NULL)
;
INSERT INTO zdbranch(BranchInnerCode, BranchCode, ParentInnerCode, Type, OrderFlag, Name, TreeLevel, IsLeaf, Phone, Fax, Manager, Leader1, 

Leader2, Prop1, Prop2, Prop3, Prop4, Memo, AddTime, AddUser, ModifyTime, ModifyUser)
  VALUES('120000095120000687120000712', '2120999119106000Q', '120000095120000687', '0', 711, '静海县王口分理处', 3, 'Y', NULL, NULL, 'admin', 'administrator', 'admin', '06', 

'120000712', NULL, NULL, NULL, 

'20100824', 'admin', NULL, NULL)
;
INSERT INTO zdbranch(BranchInnerCode, BranchCode, ParentInnerCode, Type, OrderFlag, Name, TreeLevel, IsLeaf, Phone, Fax, Manager, Leader1, 

Leader2, Prop1, Prop2, Prop3, Prop4, Memo, AddTime, AddUser, ModifyTime, ModifyUser)
  VALUES('120000095120000687120000713', '2120999119120000D', '120000095120000687', '0', 712, '静海支行王口第一储蓄所', 3, 'Y', NULL, NULL, 'admin', 'administrator', 'admin', 

'10', '120000713', NULL, NULL, 

NULL, '20100824', 'admin', NULL, NULL)
;
INSERT INTO zdbranch(BranchInnerCode, BranchCode, ParentInnerCode, Type, OrderFlag, Name, TreeLevel, IsLeaf, Phone, Fax, Manager, Leader1, 

Leader2, Prop1, Prop2, Prop3, Prop4, Memo, AddTime, AddUser, ModifyTime, ModifyUser)
  VALUES('120000095120000687120000688120000714', '2120999119001004J', '120000095120000687120000688', '0', 713, '静海支行大邱庄营业部', 4, 'Y', NULL, NULL, 'admin', 'administrator', 'admin', 

'04', '120000714', NULL, NULL, NULL, 

'20100824', 'admin', NULL, NULL)
;
INSERT INTO zdbranch(BranchInnerCode, BranchCode, ParentInnerCode, Type, OrderFlag, Name, TreeLevel, IsLeaf, Phone, Fax, Manager, Leader1, 

Leader2, Prop1, Prop2, Prop3, Prop4, Memo, AddTime, AddUser, ModifyTime, ModifyUser)
  VALUES('120000095120000687120000715', '2120999119109000B', '120000095120000687', '0', 714, '静海县前尚储蓄所', 3, 'Y', NULL, NULL, 'admin', 'administrator', 'admin', '06', 

'120000715', NULL, NULL, NULL, 

'20100824', 'admin', NULL, NULL)
;
INSERT INTO zdbranch(BranchInnerCode, BranchCode, ParentInnerCode, Type, OrderFlag, Name, TreeLevel, IsLeaf, Phone, Fax, Manager, Leader1, 

Leader2, Prop1, Prop2, Prop3, Prop4, Memo, AddTime, AddUser, ModifyTime, ModifyUser)
  VALUES('120000095120000687120000716', '2120999119108000G', '120000095120000687', '0', 715, '静海县唐官屯支行', 3, 'Y', NULL, NULL, 'admin', 'administrator', 'admin', '06', 

'120000716', NULL, NULL, NULL, 

'20100824', 'admin', NULL, NULL)
;
INSERT INTO zdbranch(BranchInnerCode, BranchCode, ParentInnerCode, Type, OrderFlag, Name, TreeLevel, IsLeaf, Phone, Fax, Manager, Leader1, 

Leader2, Prop1, Prop2, Prop3, Prop4, Memo, AddTime, AddUser, ModifyTime, ModifyUser)
  VALUES('120000095120000687120000717', '21209991191210008', '120000095120000687', '0', 716, '静海支行唐官屯车站储蓄所', 3, 'Y', NULL, NULL, 'admin', 'administrator', 

'admin', '10', '120000717', NULL, NULL, 

NULL, '20100824', 'admin', NULL, NULL)
;
INSERT INTO zdbranch(BranchInnerCode, BranchCode, ParentInnerCode, Type, OrderFlag, Name, TreeLevel, IsLeaf, Phone, Fax, Manager, Leader1, 

Leader2, Prop1, Prop2, Prop3, Prop4, Memo, AddTime, AddUser, ModifyTime, ModifyUser)
  VALUES('120000095120000687120000718', '21209991191220003', '120000095120000687', '0', 717, '静海支行唐官屯镇西储蓄所', 3, 'Y', NULL, NULL, 'admin', 'administrator', 

'admin', '10', '120000718', NULL, NULL, 

NULL, '20100824', 'admin', NULL, NULL)
;
INSERT INTO zdbranch(BranchInnerCode, BranchCode, ParentInnerCode, Type, OrderFlag, Name, TreeLevel, IsLeaf, Phone, Fax, Manager, Leader1, 

Leader2, Prop1, Prop2, Prop3, Prop4, Memo, AddTime, AddUser, ModifyTime, ModifyUser)
  VALUES('120000095120000687120000719', '2120999119123000T', '120000095120000687', '0', 718, '静海县胜利北路分理处', 3, 'Y', NULL, NULL, 'admin', 'administrator', 'admin', 

'06', '120000719', NULL, NULL, 

NULL, '20100824', 'admin', NULL, NULL)
;
INSERT INTO zdbranch(BranchInnerCode, BranchCode, ParentInnerCode, Type, OrderFlag, Name, TreeLevel, IsLeaf, Phone, Fax, Manager, Leader1, 

Leader2, Prop1, Prop2, Prop3, Prop4, Memo, AddTime, AddUser, ModifyTime, ModifyUser)
  VALUES('120000095120000687120000720', '2120999119124000O', '120000095120000687', '0', 719, '静海县西翟庄分理处', 3, 'Y', NULL, NULL, 'admin', 'administrator', 'admin', 

'06', '120000720', NULL, NULL, NULL, 

'20100824', 'admin', NULL, NULL)
;
INSERT INTO zdbranch(BranchInnerCode, BranchCode, ParentInnerCode, Type, OrderFlag, Name, TreeLevel, IsLeaf, Phone, Fax, Manager, Leader1, 

Leader2, Prop1, Prop2, Prop3, Prop4, Memo, AddTime, AddUser, ModifyTime, ModifyUser)
  VALUES('120000095120000687120000721', '2120999119132000S', '120000095120000687', '0', 720, '静海县中旺分理处', 3, 'Y', NULL, NULL, 'admin', 'administrator', 'admin', '06', 

'120000721', NULL, NULL, NULL, 

'20100824', 'admin', NULL, NULL)
;
INSERT INTO zdbranch(BranchInnerCode, BranchCode, ParentInnerCode, Type, OrderFlag, Name, TreeLevel, IsLeaf, Phone, Fax, Manager, Leader1, 

Leader2, Prop1, Prop2, Prop3, Prop4, Memo, AddTime, AddUser, ModifyTime, ModifyUser)
  VALUES('120000095120000687120000732120000722', '2120999119114106E', '120000095120000687120000732', '0', 721, '静海支行中旺储蓄所', 4, 'Y', NULL, NULL, 'admin', 'administrator', 'admin', 

'10', '120000722', NULL, NULL, NULL, 

'20100824', 'admin', NULL, NULL)
;
INSERT INTO zdbranch(BranchInnerCode, BranchCode, ParentInnerCode, Type, OrderFlag, Name, TreeLevel, IsLeaf, Phone, Fax, Manager, Leader1, 

Leader2, Prop1, Prop2, Prop3, Prop4, Memo, AddTime, AddUser, ModifyTime, ModifyUser)
  VALUES('120000095120000687120000723', '21209991191280004', '120000095120000687', '0', 722, '静海县陈官屯分理处', 3, 'Y', NULL, NULL, 'admin', 'administrator', 'admin', 

'06', '120000723', NULL, NULL, NULL, 

'20100824', 'admin', NULL, NULL)
;
INSERT INTO zdbranch(BranchInnerCode, BranchCode, ParentInnerCode, Type, OrderFlag, Name, TreeLevel, IsLeaf, Phone, Fax, Manager, Leader1, 

Leader2, Prop1, Prop2, Prop3, Prop4, Memo, AddTime, AddUser, ModifyTime, ModifyUser)
  VALUES('120000095120000687120000725120000724', '2120999119112107M', '120000095120000687120000725', '0', 723, '静海支行陈官屯储蓄所', 4, 'Y', NULL, NULL, 'admin', 'administrator', 'admin', 

'10', '120000724', NULL, NULL, 

NULL, '20100824', 'admin', NULL, NULL)
;
INSERT INTO zdbranch(BranchInnerCode, BranchCode, ParentInnerCode, Type, OrderFlag, Name, TreeLevel, IsLeaf, Phone, Fax, Manager, Leader1, 

Leader2, Prop1, Prop2, Prop3, Prop4, Memo, AddTime, AddUser, ModifyTime, ModifyUser)
  VALUES('120000095120000687120000725', '21209991191120009', '120000095120000687', '0', 724, '静海县静城支行', 3, 'Y', NULL, NULL, 'admin', 'administrator', 'admin', '06', 

'120000725', NULL, NULL, NULL, 

'20100824', 'admin', NULL, NULL)
;
INSERT INTO zdbranch(BranchInnerCode, BranchCode, ParentInnerCode, Type, OrderFlag, Name, TreeLevel, IsLeaf, Phone, Fax, Manager, Leader1, 

Leader2, Prop1, Prop2, Prop3, Prop4, Memo, AddTime, AddUser, ModifyTime, ModifyUser)
  VALUES('120000095120000687120000726', '2120999119125000J', '120000095120000687', '0', 725, '静海县津德路储蓄所', 3, 'Y', NULL, NULL, 'admin', 'administrator', 'admin', 

'06', '120000726', NULL, NULL, NULL, 

'20100824', 'admin', NULL, NULL)
;
INSERT INTO zdbranch(BranchInnerCode, BranchCode, ParentInnerCode, Type, OrderFlag, Name, TreeLevel, IsLeaf, Phone, Fax, Manager, Leader1, 

Leader2, Prop1, Prop2, Prop3, Prop4, Memo, AddTime, AddUser, ModifyTime, ModifyUser)
  VALUES('120000095120000687120000727', '2120999119126000E', '120000095120000687', '0', 726, '静海县东城分理处', 3, 'Y', NULL, NULL, 'admin', 'administrator', 'admin', '06', 

'120000727', NULL, NULL, NULL, 

'20100824', 'admin', NULL, NULL)
;
INSERT INTO zdbranch(BranchInnerCode, BranchCode, ParentInnerCode, Type, OrderFlag, Name, TreeLevel, IsLeaf, Phone, Fax, Manager, Leader1, 

Leader2, Prop1, Prop2, Prop3, Prop4, Memo, AddTime, AddUser, ModifyTime, ModifyUser)
  VALUES('120000095120000687120000728', '2120999119118000A', '120000095120000687', '0', 727, '静海支行东方红路储蓄所', 3, 'Y', NULL, NULL, 'admin', 'administrator', 'admin', 

'10', '120000728', NULL, NULL, 

NULL, '20100824', 'admin', NULL, NULL)
;
INSERT INTO zdbranch(BranchInnerCode, BranchCode, ParentInnerCode, Type, OrderFlag, Name, TreeLevel, IsLeaf, Phone, Fax, Manager, Leader1, 

Leader2, Prop1, Prop2, Prop3, Prop4, Memo, AddTime, AddUser, ModifyTime, ModifyUser)
  VALUES('120000095120000687120000725120000729', '2120999119112104S', '120000095120000687120000725', '0', 728, '静海支行高官屯储蓄所', 4, 'Y', NULL, NULL, 'admin', 'administrator', 'admin', 

'10', '120000729', NULL, NULL, 

NULL, '20100824', 'admin', NULL, NULL)
;
INSERT INTO zdbranch(BranchInnerCode, BranchCode, ParentInnerCode, Type, OrderFlag, Name, TreeLevel, IsLeaf, Phone, Fax, Manager, Leader1, 

Leader2, Prop1, Prop2, Prop3, Prop4, Memo, AddTime, AddUser, ModifyTime, ModifyUser)
  VALUES('120000095120000687120000730', '21209991191270009', '120000095120000687', '0', 729, '静海县胜利南路储蓄所', 3, 'Y', NULL, NULL, 'admin', 'administrator', 'admin', 

'06', '120000730', NULL, NULL, 

NULL, '20100824', 'admin', NULL, NULL)
;
INSERT INTO zdbranch(BranchInnerCode, BranchCode, ParentInnerCode, Type, OrderFlag, Name, TreeLevel, IsLeaf, Phone, Fax, Manager, Leader1, 

Leader2, Prop1, Prop2, Prop3, Prop4, Memo, AddTime, AddUser, ModifyTime, ModifyUser)
  VALUES('120000095120000687120000731', '21209991191310002', '120000095120000687', '0', 730, '静海县团泊新城分理处', 3, 'Y', NULL, NULL, 'admin', 'administrator', 'admin', 

'06', '120000731', NULL, NULL, 

NULL, '20100824', 'admin', NULL, NULL)
;
INSERT INTO zdbranch(BranchInnerCode, BranchCode, ParentInnerCode, Type, OrderFlag, Name, TreeLevel, IsLeaf, Phone, Fax, Manager, Leader1, 

Leader2, Prop1, Prop2, Prop3, Prop4, Memo, AddTime, AddUser, ModifyTime, ModifyUser)
  VALUES('120000095120000687120000732', '2120999119114000U', '120000095120000687', '0', 731, '静海县蔡公庄支行', 3, 'Y', NULL, NULL, 'admin', 'administrator', 'admin', '06', 

'120000732', NULL, NULL, NULL, 

'20100824', 'admin', NULL, NULL)
;
INSERT INTO zdbranch(BranchInnerCode, BranchCode, ParentInnerCode, Type, OrderFlag, Name, TreeLevel, IsLeaf, Phone, Fax, Manager, Leader1, 

Leader2, Prop1, Prop2, Prop3, Prop4, Memo, AddTime, AddUser, ModifyTime, ModifyUser)
  VALUES('120000095120000687120000733', '2120999119129000U', '120000095120000687', '0', 732, '静海县大屯储蓄所', 3, 'Y', NULL, NULL, 'admin', 'administrator', 'admin', '11', 

'120000733', NULL, NULL, NULL, 

'20100824', 'admin', NULL, NULL)
;
INSERT INTO zdbranch(BranchInnerCode, BranchCode, ParentInnerCode, Type, OrderFlag, Name, TreeLevel, IsLeaf, Phone, Fax, Manager, Leader1, 

Leader2, Prop1, Prop2, Prop3, Prop4, Memo, AddTime, AddUser, ModifyTime, ModifyUser)
  VALUES('120000095120000687120000734', '21209991191300007', '120000095120000687', '0', 733, '静海县四党口储蓄所', 3, 'Y', NULL, NULL, 'admin', 'administrator', 'admin', 

'10', '120000734', NULL, NULL, NULL, 

'20100824', 'admin', NULL, NULL)
;
INSERT INTO zdbranch(BranchInnerCode, BranchCode, ParentInnerCode, Type, OrderFlag, Name, TreeLevel, IsLeaf, Phone, Fax, Manager, Leader1, 

Leader2, Prop1, Prop2, Prop3, Prop4, Memo, AddTime, AddUser, ModifyTime, ModifyUser)
  VALUES('120000095120000687120000732120000735', '2120999119114103K', '120000095120000687120000732', '0', 734, '静海支行胡连庄储蓄所', 4, 'Y', NULL, NULL, 'admin', 'administrator', 'admin', 

'10', '120000735', NULL, NULL, 

NULL, '20100824', 'admin', NULL, NULL)
;
INSERT INTO zdbranch(BranchInnerCode, BranchCode, ParentInnerCode, Type, OrderFlag, Name, TreeLevel, IsLeaf, Phone, Fax, Manager, Leader1, 

Leader2, Prop1, Prop2, Prop3, Prop4, Memo, AddTime, AddUser, ModifyTime, ModifyUser)
  VALUES('120000095120000687120000736', '2120999119115000P', '120000095120000687', '0', 735, '静海县独流支行', 3, 'Y', NULL, NULL, 'admin', 'administrator', 'admin', '06', 

'120000736', NULL, NULL, NULL, 

'20100824', 'admin', NULL, NULL)
;
INSERT INTO zdbranch(BranchInnerCode, BranchCode, ParentInnerCode, Type, OrderFlag, Name, TreeLevel, IsLeaf, Phone, Fax, Manager, Leader1, 

Leader2, Prop1, Prop2, Prop3, Prop4, Memo, AddTime, AddUser, ModifyTime, ModifyUser)
  VALUES('120000095120000687120000737', '2120999119133000N', '120000095120000687', '0', 736, '静海县团泊洼储蓄所', 3, 'Y', NULL, NULL, 'admin', 'administrator', 'admin', 

'06', '120000737', NULL, NULL, NULL, 

'20100824', 'admin', NULL, NULL)
;
INSERT INTO zdbranch(BranchInnerCode, BranchCode, ParentInnerCode, Type, OrderFlag, Name, TreeLevel, IsLeaf, Phone, Fax, Manager, Leader1, 

Leader2, Prop1, Prop2, Prop3, Prop4, Memo, AddTime, AddUser, ModifyTime, ModifyUser)
  VALUES('120000095120000738', '21209991200000002', '120000095', '0', 737, '宝坻支行', 2, 'Y', NULL, NULL, 'admin', 'administrator', 'admin', '04', '120000738', 

NULL, NULL, NULL, '20100824', 'admin', NULL, NULL)
;
INSERT INTO zdbranch(BranchInnerCode, BranchCode, ParentInnerCode, Type, OrderFlag, Name, TreeLevel, IsLeaf, Phone, Fax, Manager, Leader1, 

Leader2, Prop1, Prop2, Prop3, Prop4, Memo, AddTime, AddUser, ModifyTime, ModifyUser)
  VALUES('120000095120000738120000739', '2120999120001000S', '120000095120000738', '0', 738, '宝坻支行机关', 3, 'Y', NULL, NULL, 'admin', 'administrator', 'admin', '04', '120000739', 

NULL, NULL, NULL, 

'20100824', 'admin', NULL, NULL)
;
INSERT INTO zdbranch(BranchInnerCode, BranchCode, ParentInnerCode, Type, OrderFlag, Name, TreeLevel, IsLeaf, Phone, Fax, Manager, Leader1, 

Leader2, Prop1, Prop2, Prop3, Prop4, Memo, AddTime, AddUser, ModifyTime, ModifyUser)
  VALUES('120000095120000738120000739120000740', '2120999120001001Q', '120000095120000738120000739', '0', 739, '宝坻支行其他', 4, 'Y', NULL, NULL, 'admin', 'administrator', 'admin', '04', '120000740', 

NULL, NULL, NULL, 

'20100824', 'admin', NULL, NULL)
;
INSERT INTO zdbranch(BranchInnerCode, BranchCode, ParentInnerCode, Type, OrderFlag, Name, TreeLevel, IsLeaf, Phone, Fax, Manager, Leader1, 

Leader2, Prop1, Prop2, Prop3, Prop4, Memo, AddTime, AddUser, ModifyTime, ModifyUser)
  VALUES('120000095120000738120000739120000741', '2120999120001002O', '120000095120000738120000739', '0', 740, '宝坻支行综合管理部', 4, 'Y', NULL, NULL, 'admin', 'administrator', 'admin', '04', 

'120000741', NULL, NULL, NULL, 

'20100824', 'admin', NULL, NULL)
;
INSERT INTO zdbranch(BranchInnerCode, BranchCode, ParentInnerCode, Type, OrderFlag, Name, TreeLevel, IsLeaf, Phone, Fax, Manager, Leader1, 

Leader2, Prop1, Prop2, Prop3, Prop4, Memo, AddTime, AddUser, ModifyTime, ModifyUser)
  VALUES('120000095120000738120000739120000742', '2120999120001003M', '120000095120000738120000739', '0', 741, '宝坻支行行长室', 4, 'Y', NULL, NULL, 'admin', 'administrator', 'admin', '04', 

'120000742', NULL, NULL, NULL, 

'20100824', 'admin', NULL, NULL)
;
INSERT INTO zdbranch(BranchInnerCode, BranchCode, ParentInnerCode, Type, OrderFlag, Name, TreeLevel, IsLeaf, Phone, Fax, Manager, Leader1, 

Leader2, Prop1, Prop2, Prop3, Prop4, Memo, AddTime, AddUser, ModifyTime, ModifyUser)
  VALUES('120000095120000738120000739120000743', '2120999120001004K', '120000095120000738120000739', '0', 742, '宝坻支行内退人员', 4, 'Y', NULL, NULL, 'admin', 'administrator', 'admin', '04', 

'120000743', NULL, NULL, NULL, 

'20100824', 'admin', NULL, NULL)
;
INSERT INTO zdbranch(BranchInnerCode, BranchCode, ParentInnerCode, Type, OrderFlag, Name, TreeLevel, IsLeaf, Phone, Fax, Manager, Leader1, 

Leader2, Prop1, Prop2, Prop3, Prop4, Memo, AddTime, AddUser, ModifyTime, ModifyUser)
  VALUES('120000095120000738120000739120000744', '2120999120001005I', '120000095120000738120000739', '0', 743, '宝坻支行保险代理部', 4, 'Y', NULL, NULL, 'admin', 'administrator', 'admin', '04', 

'120000744', NULL, NULL, NULL, 

'20100824', 'admin', NULL, NULL)
;
INSERT INTO zdbranch(BranchInnerCode, BranchCode, ParentInnerCode, Type, OrderFlag, Name, TreeLevel, IsLeaf, Phone, Fax, Manager, Leader1, 

Leader2, Prop1, Prop2, Prop3, Prop4, Memo, AddTime, AddUser, ModifyTime, ModifyUser)
  VALUES('120000095120000738120000739120000745', '2120999120001006G', '120000095120000738120000739', '0', 744, '宝坻支行财会运营部', 4, 'Y', NULL, NULL, 'admin', 'administrator', 'admin', '04', 

'120000745', NULL, NULL, NULL, 

'20100824', 'admin', NULL, NULL)
;
INSERT INTO zdbranch(BranchInnerCode, BranchCode, ParentInnerCode, Type, OrderFlag, Name, TreeLevel, IsLeaf, Phone, Fax, Manager, Leader1, 

Leader2, Prop1, Prop2, Prop3, Prop4, Memo, AddTime, AddUser, ModifyTime, ModifyUser)
  VALUES('120000095120000738120000739120000746', '2120999120001007E', '120000095120000738120000739', '0', 745, '宝坻支行监察保卫/内控合规部', 4, 'Y', NULL, NULL, 'admin', 'administrator', 

'admin', '04', '120000746', NULL, NULL, 

NULL, '20100824', 'admin', NULL, NULL)
;
INSERT INTO zdbranch(BranchInnerCode, BranchCode, ParentInnerCode, Type, OrderFlag, Name, TreeLevel, IsLeaf, Phone, Fax, Manager, Leader1, 

Leader2, Prop1, Prop2, Prop3, Prop4, Memo, AddTime, AddUser, ModifyTime, ModifyUser)
  VALUES('120000095120000738120000739120000747', '2120999120001008C', '120000095120000738120000739', '0', 746, '宝坻支行离退休人员', 4, 'Y', NULL, NULL, 'admin', 'administrator', 'admin', '04', 

'120000747', NULL, NULL, NULL, 

'20100824', 'admin', NULL, NULL)
;
INSERT INTO zdbranch(BranchInnerCode, BranchCode, ParentInnerCode, Type, OrderFlag, Name, TreeLevel, IsLeaf, Phone, Fax, Manager, Leader1, 

Leader2, Prop1, Prop2, Prop3, Prop4, Memo, AddTime, AddUser, ModifyTime, ModifyUser)
  VALUES('120000095120000738120000739120000748', '2120999120001009A', '120000095120000738120000739', '0', 747, '宝坻支行人力资源部', 4, 'Y', NULL, NULL, 'admin', 'administrator', 'admin', '04', 

'120000748', NULL, NULL, NULL, 

'20100824', 'admin', NULL, NULL)
;
INSERT INTO zdbranch(BranchInnerCode, BranchCode, ParentInnerCode, Type, OrderFlag, Name, TreeLevel, IsLeaf, Phone, Fax, Manager, Leader1, 

Leader2, Prop1, Prop2, Prop3, Prop4, Memo, AddTime, AddUser, ModifyTime, ModifyUser)
  VALUES('120000095120000738120000739120000749', '2120999120001010P', '120000095120000738120000739', '0', 748, '宝坻支行信贷管理部', 4, 'Y', NULL, NULL, 'admin', 'administrator', 'admin', '04', 

'120000749', NULL, NULL, NULL, 

'20100824', 'admin', NULL, NULL)
;
INSERT INTO zdbranch(BranchInnerCode, BranchCode, ParentInnerCode, Type, OrderFlag, Name, TreeLevel, IsLeaf, Phone, Fax, Manager, Leader1, 

Leader2, Prop1, Prop2, Prop3, Prop4, Memo, AddTime, AddUser, ModifyTime, ModifyUser)
  VALUES('120000095120000738120000739120000750', '2120999120001011N', '120000095120000738120000739', '0', 749, '宝坻支行公司业务部', 4, 'Y', NULL, NULL, 'admin', 'administrator', 'admin', '04', 

'120000750', NULL, NULL, NULL, 

'20100824', 'admin', NULL, NULL)
;
INSERT INTO zdbranch(BranchInnerCode, BranchCode, ParentInnerCode, Type, OrderFlag, Name, TreeLevel, IsLeaf, Phone, Fax, Manager, Leader1, 

Leader2, Prop1, Prop2, Prop3, Prop4, Memo, AddTime, AddUser, ModifyTime, ModifyUser)
  VALUES('120000095120000738120000739120000751', '2120999120001012L', '120000095120000738120000739', '0', 750, '宝坻支行个人金融部', 4, 'Y', NULL, NULL, 'admin', 'administrator', 'admin', '04', 

'120000751', NULL, NULL, NULL, 

'20100824', 'admin', NULL, NULL)
;
INSERT INTO zdbranch(BranchInnerCode, BranchCode, ParentInnerCode, Type, OrderFlag, Name, TreeLevel, IsLeaf, Phone, Fax, Manager, Leader1, 

Leader2, Prop1, Prop2, Prop3, Prop4, Memo, AddTime, AddUser, ModifyTime, ModifyUser)
  VALUES('120000095120000738120000739120000752', '2120999120001013J', '120000095120000738120000739', '0', 751, '宝坻支行资产风险部', 4, 'Y', NULL, NULL, 'admin', 'administrator', 'admin', '04', 

'120000752', NULL, NULL, NULL, 

'20100824', 'admin', NULL, NULL)
;
INSERT INTO zdbranch(BranchInnerCode, BranchCode, ParentInnerCode, Type, OrderFlag, Name, TreeLevel, IsLeaf, Phone, Fax, Manager, Leader1, 

Leader2, Prop1, Prop2, Prop3, Prop4, Memo, AddTime, AddUser, ModifyTime, ModifyUser)
  VALUES('120000095120000738120000739120000753', '2120999120001014H', '120000095120000738120000739', '0', 752, '宝坻支行内部分流人员', 4, 'Y', NULL, NULL, 'admin', 'administrator', 'admin', 

'04', '120000753', NULL, NULL, NULL, 

'20100824', 'admin', NULL, NULL)
;
INSERT INTO zdbranch(BranchInnerCode, BranchCode, ParentInnerCode, Type, OrderFlag, Name, TreeLevel, IsLeaf, Phone, Fax, Manager, Leader1, 

Leader2, Prop1, Prop2, Prop3, Prop4, Memo, AddTime, AddUser, ModifyTime, ModifyUser)
  VALUES('120000095120000738120000754', '21209991201040006', '120000095120000738', '0', 753, '宝坻区开发区分理处', 3, 'Y', NULL, NULL, 'admin', 'administrator', 'admin', 

'06', '120000754', NULL, NULL, NULL, 

'20100824', 'admin', NULL, NULL)
;
INSERT INTO zdbranch(BranchInnerCode, BranchCode, ParentInnerCode, Type, OrderFlag, Name, TreeLevel, IsLeaf, Phone, Fax, Manager, Leader1, 

Leader2, Prop1, Prop2, Prop3, Prop4, Memo, AddTime, AddUser, ModifyTime, ModifyUser)
  VALUES('120000095120000738120000755', '2120999120107000M', '120000095120000738', '0', 754, '宝坻区马家店分理处', 3, 'Y', NULL, NULL, 'admin', 'administrator', 'admin', 

'06', '120000755', NULL, NULL, NULL, 

'20100824', 'admin', NULL, NULL)
;
INSERT INTO zdbranch(BranchInnerCode, BranchCode, ParentInnerCode, Type, OrderFlag, Name, TreeLevel, IsLeaf, Phone, Fax, Manager, Leader1, 

Leader2, Prop1, Prop2, Prop3, Prop4, Memo, AddTime, AddUser, ModifyTime, ModifyUser)
  VALUES('120000095120000738120000756', '2120999120115000Q', '120000095120000738', '0', 755, '宝坻区新安镇分理处', 3, 'Y', NULL, NULL, 'admin', 'administrator', 'admin', 

'06', '120000756', NULL, NULL, NULL, 

'20100824', 'admin', NULL, NULL)
;
INSERT INTO zdbranch(BranchInnerCode, BranchCode, ParentInnerCode, Type, OrderFlag, Name, TreeLevel, IsLeaf, Phone, Fax, Manager, Leader1, 

Leader2, Prop1, Prop2, Prop3, Prop4, Memo, AddTime, AddUser, ModifyTime, ModifyUser)
  VALUES('120000095120000738120000757', '21209991201140000', '120000095120000738', '0', 756, '宝坻区赵各庄分理处', 3, 'Y', NULL, NULL, 'admin', 'administrator', 'admin', 

'06', '120000757', NULL, NULL, NULL, 

'20100824', 'admin', NULL, NULL)
;
INSERT INTO zdbranch(BranchInnerCode, BranchCode, ParentInnerCode, Type, OrderFlag, Name, TreeLevel, IsLeaf, Phone, Fax, Manager, Leader1, 

Leader2, Prop1, Prop2, Prop3, Prop4, Memo, AddTime, AddUser, ModifyTime, ModifyUser)
  VALUES('120000095120000738120000758', '2120999120108000H', '120000095120000738', '0', 757, '大口屯支行', 3, 'Y', NULL, NULL, 'admin', 'administrator', 'admin', '06', '120000758', 

NULL, NULL, NULL, 

'20100824', 'admin', NULL, NULL)
;
INSERT INTO zdbranch(BranchInnerCode, BranchCode, ParentInnerCode, Type, OrderFlag, Name, TreeLevel, IsLeaf, Phone, Fax, Manager, Leader1, 

Leader2, Prop1, Prop2, Prop3, Prop4, Memo, AddTime, AddUser, ModifyTime, ModifyUser)
  VALUES('120000095120000738120000759', '2120999120109000C', '120000095120000738', '0', 758, '新城开发区支行', 3, 'Y', NULL, NULL, 'admin', 'administrator', 'admin', '06', 

'120000759', NULL, NULL, NULL, 

'20100824', 'admin', NULL, NULL)
;
INSERT INTO zdbranch(BranchInnerCode, BranchCode, ParentInnerCode, Type, OrderFlag, Name, TreeLevel, IsLeaf, Phone, Fax, Manager, Leader1, 

Leader2, Prop1, Prop2, Prop3, Prop4, Memo, AddTime, AddUser, ModifyTime, ModifyUser)
  VALUES('120000095120000738120000760', '2120999120112000A', '120000095120000738', '0', 759, '林亭口支行', 3, 'Y', NULL, NULL, 'admin', 'administrator', 'admin', '06', '120000760', 

NULL, NULL, NULL, 

'20100824', 'admin', NULL, NULL)
;
INSERT INTO zdbranch(BranchInnerCode, BranchCode, ParentInnerCode, Type, OrderFlag, Name, TreeLevel, IsLeaf, Phone, Fax, Manager, Leader1, 

Leader2, Prop1, Prop2, Prop3, Prop4, Memo, AddTime, AddUser, ModifyTime, ModifyUser)
  VALUES('120000095120000738120000761', '21209991201130005', '120000095120000738', '0', 760, '宝坻区大钟庄分理处', 3, 'Y', NULL, NULL, 'admin', 'administrator', 'admin', 

'06', '120000761', NULL, NULL, NULL, 

'20100824', 'admin', NULL, NULL)
;
INSERT INTO zdbranch(BranchInnerCode, BranchCode, ParentInnerCode, Type, OrderFlag, Name, TreeLevel, IsLeaf, Phone, Fax, Manager, Leader1, 

Leader2, Prop1, Prop2, Prop3, Prop4, Memo, AddTime, AddUser, ModifyTime, ModifyUser)
  VALUES('120000095120000738120000762', '21209991201050001', '120000095120000738', '0', 761, '石幢支行', 3, 'Y', NULL, NULL, 'admin', 'administrator', 'admin', '06', '120000762', 

NULL, NULL, NULL, '20100824', 'admin', NULL, NULL);
INSERT INTO zdbranch(BranchInnerCode, BranchCode, ParentInnerCode, Type, OrderFlag, Name, TreeLevel, IsLeaf, Phone, Fax, Manager, Leader1, 

Leader2, Prop1, Prop2, Prop3, Prop4, Memo, AddTime, AddUser, ModifyTime, ModifyUser)
  VALUES('120000095120000738120000763', '2120999120111000F', '120000095120000738', '0', 762, '宝坻支行文化广场营业所', 3, 'Y', NULL, NULL, 'admin', 'administrator', 'admin', 

'06', '120000763', NULL, NULL, 

NULL, '20100824', 'admin', NULL, NULL)
;
INSERT INTO zdbranch(BranchInnerCode, BranchCode, ParentInnerCode, Type, OrderFlag, Name, TreeLevel, IsLeaf, Phone, Fax, Manager, Leader1, 

Leader2, Prop1, Prop2, Prop3, Prop4, Memo, AddTime, AddUser, ModifyTime, ModifyUser)
  VALUES('120000095120000738120000764', '2120999120102000G', '120000095120000738', '0', 763, '城关支行', 3, 'Y', NULL, NULL, 'admin', 'administrator', 'admin', '06', '120000764', 

NULL, NULL, NULL, '20100824', 'admin', NULL, NULL);
INSERT INTO zdbranch(BranchInnerCode, BranchCode, ParentInnerCode, Type, OrderFlag, Name, TreeLevel, IsLeaf, Phone, Fax, Manager, Leader1, 

Leader2, Prop1, Prop2, Prop3, Prop4, Memo, AddTime, AddUser, ModifyTime, ModifyUser)
  VALUES('120000095120000738120000765', '2120999120110000K', '120000095120000738', '0', 764, '宝坻区王卜庄分理处', 3, 'Y', NULL, NULL, 'admin', 'administrator', 'admin', 

'06', '120000765', NULL, NULL, NULL, 

'20100824', 'admin', NULL, NULL)
;
INSERT INTO zdbranch(BranchInnerCode, BranchCode, ParentInnerCode, Type, OrderFlag, Name, TreeLevel, IsLeaf, Phone, Fax, Manager, Leader1, 

Leader2, Prop1, Prop2, Prop3, Prop4, Memo, AddTime, AddUser, ModifyTime, ModifyUser)
  VALUES('120000095120000738120000766', '2120999120103000B', '120000095120000738', '0', 765, '宝坻支行驻防营营业所', 3, 'Y', NULL, NULL, 'admin', 'administrator', 'admin', 

'06', '120000766', NULL, NULL, NULL, 

'20100824', 'admin', NULL, NULL)
;
INSERT INTO zdbranch(BranchInnerCode, BranchCode, ParentInnerCode, Type, OrderFlag, Name, TreeLevel, IsLeaf, Phone, Fax, Manager, Leader1, 

Leader2, Prop1, Prop2, Prop3, Prop4, Memo, AddTime, AddUser, ModifyTime, ModifyUser)
  VALUES('120000095120000738120000767', '2120999120106000R', '120000095120000738', '0', 766, '宝坻区东城路分理处', 3, 'Y', NULL, NULL, 'admin', 'administrator', 'admin', 

'06', '120000767', NULL, NULL, NULL, 

'20100824', 'admin', NULL, NULL)
;
INSERT INTO zdbranch(BranchInnerCode, BranchCode, ParentInnerCode, Type, OrderFlag, Name, TreeLevel, IsLeaf, Phone, Fax, Manager, Leader1, 

Leader2, Prop1, Prop2, Prop3, Prop4, Memo, AddTime, AddUser, ModifyTime, ModifyUser)
  VALUES('120000095120000738120000768', '21209991201210009', '120000095120000738', '0', 767, '宝坻区田场分理处', 3, 'Y', NULL, NULL, 'admin', 'administrator', 'admin', '06', 

'120000768', NULL, NULL, NULL, 

'20100824', 'admin', NULL, NULL)
;
INSERT INTO zdbranch(BranchInnerCode, BranchCode, ParentInnerCode, Type, OrderFlag, Name, TreeLevel, IsLeaf, Phone, Fax, Manager, Leader1, 

Leader2, Prop1, Prop2, Prop3, Prop4, Memo, AddTime, AddUser, ModifyTime, ModifyUser)
  VALUES('120000095120000738120000764120000769', '21209991201021044', '120000095120000738120000764', '0', 768, '宝坻支行苏北路储蓄所', 4, 'Y', NULL, NULL, 'admin', 'administrator', 'admin', 

'10', '120000769', NULL, NULL, 

NULL, '20100824', 'admin', NULL, NULL)
;
INSERT INTO zdbranch(BranchInnerCode, BranchCode, ParentInnerCode, Type, OrderFlag, Name, TreeLevel, IsLeaf, Phone, Fax, Manager, Leader1, 

Leader2, Prop1, Prop2, Prop3, Prop4, Memo, AddTime, AddUser, ModifyTime, ModifyUser)
  VALUES('120000095120000738120000770', '2120999120117000G', '120000095120000738', '0', 769, '南关大街支行', 3, 'Y', NULL, NULL, 'admin', 'administrator', 'admin', '06', 

'120000770', NULL, NULL, NULL, 

'20100824', 'admin', NULL, NULL)
;
INSERT INTO zdbranch(BranchInnerCode, BranchCode, ParentInnerCode, Type, OrderFlag, Name, TreeLevel, IsLeaf, Phone, Fax, Manager, Leader1, 

Leader2, Prop1, Prop2, Prop3, Prop4, Memo, AddTime, AddUser, ModifyTime, ModifyUser)
  VALUES('120000095120000738120000771', '21209991201300008', '120000095120000738', '0', 770, '宝坻区周良庄储蓄所', 3, 'Y', NULL, NULL, 'admin', 'administrator', 'admin', 

'06', '120000771', NULL, NULL, NULL, 

'20100824', 'admin', NULL, NULL)
;
INSERT INTO zdbranch(BranchInnerCode, BranchCode, ParentInnerCode, Type, OrderFlag, Name, TreeLevel, IsLeaf, Phone, Fax, Manager, Leader1, 

Leader2, Prop1, Prop2, Prop3, Prop4, Memo, AddTime, AddUser, ModifyTime, ModifyUser)
  VALUES('120000095120000738120000772', '2120999120127000A', '120000095120000738', '0', 771, '宝坻支行南街储蓄所', 3, 'Y', NULL, NULL, 'admin', 'administrator', 'admin', '10', 

'120000772', NULL, NULL, NULL, 

'20100824', 'admin', NULL, NULL)
;
INSERT INTO zdbranch(BranchInnerCode, BranchCode, ParentInnerCode, Type, OrderFlag, Name, TreeLevel, IsLeaf, Phone, Fax, Manager, Leader1, 

Leader2, Prop1, Prop2, Prop3, Prop4, Memo, AddTime, AddUser, ModifyTime, ModifyUser)
  VALUES('120000095120000738120000773', '21209991201190006', '120000095120000738', '0', 772, '宝坻支行北城路营业所', 3, 'Y', NULL, NULL, 'admin', 'administrator', 'admin', 

'06', '120000773', NULL, NULL, NULL, 

'20100824', 'admin', NULL, NULL)
;
INSERT INTO zdbranch(BranchInnerCode, BranchCode, ParentInnerCode, Type, OrderFlag, Name, TreeLevel, IsLeaf, Phone, Fax, Manager, Leader1, 

Leader2, Prop1, Prop2, Prop3, Prop4, Memo, AddTime, AddUser, ModifyTime, ModifyUser)
  VALUES('120000095120000738120000774', '2120999120124000P', '120000095120000738', '0', 773, '宝坻区宝平储蓄所', 3, 'Y', NULL, NULL, 'admin', 'administrator', 'admin', '06', 

'120000774', NULL, NULL, NULL, 

'20100824', 'admin', NULL, NULL)
;
INSERT INTO zdbranch(BranchInnerCode, BranchCode, ParentInnerCode, Type, OrderFlag, Name, TreeLevel, IsLeaf, Phone, Fax, Manager, Leader1, 

Leader2, Prop1, Prop2, Prop3, Prop4, Memo, AddTime, AddUser, ModifyTime, ModifyUser)
  VALUES('120000095120000738120000775', '2120999120123000U', '120000095120000738', '0', 774, '宝坻支行宝平景苑储蓄所', 3, 'Y', NULL, NULL, 'admin', 'administrator', 'admin', 

'10', '120000775', NULL, NULL, 

NULL, '20100824', 'admin', NULL, NULL)
;
INSERT INTO zdbranch(BranchInnerCode, BranchCode, ParentInnerCode, Type, OrderFlag, Name, TreeLevel, IsLeaf, Phone, Fax, Manager, Leader1, 

Leader2, Prop1, Prop2, Prop3, Prop4, Memo, AddTime, AddUser, ModifyTime, ModifyUser)
  VALUES('120000095120000738120000776', '2120999120118000B', '120000095120000738', '0', 775, '宝坻区方家庄分理处', 3, 'Y', NULL, NULL, 'admin', 'administrator', 'admin', 

'06', '120000776', NULL, NULL, NULL, 

'20100824', 'admin', NULL, NULL)
;
INSERT INTO zdbranch(BranchInnerCode, BranchCode, ParentInnerCode, Type, OrderFlag, Name, TreeLevel, IsLeaf, Phone, Fax, Manager, Leader1, 

Leader2, Prop1, Prop2, Prop3, Prop4, Memo, AddTime, AddUser, ModifyTime, ModifyUser)
  VALUES('120000095120000738120000764120000777', '2120999120102101A', '120000095120000738120000764', '0', 776, '宝坻支行吴辛庄储蓄所', 4, 'Y', NULL, NULL, 'admin', 'administrator', 'admin', 

'10', '120000777', NULL, NULL, 

NULL, '20100824', 'admin', NULL, NULL)
;
INSERT INTO zdbranch(BranchInnerCode, BranchCode, ParentInnerCode, Type, OrderFlag, Name, TreeLevel, IsLeaf, Phone, Fax, Manager, Leader1, 

Leader2, Prop1, Prop2, Prop3, Prop4, Memo, AddTime, AddUser, ModifyTime, ModifyUser)
  VALUES('120000095120000738120000764120000778', '21209991201021052', '120000095120000738120000764', '0', 777, '宝坻支行北关储蓄所', 4, 'Y', NULL, NULL, 'admin', 'administrator', 'admin', 

'10', '120000778', NULL, NULL, NULL, 

'20100824', 'admin', NULL, NULL)
;
INSERT INTO zdbranch(BranchInnerCode, BranchCode, ParentInnerCode, Type, OrderFlag, Name, TreeLevel, IsLeaf, Phone, Fax, Manager, Leader1, 

Leader2, Prop1, Prop2, Prop3, Prop4, Memo, AddTime, AddUser, ModifyTime, ModifyUser)
  VALUES('120000095120000738120000762120000779', '2120999120105105I', '120000095120000738120000762', '0', 778, '宝坻支行史各庄储蓄所', 4, 'Y', NULL, NULL, 'admin', 'administrator', 'admin', 

'10', '120000779', NULL, NULL, 

NULL, '20100824', 'admin', NULL, NULL)
;
INSERT INTO zdbranch(BranchInnerCode, BranchCode, ParentInnerCode, Type, OrderFlag, Name, TreeLevel, IsLeaf, Phone, Fax, Manager, Leader1, 

Leader2, Prop1, Prop2, Prop3, Prop4, Memo, AddTime, AddUser, ModifyTime, ModifyUser)
  VALUES('120000095120000738120000780', '21209991201280005', '120000095120000738', '0', 779, '宝坻支行大唐庄储蓄所', 3, 'Y', NULL, NULL, 'admin', 'administrator', 'admin', 

'10', '120000780', NULL, NULL, NULL, 

'20100824', 'admin', NULL, NULL)
;
INSERT INTO zdbranch(BranchInnerCode, BranchCode, ParentInnerCode, Type, OrderFlag, Name, TreeLevel, IsLeaf, Phone, Fax, Manager, Leader1, 

Leader2, Prop1, Prop2, Prop3, Prop4, Memo, AddTime, AddUser, ModifyTime, ModifyUser)
  VALUES('120000095120000738120000781', '2120999120132000T', '120000095120000738', '0', 780, '宝坻区八门城储蓄所', 3, 'Y', NULL, NULL, 'admin', 'administrator', 'admin', 

'06', '120000781', NULL, NULL, NULL, 

'20100824', 'admin', NULL, NULL)
;
INSERT INTO zdbranch(BranchInnerCode, BranchCode, ParentInnerCode, Type, OrderFlag, Name, TreeLevel, IsLeaf, Phone, Fax, Manager, Leader1, 

Leader2, Prop1, Prop2, Prop3, Prop4, Memo, AddTime, AddUser, ModifyTime, ModifyUser)
  VALUES('120000095120000738120000782', '21209991201310003', '120000095120000738', '0', 781, '宝坻区黄庄储蓄所', 3, 'Y', NULL, NULL, 'admin', 'administrator', 'admin', '06', 

'120000782', NULL, NULL, NULL, 

'20100824', 'admin', NULL, NULL)
;
INSERT INTO zdbranch(BranchInnerCode, BranchCode, ParentInnerCode, Type, OrderFlag, Name, TreeLevel, IsLeaf, Phone, Fax, Manager, Leader1, 

Leader2, Prop1, Prop2, Prop3, Prop4, Memo, AddTime, AddUser, ModifyTime, ModifyUser)
  VALUES('120000095120000738120000783', '21209991201290000', '120000095120000738', '0', 782, '宝坻区口东分理处', 3, 'Y', NULL, NULL, 'admin', 'administrator', 'admin', '06', 

'120000783', NULL, NULL, NULL, 

'20100824', 'admin', NULL, NULL)
;
INSERT INTO zdbranch(BranchInnerCode, BranchCode, ParentInnerCode, Type, OrderFlag, Name, TreeLevel, IsLeaf, Phone, Fax, Manager, Leader1, 

Leader2, Prop1, Prop2, Prop3, Prop4, Memo, AddTime, AddUser, ModifyTime, ModifyUser)
  VALUES('120000095120000738120000784', '2120999120126000F', '120000095120000738', '0', 783, '宝坻区新开口储蓄所', 3, 'Y', NULL, NULL, 'admin', 'administrator', 'admin', 

'06', '120000784', NULL, NULL, NULL, 

'20100824', 'admin', NULL, NULL)
;
INSERT INTO zdbranch(BranchInnerCode, BranchCode, ParentInnerCode, Type, OrderFlag, Name, TreeLevel, IsLeaf, Phone, Fax, Manager, Leader1, 

Leader2, Prop1, Prop2, Prop3, Prop4, Memo, AddTime, AddUser, ModifyTime, ModifyUser)
  VALUES('120000095120000738120000785', '2120999120125000K', '120000095120000738', '0', 784, '宝坻区郝各庄储蓄所', 3, 'Y', NULL, NULL, 'admin', 'administrator', 'admin', 

'06', '120000785', NULL, NULL, NULL, 

'20100824', 'admin', NULL, NULL)
;
INSERT INTO zdbranch(BranchInnerCode, BranchCode, ParentInnerCode, Type, OrderFlag, Name, TreeLevel, IsLeaf, Phone, Fax, Manager, Leader1, 

Leader2, Prop1, Prop2, Prop3, Prop4, Memo, AddTime, AddUser, ModifyTime, ModifyUser)
  VALUES('120000095120000738120000763120000786', '21209991201111019', '120000095120000738120000763', '0', 785, '宝坻支行糙甸储蓄所', 4, 'Y', NULL, NULL, 'admin', 'administrator', 'admin', 

'10', '120000786', NULL, NULL, NULL, 

'20100824', 'admin', NULL, NULL)
;
INSERT INTO zdbranch(BranchInnerCode, BranchCode, ParentInnerCode, Type, OrderFlag, Name, TreeLevel, IsLeaf, Phone, Fax, Manager, Leader1, 

Leader2, Prop1, Prop2, Prop3, Prop4, Memo, AddTime, AddUser, ModifyTime, ModifyUser)
  VALUES('120000095120000738120000757120000787', '2120999120114101P', '120000095120000738120000757', '0', 786, '宝坻支行牛道口储蓄所', 4, 'Y', NULL, NULL, 'admin', 'administrator', 'admin', 

'10', '120000787', NULL, NULL, 

NULL, '20100824', 'admin', NULL, NULL)
;
INSERT INTO zdbranch(BranchInnerCode, BranchCode, ParentInnerCode, Type, OrderFlag, Name, TreeLevel, IsLeaf, Phone, Fax, Manager, Leader1, 

Leader2, Prop1, Prop2, Prop3, Prop4, Memo, AddTime, AddUser, ModifyTime, ModifyUser)
  VALUES('120000095120000738120000788', '21209991201220004', '120000095120000738', '0', 787, '宝坻支行石桥储蓄所', 3, 'Y', NULL, NULL, 'admin', 'administrator', 'admin', '10', 

'120000788', NULL, NULL, NULL, 

'20100824', 'admin', NULL, NULL)
;
INSERT INTO zdbranch(BranchInnerCode, BranchCode, ParentInnerCode, Type, OrderFlag, Name, TreeLevel, IsLeaf, Phone, Fax, Manager, Leader1, 

Leader2, Prop1, Prop2, Prop3, Prop4, Memo, AddTime, AddUser, ModifyTime, ModifyUser)
  VALUES('120000095120000738120000789', '2120999120116000L', '120000095120000738', '0', 788, '京津新城支行', 3, 'Y', NULL, NULL, 'admin', 'administrator', 'admin', '06', 

'120000789', NULL, NULL, NULL, 

'20100824', 'admin', NULL, NULL)
;
INSERT INTO zdbranch(BranchInnerCode, BranchCode, ParentInnerCode, Type, OrderFlag, Name, TreeLevel, IsLeaf, Phone, Fax, Manager, Leader1, 

Leader2, Prop1, Prop2, Prop3, Prop4, Memo, AddTime, AddUser, ModifyTime, ModifyUser)
  VALUES('120000095120000805120000790', '2120999121001000K', '120000095120000805', '0', 789, '宁河县支行机关', 3, 'Y', NULL, NULL, 'admin', 'administrator', 'admin', '04', 

'120000790', NULL, NULL, NULL, 

'20100824', 'admin', NULL, NULL)
;
INSERT INTO zdbranch(BranchInnerCode, BranchCode, ParentInnerCode, Type, OrderFlag, Name, TreeLevel, IsLeaf, Phone, Fax, Manager, Leader1, 

Leader2, Prop1, Prop2, Prop3, Prop4, Memo, AddTime, AddUser, ModifyTime, ModifyUser)
  VALUES('120000095120000805120000790120000791', '2120999121001005A', '120000095120000805120000790', '0', 790, '宁河支行综合管理部', 4, 'Y', NULL, NULL, 'admin', 'administrator', 'admin', '04', 

'120000791', NULL, NULL, NULL, 

'20100824', 'admin', NULL, NULL)
;
INSERT INTO zdbranch(BranchInnerCode, BranchCode, ParentInnerCode, Type, OrderFlag, Name, TreeLevel, IsLeaf, Phone, Fax, Manager, Leader1, 

Leader2, Prop1, Prop2, Prop3, Prop4, Memo, AddTime, AddUser, ModifyTime, ModifyUser)
  VALUES('120000095120000805120000790120000792', '21209991210010068', '120000095120000805120000790', '0', 791, '宁河支行行长室', 4, 'Y', NULL, NULL, 'admin', 'administrator', 'admin', '04', 

'120000792', NULL, NULL, NULL, 

'20100824', 'admin', NULL, NULL)
;
INSERT INTO zdbranch(BranchInnerCode, BranchCode, ParentInnerCode, Type, OrderFlag, Name, TreeLevel, IsLeaf, Phone, Fax, Manager, Leader1, 

Leader2, Prop1, Prop2, Prop3, Prop4, Memo, AddTime, AddUser, ModifyTime, ModifyUser)
  VALUES('120000095120000805120000790120000793', '21209991210010076', '120000095120000805120000790', '0', 792, '宁河支行内退人员', 4, 'Y', NULL, NULL, 'admin', 'administrator', 'admin', '04', 

'120000793', NULL, NULL, NULL, 

'20100824', 'admin', NULL, NULL)
;
INSERT INTO zdbranch(BranchInnerCode, BranchCode, ParentInnerCode, Type, OrderFlag, Name, TreeLevel, IsLeaf, Phone, Fax, Manager, Leader1, 

Leader2, Prop1, Prop2, Prop3, Prop4, Memo, AddTime, AddUser, ModifyTime, ModifyUser)
  VALUES('120000095120000805120000790120000794', '21209991210010084', '120000095120000805120000790', '0', 793, '宁河支行其它人员', 4, 'Y', NULL, NULL, 'admin', 'administrator', 'admin', '04', 

'120000794', NULL, NULL, NULL, 

'20100824', 'admin', NULL, NULL)
;
INSERT INTO zdbranch(BranchInnerCode, BranchCode, ParentInnerCode, Type, OrderFlag, Name, TreeLevel, IsLeaf, Phone, Fax, Manager, Leader1, 

Leader2, Prop1, Prop2, Prop3, Prop4, Memo, AddTime, AddUser, ModifyTime, ModifyUser)
  VALUES('120000095120000805120000790120000795', '21209991210010092', '120000095120000805120000790', '0', 794, '宁河支行保险代理部', 4, 'Y', NULL, NULL, 'admin', 'administrator', 'admin', '04', 

'120000795', NULL, NULL, NULL, 

'20100824', 'admin', NULL, NULL)
;
INSERT INTO zdbranch(BranchInnerCode, BranchCode, ParentInnerCode, Type, OrderFlag, Name, TreeLevel, IsLeaf, Phone, Fax, Manager, Leader1, 

Leader2, Prop1, Prop2, Prop3, Prop4, Memo, AddTime, AddUser, ModifyTime, ModifyUser)
  VALUES('120000095120000805120000790120000796', '2120999121001010H', '120000095120000805120000790', '0', 795, '宁河支行风险资产部', 4, 'Y', NULL, NULL, 'admin', 'administrator', 'admin', '04', 

'120000796', NULL, NULL, NULL, 

'20100824', 'admin', NULL, NULL)
;
INSERT INTO zdbranch(BranchInnerCode, BranchCode, ParentInnerCode, Type, OrderFlag, Name, TreeLevel, IsLeaf, Phone, Fax, Manager, Leader1, 

Leader2, Prop1, Prop2, Prop3, Prop4, Memo, AddTime, AddUser, ModifyTime, ModifyUser)
  VALUES('120000095120000805120000790120000797', '2120999121001011F', '120000095120000805120000790', '0', 796, '宁河支行财会运营部', 4, 'Y', NULL, NULL, 'admin', 'administrator', 'admin', '04', 

'120000797', NULL, NULL, NULL, 

'20100824', 'admin', NULL, NULL)
;
INSERT INTO zdbranch(BranchInnerCode, BranchCode, ParentInnerCode, Type, OrderFlag, Name, TreeLevel, IsLeaf, Phone, Fax, Manager, Leader1, 

Leader2, Prop1, Prop2, Prop3, Prop4, Memo, AddTime, AddUser, ModifyTime, ModifyUser)
  VALUES('120000095120000805120000790120000798', '2120999121001012D', '120000095120000805120000790', '0', 797, '宁河支行监察保卫部', 4, 'Y', NULL, NULL, 'admin', 'administrator', 'admin', '04', 

'120000798', NULL, NULL, NULL, 

'20100824', 'admin', NULL, NULL)
;
INSERT INTO zdbranch(BranchInnerCode, BranchCode, ParentInnerCode, Type, OrderFlag, Name, TreeLevel, IsLeaf, Phone, Fax, Manager, Leader1, 

Leader2, Prop1, Prop2, Prop3, Prop4, Memo, AddTime, AddUser, ModifyTime, ModifyUser)
  VALUES('120000095120000805120000790120000799', '2120999121001013B', '120000095120000805120000790', '0', 798, '宁河支行离退休人员', 4, 'Y', NULL, NULL, 'admin', 'administrator', 'admin', '04', 

'120000799', NULL, NULL, NULL, 

'20100824', 'admin', NULL, NULL)
;
INSERT INTO zdbranch(BranchInnerCode, BranchCode, ParentInnerCode, Type, OrderFlag, Name, TreeLevel, IsLeaf, Phone, Fax, Manager, Leader1, 

Leader2, Prop1, Prop2, Prop3, Prop4, Memo, AddTime, AddUser, ModifyTime, ModifyUser)
  VALUES('120000095120000805120000790120000800', '21209991210010149', '120000095120000805120000790', '0', 799, '宁河支行人力资源部', 4, 'Y', NULL, NULL, 'admin', 'administrator', 'admin', '04', 

'120000800', NULL, NULL, NULL, 

'20100824', 'admin', NULL, NULL)
;
INSERT INTO zdbranch(BranchInnerCode, BranchCode, ParentInnerCode, Type, OrderFlag, Name, TreeLevel, IsLeaf, Phone, Fax, Manager, Leader1, 

Leader2, Prop1, Prop2, Prop3, Prop4, Memo, AddTime, AddUser, ModifyTime, ModifyUser)
  VALUES('120000095120000805120000790120000801', '21209991210010157', '120000095120000805120000790', '0', 800, '宁河支行信贷管理部', 4, 'Y', NULL, NULL, 'admin', 'administrator', 'admin', '04', 

'120000801', NULL, NULL, NULL, 

'20100824', 'admin', NULL, NULL)
;
INSERT INTO zdbranch(BranchInnerCode, BranchCode, ParentInnerCode, Type, OrderFlag, Name, TreeLevel, IsLeaf, Phone, Fax, Manager, Leader1, 

Leader2, Prop1, Prop2, Prop3, Prop4, Memo, AddTime, AddUser, ModifyTime, ModifyUser)
  VALUES('120000095120000805120000790120000802', '2120999121001002G', '120000095120000805120000790', '0', 801, '宁河支行公司业务部', 4, 'Y', NULL, NULL, 'admin', 'administrator', 'admin', '04', 

'120000802', NULL, NULL, NULL, 

'20100824', 'admin', NULL, NULL)
;
INSERT INTO zdbranch(BranchInnerCode, BranchCode, ParentInnerCode, Type, OrderFlag, Name, TreeLevel, IsLeaf, Phone, Fax, Manager, Leader1, 

Leader2, Prop1, Prop2, Prop3, Prop4, Memo, AddTime, AddUser, ModifyTime, ModifyUser)
  VALUES('120000095120000805120000790120000803', '2120999121001003E', '120000095120000805120000790', '0', 802, '宁河支行个人金融部', 4, 'Y', NULL, NULL, 'admin', 'administrator', 'admin', '04', 

'120000803', NULL, NULL, NULL, 

'20100824', 'admin', NULL, NULL)
;
INSERT INTO zdbranch(BranchInnerCode, BranchCode, ParentInnerCode, Type, OrderFlag, Name, TreeLevel, IsLeaf, Phone, Fax, Manager, Leader1, 

Leader2, Prop1, Prop2, Prop3, Prop4, Memo, AddTime, AddUser, ModifyTime, ModifyUser)
  VALUES('120000095120000805120000790120000804', '2120999121001004C', '120000095120000805120000790', '0', 803, '宁河支行内部分流人员', 4, 'Y', NULL, NULL, 'admin', 'administrator', 'admin', 

'04', '120000804', NULL, NULL, NULL, 

'20100824', 'admin', NULL, NULL)
;
INSERT INTO zdbranch(BranchInnerCode, BranchCode, ParentInnerCode, Type, OrderFlag, Name, TreeLevel, IsLeaf, Phone, Fax, Manager, Leader1, 

Leader2, Prop1, Prop2, Prop3, Prop4, Memo, AddTime, AddUser, ModifyTime, ModifyUser)
  VALUES('120000095120000805', '2120999121000000P', '120000095', '0', 804, '宁河县支行', 2, 'Y', NULL, NULL, 'admin', 'administrator', 'admin', '04', '120000805', 

NULL, NULL, NULL, 

'20100824', 'admin', NULL, NULL)
;
INSERT INTO zdbranch(BranchInnerCode, BranchCode, ParentInnerCode, Type, OrderFlag, Name, TreeLevel, IsLeaf, Phone, Fax, Manager, Leader1, 

Leader2, Prop1, Prop2, Prop3, Prop4, Memo, AddTime, AddUser, ModifyTime, ModifyUser)
  VALUES('120000095120000805120000806', '21209991211020008', '120000095120000805', '0', 805, '宁河县芦汉路分理处', 3, 'Y', NULL, NULL, 'admin', 'administrator', 'admin', 

'06', '120000806', NULL, NULL, NULL, 

'20100824', 'admin', NULL, NULL)
;
INSERT INTO zdbranch(BranchInnerCode, BranchCode, ParentInnerCode, Type, OrderFlag, Name, TreeLevel, IsLeaf, Phone, Fax, Manager, Leader1, 

Leader2, Prop1, Prop2, Prop3, Prop4, Memo, AddTime, AddUser, ModifyTime, ModifyUser)
  VALUES('120000095120000805120000807', '21209991211030003', '120000095120000805', '0', 806, '宁河县商业道支行', 3, 'Y', NULL, NULL, 'admin', 'administrator', 'admin', '06', 

'120000807', NULL, NULL, NULL, 

'20100824', 'admin', NULL, NULL)
;
INSERT INTO zdbranch(BranchInnerCode, BranchCode, ParentInnerCode, Type, OrderFlag, Name, TreeLevel, IsLeaf, Phone, Fax, Manager, Leader1, 

Leader2, Prop1, Prop2, Prop3, Prop4, Memo, AddTime, AddUser, ModifyTime, ModifyUser)
  VALUES('120000095120000805120000808', '2120999121104000T', '120000095120000805', '0', 807, '宁河县新华分理处', 3, 'Y', NULL, NULL, 'admin', 'administrator', 'admin', '06', 

'120000808', NULL, NULL, NULL, 

'20100824', 'admin', NULL, NULL)
;
INSERT INTO zdbranch(BranchInnerCode, BranchCode, ParentInnerCode, Type, OrderFlag, Name, TreeLevel, IsLeaf, Phone, Fax, Manager, Leader1, 

Leader2, Prop1, Prop2, Prop3, Prop4, Memo, AddTime, AddUser, ModifyTime, ModifyUser)
  VALUES('120000095120000805120000809', '2120999121105000O', '120000095120000805', '0', 808, '宁河县震新路分理处', 3, 'Y', NULL, NULL, 'admin', 'administrator', 'admin', 

'06', '120000809', NULL, NULL, NULL, 

'20100824', 'admin', NULL, NULL)
;
INSERT INTO zdbranch(BranchInnerCode, BranchCode, ParentInnerCode, Type, OrderFlag, Name, TreeLevel, IsLeaf, Phone, Fax, Manager, Leader1, 

Leader2, Prop1, Prop2, Prop3, Prop4, Memo, AddTime, AddUser, ModifyTime, ModifyUser)
  VALUES('120000095120000805120000810', '2120999121106000J', '120000095120000805', '0', 809, '宁河县金华路分理处', 3, 'Y', NULL, NULL, 'admin', 'administrator', 'admin', 

'06', '120000810', NULL, NULL, NULL, 

'20100824', 'admin', NULL, NULL)
;
INSERT INTO zdbranch(BranchInnerCode, BranchCode, ParentInnerCode, Type, OrderFlag, Name, TreeLevel, IsLeaf, Phone, Fax, Manager, Leader1, 

Leader2, Prop1, Prop2, Prop3, Prop4, Memo, AddTime, AddUser, ModifyTime, ModifyUser)
  VALUES('120000095120000805120000811', '2120999121107000E', '120000095120000805', '0', 810, '宁河县芦台分理处', 3, 'Y', NULL, NULL, 'admin', 'administrator', 'admin', '06', 

'120000811', NULL, NULL, NULL, 

'20100824', 'admin', NULL, NULL)
;
INSERT INTO zdbranch(BranchInnerCode, BranchCode, ParentInnerCode, Type, OrderFlag, Name, TreeLevel, IsLeaf, Phone, Fax, Manager, Leader1, 

Leader2, Prop1, Prop2, Prop3, Prop4, Memo, AddTime, AddUser, ModifyTime, ModifyUser)
  VALUES('120000095120000805120000812', '21209991211200006', '120000095120000805', '0', 811, '宁河支行芦台储蓄所', 3, 'Y', NULL, NULL, 'admin', 'administrator', 'admin', 

'07', '120000812', NULL, NULL, NULL, 

'20100824', 'admin', NULL, NULL)
;
INSERT INTO zdbranch(BranchInnerCode, BranchCode, ParentInnerCode, Type, OrderFlag, Name, TreeLevel, IsLeaf, Phone, Fax, Manager, Leader1, 

Leader2, Prop1, Prop2, Prop3, Prop4, Memo, AddTime, AddUser, ModifyTime, ModifyUser)
  VALUES('120000095120000805120000813', '21209991211080009', '120000095120000805', '0', 812, '宁河县丰台分理处', 3, 'Y', NULL, NULL, 'admin', 'administrator', 'admin', '06', 

'120000813', NULL, NULL, NULL, 

'20100824', 'admin', NULL, NULL)
;
INSERT INTO zdbranch(BranchInnerCode, BranchCode, ParentInnerCode, Type, OrderFlag, Name, TreeLevel, IsLeaf, Phone, Fax, Manager, Leader1, 

Leader2, Prop1, Prop2, Prop3, Prop4, Memo, AddTime, AddUser, ModifyTime, ModifyUser)
  VALUES('120000095120000805120000814', '21209991211090004', '120000095120000805', '0', 813, '宁河县岳龙分理处', 3, 'Y', NULL, NULL, 'admin', 'administrator', 'admin', '06', 

'120000814', NULL, NULL, NULL, 

'20100824', 'admin', NULL, NULL)
;
INSERT INTO zdbranch(BranchInnerCode, BranchCode, ParentInnerCode, Type, OrderFlag, Name, TreeLevel, IsLeaf, Phone, Fax, Manager, Leader1, 

Leader2, Prop1, Prop2, Prop3, Prop4, Memo, AddTime, AddUser, ModifyTime, ModifyUser)
  VALUES('120000095120000805120000815', '2120999121110000C', '120000095120000805', '0', 814, '宁河县潘庄分理处', 3, 'Y', NULL, NULL, 'admin', 'administrator', 'admin', '06', 

'120000815', NULL, NULL, NULL, 

'20100824', 'admin', NULL, NULL)
;
INSERT INTO zdbranch(BranchInnerCode, BranchCode, ParentInnerCode, Type, OrderFlag, Name, TreeLevel, IsLeaf, Phone, Fax, Manager, Leader1, 

Leader2, Prop1, Prop2, Prop3, Prop4, Memo, AddTime, AddUser, ModifyTime, ModifyUser)
  VALUES('120000095120000805120000816', '21209991211110007', '120000095120000805', '0', 815, '宁河县清河农场分理处', 3, 'Y', NULL, NULL, 'admin', 'administrator', 'admin', 

'06', '120000816', NULL, NULL, 

NULL, '20100824', 'admin', NULL, NULL)
;
INSERT INTO zdbranch(BranchInnerCode, BranchCode, ParentInnerCode, Type, OrderFlag, Name, TreeLevel, IsLeaf, Phone, Fax, Manager, Leader1, 

Leader2, Prop1, Prop2, Prop3, Prop4, Memo, AddTime, AddUser, ModifyTime, ModifyUser)
  VALUES('120000095120000805120000816120000817', '21209991211111011', '120000095120000805120000816', '0', 816, '宁河支行清河五科储蓄所', 4, 'Y', NULL, NULL, 'admin', 'administrator', 'admin', 

'07', '120000817', NULL, NULL, 

NULL, '20100824', 'admin', NULL, NULL)
;
INSERT INTO zdbranch(BranchInnerCode, BranchCode, ParentInnerCode, Type, OrderFlag, Name, TreeLevel, IsLeaf, Phone, Fax, Manager, Leader1, 

Leader2, Prop1, Prop2, Prop3, Prop4, Memo, AddTime, AddUser, ModifyTime, ModifyUser)
  VALUES('120000095120000805120000818', '21209991211120002', '120000095120000805', '0', 817, '宁河县兴宁分理处', 3, 'Y', NULL, NULL, 'admin', 'administrator', 'admin', '06', 

'120000818', NULL, NULL, NULL, 

'20100824', 'admin', NULL, NULL)
;
INSERT INTO zdbranch(BranchInnerCode, BranchCode, ParentInnerCode, Type, OrderFlag, Name, TreeLevel, IsLeaf, Phone, Fax, Manager, Leader1, 

Leader2, Prop1, Prop2, Prop3, Prop4, Memo, AddTime, AddUser, ModifyTime, ModifyUser)
  VALUES('120000095120000805120000819', '2120999121113000S', '120000095120000805', '0', 818, '宁河县廉庄分理处', 3, 'Y', NULL, NULL, 'admin', 'administrator', 'admin', '06', 

'120000819', NULL, NULL, NULL, 

'20100824', 'admin', NULL, NULL)
;
INSERT INTO zdbranch(BranchInnerCode, BranchCode, ParentInnerCode, Type, OrderFlag, Name, TreeLevel, IsLeaf, Phone, Fax, Manager, Leader1, 

Leader2, Prop1, Prop2, Prop3, Prop4, Memo, AddTime, AddUser, ModifyTime, ModifyUser)
  VALUES('120000095120000805120000820', '2120999121114000N', '120000095120000805', '0', 819, '宁河县七里海分理处', 3, 'Y', NULL, NULL, 'admin', 'administrator', 'admin', 

'06', '120000820', NULL, NULL, NULL, 

'20100824', 'admin', NULL, NULL)
;
INSERT INTO zdbranch(BranchInnerCode, BranchCode, ParentInnerCode, Type, OrderFlag, Name, TreeLevel, IsLeaf, Phone, Fax, Manager, Leader1, 

Leader2, Prop1, Prop2, Prop3, Prop4, Memo, AddTime, AddUser, ModifyTime, ModifyUser)
  VALUES('120000095120000805120000821', '2120999121115000I', '120000095120000805', '0', 820, '宁河县淮淀分理处', 3, 'Y', NULL, NULL, 'admin', 'administrator', 'admin', '06', 

'120000821', NULL, NULL, NULL, 

'20100824', 'admin', NULL, NULL)
;
INSERT INTO zdbranch(BranchInnerCode, BranchCode, ParentInnerCode, Type, OrderFlag, Name, TreeLevel, IsLeaf, Phone, Fax, Manager, Leader1, 

Leader2, Prop1, Prop2, Prop3, Prop4, Memo, AddTime, AddUser, ModifyTime, ModifyUser)
  VALUES('120000095120000805120000822', '2120999121116000D', '120000095120000805', '0', 821, '宁河支行苗庄营业所', 3, 'Y', NULL, NULL, 'admin', 'administrator', 'admin', 

'06', '120000822', NULL, NULL, NULL, 

'20100824', 'admin', NULL, NULL)
;
INSERT INTO zdbranch(BranchInnerCode, BranchCode, ParentInnerCode, Type, OrderFlag, Name, TreeLevel, IsLeaf, Phone, Fax, Manager, Leader1, 

Leader2, Prop1, Prop2, Prop3, Prop4, Memo, AddTime, AddUser, ModifyTime, ModifyUser)
  VALUES('120000095120000805120000823', '21209991211170008', '120000095120000805', '0', 822, '宁河县板桥分理处', 3, 'Y', NULL, NULL, 'admin', 'administrator', 'admin', '06', 

'120000823', NULL, NULL, NULL, 

'20100824', 'admin', NULL, NULL)
;
INSERT INTO zdbranch(BranchInnerCode, BranchCode, ParentInnerCode, Type, OrderFlag, Name, TreeLevel, IsLeaf, Phone, Fax, Manager, Leader1, 

Leader2, Prop1, Prop2, Prop3, Prop4, Memo, AddTime, AddUser, ModifyTime, ModifyUser)
  VALUES('120000095120000805120000824', '21209991211180003', '120000095120000805', '0', 823, '宁河县开发区分理处', 3, 'Y', NULL, NULL, 'admin', 'administrator', 'admin', 

'06', '120000824', NULL, NULL, NULL, 

'20100824', 'admin', NULL, NULL)
;
INSERT INTO zdbranch(BranchInnerCode, BranchCode, ParentInnerCode, Type, OrderFlag, Name, TreeLevel, IsLeaf, Phone, Fax, Manager, Leader1, 

Leader2, Prop1, Prop2, Prop3, Prop4, Memo, AddTime, AddUser, ModifyTime, ModifyUser)
  VALUES('120000095120000825', '21209991230000009', '120000095', '0', 824, '塘沽区分行', 2, 'Y', NULL, NULL, 'admin', 'administrator', 'admin', '04', '120000825', 

NULL, NULL, NULL, 

'20100824', 'admin', NULL, NULL)
;
INSERT INTO zdbranch(BranchInnerCode, BranchCode, ParentInnerCode, Type, OrderFlag, Name, TreeLevel, IsLeaf, Phone, Fax, Manager, Leader1, 

Leader2, Prop1, Prop2, Prop3, Prop4, Memo, AddTime, AddUser, ModifyTime, ModifyUser)
  VALUES('120000095120000825120000826', '21209991230010004', '120000095120000825', '0', 825, '塘沽区分行机关', 3, 'Y', NULL, NULL, 'admin', 'administrator', 'admin', '04', 

'120000826', NULL, NULL, NULL, 

'20100824', 'admin', NULL, NULL)
;
INSERT INTO zdbranch(BranchInnerCode, BranchCode, ParentInnerCode, Type, OrderFlag, Name, TreeLevel, IsLeaf, Phone, Fax, Manager, Leader1, 

Leader2, Prop1, Prop2, Prop3, Prop4, Memo, AddTime, AddUser, ModifyTime, ModifyUser)
  VALUES('120000095120000825120000826120000827', '21209991230010012', '120000095120000825120000826', '0', 826, '塘沽分行其他', 4, 'Y', NULL, NULL, 'admin', 'administrator', 'admin', '04', '120000827', 

NULL, NULL, NULL, 

'20100824', 'admin', NULL, NULL)
;
INSERT INTO zdbranch(BranchInnerCode, BranchCode, ParentInnerCode, Type, OrderFlag, Name, TreeLevel, IsLeaf, Phone, Fax, Manager, Leader1, 

Leader2, Prop1, Prop2, Prop3, Prop4, Memo, AddTime, AddUser, ModifyTime, ModifyUser)
  VALUES('120000095120000825120000826120000828', '2120999123001002V', '120000095120000825120000826', '0', 827, '塘沽分行综合管理部', 4, 'Y', NULL, NULL, 'admin', 'administrator', 'admin', '04', 

'120000828', NULL, NULL, NULL, 

'20100824', 'admin', NULL, NULL)
;
INSERT INTO zdbranch(BranchInnerCode, BranchCode, ParentInnerCode, Type, OrderFlag, Name, TreeLevel, IsLeaf, Phone, Fax, Manager, Leader1, 

Leader2, Prop1, Prop2, Prop3, Prop4, Memo, AddTime, AddUser, ModifyTime, ModifyUser)
  VALUES('120000095120000825120000826120000829', '2120999123001003T', '120000095120000825120000826', '0', 828, '塘沽分行行长室', 4, 'Y', NULL, NULL, 'admin', 'administrator', 'admin', '04', 

'120000829', NULL, NULL, NULL, 

'20100824', 'admin', NULL, NULL)
;
INSERT INTO zdbranch(BranchInnerCode, BranchCode, ParentInnerCode, Type, OrderFlag, Name, TreeLevel, IsLeaf, Phone, Fax, Manager, Leader1, 

Leader2, Prop1, Prop2, Prop3, Prop4, Memo, AddTime, AddUser, ModifyTime, ModifyUser)
  VALUES('120000095120000825120000826120000830', '2120999123001004R', '120000095120000825120000826', '0', 829, '塘沽分行保险代理部', 4, 'Y', NULL, NULL, 'admin', 'administrator', 'admin', '04', 

'120000830', NULL, NULL, NULL, 

'20100824', 'admin', NULL, NULL)
;
INSERT INTO zdbranch(BranchInnerCode, BranchCode, ParentInnerCode, Type, OrderFlag, Name, TreeLevel, IsLeaf, Phone, Fax, Manager, Leader1, 

Leader2, Prop1, Prop2, Prop3, Prop4, Memo, AddTime, AddUser, ModifyTime, ModifyUser)
  VALUES('120000095120000825120000826120000831', '2120999123001005P', '120000095120000825120000826', '0', 830, '塘沽分行国际业务部', 4, 'Y', NULL, NULL, 'admin', 'administrator', 'admin', '04', 

'120000831', NULL, NULL, NULL, 

'20100824', 'admin', NULL, NULL)
;
INSERT INTO zdbranch(BranchInnerCode, BranchCode, ParentInnerCode, Type, OrderFlag, Name, TreeLevel, IsLeaf, Phone, Fax, Manager, Leader1, 

Leader2, Prop1, Prop2, Prop3, Prop4, Memo, AddTime, AddUser, ModifyTime, ModifyUser)
  VALUES('120000095120000825120000826120000832', '2120999123001006N', '120000095120000825120000826', '0', 831, '塘沽分行财会运营部', 4, 'Y', NULL, NULL, 'admin', 'administrator', 'admin', '04', 

'120000832', NULL, NULL, NULL, 

'20100824', 'admin', NULL, NULL)
;
INSERT INTO zdbranch(BranchInnerCode, BranchCode, ParentInnerCode, Type, OrderFlag, Name, TreeLevel, IsLeaf, Phone, Fax, Manager, Leader1, 

Leader2, Prop1, Prop2, Prop3, Prop4, Memo, AddTime, AddUser, ModifyTime, ModifyUser)
  VALUES('120000095120000825120000826120000833', '2120999123001007L', '120000095120000825120000826', '0', 832, '塘沽分行监察保卫部', 4, 'Y', NULL, NULL, 'admin', 'administrator', 'admin', '04', 

'120000833', NULL, NULL, NULL, 

'20100824', 'admin', NULL, NULL)
;
INSERT INTO zdbranch(BranchInnerCode, BranchCode, ParentInnerCode, Type, OrderFlag, Name, TreeLevel, IsLeaf, Phone, Fax, Manager, Leader1, 

Leader2, Prop1, Prop2, Prop3, Prop4, Memo, AddTime, AddUser, ModifyTime, ModifyUser)
  VALUES('120000095120000825120000826120000834', '2120999123001008J', '120000095120000825120000826', '0', 833, '塘沽分行离退休人员', 4, 'Y', NULL, NULL, 'admin', 'administrator', 'admin', '04', 

'120000834', NULL, NULL, NULL, 

'20100824', 'admin', NULL, NULL)
;
INSERT INTO zdbranch(BranchInnerCode, BranchCode, ParentInnerCode, Type, OrderFlag, Name, TreeLevel, IsLeaf, Phone, Fax, Manager, Leader1, 

Leader2, Prop1, Prop2, Prop3, Prop4, Memo, AddTime, AddUser, ModifyTime, ModifyUser)
  VALUES('120000095120000825120000826120000835', '2120999123001009H', '120000095120000825120000826', '0', 834, '塘沽分行信贷管理部', 4, 'Y', NULL, NULL, 'admin', 'administrator', 'admin', '04', 

'120000835', NULL, NULL, NULL, 

'20100824', 'admin', NULL, NULL)
;
INSERT INTO zdbranch(BranchInnerCode, BranchCode, ParentInnerCode, Type, OrderFlag, Name, TreeLevel, IsLeaf, Phone, Fax, Manager, Leader1, 

Leader2, Prop1, Prop2, Prop3, Prop4, Memo, AddTime, AddUser, ModifyTime, ModifyUser)
  VALUES('120000095120000825120000826120000836', '21209991230010101', '120000095120000825120000826', '0', 835, '塘沽分行公司业务部', 4, 'Y', NULL, NULL, 'admin', 'administrator', 'admin', '04', 

'120000836', NULL, NULL, NULL, 

'20100824', 'admin', NULL, NULL)
;
INSERT INTO zdbranch(BranchInnerCode, BranchCode, ParentInnerCode, Type, OrderFlag, Name, TreeLevel, IsLeaf, Phone, Fax, Manager, Leader1, 

Leader2, Prop1, Prop2, Prop3, Prop4, Memo, AddTime, AddUser, ModifyTime, ModifyUser)
  VALUES('120000095120000825120000826120000837', '2120999123001011U', '120000095120000825120000826', '0', 836, '塘沽分行个人金融部', 4, 'Y', NULL, NULL, 'admin', 'administrator', 'admin', '04', 

'120000837', NULL, NULL, NULL, 

'20100824', 'admin', NULL, NULL)
;
INSERT INTO zdbranch(BranchInnerCode, BranchCode, ParentInnerCode, Type, OrderFlag, Name, TreeLevel, IsLeaf, Phone, Fax, Manager, Leader1, 

Leader2, Prop1, Prop2, Prop3, Prop4, Memo, AddTime, AddUser, ModifyTime, ModifyUser)
  VALUES('120000095120000825120000838', '2120999123101000S', '120000095120000825', '0', 837, '第二大街支行', 3, 'Y', NULL, NULL, 'admin', 'administrator', 'admin', '06', 

'120000838', NULL, NULL, NULL, 

'20100824', 'admin', NULL, NULL)
;
INSERT INTO zdbranch(BranchInnerCode, BranchCode, ParentInnerCode, Type, OrderFlag, Name, TreeLevel, IsLeaf, Phone, Fax, Manager, Leader1, 

Leader2, Prop1, Prop2, Prop3, Prop4, Memo, AddTime, AddUser, ModifyTime, ModifyUser)
  VALUES('120000095120000825120000839', '2120999123109000J', '120000095120000825', '0', 838, '塘沽分行戏院分理处', 3, 'Y', NULL, NULL, 'admin', 'administrator', 'admin', '06', 

'120000839', NULL, NULL, NULL, 

'20100824', 'admin', NULL, NULL)
;
INSERT INTO zdbranch(BranchInnerCode, BranchCode, ParentInnerCode, Type, OrderFlag, Name, TreeLevel, IsLeaf, Phone, Fax, Manager, Leader1, 

Leader2, Prop1, Prop2, Prop3, Prop4, Memo, AddTime, AddUser, ModifyTime, ModifyUser)
  VALUES('120000095120000825120000840', '21209991231150002', '120000095120000825', '0', 839, '塘沽区金街分理处', 3, 'Y', NULL, NULL, 'admin', 'administrator', 'admin', '06', 

'120000840', NULL, NULL, NULL, 

'20100824', 'admin', NULL, NULL)
;
INSERT INTO zdbranch(BranchInnerCode, BranchCode, ParentInnerCode, Type, OrderFlag, Name, TreeLevel, IsLeaf, Phone, Fax, Manager, Leader1, 

Leader2, Prop1, Prop2, Prop3, Prop4, Memo, AddTime, AddUser, ModifyTime, ModifyUser)
  VALUES('120000095120000825120000841', '2120999123112000H', '120000095120000825', '0', 840, '塘沽区紫云分理处', 3, 'Y', NULL, NULL, 'admin', 'administrator', 'admin', '06', 

'120000841', NULL, NULL, NULL, 

'20100824', 'admin', NULL, NULL)
;
INSERT INTO zdbranch(BranchInnerCode, BranchCode, ParentInnerCode, Type, OrderFlag, Name, TreeLevel, IsLeaf, Phone, Fax, Manager, Leader1, 

Leader2, Prop1, Prop2, Prop3, Prop4, Memo, AddTime, AddUser, ModifyTime, ModifyUser)
  VALUES('120000095120000825120000842', '2120999123111000M', '120000095120000825', '0', 841, '福州道支行', 3, 'Y', NULL, NULL, 'admin', 'administrator', 'admin', '06', '120000842', 

NULL, NULL, NULL, 

'20100824', 'admin', NULL, NULL)
;
INSERT INTO zdbranch(BranchInnerCode, BranchCode, ParentInnerCode, Type, OrderFlag, Name, TreeLevel, IsLeaf, Phone, Fax, Manager, Leader1, 

Leader2, Prop1, Prop2, Prop3, Prop4, Memo, AddTime, AddUser, ModifyTime, ModifyUser)
  VALUES('120000095120000825120000843', '2120999123102000N', '120000095120000825', '0', 842, '河北路支行', 3, 'Y', NULL, NULL, 'admin', 'administrator', 'admin', '06', '120000843', 

NULL, NULL, NULL, 

'20100824', 'admin', NULL, NULL)
;
INSERT INTO zdbranch(BranchInnerCode, BranchCode, ParentInnerCode, Type, OrderFlag, Name, TreeLevel, IsLeaf, Phone, Fax, Manager, Leader1, 

Leader2, Prop1, Prop2, Prop3, Prop4, Memo, AddTime, AddUser, ModifyTime, ModifyUser)
  VALUES('120000095120000825120000844', '2120999123113000C', '120000095120000825', '0', 843, '塘沽分行渤东分理处', 3, 'Y', NULL, NULL, 'admin', 'administrator', 'admin', '06', 

'120000844', NULL, NULL, NULL, 

'20100824', 'admin', NULL, NULL)
;
INSERT INTO zdbranch(BranchInnerCode, BranchCode, ParentInnerCode, Type, OrderFlag, Name, TreeLevel, IsLeaf, Phone, Fax, Manager, Leader1, 

Leader2, Prop1, Prop2, Prop3, Prop4, Memo, AddTime, AddUser, ModifyTime, ModifyUser)
  VALUES('120000095120000825120000845', '21209991231140007', '120000095120000825', '0', 844, '塘沽区渤升分理处', 3, 'Y', NULL, NULL, 'admin', 'administrator', 'admin', '06', 

'120000845', NULL, NULL, NULL, 

'20100824', 'admin', NULL, NULL)
;
INSERT INTO zdbranch(BranchInnerCode, BranchCode, ParentInnerCode, Type, OrderFlag, Name, TreeLevel, IsLeaf, Phone, Fax, Manager, Leader1, 

Leader2, Prop1, Prop2, Prop3, Prop4, Memo, AddTime, AddUser, ModifyTime, ModifyUser)
  VALUES('120000095120000825120000846', '2120999123103000I', '120000095120000825', '0', 845, '新洋支行', 3, 'Y', NULL, NULL, 'admin', 'administrator', 'admin', '06', '120000846', 

NULL, NULL, NULL, '20100824', 'admin', NULL, NULL);
INSERT INTO zdbranch(BranchInnerCode, BranchCode, ParentInnerCode, Type, OrderFlag, Name, TreeLevel, IsLeaf, Phone, Fax, Manager, Leader1, 

Leader2, Prop1, Prop2, Prop3, Prop4, Memo, AddTime, AddUser, ModifyTime, ModifyUser)
  VALUES('120000095120000825120000847', '2120999123116000S', '120000095120000825', '0', 846, '塘沽区胡家园分理处', 3, 'Y', NULL, NULL, 'admin', 'administrator', 'admin', 

'06', '120000847', NULL, NULL, NULL, 

'20100824', 'admin', NULL, NULL)
;
INSERT INTO zdbranch(BranchInnerCode, BranchCode, ParentInnerCode, Type, OrderFlag, Name, TreeLevel, IsLeaf, Phone, Fax, Manager, Leader1, 

Leader2, Prop1, Prop2, Prop3, Prop4, Memo, AddTime, AddUser, ModifyTime, ModifyUser)
  VALUES('120000095120000825120000848', '2120999123104000D', '120000095120000825', '0', 847, '翠亨广场支行', 3, 'Y', NULL, NULL, 'admin', 'administrator', 'admin', '06', 

'120000848', NULL, NULL, NULL, 

'20100824', 'admin', NULL, NULL)
;
INSERT INTO zdbranch(BranchInnerCode, BranchCode, ParentInnerCode, Type, OrderFlag, Name, TreeLevel, IsLeaf, Phone, Fax, Manager, Leader1, 

Leader2, Prop1, Prop2, Prop3, Prop4, Memo, AddTime, AddUser, ModifyTime, ModifyUser)
  VALUES('120000095120000825120000848120000849', '21209991231041017', '120000095120000825120000848', '0', 848, '塘沽分行普利达开发区储蓄所', 4, 'Y', NULL, NULL, 'admin', 'administrator', 

'admin', '10', '120000849', NULL, NULL, 

NULL, '20100824', 'admin', NULL, NULL)
;
INSERT INTO zdbranch(BranchInnerCode, BranchCode, ParentInnerCode, Type, OrderFlag, Name, TreeLevel, IsLeaf, Phone, Fax, Manager, Leader1, 

Leader2, Prop1, Prop2, Prop3, Prop4, Memo, AddTime, AddUser, ModifyTime, ModifyUser)
  VALUES('120000095120000825120000850', '2120999123110000R', '120000095120000825', '0', 849, '杭州道支行', 3, 'Y', NULL, NULL, 'admin', 'administrator', 'admin', '06', '120000850', 

NULL, NULL, NULL, 

'20100824', 'admin', NULL, NULL)
;
INSERT INTO zdbranch(BranchInnerCode, BranchCode, ParentInnerCode, Type, OrderFlag, Name, TreeLevel, IsLeaf, Phone, Fax, Manager, Leader1, 

Leader2, Prop1, Prop2, Prop3, Prop4, Memo, AddTime, AddUser, ModifyTime, ModifyUser)
  VALUES('120000095120000825120000851', '21209991231050008', '120000095120000825', '0', 850, '新港支行', 3, 'Y', NULL, NULL, 'admin', 'administrator', 'admin', '06', '120000851', 

NULL, NULL, NULL, '20100824', 'admin', NULL, NULL);
INSERT INTO zdbranch(BranchInnerCode, BranchCode, ParentInnerCode, Type, OrderFlag, Name, TreeLevel, IsLeaf, Phone, Fax, Manager, Leader1, 

Leader2, Prop1, Prop2, Prop3, Prop4, Memo, AddTime, AddUser, ModifyTime, ModifyUser)
  VALUES('120000095120000825120000852', '21209991231060003', '120000095120000825', '0', 851, '渤海石油支行', 3, 'Y', NULL, NULL, 'admin', 'administrator', 'admin', '06', 

'120000852', NULL, NULL, NULL, 

'20100824', 'admin', NULL, NULL)
;
INSERT INTO zdbranch(BranchInnerCode, BranchCode, ParentInnerCode, Type, OrderFlag, Name, TreeLevel, IsLeaf, Phone, Fax, Manager, Leader1, 

Leader2, Prop1, Prop2, Prop3, Prop4, Memo, AddTime, AddUser, ModifyTime, ModifyUser)
  VALUES('120000095120000825120000853', '2120999123107000T', '120000095120000825', '0', 852, '上海道支行', 3, 'Y', NULL, NULL, 'admin', 'administrator', 'admin', '06', '120000853', 

NULL, NULL, NULL, 

'20100824', 'admin', NULL, NULL)
;
INSERT INTO zdbranch(BranchInnerCode, BranchCode, ParentInnerCode, Type, OrderFlag, Name, TreeLevel, IsLeaf, Phone, Fax, Manager, Leader1, 

Leader2, Prop1, Prop2, Prop3, Prop4, Memo, AddTime, AddUser, ModifyTime, ModifyUser)
  VALUES('120000095120000825120000854', '2120999123108000O', '120000095120000825', '0', 853, '广州道支行', 3, 'Y', NULL, NULL, 'admin', 'administrator', 'admin', '06', '120000854', 

NULL, NULL, NULL, 

'20100824', 'admin', NULL, NULL)
;
INSERT INTO zdbranch(BranchInnerCode, BranchCode, ParentInnerCode, Type, OrderFlag, Name, TreeLevel, IsLeaf, Phone, Fax, Manager, Leader1, 

Leader2, Prop1, Prop2, Prop3, Prop4, Memo, AddTime, AddUser, ModifyTime, ModifyUser)
  VALUES('120000095120000909120000855', '21209991261160004', '120000095120000909', '0', 854, '汉沽支行', 3, 'Y', NULL, NULL, 'admin', 'administrator', 'admin', '06', '120000855', 

NULL, NULL, NULL, '20100824', 'admin', NULL, NULL);
INSERT INTO zdbranch(BranchInnerCode, BranchCode, ParentInnerCode, Type, OrderFlag, Name, TreeLevel, IsLeaf, Phone, Fax, Manager, Leader1, 

Leader2, Prop1, Prop2, Prop3, Prop4, Memo, AddTime, AddUser, ModifyTime, ModifyUser)
  VALUES('120000095120000909120000855120000856', '2120999126109000Q', '120000095120000909120000855', '0', 855, '汉沽支行机关', 4, 'Y', NULL, NULL, 'admin', 'administrator', 'admin', '06', '120000856', 

NULL, NULL, NULL, 

'20100824', 'admin', NULL, NULL)
;
INSERT INTO zdbranch(BranchInnerCode, BranchCode, ParentInnerCode, Type, OrderFlag, Name, TreeLevel, IsLeaf, Phone, Fax, Manager, Leader1, 

Leader2, Prop1, Prop2, Prop3, Prop4, Memo, AddTime, AddUser, ModifyTime, ModifyUser)
  VALUES('120000095120000909120000855120000856120000857', '2120999126109012J', '120000095120000909120000855120000856', '0', 856, '汉沽支行办公室', 5, 'Y', NULL, NULL, 'admin', 'administrator', 'admin', '06', 

'120000857', NULL, NULL, NULL, 

'20100824', 'admin', NULL, NULL)
;
INSERT INTO zdbranch(BranchInnerCode, BranchCode, ParentInnerCode, Type, OrderFlag, Name, TreeLevel, IsLeaf, Phone, Fax, Manager, Leader1, 

Leader2, Prop1, Prop2, Prop3, Prop4, Memo, AddTime, AddUser, ModifyTime, ModifyUser)
  VALUES('120000095120000909120000855120000856120000858', '2120999126109010N', '120000095120000909120000855120000856', '0', 857, '汉沽支行行长室', 5, 'Y', NULL, NULL, 'admin', 'administrator', 'admin', '06', 

'120000858', NULL, NULL, NULL, 

'20100824', 'admin', NULL, NULL)
;
INSERT INTO zdbranch(BranchInnerCode, BranchCode, ParentInnerCode, Type, OrderFlag, Name, TreeLevel, IsLeaf, Phone, Fax, Manager, Leader1, 

Leader2, Prop1, Prop2, Prop3, Prop4, Memo, AddTime, AddUser, ModifyTime, ModifyUser)
  VALUES('120000095120000909120000855120000856120000859', '2120999126109016B', '120000095120000909120000855120000856', '0', 858, '汉沽支行内退人员', 5, 'Y', NULL, NULL, 'admin', 'administrator', 'admin', '06', 

'120000859', NULL, NULL, NULL, 

'20100824', 'admin', NULL, NULL)
;
INSERT INTO zdbranch(BranchInnerCode, BranchCode, ParentInnerCode, Type, OrderFlag, Name, TreeLevel, IsLeaf, Phone, Fax, Manager, Leader1, 

Leader2, Prop1, Prop2, Prop3, Prop4, Memo, AddTime, AddUser, ModifyTime, ModifyUser)
  VALUES('120000095120000909120000855120000856120000860', '2120999126109013H', '120000095120000909120000855120000856', '0', 859, '汉沽支行其他人员', 5, 'Y', NULL, NULL, 'admin', 'administrator', 'admin', '06', 

'120000860', NULL, NULL, NULL, 

'20100824', 'admin', NULL, NULL)
;
INSERT INTO zdbranch(BranchInnerCode, BranchCode, ParentInnerCode, Type, OrderFlag, Name, TreeLevel, IsLeaf, Phone, Fax, Manager, Leader1, 

Leader2, Prop1, Prop2, Prop3, Prop4, Memo, AddTime, AddUser, ModifyTime, ModifyUser)
  VALUES('120000095120000909120000855120000856120000861', '2120999126109015D', '120000095120000909120000855120000856', '0', 860, '汉沽支行离退休人员', 5, 'Y', NULL, NULL, 'admin', 'administrator', 'admin', '06', 

'120000861', NULL, NULL, NULL, 

'20100824', 'admin', NULL, NULL)
;
INSERT INTO zdbranch(BranchInnerCode, BranchCode, ParentInnerCode, Type, OrderFlag, Name, TreeLevel, IsLeaf, Phone, Fax, Manager, Leader1, 

Leader2, Prop1, Prop2, Prop3, Prop4, Memo, AddTime, AddUser, ModifyTime, ModifyUser)
  VALUES('120000095120000909120000855120000856120000862', '21209991261090098', '120000095120000909120000855120000856', '0', 861, '汉沽支行三角湖储蓄所', 5, 'Y', NULL, NULL, 'admin', 'administrator', 'admin', 

'06', '120000862', NULL, NULL, NULL, 

'20100824', 'admin', NULL, NULL)
;
INSERT INTO zdbranch(BranchInnerCode, BranchCode, ParentInnerCode, Type, OrderFlag, Name, TreeLevel, IsLeaf, Phone, Fax, Manager, Leader1, 

Leader2, Prop1, Prop2, Prop3, Prop4, Memo, AddTime, AddUser, ModifyTime, ModifyUser)
  VALUES('120000095120000909120000855120000856120000863', '2120999126109014F', '120000095120000909120000855120000856', '0', 862, '汉沽支行体育场储蓄所', 5, 'Y', NULL, NULL, 'admin', 'administrator', 'admin', 

'06', '120000863', NULL, NULL, NULL, 

'20100824', 'admin', NULL, NULL)
;
INSERT INTO zdbranch(BranchInnerCode, BranchCode, ParentInnerCode, Type, OrderFlag, Name, TreeLevel, IsLeaf, Phone, Fax, Manager, Leader1, 

Leader2, Prop1, Prop2, Prop3, Prop4, Memo, AddTime, AddUser, ModifyTime, ModifyUser)
  VALUES('120000095120000909120000855120000856120000864', '2120999126109011L', '120000095120000909120000855120000856', '0', 863, '汉沽支行营业部', 5, 'Y', NULL, NULL, 'admin', 'administrator', 'admin', '06', 

'120000864', NULL, NULL, NULL, 

'20100824', 'admin', NULL, NULL)
;
INSERT INTO zdbranch(BranchInnerCode, BranchCode, ParentInnerCode, Type, OrderFlag, Name, TreeLevel, IsLeaf, Phone, Fax, Manager, Leader1, 

Leader2, Prop1, Prop2, Prop3, Prop4, Memo, AddTime, AddUser, ModifyTime, ModifyUser)
  VALUES('120000095120000909120000855120000865', '2120999126112000O', '120000095120000909120000855', '0', 864, ' 市汉沽支行三角湖储蓄所', 4, 'Y', NULL, NULL, 'admin', 'administrator', 'admin', 

'11', '120000865', NULL, NULL, 

NULL, '20100824', 'admin', NULL, NULL)
;
INSERT INTO zdbranch(BranchInnerCode, BranchCode, ParentInnerCode, Type, OrderFlag, Name, TreeLevel, IsLeaf, Phone, Fax, Manager, Leader1, 

Leader2, Prop1, Prop2, Prop3, Prop4, Memo, AddTime, AddUser, ModifyTime, ModifyUser)
  VALUES('120000095120000909120000855120000866', '2120999126113000J', '120000095120000909120000855', '0', 865, '汉沽支行金江分理处', 4, 'Y', NULL, NULL, 'admin', 'administrator', 'admin', 

'06', '120000866', NULL, NULL, NULL, 

'20100824', 'admin', NULL, NULL)
;
INSERT INTO zdbranch(BranchInnerCode, BranchCode, ParentInnerCode, Type, OrderFlag, Name, TreeLevel, IsLeaf, Phone, Fax, Manager, Leader1, 

Leader2, Prop1, Prop2, Prop3, Prop4, Memo, AddTime, AddUser, ModifyTime, ModifyUser)
  VALUES('120000095120000909120000855120000867', '21209991261070005', '120000095120000909120000855', '0', 866, '汉沽支行河西营业所', 4, 'Y', NULL, NULL, 'admin', 'administrator', 'admin', 

'06', '120000867', NULL, NULL, NULL, 

'20100824', 'admin', NULL, NULL)
;
INSERT INTO zdbranch(BranchInnerCode, BranchCode, ParentInnerCode, Type, OrderFlag, Name, TreeLevel, IsLeaf, Phone, Fax, Manager, Leader1, 

Leader2, Prop1, Prop2, Prop3, Prop4, Memo, AddTime, AddUser, ModifyTime, ModifyUser)
  VALUES('120000095120000909120000855120000868', '21209991261100003', '120000095120000909120000855', '0', 867, '汉沽支行西区分理处', 4, 'Y', NULL, NULL, 'admin', 'administrator', 'admin', 

'11', '120000868', NULL, NULL, NULL, 

'20100824', 'admin', NULL, NULL)
;
INSERT INTO zdbranch(BranchInnerCode, BranchCode, ParentInnerCode, Type, OrderFlag, Name, TreeLevel, IsLeaf, Phone, Fax, Manager, Leader1, 

Leader2, Prop1, Prop2, Prop3, Prop4, Memo, AddTime, AddUser, ModifyTime, ModifyUser)
  VALUES('120000095120000909120000855120000869', '2120999126108000V', '120000095120000909120000855', '0', 868, '汉沽支行滨海营业所', 4, 'Y', NULL, NULL, 'admin', 'administrator', 'admin', 

'06', '120000869', NULL, NULL, NULL, 

'20100824', 'admin', NULL, NULL)
;
INSERT INTO zdbranch(BranchInnerCode, BranchCode, ParentInnerCode, Type, OrderFlag, Name, TreeLevel, IsLeaf, Phone, Fax, Manager, Leader1, 

Leader2, Prop1, Prop2, Prop3, Prop4, Memo, AddTime, AddUser, ModifyTime, ModifyUser)
  VALUES('120000095120000909120000855120000870', '2120999126111000T', '120000095120000909120000855', '0', 869, '汉沽支行东风路营业所', 4, 'Y', NULL, NULL, 'admin', 'administrator', 'admin', 

'06', '120000870', NULL, NULL, 

NULL, '20100824', 'admin', NULL, NULL)
;
INSERT INTO zdbranch(BranchInnerCode, BranchCode, ParentInnerCode, Type, OrderFlag, Name, TreeLevel, IsLeaf, Phone, Fax, Manager, Leader1, 

Leader2, Prop1, Prop2, Prop3, Prop4, Memo, AddTime, AddUser, ModifyTime, ModifyUser)
  VALUES('120000095120000871', '2120999125000000O', '120000095', '0', 870, '大港区支行', 2, 'Y', NULL, NULL, 'admin', 'administrator', 'admin', '04', '120000871', 

NULL, NULL, NULL, 

'20100824', 'admin', NULL, NULL)
;
INSERT INTO zdbranch(BranchInnerCode, BranchCode, ParentInnerCode, Type, OrderFlag, Name, TreeLevel, IsLeaf, Phone, Fax, Manager, Leader1, 

Leader2, Prop1, Prop2, Prop3, Prop4, Memo, AddTime, AddUser, ModifyTime, ModifyUser)
  VALUES('120000095120000871120000872', '2120999125001000J', '120000095120000871', '0', 871, '大港支行机关', 3, 'Y', NULL, NULL, 'admin', 'administrator', 'admin', '04', '120000872', 

NULL, NULL, NULL, 

'20100824', 'admin', NULL, NULL)
;
INSERT INTO zdbranch(BranchInnerCode, BranchCode, ParentInnerCode, Type, OrderFlag, Name, TreeLevel, IsLeaf, Phone, Fax, Manager, Leader1, 

Leader2, Prop1, Prop2, Prop3, Prop4, Memo, AddTime, AddUser, ModifyTime, ModifyUser)
  VALUES('120000095120000871120000872120000873', '2120999125001002F', '120000095120000871120000872', '0', 872, '大港支行后勤', 4, 'Y', NULL, NULL, 'admin', 'administrator', 'admin', '04', '120000873', 

NULL, NULL, NULL, 

'20100824', 'admin', NULL, NULL)
;
INSERT INTO zdbranch(BranchInnerCode, BranchCode, ParentInnerCode, Type, OrderFlag, Name, TreeLevel, IsLeaf, Phone, Fax, Manager, Leader1, 

Leader2, Prop1, Prop2, Prop3, Prop4, Memo, AddTime, AddUser, ModifyTime, ModifyUser)
  VALUES('120000095120000871120000872120000874', '2120999125001003D', '120000095120000871120000872', '0', 873, '大港支行内勤', 4, 'Y', NULL, NULL, 'admin', 'administrator', 'admin', '04', '120000874', 

NULL, NULL, NULL, 

'20100824', 'admin', NULL, NULL)
;
INSERT INTO zdbranch(BranchInnerCode, BranchCode, ParentInnerCode, Type, OrderFlag, Name, TreeLevel, IsLeaf, Phone, Fax, Manager, Leader1, 

Leader2, Prop1, Prop2, Prop3, Prop4, Memo, AddTime, AddUser, ModifyTime, ModifyUser)
  VALUES('120000095120000871120000872120000875', '2120999125001004B', '120000095120000871120000872', '0', 874, '大港支行内退', 4, 'Y', NULL, NULL, 'admin', 'administrator', 'admin', '04', '120000875', 

NULL, NULL, NULL, 

'20100824', 'admin', NULL, NULL)
;
INSERT INTO zdbranch(BranchInnerCode, BranchCode, ParentInnerCode, Type, OrderFlag, Name, TreeLevel, IsLeaf, Phone, Fax, Manager, Leader1, 

Leader2, Prop1, Prop2, Prop3, Prop4, Memo, AddTime, AddUser, ModifyTime, ModifyUser)
  VALUES('120000095120000871120000872120000876', '21209991250010059', '120000095120000871120000872', '0', 875, '大港支行外勤', 4, 'Y', NULL, NULL, 'admin', 'administrator', 'admin', '04', '120000876', 

NULL, NULL, NULL, 

'20100824', 'admin', NULL, NULL)
;
INSERT INTO zdbranch(BranchInnerCode, BranchCode, ParentInnerCode, Type, OrderFlag, Name, TreeLevel, IsLeaf, Phone, Fax, Manager, Leader1, 

Leader2, Prop1, Prop2, Prop3, Prop4, Memo, AddTime, AddUser, ModifyTime, ModifyUser)
  VALUES('120000095120000871120000872120000877', '21209991250010067', '120000095120000871120000872', '0', 876, '大港区支行综合管理部', 4, 'Y', NULL, NULL, 'admin', 'administrator', 'admin', 

'04', '120000877', NULL, NULL, 

NULL, '20100824', 'admin', NULL, NULL)
;
INSERT INTO zdbranch(BranchInnerCode, BranchCode, ParentInnerCode, Type, OrderFlag, Name, TreeLevel, IsLeaf, Phone, Fax, Manager, Leader1, 

Leader2, Prop1, Prop2, Prop3, Prop4, Memo, AddTime, AddUser, ModifyTime, ModifyUser)
  VALUES('120000095120000871120000872120000878', '21209991250010075', '120000095120000871120000872', '0', 877, '大港支行离退休', 4, 'Y', NULL, NULL, 'admin', 'administrator', 'admin', '04', 

'120000878', NULL, NULL, NULL, 

'20100824', 'admin', NULL, NULL)
;
INSERT INTO zdbranch(BranchInnerCode, BranchCode, ParentInnerCode, Type, OrderFlag, Name, TreeLevel, IsLeaf, Phone, Fax, Manager, Leader1, 

Leader2, Prop1, Prop2, Prop3, Prop4, Memo, AddTime, AddUser, ModifyTime, ModifyUser)
  VALUES('120000095120000871120000872120000879', '21209991250010083', '120000095120000871120000872', '0', 878, '大港支行拓展部', 4, 'Y', NULL, NULL, 'admin', 'administrator', 'admin', '04', 

'120000879', NULL, NULL, NULL, 

'20100824', 'admin', NULL, NULL)
;
INSERT INTO zdbranch(BranchInnerCode, BranchCode, ParentInnerCode, Type, OrderFlag, Name, TreeLevel, IsLeaf, Phone, Fax, Manager, Leader1, 

Leader2, Prop1, Prop2, Prop3, Prop4, Memo, AddTime, AddUser, ModifyTime, ModifyUser)
  VALUES('120000095120000871120000872120000880', '21209991250010091', '120000095120000871120000872', '0', 879, '大港支行行长室', 4, 'Y', NULL, NULL, 'admin', 'administrator', 'admin', '04', 

'120000880', NULL, NULL, NULL, 

'20100824', 'admin', NULL, NULL)
;
INSERT INTO zdbranch(BranchInnerCode, BranchCode, ParentInnerCode, Type, OrderFlag, Name, TreeLevel, IsLeaf, Phone, Fax, Manager, Leader1, 

Leader2, Prop1, Prop2, Prop3, Prop4, Memo, AddTime, AddUser, ModifyTime, ModifyUser)
  VALUES('120000095120000871120000872120000881', '2120999125001010G', '120000095120000871120000872', '0', 880, '大港支行个人金融部', 4, 'Y', NULL, NULL, 'admin', 'administrator', 'admin', '04', 

'120000881', NULL, NULL, NULL, 

'20100824', 'admin', NULL, NULL)
;
INSERT INTO zdbranch(BranchInnerCode, BranchCode, ParentInnerCode, Type, OrderFlag, Name, TreeLevel, IsLeaf, Phone, Fax, Manager, Leader1, 

Leader2, Prop1, Prop2, Prop3, Prop4, Memo, AddTime, AddUser, ModifyTime, ModifyUser)
  VALUES('120000095120000871120000872120000882', '2120999125001011E', '120000095120000871120000872', '0', 881, '大港支行财会运营部', 4, 'Y', NULL, NULL, 'admin', 'administrator', 'admin', '04', 

'120000882', NULL, NULL, NULL, 

'20100824', 'admin', NULL, NULL)
;
INSERT INTO zdbranch(BranchInnerCode, BranchCode, ParentInnerCode, Type, OrderFlag, Name, TreeLevel, IsLeaf, Phone, Fax, Manager, Leader1, 

Leader2, Prop1, Prop2, Prop3, Prop4, Memo, AddTime, AddUser, ModifyTime, ModifyUser)
  VALUES('120000095120000871120000872120000883', '2120999125001012C', '120000095120000871120000872', '0', 882, '大港支行风险资产部', 4, 'Y', NULL, NULL, 'admin', 'administrator', 'admin', '04', 

'120000883', NULL, NULL, NULL, 

'20100824', 'admin', NULL, NULL)
;
INSERT INTO zdbranch(BranchInnerCode, BranchCode, ParentInnerCode, Type, OrderFlag, Name, TreeLevel, IsLeaf, Phone, Fax, Manager, Leader1, 

Leader2, Prop1, Prop2, Prop3, Prop4, Memo, AddTime, AddUser, ModifyTime, ModifyUser)
  VALUES('120000095120000871120000872120000884', '2120999125001013A', '120000095120000871120000872', '0', 883, '大港支行公司业务部', 4, 'Y', NULL, NULL, 'admin', 'administrator', 'admin', '04', 

'120000884', NULL, NULL, NULL, 

'20100824', 'admin', NULL, NULL)
;
INSERT INTO zdbranch(BranchInnerCode, BranchCode, ParentInnerCode, Type, OrderFlag, Name, TreeLevel, IsLeaf, Phone, Fax, Manager, Leader1, 

Leader2, Prop1, Prop2, Prop3, Prop4, Memo, AddTime, AddUser, ModifyTime, ModifyUser)
  VALUES('120000095120000871120000872120000885', '21209991250010148', '120000095120000871120000872', '0', 884, '大港支行监察保卫/内控合规部', 4, 'Y', NULL, NULL, 'admin', 'administrator', 

'admin', '04', '120000885', NULL, NULL, 

NULL, '20100824', 'admin', NULL, NULL)
;
INSERT INTO zdbranch(BranchInnerCode, BranchCode, ParentInnerCode, Type, OrderFlag, Name, TreeLevel, IsLeaf, Phone, Fax, Manager, Leader1, 

Leader2, Prop1, Prop2, Prop3, Prop4, Memo, AddTime, AddUser, ModifyTime, ModifyUser)
  VALUES('120000095120000871120000872120000886', '21209991250010156', '120000095120000871120000872', '0', 885, '大港支行信贷管理部', 4, 'Y', NULL, NULL, 'admin', 'administrator', 'admin', '04', 

'120000886', NULL, NULL, NULL, 

'20100824', 'admin', NULL, NULL)
;
INSERT INTO zdbranch(BranchInnerCode, BranchCode, ParentInnerCode, Type, OrderFlag, Name, TreeLevel, IsLeaf, Phone, Fax, Manager, Leader1, 

Leader2, Prop1, Prop2, Prop3, Prop4, Memo, AddTime, AddUser, ModifyTime, ModifyUser)
  VALUES('120000095120000871120000872120000887', '21209991250010164', '120000095120000871120000872', '0', 886, '大港支行业务发展部', 4, 'Y', NULL, NULL, 'admin', 'administrator', 'admin', '04', 

'120000887', NULL, NULL, NULL, 

'20100824', 'admin', NULL, NULL)
;
INSERT INTO zdbranch(BranchInnerCode, BranchCode, ParentInnerCode, Type, OrderFlag, Name, TreeLevel, IsLeaf, Phone, Fax, Manager, Leader1, 

Leader2, Prop1, Prop2, Prop3, Prop4, Memo, AddTime, AddUser, ModifyTime, ModifyUser)
  VALUES('120000095120000871120000888', '2120999125101000C', '120000095120000871', '0', 887, '大港支行营业部', 3, 'Y', NULL, NULL, 'admin', 'administrator', 'admin', '06', '120000888', 

NULL, NULL, NULL, '20100824', 'admin', NULL, NULL);
INSERT INTO zdbranch(BranchInnerCode, BranchCode, ParentInnerCode, Type, OrderFlag, Name, TreeLevel, IsLeaf, Phone, Fax, Manager, Leader1, 

Leader2, Prop1, Prop2, Prop3, Prop4, Memo, AddTime, AddUser, ModifyTime, ModifyUser)
  VALUES('120000095120000871120000889', '21209991251110006', '120000095120000871', '0', 888, '胜利支行', 3, 'Y', NULL, NULL, 'admin', 'administrator', 'admin', '06', '120000889', 

NULL, NULL, NULL, '20100824', 'admin', NULL, NULL);
INSERT INTO zdbranch(BranchInnerCode, BranchCode, ParentInnerCode, Type, OrderFlag, Name, TreeLevel, IsLeaf, Phone, Fax, Manager, Leader1, 

Leader2, Prop1, Prop2, Prop3, Prop4, Memo, AddTime, AddUser, ModifyTime, ModifyUser)
  VALUES('120000095120000871120000890', '2120999125122000Q', '120000095120000871', '0', 889, '大港支行六合里储蓄所', 3, 'Y', NULL, NULL, 'admin', 'administrator', 'admin', 

'10', '120000890', NULL, NULL, 

NULL, '20100824', 'admin', NULL, NULL)
;
INSERT INTO zdbranch(BranchInnerCode, BranchCode, ParentInnerCode, Type, OrderFlag, Name, TreeLevel, IsLeaf, Phone, Fax, Manager, Leader1, 

Leader2, Prop1, Prop2, Prop3, Prop4, Memo, AddTime, AddUser, ModifyTime, ModifyUser)
  VALUES('120000095120000871120000891', '21209991251120001', '120000095120000871', '0', 890, '世纪大道支行', 3, 'Y', NULL, NULL, 'admin', 'administrator', 'admin', '06', 

'120000891', NULL, NULL, NULL, 

'20100824', 'admin', NULL, NULL)
;
INSERT INTO zdbranch(BranchInnerCode, BranchCode, ParentInnerCode, Type, OrderFlag, Name, TreeLevel, IsLeaf, Phone, Fax, Manager, Leader1, 

Leader2, Prop1, Prop2, Prop3, Prop4, Memo, AddTime, AddUser, ModifyTime, ModifyUser)
  VALUES('120000095120000871120000892', '2120999125123000L', '120000095120000871', '0', 891, '大港区旭日道分理处', 3, 'Y', NULL, NULL, 'admin', 'administrator', 'admin', '06', 

'120000892', NULL, NULL, NULL, 

'20100824', 'admin', NULL, NULL)
;
INSERT INTO zdbranch(BranchInnerCode, BranchCode, ParentInnerCode, Type, OrderFlag, Name, TreeLevel, IsLeaf, Phone, Fax, Manager, Leader1, 

Leader2, Prop1, Prop2, Prop3, Prop4, Memo, AddTime, AddUser, ModifyTime, ModifyUser)
  VALUES('120000095120000871120000893', '21209991251020007', '120000095120000871', '0', 892, '小王庄支行', 3, 'Y', NULL, NULL, 'admin', 'administrator', 'admin', '06', '120000893', 

NULL, NULL, NULL, 

'20100824', 'admin', NULL, NULL)
;
INSERT INTO zdbranch(BranchInnerCode, BranchCode, ParentInnerCode, Type, OrderFlag, Name, TreeLevel, IsLeaf, Phone, Fax, Manager, Leader1, 

Leader2, Prop1, Prop2, Prop3, Prop4, Memo, AddTime, AddUser, ModifyTime, ModifyUser)
  VALUES('120000095120000871120000894', '21209991251030002', '120000095120000871', '0', 893, '港中支行', 3, 'Y', NULL, NULL, 'admin', 'administrator', 'admin', '06', '120000894', 

NULL, NULL, NULL, '20100824', 'admin', NULL, NULL);
INSERT INTO zdbranch(BranchInnerCode, BranchCode, ParentInnerCode, Type, OrderFlag, Name, TreeLevel, IsLeaf, Phone, Fax, Manager, Leader1, 

Leader2, Prop1, Prop2, Prop3, Prop4, Memo, AddTime, AddUser, ModifyTime, ModifyUser)
  VALUES('120000095120000871120000895', '2120999125115000H', '120000095120000871', '0', 894, '大港支行港中储蓄所', 3, 'Y', NULL, NULL, 'admin', 'administrator', 'admin', 

'10', '120000895', NULL, NULL, NULL, 

'20100824', 'admin', NULL, NULL)
;
INSERT INTO zdbranch(BranchInnerCode, BranchCode, ParentInnerCode, Type, OrderFlag, Name, TreeLevel, IsLeaf, Phone, Fax, Manager, Leader1, 

Leader2, Prop1, Prop2, Prop3, Prop4, Memo, AddTime, AddUser, ModifyTime, ModifyUser)
  VALUES('120000095120000871120000896', '2120999125116000C', '120000095120000871', '0', 895, '大港支行新盛储蓄所', 3, 'Y', NULL, NULL, 'admin', 'administrator', 'admin', 

'10', '120000896', NULL, NULL, NULL, 

'20100824', 'admin', NULL, NULL)
;
INSERT INTO zdbranch(BranchInnerCode, BranchCode, ParentInnerCode, Type, OrderFlag, Name, TreeLevel, IsLeaf, Phone, Fax, Manager, Leader1, 

Leader2, Prop1, Prop2, Prop3, Prop4, Memo, AddTime, AddUser, ModifyTime, ModifyUser)
  VALUES('120000095120000871120000897', '2120999125114000M', '120000095120000871', '0', 896, '大港区福苑里储蓄所', 3, 'Y', NULL, NULL, 'admin', 'administrator', 'admin', 

'10', '120000897', NULL, NULL, NULL, 

'20100824', 'admin', NULL, NULL)
;
INSERT INTO zdbranch(BranchInnerCode, BranchCode, ParentInnerCode, Type, OrderFlag, Name, TreeLevel, IsLeaf, Phone, Fax, Manager, Leader1, 

Leader2, Prop1, Prop2, Prop3, Prop4, Memo, AddTime, AddUser, ModifyTime, ModifyUser)
  VALUES('120000095120000871120000898', '2120999125104000S', '120000095120000871', '0', 897, '油田支行', 3, 'Y', NULL, NULL, 'admin', 'administrator', 'admin', '06', '120000898', 

NULL, NULL, NULL, '20100824', 'admin', NULL, NULL);
INSERT INTO zdbranch(BranchInnerCode, BranchCode, ParentInnerCode, Type, OrderFlag, Name, TreeLevel, IsLeaf, Phone, Fax, Manager, Leader1, 

Leader2, Prop1, Prop2, Prop3, Prop4, Memo, AddTime, AddUser, ModifyTime, ModifyUser)
  VALUES('120000095120000871120000899', '21209991251180002', '120000095120000871', '0', 898, '大港支行滨城储蓄所', 3, 'Y', NULL, NULL, 'admin', 'administrator', 'admin', 

'10', '120000899', NULL, NULL, NULL, 

'20100824', 'admin', NULL, NULL)
;
INSERT INTO zdbranch(BranchInnerCode, BranchCode, ParentInnerCode, Type, OrderFlag, Name, TreeLevel, IsLeaf, Phone, Fax, Manager, Leader1, 

Leader2, Prop1, Prop2, Prop3, Prop4, Memo, AddTime, AddUser, ModifyTime, ModifyUser)
  VALUES('120000095120000871120000900', '2120999125119000S', '120000095120000871', '0', 899, '大港支行钻井储蓄所', 3, 'Y', NULL, NULL, 'admin', 'administrator', 'admin', 

'10', '120000900', NULL, NULL, NULL, 

'20100824', 'admin', NULL, NULL)
;
INSERT INTO zdbranch(BranchInnerCode, BranchCode, ParentInnerCode, Type, OrderFlag, Name, TreeLevel, IsLeaf, Phone, Fax, Manager, Leader1, 

Leader2, Prop1, Prop2, Prop3, Prop4, Memo, AddTime, AddUser, ModifyTime, ModifyUser)
  VALUES('120000095120000871120000901', '21209991251090003', '120000095120000871', '0', 900, '大港区中心街分理处', 3, 'Y', NULL, NULL, 'admin', 'administrator', 'admin', 

'06', '120000901', NULL, NULL, NULL, 

'20100824', 'admin', NULL, NULL)
;
INSERT INTO zdbranch(BranchInnerCode, BranchCode, ParentInnerCode, Type, OrderFlag, Name, TreeLevel, IsLeaf, Phone, Fax, Manager, Leader1, 

Leader2, Prop1, Prop2, Prop3, Prop4, Memo, AddTime, AddUser, ModifyTime, ModifyUser)
  VALUES('120000095120000871120000902', '21209991251200005', '120000095120000871', '0', 901, '大港支行幸福里储蓄所', 3, 'Y', NULL, NULL, 'admin', 'administrator', 'admin', 

'10', '120000902', NULL, NULL, 

NULL, '20100824', 'admin', NULL, NULL)
;
INSERT INTO zdbranch(BranchInnerCode, BranchCode, ParentInnerCode, Type, OrderFlag, Name, TreeLevel, IsLeaf, Phone, Fax, Manager, Leader1, 

Leader2, Prop1, Prop2, Prop3, Prop4, Memo, AddTime, AddUser, ModifyTime, ModifyUser)
  VALUES('120000095120000871120000903', '21209991251210000', '120000095120000871', '0', 902, '大港区胜利街储蓄所', 3, 'Y', NULL, NULL, 'admin', 'administrator', 'admin', 

'10', '120000903', NULL, NULL, NULL, 

'20100824', 'admin', NULL, NULL)
;
INSERT INTO zdbranch(BranchInnerCode, BranchCode, ParentInnerCode, Type, OrderFlag, Name, TreeLevel, IsLeaf, Phone, Fax, Manager, Leader1, 

Leader2, Prop1, Prop2, Prop3, Prop4, Memo, AddTime, AddUser, ModifyTime, ModifyUser)
  VALUES('120000095120000871120000904', '21209991251170007', '120000095120000871', '0', 903, '大港区育秀街储蓄所', 3, 'Y', NULL, NULL, 'admin', 'administrator', 'admin', 

'10', '120000904', NULL, NULL, NULL, 

'20100824', 'admin', NULL, NULL)
;
INSERT INTO zdbranch(BranchInnerCode, BranchCode, ParentInnerCode, Type, OrderFlag, Name, TreeLevel, IsLeaf, Phone, Fax, Manager, Leader1, 

Leader2, Prop1, Prop2, Prop3, Prop4, Memo, AddTime, AddUser, ModifyTime, ModifyUser)
  VALUES('120000095120000871120000905', '2120999125105000N', '120000095120000871', '0', 904, '港城支行', 3, 'Y', NULL, NULL, 'admin', 'administrator', 'admin', '06', '120000905', 

NULL, NULL, NULL, '20100824', 'admin', NULL, NULL);
INSERT INTO zdbranch(BranchInnerCode, BranchCode, ParentInnerCode, Type, OrderFlag, Name, TreeLevel, IsLeaf, Phone, Fax, Manager, Leader1, 

Leader2, Prop1, Prop2, Prop3, Prop4, Memo, AddTime, AddUser, ModifyTime, ModifyUser)
  VALUES('120000095120000871120000906', '2120999125106000I', '120000095120000871', '0', 905, '泰平支行', 3, 'Y', NULL, NULL, 'admin', 'administrator', 'admin', '06', '120000906', 

NULL, NULL, NULL, '20100824', 'admin', NULL, NULL);
INSERT INTO zdbranch(BranchInnerCode, BranchCode, ParentInnerCode, Type, OrderFlag, Name, TreeLevel, IsLeaf, Phone, Fax, Manager, Leader1, 

Leader2, Prop1, Prop2, Prop3, Prop4, Memo, AddTime, AddUser, ModifyTime, ModifyUser)
  VALUES('120000095120000871120000907', '2120999125107000D', '120000095120000871', '0', 906, '中塘支行', 3, 'Y', NULL, NULL, 'admin', 'administrator', 'admin', '06', '120000907', 

NULL, NULL, NULL, '20100824', 'admin', NULL, NULL);
INSERT INTO zdbranch(BranchInnerCode, BranchCode, ParentInnerCode, Type, OrderFlag, Name, TreeLevel, IsLeaf, Phone, Fax, Manager, Leader1, 

Leader2, Prop1, Prop2, Prop3, Prop4, Memo, AddTime, AddUser, ModifyTime, ModifyUser)
  VALUES('120000095120000871120000908', '21209991251080008', '120000095120000871', '0', 907, '大港支行红旗路分理处', 3, 'Y', NULL, NULL, 'admin', 'administrator', 'admin', 

'06', '120000908', NULL, NULL, 

NULL, '20100824', 'admin', NULL, NULL)
;
INSERT INTO zdbranch(BranchInnerCode, BranchCode, ParentInnerCode, Type, OrderFlag, Name, TreeLevel, IsLeaf, Phone, Fax, Manager, Leader1, 

Leader2, Prop1, Prop2, Prop3, Prop4, Memo, AddTime, AddUser, ModifyTime, ModifyUser)
  VALUES('120000095120000909', '2120999126000000G', '120000095', '0', 908, '经济技术开发区分行', 2, 'Y', NULL, NULL, 'admin', 'administrator', 'admin', 

'04', '120000909', NULL, NULL, NULL, 

'20100824', 'admin', NULL, NULL)
;
INSERT INTO zdbranch(BranchInnerCode, BranchCode, ParentInnerCode, Type, OrderFlag, Name, TreeLevel, IsLeaf, Phone, Fax, Manager, Leader1, 

Leader2, Prop1, Prop2, Prop3, Prop4, Memo, AddTime, AddUser, ModifyTime, ModifyUser)
  VALUES('120000095120000909120000910', '2120999126103000P', '120000095120000909', '0', 909, '第五大街支行', 3, 'Y', NULL, NULL, 'admin', 'administrator', 'admin', '06', 

'120000910', NULL, NULL, NULL, 

'20100824', 'admin', NULL, NULL)
;
INSERT INTO zdbranch(BranchInnerCode, BranchCode, ParentInnerCode, Type, OrderFlag, Name, TreeLevel, IsLeaf, Phone, Fax, Manager, Leader1, 

Leader2, Prop1, Prop2, Prop3, Prop4, Memo, AddTime, AddUser, ModifyTime, ModifyUser)
  VALUES('120000095120000909120000911', '2120999126001000B', '120000095120000909', '0', 910, '经济技术开发区分行机关', 3, 'Y', NULL, NULL, 'admin', 'administrator', 'admin', 

'04', '120000911', NULL, NULL, 

NULL, '20100824', 'admin', NULL, NULL)
;
INSERT INTO zdbranch(BranchInnerCode, BranchCode, ParentInnerCode, Type, OrderFlag, Name, TreeLevel, IsLeaf, Phone, Fax, Manager, Leader1, 

Leader2, Prop1, Prop2, Prop3, Prop4, Memo, AddTime, AddUser, ModifyTime, ModifyUser)
  VALUES('120000095120000909120000911120000912', '21209991260010051', '120000095120000909120000911', '0', 911, '经济技术开发区分行其他', 4, 'Y', NULL, NULL, 'admin', 'administrator', 'admin', 

'04', '120000912', NULL, NULL, 

NULL, '20100824', 'admin', NULL, NULL)
;
INSERT INTO zdbranch(BranchInnerCode, BranchCode, ParentInnerCode, Type, OrderFlag, Name, TreeLevel, IsLeaf, Phone, Fax, Manager, Leader1, 

Leader2, Prop1, Prop2, Prop3, Prop4, Memo, AddTime, AddUser, ModifyTime, ModifyUser)
  VALUES('120000095120000909120000911120000913', '2120999126001006U', '120000095120000909120000911', '0', 912, '经济技术开发区分行综合管理部', 4, 'Y', NULL, NULL, 'admin', 'administrator', 

'admin', '04', '120000913', NULL, 

NULL, NULL, '20100824', 'admin', NULL, NULL)
;
INSERT INTO zdbranch(BranchInnerCode, BranchCode, ParentInnerCode, Type, OrderFlag, Name, TreeLevel, IsLeaf, Phone, Fax, Manager, Leader1, 

Leader2, Prop1, Prop2, Prop3, Prop4, Memo, AddTime, AddUser, ModifyTime, ModifyUser)
  VALUES('120000095120000909120000911120000914', '21209991260010043', '120000095120000909120000911', '0', 913, '经济技术开发区分行行长室', 4, 'Y', NULL, NULL, 'admin', 'administrator', 'admin', 

'04', '120000914', NULL, NULL, 

NULL, '20100824', 'admin', NULL, NULL)
;
INSERT INTO zdbranch(BranchInnerCode, BranchCode, ParentInnerCode, Type, OrderFlag, Name, TreeLevel, IsLeaf, Phone, Fax, Manager, Leader1, 

Leader2, Prop1, Prop2, Prop3, Prop4, Memo, AddTime, AddUser, ModifyTime, ModifyUser)
  VALUES('120000095120000909120000911120000915', '2120999126001007S', '120000095120000909120000911', '0', 914, '经济技术开发区分行公司业务部', 4, 'Y', NULL, NULL, 'admin', 'administrator', 

'admin', '04', '120000915', NULL, 

NULL, NULL, '20100824', 'admin', NULL, NULL)
;
INSERT INTO zdbranch(BranchInnerCode, BranchCode, ParentInnerCode, Type, OrderFlag, Name, TreeLevel, IsLeaf, Phone, Fax, Manager, Leader1, 

Leader2, Prop1, Prop2, Prop3, Prop4, Memo, AddTime, AddUser, ModifyTime, ModifyUser)
  VALUES('120000095120000909120000911120000916', '2120999126001008Q', '120000095120000909120000911', '0', 915, '经济技术开发区分行财会运营部', 4, 'Y', NULL, NULL, 'admin', 'administrator', 

'admin', '04', '120000916', NULL, 

NULL, NULL, '20100824', 'admin', NULL, NULL)
;
INSERT INTO zdbranch(BranchInnerCode, BranchCode, ParentInnerCode, Type, OrderFlag, Name, TreeLevel, IsLeaf, Phone, Fax, Manager, Leader1, 

Leader2, Prop1, Prop2, Prop3, Prop4, Memo, AddTime, AddUser, ModifyTime, ModifyUser)
  VALUES('120000095120000909120000911120000917', '2120999126001009O', '120000095120000909120000911', '0', 916, '经济技术开发区分行监察保卫部', 4, 'Y', NULL, NULL, 'admin', 'administrator', 

'admin', '04', '120000917', NULL, 

NULL, NULL, '20100824', 'admin', NULL, NULL)
;
INSERT INTO zdbranch(BranchInnerCode, BranchCode, ParentInnerCode, Type, OrderFlag, Name, TreeLevel, IsLeaf, Phone, Fax, Manager, Leader1, 

Leader2, Prop1, Prop2, Prop3, Prop4, Memo, AddTime, AddUser, ModifyTime, ModifyUser)
  VALUES('120000095120000909120000911120000918', '21209991260010108', '120000095120000909120000911', '0', 917, '经济技术开发区分行离退休人员', 4, 'Y', NULL, NULL, 'admin', 'administrator', 

'admin', '04', '120000918', NULL, 

NULL, NULL, '20100824', 'admin', NULL, NULL)
;
INSERT INTO zdbranch(BranchInnerCode, BranchCode, ParentInnerCode, Type, OrderFlag, Name, TreeLevel, IsLeaf, Phone, Fax, Manager, Leader1, 

Leader2, Prop1, Prop2, Prop3, Prop4, Memo, AddTime, AddUser, ModifyTime, ModifyUser)
  VALUES('120000095120000909120000911120000919', '21209991260010027', '120000095120000909120000911', '0', 918, '经济技术开发区分行信贷管理部', 4, 'Y', NULL, NULL, 'admin', 'administrator', 

'admin', '04', '120000919', NULL, 

NULL, NULL, '20100824', 'admin', NULL, NULL)
;
INSERT INTO zdbranch(BranchInnerCode, BranchCode, ParentInnerCode, Type, OrderFlag, Name, TreeLevel, IsLeaf, Phone, Fax, Manager, Leader1, 

Leader2, Prop1, Prop2, Prop3, Prop4, Memo, AddTime, AddUser, ModifyTime, ModifyUser)
  VALUES('120000095120000909120000911120000920', '21209991260010035', '120000095120000909120000911', '0', 919, '经济技术开发区分行个人金融部', 4, 'Y', NULL, NULL, 'admin', 'administrator', 

'admin', '04', '120000920', NULL, 

NULL, NULL, '20100824', 'admin', NULL, NULL)
;
INSERT INTO zdbranch(BranchInnerCode, BranchCode, ParentInnerCode, Type, OrderFlag, Name, TreeLevel, IsLeaf, Phone, Fax, Manager, Leader1, 

Leader2, Prop1, Prop2, Prop3, Prop4, Memo, AddTime, AddUser, ModifyTime, ModifyUser)
  VALUES('120000095120000909120000911120000921', '21209991260010019', '120000095120000909120000911', '0', 920, '经济技术开发区分行营业部', 4, 'Y', NULL, NULL, 'admin', 'administrator', 'admin', 

'04', '120000921', NULL, NULL, 

NULL, '20100824', 'admin', NULL, NULL)
;
INSERT INTO zdbranch(BranchInnerCode, BranchCode, ParentInnerCode, Type, OrderFlag, Name, TreeLevel, IsLeaf, Phone, Fax, Manager, Leader1, 

Leader2, Prop1, Prop2, Prop3, Prop4, Memo, AddTime, AddUser, ModifyTime, ModifyUser)
  VALUES('120000095120000909120000922', '2120999126101101T', '120000095120000909', '0', 921, '分行开发分行宁波里储蓄所', 3, 'Y', NULL, NULL, 'admin', 'administrator', 'admin', 

'10', '120000922', NULL, NULL, 

NULL, '20100824', 'admin', NULL, NULL)
;
INSERT INTO zdbranch(BranchInnerCode, BranchCode, ParentInnerCode, Type, OrderFlag, Name, TreeLevel, IsLeaf, Phone, Fax, Manager, Leader1, 

Leader2, Prop1, Prop2, Prop3, Prop4, Memo, AddTime, AddUser, ModifyTime, ModifyUser)
  VALUES('120000095120000909120000923', '2120999126102000U', '120000095120000909', '0', 922, '第三大街支行', 3, 'Y', NULL, NULL, 'admin', 'administrator', 'admin', '06', 

'120000923', NULL, NULL, NULL, 

'20100824', 'admin', NULL, NULL)
;
INSERT INTO zdbranch(BranchInnerCode, BranchCode, ParentInnerCode, Type, OrderFlag, Name, TreeLevel, IsLeaf, Phone, Fax, Manager, Leader1, 

Leader2, Prop1, Prop2, Prop3, Prop4, Memo, AddTime, AddUser, ModifyTime, ModifyUser)
  VALUES('120000095120000909120000924', '2120999126104000K', '120000095120000909', '0', 923, '出口加工区支行', 3, 'Y', NULL, NULL, 'admin', 'administrator', 'admin', '06', 

'120000924', NULL, NULL, NULL, 

'20100824', 'admin', NULL, NULL)
;
INSERT INTO zdbranch(BranchInnerCode, BranchCode, ParentInnerCode, Type, OrderFlag, Name, TreeLevel, IsLeaf, Phone, Fax, Manager, Leader1, 

Leader2, Prop1, Prop2, Prop3, Prop4, Memo, AddTime, AddUser, ModifyTime, ModifyUser)
  VALUES('120000095120000925', '21209991270000008', '120000095', '0', 924, '港保税区分行', 2, 'Y', NULL, NULL, 'admin', 'administrator', 'admin', '04', '120000925', 

NULL, NULL, NULL, 

'20100824', 'admin', NULL, NULL)
;
INSERT INTO zdbranch(BranchInnerCode, BranchCode, ParentInnerCode, Type, OrderFlag, Name, TreeLevel, IsLeaf, Phone, Fax, Manager, Leader1, 

Leader2, Prop1, Prop2, Prop3, Prop4, Memo, AddTime, AddUser, ModifyTime, ModifyUser)
  VALUES('120000095120000925120000926', '21209991270010003', '120000095120000925', '0', 925, '港保税区分行机关', 3, 'Y', NULL, NULL, 'admin', 'administrator', 'admin', '04', 

'120000926', NULL, NULL, NULL, 

'20100824', 'admin', NULL, NULL)
;
INSERT INTO zdbranch(BranchInnerCode, BranchCode, ParentInnerCode, Type, OrderFlag, Name, TreeLevel, IsLeaf, Phone, Fax, Manager, Leader1, 

Leader2, Prop1, Prop2, Prop3, Prop4, Memo, AddTime, AddUser, ModifyTime, ModifyUser)
  VALUES('120000095120000925120000926120000927', '2120999127001002U', '120000095120000925120000926', '0', 926, '港保税区分行其他', 4, 'Y', NULL, NULL, 'admin', 'administrator', 'admin', '04', 

'120000927', NULL, NULL, NULL, 

'20100824', 'admin', NULL, NULL)
;
INSERT INTO zdbranch(BranchInnerCode, BranchCode, ParentInnerCode, Type, OrderFlag, Name, TreeLevel, IsLeaf, Phone, Fax, Manager, Leader1, 

Leader2, Prop1, Prop2, Prop3, Prop4, Memo, AddTime, AddUser, ModifyTime, ModifyUser)
  VALUES('120000095120000925120000926120000928', '2120999127001003S', '120000095120000925120000926', '0', 927, '港保税区分行综合管理部', 4, 'Y', NULL, NULL, 'admin', 'administrator', 'admin', 

'04', '120000928', NULL, NULL, 

NULL, '20100824', 'admin', NULL, NULL)
;
INSERT INTO zdbranch(BranchInnerCode, BranchCode, ParentInnerCode, Type, OrderFlag, Name, TreeLevel, IsLeaf, Phone, Fax, Manager, Leader1, 

Leader2, Prop1, Prop2, Prop3, Prop4, Memo, AddTime, AddUser, ModifyTime, ModifyUser)
  VALUES('120000095120000925120000926120000929', '2120999127001004Q', '120000095120000925120000926', '0', 928, '港保税区分行行长室', 4, 'Y', NULL, NULL, 'admin', 'administrator', 'admin', '04', 

'120000929', NULL, NULL, NULL, 

'20100824', 'admin', NULL, NULL)
;
INSERT INTO zdbranch(BranchInnerCode, BranchCode, ParentInnerCode, Type, OrderFlag, Name, TreeLevel, IsLeaf, Phone, Fax, Manager, Leader1, 

Leader2, Prop1, Prop2, Prop3, Prop4, Memo, AddTime, AddUser, ModifyTime, ModifyUser)
  VALUES('120000095120000925120000926120000930', '2120999127001005O', '120000095120000925120000926', '0', 929, '港保税区分行公司业务部', 4, 'Y', NULL, NULL, 'admin', 'administrator', 'admin', 

'04', '120000930', NULL, NULL, 

NULL, '20100824', 'admin', NULL, NULL)
;
INSERT INTO zdbranch(BranchInnerCode, BranchCode, ParentInnerCode, Type, OrderFlag, Name, TreeLevel, IsLeaf, Phone, Fax, Manager, Leader1, 

Leader2, Prop1, Prop2, Prop3, Prop4, Memo, AddTime, AddUser, ModifyTime, ModifyUser)
  VALUES('120000095120000925120000926120000931', '2120999127001006M', '120000095120000925120000926', '0', 930, '港保税区分行财会运营部', 4, 'Y', NULL, NULL, 'admin', 'administrator', 'admin', 

'04', '120000931', NULL, NULL, 

NULL, '20100824', 'admin', NULL, NULL)
;
INSERT INTO zdbranch(BranchInnerCode, BranchCode, ParentInnerCode, Type, OrderFlag, Name, TreeLevel, IsLeaf, Phone, Fax, Manager, Leader1, 

Leader2, Prop1, Prop2, Prop3, Prop4, Memo, AddTime, AddUser, ModifyTime, ModifyUser)
  VALUES('120000095120000925120000926120000932', '2120999127001007K', '120000095120000925120000926', '0', 931, '港保税区分行监察保卫/内控合规部', 4, 'Y', NULL, NULL, 'admin', 'administrator', 

'admin', '04', '120000932', NULL, 

NULL, NULL, '20100824', 'admin', NULL, NULL)
;
INSERT INTO zdbranch(BranchInnerCode, BranchCode, ParentInnerCode, Type, OrderFlag, Name, TreeLevel, IsLeaf, Phone, Fax, Manager, Leader1, 

Leader2, Prop1, Prop2, Prop3, Prop4, Memo, AddTime, AddUser, ModifyTime, ModifyUser)
  VALUES('120000095120000925120000926120000933', '2120999127001008I', '120000095120000925120000926', '0', 932, '港保税区分行信贷管理部', 4, 'Y', NULL, NULL, 'admin', 'administrator', 'admin', 

'04', '120000933', NULL, NULL, 

NULL, '20100824', 'admin', NULL, NULL)
;
INSERT INTO zdbranch(BranchInnerCode, BranchCode, ParentInnerCode, Type, OrderFlag, Name, TreeLevel, IsLeaf, Phone, Fax, Manager, Leader1, 

Leader2, Prop1, Prop2, Prop3, Prop4, Memo, AddTime, AddUser, ModifyTime, ModifyUser)
  VALUES('120000095120000925120000926120000934', '2120999127001009G', '120000095120000925120000926', '0', 933, '港保税区分行个人金融部', 4, 'Y', NULL, NULL, 'admin', 'administrator', 'admin', 

'04', '120000934', NULL, NULL, 

NULL, '20100824', 'admin', NULL, NULL)
;
INSERT INTO zdbranch(BranchInnerCode, BranchCode, ParentInnerCode, Type, OrderFlag, Name, TreeLevel, IsLeaf, Phone, Fax, Manager, Leader1, 

Leader2, Prop1, Prop2, Prop3, Prop4, Memo, AddTime, AddUser, ModifyTime, ModifyUser)
  VALUES('120000095120000925120000935', '2120999127102000M', '120000095120000925', '0', 934, '滨海支行', 3, 'Y', NULL, NULL, 'admin', 'administrator', 'admin', '06', '120000935', 

NULL, NULL, NULL, '20100824', 'admin', NULL, NULL);
INSERT INTO zdbranch(BranchInnerCode, BranchCode, ParentInnerCode, Type, OrderFlag, Name, TreeLevel, IsLeaf, Phone, Fax, Manager, Leader1, 

Leader2, Prop1, Prop2, Prop3, Prop4, Memo, AddTime, AddUser, ModifyTime, ModifyUser)
  VALUES('120000095120000925120000936', '2120999127103000H', '120000095120000925', '0', 935, '黄海路支行', 3, 'Y', NULL, NULL, 'admin', 'administrator', 'admin', '06', '120000936', 

NULL, NULL, NULL, 

'20100824', 'admin', NULL, NULL)
;
INSERT INTO zdbranch(BranchInnerCode, BranchCode, ParentInnerCode, Type, OrderFlag, Name, TreeLevel, IsLeaf, Phone, Fax, Manager, Leader1, 

Leader2, Prop1, Prop2, Prop3, Prop4, Memo, AddTime, AddUser, ModifyTime, ModifyUser)
  VALUES('120000095120000925120000926120000931120000937', '2120999103104000V', '120000095120000925120000926120000931', '0', 936, '凯旋门支行', 5, 'Y', NULL, NULL, 'admin', 'administrator', 'admin', '06', '120000937', 

NULL, NULL, NULL, '20100824', 'admin', NULL, NULL);
INSERT INTO zdbranch(BranchInnerCode, BranchCode, ParentInnerCode, Type, OrderFlag, Name, TreeLevel, IsLeaf, Phone, Fax, Manager, Leader1, 

Leader2, Prop1, Prop2, Prop3, Prop4, Memo, AddTime, AddUser, ModifyTime, ModifyUser)
  VALUES('120000095120000925120000938', '21209991271050007', '120000095120000925', '0', 937, '海关大楼支行', 3, 'Y', NULL, NULL, 'admin', 'administrator', 'admin', '06', 

'120000938', NULL, NULL, NULL, 

'20100824', 'admin', NULL, NULL)
;
INSERT INTO zdbranch(BranchInnerCode, BranchCode, ParentInnerCode, Type, OrderFlag, Name, TreeLevel, IsLeaf, Phone, Fax, Manager, Leader1, 

Leader2, Prop1, Prop2, Prop3, Prop4, Memo, AddTime, AddUser, ModifyTime, ModifyUser)
  VALUES('120000095120000925120000939', '21209991271060002', '120000095120000925', '0', 938, '泰丰支行', 3, 'Y', NULL, NULL, 'admin', 'administrator', 'admin', '06', '120000939', 

NULL, NULL, NULL, '20100824', 'admin', NULL, NULL);
INSERT INTO zdbranch(BranchInnerCode, BranchCode, ParentInnerCode, Type, OrderFlag, Name, TreeLevel, IsLeaf, Phone, Fax, Manager, Leader1, 

Leader2, Prop1, Prop2, Prop3, Prop4, Memo, AddTime, AddUser, ModifyTime, ModifyUser)
  VALUES('120000095120000001120000940', '21200010000290007', '120000095120000001', '0', 939, '分行运营管理部', 3, 'Y', NULL, NULL, 'admin', 'administrator', 'admin', '02', 

'120000940', NULL, NULL, NULL, 

'20100824', 'admin', NULL, NULL)
;
INSERT INTO zdbranch(BranchInnerCode, BranchCode, ParentInnerCode, Type, OrderFlag, Name, TreeLevel, IsLeaf, Phone, Fax, Manager, Leader1, 

Leader2, Prop1, Prop2, Prop3, Prop4, Memo, AddTime, AddUser, ModifyTime, ModifyUser)
  VALUES('120000095120000001120000941', '2120001000030000F', '120000095120000001', '0', 940, '分行工会委员会办公室', 3, 'Y', NULL, NULL, 'admin', 'administrator', 'admin', 

'02', '120000941', NULL, NULL, 

NULL, '20100824', 'admin', NULL, NULL)
;
INSERT INTO zdbranch(BranchInnerCode, BranchCode, ParentInnerCode, Type, OrderFlag, Name, TreeLevel, IsLeaf, Phone, Fax, Manager, Leader1, 

Leader2, Prop1, Prop2, Prop3, Prop4, Memo, AddTime, AddUser, ModifyTime, ModifyUser)
  VALUES('120000095120000001120000942', '2120001000031000A', '120000095120000001', '0', 941, '分行法律与合规部', 3, 'Y', NULL, NULL, 'admin', 'administrator', 'admin', '02', 

'120000942', NULL, NULL, NULL, 

'20100824', 'admin', NULL, NULL)
;
INSERT INTO zdbranch(BranchInnerCode, BranchCode, ParentInnerCode, Type, OrderFlag, Name, TreeLevel, IsLeaf, Phone, Fax, Manager, Leader1, 

Leader2, Prop1, Prop2, Prop3, Prop4, Memo, AddTime, AddUser, ModifyTime, ModifyUser)
  VALUES('120000095120000135120000098120000099120000943', '21209991031000173', '120000095120000135120000098120000099', '0', 942, '解放路支行行长室', 5, 'Y', NULL, NULL, 'admin', 'administrator', 'admin', '06', 

'120000943', NULL, NULL, NULL, 

'20100824', 'admin', NULL, NULL)
;
INSERT INTO zdbranch(BranchInnerCode, BranchCode, ParentInnerCode, Type, OrderFlag, Name, TreeLevel, IsLeaf, Phone, Fax, Manager, Leader1, 

Leader2, Prop1, Prop2, Prop3, Prop4, Memo, AddTime, AddUser, ModifyTime, ModifyUser)
  VALUES('120000095120000135120000098120000099120000944', '2120999103100019U', '120000095120000135120000098120000099', '0', 943, '解放路支行办公室', 5, 'Y', NULL, NULL, 'admin', 'administrator', 'admin', '06', 

'120000944', NULL, NULL, NULL, 

'20100824', 'admin', NULL, NULL)
;
INSERT INTO zdbranch(BranchInnerCode, BranchCode, ParentInnerCode, Type, OrderFlag, Name, TreeLevel, IsLeaf, Phone, Fax, Manager, Leader1, 

Leader2, Prop1, Prop2, Prop3, Prop4, Memo, AddTime, AddUser, ModifyTime, ModifyUser)
  VALUES('120000095120000135120000098120000099120000945', '21209991031000181', '120000095120000135120000098120000099', '0', 944, '解放路支行监督保障部', 5, 'Y', NULL, NULL, 'admin', 'administrator', 'admin', 

'06', '120000945', NULL, NULL, NULL, 

'20100824', 'admin', NULL, NULL)
;
INSERT INTO zdbranch(BranchInnerCode, BranchCode, ParentInnerCode, Type, OrderFlag, Name, TreeLevel, IsLeaf, Phone, Fax, Manager, Leader1, 

Leader2, Prop1, Prop2, Prop3, Prop4, Memo, AddTime, AddUser, ModifyTime, ModifyUser)
  VALUES('120000095120000135120000098120000099120000946', '21209991031000157', '120000095120000135120000098120000099', '0', 945, '解放路支行国际业务部', 5, 'Y', NULL, NULL, 'admin', 'administrator', 'admin', 

'06', '120000946', NULL, NULL, NULL, 

'20100824', 'admin', NULL, NULL)
;
INSERT INTO zdbranch(BranchInnerCode, BranchCode, ParentInnerCode, Type, OrderFlag, Name, TreeLevel, IsLeaf, Phone, Fax, Manager, Leader1, 

Leader2, Prop1, Prop2, Prop3, Prop4, Memo, AddTime, AddUser, ModifyTime, ModifyUser)
  VALUES('120000095120000135120000098120000099120000947', '21209991031000165', '120000095120000135120000098120000099', '0', 946, '解放路支行计划财务部', 5, 'Y', NULL, NULL, 'admin', 'administrator', 'admin', 

'06', '120000947', NULL, NULL, NULL, 

'20100824', 'admin', NULL, NULL)
;
INSERT INTO zdbranch(BranchInnerCode, BranchCode, ParentInnerCode, Type, OrderFlag, Name, TreeLevel, IsLeaf, Phone, Fax, Manager, Leader1, 

Leader2, Prop1, Prop2, Prop3, Prop4, Memo, AddTime, AddUser, ModifyTime, ModifyUser)
  VALUES('120000095120000135120000098120000099120000948', '2120999103100012D', '120000095120000135120000098120000099', '0', 947, '解放路支行信贷管理部', 5, 'Y', NULL, NULL, 'admin', 'administrator', 'admin', 

'06', '120000948', NULL, NULL, NULL, 

'20100824', 'admin', NULL, NULL)
;
INSERT INTO zdbranch(BranchInnerCode, BranchCode, ParentInnerCode, Type, OrderFlag, Name, TreeLevel, IsLeaf, Phone, Fax, Manager, Leader1, 

Leader2, Prop1, Prop2, Prop3, Prop4, Memo, AddTime, AddUser, ModifyTime, ModifyUser)
  VALUES('120000095120000135120000098120000099120000949', '2120999103100013B', '120000095120000135120000098120000099', '0', 948, '解放路支行业务发展部', 5, 'Y', NULL, NULL, 'admin', 'administrator', 'admin', 

'06', '120000949', NULL, NULL, NULL, 

'20100824', 'admin', NULL, NULL)
;
INSERT INTO zdbranch(BranchInnerCode, BranchCode, ParentInnerCode, Type, OrderFlag, Name, TreeLevel, IsLeaf, Phone, Fax, Manager, Leader1, 

Leader2, Prop1, Prop2, Prop3, Prop4, Memo, AddTime, AddUser, ModifyTime, ModifyUser)
  VALUES('120000095120000135120000098120000099120000950', '2120999103100011F', '120000095120000135120000098120000099', '0', 949, '解放路支行中间业务部', 5, 'Y', NULL, NULL, 'admin', 'administrator', 'admin', 

'06', '120000950', NULL, NULL, NULL, 

'20100824', 'admin', NULL, NULL)
;
INSERT INTO zdbranch(BranchInnerCode, BranchCode, ParentInnerCode, Type, OrderFlag, Name, TreeLevel, IsLeaf, Phone, Fax, Manager, Leader1, 

Leader2, Prop1, Prop2, Prop3, Prop4, Memo, AddTime, AddUser, ModifyTime, ModifyUser)
  VALUES('120000095120000135120000098120000099120000951', '2120999103100020E', '120000095120000135120000098120000099', '0', 950, '解放路支行双清办公室', 5, 'Y', NULL, NULL, 'admin', 'administrator', 'admin', 

'06', '120000951', NULL, NULL, NULL, 

'20100824', 'admin', NULL, NULL)
;
INSERT INTO zdbranch(BranchInnerCode, BranchCode, ParentInnerCode, Type, OrderFlag, Name, TreeLevel, IsLeaf, Phone, Fax, Manager, Leader1, 

Leader2, Prop1, Prop2, Prop3, Prop4, Memo, AddTime, AddUser, ModifyTime, ModifyUser)
  VALUES('120000095120000135120000098120000099120000952', '21209991031000149', '120000095120000135120000098120000099', '0', 951, '解放路支行营业部', 5, 'Y', NULL, NULL, 'admin', 'administrator', 'admin', '06', 

'120000952', NULL, NULL, NULL, 

'20100824', 'admin', NULL, NULL)
;
INSERT INTO zdbranch(BranchInnerCode, BranchCode, ParentInnerCode, Type, OrderFlag, Name, TreeLevel, IsLeaf, Phone, Fax, Manager, Leader1, 

Leader2, Prop1, Prop2, Prop3, Prop4, Memo, AddTime, AddUser, ModifyTime, ModifyUser)
  VALUES('120000095120000001120000953', '21200010000320005', '120000095120000001', '0', 952, '分行大客户部', 3, 'Y', NULL, NULL, 'admin', 'administrator', 'admin', '02', 

'120000953', NULL, NULL, NULL, 

'20100824', 'admin', NULL, NULL)
;
INSERT INTO zdbranch(BranchInnerCode, BranchCode, ParentInnerCode, Type, OrderFlag, Name, TreeLevel, IsLeaf, Phone, Fax, Manager, Leader1, 

Leader2, Prop1, Prop2, Prop3, Prop4, Memo, AddTime, AddUser, ModifyTime, ModifyUser)
  VALUES('120000095120000266120000954', '21209991071200007', '120000095120000266', '0', 953, '红桥区广开四马路分理处', 3, 'Y', NULL, NULL, 'admin', 'administrator', 'admin', 

'06', '120000954', NULL, NULL, 

NULL, '20100824', 'admin', NULL, NULL)
;
INSERT INTO zdbranch(BranchInnerCode, BranchCode, ParentInnerCode, Type, OrderFlag, Name, TreeLevel, IsLeaf, Phone, Fax, Manager, Leader1, 

Leader2, Prop1, Prop2, Prop3, Prop4, Memo, AddTime, AddUser, ModifyTime, ModifyUser)
  VALUES('120000095120000233120000955', '21209991061280006', '120000095120000233', '0', 954, '金钟河大街支行', 3, 'Y', NULL, NULL, 'admin', 'administrator', 'admin', '06', 

'120000955', NULL, NULL, NULL, 

'20100824', 'admin', NULL, NULL)
;
INSERT INTO zdbranch(BranchInnerCode, BranchCode, ParentInnerCode, Type, OrderFlag, Name, TreeLevel, IsLeaf, Phone, Fax, Manager, Leader1, 

Leader2, Prop1, Prop2, Prop3, Prop4, Memo, AddTime, AddUser, ModifyTime, ModifyUser)
  VALUES('120000095120000329120000956', '2120999109125000S', '120000095120000329', '0', 955, '世贸支行李明庄储蓄所', 3, 'Y', NULL, NULL, 'admin', 'administrator', 'admin', 

'10', '120000956', NULL, NULL, NULL, 

'20100824', 'admin', NULL, NULL)
;
INSERT INTO zdbranch(BranchInnerCode, BranchCode, ParentInnerCode, Type, OrderFlag, Name, TreeLevel, IsLeaf, Phone, Fax, Manager, Leader1, 

Leader2, Prop1, Prop2, Prop3, Prop4, Memo, AddTime, AddUser, ModifyTime, ModifyUser)
  VALUES('120000095120000329120000957', '2120999109116000T', '120000095120000329', '0', 956, '梅江支行', 3, 'Y', NULL, NULL, 'admin', 'administrator', 'admin', '06', '120000957', 

NULL, NULL, NULL, '20100824', 'admin', NULL, NULL);
INSERT INTO zdbranch(BranchInnerCode, BranchCode, ParentInnerCode, Type, OrderFlag, Name, TreeLevel, IsLeaf, Phone, Fax, Manager, Leader1, 

Leader2, Prop1, Prop2, Prop3, Prop4, Memo, AddTime, AddUser, ModifyTime, ModifyUser)
  VALUES('120000095120000233120000958', '21209991061290001', '120000095120000233', '0', 957, '幸福道支行', 3, 'Y', NULL, NULL, 'admin', 'administrator', 'admin', '06', '120000958', 

NULL, NULL, NULL, 

'20100824', 'admin', NULL, NULL)
;
INSERT INTO zdbranch(BranchInnerCode, BranchCode, ParentInnerCode, Type, OrderFlag, Name, TreeLevel, IsLeaf, Phone, Fax, Manager, Leader1, 

Leader2, Prop1, Prop2, Prop3, Prop4, Memo, AddTime, AddUser, ModifyTime, ModifyUser)
  VALUES('120000095120000199120000959', '21209991051290009', '120000095120000199', '0', 958, '河东区万东路分理处', 3, 'Y', NULL, NULL, 'admin', 'administrator', 'admin', 

'06', '120000959', NULL, NULL, NULL, 

'20100824', 'admin', NULL, NULL)
;
INSERT INTO zdbranch(BranchInnerCode, BranchCode, ParentInnerCode, Type, OrderFlag, Name, TreeLevel, IsLeaf, Phone, Fax, Manager, Leader1, 

Leader2, Prop1, Prop2, Prop3, Prop4, Memo, AddTime, AddUser, ModifyTime, ModifyUser)
  VALUES('120000095120000199120000960', '2120999105130000H', '120000095120000199', '0', 959, '晨阳道支行', 3, 'Y', NULL, NULL, 'admin', 'administrator', 'admin', '06', '120000960', 

NULL, NULL, NULL, 

'20100824', 'admin', NULL, NULL)
;
INSERT INTO zdbranch(BranchInnerCode, BranchCode, ParentInnerCode, Type, OrderFlag, Name, TreeLevel, IsLeaf, Phone, Fax, Manager, Leader1, 

Leader2, Prop1, Prop2, Prop3, Prop4, Memo, AddTime, AddUser, ModifyTime, ModifyUser)
  VALUES('120000095120000329120000961', '2120999109117000O', '120000095120000329', '0', 960, '李七庄支行', 3, 'Y', NULL, NULL, 'admin', 'administrator', 'admin', '06', '120000961', 

NULL, NULL, NULL, 

'20100824', 'admin', NULL, NULL)
;
INSERT INTO zdbranch(BranchInnerCode, BranchCode, ParentInnerCode, Type, OrderFlag, Name, TreeLevel, IsLeaf, Phone, Fax, Manager, Leader1, 

Leader2, Prop1, Prop2, Prop3, Prop4, Memo, AddTime, AddUser, ModifyTime, ModifyUser)
  VALUES('120000095120000165120000962', '2120999104124000B', '120000095120000165', '0', 961, '西青区气象台路分理处', 3, 'Y', NULL, NULL, 'admin', 'administrator', 'admin', 

'06', '120000962', NULL, NULL, 

NULL, '20100824', 'admin', NULL, NULL)
;
INSERT INTO zdbranch(BranchInnerCode, BranchCode, ParentInnerCode, Type, OrderFlag, Name, TreeLevel, IsLeaf, Phone, Fax, Manager, Leader1, 

Leader2, Prop1, Prop2, Prop3, Prop4, Memo, AddTime, AddUser, ModifyTime, ModifyUser)
  VALUES('120000095120000909120000963', '2120999126112000O', '120000095120000909', '0', 962, '经济技术开发区金江分理处', 3, 'Y', NULL, NULL, 'admin', 'administrator', 

'admin', '06', '120000963', NULL, NULL, 

NULL, '20100824', 'admin', NULL, NULL)
;
INSERT INTO zdbranch(BranchInnerCode, BranchCode, ParentInnerCode, Type, OrderFlag, Name, TreeLevel, IsLeaf, Phone, Fax, Manager, Leader1, 

Leader2, Prop1, Prop2, Prop3, Prop4, Memo, AddTime, AddUser, ModifyTime, ModifyUser)
  VALUES('120000095120000909120000964', '2120999126113000J', '120000095120000909', '0', 963, '汉沽区东风路分理处', 3, 'Y', NULL, NULL, 'admin', 'administrator', 'admin', 

'06', '120000964', NULL, NULL, NULL, 

'20100824', 'admin', NULL, NULL)
;
INSERT INTO zdbranch(BranchInnerCode, BranchCode, ParentInnerCode, Type, OrderFlag, Name, TreeLevel, IsLeaf, Phone, Fax, Manager, Leader1, 

Leader2, Prop1, Prop2, Prop3, Prop4, Memo, AddTime, AddUser, ModifyTime, ModifyUser)
  VALUES('120000095120000909120000965', '2120999126114000E', '120000095120000909', '0', 964, '经济技术开发区西区分理处', 3, 'Y', NULL, NULL, 'admin', 'administrator', 

'admin', '06', '120000965', NULL, NULL, 

NULL, '20100824', 'admin', NULL, NULL)
;
INSERT INTO zdbranch(BranchInnerCode, BranchCode, ParentInnerCode, Type, OrderFlag, Name, TreeLevel, IsLeaf, Phone, Fax, Manager, Leader1, 

Leader2, Prop1, Prop2, Prop3, Prop4, Memo, AddTime, AddUser, ModifyTime, ModifyUser)
  VALUES('120000095120000909120000966', '21209991261150009', '120000095120000909', '0', 965, '经济开发区分行泰达分理处', 3, 'Y', NULL, NULL, 'admin', 'administrator', 

'admin', '06', '120000966', NULL, NULL, 

NULL, '20100824', 'admin', NULL, NULL)
;
INSERT INTO zdbranch(BranchInnerCode, BranchCode, ParentInnerCode, Type, OrderFlag, Name, TreeLevel, IsLeaf, Phone, Fax, Manager, Leader1, 

Leader2, Prop1, Prop2, Prop3, Prop4, Memo, AddTime, AddUser, ModifyTime, ModifyUser)
  VALUES('120000095120000301120000967', '2120999108123000F', '120000095120000301', '0', 966, '宾水西道支行', 3, 'Y', NULL, NULL, 'admin', 'administrator', 'admin', '06', 

'120000967', NULL, NULL, NULL, 

'20100824', 'admin', NULL, NULL)
;
INSERT INTO zdbranch(BranchInnerCode, BranchCode, ParentInnerCode, Type, OrderFlag, Name, TreeLevel, IsLeaf, Phone, Fax, Manager, Leader1, 

Leader2, Prop1, Prop2, Prop3, Prop4, Memo, AddTime, AddUser, ModifyTime, ModifyUser)
  VALUES('120000095120000301120000968', '2120999108124000A', '120000095120000301', '0', 967, '红旗南路支行', 3, 'Y', NULL, NULL, 'admin', 'administrator', 'admin', '06', 

'120000968', NULL, NULL, NULL, 

'20100824', 'admin', NULL, NULL)
;
INSERT INTO zdbranch(BranchInnerCode, BranchCode, ParentInnerCode, Type, OrderFlag, Name, TreeLevel, IsLeaf, Phone, Fax, Manager, Leader1, 

Leader2, Prop1, Prop2, Prop3, Prop4, Memo, AddTime, AddUser, ModifyTime, ModifyUser)
  VALUES('120000095120000301120000969', '21209991081250005', '120000095120000301', '0', 968, '新技术产业园区天拖南储蓄所', 3, 'Y', NULL, NULL, 'admin', 'administrator', 

'admin', '06', '120000969', NULL, 

NULL, NULL, '20100824', 'admin', NULL, NULL)
;
INSERT INTO zdbranch(BranchInnerCode, BranchCode, ParentInnerCode, Type, OrderFlag, Name, TreeLevel, IsLeaf, Phone, Fax, Manager, Leader1, 

Leader2, Prop1, Prop2, Prop3, Prop4, Memo, AddTime, AddUser, ModifyTime, ModifyUser)
  VALUES('120000095120000301120000970', '2120999108126000V', '120000095120000301', '0', 969, '新技术产业园区苑东路分理处', 3, 'Y', NULL, NULL, 'admin', 'administrator', 

'admin', '06', '120000970', NULL, 

NULL, NULL, '20100824', 'admin', NULL, NULL)
;
INSERT INTO zdbranch(BranchInnerCode, BranchCode, ParentInnerCode, Type, OrderFlag, Name, TreeLevel, IsLeaf, Phone, Fax, Manager, Leader1, 

Leader2, Prop1, Prop2, Prop3, Prop4, Memo, AddTime, AddUser, ModifyTime, ModifyUser)
  VALUES('120000095120000301120000971', '2120999108127000Q', '120000095120000301', '0', 970, '新技术产业园区华苑信美道分理处', 3, 'Y', NULL, NULL, 'admin', 'administrator', 

'admin', '06', '120000971', NULL, 

NULL, NULL, '20100824', 'admin', NULL, NULL)
;
INSERT INTO zdbranch(BranchInnerCode, BranchCode, ParentInnerCode, Type, OrderFlag, Name, TreeLevel, IsLeaf, Phone, Fax, Manager, Leader1, 

Leader2, Prop1, Prop2, Prop3, Prop4, Memo, AddTime, AddUser, ModifyTime, ModifyUser)
  VALUES('120000095120000102120000972', '2120999102126000H', '120000095120000102', '0', 971, '南开区金属市场储蓄所', 5, 'Y', NULL, NULL, 'admin', 'administrator', 'admin', 

'06', '120000972', NULL, NULL, 

NULL, '20100824', 'admin', NULL, NULL)
;
INSERT INTO zdbranch(BranchInnerCode, BranchCode, ParentInnerCode, Type, OrderFlag, Name, TreeLevel, IsLeaf, Phone, Fax, Manager, Leader1, 

Leader2, Prop1, Prop2, Prop3, Prop4, Memo, AddTime, AddUser, ModifyTime, ModifyUser)
  VALUES('120000095120000102120000973', '2120999102127000C', '120000095120000102', '0', 972, '西营门支行', 5, 'Y', NULL, NULL, 'admin', 'administrator', 'admin', '06', '120000973', 

NULL, NULL, NULL, 

'20100824', 'admin', NULL, NULL)
;
INSERT INTO zdbranch(BranchInnerCode, BranchCode, ParentInnerCode, Type, OrderFlag, Name, TreeLevel, IsLeaf, Phone, Fax, Manager, Leader1, 

Leader2, Prop1, Prop2, Prop3, Prop4, Memo, AddTime, AddUser, ModifyTime, ModifyUser)
  VALUES('120000095120000102120000974', '21209991021280007', '120000095120000102', '0', 973, '长江道支行', 5, 'Y', NULL, NULL, 'admin', 'administrator', 'admin', '06', '120000974', 

NULL, NULL, NULL, 

'20100824', 'admin', NULL, NULL)
;
INSERT INTO zdbranch(BranchInnerCode, BranchCode, ParentInnerCode, Type, OrderFlag, Name, TreeLevel, IsLeaf, Phone, Fax, Manager, Leader1, 

Leader2, Prop1, Prop2, Prop3, Prop4, Memo, AddTime, AddUser, ModifyTime, ModifyUser)
  VALUES('120000095120000102120000975', '21209991021290002', '120000095120000102', '0', 974, '双峰道支行', 5, 'Y', NULL, NULL, 'admin', 'administrator', 'admin', '06', '120000975', 

NULL, NULL, NULL, 

'20100824', 'admin', NULL, NULL)
;
INSERT INTO zdbranch(BranchInnerCode, BranchCode, ParentInnerCode, Type, OrderFlag, Name, TreeLevel, IsLeaf, Phone, Fax, Manager, Leader1, 

Leader2, Prop1, Prop2, Prop3, Prop4, Memo, AddTime, AddUser, ModifyTime, ModifyUser)
  VALUES('120000095120000301120000976', '2120999108128000L', '120000095120000301', '0', 975, '天塔支行', 3, 'Y', NULL, NULL, 'admin', 'administrator', 'admin', '06', '120000976', 

NULL, NULL, NULL, '20100824', 'admin', NULL, NULL);
INSERT INTO zdbranch(BranchInnerCode, BranchCode, ParentInnerCode, Type, OrderFlag, Name, TreeLevel, IsLeaf, Phone, Fax, Manager, Leader1, 

Leader2, Prop1, Prop2, Prop3, Prop4, Memo, AddTime, AddUser, ModifyTime, ModifyUser)
  VALUES('120000095120000301120000977', '2120999108129000G', '120000095120000301', '0', 976, '新技术产业园区体育馆分理处', 3, 'Y', NULL, NULL, 'admin', 'administrator', 

'admin', '06', '120000977', NULL, 

NULL, NULL, '20100824', 'admin', NULL, NULL)
;
INSERT INTO zdbranch(BranchInnerCode, BranchCode, ParentInnerCode, Type, OrderFlag, Name, TreeLevel, IsLeaf, Phone, Fax, Manager, Leader1, 

Leader2, Prop1, Prop2, Prop3, Prop4, Memo, AddTime, AddUser, ModifyTime, ModifyUser)
  VALUES('120000095120000301120000978', '2120999108130000O', '120000095120000301', '0', 977, '体育中心支行', 3, 'Y', NULL, NULL, 'admin', 'administrator', 'admin', '06', 

'120000978', NULL, NULL, NULL, 

'20100824', 'admin', NULL, NULL)
;
INSERT INTO zdbranch(BranchInnerCode, BranchCode, ParentInnerCode, Type, OrderFlag, Name, TreeLevel, IsLeaf, Phone, Fax, Manager, Leader1, 

Leader2, Prop1, Prop2, Prop3, Prop4, Memo, AddTime, AddUser, ModifyTime, ModifyUser)
  VALUES('120000095120000301120000979', '2120999108131000J', '120000095120000301', '0', 978, '碧轩园支行', 3, 'Y', NULL, NULL, 'admin', 'administrator', 'admin', '06', '120000979', 

NULL, NULL, NULL, 

'20100824', 'admin', NULL, NULL)
;
INSERT INTO zdbranch(BranchInnerCode, BranchCode, ParentInnerCode, Type, OrderFlag, Name, TreeLevel, IsLeaf, Phone, Fax, Manager, Leader1, 

Leader2, Prop1, Prop2, Prop3, Prop4, Memo, AddTime, AddUser, ModifyTime, ModifyUser)
  VALUES('120000095120000301120000980', '2120999108132000E', '120000095120000301', '0', 979, '信诚大厦支行', 3, 'Y', NULL, NULL, 'admin', 'administrator', 'admin', '06', 

'120000980', NULL, NULL, NULL, 

'20100824', 'admin', NULL, NULL)
;
INSERT INTO zdbranch(BranchInnerCode, BranchCode, ParentInnerCode, Type, OrderFlag, Name, TreeLevel, IsLeaf, Phone, Fax, Manager, Leader1, 

Leader2, Prop1, Prop2, Prop3, Prop4, Memo, AddTime, AddUser, ModifyTime, ModifyUser)
  VALUES('120000095120000301120000981', '21209991081330009', '120000095120000301', '0', 980, '新技术产业园区水上北路分理处', 3, 'Y', NULL, NULL, 'admin', 'administrator', 

'admin', '06', '120000981', NULL, 

NULL, NULL, '20100824', 'admin', NULL, NULL)
;
INSERT INTO zdbranch(BranchInnerCode, BranchCode, ParentInnerCode, Type, OrderFlag, Name, TreeLevel, IsLeaf, Phone, Fax, Manager, Leader1, 

Leader2, Prop1, Prop2, Prop3, Prop4, Memo, AddTime, AddUser, ModifyTime, ModifyUser)
  VALUES('120000095120000102120000982', '2120999102130000A', '120000095120000102', '0', 981, '南开区三马路分理处', 5, 'Y', NULL, NULL, 'admin', 'administrator', 'admin', 

'06', '120000982', NULL, NULL, NULL, 

'20100824', 'admin', NULL, NULL)
;
INSERT INTO zdbranch(BranchInnerCode, BranchCode, ParentInnerCode, Type, OrderFlag, Name, TreeLevel, IsLeaf, Phone, Fax, Manager, Leader1, 

Leader2, Prop1, Prop2, Prop3, Prop4, Memo, AddTime, AddUser, ModifyTime, ModifyUser)
  VALUES('120000095120000102120000983', '21209991021310005', '120000095120000102', '0', 982, '广开中街支行', 5, 'Y', NULL, NULL, 'admin', 'administrator', 'admin', '06', 

'120000983', NULL, NULL, NULL, 

'20100824', 'admin', NULL, NULL)
;
INSERT INTO zdbranch(BranchInnerCode, BranchCode, ParentInnerCode, Type, OrderFlag, Name, TreeLevel, IsLeaf, Phone, Fax, Manager, Leader1, 

Leader2, Prop1, Prop2, Prop3, Prop4, Memo, AddTime, AddUser, ModifyTime, ModifyUser)
  VALUES('120000095120000165120000984', '21209991041250006', '120000095120000165', '0', 983, '永安道支行', 3, 'Y', NULL, NULL, 'admin', 'administrator', 'admin', '06', '120000984', 

NULL, NULL, NULL, 

'20100824', 'admin', NULL, NULL)
;
INSERT INTO zdbranch(BranchInnerCode, BranchCode, ParentInnerCode, Type, OrderFlag, Name, TreeLevel, IsLeaf, Phone, Fax, Manager, Leader1, 

Leader2, Prop1, Prop2, Prop3, Prop4, Memo, AddTime, AddUser, ModifyTime, ModifyUser)
  VALUES('120000095120000165120000985', '21209991041260001', '120000095120000165', '0', 984, '西南楼支行', 3, 'Y', NULL, NULL, 'admin', 'administrator', 'admin', '06', '120000985', 

NULL, NULL, NULL, 

'20100824', 'admin', NULL, NULL)
;
INSERT INTO zdbranch(BranchInnerCode, BranchCode, ParentInnerCode, Type, OrderFlag, Name, TreeLevel, IsLeaf, Phone, Fax, Manager, Leader1, 

Leader2, Prop1, Prop2, Prop3, Prop4, Memo, AddTime, AddUser, ModifyTime, ModifyUser)
  VALUES('120000095120000165120000986', '2120999104127000R', '120000095120000165', '0', 985, '凯旋门支行', 3, 'Y', NULL, NULL, 'admin', 'administrator', 'admin', '06', '120000986', 

NULL, NULL, NULL, 

'20100824', 'admin', NULL, NULL)
;
INSERT INTO zdbranch(BranchInnerCode, BranchCode, ParentInnerCode, Type, OrderFlag, Name, TreeLevel, IsLeaf, Phone, Fax, Manager, Leader1, 

Leader2, Prop1, Prop2, Prop3, Prop4, Memo, AddTime, AddUser, ModifyTime, ModifyUser)
  VALUES('120000095120000165120000987', '2120999104128000M', '120000095120000165', '0', 986, '友谊北路支行', 3, 'Y', NULL, NULL, 'admin', 'administrator', 'admin', '06', 

'120000987', NULL, NULL, NULL, 

'20100824', 'admin', NULL, NULL)
;
INSERT INTO zdbranch(BranchInnerCode, BranchCode, ParentInnerCode, Type, OrderFlag, Name, TreeLevel, IsLeaf, Phone, Fax, Manager, Leader1, 

Leader2, Prop1, Prop2, Prop3, Prop4, Memo, AddTime, AddUser, ModifyTime, ModifyUser)
  VALUES('120000095120000165120000988', '2120999104129000H', '120000095120000165', '0', 987, '津南区登发分理处', 3, 'Y', NULL, NULL, 'admin', 'administrator', 'admin', '06', 

'120000988', NULL, NULL, NULL, 

'20100824', 'admin', NULL, NULL)
;
INSERT INTO zdbranch(BranchInnerCode, BranchCode, ParentInnerCode, Type, OrderFlag, Name, TreeLevel, IsLeaf, Phone, Fax, Manager, Leader1, 

Leader2, Prop1, Prop2, Prop3, Prop4, Memo, AddTime, AddUser, ModifyTime, ModifyUser)
  VALUES('120000095120000199120000989', '2120999105131000C', '120000095120000199', '0', 988, '太阳城支行', 3, 'Y', NULL, NULL, 'admin', 'administrator', 'admin', '06', '120000989', 

NULL, NULL, NULL, 

'20100824', 'admin', NULL, NULL)
;
INSERT INTO zdbranch(BranchInnerCode, BranchCode, ParentInnerCode, Type, OrderFlag, Name, TreeLevel, IsLeaf, Phone, Fax, Manager, Leader1, 

Leader2, Prop1, Prop2, Prop3, Prop4, Memo, AddTime, AddUser, ModifyTime, ModifyUser)
  VALUES('120000095120000199120000990', '21209991051320007', '120000095120000199', '0', 989, '丰盈支行', 3, 'Y', NULL, NULL, 'admin', 'administrator', 'admin', '06', '120000990', 

NULL, NULL, NULL, '20100824', 'admin', NULL, NULL)
;
INSERT INTO zdbranch(BranchInnerCode, BranchCode, ParentInnerCode, Type, OrderFlag, Name, TreeLevel, IsLeaf, Phone, Fax, Manager, Leader1, 

Leader2, Prop1, Prop2, Prop3, Prop4, Memo, AddTime, AddUser, ModifyTime, ModifyUser)
  VALUES('120000095120000199120000991', '21209991051330002', '120000095120000199', '0', 990, '光明支行', 3, 'Y', NULL, NULL, 'admin', 'administrator', 'admin', '06', '120000991', 

NULL, NULL, NULL, '20100824', 'admin', NULL, NULL)
;
INSERT INTO zdbranch(BranchInnerCode, BranchCode, ParentInnerCode, Type, OrderFlag, Name, TreeLevel, IsLeaf, Phone, Fax, Manager, Leader1, 

Leader2, Prop1, Prop2, Prop3, Prop4, Memo, AddTime, AddUser, ModifyTime, ModifyUser)
  VALUES('120000095120000329120000992', '2120999109118000J', '120000095120000329', '0', 991, '蓝水支行', 3, 'Y', NULL, NULL, 'admin', 'administrator', 'admin', '06', '120000992', 

NULL, NULL, NULL, '20100824', 'admin', NULL, NULL)
;
INSERT INTO zdbranch(BranchInnerCode, BranchCode, ParentInnerCode, Type, OrderFlag, Name, TreeLevel, IsLeaf, Phone, Fax, Manager, Leader1, 

Leader2, Prop1, Prop2, Prop3, Prop4, Memo, AddTime, AddUser, ModifyTime, ModifyUser)
  VALUES('120000095120000329120000993', '2120999109119000E', '120000095120000329', '0', 992, '体北道支行', 3, 'Y', NULL, NULL, 'admin', 'administrator', 'admin', '06', '120000993', 

NULL, NULL, NULL, 

'20100824', 'admin', NULL, NULL)
;
INSERT INTO zdbranch(BranchInnerCode, BranchCode, ParentInnerCode, Type, OrderFlag, Name, TreeLevel, IsLeaf, Phone, Fax, Manager, Leader1, 

Leader2, Prop1, Prop2, Prop3, Prop4, Memo, AddTime, AddUser, ModifyTime, ModifyUser)
  VALUES('120000095120000329120000994', '2120999109120000M', '120000095120000329', '0', 993, '金牛支行', 3, 'Y', NULL, NULL, 'admin', 'administrator', 'admin', '06', '120000994', 

NULL, NULL, NULL, '20100824', 'admin', NULL, NULL)
;
INSERT INTO zdbranch(BranchInnerCode, BranchCode, ParentInnerCode, Type, OrderFlag, Name, TreeLevel, IsLeaf, Phone, Fax, Manager, Leader1, 

Leader2, Prop1, Prop2, Prop3, Prop4, Memo, AddTime, AddUser, ModifyTime, ModifyUser)
  VALUES('120000095120000329120000995', '2120999109121000H', '120000095120000329', '0', 994, '微山路支行', 3, 'Y', NULL, NULL, 'admin', 'administrator', 'admin', '06', '120000995', 

NULL, NULL, NULL, 

'20100824', 'admin', NULL, NULL)
;
INSERT INTO zdbranch(BranchInnerCode, BranchCode, ParentInnerCode, Type, OrderFlag, Name, TreeLevel, IsLeaf, Phone, Fax, Manager, Leader1, 

Leader2, Prop1, Prop2, Prop3, Prop4, Memo, AddTime, AddUser, ModifyTime, ModifyUser)
  VALUES('120000095120000329120000996', '2120999109122000C', '120000095120000329', '0', 995, '黑牛城东道支行', 3, 'Y', NULL, NULL, 'admin', 'administrator', 'admin', '06', 

'120000996', NULL, NULL, NULL, 

'20100824', 'admin', NULL, NULL)
;
INSERT INTO zdbranch(BranchInnerCode, BranchCode, ParentInnerCode, Type, OrderFlag, Name, TreeLevel, IsLeaf, Phone, Fax, Manager, Leader1, 

Leader2, Prop1, Prop2, Prop3, Prop4, Memo, AddTime, AddUser, ModifyTime, ModifyUser)
  VALUES('120000095120000329120000997', '21209991091230007', '120000095120000329', '0', 996, '瑞江支行', 3, 'Y', NULL, NULL, 'admin', 'administrator', 'admin', '06', '120000997', 

NULL, NULL, NULL, '20100824', 'admin', NULL, NULL)
;
INSERT INTO zdbranch(BranchInnerCode, BranchCode, ParentInnerCode, Type, OrderFlag, Name, TreeLevel, IsLeaf, Phone, Fax, Manager, Leader1, 

Leader2, Prop1, Prop2, Prop3, Prop4, Memo, AddTime, AddUser, ModifyTime, ModifyUser)
  VALUES('120000095120000329120000998', '2120999109126000N', '120000095120000329', '0', 997, '世贸支行建国大街储蓄所', 3, 'Y', NULL, NULL, 'admin', 'administrator', 'admin', 

'10', '120000998', NULL, NULL, 

NULL, '20100824', 'admin', NULL, NULL)
;
INSERT INTO zdbranch(BranchInnerCode, BranchCode, ParentInnerCode, Type, OrderFlag, Name, TreeLevel, IsLeaf, Phone, Fax, Manager, Leader1, 

Leader2, Prop1, Prop2, Prop3, Prop4, Memo, AddTime, AddUser, ModifyTime, ModifyUser)
  VALUES('120000095120000329120000999', '2120999109127000I', '120000095120000329', '0', 998, '世贸支行体育场储蓄所', 3, 'Y', NULL, NULL, 'admin', 'administrator', 'admin', 

'10', '120000999', NULL, NULL, NULL, 

'20100824', 'admin', NULL, NULL)
;
INSERT INTO zdbranch(BranchInnerCode, BranchCode, ParentInnerCode, Type, OrderFlag, Name, TreeLevel, IsLeaf, Phone, Fax, Manager, Leader1, 

Leader2, Prop1, Prop2, Prop3, Prop4, Memo, AddTime, AddUser, ModifyTime, ModifyUser)
  VALUES('120000095120000329120001000', '21209991091240002', '120000095120000329', '0', 999, '紫金山路支行', 3, 'Y', NULL, NULL, 'admin', 'administrator', 'admin', '06', 

'120001000', NULL, NULL, NULL, 

'20100824', 'admin', NULL, NULL)
;
INSERT INTO zdbranch(BranchInnerCode, BranchCode, ParentInnerCode, Type, OrderFlag, Name, TreeLevel, IsLeaf, Phone, Fax, Manager, Leader1, 

Leader2, Prop1, Prop2, Prop3, Prop4, Memo, AddTime, AddUser, ModifyTime, ModifyUser)
  VALUES('120000095120000135120001001', '2120999103119000V', '120000095120000135', '0', 1000, '成都道支行', 3, 'Y', NULL, NULL, 'admin', 'administrator', 'admin', '06', '120001001', 

NULL, NULL, NULL, 

'20100824', 'admin', NULL, NULL)
;
INSERT INTO zdbranch(BranchInnerCode, BranchCode, ParentInnerCode, Type, OrderFlag, Name, TreeLevel, IsLeaf, Phone, Fax, Manager, Leader1, 

Leader2, Prop1, Prop2, Prop3, Prop4, Memo, AddTime, AddUser, ModifyTime, ModifyUser)
  VALUES('120000095120000001120001002', '2120001000033000V', '120000095120000001', '0', 1001, '分行风险管理部', 3, 'Y', NULL, NULL, 'admin', 'administrator', 'admin', '02', 

'120001002', NULL, NULL, NULL, 

'20100824', 'admin', NULL, NULL)
;
INSERT INTO zdbranch(BranchInnerCode, BranchCode, ParentInnerCode, Type, OrderFlag, Name, TreeLevel, IsLeaf, Phone, Fax, Manager, Leader1, 

Leader2, Prop1, Prop2, Prop3, Prop4, Memo, AddTime, AddUser, ModifyTime, ModifyUser)
  VALUES('120000095120000001120001003', '2120001000034000Q', '120000095120000001', '0', 1002, '分行结算与现金管理部', 3, 'Y', NULL, NULL, 'admin', 'administrator', 'admin', 

'02', '120001003', NULL, NULL, 

NULL, '20100824', 'admin', NULL, NULL)
;
INSERT INTO zdbranch(BranchInnerCode, BranchCode, ParentInnerCode, Type, OrderFlag, Name, TreeLevel, IsLeaf, Phone, Fax, Manager, Leader1, 

Leader2, Prop1, Prop2, Prop3, Prop4, Memo, AddTime, AddUser, ModifyTime, ModifyUser)
  VALUES('120000095120000135120001004', '21209991031200008', '120000095120000135', '0', 1003, '云翔大厦支行', 3, 'Y', NULL, NULL, 'admin', 'administrator', 'admin', '06', 

'120001004', NULL, NULL, NULL, 

'20100824', 'admin', NULL, NULL)
;
INSERT INTO zdbranch(BranchInnerCode, BranchCode, ParentInnerCode, Type, OrderFlag, Name, TreeLevel, IsLeaf, Phone, Fax, Manager, Leader1, 

Leader2, Prop1, Prop2, Prop3, Prop4, Memo, AddTime, AddUser, ModifyTime, ModifyUser)
  VALUES('120000095120000135120001005', '21209991031210003', '120000095120000135', '0', 1004, '金耀支行', 3, 'Y', NULL, NULL, 'admin', 'administrator', 'admin', '06', '120001005', 

NULL, NULL, NULL, '20100824', 'admin', NULL, NULL)
;
INSERT INTO zdbranch(BranchInnerCode, BranchCode, ParentInnerCode, Type, OrderFlag, Name, TreeLevel, IsLeaf, Phone, Fax, Manager, Leader1, 

Leader2, Prop1, Prop2, Prop3, Prop4, Memo, AddTime, AddUser, ModifyTime, ModifyUser)
  VALUES('120000095120000135120001006', '2120999103122000T', '120000095120000135', '0', 1005, '昆明路支行', 3, 'Y', NULL, NULL, 'admin', 'administrator', 'admin', '06', '120001006', 

NULL, NULL, NULL, 

'20100824', 'admin', NULL, NULL)
;
INSERT INTO zdbranch(BranchInnerCode, BranchCode, ParentInnerCode, Type, OrderFlag, Name, TreeLevel, IsLeaf, Phone, Fax, Manager, Leader1, 

Leader2, Prop1, Prop2, Prop3, Prop4, Memo, AddTime, AddUser, ModifyTime, ModifyUser)
  VALUES('120000095120000925120001007', '2120999127112000G', '120000095120000925', '0', 1006, '东疆支行', 3, 'Y', NULL, NULL, 'admin', 'administrator', 'admin', '06', '120001007', 

NULL, NULL, NULL, '20100824', 'admin', NULL, NULL)
;
INSERT INTO zdbranch(BranchInnerCode, BranchCode, ParentInnerCode, Type, OrderFlag, Name, TreeLevel, IsLeaf, Phone, Fax, Manager, Leader1, 

Leader2, Prop1, Prop2, Prop3, Prop4, Memo, AddTime, AddUser, ModifyTime, ModifyUser)
  VALUES('120000095120000094120001008', '21200030000010006', '120000095120000094', '0', 1007, '分行营业部机关', 3, 'Y', NULL, NULL, 'admin', 'administrator', 'admin', '04', 

'120001008', NULL, NULL, NULL, 

'20100824', 'admin', NULL, NULL)
;
INSERT INTO zdbranch(BranchInnerCode, BranchCode, ParentInnerCode, Type, OrderFlag, Name, TreeLevel, IsLeaf, Phone, Fax, Manager, Leader1, 

Leader2, Prop1, Prop2, Prop3, Prop4, Memo, AddTime, AddUser, ModifyTime, ModifyUser)
  VALUES('120000095120000094120001008120001009', '21200030000010014', '120000095120000094120001008', '0', 1008, '分行营业部营业室', 4, 'Y', NULL, NULL, 'admin', 'administrator', 'admin', '04', 

'120001009', NULL, NULL, NULL, 

'20100824', 'admin', NULL, NULL)
;
INSERT INTO zdbranch(BranchInnerCode, BranchCode, ParentInnerCode, Type, OrderFlag, Name, TreeLevel, IsLeaf, Phone, Fax, Manager, Leader1, 

Leader2, Prop1, Prop2, Prop3, Prop4, Memo, AddTime, AddUser, ModifyTime, ModifyUser)
  VALUES('120000095120000522120001010', '2120999116124000H', '120000095120000522', '0', 1009, '北辰区宜兴埠储蓄所', 5, 'Y', NULL, NULL, 'admin', 'administrator', 'admin', 

'06', '120001010', NULL, NULL, NULL, 

'20100824', 'admin', NULL, NULL)
;
INSERT INTO zdbranch(BranchInnerCode, BranchCode, ParentInnerCode, Type, OrderFlag, Name, TreeLevel, IsLeaf, Phone, Fax, Manager, Leader1, 

Leader2, Prop1, Prop2, Prop3, Prop4, Memo, AddTime, AddUser, ModifyTime, ModifyUser)
  VALUES('120000095120000001120001011', '2120001000035000L', '120000095120000001', '0', 1010, '分行安全保卫部', 3, 'Y', NULL, NULL, 'admin', 'administrator', 'admin', '02', 

'120001011', NULL, NULL, NULL, 

'20100824', 'admin', NULL, NULL)
;
INSERT INTO zdbranch(BranchInnerCode, BranchCode, ParentInnerCode, Type, OrderFlag, Name, TreeLevel, IsLeaf, Phone, Fax, Manager, Leader1, 

Leader2, Prop1, Prop2, Prop3, Prop4, Memo, AddTime, AddUser, ModifyTime, ModifyUser)
  VALUES('120000095120000925120000926120001012', '2120999127001014V', '120000095120000925120000926', '0', 1011, '港保税区分行客户五部', 4, 'Y', NULL, NULL, 'admin', 'administrator', 'admin', 

'04', '120001012', NULL, NULL, NULL, 

'20100824', 'admin', NULL, NULL)
;
INSERT INTO zdbranch(BranchInnerCode, BranchCode, ParentInnerCode, Type, OrderFlag, Name, TreeLevel, IsLeaf, Phone, Fax, Manager, Leader1, 

Leader2, Prop1, Prop2, Prop3, Prop4, Memo, AddTime, AddUser, ModifyTime, ModifyUser)
  VALUES('120000095120000387120000419120001013', '2120999113001016T', '120000095120000387120000419', '0', 1012, '东丽支行客户一部', 4, 'Y', NULL, NULL, 'admin', 'administrator', 'admin', '04', 

'120001013', NULL, NULL, NULL, 

'20100824', 'admin', NULL, NULL)
;
INSERT INTO zdbranch(BranchInnerCode, BranchCode, ParentInnerCode, Type, OrderFlag, Name, TreeLevel, IsLeaf, Phone, Fax, Manager, Leader1, 

Leader2, Prop1, Prop2, Prop3, Prop4, Memo, AddTime, AddUser, ModifyTime, ModifyUser)
  VALUES('120000095120000329120000330120001014', '2120999109001015N', '120000095120000329120000330', '0', 1013, '世贸支行客户一部', 4, 'Y', NULL, NULL, 'admin', 'administrator', 'admin', '04', 

'120001014', NULL, NULL, NULL, 

'20100824', 'admin', NULL, NULL)
;
INSERT INTO zdbranch(BranchInnerCode, BranchCode, ParentInnerCode, Type, OrderFlag, Name, TreeLevel, IsLeaf, Phone, Fax, Manager, Leader1, 

Leader2, Prop1, Prop2, Prop3, Prop4, Memo, AddTime, AddUser, ModifyTime, ModifyUser)
  VALUES('120000095120000301120000302120001015', '21209991080010134', '120000095120000301120000302', '0', 1014, '新技术产业园区支行客户一部', 4, 'Y', NULL, NULL, 'admin', 'administrator', 

'admin', '04', '120001015', NULL, NULL, 

NULL, '20100824', 'admin', NULL, NULL)
;
INSERT INTO zdbranch(BranchInnerCode, BranchCode, ParentInnerCode, Type, OrderFlag, Name, TreeLevel, IsLeaf, Phone, Fax, Manager, Leader1, 

Leader2, Prop1, Prop2, Prop3, Prop4, Memo, AddTime, AddUser, ModifyTime, ModifyUser)
  VALUES('120000095120000522120000523120001016', '21209991160010254', '120000095120000522120000523', '0', 1015, '北辰支行客户一部', 3, 'Y', NULL, NULL, 'admin', 'administrator', 'admin', '04', 

'120001016', NULL, NULL, NULL, 

'20100824', 'admin', NULL, NULL)
;
INSERT INTO zdbranch(BranchInnerCode, BranchCode, ParentInnerCode, Type, OrderFlag, Name, TreeLevel, IsLeaf, Phone, Fax, Manager, Leader1, 

Leader2, Prop1, Prop2, Prop3, Prop4, Memo, AddTime, AddUser, ModifyTime, ModifyUser)
  VALUES('120000095120000925120000926120001017', '2120999127001010V', '120000095120000925120000926', '0', 1016, '港保税区分行客户二部', 4, 'Y', NULL, NULL, 'admin', 'administrator', 'admin', 

'04', '120001017', NULL, NULL, NULL, 

'20100824', 'admin', NULL, NULL)
;
INSERT INTO zdbranch(BranchInnerCode, BranchCode, ParentInnerCode, Type, OrderFlag, Name, TreeLevel, IsLeaf, Phone, Fax, Manager, Leader1, 

Leader2, Prop1, Prop2, Prop3, Prop4, Memo, AddTime, AddUser, ModifyTime, ModifyUser)
  VALUES('120000095120000825120000826120001018', '2120999123001012S', '120000095120000825120000826', '0', 1017, '塘沽分行客户一部', 4, 'Y', NULL, NULL, 'admin', 'administrator', 'admin', '04', 

'120001018', NULL, NULL, NULL, 

'20100824', 'admin', NULL, NULL)
;
INSERT INTO zdbranch(BranchInnerCode, BranchCode, ParentInnerCode, Type, OrderFlag, Name, TreeLevel, IsLeaf, Phone, Fax, Manager, Leader1, 

Leader2, Prop1, Prop2, Prop3, Prop4, Memo, AddTime, AddUser, ModifyTime, ModifyUser)
  VALUES('120000095120000102120000103120001019', '2120999102001015H', '120000095120000102120000103', '0', 1018, '南开支行客户一部', 5, 'Y', NULL, NULL, 'admin', 'administrator', 'admin', '04', 

'120001019', NULL, NULL, NULL, 

'20100824', 'admin', NULL, NULL)
;
INSERT INTO zdbranch(BranchInnerCode, BranchCode, ParentInnerCode, Type, OrderFlag, Name, TreeLevel, IsLeaf, Phone, Fax, Manager, Leader1, 

Leader2, Prop1, Prop2, Prop3, Prop4, Memo, AddTime, AddUser, ModifyTime, ModifyUser)
  VALUES('120000095120000925120000926120001020', '2120999127001011T', '120000095120000925120000926', '0', 1019, '港保税区分行客户一部', 4, 'Y', NULL, NULL, 'admin', 'administrator', 'admin', 

'04', '120001020', NULL, NULL, NULL, 

'20100824', 'admin', NULL, NULL)
;
INSERT INTO zdbranch(BranchInnerCode, BranchCode, ParentInnerCode, Type, OrderFlag, Name, TreeLevel, IsLeaf, Phone, Fax, Manager, Leader1, 

Leader2, Prop1, Prop2, Prop3, Prop4, Memo, AddTime, AddUser, ModifyTime, ModifyUser)
  VALUES('120000095120000925120000926120001021', '2120999127001012R', '120000095120000925120000926', '0', 1020, '港保税区分行客户三部', 4, 'Y', NULL, NULL, 'admin', 'administrator', 'admin', 

'04', '120001021', NULL, NULL, NULL, 

'20100824', 'admin', NULL, NULL)
;
INSERT INTO zdbranch(BranchInnerCode, BranchCode, ParentInnerCode, Type, OrderFlag, Name, TreeLevel, IsLeaf, Phone, Fax, Manager, Leader1, 

Leader2, Prop1, Prop2, Prop3, Prop4, Memo, AddTime, AddUser, ModifyTime, ModifyUser)
  VALUES('120000095120000925120000926120001022', '2120999127001013P', '120000095120000925120000926', '0', 1021, '港保税区分行客户四部', 4, 'Y', NULL, NULL, 'admin', 'administrator', 'admin', 

'04', '120001022', NULL, NULL, NULL, 

'20100824', 'admin', NULL, NULL)
;
INSERT INTO zdbranch(BranchInnerCode, BranchCode, ParentInnerCode, Type, OrderFlag, Name, TreeLevel, IsLeaf, Phone, Fax, Manager, Leader1, 

Leader2, Prop1, Prop2, Prop3, Prop4, Memo, AddTime, AddUser, ModifyTime, ModifyUser)
  VALUES('120000095120000329120000330120001023', '2120999109001016L', '120000095120000329120000330', '0', 1022, '世贸支行客户二部', 4, 'Y', NULL, NULL, 'admin', 'administrator', 'admin', '04', 

'120001023', NULL, NULL, NULL, 

'20100824', 'admin', NULL, NULL)
;
INSERT INTO zdbranch(BranchInnerCode, BranchCode, ParentInnerCode, Type, OrderFlag, Name, TreeLevel, IsLeaf, Phone, Fax, Manager, Leader1, 

Leader2, Prop1, Prop2, Prop3, Prop4, Memo, AddTime, AddUser, ModifyTime, ModifyUser)
  VALUES('120000095120000329120000330120001024', '2120999109001017J', '120000095120000329120000330', '0', 1023, '世贸支行客户三部', 4, 'Y', NULL, NULL, 'admin', 'administrator', 'admin', '04', 

'120001024', NULL, NULL, NULL, 

'20100824', 'admin', NULL, NULL)
;
INSERT INTO zdbranch(BranchInnerCode, BranchCode, ParentInnerCode, Type, OrderFlag, Name, TreeLevel, IsLeaf, Phone, Fax, Manager, Leader1, 

Leader2, Prop1, Prop2, Prop3, Prop4, Memo, AddTime, AddUser, ModifyTime, ModifyUser)
  VALUES('120000095120000301120000302120001025', '21209991080010142', '120000095120000301120000302', '0', 1024, '新技术产业园区支行客户二部', 4, 'Y', NULL, NULL, 'admin', 'administrator', 

'admin', '04', '120001025', NULL, NULL, 

NULL, '20100824', 'admin', NULL, NULL)
;
INSERT INTO zdbranch(BranchInnerCode, BranchCode, ParentInnerCode, Type, OrderFlag, Name, TreeLevel, IsLeaf, Phone, Fax, Manager, Leader1, 

Leader2, Prop1, Prop2, Prop3, Prop4, Memo, AddTime, AddUser, ModifyTime, ModifyUser)
  VALUES('120000095120000301120000302120001026', '2120999108001015V', '120000095120000301120000302', '0', 1025, '新技术产业园区支行客户三部', 4, 'Y', NULL, NULL, 'admin', 'administrator', 

'admin', '04', '120001026', NULL, NULL, 

NULL, '20100824', 'admin', NULL, NULL)
;
INSERT INTO zdbranch(BranchInnerCode, BranchCode, ParentInnerCode, Type, OrderFlag, Name, TreeLevel, IsLeaf, Phone, Fax, Manager, Leader1, 

Leader2, Prop1, Prop2, Prop3, Prop4, Memo, AddTime, AddUser, ModifyTime, ModifyUser)
  VALUES('120000095120000825120000826120001027', '2120999123001013Q', '120000095120000825120000826', '0', 1026, '塘沽区分行客户二部', 4, 'Y', NULL, NULL, 'admin', 'administrator', 'admin', 

'04', '120001027', NULL, NULL, NULL, 

'20100824', 'admin', NULL, NULL)
;
INSERT INTO zdbranch(BranchInnerCode, BranchCode, ParentInnerCode, Type, OrderFlag, Name, TreeLevel, IsLeaf, Phone, Fax, Manager, Leader1, 

Leader2, Prop1, Prop2, Prop3, Prop4, Memo, AddTime, AddUser, ModifyTime, ModifyUser)
  VALUES('120000095120000805120000790120001028', '21209991210010165', '120000095120000805120000790', '0', 1027, '宁河县支行客户部', 4, 'Y', NULL, NULL, 'admin', 'administrator', 'admin', '04', 

'120001028', NULL, NULL, NULL, 

'20100824', 'admin', NULL, NULL)
;
INSERT INTO zdbranch(BranchInnerCode, BranchCode, ParentInnerCode, Type, OrderFlag, Name, TreeLevel, IsLeaf, Phone, Fax, Manager, Leader1, 

Leader2, Prop1, Prop2, Prop3, Prop4, Memo, AddTime, AddUser, ModifyTime, ModifyUser)
  VALUES('120000095120000637120000638120001029', '2120999118001017I', '120000095120000637120000638', '0', 1028, '蓟县客户部', 4, 'Y', NULL, NULL, 'admin', 'administrator', 'admin', '04', '120001029', 

NULL, NULL, NULL, 

'20100824', 'admin', NULL, NULL)
;
INSERT INTO zdbranch(BranchInnerCode, BranchCode, ParentInnerCode, Type, OrderFlag, Name, TreeLevel, IsLeaf, Phone, Fax, Manager, Leader1, 

Leader2, Prop1, Prop2, Prop3, Prop4, Memo, AddTime, AddUser, ModifyTime, ModifyUser)
  VALUES('120000095120000825120000826120001030', '2120999123001014O', '120000095120000825120000826', '0', 1029, '塘沽区分行客户三部', 4, 'Y', NULL, NULL, 'admin', 'administrator', 'admin', 

'04', '120001030', NULL, NULL, NULL, 

'20100824', 'admin', NULL, NULL)
;
INSERT INTO zdbranch(BranchInnerCode, BranchCode, ParentInnerCode, Type, OrderFlag, Name, TreeLevel, IsLeaf, Phone, Fax, Manager, Leader1, 

Leader2, Prop1, Prop2, Prop3, Prop4, Memo, AddTime, AddUser, ModifyTime, ModifyUser)
  VALUES('120000095120000825120000826120001031', '2120999123001015M', '120000095120000825120000826', '0', 1030, '塘沽区分行客户四部', 4, 'Y', NULL, NULL, 'admin', 'administrator', 'admin', 

'04', '120001031', NULL, NULL, NULL, 

'20100824', 'admin', NULL, NULL)
;
INSERT INTO zdbranch(BranchInnerCode, BranchCode, ParentInnerCode, Type, OrderFlag, Name, TreeLevel, IsLeaf, Phone, Fax, Manager, Leader1, 

Leader2, Prop1, Prop2, Prop3, Prop4, Memo, AddTime, AddUser, ModifyTime, ModifyUser)
  VALUES('120000095120000825120000826120001032', '2120999123001016K', '120000095120000825120000826', '0', 1031, '塘沽区分行金库管理部', 4, 'Y', NULL, NULL, 'admin', 'administrator', 'admin', 

'04', '120001032', NULL, NULL, 

NULL, '20100824', 'admin', NULL, NULL)
;
INSERT INTO zdbranch(BranchInnerCode, BranchCode, ParentInnerCode, Type, OrderFlag, Name, TreeLevel, IsLeaf, Phone, Fax, Manager, Leader1, 

Leader2, Prop1, Prop2, Prop3, Prop4, Memo, AddTime, AddUser, ModifyTime, ModifyUser)
  VALUES('120000095120000135120000136120001033', '2120999103001014B', '120000095120000135120000136', '0', 1032, '和平支行客户一部', 4, 'Y', NULL, NULL, 'admin', 'administrator', 'admin', '04', 

'120001033', NULL, NULL, NULL, 

'20100824', 'admin', NULL, NULL)
;
INSERT INTO zdbranch(BranchInnerCode, BranchCode, ParentInnerCode, Type, OrderFlag, Name, TreeLevel, IsLeaf, Phone, Fax, Manager, Leader1, 

Leader2, Prop1, Prop2, Prop3, Prop4, Memo, AddTime, AddUser, ModifyTime, ModifyUser)
  VALUES('120000095120000135120000136120001034', '21209991030010159', '120000095120000135120000136', '0', 1033, '和平支行客户二部', 4, 'Y', NULL, NULL, 'admin', 'administrator', 'admin', '04', 

'120001034', NULL, NULL, NULL, 

'20100824', 'admin', NULL, NULL)
;
INSERT INTO zdbranch(BranchInnerCode, BranchCode, ParentInnerCode, Type, OrderFlag, Name, TreeLevel, IsLeaf, Phone, Fax, Manager, Leader1, 

Leader2, Prop1, Prop2, Prop3, Prop4, Memo, AddTime, AddUser, ModifyTime, ModifyUser)
  VALUES('120000095120000135120000136120001035', '21209991030010167', '120000095120000135120000136', '0', 1034, '和平支行客户三部', 4, 'Y', NULL, NULL, 'admin', 'administrator', 'admin', '04', 

'120001035', NULL, NULL, NULL, 

'20100824', 'admin', NULL, NULL)
;
INSERT INTO zdbranch(BranchInnerCode, BranchCode, ParentInnerCode, Type, OrderFlag, Name, TreeLevel, IsLeaf, Phone, Fax, Manager, Leader1, 

Leader2, Prop1, Prop2, Prop3, Prop4, Memo, AddTime, AddUser, ModifyTime, ModifyUser)
  VALUES('120000095120000135120000136120001036', '21209991030010175', '120000095120000135120000136', '0', 1035, '和平支行客户四部', 4, 'Y', NULL, NULL, 'admin', 'administrator', 'admin', '04', 

'120001036', NULL, NULL, NULL, 

'20100824', 'admin', NULL, NULL)
;
INSERT INTO zdbranch(BranchInnerCode, BranchCode, ParentInnerCode, Type, OrderFlag, Name, TreeLevel, IsLeaf, Phone, Fax, Manager, Leader1, 

Leader2, Prop1, Prop2, Prop3, Prop4, Memo, AddTime, AddUser, ModifyTime, ModifyUser)
  VALUES('120000095120000135120000136120001037', '21209991030010183', '120000095120000135120000136', '0', 1036, '和平支行客户五部', 4, 'Y', NULL, NULL, 'admin', 'administrator', 'admin', '04', 

'120001037', NULL, NULL, NULL, 

'20100824', 'admin', NULL, NULL)
;
INSERT INTO zdbranch(BranchInnerCode, BranchCode, ParentInnerCode, Type, OrderFlag, Name, TreeLevel, IsLeaf, Phone, Fax, Manager, Leader1, 

Leader2, Prop1, Prop2, Prop3, Prop4, Memo, AddTime, AddUser, ModifyTime, ModifyUser)
  VALUES('120000095120000522120000523120001038', '21209991160010262', '120000095120000522120000523', '0', 1037, '北辰支行辞退人员', 3, 'Y', NULL, NULL, 'admin', 'administrator', 'admin', '04', 

'120001038', NULL, NULL, NULL, 

'20100824', 'admin', NULL, NULL)
;
INSERT INTO zdbranch(BranchInnerCode, BranchCode, ParentInnerCode, Type, OrderFlag, Name, TreeLevel, IsLeaf, Phone, Fax, Manager, Leader1, 

Leader2, Prop1, Prop2, Prop3, Prop4, Memo, AddTime, AddUser, ModifyTime, ModifyUser)
  VALUES('120000095120000738120000739120001039', '2120999120001016D', '120000095120000738120000739', '0', 1038, '宝坻支行客户一部', 4, 'Y', NULL, NULL, 'admin', 'administrator', 'admin', '04', 

'120001039', NULL, NULL, NULL, 

'20100824', 'admin', NULL, NULL)
;
INSERT INTO zdbranch(BranchInnerCode, BranchCode, ParentInnerCode, Type, OrderFlag, Name, TreeLevel, IsLeaf, Phone, Fax, Manager, Leader1, 

Leader2, Prop1, Prop2, Prop3, Prop4, Memo, AddTime, AddUser, ModifyTime, ModifyUser)
  VALUES('120000095120000738120000739120001040', '2120999120001017B', '120000095120000738120000739', '0', 1039, '宝坻支行客户二部', 4, 'Y', NULL, NULL, 'admin', 'administrator', 'admin', '04', 

'120001040', NULL, NULL, NULL, 

'20100824', 'admin', NULL, NULL)
;
INSERT INTO zdbranch(BranchInnerCode, BranchCode, ParentInnerCode, Type, OrderFlag, Name, TreeLevel, IsLeaf, Phone, Fax, Manager, Leader1, 

Leader2, Prop1, Prop2, Prop3, Prop4, Memo, AddTime, AddUser, ModifyTime, ModifyUser)
  VALUES('120000095120000233120000235120001041', '2120999106001017C', '120000095120000233120000235', '0', 1040, '河北支行客户一部', 4, 'Y', NULL, NULL, 'admin', 'administrator', 'admin', '04', 

'120001041', NULL, NULL, NULL, 

'20100824', 'admin', NULL, NULL)
;
INSERT INTO zdbranch(BranchInnerCode, BranchCode, ParentInnerCode, Type, OrderFlag, Name, TreeLevel, IsLeaf, Phone, Fax, Manager, Leader1, 

Leader2, Prop1, Prop2, Prop3, Prop4, Memo, AddTime, AddUser, ModifyTime, ModifyUser)
  VALUES('120000095120000233120000235120001042', '2120999106001018A', '120000095120000233120000235', '0', 1041, '河北支行客户二部', 4, 'Y', NULL, NULL, 'admin', 'administrator', 'admin', '04', 

'120001042', NULL, NULL, NULL, 

'20100824', 'admin', NULL, NULL)
;
INSERT INTO zdbranch(BranchInnerCode, BranchCode, ParentInnerCode, Type, OrderFlag, Name, TreeLevel, IsLeaf, Phone, Fax, Manager, Leader1, 

Leader2, Prop1, Prop2, Prop3, Prop4, Memo, AddTime, AddUser, ModifyTime, ModifyUser)
  VALUES('120000095120000199120000200120001043', '2120999105001015O', '120000095120000199120000200', '0', 1042, '河东支行客户一部', 4, 'Y', NULL, NULL, 'admin', 'administrator', 'admin', '04', 

'120001043', NULL, NULL, NULL, 

'20100824', 'admin', NULL, NULL)
;
INSERT INTO zdbranch(BranchInnerCode, BranchCode, ParentInnerCode, Type, OrderFlag, Name, TreeLevel, IsLeaf, Phone, Fax, Manager, Leader1, 

Leader2, Prop1, Prop2, Prop3, Prop4, Memo, AddTime, AddUser, ModifyTime, ModifyUser)
  VALUES('120000095120000522120001044', '2120999116125000C', '120000095120000522', '0', 1043, '北辰支行暂时存储宜兴埠储蓄所人员', 5, 'Y', NULL, NULL, 'admin', 

'administrator', 'admin', '04', '120001044', 

NULL, NULL, NULL, '20100824', 'admin', NULL, NULL)
;
INSERT INTO zdbranch(BranchInnerCode, BranchCode, ParentInnerCode, Type, OrderFlag, Name, TreeLevel, IsLeaf, Phone, Fax, Manager, Leader1, 

Leader2, Prop1, Prop2, Prop3, Prop4, Memo, AddTime, AddUser, ModifyTime, ModifyUser)
  VALUES('120000095120000199120000200120001045', '2120999105001016M', '120000095120000199120000200', '0', 1044, '河东支行客户二部', 4, 'Y', NULL, NULL, 'admin', 'administrator', 'admin', '04', 

'120001045', NULL, NULL, NULL, 

'20100824', 'admin', NULL, NULL)
;
INSERT INTO zdbranch(BranchInnerCode, BranchCode, ParentInnerCode, Type, OrderFlag, Name, TreeLevel, IsLeaf, Phone, Fax, Manager, Leader1, 

Leader2, Prop1, Prop2, Prop3, Prop4, Memo, AddTime, AddUser, ModifyTime, ModifyUser)
  VALUES('120000095120000199120000200120001046', '2120999105001017K', '120000095120000199120000200', '0', 1045, '河东支行客户三部', 4, 'Y', NULL, NULL, 'admin', 'administrator', 'admin', '04', 

'120001046', NULL, NULL, NULL, 

'20100824', 'admin', NULL, NULL)
;
INSERT INTO zdbranch(BranchInnerCode, BranchCode, ParentInnerCode, Type, OrderFlag, Name, TreeLevel, IsLeaf, Phone, Fax, Manager, Leader1, 

Leader2, Prop1, Prop2, Prop3, Prop4, Memo, AddTime, AddUser, ModifyTime, ModifyUser)
  VALUES('120000095120000199120000200120001047', '2120999105001018I', '120000095120000199120000200', '0', 1046, '河东支行客户四部', 4, 'Y', NULL, NULL, 'admin', 'administrator', 'admin', '04', 

'120001047', NULL, NULL, NULL, 

'20100824', 'admin', NULL, NULL)
;
INSERT INTO zdbranch(BranchInnerCode, BranchCode, ParentInnerCode, Type, OrderFlag, Name, TreeLevel, IsLeaf, Phone, Fax, Manager, Leader1, 

Leader2, Prop1, Prop2, Prop3, Prop4, Memo, AddTime, AddUser, ModifyTime, ModifyUser)
  VALUES('120000095120000387120000419120001048', '2120999113001017R', '120000095120000387120000419', '0', 1047, '东丽区支行客户二部', 4, 'Y', NULL, NULL, 'admin', 'administrator', 'admin', 

'04', '120001048', NULL, NULL, NULL, 

'20100824', 'admin', NULL, NULL)
;
INSERT INTO zdbranch(BranchInnerCode, BranchCode, ParentInnerCode, Type, OrderFlag, Name, TreeLevel, IsLeaf, Phone, Fax, Manager, Leader1, 

Leader2, Prop1, Prop2, Prop3, Prop4, Memo, AddTime, AddUser, ModifyTime, ModifyUser)
  VALUES('120000095120000387120000419120001049', '2120999113001018P', '120000095120000387120000419', '0', 1048, '东丽区支行客户三部', 4, 'Y', NULL, NULL, 'admin', 'administrator', 'admin', 

'04', '120001049', NULL, NULL, NULL, 

'20100824', 'admin', NULL, NULL)
;
INSERT INTO zdbranch(BranchInnerCode, BranchCode, ParentInnerCode, Type, OrderFlag, Name, TreeLevel, IsLeaf, Phone, Fax, Manager, Leader1, 

Leader2, Prop1, Prop2, Prop3, Prop4, Memo, AddTime, AddUser, ModifyTime, ModifyUser)
  VALUES('120000095120000435120000436120001050', '2120999114001018H', '120000095120000435120000436', '0', 1049, '西青区支行客户二部', 4, 'Y', NULL, NULL, 'admin', 'administrator', 'admin', 

'04', '120001050', NULL, NULL, NULL, 

'20100824', 'admin', NULL, NULL)
;
INSERT INTO zdbranch(BranchInnerCode, BranchCode, ParentInnerCode, Type, OrderFlag, Name, TreeLevel, IsLeaf, Phone, Fax, Manager, Leader1, 

Leader2, Prop1, Prop2, Prop3, Prop4, Memo, AddTime, AddUser, ModifyTime, ModifyUser)
  VALUES('120000095120000435120000436120001051', '2120999114001019F', '120000095120000435120000436', '0', 1050, '西青区支行客户三部', 4, 'Y', NULL, NULL, 'admin', 'administrator', 'admin', 

'04', '120001051', NULL, NULL, NULL, 

'20100824', 'admin', NULL, NULL)
;
INSERT INTO zdbranch(BranchInnerCode, BranchCode, ParentInnerCode, Type, OrderFlag, Name, TreeLevel, IsLeaf, Phone, Fax, Manager, Leader1, 

Leader2, Prop1, Prop2, Prop3, Prop4, Memo, AddTime, AddUser, ModifyTime, ModifyUser)
  VALUES('120000095120000687120000688120001052', '21209991190010188', '120000095120000687120000688', '0', 1051, '静海支行客户一部', 4, 'Y', NULL, NULL, 'admin', 'administrator', 'admin', '04', 

'120001052', NULL, NULL, NULL, 

'20100824', 'admin', NULL, NULL)
;
INSERT INTO zdbranch(BranchInnerCode, BranchCode, ParentInnerCode, Type, OrderFlag, Name, TreeLevel, IsLeaf, Phone, Fax, Manager, Leader1, 

Leader2, Prop1, Prop2, Prop3, Prop4, Memo, AddTime, AddUser, ModifyTime, ModifyUser)
  VALUES('120000095120000687120000688120001053', '21209991190010196', '120000095120000687120000688', '0', 1052, '静海支行客户二部', 4, 'Y', NULL, NULL, 'admin', 'administrator', 'admin', '04', 

'120001053', NULL, NULL, NULL, 

'20100824', 'admin', NULL, NULL)
;
INSERT INTO zdbranch(BranchInnerCode, BranchCode, ParentInnerCode, Type, OrderFlag, Name, TreeLevel, IsLeaf, Phone, Fax, Manager, Leader1, 

Leader2, Prop1, Prop2, Prop3, Prop4, Memo, AddTime, AddUser, ModifyTime, ModifyUser)
  VALUES('120000095120000687120000688120001054', '2120999119001020L', '120000095120000687120000688', '0', 1053, '静海支行客户三部', 4, 'Y', NULL, NULL, 'admin', 'administrator', 'admin', '04', 

'120001054', NULL, NULL, NULL, 

'20100824', 'admin', NULL, NULL)
;
INSERT INTO zdbranch(BranchInnerCode, BranchCode, ParentInnerCode, Type, OrderFlag, Name, TreeLevel, IsLeaf, Phone, Fax, Manager, Leader1, 

Leader2, Prop1, Prop2, Prop3, Prop4, Memo, AddTime, AddUser, ModifyTime, ModifyUser)
  VALUES('120000095120000495120000481120001055', '2120999115001015B', '120000095120000495120000481', '0', 1054, '津南支行客户一部', 4, 'Y', NULL, NULL, 'admin', 'administrator', 'admin', '04', 

'120001055', NULL, NULL, NULL, 

'20100824', 'admin', NULL, NULL)
;
INSERT INTO zdbranch(BranchInnerCode, BranchCode, ParentInnerCode, Type, OrderFlag, Name, TreeLevel, IsLeaf, Phone, Fax, Manager, Leader1, 

Leader2, Prop1, Prop2, Prop3, Prop4, Memo, AddTime, AddUser, ModifyTime, ModifyUser)
  VALUES('120000095120000495120000481120001056', '2120999115001016L', '120000095120000495120000481', '0', 1055, '津南支行客户二部', 4, 'Y', NULL, NULL, 'admin', 'administrator', 'admin', '04', 

'120001056', NULL, NULL, NULL, 

'20100824', 'admin', NULL, NULL)
;
INSERT INTO zdbranch(BranchInnerCode, BranchCode, ParentInnerCode, Type, OrderFlag, Name, TreeLevel, IsLeaf, Phone, Fax, Manager, Leader1, 

Leader2, Prop1, Prop2, Prop3, Prop4, Memo, AddTime, AddUser, ModifyTime, ModifyUser)
  VALUES('120000095120000871120000872120001057', '21209991250010172', '120000095120000871120000872', '0', 1056, '大港支行客户一部', 4, 'Y', NULL, NULL, 'admin', 'administrator', 'admin', '04', 

'120001057', NULL, NULL, NULL, 

'20100824', 'admin', NULL, NULL)
;
INSERT INTO zdbranch(BranchInnerCode, BranchCode, ParentInnerCode, Type, OrderFlag, Name, TreeLevel, IsLeaf, Phone, Fax, Manager, Leader1, 

Leader2, Prop1, Prop2, Prop3, Prop4, Memo, AddTime, AddUser, ModifyTime, ModifyUser)
  VALUES('120000095120000871120000872120001058', '2120999125001018V', '120000095120000871120000872', '0', 1057, '大港支行客户二部', 4, 'Y', NULL, NULL, 'admin', 'administrator', 'admin', '04', 

'120001058', NULL, NULL, NULL, 

'20100824', 'admin', NULL, NULL)
;
INSERT INTO zdbranch(BranchInnerCode, BranchCode, ParentInnerCode, Type, OrderFlag, Name, TreeLevel, IsLeaf, Phone, Fax, Manager, Leader1, 

Leader2, Prop1, Prop2, Prop3, Prop4, Memo, AddTime, AddUser, ModifyTime, ModifyUser)
  VALUES('120000095120000871120000872120001059', '2120999125001019T', '120000095120000871120000872', '0', 1058, '大港支行客户三部', 4, 'Y', NULL, NULL, 'admin', 'administrator', 'admin', '04', 

'120001059', NULL, NULL, NULL, 

'20100824', 'admin', NULL, NULL)
;
INSERT INTO zdbranch(BranchInnerCode, BranchCode, ParentInnerCode, Type, OrderFlag, Name, TreeLevel, IsLeaf, Phone, Fax, Manager, Leader1, 

Leader2, Prop1, Prop2, Prop3, Prop4, Memo, AddTime, AddUser, ModifyTime, ModifyUser)
  VALUES('120000095120000909120000911120001060', '21209991260010116', '120000095120000909120000911', '0', 1059, '经济技术开发区分行客户一部', 4, 'Y', NULL, NULL, 'admin', 'administrator', 

'admin', '04', '120001060', NULL, NULL, 

NULL, '20100824', 'admin', NULL, NULL)
;
INSERT INTO zdbranch(BranchInnerCode, BranchCode, ParentInnerCode, Type, OrderFlag, Name, TreeLevel, IsLeaf, Phone, Fax, Manager, Leader1, 

Leader2, Prop1, Prop2, Prop3, Prop4, Memo, AddTime, AddUser, ModifyTime, ModifyUser)
  VALUES('120000095120000909120000911120001061', '21209991260010124', '120000095120000909120000911', '0', 1060, '经济技术开发区分行客户二部', 4, 'Y', NULL, NULL, 'admin', 'administrator', 

'admin', '04', '120001061', NULL, NULL, 

NULL, '20100824', 'admin', NULL, NULL)
;
INSERT INTO zdbranch(BranchInnerCode, BranchCode, ParentInnerCode, Type, OrderFlag, Name, TreeLevel, IsLeaf, Phone, Fax, Manager, Leader1, 

Leader2, Prop1, Prop2, Prop3, Prop4, Memo, AddTime, AddUser, ModifyTime, ModifyUser)
  VALUES('120000095120000909120000911120001062', '21209991260010132', '120000095120000909120000911', '0', 1061, '经济技术开发区分行客户三部', 4, 'Y', NULL, NULL, 'admin', 'administrator', 

'admin', '04', '120001062', NULL, NULL, 

NULL, '20100824', 'admin', NULL, NULL)
;
INSERT INTO zdbranch(BranchInnerCode, BranchCode, ParentInnerCode, Type, OrderFlag, Name, TreeLevel, IsLeaf, Phone, Fax, Manager, Leader1, 
Leader2, Prop1, Prop2, Prop3, Prop4, Memo, AddTime, AddUser, ModifyTime, ModifyUser)
  VALUES('120000095120000909120000911120001063', '2120999126001014V', '120000095120000909120000911', '0', 1062, '经济技术开发区分行客户四部', 4, 'Y',
   NULL, NULL, 'admin', 'administrator', 'admin', '04', '120001063', NULL, NULL, NULL, '20100824', 'admin', NULL, NULL);
INSERT INTO zdbranch(BranchInnerCode, BranchCode, ParentInnerCode, Type, OrderFlag, Name, TreeLevel, IsLeaf, Phone, Fax, Manager, Leader1, 
Leader2, Prop1, Prop2, Prop3, Prop4, Memo, AddTime, AddUser, ModifyTime, ModifyUser)
  VALUES('120000095120000102120000103120001064', '2120999102001016F', '120000095120000102120000103', '0', 1063, '南开支行客户二部', 5, 'Y',
   NULL, NULL, 'admin', 'administrator', 'admin', '04', '120001064', NULL, NULL, NULL, '20100824', 'admin', NULL, NULL);
INSERT INTO zdbranch(BranchInnerCode, BranchCode, ParentInnerCode, Type, OrderFlag, Name, TreeLevel, IsLeaf, Phone, Fax, Manager, Leader1, 
Leader2, Prop1, Prop2, Prop3, Prop4, Memo, AddTime, AddUser, ModifyTime, ModifyUser)
  VALUES('120000095120000102120000103120001065', '2120999102001017D', '120000095120000102120000103', '0', 1064, '南开支行客户三部', 5, 'Y',
   NULL, NULL, 'admin', 'administrator', 'admin', '04', '120001065', NULL, NULL, NULL, '20100824', 'admin', NULL, NULL);
   
INSERT INTO zdbranch(BranchInnerCode, BranchCode, ParentInnerCode, Type, OrderFlag, Name, TreeLevel, IsLeaf, Phone, Fax, Manager, Leader1, 
Leader2, Prop1, Prop2, Prop3, Prop4, Memo, AddTime, AddUser, ModifyTime, ModifyUser)
  VALUES('120000095120000805120000790120001066', '21209991210010173', '120000095120000805120000790', '0', 1065, '宁河县支行宁河县营业部', 4, 'Y',
   NULL, NULL, 'admin', 'administrator', 'admin', '04', '120001066', NULL, NULL, NULL, '20100824', 'admin', NULL, NULL);
   
INSERT INTO zdbranch(BranchInnerCode, BranchCode, ParentInnerCode, Type, OrderFlag, Name, TreeLevel, IsLeaf, Phone, Fax, Manager, Leader1, 
Leader2, Prop1, Prop2, Prop3, Prop4, Memo, AddTime, AddUser, ModifyTime, ModifyUser)
  VALUES('120000095120000687120000688120001067', '2120999119001021J', '120000095120000687120000688', '0', 1066, '静海支行信贷管理部', 4, 'Y', 
  NULL, NULL, 'admin', 'administrator', 'admin', '04', '120001067', NULL, NULL, NULL, '20100824', 'admin', NULL, NULL);
  
INSERT INTO zdbranch(BranchInnerCode, BranchCode, ParentInnerCode, Type, OrderFlag, Name, TreeLevel, IsLeaf, Phone, Fax, Manager, Leader1, 
Leader2, Prop1, Prop2, Prop3, Prop4, Memo, AddTime, AddUser, ModifyTime, ModifyUser)
  VALUES('120000095120000165120000166120001068', '21209991040010135', '120000095120000165120000166', '0', 1067, '河西支行支行客户二部', 4, 'Y',
   NULL, NULL, 'admin', 'administrator', 'admin', '04', '120001068', NULL, NULL, NULL, '20100824', 'admin', NULL, NULL);
   
INSERT INTO zdbranch(BranchInnerCode, BranchCode, ParentInnerCode, Type, OrderFlag, Name, TreeLevel, IsLeaf, Phone, Fax, Manager, Leader1, 
Leader2, Prop1, Prop2, Prop3, Prop4, Memo, AddTime, AddUser, ModifyTime, ModifyUser)
  VALUES('120000095120000165120000166120001069', '21209991040010143', '120000095120000165120000166', '0', 1068, '河西支行支行客户三部', 4, 'Y',
   NULL, NULL, 'admin', 'administrator', 'admin', '04', '120001069', NULL, NULL, NULL, '20100824', 'admin', NULL, NULL);

INSERT INTO zdbranch(BranchInnerCode, BranchCode, ParentInnerCode, Type, OrderFlag, Name, TreeLevel, IsLeaf, Phone, Fax, Manager, Leader1, 
Leader2, Prop1, Prop2, Prop3, Prop4, Memo, AddTime, AddUser, ModifyTime, ModifyUser)
  VALUES('120000095120000165120000166120001070', '21209991040010151', '120000095120000165120000166', '0', 1069, '河西支行支行客户四部', 4, 'Y',
   NULL, NULL, 'admin', 'administrator', 'admin', '04', '120001070', NULL, NULL, NULL, '20100824', 'admin', NULL, NULL);

INSERT INTO zdbranch(BranchInnerCode, BranchCode, ParentInnerCode, Type, OrderFlag, Name, TreeLevel, IsLeaf, Phone, Fax, Manager, Leader1, Leader2, Prop1, Prop2, Prop3, Prop4, Memo, AddTime, AddUser, ModifyTime, ModifyUser)
  VALUES('120000095120000165120000166120001071', '2120999104001016U', '120000095120000165120000166', '0', 1070, '河西支行支行营业部', 4, 'Y',
   NULL, NULL, 'admin', 'administrator', 'admin', '04', '120001071', NULL, NULL, NULL,'20100824', 'admin', NULL, NULL);

