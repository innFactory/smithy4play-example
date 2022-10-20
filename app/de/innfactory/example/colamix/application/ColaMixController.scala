package de.innfactory.example.colamix.application

import cats.data.EitherT
import de.innfactory.example.application.controller.BaseController
import de.innfactory.example.colamix.domain.interfaces.ColaMixService
import de.innfactory.play.controller.ResultStatus
import de.innfactory.play.smithy4play.AbstractBaseController
import de.innfactory.smithy4play.{AutoRouting, ContextRoute}
import play.api.Application
import play.api.mvc.ControllerComponents
import playSmithy.{BuyColaOutput, ColaMixAPIService, DelieverColaOutput, DeliverColaOutput, GetColaAmountOutput, HaggleAskingPriceOutput}

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

  override def buyCola(amount: Int): ContextRoute[BuyColaOutput] =
    Endpoint
      .execute(c => colaMixService.buyCola(amount))
      .complete

  override def haggleAskingPrice(
    price: Double,
    name: String
  ): ContextRoute[HaggleAskingPriceOutput] =
    Endpoint
      .execute(
        c => colaMixService.haggleAskingPrice(price, name)
      )
      .complete

  override def getColaAmount(): ContextRoute[GetColaAmountOutput] = Endpoint.execute(
    c => colaMixService.getColaAmount()).complete

  override def deliverCola(amount: Int): ContextRoute[DeliverColaOutput] = Endpoint.execute(
    c => colaMixService.deliverCola(amount)).complete
}
