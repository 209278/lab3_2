package edu.iis.mto.staticmock;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

@RunWith(PowerMockRunner.class)
@PrepareForTest({ConfigurationLoader.class, NewsReaderFactory.class})
class NewsLoaderTest {

    @Test
    void loadNews() {
    }
}