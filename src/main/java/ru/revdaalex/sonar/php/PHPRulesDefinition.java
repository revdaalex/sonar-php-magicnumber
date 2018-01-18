package ru.revdaalex.sonar.php;

import com.google.common.collect.ImmutableList;
import org.sonar.plugins.php.api.visitors.PHPCustomRulesDefinition;
import ru.revdaalex.sonar.php.checks.MagicNumberCheck;

/**
 * Extension point to define a PHP rule repository.
 */
public class PHPRulesDefinition extends PHPCustomRulesDefinition {

    /**
     * Provide the repository name
     */
    @Override
    public String repositoryName() {
        return "Common PHP";
    }

    /**
     * Provide the repository key
     */
    @Override
    public String repositoryKey() {
        return "common-php";
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
