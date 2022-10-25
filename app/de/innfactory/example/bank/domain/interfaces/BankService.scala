package de.innfactory.example.bank.domain.interfaces

import cats.data.EitherT
import com.google.inject.ImplementedBy
import de.innfactory.example.bank.domain.services.BankServiceImpl
import de.innfactory.play.controller.ResultStatus
import playSmithy.{CreateDeleteAccountOutput, GetAllAccountsOutput, TransferOutput}

import scala.concurrent.{ExecutionContext, Future}

@ImplementedBy(classOf[BankServiceImpl])
trait BankService {


  def getAllAccounts(): EitherT[Future, ResultStatus, GetAllAccountsOutput]

  def createAccount(
    startingCredit: Double,
    name: String
  ): EitherT[Future, ResultStatus, CreateDeleteAccountOutput]

  def deleteAccount(
    name: String
  ): EitherT[Future, ResultStatus, CreateDeleteAccountOutput]

  def transfer(amount: Double, name: String): EitherT[Future, ResultStatus, TransferOutput]

  def toCreateAccountOutput(key: String)(
    implicit ec: ExecutionContext
  ): EitherT[Future, ResultStatus, CreateDeleteAccountOutput]
}
