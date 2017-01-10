package de.bluemx.stocktool.fetch;

import com.google.inject.Guice;
import de.bluemx.stocktool.helper.DefaultInject;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

/**
 * Created by teclis on 10.01.17.
 */
@Disabled
class BatchRunnerTest {
    @Test
    void updateDatabase() {
        BatchRunner runner = Guice.createInjector(new DefaultInject()).getInstance(BatchRunner.class);
        runner.updateDatabase();
    }

}