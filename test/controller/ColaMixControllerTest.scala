package controller

import de.innfactory.smithy4play.client.SmithyPlayTestUtils._
import de.innfactory.smithy4play.client.GenericAPIClient.EnhancedGenericAPIClient
import org.scalatestplus.play.{BaseOneAppPerSuite, PlaySpec}
import playSmithy.{BankAPIServiceGen, ColaMixAPIServiceGen}
import utils.FakeRequestClient

class ColaMixControllerTest extends PlaySpec with BaseOneAppPerSuite with TestApplicationFactory {

  val client = ColaMixAPIServiceGen.withClient(new FakeRequestClient)
  val bankClient = BankAPIServiceGen.withClient(new FakeRequestClient)

  "ColaMixController" must {


    "get ColaServiceInformations" in {
      val result = client.getColaMixInfo().run(None).awaitRight
      println(result)
      result.statusCode mustBe result.expectedStatusCode
    }

    "post deliverCola with Authorization" in {
      val amount = 10000
      val colaInfoBefore = client.getColaMixInfo().run(None).awaitRight
      val result = client.deliverCola(10000).run(Some(Map("Authorization" -> Seq("ADMIN")))).awaitRight

      result.statusCode mustBe result.expectedStatusCode
      result.body.get.newStorage mustBe colaInfoBefore.body.get.amount + amount
    }

    "post deliverCola without Authorization" in {
      val result = client.deliverCola(10000).run(None).awaitLeft

      result.statusCode must not be result.expectedStatusCode
      result.statusCode mustBe 401
    }

    "buy ColaMix with no existing BankAccount" in {
      val result = client.buyCola(100, "Hans").run(None).awaitLeft

      result.statusCode must not be result.expectedStatusCode
      result.statusCode mustBe 404
    }

    "buy ColaMix with existing BankAccount" in {
      val account = bankClient.createAccount(1000000.0, "Hans").run(None).awaitRight.body.get
      val result = client.buyCola(100, account.name).run(None).awaitRight

      result.statusCode mustBe result.expectedStatusCode
      result.body.get.newBankBalance must not be account.balance
    }
  }

}
