package playSmithy

import smithy4s.Schema
import smithy4s.schema.Schema.unit
import smithy4s.Hints
import smithy4s.StreamingSchema
import smithy4s.Transformation
import smithy4s.Monadic
import smithy4s.ShapeId
import smithy4s.Endpoint
import smithy4s.Service

trait ColaMixAPIServiceGen[F[_, _, _, _, _]] {
  self =>

  def buyCola(amount: Int, bankAccountName: String) : F[BuyColaInput, Nothing, BuyColaOutput, Nothing, Nothing]
  def getColaMixInfo() : F[Unit, Nothing, GetColaMixInfoOutput, Nothing, Nothing]
  def deliverCola(amount: Int) : F[DeliverColaInput, Nothing, DeliverColaOutput, Nothing, Nothing]

  def transform[G[_, _, _, _, _]](transformation : Transformation[F, G]) : ColaMixAPIServiceGen[G] = new Transformed(transformation)
  class Transformed[G[_, _, _, _, _]](transformation : Transformation[F, G]) extends ColaMixAPIServiceGen[G] {
    def buyCola(amount: Int, bankAccountName: String) = transformation[BuyColaInput, Nothing, BuyColaOutput, Nothing, Nothing](self.buyCola(amount, bankAccountName))
    def getColaMixInfo() = transformation[Unit, Nothing, GetColaMixInfoOutput, Nothing, Nothing](self.getColaMixInfo())
    def deliverCola(amount: Int) = transformation[DeliverColaInput, Nothing, DeliverColaOutput, Nothing, Nothing](self.deliverCola(amount))
  }
}

object ColaMixAPIServiceGen extends Service[ColaMixAPIServiceGen, ColaMixAPIServiceOperation] {

  def apply[F[_]](implicit F: Monadic[ColaMixAPIServiceGen, F]): F.type = F

  val id: ShapeId = ShapeId("playSmithy", "ColaMixAPIService")

  val hints : Hints = Hints(
    smithy4s.api.SimpleRestJson(),
  )

  val endpoints: List[Endpoint[ColaMixAPIServiceOperation, _, _, _, _, _]] = List(
    BuyCola,
    GetColaMixInfo,
    DeliverCola,
  )

  val version: String = "1.0.0"

  def endpoint[I, E, O, SI, SO](op : ColaMixAPIServiceOperation[I, E, O, SI, SO]) = op match {
    case BuyCola(input) => (input, BuyCola)
    case GetColaMixInfo() => ((), GetColaMixInfo)
    case DeliverCola(input) => (input, DeliverCola)
  }

  object reified extends ColaMixAPIServiceGen[ColaMixAPIServiceOperation] {
    def buyCola(amount: Int, bankAccountName: String) = BuyCola(BuyColaInput(amount, bankAccountName))
    def getColaMixInfo() = GetColaMixInfo()
    def deliverCola(amount: Int) = DeliverCola(DeliverColaInput(amount))
  }

  def transform[P[_, _, _, _, _]](transformation: Transformation[ColaMixAPIServiceOperation, P]): ColaMixAPIServiceGen[P] = reified.transform(transformation)

  def transform[P[_, _, _, _, _], P1[_, _, _, _, _]](alg: ColaMixAPIServiceGen[P], transformation: Transformation[P, P1]): ColaMixAPIServiceGen[P1] = alg.transform(transformation)

  def asTransformation[P[_, _, _, _, _]](impl : ColaMixAPIServiceGen[P]): Transformation[ColaMixAPIServiceOperation, P] = new Transformation[ColaMixAPIServiceOperation, P] {
    def apply[I, E, O, SI, SO](op : ColaMixAPIServiceOperation[I, E, O, SI, SO]) : P[I, E, O, SI, SO] = op match  {
      case BuyCola(BuyColaInput(amount, bankAccountName)) => impl.buyCola(amount, bankAccountName)
      case GetColaMixInfo() => impl.getColaMixInfo()
      case DeliverCola(DeliverColaInput(amount)) => impl.deliverCola(amount)
    }
  }
  case class BuyCola(input: BuyColaInput) extends ColaMixAPIServiceOperation[BuyColaInput, Nothing, BuyColaOutput, Nothing, Nothing]
  object BuyCola extends Endpoint[ColaMixAPIServiceOperation, BuyColaInput, Nothing, BuyColaOutput, Nothing, Nothing] {
    val id: ShapeId = ShapeId("playSmithy", "BuyCola")
    val input: Schema[BuyColaInput] = BuyColaInput.schema.addHints(smithy4s.internals.InputOutput.Input.widen)
    val output: Schema[BuyColaOutput] = BuyColaOutput.schema.addHints(smithy4s.internals.InputOutput.Output.widen)
    val streamedInput : StreamingSchema[Nothing] = StreamingSchema.nothing
    val streamedOutput : StreamingSchema[Nothing] = StreamingSchema.nothing
    val hints : Hints = Hints(
      smithy.api.Http(method = smithy.api.NonEmptyString("POST"), uri = smithy.api.NonEmptyString("/buy"), code = 200),
    )
    def wrap(input: BuyColaInput) = BuyCola(input)
  }
  case class GetColaMixInfo() extends ColaMixAPIServiceOperation[Unit, Nothing, GetColaMixInfoOutput, Nothing, Nothing]
  object GetColaMixInfo extends Endpoint[ColaMixAPIServiceOperation, Unit, Nothing, GetColaMixInfoOutput, Nothing, Nothing] {
    val id: ShapeId = ShapeId("playSmithy", "GetColaMixInfo")
    val input: Schema[Unit] = unit.addHints(smithy4s.internals.InputOutput.Input.widen)
    val output: Schema[GetColaMixInfoOutput] = GetColaMixInfoOutput.schema.addHints(smithy4s.internals.InputOutput.Output.widen)
    val streamedInput : StreamingSchema[Nothing] = StreamingSchema.nothing
    val streamedOutput : StreamingSchema[Nothing] = StreamingSchema.nothing
    val hints : Hints = Hints(
      smithy.api.Http(method = smithy.api.NonEmptyString("GET"), uri = smithy.api.NonEmptyString("/storage"), code = 201),
      smithy.api.Readonly(),
    )
    def wrap(input: Unit) = GetColaMixInfo()
  }
  case class DeliverCola(input: DeliverColaInput) extends ColaMixAPIServiceOperation[DeliverColaInput, Nothing, DeliverColaOutput, Nothing, Nothing]
  object DeliverCola extends Endpoint[ColaMixAPIServiceOperation, DeliverColaInput, Nothing, DeliverColaOutput, Nothing, Nothing] {
    val id: ShapeId = ShapeId("playSmithy", "DeliverCola")
    val input: Schema[DeliverColaInput] = DeliverColaInput.schema.addHints(smithy4s.internals.InputOutput.Input.widen)
    val output: Schema[DeliverColaOutput] = DeliverColaOutput.schema.addHints(smithy4s.internals.InputOutput.Output.widen)
    val streamedInput : StreamingSchema[Nothing] = StreamingSchema.nothing
    val streamedOutput : StreamingSchema[Nothing] = StreamingSchema.nothing
    val hints : Hints = Hints(
      smithy.api.Http(method = smithy.api.NonEmptyString("POST"), uri = smithy.api.NonEmptyString("/storage"), code = 201),
      smithy.api.Auth(Set()),
    )
    def wrap(input: DeliverColaInput) = DeliverCola(input)
  }
}

sealed trait ColaMixAPIServiceOperation[Input, Err, Output, StreamedInput, StreamedOutput]
