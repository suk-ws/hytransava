package cc.sukazyo.hytrans.var_text;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.junit.jupiter.api.Assertions.*;

public class JTestVar {
	
	@Nested
	@DisplayName("A Var")
	class OneVar {
		
		@Nested
		@DisplayName("should be able to created")
		class NewOne {
			
			private final String my_key = "var-key";
			private final String my_value = "Some Random Value";
			
			@Test
			@DisplayName("using new keyword")
			void using_new_keyword () {
				final Var var = new Var(my_key, my_value);
				assertEquals(var.id(), my_key);
				assertEquals(var.text(), my_value);
			}
			
			@Test
			@DisplayName("using apply method from scala")
			void using_scala_apply () {
				final Var var = Var.apply(my_key, my_value);
				assertEquals(var.id(), my_key);
				assertEquals(var.text(), my_value);
			}
			
			@Test
			@DisplayName("by converted from ...")
			@Disabled("converting from scala types may be strange, while converting from java types is not implemented.")
			void conversions () {}
			
			@Test
			@DisplayName("when contains letters, digits, and some allowed special characters (\"\")")
			void accept_letters_digits_some_specials () {
				assertDoesNotThrow(() -> {
					new Var("key-with-1_0000-and-1.1-AND-1:*//\\:123#M@%&?;,~", "value");
				});
			}
			
			@Nested
			@DisplayName("but should failed")
			class WillFailWhen {
				
				@DisplayName("when the key contains character that is not in the allow list(letters, digits, \"_-.*/\\|:#@%&?;,~\")")
				@ParameterizedTest(name = "like {0}")
				@CsvSource(delimiter = '|', value = {
						/* "'Type Name', 'Key'" */
						" 'space'                | 'key with space'    ",
						" 'Chinese'              | 'key-with-中文'     ",
						" 'Japanese'             | 'key-with-ジャパン' ",
						" 'Pin Yin character'    | 'key-with-ɑ́'        ",
						" 'latin character'      | 'key-with-ȧ'        ",
						" 'tab'                  | 'key-with-\t'       ",
						" 'line break'           | 'key-with-\n'       ",
						" 'line square brackets' | 'key-with-[]'       ",
						" 'line curly brackets'  | 'key-with-{}'       ",
						" 'line angle brackets'  | 'key-with-<>'       ",
				})
				void when_key_invalid (String _name, String key) {
					assertThrows(Var.IllegalVarIdException.class,
							() -> new Var(key, "value"));
				}
				
				@Test
				@DisplayName("when the key is empty")
				void when_key_empty () {
					assertThrows(Var.IllegalVarIdException.class,
							() -> new Var("", "value"));
				}
				
			}
			
		}
		
		@Nested
		@DisplayName("should be able to converted")
		class Converting {
			
			@Test
			@DisplayName("to another Var with a new id and the same text, using `asId` method")
			void to_another_with_new_id () {
				final Var v1 = new Var("key", "value");
				final Var v2 = v1.asId("new-key");
				assertEquals(v2.id(), "new-key");
				assertEquals(v2.text(), v1.text());
			}
			
			@Test
			@DisplayName("to another Var with a new text and the same id, using `asText` method")
			void to_another_with_new_text () {
				final Var v1 = new Var("key", "value");
				final Var v2 = v1.asText("sOME nEW vALUe");
				assertEquals(v2.id(), v1.id());
				assertEquals(v2.text(), "sOME nEW vALUe");
			}
			
		}
		
		@Nested
		@DisplayName("when comparing")
		class Comparing {
			
			@Test
			@DisplayName("should be equal to another Var with the same id and text")
			void equal_to_same_id_and_text () {
				final Var v1 = new Var("key", "value");
				final Var v2 = new Var("key", "value");
				assertEquals(v1, v2);
			}
			
			@Test
			@DisplayName("should not be equal to another Var with different id or text")
			void not_equal_to_different_id_or_text () {
				final Var v1 = new Var("key-1", "value-1");
				assertNotEquals(v1, v1.asId("key_1"));
				assertNotEquals(v1, v1.asText("value_1"));
			}
			
		}
		
	}
	
}
