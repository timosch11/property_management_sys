--CREATE DB
-- ****************** SqlDBM: Microsoft SQL Server ******************
-- ******************************************************************

-- ************************************** [dbo].[ss2101_Renter]
CREATE TABLE [dbo].[ss2101_Renter]
(
 [RenterID]   int IDENTITY (10000, 10) NOT NULL ,
 [rLastName]  varchar(50) NOT NULL ,
 [rFirstName] varchar(50) NOT NULL ,
 [rGender]    char(1) NOT NULL ,


 CONSTRAINT [PK_g3wsrenter] PRIMARY KEY CLUSTERED ([RenterID] ASC),
 CONSTRAINT [check_83] CHECK ( [rGender] = 'W' or [rGender] = 'M' or [rGender] = 'D' )
);
GO
-- ************************************** [dbo].[ss2101_RawData]
CREATE TABLE [dbo].[ss2101_RawData]
(
 [PostingDate]       date NOT NULL ,
 [PaymentReason]     varchar(200) NOT NULL ,
 [Amount]            money NOT NULL ,
 [DebitorCreditorID] varchar(200) NOT NULL ,
 [ValueDate]         date NOT NULL ,
 [PostingText]       varchar(200) NOT NULL ,


 CONSTRAINT [PK_g3ssacountmovement_copy] PRIMARY KEY CLUSTERED ([PostingDate] ASC, [PaymentReason] ASC, [Amount] ASC, [DebitorCreditorID] ASC, [ValueDate] ASC, [PostingText] ASC)
);
GO
-- ************************************** [dbo].[ss2101_DimPLZ]
CREATE TABLE [dbo].[ss2101_DimPLZ]
(
 [City]     varchar(50) NOT NULL ,
 [Postcode] int NOT NULL ,


 CONSTRAINT [PK_ss2101_dimplz] PRIMARY KEY CLUSTERED ([Postcode] ASC)
);
GO
-- ************************************** [dbo].[ss2101_AcountMovement]
CREATE TABLE [dbo].[ss2101_AcountMovement]
(
 [AccountMovementID] int IDENTITY (10003, 10) NOT NULL ,
 [PostingDate]       date NOT NULL ,
 [PaymentReason]     varchar(200) NOT NULL ,
 [Amount]            money NOT NULL ,
 [DebitorCreditorID] varchar(200) NOT NULL ,
 [PostingText]       varchar(200) NOT NULL ,
 [IsInTransaction]   char(1) NOT NULL DEFAULT 'N',


 CONSTRAINT [PK_g3ssacountmovement] PRIMARY KEY CLUSTERED ([AccountMovementID] ASC)
);
GO
INSERT INTO ss2101_AcountMovement
VALUES
	('1900-01-01', 'Dummy für monatliche Sollstellung der Miete', 0.00, '', '', 'J'),
	('1900-01-01', 'Dummy für monatliche Sollstellung der Nebenkosten', 0.00, '', '', 'J');
-- ************************************** [dbo].[ss2101_Transaction]
CREATE TABLE [dbo].[ss2101_Transaction]
(
 [AccountMovementID] int NOT NULL ,
 [RenterID]          int NOT NULL ,
 [PostingDate]       date NOT NULL ,
 [Payment Reason]    varchar(200) NOT NULL ,
 [TransactionAmount] money NOT NULL ,


 CONSTRAINT [PK_g3wstransaction] PRIMARY KEY CLUSTERED ([AccountMovementID] ASC, [RenterID] ASC, [PostingDate] ASC),
 CONSTRAINT [FK_59000] FOREIGN KEY ([AccountMovementID])  REFERENCES [dbo].[ss2101_AcountMovement]([AccountMovementID]),
 CONSTRAINT [FK_62000] FOREIGN KEY ([RenterID])  REFERENCES [dbo].[ss2101_Renter]([RenterID])
);
GO


CREATE NONCLUSTERED INDEX [ss2101_Index2] ON [dbo].[ss2101_Transaction] 
 (
  [RenterID] ASC
 )

