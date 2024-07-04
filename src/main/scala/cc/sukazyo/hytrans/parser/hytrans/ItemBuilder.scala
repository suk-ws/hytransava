package cc.sukazyo.hytrans.parser.hytrans

case class ItemBuilder (
	itemKey: String,
	contentBuilder: ItemContentBuilder = ItemContentBuilder()
)
