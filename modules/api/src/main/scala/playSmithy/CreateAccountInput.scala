package playSmithy

import smithy4s.Schema
import smithy4s.Hints
import smithy4s.schema.Schema.string
import smithy4s.ShapeId
import smithy4s.schema.Schema.struct
import smithy4s.schema.Schema.double
import smithy4s.ShapeTag

case class CreateAccountInput(startingCredit: Double, name: String)
object CreateAccountInput extends ShapeTag.Companion[CreateAccountInput] {
  val id: ShapeId = ShapeId("playSmithy", "CreateAccountInput")

  val hints : Hints = Hints(
    smithy.api.Input(),
  )

  implicit val schema: Schema[CreateAccountInput] = struct(
    double.required[CreateAccountInput]("startingCredit", _.startingCredit).addHints(smithy.api.Required()),
    string.validated(smithy.api.Length(min = Some(3), max = None)).required[CreateAccountInput]("name", _.name).addHints(smithy.api.Required()),
  ){
    CreateAccountInput.apply
  }.withId(id).addHints(hints)
}