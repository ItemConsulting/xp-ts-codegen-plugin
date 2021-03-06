package no.item.xp.plugin.renderers.iots

import no.item.xp.plugin.models.StringFieldWithValidation
import no.item.xp.plugin.renderers.ts.createIndentation
import no.item.xp.plugin.renderers.ts.escapeName
import no.item.xp.plugin.renderers.ts.renderComment

fun renderStringFieldWithValidation(field: StringFieldWithValidation, indentLevel: Int): String {
  val indentation = createIndentation(indentLevel)
  val indentation1 = createIndentation(indentLevel + 1)

  val innerType = when (field.isNullable) {
    true -> """
      #w.RegexpValidatedString({
      #${indentation1}regexp: /${field.regexp}/,
      #${indentation1}isNullable: true,
      #$indentation})
      """.trimMargin("#")
    false -> "w.RegexpValidatedString({ regexp: /${field.regexp}/ })"
  }

  return """
      #${renderComment(field.comment, indentLevel)}
      #$indentation${escapeName(field.name)}: ${renderArray(field, innerType)},
      """.trimMargin("#")
}
