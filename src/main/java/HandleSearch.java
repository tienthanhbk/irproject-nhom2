import org.apache.logging.log4j.LogManager;
import org.apache.lucene.queryparser.classic.ParseException;
import org.apache.lucene.search.highlight.InvalidTokenOffsetsException;
import org.eclipse.jetty.server.Request;
import org.eclipse.jetty.server.handler.AbstractHandler;
import org.json.simple.JSONObject;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class HandleSearch extends AbstractHandler
{
    private static final org.apache.logging.log4j.Logger logger = LogManager.getLogger(HandleSearch.class);
    private Searcher searcher = new Searcher();
    public static final int hitsPerPage = 10;

    public HandleSearch() throws IOException {
    }

    public void handle(String target, Request baseRequest, HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException
    {
        if (!target.equalsIgnoreCase("/search"))
            return;

        try {
            String q = baseRequest.getParameter("q");
            System.out.println(q);
            String page_str = baseRequest.getParameter("page");

//            org.apache.logging.log4j.LogManager.getRootLogger().debug("Query: " + q + " page: " + page_str);
            logger.debug("Query: " + q + " page: " + page_str);
            int page = 1;
            if (page_str!= null && !page_str.isEmpty())
                page = Integer.parseInt(page_str);
            JSONObject result = searcher.search(q, page);
            response.setContentType("application/json;charset=utf-8");
            response.setStatus(HttpServletResponse.SC_OK);
            baseRequest.setHandled(true);
            response.getWriter().write(result.toJSONString());

        } catch (ParseException e) {
            e.printStackTrace();
        } catch (InvalidTokenOffsetsException e) {
            e.printStackTrace();
        }
    }
}