/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.fmb.entity;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author murtuza
 */
@Entity
@Table(name = "account")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Account.findAll", query = "SELECT a FROM Account a"),
    @NamedQuery(name = "Account.findAllLedgers", query = "SELECT a FROM Account a WHERE a.parent.accountNo='1' ORDER BY a.accountNo"),
    @NamedQuery(name = "Account.findByAccountNo", query = "SELECT a FROM Account a WHERE a.accountNo = :accountNo"),
    @NamedQuery(name = "Account.findByAccountName", query = "SELECT a FROM Account a WHERE a.accountName = :accountName"),
    @NamedQuery(name = "Account.findByAccountType", query = "SELECT a FROM Account a WHERE a.accountType = :accountType"),
    @NamedQuery(name = "Account.findByParentNo", query = "SELECT a FROM Account a WHERE a.parent.accountNo = :parentNo"),
    @NamedQuery(name = "Account.findByAccountType2", query = "SELECT a FROM Account a WHERE a.accountType2 = :accountType2"),
    @NamedQuery(name = "Account.findByOpeningBalance", query = "SELECT a FROM Account a WHERE a.openingBalance = :openingBalance")})
public class Account implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 45)
    @Column(name = "accountNo")
    private String accountNo;
    @Size(max = 255)
    @Column(name = "accountName")
    private String accountName;
    @Size(max = 45)
    @Column(name = "accountType")
    private String accountType;
    @ManyToOne(optional = true, fetch = FetchType.LAZY)
    @JoinColumn(name="parentNo")    
    private Account parent;
    @Size(max = 45)
    @Column(name = "accountType2")
    private String accountType2;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "openingBalance")
    private Double openingBalance;
    //@OneToMany(mappedBy = "accountNo", fetch = FetchType.LAZY)
    //private List<Ledger> ledgerList;

    public Account() {
    }

    public Account(String accountNo) {
        this.accountNo = accountNo;
    }

    public Account(String accountNo, Account parentNo) {
        this.accountNo = accountNo;
        this.parent = parentNo;
    }

    public String getAccountNo() {
        return accountNo;
    }

    public void setAccountNo(String accountNo) {
        this.accountNo = accountNo;
    }

    public String getAccountName() {
        return accountName;
    }

    public void setAccountName(String accountName) {
        this.accountName = accountName;
    }

    public String getAccountType() {
        return accountType;
    }

    public void setAccountType(String accountType) {
        this.accountType = accountType;
    }

    public Account getParent() {
        return parent;
    }

    public void setParent(Account parent) {
        this.parent = parent;
    }

    public String getAccountType2() {
        return accountType2;
    }

    public void setAccountType2(String accountType2) {
        this.accountType2 = accountType2;
    }

    public Double getOpeningBalance() {
        return openingBalance;
    }

    public void setOpeningBalance(Double openingBalance) {
        this.openingBalance = openingBalance;
    }

    //@XmlTransient
    //public List<Ledger> getLedgerList() {
    //    return ledgerList;
    //}

    //public void setLedgerList(List<Ledger> ledgerList) {
    //    this.ledgerList = ledgerList;
    //}

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (accountNo != null ? accountNo.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Account)) {
            return false;
        }
        Account other = (Account) object;
        if ((this.accountNo == null && other.accountNo != null) || (this.accountNo != null && !this.accountNo.equals(other.accountNo))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        if(getAccountName()!=null){
            sb.append(getAccountName());
        }
        if(getAccountNo()!=null){
            sb.append(" [").append(getAccountNo()).append("]");
        }
        return sb.toString();
        //return getAccountName() + " [" + accountNo + "]";
    }
    
    @XmlTransient
    public String getParentAccount(){
        if(getParent()==null){
            return null;
        }
        return getParent().toString();
    }
    
}
