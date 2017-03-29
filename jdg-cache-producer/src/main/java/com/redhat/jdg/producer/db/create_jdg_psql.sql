drop database products;
create database products;
drop table product;
create table product 
(
    itemid numeric(10) CONSTRAINT itemid_pk PRIMARY KEY,
    name varchar(50),
    description varchar(1024),
    price decimal(10,2)
);

COPY product FROM 'product-data.csv' ( FORMAT CSV, DELIMITER(','), HEADER(TRUE));
