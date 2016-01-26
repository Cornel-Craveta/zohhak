package zohhak;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TestRule;
import org.junit.runner.Description;
import org.junit.runner.RunWith;
import org.junit.runners.model.Statement;

import com.googlecode.zohhak.api.TestWith;
import com.googlecode.zohhak.api.runners.ZohhakRunner;

@RunWith(ZohhakRunner.class)
public class ProofTest {

    @Rule
    public TestRule rule = new TestRule() {
        @Override
        public Statement apply(Statement base, Description description) {

            assertDescription(description);

            return new Statement() {
                @Override
                public void evaluate() throws Throwable {

                    base.evaluate();
                }
            };
        }

        private void assertDescription(Description description) {

            String methodName = description.getMethodName();

            if (methodName.startsWith("test1")) {

                assertThat(description.getAnnotation(IgnoredOnRestrictedSystems.class)).as("test1").isNull();
            }

            else if (methodName.startsWith("test2")) {

                assertThat(description.getAnnotation(IgnoredOnRestrictedSystems.class)).as("test2").isNotNull();
            }

            else if (methodName.startsWith("test3")) {
                assertThat(description.getAnnotation(IgnoredOnRestrictedSystems.class)).as("test3").isNotNull();
            }

            else {
                Assert.fail();
            }
        }
    };

    @Test
    public void test1() throws Exception {

        Assert.assertTrue(true);
    }

    @IgnoredOnRestrictedSystems
    @Test
    public void test2() throws Exception {

        Assert.assertTrue(true);
    }

    @IgnoredOnRestrictedSystems
    @TestWith({
            "arg1",
            "arg2"
    })
    public void test3(String arg) throws Exception {

        Assert.assertTrue(true);
    }
}
