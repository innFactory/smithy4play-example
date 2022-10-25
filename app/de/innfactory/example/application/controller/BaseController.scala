package de.innfactory.example.application.controller

import akka.http.scaladsl.model.HttpHeader.ParsingResult.Ok
import cats.data.{EitherT, Kleisli, RWS, Validated}
import de.innfactory.play.controller.{ErrorResult, ResultStatus}
import de.innfactory.play.results.errors.Errors.Forbidden
import de.innfactory.play.smithy4play.{AbstractBaseController, ContextWithHeaders, HttpHeaders, ImplicitLogContext}
import de.innfactory.smithy4play.{ContextRouteError, RoutingContext}
import play.api.Application

import scala.concurrent.{ExecutionContext, Future}

class BaseController(implicit ec: ExecutionContext, app: Application)
    extends AbstractBaseController[ResultStatus, ContextWithHeaders, ContextWithHeaders]
    with ImplicitLogContext
   {
     override def errorHandler(e: ResultStatus): ContextRouteError =
       e match {
         case result: ErrorResult =>
           new ContextRouteError {
             override def message: String = result.message

             override def additionalInfoToLog: Option[String] = result.additionalInfoToLog

             override def additionalInfoErrorCode: Option[String] = result.additionalInfoErrorCode

             override def additionalInformation: Option[String] = None

             override def statusCode: Int = result.statusCode
           }
       }

     override def createRequestContextFromRoutingContext(r: RoutingContext): ContextWithHeaders = new ContextWithHeaders {
       override def httpHeaders: HttpHeaders = HttpHeaders(r.map)
     }

     override def AuthAction: Kleisli[ApplicationRouteResult, ContextWithHeaders, ContextWithHeaders] = Kleisli {
       context =>
         println(context.httpHeaders.rc)
         EitherT.rightT[Future, ResultStatus](context)
     }

   }