GO
-- ************************************** [dbo].[ss2101_House]
CREATE TABLE [dbo].[ss2101_House]
(
 [Housenumber] int IDENTITY (10004, 10) NOT NULL ,
 [Street]      varchar(50) NOT NULL ,
 [Postcode]    int NOT NULL ,
 [HNumber]     int NOT NULL ,


 CONSTRAINT [PK_ss2101_housenumber] PRIMARY KEY CLUSTERED ([Housenumber] ASC),
 CONSTRAINT [FK_117000] FOREIGN KEY ([Postcode])  REFERENCES [dbo].[ss2101_DimPLZ]([Postcode])
);
GO


CREATE NONCLUSTERED INDEX [fkIdx_118] ON [dbo].[ss2101_House] 
 (
  [Postcode] ASC
 )

GO
-- ************************************** [dbo].[ss2101_Appartment]
CREATE TABLE [dbo].[ss2101_Appartment]
(
 [AppartmentID]     int IDENTITY (10001, 10) NOT NULL ,
 [MaxRenter]        int NOT NULL ,
 [Size]             float NOT NULL ,
 [AppartmentNumber] int NOT NULL ,
 [NumberRooms]      int NOT NULL ,
 [SecurityDeposit]  money NOT NULL ,
 [Housenumber]      int NOT NULL ,


 CONSTRAINT [PK_g3ssappartment] PRIMARY KEY CLUSTERED ([AppartmentID] ASC),
 CONSTRAINT [FK_120000] FOREIGN KEY ([Housenumber])  REFERENCES [dbo].[ss2101_House]([Housenumber])
);
GO


CREATE NONCLUSTERED INDEX [fkIdx_121] ON [dbo].[ss2101_Appartment] 
 (
  [Housenumber] ASC
 )

GO
-- ************************************** [dbo].[ss2101_LeaseContract]
CREATE TABLE [dbo].[ss2101_LeaseContract]
(
 [ContractID]      int IDENTITY (10002, 10) NOT NULL ,
 [LeaseAmount]     money NOT NULL ,
 [DateOfSignature] date NOT NULL ,
 [AdditionalCosts] money NOT NULL ,
 [NumberOfPersons] int NOT NULL ,
 [RenterID]        int NOT NULL ,
 [AppartmentID]    int NOT NULL ,
 [Active]          char(1) NOT NULL ,


 CONSTRAINT [PK_g3wsleasecontract] PRIMARY KEY CLUSTERED ([ContractID] ASC),
 CONSTRAINT [FK_65000] FOREIGN KEY ([RenterID])  REFERENCES [dbo].[ss2101_Renter]([RenterID]),
 CONSTRAINT [FK_68000] FOREIGN KEY ([AppartmentID])  REFERENCES [dbo].[ss2101_Appartment]([AppartmentID]),
 CONSTRAINT [check_99] CHECK ( [Active] = 'J' or [Active] = 'N' )
);
GO


CREATE NONCLUSTERED INDEX [ss2101_Index3] ON [dbo].[ss2101_LeaseContract] 
 (
  [RenterID] ASC
 )

GO

CREATE NONCLUSTERED INDEX [ss2101_Index4] ON [dbo].[ss2101_LeaseContract] 
 (
  [AppartmentID] ASC
 )

GO

-- TRIGGER
CREATE TRIGGER ss2101_CopyRawDataToAccountMovement
ON ss2101_RawData
AFTER INSERT 
AS 
BEGIN 
    INSERT INTO  ss2101_AcountMovement 
    Select rd.PostingDate, rd.PaymentReason, rd.Amount, rd.DebitorCreditorID, rd.PostingText, 'N' 
    FROM inserted AS rd 
END


CREATE TRIGGER ss2101_ExecStoredProcedure_Ss2101_transactionassignmentprocedure
ON ss2101_AcountMovement
AFTER INSERT 
AS 
BEGIN 
    EXEC Ss2101_transactionassignmentprocedure
END

