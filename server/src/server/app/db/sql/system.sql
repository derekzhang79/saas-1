CREATE TABLE CLIENT
(
	id             int(9) unsigned NOT NULL AUTO_INCREMENT,
	owner_name     varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
	company_name   varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
	address        varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
	city           varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
	postal_code    int(5) unsigned NOT NULL,
	telephone      int(9) unsigned NOT NULL,
	fax            int(9) unsigned NOT NULL,
	identification varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
	email          varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
	webpage        varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
	ddbb           varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
	
	PRIMARY KEY (id)
)
ENGINE=InnoDB  DEFAULT CHARSET=utf8 COLLATE=utf8_general_ci AUTO_INCREMENT=2;

CREATE TABLE TASK
(	
	id   int(9) unsigned NOT NULL AUTO_INCREMENT,
	name varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
	path varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
	
	PRIMARY KEY (id),
	UNIQUE KEY name (name),
	UNIQUE KEY path (path)	
)
ENGINE=InnoDB  DEFAULT CHARSET=utf8 COLLATE=utf8_general_ci AUTO_INCREMENT=1;

INSERT INTO TASK (id, name, path) VALUES
(1, 'Agregar marcas',   'client.app.brands.tasks.AddBrand'),
(2, 'Consultar marcas', 'client.app.brands.tasks.BrowseBrands'),
(3, 'Eliminar marcas',  'client.app.brands.tasks.DeleteBrand'),
(4, 'Modificar marcas', 'client.app.brands.tasks.EditBrand'),
(5, 'Buscar marcas',    'client.app.brands.tasks.SearchBrand'),

(6, 'Agregar arqueos de caja',   'client.app.cashcount.tasks.AddCashCount'),
(7, 'Consultar arqueos de caja', 'client.app.cashcount.tasks.BrowseCashCount'),
(8, 'Eliminar arqueos de caja',  'client.app.cashcount.tasks.DeleteCashCount'),
(9, 'Modificar arqueos de caja', 'client.app.cashcount.tasks.EditCashCount'),

(10, 'Agregar clientes',   'client.app.contacts.clients.tasks.AddClient'),
(11, 'Consultar clientes', 'client.app.contacts.clients.tasks.BrowseClients'),
(12, 'Eliminar clientes',  'client.app.contacts.clients.tasks.DeleteClient'),
(13, 'Modificar clientes', 'client.app.contacts.clients.tasks.EditClient'),
(14, 'Buscar clientes',    'client.app.contacts.clients.tasks.SearchClient'),

(15, 'Agregar proveedores',   'client.app.contacts.suppliers.tasks.AddSupplier'),
(16, 'Consultar proveedores', 'client.app.contacts.suppliers.tasks.BrowseSuppliers'),
(17, 'Eliminar proveedores',  'client.app.contacts.suppliers.tasks.DeleteSupplier'),
(18, 'Modificar proveedores', 'client.app.contacts.suppliers.tasks.EditSupplier'),

(19, 'Agregar facturas',               'client.app.invoices.tasks.AddInvoice'),
(20, 'Agregar detalles de facturas',   'client.app.invoices.tasks.AddInvoiceDetail'),
(21, 'Consultar detalles de facturas', 'client.app.invoices.tasks.BrowseInvoiceDetail'),
(22, 'Consultar facturas',             'client.app.invoices.tasks.BrowseInvoices'),
(23, 'Eliminar facturas',              'client.app.invoices.tasks.DeleteInvoice'),
(24, 'Eliminar detalles de facturas',  'client.app.invoices.tasks.DeleteInvoiceDetail'),
(25, 'Modificar facturas',             'client.app.invoices.tasks.EditInvoice'),
(26, 'Modificar detalles de facturas', 'client.app.invoices.tasks.EditInvoiceDetail'),

(27, 'Agregar registros de ventas',               'client.app.journals.tasks.AddJournal'),
(28, 'Consultar detalles de registros de ventas', 'client.app.journals.tasks.BrowseJournalDetail'),
(29, 'Consultar registros de ventas',             'client.app.journals.tasks.BrowseJournals'),
(30, 'Eliminar registros de ventas',              'client.app.journals.tasks.DeleteJournal'),
(31, 'Modificar registros de ventas',             'client.app.journals.tasks.EditJournal'),
(32, 'Modificar detalles de registros de ventas', 'client.app.journals.tasks.EditJournalDetail'),

(33, 'Agregar productos',   'client.app.products.tasks.AddProduct'),
(34, 'Consultar productos', 'client.app.products.tasks.BrowseProducts'),
(35, 'Eliminar productos',  'client.app.products.tasks.DeleteProduct'),
(36, 'Modificar productos', 'client.app.products.tasks.EditProduct'),
(37, 'Buscar productos',    'client.app.products.tasks.SearchProduct'),

(38, 'Agregar rubros',   'client.app.sections.tasks.AddSection'),
(39, 'Consultar rubros', 'client.app.sections.tasks.BrowseSections'),
(40, 'Eliminar rubros',  'client.app.sections.tasks.DeleteSection'),
(41, 'Modificar rubros', 'client.app.sections.tasks.EditSection'),
(42, 'Buscar rubros',    'client.app.sections.tasks.SearchSection'),

(43, 'Agregar incidencias',   'client.app.support.tasks.AddSupport'),
(44, 'Consultar incidencias', 'client.app.support.tasks.BrowseSupports'),
(45, 'Modificar incidencias', 'client.app.support.tasks.EditSupport'),

(46, 'Agregar impuestos',   'client.app.taxes.tasks.AddTax'),
(47, 'Consultar impuestos', 'client.app.taxes.tasks.BrowseTaxes'),
(48, 'Eliminar impuestos',  'client.app.taxes.tasks.DeleteTax'),
(49, 'Modificar impuestos', 'client.app.taxes.tasks.EditTax'),

