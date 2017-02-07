package com.zburzhynski.jsender.impl.domain;

import org.hibernate.sql.JoinType;

/**
 * Hibernate criteria alias.
 * <p/>
 * Date: 24.04.15
 *
 * @author Vladimir Zburzhynski
 */
public class CriteriaAlias {

    private String associationPath;

    private String alias;

    private JoinType joinType;

    /**
     * Constructor.
     *
     * @param associationPath association path
     * @param alias alias
     */
    public CriteriaAlias(String associationPath, String alias) {
        this.associationPath = associationPath;
        this.alias = alias;
    }

    /**
     * Constructor.
     *
     * @param associationPath association path
     * @param alias           alias
     * @param joinType        join type
     */
    public CriteriaAlias(String associationPath, String alias, JoinType joinType) {
        this.associationPath = associationPath;
        this.alias = alias;
        this.joinType = joinType;
    }

    /**
     * Gets association path.
     *
     * @return association path
     */
    public String getAssociationPath() {
        return associationPath;
    }

    /**
     * Sets association path.
     *
     * @param associationPath association path to set
     */
    public void setAssociationPath(String associationPath) {
        this.associationPath = associationPath;
    }

    /**
     * Gets alias.
     *
     * @return alias
     */
    public String getAlias() {
        return alias;
    }

    /**
     * Sets alias.
     *
     * @param alias alias to set
     */
    public void setAlias(String alias) {
        this.alias = alias;
    }

    /**
     * Gets join type.
     *
     * @return join type
     */
    public JoinType getJoinType() {
        return joinType;
    }

    /**
     * Sets join type.
     *
     * @param joinType join type to set
     */
    public void setJoinType(JoinType joinType) {
        this.joinType = joinType;
    }

}
