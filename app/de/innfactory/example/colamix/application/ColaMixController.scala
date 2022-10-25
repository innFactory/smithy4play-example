package de.innfactory.example.colamix.application

import cats.data.EitherT
import de.innfactory.example.application.controller.BaseController
import de.innfactory.example.colamix.domain.interfaces.ColaMixService
import de.innfactory.play.controller.ResultStatus
import de.innfactory.play.smithy4play.AbstractBaseController
import de.innfactory.smithy4play.{AutoRouting, ContextRoute}
import play.api.Application
import play.api.mvc.ControllerComponents
import playSmithy.{BuyColaOutput, ColaMixAPIService, DeliverColaOutput,  GetColaMixInfoOutput}

import javax.inject.{Inject, Singleton}
import scala.concurrent.{ExecutionContext, Future}
@AutoRouting
@Singleton
class ColaMixController @Inject()(colaMixService: ColaMixService)(
  implicit ec: ExecutionContext,
  cc: ControllerComponents,
  app: Application
) extends BaseController
    with ColaMixAPIService[ContextRoute] {


  override def deliverCola(amount: Int): ContextRoute[DeliverColaOutput] = Endpoint.execute(
    c => colaMixService.deliverCola(amount)).complete

  override def buyCola(amount: Int, bankAccountName: String): ContextRoute[BuyColaOutput] =
    Endpoint
      .execute(c => colaMixService.buyCola(amount, bankAccountName: String))
      .complete

  override def getColaMixInfo(): ContextRoute[GetColaMixInfoOutput] = Endpoint.execute(
    c => colaMixService.getColaMixInfo).complete
}
