package share.app.products;

public interface IOperations
{
	public static final String GET_PRODUCTS = "server.app.products.Operations:getProducts";
	public static final String ADD_PRODUCT = "server.app.products.Operations:addProduct";
	public static final String EDIT_PRODUCT = "server.app.products.Operations:editProduct";
	public static final String DELETE_PRODUCT = "server.app.products.Operations:deleteProduct";

	public Product[] getProducts(Long barCode, Integer sectionID, Integer brand);

	public Product addProduct(Product product);

	public Boolean editProduct(Product original, Product newProduct);

	public Boolean deleteProduct(Product product);
}