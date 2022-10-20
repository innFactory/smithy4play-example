package de.innfactory.example.colamix.domain.services

import cats.data.EitherT
import de.innfactory.example.colamix.domain.interfaces.ColaMixService
import de.innfactory.play.controller.ResultStatus
import playSmithy.{BuyColaOutput, DeliverColaOutput, GetColaAmountOutput, HaggleAskingPriceOutput}
import cats.data.Validated
import de.innfactory.play.results.errors.Errors.BadRequest

import javax.inject.Inject
import scala.concurrent.{ExecutionContext, Future}

class ColaMixServiceImpl @Inject()()(implicit ec: ExecutionContext)
    extends ColaMixService {

  val rand = new scala.util.Random
  var colaStorage: Int = rand.nextInt(1337)
  var buyingPrice: Double = rand.nextDouble() * 10 + 3
  var haggleTimes: Int = rand.nextInt(15)

  override def buyCola(
    amount: Int
  ): EitherT[Future, ResultStatus, BuyColaOutput] =
    for {
      _ <- EitherT[Future, ResultStatus, Unit](
        Future(
          Validated
            .cond(
              amount < colaStorage,
              (),
              BadRequest(
                s"This exceeds my Storage! I only have $colaStorage left."
              )
            )
            .toEither
        )
      )
      _ = colaStorage = colaStorage - amount

    } yield BuyColaOutput(amount * buyingPrice, colaStorage)

  override def haggleAskingPrice(
    price: Double,
    name: String
  ): EitherT[Future, ResultStatus, HaggleAskingPriceOutput] =
    for {
      _ <- EitherT[Future, ResultStatus, Unit](
        Future(
          Validated
            .cond(
              haggleTimes >= 0,
              (),
              BadRequest(
                s"We've haggled enough! Now take it or leave! The price is ${buyingPrice - buyingPrice % 0.01} "
              )
            )
            .toEither
        )
      )
      _ <- EitherT[Future, ResultStatus, Unit](
        Future(
          Validated
            .cond(
              !name.contains("W") ||
                !name.contains("Y") || !name.contains("Z") || name.length < 10,
              (),
              BadRequest(
                s"I don't like your name. Leave and look for another shop!"
              )
            )
            .toEither
        )
      )
      _ = haggleTimes -= 1
      lowerBound = buyingPrice * 0.95
      _ <- EitherT[Future, ResultStatus, Unit](
        Future(
          Validated
            .cond(
              price > lowerBound,
              (),
              BadRequest(s"You are trying to rob me blind!")
            )
            .toEither
        )
      )
      _ = buyingPrice = price
    } yield HaggleAskingPriceOutput(Some(buyingPrice), Some("I'll take it!"))

  override def getColaAmount()
    : EitherT[Future, ResultStatus, GetColaAmountOutput] =
    EitherT.rightT[Future, ResultStatus](GetColaAmountOutput(colaStorage))

  override def deliverCola(amount: Int): EitherT[Future, ResultStatus, DeliverColaOutput] =
    EitherT.rightT[Future, ResultStatus]({
      colaStorage += amount
      buyingPrice = rand.nextDouble() * 10 + 3
      haggleTimes =  rand.nextInt(15)
      DeliverColaOutput(colaStorage)
    })
}
