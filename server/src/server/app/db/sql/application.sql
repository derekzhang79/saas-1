CREATE TABLE SECTION (
	
	id     int(9) unsigned NOT NULL AUTO_INCREMENT,
	name   varchar(30) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
	profit decimal(3,2) NOT NULL,
	
	PRIMARY KEY (id),
	UNIQUE KEY name(name)
	
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_general_ci AUTO_INCREMENT=13;

INSERT INTO SECTION (id, name) VALUES
(1, 'Mercería'),
(2, 'Complementos'),
(3, 'Paquetería'),
(4, 'Lencería'),
(5, 'Taller'),
(6, 'Bebé'),
(7, 'Baño'),
(8, 'Ropa'),
(11, 'Barras'),
(12, 'Telas');

CREATE TABLE JOURNAL (

	id   int(9) unsigned NOT NULL AUTO_INCREMENT,
	date date NOT NULL,

	PRIMARY KEY (id),
	UNIQUE KEY date(date)
	
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_general_ci AUTO_INCREMENT=1;

CREATE TABLE JOURNAL_DETAIL (

	id      int(9) unsigned NOT NULL AUTO_INCREMENT,
	journal int(9) unsigned NOT NULL,
	section int(9) unsigned NOT NULL,
	amount  decimal(6,2) NOT NULL,

	PRIMARY KEY (id),
	UNIQUE KEY index01(journal, section),
	
	KEY journal (journal),
	FOREIGN KEY (journal) REFERENCES JOURNAL(id) ON DELETE NO ACTION ON UPDATE NO ACTION,
	KEY section (section),
	FOREIGN KEY (section) REFERENCES SECTION(id) ON DELETE NO ACTION ON UPDATE NO ACTION
	
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_general_ci AUTO_INCREMENT=1;

CREATE TABLE CASH_COUNT (
	
	id        int(9) unsigned NOT NULL AUTO_INCREMENT,
	date      date NOT NULL,
	type_500  int(3) unsigned NOT NULL,
	type_200  int(3) unsigned NOT NULL,
	type_100  int(3) unsigned NOT NULL,
	type_50   int(3) unsigned NOT NULL,
	type_20   int(3) unsigned NOT NULL,
	type_10   int(3) unsigned NOT NULL,
	type_5    int(3) unsigned NOT NULL,
	type_2    int(3) unsigned NOT NULL,
	type_1    int(3) unsigned NOT NULL,
	type_0_5  int(3) unsigned NOT NULL,
	type_0_2  int(3) unsigned NOT NULL,
	type_0_1  int(3) unsigned NOT NULL,
	type_0_05 int(3) unsigned NOT NULL,
	type_0_02 int(3) unsigned NOT NULL,
	type_0_01 int(3) unsigned NOT NULL,

	PRIMARY KEY (id),
	UNIQUE KEY date(date)
  
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_general_ci AUTO_INCREMENT=1;

CREATE TABLE DICTIONARY (
	
	category varchar(30)  CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
	code     varchar(30)  CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
	value    varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
	
	PRIMARY KEY (category, code)
	
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_general_ci;

INSERT INTO DICTIONARY (category, code, value) VALUES
('PAYMENT_METHOD', 'C', 'Contado'),
('PAYMENT_METHOD', 'T', 'Tarjeta'),
('PAYMENT_METHOD', 'Q', 'Cheque'),
('PAYMENT_METHOD', 'S', 'Transferencia'),
('MEASURING_UNIT', 'MT', 'Metros'),
('MEASURING_UNIT', 'OT', 'Otro'),
('MEASURING_UNIT', 'UN', 'Unidades'),
('FIX_ORDER_STATUS', 'R', 'Recibido'),
('FIX_ORDER_STATUS', 'P', 'En proceso'),
('FIX_ORDER_STATUS', 'T', 'Terminado'),
('MONTHS', '01', 'Enero'),
('MONTHS', '02', 'Febrero'),
('MONTHS', '03', 'Marzo'),
('MONTHS', '04', 'Abril'),
('MONTHS', '05', 'Mayo'),
('MONTHS', '06', 'Junio'),
('MONTHS', '07', 'Julio'),
('MONTHS', '08', 'Agosto'),
('MONTHS', '09', 'Septiembre'),
('MONTHS', '10', 'Octubre'),
('MONTHS', '11', 'Noviembre'),
('MONTHS', '12', 'Diciembre'),
('SYSTEM_MODULES', 'RU', 'Rubros'),
('SYSTEM_MODULES', 'DI', 'Registro de ventas'),
('SYSTEM_MODULES', 'AR', 'Arqueo de caja'),
('SYSTEM_MODULES', 'CL', 'Clientes'),
('SYSTEM_MODULES', 'PV', 'Proveedores'),
('SYSTEM_MODULES', 'PE', 'Pedidos'),
('SYSTEM_MODULES', 'PR', 'Productos'),
('SYSTEM_MODULES', 'FA', 'Facturación'),
('SYSTEM_MODULES', 'CO', 'Configuración'),
('SYSTEM_MODULES', 'SO', 'Soporte'),
('SYSTEM_MODULES', 'OT', 'Otro'),
('SUPPORT_STATUS', 'P', 'Pendiente'),
('SUPPORT_STATUS', 'E', 'En proceso'),
('SUPPORT_STATUS', 'C', 'Corregido'),
('TAX', 'A', 'General'),
('TAX', 'B', 'Reducido'),
('TAX', 'C', 'Superreducido');

CREATE TABLE CONTACT (
	
	id             int(9) unsigned NOT NULL AUTO_INCREMENT,
	first_name     varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
	last_name      varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
	identification varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
	address        varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
	city           varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
	postal_code    int(5) unsigned NOT NULL,
	telephone      int(9) unsigned NOT NULL,
	mobile         int(9) unsigned NOT NULL,
	email          varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
	contact_person varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
	comments       varchar(500) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
	
	PRIMARY KEY (id)
	
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_general_ci AUTO_INCREMENT=1;

CREATE TABLE CLIENT (
	
	id      int(9) unsigned NOT NULL AUTO_INCREMENT,
	contact int(9) unsigned NOT NULL,
	
	PRIMARY KEY (id),
	
	UNIQUE KEY contact(contact),
	FOREIGN KEY (contact) REFERENCES CONTACT(id) ON DELETE NO ACTION ON UPDATE NO ACTION
	
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_general_ci AUTO_INCREMENT=1;

CREATE TABLE SUPPLIER (
	
	id      int(9) unsigned NOT NULL AUTO_INCREMENT,
	contact int(9) unsigned NOT NULL,
	
	PRIMARY KEY (id),
	
	UNIQUE KEY contact(contact),
	FOREIGN KEY (contact) REFERENCES CONTACT(id) ON DELETE NO ACTION ON UPDATE NO ACTION
	
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_general_ci AUTO_INCREMENT=1;

CREATE TABLE FIX_ORDER (
	
	id       int(9) unsigned NOT NULL AUTO_INCREMENT,
	client   int(9) unsigned NOT NULL,
	status   varchar(1) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
	start    date NOT NULL,
	finish   date NOT NULL,
	comments varchar(500) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
	
	PRIMARY KEY (id),
	
	KEY client (client),
	FOREIGN KEY (client) REFERENCES CLIENT(id) ON DELETE NO ACTION ON UPDATE NO ACTION
	
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_general_ci AUTO_INCREMENT=1;

CREATE TABLE FIX_ORDER_DETAIL (
	
	id        int(9) unsigned NOT NULL AUTO_INCREMENT,
	fix_order int(9) unsigned NOT NULL,
	line      int(3) unsigned NOT NULL,
	quantity  int(3) unsigned NOT NULL,
	detail    varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
	amount    decimal(6,2) NOT NULL,
	
	PRIMARY KEY (id),
	
	KEY fix_order (fix_order),
	FOREIGN KEY (fix_order) REFERENCES FIX_ORDER(id) ON DELETE NO ACTION ON UPDATE NO ACTION
	
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_general_ci AUTO_INCREMENT=1;

CREATE TABLE BRAND (
	
	id   int(9) unsigned NOT NULL AUTO_INCREMENT,
	name varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
	
	PRIMARY KEY (id),
	UNIQUE KEY name(name)
	
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_general_ci AUTO_INCREMENT=1;

CREATE TABLE TAX (
	
	id     int(9) unsigned NOT NULL AUTO_INCREMENT,
	type   varchar(1) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
	value  decimal(4,2) NOT NULL,
	start  date NOT NULL,
	
	PRIMARY KEY (id)
	
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_general_ci AUTO_INCREMENT=1;

CREATE TABLE PRODUCT (
	
	id             int(9) unsigned NOT NULL AUTO_INCREMENT,
	section        int(9) unsigned NOT NULL,
	bar_code       bigint(13) unsigned NOT NULL,
	name           varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
	description    varchar(300) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
	cost_price     decimal(6,2) NOT NULL,
	sale_price     decimal(6,2) NOT NULL,
	tax            varchar(1) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
	brand          int(9) unsigned NOT NULL,
	model          varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
	color          varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
	measuring_unit varchar(2) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
	length         int(3) unsigned NOT NULL,
	quantity       int(5) unsigned NOT NULL,
	
	PRIMARY KEY (id),
	
	INDEX section(section),
	FOREIGN KEY (section) REFERENCES SECTION(id) ON DELETE NO ACTION ON UPDATE NO ACTION,
	
	INDEX brand(brand),
	FOREIGN KEY (brand) REFERENCES BRAND(id) ON DELETE NO ACTION ON UPDATE NO ACTION
	
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_general_ci AUTO_INCREMENT=1;

CREATE TABLE INVOICE (
	
	id             int(9) unsigned NOT NULL AUTO_INCREMENT,
	number         int(9) unsigned NOT NULL,
	date           date NOT NULL,
	client         int(9) unsigned NOT NULL,
	discount       decimal(5,2) NOT NULL,
	payment_method varchar(1) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
	comments       varchar(500) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
	
	PRIMARY KEY (id),
	UNIQUE KEY number(number),
	
	INDEX client(client),
	FOREIGN KEY (client) REFERENCES CLIENT(id) ON DELETE NO ACTION ON UPDATE NO ACTION
	
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_general_ci AUTO_INCREMENT=1;

CREATE TABLE INVOICE_DETAIL (
	
	id       int(9) unsigned NOT NULL AUTO_INCREMENT,
	invoice  int(9) unsigned NOT NULL,
	line     int(3) unsigned NOT NULL,
	product  int(9) unsigned NOT NULL,
	quantity decimal(8,2) NOT NULL,
	tax      varchar(1) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
	price    decimal(6,2) NOT NULL,
	
	PRIMARY KEY (id),
	
	INDEX invoice(invoice),
	FOREIGN KEY (invoice) REFERENCES INVOICE(id) ON DELETE NO ACTION ON UPDATE NO ACTION,
	
	INDEX product(product),
	FOREIGN KEY (product) REFERENCES PRODUCT(id) ON DELETE NO ACTION ON UPDATE NO ACTION
	
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_general_ci AUTO_INCREMENT=1;
