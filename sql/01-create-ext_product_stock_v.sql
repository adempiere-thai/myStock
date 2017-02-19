DROP VIEW ext_product_stock_v;
 
CREATE OR REPLACE VIEW ext_product_stock_v AS 
 SELECT ms.isactive, ms.created, ms.createdby, ms.updated, ms.updatedby, mp.m_product_id, mp.isstocked, mp.value, mp.name AS product_name, mp.help, ms.qtyonhand - ms.qtyreserved AS qtyavailable, ms.qtyonhand, ms.qtyreserved, ms.qtyordered, mp.description, mw.name AS warehouse, mw.m_warehouse_id, ml.value AS locator_code, ml.m_locator_id, mw.ad_client_id, mw.ad_org_id, mp.documentnote
   FROM m_product mp
   LEFT JOIN m_storage ms ON ms.m_product_id = mp.m_product_id
   LEFT JOIN m_locator ml ON ms.m_locator_id = ml.m_locator_id
   LEFT JOIN m_warehouse mw ON ml.m_warehouse_id = mw.m_warehouse_id
  ORDER BY mw.name;
  
ALTER TABLE ext_product_stock_v OWNER TO adempiere;