package controller

import akka.stream.Materializer
import org.scalatestplus.play.FakeApplicationFactory
import play.api.Application
import play.api.inject.guice.GuiceApplicationBuilder

import scala.concurrent.ExecutionContext

trait TestApplicationFactory extends FakeApplicationFactory {
  implicit var ec: ExecutionContext = _
  implicit var mat: Materializer = _

  def fakeApplication(): Application = {
    val app = GuiceApplicationBuilder()
      .build()

    ec = app.injector.instanceOf[ExecutionContext]
    mat = app.injector.instanceOf[Materializer]

    app
  }
}
