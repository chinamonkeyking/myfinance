/*
 *  This file should be viewed and complied with GBK encoding
 *  Another choice is to use native2ascii tool to convert the Chinese into unicode and then complie
 */

package com.finance.dennis.loader;

import jdk.nashorn.internal.runtime.regexp.joni.encoding.IntHolder;
import org.apache.commons.io.IOUtils;
import org.json.JSONArray;
import org.json.JSONObject;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.junit.Assert;
import org.junit.Test;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by XiaoNiuniu on 11/12/2016.
 */
//@RunWith(SpringJUnit4ClassRunner.class)
//@ContextConfiguration(locations = {"classpath:spring/domain-context.xml", "classpath:spring/hsqldb-datasource.xml", "classpath:spring/jpa.xml"})
public class JsoupTest {

    private static final String CHARSET_NAME = "GB2312";
    private static final String DEFAULT_BLANK_BASE_URL = "";
    private static final String ATTR_FUND_TYPE = "tit";
    private static final String ALL_OPEN_FUNDS_TYPE = "1";

    private static final String USERAGNET = "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_9_2) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/33.0.1750.152 Safari/537.36";
    private static final String HX_FUND_URL_MAINPAGE = "http://jingzhi.funds.hexun.com/jz";
    private static final String HX_FUND_URL_SUBPAGE_BASE = "http://jingzhi.funds.hexun.com/jz/JsonData/KaifangJingz.aspx?subtype=";
    private static final String HX_FUND_URL_SUBPAGE_MONEY_BASE = "http://jingzhi.funds.hexun.com/jz/JsonData/HuobiJingz.aspx?subtype=";
    private static final String HX_FUND_URL_NETVALUE_FOR_ONE_FUND_BASE = "http://jingzhi.funds.hexun.com/database/jzzs.aspx?startdate=1900-01-01&enddate=2099-12-31&fundcode=";
    private static final String HX_FUND_URL_NETVALUE_FOR_ONE_MONEY_FUND_BASE = "http://jingzhi.funds.hexun.com/database/jzzshb.aspx?startdate=1900-01-01&enddate=2099-12-31&fundcode=";

    private static final String HX_FUND_SELECTOR_ALL_SUBTYPE = "div[class=fundSecNav]>a";
    private static final String HX_FUND_SELECTOR_NETVALUE_FOR_ONE_FUND = "table[class=\"n_table m_table\"] > tbody > tr";
    private static final String HX_FUND_PATTERN_ALL_FUND_IN_A_SUBTYPE = "callback\\((.*)\\)";

    private static final HashMap<String, String> HF_MONEY_FUND_TYPES = new HashMap<String, String>() {{
        put("40", "1");
        put("70", "1");
    }};

    private static final int DEFAULT_SUBTYPE_CNT = 20;
    private static final int DEFAULT_FUND_CNT = 5000;

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
            Elements elements = doc.select(HX_FUND_SELECTOR_ALL_SUBTYPE);
            Assert.assertEquals(14, elements.size());

