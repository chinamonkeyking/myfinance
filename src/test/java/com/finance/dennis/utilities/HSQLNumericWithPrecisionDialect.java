package com.finance.dennis.utilities;

import org.hibernate.dialect.HSQLDialect;
import org.hsqldb.types.Types;

/**
 * Created by XiaoNiuniu on 4/18/2016.
 */

/**
 * This will set HSQL db numeric types to 38,6
 * So that BigDecimal to have 6 decimal places and it is easier for testing
 */
public class HSQLNumericWithPrecisionDialect extends HSQLDialect {
    public HSQLNumericWithPrecisionDialect() {
        registerColumnType(Types.NUMERIC, "numeric(38,6)");
    }
}
