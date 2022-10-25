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

trait BankAPIServiceGen[F[_, _, _, _, _]] {
  self =>

  def createAccount(startingCredit: Double, name: String) : F[CreateAccountInput, Nothing, CreateDeleteAccountOutput, Nothing, Nothing]
  def deleteAccount(name: String) : F[DeleteAccountInput, Nothing, CreateDeleteAccountOutput, Nothing, Nothing]
  def transfer(amount: Double, name: String) : F[TransferInput, Nothing, TransferOutput, Nothing, Nothing]
  def getAllAccounts() : F[Unit, Nothing, GetAllAccountsOutput, Nothing, Nothing]

  def transform[G[_, _, _, _, _]](transformation : Transformation[F, G]) : BankAPIServiceGen[G] = new Transformed(transformation)
  class Transformed[G[_, _, _, _, _]](transformation : Transformation[F, G]) extends BankAPIServiceGen[G] {
    def createAccount(startingCredit: Double, name: String) = transformation[CreateAccountInput, Nothing, CreateDeleteAccountOutput, Nothing, Nothing](self.createAccount(startingCredit, name))
    def deleteAccount(name: String) = transformation[DeleteAccountInput, Nothing, CreateDeleteAccountOutput, Nothing, Nothing](self.deleteAccount(name))
    def transfer(amount: Double, name: String) = transformation[TransferInput, Nothing, TransferOutput, Nothing, Nothing](self.transfer(amount, name))
    def getAllAccounts() = transformation[Unit, Nothing, GetAllAccountsOutput, Nothing, Nothing](self.getAllAccounts())
  }
}

object BankAPIServiceGen extends Service[BankAPIServiceGen, BankAPIServiceOperation] {

  def apply[F[_]](implicit F: Monadic[BankAPIServiceGen, F]): F.type = F

  val id: ShapeId = ShapeId("playSmithy", "BankAPIService")

  val hints : Hints = Hints(
    smithy4s.api.SimpleRestJson(),
  )

  val endpoints: List[Endpoint[BankAPIServiceOperation, _, _, _, _, _]] = List(
    CreateAccount,
    DeleteAccount,
    Transfer,
    GetAllAccounts,
  )

  val version: String = "1.0.0"

  def endpoint[I, E, O, SI, SO](op : BankAPIServiceOperation[I, E, O, SI, SO]) = op match {
    case CreateAccount(input) => (input, CreateAccount)
    case DeleteAccount(input) => (input, DeleteAccount)
    case Transfer(input) => (input, Transfer)
    case GetAllAccounts() => ((), GetAllAccounts)
  }

  object reified extends BankAPIServiceGen[BankAPIServiceOperation] {
    def createAccount(startingCredit: Double, name: String) = CreateAccount(CreateAccountInput(startingCredit, name))
    def deleteAccount(name: String) = DeleteAccount(DeleteAccountInput(name))
    def transfer(amount: Double, name: String) = Transfer(TransferInput(amount, name))
    def getAllAccounts() = GetAllAccounts()
  }

  def transform[P[_, _, _, _, _]](transformation: Transformation[BankAPIServiceOperation, P]): BankAPIServiceGen[P] = reified.transform(transformation)

  def transform[P[_, _, _, _, _], P1[_, _, _, _, _]](alg: BankAPIServiceGen[P], transformation: Transformation[P, P1]): BankAPIServiceGen[P1] = alg.transform(transformation)

