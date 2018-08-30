package cz.muni.fi.pb162.project.helper;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Radek Oslejsek, Petr Adamek, Marek Sabo
 */
public class BasicRulesTester extends org.junit.Assert {

    public static final double DELTA = 0.001;
    private static final String MESSAGE = "Spustitelna trida";

    private static boolean isConstant(int mod) {
        return Modifier.isStatic(mod) && Modifier.isFinal(mod);
    }

    /**
     * Tests that class contains a proper main method.
     *
     * @param clazz tested class
     */
    public static void testRunnableClass(Class clazz) {
        for (Method method : clazz.getMethods()) {
            if (method.getName().equals("main")) {
                assertTrue(MESSAGE, Modifier.isStatic(method.getModifiers()));
                assertTrue(MESSAGE, Modifier.isPublic(method.getModifiers()));
                assertTrue(MESSAGE, method.getReturnType().equals(void.class));
                Class<?>[] params = method.getParameterTypes();
                assertNotNull(MESSAGE, params);
                assertTrue(MESSAGE, params.length == 1);
                assertTrue(MESSAGE, params[0].isArray());
                return; // ok
            }
        }
        fail(MESSAGE);
    }

    public static Field[] getFields(Class clazz) {
        Field[] fields = clazz.getDeclaredFields();
        List<Field> nonSyntheticFields = new ArrayList<>();

        for (Field field : fields) {
            if (!field.isSynthetic()) {
                nonSyntheticFields.add(field);
            }
        }
        return nonSyntheticFields.toArray(new Field[nonSyntheticFields.size()]);
    }

    public static void attributesFinal(Class clazz) {
        Field[] attributes = BasicRulesTester.getFields(clazz);
        for (Field field : attributes) {
            assertTrue("Attributes should be final", Modifier.isFinal(field.getModifiers()));
        }
    }

    public static void attributesAmount(Class clazz, int expected) {
        // + 1 due to coverage
        assertTrue("Useless attributes", clazz.getDeclaredFields().length <= (expected + 1));
    }


    public static void methodsAmount(Class clazz, int expected) {
        // + 1 due to coverage
        assertTrue("Useless methods", clazz.getDeclaredMethods().length <= (expected + 1));
    }

    /**
     * Tests class inheritance ancestor.
     *
     * @param ancestor ancestor class
     * @param checkedClass class to be checked
     */
    public static void testAncestor(Class ancestor, Class checkedClass) {
        assertEquals("Class " + checkedClass + "  should inherit from class " + ancestor,
                ancestor, checkedClass.getSuperclass());
    }

}
