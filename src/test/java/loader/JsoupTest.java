package loader;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.io.IOException;
import java.io.InputStream;

/**
 * Created by XiaoNiuniu on 11/12/2016.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:spring/domain-context.xml","classpath:spring/hsqldb-datasource.xml","classpath:spring/jpa.xml"})
public class JsoupTest {

    private static final String CHARSET_NAME = "GB2312";
    private static final String DEFAULT_BLANK_BASE_URL = "";
    private static final String ATTR_FUND_TYPE = "tit";
    private static final String ALL_OPEN_FUNDS_TYPE = "1";

    @Test
    public void test() {

        InputStream in = JsoupTest.class.getClassLoader().getResourceAsStream("files\\jingzhi_main_page.html");
        try {
            Document doc = Jsoup.parse(in, CHARSET_NAME, DEFAULT_BLANK_BASE_URL);
            Elements elements = doc.select("div[class=fundSecNav]>a");
            for (Element element : elements) {
                // Skip the type for all funds
                if (!ALL_OPEN_FUNDS_TYPE.equals(element.attr(ATTR_FUND_TYPE))) {
                    System.out.println(element.attr(ATTR_FUND_TYPE) + " " + element.text());
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
