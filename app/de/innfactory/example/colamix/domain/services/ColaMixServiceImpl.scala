package de.innfactory.example.colamix.domain.services

import cats.data.EitherT
import de.innfactory.example.colamix.domain.interfaces.ColaMixService
import de.innfactory.play.controller.ResultStatus
import playSmithy.{BuyColaOutput, DeliverColaOutput,  GetColaMixInfoOutput}
import cats.data.Validated
import de.innfactory.example.bank.domain.interfaces.BankService
import de.innfactory.play.results.errors.Errors.BadRequest

import javax.inject.Inject
import scala.concurrent.{ExecutionContext, Future}

class ColaMixServiceImpl @Inject()(bankService: BankService)(implicit ec: ExecutionContext)
    extends ColaMixService {

  val rand = new scala.util.Random
  var colaStorage: Int = rand.nextInt(1337)
  var buyingPrice: Double = rand.nextDouble() * 10 + 3

  override def buyCola(
    amount: Int, bankAccountName: String
  ): EitherT[Future, ResultStatus, BuyColaOutput] =
    for {
      _ <- EitherT[Future, ResultStatus, Unit](
        Future(
          Validated
            .cond(
              amount < colaStorage,
              (),
              BadRequest(
                s"This exceeds my Storage! I only have $colaStorage left. Wait for the next delivery."
              )
            )
            .toEither
        )
      )
      oldBankAccount <- bankService.toCreateAccountOutput(bankAccountName)
      _ <- bankService.transfer(-1 * amount * buyingPrice, bankAccountName)
      _ = colaStorage = colaStorage - amount
      updatedBankAccount <- bankService.toCreateAccountOutput(bankAccountName)
    } yield BuyColaOutput(oldBankAccount.balance, amount * buyingPrice, updatedBankAccount.balance, colaStorage)


  override def getColaMixInfo: EitherT[Future, ResultStatus, GetColaMixInfoOutput] =
    EitherT.rightT[Future, ResultStatus](GetColaMixInfoOutput(colaStorage, buyingPrice))

  override def deliverCola(amount: Int): EitherT[Future, ResultStatus, DeliverColaOutput] =
    EitherT.rightT[Future, ResultStatus]({
      colaStorage += amount
      buyingPrice = rand.nextDouble() * 10 + 3
      DeliverColaOutput(colaStorage)
    })
}
