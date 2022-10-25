package de.innfactory.example.bank.application

import de.innfactory.example.application.controller.BaseController
import de.innfactory.example.bank.domain.interfaces.BankService
import de.innfactory.example.colamix.domain.interfaces.ColaMixService
import de.innfactory.smithy4play.{AutoRouting, ContextRoute}
import play.api.Application
import play.api.mvc.ControllerComponents
import playSmithy.{BankAPIService, CreateDeleteAccountOutput, GetAllAccountsOutput, TransferOutput}

import javax.inject.{Inject, Singleton}
import scala.concurrent.ExecutionContext

@AutoRouting
@Singleton
class BankController @Inject()(bankService: BankService)(
  implicit ec: ExecutionContext,
  cc: ControllerComponents,
  app: Application
) extends BaseController
    with BankAPIService[ContextRoute] {

  override def createAccount(
    startingCredit: Double,
    name: String
  ): ContextRoute[CreateDeleteAccountOutput] = Endpoint.withAuth.execute(
    c => bankService.createAccount(startingCredit, name)).complete

  override def deleteAccount(
    name: String
  ): ContextRoute[CreateDeleteAccountOutput] = Endpoint.execute(
    c => bankService.deleteAccount( name)).complete

  override def transfer(amount: Double,
                        name: String): ContextRoute[TransferOutput] = Endpoint.execute(
    c => bankService.transfer(amount, name)).complete

  override def getAllAccounts(): ContextRoute[GetAllAccountsOutput] = Endpoint.withAuth.execute(
    c => bankService.getAllAccounts()
  ).complete
}