-- STORED PROCEDURE monthly rent
CREATE PROCEDURE ss2101_rentToTransactionMonthly
AS
	SET NOCOUNT ON;
	
	DECLARE
		@RenterID int,
		@LeaseAmount money,
		@AdditionalCosts money;

	DECLARE ss2101_activeLeaseContractCursor CURSOR FOR
	SELECT
		RenterId, LeaseAmount, AdditionalCosts 
	FROM ss2101_LeaseContract
	WHERE Active='J';
	
	OPEN ss2101_activeLeaseContractCursor
	FETCH NEXT FROM ss2101_activeLeaseContractCursor
	INTO @RenterID, @LeaseAmount, @AdditionalCosts;
	
	WHILE @@FETCH_STATUS = 0
	BEGIN
	
		INSERT INTO ss2101_Transaction (AccountMovementID, PostingDate, [Payment Reason], RenterID, TransactionAmount)
		VALUES (10003, GETDATE(),'Miete', @RenterID, -@LeaseAmount),
				(10013, GETDATE(), 'Betriebskosten', @RenterID, -@AdditionalCosts)
	
		FETCH NEXT FROM ss2101_activeLeaseContractCursor
		INTO @RenterID, @LeaseAmount, @AdditionalCosts
	END
	CLOSE ss2101_activeLeaseContractCursor;
	DEALLOCATE ss2101_activeLeaseContractCursor;

