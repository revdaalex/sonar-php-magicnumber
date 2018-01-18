package org.sonar.samples.php;

import org.sonar.api.Plugin;

/**
 * Extension point to define a Sonar Plugin.
 */
public class PHPMagicNumberPlugin implements Plugin {

    @Override
    public void define(Context context) {
        context.addExtension(PHPRulesDefinition.class);
    }
}
