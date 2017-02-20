package com.zburzhynski.jsender.api.criteria;

/**
 * Sent message search criteria.
 * <p/>
 * Date: 19.02.2017
 *
 * @author Nikita Shevtsou
 */
public class SentMessageSearchCriteria {

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
