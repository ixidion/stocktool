package de.bluemx.stocktool.fetch;

import de.bluemx.stocktool.cache.Cacheprovider;
import de.bluemx.stocktool.helper.GlobalGuiceInjector;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

/**
 * Created by teclis on 10.01.17.
 */
@Disabled
class BatchRunnerTest {
    @Test
    void updateDatabase() {
        new Cacheprovider().createCache();
        BatchRunner runner = GlobalGuiceInjector.getInjector().getInstance(BatchRunner.class);
        runner.updateDatabase();
    }

}