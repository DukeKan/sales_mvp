update SALES_ORDER_LINE set QUANTITY = 0 where QUANTITY is null ;
alter table SALES_ORDER_LINE alter column QUANTITY set not null ;
