package cc.sukazyo.hytrans.data

import cc.sukazyo.hytrans.SuiteHytransava
import cc.sukazyo.hytrans.data.hylangs.LanguageTag
import cc.sukazyo.hytrans.data.hylangs.LanguageTag.IllegalLanguageTagException


class TestLanguageTag extends SuiteHytransava {
	
	"A LanguageTag" - {
		
		"cannot be created" - {
			"via new keyword" in {
				"new LanguageTag(\"en-US\")" shouldNot compile
			}
		}
		"can be created" - {
			"via the apply method" in {
				"LanguageTag(\"en-US\")" should compile
				"LanguageTag.apply(\"en-US\")" should compile
			}
		}
		
		"name" - {
			
			"can have lower cased English characters, digits, and underscores" in {
				noException should be thrownBy { LanguageTag("001en_us_0192842az_csx1___") }
			}
			
			"cannot have" - {
				"upper cased English characters" in {
					an[IllegalLanguageTagException] should be thrownBy { LanguageTag("en_US") }
				}
				"non-English characters" in {
					an[IllegalLanguageTagException] should be thrownBy { LanguageTag("ên_gb") }
					an[IllegalLanguageTagException] should be thrownBy { LanguageTag("en_中文") }
				}
				"dashes" in {
					an[IllegalLanguageTagException] should be thrownBy { LanguageTag("en-us") }
				}
				"spaces or other space characters" in {
					an[IllegalLanguageTagException] should be thrownBy { LanguageTag("en us") }
					an[IllegalLanguageTagException] should be thrownBy { LanguageTag("en\tus") }
					an[IllegalLanguageTagException] should be thrownBy { LanguageTag("en\rus") }
					an[IllegalLanguageTagException] should be thrownBy { LanguageTag("en\nus") }
				}
				"other characters" in {
					an[IllegalLanguageTagException] should be thrownBy { LanguageTag("en_us!") }
				}
			}
			
		}
		
		"should be" - {
			
			"immutable" in {
				val tag = LanguageTag("en_us")
				"tag.id = \"en-US\"" shouldNot compile
			}
			
			"able to compare" - {
				"deep equality" - {
					"with another LanguageTag" in {
						val tag1 = LanguageTag("en_us")
						val tag2 = LanguageTag("en_us")
						tag1 shouldEqual tag2
					}
				}
			}
			
		}
		
		"method isOf() should be able to check if a name is the same as the tag after normalized" in {
			val tag = LanguageTag("en_us")
			tag.isOf("en_us") shouldEqual true
			tag.isOf("en-US") shouldEqual true
			tag.isOf("en-gb") shouldEqual false
		}
		
	}
	
	"the LanguageTag.IllegalLanguageTagException class" - {
		
		"should be able to store the original name when failed to build a LanguageTag" in {
			val err = the[IllegalLanguageTagException] thrownBy { LanguageTag("zh_简体") }
			err.original shouldEqual "zh_简体"
		}
		
	}
	
	"the LanguageTag.ensuring method" - {
		
		"should be able to check if a name is valid LanguageTag name" in {
			noException should be thrownBy { LanguageTag.ensuring("en_us") }
			an[IllegalLanguageTagException] should be thrownBy { LanguageTag.ensuring("en_US") }
			an[IllegalLanguageTagException] should be thrownBy { LanguageTag.ensuring("en-us") }
			an[IllegalLanguageTagException] should be thrownBy { LanguageTag.ensuring("en us") }
			an[IllegalLanguageTagException] should be thrownBy { LanguageTag.ensuring("en\tus") }
			an[IllegalLanguageTagException] should be thrownBy { LanguageTag.ensuring("en\rus") }
			an[IllegalLanguageTagException] should be thrownBy { LanguageTag.ensuring("en\nus") }
			an[IllegalLanguageTagException] should be thrownBy { LanguageTag.ensuring("en_us!") }
			an[IllegalLanguageTagException] should be thrownBy { LanguageTag.ensuring("ên_gb") }
			an[IllegalLanguageTagException] should be thrownBy { LanguageTag.ensuring("en_中文") }
		}
		
	}
	
	"the LanguageTag.normalizedName method" - {
		"should be able to convert" - {
			"a name's upper cased letters to lower cased" in {
				LanguageTag.normalizedName("en_US") shouldEqual "en_us"
			}
			"spaces to underscores" in {
				LanguageTag.normalizedName("en us") shouldEqual "en_us"
			}
			"dashes to underscores" in {
				LanguageTag.normalizedName("en-us") shouldEqual "en_us"
			}
		}
	}
	
	"the LanguageTag.of method" - {
		
		"should be able to create a LanguageTag" - {
			
			"and be able to auto-process" - {
				"the uppercased letters" in {
					val tag = LanguageTag.of("en_US")
					tag.id shouldEqual "en_us"
				}
				"the dashes" in {
					val tag = LanguageTag.of("en-us")
					tag.id shouldEqual "en_us"
				}
				"the spaces" in {
					val tag = LanguageTag.of("en us")
					tag.id shouldEqual "en_us"
				}
			}
			
		}
		
	}
	
}
