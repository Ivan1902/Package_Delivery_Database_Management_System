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