package com.landleaf.ibsaas.common.utils.help;



import com.landleaf.ibsaas.common.utils.number.NumberUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PagingUtil {

    private static final int PAGESIZE = 15;

    /**
     * 返回分页信息
     *
     * @param pageNum
     * @return 2016年1月19日
     * @author jimmy
     */
    public static Map<String, Integer> getPageContext(Integer pageNum, Integer pageSize) {
        Map<String, Integer> context = new HashMap<String, Integer>(3);
        if (NumberUtils.isEmpty(pageSize)) {
            pageSize = PAGESIZE;
        }
        context.put("min", (pageNum - 1) * pageSize);
        context.put("pagesize", pageSize);
        context.put("pageNum", pageNum);
        return context;
    }

    /**
     * 获取分页信息
     *
     * @param total
     * @param pageNum
     * @param pageSize
     * @return 2016年1月19日
     * @author jimmy
     */
    public static Map<String, Object> getPageInfo(Integer total, Integer pageNum, Integer pageSize) {
        Map<String, Object> pageInfo = new HashMap<String, Object>(6);
        if (total > 0) {
            pageInfo.put("pageNum", pageNum);
            pageInfo.put("total", total);
            if (NumberUtils.isEmpty(pageSize)) {
                pageSize = PAGESIZE;
            }
            int pageCount = (total + pageSize- 1) / pageSize;
            //是否有上一页
            boolean hasPreviousPage = false;
            //是否有下一页
            boolean hasNextPage = false;
            if (pageNum == 1) {
                hasPreviousPage = false;
            } else {
                hasPreviousPage = true;
                pageInfo.put("prePage", pageNum - 1);
            }
            if (pageNum + 1 > pageCount) {
                hasNextPage = false;
            } else {
                hasNextPage = true;
                pageInfo.put("nextPage", pageNum + 1);
            }
            int begin = 1;
            int end = pageCount;
            if (pageNum - 4 > 0) {
                begin = pageNum - 4;
            }
            if (pageCount < 8) {
                end = pageCount;
            } else if (pageNum + 3 < 8) {
                end = 8;
            } else if (pageNum + 3 < pageCount) {
                end = pageNum + 3;
            }
            //当前显示的页码
            List<Integer> navigatepageNums = new ArrayList<Integer>();
            for (int i = begin; i <= end; i++) {
                navigatepageNums.add(i);
            }
            pageInfo.put("hasPreviousPage", hasPreviousPage);
            pageInfo.put("hasNextPage", hasNextPage);
            pageInfo.put("navigatepageNums", navigatepageNums);
            pageInfo.put("pages", pageCount);
            return pageInfo;
        }
        pageInfo.put("pageNum", pageNum);
        pageInfo.put("total", total);
        pageInfo.put("hasPreviousPage", false);
        pageInfo.put("hasNextPage", false);
        pageInfo.put("navigatepageNums", null);
        pageInfo.put("pages", 0);
        return pageInfo;
    }


}