  def asTransformation[P[_, _, _, _, _]](impl : BankAPIServiceGen[P]): Transformation[BankAPIServiceOperation, P] = new Transformation[BankAPIServiceOperation, P] {
    def apply[I, E, O, SI, SO](op : BankAPIServiceOperation[I, E, O, SI, SO]) : P[I, E, O, SI, SO] = op match  {
      case CreateAccount(CreateAccountInput(startingCredit, name)) => impl.createAccount(startingCredit, name)
      case DeleteAccount(DeleteAccountInput(name)) => impl.deleteAccount(name)
      case Transfer(TransferInput(amount, name)) => impl.transfer(amount, name)
      case GetAllAccounts() => impl.getAllAccounts()
    }
  }
  case class CreateAccount(input: CreateAccountInput) extends BankAPIServiceOperation[CreateAccountInput, Nothing, CreateDeleteAccountOutput, Nothing, Nothing]
  object CreateAccount extends Endpoint[BankAPIServiceOperation, CreateAccountInput, Nothing, CreateDeleteAccountOutput, Nothing, Nothing] {
    val id: ShapeId = ShapeId("playSmithy", "CreateAccount")
    val input: Schema[CreateAccountInput] = CreateAccountInput.schema.addHints(smithy4s.internals.InputOutput.Input.widen)
    val output: Schema[CreateDeleteAccountOutput] = CreateDeleteAccountOutput.schema.addHints(smithy4s.internals.InputOutput.Output.widen)
    val streamedInput : StreamingSchema[Nothing] = StreamingSchema.nothing
    val streamedOutput : StreamingSchema[Nothing] = StreamingSchema.nothing
    val hints : Hints = Hints(
      smithy.api.Http(method = smithy.api.NonEmptyString("POST"), uri = smithy.api.NonEmptyString("/account"), code = 200),
    )
    def wrap(input: CreateAccountInput) = CreateAccount(input)
  }
  case class DeleteAccount(input: DeleteAccountInput) extends BankAPIServiceOperation[DeleteAccountInput, Nothing, CreateDeleteAccountOutput, Nothing, Nothing]
  object DeleteAccount extends Endpoint[BankAPIServiceOperation, DeleteAccountInput, Nothing, CreateDeleteAccountOutput, Nothing, Nothing] {
    val id: ShapeId = ShapeId("playSmithy", "DeleteAccount")
    val input: Schema[DeleteAccountInput] = DeleteAccountInput.schema.addHints(smithy4s.internals.InputOutput.Input.widen)
    val output: Schema[CreateDeleteAccountOutput] = CreateDeleteAccountOutput.schema.addHints(smithy4s.internals.InputOutput.Output.widen)
    val streamedInput : StreamingSchema[Nothing] = StreamingSchema.nothing
    val streamedOutput : StreamingSchema[Nothing] = StreamingSchema.nothing
    val hints : Hints = Hints(
      smithy.api.Http(method = smithy.api.NonEmptyString("DELETE"), uri = smithy.api.NonEmptyString("/account/{name}"), code = 200),
      smithy.api.Idempotent(),
    )
    def wrap(input: DeleteAccountInput) = DeleteAccount(input)
  }
  case class Transfer(input: TransferInput) extends BankAPIServiceOperation[TransferInput, Nothing, TransferOutput, Nothing, Nothing]
  object Transfer extends Endpoint[BankAPIServiceOperation, TransferInput, Nothing, TransferOutput, Nothing, Nothing] {
    val id: ShapeId = ShapeId("playSmithy", "Transfer")
    val input: Schema[TransferInput] = TransferInput.schema.addHints(smithy4s.internals.InputOutput.Input.widen)
    val output: Schema[TransferOutput] = TransferOutput.schema.addHints(smithy4s.internals.InputOutput.Output.widen)
    val streamedInput : StreamingSchema[Nothing] = StreamingSchema.nothing
    val streamedOutput : StreamingSchema[Nothing] = StreamingSchema.nothing
    val hints : Hints = Hints(
      smithy.api.Http(method = smithy.api.NonEmptyString("POST"), uri = smithy.api.NonEmptyString("/account/transfer"), code = 201),
    )
    def wrap(input: TransferInput) = Transfer(input)
  }
  case class GetAllAccounts() extends BankAPIServiceOperation[Unit, Nothing, GetAllAccountsOutput, Nothing, Nothing]
  object GetAllAccounts extends Endpoint[BankAPIServiceOperation, Unit, Nothing, GetAllAccountsOutput, Nothing, Nothing] {
    val id: ShapeId = ShapeId("playSmithy", "GetAllAccounts")
    val input: Schema[Unit] = unit.addHints(smithy4s.internals.InputOutput.Input.widen)
    val output: Schema[GetAllAccountsOutput] = GetAllAccountsOutput.schema.addHints(smithy4s.internals.InputOutput.Output.widen)
    val streamedInput : StreamingSchema[Nothing] = StreamingSchema.nothing
    val streamedOutput : StreamingSchema[Nothing] = StreamingSchema.nothing
    val hints : Hints = Hints(
      smithy.api.Auth(Set()),
      smithy.api.Http(method = smithy.api.NonEmptyString("GET"), uri = smithy.api.NonEmptyString("/account"), code = 200),
      smithy.api.Readonly(),
    )
    def wrap(input: Unit) = GetAllAccounts()
  }
}

sealed trait BankAPIServiceOperation[Input, Err, Output, StreamedInput, StreamedOutput]