--Stored Procedure Miete zuordnen
CREATE PROCEDURE Ss2101_transactionassignmentprocedure
AS
    SET nocount ON;

    DECLARE @PostingDate       DATE,
            @PaymentReason     VARCHAR(200),
            @Amount            MONEY,
            @DebitorCreditorID VARCHAR(200),
            @PostingText       VARCHAR(200),
            @RenterID          INT,
            @AmountSplitted      MONEY,
			@MieterNr   INT,
			@HouseNr Int,
			@AnzahlMieterProHouse int,
            @AccountMovementID INT,
			@Nebenk money,
			@AnzahlMieterProHausAufsummiert int;
    DECLARE ss2101_transactionassignemtcursor CURSOR FOR
      SELECT AccountMovementID, PostingDate, PaymentReason, Amount, DebitorCreditorID, PostingText
      FROM   ss2101_acountmovement;

    OPEN ss2101_transactionassignemtcursor

    FETCH next FROM ss2101_transactionassignemtcursor INTO @AccountMovementID,
    @PostingDate, @PaymentReason, @Amount, @DebitorCreditorID, @PostingText;

    WHILE @@FETCH_STATUS = 0
     BEGIN 
      IF ( 1 IN (SELECT RIGHT(value, 1)
                 FROM   String_split(@PaymentReason, ' ')
                 WHERE  Isnumeric(value) = 1) )
          OR ( 0 IN (SELECT RIGHT(value, 1)
                     FROM   String_split(@PaymentReason, ' ')
                     WHERE  Isnumeric(value) = 1) )
          OR ( 2 IN (SELECT RIGHT(value, 1)
                     FROM   String_split(@PaymentReason, ' ')
                     WHERE  Isnumeric(value) = 1) )
          OR ( (SELECT value
                FROM   String_split(@PaymentReason, ' ')
                WHERE  lower(value) IN (SELECT lower(rlastname)
                                 FROM   dbo.ss2101_renter)
                        OR lower(value) IN (SELECT lower(rfirstname)
                                     FROM   dbo.ss2101_renter)) IS NOT NULL )
            BEGIN
            
			
            SET @RenterID = (Select distinct re.RenterID from dbo.ss2101_Renter as re
            inner join dbo.ss2101_LeaseContract as lc on lc.RenterID = re.RenterID
            inner join dbo.ss2101_Appartment as ap on ap.AppartmentID = lc.AppartmentID
            where lower(re.rLastName) in ( Select lower(value) from STRING_SPLIT(@PaymentReason , ' '))
            or lower(re.rFirstName) in ( Select lower(value) from STRING_SPLIT(@PaymentReason , ' '))
            or re.RenterID in ( Select value from STRING_SPLIT(@PaymentReason , ' ') where ISNUMERIC(value) = 1 and right(value,1) = 0)
            or lc.ContractID in ( Select value from STRING_SPLIT(@PaymentReason , ' ') where ISNUMERIC(value) = 1 and right(value,1) = 2)
            or ap.AppartmentID in ( Select value from STRING_SPLIT(@PaymentReason , ' ') where ISNUMERIC(value) = 1 and right(value,1) = 1))
			
							
   
           INSERT INTO dbo.ss2101_transaction
            VALUES     ( @AccountMovementID,
                         @RenterID,
                         @PostingDate,
                         'Miete',
                         @Amount)

            
			END

			ELSE if ( 4 IN (SELECT RIGHT(value, 1)
                 FROM   String_split(@PaymentReason, ' ')
                 WHERE  Isnumeric(value) = 1) )
				 and ( 'enbw' IN (SELECT lower(value)
                FROM   String_split(@DebitorCreditorID, ' ')) or
				( 'strom' IN (SELECT lower(value)
                FROM   String_split(@PaymentReason, ' '))))
            BEGIN
			SET @HouseNr = (SELECT value
                                        FROM   String_split(@PaymentReason, ' ')
                                        WHERE  Isnumeric(value) = 1
										and value in (Select distinct Housenumber
										from ss2101_House))

			SET @AnzahlMieterProHouse = (Select count(*)
                                        from dbo.ss2101_Appartment as ap
										inner join dbo.ss2101_LeaseContract as lc 
										on lc.AppartmentID = ap.AppartmentID
                                        where Housenumber = @HouseNr)
             
			SET @AnzahlMieterProHausAufsummiert = (Select sum(NumberofPersons) 
			                                       from dbo.ss2101_LeaseContract as lc
			                                       inner join dbo.ss2101_Appartment ap on lc.AppartmentID = ap.appartmentID
												   where Housenumber = @HouseNr)

            SET @AmountSplitted = (@Amount/@AnzahlMieterProHausAufsummiert)
			
			 DECLARE ss2101_RenterCursor1 CURSOR FOR
             SELECT RenterID
             FROM   ss2101_LeaseContract as lc
			 inner join ss2101_Appartment as ap on ap.AppartmentID = lc.AppartmentID
			 where ap.AppartmentID in ( Select AppartmentID from ss2101_Appartment
			 where Housenumber = @HouseNr);

             OPEN ss2101_RenterCursor1

             FETCH next FROM ss2101_RenterCursor1 INTO @RenterID;

			 WHILE @@FETCH_STATUS = 0
			 BEGIN
			 SET @Nebenk = (Select top 1 NumberOfPersons
			                from dbo.ss2101_LeaseContract
							where RenterID = @RenterID and Active = 'J'
							order by DateOfSignature desc)
			 INSERT INTO dbo.ss2101_Transaction values ( @AccountMovementID,
                         @RenterID,
                         @PostingDate,
                         'Strom',
                         (@AmountSplitted*@Nebenk))
            FETCH next FROM ss2101_RenterCursor1 INTO @RenterID;
			END
			CLOSE ss2101_RenterCursor1;
			DEALLOCATE ss2101_RenterCursor1;
			END

			ELSE if ( 4 IN (SELECT RIGHT(value, 1)
                 FROM   String_split(@PaymentReason, ' ')
                 WHERE  Isnumeric(value) = 1) )
				 and ( 'wasser' IN (SELECT lower(value)
                 FROM   String_split(@PaymentReason, ' '))
				 or  ('gas' IN (SELECT lower(value)
                 FROM   String_split(@PaymentReason, ' '))))
            BEGIN
			
			SET @HouseNr = (SELECT value
                                        FROM   String_split(@PaymentReason, ' ')
                                        WHERE  Isnumeric(value) = 1
										and value in (Select distinct Housenumber
										from ss2101_House))

			SET @AnzahlMieterProHouse = (Select count(*)
                                        from dbo.ss2101_Appartment as ap
										inner join dbo.ss2101_LeaseContract as lc 
										on lc.AppartmentID = ap.AppartmentID
                                        where Housenumber = @HouseNr)
             
			SET @AnzahlMieterProHausAufsummiert = (Select sum(NumberofPersons) 
			                                       from dbo.ss2101_LeaseContract as lc
			                                       inner join dbo.ss2101_Appartment ap on lc.AppartmentID = ap.appartmentID
												   where Housenumber = @HouseNr)

            SET @AmountSplitted = (@Amount/@AnzahlMieterProHausAufsummiert)
			
			 DECLARE ss2101_RenterCursor2 CURSOR FOR
             SELECT RenterID
             FROM   ss2101_LeaseContract as lc
			 inner join ss2101_Appartment as ap on ap.AppartmentID = lc.AppartmentID
			 where ap.AppartmentID in ( Select AppartmentID from ss2101_Appartment
			 where Housenumber = @HouseNr);

             OPEN ss2101_RenterCursor2

             FETCH next FROM ss2101_RenterCursor2 INTO @RenterID;

			 WHILE @@FETCH_STATUS = 0
			 BEGIN
			 SET @Nebenk = (Select top 1 NumberOfPersons
			                from dbo.ss2101_LeaseContract
							where RenterID = @RenterID and Active = 'J'
							order by DateOfSignature desc)
			 INSERT INTO dbo.ss2101_Transaction values ( @AccountMovementID,
                         @RenterID,
                         @PostingDate,
                         'Wasser/Gas',
                         (@AmountSplitted*@Nebenk))
            FETCH next FROM ss2101_RenterCursor2 INTO @RenterID;
			END
			CLOSE ss2101_RenterCursor2;
			DEALLOCATE ss2101_RenterCursor2;
			END
			
			

			ELSE WAITFOR DELAY '00:00:00'
		
			FETCH next FROM ss2101_transactionassignemtcursor INTO
            @AccountMovementID,
            @PostingDate, @PaymentReason, @Amount, @DebitorCreditorID,
            @PostingText;

			
            END
    
    CLOSE ss2101_transactionassignemtcursor;

    DEALLOCATE ss2101_transactionassignemtcursor; 
	
