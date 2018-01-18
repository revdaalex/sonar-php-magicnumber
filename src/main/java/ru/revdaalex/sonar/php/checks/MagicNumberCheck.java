package ru.revdaalex.sonar.php.checks;

import org.sonar.check.Priority;
import org.sonar.check.Rule;
import org.sonar.check.RuleProperty;
import org.sonar.plugins.php.api.tree.Tree.Kind;
import org.sonar.plugins.php.api.tree.declaration.ClassPropertyDeclarationTree;
import org.sonar.plugins.php.api.tree.declaration.ConstantDeclarationTree;
import org.sonar.plugins.php.api.tree.declaration.VariableDeclarationTree;
import org.sonar.plugins.php.api.tree.expression.ArrayInitializerBracketTree;
import org.sonar.plugins.php.api.tree.expression.LiteralTree;
import org.sonar.plugins.php.api.visitors.PHPVisitorCheck;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.ParseException;
import java.util.*;

/**
 * Check magic number in php.
 */
@Rule(
        key = "S1",
        priority = Priority.MAJOR,
        name = "Magic number should not be used.",
        tags = {"convention"}
)
public class MagicNumberCheck extends PHPVisitorCheck {

    private static final String DEFAULT_AUTHORIZED_NUMBERS = "-1,0,1";

    @RuleProperty(
            key = "Authorized numbers",
            description = "Comma separated list of authorized numbers. Example: -1,0,1,2",
            defaultValue = "" + DEFAULT_AUTHORIZED_NUMBERS)
    public String authorizedNumbers = DEFAULT_AUTHORIZED_NUMBERS;
    private List<BigDecimal> authorizedNumbersList = null;

    @Override
    public void visitLiteral(LiteralTree tree) {
        this.authorizedNumbersList = new ArrayList<>();
        for (String s : authorizedNumbers.split(",")) {
            authorizedNumbersList.add(new BigDecimal(s.trim()));
        }
        if (isNumberLiteral(tree)) {
            DecimalFormat decimalFormat = new DecimalFormat();
            decimalFormat.setDecimalFormatSymbols(new DecimalFormatSymbols(Locale.ENGLISH));
            decimalFormat.setParseBigDecimal(true);
            BigDecimal checked = null;
            try {
                checked = (BigDecimal) decimalFormat.parse(tree.value());
            } catch (ParseException e) {
                // ignore
            }
            if (checked != null && !isExcluded(checked)) {
                context().newIssue(this, tree, "Assign this magic number " + tree.value() + " to a well-named constant, and use the constant instead.");
            }
        }
    }

    private static boolean isNumberLiteral(LiteralTree tree) {
        return tree.is(Kind.NUMERIC_LITERAL);
    }

    private boolean isExcluded(BigDecimal bigDecimal) {
        for (BigDecimal bd : this.authorizedNumbersList) {
            if (bigDecimal.compareTo(bd) == 0) {
                return true;
            }
        }
        return false;
    }

    @Override
    public void visitVariableDeclaration(VariableDeclarationTree tree) {
        if (tree.is(Kind.CONSTANT_DECLARATION)) {
            super.visitVariableDeclaration(tree);
        }
    }

    @Override
    public void visitClassPropertyDeclaration(ClassPropertyDeclarationTree tree) {
        if (!tree.is(Kind.CLASS_CONSTANT_PROPERTY_DECLARATION)) {
            super.visitClassPropertyDeclaration(tree);
        }
    }

    @Override
    public void visitConstDeclaration(ConstantDeclarationTree tree) {
        if (!tree.is(Kind.CONSTANT_DECLARATION)) {
            super.visitConstDeclaration(tree);
        }
    }

    @Override
    public void visitArrayInitializerBracket(ArrayInitializerBracketTree tree) {
        // Ignore array.
    }
}
