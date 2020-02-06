/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ejms.repo;

import java.util.List;
import org.ejms.entity.PaymentInvoice;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

/**
 *
 * @author Burhani152
 */
public interface PaymentInvoiceRepository extends BaseRepository<PaymentInvoice, Long> {
    
    @Query("SELECT p FROM PaymentInvoice p WHERE p.accountDocumentNo.accountDocumentNo = :docNo")
    List<PaymentInvoice> findPaymentInvoiceByAccountDocumentNo(@Param("docNo") String docNo);
    
    @Query("SELECT b FROM PaymentInvoice b WHERE b.itsNo.itsNo = :itsNo ORDER BY b.hijriYear, b.hijriMonth")
    List<PaymentInvoice> findPaymentInvoiceByItsNo(@Param("itsNo") int itsNo);
    
    @Query("SELECT b FROM PaymentInvoice b WHERE b.itsNo.itsNo = :itsNo AND b.invoiceType = :invoiceType ORDER BY b.hijriYear, b.hijriMonth")
    List<PaymentInvoice> findPaymentInvoiceByItsNo(@Param("itsNo") int itsNo, @Param("invoiceType") String invoiceType);
    
    @Query(value = "SELECT * FROM PaymentInvoice b WHERE b.itsNo = :itsNo AND b.invoiceType = :invoiceType ORDER BY b.hijriYear desc, b.hijriMonth desc limit 1", nativeQuery = true)
    List<PaymentInvoice> findLastPaymentInvoiceByItsNo(@Param("itsNo") int itsNo, @Param("invoiceType") String invoiceType);

    @Query("SELECT b FROM PaymentInvoice b WHERE b.itsNo.itsNo = :itsNo AND b.hijriYear = :hijriYear ORDER BY b.hijriYear, b.hijriMonth")
    List<PaymentInvoice> findPaymentInvoiceByItsNoByHijriYear(@Param("itsNo") int itsNo, @Param("hijriYear") int hijriYear);
    
    @Query("SELECT b FROM PaymentInvoice b WHERE b.itsNo.itsNo = :itsNo AND b.accountDocumentNo IS NULL ORDER BY b.hijriYear, b.hijriMonth")
    List<PaymentInvoice> findPaymentInvoiceByItsNoOutstanding(@Param("itsNo") int itsNo);
    
    @Query("SELECT b FROM PaymentInvoice b WHERE b.itsNo.itsNo = :itsNo AND b.accountDocumentNo IS NOT NULL ORDER BY b.hijriYear, b.hijriMonth")
    List<PaymentInvoice> findPaymentInvoiceByItsNoPaid(@Param("itsNo") int itsNo);
    
    @Query("SELECT b FROM PaymentInvoice b WHERE b.businessNo.businessNo = :businessNo ORDER BY b.hijriYear, b.hijriMonth")
    List<PaymentInvoice> findPaymentInvoiceByBusinessNo(@Param("businessNo") long businessNo);
    
    @Query(value="SELECT * FROM PaymentInvoice b WHERE b.businessNo = :businessNo ORDER BY b.hijriYear desc, b.hijriMonth desc limit 1", nativeQuery = true)
    List<PaymentInvoice> findLastPaymentInvoiceByBusinessNo(@Param("businessNo") long businessNo);

    @Query("SELECT b FROM PaymentInvoice b WHERE b.businessNo.businessNo = :businessNo AND b.hijriYear = :hijriYear ORDER BY b.hijriYear, b.hijriMonth")
    List<PaymentInvoice> findPaymentInvoiceByBusinessNoByYear(@Param("businessNo") long businessNo, @Param("hijriYear") int hijriYear);
    
    @Query("SELECT b FROM PaymentInvoice b WHERE b.businessNo.businessNo = :businessNo AND b.accountDocumentNo IS NULL ORDER BY b.hijriYear, b.hijriMonth")
    List<PaymentInvoice> findPaymentInvoiceByBusinessNoOutstanding(@Param("businessNo") long businessNo);
    
    @Query("SELECT b FROM PaymentInvoice b WHERE b.businessNo.businessNo = :businessNo AND b.accountDocumentNo IS NOT NULL ORDER BY b.hijriYear, b.hijriMonth")
    List<PaymentInvoice> findPaymentInvoiceByBusinessNoPaid(@Param("businessNo") long businessNo);
    
    @Query("SELECT p FROM PaymentInvoice p WHERE p.accountDocumentNo.accountDocumentNo = :docNo")
    List<PaymentInvoice> findPaymentInvoiceByDocNo(@Param("docNo") String docNo);
}
