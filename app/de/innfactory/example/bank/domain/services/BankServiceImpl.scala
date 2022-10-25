package de.innfactory.example.bank.domain.services

import cats.data.{EitherT, Validated}
import de.innfactory.example.bank.domain.interfaces.BankService
import de.innfactory.play.controller.ResultStatus
import de.innfactory.play.results.errors.Errors.BadRequest
import playSmithy.{
  CreateDeleteAccountOutput,
  GetAllAccountsOutput,
  TransferOutput
}
import de.innfactory.play.results.errors.Errors.NotFound

import javax.inject.Inject
import scala.concurrent.{ExecutionContext, Future}

class BankServiceImpl @Inject()()(implicit ec: ExecutionContext)
    extends BankService {

  var database: Map[String, Double] = Map.empty

  override def createAccount(
    startingCredit: Double,
    name: String
  ): EitherT[Future, ResultStatus, CreateDeleteAccountOutput] =
    for {
      _ <- EitherT[Future, ResultStatus, Unit](
        Future(
          Validated
            .cond(
              !database.contains(name),
              (),
              BadRequest(s"A Account for this user already exists.")
            )
            .toEither
        )
      )
      _ = database += (name -> startingCredit)
      account <- toCreateAccountOutput(name)
    } yield account

  override def deleteAccount(
    name: String
  ): EitherT[Future, ResultStatus, CreateDeleteAccountOutput] =
    for {
      account <- toCreateAccountOutput(name)
      _ = database -= name
    } yield account

  override def transfer(
    amount: Double,
    name: String
  ): EitherT[Future, ResultStatus, TransferOutput] =
    for {
      oldAccount <- toCreateAccountOutput(name)
      _ = database += (name -> (oldAccount.balance + amount))
      newAccount <- toCreateAccountOutput(name)
      message = if (newAccount.balance < 0)
        Some("You now have negative balance!")
      else None
    } yield TransferOutput(newAccount.balance, newAccount.name, message)

  override def toCreateAccountOutput(key: String)(
    implicit ec: ExecutionContext
  ): EitherT[Future, ResultStatus, CreateDeleteAccountOutput] =
    for {
      value <- EitherT.fromOptionF[Future, ResultStatus, Double](
        Future(database.get(key)),
        NotFound("No Bank Account for this User")
      )
    } yield CreateDeleteAccountOutput(value, key)

  override def getAllAccounts()
    : EitherT[Future, ResultStatus, GetAllAccountsOutput] =
    EitherT.rightT[Future, ResultStatus](GetAllAccountsOutput(
            database.map(acc => CreateDeleteAccountOutput(acc._2, acc._1) ).toList)
    )
}