            ArrayList<String> types = new ArrayList();
            ArrayList<String> names = new ArrayList();
            for (Element element : elements) {
                // Skip the type for all funds
                if (!ALL_OPEN_FUNDS_TYPE.equals(element.attr(ATTR_FUND_TYPE))) {
                    types.add(element.attr(ATTR_FUND_TYPE));
                    names.add(element.text());
                    //System.out.println(element.text());
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
    public void testSubTypePage() {

        InputStream in = JsoupTest.class.getClassLoader().getResourceAsStream("files\\jingzhi_subtype.html");

        try {
            String fileContent = IOUtils.toString(in, "GBK");
            Pattern p = Pattern.compile(HX_FUND_PATTERN_ALL_FUND_IN_A_SUBTYPE);
            Matcher m = p.matcher(fileContent);
            String fundContent = "{}";
            if (m.find()) {
                fundContent = m.group(1);
            }

            JSONObject funds = new JSONObject(fundContent);
            int fundCnt = funds.getInt("sum");
            Assert.assertEquals(1964, fundCnt);

            final IntHolder cnt = new IntHolder();
            JSONArray fundList = funds.getJSONArray("list");
            fundList.forEach(o -> {
                        //JSONObject fund = (JSONObject) o;
                        //System.out.println(fund.getString("fundCode") + fund.getString("fundName"));
                        cnt.value++;
                    }
            );
            Assert.assertEquals(fundCnt, cnt.value);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testFundPage() {

        InputStream in = JsoupTest.class.getClassLoader().getResourceAsStream("files\\jingzhi_sample_110025.html");
        try {
            Document doc = Jsoup.parse(in, CHARSET_NAME, DEFAULT_BLANK_BASE_URL);
            Elements elements = doc.select(HX_FUND_SELECTOR_NETVALUE_FOR_ONE_FUND);
            Assert.assertEquals(3, elements.size());

            ArrayList<String> dates = new ArrayList();
            ArrayList<String> netValues = new ArrayList();
            ArrayList<String> AccNetValues = new ArrayList();
            ArrayList<String> dailyChangePcts = new ArrayList();
            for (Element element : elements) {
                Elements record = element.select("td");
                Assert.assertEquals(4, record.size());
                dates.add(record.get(0).text());
                netValues.add(record.get(1).text());
                AccNetValues.add(record.get(2).text());
                dailyChangePcts.add(record.get(3).text());
            }

            String[] expectedDates = {"2016-11-11", "2016-11-10", "2016-11-09"};
            String[] expectedNetValues = {"1.0670", "1.0190", "1.0060"};
            String[] expectedAccNetValues = {"1.0670", "1.0190", "1.0060"};
            String[] expectedDailyChangePcts = {"4.71%", "1.29%", "1.41%"};

            Assert.assertArrayEquals(expectedDates, dates.toArray());
            Assert.assertArrayEquals(expectedNetValues, netValues.toArray());
            Assert.assertArrayEquals(expectedAccNetValues, AccNetValues.toArray());
            Assert.assertArrayEquals(expectedDailyChangePcts, dailyChangePcts.toArray());

        } catch (IOException e) {
            e.printStackTrace();
        }
    }


//    @Test
//    public void testEncoding() throws UnsupportedEncodingException {
//        String chinese = "中文";//java内部编码
//        String gbkChinese = new String(chinese.getBytes("GBK"),"ISO-8859-1");
//        String unicodeChinese = new String(gbkChinese.getBytes("ISO-8859-1"),"GBK");
//        System.out.println(unicodeChinese);//中文
//        String utf8Chinese = new String(unicodeChinese.getBytes("UTF-8"),"ISO-8859-1");
//        System.out.println(utf8Chinese);//乱码
//        unicodeChinese = new String(utf8Chinese.getBytes("ISO-8859-1"),"UTF-8");//java内部编码
//        System.out.println(unicodeChinese);//中文
//    }

    @Test
    public void testWorkFlow() throws Exception {
        // Get main page
        ArrayList<HXFundType> fundTypes = getHxFundTypes();
        System.out.println(fundTypes);

        // Get funds in each sub type
        List<HXFund> funds = getHxFunds(fundTypes);
    }

    private List<HXFund> getHxFunds(ArrayList<HXFundType> fundTypes) throws IOException {
        ArrayList<HXFund> funds = new ArrayList<>(DEFAULT_FUND_CNT);
        for (HXFundType fundType : fundTypes) {
            Document doc = null;
            try {
                // Get page
                String url = constructSubPageURL(fundType);
                System.out.println(url);
                doc = readFromUrl(url);

                // Get the content containing fund information
                String content = doc.html();
                Pattern p = Pattern.compile(HX_FUND_PATTERN_ALL_FUND_IN_A_SUBTYPE);
                Matcher m = p.matcher(content);
                String fundContent = "{}";
                if (m.find()) {
                    fundContent = m.group(1);
                }

                // Parse content to funds
                JSONObject fundsInPage = new JSONObject(fundContent);
                int fundCnt = fundsInPage.getInt("sum");
                System.out.print(fundType + "->");
                System.out.println(fundCnt);

//                final IntHolder cnt = new IntHolder();
//                JSONArray fundList = fundsInPage.getJSONArray("list");
//                fundList.forEach(o -> {
//                            //JSONObject fund = (JSONObject) o;
//                            //System.out.println(fund.getString("fundCode") + fund.getString("fundName"));
//                            cnt.value++;
//                        }
//                );
//                Assert.assertEquals(fundCnt, cnt.value);
            } catch (IOException e) {
                e.printStackTrace();
                throw e;
            }
        }
        return funds;
    }

    private String constructSubPageURL(HXFundType fundType) {
        if (HF_MONEY_FUND_TYPES.containsKey(fundType.getType())) {
            return HX_FUND_URL_SUBPAGE_MONEY_BASE + fundType.getType();
        }
        return HX_FUND_URL_SUBPAGE_BASE + fundType.getType();
    }

    private Document readFromUrl(String url) throws IOException {
        // userAgent() for some websites is a must
        // maxBodySize() is a must if the content is more than 1MB (The default maximum is 1MB）
        // ignoreContentType(true) sometimes is a must. otherwise error

        //Connection.Response response = Jsoup.connect(url).ignoreContentType(true).userAgent(USERAGNET).maxBodySize(0).execute();
        //System.out.println(response.charset());
        //response.charset(CHARSET_NAME);
        //System.out.println(response.charset());
        //Document doc = response.parse();
        //System.out.println(doc.charset());
        //System.out.println(Charset.isSupported("UTF8"));
        //return doc;

        return Jsoup.connect(url).ignoreContentType(true).userAgent(USERAGNET).maxBodySize(0).execute().charset(CHARSET_NAME).parse();
    }

    private ArrayList<HXFundType> getHxFundTypes() throws IOException {
        Document doc = null;
        try {
            // userAgent() for some websites is a must
            // maxBodySize() is a must if the content is more than 1MB
            //doc = Jsoup.connect(HX_FUND_URL_MAINPAGE).userAgent(USERAGNET).maxBodySize(0).get();
            doc = readFromUrl(HX_FUND_URL_MAINPAGE);
        } catch (IOException e) {
            e.printStackTrace();
            throw e;
        }
        ArrayList<HXFundType> fundTypes = new ArrayList<>(DEFAULT_SUBTYPE_CNT);
        Elements types = doc.select(HX_FUND_SELECTOR_ALL_SUBTYPE);
        for (Element type : types) {
            // Skip the type for all funds
            if (!ALL_OPEN_FUNDS_TYPE.equals(type.attr(ATTR_FUND_TYPE))) {
                fundTypes.add(new HXFundType(type.text(), type.attr(ATTR_FUND_TYPE)));
            }
        }
        return fundTypes;
    }

    static final class HXFundType {
        private final String name;
        private final String type;

        public HXFundType(String name, String type) {
            this.name = name;
            this.type = type;
        }

        public String getName() {
            return name;
        }

        public String getType() {
            return type;
        }

        @Override
        public String toString() {
            return "HXFundType{" +
                    "name='" + name + '\'' +
                    ", type='" + type + '\'' +
                    '}';
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;

            HXFundType that = (HXFundType) o;

            if (!name.equals(that.name)) return false;
            return type.equals(that.type);

        }

        @Override
        public int hashCode() {
            int result = name.hashCode();
            result = 31 * result + type.hashCode();
            return result;
        }
    }

    static final class HXFund {
        private final String name;
        private final String code;
        private final HXFundType hxFundType;

        public HXFund(String name, String code, HXFundType hxFundType) {
            this.name = name;
            this.code = code;
            this.hxFundType = hxFundType;
        }

        public String getName() {
            return name;
        }

        public String getCode() {
            return code;
        }

        public HXFundType getHxFundType() {
            return hxFundType;
        }

        @Override
        public String toString() {
            return "HXFund{" +
                    "name='" + name + '\'' +
                    ", code='" + code + '\'' +
                    ", hxFundType=" + hxFundType +
                    '}';
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;

            HXFund hxFund = (HXFund) o;

            if (!name.equals(hxFund.name)) return false;
            if (!code.equals(hxFund.code)) return false;
            return hxFundType.equals(hxFund.hxFundType);

        }

        @Override
        public int hashCode() {
            int result = name.hashCode();
            result = 31 * result + code.hashCode();
            result = 31 * result + hxFundType.hashCode();
            return result;
        }
    }
}
