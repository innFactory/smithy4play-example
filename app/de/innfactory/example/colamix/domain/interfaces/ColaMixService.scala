package de.innfactory.example.colamix.domain.interfaces

import cats.data.EitherT
import com.google.inject.ImplementedBy
import de.innfactory.example.colamix.domain.services.ColaMixServiceImpl
import de.innfactory.play.controller.ResultStatus
import playSmithy.{BuyColaOutput, DeliverColaOutput, GetColaAmountOutput, HaggleAskingPriceOutput}

import scala.concurrent.Future

@ImplementedBy(classOf[ColaMixServiceImpl])
trait ColaMixService {

  def buyCola(amount: Int): EitherT[Future, ResultStatus, BuyColaOutput]

  def haggleAskingPrice(
    price: Double,
    name: String
  ): EitherT[Future, ResultStatus, HaggleAskingPriceOutput]

  def getColaAmount(): EitherT[Future, ResultStatus, GetColaAmountOutput]

  def deliverCola(amount: Int): EitherT[Future, ResultStatus, DeliverColaOutput]

}
