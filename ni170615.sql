CREATE TABLE [Administrator]
( 
	[KorisnickoIme]      varchar(100)  NOT NULL 
)
go

CREATE TABLE [Grad]
( 
	[IdG]                integer  IDENTITY ( 1,1 )  NOT NULL ,
	[Naziv]              varchar(100)  NOT NULL ,
	[PostanskiBroj]      varchar(100)  NOT NULL 
)
go

CREATE TABLE [Korisnik]
( 
	[KorisnickoIme]      varchar(100)  NOT NULL ,
	[Ime]                varchar(100)  NOT NULL ,
	[Prezime]            varchar(100)  NOT NULL ,
	[Sifra]              varchar(100)  NOT NULL ,
	[BrojPoslatihPaketa] integer  NOT NULL 
	CONSTRAINT [Nula_504437673]
		 DEFAULT  0
	CONSTRAINT [VeceJednakoNula_420158480]
		CHECK  ( BrojPoslatihPaketa >= 0 )
)
go

CREATE TABLE [Kurir]
( 
	[KorisnickoIme]      varchar(100)  NOT NULL ,
	[BrojIsporucenihPaketa] integer  NOT NULL 
	CONSTRAINT [Nula_252695035]
		 DEFAULT  0
	CONSTRAINT [VeceJednakoNula_954589863]
		CHECK  ( BrojIsporucenihPaketa >= 0 ),
	[OstvareniProfit]    decimal(10,3)  NOT NULL 
	CONSTRAINT [Nula_1617657483]
		 DEFAULT  0,
	[Status]             integer  NOT NULL 
	CONSTRAINT [Nula_430418619]
		 DEFAULT  0
	CONSTRAINT [ProveraStatusaKurira_616349329]
		CHECK  ( Status BETWEEN 0 AND 1 ),
	[RegistracioniBroj]  varchar(100)  NOT NULL 
)
go

CREATE TABLE [Opstina]
( 
	[IdO]                integer  IDENTITY ( 1,1 )  NOT NULL ,
	[Naziv]              varchar(100)  NOT NULL ,
	[Xkoord]             integer  NOT NULL ,
	[Ykoord]             integer  NOT NULL ,
	[IdG]                integer  NOT NULL 
)
go

CREATE TABLE [Paket]
( 
	[IdP]                integer  IDENTITY ( 1,1 )  NOT NULL ,
	[IdOpstOd]           integer  NOT NULL ,
	[IdOpstDo]           integer  NOT NULL ,
	[Tip]                integer  NOT NULL 
	CONSTRAINT [ProveraTipaPaketa_1986815398]
		CHECK  ( Tip BETWEEN 0 AND 2 ),
	[Tezina]             decimal(10,3)  NOT NULL 
	CONSTRAINT [VeceJednakoNula_1415041212]
		CHECK  ( Tezina >= 0 ),
	[Korisnik]           varchar(100)  NOT NULL ,
	[Kurir]              varchar(100)  NULL ,
	[Status]             integer  NOT NULL 
	CONSTRAINT [Nula_363763380]
		 DEFAULT  0
	CONSTRAINT [ProveraStatusaPaketa_800705452]
		CHECK  ( Status BETWEEN 0 AND 3 ),
	[Cena]               decimal(10,3)  NULL 
	CONSTRAINT [VeceJednakoNula_1209824108]
		CHECK  ( Cena >= 0 ),
	[VremePrihvatanja]   datetime  NULL 
)
go

CREATE TABLE [Ponuda]
( 
	[IdP]                integer  NOT NULL ,
	[KorisnickoIme]      varchar(100)  NOT NULL ,
	[ProcenatCene]       decimal(10,3)  NOT NULL 
	CONSTRAINT [VeceJednakoNula_803167884]
		CHECK  ( ProcenatCene >= 0 ),
	[IdPonuda]           integer  IDENTITY ( 1,1 )  NOT NULL 
)
go

CREATE TABLE [Vozilo]
( 
	[RegistracioniBroj]  varchar(100)  NOT NULL ,
	[TipGoriva]          integer  NOT NULL 
	CONSTRAINT [ProveraTipaGoriva_1681401909]
		CHECK  ( TipGoriva BETWEEN 0 AND 2 ),
	[Potrosnja]          decimal(10,3)  NOT NULL 
	CONSTRAINT [VeceJednakoNula_1111209901]
		CHECK  ( Potrosnja >= 0 )
)
go

CREATE TABLE [Zahtev]
( 
	[KorisnickoIme]      varchar(100)  NOT NULL ,
	[RegistracioniBroj]  varchar(100)  NOT NULL 
)
go

ALTER TABLE [Administrator]
	ADD CONSTRAINT [XPKAdministrator] PRIMARY KEY  CLUSTERED ([KorisnickoIme] ASC)
go

ALTER TABLE [Grad]
	ADD CONSTRAINT [XPKGrad] PRIMARY KEY  CLUSTERED ([IdG] ASC)
go

ALTER TABLE [Korisnik]
	ADD CONSTRAINT [XPKKorisnik] PRIMARY KEY  CLUSTERED ([KorisnickoIme] ASC)
go

ALTER TABLE [Kurir]
	ADD CONSTRAINT [XPKKurir] PRIMARY KEY  CLUSTERED ([KorisnickoIme] ASC)
go

ALTER TABLE [Opstina]
	ADD CONSTRAINT [XPKOpstina] PRIMARY KEY  CLUSTERED ([IdO] ASC)
go

