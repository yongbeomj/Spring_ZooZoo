package ZooZoo.Service.Crawlling;

import ZooZoo.Domain.DTO.Board.CrawllingDTO;
import ZooZoo.Service.Category.CategorySerivce;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

@Service
public class Share {
    @Autowired
    HttpServletRequest request;
    @Autowired
    CategorySerivce categorySerivce;

    public ArrayList<CrawllingDTO> getShareNews() throws Exception{
        ArrayList<CrawllingDTO> news = new ArrayList<>();
        String realURL = "";
        String realTITLE = "";
        //카테고리 생성하기
        categorySerivce.makeCategory();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy.MM.dd", Locale.KOREA);
        Date currentTime = new Date();

        String dTime = formatter.format(currentTime);
        String e_date = dTime;

        currentTime.setDate(currentTime.getDate() - 1);
        String s_date = formatter.format(currentTime);

        String query = "동물분양";
        String s_from = s_date.replace(".", "");
        String e_to = e_date.replace(".", "");
        int page = 1;
        while (page < 20) {

            String address = "https://search.naver.com/search.naver?where=news&query=" + query + "&sort=1&ds=" + s_date
                    + "&de=" + e_date + "&nso=so%3Ar%2Cp%3Afrom" + s_from + "to" + e_to + "%2Ca%3A&start="
                    + Integer.toString(page);
            Document rawData = Jsoup.connect(address).timeout(5000).get();
            Elements blogOption = rawData.select(".news_area");
            for (Element option : blogOption) {
                realURL = option.select(".news_tit").attr("href");
                realTITLE = option.select("a").attr("title");
                CrawllingDTO crawllingDTO = new CrawllingDTO();
                crawllingDTO.setNewstitle(realTITLE);
                crawllingDTO.setNewslink(realURL);
                news.add(crawllingDTO);
            }
            page += 10;
        }
        return news;
    }
    public ArrayList<CrawllingDTO> getHospitalNews() throws Exception{
        ArrayList<CrawllingDTO> news = new ArrayList<>();
        String realURL = "";
        String realTITLE = "";
        //카테고리 생성하기
        categorySerivce.makeCategory();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy.MM.dd", Locale.KOREA);
        Date currentTime = new Date();

        String dTime = formatter.format(currentTime);
        String e_date = dTime;

        currentTime.setDate(currentTime.getDate() - 1);
        String s_date = formatter.format(currentTime);

        String query = "동물병원";
        String s_from = s_date.replace(".", "");
        String e_to = e_date.replace(".", "");
        int page = 1;
        while (page < 20) {

            String address = "https://search.naver.com/search.naver?where=news&query=" + query + "&sort=1&ds=" + s_date
                    + "&de=" + e_date + "&nso=so%3Ar%2Cp%3Afrom" + s_from + "to" + e_to + "%2Ca%3A&start="
                    + Integer.toString(page);
            Document rawData = Jsoup.connect(address).timeout(5000).get();
            Elements blogOption = rawData.select(".news_area");
            for (Element option : blogOption) {
                realURL = option.select(".news_tit").attr("href");
                realTITLE = option.select("a").attr("title");
                CrawllingDTO crawllingDTO = new CrawllingDTO();
                crawllingDTO.setNewstitle(realTITLE);
                crawllingDTO.setNewslink(realURL);
                news.add(crawllingDTO);
            }
            page += 10;
        }
        return news;
    }
    public ArrayList<CrawllingDTO> getLossNews() throws Exception{
        ArrayList<CrawllingDTO> news = new ArrayList<>();
        String realURL = "";
        String realTITLE = "";
        //카테고리 생성하기
        categorySerivce.makeCategory();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy.MM.dd", Locale.KOREA);
        Date currentTime = new Date();

        String dTime = formatter.format(currentTime);
        String e_date = dTime;

        currentTime.setDate(currentTime.getDate() - 1);
        String s_date = formatter.format(currentTime);

        String query = "유기동물";
        String s_from = s_date.replace(".", "");
        String e_to = e_date.replace(".", "");
        int page = 1;
        while (page < 20) {

            String address = "https://search.naver.com/search.naver?where=news&query=" + query + "&sort=1&ds=" + s_date
                    + "&de=" + e_date + "&nso=so%3Ar%2Cp%3Afrom" + s_from + "to" + e_to + "%2Ca%3A&start="
                    + Integer.toString(page);
            Document rawData = Jsoup.connect(address).timeout(5000).get();
            Elements blogOption = rawData.select(".news_area");
            for (Element option : blogOption) {
                realURL = option.select(".news_tit").attr("href");
                realTITLE = option.select("a").attr("title");
                CrawllingDTO crawllingDTO = new CrawllingDTO();
                crawllingDTO.setNewstitle(realTITLE);
                crawllingDTO.setNewslink(realURL);
                news.add(crawllingDTO);
            }
            page += 10;
        }
        return news;
    }
}
