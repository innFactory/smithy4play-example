package de.innfactory.example.colamix.domain.interfaces

import cats.data.EitherT
import com.google.inject.ImplementedBy
import de.innfactory.example.colamix.domain.services.ColaMixServiceImpl
import de.innfactory.play.controller.ResultStatus
import de.innfactory.smithy4play.ContextRoute
import playSmithy.{BuyColaOutput, DeliverColaOutput, GetColaMixInfoOutput}

import scala.concurrent.Future

@ImplementedBy(classOf[ColaMixServiceImpl])
trait ColaMixService {

  def buyCola(amount: Int, bankAccountName: String): EitherT[Future, ResultStatus, BuyColaOutput]

  def getColaMixInfo: EitherT[Future, ResultStatus, GetColaMixInfoOutput]

  def deliverCola(amount: Int): EitherT[Future, ResultStatus, DeliverColaOutput]

}