ALTER TABLE [Paket]
	ADD CONSTRAINT [XPKPaket] PRIMARY KEY  CLUSTERED ([IdP] ASC)
go

ALTER TABLE [Ponuda]
	ADD CONSTRAINT [XPKPonuda] PRIMARY KEY  CLUSTERED ([IdPonuda] ASC)
go

ALTER TABLE [Vozilo]
	ADD CONSTRAINT [XPKVozilo] PRIMARY KEY  CLUSTERED ([RegistracioniBroj] ASC)
go

ALTER TABLE [Zahtev]
	ADD CONSTRAINT [XPKZahtev] PRIMARY KEY  CLUSTERED ([KorisnickoIme] ASC)
go


ALTER TABLE [Administrator]
	ADD CONSTRAINT [R_5] FOREIGN KEY ([KorisnickoIme]) REFERENCES [Korisnik]([KorisnickoIme])
		ON DELETE CASCADE
		ON UPDATE CASCADE
go


ALTER TABLE [Kurir]
	ADD CONSTRAINT [R_6] FOREIGN KEY ([KorisnickoIme]) REFERENCES [Korisnik]([KorisnickoIme])
		ON DELETE CASCADE
		ON UPDATE CASCADE
go

ALTER TABLE [Kurir]
	ADD CONSTRAINT [R_7] FOREIGN KEY ([RegistracioniBroj]) REFERENCES [Vozilo]([RegistracioniBroj])
		ON DELETE NO ACTION
		ON UPDATE NO ACTION
go


ALTER TABLE [Opstina]
	ADD CONSTRAINT [R_1] FOREIGN KEY ([IdG]) REFERENCES [Grad]([IdG])
		ON DELETE NO ACTION
		ON UPDATE NO ACTION
go


ALTER TABLE [Paket]
	ADD CONSTRAINT [R_8] FOREIGN KEY ([IdOpstOd]) REFERENCES [Opstina]([IdO])
		ON DELETE NO ACTION
		ON UPDATE NO ACTION
go

ALTER TABLE [Paket]
	ADD CONSTRAINT [R_9] FOREIGN KEY ([IdOpstDo]) REFERENCES [Opstina]([IdO])
		ON DELETE NO ACTION
		ON UPDATE NO ACTION
go

ALTER TABLE [Paket]
	ADD CONSTRAINT [R_10] FOREIGN KEY ([Korisnik]) REFERENCES [Korisnik]([KorisnickoIme])
		ON DELETE NO ACTION
		ON UPDATE NO ACTION
go

ALTER TABLE [Paket]
	ADD CONSTRAINT [R_17] FOREIGN KEY ([Kurir]) REFERENCES [Kurir]([KorisnickoIme])
		ON DELETE NO ACTION
		ON UPDATE NO ACTION
go


ALTER TABLE [Ponuda]
	ADD CONSTRAINT [R_15] FOREIGN KEY ([IdP]) REFERENCES [Paket]([IdP])
		ON DELETE NO ACTION
		ON UPDATE NO ACTION
go

ALTER TABLE [Ponuda]
	ADD CONSTRAINT [R_16] FOREIGN KEY ([KorisnickoIme]) REFERENCES [Kurir]([KorisnickoIme])
		ON DELETE NO ACTION
		ON UPDATE NO ACTION
go


ALTER TABLE [Zahtev]
	ADD CONSTRAINT [R_12] FOREIGN KEY ([KorisnickoIme]) REFERENCES [Korisnik]([KorisnickoIme])
		ON DELETE NO ACTION
		ON UPDATE NO ACTION
go

ALTER TABLE [Zahtev]
	ADD CONSTRAINT [R_14] FOREIGN KEY ([RegistracioniBroj]) REFERENCES [Vozilo]([RegistracioniBroj])
		ON DELETE NO ACTION
		ON UPDATE NO ACTION
go


USE [SAB_projekat]
GO
/****** Object:  Trigger [dbo].[TR_TransportOffer_]    Script Date: 7/12/2021 9:25:46 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
ALTER TRIGGER  [dbo].[TR_TransportOffer_]
   ON  [dbo].[Ponuda]
   AFTER DELETE
AS 
BEGIN
	declare @kursor cursor
	declare @KorisnickoIme varchar(100)
	declare @IdP int

	set @kursor = cursor for
	select KorisnickoIme, IdP
	from deleted


	open @kursor

	fetch from @kursor
	into @KorisnickoIme, @IdP

	while @@FETCH_STATUS = 0
	begin
		delete from ponuda where IdP = @IdP

		fetch from @kursor
		into @KorisnickoIme, @IdP
	end
END

USE [SAB_projekat]
GO
/****** Object:  StoredProcedure [dbo].[spOdobriZahtev]    Script Date: 7/12/2021 9:26:44 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO

ALTER PROCEDURE [dbo].[spOdobriZahtev]
	@KorisnickoIme varchar(100), 
	@Indikator int output
AS
BEGIN
	declare @regBr varchar(100)
	declare @br int

	set @regBr = coalesce((select RegistracioniBroj from Zahtev where KorisnickoIme = @KorisnickoIme), '0')

	if (@regBr = '0') begin
		set @indikator = 0
	end
	else begin
		set @br = (select count(*) from Kurir where RegistracioniBroj = @regBr)

		if(@br = 0 ) begin
			insert into Kurir(KorisnickoIme, RegistracioniBroj, Status) values (@KorisnickoIme, @regBr, 0)
			delete from Zahtev where RegistracioniBroj = @regBr
			set @Indikator = 1
		end
		else begin 
			set @Indikator = 0

		end
	end
	
END