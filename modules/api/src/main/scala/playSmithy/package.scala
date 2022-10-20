package object playSmithy {
  type ColaMixAPIService[F[_]] = smithy4s.Monadic[ColaMixAPIServiceGen, F]
  object ColaMixAPIService extends smithy4s.Service.Provider[ColaMixAPIServiceGen, ColaMixAPIServiceOperation] {
    def apply[F[_]](implicit F: ColaMixAPIService[F]): F.type = F
    def service: smithy4s.Service[ColaMixAPIServiceGen, ColaMixAPIServiceOperation] = ColaMixAPIServiceGen
    val id: smithy4s.ShapeId = service.id
  }


}