(50, 'Agregar pedidos',               'client.app.workshop.tasks.AddFixOrder'),
(51, 'Agregar detalles de pedidos',   'client.app.workshop.tasks.AddFixOrderDetail'),
(52, 'Consultar pedidos',             'client.app.workshop.tasks.BrowseFixOrder'),
(53, 'Consultar detalles de pedidos', 'client.app.workshop.tasks.BrowseFixOrderDetail'),
(54, 'Eliminar pedidos',              'client.app.workshop.tasks.DeleteFixOrder'),
(55, 'Eliminar detalles de pedidos',  'client.app.workshop.tasks.DeleteFixOrderDetail'),
(56, 'Modificar pedidos',             'client.app.workshop.tasks.EditFixOrder'),
(57, 'Modificar detalles de pedidos', 'client.app.workshop.tasks.EditFixOrderDetail'),

(58, 'Agregar usuarios',   'client.app.configuration.users.tasks.AddUser'),
(59, 'Consultar usuarios', 'client.app.configuration.users.tasks.BrowseUsers'),
(60, 'Eliminar usuarios',  'client.app.configuration.users.tasks.DeleteUser'),
(61, 'Modificar usuarios', 'client.app.configuration.users.tasks.EditUser'),

(62, 'Agregar grupos de usuarios',               'client.app.configuration.usergroups.tasks.AddUserGroup'),
(63, 'Consultar grupos de usuarios',             'client.app.configuration.usergroups.tasks.BrowseUserGroups'),
(64, 'Eliminar grupos de usuarios',              'client.app.configuration.usergroups.tasks.DeleteUserGroup'),
(65, 'Modificar grupos de usuarios',             'client.app.configuration.usergroups.tasks.EditUserGroup'),
(66, 'Modificar permisos de grupos de usuarios', 'client.app.configuration.usergroups.tasks.EditUserGroupPermissions'),
(67, 'Buscar grupos de usuarios',                'client.app.configuration.usergroups.tasks.SearchUserGroup');

CREATE TABLE USER_GROUP
(	
	id            int(9) unsigned NOT NULL AUTO_INCREMENT,
	name          varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
	administrator boolean NOT NULL,
	client        int(9) unsigned NOT NULL,
	
	PRIMARY KEY (id),
	UNIQUE KEY name (name),
	
	KEY client (client),
	FOREIGN KEY (client) REFERENCES CLIENT(id) ON DELETE NO ACTION ON UPDATE NO ACTION	
)
ENGINE=InnoDB  DEFAULT CHARSET=utf8 COLLATE=utf8_general_ci AUTO_INCREMENT=2;

INSERT INTO USER_GROUP (id, name, administrator, client) VALUES
(1, 'Administrador', 1, 1);

CREATE TABLE USER
(	
	id            int(9) unsigned NOT NULL AUTO_INCREMENT,
	name          varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
	password      varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
	ticket        varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
	session_id    varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
	user_group    int(9) unsigned NOT NULL,
	
	PRIMARY KEY (id),
	UNIQUE KEY name (name),
	
	KEY user_group (user_group),
	FOREIGN KEY (user_group) REFERENCES USER_GROUP(id) ON DELETE NO ACTION ON UPDATE NO ACTION	
)
ENGINE=InnoDB  DEFAULT CHARSET=utf8 COLLATE=utf8_general_ci AUTO_INCREMENT=2;

INSERT INTO USER (id, name, password, ticket, session_id, user_group) VALUES
(1, 'user', '202cb962ac59075b964b07152d234b70', '', '', 1);

CREATE TABLE PERMISSION
(	
	id         int(9) unsigned NOT NULL AUTO_INCREMENT,
	user_group int(9) unsigned NOT NULL,
	task       int(9) unsigned NOT NULL,
	
	PRIMARY KEY (id),
	UNIQUE KEY user_task (user_group, task),
	
	KEY user_group (user_group),
	FOREIGN KEY (user_group) REFERENCES USER_GROUP(id) ON DELETE NO ACTION ON UPDATE NO ACTION,
	KEY task (task),
	FOREIGN KEY (task) REFERENCES TASK(id) ON DELETE NO ACTION ON UPDATE NO ACTION	
)
ENGINE=InnoDB  DEFAULT CHARSET=utf8 COLLATE=utf8_general_ci AUTO_INCREMENT=1;

CREATE TABLE SUPPORT
(	
	id                int(9) unsigned NOT NULL AUTO_INCREMENT,
	user              int(9) unsigned NOT NULL,
	module            varchar(2) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
	status            varchar(1) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
	date_creation     date NOT NULL,
	date_modification date NOT NULL,
	name              varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
	description       varchar(500) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
	
	PRIMARY KEY (id),
	
	KEY user (user),
	FOREIGN KEY (user) REFERENCES USER(id) ON DELETE NO ACTION ON UPDATE NO ACTION
)
ENGINE=InnoDB  DEFAULT CHARSET=utf8 COLLATE=utf8_general_ci AUTO_INCREMENT=1;

CREATE TABLE LOGIN
(	
	id           int(9) unsigned NOT NULL AUTO_INCREMENT,
	ip           varchar(15) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
	java_vendor  varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
	java_version varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
	java_jre     varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
	os_arch      varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
	os_name      varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
	os_version   varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
	user         int(9) unsigned NOT NULL,
	
	PRIMARY KEY (id),
	
	KEY user (user),
	FOREIGN KEY (user) REFERENCES USER(id) ON DELETE NO ACTION ON UPDATE NO ACTION	
)
ENGINE=InnoDB  DEFAULT CHARSET=utf8 COLLATE=utf8_general_ci AUTO_INCREMENT=1;
