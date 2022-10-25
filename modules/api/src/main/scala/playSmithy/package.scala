package object playSmithy {
  type BankAPIService[F[_]] = smithy4s.Monadic[BankAPIServiceGen, F]
  object BankAPIService extends smithy4s.Service.Provider[BankAPIServiceGen, BankAPIServiceOperation] {
    def apply[F[_]](implicit F: BankAPIService[F]): F.type = F
    def service: smithy4s.Service[BankAPIServiceGen, BankAPIServiceOperation] = BankAPIServiceGen
    val id: smithy4s.ShapeId = service.id
  }
  type ColaMixAPIService[F[_]] = smithy4s.Monadic[ColaMixAPIServiceGen, F]
  object ColaMixAPIService extends smithy4s.Service.Provider[ColaMixAPIServiceGen, ColaMixAPIServiceOperation] {
    def apply[F[_]](implicit F: ColaMixAPIService[F]): F.type = F
    def service: smithy4s.Service[ColaMixAPIServiceGen, ColaMixAPIServiceOperation] = ColaMixAPIServiceGen
    val id: smithy4s.ShapeId = service.id
  }

  type CreateDeleteAccountOutputList = playSmithy.CreateDeleteAccountOutputList.Type

}