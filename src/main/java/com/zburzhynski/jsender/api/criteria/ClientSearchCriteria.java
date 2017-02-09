package com.zburzhynski.jsender.api.criteria;

/**
 * Client search criteria.
 * <p/>
 * Date: 08.02.2017
 *
 * @author Nikita Shevtsov
 */
public class ClientSearchCriteria {

    private Long start;

    private Long end;

    public Long getStart() {
        return start;
    }

    public void setStart(Long start) {
        this.start = start;
    }

    public Long getEnd() {
        return end;
    }

    public void setEnd(Long end) {
        this.end = end;
    }

}
