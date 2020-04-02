package no.item.xp.plugin.models

@Suppress("SpellCheckingInspection")
enum class InputType(val value: String) {
  ATTACHMENTUPLOADER("ATTACHMENTUPLOADER"),
  CHECKBOX("CHECKBOX"),
  COMBOBOX("COMBOBOX"),
  CONTENTSELECTOR("CONTENTSELECTOR"),
  CUSTOMSELECTOR("CUSTOMSELECTOR"),
  DATE("DATE"),
  DATETIME("DATETIME"),
  DOUBLE("DOUBLE"),
  GEOPOINT("GEOPOINT"),
  HTMLAREA("HTMLAREA"),
  IMAGESELECTOR("IMAGESELECTOR"),
  LONG("LONG"),
  MEDIASELECTOR("MEDIASELECTOR"),
  RADIOBUTTON("RADIOBUTTON"),
  TAG("TAG"),
  TEXTAREA("TEXTAREA"),
  TEXTLINE("TEXTLINE");
}
