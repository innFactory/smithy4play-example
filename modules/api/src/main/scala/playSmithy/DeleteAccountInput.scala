package playSmithy

import smithy4s.Schema
import smithy4s.Hints
import smithy4s.schema.Schema.string
import smithy4s.ShapeId
import smithy4s.schema.Schema.struct
import smithy4s.ShapeTag

case class DeleteAccountInput(name: String)
object DeleteAccountInput extends ShapeTag.Companion[DeleteAccountInput] {
  val id: ShapeId = ShapeId("playSmithy", "DeleteAccountInput")

  val hints : Hints = Hints(
    smithy.api.Input(),
  )

  implicit val schema: Schema[DeleteAccountInput] = struct(
    string.required[DeleteAccountInput]("name", _.name).addHints(smithy.api.HttpLabel(), smithy.api.Required()),
  ){
    DeleteAccountInput.apply
  }.withId(id).addHints(hints)
}