package cc.sukazyo.hytrans.var_text

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
			
			import cc.sukazyo.hytrans.var_text.Var.IllegalVarIdException
			
			"when contains letters, digits, and some allowed special characters (\"\")" in {
				noException should be thrownBy :
					Var("key-with-1_0000-and-1.1-AND-1:*//\\:123#M@%&?;,~", "value")
			}
			
			"but should failed" - {
				
				"when the key contains character that is not in the allow list(letters, digits, \"_-.*/\\|:#@%&?;,~\")" - {
					
					val table = Table(
						("Type Name", "Key"),
						("space", "key with space"),
						("Chinese", "key-with-中文"),
						("Japanese", "key-with-ジャパン"),
						("Pin Yin character", "key-with-ɑ́"),
						("latin character", "key-with-ȧ"),
						("tab", "key-with-\t"),
						("line break", "key-with-\n"),
						("line square brackets", "key-with-[]"),
						("line curly brackets", "key-with-{}"),
						("line angle brackets", "key-with-<>"),
					)
					forAll (table) { (typeName, key) =>
						s"like $typeName" in {
							an[IllegalVarIdException] should be thrownBy :
								Var(key, "value")
						}
					}
					
				}
				
				"when the key is empty" in {
					an[IllegalVarIdException] should be thrownBy :
						Var("", "value")
				}
				
			}
			
		}
		
		"when accessing its vals" - {
			
			val _v: Var = Var("my-key", "Some Random Value")
			
			"should be able to access its id" in {
				assertCompiles("_v.id == \"my-key\"")
			}
			
			"should be able to access its text" in {
				assertCompiles("_v.text == \"Some Random Value\"")
			}
			
			"should not be able to modify its id" in {
				assertDoesNotCompile("_v.id = \"new-key\"")
			}
			
			"should not be able to modify its text" in {
				assertDoesNotCompile("_v.text = \"new value!!!\"")
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
		
		"should be able to use the unapply method" - {
			
			"to unpack its id and text" in {
				val v1: Var = Var("key", "value")
				val Var(k, v) = v1
				k `shouldEqual` "key"
				v `shouldEqual` "value"
			}
			
			"to match vals in a match case" in {
				Var("key", "value") match
					case Var(k, v) =>
						k `shouldEqual` "key"
						v `shouldEqual` "value"
					case _ =>
						fail("Failed match")
			}
			
		}
		
		"when comparing" - {
			
			"to another Var" - {
				
				"with the same id and text should be equals" in {
					val v1: Var = Var("key", "value")
					val v2: Var = Var("key", "value")
					v1 shouldEqual v2
				}
				
				"with the same id but another text should not be equals" in {
					val v1: Var = Var("key", "value-1")
					val v1p: Var = v1.asText("value_1")
					v1 should not equal v1p
				}
				
			}
			
			"to its unpacked (String, String) tuple should not be equals" in {
				val v: Var = Var("key", "value")
				val vtp: (String, String) = v.unpackKV
				v should not equal vtp
			}
			
		}
		
	}
	
}
