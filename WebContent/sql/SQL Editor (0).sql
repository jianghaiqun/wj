
    create table `wisecodecmstest`.`zcarticle`(
        `ID` BIGINT not null,
       `SiteID` BIGINT not null,
       `CatalogID` BIGINT not null,
       `CatalogInnerCode` VARCHAR(100) not null,
       `BranchInnerCode` VARCHAR(100),
       `Title` VARCHAR(200) not null,
       `SubTitle` VARCHAR(200),
       `ShortTitle` VARCHAR(200),
       `TitleStyle` VARCHAR(100),
       `ShortTitleStyle` VARCHAR(100),
       `Author` VARCHAR(50),
       `Type` VARCHAR(2) not null,
       `Attribute` VARCHAR(100),
       `URL` VARCHAR(200),
       `RedirectURL` VARCHAR(200),
       `Status` BIGINT,
       `Summary` VARCHAR(2000),
       `Content` MEDIUMTEXT,
       `TopFlag` VARCHAR(2) not null,
       `TopDate` DATETIME,
       `TemplateFlag` VARCHAR(2) not null,
       `Template` VARCHAR(100),
       `CommentFlag` VARCHAR(2) not null,
       `CopyImageFlag` VARCHAR(2),
       `OrderFlag` BIGINT not null,
       `ReferName` VARCHAR(100),
       `ReferURL` VARCHAR(200),
       `Keyword` VARCHAR(100),
       `Tag` VARCHAR(1000),
       `RelativeArticle` VARCHAR(200),
       `RecommendArticle` VARCHAR(200),
       `ReferType` BIGINT,
       `ReferSourceID` BIGINT,
       `HitCount` BIGINT not null,
       `StickTime` BIGINT not null,
       `PublishFlag` VARCHAR(2) not null,
       `Priority` VARCHAR(2),
       `LockUser` VARCHAR(50),
       `PublishDate` DATETIME,
       `DownlineDate` DATETIME,
       `ArchiveDate` DATETIME,
       `WorkFlowID` BIGINT,
       `IssueID` BIGINT,
       `Logo` VARCHAR(100),
       `PageTitle` VARCHAR(200),
       `ClusterSource` VARCHAR(200),
       `ClusterTarget` VARCHAR(1000),
       `ReferTarget` VARCHAR(1000),
       `Prop1` VARCHAR(50),
       `Prop2` VARCHAR(50),
       `Prop3` VARCHAR(50),
       `Prop4` VARCHAR(50),
       `AddUser` VARCHAR(50) not null,
       `AddTime` DATETIME not null,
       `ModifyUser` VARCHAR(50),
       `ModifyTime` DATETIME,
        primary key (`ID`)
    );

    create unique index `PRIMARY` on `wisecodecmstest`.`zcarticle`(`ID`);
    create index `idx0_article` on `wisecodecmstest`.`zcarticle`(`CatalogID`,`Status`);
    create index `idx1_article` on `wisecodecmstest`.`zcarticle`(`OrderFlag`);
    create index `idx2_article` on `wisecodecmstest`.`zcarticle`(`PublishDate`);
    create index `idx3_article` on `wisecodecmstest`.`zcarticle`(`AddTime`);
    create index `idx4_article` on `wisecodecmstest`.`zcarticle`(`ModifyTime`);
    create index `idx5_article` on `wisecodecmstest`.`zcarticle`(`DownlineDate`);
    create index `idx6_article` on `wisecodecmstest`.`zcarticle`(`CatalogInnerCode`);
    create index `idx7_article` on `wisecodecmstest`.`zcarticle`(`SiteID`);
    create index `idx8_article` on `wisecodecmstest`.`zcarticle`(`ReferSourceID`);
    create index `idx9_article` on `wisecodecmstest`.`zcarticle`(`Keyword`);
    create index `idx10_article` on `wisecodecmstest`.`zcarticle`(`ArchiveDate`);