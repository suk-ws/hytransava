package cc.sukazyo.hytrans.data.hytrans

class LocalizedDocument (
	private val localizedContents: Map[String, LocalizedContent]
) {
	
	def items: Map[String, LocalizedContent] =
		localizedContents
	
	def get (key: String): Option[LocalizedContent] =
		localizedContents.get(key)
	
	def mixed (newOne: LocalizedDocument): LocalizedDocument =
		LocalizedDocument(
			this.localizedContents ++ newOne.localizedContents
		)
	
	//noinspection NoTargetNameAnnotationForOperatorLikeDefinition
	def ::: (newOne: LocalizedDocument): LocalizedDocument =
		this.mixed(newOne)
	
}

object LocalizedDocument {
	
	implicit class DocumentSeq (documents: Seq[LocalizedDocument]) {
		
		def mixed: LocalizedDocument =
			documents.reduce(_ ::: _)
		
	}
	
}
