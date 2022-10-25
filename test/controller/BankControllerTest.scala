package controller

import de.innfactory.smithy4play.client.GenericAPIClient.EnhancedGenericAPIClient
import de.innfactory.smithy4play.client.SmithyPlayTestUtils._
import org.scalatestplus.play.{BaseOneAppPerSuite, PlaySpec}
import playSmithy.{BankAPIServiceGen, ColaMixAPIServiceGen}
import utils.FakeRequestClient

class BankControllerTest
    extends PlaySpec
    with BaseOneAppPerSuite
    with TestApplicationFactory {

  val bankClient = BankAPIServiceGen.withClient(new FakeRequestClient)
  val accountName = "Tommy McFlommy"

  "BankController" must {

    "create Account " in {
      val startingBalance = 10
      val result = bankClient
        .createAccount(startingBalance, accountName)
        .run(None)
        .awaitRight

      result.body.get.name mustBe accountName
      result.body.get.balance mustBe startingBalance
      result.statusCode mustBe result.expectedStatusCode
    }
    "delete Account" in {
      val result = bankClient.deleteAccount(accountName).run(None).awaitRight

      result.body.get.name mustBe accountName
      result.statusCode mustBe result.expectedStatusCode
    }

    "transfer money to account" in {
      val name = "McFloppy"
      val startingBalance = 10.0
      val transferAmount = 20.0
      bankClient.createAccount(startingBalance, name).run(None).awaitRight
      val result =
        bankClient.transfer(transferAmount, name).run(None).awaitRight

      result.body.get.name mustBe name
      result.body.get.balance mustBe startingBalance + transferAmount
      result.statusCode mustBe result.expectedStatusCode
    }

    "get all Accounts" in {
      bankClient.createAccount(10.0, "Randy").run(None).awaitRight
      val result = bankClient
        .getAllAccounts()
        .run(Some(Map("Authorization" -> Seq("ADMIN")))).awaitRight

      result.statusCode mustBe result.expectedStatusCode
      result.body.get.body.nonEmpty mustBe true
    }
  }
}
