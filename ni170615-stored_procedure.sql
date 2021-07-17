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