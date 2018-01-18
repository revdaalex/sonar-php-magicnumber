package org.sonar.samples.php;

import com.google.common.collect.ImmutableList;
import org.sonar.plugins.php.api.visitors.PHPCustomRulesDefinition;
import org.sonar.samples.php.checks.MagicNumberCheck;

/**
 * Extension point to define a PHP rule repository.
 */
public class PHPRulesDefinition extends PHPCustomRulesDefinition {

    /**
     * Provide the repository name
     */
    @Override
    public String repositoryName() {
        return "PHP Magic Number";
    }

    /**
     * Provide the repository key
     */
    @Override
    public String repositoryKey() {
        return "mnd";
    }

    /**
     * Provide the list of checks class that implements rules
     * to be part of the rule repository
     */
    @Override
    public ImmutableList<Class> checkClasses() {
        return ImmutableList.of(MagicNumberCheck.class);
    }
}
