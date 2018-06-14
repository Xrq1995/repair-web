package cn.edu.ahu.repairbackend.entity;

import javax.persistence.*;
import javax.persistence.criteria.CriteriaBuilder;
import java.io.Serializable;

/**
 * Author: WangMiao
 * Date: 2018/1/11
 * Time: 22:22
 * Created with IntelliJ IDEA
 * Description:
 */
@Entity
@Table(name = "repaircategories")
public class RepairCategoriesEntity implements Serializable {
    @Id
    @Column(name= "id")
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "parent")
    private String parent;

    @Column(name = "firstchild")
    private String firstChild;

    @Column(name = "secondchild")
    private String secondChild;

    @Column(name = "thirdchild")
    private String thirdChild;

    @Column(name = "fourthchild")
    private String fourthChild;

    @Column(name = "fifthchild")
    private String fifthChild;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getParent() {
        return parent;
    }

    public void setParent(String parent) {
        this.parent = parent;
    }

    public String getFirstChild() {
        return firstChild;
    }

    public void setFirstChild(String firstChild) {
        this.firstChild = firstChild;
    }

    public String getSecondChild() {
        return secondChild;
    }

    public void setSecondChild(String secondChild) {
        this.secondChild = secondChild;
    }

    public String getThirdChild() {
        return thirdChild;
    }

    public void setThirdChild(String thirdChild) {
        this.thirdChild = thirdChild;
    }

    public String getFourthChild() {
        return fourthChild;
    }

    public void setFourthChild(String fourthChild) {
        this.fourthChild = fourthChild;
    }

    public String getFifthChild() {
        return fifthChild;
    }

    public void setFifthChild(String fifthChild) {
        this.fifthChild = fifthChild;
    }

}
