package org.sonar.samples.php.checks;

import org.junit.Test;
import org.sonar.plugins.php.api.tests.PHPCheckVerifier;
import java.io.File;

public class MagicNumberCheckTest {
    @Test
    public void checkMagicNumber() throws Exception {
        PHPCheckVerifier.verify(new File("src/test/resources/checks/magicNumberCheck.php"), new MagicNumberCheck());
    }
}