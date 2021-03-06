package no.item.xp.plugin.renderers.jsdoc

import no.item.xp.plugin.models.*
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

class RenderOptionSetFieldTest {
  @Test
  fun `render option set field`() {
    val result = renderInterfaceModelAsJSDoc(
      InterfaceModel(
        "my-type",
        listOf(
          OptionSetField(
            "myOptionSet",
            "Select content manually?",
            false,
            false,
            false,
            listOf(
              ObjectField(
                "no",
                "No",
                true,
                false,
                listOf()
              ),
              ObjectField(
                "yes",
                "Yes",
                true,
                false,
                listOf(
                  StringField("articleList", "Select articles for the list", true, true)
                )
              )
            )
          )
        )
      )
    )

    assertEquals(
      """
      #/**
      # * @typedef {Object} MyType
      # * @property {Object} myOptionSet Select content manually?
      # * @property {("no" | "yes")} myOptionSet._selected Selected
      # * @property {Object} [myOptionSet.no] No
      # * @property {Object} [myOptionSet.yes] Yes
      # * @property {string[]} [myOptionSet.yes.articleList] Select articles for the list
      # */
      #""".trimMargin("#"),
      result
    )
  }
}
