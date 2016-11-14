/*
 *  This file should be viewed and complied with GBK encoding
 *  Another choice is to use native2ascii tool to convert the Chinese into unicode and then complie
 */

package com.finance.dennis.loader;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;

/**
 * Created by XiaoNiuniu on 11/12/2016.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:spring/domain-context.xml", "classpath:spring/hsqldb-datasource.xml", "classpath:spring/jpa.xml"})
public class JsoupTest {

    private static final String CHARSET_NAME = "GB2312";
    private static final String DEFAULT_BLANK_BASE_URL = "";
    private static final String ATTR_FUND_TYPE = "tit";
    private static final String ALL_OPEN_FUNDS_TYPE = "1";

    /*
    http://jingzhi.funds.hexun.com/jz/
    div[class=fundSecNav]>a

    http://jingzhi.funds.hexun.com/jz/JsonData/KaifangJingz.aspx?subtype=3

    http://jingzhi.funds.hexun.com/database/jzzs.aspx?fundcode=110025&startdate=1900-01-01&enddate=2016-11-11
    table[class=n_table m_table]
    */

    @Test
    public void testMainPage() {

        InputStream in = JsoupTest.class.getClassLoader().getResourceAsStream("files\\jingzhi_main_page.html");
        try {
            Document doc = Jsoup.parse(in, CHARSET_NAME, DEFAULT_BLANK_BASE_URL);
            Elements elements = doc.select("div[class=fundSecNav]>a");
            Assert.assertEquals(14, elements.size());

            ArrayList<String> types = new ArrayList<String>();
            ArrayList<String> names = new ArrayList<String>();
            for (Element element : elements) {
                // Skip the type for all funds
                if (!ALL_OPEN_FUNDS_TYPE.equals(element.attr(ATTR_FUND_TYPE))) {
                    types.add(element.attr(ATTR_FUND_TYPE));
                    names.add(element.text());
                    System.out.println(element.text());
                }
            }

            String[] expectedTypes = {"2", "3", "4", "6", "8", "9", "7", "11", "10", "12", "5", "40", "70"};
            String[] expectedNames = {"股票型", "配置型", "债券型", "保本型", "ETF", "LOF", "QDII", "FOF", "联接", "创新型", "指数型", "货币型", "创新理财型"};

            Assert.assertArrayEquals(expectedTypes, types.toArray());
            Assert.assertArrayEquals(expectedNames, names.toArray());

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testFundPage() {

/*        InputStream in = JsoupTest.class.getClassLoader().getResourceAsStream("files\\jingzhi_main_page.html");
        try {
            Document doc = Jsoup.parse(in, CHARSET_NAME, DEFAULT_BLANK_BASE_URL);
            Elements elements = doc.select("div[class=fundSecNav]>a");
            Assert.assertEquals(14, elements.size());

            ArrayList<String> types = new ArrayList<String>();
            ArrayList<String> names = new ArrayList<String>();
            for (Element element : elements) {
                // Skip the type for all funds
                if (!ALL_OPEN_FUNDS_TYPE.equals(element.attr(ATTR_FUND_TYPE))) {
                    types.add(element.attr(ATTR_FUND_TYPE));
                    names.add(element.text());
                    System.out.println(element.text());
                }
            }

            String[] expectedTypes = {"2", "3", "4", "6", "8", "9", "7", "11", "10", "12", "5", "40", "70"};
            String[] expectedNames = {"股票型", "配置型", "债券型", "保本型", "ETF", "LOF", "QDII", "FOF", "联接", "创新型", "指数型", "货币型", "创新理财型"};

            Assert.assertArrayEquals(expectedTypes, types.toArray());
            Assert.assertArrayEquals(expectedNames, names.toArray());

        } catch (IOException e) {
            e.printStackTrace();
        }*/
    }


    @Test
    public void testEncoding() throws UnsupportedEncodingException {
        String chinese = "中文";//java内部编码
        String gbkChinese = new String(chinese.getBytes("GBK"),"ISO-8859-1");
        String unicodeChinese = new String(gbkChinese.getBytes("ISO-8859-1"),"GBK");
        System.out.println(unicodeChinese);//中文
        String utf8Chinese = new String(unicodeChinese.getBytes("UTF-8"),"ISO-8859-1");
        System.out.println(utf8Chinese);//乱码
        unicodeChinese = new String(utf8Chinese.getBytes("ISO-8859-1"),"UTF-8");//java内部编码
        System.out.println(unicodeChinese);//中文
    }
}
