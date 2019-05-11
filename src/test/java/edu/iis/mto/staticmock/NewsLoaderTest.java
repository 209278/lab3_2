package edu.iis.mto.staticmock;

import edu.iis.mto.staticmock.reader.NewsReader;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.modules.junit4.PowerMockRunner;
import org.powermock.reflect.Whitebox;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.Matchers.is;

@RunWith(PowerMockRunner.class)
@PrepareForTest({ConfigurationLoader.class, NewsReaderFactory.class, PublishableNews.class})
public class NewsLoaderTest {

    ConfigurationLoader configurationLoader = PowerMockito.mock(ConfigurationLoader.class);
    Configuration configuration = PowerMockito.mock(Configuration.class);
    NewsReader newsReader = PowerMockito.mock(NewsReader.class);
    IncomingNews incomingNews = PowerMockito.mock(IncomingNews.class);
    PublishableNews publishableNews;

    List<IncomingInfo> incomingInfoList;


    String readerType = "a";
    NewsLoader newsLoader;

    @Before
    public void init(){

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
    public void checkIfAllContentIsAddedProperly() {
        incomingInfoList.add(new IncomingInfo("NONE", SubsciptionType.NONE));
        incomingInfoList.add(new IncomingInfo("A", SubsciptionType.A));
        incomingInfoList.add(new IncomingInfo("B", SubsciptionType.B));
        incomingInfoList.add(new IncomingInfo("C", SubsciptionType.C));
        newsLoader.loadNews();

        List<String> publicContent = Whitebox.getInternalState(publishableNews, "publicContent");
        List<String> subscribentContent = Whitebox.getInternalState(publishableNews, "subscribentContent");

        Assert.assertThat(publicContent.size(), is(1));
        Assert.assertThat(subscribentContent.size(), is(3));
    }
}