package com.landleaf.ibsaas.web.web.filter.web;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.http.HttpStatus;
import org.springframework.util.CollectionUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
* @Title: IpWhiteListFilter 
* @Description: ip白名单过滤器
*/
public class IpWhiteListFilter extends OncePerRequestFilter {

    private Log logger = LogFactory.getLog(IpWhiteListFilter.class);

    /**
     * ip白名单
     */
    private List<String> whiteList;

    /**
     *
     * @param request
     * @param response
     * @param filterChain
     * @throws ServletException
     * @throws IOException
     */
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        //获取请求地址
        String address = request.getRemoteAddr();
        if (CollectionUtils.isEmpty(whiteList) || whiteList.contains(address)) {
            filterChain.doFilter(request, response);
        } else {
            logger.warn("Accept Invalid Request From ["+ address +"]");
            response.setStatus(HttpStatus.SC_FORBIDDEN);
        }
    }

    public List<String> getWhiteList() {
        return whiteList;
    }

    public void setWhiteList(List<String> whiteList) {
        this.whiteList = whiteList;
    }
}
