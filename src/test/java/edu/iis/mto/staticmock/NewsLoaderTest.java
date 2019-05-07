package edu.iis.mto.staticmock;

import edu.iis.mto.staticmock.reader.NewsReader;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import java.util.ArrayList;
import java.util.List;

@RunWith(PowerMockRunner.class)
@PrepareForTest({ConfigurationLoader.class, NewsReaderFactory.class})
class NewsLoaderTest {

    ConfigurationLoader configurationLoader = PowerMockito.mock(ConfigurationLoader.class);
    Configuration configuration = PowerMockito.mock(Configuration.class);
    NewsReader newsReader = PowerMockito.mock(NewsReader.class);
    IncomingNews incomingNews = PowerMockito.mock(IncomingNews.class);
    PublishableNews publishableNews;

    List<IncomingInfo> incomingInfoList;


    String readerType = "a";
    NewsLoader newsLoader;

    @BeforeEach
    void init(){

        newsLoader = new NewsLoader();
        incomingInfoList = new ArrayList<>();
        publishableNews = new PublishableNews();

        PowerMockito.mockStatic(ConfigurationLoader.class);
        PowerMockito.when(ConfigurationLoader.getInstance()).thenReturn(configurationLoader);
        PowerMockito.when(configurationLoader.loadConfiguration()).thenReturn(configuration);
        PowerMockito.when(configuration.getReaderType()).thenReturn(readerType);

        PowerMockito.mockStatic(NewsReaderFactory.class);
        PowerMockito.when(NewsReaderFactory.getReader(readerType)).thenReturn(newsReader);

        PowerMockito.when(newsReader.read()).thenReturn(incomingNews);
        PowerMockito.when(incomingNews.elems()).thenReturn(incomingInfoList);

        PowerMockito.mockStatic(PublishableNews.class);
        PowerMockito.when(PublishableNews.create()).thenReturn(publishableNews);

    }
    @Test
    void loadNews() {
        newsLoader.loadNews();
    }
}