package no.item.xp.plugin.renderers.iots

import no.item.xp.plugin.models.*
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

class RenderInterfaceModelAsIoTsTest {
  @Test
  fun `render mixed`() {
    val result = renderInterfaceModelAsIoTs(
      InterfaceModel(
        "my-type",
        listOf(
          StringField("name", "Name", false, false),
          StringField("emails", "Emails", true, true),
          BooleanField("isManager", "Is manager", false, false),
          NumberField("age", "Age", true, false),
          UnionOfStringLiteralField("favouriteColor", "Favourite color", false, false, listOf("red", "green", "blue")),
          ObjectField(
            "preferences",
            "Preferences",
            true,
            true,
            listOf(
              StringField("key", "Key", false, false),
              StringField("value", "Value", false, false)
            )
          )
        )
      )
    )

    assertEquals(
      """
      #import * as t from "io-ts";
      #
      #export const MyType = t.type({
      #  /**
      #   * Name
      #   */
      #  name: t.string,
      #
      #  /**
      #   * Emails
      #   */
      #  emails: t.union([t.undefined, t.array(t.string)]),
      #
      #  /**
      #   * Is manager
      #   */
      #  isManager: t.boolean,
      #
      #  /**
      #   * Age
      #   */
      #  age: t.union([t.undefined, t.number]),
      #
      #  /**
      #   * Favourite color
      #   */
      #  favouriteColor: t.union([t.literal("red"), t.literal("green"), t.literal("blue")]),
      #
      #  /**
      #   * Preferences
      #   */
      #  preferences: t.union([t.undefined, t.array(t.type({
      #    /**
      #     * Key
      #     */
      #    key: t.string,
      #
      #    /**
      #     * Value
      #     */
      #    value: t.string,
      #  }))]),
      #});
      #
      #export type MyType = t.TypeOf<typeof MyType>;
      #""".trimMargin("#"),
      result
    )
  }
}
