import com.google.inject.AbstractModule
import com.typesafe.config.Config
import de.innfactory.play.tracing.OpentelemetryProvider
import play.api.{Configuration, Environment, Logger, Mode}
import play.api.inject.ApplicationLifecycle
import play.api.libs.concurrent.AkkaGuiceSupport
import slick.jdbc.JdbcBackend.Database

import javax.inject.{Inject, Singleton}
import scala.concurrent.ExecutionContext
import scala.util.Try

class Module(environment: Environment, configuration: Configuration)
    extends AbstractModule
    with AkkaGuiceSupport {

  val logger = Logger("application")

  override def configure(): Unit = {

    bind(classOf[TracingConfigurator]).asEagerSingleton()

  }
}

@Singleton
class TracingConfigurator @Inject()(implicit ec: ExecutionContext,
                                    config: Config,
                                    lifecycle: ApplicationLifecycle) {
  Try(
    OpentelemetryProvider
      .configure("smithy4play-example", "smithy4play-example", None)
  )
}