--Stored Procedure calculation open positions	
CREATE PROCEDURE ss2101_calculateOpenPositionByRenterId
AS
BEGIN
	
	SELECT t.RenterID, r.rLastName, r.rFirstName, sum(t.TransactionAmount) AS Bilanz
	FROM [Infosys].[dbo].[ss2101_Transaction] AS t
	JOIN [Infosys].[dbo].[ss2101_Renter] AS r ON r.RenterID = t.RenterID
	GROUP BY t.RenterID, r.rLastName, r.rFirstName

END

-- Renter View for Operating Cost Statement
CREATE VIEW ss2101_RenterView AS
SELECT
	HouseTotalAmount.[Year],
	TotalAmount,
	HouseTotalAmount.[Payment Reason],
	HouseTotalAmount.Housenumber,
	RenterId,
	Aufgeteilt
FROM
	(
		SELECT
			YEAR(t.postingDate) AS [Year],
			SUM(transactionAmount) AS TotalAmount,
			[Payment Reason],
			a.Housenumber AS Housenumber
		FROM ss2101_transaction AS t
		JOIN ss2101_LeaseContract AS l ON t.renterId = l.renterId
		JOIN ss2101_Appartment AS a ON l.AppartmentID = a.AppartmentID
		WHERE transactionAmount < 0
		GROUP BY YEAR(postingDate), [Payment Reason], a.Housenumber
	) AS HouseTotalAmount
JOIN (
	SELECT
		YEAR(t.postingDate) AS [Year],
		SUM(transactionAmount) AS Aufgeteilt,
		[Payment Reason],
		t.renterId AS RenterId,
		a.Housenumber
	FROM ss2101_transaction AS t
	JOIN ss2101_LeaseContract AS l ON t.renterId = l.renterId
	JOIN ss2101_Appartment AS a ON l.AppartmentID = a.AppartmentID
	WHERE transactionAmount < 0
	GROUP BY YEAR(t.postingDate), [Payment Reason], t.renterID, a.Housenumber
) AS RenterAmount
ON
	HouseTotalAmount.[Year] = RenterAmount.[Year]
	AND HouseTotalAmount.Housenumber = RenterAmount.Housenumber
	AND HouseTotalAmount.[Payment Reason] = RenterAmount.[Payment Reason]