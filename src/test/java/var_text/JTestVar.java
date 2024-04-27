package cc.sukazyo.hytrans.var_text;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

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
				
				@Nested
				@DisplayName("when the key contains character that is not in the allow list(letters, digits, \"_-.*/\\|:#@%&?;,~\")")
				class WhenKeyInvalid {
					
					@Test
					@DisplayName("like space")
					void when_space () {
						assertThrows(Var.IllegalVarIdException.class,
								() -> new Var("key with space", "value"));
					}
					
					@Test
					@DisplayName("like Chinese")
					void when_chinese () {
						assertThrows(Var.IllegalVarIdException.class,
								() -> new Var("key-with-中文", "value"));
					}
					
					@Test
					@DisplayName("like Japanese")
					void when_japanese () {
						assertThrows(Var.IllegalVarIdException.class,
								() -> new Var("key-with-ジャパン", "value"));
					}
					
					@Test
					@DisplayName("like Pin Yin characters")
					void when_pin_yin () {
						assertThrows(Var.IllegalVarIdException.class,
								() -> new Var("key-with-ɑ́", "value"));
					}
					
					@Test
					@DisplayName("like latin character")
					void when_latin () {
						assertThrows(Var.IllegalVarIdException.class,
								() -> new Var("key-with-ȧ", "value"));
					}
					
					@Test
					@DisplayName("like tab")
					void when_tab () {
						assertThrows(Var.IllegalVarIdException.class,
								() -> new Var("key-with-\t", "value"));
					}
					
					@Test
					@DisplayName("like line break")
					void when_line_breaks () {
						assertThrows(Var.IllegalVarIdException.class,
								() -> new Var("key-with-\n", "value"));
					}
					
					@Test
					@DisplayName("like square brackets")
					void when_square_brackets () {
						assertThrows(Var.IllegalVarIdException.class,
								() -> new Var("key-with-[]", "value"));
					}
					
					@Test
					@DisplayName("like curly brackets")
					void when_curly_brackets () {
						assertThrows(Var.IllegalVarIdException.class,
								() -> new Var("key-with-{}", "value"));
					}
					
					@Test
					@DisplayName("like angle brackets")
					void when_angle_brackets () {
						assertThrows(Var.IllegalVarIdException.class,
								() -> new Var("key-with-<>", "value"));
					}
					
				}
				
				@Test
				@DisplayName("when the key is empty")
				void when_key_empty () {
					assertThrows(Var.IllegalVarIdException.class,
							() -> new Var("", "value"));
				}
				
			}
			
		}
		
	}
	
}
