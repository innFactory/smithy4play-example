package playSmithy

import smithy4s.Schema
import smithy4s.schema.Schema.list
import smithy4s.Hints
import smithy4s.ShapeId
import smithy4s.schema.Schema.bijection
import smithy4s.Newtype

object CreateDeleteAccountOutputList extends Newtype[List[CreateDeleteAccountOutput]] {
  val id: ShapeId = ShapeId("playSmithy", "CreateDeleteAccountOutputList")
  val hints : Hints = Hints.empty
  val underlyingSchema : Schema[List[CreateDeleteAccountOutput]] = list(CreateDeleteAccountOutput.schema).withId(id).addHints(hints)
  implicit val schema : Schema[CreateDeleteAccountOutputList] = bijection(underlyingSchema, asBijection)
}