package cz.muni.fi.pb162.project.exceptions;

import cz.muni.fi.pb162.project.helper.BasicRulesTester;
import org.junit.Test;

import static org.junit.Assert.fail;

/**
 * Class checking exceptions constructors.
 *
 * @author Marek Sabo
 */
public class ExceptionsTest {

    @Test
    public void shouldInheritFromException() {
        BasicRulesTester.testAncestor(Exception.class, EmptyDrawableException.class);
        BasicRulesTester.testAncestor(Exception.class, MissingVerticesException.class);
        BasicRulesTester.testAncestor(Exception.class, TransparentColorException.class);
    }

    @Test
    public void checkConstructors() {
        try {
            EmptyDrawableException.class.getConstructor(String.class);
            EmptyDrawableException.class.getConstructor(String.class, Throwable.class);
            MissingVerticesException.class.getConstructor(String.class);
            MissingVerticesException.class.getConstructor(String.class, Throwable.class);
            TransparentColorException.class.getConstructor(String.class);
            TransparentColorException.class.getConstructor(String.class, Throwable.class);
        } catch (NoSuchMethodException e) {
            fail("Missing constructor: " + e.getMessage());
        }
    }
}
