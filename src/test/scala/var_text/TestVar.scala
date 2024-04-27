package cc.sukazyo.hytrans
package var_text

class TestVar extends SuiteVarTexts {
	
	"A Var" - {
		
		"should be able to created" - {
			
			val my_key = "var-key"
			val my_value = "Some Random Value"
			
			"using apply method with two String" in {
				val v: Var = Var(my_key, my_value)
				v.id `shouldEqual` my_key
				v.text `shouldEqual` my_value
			}
			
			"by converted from a (String, String) tuple" in {
				val kv: (String, String) = my_key -> my_value
				val v: Var = kv
				v.id `shouldEqual` my_key
				v.text `shouldEqual` my_value
			}
			
			"by using the extension method `asVar` of String" in {
				import Var.String_As_VarText
				val v: Var = my_value.asVar(my_key)
				v.id `shouldEqual` my_key
				v.text `shouldEqual` my_value
			}
			
			import var_text.Var.IllegalVarIdException
			
			"when contains letters, digits, and some allowed special characters (\"\")" in {
				noException should be thrownBy :
					Var("key-with-1_0000-and-1.1-AND-1:*//\\:123#M@%&?;,~", "value")
			}
			
			"but should failed" - {
				
				"when the key contains character that is not in the allow list(letters, digits, \"_-.*/\\|:#@%&?;,~\")" - {
					"like space" in :
						an[IllegalVarIdException] should be thrownBy :
							Var("key with space", "value")
					"like Chinese" in :
						an[IllegalVarIdException] should be thrownBy :
							Var("key-with-中文", "value")
					"like Japanese" in :
						an[IllegalVarIdException] should be thrownBy :
							Var("key-with-ジャパン", "value")
					"like Pin Yin character" in :
						an[IllegalVarIdException] should be thrownBy :
							Var("key-with-ɑ́", "value")
					"like latin character" in :
						an[IllegalVarIdException] should be thrownBy :
							Var("key-with-ȧ", "value")
					"like tab" in :
						an[IllegalVarIdException] should be thrownBy :
							Var("key-with-\t", "value")
					"like line break" in :
						an[IllegalVarIdException] should be thrownBy :
							Var("key-with-\n", "value")
					"like square brackets" in :
						an[IllegalVarIdException] should be thrownBy :
							Var("key-with-[]", "value")
					"like curly brackets" in :
						an[IllegalVarIdException] should be thrownBy :
							Var("key-with-{}", "value")
					"like angle brackets" in :
						an[IllegalVarIdException] should be thrownBy :
							Var("key-with-<>", "value")
				}
				
				"when the key is empty" in {
					an[IllegalVarIdException] should be thrownBy :
						Var("", "value")
				}
				
			}
			
			
		}
		
		"should be able to convert" - {
			
			"to another Var with the same text and other id with `asId`" in {
				val v: Var = Var("key", "value")
				val v2: Var = v.asId("new-key")
				v2.id `shouldEqual` "new-key"
				v2.text `shouldEqual` v.text
			}
			
			"to another Var with the same id and other text with `asText`" in {
				val v: Var = Var("key", "value")
				val v2: Var = v.asText("new value")
				v2.id `shouldEqual` v.id
				v2.text `shouldEqual` "new value"
			}
			
			"to a (String, String) tuple using `.unpackKV` method" in {
				val v: Var = Var("key", "value")
				val tp: (String, String) = v.unpackKV
				tp._1 `shouldEqual` "key"
				tp._2 `shouldEqual` "value"
			}
			
		}
		
	}
	
}
